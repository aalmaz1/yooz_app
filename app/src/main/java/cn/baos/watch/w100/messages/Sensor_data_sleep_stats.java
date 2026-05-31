package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_sleep_stats extends Sensor_data {
    public int begin_timestamp;
    public int deep_sec;
    public int end_timestamp;
    public int eyesmove_sec;
    public int light_sec;
    public int total_sec;
    public int wakeup_sec;

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.begin_timestamp);
        messagePacker.packLong(this.end_timestamp);
        messagePacker.packLong(this.total_sec);
        messagePacker.packLong(this.light_sec);
        messagePacker.packLong(this.deep_sec);
        messagePacker.packLong(this.wakeup_sec);
        messagePacker.packLong(this.eyesmove_sec);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data_sleep_stats load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.begin_timestamp = (int) messageUnpacker.unpackLong();
        this.end_timestamp = (int) messageUnpacker.unpackLong();
        this.total_sec = (int) messageUnpacker.unpackLong();
        this.light_sec = (int) messageUnpacker.unpackLong();
        this.deep_sec = (int) messageUnpacker.unpackLong();
        this.wakeup_sec = (int) messageUnpacker.unpackLong();
        this.eyesmove_sec = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sensor_data_sleep_stats() {
        this.catagory = CatagoryEnum.SENSOR_DATA_SLEEP_STATS;
    }
}
