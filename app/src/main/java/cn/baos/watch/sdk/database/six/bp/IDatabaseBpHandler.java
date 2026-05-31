package cn.baos.watch.sdk.database.six.bp;

import cn.baos.watch.w100.messages.Sensor_data_blood_pressure;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IDatabaseBpHandler {
    void close();

    void createDatabase();

    void delete(Sensor_data_blood_pressure sensor_data_blood_pressure);

    void insert(Sensor_data_blood_pressure sensor_data_blood_pressure);

    void open();

    ArrayList<BpEntity> queryArrayBetween(int i, int i2);

    void update(Sensor_data_blood_pressure sensor_data_blood_pressure);
}
