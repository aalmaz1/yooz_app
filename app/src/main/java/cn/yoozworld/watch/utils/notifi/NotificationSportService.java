package cn.yoozworld.watch.utils.notifi;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class NotificationSportService extends Service {
    private NotificationCompat.Builder builder;
    private android.app.NotificationManager notificationManager;
    private final int serviceId = 3;
    private int totalSecond;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        return super.onStartCommand(intent, i, i2);
    }

    private String getStringTime(int i) {
        return String.format(Locale.CHINA, "%02d:%02d:%02d", Integer.valueOf(i / 3600), Integer.valueOf((i % 3600) / 60), Integer.valueOf(i % 60));
    }

    public void stopService() {
        stopForeground(true);
    }
}
