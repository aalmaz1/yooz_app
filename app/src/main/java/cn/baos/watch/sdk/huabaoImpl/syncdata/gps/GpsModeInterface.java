package cn.baos.watch.sdk.huabaoImpl.syncdata.gps;

import cn.baos.watch.sdk.database.gps.GpslocEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface GpsModeInterface {
    void deleteGpsModeInInterval(int i, int i2);

    ArrayList<GpslocEntity> queryGpsModeInInterval(int i, int i2);

    ArrayList<GpslocEntity> queryGpsModeToday(int i);

    void saveGpsModeEntitiesToDb(GpslocEntity gpslocEntity);
}
