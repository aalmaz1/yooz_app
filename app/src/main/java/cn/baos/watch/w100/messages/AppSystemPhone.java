package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class AppSystemPhone extends MessageBase {
    public String contacter;
    public String phone_num;
    public int phone_state;
    public int start_time_s;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.phone_num == null) {
            this.phone_num = "";
        }
        messagePacker.packString(this.phone_num);
        if (this.contacter == null) {
            this.contacter = "";
        }
        messagePacker.packString(this.contacter);
        messagePacker.packLong(this.phone_state);
        messagePacker.packLong(this.start_time_s);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public AppSystemPhone load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.phone_num = messageUnpacker.unpackString();
        this.contacter = messageUnpacker.unpackString();
        this.phone_state = (int) messageUnpacker.unpackLong();
        this.start_time_s = (int) messageUnpacker.unpackLong();
        return this;
    }

    public AppSystemPhone() {
        this.catagory = CatagoryEnum.APPSYSTEMPHONE;
    }
}
