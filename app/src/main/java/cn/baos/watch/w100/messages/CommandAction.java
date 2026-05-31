package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandAction extends MessageBase {
    public int action_id;
    public String action_name;
    public int action_time;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.action_id);
        if (this.action_name == null) {
            this.action_name = "";
        }
        messagePacker.packString(this.action_name);
        messagePacker.packLong(this.action_time);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandAction load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.action_id = (int) messageUnpacker.unpackLong();
        this.action_name = messageUnpacker.unpackString();
        this.action_time = (int) messageUnpacker.unpackLong();
        return this;
    }

    public CommandAction() {
        this.catagory = CatagoryEnum.COMMANDACTION;
    }
}
