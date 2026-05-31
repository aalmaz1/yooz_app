package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Wrist_lightup_config extends Serializable {
    public int begin_hour;
    public int duration_hours;
    public int is_enable;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.is_enable);
        messagePacker.packLong(this.begin_hour);
        messagePacker.packLong(this.duration_hours);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Wrist_lightup_config load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.is_enable = (int) messageUnpacker.unpackLong();
        this.begin_hour = (int) messageUnpacker.unpackLong();
        this.duration_hours = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Wrist_lightup_config() {
        this.catagory = CatagoryEnum.WRIST_LIGHTUP_CONFIG;
    }
}
