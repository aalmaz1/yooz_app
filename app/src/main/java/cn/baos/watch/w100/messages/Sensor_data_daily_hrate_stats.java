package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_daily_hrate_stats extends Sensor_data {
    public int avg_hrate;
    public int max_hrate;
    public int min_hrate;
    public int rhr_hrate;

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.avg_hrate);
        messagePacker.packLong(this.max_hrate);
        messagePacker.packLong(this.min_hrate);
        messagePacker.packLong(this.rhr_hrate);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data_daily_hrate_stats load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.avg_hrate = (int) messageUnpacker.unpackLong();
        this.max_hrate = (int) messageUnpacker.unpackLong();
        this.min_hrate = (int) messageUnpacker.unpackLong();
        this.rhr_hrate = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sensor_data_daily_hrate_stats() {
        this.catagory = CatagoryEnum.SENSOR_DATA_DAILY_HRATE_STATS;
    }
}
