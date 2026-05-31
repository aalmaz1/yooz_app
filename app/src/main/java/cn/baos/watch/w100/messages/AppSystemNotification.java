package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class AppSystemNotification extends MessageBase {
    public String content;
    public int highlight;
    public String package_name;
    public int start_time_s;
    public String title;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.title == null) {
            this.title = "";
        }
        messagePacker.packString(this.title);
        if (this.content == null) {
            this.content = "";
        }
        messagePacker.packString(this.content);
        messagePacker.packLong(this.highlight);
        if (this.package_name == null) {
            this.package_name = "";
        }
        messagePacker.packString(this.package_name);
        messagePacker.packLong(this.start_time_s);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public AppSystemNotification load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.title = messageUnpacker.unpackString();
        this.content = messageUnpacker.unpackString();
        this.highlight = (int) messageUnpacker.unpackLong();
        this.package_name = messageUnpacker.unpackString();
        this.start_time_s = (int) messageUnpacker.unpackLong();
        return this;
    }

    public AppSystemNotification() {
        this.catagory = CatagoryEnum.APPSYSTEMNOTIFICATION;
    }
}
