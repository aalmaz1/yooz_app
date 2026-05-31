package cn.yoozworld.watch.utils.notifi;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.Window;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationUtils {
    public static int getNotificationLocationY(Context context) {
        Window window;
        View viewFindViewById;
        if (!(context instanceof Activity) || (window = ((Activity) context).getWindow()) == null || (viewFindViewById = window.findViewById(R.id.content)) == null) {
            return 0;
        }
        int[] iArr = new int[2];
        viewFindViewById.getLocationOnScreen(iArr);
        return iArr[1];
    }

    public static boolean isActivityNotAlive(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (!activity.isFinishing() && !activity.isDestroyed()) {
                return false;
            }
        }
        return true;
    }
}
