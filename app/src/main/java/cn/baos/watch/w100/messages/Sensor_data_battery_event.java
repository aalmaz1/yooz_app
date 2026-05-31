package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_battery_event extends MessageBase {
    public int event_type;
    public int value;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.event_type);
        messagePacker.packLong(this.value);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data_battery_event load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.event_type = (int) messageUnpacker.unpackLong();
        this.value = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sensor_data_battery_event() {
        this.catagory = CatagoryEnum.SENSOR_DATA_BATTERY_EVENT;
    }
}
