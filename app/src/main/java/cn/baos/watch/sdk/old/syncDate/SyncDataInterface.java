package cn.baos.watch.sdk.old.syncDate;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public interface SyncDataInterface {
    int getSyncStatus();

    void startLoadDateFromServer(Context context);
}
