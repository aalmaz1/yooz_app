package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandBleOTAFileData extends MessageBase {
    public byte[] file_data;
    public int index;
    public String md5;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.index);
        if (this.md5 == null) {
            this.md5 = "";
        }
        messagePacker.packString(this.md5);
        byte[] bArr = this.file_data;
        if (bArr != null) {
            messagePacker.packBinaryHeader(bArr.length);
            byte[] bArr2 = this.file_data;
            if (bArr2.length <= 0) {
                return true;
            }
            messagePacker.writePayload(bArr2);
            return true;
        }
        messagePacker.packBinaryHeader(0);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandBleOTAFileData load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.index = (int) messageUnpacker.unpackLong();
        this.md5 = messageUnpacker.unpackString();
        int iUnpackBinaryHeader = messageUnpacker.unpackBinaryHeader();
        if (iUnpackBinaryHeader > 0) {
            this.file_data = messageUnpacker.readPayload(iUnpackBinaryHeader);
        }
        return this;
    }

    public CommandBleOTAFileData() {
        this.catagory = CatagoryEnum.COMMANDBLEOTAFILEDATA;
    }
}
