package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class SyncMessage extends Serializable {
    public int begin_seq;
    public byte[] dev_id;
    public int end_seq;
    public int seq_received;
    public SyncObj[] serialized_bytes;
    public byte[] sign;

    public static class SyncObj {
        public byte[] obj;

        public boolean put(MessagePacker messagePacker) throws IOException {
            byte[] bArr = this.obj;
            if (bArr != null) {
                messagePacker.packBinaryHeader(bArr.length);
                byte[] bArr2 = this.obj;
                if (bArr2.length <= 0) {
                    return true;
                }
                messagePacker.writePayload(bArr2);
                return true;
            }
            messagePacker.packBinaryHeader(0);
            return true;
        }

        public SyncObj load(MessageUnpacker messageUnpacker) throws IOException {
            int iUnpackBinaryHeader = messageUnpacker.unpackBinaryHeader();
            if (iUnpackBinaryHeader > 0) {
                this.obj = messageUnpacker.readPayload(iUnpackBinaryHeader);
            }
            return this;
        }
    }

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.begin_seq);
        messagePacker.packLong(this.end_seq);
        messagePacker.packLong(this.seq_received);
        byte[] bArr = this.dev_id;
        if (bArr != null) {
            messagePacker.packBinaryHeader(bArr.length);
            byte[] bArr2 = this.dev_id;
            if (bArr2.length > 0) {
                messagePacker.writePayload(bArr2);
            }
        } else {
            messagePacker.packBinaryHeader(0);
        }
        if (this.serialized_bytes != null) {
            messagePacker.packLong(r0.length);
            SyncObj[] syncObjArr = this.serialized_bytes;
            if (syncObjArr.length > 0) {
                for (SyncObj syncObj : syncObjArr) {
                    syncObj.put(messagePacker);
                }
            }
        } else {
            messagePacker.packLong(0L);
        }
        byte[] bArr3 = this.sign;
        if (bArr3 != null) {
            messagePacker.packBinaryHeader(bArr3.length);
            byte[] bArr4 = this.sign;
            if (bArr4.length <= 0) {
                return true;
            }
            messagePacker.writePayload(bArr4);
            return true;
        }
        messagePacker.packBinaryHeader(0);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public SyncMessage load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.begin_seq = (int) messageUnpacker.unpackLong();
        this.end_seq = (int) messageUnpacker.unpackLong();
        this.seq_received = (int) messageUnpacker.unpackLong();
        int iUnpackBinaryHeader = messageUnpacker.unpackBinaryHeader();
        if (iUnpackBinaryHeader > 0) {
            this.dev_id = messageUnpacker.readPayload(iUnpackBinaryHeader);
        }
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.serialized_bytes = new SyncObj[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.serialized_bytes[i] = new SyncObj();
                this.serialized_bytes[i].load(messageUnpacker);
            }
        }
        int iUnpackBinaryHeader2 = messageUnpacker.unpackBinaryHeader();
        if (iUnpackBinaryHeader2 > 0) {
            this.sign = messageUnpacker.readPayload(iUnpackBinaryHeader2);
        }
        return this;
    }

    public SyncMessage() {
        this.catagory = CatagoryEnum.SYNCMESSAGE;
    }
}
