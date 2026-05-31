package cn.baos.watch.w100.messages;

import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class AppTencentQQ extends MessageBase {
    public String content;
    public String from;
    public int start_time_s;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.from == null) {
            this.from = "";
        }
        messagePacker.packString(this.from);
        if (this.content == null) {
            this.content = "";
        }
        messagePacker.packString(this.content);
        messagePacker.packLong(this.start_time_s);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public AppTencentQQ load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.from = messageUnpacker.unpackString();
        this.content = messageUnpacker.unpackString();
        this.start_time_s = (int) messageUnpacker.unpackLong();
        return this;
    }

    public AppTencentQQ() {
        this.catagory = 10002;
    }
}
