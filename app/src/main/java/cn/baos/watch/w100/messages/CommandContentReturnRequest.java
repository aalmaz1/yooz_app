package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandContentReturnRequest extends MessageBase {
    public String content;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.content == null) {
            this.content = "";
        }
        messagePacker.packString(this.content);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandContentReturnRequest load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.content = messageUnpacker.unpackString();
        return this;
    }

    public CommandContentReturnRequest() {
        this.catagory = CatagoryEnum.COMMANDCONTENTRETURNREQUEST;
    }
}
