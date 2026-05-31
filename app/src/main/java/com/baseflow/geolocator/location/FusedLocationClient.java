package com.baseflow.geolocator.location;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import com.baseflow.geolocator.errors.ErrorCallback;
import com.baseflow.geolocator.errors.ErrorCodes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import java.security.SecureRandom;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
class FusedLocationClient implements LocationClient {
    private static final String TAG = "FlutterGeolocator";
    private final int activityRequestCode = generateActivityRequestCode();
    private final Context context;
    private ErrorCallback errorCallback;
    private final FusedLocationProviderClient fusedLocationProviderClient;
    private final LocationCallback locationCallback;
    private final LocationOptions locationOptions;
    private final NmeaClient nmeaClient;
    private PositionChangedCallback positionChangedCallback;

    public FusedLocationClient(final Context context, LocationOptions locationOptions) {
        this.context = context;
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        this.locationOptions = locationOptions;
        this.nmeaClient = new NmeaClient(context, locationOptions);
        this.locationCallback = new LocationCallback() { // from class: com.baseflow.geolocator.location.FusedLocationClient.1
            @Override // com.google.android.gms.location.LocationCallback
            public synchronized void onLocationResult(LocationResult locationResult) {
                if (FusedLocationClient.this.positionChangedCallback == null) {
                    Log.e(FusedLocationClient.TAG, "LocationCallback was called with empty locationResult or no positionChangedCallback was registered.");
                    FusedLocationClient.this.fusedLocationProviderClient.removeLocationUpdates(FusedLocationClient.this.locationCallback);
                    if (FusedLocationClient.this.errorCallback != null) {
                        FusedLocationClient.this.errorCallback.onError(ErrorCodes.errorWhileAcquiringPosition);
                    }
                    return;
                }
                Location lastLocation = locationResult.getLastLocation();
                FusedLocationClient.this.nmeaClient.enrichExtrasWithNmea(lastLocation);
                FusedLocationClient.this.positionChangedCallback.onPositionChanged(lastLocation);
            }

            @Override // com.google.android.gms.location.LocationCallback
            public synchronized void onLocationAvailability(LocationAvailability locationAvailability) {
                if (!locationAvailability.isLocationAvailable() && !FusedLocationClient.this.checkLocationService(context) && FusedLocationClient.this.errorCallback != null) {
                    FusedLocationClient.this.errorCallback.onError(ErrorCodes.locationServicesDisabled);
                }
            }
        };
    }

    private static LocationRequest buildLocationRequest(LocationOptions locationOptions) {
        if (Build.VERSION.SDK_INT < 33) {
            return buildLocationRequestDeprecated(locationOptions);
        }
        LocationRequest.Builder builder = new LocationRequest.Builder(0L);
        if (locationOptions != null) {
            builder.setPriority(toPriority(locationOptions.getAccuracy()));
            builder.setIntervalMillis(locationOptions.getTimeInterval());
            builder.setMinUpdateIntervalMillis(locationOptions.getTimeInterval());
            builder.setMinUpdateDistanceMeters(locationOptions.getDistanceFilter());
        }
        return builder.build();
    }

    private static LocationRequest buildLocationRequestDeprecated(LocationOptions locationOptions) {
        LocationRequest locationRequestCreate = LocationRequest.create();
        if (locationOptions != null) {
            locationRequestCreate.setPriority(toPriority(locationOptions.getAccuracy()));
            locationRequestCreate.setInterval(locationOptions.getTimeInterval());
            locationRequestCreate.setFastestInterval(locationOptions.getTimeInterval() / 2);
            locationRequestCreate.setSmallestDisplacement(locationOptions.getDistanceFilter());
        }
        return locationRequestCreate;
    }

    private static LocationSettingsRequest buildLocationSettingsRequest(LocationRequest locationRequest) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        return builder.build();
    }

    /* JADX INFO: renamed from: com.baseflow.geolocator.location.FusedLocationClient$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
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
                $SwitchMap$com$baseflow$geolocator$location$LocationAccuracy[LocationAccuracy.medium.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static int toPriority(LocationAccuracy locationAccuracy) {
        int i = AnonymousClass2.$SwitchMap$com$baseflow$geolocator$location$LocationAccuracy[locationAccuracy.ordinal()];
        if (i == 1) {
            return 105;
        }
        if (i != 2) {
            return i != 3 ? 100 : 102;
        }
        return 104;
    }

    private synchronized int generateActivityRequestCode() {
        return new SecureRandom().nextInt(65536);
    }

    private void requestPositionUpdates(LocationOptions locationOptions) {
        LocationRequest locationRequestBuildLocationRequest = buildLocationRequest(locationOptions);
        this.nmeaClient.start();
        this.fusedLocationProviderClient.requestLocationUpdates(locationRequestBuildLocationRequest, this.locationCallback, Looper.getMainLooper());
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    public void isLocationServiceEnabled(final LocationServiceListener locationServiceListener) {
        LocationServices.getSettingsClient(this.context).checkLocationSettings(new LocationSettingsRequest.Builder().build()).addOnCompleteListener(new OnCompleteListener() { // from class: com.baseflow.geolocator.location.FusedLocationClient$$ExternalSyntheticLambda4
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                FusedLocationClient.lambda$isLocationServiceEnabled$0(locationServiceListener, task);
            }
        });
    }

    static /* synthetic */ void lambda$isLocationServiceEnabled$0(LocationServiceListener locationServiceListener, Task task) {
        if (!task.isSuccessful()) {
            locationServiceListener.onLocationServiceError(ErrorCodes.locationServicesDisabled);
        }
        LocationSettingsResponse locationSettingsResponse = (LocationSettingsResponse) task.getResult();
        if (locationSettingsResponse != null) {
            LocationSettingsStates locationSettingsStates = locationSettingsResponse.getLocationSettingsStates();
            boolean z = true;
            boolean z2 = locationSettingsStates != null && locationSettingsStates.isGpsUsable();
            boolean z3 = locationSettingsStates != null && locationSettingsStates.isNetworkLocationUsable();
            if (!z2 && !z3) {
                z = false;
            }
            locationServiceListener.onLocationServiceResult(z);
            return;
        }
        locationServiceListener.onLocationServiceError(ErrorCodes.locationServicesDisabled);
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    public void getLastKnownPosition(final PositionChangedCallback positionChangedCallback, final ErrorCallback errorCallback) {
        Task<Location> lastLocation = this.fusedLocationProviderClient.getLastLocation();
        Objects.requireNonNull(positionChangedCallback);
        lastLocation.addOnSuccessListener(new OnSuccessListener() { // from class: com.baseflow.geolocator.location.FusedLocationClient$$ExternalSyntheticLambda0
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                positionChangedCallback.onPositionChanged((Location) obj);
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: com.baseflow.geolocator.location.FusedLocationClient$$ExternalSyntheticLambda1
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                FusedLocationClient.lambda$getLastKnownPosition$1(errorCallback, exc);
            }
        });
    }

    static /* synthetic */ void lambda$getLastKnownPosition$1(ErrorCallback errorCallback, Exception exc) {
        Log.e("Geolocator", "Error trying to get last the last known GPS location");
        if (errorCallback != null) {
            errorCallback.onError(ErrorCodes.errorWhileAcquiringPosition);
        }
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    public boolean onActivityResult(int i, int i2) {
        if (i == this.activityRequestCode) {
            if (i2 == -1) {
                LocationOptions locationOptions = this.locationOptions;
                if (locationOptions == null || this.positionChangedCallback == null || this.errorCallback == null) {
                    return false;
                }
                requestPositionUpdates(locationOptions);
                return true;
            }
            ErrorCallback errorCallback = this.errorCallback;
            if (errorCallback != null) {
                errorCallback.onError(ErrorCodes.locationServicesDisabled);
            }
        }
        return false;
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    public void startPositionUpdates(final Activity activity, PositionChangedCallback positionChangedCallback, final ErrorCallback errorCallback) {
        this.positionChangedCallback = positionChangedCallback;
        this.errorCallback = errorCallback;
        LocationServices.getSettingsClient(this.context).checkLocationSettings(buildLocationSettingsRequest(buildLocationRequest(this.locationOptions))).addOnSuccessListener(new OnSuccessListener() { // from class: com.baseflow.geolocator.location.FusedLocationClient$$ExternalSyntheticLambda2
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                this.f$0.m479x2c9cec4b((LocationSettingsResponse) obj);
            }
        }).addOnFailureListener(new OnFailureListener() { // from class: com.baseflow.geolocator.location.FusedLocationClient$$ExternalSyntheticLambda3
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                this.f$0.m480x46b86aea(activity, errorCallback, exc);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$startPositionUpdates$2$com-baseflow-geolocator-location-FusedLocationClient, reason: not valid java name */
    /* synthetic */ void m479x2c9cec4b(LocationSettingsResponse locationSettingsResponse) {
        requestPositionUpdates(this.locationOptions);
    }

    /* JADX INFO: renamed from: lambda$startPositionUpdates$3$com-baseflow-geolocator-location-FusedLocationClient, reason: not valid java name */
    /* synthetic */ void m480x46b86aea(Activity activity, ErrorCallback errorCallback, Exception exc) {
        if (!(exc instanceof ResolvableApiException)) {
            if (((ApiException) exc).getStatusCode() == 8502) {
                requestPositionUpdates(this.locationOptions);
                return;
            } else {
                errorCallback.onError(ErrorCodes.locationServicesDisabled);
                return;
            }
        }
        if (activity == null) {
            errorCallback.onError(ErrorCodes.locationServicesDisabled);
            return;
        }
        ResolvableApiException resolvableApiException = (ResolvableApiException) exc;
        if (resolvableApiException.getStatusCode() == 6) {
            try {
                resolvableApiException.startResolutionForResult(activity, this.activityRequestCode);
                return;
            } catch (IntentSender.SendIntentException unused) {
                errorCallback.onError(ErrorCodes.locationServicesDisabled);
                return;
            }
        }
        errorCallback.onError(ErrorCodes.locationServicesDisabled);
    }

    @Override // com.baseflow.geolocator.location.LocationClient
    public void stopPositionUpdates() {
        this.nmeaClient.stop();
        this.fusedLocationProviderClient.removeLocationUpdates(this.locationCallback);
    }
}
