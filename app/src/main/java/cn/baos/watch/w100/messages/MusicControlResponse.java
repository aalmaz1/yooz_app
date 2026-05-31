package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class MusicControlResponse extends MessageBase {
    public String name;
    public int status;
    public int volumn;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.status);
        if (this.name == null) {
            this.name = "";
        }
        messagePacker.packString(this.name);
        messagePacker.packLong(this.volumn);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public MusicControlResponse load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.status = (int) messageUnpacker.unpackLong();
        this.name = messageUnpacker.unpackString();
        this.volumn = (int) messageUnpacker.unpackLong();
        return this;
    }

    public MusicControlResponse() {
        this.catagory = CatagoryEnum.MUSICCONTROLRESPONSE;
    }
}
