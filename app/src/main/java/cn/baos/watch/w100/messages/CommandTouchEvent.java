package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandTouchEvent extends MessageBase {
    public int state;
    public int touch_x;
    public int touch_y;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.state);
        messagePacker.packLong(this.touch_x);
        messagePacker.packLong(this.touch_y);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandTouchEvent load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.state = (int) messageUnpacker.unpackLong();
        this.touch_x = (int) messageUnpacker.unpackLong();
        this.touch_y = (int) messageUnpacker.unpackLong();
        return this;
    }

    public CommandTouchEvent() {
        this.catagory = CatagoryEnum.COMMANDTOUCHEVENT;
    }
}
