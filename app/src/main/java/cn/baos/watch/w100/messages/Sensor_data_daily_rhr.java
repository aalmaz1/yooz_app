package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_daily_rhr extends Sensor_data {
    public int rhr;

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.rhr);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data_daily_rhr load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.rhr = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sensor_data_daily_rhr() {
        this.catagory = CatagoryEnum.SENSOR_DATA_DAILY_RHR;
    }
}
