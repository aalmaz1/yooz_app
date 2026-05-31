package cn.baos.watch.sdk.huabaoImpl.syncdata.six.temp;

import android.content.Context;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.database.six.temp.TempEntity;
import cn.baos.watch.sdk.database.six.temp.TempHandler;
import cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SyncDataUtils;
import cn.baos.watch.w100.messages.Sensor_data_temperature;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class TempManager extends SyncDataBaseManager implements TempModeInterface {
    private static TempManager instance;
    private Context mContext;
    private TempHandler mDatabaseHandler;

    public static TempManager getInstance() {
        if (instance == null) {
            synchronized (TempManager.class) {
                if (instance == null) {
                    instance = new TempManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        TempHandler tempHandler = new TempHandler(context);
        this.mDatabaseHandler = tempHandler;
        tempHandler.createDatabase();
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

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.six.temp.TempModeInterface
    public void savTempModeEntitiesToDb(Sensor_data_temperature sensor_data_temperature) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            LogUtil.d("数据同步->手表数据-> 温度 数据: success");
            open();
            this.mDatabaseHandler.insert(sensor_data_temperature);
            close();
        }
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.six.temp.TempModeInterface
    public ArrayList<TempEntity> queryTempModeToday(int i) {
        ArrayList<TempEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(SyncDataUtils.getZeroTimeStamp(i), (86400 + r4) - 1);
            Collections.reverse(arrayListQueryArrayBetween);
            close();
        }
        return arrayListQueryArrayBetween;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.six.temp.TempModeInterface
    public ArrayList<TempEntity> queryTempModeInInterval(int i, int i2) {
        ArrayList<TempEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(i, i2);
            Collections.reverse(arrayListQueryArrayBetween);
            LogUtil.d("查询区间内n天的温度数据:" + ArrayUtils.toString(arrayListQueryArrayBetween));
            close();
        }
        return arrayListQueryArrayBetween;
    }
}
