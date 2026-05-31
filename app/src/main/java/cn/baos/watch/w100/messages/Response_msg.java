package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Response_msg extends Serializable {
    public int act_catagory;
    public int act_sequence_id;
    public int result;
    public int return_obj_catagory;
    public Serialized_obj[] return_objs;
    public int return_value_u16;
    public int return_value_u8;

    public static class Serialized_obj {
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

        public Serialized_obj load(MessageUnpacker messageUnpacker) throws IOException {
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
        messagePacker.packLong(this.act_catagory);
        messagePacker.packLong(this.act_sequence_id);
        messagePacker.packLong(this.result);
        messagePacker.packLong(this.return_value_u8);
        messagePacker.packLong(this.return_value_u16);
        if (this.return_objs != null) {
            messagePacker.packLong(r0.length);
            Serialized_obj[] serialized_objArr = this.return_objs;
            if (serialized_objArr.length > 0) {
                for (Serialized_obj serialized_obj : serialized_objArr) {
                    serialized_obj.put(messagePacker);
                }
            }
        } else {
            messagePacker.packLong(0L);
        }
        messagePacker.packLong(this.return_obj_catagory);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Response_msg load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.act_catagory = (int) messageUnpacker.unpackLong();
        this.act_sequence_id = (int) messageUnpacker.unpackLong();
        this.result = (int) messageUnpacker.unpackLong();
        this.return_value_u8 = (int) messageUnpacker.unpackLong();
        this.return_value_u16 = (int) messageUnpacker.unpackLong();
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.return_objs = new Serialized_obj[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.return_objs[i] = new Serialized_obj();
                this.return_objs[i].load(messageUnpacker);
            }
        }
        this.return_obj_catagory = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Response_msg() {
        this.catagory = CatagoryEnum.RESPONSE_MSG;
    }
}
