package cn.baos.watch.sdk.interfac.reminder;

import cn.baos.watch.sdk.entitiy.ReminderListEntity;
import cn.baos.watch.w100.messages.QueryReminder;

/* JADX INFO: loaded from: classes.dex */
public interface IReminderManager {
    void addReminder(ReminderListEntity reminderListEntity, OnCrudReminderDataListener onCrudReminderDataListener);

    void deleteReminder(ReminderListEntity reminderListEntity, OnCrudReminderDataListener onCrudReminderDataListener);

    void requestReminderListToWatch(QueryReminder queryReminder, OnGetReminderDataListener onGetReminderDataListener);

    void updateReminder(ReminderListEntity reminderListEntity, OnCrudReminderDataListener onCrudReminderDataListener);
}
