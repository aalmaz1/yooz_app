package cn.baos.message;

import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Serializable {
    public int catagory;

    public boolean put(MessagePacker messagePacker) throws IOException {
        messagePacker.packLong(this.catagory);
        return true;
    }

    public Serializable load(MessageUnpacker messageUnpacker) throws IOException {
        this.catagory = messageUnpacker.unpackInt();
        return this;
    }
}
