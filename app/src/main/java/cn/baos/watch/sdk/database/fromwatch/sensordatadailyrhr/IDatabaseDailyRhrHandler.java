package cn.baos.watch.sdk.database.fromwatch.sensordatadailyrhr;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IDatabaseDailyRhrHandler {
    void close();

    void createDatabase();

    void delete(DailyRhrEntity dailyRhrEntity);

    void insert(DailyRhrEntity dailyRhrEntity);

    void open();

    ArrayList<DailyRhrEntity> queryArrayBetween(int i, int i2);

    void update(DailyRhrEntity dailyRhrEntity);
}
