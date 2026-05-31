package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Device_resource_info extends MessageBase {
    public int active_res_location;
    public Dev_res[] resource_array;
    public int resource_count;
    public int resource_type;
    public int sync_param;
    public int sync_reason;

    public static class Dev_res {
        public int location_index;
        public int resource_id;

        public boolean put(MessagePacker messagePacker) throws IOException {
            messagePacker.packLong(this.location_index);
            messagePacker.packLong(this.resource_id);
            return true;
        }

        public Dev_res load(MessageUnpacker messageUnpacker) throws IOException {
            this.location_index = (int) messageUnpacker.unpackLong();
            this.resource_id = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.resource_type);
        messagePacker.packLong(this.sync_reason);
        messagePacker.packLong(this.sync_param);
        messagePacker.packLong(this.resource_count);
        messagePacker.packLong(this.active_res_location);
        if (this.resource_array != null) {
            messagePacker.packLong(r0.length);
            Dev_res[] dev_resArr = this.resource_array;
            if (dev_resArr.length <= 0) {
                return true;
            }
            for (Dev_res dev_res : dev_resArr) {
                dev_res.put(messagePacker);
            }
            return true;
        }
        messagePacker.packLong(0L);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Device_resource_info load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.resource_type = (int) messageUnpacker.unpackLong();
        this.sync_reason = (int) messageUnpacker.unpackLong();
        this.sync_param = (int) messageUnpacker.unpackLong();
        this.resource_count = (int) messageUnpacker.unpackLong();
        this.active_res_location = (int) messageUnpacker.unpackLong();
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.resource_array = new Dev_res[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.resource_array[i] = new Dev_res();
                this.resource_array[i].load(messageUnpacker);
            }
        }
        return this;
    }

    public Device_resource_info() {
        this.catagory = CatagoryEnum.DEVICE_RESOURCE_INFO;
    }
}
