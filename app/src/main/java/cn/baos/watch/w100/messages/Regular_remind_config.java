package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Regular_remind_config extends Serializable {
    public Regular_remind_cfg cfg;
    public int is_enable;
    public int remind_item;

    public static class Regular_remind_cfg {
        public int interval_sec;
        public Regular_timespan timespan;

        public static class Regular_timespan {
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

            public Regular_timespan load(MessageUnpacker messageUnpacker) throws IOException {
                this.begin_hour = (int) messageUnpacker.unpackLong();
                this.begin_min = (int) messageUnpacker.unpackLong();
                this.end_hour = (int) messageUnpacker.unpackLong();
                this.end_min = (int) messageUnpacker.unpackLong();
                return this;
            }
        }

        public boolean put(MessagePacker messagePacker) throws IOException {
            this.timespan.put(messagePacker);
            messagePacker.packLong(this.interval_sec);
            return true;
        }

        public Regular_remind_cfg load(MessageUnpacker messageUnpacker) throws IOException {
            Regular_timespan regular_timespan = new Regular_timespan();
            this.timespan = regular_timespan;
            regular_timespan.load(messageUnpacker);
            this.interval_sec = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.remind_item);
        messagePacker.packLong(this.is_enable);
        this.cfg.put(messagePacker);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Regular_remind_config load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.remind_item = (int) messageUnpacker.unpackLong();
        this.is_enable = (int) messageUnpacker.unpackLong();
        Regular_remind_cfg regular_remind_cfg = new Regular_remind_cfg();
        this.cfg = regular_remind_cfg;
        regular_remind_cfg.load(messageUnpacker);
        return this;
    }

    public Regular_remind_config() {
        this.catagory = CatagoryEnum.REGULAR_REMIND_CONFIG;
    }
}
