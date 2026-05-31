package cn.baos.watch.sdk.huabaoImpl.syncdata.sportrecord;

import cn.baos.watch.sdk.database.fromwatch.sportrecord.SportRecordFromWatchEntity;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sport.SportPhoneRecordEntity;
import cn.baos.watch.w100.messages.Sport_record_array;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface SportRecordFromWatchInterface {
    ArrayList<SportRecordFromWatchEntity> querySportRecordFromWatchInInterval(int i, int i2);

    ArrayList<SportRecordFromWatchEntity> querySportRecordFromWatchToday(int i);

    void saveSportRecordFromPhoneEntitiesToDb(SportPhoneRecordEntity sportPhoneRecordEntity);

    void saveSportRecordFromWatchEntitiesToDb(Sport_record_array sport_record_array);
}
