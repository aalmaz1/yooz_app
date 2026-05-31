package cn.baos.watch.sdk.huabaoImpl.syncdata.sportrecord;

import android.content.Context;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.database.fromwatch.sportrecord.DatabaseSportRecordFromWatchHandler;
import cn.baos.watch.sdk.database.fromwatch.sportrecord.SportRecordFromWatchEntity;
import cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sport.SportPhoneRecordEntity;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SyncDataUtils;
import cn.baos.watch.sdk.util.W100Utils;
import cn.baos.watch.w100.messages.Sport_record_array;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class SportRecordFromWatchManager extends SyncDataBaseManager implements SportRecordFromWatchInterface {
    private static SportRecordFromWatchManager instance;
    private Context mContext;
    private DatabaseSportRecordFromWatchHandler mDatabaseHandler;

    public static SportRecordFromWatchManager getInstance() {
        if (instance == null) {
            synchronized (SportRecordFromWatchManager.class) {
                if (instance == null) {
                    instance = new SportRecordFromWatchManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        DatabaseSportRecordFromWatchHandler databaseSportRecordFromWatchHandler = new DatabaseSportRecordFromWatchHandler(context);
        this.mDatabaseHandler = databaseSportRecordFromWatchHandler;
        databaseSportRecordFromWatchHandler.createDatabase();
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

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.sportrecord.SportRecordFromWatchInterface
    public void saveSportRecordFromPhoneEntitiesToDb(SportPhoneRecordEntity sportPhoneRecordEntity) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            LogUtil.d("数据同步->手表数据->手机运动记录:" + W100Utils.toString(sportPhoneRecordEntity));
            open();
            if (isRightData(sportPhoneRecordEntity.update_timestamp, getDatabaseHandler().queryLatestTime())) {
                LogUtil.d("localDb->数据同步->数据库插入" + W100Utils.toString(sportPhoneRecordEntity));
                this.mDatabaseHandler.insertPhone(sportPhoneRecordEntity);
            }
            close();
        }
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.sportrecord.SportRecordFromWatchInterface
    public void saveSportRecordFromWatchEntitiesToDb(Sport_record_array sport_record_array) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            LogUtil.d("数据同步->手表数据->运动记录:" + W100Utils.toString(sport_record_array));
            open();
            SportRecordFromWatchEntity sportRecordFromWatchEntity = new SportRecordFromWatchEntity();
            for (int i = 0; i < sport_record_array.datas.length; i++) {
                sportRecordFromWatchEntity.setSport_record(sport_record_array.datas[i]);
                if (isRightData(sport_record_array.datas[i].update_timestamp, getDatabaseHandler().queryLatestTime())) {
                    LogUtil.d("localDb->数据同步->数据库插入" + W100Utils.toString(sportRecordFromWatchEntity));
                    this.mDatabaseHandler.insert(sportRecordFromWatchEntity);
                }
            }
            close();
        }
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.sportrecord.SportRecordFromWatchInterface
    public ArrayList<SportRecordFromWatchEntity> querySportRecordFromWatchToday(int i) {
        ArrayList<SportRecordFromWatchEntity> arrayListQuerySportRecordTodayNoOpenClose;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQuerySportRecordTodayNoOpenClose = querySportRecordTodayNoOpenClose(i);
            close();
        }
        return arrayListQuerySportRecordTodayNoOpenClose;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.sportrecord.SportRecordFromWatchInterface
    public ArrayList<SportRecordFromWatchEntity> querySportRecordFromWatchInInterval(int i, int i2) {
        ArrayList<SportRecordFromWatchEntity> arrayListQuerySportRecordInIntervalNoOpenClose;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQuerySportRecordInIntervalNoOpenClose = querySportRecordInIntervalNoOpenClose(i, i2);
            close();
        }
        return arrayListQuerySportRecordInIntervalNoOpenClose;
    }

    private ArrayList<SportRecordFromWatchEntity> querySportRecordTodayNoOpenClose(int i) throws Exception {
        ArrayList<SportRecordFromWatchEntity> arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(SyncDataUtils.getZeroTimeStamp(i), (86400 + r3) - 1);
        Collections.reverse(arrayListQueryArrayBetween);
        return arrayListQueryArrayBetween;
    }

    private ArrayList<SportRecordFromWatchEntity> querySportRecordInIntervalNoOpenClose(int i, int i2) throws Exception {
        ArrayList<SportRecordFromWatchEntity> arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(SyncDataUtils.getZeroTimeStamp(i), (SyncDataUtils.getZeroTimeStamp(i2) + 86400) - 1);
        Collections.reverse(arrayListQueryArrayBetween);
        LogUtil.d("查询区间内n天的运动静态数据:" + ArrayUtils.toString(arrayListQueryArrayBetween));
        return arrayListQueryArrayBetween;
    }
}
