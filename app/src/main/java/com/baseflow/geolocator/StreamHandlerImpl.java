package com.baseflow.geolocator;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import com.baseflow.geolocator.errors.ErrorCallback;
import com.baseflow.geolocator.errors.ErrorCodes;
import com.baseflow.geolocator.errors.PermissionUndefinedException;
import com.baseflow.geolocator.location.ForegroundNotificationOptions;
import com.baseflow.geolocator.location.GeolocationManager;
import com.baseflow.geolocator.location.LocationClient;
import com.baseflow.geolocator.location.LocationMapper;
import com.baseflow.geolocator.location.LocationOptions;
import com.baseflow.geolocator.location.PositionChangedCallback;
import com.baseflow.geolocator.permission.PermissionManager;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class StreamHandlerImpl implements EventChannel.StreamHandler {
    private static final String TAG = "FlutterGeolocator";
    private Activity activity;
    private EventChannel channel;
    private Context context;
    private GeolocatorLocationService foregroundLocationService;
    private GeolocationManager geolocationManager = new GeolocationManager();
    private LocationClient locationClient;
    private final PermissionManager permissionManager;

    public StreamHandlerImpl(PermissionManager permissionManager) {
        this.permissionManager = permissionManager;
    }

    public void setForegroundLocationService(GeolocatorLocationService geolocatorLocationService) {
        this.foregroundLocationService = geolocatorLocationService;
    }

    public void setActivity(Activity activity) {
        if (activity == null && this.locationClient != null && this.channel != null) {
            stopListening();
        }
        this.activity = activity;
    }

    void startListening(Context context, BinaryMessenger binaryMessenger) {
        if (this.channel != null) {
            Log.w(TAG, "Setting a event call handler before the last was disposed.");
            stopListening();
        }
        EventChannel eventChannel = new EventChannel(binaryMessenger, "flutter.baseflow.com/geolocator_updates_android");
        this.channel = eventChannel;
        eventChannel.setStreamHandler(this);
        this.context = context;
    }

    void stopListening() {
        if (this.channel == null) {
            Log.d(TAG, "Tried to stop listening when no MethodChannel had been initialized.");
            return;
        }
        disposeListeners(false);
        this.channel.setStreamHandler(null);
        this.channel = null;
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onListen(Object obj, final EventChannel.EventSink eventSink) {
        try {
            if (!this.permissionManager.hasPermission(this.context)) {
                eventSink.error(ErrorCodes.permissionDenied.toString(), ErrorCodes.permissionDenied.toDescription(), null);
                return;
            }
            if (this.foregroundLocationService == null) {
                Log.e(TAG, "Location background service has not started correctly");
                return;
            }
            Map map = (Map) obj;
            boolean zBooleanValue = (map == null || map.get("forceLocationManager") == null) ? false : ((Boolean) map.get("forceLocationManager")).booleanValue();
            LocationOptions arguments = LocationOptions.parseArguments(map);
            ForegroundNotificationOptions arguments2 = map != null ? ForegroundNotificationOptions.parseArguments((Map) map.get("foregroundNotificationConfig")) : null;
            if (arguments2 != null) {
                Log.e(TAG, "Geolocator position updates started using Android foreground service");
                this.foregroundLocationService.startLocationService(zBooleanValue, arguments, eventSink);
                this.foregroundLocationService.enableBackgroundMode(arguments2);
            } else {
                Log.e(TAG, "Geolocator position updates started");
                LocationClient locationClientCreateLocationClient = this.geolocationManager.createLocationClient(this.context, Boolean.TRUE.equals(Boolean.valueOf(zBooleanValue)), arguments);
                this.locationClient = locationClientCreateLocationClient;
                this.geolocationManager.startPositionUpdates(locationClientCreateLocationClient, this.activity, new PositionChangedCallback() { // from class: com.baseflow.geolocator.StreamHandlerImpl$$ExternalSyntheticLambda0
                    @Override // com.baseflow.geolocator.location.PositionChangedCallback
                    public final void onPositionChanged(Location location) {
                        eventSink.success(LocationMapper.toHashMap(location));
                    }
                }, new ErrorCallback() { // from class: com.baseflow.geolocator.StreamHandlerImpl$$ExternalSyntheticLambda1
                    @Override // com.baseflow.geolocator.errors.ErrorCallback
                    public final void onError(ErrorCodes errorCodes) {
                        eventSink.error(errorCodes.toString(), errorCodes.toDescription(), null);
                    }
                });
            }
        } catch (PermissionUndefinedException unused) {
            eventSink.error(ErrorCodes.permissionDefinitionsNotFound.toString(), ErrorCodes.permissionDefinitionsNotFound.toDescription(), null);
        }
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onCancel(Object obj) {
        disposeListeners(true);
    }

    private void disposeListeners(boolean z) {
        GeolocationManager geolocationManager;
        Log.e(TAG, "Geolocator position updates stopped");
        GeolocatorLocationService geolocatorLocationService = this.foregroundLocationService;
        if (geolocatorLocationService != null && geolocatorLocationService.canStopLocationService(z)) {
            this.foregroundLocationService.stopLocationService();
            this.foregroundLocationService.disableBackgroundMode();
        } else {
            Log.e(TAG, "There is still another flutter engine connected, not stopping location service");
        }
        LocationClient locationClient = this.locationClient;
        if (locationClient == null || (geolocationManager = this.geolocationManager) == null) {
            return;
        }
        geolocationManager.stopPositionUpdates(locationClient);
        this.locationClient = null;
    }
}
