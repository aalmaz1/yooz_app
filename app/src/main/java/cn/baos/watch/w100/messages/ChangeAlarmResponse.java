package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class ChangeAlarmResponse extends MessageBase {
    public int alarm_id;
    public int alarm_type;
    public int change_type;
    public int return_code;
    public int status;
    public int trigger_time;
    public int verison;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.alarm_type);
        messagePacker.packLong(this.change_type);
        messagePacker.packLong(this.return_code);
        messagePacker.packLong(this.verison);
        messagePacker.packLong(this.alarm_id);
        messagePacker.packLong(this.trigger_time);
        messagePacker.packLong(this.status);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public ChangeAlarmResponse load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.alarm_type = (int) messageUnpacker.unpackLong();
        this.change_type = (int) messageUnpacker.unpackLong();
        this.return_code = (int) messageUnpacker.unpackLong();
        this.verison = (int) messageUnpacker.unpackLong();
        this.alarm_id = (int) messageUnpacker.unpackLong();
        this.trigger_time = (int) messageUnpacker.unpackLong();
        this.status = (int) messageUnpacker.unpackLong();
        return this;
    }

    public ChangeAlarmResponse() {
        this.catagory = CatagoryEnum.CHANGEALARMRESPONSE;
    }
}
