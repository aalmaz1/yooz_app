package cn.baos.watch.sdk.huabaoImpl.syncdata.sleepstats;

import android.content.Context;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.database.fromwatch.sensordatasleepstats.DatabaseSleepStatsHandler;
import cn.baos.watch.sdk.database.fromwatch.sensordatasleepstats.SleepStatsEntity;
import cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SyncDataUtils;
import cn.baos.watch.sdk.util.W100Utils;
import cn.baos.watch.w100.messages.Sensor_data_sleep_stats_array;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class SleepStatsManager extends SyncDataBaseManager implements SleepStatsInterface {
    private static SleepStatsManager instance;
    private Context mContext;
    private DatabaseSleepStatsHandler mDatabaseHandler;

    public static SleepStatsManager getInstance() {
        if (instance == null) {
            synchronized (SleepStatsManager.class) {
                if (instance == null) {
                    instance = new SleepStatsManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        DatabaseSleepStatsHandler databaseSleepStatsHandler = new DatabaseSleepStatsHandler(context);
        this.mDatabaseHandler = databaseSleepStatsHandler;
        databaseSleepStatsHandler.createDatabase();
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager
    public void open() {
        this.mDatabaseHandler.open();
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager
    public void close() {
        this.mDatabaseHandler.close();
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager
    public DataBaseFartherHandler getDatabaseHandler() {
        return this.mDatabaseHandler;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.sleepstats.SleepStatsInterface
    public void saveSleepStatsEntitiesToDb(Sensor_data_sleep_stats_array sensor_data_sleep_stats_array) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            LogUtil.d("数据同步->手表数据->睡眠概况:" + W100Utils.toString(sensor_data_sleep_stats_array));
            open();
            SleepStatsEntity sleepStatsEntity = new SleepStatsEntity();
            for (int i = 0; i < sensor_data_sleep_stats_array.datas.length; i++) {
                sleepStatsEntity.setSensor_data_sleep_stats(sensor_data_sleep_stats_array.datas[i]);
                if (isRightData(sensor_data_sleep_stats_array.datas[i].update_timestamp, getDatabaseHandler().queryLatestTime())) {
                    LogUtil.d("localDb->数据同步->数据库插入" + W100Utils.toString(sleepStatsEntity));
                    this.mDatabaseHandler.insert(sleepStatsEntity);
                }
            }
            close();
        }
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.sleepstats.SleepStatsInterface
    public ArrayList<SleepStatsEntity> querySleepStatsToday(int i) {
        ArrayList<SleepStatsEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(SyncDataUtils.getZeroTimeStamp(i), (86400 + r4) - 1);
            Collections.reverse(arrayListQueryArrayBetween);
            close();
        }
        return arrayListQueryArrayBetween;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.sleepstats.SleepStatsInterface
    public ArrayList<SleepStatsEntity> querySleepStatsInInterval(int i, int i2) {
        ArrayList<SleepStatsEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(SyncDataUtils.getZeroTimeStamp(i) - 10800, (SyncDataUtils.getZeroTimeStamp(i2) + 75600) - 1);
            Collections.reverse(arrayListQueryArrayBetween);
            LogUtil.d("查询区间内n天的睡眠概况静态数据:" + ArrayUtils.toString(arrayListQueryArrayBetween));
            close();
        }
        return arrayListQueryArrayBetween;
    }

    public ArrayList<SleepStatsEntity> querySleepAll(int i) {
        ArrayList<SleepStatsEntity> arrayListQuerySleepStatsToday = querySleepStatsToday(i);
        if (arrayListQuerySleepStatsToday != null && arrayListQuerySleepStatsToday.size() > 0) {
            for (SleepStatsEntity sleepStatsEntity : arrayListQuerySleepStatsToday) {
                sleepStatsEntity.setSleepStatusArr(querySleepStatsInInterval(sleepStatsEntity.getSensor_data_sleep_stats().begin_timestamp, sleepStatsEntity.getSensor_data_sleep_stats().end_timestamp));
            }
        }
        return arrayListQuerySleepStatsToday;
    }
}
