package cn.baos.watch.sdk.util;

import android.content.Intent;
import android.media.RemoteController;
import android.os.Handler;
import android.os.Message;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import cn.baos.watch.sdk.entitiy.Constant;
import cn.baos.watch.sdk.entitiy.NotificationEntity;
import cn.baos.watch.sdk.manager.musiccontroller.MusicControlManager;
import cn.baos.watch.sdk.manager.musiccontroller.RemoteControllerManager;
import cn.baos.watch.sdk.manager.notification.NotificationHandler;
import cn.baos.watch.w100.messages.MusicControlResponse;
import com.google.gson.Gson;

/* JADX INFO: loaded from: classes.dex */
public class NotificationListener extends NotificationListenerService implements RemoteController.OnClientUpdateListener {
    private static boolean isConnected = false;
    public static int musicPosition;
    private Handler mHandler;
    private MusicControlResponse musicControlResponse = new MusicControlResponse();

    public static boolean isConnected() {
        LogUtil.d("获取消息通知当前连接状态:" + isConnected);
        return isConnected;
    }

    public void setConnected(boolean z) {
        isConnected = z;
        LogUtil.d("设置消息通知当前连接状态:" + isConnected);
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        LogUtil.d("notification service onCreate");
        this.mHandler = new NotificationHandler(this);
        RemoteControllerManager.getInstance().registerRemoteController(this, this);
        this.musicControlResponse.status = 12;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        LogUtil.d("NotificationListener notification service重启模式:" + i);
        return 2;
    }

    @Override // android.service.notification.NotificationListenerService, android.app.Service
    public void onDestroy() {
        LogUtil.d("消息监听服务notification service onDestroy");
        setConnected(false);
        this.mHandler = null;
        super.onDestroy();
    }

    @Override // android.service.notification.NotificationListenerService
    public void onListenerConnected() {
        setConnected(true);
        LogUtil.d("notification service onListenerConnected");
        Handler handler = this.mHandler;
        if (handler != null) {
            Message.obtain(handler, 3).sendToTarget();
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        LogUtil.d("notification service onNotificationRankingUpdate");
        Handler handler = this.mHandler;
        if (handler != null) {
            Message.obtain(handler, 4, new NotificationEntity(null, rankingMap)).sendToTarget();
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        if (Constant.isPushNotification) {
            LogUtil.d("notification service onNotificationPosted");
            Handler handler = this.mHandler;
            if (handler != null) {
                Message.obtain(handler, 1, new NotificationEntity(statusBarNotification, rankingMap)).sendToTarget();
            }
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        LogUtil.d("notification service onNotificationRemoved");
        try {
            Handler handler = this.mHandler;
            if (handler != null) {
                Message.obtain(handler, 2, new NotificationEntity(statusBarNotification, rankingMap)).sendToTarget();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        LogUtil.d("notification service onListenerDisconnected");
    }

    @Override // android.media.RemoteController.OnClientUpdateListener
    public void onClientChange(boolean z) {
        this.musicControlResponse.status = 12;
        MusicControlManager.getInstance().sendSongStatusAndVolumeToWatch(this.musicControlResponse);
        LogUtil.d("notification service clearing:" + z);
    }

    @Override // android.media.RemoteController.OnClientUpdateListener
    public void onClientPlaybackStateUpdate(int i) {
        LogUtil.d("state:" + i);
    }

    @Override // android.media.RemoteController.OnClientUpdateListener
    public void onClientPlaybackStateUpdate(int i, long j, long j2, float f) {
        LogUtil.d("StateUpdate 歌曲状态更新,回调" + i);
        if (i == 2) {
            this.musicControlResponse.status = 2;
        } else if (i == 3) {
            this.musicControlResponse.status = 1;
        } else if (i == 0) {
            this.musicControlResponse.status = 2;
        }
        musicPosition = (int) j2;
        LogUtil.d("StateUpdate 歌曲状态更新" + W100Utils.toString(this.musicControlResponse));
        MusicControlManager.getInstance().sendSongStatusAndVolumeToWatch(this.musicControlResponse);
    }

    @Override // android.media.RemoteController.OnClientUpdateListener
    public void onClientTransportControlUpdate(int i) {
        LogUtil.d("transportControlFlags:" + i);
    }

    @Override // android.media.RemoteController.OnClientUpdateListener
    public void onClientMetadataUpdate(RemoteController.MetadataEditor metadataEditor) {
        String string = metadataEditor.getString(7, null);
        metadataEditor.getString(2, null);
        metadataEditor.getString(1, null);
        metadataEditor.getLong(9, -1L);
        metadataEditor.getBitmap(100, null);
        this.musicControlResponse.name = string;
        LogUtil.d(String.format("MetadataUpdate 歌曲信息更新:" + new Gson().toJson(this.musicControlResponse), new Object[0]));
        MusicControlManager.getInstance().sendSongStatusAndVolumeToWatch(this.musicControlResponse);
    }
}
