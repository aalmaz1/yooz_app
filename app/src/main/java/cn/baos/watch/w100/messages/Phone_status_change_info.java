package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Phone_status_change_info extends Serializable {
    public String contact;
    public String number;
    public int reserved1;
    public int reserved2;
    public int status;
    public int tick;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.status);
        if (this.contact == null) {
            this.contact = "";
        }
        messagePacker.packString(this.contact);
        if (this.number == null) {
            this.number = "";
        }
        messagePacker.packString(this.number);
        messagePacker.packLong(this.tick);
        messagePacker.packLong(this.reserved1);
        messagePacker.packLong(this.reserved2);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Phone_status_change_info load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.status = (int) messageUnpacker.unpackLong();
        this.contact = messageUnpacker.unpackString();
        this.number = messageUnpacker.unpackString();
        this.tick = (int) messageUnpacker.unpackLong();
        this.reserved1 = (int) messageUnpacker.unpackLong();
        this.reserved2 = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Phone_status_change_info() {
        this.catagory = CatagoryEnum.PHONE_STATUS_CHANGE_INFO;
    }
}
