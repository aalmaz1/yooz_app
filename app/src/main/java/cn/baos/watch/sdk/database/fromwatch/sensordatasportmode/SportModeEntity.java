package cn.baos.watch.sdk.database.fromwatch.sensordatasportmode;

import cn.baos.watch.w100.messages.Sensor_data_sport_mode;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SportModeEntity implements Serializable {
    public String mac;
    private int id = 0;
    private long userId = 0;
    private String devId = "";
    private Sensor_data_sport_mode sensor_data_sport_mode = new Sensor_data_sport_mode();

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

    public Sensor_data_sport_mode getSensor_data_sport_mode() {
        return this.sensor_data_sport_mode;
    }

    public void setSensor_data_sport_mode(Sensor_data_sport_mode sensor_data_sport_mode) {
        this.sensor_data_sport_mode = sensor_data_sport_mode;
    }
}
