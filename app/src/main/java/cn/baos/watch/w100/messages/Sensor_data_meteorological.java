package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_meteorological extends Sensor_data {
    public Meteorological[] datas;

    public static class Meteorological {
        public int UVI;
        public int air_pressure;
        public int cloud_amount;
        public int humidity;
        public int precipitation;
        public int reserve;
        public int wind_direction;
        public int wind_speed;

        public boolean put(MessagePacker messagePacker) throws IOException {
            messagePacker.packLong(this.air_pressure);
            messagePacker.packLong(this.precipitation);
            messagePacker.packLong(this.UVI);
            messagePacker.packLong(this.humidity);
            messagePacker.packLong(this.wind_speed);
            messagePacker.packLong(this.wind_direction);
            messagePacker.packLong(this.cloud_amount);
            messagePacker.packLong(this.reserve);
            return true;
        }

        public Meteorological load(MessageUnpacker messageUnpacker) throws IOException {
            this.air_pressure = (int) messageUnpacker.unpackLong();
            this.precipitation = (int) messageUnpacker.unpackLong();
            this.UVI = (int) messageUnpacker.unpackLong();
            this.humidity = (int) messageUnpacker.unpackLong();
            this.wind_speed = (int) messageUnpacker.unpackLong();
            this.wind_direction = (int) messageUnpacker.unpackLong();
            this.cloud_amount = (int) messageUnpacker.unpackLong();
            this.reserve = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.datas != null) {
            messagePacker.packLong(r0.length);
            Meteorological[] meteorologicalArr = this.datas;
            if (meteorologicalArr.length <= 0) {
                return true;
            }
            for (Meteorological meteorological : meteorologicalArr) {
                meteorological.put(messagePacker);
            }
            return true;
        }
        messagePacker.packLong(0L);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data_meteorological load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.datas = new Meteorological[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.datas[i] = new Meteorological();
                this.datas[i].load(messageUnpacker);
            }
        }
        return this;
    }

    public Sensor_data_meteorological() {
        this.catagory = CatagoryEnum.SENSOR_DATA_METEOROLOGICAL;
    }
}
