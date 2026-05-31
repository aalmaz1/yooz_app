package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sport_trace extends Sensor_data {
    public int begin_timestamp;
    public int end_timestamp;
    public byte[] points_x;
    public byte[] points_y;

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.begin_timestamp);
        messagePacker.packLong(this.end_timestamp);
        byte[] bArr = this.points_x;
        if (bArr != null) {
            messagePacker.packBinaryHeader(bArr.length);
            byte[] bArr2 = this.points_x;
            if (bArr2.length > 0) {
                messagePacker.writePayload(bArr2);
            }
        } else {
            messagePacker.packBinaryHeader(0);
        }
        byte[] bArr3 = this.points_y;
        if (bArr3 != null) {
            messagePacker.packBinaryHeader(bArr3.length);
            byte[] bArr4 = this.points_y;
            if (bArr4.length <= 0) {
                return true;
            }
            messagePacker.writePayload(bArr4);
            return true;
        }
        messagePacker.packBinaryHeader(0);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sport_trace load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.begin_timestamp = (int) messageUnpacker.unpackLong();
        this.end_timestamp = (int) messageUnpacker.unpackLong();
        int iUnpackBinaryHeader = messageUnpacker.unpackBinaryHeader();
        if (iUnpackBinaryHeader > 0) {
            this.points_x = messageUnpacker.readPayload(iUnpackBinaryHeader);
        }
        int iUnpackBinaryHeader2 = messageUnpacker.unpackBinaryHeader();
        if (iUnpackBinaryHeader2 > 0) {
            this.points_y = messageUnpacker.readPayload(iUnpackBinaryHeader2);
        }
        return this;
    }

    public Sport_trace() {
        this.catagory = CatagoryEnum.SPORT_TRACE;
    }
}
