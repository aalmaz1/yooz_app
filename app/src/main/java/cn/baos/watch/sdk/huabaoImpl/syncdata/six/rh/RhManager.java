package cn.baos.watch.sdk.huabaoImpl.syncdata.six.rh;

import android.content.Context;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.database.six.rh.RhEntity;
import cn.baos.watch.sdk.database.six.rh.RhHandler;
import cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SyncDataUtils;
import cn.baos.watch.w100.messages.Sensor_data_general_health;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class RhManager extends SyncDataBaseManager implements RhModeInterface {
    private static RhManager instance;
    private Context mContext;
    private RhHandler mDatabaseHandler;

    public static RhManager getInstance() {
        if (instance == null) {
            synchronized (RhManager.class) {
                if (instance == null) {
                    instance = new RhManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        RhHandler rhHandler = new RhHandler(context);
        this.mDatabaseHandler = rhHandler;
        rhHandler.createDatabase();
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

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.six.rh.RhModeInterface
    public void savRhModeEntitiesToDb(Sensor_data_general_health sensor_data_general_health) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            LogUtil.d("数据同步->手表数据->压力和呼吸频率数据: success");
            open();
            this.mDatabaseHandler.insert(sensor_data_general_health);
            close();
        }
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.six.rh.RhModeInterface
    public ArrayList<RhEntity> queryRhModeToday(int i) {
        ArrayList<RhEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(SyncDataUtils.getZeroTimeStamp(i), (86400 + r4) - 1);
            Collections.reverse(arrayListQueryArrayBetween);
            close();
        }
        return arrayListQueryArrayBetween;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.six.rh.RhModeInterface
    public ArrayList<RhEntity> queryRhModeInInterval(int i, int i2) {
        ArrayList<RhEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(i, i2);
            Collections.reverse(arrayListQueryArrayBetween);
            LogUtil.d("查询区间内n天的压力和呼吸频率数据:" + ArrayUtils.toString(arrayListQueryArrayBetween));
            close();
        }
        return arrayListQueryArrayBetween;
    }
}
