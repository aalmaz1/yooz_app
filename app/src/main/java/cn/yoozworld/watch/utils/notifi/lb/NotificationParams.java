package cn.yoozworld.watch.utils.notifi.lb;

import android.app.PendingIntent;
import android.net.Uri;
import android.widget.RemoteViews;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationParams {
    public int[] flags;
    public boolean onlyAlertOnce = false;
    public int defaults = 0;
    public long[] pattern = null;
    public boolean ongoing = false;
    public RemoteViews remoteViews = null;
    public PendingIntent intent = null;
    public String ticker = "";
    public int priority = 0;
    public long when = 0;
    public Uri sound = null;

    public NotificationParams setOngoing(boolean z) {
        this.ongoing = z;
        return this;
    }

    public NotificationParams setContent(RemoteViews remoteViews) {
        this.remoteViews = remoteViews;
        return this;
    }

    public NotificationParams setContentIntent(PendingIntent pendingIntent) {
        this.intent = pendingIntent;
        return this;
    }

    public NotificationParams setTicker(String str) {
        this.ticker = str;
        return this;
    }

    public NotificationParams setPriority(int i) {
        this.priority = i;
        return this;
    }

    public NotificationParams setOnlyAlertOnce(boolean z) {
        this.onlyAlertOnce = z;
        return this;
    }

    public NotificationParams setWhen(long j) {
        this.when = j;
        return this;
    }

    public NotificationParams setSound(Uri uri) {
        this.sound = uri;
        return this;
    }

    public NotificationParams setDefaults(int i) {
        this.defaults = i;
        return this;
    }

    public NotificationParams setVibrate(long[] jArr) {
        this.pattern = jArr;
        return this;
    }

    public NotificationParams setFlags(int... iArr) {
        this.flags = iArr;
        return this;
    }
}
