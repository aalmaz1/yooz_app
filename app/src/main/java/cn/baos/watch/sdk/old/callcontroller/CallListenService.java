package cn.baos.watch.sdk.old.callcontroller;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import cn.baos.watch.sdk.entitiy.CallInfoEntity;
import cn.baos.watch.sdk.entitiy.NotificationAppListEntity;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.manager.notification.db.NotificationDbManager;
import cn.baos.watch.sdk.old.MainHandler;
import cn.baos.watch.sdk.utils.LogUtil;
import cn.baos.watch.sdk.utils.SharePreferenceUtils;
import cn.baos.watch.sdk.utils.W100Utils;

/* JADX INFO: loaded from: classes.dex */
public class CallListenService extends Service {
    public static boolean phoneState = false;
    private MyPhoneStateListener mPhoneStateListener;
    private TelephonyManager telephonyManager;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        return 2;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        LogUtil.d("phone 来电服务启动");
        try {
            getIncomingCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("手机来电监听服务被杀死");
        getIncomingCallCancel();
    }

    private void getIncomingCallCancel() {
        this.telephonyManager.listen(this.mPhoneStateListener, 0);
    }

    private void getIncomingCall() {
        this.telephonyManager = (TelephonyManager) getSystemService("phone");
        MyPhoneStateListener myPhoneStateListener = new MyPhoneStateListener(this);
        this.mPhoneStateListener = myPhoneStateListener;
        this.telephonyManager.listen(myPhoneStateListener, 32);
    }

    class MyPhoneStateListener extends PhoneStateListener {
        private Context mContext;

        public MyPhoneStateListener(Context context) {
            this.mContext = context;
        }

        @Override // android.telephony.PhoneStateListener
        public void onCallStateChanged(int i, String str) {
            String contactNameFromPhoneBook;
            super.onCallStateChanged(i, str);
            if (!SharePreferenceUtils.queryBooleanByKeySetBoolean(this.mContext, "SWITCH_CALL_PHONE_NOTIFICATION", true)) {
                LogUtil.d("phone 来电提醒功能已被用户关闭");
                return;
            }
            LogUtil.d("phone 服务监听状态:" + i + " 电话:" + str + "  -->1为来电/0为挂断");
            if (!NotificationDbManager.getInstance(this.mContext).queryCheckStateLightDb("notificationManageKey")) {
                LogUtil.d("phone 通知总开关被关闭了，不通知");
                return;
            }
            LogUtil.d("phone 通知总开关被打开，通知");
            NotificationAppListEntity notificationAppListEntity = new NotificationAppListEntity("com.android.incallui", W100Utils.getAppName(this.mContext, "com.android.incallui"));
            LogUtil.d("phone notificationAppListEntity:" + W100Utils.toString(notificationAppListEntity));
            NotificationAppListEntity notificationAppListEntityQueryNotification = NotificationDbManager.getInstance(this.mContext).queryNotification(notificationAppListEntity);
            if (notificationAppListEntityQueryNotification != null && notificationAppListEntityQueryNotification.isChecked()) {
                LogUtil.d("phone notification isCheck is true,通知:" + W100Utils.toString(notificationAppListEntityQueryNotification));
                if (i == 0) {
                    MainHandler.getInstance().obtainMessage(103).sendToTarget();
                    CallStateManager.getInstance().quietCallOff();
                    return;
                }
                if (i != 1) {
                    if (i != 2) {
                        return;
                    }
                    MainHandler.getInstance().obtainMessage(102).sendToTarget();
                    return;
                }
                CallListenService.phoneState = true;
                if (str != null && !str.isEmpty() && !str.equals("null")) {
                    try {
                        contactNameFromPhoneBook = MessageManager.getInstance().getContactNameFromPhoneBook(CallListenService.this, str);
                    } catch (Exception unused) {
                        LogUtil.d("需要通讯录权限才能查询手机号对应名称");
                        contactNameFromPhoneBook = "未知";
                    }
                    if (contactNameFromPhoneBook.isEmpty()) {
                        contactNameFromPhoneBook = str;
                    }
                    LogUtil.d("phone 来电电话:" + str + " 来电人:" + contactNameFromPhoneBook);
                    MainHandler.getInstance().obtainMessage(101, new CallInfoEntity(str, contactNameFromPhoneBook)).sendToTarget();
                    return;
                }
                LogUtil.d("phone 来电电话为空:" + str);
                return;
            }
            LogUtil.d("phone notification isCheck is false or null,不通知:" + W100Utils.toString(notificationAppListEntityQueryNotification));
        }
    }
}
