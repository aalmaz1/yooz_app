package cn.yoozworld.watch.utils.notifi;

import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class MyHandler extends Handler {
    private final WeakReference<NotificationManager> mWeakReference;

    public MyHandler(NotificationManager notificationManager) {
        this.mWeakReference = new WeakReference<>(notificationManager);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        super.handleMessage(message);
        WeakReference<NotificationManager> weakReference = this.mWeakReference;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        int i = message.what;
        if (i == 1) {
            CustomNotification customNotification = (CustomNotification) message.getData().getParcelable(NotificationManager.BUNDLE_NOTIFICATION);
            if (customNotification != null) {
                this.mWeakReference.get().showNotification(customNotification);
                return;
            }
            return;
        }
        this.mWeakReference.get().hideNotification(i - 2);
    }
}
