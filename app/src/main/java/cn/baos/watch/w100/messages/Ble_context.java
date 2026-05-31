package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Ble_context extends Serializable {
    public int bind_status;
    public int connect_status;
    public int phone_os;
    public int protocol_version;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.connect_status);
        messagePacker.packLong(this.bind_status);
        messagePacker.packLong(this.phone_os);
        messagePacker.packLong(this.protocol_version);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Ble_context load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.connect_status = (int) messageUnpacker.unpackLong();
        this.bind_status = (int) messageUnpacker.unpackLong();
        this.phone_os = (int) messageUnpacker.unpackLong();
        this.protocol_version = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Ble_context() {
        this.catagory = CatagoryEnum.BLE_CONTEXT;
    }
}
