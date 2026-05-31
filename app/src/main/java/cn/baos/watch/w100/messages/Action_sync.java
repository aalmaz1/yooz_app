package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Action_sync extends Serializable {
    public int action_param;
    public int action_type;
    public int reserve;
    public int timestamp;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.timestamp);
        messagePacker.packLong(this.action_type);
        messagePacker.packLong(this.action_param);
        messagePacker.packLong(this.reserve);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Action_sync load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.timestamp = (int) messageUnpacker.unpackLong();
        this.action_type = (int) messageUnpacker.unpackLong();
        this.action_param = (int) messageUnpacker.unpackLong();
        this.reserve = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Action_sync() {
        this.catagory = CatagoryEnum.ACTION_SYNC;
    }
}
