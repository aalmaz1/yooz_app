package com.baseflow.permissionhandler;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import io.flutter.plugin.common.PluginRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class PermissionManager implements PluginRegistry.ActivityResultListener, PluginRegistry.RequestPermissionsResultListener {
    private Activity activity;
    private final Context context;
    private int pendingRequestCount;
    private Map<Integer, Integer> requestResults;
    private RequestPermissionsSuccessCallback successCallback;

    @FunctionalInterface
    interface CheckPermissionsSuccessCallback {
        void onSuccess(int i);
    }

    @FunctionalInterface
    interface RequestPermissionsSuccessCallback {
        void onSuccess(Map<Integer, Integer> map);
    }

    @FunctionalInterface
    interface ShouldShowRequestPermissionRationaleSuccessCallback {
        void onSuccess(boolean z);
    }

    public PermissionManager(Context context) {
        this.context = context;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
    public boolean onActivityResult(int i, int i2, Intent intent) {
        int i3;
        int iCanScheduleExactAlarms;
        Activity activity = this.activity;
        boolean z = false;
        z = false;
        if (activity == null) {
            return false;
        }
        if (this.requestResults == null) {
            this.pendingRequestCount = 0;
            return false;
        }
        if (i == 209) {
            String packageName = this.context.getPackageName();
            PowerManager powerManager = (PowerManager) this.context.getSystemService("power");
            if (powerManager != null && powerManager.isIgnoringBatteryOptimizations(packageName)) {
                z = true;
            }
            i3 = 16;
            iCanScheduleExactAlarms = z;
        } else if (i == 210) {
            if (Build.VERSION.SDK_INT < 30) {
                return false;
            }
            i3 = 22;
            iCanScheduleExactAlarms = Environment.isExternalStorageManager();
        } else if (i == 211) {
            i3 = 23;
            iCanScheduleExactAlarms = Settings.canDrawOverlays(this.activity);
        } else if (i == 212) {
            i3 = 24;
            iCanScheduleExactAlarms = this.activity.getPackageManager().canRequestPackageInstalls();
        } else if (i == 213) {
            i3 = 27;
            iCanScheduleExactAlarms = ((NotificationManager) this.activity.getSystemService(cn.yoozworld.watch.utils.notifi.NotificationManager.BUNDLE_NOTIFICATION)).isNotificationPolicyAccessGranted();
        } else {
            if (i != 214) {
                return false;
            }
            i3 = 34;
            iCanScheduleExactAlarms = Build.VERSION.SDK_INT >= 31 ? ((AlarmManager) activity.getSystemService("alarm")).canScheduleExactAlarms() : true;
        }
        this.requestResults.put(Integer.valueOf(i3), Integer.valueOf(iCanScheduleExactAlarms));
        int i4 = this.pendingRequestCount - 1;
        this.pendingRequestCount = i4;
        RequestPermissionsSuccessCallback requestPermissionsSuccessCallback = this.successCallback;
        if (requestPermissionsSuccessCallback != null && i4 == 0) {
            requestPermissionsSuccessCallback.onSuccess(this.requestResults);
        }
        return true;
    }

    @Override // io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener
    public boolean onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        int manifestName;
        if (i != 24) {
            this.pendingRequestCount = 0;
            return false;
        }
        if (this.requestResults == null) {
            return false;
        }
        if (strArr.length == 0 && iArr.length == 0) {
            Log.w("permissions_handler", "onRequestPermissionsResult is called without results. This is probably caused by interfering request codes. If you see this error, please file an issue in flutter-permission-handler, including a list of plugins used by this application: https://github.com/Baseflow/flutter-permission-handler/issues");
            return false;
        }
        List listAsList = Arrays.asList(strArr);
        int iIndexOf = listAsList.indexOf("android.permission.WRITE_CALENDAR");
        if (iIndexOf >= 0) {
            int permissionStatus = PermissionUtils.toPermissionStatus(this.activity, "android.permission.WRITE_CALENDAR", iArr[iIndexOf]);
            this.requestResults.put(36, Integer.valueOf(permissionStatus));
            int iIndexOf2 = listAsList.indexOf("android.permission.READ_CALENDAR");
            if (iIndexOf2 >= 0) {
                int iIntValue = PermissionUtils.strictestStatus(Integer.valueOf(permissionStatus), Integer.valueOf(PermissionUtils.toPermissionStatus(this.activity, "android.permission.READ_CALENDAR", iArr[iIndexOf2]))).intValue();
                this.requestResults.put(37, Integer.valueOf(iIntValue));
                this.requestResults.put(0, Integer.valueOf(iIntValue));
            }
        }
        for (int i2 = 0; i2 < strArr.length; i2++) {
            String str = strArr[i2];
            if (!str.equals("android.permission.WRITE_CALENDAR") && !str.equals("android.permission.READ_CALENDAR") && (manifestName = PermissionUtils.parseManifestName(str)) != 20) {
                int i3 = iArr[i2];
                if (manifestName == 8) {
                    this.requestResults.put(8, PermissionUtils.strictestStatus(this.requestResults.get(8), Integer.valueOf(PermissionUtils.toPermissionStatus(this.activity, str, i3))));
                } else if (manifestName == 7) {
                    if (!this.requestResults.containsKey(7)) {
                        this.requestResults.put(7, Integer.valueOf(PermissionUtils.toPermissionStatus(this.activity, str, i3)));
                    }
                    if (!this.requestResults.containsKey(14)) {
                        this.requestResults.put(14, Integer.valueOf(PermissionUtils.toPermissionStatus(this.activity, str, i3)));
                    }
                } else if (manifestName == 4) {
                    int permissionStatus2 = PermissionUtils.toPermissionStatus(this.activity, str, i3);
                    if (!this.requestResults.containsKey(4)) {
                        this.requestResults.put(4, Integer.valueOf(permissionStatus2));
                    }
                } else if (manifestName == 3) {
                    int permissionStatus3 = PermissionUtils.toPermissionStatus(this.activity, str, i3);
                    if (Build.VERSION.SDK_INT < 29 && !this.requestResults.containsKey(4)) {
                        this.requestResults.put(4, Integer.valueOf(permissionStatus3));
                    }
                    if (!this.requestResults.containsKey(5)) {
                        this.requestResults.put(5, Integer.valueOf(permissionStatus3));
                    }
                    this.requestResults.put(Integer.valueOf(manifestName), Integer.valueOf(permissionStatus3));
                } else if (manifestName == 9 || manifestName == 32) {
                    this.requestResults.put(Integer.valueOf(manifestName), Integer.valueOf(determinePermissionStatus(manifestName)));
                } else if (!this.requestResults.containsKey(Integer.valueOf(manifestName))) {
                    this.requestResults.put(Integer.valueOf(manifestName), Integer.valueOf(PermissionUtils.toPermissionStatus(this.activity, str, i3)));
                }
            }
        }
        int length = this.pendingRequestCount - iArr.length;
        this.pendingRequestCount = length;
        RequestPermissionsSuccessCallback requestPermissionsSuccessCallback = this.successCallback;
        if (requestPermissionsSuccessCallback == null || length != 0) {
            return true;
        }
        requestPermissionsSuccessCallback.onSuccess(this.requestResults);
        return true;
    }

    void checkPermissionStatus(int i, CheckPermissionsSuccessCallback checkPermissionsSuccessCallback) {
        checkPermissionsSuccessCallback.onSuccess(determinePermissionStatus(i));
    }

    void requestPermissions(List<Integer> list, RequestPermissionsSuccessCallback requestPermissionsSuccessCallback, ErrorCallback errorCallback) {
        if (this.pendingRequestCount > 0) {
            errorCallback.onError("PermissionHandler.PermissionManager", "A request for permissions is already running, please wait for it to finish before doing another request (note that you can request multiple permissions at the same time).");
            return;
        }
        if (this.activity == null) {
            Log.d("permissions_handler", "Unable to detect current Activity.");
            errorCallback.onError("PermissionHandler.PermissionManager", "Unable to detect current Android Activity.");
            return;
        }
        this.successCallback = requestPermissionsSuccessCallback;
        this.requestResults = new HashMap();
        this.pendingRequestCount = 0;
        ArrayList arrayList = new ArrayList();
        for (Integer num : list) {
            if (determinePermissionStatus(num.intValue()) == 1) {
                if (!this.requestResults.containsKey(num)) {
                    this.requestResults.put(num, 1);
                }
            } else {
                List<String> manifestNames = PermissionUtils.getManifestNames(this.activity, num.intValue());
                if (manifestNames == null || manifestNames.isEmpty()) {
                    if (!this.requestResults.containsKey(num)) {
                        num.intValue();
                        this.requestResults.put(num, 0);
                        if (num.intValue() == 22 && Build.VERSION.SDK_INT < 30) {
                            this.requestResults.put(num, 2);
                        } else {
                            this.requestResults.put(num, 0);
                        }
                    }
                } else if (num.intValue() == 16) {
                    launchSpecialPermission("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS", 209);
                } else if (Build.VERSION.SDK_INT >= 30 && num.intValue() == 22) {
                    launchSpecialPermission("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION", 210);
                } else if (num.intValue() == 23) {
                    launchSpecialPermission("android.settings.action.MANAGE_OVERLAY_PERMISSION", 211);
                } else if (num.intValue() == 24) {
                    launchSpecialPermission("android.settings.MANAGE_UNKNOWN_APP_SOURCES", 212);
                } else if (num.intValue() == 27) {
                    launchSpecialPermission("android.settings.NOTIFICATION_POLICY_ACCESS_SETTINGS", 213);
                } else if (Build.VERSION.SDK_INT >= 31 && num.intValue() == 34) {
                    launchSpecialPermission("android.settings.REQUEST_SCHEDULE_EXACT_ALARM", 214);
                } else if (num.intValue() == 37 || num.intValue() == 0) {
                    if (isValidManifestForCalendarFullAccess()) {
                        arrayList.add("android.permission.WRITE_CALENDAR");
                        arrayList.add("android.permission.READ_CALENDAR");
                        this.pendingRequestCount += 2;
                    } else {
                        this.requestResults.put(num, 0);
                    }
                } else {
                    arrayList.addAll(manifestNames);
                    this.pendingRequestCount += manifestNames.size();
                }
            }
        }
        if (arrayList.size() > 0) {
            ActivityCompat.requestPermissions(this.activity, (String[]) arrayList.toArray(new String[0]), 24);
        }
        RequestPermissionsSuccessCallback requestPermissionsSuccessCallback2 = this.successCallback;
        if (requestPermissionsSuccessCallback2 == null || this.pendingRequestCount != 0) {
            return;
        }
        requestPermissionsSuccessCallback2.onSuccess(this.requestResults);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private int determinePermissionStatus(int i) {
        if (i == 17) {
            return checkNotificationPermissionStatus();
        }
        if (i == 21) {
            return checkBluetoothPermissionStatus();
        }
        if ((i == 30 || i == 28 || i == 29) && Build.VERSION.SDK_INT < 31) {
            return checkBluetoothPermissionStatus();
        }
        if ((i == 37 || i == 0) && !isValidManifestForCalendarFullAccess()) {
            return 0;
        }
        List<String> manifestNames = PermissionUtils.getManifestNames(this.context, i);
        if (manifestNames == null) {
            Log.d("permissions_handler", "No android specific permissions needed for: " + i);
            return 1;
        }
        if (manifestNames.size() == 0) {
            Log.d("permissions_handler", "No permissions found in manifest for: " + manifestNames + i);
            return (i != 22 || Build.VERSION.SDK_INT >= 30) ? 0 : 2;
        }
        if ((this.context.getApplicationInfo().targetSdkVersion >= 23) != false) {
            HashSet hashSet = new HashSet();
            for (String str : manifestNames) {
                if (i == 16) {
                    String packageName = this.context.getPackageName();
                    PowerManager powerManager = (PowerManager) this.context.getSystemService("power");
                    if (powerManager != null && powerManager.isIgnoringBatteryOptimizations(packageName)) {
                        hashSet.add(1);
                    } else {
                        hashSet.add(0);
                    }
                } else if (i == 22) {
                    if (Build.VERSION.SDK_INT < 30) {
                        hashSet.add(2);
                    }
                    hashSet.add(Integer.valueOf(Environment.isExternalStorageManager() ? 1 : 0));
                } else if (i == 23) {
                    hashSet.add(Integer.valueOf(Settings.canDrawOverlays(this.context) ? 1 : 0));
                } else if (i == 24) {
                    hashSet.add(Integer.valueOf(this.context.getPackageManager().canRequestPackageInstalls() ? 1 : 0));
                } else if (i == 27) {
                    hashSet.add(Integer.valueOf(((NotificationManager) this.context.getSystemService(cn.yoozworld.watch.utils.notifi.NotificationManager.BUNDLE_NOTIFICATION)).isNotificationPolicyAccessGranted() ? 1 : 0));
                } else if (i == 34) {
                    if (Build.VERSION.SDK_INT >= 31) {
                        hashSet.add(Integer.valueOf(((AlarmManager) this.context.getSystemService("alarm")).canScheduleExactAlarms() ? 1 : 0));
                    } else {
                        hashSet.add(1);
                    }
                } else if (i == 9 || i == 32) {
                    int iCheckSelfPermission = ContextCompat.checkSelfPermission(this.context, str);
                    if ((Build.VERSION.SDK_INT >= 34 ? ContextCompat.checkSelfPermission(this.context, "android.permission.READ_MEDIA_VISUAL_USER_SELECTED") : iCheckSelfPermission) == 0 && iCheckSelfPermission == -1) {
                        hashSet.add(3);
                    } else if (iCheckSelfPermission == 0) {
                        hashSet.add(1);
                    } else {
                        hashSet.add(Integer.valueOf(PermissionUtils.determineDeniedVariant(this.activity, str)));
                    }
                } else if (ContextCompat.checkSelfPermission(this.context, str) != 0) {
                    hashSet.add(Integer.valueOf(PermissionUtils.determineDeniedVariant(this.activity, str)));
                }
            }
            if (!hashSet.isEmpty()) {
                return PermissionUtils.strictestStatus(hashSet).intValue();
            }
        }
        return 1;
    }

    private void launchSpecialPermission(String str, int i) {
        if (this.activity == null) {
            return;
        }
        Intent intent = new Intent(str);
        if (!str.equals("android.settings.NOTIFICATION_POLICY_ACCESS_SETTINGS")) {
            intent.setData(Uri.parse("package:" + this.activity.getPackageName()));
        }
        this.activity.startActivityForResult(intent, i);
        this.pendingRequestCount++;
    }

    void shouldShowRequestPermissionRationale(int i, ShouldShowRequestPermissionRationaleSuccessCallback shouldShowRequestPermissionRationaleSuccessCallback, ErrorCallback errorCallback) {
        Activity activity = this.activity;
        if (activity == null) {
            Log.d("permissions_handler", "Unable to detect current Activity.");
            errorCallback.onError("PermissionHandler.PermissionManager", "Unable to detect current Android Activity.");
            return;
        }
        List<String> manifestNames = PermissionUtils.getManifestNames(activity, i);
        if (manifestNames == null) {
            Log.d("permissions_handler", "No android specific permissions needed for: " + i);
            shouldShowRequestPermissionRationaleSuccessCallback.onSuccess(false);
        } else if (manifestNames.isEmpty()) {
            Log.d("permissions_handler", "No permissions found in manifest for: " + i + " no need to show request rationale");
            shouldShowRequestPermissionRationaleSuccessCallback.onSuccess(false);
        } else {
            shouldShowRequestPermissionRationaleSuccessCallback.onSuccess(ActivityCompat.shouldShowRequestPermissionRationale(this.activity, manifestNames.get(0)));
        }
    }

    private int checkNotificationPermissionStatus() {
        if (Build.VERSION.SDK_INT < 33) {
            return NotificationManagerCompat.from(this.context).areNotificationsEnabled() ? 1 : 0;
        }
        if (this.context.checkSelfPermission("android.permission.POST_NOTIFICATIONS") == 0) {
            return 1;
        }
        return PermissionUtils.determineDeniedVariant(this.activity, "android.permission.POST_NOTIFICATIONS");
    }

    private int checkBluetoothPermissionStatus() {
        List<String> manifestNames = PermissionUtils.getManifestNames(this.context, 21);
        if (!(manifestNames == null || manifestNames.isEmpty())) {
            return 1;
        }
        Log.d("permissions_handler", "Bluetooth permission missing in manifest");
        return 0;
    }

    private boolean isValidManifestForCalendarFullAccess() {
        List<String> manifestNames = PermissionUtils.getManifestNames(this.context, 37);
        boolean z = manifestNames != null && manifestNames.contains("android.permission.WRITE_CALENDAR");
        boolean z2 = manifestNames != null && manifestNames.contains("android.permission.READ_CALENDAR");
        if (z && z2) {
            return true;
        }
        if (!z) {
            Log.d("permissions_handler", "android.permission.WRITE_CALENDAR missing in manifest");
        }
        if (!z2) {
            Log.d("permissions_handler", "android.permission.READ_CALENDAR missing in manifest");
        }
        return false;
    }
}
