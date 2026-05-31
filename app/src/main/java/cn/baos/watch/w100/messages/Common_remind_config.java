package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Common_remind_config extends Serializable {
    public int enable;
    public int interval_sec;
    public int reserve1;
    public int reserve2;
    public CRC_timespan timespan;
    public int type;
    public int week_day_mask;

    public static class CRC_timespan {
        public int begin_hour;
        public int begin_min;
        public int end_hour;
        public int end_min;

        public boolean put(MessagePacker messagePacker) throws IOException {
            messagePacker.packLong(this.begin_hour);
            messagePacker.packLong(this.begin_min);
            messagePacker.packLong(this.end_hour);
            messagePacker.packLong(this.end_min);
            return true;
        }

        public CRC_timespan load(MessageUnpacker messageUnpacker) throws IOException {
            this.begin_hour = (int) messageUnpacker.unpackLong();
            this.begin_min = (int) messageUnpacker.unpackLong();
            this.end_hour = (int) messageUnpacker.unpackLong();
            this.end_min = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        this.timespan.put(messagePacker);
        messagePacker.packLong(this.interval_sec);
        messagePacker.packLong(this.week_day_mask);
        messagePacker.packLong(this.type);
        messagePacker.packLong(this.enable);
        messagePacker.packLong(this.reserve1);
        messagePacker.packLong(this.reserve2);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Common_remind_config load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        CRC_timespan cRC_timespan = new CRC_timespan();
        this.timespan = cRC_timespan;
        cRC_timespan.load(messageUnpacker);
        this.interval_sec = (int) messageUnpacker.unpackLong();
        this.week_day_mask = (int) messageUnpacker.unpackLong();
        this.type = (int) messageUnpacker.unpackLong();
        this.enable = (int) messageUnpacker.unpackLong();
        this.reserve1 = (int) messageUnpacker.unpackLong();
        this.reserve2 = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Common_remind_config() {
        this.catagory = CatagoryEnum.COMMON_REMIND_CONFIG;
    }
}
