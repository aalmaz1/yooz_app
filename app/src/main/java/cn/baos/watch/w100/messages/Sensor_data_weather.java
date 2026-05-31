package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sensor_data_weather extends Sensor_data {
    public int current_temperature;
    public int current_weather_type;
    public daily_Weather[] daily_Weathers;
    public String location;
    public String provider;
    public int today_high_temperature;
    public int today_low_temperature;

    public static class daily_Weather {
        public int high_temperature;
        public int low_temperature;
        public int weather_type;

        public boolean put(MessagePacker messagePacker) throws IOException {
            messagePacker.packLong(this.high_temperature);
            messagePacker.packLong(this.low_temperature);
            messagePacker.packLong(this.weather_type);
            return true;
        }

        public daily_Weather load(MessageUnpacker messageUnpacker) throws IOException {
            this.high_temperature = (int) messageUnpacker.unpackLong();
            this.low_temperature = (int) messageUnpacker.unpackLong();
            this.weather_type = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.provider == null) {
            this.provider = "";
        }
        messagePacker.packString(this.provider);
        if (this.location == null) {
            this.location = "";
        }
        messagePacker.packString(this.location);
        messagePacker.packLong(this.current_weather_type);
        messagePacker.packLong(this.current_temperature);
        messagePacker.packLong(this.today_high_temperature);
        messagePacker.packLong(this.today_low_temperature);
        if (this.daily_Weathers != null) {
            messagePacker.packLong(r0.length);
            daily_Weather[] daily_weatherArr = this.daily_Weathers;
            if (daily_weatherArr.length <= 0) {
                return true;
            }
            for (daily_Weather daily_weather : daily_weatherArr) {
                daily_weather.put(messagePacker);
            }
            return true;
        }
        messagePacker.packLong(0L);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sensor_data_weather load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.provider = messageUnpacker.unpackString();
        this.location = messageUnpacker.unpackString();
        this.current_weather_type = (int) messageUnpacker.unpackLong();
        this.current_temperature = (int) messageUnpacker.unpackLong();
        this.today_high_temperature = (int) messageUnpacker.unpackLong();
        this.today_low_temperature = (int) messageUnpacker.unpackLong();
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.daily_Weathers = new daily_Weather[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.daily_Weathers[i] = new daily_Weather();
                this.daily_Weathers[i].load(messageUnpacker);
            }
        }
        return this;
    }

    public Sensor_data_weather() {
        this.catagory = CatagoryEnum.SENSOR_DATA_WEATHER;
    }
}
