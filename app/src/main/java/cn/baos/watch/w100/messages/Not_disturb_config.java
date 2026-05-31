package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Not_disturb_config extends Serializable {
    public int begin_hour;
    public int begin_min;
    public int end_hour;
    public int end_min;
    public int is_enable;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.is_enable);
        messagePacker.packLong(this.begin_hour);
        messagePacker.packLong(this.begin_min);
        messagePacker.packLong(this.end_hour);
        messagePacker.packLong(this.end_min);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Not_disturb_config load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.is_enable = (int) messageUnpacker.unpackLong();
        this.begin_hour = (int) messageUnpacker.unpackLong();
        this.begin_min = (int) messageUnpacker.unpackLong();
        this.end_hour = (int) messageUnpacker.unpackLong();
        this.end_min = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Not_disturb_config() {
        this.catagory = CatagoryEnum.NOT_DISTURB_CONFIG;
    }
}
