package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_array extends Serializable {
    public int data_catagory;
    public Sensor_data_obj[] datas;

    public static class Sensor_data_obj {
        public byte[] obj;

        public boolean put(MessagePacker messagePacker) throws IOException {
            byte[] bArr = this.obj;
            if (bArr != null) {
                messagePacker.packBinaryHeader(bArr.length);
                byte[] bArr2 = this.obj;
                if (bArr2.length <= 0) {
                    return true;
                }
                messagePacker.writePayload(bArr2);
                return true;
            }
            messagePacker.packBinaryHeader(0);
            return true;
        }

        public Sensor_data_obj load(MessageUnpacker messageUnpacker) throws IOException {
            int iUnpackBinaryHeader = messageUnpacker.unpackBinaryHeader();
            if (iUnpackBinaryHeader > 0) {
                this.obj = messageUnpacker.readPayload(iUnpackBinaryHeader);
            }
            return this;
        }
    }

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.data_catagory);
        if (this.datas != null) {
            messagePacker.packLong(r0.length);
            Sensor_data_obj[] sensor_data_objArr = this.datas;
            if (sensor_data_objArr.length <= 0) {
                return true;
            }
            for (Sensor_data_obj sensor_data_obj : sensor_data_objArr) {
                sensor_data_obj.put(messagePacker);
            }
            return true;
        }
        messagePacker.packLong(0L);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Sensor_data_array load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.data_catagory = (int) messageUnpacker.unpackLong();
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.datas = new Sensor_data_obj[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.datas[i] = new Sensor_data_obj();
                this.datas[i].load(messageUnpacker);
            }
        }
        return this;
    }

    public Sensor_data_array() {
        this.catagory = CatagoryEnum.SENSOR_DATA_ARRAY;
    }
}
