package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandSedentary extends MessageBase {
    public int remind_enable;
    public int sedentary_enable;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.sedentary_enable);
        messagePacker.packLong(this.remind_enable);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandSedentary load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.sedentary_enable = (int) messageUnpacker.unpackLong();
        this.remind_enable = (int) messageUnpacker.unpackLong();
        return this;
    }

    public CommandSedentary() {
        this.catagory = CatagoryEnum.COMMANDSEDENTARY;
    }
}
