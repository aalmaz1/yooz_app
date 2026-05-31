package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Hrate_range_config extends Serializable {
    public int range_1;
    public int range_2;
    public int range_3;
    public int range_4;
    public int range_max;
    public int range_min;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.range_min);
        messagePacker.packLong(this.range_1);
        messagePacker.packLong(this.range_2);
        messagePacker.packLong(this.range_3);
        messagePacker.packLong(this.range_4);
        messagePacker.packLong(this.range_max);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Hrate_range_config load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.range_min = (int) messageUnpacker.unpackLong();
        this.range_1 = (int) messageUnpacker.unpackLong();
        this.range_2 = (int) messageUnpacker.unpackLong();
        this.range_3 = (int) messageUnpacker.unpackLong();
        this.range_4 = (int) messageUnpacker.unpackLong();
        this.range_max = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Hrate_range_config() {
        this.catagory = CatagoryEnum.HRATE_RANGE_CONFIG;
    }
}
