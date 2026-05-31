package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Music_info extends Serializable {
    public Music_id music_id;
    public Music_tag tag;

    public static class Music_tag {
        public String album;
        public String artist;
        public int duration_ms;
        public String title;

        public boolean put(MessagePacker messagePacker) throws IOException {
            if (this.title == null) {
                this.title = "";
            }
            messagePacker.packString(this.title);
            if (this.artist == null) {
                this.artist = "";
            }
            messagePacker.packString(this.artist);
            if (this.album == null) {
                this.album = "";
            }
            messagePacker.packString(this.album);
            messagePacker.packLong(this.duration_ms);
            return true;
        }

        public Music_tag load(MessageUnpacker messageUnpacker) throws IOException {
            this.title = messageUnpacker.unpackString();
            this.artist = messageUnpacker.unpackString();
            this.album = messageUnpacker.unpackString();
            this.duration_ms = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        this.music_id.put(messagePacker);
        this.tag.put(messagePacker);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Music_info load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        Music_id music_id = new Music_id();
        this.music_id = music_id;
        music_id.load(messageUnpacker);
        Music_tag music_tag = new Music_tag();
        this.tag = music_tag;
        music_tag.load(messageUnpacker);
        return this;
    }

    public Music_info() {
        this.catagory = CatagoryEnum.MUSIC_INFO;
    }
}
