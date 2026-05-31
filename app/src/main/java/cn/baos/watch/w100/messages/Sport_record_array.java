package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sport_record_array extends Serializable {
    public Sport_record[] datas;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.datas != null) {
            messagePacker.packLong(r0.length);
            Sport_record[] sport_recordArr = this.datas;
            if (sport_recordArr.length <= 0) {
                return true;
            }
            for (Sport_record sport_record : sport_recordArr) {
                sport_record.put(messagePacker);
            }
            return true;
        }
        messagePacker.packLong(0L);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Sport_record_array load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.datas = new Sport_record[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.datas[i] = new Sport_record();
                this.datas[i].load(messageUnpacker);
            }
        }
        return this;
    }

    public Sport_record_array() {
        this.catagory = CatagoryEnum.SPORT_RECORD_ARRAY;
    }
}
