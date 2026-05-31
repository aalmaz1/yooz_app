package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Bt_slave_device extends Serializable {
    public byte[] mac;
    public String name;
    public int rssi;
    public int status;
    public int type;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        byte[] bArr = this.mac;
        if (bArr != null) {
            messagePacker.packBinaryHeader(bArr.length);
            byte[] bArr2 = this.mac;
            if (bArr2.length > 0) {
                messagePacker.writePayload(bArr2);
            }
        } else {
            messagePacker.packBinaryHeader(0);
        }
        if (this.name == null) {
            this.name = "";
        }
        messagePacker.packString(this.name);
        messagePacker.packLong(this.type);
        messagePacker.packLong(this.rssi);
        messagePacker.packLong(this.status);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Bt_slave_device load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackBinaryHeader = messageUnpacker.unpackBinaryHeader();
        if (iUnpackBinaryHeader > 0) {
            this.mac = messageUnpacker.readPayload(iUnpackBinaryHeader);
        }
        this.name = messageUnpacker.unpackString();
        this.type = (int) messageUnpacker.unpackLong();
        this.rssi = (int) messageUnpacker.unpackLong();
        this.status = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Bt_slave_device() {
        this.catagory = CatagoryEnum.BT_SLAVE_DEVICE;
    }
}
