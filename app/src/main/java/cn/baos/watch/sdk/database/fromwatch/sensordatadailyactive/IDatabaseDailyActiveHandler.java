package cn.baos.watch.sdk.database.fromwatch.sensordatadailyactive;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IDatabaseDailyActiveHandler {
    void close();

    void createDatabase();

    void delete(DailyActiveEntity dailyActiveEntity);

    void insert(DailyActiveEntity dailyActiveEntity);

    void open();

    ArrayList<DailyActiveEntity> queryArrayBetween(int i, int i2);

    void update(DailyActiveEntity dailyActiveEntity);
}
