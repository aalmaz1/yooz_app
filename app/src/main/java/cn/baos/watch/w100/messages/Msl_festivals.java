package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Msl_festivals extends Serializable {
    public Msl_festival[] datas;

    public static class Msl_festival {
        public Hb_date begin_date_msl;
        public int begin_timestamp;
        public int duration_day;
        public int type;

        public boolean put(MessagePacker messagePacker) throws IOException {
            messagePacker.packLong(this.begin_timestamp);
            this.begin_date_msl.put(messagePacker);
            messagePacker.packLong(this.duration_day);
            messagePacker.packLong(this.type);
            return true;
        }

        public Msl_festival load(MessageUnpacker messageUnpacker) throws IOException {
            this.begin_timestamp = (int) messageUnpacker.unpackLong();
            Hb_date hb_date = new Hb_date();
            this.begin_date_msl = hb_date;
            hb_date.load(messageUnpacker);
            this.duration_day = (int) messageUnpacker.unpackLong();
            this.type = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.datas != null) {
            messagePacker.packLong(r0.length);
            Msl_festival[] msl_festivalArr = this.datas;
            if (msl_festivalArr.length <= 0) {
                return true;
            }
            for (Msl_festival msl_festival : msl_festivalArr) {
                msl_festival.put(messagePacker);
            }
            return true;
        }
        messagePacker.packLong(0L);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Msl_festivals load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.datas = new Msl_festival[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.datas[i] = new Msl_festival();
                this.datas[i].load(messageUnpacker);
            }
        }
        return this;
    }

    public Msl_festivals() {
        this.catagory = CatagoryEnum.MSL_FESTIVALS;
    }
}
