package cn.baos.watch.sdk.huabaoImpl.syncdata.sleepstatus;

import android.content.Context;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.database.fromwatch.sensordatasleepstatus.DatabaseSleepStatusHandler;
import cn.baos.watch.sdk.database.fromwatch.sensordatasleepstatus.SleepStatusEntity;
import cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SyncDataUtils;
import cn.baos.watch.sdk.util.W100Utils;
import cn.baos.watch.w100.messages.Sensor_data_sleep_status_array;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class SleepStatusManager extends SyncDataBaseManager implements SleepStatusInterface {
    private static SleepStatusManager instance;
    private Context mContext;
    private DatabaseSleepStatusHandler mDatabaseHandler;

    public static SleepStatusManager getInstance() {
        if (instance == null) {
            synchronized (SleepStatusManager.class) {
                if (instance == null) {
                    instance = new SleepStatusManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        DatabaseSleepStatusHandler databaseSleepStatusHandler = new DatabaseSleepStatusHandler(context);
        this.mDatabaseHandler = databaseSleepStatusHandler;
        databaseSleepStatusHandler.createDatabase();
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

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.sleepstatus.SleepStatusInterface
    public void saveSleepStatusEntitiesToDb(Sensor_data_sleep_status_array sensor_data_sleep_status_array) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            LogUtil.d("数据同步->手表数据->睡眠状态变化:" + W100Utils.toString(sensor_data_sleep_status_array));
            open();
            SleepStatusEntity sleepStatusEntity = new SleepStatusEntity();
            for (int i = 0; i < sensor_data_sleep_status_array.datas.length; i++) {
                sleepStatusEntity.setSensor_data_sleep_status(sensor_data_sleep_status_array.datas[i]);
                if (isRightData(sensor_data_sleep_status_array.datas[i].update_timestamp, getDatabaseHandler().queryLatestTime())) {
                    LogUtil.d("localDb->数据同步->数据库插入" + W100Utils.toString(sleepStatusEntity));
                    this.mDatabaseHandler.insert(sleepStatusEntity);
                }
            }
            close();
        }
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.sleepstatus.SleepStatusInterface
    public ArrayList<SleepStatusEntity> querySleepStatusToday(int i) {
        ArrayList<SleepStatusEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(SyncDataUtils.getZeroTimeStamp(i), (86400 + r4) - 1);
            Collections.reverse(arrayListQueryArrayBetween);
            close();
        }
        return arrayListQueryArrayBetween;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.sleepstatus.SleepStatusInterface
    public ArrayList<SleepStatusEntity> querySleepStatusInInterval(int i, int i2) {
        ArrayList<SleepStatusEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(i, i2);
            Collections.reverse(arrayListQueryArrayBetween);
            LogUtil.d("查询区间内n天的睡眠状态变化数据:" + ArrayUtils.toString(arrayListQueryArrayBetween));
            close();
        }
        return arrayListQueryArrayBetween;
    }
}
