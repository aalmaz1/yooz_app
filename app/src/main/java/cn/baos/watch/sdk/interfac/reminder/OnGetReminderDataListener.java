package cn.baos.watch.sdk.interfac.reminder;

import cn.baos.watch.sdk.entitiy.ReminderListEntity;

/* JADX INFO: loaded from: classes.dex */
public interface OnGetReminderDataListener {
    void onGetReminderData(ReminderListEntity reminderListEntity);

    void onGetReminderDateFinish();
}
