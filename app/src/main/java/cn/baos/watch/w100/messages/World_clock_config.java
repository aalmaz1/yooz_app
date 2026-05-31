package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class World_clock_config extends Serializable {
    public World_clock[] clocks;
    public int reserve;

    public static class World_clock {
        public String name;
        public int reserve;
        public int timezone;

        public boolean put(MessagePacker messagePacker) throws IOException {
            if (this.name == null) {
                this.name = "";
            }
            messagePacker.packString(this.name);
            messagePacker.packLong(this.timezone);
            messagePacker.packLong(this.reserve);
            return true;
        }

        public World_clock load(MessageUnpacker messageUnpacker) throws IOException {
            this.name = messageUnpacker.unpackString();
            this.timezone = (int) messageUnpacker.unpackLong();
            this.reserve = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.clocks != null) {
            messagePacker.packLong(r0.length);
            World_clock[] world_clockArr = this.clocks;
            if (world_clockArr.length > 0) {
                for (World_clock world_clock : world_clockArr) {
                    world_clock.put(messagePacker);
                }
            }
        } else {
            messagePacker.packLong(0L);
        }
        messagePacker.packLong(this.reserve);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public World_clock_config load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.clocks = new World_clock[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.clocks[i] = new World_clock();
                this.clocks[i].load(messageUnpacker);
            }
        }
        this.reserve = (int) messageUnpacker.unpackLong();
        return this;
    }

    public World_clock_config() {
        this.catagory = CatagoryEnum.WORLD_CLOCK_CONFIG;
    }
}
