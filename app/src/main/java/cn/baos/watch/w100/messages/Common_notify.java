package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Common_notify extends Serializable {
    public int notify_type;
    public int param;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.notify_type);
        messagePacker.packLong(this.param);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Common_notify load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.notify_type = (int) messageUnpacker.unpackLong();
        this.param = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Common_notify() {
        this.catagory = CatagoryEnum.COMMON_NOTIFY;
    }
}
