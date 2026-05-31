package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Rpc_param_sport_remind extends Serializable {
    public int calories;
    public int distance_m;
    public int distance_per_m;
    public int max_heartrate;
    public int max_pace_s;
    public int min_pace_s;
    public int steps;
    public int times_per_s;
    public int times_s;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.distance_m);
        messagePacker.packLong(this.calories);
        messagePacker.packLong(this.times_s);
        messagePacker.packLong(this.steps);
        messagePacker.packLong(this.distance_per_m);
        messagePacker.packLong(this.times_per_s);
        messagePacker.packLong(this.max_pace_s);
        messagePacker.packLong(this.min_pace_s);
        messagePacker.packLong(this.max_heartrate);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Rpc_param_sport_remind load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.distance_m = (int) messageUnpacker.unpackLong();
        this.calories = (int) messageUnpacker.unpackLong();
        this.times_s = (int) messageUnpacker.unpackLong();
        this.steps = (int) messageUnpacker.unpackLong();
        this.distance_per_m = (int) messageUnpacker.unpackLong();
        this.times_per_s = (int) messageUnpacker.unpackLong();
        this.max_pace_s = (int) messageUnpacker.unpackLong();
        this.min_pace_s = (int) messageUnpacker.unpackLong();
        this.max_heartrate = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Rpc_param_sport_remind() {
        this.catagory = CatagoryEnum.RPC_PARAM_SPORT_REMIND;
    }
}
