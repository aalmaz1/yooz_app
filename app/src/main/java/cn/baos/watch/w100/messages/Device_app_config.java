package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Device_app_config extends Serializable {
    public int app_id_mask;
    public byte[] app_ids;
    public int reserve1;
    public int reserve2;
    public int type;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        byte[] bArr = this.app_ids;
        if (bArr != null) {
            messagePacker.packBinaryHeader(bArr.length);
            byte[] bArr2 = this.app_ids;
            if (bArr2.length > 0) {
                messagePacker.writePayload(bArr2);
            }
        } else {
            messagePacker.packBinaryHeader(0);
        }
        messagePacker.packLong(this.app_id_mask);
        messagePacker.packLong(this.type);
        messagePacker.packLong(this.reserve1);
        messagePacker.packLong(this.reserve2);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Device_app_config load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackBinaryHeader = messageUnpacker.unpackBinaryHeader();
        if (iUnpackBinaryHeader > 0) {
            this.app_ids = messageUnpacker.readPayload(iUnpackBinaryHeader);
        }
        this.app_id_mask = (int) messageUnpacker.unpackLong();
        this.type = (int) messageUnpacker.unpackLong();
        this.reserve1 = (int) messageUnpacker.unpackLong();
        this.reserve2 = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Device_app_config() {
        this.catagory = CatagoryEnum.DEVICE_APP_CONFIG;
    }
}
