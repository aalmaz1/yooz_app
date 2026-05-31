package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_g extends Sensor_data {
    public int acc_x;
    public int acc_y;
    public int acc_z;
    public int gyr_x;
    public int gyr_y;
    public int gyr_z;

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.acc_x);
        messagePacker.packLong(this.acc_y);
        messagePacker.packLong(this.acc_z);
        messagePacker.packLong(this.gyr_x);
        messagePacker.packLong(this.gyr_y);
        messagePacker.packLong(this.gyr_z);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data_g load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.acc_x = (int) messageUnpacker.unpackLong();
        this.acc_y = (int) messageUnpacker.unpackLong();
        this.acc_z = (int) messageUnpacker.unpackLong();
        this.gyr_x = (int) messageUnpacker.unpackLong();
        this.gyr_y = (int) messageUnpacker.unpackLong();
        this.gyr_z = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sensor_data_g() {
        this.catagory = CatagoryEnum.SENSOR_DATA_G;
    }
}
