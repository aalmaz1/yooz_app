package cn.baos.watch.sdk.manager.notification;

import android.content.Context;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

/* JADX INFO: loaded from: classes.dex */
public interface INotificationManager {
    void cancelNotification(NotificationListenerService notificationListenerService, StatusBarNotification statusBarNotification);

    boolean isNotificationListenerEnabled(Context context);

    void openNotificationListenSettings(Context context);

    boolean requestRebindNotificationService(Context context);
}
