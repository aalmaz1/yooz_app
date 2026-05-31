package cn.baos.watch.sdk.database.fromwatch.sensordatadailyactive;

import cn.baos.watch.w100.messages.Sensor_data_daily_active_sum;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class DailyActiveEntity implements Serializable {
    public String mac;
    private int id = 0;
    private long userId = 0;
    private String devId = "";
    private Sensor_data_daily_active_sum sensor_data_daily_active_sum = new Sensor_data_daily_active_sum();

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long j) {
        this.userId = j;
    }

    public String getDevId() {
        return this.devId;
    }

    public void setDevId(String str) {
        this.devId = str;
    }

    public Sensor_data_daily_active_sum getSensor_data_daily_active_sum() {
        return this.sensor_data_daily_active_sum;
    }

    public void setSensor_data_daily_active_sum(Sensor_data_daily_active_sum sensor_data_daily_active_sum) {
        this.sensor_data_daily_active_sum = sensor_data_daily_active_sum;
    }

    public String toString() {
        return "DailyActiveEntity{id=" + this.id + ", userId=" + this.userId + ", devId='" + this.devId + "', sensor_data_daily_active_sum=" + this.sensor_data_daily_active_sum + '}';
    }
}
