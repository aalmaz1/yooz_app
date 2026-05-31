package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class OtaUpdateUi extends CommandBleOTAFileInfo {
    public int ota_status;
    public int recv_size;

    @Override // cn.baos.watch.w100.messages.CommandBleOTAFileInfo, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.ota_status);
        messagePacker.packLong(this.recv_size);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.CommandBleOTAFileInfo, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public OtaUpdateUi load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.ota_status = (int) messageUnpacker.unpackLong();
        this.recv_size = (int) messageUnpacker.unpackLong();
        return this;
    }

    public OtaUpdateUi() {
        this.catagory = CatagoryEnum.OTAUPDATEUI;
    }
}
