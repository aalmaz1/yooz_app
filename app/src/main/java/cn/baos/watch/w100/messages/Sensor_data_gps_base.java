package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_gps_base extends Sensor_data {
    public int height;
    public int latitude;
    public int longitude;
    public int reserve1;
    public int reserve2;
    public int satellite_count;

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.longitude);
        messagePacker.packLong(this.latitude);
        messagePacker.packLong(this.height);
        messagePacker.packLong(this.satellite_count);
        messagePacker.packLong(this.reserve1);
        messagePacker.packLong(this.reserve2);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data_gps_base load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.longitude = (int) messageUnpacker.unpackLong();
        this.latitude = (int) messageUnpacker.unpackLong();
        this.height = (int) messageUnpacker.unpackLong();
        this.satellite_count = (int) messageUnpacker.unpackLong();
        this.reserve1 = (int) messageUnpacker.unpackLong();
        this.reserve2 = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sensor_data_gps_base() {
        this.catagory = CatagoryEnum.SENSOR_DATA_GPS_BASE;
    }
}
