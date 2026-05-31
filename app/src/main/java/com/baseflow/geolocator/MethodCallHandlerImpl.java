package com.baseflow.geolocator;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import com.baseflow.geolocator.errors.ErrorCallback;
import com.baseflow.geolocator.errors.ErrorCodes;
import com.baseflow.geolocator.errors.PermissionUndefinedException;
import com.baseflow.geolocator.location.FlutterLocationServiceListener;
import com.baseflow.geolocator.location.GeolocationManager;
import com.baseflow.geolocator.location.LocationAccuracyManager;
import com.baseflow.geolocator.location.LocationAccuracyStatus;
import com.baseflow.geolocator.location.LocationClient;
import com.baseflow.geolocator.location.LocationMapper;
import com.baseflow.geolocator.location.LocationOptions;
import com.baseflow.geolocator.location.PositionChangedCallback;
import com.baseflow.geolocator.permission.LocationPermission;
import com.baseflow.geolocator.permission.PermissionManager;
import com.baseflow.geolocator.permission.PermissionResultCallback;
import com.baseflow.geolocator.utils.Utils;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class MethodCallHandlerImpl implements MethodChannel.MethodCallHandler {
    private static final String TAG = "MethodCallHandlerImpl";
    private Activity activity;
    private MethodChannel channel;
    private Context context;
    private final GeolocationManager geolocationManager;
    private final LocationAccuracyManager locationAccuracyManager;
    final Map<String, LocationClient> pendingCurrentPositionLocationClients = new HashMap();
    private final PermissionManager permissionManager;

    MethodCallHandlerImpl(PermissionManager permissionManager, GeolocationManager geolocationManager, LocationAccuracyManager locationAccuracyManager) {
        this.permissionManager = permissionManager;
        this.geolocationManager = geolocationManager;
        this.locationAccuracyManager = locationAccuracyManager;
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        String str = methodCall.method;
        str.hashCode();
        switch (str) {
            case "getCurrentPosition":
                onGetCurrentPosition(methodCall, result);
                break;
            case "getLastKnownPosition":
                onGetLastKnownPosition(methodCall, result);
                break;
            case "openLocationSettings":
                result.success(Boolean.valueOf(Utils.openLocationSettings(this.context)));
                break;
            case "openAppSettings":
                result.success(Boolean.valueOf(Utils.openAppSettings(this.context)));
                break;
            case "isLocationServiceEnabled":
                onIsLocationServiceEnabled(result);
                break;
            case "checkPermission":
                onCheckPermission(result);
                break;
            case "requestPermission":
                onRequestPermission(result);
                break;
            case "getLocationAccuracy":
                getLocationAccuracy(result, this.context);
                break;
            case "cancelGetCurrentPosition":
                onCancelGetCurrentPosition(methodCall, result);
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    void startListening(Context context, BinaryMessenger binaryMessenger) {
        if (this.channel != null) {
            Log.w(TAG, "Setting a method call handler before the last was disposed.");
            stopListening();
        }
        MethodChannel methodChannel = new MethodChannel(binaryMessenger, "flutter.baseflow.com/geolocator_android");
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        this.context = context;
    }

    void stopListening() {
        MethodChannel methodChannel = this.channel;
        if (methodChannel == null) {
            Log.d(TAG, "Tried to stop listening when no MethodChannel had been initialized.");
        } else {
            methodChannel.setMethodCallHandler(null);
            this.channel = null;
        }
    }

    void setActivity(Activity activity) {
        this.activity = activity;
    }

    private void onCheckPermission(MethodChannel.Result result) {
        try {
            result.success(Integer.valueOf(this.permissionManager.checkPermissionStatus(this.context).toInt()));
        } catch (PermissionUndefinedException unused) {
            ErrorCodes errorCodes = ErrorCodes.permissionDefinitionsNotFound;
            result.error(errorCodes.toString(), errorCodes.toDescription(), null);
        }
    }

    private void onIsLocationServiceEnabled(MethodChannel.Result result) {
        this.geolocationManager.isLocationServiceEnabled(this.context, new FlutterLocationServiceListener(result));
    }

    private void onRequestPermission(final MethodChannel.Result result) {
        try {
            this.permissionManager.requestPermission(this.activity, new PermissionResultCallback() { // from class: com.baseflow.geolocator.MethodCallHandlerImpl$$ExternalSyntheticLambda3
                @Override // com.baseflow.geolocator.permission.PermissionResultCallback
                public final void onResult(LocationPermission locationPermission) {
                    result.success(Integer.valueOf(locationPermission.toInt()));
                }
            }, new ErrorCallback() { // from class: com.baseflow.geolocator.MethodCallHandlerImpl$$ExternalSyntheticLambda4
                @Override // com.baseflow.geolocator.errors.ErrorCallback
                public final void onError(ErrorCodes errorCodes) {
                    result.error(errorCodes.toString(), errorCodes.toDescription(), null);
                }
            });
        } catch (PermissionUndefinedException unused) {
            ErrorCodes errorCodes = ErrorCodes.permissionDefinitionsNotFound;
            result.error(errorCodes.toString(), errorCodes.toDescription(), null);
        }
    }

    private void getLocationAccuracy(final MethodChannel.Result result, Context context) {
        LocationAccuracyStatus locationAccuracy = this.locationAccuracyManager.getLocationAccuracy(context, new ErrorCallback() { // from class: com.baseflow.geolocator.MethodCallHandlerImpl$$ExternalSyntheticLambda2
            @Override // com.baseflow.geolocator.errors.ErrorCallback
            public final void onError(ErrorCodes errorCodes) {
                result.error(errorCodes.toString(), errorCodes.toDescription(), null);
            }
        });
        if (locationAccuracy != null) {
            result.success(Integer.valueOf(locationAccuracy.ordinal()));
        }
    }

    private void onGetLastKnownPosition(MethodCall methodCall, final MethodChannel.Result result) {
        try {
            if (!this.permissionManager.hasPermission(this.context)) {
                result.error(ErrorCodes.permissionDenied.toString(), ErrorCodes.permissionDenied.toDescription(), null);
            } else {
                Boolean bool = (Boolean) methodCall.argument("forceLocationManager");
                this.geolocationManager.getLastKnownPosition(this.context, bool != null && bool.booleanValue(), new PositionChangedCallback() { // from class: com.baseflow.geolocator.MethodCallHandlerImpl$$ExternalSyntheticLambda0
                    @Override // com.baseflow.geolocator.location.PositionChangedCallback
                    public final void onPositionChanged(Location location) {
                        result.success(LocationMapper.toHashMap(location));
                    }
                }, new ErrorCallback() { // from class: com.baseflow.geolocator.MethodCallHandlerImpl$$ExternalSyntheticLambda1
                    @Override // com.baseflow.geolocator.errors.ErrorCallback
                    public final void onError(ErrorCodes errorCodes) {
                        result.error(errorCodes.toString(), errorCodes.toDescription(), null);
                    }
                });
            }
        } catch (PermissionUndefinedException unused) {
            result.error(ErrorCodes.permissionDefinitionsNotFound.toString(), ErrorCodes.permissionDefinitionsNotFound.toDescription(), null);
        }
    }

    private void onGetCurrentPosition(MethodCall methodCall, final MethodChannel.Result result) {
        try {
            if (!this.permissionManager.hasPermission(this.context)) {
                result.error(ErrorCodes.permissionDenied.toString(), ErrorCodes.permissionDenied.toDescription(), null);
                return;
            }
            Map map = (Map) methodCall.arguments;
            boolean zBooleanValue = map.get("forceLocationManager") != null ? ((Boolean) map.get("forceLocationManager")).booleanValue() : false;
            LocationOptions arguments = LocationOptions.parseArguments(map);
            final String str = (String) map.get("requestId");
            final boolean[] zArr = {false};
            final LocationClient locationClientCreateLocationClient = this.geolocationManager.createLocationClient(this.context, zBooleanValue, arguments);
            this.pendingCurrentPositionLocationClients.put(str, locationClientCreateLocationClient);
            this.geolocationManager.startPositionUpdates(locationClientCreateLocationClient, this.activity, new PositionChangedCallback() { // from class: com.baseflow.geolocator.MethodCallHandlerImpl$$ExternalSyntheticLambda5
                @Override // com.baseflow.geolocator.location.PositionChangedCallback
                public final void onPositionChanged(Location location) {
                    this.f$0.m477xece4955a(zArr, locationClientCreateLocationClient, str, result, location);
                }
            }, new ErrorCallback() { // from class: com.baseflow.geolocator.MethodCallHandlerImpl$$ExternalSyntheticLambda6
                @Override // com.baseflow.geolocator.errors.ErrorCallback
                public final void onError(ErrorCodes errorCodes) {
                    this.f$0.m478x625ebb9b(zArr, locationClientCreateLocationClient, str, result, errorCodes);
                }
            });
        } catch (PermissionUndefinedException unused) {
            result.error(ErrorCodes.permissionDefinitionsNotFound.toString(), ErrorCodes.permissionDefinitionsNotFound.toDescription(), null);
        }
    }

    /* JADX INFO: renamed from: lambda$onGetCurrentPosition$5$com-baseflow-geolocator-MethodCallHandlerImpl, reason: not valid java name */
    /* synthetic */ void m477xece4955a(boolean[] zArr, LocationClient locationClient, String str, MethodChannel.Result result, Location location) {
        if (zArr[0]) {
            return;
        }
        zArr[0] = true;
        this.geolocationManager.stopPositionUpdates(locationClient);
        this.pendingCurrentPositionLocationClients.remove(str);
        result.success(LocationMapper.toHashMap(location));
    }

    /* JADX INFO: renamed from: lambda$onGetCurrentPosition$6$com-baseflow-geolocator-MethodCallHandlerImpl, reason: not valid java name */
    /* synthetic */ void m478x625ebb9b(boolean[] zArr, LocationClient locationClient, String str, MethodChannel.Result result, ErrorCodes errorCodes) {
        if (zArr[0]) {
            return;
        }
        zArr[0] = true;
        this.geolocationManager.stopPositionUpdates(locationClient);
        this.pendingCurrentPositionLocationClients.remove(str);
        result.error(errorCodes.toString(), errorCodes.toDescription(), null);
    }

    private void onCancelGetCurrentPosition(MethodCall methodCall, MethodChannel.Result result) {
        String str = (String) ((Map) methodCall.arguments).get("requestId");
        LocationClient locationClient = this.pendingCurrentPositionLocationClients.get(str);
        if (locationClient != null) {
            locationClient.stopPositionUpdates();
        }
        this.pendingCurrentPositionLocationClients.remove(str);
        result.success(null);
    }
}
