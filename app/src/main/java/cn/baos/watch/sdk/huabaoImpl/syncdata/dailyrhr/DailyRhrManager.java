package cn.baos.watch.sdk.huabaoImpl.syncdata.dailyrhr;

import android.content.Context;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.database.fromwatch.sensordatadailyrhr.DailyRhrEntity;
import cn.baos.watch.sdk.database.fromwatch.sensordatadailyrhr.DatabaseDailyRhrHandler;
import cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SyncDataUtils;
import cn.baos.watch.sdk.util.W100Utils;
import cn.baos.watch.w100.messages.Sensor_data_daily_rhr_array;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class DailyRhrManager extends SyncDataBaseManager implements DailyRhrInterface {
    private static DailyRhrManager instance;
    private Context mContext;
    private DatabaseDailyRhrHandler mDatabaseHandler;

    public static DailyRhrManager getInstance() {
        if (instance == null) {
            synchronized (DailyRhrManager.class) {
                if (instance == null) {
                    instance = new DailyRhrManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        DatabaseDailyRhrHandler databaseDailyRhrHandler = new DatabaseDailyRhrHandler(context);
        this.mDatabaseHandler = databaseDailyRhrHandler;
        databaseDailyRhrHandler.createDatabase();
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

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.dailyrhr.DailyRhrInterface
    public void saveDailyRhrEntitiesToDb(Sensor_data_daily_rhr_array sensor_data_daily_rhr_array) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            LogUtil.d("数据同步->手表数据->运动记录:" + W100Utils.toString(sensor_data_daily_rhr_array));
            open();
            DailyRhrEntity dailyRhrEntity = new DailyRhrEntity();
            for (int i = 0; i < sensor_data_daily_rhr_array.datas.length; i++) {
                dailyRhrEntity.setSensor_data_daily_rhr(sensor_data_daily_rhr_array.datas[i]);
                if (isRightData(sensor_data_daily_rhr_array.datas[i].update_timestamp, getDatabaseHandler().queryLatestTime())) {
                    LogUtil.d("localDb->数据库插入" + W100Utils.toString(dailyRhrEntity));
                    this.mDatabaseHandler.insert(dailyRhrEntity);
                }
            }
            close();
        }
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.dailyrhr.DailyRhrInterface
    public ArrayList<DailyRhrEntity> queryDailyRhrToday(int i) {
        ArrayList<DailyRhrEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(SyncDataUtils.getZeroTimeStamp(i), (86400 + r4) - 1);
            Collections.reverse(arrayListQueryArrayBetween);
            close();
        }
        return arrayListQueryArrayBetween;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.dailyrhr.DailyRhrInterface
    public ArrayList<DailyRhrEntity> queryDailyRhrInInterval(int i, int i2) {
        ArrayList<DailyRhrEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(SyncDataUtils.getZeroTimeStamp(i), (SyncDataUtils.getZeroTimeStamp(i2) + 86400) - 1);
            Collections.reverse(arrayListQueryArrayBetween);
            LogUtil.d("查询区间内n天的运动静态数据:" + ArrayUtils.toString(arrayListQueryArrayBetween));
            close();
        }
        return arrayListQueryArrayBetween;
    }
}
