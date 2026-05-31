package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandPhonePairResult extends MessageBase {
    public int pair_result;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.pair_result);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandPhonePairResult load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.pair_result = (int) messageUnpacker.unpackLong();
        return this;
    }

    public CommandPhonePairResult() {
        this.catagory = CatagoryEnum.COMMANDPHONEPAIRRESULT;
    }
}
