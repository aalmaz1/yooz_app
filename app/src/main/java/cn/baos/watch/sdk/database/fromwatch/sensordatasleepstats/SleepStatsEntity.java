package cn.baos.watch.sdk.database.fromwatch.sensordatasleepstats;

import cn.baos.watch.w100.messages.Sensor_data_sleep_stats;
import java.io.Serializable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SleepStatsEntity implements Serializable {
    public String mac;
    private List<SleepStatsEntity> sleepStatusArr;
    private int id = 0;
    private long userId = 0;
    private String devId = "";
    private Sensor_data_sleep_stats sensor_data_sleep_stats = new Sensor_data_sleep_stats();

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

    public Sensor_data_sleep_stats getSensor_data_sleep_stats() {
        return this.sensor_data_sleep_stats;
    }

    public void setSensor_data_sleep_stats(Sensor_data_sleep_stats sensor_data_sleep_stats) {
        this.sensor_data_sleep_stats = sensor_data_sleep_stats;
    }

    public List<SleepStatsEntity> getSleepStatusArr() {
        return this.sleepStatusArr;
    }

    public void setSleepStatusArr(List<SleepStatsEntity> list) {
        this.sleepStatusArr = list;
    }
}
