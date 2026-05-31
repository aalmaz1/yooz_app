package cn.yoozworld.watch.utils.notifi.lb;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class NotificationUtils extends ContextWrapper {
    private static String CHANNEL_ID = "default";
    private static String CHANNEL_NAME = "Default_Channel";
    private NotificationChannel channel;
    private NotificationManager mManager;
    private NotificationParams params;

    public NotificationUtils(Context context) {
        super(context);
        createNotificationChannel(null, null);
    }

    public NotificationUtils(Context context, String str) {
        super(context);
        createNotificationChannel(str, null);
    }

    public NotificationUtils(Context context, String str, String str2) {
        super(context);
        createNotificationChannel(str, str2);
    }

    private NotificationChannel createNotificationChannel(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            CHANNEL_ID = str;
        }
        if (!TextUtils.isEmpty(str2)) {
            CHANNEL_NAME = str2;
        }
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, 3);
        this.channel = notificationChannel;
        notificationChannel.setSound(null, null);
        getManager().createNotificationChannel(this.channel);
        return this.channel;
    }

    public NotificationManager getManager() {
        if (this.mManager == null) {
            this.mManager = (NotificationManager) getSystemService(cn.yoozworld.watch.utils.notifi.NotificationManager.BUNDLE_NOTIFICATION);
        }
        return this.mManager;
    }

    public NotificationChannel getNotificationChannel() {
        if (this.channel == null) {
            this.channel = createNotificationChannel(null, null);
        }
        return this.channel;
    }

    public NotificationChannel getNotificationChannel(String str) {
        if (TextUtils.isEmpty(str)) {
            return getNotificationChannel();
        }
        return getManager().getNotificationChannel(str);
    }

    public void clearNotification() {
        getManager().cancelAll();
    }

    public void clearId() {
        getManager().cancel(1520);
    }

    public void clearNotificationChannel(String str) {
        if (str == null || str.length() == 0) {
            return;
        }
        getManager().deleteNotificationChannel(str);
    }

    public void clearAllNotification() {
        NotificationManager manager = getManager();
        List<NotificationChannel> notificationChannels = manager.getNotificationChannels();
        if (notificationChannels != null) {
            for (int i = 0; i < notificationChannels.size(); i++) {
                NotificationChannel notificationChannel = notificationChannels.get(i);
                if (notificationChannel != null) {
                    String id = notificationChannel.getId();
                    Log.d("notification channel ", id + " , " + ((Object) notificationChannel.getName()));
                    manager.deleteNotificationChannel(id);
                }
            }
        }
    }

    public void clearAllGroupNotification() {
        NotificationManager manager = getManager();
        List<NotificationChannelGroup> notificationChannelGroups = manager.getNotificationChannelGroups();
        if (notificationChannelGroups != null) {
            for (int i = 0; i < notificationChannelGroups.size(); i++) {
                NotificationChannelGroup notificationChannelGroup = notificationChannelGroups.get(i);
                if (notificationChannelGroup != null) {
                    String id = notificationChannelGroup.getId();
                    Log.d("notification group ", id + " , " + ((Object) notificationChannelGroup.getName()));
                    manager.deleteNotificationChannel(id);
                }
            }
        }
    }

    public Notification getNotification(String str, String str2, int i) {
        Notification notificationBuild = getNotificationV4(str, str2, i).build();
        NotificationParams notificationParams = getNotificationParams();
        if (notificationParams.flags != null && notificationParams.flags.length > 0) {
            for (int i2 = 0; i2 < notificationParams.flags.length; i2++) {
                notificationBuild.flags |= notificationParams.flags[i2];
            }
        }
        return notificationBuild;
    }

    public void sendNotification(int i, String str, String str2, int i2) {
        Notification notificationBuild = getNotificationV4(str, str2, i2).build();
        NotificationParams notificationParams = getNotificationParams();
        if (notificationParams.flags != null && notificationParams.flags.length > 0) {
            for (int i3 = 0; i3 < notificationParams.flags.length; i3++) {
                notificationBuild.flags |= notificationParams.flags[i3];
            }
        }
        getManager().notify(i, notificationBuild);
    }

    public void sendNotificationCompat(int i, String str, String str2, int i2) {
        Notification notificationBuild = getNotificationCompat(str, str2, i2).build();
        NotificationParams notificationParams = getNotificationParams();
        if (notificationParams.flags != null && notificationParams.flags.length > 0) {
            for (int i3 = 0; i3 < notificationParams.flags.length; i3++) {
                notificationBuild.flags |= notificationParams.flags[i3];
            }
        }
        getManager().notify(i, notificationBuild);
    }

    private NotificationCompat.Builder getNotificationCompat(String str, String str2, int i) {
        NotificationParams notificationParams = getNotificationParams();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setContentTitle(str);
        builder.setContentText(str2);
        builder.setSmallIcon(i);
        builder.setPriority(notificationParams.priority);
        builder.setOnlyAlertOnce(notificationParams.onlyAlertOnce);
        builder.setOngoing(notificationParams.ongoing);
        if (notificationParams.remoteViews != null) {
            builder.setContent(notificationParams.remoteViews);
        }
        if (notificationParams.intent != null) {
            builder.setContentIntent(notificationParams.intent);
        }
        if (notificationParams.ticker != null && notificationParams.ticker.length() > 0) {
            builder.setTicker(notificationParams.ticker);
        }
        if (notificationParams.when != 0) {
            builder.setWhen(notificationParams.when);
        }
        if (notificationParams.sound != null) {
            builder.setSound(notificationParams.sound);
        }
        if (notificationParams.defaults != 0) {
            builder.setDefaults(notificationParams.defaults);
        }
        builder.setAutoCancel(true);
        return builder;
    }

    private Notification.Builder getNotificationV4(String str, String str2, int i) {
        NotificationParams notificationParams = getNotificationParams();
        Notification.Builder autoCancel = new Notification.Builder(getApplicationContext(), CHANNEL_ID).setContentTitle(str).setContentText(str2).setSmallIcon(i).setOngoing(notificationParams.ongoing).setPriority(notificationParams.priority).setOnlyAlertOnce(notificationParams.onlyAlertOnce).setAutoCancel(true);
        if (notificationParams.remoteViews != null) {
            autoCancel.setContent(notificationParams.remoteViews);
        }
        if (notificationParams.intent != null) {
            autoCancel.setContentIntent(notificationParams.intent);
        }
        if (notificationParams.ticker != null && notificationParams.ticker.length() > 0) {
            autoCancel.setTicker(notificationParams.ticker);
        }
        if (notificationParams.when != 0) {
            autoCancel.setWhen(notificationParams.when);
        }
        if (notificationParams.sound != null) {
            autoCancel.setSound(notificationParams.sound);
        }
        if (notificationParams.defaults != 0) {
            autoCancel.setDefaults(notificationParams.defaults);
        }
        if (notificationParams.pattern != null) {
            autoCancel.setVibrate(notificationParams.pattern);
        }
        return autoCancel;
    }

    public NotificationParams getNotificationParams() {
        NotificationParams notificationParams = this.params;
        return notificationParams == null ? new NotificationParams() : notificationParams;
    }

    public NotificationUtils setNotificationParams(NotificationParams notificationParams) {
        this.params = notificationParams;
        return this;
    }

    public boolean isNoImportance(NotificationChannel notificationChannel) {
        return notificationChannel.getImportance() == 0;
    }

    public boolean isNoImportance(String str) {
        return isNoImportance(getNotificationChannel(str));
    }

    public void openChannelSetting(String str) {
        openChannelSetting(getNotificationChannel(str));
    }

    public void openChannelSetting(NotificationChannel notificationChannel) {
        if (notificationChannel != null && notificationChannel.getImportance() == 0) {
            Intent intent = new Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
            intent.putExtra("android.provider.extra.CHANNEL_ID", notificationChannel.getId());
            startActivity(intent);
        }
    }
}
