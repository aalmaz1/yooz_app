package cn.baos.watch.sdk.database.fromwatch.sensordatadailyhrate;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IDatabaseDailyHrateHandler {
    void close();

    void createDatabase();

    void delete(DailyHrateEntity dailyHrateEntity);

    void insert(DailyHrateEntity dailyHrateEntity);

    void open();

    ArrayList<DailyHrateEntity> queryArrayBetween(int i, int i2);

    void update(DailyHrateEntity dailyHrateEntity);
}
