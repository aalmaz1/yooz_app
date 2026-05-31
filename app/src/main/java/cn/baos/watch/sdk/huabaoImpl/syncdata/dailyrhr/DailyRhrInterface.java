package cn.baos.watch.sdk.huabaoImpl.syncdata.dailyrhr;

import cn.baos.watch.sdk.database.fromwatch.sensordatadailyrhr.DailyRhrEntity;
import cn.baos.watch.w100.messages.Sensor_data_daily_rhr_array;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface DailyRhrInterface {
    ArrayList<DailyRhrEntity> queryDailyRhrInInterval(int i, int i2);

    ArrayList<DailyRhrEntity> queryDailyRhrToday(int i);

    void saveDailyRhrEntitiesToDb(Sensor_data_daily_rhr_array sensor_data_daily_rhr_array);
}
