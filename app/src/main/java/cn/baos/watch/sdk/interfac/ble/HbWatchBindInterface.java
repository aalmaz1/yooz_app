package cn.baos.watch.sdk.interfac.ble;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public interface HbWatchBindInterface {
    void connectWatchByBindWatch(Context context);

    String getBindWatchAddress(Context context);

    boolean hasBindWatch(Context context);

    void unBindWatch(Context context);
}
