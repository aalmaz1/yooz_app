package com.baseflow.geolocator;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import com.baseflow.geolocator.errors.ErrorCallback;
import com.baseflow.geolocator.errors.ErrorCodes;
import com.baseflow.geolocator.location.BackgroundNotification;
import com.baseflow.geolocator.location.ForegroundNotificationOptions;
import com.baseflow.geolocator.location.GeolocationManager;
import com.baseflow.geolocator.location.LocationClient;
import com.baseflow.geolocator.location.LocationMapper;
import com.baseflow.geolocator.location.LocationOptions;
import com.baseflow.geolocator.location.PositionChangedCallback;
import io.flutter.plugin.common.EventChannel;

/* JADX INFO: loaded from: classes.dex */
public class GeolocatorLocationService extends Service {
    private static final String CHANNEL_ID = "geolocator_channel_01";
    private static final int ONGOING_NOTIFICATION_ID = 75415;
    private static final String TAG = "FlutterGeolocator";
    private LocationClient locationClient;
    private final String WAKELOCK_TAG = "GeolocatorLocationService:Wakelock";
    private final String WIFILOCK_TAG = "GeolocatorLocationService:WifiLock";
    private final LocalBinder binder = new LocalBinder(this);
    private boolean isForeground = false;
    private int connectedEngines = 0;
    private int listenerCount = 0;
    private Activity activity = null;
    private GeolocationManager geolocationManager = null;
    private PowerManager.WakeLock wakeLock = null;
    private WifiManager.WifiLock wifiLock = null;
    private BackgroundNotification backgroundNotification = null;

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        return 1;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Creating service.");
        this.geolocationManager = new GeolocationManager();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "Binding to location service.");
        return this.binder;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "Unbinding from location service.");
        return super.onUnbind(intent);
    }

    @Override // android.app.Service
    public void onDestroy() {
        Log.d(TAG, "Destroying location service.");
        stopLocationService();
        disableBackgroundMode();
        this.geolocationManager = null;
        this.backgroundNotification = null;
        Log.d(TAG, "Destroyed location service.");
        super.onDestroy();
    }

    public boolean canStopLocationService(boolean z) {
        return z ? this.listenerCount == 1 : this.connectedEngines == 0;
    }

    public void flutterEngineConnected() {
        this.connectedEngines++;
        Log.d(TAG, "Flutter engine connected. Connected engine count " + this.connectedEngines);
    }

    public void flutterEngineDisconnected() {
        this.connectedEngines--;
        Log.d(TAG, "Flutter engine disconnected. Connected engine count " + this.connectedEngines);
    }

    public void startLocationService(boolean z, LocationOptions locationOptions, final EventChannel.EventSink eventSink) {
        this.listenerCount++;
        GeolocationManager geolocationManager = this.geolocationManager;
        if (geolocationManager != null) {
            LocationClient locationClientCreateLocationClient = geolocationManager.createLocationClient(getApplicationContext(), Boolean.TRUE.equals(Boolean.valueOf(z)), locationOptions);
            this.locationClient = locationClientCreateLocationClient;
            this.geolocationManager.startPositionUpdates(locationClientCreateLocationClient, this.activity, new PositionChangedCallback() { // from class: com.baseflow.geolocator.GeolocatorLocationService$$ExternalSyntheticLambda0
                @Override // com.baseflow.geolocator.location.PositionChangedCallback
                public final void onPositionChanged(Location location) {
                    eventSink.success(LocationMapper.toHashMap(location));
                }
            }, new ErrorCallback() { // from class: com.baseflow.geolocator.GeolocatorLocationService$$ExternalSyntheticLambda1
                @Override // com.baseflow.geolocator.errors.ErrorCallback
                public final void onError(ErrorCodes errorCodes) {
                    eventSink.error(errorCodes.toString(), errorCodes.toDescription(), null);
                }
            });
        }
    }

    public void stopLocationService() {
        GeolocationManager geolocationManager;
        this.listenerCount--;
        Log.d(TAG, "Stopping location service.");
        LocationClient locationClient = this.locationClient;
        if (locationClient == null || (geolocationManager = this.geolocationManager) == null) {
            return;
        }
        geolocationManager.stopPositionUpdates(locationClient);
    }

    public void enableBackgroundMode(ForegroundNotificationOptions foregroundNotificationOptions) {
        if (this.backgroundNotification != null) {
            Log.d(TAG, "Service already in foreground mode.");
            changeNotificationOptions(foregroundNotificationOptions);
        } else {
            Log.d(TAG, "Start service in foreground mode.");
            BackgroundNotification backgroundNotification = new BackgroundNotification(getApplicationContext(), CHANNEL_ID, Integer.valueOf(ONGOING_NOTIFICATION_ID), foregroundNotificationOptions);
            this.backgroundNotification = backgroundNotification;
            backgroundNotification.updateChannel("Background Location");
            startForeground(ONGOING_NOTIFICATION_ID, this.backgroundNotification.build());
            this.isForeground = true;
        }
        obtainWakeLocks(foregroundNotificationOptions);
    }

    public void disableBackgroundMode() {
        if (this.isForeground) {
            Log.d(TAG, "Stop service in foreground.");
            stopForeground(1);
            releaseWakeLocks();
            this.isForeground = false;
            this.backgroundNotification = null;
        }
    }

    public void changeNotificationOptions(ForegroundNotificationOptions foregroundNotificationOptions) {
        BackgroundNotification backgroundNotification = this.backgroundNotification;
        if (backgroundNotification != null) {
            backgroundNotification.updateOptions(foregroundNotificationOptions, this.isForeground);
            obtainWakeLocks(foregroundNotificationOptions);
        }
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    private void releaseWakeLocks() {
        PowerManager.WakeLock wakeLock = this.wakeLock;
        if (wakeLock != null && wakeLock.isHeld()) {
            this.wakeLock.release();
            this.wakeLock = null;
        }
        WifiManager.WifiLock wifiLock = this.wifiLock;
        if (wifiLock == null || !wifiLock.isHeld()) {
            return;
        }
        this.wifiLock.release();
        this.wifiLock = null;
    }

    private void obtainWakeLocks(ForegroundNotificationOptions foregroundNotificationOptions) {
        WifiManager wifiManager;
        PowerManager powerManager;
        releaseWakeLocks();
        if (foregroundNotificationOptions.isEnableWakeLock() && (powerManager = (PowerManager) getApplicationContext().getSystemService("power")) != null) {
            PowerManager.WakeLock wakeLockNewWakeLock = powerManager.newWakeLock(1, "GeolocatorLocationService:Wakelock");
            this.wakeLock = wakeLockNewWakeLock;
            wakeLockNewWakeLock.setReferenceCounted(false);
            this.wakeLock.acquire();
        }
        if (!foregroundNotificationOptions.isEnableWifiLock() || (wifiManager = (WifiManager) getApplicationContext().getSystemService("wifi")) == null) {
            return;
        }
        WifiManager.WifiLock wifiLockCreateWifiLock = wifiManager.createWifiLock(3, "GeolocatorLocationService:WifiLock");
        this.wifiLock = wifiLockCreateWifiLock;
        wifiLockCreateWifiLock.setReferenceCounted(false);
        this.wifiLock.acquire();
    }

    class LocalBinder extends Binder {
        private final GeolocatorLocationService locationService;

        LocalBinder(GeolocatorLocationService geolocatorLocationService) {
            this.locationService = geolocatorLocationService;
        }

        public GeolocatorLocationService getLocationService() {
            return this.locationService;
        }
    }
}
