package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_daily_active_sum_v2 extends Sensor_data {
    public int reserve1;
    public int reserve2;
    public int sum_calorie;
    public int sum_distance_m;
    public int sum_met;
    public int sum_step;
    public int sum_times;

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.sum_distance_m);
        messagePacker.packLong(this.sum_step);
        messagePacker.packLong(this.sum_calorie);
        messagePacker.packLong(this.sum_times);
        messagePacker.packLong(this.sum_met);
        messagePacker.packLong(this.reserve1);
        messagePacker.packLong(this.reserve2);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data_daily_active_sum_v2 load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.sum_distance_m = (int) messageUnpacker.unpackLong();
        this.sum_step = (int) messageUnpacker.unpackLong();
        this.sum_calorie = (int) messageUnpacker.unpackLong();
        this.sum_times = (int) messageUnpacker.unpackLong();
        this.sum_met = (int) messageUnpacker.unpackLong();
        this.reserve1 = (int) messageUnpacker.unpackLong();
        this.reserve2 = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sensor_data_daily_active_sum_v2() {
        this.catagory = CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_SUM_V2;
    }
}
