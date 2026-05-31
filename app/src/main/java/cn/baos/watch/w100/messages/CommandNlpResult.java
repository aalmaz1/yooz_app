package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandNlpResult extends MessageBase {
    public int action_id;
    public String show_text;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.show_text == null) {
            this.show_text = "";
        }
        messagePacker.packString(this.show_text);
        messagePacker.packLong(this.action_id);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandNlpResult load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.show_text = messageUnpacker.unpackString();
        this.action_id = (int) messageUnpacker.unpackLong();
        return this;
    }

    public CommandNlpResult() {
        this.catagory = CatagoryEnum.COMMANDNLPRESULT;
    }
}
