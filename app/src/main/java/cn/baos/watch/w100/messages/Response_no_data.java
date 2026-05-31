package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Response_no_data extends Serializable {
    public int data_catagary;
    public int enum_param;
    public int last_data_timestamp;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.data_catagary);
        messagePacker.packLong(this.last_data_timestamp);
        messagePacker.packLong(this.enum_param);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Response_no_data load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.data_catagary = (int) messageUnpacker.unpackLong();
        this.last_data_timestamp = (int) messageUnpacker.unpackLong();
        this.enum_param = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Response_no_data() {
        this.catagory = CatagoryEnum.RESPONSE_NO_DATA;
    }
}
