package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Device_logs extends MessageBase {
    public Device_log_content[] logs;
    public String mac_address;
    public int reserve;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.mac_address == null) {
            this.mac_address = "";
        }
        messagePacker.packString(this.mac_address);
        if (this.logs != null) {
            messagePacker.packLong(r0.length);
            Device_log_content[] device_log_contentArr = this.logs;
            if (device_log_contentArr.length > 0) {
                for (Device_log_content device_log_content : device_log_contentArr) {
                    device_log_content.put(messagePacker);
                }
            }
        } else {
            messagePacker.packLong(0L);
        }
        messagePacker.packLong(this.reserve);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Device_logs load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.mac_address = messageUnpacker.unpackString();
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.logs = new Device_log_content[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.logs[i] = new Device_log_content();
                this.logs[i].load(messageUnpacker);
            }
        }
        this.reserve = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Device_logs() {
        this.catagory = CatagoryEnum.DEVICE_LOGS;
    }
}
