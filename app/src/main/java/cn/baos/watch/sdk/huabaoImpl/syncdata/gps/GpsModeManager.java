package cn.baos.watch.sdk.huabaoImpl.syncdata.gps;

import android.content.Context;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.database.gps.GpsLocHandler;
import cn.baos.watch.sdk.database.gps.GpslocEntity;
import cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataBaseManager;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SyncDataUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class GpsModeManager extends SyncDataBaseManager implements GpsModeInterface {
    private static GpsModeManager instance;
    private Context mContext;
    private GpsLocHandler mDatabaseHandler;

    public static GpsModeManager getInstance() {
        if (instance == null) {
            synchronized (GpsModeManager.class) {
                if (instance == null) {
                    instance = new GpsModeManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        GpsLocHandler gpsLocHandler = new GpsLocHandler(context);
        this.mDatabaseHandler = gpsLocHandler;
        gpsLocHandler.createDatabase();
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

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.gps.GpsModeInterface
    public void saveGpsModeEntitiesToDb(GpslocEntity gpslocEntity) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            LogUtil.d("数据同步->手表数据->运动记录: success");
            open();
            this.mDatabaseHandler.insert(gpslocEntity);
            close();
        }
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.gps.GpsModeInterface
    public ArrayList<GpslocEntity> queryGpsModeToday(int i) {
        ArrayList<GpslocEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(SyncDataUtils.getZeroTimeStamp(i), (86400 + r4) - 1);
            Collections.reverse(arrayListQueryArrayBetween);
            close();
        }
        return arrayListQueryArrayBetween;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.gps.GpsModeInterface
    public ArrayList<GpslocEntity> queryGpsModeInInterval(int i, int i2) {
        ArrayList<GpslocEntity> arrayListQueryArrayBetween;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(i, i2);
            Collections.reverse(arrayListQueryArrayBetween);
            LogUtil.d("查询区间内n天的运动静态数据:" + ArrayUtils.toString(arrayListQueryArrayBetween));
            close();
        }
        return arrayListQueryArrayBetween;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.syncdata.gps.GpsModeInterface
    public void deleteGpsModeInInterval(int i, int i2) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            ArrayList<GpslocEntity> arrayListQueryArrayBetween = this.mDatabaseHandler.queryArrayBetween(i, i2);
            if (arrayListQueryArrayBetween != null && arrayListQueryArrayBetween.size() > 0) {
                Iterator<GpslocEntity> it = arrayListQueryArrayBetween.iterator();
                while (it.hasNext()) {
                    this.mDatabaseHandler.delete(it.next());
                }
            }
            LogUtil.d("查询并删除区间内n天的运动静态数据:" + ArrayUtils.toString(arrayListQueryArrayBetween));
            close();
        }
    }
}
