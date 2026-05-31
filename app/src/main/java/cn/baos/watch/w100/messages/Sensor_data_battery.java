package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_battery extends Sensor_data {
    public int battery_level;
    public int charging;

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.battery_level);
        messagePacker.packLong(this.charging);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data_battery load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.battery_level = (int) messageUnpacker.unpackLong();
        this.charging = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sensor_data_battery() {
        this.catagory = CatagoryEnum.SENSOR_DATA_BATTERY;
    }
}
