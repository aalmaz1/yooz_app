package cn.baos.watch.sdk.old.volume;

import android.content.Context;
import android.content.IntentFilter;
import cn.baos.watch.sdk.utils.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class VolumeManager {
    private static VolumeManager instance;
    private Context mContext;
    private boolean mRegistered = false;
    private VolumeBroadcastReceiver mVolumeBroadcastReceiver;
    private VolumeChangeListener mVolumeChangeListener;

    public static VolumeManager getInstance() {
        if (instance == null) {
            synchronized (VolumeManager.class) {
                if (instance == null) {
                    instance = new VolumeManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public VolumeChangeListener getVolumeChangeListener() {
        return this.mVolumeChangeListener;
    }

    public void setVolumeChangeListener(VolumeChangeListener volumeChangeListener) {
        this.mVolumeChangeListener = volumeChangeListener;
    }

    public void registerReceiver() {
        this.mVolumeBroadcastReceiver = new VolumeBroadcastReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
        this.mContext.registerReceiver(this.mVolumeBroadcastReceiver, intentFilter);
        this.mRegistered = true;
    }

    public void unregisterReceiver() {
        if (this.mRegistered) {
            try {
                this.mContext.unregisterReceiver(this.mVolumeBroadcastReceiver);
                LogUtil.d("取消声音监听广播");
                this.mVolumeChangeListener = null;
                this.mRegistered = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
