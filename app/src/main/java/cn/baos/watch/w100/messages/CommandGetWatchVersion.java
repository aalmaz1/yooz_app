package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandGetWatchVersion extends MessageBase {
    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandGetWatchVersion load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        return this;
    }

    public CommandGetWatchVersion() {
        this.catagory = CatagoryEnum.COMMANDGETWATCHVERSION;
    }
}
