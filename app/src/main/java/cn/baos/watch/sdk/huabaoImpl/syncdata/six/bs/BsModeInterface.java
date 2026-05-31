package cn.baos.watch.sdk.huabaoImpl.syncdata.six.bs;

import cn.baos.watch.sdk.database.six.bs.BsEntity;
import cn.baos.watch.w100.messages.Sensor_data_blood_sugar;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface BsModeInterface {
    ArrayList<BsEntity> queryBsModeInInterval(int i, int i2);

    ArrayList<BsEntity> queryBsModeToday(int i);

    void saveBsModeEntitiesToDb(Sensor_data_blood_sugar sensor_data_blood_sugar);
}
