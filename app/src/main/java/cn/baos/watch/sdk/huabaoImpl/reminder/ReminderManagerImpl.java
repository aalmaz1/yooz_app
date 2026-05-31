package cn.baos.watch.sdk.huabaoImpl.reminder;

import cn.baos.watch.sdk.entitiy.ReminderListEntity;
import cn.baos.watch.sdk.interfac.reminder.IReminderManager;
import cn.baos.watch.sdk.interfac.reminder.OnCrudReminderDataListener;
import cn.baos.watch.sdk.interfac.reminder.OnGetReminderDataListener;
import cn.baos.watch.sdk.manager.clock.ClockAgendaCrudUtils;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.w100.messages.QueryReminder;

/* JADX INFO: loaded from: classes.dex */
public class ReminderManagerImpl implements IReminderManager {
    private static ReminderManagerImpl instance;

    public static ReminderManagerImpl getInstance() {
        if (instance == null) {
            synchronized (ReminderManagerImpl.class) {
                if (instance == null) {
                    instance = new ReminderManagerImpl();
                }
            }
        }
        return instance;
    }

    @Override // cn.baos.watch.sdk.interfac.reminder.IReminderManager
    public void requestReminderListToWatch(QueryReminder queryReminder, final OnGetReminderDataListener onGetReminderDataListener) {
        MessageManager.getInstance().requestReminderListToWatch(queryReminder, new OnGetReminderDataListener() { // from class: cn.baos.watch.sdk.huabaoImpl.reminder.ReminderManagerImpl.1
            @Override // cn.baos.watch.sdk.interfac.reminder.OnGetReminderDataListener
            public void onGetReminderData(ReminderListEntity reminderListEntity) {
                LogUtil.d(String.format("拉取提醒:%s", reminderListEntity.toString()));
                OnGetReminderDataListener onGetReminderDataListener2 = onGetReminderDataListener;
                if (onGetReminderDataListener2 != null) {
                    onGetReminderDataListener2.onGetReminderData(reminderListEntity);
                }
            }

            @Override // cn.baos.watch.sdk.interfac.reminder.OnGetReminderDataListener
            public void onGetReminderDateFinish() {
                LogUtil.d("拉取提醒完成:结束");
                OnGetReminderDataListener onGetReminderDataListener2 = onGetReminderDataListener;
                if (onGetReminderDataListener2 != null) {
                    onGetReminderDataListener2.onGetReminderDateFinish();
                }
            }
        });
    }

    @Override // cn.baos.watch.sdk.interfac.reminder.IReminderManager
    public void addReminder(final ReminderListEntity reminderListEntity, final OnCrudReminderDataListener onCrudReminderDataListener) {
        reminderListEntity.setCrudState(0);
        ClockAgendaCrudUtils.getInstance().addReminderToWatchByPhone(reminderListEntity, new OnCrudReminderDataListener() { // from class: cn.baos.watch.sdk.huabaoImpl.reminder.ReminderManagerImpl.2
            @Override // cn.baos.watch.sdk.interfac.reminder.OnCrudReminderDataListener
            public void onCrudReminderSuccess(int i, int i2) {
                reminderListEntity.setId(i2);
                LogUtil.d("提醒管理 addReminder success:" + reminderListEntity.toString());
                OnCrudReminderDataListener onCrudReminderDataListener2 = onCrudReminderDataListener;
                if (onCrudReminderDataListener2 != null) {
                    onCrudReminderDataListener2.onCrudReminderSuccess(i, i2);
                }
            }

            @Override // cn.baos.watch.sdk.interfac.reminder.OnCrudReminderDataListener
            public void onCrudReminderFail(int i) {
                LogUtil.d("提醒管理 addReminder fail:" + reminderListEntity.toString());
                OnCrudReminderDataListener onCrudReminderDataListener2 = onCrudReminderDataListener;
                if (onCrudReminderDataListener2 != null) {
                    onCrudReminderDataListener2.onCrudReminderFail(i);
                }
            }
        });
    }

    @Override // cn.baos.watch.sdk.interfac.reminder.IReminderManager
    public void deleteReminder(final ReminderListEntity reminderListEntity, final OnCrudReminderDataListener onCrudReminderDataListener) {
        reminderListEntity.setCrudState(1);
        ClockAgendaCrudUtils.getInstance().deleteReminderToWatchByPhone(reminderListEntity, new OnCrudReminderDataListener() { // from class: cn.baos.watch.sdk.huabaoImpl.reminder.ReminderManagerImpl.3
            @Override // cn.baos.watch.sdk.interfac.reminder.OnCrudReminderDataListener
            public void onCrudReminderSuccess(int i, int i2) {
                reminderListEntity.setId(i2);
                LogUtil.d("提醒管理 deleteReminder success:" + reminderListEntity.toString());
                OnCrudReminderDataListener onCrudReminderDataListener2 = onCrudReminderDataListener;
                if (onCrudReminderDataListener2 != null) {
                    onCrudReminderDataListener2.onCrudReminderSuccess(i, i2);
                }
            }

            @Override // cn.baos.watch.sdk.interfac.reminder.OnCrudReminderDataListener
            public void onCrudReminderFail(int i) {
                LogUtil.d("提醒管理 deleteReminder fail:" + reminderListEntity.toString());
                OnCrudReminderDataListener onCrudReminderDataListener2 = onCrudReminderDataListener;
                if (onCrudReminderDataListener2 != null) {
                    onCrudReminderDataListener2.onCrudReminderFail(i);
                }
            }
        });
    }

    @Override // cn.baos.watch.sdk.interfac.reminder.IReminderManager
    public void updateReminder(final ReminderListEntity reminderListEntity, final OnCrudReminderDataListener onCrudReminderDataListener) {
        reminderListEntity.setCrudState(2);
        ClockAgendaCrudUtils.getInstance().updateReminderToWatchByPhone(reminderListEntity, new OnCrudReminderDataListener() { // from class: cn.baos.watch.sdk.huabaoImpl.reminder.ReminderManagerImpl.4
            @Override // cn.baos.watch.sdk.interfac.reminder.OnCrudReminderDataListener
            public void onCrudReminderSuccess(int i, int i2) {
                reminderListEntity.setId(i2);
                LogUtil.d("提醒管理 updateReminder success:" + reminderListEntity.toString());
                OnCrudReminderDataListener onCrudReminderDataListener2 = onCrudReminderDataListener;
                if (onCrudReminderDataListener2 != null) {
                    onCrudReminderDataListener2.onCrudReminderSuccess(i, i2);
                }
            }

            @Override // cn.baos.watch.sdk.interfac.reminder.OnCrudReminderDataListener
            public void onCrudReminderFail(int i) {
                LogUtil.d("提醒管理 updateReminder fail:" + reminderListEntity.toString());
                OnCrudReminderDataListener onCrudReminderDataListener2 = onCrudReminderDataListener;
                if (onCrudReminderDataListener2 != null) {
                    onCrudReminderDataListener2.onCrudReminderFail(i);
                }
            }
        });
    }
}
