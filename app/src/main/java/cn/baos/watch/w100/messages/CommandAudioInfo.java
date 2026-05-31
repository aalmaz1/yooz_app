package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandAudioInfo extends MessageBase {
    public int run_time;
    public String singer;
    public String song_name;
    public int state;
    public int total_time;
    public int volume;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.song_name == null) {
            this.song_name = "";
        }
        messagePacker.packString(this.song_name);
        if (this.singer == null) {
            this.singer = "";
        }
        messagePacker.packString(this.singer);
        messagePacker.packLong(this.run_time);
        messagePacker.packLong(this.total_time);
        messagePacker.packLong(this.state);
        messagePacker.packLong(this.volume);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandAudioInfo load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.song_name = messageUnpacker.unpackString();
        this.singer = messageUnpacker.unpackString();
        this.run_time = (int) messageUnpacker.unpackLong();
        this.total_time = (int) messageUnpacker.unpackLong();
        this.state = (int) messageUnpacker.unpackLong();
        this.volume = (int) messageUnpacker.unpackLong();
        return this;
    }

    public CommandAudioInfo() {
        this.catagory = CatagoryEnum.COMMANDAUDIOINFO;
    }
}
