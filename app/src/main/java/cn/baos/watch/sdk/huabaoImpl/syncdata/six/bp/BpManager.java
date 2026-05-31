package cn.baos.watch.sdk.huabaoImpl.syncdata.six.bp;

import android.content.Context;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.database.six.bp.BpEntity;
import cn.baos.watch.sdk.database.six.bp.BpHandler;
import cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SyncDataUtils;
import cn.baos.watch.w100.messages.Sensor_data_blood_pressure;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class BpManager extends SyncDataBaseManager implements BpModeInterface {
    private static BpManager instance;
    private Context mContext;
    private BpHandler mDatabaseHandler;

    public static BpManager getInstance() {
        if (instance == null) {
            synchronized (BpManager.class) {
                if (instance == null) {
                    instance = new BpManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        BpHandler bpHandler = new BpHandler(context);
        this.mDatabaseHandler = bpHandler;
        bpHandler.createDatabase();
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

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.six.bp.BpModeInterface
    public void saveBpModeEntitiesToDb(Sensor_data_blood_pressure sensor_data_blood_pressure) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            LogUtil.d("数据同步->手表数据->运动记录: success");
            open();
            this.mDatabaseHandler.insert(sensor_data_blood_pressure);
            close();
        }
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.six.bp.BpModeInterface
    public ArrayList<BpEntity> queryBpModeToday(int i) {
        ArrayList<BpEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(SyncDataUtils.getZeroTimeStamp(i), (86400 + r4) - 1);
            Collections.reverse(arrayListQueryArrayBetween);
            close();
        }
        return arrayListQueryArrayBetween;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.six.bp.BpModeInterface
    public ArrayList<BpEntity> queryBpModeInInterval(int i, int i2) {
        ArrayList<BpEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(i, i2);
            Collections.reverse(arrayListQueryArrayBetween);
            LogUtil.d("查询区间内n天的 血压:" + ArrayUtils.toString(arrayListQueryArrayBetween));
            close();
        }
        return arrayListQueryArrayBetween;
    }
}
