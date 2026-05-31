package cn.baos.watch.sdk.huabaoImpl.syncdata.dailyhrate;

import cn.baos.watch.sdk.database.fromwatch.sensordatadailyhrate.DailyHrateEntity;
import cn.baos.watch.w100.messages.Sensor_data_daily_hrate_array;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface DailyHrateInterface {
    ArrayList<DailyHrateEntity> queryDailyHrateInInterval(int i, int i2);

    ArrayList<DailyHrateEntity> queryDailyHrateOnlyInterval(int i, int i2);

    ArrayList<DailyHrateEntity> queryDailyHrateToday(int i);

    void saveDailyHrateEntitiesToDb(Sensor_data_daily_hrate_array sensor_data_daily_hrate_array);
}
