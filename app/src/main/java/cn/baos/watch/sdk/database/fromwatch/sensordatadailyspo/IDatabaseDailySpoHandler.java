package cn.baos.watch.sdk.database.fromwatch.sensordatadailyspo;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IDatabaseDailySpoHandler {
    void close();

    void createDatabase();

    void delete(DailySpoEntity dailySpoEntity);

    void insert(DailySpoEntity dailySpoEntity);

    void open();

    DailySpoEntity query(int i);

    ArrayList<DailySpoEntity> queryArrayBetween(int i, int i2);

    void update(DailySpoEntity dailySpoEntity);
}
