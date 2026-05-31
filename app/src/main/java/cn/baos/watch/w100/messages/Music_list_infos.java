package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Music_list_infos extends Serializable {
    public Music_list_info lists;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        this.lists.put(messagePacker);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Music_list_infos load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        Music_list_info music_list_info = new Music_list_info();
        this.lists = music_list_info;
        music_list_info.load(messageUnpacker);
        return this;
    }

    public Music_list_infos() {
        this.catagory = CatagoryEnum.MUSIC_LIST_INFOS;
    }
}
