package cn.baos.watch.sdk.huabaoImpl.syncdata.sporthrate;

import android.content.Context;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.database.fromwatch.sensordatasporthrate.DatabaseSportHrateHandler;
import cn.baos.watch.sdk.database.fromwatch.sensordatasporthrate.SportHrateEntity;
import cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SyncDataUtils;
import cn.baos.watch.sdk.util.W100Utils;
import cn.baos.watch.w100.messages.Sensor_data_sport_hrate_array;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class SportHeartManager extends SyncDataBaseManager implements SportHrateInterface {
    private static SportHeartManager instance;
    private Context mContext;
    private DatabaseSportHrateHandler mDatabaseHandler;

    public static SportHeartManager getInstance() {
        if (instance == null) {
            synchronized (SportHeartManager.class) {
                if (instance == null) {
                    instance = new SportHeartManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        DatabaseSportHrateHandler databaseSportHrateHandler = new DatabaseSportHrateHandler(context);
        this.mDatabaseHandler = databaseSportHrateHandler;
        databaseSportHrateHandler.createDatabase();
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

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.sporthrate.SportHrateInterface
    public void saveSportHrateEntitiesToDb(Sensor_data_sport_hrate_array sensor_data_sport_hrate_array) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            LogUtil.d("数据同步->手表数据->运动记录:" + W100Utils.toString(sensor_data_sport_hrate_array));
            open();
            SportHrateEntity sportHrateEntity = new SportHrateEntity();
            for (int i = 0; i < sensor_data_sport_hrate_array.datas.length; i++) {
                sportHrateEntity.setSensor_data_sport_hrate(sensor_data_sport_hrate_array.datas[i]);
                if (isRightData(sensor_data_sport_hrate_array.datas[i].update_timestamp, getDatabaseHandler().queryLatestTime())) {
                    LogUtil.d("localDb->数据同步->数据库插入" + W100Utils.toString(sportHrateEntity));
                    this.mDatabaseHandler.insert(sportHrateEntity);
                }
            }
            close();
        }
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.sporthrate.SportHrateInterface
    public ArrayList<SportHrateEntity> querySportHrateToday(int i) {
        ArrayList<SportHrateEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(SyncDataUtils.getZeroTimeStamp(i), (86400 + r4) - 1);
            Collections.reverse(arrayListQueryArrayBetween);
            close();
        }
        return arrayListQueryArrayBetween;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.sporthrate.SportHrateInterface
    public ArrayList<SportHrateEntity> querySportHrateInInterval(int i, int i2) {
        ArrayList<SportHrateEntity> arrayListQueryArrayBetween;
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
