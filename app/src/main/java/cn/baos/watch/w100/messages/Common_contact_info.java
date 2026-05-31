package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Common_contact_info extends Serializable {
    public Contact_info[] contacts;

    public static class Contact_info {
        public int flag;
        public String name;
        public String number;

        public boolean put(MessagePacker messagePacker) throws IOException {
            if (this.name == null) {
                this.name = "";
            }
            messagePacker.packString(this.name);
            if (this.number == null) {
                this.number = "";
            }
            messagePacker.packString(this.number);
            messagePacker.packLong(this.flag);
            return true;
        }

        public Contact_info load(MessageUnpacker messageUnpacker) throws IOException {
            this.name = messageUnpacker.unpackString();
            this.number = messageUnpacker.unpackString();
            this.flag = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.contacts != null) {
            messagePacker.packLong(r0.length);
            Contact_info[] contact_infoArr = this.contacts;
            if (contact_infoArr.length <= 0) {
                return true;
            }
            for (Contact_info contact_info : contact_infoArr) {
                contact_info.put(messagePacker);
            }
            return true;
        }
        messagePacker.packLong(0L);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Common_contact_info load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.contacts = new Contact_info[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.contacts[i] = new Contact_info();
                this.contacts[i].load(messageUnpacker);
            }
        }
        return this;
    }

    public Common_contact_info() {
        this.catagory = CatagoryEnum.COMMON_CONTACT_INFO;
    }
}
