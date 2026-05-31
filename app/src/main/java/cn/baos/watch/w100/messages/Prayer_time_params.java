package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Prayer_time_params extends Serializable {
    public int adjust_high_lats;
    public int asr_juristic;
    public int calc_method;
    public int fajr_angle;
    public int isha_is_minutes;
    public int isha_value;
    public int maghrib_is_minutes;
    public int maghrib_value;
    public int reserve1;
    public int reserve2;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.calc_method);
        messagePacker.packLong(this.asr_juristic);
        messagePacker.packLong(this.adjust_high_lats);
        messagePacker.packLong(this.reserve1);
        messagePacker.packLong(this.fajr_angle);
        messagePacker.packLong(this.maghrib_value);
        messagePacker.packLong(this.isha_value);
        messagePacker.packLong(this.maghrib_is_minutes);
        messagePacker.packLong(this.isha_is_minutes);
        messagePacker.packLong(this.reserve2);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Prayer_time_params load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.calc_method = (int) messageUnpacker.unpackLong();
        this.asr_juristic = (int) messageUnpacker.unpackLong();
        this.adjust_high_lats = (int) messageUnpacker.unpackLong();
        this.reserve1 = (int) messageUnpacker.unpackLong();
        this.fajr_angle = (int) messageUnpacker.unpackLong();
        this.maghrib_value = (int) messageUnpacker.unpackLong();
        this.isha_value = (int) messageUnpacker.unpackLong();
        this.maghrib_is_minutes = (int) messageUnpacker.unpackLong();
        this.isha_is_minutes = (int) messageUnpacker.unpackLong();
        this.reserve2 = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Prayer_time_params() {
        this.catagory = CatagoryEnum.PRAYER_TIME_PARAMS;
    }
}
