package cn.baos.watch.sdk.old.volume;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import cn.baos.watch.sdk.manager.musiccontroller.MusicControlManager;
import cn.baos.watch.sdk.old.MainHandler;
import cn.baos.watch.sdk.utils.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class VolumeBroadcastReceiver extends BroadcastReceiver {
    private VolumeManager mVolumeManager;

    public VolumeBroadcastReceiver(VolumeManager volumeManager) {
        this.mVolumeManager = volumeManager;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        VolumeChangeListener volumeChangeListener;
        if ("android.media.VOLUME_CHANGED_ACTION".equals(intent.getAction()) && intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) == 3) {
            LogUtil.d("android.media.VOLUME_CHANGED_ACTION,用户调节音量修改了...");
            int currentVolume = MusicControlManager.getInstance().getCurrentVolume();
            MainHandler.getInstance().obtainMessage(183).sendToTarget();
            VolumeManager volumeManager = this.mVolumeManager;
            if (volumeManager == null || (volumeChangeListener = volumeManager.getVolumeChangeListener()) == null || currentVolume < 0) {
                return;
            }
            volumeChangeListener.onVolumeChanged(currentVolume);
        }
    }
}
