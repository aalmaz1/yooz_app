package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class MusicControlRequest extends MessageBase {
    public int action;
    public int volumn;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.action);
        messagePacker.packLong(this.volumn);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public MusicControlRequest load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.action = (int) messageUnpacker.unpackLong();
        this.volumn = (int) messageUnpacker.unpackLong();
        return this;
    }

    public MusicControlRequest() {
        this.catagory = CatagoryEnum.MUSICCONTROLREQUEST;
    }
}
