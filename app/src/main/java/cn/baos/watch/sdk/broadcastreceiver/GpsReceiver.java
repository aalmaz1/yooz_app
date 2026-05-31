package cn.baos.watch.sdk.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import cn.baos.watch.sdk.bluetooth.BleService;
import cn.baos.watch.sdk.manager.gps.GpsManager;
import cn.baos.watch.sdk.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class GpsReceiver extends BroadcastReceiver {
    boolean isFirstTime = true;
    boolean isGpsOpen;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
            if (GpsManager.getInstance().checkGPSOpen() != this.isGpsOpen || this.isFirstTime) {
                if (GpsManager.getInstance().checkGPSOpen()) {
                    this.isGpsOpen = true;
                    LogUtil.d("定位已打开");
                } else {
                    this.isGpsOpen = false;
                    LogUtil.d("定位已关闭");
                    BleService.getInstance().getNotificationHandler().onGpsNotOpen();
                }
                this.isFirstTime = false;
            }
        }
    }
}
