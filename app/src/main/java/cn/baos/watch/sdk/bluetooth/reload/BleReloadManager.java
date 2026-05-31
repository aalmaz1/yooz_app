package cn.baos.watch.sdk.bluetooth.reload;

import android.content.Context;
import android.content.Intent;
import cn.baos.watch.sdk.base.AppDataConfig;
import cn.baos.watch.sdk.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class BleReloadManager {
    private static BleReloadManager instance;
    private Context mContext;

    public static BleReloadManager getInstance() {
        if (instance == null) {
            synchronized (BleReloadManager.class) {
                if (instance == null) {
                    instance = new BleReloadManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void startService() {
        LogUtil.e("-startService-isBindWatch-" + AppDataConfig.getInstance().isBindWatch());
        this.mContext.startService(new Intent(this.mContext, (Class<?>) BleReloadService.class));
    }

    public void stopService() {
        this.mContext.stopService(new Intent(this.mContext, (Class<?>) BleReloadService.class));
    }
}
