package cn.baos.watch.sdk.database.fromwatch.sportrecord;

import cn.baos.watch.w100.messages.Sport_record;
import com.google.gson.Gson;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SportRecordFromWatchEntity implements Serializable {
    public String mac;
    private int id = 0;
    private long userId = 0;
    private String devId = "";
    private Sport_record sport_record = new Sport_record();

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

    public Sport_record getSport_record() {
        return this.sport_record;
    }

    public void setSport_record(Sport_record sport_record) {
        this.sport_record = sport_record;
    }

    public String toString() {
        return "SportRecordFromWatchEntity{id=" + this.id + ", userId=" + this.userId + ", devId='" + this.devId + "', sport_record=" + new Gson().toJson(this.sport_record) + '}';
    }
}
