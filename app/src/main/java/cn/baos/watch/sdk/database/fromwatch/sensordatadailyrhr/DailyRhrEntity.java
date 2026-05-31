package cn.baos.watch.sdk.database.fromwatch.sensordatadailyrhr;

import cn.baos.watch.w100.messages.Sensor_data_daily_rhr;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class DailyRhrEntity implements Serializable {
    public String mac;
    private int id = 0;
    private long userId = 0;
    private String devId = "";
    private Sensor_data_daily_rhr sensor_data_daily_rhr = new Sensor_data_daily_rhr();

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

    public Sensor_data_daily_rhr getSensor_data_daily_rhr() {
        return this.sensor_data_daily_rhr;
    }

    public void setSensor_data_daily_rhr(Sensor_data_daily_rhr sensor_data_daily_rhr) {
        this.sensor_data_daily_rhr = sensor_data_daily_rhr;
    }
}
