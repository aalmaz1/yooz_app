package cn.baos.watch.sdk.database.fromwatch.sensordatadailyspo;

import cn.baos.watch.w100.messages.Sensor_data_daily_spo;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class DailySpoEntity implements Serializable {
    public String mac;
    private int id = 0;
    private long userId = 0;
    private String devId = "";
    private Sensor_data_daily_spo sensor_data_daily_spo = new Sensor_data_daily_spo();

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

    public Sensor_data_daily_spo getSensor_data_daily_spo() {
        return this.sensor_data_daily_spo;
    }

    public void setSensor_data_daily_spo(Sensor_data_daily_spo sensor_data_daily_spo) {
        this.sensor_data_daily_spo = sensor_data_daily_spo;
    }
}
