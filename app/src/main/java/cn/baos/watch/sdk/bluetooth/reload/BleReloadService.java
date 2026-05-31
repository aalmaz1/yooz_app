package cn.baos.watch.sdk.bluetooth.reload;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import cn.baos.watch.sdk.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class BleReloadService extends Service {
    PowerManager.WakeLock wakeLock = null;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        return super.onStartCommand(intent, i, i2);
    }

    private void acquireWakeLock() {
        if (this.wakeLock == null) {
            PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) getSystemService("power")).newWakeLock(536870913, getClass().getCanonicalName());
            this.wakeLock = wakeLockNewWakeLock;
            if (wakeLockNewWakeLock != null) {
                wakeLockNewWakeLock.acquire();
            }
        }
    }

    private void releaseWakeLock() {
        PowerManager.WakeLock wakeLock = this.wakeLock;
        if (wakeLock == null || !wakeLock.isHeld()) {
            return;
        }
        this.wakeLock.release();
        this.wakeLock = null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        LogUtil.e("BleReloadService->>onCreate");
        acquireWakeLock();
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        releaseWakeLock();
    }
}
