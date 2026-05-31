package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Device_base_info extends Serializable {
    public String device_model;
    public String device_name;
    public String hardware_version;
    public String serial_number;
    public String software_version;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.device_model == null) {
            this.device_model = "";
        }
        messagePacker.packString(this.device_model);
        if (this.device_name == null) {
            this.device_name = "";
        }
        messagePacker.packString(this.device_name);
        if (this.serial_number == null) {
            this.serial_number = "";
        }
        messagePacker.packString(this.serial_number);
        if (this.software_version == null) {
            this.software_version = "";
        }
        messagePacker.packString(this.software_version);
        if (this.hardware_version == null) {
            this.hardware_version = "";
        }
        messagePacker.packString(this.hardware_version);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Device_base_info load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.device_model = messageUnpacker.unpackString();
        this.device_name = messageUnpacker.unpackString();
        this.serial_number = messageUnpacker.unpackString();
        this.software_version = messageUnpacker.unpackString();
        this.hardware_version = messageUnpacker.unpackString();
        return this;
    }

    public Device_base_info() {
        this.catagory = CatagoryEnum.DEVICE_BASE_INFO;
    }
}
