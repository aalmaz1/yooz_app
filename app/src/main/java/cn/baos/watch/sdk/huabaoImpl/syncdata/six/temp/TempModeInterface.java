package cn.baos.watch.sdk.huabaoImpl.syncdata.six.temp;

import cn.baos.watch.sdk.database.six.temp.TempEntity;
import cn.baos.watch.w100.messages.Sensor_data_temperature;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface TempModeInterface {
    ArrayList<TempEntity> queryTempModeInInterval(int i, int i2);

    ArrayList<TempEntity> queryTempModeToday(int i);

    void savTempModeEntitiesToDb(Sensor_data_temperature sensor_data_temperature);
}
