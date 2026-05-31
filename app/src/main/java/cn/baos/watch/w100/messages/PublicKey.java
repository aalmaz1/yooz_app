package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class PublicKey extends SyncBase {
    public byte[] publickey;

    @Override // cn.baos.watch.w100.messages.SyncBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        byte[] bArr = this.publickey;
        if (bArr != null) {
            messagePacker.packBinaryHeader(bArr.length);
            byte[] bArr2 = this.publickey;
            if (bArr2.length <= 0) {
                return true;
            }
            messagePacker.writePayload(bArr2);
            return true;
        }
        messagePacker.packBinaryHeader(0);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.SyncBase, cn.baos.message.Serializable
    public PublicKey load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackBinaryHeader = messageUnpacker.unpackBinaryHeader();
        if (iUnpackBinaryHeader > 0) {
            this.publickey = messageUnpacker.readPayload(iUnpackBinaryHeader);
        }
        return this;
    }

    public PublicKey() {
        this.catagory = CatagoryEnum.PUBLICKEY;
    }
}
