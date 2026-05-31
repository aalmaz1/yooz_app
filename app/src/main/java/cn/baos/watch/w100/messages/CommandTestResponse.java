package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandTestResponse extends MessageBase {
    public String content;
    public int function;
    public int result;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.function);
        messagePacker.packLong(this.result);
        if (this.content == null) {
            this.content = "";
        }
        messagePacker.packString(this.content);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandTestResponse load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.function = (int) messageUnpacker.unpackLong();
        this.result = (int) messageUnpacker.unpackLong();
        this.content = messageUnpacker.unpackString();
        return this;
    }

    public CommandTestResponse() {
        this.catagory = CatagoryEnum.COMMANDTESTRESPONSE;
    }
}
