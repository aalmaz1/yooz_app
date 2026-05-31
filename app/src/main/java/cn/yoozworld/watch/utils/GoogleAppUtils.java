package cn.yoozworld.watch.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/* JADX INFO: loaded from: classes.dex */
public class GoogleAppUtils {
    public static void jumpToGooglePlay(Activity activity) {
        if (activity != null) {
            String packageName = activity.getPackageName();
            try {
                Intent intent = new Intent();
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                activity.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + packageName));
                try {
                    if (intent2.resolveActivity(activity.getPackageManager()) != null) {
                        activity.startActivity(intent2);
                    } else {
                        intent2.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                        activity.startActivity(intent2);
                    }
                } catch (Exception unused) {
                    activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                }
            }
        }
    }
}
