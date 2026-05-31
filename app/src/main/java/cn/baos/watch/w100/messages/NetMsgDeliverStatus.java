package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class NetMsgDeliverStatus extends Serializable {
    public int session_id;
    public int status;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.session_id);
        messagePacker.packLong(this.status);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public NetMsgDeliverStatus load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.session_id = (int) messageUnpacker.unpackLong();
        this.status = (int) messageUnpacker.unpackLong();
        return this;
    }

    public NetMsgDeliverStatus() {
        this.catagory = CatagoryEnum.NETMSGDELIVERSTATUS;
    }
}
