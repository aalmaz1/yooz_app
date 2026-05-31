package cn.baos.watch.sdk.interfac.clock;

import cn.baos.watch.sdk.entitiy.ClockListEntity;

/* JADX INFO: loaded from: classes.dex */
public interface OnGetClockDataListener {
    void onGetClockData(ClockListEntity clockListEntity);

    void onGetClockDateFinish();
}
