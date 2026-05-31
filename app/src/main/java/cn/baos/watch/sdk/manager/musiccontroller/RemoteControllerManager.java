package cn.baos.watch.sdk.manager.musiccontroller;

import android.content.Context;
import android.media.AudioManager;
import android.media.RemoteController;
import cn.baos.watch.sdk.R;
import cn.baos.watch.sdk.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class RemoteControllerManager {
    private static RemoteControllerManager instance;
    private RemoteController remoteController;

    public static RemoteControllerManager getInstance() {
        if (instance == null) {
            synchronized (RemoteControllerManager.class) {
                if (instance == null) {
                    instance = new RemoteControllerManager();
                }
            }
        }
        return instance;
    }

    public RemoteController getRemoteController() {
        return this.remoteController;
    }

    public void registerRemoteController(Context context, RemoteController.OnClientUpdateListener onClientUpdateListener) {
        LogUtil.d("注册 registerRemoteController");
        this.remoteController = new RemoteController(context, onClientUpdateListener);
        boolean zRegisterRemoteController = false;
        try {
            zRegisterRemoteController = ((AudioManager) context.getSystemService("audio")).registerRemoteController(this.remoteController);
        } catch (NullPointerException unused) {
        } catch (Exception e) {
            LogUtil.d(e.getMessage());
        }
        if (zRegisterRemoteController) {
            LogUtil.d("注册 registerRemoteController registered:true");
            try {
                this.remoteController.setArtworkConfiguration(context.getResources().getDimensionPixelSize(R.dimen.dp_10), context.getResources().getDimensionPixelSize(R.dimen.dp_10));
                this.remoteController.setSynchronizationMode(1);
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void unRegisterRemoteController(Context context) {
        if (this.remoteController != null) {
            LogUtil.d("取消注册 registerRemoteController");
            try {
                ((AudioManager) context.getSystemService("audio")).unregisterRemoteController(this.remoteController);
            } catch (NullPointerException unused) {
            } catch (Exception e) {
                LogUtil.d(e.getMessage());
            }
        }
    }
}
