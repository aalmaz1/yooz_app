package cn.baos.watch.sdk.database.notification;

import cn.baos.watch.sdk.entitiy.NotificationAppListEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IDatabaseNotificationHandler {
    void close();

    void createDatabase();

    void delete(NotificationAppListEntity notificationAppListEntity);

    ArrayList<NotificationAppListEntity> getAllNotificationAppListEntities();

    boolean hasNotification(NotificationAppListEntity notificationAppListEntity);

    void insert(NotificationAppListEntity notificationAppListEntity);

    void open();

    NotificationAppListEntity query(NotificationAppListEntity notificationAppListEntity);

    void update(NotificationAppListEntity notificationAppListEntity);
}
