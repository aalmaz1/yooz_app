package cn.baos.watch.w100.messages;

import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: compiled from: Hb_date.java */
/* JADX INFO: loaded from: classes.dex */
class Hb_timespan {
    public int begin_hour;
    public int begin_min;
    public int end_hour;
    public int end_min;

    public boolean put(MessagePacker messagePacker) throws IOException {
        messagePacker.packLong(this.begin_hour);
        messagePacker.packLong(this.begin_min);
        messagePacker.packLong(this.end_hour);
        messagePacker.packLong(this.end_min);
        return true;
    }

    public Hb_timespan load(MessageUnpacker messageUnpacker) throws IOException {
        this.begin_hour = (int) messageUnpacker.unpackLong();
        this.begin_min = (int) messageUnpacker.unpackLong();
        this.end_hour = (int) messageUnpacker.unpackLong();
        this.end_min = (int) messageUnpacker.unpackLong();
        return this;
    }
}
