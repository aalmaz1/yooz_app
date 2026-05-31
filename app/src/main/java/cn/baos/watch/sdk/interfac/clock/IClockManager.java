package cn.baos.watch.sdk.interfac.clock;

import cn.baos.watch.sdk.entitiy.ClockListEntity;

/* JADX INFO: loaded from: classes.dex */
public interface IClockManager {
    void addAlarm(ClockListEntity clockListEntity, OnCrudClockDataListener onCrudClockDataListener);

    void deleteAlarm(ClockListEntity clockListEntity, OnCrudClockDataListener onCrudClockDataListener);

    void requestClockListToWatch(OnGetClockDataListener onGetClockDataListener);

    void updateAlarm(ClockListEntity clockListEntity, OnCrudClockDataListener onCrudClockDataListener);
}
