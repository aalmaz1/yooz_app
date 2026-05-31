package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Bt_slave_devices extends Serializable {
    public Bt_slave_device[] devices;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.devices != null) {
            messagePacker.packLong(r0.length);
            Bt_slave_device[] bt_slave_deviceArr = this.devices;
            if (bt_slave_deviceArr.length <= 0) {
                return true;
            }
            for (Bt_slave_device bt_slave_device : bt_slave_deviceArr) {
                bt_slave_device.put(messagePacker);
            }
            return true;
        }
        messagePacker.packLong(0L);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Bt_slave_devices load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.devices = new Bt_slave_device[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.devices[i] = new Bt_slave_device();
                this.devices[i].load(messageUnpacker);
            }
        }
        return this;
    }

    public Bt_slave_devices() {
        this.catagory = CatagoryEnum.BT_SLAVE_DEVICES;
    }
}
