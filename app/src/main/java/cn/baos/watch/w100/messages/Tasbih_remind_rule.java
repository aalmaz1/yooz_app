package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Tasbih_remind_rule extends Serializable {
    public int enable;
    public int interval_min;
    public Hb_timespan timespan;
    public int week_day_mask;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        this.timespan.put(messagePacker);
        messagePacker.packLong(this.interval_min);
        messagePacker.packLong(this.week_day_mask);
        messagePacker.packLong(this.enable);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Tasbih_remind_rule load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        Hb_timespan hb_timespan = new Hb_timespan();
        this.timespan = hb_timespan;
        hb_timespan.load(messageUnpacker);
        this.interval_min = (int) messageUnpacker.unpackLong();
        this.week_day_mask = (int) messageUnpacker.unpackLong();
        this.enable = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Tasbih_remind_rule() {
        this.catagory = CatagoryEnum.TASBIH_REMIND_RULE;
    }
}
