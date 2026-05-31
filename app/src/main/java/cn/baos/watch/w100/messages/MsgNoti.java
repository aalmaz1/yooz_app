package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class MsgNoti extends MessageBase {
    public int arr_time;
    public int msg_id;
    public int reserve;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.arr_time);
        messagePacker.packLong(this.msg_id);
        messagePacker.packLong(this.reserve);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public MsgNoti load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.arr_time = (int) messageUnpacker.unpackLong();
        this.msg_id = (int) messageUnpacker.unpackLong();
        this.reserve = (int) messageUnpacker.unpackLong();
        return this;
    }

    public MsgNoti() {
        this.catagory = CatagoryEnum.MSGNOTI;
    }
}
