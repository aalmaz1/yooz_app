package cn.baos.watch.sdk.database.six.bs;

import cn.baos.watch.w100.messages.Sensor_data_blood_sugar;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface IDatabaseBsHandler {
    void close();

    void createDatabase();

    void delete(Sensor_data_blood_sugar sensor_data_blood_sugar);

    void insert(Sensor_data_blood_sugar sensor_data_blood_sugar);

    void open();

    ArrayList<BsEntity> queryArrayBetween(int i, int i2);

    void update(Sensor_data_blood_sugar sensor_data_blood_sugar);
}
