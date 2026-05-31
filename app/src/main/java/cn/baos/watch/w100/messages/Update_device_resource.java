package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Update_device_resource extends MessageBase {
    public int location_index;
    public int reserve1;
    public int reserve2;
    public int resource_id;
    public int update_type;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.location_index);
        messagePacker.packLong(this.resource_id);
        messagePacker.packLong(this.update_type);
        messagePacker.packLong(this.reserve1);
        messagePacker.packLong(this.reserve2);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Update_device_resource load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.location_index = (int) messageUnpacker.unpackLong();
        this.resource_id = (int) messageUnpacker.unpackLong();
        this.update_type = (int) messageUnpacker.unpackLong();
        this.reserve1 = (int) messageUnpacker.unpackLong();
        this.reserve2 = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Update_device_resource() {
        this.catagory = CatagoryEnum.UPDATE_DEVICE_RESOURCE;
    }
}
