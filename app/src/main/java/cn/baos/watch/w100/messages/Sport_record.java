package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sport_record extends Sensor_data {
    public int begin_timestamp;
    public int end_timestamp;
    public int initiator;
    public int mode;
    public Sporting_remind remind;
    public int status;
    public Sport_target target;
    public int timezone;

    public static class Sport_target {
        public int calories;
        public int distance_m;
        public int times_s;

        public boolean put(MessagePacker messagePacker) throws IOException {
            messagePacker.packLong(this.distance_m);
            messagePacker.packLong(this.calories);
            messagePacker.packLong(this.times_s);
            return true;
        }

        public Sport_target load(MessageUnpacker messageUnpacker) throws IOException {
            this.distance_m = (int) messageUnpacker.unpackLong();
            this.calories = (int) messageUnpacker.unpackLong();
            this.times_s = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    public static class Sporting_remind {
        public int distance_per_m;
        public int max_heartrate;
        public int max_pace_s;
        public int min_pace_s;
        public int times_per_s;

        public boolean put(MessagePacker messagePacker) throws IOException {
            messagePacker.packLong(this.distance_per_m);
            messagePacker.packLong(this.times_per_s);
            messagePacker.packLong(this.max_pace_s);
            messagePacker.packLong(this.min_pace_s);
            messagePacker.packLong(this.max_heartrate);
            return true;
        }

        public Sporting_remind load(MessageUnpacker messageUnpacker) throws IOException {
            this.distance_per_m = (int) messageUnpacker.unpackLong();
            this.times_per_s = (int) messageUnpacker.unpackLong();
            this.max_pace_s = (int) messageUnpacker.unpackLong();
            this.min_pace_s = (int) messageUnpacker.unpackLong();
            this.max_heartrate = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        this.target.put(messagePacker);
        this.remind.put(messagePacker);
        messagePacker.packLong(this.begin_timestamp);
        messagePacker.packLong(this.end_timestamp);
        messagePacker.packLong(this.timezone);
        messagePacker.packLong(this.initiator);
        messagePacker.packLong(this.mode);
        messagePacker.packLong(this.status);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.Sensor_data, cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Sport_record load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        Sport_target sport_target = new Sport_target();
        this.target = sport_target;
        sport_target.load(messageUnpacker);
        Sporting_remind sporting_remind = new Sporting_remind();
        this.remind = sporting_remind;
        sporting_remind.load(messageUnpacker);
        this.begin_timestamp = (int) messageUnpacker.unpackLong();
        this.end_timestamp = (int) messageUnpacker.unpackLong();
        this.timezone = (int) messageUnpacker.unpackLong();
        this.initiator = (int) messageUnpacker.unpackLong();
        this.mode = (int) messageUnpacker.unpackLong();
        this.status = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sport_record() {
        this.catagory = CatagoryEnum.SPORT_RECORD;
    }
}
