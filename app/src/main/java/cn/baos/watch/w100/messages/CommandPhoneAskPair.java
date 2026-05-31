package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandPhoneAskPair extends MessageBase {
    public int match_code;
    public String phone_type;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.phone_type == null) {
            this.phone_type = "";
        }
        messagePacker.packString(this.phone_type);
        messagePacker.packLong(this.match_code);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandPhoneAskPair load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.phone_type = messageUnpacker.unpackString();
        this.match_code = (int) messageUnpacker.unpackLong();
        return this;
    }

    public CommandPhoneAskPair() {
        this.catagory = CatagoryEnum.COMMANDPHONEASKPAIR;
    }
}
