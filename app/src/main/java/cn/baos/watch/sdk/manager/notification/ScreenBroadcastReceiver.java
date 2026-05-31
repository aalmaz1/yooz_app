package cn.baos.watch.sdk.manager.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import cn.baos.watch.sdk.base.AppDataConfig;
import cn.baos.watch.sdk.constant.Constant;
import cn.baos.watch.sdk.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class ScreenBroadcastReceiver extends BroadcastReceiver {
    private String action = null;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        this.action = action;
        if ("android.intent.action.SCREEN_ON".equals(action)) {
            LogUtil.e("ScreenBroadcastReceiver开屏");
            AppDataConfig.getInstance().put(Constant.SUOPING, true);
        } else if ("android.intent.action.SCREEN_OFF".equals(this.action)) {
            AppDataConfig.getInstance().put(Constant.SUOPING, false);
            LogUtil.e("ScreenBroadcastReceiver锁屏");
        } else if ("android.intent.action.USER_PRESENT".equals(this.action)) {
            AppDataConfig.getInstance().put(Constant.SUOPING, true);
            LogUtil.e("ScreenBroadcastReceiver解锁");
        }
    }
}
