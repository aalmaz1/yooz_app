package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Health_measure_config extends Serializable {
    public int hrate_measure_auto_save_value;
    public int hrate_measure_interval;
    public int hrate_remind_max;
    public int hrate_remind_min;
    public int spo_measure_interval;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.hrate_remind_max);
        messagePacker.packLong(this.hrate_remind_min);
        messagePacker.packLong(this.hrate_measure_interval);
        messagePacker.packLong(this.hrate_measure_auto_save_value);
        messagePacker.packLong(this.spo_measure_interval);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Health_measure_config load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.hrate_remind_max = (int) messageUnpacker.unpackLong();
        this.hrate_remind_min = (int) messageUnpacker.unpackLong();
        this.hrate_measure_interval = (int) messageUnpacker.unpackLong();
        this.hrate_measure_auto_save_value = (int) messageUnpacker.unpackLong();
        this.spo_measure_interval = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Health_measure_config() {
        this.catagory = CatagoryEnum.HEALTH_MEASURE_CONFIG;
    }
}
