package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_daily_spo_stats extends Sensor_data {
    public int avg_spo;
    public int max_spo;
    public int min_spo;

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.avg_spo);
        messagePacker.packLong(this.max_spo);
        messagePacker.packLong(this.min_spo);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data_daily_spo_stats load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.avg_spo = (int) messageUnpacker.unpackLong();
        this.max_spo = (int) messageUnpacker.unpackLong();
        this.min_spo = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sensor_data_daily_spo_stats() {
        this.catagory = CatagoryEnum.SENSOR_DATA_DAILY_SPO_STATS;
    }
}
