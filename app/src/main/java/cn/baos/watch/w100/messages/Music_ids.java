package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Music_ids extends Serializable {
    public Music_id[] ids;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.ids != null) {
            messagePacker.packLong(r0.length);
            Music_id[] music_idArr = this.ids;
            if (music_idArr.length <= 0) {
                return true;
            }
            for (Music_id music_id : music_idArr) {
                music_id.put(messagePacker);
            }
            return true;
        }
        messagePacker.packLong(0L);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Music_ids load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.ids = new Music_id[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.ids[i] = new Music_id();
                this.ids[i].load(messageUnpacker);
            }
        }
        return this;
    }

    public Music_ids() {
        this.catagory = CatagoryEnum.MUSIC_IDS;
    }
}
