package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Rpc_request extends Serializable {
    public int api_id;
    public byte[] obj_param;
    public int param1;
    public int param2;
    public int param3;
    public int param4;
    public int session_id;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.session_id);
        messagePacker.packLong(this.api_id);
        messagePacker.packLong(this.param1);
        messagePacker.packLong(this.param2);
        messagePacker.packLong(this.param3);
        messagePacker.packLong(this.param4);
        byte[] bArr = this.obj_param;
        if (bArr != null) {
            messagePacker.packBinaryHeader(bArr.length);
            byte[] bArr2 = this.obj_param;
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
    public Rpc_request load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.session_id = (int) messageUnpacker.unpackLong();
        this.api_id = (int) messageUnpacker.unpackLong();
        this.param1 = (int) messageUnpacker.unpackLong();
        this.param2 = (int) messageUnpacker.unpackLong();
        this.param3 = (int) messageUnpacker.unpackLong();
        this.param4 = (int) messageUnpacker.unpackLong();
        int iUnpackBinaryHeader = messageUnpacker.unpackBinaryHeader();
        if (iUnpackBinaryHeader > 0) {
            this.obj_param = messageUnpacker.readPayload(iUnpackBinaryHeader);
        }
        return this;
    }

    public Rpc_request() {
        this.catagory = CatagoryEnum.RPC_REQUEST;
    }
}
