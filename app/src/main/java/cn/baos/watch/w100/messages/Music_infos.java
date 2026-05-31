package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Music_infos extends Serializable {
    public Music_info[] musics;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.musics != null) {
            messagePacker.packLong(r0.length);
            Music_info[] music_infoArr = this.musics;
            if (music_infoArr.length <= 0) {
                return true;
            }
            for (Music_info music_info : music_infoArr) {
                music_info.put(messagePacker);
            }
            return true;
        }
        messagePacker.packLong(0L);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Music_infos load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.musics = new Music_info[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.musics[i] = new Music_info();
                this.musics[i].load(messageUnpacker);
            }
        }
        return this;
    }

    public Music_infos() {
        this.catagory = CatagoryEnum.MUSIC_INFOS;
    }
}
