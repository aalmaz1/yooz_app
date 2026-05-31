package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class AlarmUpdateNotification extends MessageBase {
    public int alarm_id;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.alarm_id);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public AlarmUpdateNotification load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.alarm_id = (int) messageUnpacker.unpackLong();
        return this;
    }

    public AlarmUpdateNotification() {
        this.catagory = CatagoryEnum.ALARMUPDATENOTIFICATION;
    }
}
