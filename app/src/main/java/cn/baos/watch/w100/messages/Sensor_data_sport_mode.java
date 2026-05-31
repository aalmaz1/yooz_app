package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_sport_mode extends Sensor_data {
    public int avg_frequency_cpm;
    public int avg_hrate;
    public int avg_pace_s;
    public int avg_step_len_cm;
    public int cur_frequency_cpm;
    public int cur_hrate;
    public int cur_pace_s;
    public int cur_step_len_cm;
    public int latitude;
    public int longitude;
    public int max_frequency_cpm;
    public int max_hrate;
    public int max_pace_s;
    public int max_step_len_cm;
    public int min_frequency_cpm;
    public int min_hrate;
    public int min_pace_s;
    public int min_step_len_cm;
    public int mode;
    public int status;
    public int sum_action_count;
    public int sum_calories;
    public int sum_distance_m;
    public int sum_times_s;

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.longitude);
        messagePacker.packLong(this.latitude);
        messagePacker.packLong(this.avg_hrate);
        messagePacker.packLong(this.max_hrate);
        messagePacker.packLong(this.min_hrate);
        messagePacker.packLong(this.cur_hrate);
        messagePacker.packLong(this.avg_step_len_cm);
        messagePacker.packLong(this.max_step_len_cm);
        messagePacker.packLong(this.min_step_len_cm);
        messagePacker.packLong(this.cur_step_len_cm);
        messagePacker.packLong(this.avg_frequency_cpm);
        messagePacker.packLong(this.max_frequency_cpm);
        messagePacker.packLong(this.min_frequency_cpm);
        messagePacker.packLong(this.cur_frequency_cpm);
        messagePacker.packLong(this.avg_pace_s);
        messagePacker.packLong(this.max_pace_s);
        messagePacker.packLong(this.min_pace_s);
        messagePacker.packLong(this.cur_pace_s);
        messagePacker.packLong(this.sum_distance_m);
        messagePacker.packLong(this.sum_action_count);
        messagePacker.packLong(this.sum_calories);
        messagePacker.packLong(this.sum_times_s);
        messagePacker.packLong(this.mode);
        messagePacker.packLong(this.status);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data_sport_mode load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.longitude = (int) messageUnpacker.unpackLong();
        this.latitude = (int) messageUnpacker.unpackLong();
        this.avg_hrate = (int) messageUnpacker.unpackLong();
        this.max_hrate = (int) messageUnpacker.unpackLong();
        this.min_hrate = (int) messageUnpacker.unpackLong();
        this.cur_hrate = (int) messageUnpacker.unpackLong();
        this.avg_step_len_cm = (int) messageUnpacker.unpackLong();
        this.max_step_len_cm = (int) messageUnpacker.unpackLong();
        this.min_step_len_cm = (int) messageUnpacker.unpackLong();
        this.cur_step_len_cm = (int) messageUnpacker.unpackLong();
        this.avg_frequency_cpm = (int) messageUnpacker.unpackLong();
        this.max_frequency_cpm = (int) messageUnpacker.unpackLong();
        this.min_frequency_cpm = (int) messageUnpacker.unpackLong();
        this.cur_frequency_cpm = (int) messageUnpacker.unpackLong();
        this.avg_pace_s = (int) messageUnpacker.unpackLong();
        this.max_pace_s = (int) messageUnpacker.unpackLong();
        this.min_pace_s = (int) messageUnpacker.unpackLong();
        this.cur_pace_s = (int) messageUnpacker.unpackLong();
        this.sum_distance_m = (int) messageUnpacker.unpackLong();
        this.sum_action_count = (int) messageUnpacker.unpackLong();
        this.sum_calories = (int) messageUnpacker.unpackLong();
        this.sum_times_s = (int) messageUnpacker.unpackLong();
        this.mode = (int) messageUnpacker.unpackLong();
        this.status = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sensor_data_sport_mode() {
        this.catagory = CatagoryEnum.SENSOR_DATA_SPORT_MODE;
    }
}
