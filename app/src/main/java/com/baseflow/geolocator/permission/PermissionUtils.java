package com.baseflow.geolocator.permission;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
public class PermissionUtils {
    public static boolean hasPermissionInManifest(Context context, String str) {
        try {
            PackageInfo packageInfo = getPackageInfo(context);
            if (packageInfo.requestedPermissions != null) {
                for (String str2 : packageInfo.requestedPermissions) {
                    if (str2.equals(str)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static PackageInfo getPackageInfo(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        if (Build.VERSION.SDK_INT < 33) {
            return packageManager.getPackageInfo(packageName, 4096);
        }
        return packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(4096L));
    }
}
