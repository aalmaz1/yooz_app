package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Lcd_config extends MessageBase {
    public int default_lightup_sec;
    public int light_level;
    public int night_light_adjust_enable;
    public Night_timespan night_timespan;

    public static class Night_timespan {
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

        public Night_timespan load(MessageUnpacker messageUnpacker) throws IOException {
            this.begin_hour = (int) messageUnpacker.unpackLong();
            this.begin_min = (int) messageUnpacker.unpackLong();
            this.end_hour = (int) messageUnpacker.unpackLong();
            this.end_min = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.night_light_adjust_enable);
        this.night_timespan.put(messagePacker);
        messagePacker.packLong(this.light_level);
        messagePacker.packLong(this.default_lightup_sec);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Lcd_config load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.night_light_adjust_enable = (int) messageUnpacker.unpackLong();
        Night_timespan night_timespan = new Night_timespan();
        this.night_timespan = night_timespan;
        night_timespan.load(messageUnpacker);
        this.light_level = (int) messageUnpacker.unpackLong();
        this.default_lightup_sec = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Lcd_config() {
        this.catagory = CatagoryEnum.LCD_CONFIG;
    }
}
