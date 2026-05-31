package cn.baos.watch.w100.messages;

import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class FSTSBase extends Serializable {
    public int checksum;
    public int data_size;
    public int timestamp;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.timestamp);
        messagePacker.packLong(this.checksum);
        messagePacker.packLong(this.data_size);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public FSTSBase load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.timestamp = (int) messageUnpacker.unpackLong();
        this.checksum = (int) messageUnpacker.unpackLong();
        this.data_size = (int) messageUnpacker.unpackLong();
        return this;
    }

    public FSTSBase() {
        this.catagory = 103;
    }
}
