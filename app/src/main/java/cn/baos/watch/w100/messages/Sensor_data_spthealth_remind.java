package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_spthealth_remind extends Sensor_data {
    public int remind_item;
    public int value;

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.remind_item);
        messagePacker.packLong(this.value);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data_spthealth_remind load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.remind_item = (int) messageUnpacker.unpackLong();
        this.value = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sensor_data_spthealth_remind() {
        this.catagory = CatagoryEnum.SENSOR_DATA_SPTHEALTH_REMIND;
    }
}
