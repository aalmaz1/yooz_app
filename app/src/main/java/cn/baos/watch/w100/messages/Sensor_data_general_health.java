package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_general_health extends Sensor_data {
    public int breathing_rate;
    public int reserve1;
    public int reserve2;
    public int reserve3;
    public int reserve4;
    public int stress;

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.stress);
        messagePacker.packLong(this.breathing_rate);
        messagePacker.packLong(this.reserve1);
        messagePacker.packLong(this.reserve2);
        messagePacker.packLong(this.reserve3);
        messagePacker.packLong(this.reserve4);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data_general_health load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.stress = (int) messageUnpacker.unpackLong();
        this.breathing_rate = (int) messageUnpacker.unpackLong();
        this.reserve1 = (int) messageUnpacker.unpackLong();
        this.reserve2 = (int) messageUnpacker.unpackLong();
        this.reserve3 = (int) messageUnpacker.unpackLong();
        this.reserve4 = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sensor_data_general_health() {
        this.catagory = CatagoryEnum.SENSOR_DATA_GENERAL_HEALTH;
    }
}
