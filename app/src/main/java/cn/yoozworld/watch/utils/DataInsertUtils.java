package cn.yoozworld.watch.utils;

import android.content.Context;
import cn.baos.watch.sdk.huabaoImpl.syncdata.dailyhrate.DailyHrateManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.dailyspo.DailySpoManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sleepstats.SleepStatsManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sleepstatus.SleepStatusManager;
import cn.baos.watch.sdk.util.SyncDataUtils;
import cn.baos.watch.w100.messages.Sensor_data_daily_hrate;
import cn.baos.watch.w100.messages.Sensor_data_daily_hrate_array;
import cn.baos.watch.w100.messages.Sensor_data_daily_spo;
import cn.baos.watch.w100.messages.Sensor_data_daily_spo_array;
import cn.baos.watch.w100.messages.Sensor_data_sleep_stats;
import cn.baos.watch.w100.messages.Sensor_data_sleep_stats_array;
import cn.baos.watch.w100.messages.Sensor_data_sleep_status;
import cn.baos.watch.w100.messages.Sensor_data_sleep_status_array;

/* JADX INFO: loaded from: classes.dex */
public class DataInsertUtils {
    public static void insertData(Context context) {
        int zeroTimeStamp = SyncDataUtils.getZeroTimeStamp((int) (System.currentTimeMillis() / 1000));
        int i = zeroTimeStamp - 43200;
        int i2 = (86400 + zeroTimeStamp) - i;
        int i3 = i2 / 300;
        for (int i4 = 0; i4 < i3; i4++) {
            Sensor_data_daily_hrate_array sensor_data_daily_hrate_array = new Sensor_data_daily_hrate_array();
            Sensor_data_daily_hrate sensor_data_daily_hrate = new Sensor_data_daily_hrate();
            sensor_data_daily_hrate.heartrate = ((int) (Math.random() * 40.0d)) + 60;
            sensor_data_daily_hrate.update_timestamp = (i4 * 5 * 60) + i;
            sensor_data_daily_hrate_array.datas = new Sensor_data_daily_hrate[]{sensor_data_daily_hrate};
            DailyHrateManager.getInstance().saveDailyHrateEntitiesToDb(sensor_data_daily_hrate_array);
        }
        int i5 = i2 / 1800;
        for (int i6 = 0; i6 < i5; i6++) {
            Sensor_data_daily_spo_array sensor_data_daily_spo_array = new Sensor_data_daily_spo_array();
            Sensor_data_daily_spo sensor_data_daily_spo = new Sensor_data_daily_spo();
            sensor_data_daily_spo.spo = ((int) (Math.random() * 40.0d)) + 60;
            sensor_data_daily_spo.update_timestamp = (i6 * 30 * 60) + i;
            sensor_data_daily_spo_array.datas = new Sensor_data_daily_spo[]{sensor_data_daily_spo};
            DailySpoManager.getInstance().saveDailySpoEntitiesToDb(sensor_data_daily_spo_array);
        }
        int i7 = zeroTimeStamp - 7200;
        int i8 = zeroTimeStamp + 21600;
        Sensor_data_sleep_stats_array sensor_data_sleep_stats_array = new Sensor_data_sleep_stats_array();
        Sensor_data_sleep_stats sensor_data_sleep_stats = new Sensor_data_sleep_stats();
        sensor_data_sleep_stats.update_timestamp = i8 + 1;
        sensor_data_sleep_stats.begin_timestamp = i7;
        sensor_data_sleep_stats.end_timestamp = i8;
        sensor_data_sleep_stats.total_sec = i8 - i7;
        sensor_data_sleep_stats.light_sec = 10800;
        sensor_data_sleep_stats.deep_sec = 10800;
        sensor_data_sleep_stats.wakeup_sec = 3600;
        sensor_data_sleep_stats.eyesmove_sec = 7200;
        sensor_data_sleep_stats_array.datas = new Sensor_data_sleep_stats[]{sensor_data_sleep_stats};
        SleepStatsManager.getInstance().saveSleepStatsEntitiesToDb(sensor_data_sleep_stats_array);
        for (int i9 = 0; i9 < 4; i9++) {
            int i10 = (i9 * 3600) + i7;
            Sensor_data_sleep_status_array sensor_data_sleep_status_array = new Sensor_data_sleep_status_array();
            Sensor_data_sleep_status sensor_data_sleep_status = new Sensor_data_sleep_status();
            if (i9 == 3) {
                i10 += 18000;
            }
            sensor_data_sleep_status.update_timestamp = i10;
            sensor_data_sleep_status.sleep_status = 1;
            sensor_data_sleep_status_array.datas = new Sensor_data_sleep_status[]{sensor_data_sleep_status};
            SleepStatusManager.getInstance().saveSleepStatusEntitiesToDb(sensor_data_sleep_status_array);
        }
        int i11 = zeroTimeStamp + 28800;
        int i12 = zeroTimeStamp + 36000;
        Sensor_data_sleep_stats_array sensor_data_sleep_stats_array2 = new Sensor_data_sleep_stats_array();
        Sensor_data_sleep_stats sensor_data_sleep_stats2 = new Sensor_data_sleep_stats();
        sensor_data_sleep_stats2.update_timestamp = i12 + 1;
        sensor_data_sleep_stats2.begin_timestamp = i11;
        sensor_data_sleep_stats2.end_timestamp = i12;
        sensor_data_sleep_stats2.total_sec = i12 - i11;
        sensor_data_sleep_stats2.light_sec = 1800;
        sensor_data_sleep_stats2.deep_sec = 1800;
        sensor_data_sleep_stats2.wakeup_sec = 1800;
        sensor_data_sleep_stats2.eyesmove_sec = 1800;
        sensor_data_sleep_stats_array2.datas = new Sensor_data_sleep_stats[]{sensor_data_sleep_stats2};
        SleepStatsManager.getInstance().saveSleepStatsEntitiesToDb(sensor_data_sleep_stats_array2);
        int i13 = 0;
        while (i13 < 4) {
            int i14 = (i13 * 1800) + i11;
            Sensor_data_sleep_status_array sensor_data_sleep_status_array2 = new Sensor_data_sleep_status_array();
            Sensor_data_sleep_status sensor_data_sleep_status2 = new Sensor_data_sleep_status();
            if (i13 == 3) {
                i14 = (int) (((double) i14) + 5400.0d);
            }
            sensor_data_sleep_status2.update_timestamp = i14;
            sensor_data_sleep_status2.sleep_status = (i13 == 0 || i13 > 4) ? 1 : i13;
            sensor_data_sleep_status_array2.datas = new Sensor_data_sleep_status[]{sensor_data_sleep_status2};
            SleepStatusManager.getInstance().saveSleepStatusEntitiesToDb(sensor_data_sleep_status_array2);
            i13++;
        }
    }
}
