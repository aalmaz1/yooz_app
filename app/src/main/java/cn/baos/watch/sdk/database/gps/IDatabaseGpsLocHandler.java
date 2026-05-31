package cn.baos.watch.sdk.database.gps;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IDatabaseGpsLocHandler {
    void close();

    void createDatabase();

    void delete(GpslocEntity gpslocEntity);

    void insert(GpslocEntity gpslocEntity);

    void open();

    GpslocEntity query(int i);

    ArrayList<GpslocEntity> queryArrayBetween(int i, int i2);

    void update(GpslocEntity gpslocEntity);
}
