package cn.baos.watch.w100.messages;

import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
class Hb_date {
    public int day;
    public int month;
    public int year;

    public boolean put(MessagePacker messagePacker) throws IOException {
        messagePacker.packLong(this.year);
        messagePacker.packLong(this.month);
        messagePacker.packLong(this.day);
        return true;
    }

    public Hb_date load(MessageUnpacker messageUnpacker) throws IOException {
        this.year = (int) messageUnpacker.unpackLong();
        this.month = (int) messageUnpacker.unpackLong();
        this.day = (int) messageUnpacker.unpackLong();
        return this;
    }
}
