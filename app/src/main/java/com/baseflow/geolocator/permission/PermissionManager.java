package com.baseflow.geolocator.permission;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.baseflow.geolocator.errors.ErrorCallback;
import com.baseflow.geolocator.errors.ErrorCodes;
import com.baseflow.geolocator.errors.PermissionUndefinedException;
import io.flutter.plugin.common.PluginRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PermissionManager implements PluginRegistry.RequestPermissionsResultListener {
    private static final int PERMISSION_REQUEST_CODE = 109;
    private Activity activity;
    private ErrorCallback errorCallback;
    private PermissionResultCallback resultCallback;

    public LocationPermission checkPermissionStatus(Context context) throws PermissionUndefinedException {
        byte b;
        Iterator<String> it = getLocationPermissionsFromManifest(context).iterator();
        while (true) {
            if (!it.hasNext()) {
                b = -1;
                break;
            }
            if (ContextCompat.checkSelfPermission(context, it.next()) == 0) {
                b = 0;
                break;
            }
        }
        if (b == -1) {
            return LocationPermission.denied;
        }
        if (Build.VERSION.SDK_INT < 29) {
            return LocationPermission.always;
        }
        if (!PermissionUtils.hasPermissionInManifest(context, "android.permission.ACCESS_BACKGROUND_LOCATION")) {
            return LocationPermission.whileInUse;
        }
        if (ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_BACKGROUND_LOCATION") == 0) {
            return LocationPermission.always;
        }
        return LocationPermission.whileInUse;
    }

    public void requestPermission(Activity activity, PermissionResultCallback permissionResultCallback, ErrorCallback errorCallback) throws PermissionUndefinedException {
        if (activity == null) {
            errorCallback.onError(ErrorCodes.activityMissing);
            return;
        }
        List<String> locationPermissionsFromManifest = getLocationPermissionsFromManifest(activity);
        if (Build.VERSION.SDK_INT >= 29 && PermissionUtils.hasPermissionInManifest(activity, "android.permission.ACCESS_BACKGROUND_LOCATION") && checkPermissionStatus(activity) == LocationPermission.whileInUse) {
            locationPermissionsFromManifest.add("android.permission.ACCESS_BACKGROUND_LOCATION");
        }
        this.errorCallback = errorCallback;
        this.resultCallback = permissionResultCallback;
        this.activity = activity;
        ActivityCompat.requestPermissions(activity, (String[]) locationPermissionsFromManifest.toArray(new String[0]), 109);
    }

    @Override // io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener
    public boolean onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        LocationPermission locationPermission;
        if (i != 109) {
            return false;
        }
        Activity activity = this.activity;
        if (activity == null) {
            Log.e("Geolocator", "Trying to process permission result without an valid Activity instance");
            ErrorCallback errorCallback = this.errorCallback;
            if (errorCallback != null) {
                errorCallback.onError(ErrorCodes.activityMissing);
            }
            return false;
        }
        try {
            List<String> locationPermissionsFromManifest = getLocationPermissionsFromManifest(activity);
            if (iArr.length == 0) {
                Log.i("Geolocator", "The grantResults array is empty. This can happen when the user cancels the permission request");
                return false;
            }
            LocationPermission locationPermission2 = LocationPermission.denied;
            byte b = -1;
            boolean z = false;
            boolean z2 = false;
            for (String str : locationPermissionsFromManifest) {
                int iIndexOf = indexOf(strArr, str);
                if (iIndexOf >= 0) {
                    z = true;
                }
                if (iArr[iIndexOf] == 0) {
                    b = 0;
                }
                if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity, str)) {
                    z2 = true;
                }
            }
            if (!z) {
                Log.w("Geolocator", "Location permissions not part of permissions send to onRequestPermissionsResult method.");
                return false;
            }
            if (b == 0) {
                if (Build.VERSION.SDK_INT < 29 || hasBackgroundAccess(strArr, iArr)) {
                    locationPermission = LocationPermission.always;
                } else {
                    locationPermission = LocationPermission.whileInUse;
                }
                locationPermission2 = locationPermission;
            } else if (!z2) {
                locationPermission2 = LocationPermission.deniedForever;
            }
            PermissionResultCallback permissionResultCallback = this.resultCallback;
            if (permissionResultCallback != null) {
                permissionResultCallback.onResult(locationPermission2);
            }
            return true;
        } catch (PermissionUndefinedException unused) {
            ErrorCallback errorCallback2 = this.errorCallback;
            if (errorCallback2 != null) {
                errorCallback2.onError(ErrorCodes.permissionDefinitionsNotFound);
            }
            return false;
        }
    }

    private boolean hasBackgroundAccess(String[] strArr, int[] iArr) {
        int iIndexOf = indexOf(strArr, "android.permission.ACCESS_BACKGROUND_LOCATION");
        return iIndexOf >= 0 && iArr[iIndexOf] == 0;
    }

    private static <T> int indexOf(T[] tArr, T t) {
        return Arrays.asList(tArr).indexOf(t);
    }

    private static List<String> getLocationPermissionsFromManifest(Context context) throws PermissionUndefinedException {
        boolean zHasPermissionInManifest = PermissionUtils.hasPermissionInManifest(context, "android.permission.ACCESS_FINE_LOCATION");
        boolean zHasPermissionInManifest2 = PermissionUtils.hasPermissionInManifest(context, "android.permission.ACCESS_COARSE_LOCATION");
        if (!zHasPermissionInManifest && !zHasPermissionInManifest2) {
            throw new PermissionUndefinedException();
        }
        ArrayList arrayList = new ArrayList();
        if (zHasPermissionInManifest) {
            arrayList.add("android.permission.ACCESS_FINE_LOCATION");
        }
        if (zHasPermissionInManifest2) {
            arrayList.add("android.permission.ACCESS_COARSE_LOCATION");
        }
        return arrayList;
    }

    public boolean hasPermission(Context context) throws PermissionUndefinedException {
        LocationPermission locationPermissionCheckPermissionStatus = checkPermissionStatus(context);
        return locationPermissionCheckPermissionStatus == LocationPermission.whileInUse || locationPermissionCheckPermissionStatus == LocationPermission.always;
    }
}
