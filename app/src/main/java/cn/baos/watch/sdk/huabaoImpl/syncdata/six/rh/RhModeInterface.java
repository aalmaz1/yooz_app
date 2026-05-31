package cn.baos.watch.sdk.huabaoImpl.syncdata.six.rh;

import cn.baos.watch.sdk.database.six.rh.RhEntity;
import cn.baos.watch.w100.messages.Sensor_data_general_health;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface RhModeInterface {
    ArrayList<RhEntity> queryRhModeInInterval(int i, int i2);

    ArrayList<RhEntity> queryRhModeToday(int i);

    void savRhModeEntitiesToDb(Sensor_data_general_health sensor_data_general_health);
}
