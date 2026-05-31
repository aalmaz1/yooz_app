package cn.baos.watch.sdk.huabaoImpl.clock;

import cn.baos.watch.sdk.entitiy.ClockListEntity;
import cn.baos.watch.sdk.interfac.clock.IClockManager;
import cn.baos.watch.sdk.interfac.clock.OnCrudClockDataListener;
import cn.baos.watch.sdk.interfac.clock.OnGetClockDataListener;
import cn.baos.watch.sdk.manager.clock.ClockAgendaCrudUtils;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class ClockManagerImpl implements IClockManager {
    private static ClockManagerImpl instance;

    public static ClockManagerImpl getInstance() {
        if (instance == null) {
            synchronized (ClockManagerImpl.class) {
                if (instance == null) {
                    instance = new ClockManagerImpl();
                }
            }
        }
        return instance;
    }

    @Override // cn.baos.watch.sdk.interfac.clock.IClockManager
    public void requestClockListToWatch(final OnGetClockDataListener onGetClockDataListener) {
        MessageManager.getInstance().requestClockListToWatch(new OnGetClockDataListener() { // from class: cn.baos.watch.sdk.huabaoImpl.clock.ClockManagerImpl.1
            @Override // cn.baos.watch.sdk.interfac.clock.OnGetClockDataListener
            public void onGetClockData(ClockListEntity clockListEntity) {
                LogUtil.d(String.format("拉取闹钟:%s", clockListEntity.toString()));
                OnGetClockDataListener onGetClockDataListener2 = onGetClockDataListener;
                if (onGetClockDataListener2 != null) {
                    onGetClockDataListener2.onGetClockData(clockListEntity);
                }
            }

            @Override // cn.baos.watch.sdk.interfac.clock.OnGetClockDataListener
            public void onGetClockDateFinish() {
                LogUtil.d("拉取闹钟完成:结束");
                OnGetClockDataListener onGetClockDataListener2 = onGetClockDataListener;
                if (onGetClockDataListener2 != null) {
                    onGetClockDataListener2.onGetClockDateFinish();
                }
            }
        });
    }

    @Override // cn.baos.watch.sdk.interfac.clock.IClockManager
    public void addAlarm(final ClockListEntity clockListEntity, final OnCrudClockDataListener onCrudClockDataListener) {
        clockListEntity.setCrudState(0);
        ClockAgendaCrudUtils.getInstance().setAlarmToWatchByPhone(clockListEntity, new OnCrudClockDataListener() { // from class: cn.baos.watch.sdk.huabaoImpl.clock.ClockManagerImpl.2
            @Override // cn.baos.watch.sdk.interfac.clock.OnCrudClockDataListener
            public void onCrudClockSuccess(int i, int i2) {
                clockListEntity.setId(i2);
                LogUtil.d("闹钟管理 addAlarm success:" + clockListEntity.toString());
                OnCrudClockDataListener onCrudClockDataListener2 = onCrudClockDataListener;
                if (onCrudClockDataListener2 != null) {
                    onCrudClockDataListener2.onCrudClockSuccess(i, i2);
                }
            }

            @Override // cn.baos.watch.sdk.interfac.clock.OnCrudClockDataListener
            public void onCrudClockFail(int i) {
                LogUtil.d("闹钟管理 addAlarm fail:" + clockListEntity.toString());
                OnCrudClockDataListener onCrudClockDataListener2 = onCrudClockDataListener;
                if (onCrudClockDataListener2 != null) {
                    onCrudClockDataListener2.onCrudClockFail(i);
                }
            }
        });
    }

    @Override // cn.baos.watch.sdk.interfac.clock.IClockManager
    public void deleteAlarm(final ClockListEntity clockListEntity, final OnCrudClockDataListener onCrudClockDataListener) {
        clockListEntity.setCrudState(0);
        ClockAgendaCrudUtils.getInstance().deleteAlarmToWatchByPhone(clockListEntity, new OnCrudClockDataListener() { // from class: cn.baos.watch.sdk.huabaoImpl.clock.ClockManagerImpl.3
            @Override // cn.baos.watch.sdk.interfac.clock.OnCrudClockDataListener
            public void onCrudClockSuccess(int i, int i2) {
                clockListEntity.setId(i2);
                LogUtil.d("闹钟管理 deleteAlarm success:" + clockListEntity.toString());
                OnCrudClockDataListener onCrudClockDataListener2 = onCrudClockDataListener;
                if (onCrudClockDataListener2 != null) {
                    onCrudClockDataListener2.onCrudClockSuccess(i, i2);
                }
            }

            @Override // cn.baos.watch.sdk.interfac.clock.OnCrudClockDataListener
            public void onCrudClockFail(int i) {
                LogUtil.d("闹钟管理 deleteAlarm fail:" + clockListEntity.toString());
                OnCrudClockDataListener onCrudClockDataListener2 = onCrudClockDataListener;
                if (onCrudClockDataListener2 != null) {
                    onCrudClockDataListener2.onCrudClockFail(i);
                }
            }
        });
    }

    @Override // cn.baos.watch.sdk.interfac.clock.IClockManager
    public void updateAlarm(final ClockListEntity clockListEntity, final OnCrudClockDataListener onCrudClockDataListener) {
        clockListEntity.setCrudState(1);
        ClockAgendaCrudUtils.getInstance().updateAlarmToWatchByPhone(clockListEntity, new OnCrudClockDataListener() { // from class: cn.baos.watch.sdk.huabaoImpl.clock.ClockManagerImpl.4
            @Override // cn.baos.watch.sdk.interfac.clock.OnCrudClockDataListener
            public void onCrudClockSuccess(int i, int i2) {
                clockListEntity.setId(i2);
                LogUtil.d("闹钟管理 updateAlarm success:" + clockListEntity.toString());
                OnCrudClockDataListener onCrudClockDataListener2 = onCrudClockDataListener;
                if (onCrudClockDataListener2 != null) {
                    onCrudClockDataListener2.onCrudClockSuccess(i, i2);
                }
            }

            @Override // cn.baos.watch.sdk.interfac.clock.OnCrudClockDataListener
            public void onCrudClockFail(int i) {
                LogUtil.d("闹钟管理 updateAlarm fail:" + clockListEntity.toString());
                OnCrudClockDataListener onCrudClockDataListener2 = onCrudClockDataListener;
                if (onCrudClockDataListener2 != null) {
                    onCrudClockDataListener2.onCrudClockFail(i);
                }
            }
        });
    }
}
