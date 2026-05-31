package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Recent_Call_info extends Serializable {
    public Call_info[] calls;

    public static class Call_info {
        public int count;
        public int direction;
        public int last_dir;
        public int last_status;
        public String name;
        public String number;
        public int time;

        public boolean put(MessagePacker messagePacker) throws IOException {
            if (this.name == null) {
                this.name = "";
            }
            messagePacker.packString(this.name);
            if (this.number == null) {
                this.number = "";
            }
            messagePacker.packString(this.number);
            messagePacker.packLong(this.time);
            messagePacker.packLong(this.direction);
            messagePacker.packLong(this.count);
            messagePacker.packLong(this.last_dir);
            messagePacker.packLong(this.last_status);
            return true;
        }

        public Call_info load(MessageUnpacker messageUnpacker) throws IOException {
            this.name = messageUnpacker.unpackString();
            this.number = messageUnpacker.unpackString();
            this.time = (int) messageUnpacker.unpackLong();
            this.direction = (int) messageUnpacker.unpackLong();
            this.count = (int) messageUnpacker.unpackLong();
            this.last_dir = (int) messageUnpacker.unpackLong();
            this.last_status = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.calls != null) {
            messagePacker.packLong(r0.length);
            Call_info[] call_infoArr = this.calls;
            if (call_infoArr.length <= 0) {
                return true;
            }
            for (Call_info call_info : call_infoArr) {
                call_info.put(messagePacker);
            }
            return true;
        }
        messagePacker.packLong(0L);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Recent_Call_info load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.calls = new Call_info[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.calls[i] = new Call_info();
                this.calls[i].load(messageUnpacker);
            }
        }
        return this;
    }

    public Recent_Call_info() {
        this.catagory = CatagoryEnum.RECENT_CALL_INFO;
    }
}
