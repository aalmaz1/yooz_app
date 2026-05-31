package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_m extends Sensor_data {
    public int unknown;

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.unknown);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data_m load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.unknown = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sensor_data_m() {
        this.catagory = CatagoryEnum.SENSOR_DATA_M;
    }
}
