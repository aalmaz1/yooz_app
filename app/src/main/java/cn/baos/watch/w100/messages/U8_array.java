package cn.baos.watch.w100.messages;

import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class U8_array extends Serializable {
    public byte[] datas;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        byte[] bArr = this.datas;
        if (bArr != null) {
            messagePacker.packBinaryHeader(bArr.length);
            byte[] bArr2 = this.datas;
            if (bArr2.length <= 0) {
                return true;
            }
            messagePacker.writePayload(bArr2);
            return true;
        }
        messagePacker.packBinaryHeader(0);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public U8_array load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackBinaryHeader = messageUnpacker.unpackBinaryHeader();
        if (iUnpackBinaryHeader > 0) {
            this.datas = messageUnpacker.readPayload(iUnpackBinaryHeader);
        }
        return this;
    }

    public U8_array() {
        this.catagory = 107;
    }
}
