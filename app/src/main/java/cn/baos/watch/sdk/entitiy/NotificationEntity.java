package cn.baos.watch.sdk.entitiy;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

/* JADX INFO: loaded from: classes.dex */
public class NotificationEntity {
    private NotificationListenerService.RankingMap rankingMap;
    private StatusBarNotification sbn;

    public NotificationEntity(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        this.sbn = statusBarNotification;
        this.rankingMap = rankingMap;
    }

    public StatusBarNotification getSbn() {
        return this.sbn;
    }

    public void setSbn(StatusBarNotification statusBarNotification) {
        this.sbn = statusBarNotification;
    }

    public NotificationListenerService.RankingMap getRankingMap() {
        return this.rankingMap;
    }

    public void setRankingMap(NotificationListenerService.RankingMap rankingMap) {
        this.rankingMap = rankingMap;
    }
}
