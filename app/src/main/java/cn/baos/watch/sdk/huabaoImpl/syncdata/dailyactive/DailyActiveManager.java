package cn.baos.watch.sdk.huabaoImpl.syncdata.dailyactive;

import android.content.Context;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.database.fromwatch.sensordatadailyactive.DailyActiveEntity;
import cn.baos.watch.sdk.database.fromwatch.sensordatadailyactive.DatabaseDailyActiveHandler;
import cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SyncDataUtils;
import cn.baos.watch.sdk.util.W100Utils;
import cn.baos.watch.w100.messages.Sensor_data_daily_active_array;
import cn.baos.watch.w100.messages.Sensor_data_daily_active_sum;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class DailyActiveManager extends SyncDataBaseManager implements DailyActiveInterface {
    private static DailyActiveManager instance;
    private Context mContext;
    private DatabaseDailyActiveHandler mDatabaseHandler;

    public static DailyActiveManager getInstance() {
        if (instance == null) {
            synchronized (DailyActiveManager.class) {
                if (instance == null) {
                    instance = new DailyActiveManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        DatabaseDailyActiveHandler databaseDailyActiveHandler = new DatabaseDailyActiveHandler(context);
        this.mDatabaseHandler = databaseDailyActiveHandler;
        databaseDailyActiveHandler.createDatabase();
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager
    public DataBaseFartherHandler getDatabaseHandler() {
        return this.mDatabaseHandler;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager
    public void open() {
        this.mDatabaseHandler.open();
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager
    public void close() {
        this.mDatabaseHandler.close();
    }

    public void saveData(Sensor_data_daily_active_array sensor_data_daily_active_array) {
        Sensor_data_daily_active_sum sensor_data_daily_active_sum;
        LogUtil.d("------今日活动-saveDailyActiveEntitiesToDb ---start：" + new Gson().toJson(sensor_data_daily_active_array));
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int zeroTimeStamp = SyncDataUtils.getZeroTimeStamp(iCurrentTimeMillis);
        int zeroTimeStamp2 = (SyncDataUtils.getZeroTimeStamp(iCurrentTimeMillis) + 86400) - 1;
        ArrayList<DailyActiveEntity> arrayListQueryDailyActiveInInterval = DailyActivePhoneManager.getInstance().queryDailyActiveInInterval(zeroTimeStamp, zeroTimeStamp2);
        if (arrayListQueryDailyActiveInInterval.size() > 0) {
            sensor_data_daily_active_sum = new Sensor_data_daily_active_sum();
            Iterator<DailyActiveEntity> it = arrayListQueryDailyActiveInInterval.iterator();
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            while (it.hasNext()) {
                Sensor_data_daily_active_sum sensor_data_daily_active_sum2 = it.next().getSensor_data_daily_active_sum();
                i += sensor_data_daily_active_sum2.sum_distance_m;
                i2 += sensor_data_daily_active_sum2.sum_step;
                i3 += sensor_data_daily_active_sum2.sum_calorie;
                i4 += sensor_data_daily_active_sum2.sum_times;
            }
            sensor_data_daily_active_sum.sum_distance_m = i;
            sensor_data_daily_active_sum.sum_step = i2;
            sensor_data_daily_active_sum.sum_calorie = i3;
            sensor_data_daily_active_sum.sum_times = i4;
        } else {
            sensor_data_daily_active_sum = null;
        }
        try {
            LogUtil.d("------今日活动-前个数据");
            DailyActiveEntity dailyActiveEntityQueryLastActiveToday = queryLastActiveToday(zeroTimeStamp, zeroTimeStamp2);
            if (dailyActiveEntityQueryLastActiveToday != null) {
                LogUtil.d("------今日活动-lastEntity" + new Gson().toJson(dailyActiveEntityQueryLastActiveToday));
                Sensor_data_daily_active_sum sensor_data_daily_active_sum3 = dailyActiveEntityQueryLastActiveToday.getSensor_data_daily_active_sum();
                if (sensor_data_daily_active_sum == null) {
                    sensor_data_daily_active_sum = new Sensor_data_daily_active_sum();
                }
                sensor_data_daily_active_sum.sum_distance_m = sensor_data_daily_active_sum3.sum_distance_m + sensor_data_daily_active_sum.sum_distance_m;
                sensor_data_daily_active_sum.sum_step = sensor_data_daily_active_sum3.sum_step + sensor_data_daily_active_sum.sum_step;
                sensor_data_daily_active_sum.sum_calorie = sensor_data_daily_active_sum3.sum_calorie + sensor_data_daily_active_sum.sum_calorie;
                sensor_data_daily_active_sum.sum_times = sensor_data_daily_active_sum3.sum_times + sensor_data_daily_active_sum.sum_times;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sensor_data_daily_active_array != null && sensor_data_daily_active_sum != null && sensor_data_daily_active_array.datas != null && sensor_data_daily_active_array.datas.length > 0) {
            Sensor_data_daily_active_sum sensor_data_daily_active_sum4 = sensor_data_daily_active_array.datas[sensor_data_daily_active_array.datas.length - 1];
            sensor_data_daily_active_sum4.sum_distance_m += sensor_data_daily_active_sum.sum_distance_m;
            sensor_data_daily_active_sum4.sum_step += sensor_data_daily_active_sum.sum_step;
            sensor_data_daily_active_sum4.sum_calorie += sensor_data_daily_active_sum.sum_calorie;
            sensor_data_daily_active_sum4.sum_times += sensor_data_daily_active_sum.sum_times;
            sensor_data_daily_active_array.datas[sensor_data_daily_active_array.datas.length - 1] = sensor_data_daily_active_sum4;
        }
        LogUtil.d("------今日活动-saveDailyActiveEntitiesToDb ---end：" + new Gson().toJson(sensor_data_daily_active_array));
        saveDailyActiveEntitiesToDb(sensor_data_daily_active_array);
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.dailyactive.DailyActiveInterface
    public void saveDailyActiveEntitiesToDb(Sensor_data_daily_active_array sensor_data_daily_active_array) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            LogUtil.d("数据同步->服务器数据->实时数据日常活动:" + W100Utils.toString(sensor_data_daily_active_array));
            open();
            DailyActiveEntity dailyActiveEntity = new DailyActiveEntity();
            for (int i = 0; i < sensor_data_daily_active_array.datas.length; i++) {
                dailyActiveEntity.setSensor_data_daily_active_sum(sensor_data_daily_active_array.datas[i]);
                if (isRightData(sensor_data_daily_active_array.datas[i].update_timestamp, getDatabaseHandler().queryLatestTime())) {
                    LogUtil.d("localDb->数据同步->实时数据日常活动数据库插入" + W100Utils.toString(dailyActiveEntity));
                    this.mDatabaseHandler.insert(dailyActiveEntity);
                }
            }
            close();
        }
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.dailyactive.DailyActiveInterface
    public ArrayList<DailyActiveEntity> queryDailyActiveToday(int i) {
        ArrayList<DailyActiveEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(SyncDataUtils.getZeroTimeStamp(i), (86400 + r4) - 1);
            Collections.reverse(arrayListQueryArrayBetween);
            close();
        }
        return arrayListQueryArrayBetween;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.dailyactive.DailyActiveInterface
    public ArrayList<DailyActiveEntity> queryDailyActiveInInterval(int i, int i2) {
        ArrayList<DailyActiveEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            int zeroTimeStamp = SyncDataUtils.getZeroTimeStamp(i);
            int zeroTimeStamp2 = (SyncDataUtils.getZeroTimeStamp(i2) + 86400) - 1;
            LogUtil.d("查询区间内n天的左边时间:" + zeroTimeStamp + " 右边时间:" + zeroTimeStamp2);
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(zeroTimeStamp, zeroTimeStamp2);
            Collections.reverse(arrayListQueryArrayBetween);
            LogUtil.d("查询区间内n天的运动静态数据:" + ArrayUtils.toString(arrayListQueryArrayBetween));
            close();
        }
        return arrayListQueryArrayBetween;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.dailyactive.DailyActiveInterface
    public DailyActiveEntity queryLastActiveToday(int i, int i2) {
        DailyActiveEntity dailyActiveEntityQueryActiveBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            LogUtil.d("查询区间内上次運動的左边时间:" + i + " 右边时间:" + i2);
            dailyActiveEntityQueryActiveBetween = this.mDatabaseHandler.queryActiveBetween(i, i2);
            LogUtil.d("查询区间内上次運動的运动静态数据:" + ArrayUtils.toString(dailyActiveEntityQueryActiveBetween));
            close();
        }
        return dailyActiveEntityQueryActiveBetween;
    }
}
