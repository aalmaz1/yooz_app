package cn.baos.watch.sdk.huabaoImpl.syncdata.dailyspo;

import cn.baos.watch.sdk.database.fromwatch.sensordatadailyspo.DailySpoEntity;
import cn.baos.watch.w100.messages.Sensor_data_daily_spo_array;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface DailySpoInterface {
    ArrayList<DailySpoEntity> queryDailySpoInInterval(int i, int i2);

    ArrayList<DailySpoEntity> queryDailySpoToday(int i);

    void saveDailySpoEntitiesToDb(Sensor_data_daily_spo_array sensor_data_daily_spo_array);
}
