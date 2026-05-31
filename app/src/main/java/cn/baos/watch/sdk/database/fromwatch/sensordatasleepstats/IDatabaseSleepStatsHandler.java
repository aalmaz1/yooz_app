package cn.baos.watch.sdk.database.fromwatch.sensordatasleepstats;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IDatabaseSleepStatsHandler {
    void close();

    void createDatabase();

    void delete(SleepStatsEntity sleepStatsEntity);

    void insert(SleepStatsEntity sleepStatsEntity);

    void open();

    SleepStatsEntity query(int i);

    ArrayList<SleepStatsEntity> queryArrayBetween(int i, int i2);

    void update(SleepStatsEntity sleepStatsEntity);
}
