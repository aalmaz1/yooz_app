package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandAutoTest extends MessageBase {
    public int state;
    public String test_result;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.state);
        if (this.test_result == null) {
            this.test_result = "";
        }
        messagePacker.packString(this.test_result);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandAutoTest load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.state = (int) messageUnpacker.unpackLong();
        this.test_result = messageUnpacker.unpackString();
        return this;
    }

    public CommandAutoTest() {
        this.catagory = CatagoryEnum.COMMANDAUTOTEST;
    }
}
