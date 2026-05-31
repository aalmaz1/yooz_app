package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandTestRequest extends MessageBase {
    public int test_function;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.test_function);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandTestRequest load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.test_function = (int) messageUnpacker.unpackLong();
        return this;
    }

    public CommandTestRequest() {
        this.catagory = CatagoryEnum.COMMANDTESTREQUEST;
    }
}
