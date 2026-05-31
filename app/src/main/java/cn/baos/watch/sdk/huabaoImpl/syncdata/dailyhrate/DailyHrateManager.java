package cn.baos.watch.sdk.huabaoImpl.syncdata.dailyhrate;

import android.content.Context;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.database.fromwatch.sensordatadailyhrate.DailyHrateEntity;
import cn.baos.watch.sdk.database.fromwatch.sensordatadailyhrate.DatabaseDailyHrateHandler;
import cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SyncDataUtils;
import cn.baos.watch.w100.messages.Sensor_data_daily_hrate_array;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class DailyHrateManager extends SyncDataBaseManager implements DailyHrateInterface {
    private static DailyHrateManager instance;
    private Context mContext;
    private DatabaseDailyHrateHandler mDatabaseHandler;

    public static DailyHrateManager getInstance() {
        if (instance == null) {
            synchronized (DailyHrateManager.class) {
                if (instance == null) {
                    instance = new DailyHrateManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        DatabaseDailyHrateHandler databaseDailyHrateHandler = new DatabaseDailyHrateHandler(context);
        this.mDatabaseHandler = databaseDailyHrateHandler;
        databaseDailyHrateHandler.createDatabase();
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

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.dailyhrate.DailyHrateInterface
    public void saveDailyHrateEntitiesToDb(Sensor_data_daily_hrate_array sensor_data_daily_hrate_array) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            LogUtil.d("数据同步->手表数据->心率记录:" + new Gson().toJson(sensor_data_daily_hrate_array));
            open();
            DailyHrateEntity dailyHrateEntity = new DailyHrateEntity();
            for (int i = 0; i < sensor_data_daily_hrate_array.datas.length; i++) {
                dailyHrateEntity.setSensor_data_daily_hrate(sensor_data_daily_hrate_array.datas[i]);
                if (isRightData(sensor_data_daily_hrate_array.datas[i].update_timestamp, getDatabaseHandler().queryLatestTime())) {
                    LogUtil.d("localDb->数据同步->数据库插入" + new Gson().toJson(dailyHrateEntity));
                    this.mDatabaseHandler.insert(dailyHrateEntity);
                }
            }
            close();
        }
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.dailyhrate.DailyHrateInterface
    public ArrayList<DailyHrateEntity> queryDailyHrateToday(int i) {
        ArrayList<DailyHrateEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(SyncDataUtils.getZeroTimeStamp(i), (86400 + r4) - 1);
            Collections.reverse(arrayListQueryArrayBetween);
            close();
        }
        return arrayListQueryArrayBetween;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.dailyhrate.DailyHrateInterface
    public ArrayList<DailyHrateEntity> queryDailyHrateInInterval(int i, int i2) {
        ArrayList<DailyHrateEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(SyncDataUtils.getZeroTimeStamp(i), (SyncDataUtils.getZeroTimeStamp(i2) + 86400) - 1);
            Collections.reverse(arrayListQueryArrayBetween);
            LogUtil.d("查询区间内n天的运动静态数据:" + ArrayUtils.toString(arrayListQueryArrayBetween));
            close();
        }
        return arrayListQueryArrayBetween;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.dailyhrate.DailyHrateInterface
    public ArrayList<DailyHrateEntity> queryDailyHrateOnlyInterval(int i, int i2) {
        ArrayList<DailyHrateEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(i, i2);
            Collections.reverse(arrayListQueryArrayBetween);
            LogUtil.d("查询区间内n天的运动静态数据:" + ArrayUtils.toString(arrayListQueryArrayBetween));
            close();
        }
        return arrayListQueryArrayBetween;
    }
}
