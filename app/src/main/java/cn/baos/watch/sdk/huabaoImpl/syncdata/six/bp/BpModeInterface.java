package cn.baos.watch.sdk.huabaoImpl.syncdata.six.bp;

import cn.baos.watch.sdk.database.six.bp.BpEntity;
import cn.baos.watch.w100.messages.Sensor_data_blood_pressure;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface BpModeInterface {
    ArrayList<BpEntity> queryBpModeInInterval(int i, int i2);

    ArrayList<BpEntity> queryBpModeToday(int i);

    void saveBpModeEntitiesToDb(Sensor_data_blood_pressure sensor_data_blood_pressure);
}
