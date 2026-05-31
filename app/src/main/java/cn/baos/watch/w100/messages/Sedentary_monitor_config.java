package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sedentary_monitor_config extends Serializable {
    public int is_enable;
    public Sedentary_rule rule;
    public Sedentary_timespan timespan;

    public static class Sedentary_timespan {
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

        public Sedentary_timespan load(MessageUnpacker messageUnpacker) throws IOException {
            this.begin_hour = (int) messageUnpacker.unpackLong();
            this.begin_min = (int) messageUnpacker.unpackLong();
            this.end_hour = (int) messageUnpacker.unpackLong();
            this.end_min = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    public static class Sedentary_rule {
        public int remind_count;
        public int remind_interval_min;
        public int sedentary_min;
        public int target_steps;

        public boolean put(MessagePacker messagePacker) throws IOException {
            messagePacker.packLong(this.sedentary_min);
            messagePacker.packLong(this.target_steps);
            messagePacker.packLong(this.remind_interval_min);
            messagePacker.packLong(this.remind_count);
            return true;
        }

        public Sedentary_rule load(MessageUnpacker messageUnpacker) throws IOException {
            this.sedentary_min = (int) messageUnpacker.unpackLong();
            this.target_steps = (int) messageUnpacker.unpackLong();
            this.remind_interval_min = (int) messageUnpacker.unpackLong();
            this.remind_count = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.is_enable);
        this.timespan.put(messagePacker);
        this.rule.put(messagePacker);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Sedentary_monitor_config load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.is_enable = (int) messageUnpacker.unpackLong();
        Sedentary_timespan sedentary_timespan = new Sedentary_timespan();
        this.timespan = sedentary_timespan;
        sedentary_timespan.load(messageUnpacker);
        Sedentary_rule sedentary_rule = new Sedentary_rule();
        this.rule = sedentary_rule;
        sedentary_rule.load(messageUnpacker);
        return this;
    }

    public Sedentary_monitor_config() {
        this.catagory = CatagoryEnum.SEDENTARY_MONITOR_CONFIG;
    }
}
