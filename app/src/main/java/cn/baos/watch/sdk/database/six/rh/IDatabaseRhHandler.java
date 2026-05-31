package cn.baos.watch.sdk.database.six.rh;

import cn.baos.watch.w100.messages.Sensor_data_general_health;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IDatabaseRhHandler {
    void close();

    void createDatabase();

    void delete(Sensor_data_general_health sensor_data_general_health);

    void insert(Sensor_data_general_health sensor_data_general_health);

    void open();

    ArrayList<RhEntity> queryArrayBetween(int i, int i2);

    void update(Sensor_data_general_health sensor_data_general_health);
}
