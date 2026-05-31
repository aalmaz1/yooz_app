package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Music_play_context extends Serializable {
    public Music_info music;
    public Music_list_info music_list;
    public int play_mode;
    public int play_pos_file;
    public int play_pos_ms;
    public int play_status;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        this.music_list.put(messagePacker);
        this.music.put(messagePacker);
        messagePacker.packLong(this.play_pos_ms);
        messagePacker.packLong(this.play_pos_file);
        messagePacker.packLong(this.play_status);
        messagePacker.packLong(this.play_mode);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Music_play_context load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        Music_list_info music_list_info = new Music_list_info();
        this.music_list = music_list_info;
        music_list_info.load(messageUnpacker);
        Music_info music_info = new Music_info();
        this.music = music_info;
        music_info.load(messageUnpacker);
        this.play_pos_ms = (int) messageUnpacker.unpackLong();
        this.play_pos_file = (int) messageUnpacker.unpackLong();
        this.play_status = (int) messageUnpacker.unpackLong();
        this.play_mode = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Music_play_context() {
        this.catagory = CatagoryEnum.MUSIC_PLAY_CONTEXT;
    }
}
