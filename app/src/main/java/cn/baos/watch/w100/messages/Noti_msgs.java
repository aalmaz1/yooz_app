package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Noti_msgs extends Serializable {
    public AppSystemNotification[] msgs;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.msgs != null) {
            messagePacker.packLong(r0.length);
            AppSystemNotification[] appSystemNotificationArr = this.msgs;
            if (appSystemNotificationArr.length <= 0) {
                return true;
            }
            for (AppSystemNotification appSystemNotification : appSystemNotificationArr) {
                appSystemNotification.put(messagePacker);
            }
            return true;
        }
        messagePacker.packLong(0L);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Noti_msgs load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.msgs = new AppSystemNotification[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.msgs[i] = new AppSystemNotification();
                this.msgs[i].load(messageUnpacker);
            }
        }
        return this;
    }

    public Noti_msgs() {
        this.catagory = CatagoryEnum.NOTI_MSGS;
    }
}
