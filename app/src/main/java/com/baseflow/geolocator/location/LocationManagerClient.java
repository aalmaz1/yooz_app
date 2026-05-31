package com.baseflow.geolocator.location;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import androidx.core.location.LocationListenerCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.core.location.LocationRequestCompat;
import com.baseflow.geolocator.errors.ErrorCallback;
import com.baseflow.geolocator.errors.ErrorCodes;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class LocationManagerClient implements LocationClient, LocationListenerCompat {
    private static final long TWO_MINUTES = 120000;
    public Context context;
    private Location currentBestLocation;
    private String currentLocationProvider;
    private ErrorCallback errorCallback;
    private boolean isListening = false;
    private final LocationManager locationManager;
    private final LocationOptions locationOptions;
    private final NmeaClient nmeaClient;
    private PositionChangedCallback positionChangedCallback;

    @Override // com.baseflow.geolocator.location.LocationClient
    public boolean onActivityResult(int i, int i2) {
        return false;
    }

    @Override // androidx.core.location.LocationListenerCompat, android.location.LocationListener
    public void onProviderEnabled(String str) {
    }

    public LocationManagerClient(Context context, LocationOptions locationOptions) {
        this.locationManager = (LocationManager) context.getSystemService("location");
        this.locationOptions = locationOptions;
        this.context = context;
        this.nmeaClient = new NmeaClient(context, locationOptions);
    }

    static boolean isBetterLocation(Location location, Location location2) {
        if (location2 == null) {
            return true;
        }
        long time = location.getTime() - location2.getTime();
        boolean z = time > TWO_MINUTES;
        boolean z2 = time < -120000;
        boolean z3 = time > 0;
        if (z) {
            return true;
        }
        if (z2) {
            return false;
        }
        float accuracy = (int) (location.getAccuracy() - location2.getAccuracy());
        boolean z4 = accuracy > 0.0f;
        boolean z5 = accuracy < 0.0f;
        boolean z6 = accuracy > 200.0f;
        boolean zEquals = location.getProvider() != null ? location.getProvider().equals(location2.getProvider()) : false;
        if (z5) {
            return true;
        }
        if (!z3 || z4) {
            return z3 && !z6 && zEquals;
        }
        return true;
    }

    private static String determineProvider(LocationManager locationManager, LocationAccuracy locationAccuracy) {
        List<String> providers = locationManager.getProviders(true);
        if (locationAccuracy == LocationAccuracy.lowest) {
            return "passive";
        }
        if (providers.contains("fused") && Build.VERSION.SDK_INT >= 31) {
            return "fused";
        }
        if (providers.contains("gps")) {
            return "gps";
        }
        if (providers.contains("network")) {
            return "network";
        }
        if (providers.isEmpty()) {
            return null;
        }
        return providers.get(0);
    }

    /* JADX INFO: renamed from: com.baseflow.geolocator.location.LocationManagerClient$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$baseflow$geolocator$location$LocationAccuracy;

        static {
            int[] iArr = new int[LocationAccuracy.values().length];
            $SwitchMap$com$baseflow$geolocator$location$LocationAccuracy = iArr;
            try {
                iArr[LocationAccuracy.lowest.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$baseflow$geolocator$location$LocationAccuracy[LocationAccuracy.low.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$baseflow$geolocator$location$LocationAccuracy[LocationAccuracy.high.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$baseflow$geolocator$location$LocationAccuracy[LocationAccuracy.best.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$baseflow$geolocator$location$LocationAccuracy[LocationAccuracy.bestForNavigation.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$baseflow$geolocator$location$LocationAccuracy[LocationAccuracy.medium.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private static int accuracyToQuality(LocationAccuracy locationAccuracy) {
        int i = AnonymousClass1.$SwitchMap$com$baseflow$geolocator$location$LocationAccuracy[locationAccuracy.ordinal()];
        if (i == 1 || i == 2) {
            return 104;
        }
        return (i == 3 || i == 4 || i == 5) ? 100 : 102;
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    public void isLocationServiceEnabled(LocationServiceListener locationServiceListener) {
        if (this.locationManager == null) {
            locationServiceListener.onLocationServiceResult(false);
        } else {
            locationServiceListener.onLocationServiceResult(checkLocationService(this.context));
        }
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    public void getLastKnownPosition(PositionChangedCallback positionChangedCallback, ErrorCallback errorCallback) {
        Iterator<String> it = this.locationManager.getProviders(true).iterator();
        Location location = null;
        while (it.hasNext()) {
            Location lastKnownLocation = this.locationManager.getLastKnownLocation(it.next());
            if (lastKnownLocation != null && isBetterLocation(lastKnownLocation, location)) {
                location = lastKnownLocation;
            }
        }
        positionChangedCallback.onPositionChanged(location);
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    public void startPositionUpdates(Activity activity, PositionChangedCallback positionChangedCallback, ErrorCallback errorCallback) {
        long timeInterval;
        float f;
        int iAccuracyToQuality;
        if (!checkLocationService(this.context)) {
            errorCallback.onError(ErrorCodes.locationServicesDisabled);
            return;
        }
        this.positionChangedCallback = positionChangedCallback;
        this.errorCallback = errorCallback;
        LocationAccuracy locationAccuracy = LocationAccuracy.best;
        LocationOptions locationOptions = this.locationOptions;
        if (locationOptions != null) {
            float distanceFilter = locationOptions.getDistanceFilter();
            LocationAccuracy accuracy = this.locationOptions.getAccuracy();
            timeInterval = accuracy == LocationAccuracy.lowest ? Long.MAX_VALUE : this.locationOptions.getTimeInterval();
            iAccuracyToQuality = accuracyToQuality(accuracy);
            f = distanceFilter;
            locationAccuracy = accuracy;
        } else {
            timeInterval = 0;
            f = 0.0f;
            iAccuracyToQuality = 102;
        }
        String strDetermineProvider = determineProvider(this.locationManager, locationAccuracy);
        this.currentLocationProvider = strDetermineProvider;
        if (strDetermineProvider == null) {
            errorCallback.onError(ErrorCodes.locationServicesDisabled);
            return;
        }
        LocationRequestCompat locationRequestCompatBuild = new LocationRequestCompat.Builder(timeInterval).setMinUpdateDistanceMeters(f).setQuality(iAccuracyToQuality).build();
        this.isListening = true;
        this.nmeaClient.start();
        LocationManagerCompat.requestLocationUpdates(this.locationManager, this.currentLocationProvider, locationRequestCompatBuild, this, Looper.getMainLooper());
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    public void stopPositionUpdates() {
        this.isListening = false;
        this.nmeaClient.stop();
        this.locationManager.removeUpdates(this);
    }

    @Override // android.location.LocationListener
    public synchronized void onLocationChanged(Location location) {
        if (isBetterLocation(location, this.currentBestLocation)) {
            this.currentBestLocation = location;
            if (this.positionChangedCallback != null) {
                this.nmeaClient.enrichExtrasWithNmea(location);
                this.positionChangedCallback.onPositionChanged(this.currentBestLocation);
            }
        }
    }

    @Override // androidx.core.location.LocationListenerCompat, android.location.LocationListener
    public void onStatusChanged(String str, int i, Bundle bundle) {
        if (i == 2) {
            onProviderEnabled(str);
        } else if (i == 0) {
            onProviderDisabled(str);
        }
    }

    @Override // androidx.core.location.LocationListenerCompat, android.location.LocationListener
    public void onProviderDisabled(String str) {
        if (str.equals(this.currentLocationProvider)) {
            if (this.isListening) {
                this.locationManager.removeUpdates(this);
            }
            ErrorCallback errorCallback = this.errorCallback;
            if (errorCallback != null) {
                errorCallback.onError(ErrorCodes.locationServicesDisabled);
            }
            this.currentLocationProvider = null;
        }
    }
}
