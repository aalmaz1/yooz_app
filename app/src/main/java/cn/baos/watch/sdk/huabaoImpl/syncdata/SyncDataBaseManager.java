package cn.baos.watch.sdk.huabaoImpl.syncdata;

import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public abstract class SyncDataBaseManager {
    public abstract void close();

    public abstract DataBaseFartherHandler getDatabaseHandler();

    public abstract void open();

    public int queryLatestTime() {
        int iQueryLatestTime;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            iQueryLatestTime = getDatabaseHandler().queryLatestTime();
            LogUtil.d("查询区间内最近一天的时间戳");
            close();
        }
        return iQueryLatestTime;
    }

    public int queryLatestTime(int i) {
        int iQueryLatestTime;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            open();
            iQueryLatestTime = getDatabaseHandler().queryLatestTime(i);
            LogUtil.d("查询区间内最近一天的时间戳");
            close();
        }
        return iQueryLatestTime;
    }

    public boolean isRightData(int i, int i2) {
        if (i > i2) {
            return true;
        }
        LogUtil.d("数据同步->localDb->数据库插入失败:原因时间戳比数据库中的最晚时间戳要早或相等，数据时间戳:" + i + " 数据库最晚时间戳:" + i2);
        return false;
    }
}
