package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Device_log_content extends Serializable {
    public int action_id;
    public int sys_tick;
    public String text;
    public int timestamp;
    public int type;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.timestamp);
        messagePacker.packLong(this.sys_tick);
        messagePacker.packLong(this.type);
        messagePacker.packLong(this.action_id);
        if (this.text == null) {
            this.text = "";
        }
        messagePacker.packString(this.text);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Device_log_content load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.timestamp = (int) messageUnpacker.unpackLong();
        this.sys_tick = (int) messageUnpacker.unpackLong();
        this.type = (int) messageUnpacker.unpackLong();
        this.action_id = (int) messageUnpacker.unpackLong();
        this.text = messageUnpacker.unpackString();
        return this;
    }

    public Device_log_content() {
        this.catagory = CatagoryEnum.DEVICE_LOG_CONTENT;
    }
}
