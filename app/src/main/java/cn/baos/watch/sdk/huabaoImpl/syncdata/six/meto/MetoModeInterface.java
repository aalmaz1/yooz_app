package cn.baos.watch.sdk.huabaoImpl.syncdata.six.meto;

import cn.baos.watch.sdk.database.six.meto.MetoEntity;
import cn.baos.watch.w100.messages.Sensor_data_daily_active_sum_v2;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface MetoModeInterface {
    ArrayList<MetoEntity> queryMetoModeInInterval(int i, int i2);

    ArrayList<MetoEntity> queryMetoModeToday(int i);

    void saveMetoModeEntitiesToDb(Sensor_data_daily_active_sum_v2 sensor_data_daily_active_sum_v2);
}
