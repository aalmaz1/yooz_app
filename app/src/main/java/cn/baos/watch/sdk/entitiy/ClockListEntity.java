package cn.baos.watch.sdk.entitiy;

import cn.baos.watch.sdk.util.TimeUtils;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class ClockListEntity implements Serializable, Comparable<ClockListEntity> {
    private int crudState;
    private int from;
    private int id;
    private boolean isChecked;
    private boolean isSynchronizeNetwork;
    private int position;
    private String time;
    private String timeSlot;
    private String timeWhen;
    private String triggerTime;

    public ClockListEntity() {
        this.isChecked = true;
        this.isSynchronizeNetwork = false;
    }

    public ClockListEntity(String str, String str2, String str3, boolean z) {
        this.isSynchronizeNetwork = false;
        this.time = str;
        this.timeSlot = str2;
        this.timeWhen = str3;
        this.isChecked = z;
    }

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

    public String getTimeSlot() {
        return this.timeSlot;
    }

    public void setTimeSlot(String str) {
        this.timeSlot = str;
    }

    public String getTimeWhen() {
        return this.timeWhen;
    }

    public void setTimeWhen(String str) {
        this.timeWhen = str;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean z) {
        this.isChecked = z;
    }

    public boolean isSynchronizeNetwork() {
        return this.isSynchronizeNetwork;
    }

    public void setSynchronizeNetwork(boolean z) {
        this.isSynchronizeNetwork = z;
    }

    public String getTriggerTime() {
        return this.triggerTime;
    }

    public void setTriggerTime(String str) {
        this.triggerTime = str;
    }

    public int getFrom() {
        return this.from;
    }

    public void setFrom(int i) {
        this.from = i;
    }

    public String toString() {
        return "ClockListEntity{crudState=" + this.crudState + ", from=" + this.from + ", id=" + this.id + ", position=" + this.position + ", time='" + this.time + "', timeSlot='" + this.timeSlot + "', timeWhen='" + this.timeWhen + "', isChecked=" + this.isChecked + ", isSynchronizeNetwork=" + this.isSynchronizeNetwork + '}';
    }

    @Override // java.lang.Comparable
    public int compareTo(ClockListEntity clockListEntity) {
        return (int) (TimeUtils.getClockManageAlarmTimeStamp(this) - TimeUtils.getClockManageAlarmTimeStamp(clockListEntity));
    }
}
