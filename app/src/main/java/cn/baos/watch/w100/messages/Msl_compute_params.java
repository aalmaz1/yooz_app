package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Msl_compute_params extends Serializable {
    public int action_bits;
    public Sensor_data_gps1 gps;
    public Prayer_time_params method;
    public int msl_day_offset;
    public int timestamp;
    public int timezone;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        this.method.put(messagePacker);
        this.gps.put(messagePacker);
        messagePacker.packLong(this.timestamp);
        messagePacker.packLong(this.timezone);
        messagePacker.packLong(this.msl_day_offset);
        messagePacker.packLong(this.action_bits);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Msl_compute_params load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        Prayer_time_params prayer_time_params = new Prayer_time_params();
        this.method = prayer_time_params;
        prayer_time_params.load(messageUnpacker);
        Sensor_data_gps1 sensor_data_gps1 = new Sensor_data_gps1();
        this.gps = sensor_data_gps1;
        sensor_data_gps1.load(messageUnpacker);
        this.timestamp = (int) messageUnpacker.unpackLong();
        this.timezone = (int) messageUnpacker.unpackLong();
        this.msl_day_offset = (int) messageUnpacker.unpackLong();
        this.action_bits = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Msl_compute_params() {
        this.catagory = CatagoryEnum.MSL_COMPUTE_PARAMS;
    }
}
