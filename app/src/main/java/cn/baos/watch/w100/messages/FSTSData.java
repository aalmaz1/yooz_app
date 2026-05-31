package cn.baos.watch.w100.messages;

import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class FSTSData extends FSTSBase {
    public byte[] data;

    @Override // cn.baos.watch.w100.messages.FSTSBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        byte[] bArr = this.data;
        if (bArr != null) {
            messagePacker.packBinaryHeader(bArr.length);
            byte[] bArr2 = this.data;
            if (bArr2.length <= 0) {
                return true;
            }
            messagePacker.writePayload(bArr2);
            return true;
        }
        messagePacker.packBinaryHeader(0);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.FSTSBase, cn.baos.message.Serializable
    public FSTSData load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackBinaryHeader = messageUnpacker.unpackBinaryHeader();
        if (iUnpackBinaryHeader > 0) {
            this.data = messageUnpacker.readPayload(iUnpackBinaryHeader);
        }
        return this;
    }

    public FSTSData() {
        this.catagory = 105;
    }
}
