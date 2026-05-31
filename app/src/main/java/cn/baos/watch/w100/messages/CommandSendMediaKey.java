package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandSendMediaKey extends MessageBase {
    public int key_value;
    public int media_key;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.media_key);
        messagePacker.packLong(this.key_value);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandSendMediaKey load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.media_key = (int) messageUnpacker.unpackLong();
        this.key_value = (int) messageUnpacker.unpackLong();
        return this;
    }

    public CommandSendMediaKey() {
        this.catagory = CatagoryEnum.COMMANDSENDMEDIAKEY;
    }
}
