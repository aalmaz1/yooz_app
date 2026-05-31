package cn.baos.watch.sdk.huabaoImpl.syncdata.dailyspo;

import android.content.Context;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.database.fromwatch.sensordatadailyspo.DailySpoEntity;
import cn.baos.watch.sdk.database.fromwatch.sensordatadailyspo.DatabaseDailySpoHandler;
import cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SyncDataUtils;
import cn.baos.watch.sdk.util.W100Utils;
import cn.baos.watch.w100.messages.Sensor_data_daily_spo_array;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class DailySpoManager extends SyncDataBaseManager implements DailySpoInterface {
    private static DailySpoManager instance;
    private Context mContext;
    private DatabaseDailySpoHandler mDatabaseHandler;

    public static DailySpoManager getInstance() {
        if (instance == null) {
            synchronized (DailySpoManager.class) {
                if (instance == null) {
                    instance = new DailySpoManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        DatabaseDailySpoHandler databaseDailySpoHandler = new DatabaseDailySpoHandler(context);
        this.mDatabaseHandler = databaseDailySpoHandler;
        databaseDailySpoHandler.createDatabase();
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

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.dailyspo.DailySpoInterface
    public void saveDailySpoEntitiesToDb(Sensor_data_daily_spo_array sensor_data_daily_spo_array) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            LogUtil.d("数据同步->手表数据->运动记录:" + W100Utils.toString(sensor_data_daily_spo_array));
            open();
            DailySpoEntity dailySpoEntity = new DailySpoEntity();
            for (int i = 0; i < sensor_data_daily_spo_array.datas.length; i++) {
                dailySpoEntity.setSensor_data_daily_spo(sensor_data_daily_spo_array.datas[i]);
                int iQueryLatestTime = getDatabaseHandler().queryLatestTime();
                if (isRightData(sensor_data_daily_spo_array.datas[i].update_timestamp, iQueryLatestTime)) {
                    LogUtil.d("localDb->数据同步->数据库插入" + W100Utils.toString(dailySpoEntity.getSensor_data_daily_spo()) + " 当前时间戳:" + sensor_data_daily_spo_array.datas[i].update_timestamp + " 数据库最晚时间戳:" + iQueryLatestTime);
                    this.mDatabaseHandler.insert(dailySpoEntity);
                }
            }
            close();
        }
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.dailyspo.DailySpoInterface
    public ArrayList<DailySpoEntity> queryDailySpoToday(int i) {
        ArrayList<DailySpoEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(SyncDataUtils.getZeroTimeStamp(i), (86400 + r4) - 1);
            Collections.reverse(arrayListQueryArrayBetween);
            close();
        }
        return arrayListQueryArrayBetween;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.dailyspo.DailySpoInterface
    public ArrayList<DailySpoEntity> queryDailySpoInInterval(int i, int i2) {
        ArrayList<DailySpoEntity> arrayListQueryArrayBetween;
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
