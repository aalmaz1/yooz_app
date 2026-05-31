package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Hbapp_sync_peer extends Serializable {
    public int app_id;
    public String txt;
    public int u32v1;
    public int u32v2;
    public int u32v3;
    public int u32v4;
    public int u32v5;
    public int u32v6;
    public int u32v7;
    public int u8v1;
    public int u8v2;
    public int u8v3;
    public int u8v4;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.txt == null) {
            this.txt = "";
        }
        messagePacker.packString(this.txt);
        messagePacker.packLong(this.app_id);
        messagePacker.packLong(this.u32v1);
        messagePacker.packLong(this.u32v2);
        messagePacker.packLong(this.u32v3);
        messagePacker.packLong(this.u32v4);
        messagePacker.packLong(this.u32v5);
        messagePacker.packLong(this.u32v6);
        messagePacker.packLong(this.u32v7);
        messagePacker.packLong(this.u8v1);
        messagePacker.packLong(this.u8v2);
        messagePacker.packLong(this.u8v3);
        messagePacker.packLong(this.u8v4);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Hbapp_sync_peer load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.txt = messageUnpacker.unpackString();
        this.app_id = (int) messageUnpacker.unpackLong();
        this.u32v1 = (int) messageUnpacker.unpackLong();
        this.u32v2 = (int) messageUnpacker.unpackLong();
        this.u32v3 = (int) messageUnpacker.unpackLong();
        this.u32v4 = (int) messageUnpacker.unpackLong();
        this.u32v5 = (int) messageUnpacker.unpackLong();
        this.u32v6 = (int) messageUnpacker.unpackLong();
        this.u32v7 = (int) messageUnpacker.unpackLong();
        this.u8v1 = (int) messageUnpacker.unpackLong();
        this.u8v2 = (int) messageUnpacker.unpackLong();
        this.u8v3 = (int) messageUnpacker.unpackLong();
        this.u8v4 = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Hbapp_sync_peer() {
        this.catagory = CatagoryEnum.HBAPP_SYNC_PEER;
    }
}
