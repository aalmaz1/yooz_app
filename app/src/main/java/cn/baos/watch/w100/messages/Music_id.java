package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Music_id extends Serializable {
    public int id;
    public String url;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.url == null) {
            this.url = "";
        }
        messagePacker.packString(this.url);
        messagePacker.packLong(this.id);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Music_id load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.url = messageUnpacker.unpackString();
        this.id = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Music_id() {
        this.catagory = CatagoryEnum.MUSIC_ID;
    }
}
