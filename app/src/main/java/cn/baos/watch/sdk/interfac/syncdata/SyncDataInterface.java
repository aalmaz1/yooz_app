package cn.baos.watch.sdk.interfac.syncdata;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public interface SyncDataInterface {
    int getSyncStatus();

    void startSyncDateFromWatch(Context context, SyncStatusCallback syncStatusCallback);

    void startSyncRightNowData(Context context, int i, SyncRightNowDataCallback syncRightNowDataCallback);
}
