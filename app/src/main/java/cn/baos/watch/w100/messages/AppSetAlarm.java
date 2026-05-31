package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class AppSetAlarm extends MessageBase {
    public String action_name;
    public int alarm_at_time_s;
    public int alarm_id;
    public String alarm_name;
    public int alarm_type;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.alarm_id);
        messagePacker.packLong(this.alarm_type);
        if (this.action_name == null) {
            this.action_name = "";
        }
        messagePacker.packString(this.action_name);
        messagePacker.packLong(this.alarm_at_time_s);
        if (this.alarm_name == null) {
            this.alarm_name = "";
        }
        messagePacker.packString(this.alarm_name);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public AppSetAlarm load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.alarm_id = (int) messageUnpacker.unpackLong();
        this.alarm_type = (int) messageUnpacker.unpackLong();
        this.action_name = messageUnpacker.unpackString();
        this.alarm_at_time_s = (int) messageUnpacker.unpackLong();
        this.alarm_name = messageUnpacker.unpackString();
        return this;
    }

    public AppSetAlarm() {
        this.catagory = CatagoryEnum.APPSETALARM;
    }
}
