package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Device_status_changed extends MessageBase {
    public int change_type;
    public int param;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.change_type);
        messagePacker.packLong(this.param);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Device_status_changed load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.change_type = (int) messageUnpacker.unpackLong();
        this.param = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Device_status_changed() {
        this.catagory = CatagoryEnum.DEVICE_STATUS_CHANGED;
    }
}
