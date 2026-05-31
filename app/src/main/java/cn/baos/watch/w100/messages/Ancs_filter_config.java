package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Ancs_filter_config extends MessageBase {
    public String[] app_id_array;
    public int show_or_hide;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.show_or_hide);
        if (this.app_id_array != null) {
            messagePacker.packLong(r0.length);
            String[] strArr = this.app_id_array;
            if (strArr.length <= 0) {
                return true;
            }
            for (String str : strArr) {
                messagePacker.packString(str);
            }
            return true;
        }
        messagePacker.packLong(0L);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Ancs_filter_config load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.show_or_hide = (int) messageUnpacker.unpackLong();
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.app_id_array = new String[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.app_id_array[i] = messageUnpacker.unpackString();
            }
        }
        return this;
    }

    public Ancs_filter_config() {
        this.catagory = CatagoryEnum.ANCS_FILTER_CONFIG;
    }
}
