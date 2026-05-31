package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Daily_active_target_config extends Serializable {
    public int sum_calorie;
    public int sum_distance_m;
    public int sum_step;
    public int sum_times;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.sum_distance_m);
        messagePacker.packLong(this.sum_step);
        messagePacker.packLong(this.sum_calorie);
        messagePacker.packLong(this.sum_times);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Daily_active_target_config load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.sum_distance_m = (int) messageUnpacker.unpackLong();
        this.sum_step = (int) messageUnpacker.unpackLong();
        this.sum_calorie = (int) messageUnpacker.unpackLong();
        this.sum_times = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Daily_active_target_config() {
        this.catagory = CatagoryEnum.DAILY_ACTIVE_TARGET_CONFIG;
    }
}
