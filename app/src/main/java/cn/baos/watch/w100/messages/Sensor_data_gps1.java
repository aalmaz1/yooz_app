package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_gps1 extends Sensor_data {
    public int latitude;
    public int longitude;
    public int reserve;
    public int satellite_count;

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.longitude);
        messagePacker.packLong(this.latitude);
        messagePacker.packLong(this.satellite_count);
        messagePacker.packLong(this.reserve);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data_gps1 load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.longitude = (int) messageUnpacker.unpackLong();
        this.latitude = (int) messageUnpacker.unpackLong();
        this.satellite_count = (int) messageUnpacker.unpackLong();
        this.reserve = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sensor_data_gps1() {
        this.catagory = CatagoryEnum.SENSOR_DATA_GPS1;
    }
}
