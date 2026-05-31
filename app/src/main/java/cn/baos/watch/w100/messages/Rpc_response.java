package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Rpc_response extends Serializable {
    public int api_id;
    public Rpc_response_obj[] objs_result;
    public int result1;
    public int result2;
    public int session_id;

    public static class Rpc_response_obj {
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

        public Rpc_response_obj load(MessageUnpacker messageUnpacker) throws IOException {
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
        messagePacker.packLong(this.session_id);
        messagePacker.packLong(this.api_id);
        messagePacker.packLong(this.result1);
        messagePacker.packLong(this.result2);
        if (this.objs_result != null) {
            messagePacker.packLong(r0.length);
            Rpc_response_obj[] rpc_response_objArr = this.objs_result;
            if (rpc_response_objArr.length <= 0) {
                return true;
            }
            for (Rpc_response_obj rpc_response_obj : rpc_response_objArr) {
                rpc_response_obj.put(messagePacker);
            }
            return true;
        }
        messagePacker.packLong(0L);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Rpc_response load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.session_id = (int) messageUnpacker.unpackLong();
        this.api_id = (int) messageUnpacker.unpackLong();
        this.result1 = (int) messageUnpacker.unpackLong();
        this.result2 = (int) messageUnpacker.unpackLong();
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.objs_result = new Rpc_response_obj[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.objs_result[i] = new Rpc_response_obj();
                this.objs_result[i].load(messageUnpacker);
            }
        }
        return this;
    }

    public Rpc_response() {
        this.catagory = CatagoryEnum.RPC_RESPONSE;
    }
}
