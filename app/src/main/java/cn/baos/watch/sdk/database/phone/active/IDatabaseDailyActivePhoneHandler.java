package cn.baos.watch.sdk.database.phone.active;

import cn.baos.watch.sdk.database.fromwatch.sensordatadailyactive.DailyActiveEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IDatabaseDailyActivePhoneHandler {
    void close();

    void createDatabase();

    void delete(DailyActiveEntity dailyActiveEntity);

    void insert(DailyActiveEntity dailyActiveEntity);

    void open();

    DailyActiveEntity query(int i);

    ArrayList<DailyActiveEntity> queryArrayBetween(int i, int i2);

    void update(DailyActiveEntity dailyActiveEntity);
}
