package com.baseflow.geolocator.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;

/* JADX INFO: loaded from: classes.dex */
public class Utils {
    public static boolean openAppSettings(Context context) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            intent.addFlags(268435456);
            intent.addFlags(BasicMeasure.EXACTLY);
            intent.addFlags(8388608);
            context.startActivity(intent);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean openLocationSettings(Context context) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.settings.LOCATION_SOURCE_SETTINGS");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(268435456);
            intent.addFlags(BasicMeasure.EXACTLY);
            intent.addFlags(8388608);
            context.startActivity(intent);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }
}
