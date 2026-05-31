package cn.baos.watch.sdk.entitiy;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.time.TimeZones;

/* JADX INFO: loaded from: classes.dex */
public class WorldEntity {
    public String cityCn;
    public String cityEn;
    public String continentsCn;
    public String continentsEn;
    public String contryCn;
    public String contryEn;

    @SerializedName(TimeZones.GMT_ID)
    public String gMT;
    public String name;
    public int reserve;
    public Integer secondsFromGMT;
    public int timezone;
}
