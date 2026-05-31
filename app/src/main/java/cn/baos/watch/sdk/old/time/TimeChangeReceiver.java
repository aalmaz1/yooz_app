package cn.baos.watch.sdk.old.time;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import cn.baos.watch.sdk.BasSdk;
import cn.baos.watch.sdk.base.AppDataConfig;
import cn.baos.watch.sdk.bluetooth.DataUtils;
import cn.baos.watch.sdk.utils.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class TimeChangeReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.TIMEZONE_CHANGED")) {
            LogUtil.d("时区变化");
            if (AppDataConfig.getInstance().isBindWatch()) {
                BasSdk.responseTimeZoneModify();
                return;
            }
            return;
        }
        if (intent.getAction().equals("android.intent.action.TIME_SET")) {
            LogUtil.d("时间制变化");
            if (AppDataConfig.getInstance().isBindWatch()) {
                DataUtils.setTimeFormat(context);
            }
        }
    }
}
