package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class NotiRtcTimeChanged extends MessageBase {
    public int timestamp_offset;
    public int timezone_offset;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.timestamp_offset);
        messagePacker.packLong(this.timezone_offset);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public NotiRtcTimeChanged load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.timestamp_offset = (int) messageUnpacker.unpackLong();
        this.timezone_offset = (int) messageUnpacker.unpackLong();
        return this;
    }

    public NotiRtcTimeChanged() {
        this.catagory = CatagoryEnum.NOTIRTCTIMECHANGED;
    }
}
