package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Config_items extends Serializable {
    public int[] datas;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.datas != null) {
            messagePacker.packLong(r0.length);
            int[] iArr = this.datas;
            if (iArr.length <= 0) {
                return true;
            }
            for (int i : iArr) {
                messagePacker.packLong(i);
            }
            return true;
        }
        messagePacker.packLong(0L);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Config_items load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.datas = new int[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.datas[i] = (int) messageUnpacker.unpackLong();
            }
        }
        return this;
    }

    public Config_items() {
        this.catagory = CatagoryEnum.CONFIG_ITEMS;
    }
}
