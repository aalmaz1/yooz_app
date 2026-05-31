package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandTimeSync extends MessageBase {
    public int sync_id;
    public String time_zone;
    public int timestamp;
    public int tv_nsec;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.timestamp);
        if (this.time_zone == null) {
            this.time_zone = "";
        }
        messagePacker.packString(this.time_zone);
        messagePacker.packLong(this.tv_nsec);
        messagePacker.packLong(this.sync_id);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandTimeSync load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.timestamp = (int) messageUnpacker.unpackLong();
        this.time_zone = messageUnpacker.unpackString();
        this.tv_nsec = (int) messageUnpacker.unpackLong();
        this.sync_id = (int) messageUnpacker.unpackLong();
        return this;
    }

    public CommandTimeSync() {
        this.catagory = CatagoryEnum.COMMANDTIMESYNC;
    }
}
