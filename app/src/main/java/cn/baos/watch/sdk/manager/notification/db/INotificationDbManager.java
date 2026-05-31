package cn.baos.watch.sdk.manager.notification.db;

import cn.baos.watch.sdk.entitiy.NotificationAppListEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface INotificationDbManager {
    void deleteNotification(NotificationAppListEntity notificationAppListEntity);

    ArrayList<NotificationAppListEntity> getAllNotification();

    boolean hasNotification(NotificationAppListEntity notificationAppListEntity);

    void initNotificationDb();

    void insertNotification(NotificationAppListEntity notificationAppListEntity);

    boolean queryCheckStateLightDb(String str);

    NotificationAppListEntity queryNotification(NotificationAppListEntity notificationAppListEntity);

    void saveCheckStateLightDb(String str, boolean z);

    void updateNotification(NotificationAppListEntity notificationAppListEntity);

    void updateNotifications(ArrayList<NotificationAppListEntity> arrayList, boolean z);
}
