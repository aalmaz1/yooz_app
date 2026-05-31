package cn.baos.watch.sdk.database.six.meto;

import cn.baos.watch.w100.messages.Sensor_data_daily_active_sum_v2;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IDatabaseMetoHandler {
    void close();

    void createDatabase();

    void delete(Sensor_data_daily_active_sum_v2 sensor_data_daily_active_sum_v2);

    void insert(Sensor_data_daily_active_sum_v2 sensor_data_daily_active_sum_v2);

    void open();

    ArrayList<MetoEntity> queryArrayBetween(int i, int i2);

    void update(Sensor_data_daily_active_sum_v2 sensor_data_daily_active_sum_v2);
}
