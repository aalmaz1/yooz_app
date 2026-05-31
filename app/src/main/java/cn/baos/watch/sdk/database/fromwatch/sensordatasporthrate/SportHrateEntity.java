package cn.baos.watch.sdk.database.fromwatch.sensordatasporthrate;

import cn.baos.watch.w100.messages.Sensor_data_sport_hrate;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SportHrateEntity implements Serializable {
    public String mac;
    private int id = 0;
    private long userId = 0;
    private String devId = "";
    private Sensor_data_sport_hrate sensor_data_sport_hrate = new Sensor_data_sport_hrate();

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

    public Sensor_data_sport_hrate getSensor_data_sport_hrate() {
        return this.sensor_data_sport_hrate;
    }

    public void setSensor_data_sport_hrate(Sensor_data_sport_hrate sensor_data_sport_hrate) {
        this.sensor_data_sport_hrate = sensor_data_sport_hrate;
    }
}
