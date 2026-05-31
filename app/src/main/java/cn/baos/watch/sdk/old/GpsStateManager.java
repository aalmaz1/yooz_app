package cn.baos.watch.sdk.old;

import android.content.Context;
import android.content.IntentFilter;
import cn.baos.watch.sdk.broadcastreceiver.GpsReceiver;

/* JADX INFO: loaded from: classes.dex */
public class GpsStateManager {
    private static GpsStateManager instance;
    private GpsReceiver mReceiver = new GpsReceiver();

    private GpsStateManager() {
    }

    public static GpsStateManager getInstance() {
        if (instance == null) {
            synchronized (GpsStateManager.class) {
                if (instance == null) {
                    instance = new GpsStateManager();
                }
            }
        }
        return instance;
    }

    public void register(Context context) {
        context.registerReceiver(this.mReceiver, new IntentFilter("android.location.PROVIDERS_CHANGED"));
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this.mReceiver);
    }
}
