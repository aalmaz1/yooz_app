package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class CommandWatchVersionResponse extends MessageBase {
    public String apollo_software_ver;
    public String hardware_ver;
    public String software_ver;

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.hardware_ver == null) {
            this.hardware_ver = "";
        }
        messagePacker.packString(this.hardware_ver);
        if (this.software_ver == null) {
            this.software_ver = "";
        }
        messagePacker.packString(this.software_ver);
        if (this.apollo_software_ver == null) {
            this.apollo_software_ver = "";
        }
        messagePacker.packString(this.apollo_software_ver);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public CommandWatchVersionResponse load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.hardware_ver = messageUnpacker.unpackString();
        this.software_ver = messageUnpacker.unpackString();
        this.apollo_software_ver = messageUnpacker.unpackString();
        return this;
    }

    public CommandWatchVersionResponse() {
        this.catagory = CatagoryEnum.COMMANDWATCHVERSIONRESPONSE;
    }
}
