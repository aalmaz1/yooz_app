package cn.baos.watch.sdk.old.callcontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import cn.baos.watch.sdk.entitiy.CallInfoEntity;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.old.MainHandler;
import cn.baos.watch.sdk.utils.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class CallStateReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String contactNameFromPhoneBook;
        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            LogUtil.d("phone 去电");
            return;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        LogUtil.d("phone 来电状态:" + telephonyManager.getCallState());
        int callState = telephonyManager.getCallState();
        if (callState == 0) {
            MainHandler.getInstance().obtainMessage(103).sendToTarget();
            return;
        }
        if (callState != 1) {
            if (callState != 2) {
                return;
            }
            MainHandler.getInstance().obtainMessage(102).sendToTarget();
            return;
        }
        String stringExtra = intent.getStringExtra("incoming_number");
        if (!stringExtra.isEmpty() && !stringExtra.equals("null")) {
            try {
                contactNameFromPhoneBook = MessageManager.getInstance().getContactNameFromPhoneBook(context, stringExtra);
            } catch (Exception unused) {
                LogUtil.d("需要通讯录权限才能查询手机号对应名称");
                contactNameFromPhoneBook = "未知";
            }
            LogUtil.d("phone 来电电话:" + stringExtra + " 来电人:" + contactNameFromPhoneBook);
            MainHandler.getInstance().obtainMessage(101, new CallInfoEntity(stringExtra, contactNameFromPhoneBook)).sendToTarget();
            return;
        }
        LogUtil.d("phone 来电电话为空:" + stringExtra);
    }
}
