package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sleep_monitor_config extends Serializable {
    public int is_enable;
    public Sleep_timespan timespan;

    public static class Sleep_timespan {
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

        public Sleep_timespan load(MessageUnpacker messageUnpacker) throws IOException {
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
        messagePacker.packLong(this.is_enable);
        this.timespan.put(messagePacker);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Sleep_monitor_config load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.is_enable = (int) messageUnpacker.unpackLong();
        Sleep_timespan sleep_timespan = new Sleep_timespan();
        this.timespan = sleep_timespan;
        sleep_timespan.load(messageUnpacker);
        return this;
    }

    public Sleep_monitor_config() {
        this.catagory = CatagoryEnum.SLEEP_MONITOR_CONFIG;
    }
}
