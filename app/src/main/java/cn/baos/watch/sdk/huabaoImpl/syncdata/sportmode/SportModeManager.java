package cn.baos.watch.sdk.huabaoImpl.syncdata.sportmode;

import android.content.Context;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.database.fromwatch.sensordatasportmode.DatabaseSportModeHandler;
import cn.baos.watch.sdk.database.fromwatch.sensordatasportmode.SportModeEntity;
import cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sport.SportPhoneRecordDetailEntity;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SyncDataUtils;
import cn.baos.watch.sdk.util.W100Utils;
import cn.baos.watch.w100.messages.Sensor_data_sport_mode_array;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class SportModeManager extends SyncDataBaseManager implements SportModeInterface {
    private static SportModeManager instance;
    private Context mContext;
    private DatabaseSportModeHandler mDatabaseHandler;

    public static SportModeManager getInstance() {
        if (instance == null) {
            synchronized (SportModeManager.class) {
                if (instance == null) {
                    instance = new SportModeManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        DatabaseSportModeHandler databaseSportModeHandler = new DatabaseSportModeHandler(context);
        this.mDatabaseHandler = databaseSportModeHandler;
        databaseSportModeHandler.createDatabase();
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

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.sportmode.SportModeInterface
    public void saveSportModeEntitiesToDb(Sensor_data_sport_mode_array sensor_data_sport_mode_array) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            LogUtil.d("数据同步->手表数据->运动记录:" + W100Utils.toString(sensor_data_sport_mode_array));
            open();
            SportModeEntity sportModeEntity = new SportModeEntity();
            for (int i = 0; i < sensor_data_sport_mode_array.datas.length; i++) {
                sportModeEntity.setSensor_data_sport_mode(sensor_data_sport_mode_array.datas[i]);
                if (isRightData(sensor_data_sport_mode_array.datas[i].update_timestamp, getDatabaseHandler().queryLatestTime())) {
                    LogUtil.d("localDb->数据同步->数据库插入" + W100Utils.toString(sportModeEntity));
                    this.mDatabaseHandler.insert(sportModeEntity);
                }
            }
            close();
        }
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.sportmode.SportModeInterface
    public void saveSportModeEntitiesPhoneToDb(SportPhoneRecordDetailEntity sportPhoneRecordDetailEntity) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            LogUtil.d("数据同步->手表数据->手机运动记录:" + W100Utils.toString(sportPhoneRecordDetailEntity));
            open();
            this.mDatabaseHandler.insertToPhone(sportPhoneRecordDetailEntity);
            close();
        }
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.sportmode.SportModeInterface
    public ArrayList<SportModeEntity> querySportModeToday(int i) {
        ArrayList<SportModeEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(SyncDataUtils.getZeroTimeStamp(i), (86400 + r4) - 1);
            Collections.reverse(arrayListQueryArrayBetween);
            close();
        }
        return arrayListQueryArrayBetween;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.sportmode.SportModeInterface
    public ArrayList<SportModeEntity> querySportModeInInterval(int i, int i2) {
        ArrayList<SportModeEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(i, i2);
            Collections.reverse(arrayListQueryArrayBetween);
            LogUtil.d("查询区间内n天的运动静态数据:" + ArrayUtils.toString(arrayListQueryArrayBetween));
            close();
        }
        return arrayListQueryArrayBetween;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.sportmode.SportModeInterface
    public ArrayList<SportPhoneRecordDetailEntity> querySportModeInIntervalPhone(int i, int i2) {
        ArrayList<SportPhoneRecordDetailEntity> arrayListQueryArrayBetweenPhone;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetweenPhone = this.mDatabaseHandler.queryArrayBetweenPhone(i, i2);
            Collections.reverse(arrayListQueryArrayBetweenPhone);
            LogUtil.d("查询区间内n天的运动静态数据:" + ArrayUtils.toString(arrayListQueryArrayBetweenPhone));
            close();
        }
        return arrayListQueryArrayBetweenPhone;
    }
}
