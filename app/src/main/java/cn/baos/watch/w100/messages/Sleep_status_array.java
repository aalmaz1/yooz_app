package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sleep_status_array extends Serializable {
    public Sleep_status[] datas;

    public static class Sleep_status {
        public int status;
        public int timestamp;

        public boolean put(MessagePacker messagePacker) throws IOException {
            messagePacker.packLong(this.timestamp);
            messagePacker.packLong(this.status);
            return true;
        }

        public Sleep_status load(MessageUnpacker messageUnpacker) throws IOException {
            this.timestamp = (int) messageUnpacker.unpackLong();
            this.status = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.datas != null) {
            messagePacker.packLong(r0.length);
            Sleep_status[] sleep_statusArr = this.datas;
            if (sleep_statusArr.length <= 0) {
                return true;
            }
            for (Sleep_status sleep_status : sleep_statusArr) {
                sleep_status.put(messagePacker);
            }
            return true;
        }
        messagePacker.packLong(0L);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Sleep_status_array load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.datas = new Sleep_status[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.datas[i] = new Sleep_status();
                this.datas[i].load(messageUnpacker);
            }
        }
        return this;
    }

    public Sleep_status_array() {
        this.catagory = CatagoryEnum.SLEEP_STATUS_ARRAY;
    }
}
