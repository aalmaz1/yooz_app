package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class FindMobileResponse extends MessageBase {
    public int status;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.status);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public FindMobileResponse load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.status = (int) messageUnpacker.unpackLong();
        return this;
    }

    public FindMobileResponse() {
        this.catagory = CatagoryEnum.FINDMOBILERESPONSE;
    }
}
