package cn.baos.watch.sdk.interfac.syncdata;

import cn.baos.watch.w100.messages.Sensor_data_daily_active_sum;
import cn.baos.watch.w100.messages.Sensor_data_daily_hrate;
import cn.baos.watch.w100.messages.Sensor_data_daily_spo;

/* JADX INFO: loaded from: classes.dex */
public interface SyncRightNowDataCallback {
    void onDailyActiveSum(Sensor_data_daily_active_sum sensor_data_daily_active_sum);

    void onDailyHrate(Sensor_data_daily_hrate sensor_data_daily_hrate);

    void onDailySpo(Sensor_data_daily_spo sensor_data_daily_spo);
}
