package cn.baos.watch.w100.messages;

import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class MessageBase extends Serializable {
    public int addtime;
    public int id;
    public int rank;
    public int tag;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.id);
        messagePacker.packLong(this.rank);
        messagePacker.packLong(this.addtime);
        messagePacker.packLong(this.tag);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public MessageBase load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.id = (int) messageUnpacker.unpackLong();
        this.rank = (int) messageUnpacker.unpackLong();
        this.addtime = (int) messageUnpacker.unpackLong();
        this.tag = (int) messageUnpacker.unpackLong();
        return this;
    }

    public MessageBase() {
        this.catagory = 100;
    }
}
