package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandSystemCtrl extends MessageBase {
    public int cmd_extra;
    public int cmd_type;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.cmd_type);
        messagePacker.packLong(this.cmd_extra);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandSystemCtrl load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.cmd_type = (int) messageUnpacker.unpackLong();
        this.cmd_extra = (int) messageUnpacker.unpackLong();
        return this;
    }

    public CommandSystemCtrl() {
        this.catagory = CatagoryEnum.COMMANDSYSTEMCTRL;
    }
}
