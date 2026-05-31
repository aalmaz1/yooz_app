package cn.baos.watch.sdk.entitiy;

import cn.baos.watch.sdk.util.TimeUtils;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class ReminderListEntity implements Serializable, Comparable<ReminderListEntity> {
    private String circleType;
    private int crudState;
    private String event;
    private int id;
    private boolean isChecked = true;
    private int position;
    private String reminder;
    private String time;
    private String triggerTime;

    public int getCrudState() {
        return this.crudState;
    }

    public void setCrudState(int i) {
        this.crudState = i;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String str) {
        this.time = str;
    }

    public String getEvent() {
        return this.event;
    }

    public void setEvent(String str) {
        this.event = str;
    }

    public String getReminder() {
        return this.reminder;
    }

    public void setReminder(String str) {
        this.reminder = str;
    }

    public String getCircleType() {
        return this.circleType;
    }

    public void setCircleType(String str) {
        this.circleType = str;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean z) {
        this.isChecked = z;
    }

    public String getTriggerTime() {
        return this.triggerTime;
    }

    public void setTriggerTime(String str) {
        this.triggerTime = str;
    }

    public String toString() {
        return "ReminderListEntity{crudState=" + this.crudState + ", id=" + this.id + ", position=" + this.position + ", event='" + this.event + "', reminder='" + this.reminder + "', time='" + this.time + "', triggerTime='" + this.triggerTime + "', circleType='" + this.circleType + "', isChecked=" + this.isChecked + '}';
    }

    @Override // java.lang.Comparable
    public int compareTo(ReminderListEntity reminderListEntity) {
        return (int) (TimeUtils.getReminderManageAlarmTimeStamp(this) - TimeUtils.getReminderManageAlarmTimeStamp(reminderListEntity));
    }
}
