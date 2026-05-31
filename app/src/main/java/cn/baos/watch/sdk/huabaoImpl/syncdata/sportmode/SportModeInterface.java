package cn.baos.watch.sdk.huabaoImpl.syncdata.sportmode;

import cn.baos.watch.sdk.database.fromwatch.sensordatasportmode.SportModeEntity;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sport.SportPhoneRecordDetailEntity;
import cn.baos.watch.w100.messages.Sensor_data_sport_mode_array;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface SportModeInterface {
    ArrayList<SportModeEntity> querySportModeInInterval(int i, int i2);

    ArrayList<SportPhoneRecordDetailEntity> querySportModeInIntervalPhone(int i, int i2);

    ArrayList<SportModeEntity> querySportModeToday(int i);

    void saveSportModeEntitiesPhoneToDb(SportPhoneRecordDetailEntity sportPhoneRecordDetailEntity);

    void saveSportModeEntitiesToDb(Sensor_data_sport_mode_array sensor_data_sport_mode_array);
}
