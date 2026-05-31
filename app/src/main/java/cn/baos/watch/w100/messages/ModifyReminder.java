package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class ModifyReminder extends MessageBase {
    public int alarm_id;
    public int circle_extra;
    public int circle_type;
    public String event;
    public int mask_mday;
    public int mask_mweek;
    public int mask_wday;
    public int mask_ymonth;
    public String reminder;
    public int start_time;
    public int status;
    public int time_zone;
    public int verison;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.verison);
        messagePacker.packLong(this.alarm_id);
        messagePacker.packLong(this.circle_type);
        messagePacker.packLong(this.circle_extra);
        messagePacker.packLong(this.mask_wday);
        messagePacker.packLong(this.mask_mday);
        messagePacker.packLong(this.mask_mweek);
        messagePacker.packLong(this.mask_ymonth);
        messagePacker.packLong(this.time_zone);
        messagePacker.packLong(this.start_time);
        if (this.reminder == null) {
            this.reminder = "";
        }
        messagePacker.packString(this.reminder);
        if (this.event == null) {
            this.event = "";
        }
        messagePacker.packString(this.event);
        messagePacker.packLong(this.status);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public ModifyReminder load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.verison = (int) messageUnpacker.unpackLong();
        this.alarm_id = (int) messageUnpacker.unpackLong();
        this.circle_type = (int) messageUnpacker.unpackLong();
        this.circle_extra = (int) messageUnpacker.unpackLong();
        this.mask_wday = (int) messageUnpacker.unpackLong();
        this.mask_mday = (int) messageUnpacker.unpackLong();
        this.mask_mweek = (int) messageUnpacker.unpackLong();
        this.mask_ymonth = (int) messageUnpacker.unpackLong();
        this.time_zone = (int) messageUnpacker.unpackLong();
        this.start_time = (int) messageUnpacker.unpackLong();
        this.reminder = messageUnpacker.unpackString();
        this.event = messageUnpacker.unpackString();
        this.status = (int) messageUnpacker.unpackLong();
        return this;
    }

    public ModifyReminder() {
        this.catagory = CatagoryEnum.MODIFYREMINDER;
    }
}
