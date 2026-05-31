package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Health_measure_control extends Serializable {
    public int measure_action;
    public int measure_type;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.measure_action);
        messagePacker.packLong(this.measure_type);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Health_measure_control load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.measure_action = (int) messageUnpacker.unpackLong();
        this.measure_type = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Health_measure_control() {
        this.catagory = CatagoryEnum.HEALTH_MEASURE_CONTROL;
    }
}
