package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_daily_active_array extends Serializable {
    public Sensor_data_daily_active_sum[] datas;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.datas != null) {
            messagePacker.packLong(r0.length);
            Sensor_data_daily_active_sum[] sensor_data_daily_active_sumArr = this.datas;
            if (sensor_data_daily_active_sumArr.length <= 0) {
                return true;
            }
            for (Sensor_data_daily_active_sum sensor_data_daily_active_sum : sensor_data_daily_active_sumArr) {
                sensor_data_daily_active_sum.put(messagePacker);
            }
            return true;
        }
        messagePacker.packLong(0L);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Sensor_data_daily_active_array load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.datas = new Sensor_data_daily_active_sum[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.datas[i] = new Sensor_data_daily_active_sum();
                this.datas[i].load(messageUnpacker);
            }
        }
        return this;
    }

    public Sensor_data_daily_active_array() {
        this.catagory = CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_ARRAY;
    }
}
