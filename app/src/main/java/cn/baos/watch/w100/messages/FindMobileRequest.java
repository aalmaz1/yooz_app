package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class FindMobileRequest extends MessageBase {
    public int is_find;
    public int start_time;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.is_find);
        messagePacker.packLong(this.start_time);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public FindMobileRequest load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.is_find = (int) messageUnpacker.unpackLong();
        this.start_time = (int) messageUnpacker.unpackLong();
        return this;
    }

    public FindMobileRequest() {
        this.catagory = CatagoryEnum.FINDMOBILEREQUEST;
    }
}
