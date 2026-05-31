package cn.yoozworld.watch.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import cn.baos.watch.sdk.BasSdk;
import cn.baos.watch.sdk.base.AppDataConfig;
import cn.baos.watch.sdk.bluetooth.DataUtils;
import cn.baos.watch.sdk.util.LogUtil;
import cn.yoozworld.watch.utils.BtConstant;

/* JADX INFO: loaded from: classes.dex */
public class TimeZoneReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        LogUtil.d("时区/时间/日期发生变化");
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: cn.yoozworld.watch.ui.TimeZoneReceiver$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BLfLst.getInstance().invokeFlutterMethod(BtConstant.getWeather, null);
            }
        });
        if (intent.getAction().equals("android.intent.action.TIMEZONE_CHANGED")) {
            LogUtil.d("时区变化");
            if (AppDataConfig.getInstance().isBindWatch()) {
                BasSdk.responseTimeZoneModify();
                return;
            }
            return;
        }
        if (intent.getAction().equals("android.intent.action.TIME_SET") || intent.getAction().equals("android.intent.action.DATE_CHANGED")) {
            LogUtil.d("时间制变化");
            LogUtil.d("----action:" + intent.getAction());
            if (AppDataConfig.getInstance().isBindWatch()) {
                BasSdk.responseTimeModify();
                DataUtils.setTimeFormat(context);
            }
        }
    }
}
