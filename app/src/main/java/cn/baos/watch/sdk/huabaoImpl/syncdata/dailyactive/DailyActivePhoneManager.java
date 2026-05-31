package cn.baos.watch.sdk.huabaoImpl.syncdata.dailyactive;

import android.content.Context;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.database.fromwatch.sensordatadailyactive.DailyActiveEntity;
import cn.baos.watch.sdk.database.phone.active.DatabaseDailyActivePhoneHandler;
import cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SyncDataUtils;
import cn.baos.watch.sdk.util.W100Utils;
import cn.baos.watch.w100.messages.Sensor_data_daily_active_array;
import cn.baos.watch.w100.messages.Sensor_data_daily_active_sum;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class DailyActivePhoneManager extends SyncDataBaseManager implements DailyActivePhoneInterface {
    private static DailyActivePhoneManager instance;
    private Context mContext;
    private DatabaseDailyActivePhoneHandler mDatabaseHandler;

    public static DailyActivePhoneManager getInstance() {
        if (instance == null) {
            synchronized (DailyActivePhoneManager.class) {
                if (instance == null) {
                    instance = new DailyActivePhoneManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        DatabaseDailyActivePhoneHandler databaseDailyActivePhoneHandler = new DatabaseDailyActivePhoneHandler(context);
        this.mDatabaseHandler = databaseDailyActivePhoneHandler;
        databaseDailyActivePhoneHandler.createDatabase();
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
        Sensor_data_daily_active_sum sensor_data_daily_active_sum2 = sensor_data_daily_active_array.datas[0];
        Sensor_data_daily_active_sum sensor_data_daily_active_sum3 = new Sensor_data_daily_active_sum();
        sensor_data_daily_active_sum3.sum_distance_m = sensor_data_daily_active_sum2.sum_distance_m;
        sensor_data_daily_active_sum3.sum_step = sensor_data_daily_active_sum2.sum_step;
        sensor_data_daily_active_sum3.sum_calorie = sensor_data_daily_active_sum2.sum_calorie;
        sensor_data_daily_active_sum3.sum_times = sensor_data_daily_active_sum2.sum_times;
        sensor_data_daily_active_sum3.update_timestamp = (int) (System.currentTimeMillis() / 1000);
        Sensor_data_daily_active_array sensor_data_daily_active_array2 = new Sensor_data_daily_active_array();
        sensor_data_daily_active_array2.datas = new Sensor_data_daily_active_sum[]{sensor_data_daily_active_sum3};
        saveDailyActiveEntitiesToDb(sensor_data_daily_active_array);
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int zeroTimeStamp = SyncDataUtils.getZeroTimeStamp(iCurrentTimeMillis);
        int zeroTimeStamp2 = (SyncDataUtils.getZeroTimeStamp(iCurrentTimeMillis) + 86400) - 1;
        Sensor_data_daily_active_sum sensor_data_daily_active_sum4 = sensor_data_daily_active_array2.datas[0];
        ArrayList<DailyActiveEntity> arrayListQueryDailyActiveInInterval = DailyActiveManager.getInstance().queryDailyActiveInInterval(zeroTimeStamp, zeroTimeStamp2);
        if (arrayListQueryDailyActiveInInterval.size() > 0) {
            sensor_data_daily_active_sum = arrayListQueryDailyActiveInInterval.get(arrayListQueryDailyActiveInInterval.size() - 1).getSensor_data_daily_active_sum();
        } else {
            sensor_data_daily_active_sum = new Sensor_data_daily_active_sum();
        }
        sensor_data_daily_active_sum.sum_distance_m += sensor_data_daily_active_sum4.sum_distance_m;
        sensor_data_daily_active_sum.sum_step += sensor_data_daily_active_sum4.sum_step;
        sensor_data_daily_active_sum.sum_calorie += sensor_data_daily_active_sum4.sum_calorie;
        sensor_data_daily_active_sum.sum_times += sensor_data_daily_active_sum4.sum_times;
        sensor_data_daily_active_sum.update_timestamp = (int) (System.currentTimeMillis() / 1000);
        Sensor_data_daily_active_array sensor_data_daily_active_array3 = new Sensor_data_daily_active_array();
        sensor_data_daily_active_array3.datas = new Sensor_data_daily_active_sum[]{sensor_data_daily_active_sum};
        DailyActiveManager.getInstance().saveDailyActiveEntitiesToDb(sensor_data_daily_active_array3);
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.dailyactive.DailyActivePhoneInterface
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

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.dailyactive.DailyActivePhoneInterface
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

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.dailyactive.DailyActivePhoneInterface
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
}
