package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_daily_active_sum extends Sensor_data {
    public int sum_calorie;
    public int sum_distance_m;
    public int sum_step;
    public int sum_times;

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.sum_distance_m);
        messagePacker.packLong(this.sum_step);
        messagePacker.packLong(this.sum_calorie);
        messagePacker.packLong(this.sum_times);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data_daily_active_sum load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.sum_distance_m = (int) messageUnpacker.unpackLong();
        this.sum_step = (int) messageUnpacker.unpackLong();
        this.sum_calorie = (int) messageUnpacker.unpackLong();
        this.sum_times = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sensor_data_daily_active_sum() {
        this.catagory = CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_SUM;
    }
}
