package cn.baos.watch.w100.messages;

import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data extends MessageBase {
    public int update_timestamp;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.update_timestamp);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.update_timestamp = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sensor_data() {
        this.catagory = 102;
    }
}
