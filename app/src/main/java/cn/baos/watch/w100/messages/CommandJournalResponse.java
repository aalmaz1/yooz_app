package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandJournalResponse extends MessageBase {
    public int from;
    public String mac_address;
    public String response;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.from);
        if (this.mac_address == null) {
            this.mac_address = "";
        }
        messagePacker.packString(this.mac_address);
        if (this.response == null) {
            this.response = "";
        }
        messagePacker.packString(this.response);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandJournalResponse load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.from = (int) messageUnpacker.unpackLong();
        this.mac_address = messageUnpacker.unpackString();
        this.response = messageUnpacker.unpackString();
        return this;
    }

    public CommandJournalResponse() {
        this.catagory = CatagoryEnum.COMMANDJOURNALRESPONSE;
    }
}
