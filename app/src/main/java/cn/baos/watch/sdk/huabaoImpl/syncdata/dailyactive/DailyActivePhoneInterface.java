package cn.baos.watch.sdk.huabaoImpl.syncdata.dailyactive;

import cn.baos.watch.sdk.database.fromwatch.sensordatadailyactive.DailyActiveEntity;
import cn.baos.watch.w100.messages.Sensor_data_daily_active_array;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface DailyActivePhoneInterface {
    ArrayList<DailyActiveEntity> queryDailyActiveInInterval(int i, int i2);

    ArrayList<DailyActiveEntity> queryDailyActiveToday(int i);

    void saveDailyActiveEntitiesToDb(Sensor_data_daily_active_array sensor_data_daily_active_array);
}
