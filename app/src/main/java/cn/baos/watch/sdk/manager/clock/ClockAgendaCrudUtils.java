package cn.baos.watch.sdk.manager.clock;

import cn.baos.watch.sdk.entitiy.ClockListEntity;
import cn.baos.watch.sdk.entitiy.Constant;
import cn.baos.watch.sdk.entitiy.NlpEntity;
import cn.baos.watch.sdk.entitiy.ReminderListEntity;
import cn.baos.watch.sdk.interfac.clock.OnCrudClockDataListener;
import cn.baos.watch.sdk.interfac.reminder.OnCrudReminderDataListener;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.TimeUtils;
import cn.baos.watch.sdk.util.W100Utils;
import cn.baos.watch.w100.messages.AddAlarm;
import cn.baos.watch.w100.messages.AddReminder;
import cn.baos.watch.w100.messages.DeleteAlarm;
import cn.baos.watch.w100.messages.DeleteReminder;
import cn.baos.watch.w100.messages.MessageBase;
import cn.baos.watch.w100.messages.ModifyAlarm;
import cn.baos.watch.w100.messages.ModifyReminder;

/* JADX INFO: loaded from: classes.dex */
public class ClockAgendaCrudUtils {
    private static ClockAgendaCrudUtils instance;

    public static ClockAgendaCrudUtils getInstance() {
        if (instance == null) {
            synchronized (ClockAgendaCrudUtils.class) {
                if (instance == null) {
                    instance = new ClockAgendaCrudUtils();
                }
            }
        }
        return instance;
    }

    public void setAlarmToWatchByPhone(ClockListEntity clockListEntity, OnCrudClockDataListener onCrudClockDataListener) {
        AddAlarm addAlarm = new AddAlarm();
        addAlarm.verison = 0;
        NlpEntity.CircleModel cycleModel = TimeUtils.getCycleModel(clockListEntity.getTimeWhen(), TimeUtils.getClockManageAlarmTimeStamp(clockListEntity));
        addAlarm.circle_type = cycleModel.getCircleType();
        addAlarm.circle_extra = cycleModel.getCircleExtra();
        addAlarm.mask_wday = cycleModel.getDayOfWeek();
        addAlarm.mask_mday = cycleModel.getDayOfMouth();
        addAlarm.mask_mweek = cycleModel.getWeekOfMouth();
        addAlarm.mask_ymonth = cycleModel.getMouthOfYear();
        addAlarm.time_zone = TimeUtils.getTimeZoneChange();
        addAlarm.start_time = (int) (TimeUtils.getClockManageAlarmTimeStamp(clockListEntity) / 1000);
        addAlarm.reminder = "闹钟";
        addAlarm.event = "闹钟";
        MessageManager.makeMessageToSend(addAlarm, String.valueOf((System.currentTimeMillis() / 1000) + 1));
        LogUtil.d("手机闹钟管理发送alarm:" + W100Utils.toString(addAlarm));
        MessageManager.getInstance().setCrudClockListener(0, onCrudClockDataListener);
        MessageManager.getInstance().sendMessage((MessageBase) addAlarm);
    }

    public void deleteAlarmToWatchByPhone(ClockListEntity clockListEntity, OnCrudClockDataListener onCrudClockDataListener) {
        DeleteAlarm deleteAlarm = new DeleteAlarm();
        deleteAlarm.id = clockListEntity.getId();
        MessageManager.makeMessageToSend(deleteAlarm, String.valueOf(System.currentTimeMillis() / 1000));
        LogUtil.d("手机闹钟管理删除alarm:" + W100Utils.toString(deleteAlarm));
        MessageManager.getInstance().setCrudClockListener(2, onCrudClockDataListener);
        MessageManager.getInstance().sendMessage((MessageBase) deleteAlarm);
    }

    public void updateAlarmToWatchByPhone(ClockListEntity clockListEntity, OnCrudClockDataListener onCrudClockDataListener) {
        ModifyAlarm modifyAlarm = new ModifyAlarm();
        modifyAlarm.verison = Constant.alarm_version;
        modifyAlarm.id = clockListEntity.getId();
        NlpEntity.CircleModel cycleModel = TimeUtils.getCycleModel(clockListEntity.getTimeWhen(), TimeUtils.getClockManageAlarmTimeStamp(clockListEntity));
        modifyAlarm.circle_type = cycleModel.getCircleType();
        modifyAlarm.circle_extra = cycleModel.getCircleExtra();
        modifyAlarm.mask_wday = cycleModel.getDayOfWeek();
        modifyAlarm.mask_mday = cycleModel.getDayOfMouth();
        modifyAlarm.mask_mweek = cycleModel.getWeekOfMouth();
        modifyAlarm.mask_ymonth = cycleModel.getMouthOfYear();
        modifyAlarm.time_zone = TimeUtils.getTimeZoneChange();
        modifyAlarm.start_time = (int) (TimeUtils.getClockManageAlarmTimeStamp(clockListEntity) / 1000);
        modifyAlarm.reminder = "闹钟";
        modifyAlarm.event = "闹钟";
        if (clockListEntity.isChecked()) {
            modifyAlarm.status = Constant.checkOpen;
        } else {
            modifyAlarm.status = Constant.checkClose;
        }
        MessageManager.makeMessageToSend(modifyAlarm, String.valueOf((System.currentTimeMillis() / 1000) + 1));
        LogUtil.d("修改闹钟管理alarm:" + W100Utils.toString(modifyAlarm));
        MessageManager.getInstance().setCrudClockListener(1, onCrudClockDataListener);
        MessageManager.getInstance().sendMessage((MessageBase) modifyAlarm);
    }

    public void addReminderToWatchByPhone(ReminderListEntity reminderListEntity, OnCrudReminderDataListener onCrudReminderDataListener) {
        AddReminder addReminder = new AddReminder();
        addReminder.verison = Constant.alarm_version;
        addReminder.id = reminderListEntity.getId();
        NlpEntity.CircleModel cycleModel = TimeUtils.getCycleModel(reminderListEntity.getCircleType(), TimeUtils.getReminderManageAlarmTimeStamp(reminderListEntity));
        addReminder.circle_type = cycleModel.getCircleType();
        addReminder.circle_extra = cycleModel.getCircleExtra();
        addReminder.mask_wday = cycleModel.getDayOfWeek();
        addReminder.mask_mday = cycleModel.getDayOfMouth();
        addReminder.mask_mweek = cycleModel.getWeekOfMouth();
        addReminder.mask_ymonth = cycleModel.getMouthOfYear();
        addReminder.time_zone = TimeUtils.getTimeZoneChange();
        addReminder.start_time = (int) (TimeUtils.getReminderManageAlarmTimeStamp(reminderListEntity) / 1000);
        addReminder.reminder = reminderListEntity.getReminder();
        addReminder.event = reminderListEntity.getEvent();
        MessageManager.makeMessageToSend(addReminder, String.valueOf((System.currentTimeMillis() / 1000) + 1));
        LogUtil.d("日程管理添加提醒reminder:" + W100Utils.toString(addReminder));
        MessageManager.getInstance().setCrudReminderListener(0, onCrudReminderDataListener);
        MessageManager.getInstance().sendMessage((MessageBase) addReminder);
    }

    public void updateReminderToWatchByPhone(ReminderListEntity reminderListEntity, OnCrudReminderDataListener onCrudReminderDataListener) {
        ModifyReminder modifyReminder = new ModifyReminder();
        modifyReminder.verison = Constant.alarm_version;
        modifyReminder.id = reminderListEntity.getId();
        NlpEntity.CircleModel cycleModel = TimeUtils.getCycleModel(reminderListEntity.getCircleType(), TimeUtils.getReminderManageAlarmTimeStamp(reminderListEntity));
        modifyReminder.circle_type = cycleModel.getCircleType();
        modifyReminder.circle_extra = cycleModel.getCircleExtra();
        modifyReminder.mask_wday = cycleModel.getDayOfWeek();
        modifyReminder.mask_mday = cycleModel.getDayOfMouth();
        modifyReminder.mask_mweek = cycleModel.getWeekOfMouth();
        modifyReminder.mask_ymonth = cycleModel.getMouthOfYear();
        modifyReminder.time_zone = TimeUtils.getTimeZoneChange();
        modifyReminder.start_time = (int) (TimeUtils.getReminderManageAlarmTimeStamp(reminderListEntity) / 1000);
        modifyReminder.reminder = reminderListEntity.getReminder();
        modifyReminder.event = reminderListEntity.getEvent();
        if (reminderListEntity.isChecked()) {
            modifyReminder.status = Constant.checkOpen;
        } else {
            modifyReminder.status = Constant.checkClose;
        }
        MessageManager.makeMessageToSend(modifyReminder, String.valueOf((System.currentTimeMillis() / 1000) + 1));
        LogUtil.d("日程管理修改提醒reminder:" + W100Utils.toString(modifyReminder));
        MessageManager.getInstance().setCrudReminderListener(1, onCrudReminderDataListener);
        MessageManager.getInstance().sendMessage((MessageBase) modifyReminder);
    }

    public void deleteReminderToWatchByPhone(ReminderListEntity reminderListEntity, OnCrudReminderDataListener onCrudReminderDataListener) {
        DeleteReminder deleteReminder = new DeleteReminder();
        deleteReminder.id = reminderListEntity.getId();
        MessageManager.makeMessageToSend(deleteReminder, String.valueOf(System.currentTimeMillis() / 1000));
        LogUtil.d("手机日程管理删除reminder:" + W100Utils.toString(deleteReminder));
        MessageManager.getInstance().setCrudReminderListener(2, onCrudReminderDataListener);
        MessageManager.getInstance().sendMessage((MessageBase) deleteReminder);
    }
}
