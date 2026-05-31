package cn.baos.watch.sdk.database.fromwatch.sensordatasleepstatus;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IDatabaseSleepStatusHandler {
    void close();

    void createDatabase();

    void delete(SleepStatusEntity sleepStatusEntity);

    void insert(SleepStatusEntity sleepStatusEntity);

    void open();

    SleepStatusEntity query(int i);

    ArrayList<SleepStatusEntity> queryArrayBetween(int i, int i2);

    void update(SleepStatusEntity sleepStatusEntity);
}
