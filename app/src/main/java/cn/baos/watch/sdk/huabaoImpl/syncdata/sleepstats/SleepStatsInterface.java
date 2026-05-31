package cn.baos.watch.sdk.huabaoImpl.syncdata.sleepstats;

import cn.baos.watch.sdk.database.fromwatch.sensordatasleepstats.SleepStatsEntity;
import cn.baos.watch.w100.messages.Sensor_data_sleep_stats_array;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface SleepStatsInterface {
    ArrayList<SleepStatsEntity> querySleepStatsInInterval(int i, int i2);

    ArrayList<SleepStatsEntity> querySleepStatsToday(int i);

    void saveSleepStatsEntitiesToDb(Sensor_data_sleep_stats_array sensor_data_sleep_stats_array);
}
