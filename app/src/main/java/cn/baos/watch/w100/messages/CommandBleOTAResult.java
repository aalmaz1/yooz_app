package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandBleOTAResult extends MessageBase {
    public int func;
    public int index;
    public int result;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.func);
        messagePacker.packLong(this.result);
        messagePacker.packLong(this.index);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandBleOTAResult load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.func = (int) messageUnpacker.unpackLong();
        this.result = (int) messageUnpacker.unpackLong();
        this.index = (int) messageUnpacker.unpackLong();
        return this;
    }

    public CommandBleOTAResult() {
        this.catagory = CatagoryEnum.COMMANDBLEOTARESULT;
    }
}
