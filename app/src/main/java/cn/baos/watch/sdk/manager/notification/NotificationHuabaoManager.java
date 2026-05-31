package cn.baos.watch.sdk.manager.notification;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Process;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import androidx.core.app.NotificationManagerCompat;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.NotificationListener;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class NotificationHuabaoManager implements INotificationManager {
    private static final long TIME_DURATION = 60000;
    private static NotificationHuabaoManager mNotificationHuabaoManager;

    public static NotificationHuabaoManager getInstance() {
        if (mNotificationHuabaoManager == null) {
            synchronized (NotificationHuabaoManager.class) {
                if (mNotificationHuabaoManager == null) {
                    mNotificationHuabaoManager = new NotificationHuabaoManager();
                }
            }
        }
        return mNotificationHuabaoManager;
    }

    private NotificationHuabaoManager() {
    }

    private void ensureCollectorRunning(Context context) {
        ComponentName componentName = new ComponentName(context, (Class<?>) NotificationListener.class);
        LogUtil.d("notification service 启动消息通知监听类ensureCollectorRunning collectorComponent: " + componentName);
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE);
        if (runningServices == null) {
            LogUtil.d("notification service ensureCollectorRunning() runningServices is NULL");
            return;
        }
        boolean z = false;
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
            if (runningServiceInfo.service.equals(componentName)) {
                LogUtil.d("notification service 系统消息通知监听服务ensureCollectorRunning service - pid: " + runningServiceInfo.pid + ", currentPID: " + Process.myPid() + ", clientPackage: " + runningServiceInfo.clientPackage + ", clientCount: " + runningServiceInfo.clientCount + ", clientLabel: " + (runningServiceInfo.clientLabel == 0 ? "0" : "(" + context.getResources().getString(runningServiceInfo.clientLabel) + ")"));
                if (runningServiceInfo.pid == Process.myPid()) {
                    z = true;
                }
            }
        }
        if (z) {
            LogUtil.d("notification service ensureCollectorRunning: collector is running");
        } else {
            LogUtil.d("notification service ensureCollectorRunning: collector not running, reviving...");
            toggleNotificationListenerService(context);
        }
    }

    private void toggleNotificationListenerService(Context context) {
        LogUtil.d("notification service 消息通知toggleNotificationListenerService() called");
        ComponentName componentName = new ComponentName(context, (Class<?>) NotificationListener.class);
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(componentName, 2, 1);
        packageManager.setComponentEnabledSetting(componentName, 1, 1);
    }

    @Override // cn.baos.watch.sdk.manager.notification.INotificationManager
    public boolean requestRebindNotificationService(Context context) {
        LogUtil.d("消息通知request rebind notification service inter");
        if (!isNotificationListenerEnabled(context)) {
            return false;
        }
        if (NotificationListener.isConnected()) {
            return true;
        }
        LogUtil.d("消息通知request rebind notification service start");
        ensureCollectorRunning(context);
        return false;
    }

    public void requestStopNotificationService(Context context) {
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, (Class<?>) NotificationListener.class), 2, 1);
        LogUtil.d("关闭消息通知监听类COMPONENT_ENABLED_STATE_DISABLED");
    }

    @Override // cn.baos.watch.sdk.manager.notification.INotificationManager
    public void openNotificationListenSettings(Context context) {
        LogUtil.d("打开消息监听通知的申请");
        if (isNotificationListenerEnabled(context)) {
            return;
        }
        try {
            context.startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // cn.baos.watch.sdk.manager.notification.INotificationManager
    public boolean isNotificationListenerEnabled(Context context) {
        if (NotificationManagerCompat.getEnabledListenerPackages(context).contains(context.getPackageName())) {
            LogUtil.d("消息通知监听服务已打开");
            return true;
        }
        LogUtil.d("消息通知监听服务未打开");
        return false;
    }

    @Override // cn.baos.watch.sdk.manager.notification.INotificationManager
    public void cancelNotification(NotificationListenerService notificationListenerService, StatusBarNotification statusBarNotification) {
        notificationListenerService.cancelNotification(statusBarNotification.getKey());
    }
}
