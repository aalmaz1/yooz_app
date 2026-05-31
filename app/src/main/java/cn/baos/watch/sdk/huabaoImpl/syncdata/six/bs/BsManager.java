package cn.baos.watch.sdk.huabaoImpl.syncdata.six.bs;

import android.content.Context;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.database.six.bs.BsEntity;
import cn.baos.watch.sdk.database.six.bs.BsHandler;
import cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SyncDataUtils;
import cn.baos.watch.w100.messages.Sensor_data_blood_sugar;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class BsManager extends SyncDataBaseManager implements BsModeInterface {
    private static BsManager instance;
    private Context mContext;
    private BsHandler mDatabaseHandler;

    public static BsManager getInstance() {
        if (instance == null) {
            synchronized (BsManager.class) {
                if (instance == null) {
                    instance = new BsManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        BsHandler bsHandler = new BsHandler(context);
        this.mDatabaseHandler = bsHandler;
        bsHandler.createDatabase();
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

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.six.bs.BsModeInterface
    public void saveBsModeEntitiesToDb(Sensor_data_blood_sugar sensor_data_blood_sugar) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            LogUtil.d("数据同步->手表数据->血糖: success");
            open();
            this.mDatabaseHandler.insert(sensor_data_blood_sugar);
            close();
        }
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.six.bs.BsModeInterface
    public ArrayList<BsEntity> queryBsModeToday(int i) {
        ArrayList<BsEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(SyncDataUtils.getZeroTimeStamp(i), (86400 + r4) - 1);
            Collections.reverse(arrayListQueryArrayBetween);
            close();
        }
        return arrayListQueryArrayBetween;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.six.bs.BsModeInterface
    public ArrayList<BsEntity> queryBsModeInInterval(int i, int i2) {
        ArrayList<BsEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(i, i2);
            Collections.reverse(arrayListQueryArrayBetween);
            LogUtil.d("查询区间内n天的 血糖:" + ArrayUtils.toString(arrayListQueryArrayBetween));
            close();
        }
        return arrayListQueryArrayBetween;
    }
}
