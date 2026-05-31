package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandBleOTAFileInfo extends MessageBase {
    public String desc;
    public int dest_addr;
    public int file_id;
    public String file_md5;
    public int file_size;
    public int file_type;
    public String hardware_id;
    public String name;
    public String new_version;
    public int old_addr;
    public String old_version;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.file_type);
        messagePacker.packLong(this.file_size);
        if (this.file_md5 == null) {
            this.file_md5 = "";
        }
        messagePacker.packString(this.file_md5);
        if (this.old_version == null) {
            this.old_version = "";
        }
        messagePacker.packString(this.old_version);
        if (this.new_version == null) {
            this.new_version = "";
        }
        messagePacker.packString(this.new_version);
        if (this.hardware_id == null) {
            this.hardware_id = "";
        }
        messagePacker.packString(this.hardware_id);
        if (this.name == null) {
            this.name = "";
        }
        messagePacker.packString(this.name);
        if (this.desc == null) {
            this.desc = "";
        }
        messagePacker.packString(this.desc);
        messagePacker.packLong(this.file_id);
        messagePacker.packLong(this.old_addr);
        messagePacker.packLong(this.dest_addr);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandBleOTAFileInfo load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.file_type = (int) messageUnpacker.unpackLong();
        this.file_size = (int) messageUnpacker.unpackLong();
        this.file_md5 = messageUnpacker.unpackString();
        this.old_version = messageUnpacker.unpackString();
        this.new_version = messageUnpacker.unpackString();
        this.hardware_id = messageUnpacker.unpackString();
        this.name = messageUnpacker.unpackString();
        this.desc = messageUnpacker.unpackString();
        this.file_id = (int) messageUnpacker.unpackLong();
        this.old_addr = (int) messageUnpacker.unpackLong();
        this.dest_addr = (int) messageUnpacker.unpackLong();
        return this;
    }

    public CommandBleOTAFileInfo() {
        this.catagory = CatagoryEnum.COMMANDBLEOTAFILEINFO;
    }
}
