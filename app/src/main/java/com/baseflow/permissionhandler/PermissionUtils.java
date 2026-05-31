package com.baseflow.permissionhandler;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PermissionUtils {
    static final String SHARED_PREFERENCES_PERMISSION_WAS_DENIED_BEFORE_KEY = "sp_permission_handler_permission_was_denied_before";

    static int parseManifestName(String str) {
        str.hashCode();
        switch (str) {
            case "android.permission.READ_SMS":
            case "android.permission.RECEIVE_WAP_PUSH":
            case "android.permission.RECEIVE_MMS":
            case "android.permission.RECEIVE_SMS":
            case "android.permission.SEND_SMS":
                return 13;
            case "android.permission.READ_CALENDAR":
            case "android.permission.WRITE_CALENDAR":
                return 0;
            case "android.permission.POST_NOTIFICATIONS":
                return 17;
            case "android.permission.READ_CALL_LOG":
            case "android.permission.READ_PHONE_NUMBERS":
            case "android.permission.READ_PHONE_STATE":
            case "android.permission.CALL_PHONE":
            case "android.permission.WRITE_CALL_LOG":
            case "android.permission.USE_SIP":
            case "com.android.voicemail.permission.ADD_VOICEMAIL":
                return 8;
            case "android.permission.ACCESS_FINE_LOCATION":
            case "android.permission.ACCESS_COARSE_LOCATION":
                return 3;
            case "android.permission.MANAGE_EXTERNAL_STORAGE":
                return 22;
            case "android.permission.ACCESS_NOTIFICATION_POLICY":
                return 27;
            case "android.permission.SYSTEM_ALERT_WINDOW":
                return 23;
            case "android.permission.BODY_SENSORS":
                return 12;
            case "android.permission.NEARBY_WIFI_DEVICES":
                return 31;
            case "android.permission.BLUETOOTH_CONNECT":
                return 30;
            case "android.permission.READ_EXTERNAL_STORAGE":
            case "android.permission.WRITE_EXTERNAL_STORAGE":
                return 15;
            case "android.permission.READ_MEDIA_IMAGES":
                return 9;
            case "android.permission.WRITE_CONTACTS":
            case "android.permission.GET_ACCOUNTS":
            case "android.permission.READ_CONTACTS":
                return 2;
            case "android.permission.BODY_SENSORS_BACKGROUND":
                return 35;
            case "android.permission.CAMERA":
                return 1;
            case "android.permission.READ_MEDIA_AUDIO":
                return 33;
            case "android.permission.READ_MEDIA_VIDEO":
                return 32;
            case "android.permission.SCHEDULE_EXACT_ALARM":
                return 34;
            case "android.permission.BLUETOOTH_ADVERTISE":
                return 29;
            case "android.permission.REQUEST_INSTALL_PACKAGES":
                return 24;
            case "android.permission.ACTIVITY_RECOGNITION":
                return 19;
            case "android.permission.RECORD_AUDIO":
                return 7;
            case "android.permission.ACCESS_BACKGROUND_LOCATION":
                return 4;
            case "android.permission.BLUETOOTH_SCAN":
                return 28;
            case "android.permission.ACCESS_MEDIA_LOCATION":
                return 18;
            default:
                return 20;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    static List<String> getManifestNames(Context context, int i) {
        String strDetermineBluetoothPermission;
        String strDetermineBluetoothPermission2;
        String strDetermineBluetoothPermission3;
        ArrayList arrayList = new ArrayList();
        switch (i) {
            case 0:
            case 37:
                if (hasPermissionInManifest(context, arrayList, "android.permission.WRITE_CALENDAR")) {
                    arrayList.add("android.permission.WRITE_CALENDAR");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.READ_CALENDAR")) {
                    arrayList.add("android.permission.READ_CALENDAR");
                }
                return arrayList;
            case 1:
                if (hasPermissionInManifest(context, arrayList, "android.permission.CAMERA")) {
                    arrayList.add("android.permission.CAMERA");
                }
                return arrayList;
            case 2:
                if (hasPermissionInManifest(context, arrayList, "android.permission.READ_CONTACTS")) {
                    arrayList.add("android.permission.READ_CONTACTS");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.WRITE_CONTACTS")) {
                    arrayList.add("android.permission.WRITE_CONTACTS");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.GET_ACCOUNTS")) {
                    arrayList.add("android.permission.GET_ACCOUNTS");
                }
                return arrayList;
            case 3:
            case 4:
            case 5:
                if (i == 4 && Build.VERSION.SDK_INT >= 29) {
                    if (hasPermissionInManifest(context, arrayList, "android.permission.ACCESS_BACKGROUND_LOCATION")) {
                        arrayList.add("android.permission.ACCESS_BACKGROUND_LOCATION");
                    }
                } else {
                    if (hasPermissionInManifest(context, arrayList, "android.permission.ACCESS_COARSE_LOCATION")) {
                        arrayList.add("android.permission.ACCESS_COARSE_LOCATION");
                    }
                    if (hasPermissionInManifest(context, arrayList, "android.permission.ACCESS_FINE_LOCATION")) {
                        arrayList.add("android.permission.ACCESS_FINE_LOCATION");
                    }
                }
                return arrayList;
            case 6:
            case 11:
            case 20:
                return null;
            case 7:
            case 14:
                if (hasPermissionInManifest(context, arrayList, "android.permission.RECORD_AUDIO")) {
                    arrayList.add("android.permission.RECORD_AUDIO");
                }
                return arrayList;
            case 8:
                if (hasPermissionInManifest(context, arrayList, "android.permission.READ_PHONE_STATE")) {
                    arrayList.add("android.permission.READ_PHONE_STATE");
                }
                if (Build.VERSION.SDK_INT > 29 && hasPermissionInManifest(context, arrayList, "android.permission.READ_PHONE_NUMBERS")) {
                    arrayList.add("android.permission.READ_PHONE_NUMBERS");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.CALL_PHONE")) {
                    arrayList.add("android.permission.CALL_PHONE");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.READ_CALL_LOG")) {
                    arrayList.add("android.permission.READ_CALL_LOG");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.WRITE_CALL_LOG")) {
                    arrayList.add("android.permission.WRITE_CALL_LOG");
                }
                if (hasPermissionInManifest(context, arrayList, "com.android.voicemail.permission.ADD_VOICEMAIL")) {
                    arrayList.add("com.android.voicemail.permission.ADD_VOICEMAIL");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.USE_SIP")) {
                    arrayList.add("android.permission.USE_SIP");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.ANSWER_PHONE_CALLS")) {
                    arrayList.add("android.permission.ANSWER_PHONE_CALLS");
                }
                return arrayList;
            case 9:
                if (Build.VERSION.SDK_INT >= 33 && hasPermissionInManifest(context, arrayList, "android.permission.READ_MEDIA_IMAGES")) {
                    arrayList.add("android.permission.READ_MEDIA_IMAGES");
                }
                return arrayList;
            case 10:
            case 25:
            case 26:
            default:
                return arrayList;
            case 12:
                if (hasPermissionInManifest(context, arrayList, "android.permission.BODY_SENSORS")) {
                    arrayList.add("android.permission.BODY_SENSORS");
                }
                return arrayList;
            case 13:
                if (hasPermissionInManifest(context, arrayList, "android.permission.SEND_SMS")) {
                    arrayList.add("android.permission.SEND_SMS");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.RECEIVE_SMS")) {
                    arrayList.add("android.permission.RECEIVE_SMS");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.READ_SMS")) {
                    arrayList.add("android.permission.READ_SMS");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.RECEIVE_WAP_PUSH")) {
                    arrayList.add("android.permission.RECEIVE_WAP_PUSH");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.RECEIVE_MMS")) {
                    arrayList.add("android.permission.RECEIVE_MMS");
                }
                return arrayList;
            case 15:
                if (hasPermissionInManifest(context, arrayList, "android.permission.READ_EXTERNAL_STORAGE")) {
                    arrayList.add("android.permission.READ_EXTERNAL_STORAGE");
                }
                if ((Build.VERSION.SDK_INT < 29 || (Build.VERSION.SDK_INT == 29 && Environment.isExternalStorageLegacy())) && hasPermissionInManifest(context, arrayList, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                    arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
                }
                return arrayList;
            case 16:
                if (hasPermissionInManifest(context, arrayList, "android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS")) {
                    arrayList.add("android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
                }
                return arrayList;
            case 17:
                if (Build.VERSION.SDK_INT >= 33 && hasPermissionInManifest(context, arrayList, "android.permission.POST_NOTIFICATIONS")) {
                    arrayList.add("android.permission.POST_NOTIFICATIONS");
                }
                return arrayList;
            case 18:
                if (Build.VERSION.SDK_INT < 29) {
                    return null;
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.ACCESS_MEDIA_LOCATION")) {
                    arrayList.add("android.permission.ACCESS_MEDIA_LOCATION");
                }
                return arrayList;
            case 19:
                if (Build.VERSION.SDK_INT < 29) {
                    return null;
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.ACTIVITY_RECOGNITION")) {
                    arrayList.add("android.permission.ACTIVITY_RECOGNITION");
                }
                return arrayList;
            case 21:
                if (hasPermissionInManifest(context, arrayList, "android.permission.BLUETOOTH")) {
                    arrayList.add("android.permission.BLUETOOTH");
                }
                return arrayList;
            case 22:
                if (Build.VERSION.SDK_INT >= 30 && hasPermissionInManifest(context, arrayList, "android.permission.MANAGE_EXTERNAL_STORAGE")) {
                    arrayList.add("android.permission.MANAGE_EXTERNAL_STORAGE");
                }
                return arrayList;
            case 23:
                if (hasPermissionInManifest(context, arrayList, "android.permission.SYSTEM_ALERT_WINDOW")) {
                    arrayList.add("android.permission.SYSTEM_ALERT_WINDOW");
                }
                return arrayList;
            case 24:
                if (hasPermissionInManifest(context, arrayList, "android.permission.REQUEST_INSTALL_PACKAGES")) {
                    arrayList.add("android.permission.REQUEST_INSTALL_PACKAGES");
                }
                return arrayList;
            case 27:
                if (hasPermissionInManifest(context, arrayList, "android.permission.ACCESS_NOTIFICATION_POLICY")) {
                    arrayList.add("android.permission.ACCESS_NOTIFICATION_POLICY");
                }
                return arrayList;
            case 28:
                if (Build.VERSION.SDK_INT >= 31 && (strDetermineBluetoothPermission = determineBluetoothPermission(context, "android.permission.BLUETOOTH_SCAN")) != null) {
                    arrayList.add(strDetermineBluetoothPermission);
                }
                return arrayList;
            case 29:
                if (Build.VERSION.SDK_INT >= 31 && (strDetermineBluetoothPermission2 = determineBluetoothPermission(context, "android.permission.BLUETOOTH_ADVERTISE")) != null) {
                    arrayList.add(strDetermineBluetoothPermission2);
                }
                return arrayList;
            case 30:
                if (Build.VERSION.SDK_INT >= 31 && (strDetermineBluetoothPermission3 = determineBluetoothPermission(context, "android.permission.BLUETOOTH_CONNECT")) != null) {
                    arrayList.add(strDetermineBluetoothPermission3);
                }
                return arrayList;
            case 31:
                if (Build.VERSION.SDK_INT >= 33 && hasPermissionInManifest(context, arrayList, "android.permission.NEARBY_WIFI_DEVICES")) {
                    arrayList.add("android.permission.NEARBY_WIFI_DEVICES");
                }
                return arrayList;
            case 32:
                if (Build.VERSION.SDK_INT >= 33 && hasPermissionInManifest(context, arrayList, "android.permission.READ_MEDIA_VIDEO")) {
                    arrayList.add("android.permission.READ_MEDIA_VIDEO");
                }
                return arrayList;
            case 33:
                if (Build.VERSION.SDK_INT >= 33 && hasPermissionInManifest(context, arrayList, "android.permission.READ_MEDIA_AUDIO")) {
                    arrayList.add("android.permission.READ_MEDIA_AUDIO");
                }
                return arrayList;
            case 34:
                if (hasPermissionInManifest(context, arrayList, "android.permission.SCHEDULE_EXACT_ALARM")) {
                    arrayList.add("android.permission.SCHEDULE_EXACT_ALARM");
                }
                return arrayList;
            case 35:
                if (Build.VERSION.SDK_INT >= 33 && hasPermissionInManifest(context, arrayList, "android.permission.BODY_SENSORS_BACKGROUND")) {
                    arrayList.add("android.permission.BODY_SENSORS_BACKGROUND");
                }
                return arrayList;
            case 36:
                if (hasPermissionInManifest(context, arrayList, "android.permission.WRITE_CALENDAR")) {
                    arrayList.add("android.permission.WRITE_CALENDAR");
                }
                return arrayList;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0021 A[Catch: Exception -> 0x001d, TryCatch #0 {Exception -> 0x001d, blocks: (B:4:0x0006, B:5:0x000a, B:7:0x0010, B:13:0x0021, B:15:0x0027, B:17:0x002d, B:19:0x0033, B:20:0x0042, B:22:0x0048), top: B:27:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0027 A[Catch: Exception -> 0x001d, TryCatch #0 {Exception -> 0x001d, blocks: (B:4:0x0006, B:5:0x000a, B:7:0x0010, B:13:0x0021, B:15:0x0027, B:17:0x002d, B:19:0x0033, B:20:0x0042, B:22:0x0048), top: B:27:0x0006 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean hasPermissionInManifest(android.content.Context r4, java.util.ArrayList<java.lang.String> r5, java.lang.String r6) {
        /*
            r0 = 1
            r1 = 0
            java.lang.String r2 = "permissions_handler"
            if (r5 == 0) goto L1f
            java.util.Iterator r5 = r5.iterator()     // Catch: java.lang.Exception -> L1d
        La:
            boolean r3 = r5.hasNext()     // Catch: java.lang.Exception -> L1d
            if (r3 == 0) goto L1f
            java.lang.Object r3 = r5.next()     // Catch: java.lang.Exception -> L1d
            java.lang.String r3 = (java.lang.String) r3     // Catch: java.lang.Exception -> L1d
            boolean r3 = r3.equals(r6)     // Catch: java.lang.Exception -> L1d
            if (r3 == 0) goto La
            return r0
        L1d:
            r4 = move-exception
            goto L55
        L1f:
            if (r4 != 0) goto L27
            java.lang.String r4 = "Unable to detect current Activity or App Context."
            android.util.Log.d(r2, r4)     // Catch: java.lang.Exception -> L1d
            return r1
        L27:
            android.content.pm.PackageInfo r4 = getPackageInfo(r4)     // Catch: java.lang.Exception -> L1d
            if (r4 != 0) goto L33
            java.lang.String r4 = "Unable to get Package info, will not be able to determine permissions to request."
            android.util.Log.d(r2, r4)     // Catch: java.lang.Exception -> L1d
            return r1
        L33:
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch: java.lang.Exception -> L1d
            java.lang.String[] r4 = r4.requestedPermissions     // Catch: java.lang.Exception -> L1d
            java.util.List r4 = java.util.Arrays.asList(r4)     // Catch: java.lang.Exception -> L1d
            r5.<init>(r4)     // Catch: java.lang.Exception -> L1d
            java.util.Iterator r4 = r5.iterator()     // Catch: java.lang.Exception -> L1d
        L42:
            boolean r5 = r4.hasNext()     // Catch: java.lang.Exception -> L1d
            if (r5 == 0) goto L5a
            java.lang.Object r5 = r4.next()     // Catch: java.lang.Exception -> L1d
            java.lang.String r5 = (java.lang.String) r5     // Catch: java.lang.Exception -> L1d
            boolean r5 = r5.equals(r6)     // Catch: java.lang.Exception -> L1d
            if (r5 == 0) goto L42
            return r0
        L55:
            java.lang.String r5 = "Unable to check manifest for permission: "
            android.util.Log.d(r2, r5, r4)
        L5a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baseflow.permissionhandler.PermissionUtils.hasPermissionInManifest(android.content.Context, java.util.ArrayList, java.lang.String):boolean");
    }

    static int toPermissionStatus(Activity activity, String str, int i) {
        if (i == -1) {
            return determineDeniedVariant(activity, str);
        }
        return 1;
    }

    static Integer strictestStatus(Collection<Integer> collection) {
        if (collection.contains(4)) {
            return 4;
        }
        if (collection.contains(2)) {
            return 2;
        }
        if (collection.contains(0)) {
            return 0;
        }
        if (collection.contains(3)) {
            return 3;
        }
        return 1;
    }

    static Integer strictestStatus(Integer num, Integer num2) {
        HashSet hashSet = new HashSet();
        hashSet.add(num);
        hashSet.add(num2);
        return strictestStatus(hashSet);
    }

    static int determineDeniedVariant(Activity activity, String str) {
        if (activity == null) {
            return 0;
        }
        boolean zWasPermissionDeniedBefore = wasPermissionDeniedBefore(activity, str);
        boolean z = true;
        boolean z2 = !isNeverAskAgainSelected(activity, str);
        if (!zWasPermissionDeniedBefore) {
            z = z2;
        } else if (z2) {
            z = false;
        }
        if (!zWasPermissionDeniedBefore && z) {
            setPermissionDenied(activity, str);
        }
        return (zWasPermissionDeniedBefore && z) ? 4 : 0;
    }

    static boolean isNeverAskAgainSelected(Activity activity, String str) {
        return !ActivityCompat.shouldShowRequestPermissionRationale(activity, str);
    }

    private static String determineBluetoothPermission(Context context, String str) {
        if (Build.VERSION.SDK_INT >= 31 && hasPermissionInManifest(context, null, str)) {
            return str;
        }
        if (Build.VERSION.SDK_INT < 29) {
            if (hasPermissionInManifest(context, null, "android.permission.ACCESS_FINE_LOCATION")) {
                return "android.permission.ACCESS_FINE_LOCATION";
            }
            if (hasPermissionInManifest(context, null, "android.permission.ACCESS_COARSE_LOCATION")) {
                return "android.permission.ACCESS_COARSE_LOCATION";
            }
            return null;
        }
        if (hasPermissionInManifest(context, null, "android.permission.ACCESS_FINE_LOCATION")) {
            return "android.permission.ACCESS_FINE_LOCATION";
        }
        return null;
    }

    private static PackageInfo getPackageInfo(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        if (Build.VERSION.SDK_INT >= 33) {
            return packageManager.getPackageInfo(context.getPackageName(), PackageManager.PackageInfoFlags.of(4096L));
        }
        return packageManager.getPackageInfo(context.getPackageName(), 4096);
    }

    private static boolean wasPermissionDeniedBefore(Context context, String str) {
        return context.getSharedPreferences(str, 0).getBoolean(SHARED_PREFERENCES_PERMISSION_WAS_DENIED_BEFORE_KEY, false);
    }

    private static void setPermissionDenied(Context context, String str) {
        context.getSharedPreferences(str, 0).edit().putBoolean(SHARED_PREFERENCES_PERMISSION_WAS_DENIED_BEFORE_KEY, true).apply();
    }
}
