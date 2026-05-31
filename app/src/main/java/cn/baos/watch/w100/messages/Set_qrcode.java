package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Set_qrcode extends Serializable {
    public String text;
    public int type;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.type);
        if (this.text == null) {
            this.text = "";
        }
        messagePacker.packString(this.text);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Set_qrcode load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.type = (int) messageUnpacker.unpackLong();
        this.text = messageUnpacker.unpackString();
        return this;
    }

    public Set_qrcode() {
        this.catagory = CatagoryEnum.SET_QRCODE;
    }
}
