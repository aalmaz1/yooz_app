package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_sleep_stats_array extends Serializable {
    public Sensor_data_sleep_stats[] datas;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.datas != null) {
            messagePacker.packLong(r0.length);
            Sensor_data_sleep_stats[] sensor_data_sleep_statsArr = this.datas;
            if (sensor_data_sleep_statsArr.length <= 0) {
                return true;
            }
            for (Sensor_data_sleep_stats sensor_data_sleep_stats : sensor_data_sleep_statsArr) {
                sensor_data_sleep_stats.put(messagePacker);
            }
            return true;
        }
        messagePacker.packLong(0L);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Sensor_data_sleep_stats_array load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.datas = new Sensor_data_sleep_stats[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.datas[i] = new Sensor_data_sleep_stats();
                this.datas[i].load(messageUnpacker);
            }
        }
        return this;
    }

    public Sensor_data_sleep_stats_array() {
        this.catagory = CatagoryEnum.SENSOR_DATA_SLEEP_STATS_ARRAY;
    }
}
