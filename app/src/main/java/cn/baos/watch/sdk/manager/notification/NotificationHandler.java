package cn.baos.watch.sdk.manager.notification;

import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import androidx.core.os.EnvironmentCompat;
import cn.baos.watch.sdk.base.AppDataConfig;
import cn.baos.watch.sdk.code.callcontroller.CallListenService;
import cn.baos.watch.sdk.entitiy.Constant;
import cn.baos.watch.sdk.entitiy.NotificationAppListEntity;
import cn.baos.watch.sdk.entitiy.NotificationEntity;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.manager.notification.db.NotificationDbManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.NotificationListener;
import cn.baos.watch.sdk.util.SharePreferenceUtils;
import cn.baos.watch.sdk.util.W100Utils;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class NotificationHandler extends Handler {
    private NotificationAppListEntity mNotificationAppListEntity;
    private NotificationEntity mNotificationEntity;
    private NotificationListener mNotificationListener;
    private ArrayList mNotificationContents = new ArrayList();
    Runnable contentResetRunnable = new Runnable() { // from class: cn.baos.watch.sdk.manager.notification.NotificationHandler.1
        @Override // java.lang.Runnable
        public void run() {
            new Thread(new Runnable() { // from class: cn.baos.watch.sdk.manager.notification.NotificationHandler.1.1
                @Override // java.lang.Runnable
                public void run() {
                    LogUtil.d("通知内容存储常量重置");
                    NotificationHandler.this.mNotificationContents.clear();
                }
            }).start();
        }
    };

    public NotificationHandler(NotificationListener notificationListener) {
        this.mNotificationListener = notificationListener;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        if (this.mNotificationListener == null) {
            LogUtil.e("系统监听通知接口为空");
            return;
        }
        if (message.obj instanceof NotificationEntity) {
            this.mNotificationEntity = (NotificationEntity) message.obj;
        }
        if (!NotificationListener.isConnected()) {
            LogUtil.e("系统监听通知接口未连接");
            return;
        }
        switch (message.what) {
            case 1:
                logSbn("posted_notify:", this.mNotificationEntity.getSbn());
                if (!NotificationDbManager.getInstance(this.mNotificationListener).queryCheckStateLightDb("notificationManageKey")) {
                    LogUtil.d("通知总开关被关闭了，不通知,包名:" + this.mNotificationEntity.getSbn().getPackageName());
                    return;
                }
                try {
                    boolean zIsScreenOn = ((PowerManager) this.mNotificationListener.getSystemService("power")).isScreenOn();
                    ((KeyguardManager) this.mNotificationListener.getSystemService("keyguard")).inKeyguardRestrictedInputMode();
                    boolean zIsDeviceLock = AppDataConfig.getInstance().isDeviceLock();
                    boolean zQuerySwitchOnlyNotice = NotificationDbManager.getInstance(this.mNotificationListener.getApplicationContext()).querySwitchOnlyNotice();
                    LogUtil.d("通知锁屏通知:" + zQuerySwitchOnlyNotice);
                    LogUtil.d("是否锁屏:" + zIsScreenOn);
                    LogUtil.d("是否锁屏-suoping:" + zIsDeviceLock);
                    if (zIsDeviceLock && zQuerySwitchOnlyNotice) {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (NotificationHandler.class) {
                    if (this.mNotificationEntity.getSbn().getNotification().extras.getCharSequence(NotificationCompat.EXTRA_TITLE) == null && this.mNotificationEntity.getSbn().getNotification().extras.getCharSequence(NotificationCompat.EXTRA_TEXT) == null) {
                        LogUtil.d("无用通知推送");
                        return;
                    }
                    String string = this.mNotificationEntity.getSbn().getNotification().extras.getString(NotificationCompat.EXTRA_TITLE);
                    String string2 = this.mNotificationEntity.getSbn().getNotification().extras.getString(NotificationCompat.EXTRA_TEXT);
                    if (string2 == null && this.mNotificationEntity.getSbn().getNotification().extras.getCharSequence(NotificationCompat.EXTRA_TEXT) != null) {
                        string2 = this.mNotificationEntity.getSbn().getNotification().extras.getCharSequence(NotificationCompat.EXTRA_TEXT).toString();
                        LogUtil.d("content为空，重新赋值:" + string2);
                    }
                    LogUtil.d("消息通知tittle:" + string + ",content:" + string2);
                    if (!TextUtils.isEmpty(string2) && !TextUtils.isEmpty(string)) {
                        if (!TextUtils.isEmpty(string) && string.contains("”正在运行")) {
                            LogUtil.d("标题包含XX正在运行,不通知");
                            return;
                        }
                        if (!TextUtils.isEmpty(string2) && string2.contains("正在后台运行")) {
                            LogUtil.d("内容包含正在后台运行,不通知");
                            return;
                        }
                        if (!TextUtils.isEmpty(string2) && (string2.contains("已连接") || string2.contains("已断开"))) {
                            LogUtil.d("内容包含已连接,不通知");
                            return;
                        }
                        if (!TextUtils.isEmpty(string) && string.startsWith("今日") && string.contains("步")) {
                            LogUtil.d("内容含今日？步,不通知");
                            return;
                        }
                        if (!TextUtils.isEmpty(string2)) {
                            if (string2.contains("语音通话") && !string2.contains("[")) {
                                LogUtil.d("内容包含语音通话(非未接),不通知");
                                return;
                            }
                            if (string2.contains("视频通话") && !string2.contains("[")) {
                                LogUtil.d("内容包含视频通话(非未接),不通知");
                                return;
                            } else if (string2.contains("正在呼叫你")) {
                                LogUtil.d("内容包含正在呼叫你,不通知");
                                return;
                            } else if (string2.contains("地理位置")) {
                                LogUtil.d("获取地理位置,不通知");
                                return;
                            }
                        }
                        String packageName = this.mNotificationEntity.getSbn().getPackageName();
                        if (packageName.equals("com.android.incallui") || packageName.equals(Constant.PACKAGE_NAME_CONTACTS)) {
                            if (!SharePreferenceUtils.queryBooleanByKeySetBoolean(this.mNotificationListener, "SWITCH_CALL_PHONE_NOTIFICATION", true)) {
                                LogUtil.d("来电提醒功能已被用户关闭");
                                return;
                            }
                            LogUtil.d("phone 来电提醒通知和未接来电，目前需要处理");
                            if (CallListenService.phoneState) {
                                CallListenService.phoneState = false;
                            } else {
                                LogUtil.d("phone 没有来电进来不通知该条消息");
                                return;
                            }
                        } else {
                            if (!packageName.equals("com.android.systemui") && !packageName.equals("android") && !packageName.equals("com.mfashiongallery.emag") && !packageName.equals("com.android.deskclock") && !packageName.equals("cn.baos.watch.w100") && !packageName.equals("com.xiaomi.bsp.gps.nps") && !packageName.equals("com.xiaomi.market") && !packageName.equals("com.xiaomi.simactivate.service") && !packageName.equals("com.xiaomi.mi_connect_service") && ((!packageName.startsWith("com.android") || packageName.contains("com.samsung.android.messaging") || packageName.contains(Constant.PACKAGE_NAME_SHORT_MESSAGE) || packageName.equals(Constant.PACKAGE_NAME_PHONE_CALL)) && !packageName.equals("com.tencent.qqmusic"))) {
                                boolean zIsSystemApp = NotificationDbManager.getInstance(this.mNotificationListener).isSystemApp(packageName);
                                LogUtil.d("系统类APP " + packageName + ":" + zIsSystemApp);
                                if (zIsSystemApp && !packageName.equals(Constant.PACKAGE_NAME_PHONE_CALL) && !packageName.contains(Constant.PACKAGE_NAME_SHORT_MESSAGE) && !packageName.contains("com.samsung.android.messaging") && !packageName.contains("com.samsung.android.dialer")) {
                                    LogUtil.d("系统类APP:默认不通知: " + packageName);
                                    return;
                                } else if (Constant.PACKAGE_KUGOU.equals(packageName)) {
                                    LogUtil.d("系统类APP:默认不通知: 酷狗音乐");
                                    return;
                                }
                            }
                            LogUtil.d("黑名单:默认不通知app");
                            return;
                        }
                        if (this.mNotificationContents.size() != 0 && this.mNotificationContents.contains(this.mNotificationEntity.getSbn().getNotification().extras.getCharSequence(NotificationCompat.EXTRA_TEXT))) {
                            LogUtil.d("重复内容通知,过滤:" + ((Object) this.mNotificationEntity.getSbn().getNotification().extras.getCharSequence(NotificationCompat.EXTRA_TEXT)));
                            return;
                        }
                        this.mNotificationAppListEntity = new NotificationAppListEntity(packageName, W100Utils.getAppName(this.mNotificationListener, packageName));
                        if (NotificationDbManager.getInstance(this.mNotificationListener).hasNotification(this.mNotificationAppListEntity)) {
                            LogUtil.d("notification has already");
                            if (NotificationDbManager.getInstance(this.mNotificationListener).queryNotification(this.mNotificationAppListEntity).isChecked()) {
                                LogUtil.d("notification isCheck is true");
                                MessageManager.getInstance().pushNotification(this.mNotificationEntity.getSbn());
                            } else {
                                LogUtil.d("notification isCheck is false");
                            }
                        } else {
                            LogUtil.d("can not find notification so add (default true)");
                            this.mNotificationAppListEntity.setChecked(true);
                            if (!this.mNotificationAppListEntity.getAppName().equals(EnvironmentCompat.MEDIA_UNKNOWN)) {
                                NotificationDbManager.getInstance(this.mNotificationListener).insertNotification(this.mNotificationAppListEntity);
                            }
                        }
                        this.mNotificationContents.add(this.mNotificationEntity.getSbn().getNotification().extras.getCharSequence(NotificationCompat.EXTRA_TEXT));
                        removeCallbacks(this.contentResetRunnable);
                        postDelayed(this.contentResetRunnable, 1000L);
                        this.mNotificationListener.sendBroadcast(new Intent(Constant.ACTION_UPDATE_NOTIFICATION_LIST_PAGE));
                        return;
                    }
                    LogUtil.d("content或着tittle为空,不推送");
                    return;
                }
            case 2:
                NotificationEntity notificationEntity = this.mNotificationEntity;
                if (notificationEntity == null || notificationEntity.getSbn() == null) {
                    return;
                }
                logSbn("removed_cancel:", this.mNotificationEntity.getSbn());
                return;
            case 3:
                LogUtil.d("MSG_STARTUP");
                return;
            case 4:
                LogUtil.d("onUpdate--order");
                return;
            case 5:
                LogUtil.d("MSG_DISMISS");
                return;
            case 6:
                LogUtil.d("MSG_LAUNCH");
                return;
            case 7:
                LogUtil.d("trying to snooze");
                return;
            default:
                return;
        }
    }

    private void logSbn(String str, StatusBarNotification statusBarNotification) {
        LogUtil.d(str + statusBarNotification.getId() + " |key:(" + statusBarNotification.getKey() + ")  |package name:" + statusBarNotification.getPackageName() + " |app name:" + W100Utils.getAppName(this.mNotificationListener, statusBarNotification.getPackageName()) + " |title:" + ((Object) statusBarNotification.getNotification().extras.getCharSequence(NotificationCompat.EXTRA_TITLE)) + " |category:" + statusBarNotification.getNotification().category + " |content:" + ((Object) statusBarNotification.getNotification().extras.getCharSequence(NotificationCompat.EXTRA_TEXT)) + " |tag:" + statusBarNotification.getTag());
    }
}
