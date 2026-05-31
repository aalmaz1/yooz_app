package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_blood_pressure extends Sensor_data {
    public int high;
    public int low;

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.high);
        messagePacker.packLong(this.low);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data_blood_pressure load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.high = (int) messageUnpacker.unpackLong();
        this.low = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sensor_data_blood_pressure() {
        this.catagory = CatagoryEnum.SENSOR_DATA_BLOOD_PRESSURE;
    }
}
