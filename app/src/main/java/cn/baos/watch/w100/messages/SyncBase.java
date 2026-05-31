package cn.baos.watch.w100.messages;

import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class SyncBase extends Serializable {
    public int seq_id;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.seq_id);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public SyncBase load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.seq_id = (int) messageUnpacker.unpackLong();
        return this;
    }

    public SyncBase() {
        this.catagory = 101;
    }
}
