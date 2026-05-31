package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class QueryAlarm extends MessageBase {
    public int query_mode;
    public int trigger_begin_time;
    public int trigger_end_time;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.query_mode);
        messagePacker.packLong(this.trigger_begin_time);
        messagePacker.packLong(this.trigger_end_time);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public QueryAlarm load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.query_mode = (int) messageUnpacker.unpackLong();
        this.trigger_begin_time = (int) messageUnpacker.unpackLong();
        this.trigger_end_time = (int) messageUnpacker.unpackLong();
        return this;
    }

    public QueryAlarm() {
        this.catagory = CatagoryEnum.QUERYALARM;
    }
}
