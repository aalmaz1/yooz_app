package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class QueryAlarmNextPage extends MessageBase {
    public int current_page;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.current_page);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public QueryAlarmNextPage load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.current_page = (int) messageUnpacker.unpackLong();
        return this;
    }

    public QueryAlarmNextPage() {
        this.catagory = CatagoryEnum.QUERYALARMNEXTPAGE;
    }
}
