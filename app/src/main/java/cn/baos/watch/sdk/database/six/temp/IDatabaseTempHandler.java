package cn.baos.watch.sdk.database.six.temp;

import cn.baos.watch.w100.messages.Sensor_data_temperature;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IDatabaseTempHandler {
    void close();

    void createDatabase();

    void delete(Sensor_data_temperature sensor_data_temperature);

    void insert(Sensor_data_temperature sensor_data_temperature);

    void open();

    ArrayList<TempEntity> queryArrayBetween(int i, int i2);

    void update(Sensor_data_temperature sensor_data_temperature);
}
