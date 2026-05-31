package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Music_list_info extends Serializable {
    public int count;
    public int id;
    public String name;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.name == null) {
            this.name = "";
        }
        messagePacker.packString(this.name);
        messagePacker.packLong(this.count);
        messagePacker.packLong(this.id);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Music_list_info load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.name = messageUnpacker.unpackString();
        this.count = (int) messageUnpacker.unpackLong();
        this.id = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Music_list_info() {
        this.catagory = CatagoryEnum.MUSIC_LIST_INFO;
    }
}
