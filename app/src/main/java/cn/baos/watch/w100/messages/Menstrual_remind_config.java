package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Menstrual_remind_config extends Serializable {
    public Menstrual_remind_cfg cfg;
    public int is_enable;

    public static class Menstrual_remind_cfg {
        public int last_mens_day;
        public int last_mens_month;
        public int last_mens_year;
        public int mens_days;
        public int mens_period;
        public int mens_remind_before_days;
        public int ovul_remind_before_days;
        public int remind_hour;
        public int remind_min;

        public boolean put(MessagePacker messagePacker) throws IOException {
            messagePacker.packLong(this.mens_remind_before_days);
            messagePacker.packLong(this.ovul_remind_before_days);
            messagePacker.packLong(this.remind_hour);
            messagePacker.packLong(this.remind_min);
            messagePacker.packLong(this.last_mens_year);
            messagePacker.packLong(this.last_mens_month);
            messagePacker.packLong(this.last_mens_day);
            messagePacker.packLong(this.mens_days);
            messagePacker.packLong(this.mens_period);
            return true;
        }

        public Menstrual_remind_cfg load(MessageUnpacker messageUnpacker) throws IOException {
            this.mens_remind_before_days = (int) messageUnpacker.unpackLong();
            this.ovul_remind_before_days = (int) messageUnpacker.unpackLong();
            this.remind_hour = (int) messageUnpacker.unpackLong();
            this.remind_min = (int) messageUnpacker.unpackLong();
            this.last_mens_year = (int) messageUnpacker.unpackLong();
            this.last_mens_month = (int) messageUnpacker.unpackLong();
            this.last_mens_day = (int) messageUnpacker.unpackLong();
            this.mens_days = (int) messageUnpacker.unpackLong();
            this.mens_period = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.is_enable);
        this.cfg.put(messagePacker);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Menstrual_remind_config load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.is_enable = (int) messageUnpacker.unpackLong();
        Menstrual_remind_cfg menstrual_remind_cfg = new Menstrual_remind_cfg();
        this.cfg = menstrual_remind_cfg;
        menstrual_remind_cfg.load(messageUnpacker);
        return this;
    }

    public Menstrual_remind_config() {
        this.catagory = CatagoryEnum.MENSTRUAL_REMIND_CONFIG;
    }
}
