package cn.baos.watch.sdk.huabaoImpl.syncdata.sleepstatus;

import cn.baos.watch.sdk.database.fromwatch.sensordatasleepstatus.SleepStatusEntity;
import cn.baos.watch.w100.messages.Sensor_data_sleep_status_array;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface SleepStatusInterface {
    ArrayList<SleepStatusEntity> querySleepStatusInInterval(int i, int i2);

    ArrayList<SleepStatusEntity> querySleepStatusToday(int i);

    void saveSleepStatusEntitiesToDb(Sensor_data_sleep_status_array sensor_data_sleep_status_array);
}
