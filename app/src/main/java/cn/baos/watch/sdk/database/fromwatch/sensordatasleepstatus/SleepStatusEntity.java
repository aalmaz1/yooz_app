package cn.baos.watch.sdk.database.fromwatch.sensordatasleepstatus;

import cn.baos.watch.w100.messages.Sensor_data_sleep_status;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SleepStatusEntity implements Serializable {
    public String mac;
    private int id = 0;
    private long userId = 0;
    private String devId = "";
    private Sensor_data_sleep_status sensor_data_sleep_status = new Sensor_data_sleep_status();

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

    public Sensor_data_sleep_status getSensor_data_sleep_status() {
        return this.sensor_data_sleep_status;
    }

    public void setSensor_data_sleep_status(Sensor_data_sleep_status sensor_data_sleep_status) {
        this.sensor_data_sleep_status = sensor_data_sleep_status;
    }
}
