package cn.baos.watch.sdk.database.fromwatch.sportrecord;

import cn.baos.watch.sdk.huabaoImpl.syncdata.sport.SportPhoneRecordEntity;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IDatabaseSportRecordFromWatchHandler {
    void close();

    void createDatabase();

    void delete(SportRecordFromWatchEntity sportRecordFromWatchEntity);

    void insert(SportRecordFromWatchEntity sportRecordFromWatchEntity);

    void insertPhone(SportPhoneRecordEntity sportPhoneRecordEntity);

    void open();

    SportRecordFromWatchEntity query(int i);

    ArrayList<SportRecordFromWatchEntity> queryArrayBetween(int i, int i2);

    void update(SportRecordFromWatchEntity sportRecordFromWatchEntity);
}
