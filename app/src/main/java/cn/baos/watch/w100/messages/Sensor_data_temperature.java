package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_temperature extends Sensor_data {
    public int value;

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.value);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data_temperature load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.value = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sensor_data_temperature() {
        this.catagory = CatagoryEnum.SENSOR_DATA_TEMPERATURE;
    }
}
