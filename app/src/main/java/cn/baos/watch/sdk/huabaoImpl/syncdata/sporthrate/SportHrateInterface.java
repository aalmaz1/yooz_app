package cn.baos.watch.sdk.huabaoImpl.syncdata.sporthrate;

import cn.baos.watch.sdk.database.fromwatch.sensordatasporthrate.SportHrateEntity;
import cn.baos.watch.w100.messages.Sensor_data_sport_hrate_array;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface SportHrateInterface {
    ArrayList<SportHrateEntity> querySportHrateInInterval(int i, int i2);

    ArrayList<SportHrateEntity> querySportHrateToday(int i);

    void saveSportHrateEntitiesToDb(Sensor_data_sport_hrate_array sensor_data_sport_hrate_array);
}
