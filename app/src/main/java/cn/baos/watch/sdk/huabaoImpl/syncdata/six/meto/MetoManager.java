package cn.baos.watch.sdk.huabaoImpl.syncdata.six.meto;

import android.content.Context;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.database.six.meto.MetoEntity;
import cn.baos.watch.sdk.database.six.meto.MetoHandler;
import cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SyncDataUtils;
import cn.baos.watch.w100.messages.Sensor_data_daily_active_sum_v2;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class MetoManager extends SyncDataBaseManager implements MetoModeInterface {
    private static MetoManager instance;
    private Context mContext;
    private MetoHandler mDatabaseHandler;

    public static MetoManager getInstance() {
        if (instance == null) {
            synchronized (MetoManager.class) {
                if (instance == null) {
                    instance = new MetoManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        MetoHandler metoHandler = new MetoHandler(context);
        this.mDatabaseHandler = metoHandler;
        metoHandler.createDatabase();
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

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.six.meto.MetoModeInterface
    public void saveMetoModeEntitiesToDb(Sensor_data_daily_active_sum_v2 sensor_data_daily_active_sum_v2) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            LogUtil.d("数据同步->手表数据->梅托: success");
            open();
            this.mDatabaseHandler.insert(sensor_data_daily_active_sum_v2);
            close();
        }
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.six.meto.MetoModeInterface
    public ArrayList<MetoEntity> queryMetoModeToday(int i) {
        ArrayList<MetoEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(SyncDataUtils.getZeroTimeStamp(i), (86400 + r4) - 1);
            Collections.reverse(arrayListQueryArrayBetween);
            close();
        }
        return arrayListQueryArrayBetween;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.six.meto.MetoModeInterface
    public ArrayList<MetoEntity> queryMetoModeInInterval(int i, int i2) {
        ArrayList<MetoEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(i, i2);
            Collections.reverse(arrayListQueryArrayBetween);
            LogUtil.d("查询区间内n天的梅托:" + ArrayUtils.toString(arrayListQueryArrayBetween));
            close();
        }
        return arrayListQueryArrayBetween;
    }
}
