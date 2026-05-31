package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Sport_plans extends Serializable {
    public int enable;
    public Sport_plan[] plans;
    public int reserve1;
    public int reserve2;

    public static class Sport_plan {
        public int day_of_week;
        public int mode;
        public int reach_value;
        public int reserve;
        public int target_type;
        public int target_value;

        public boolean put(MessagePacker messagePacker) throws IOException {
            messagePacker.packLong(this.target_value);
            messagePacker.packLong(this.reach_value);
            messagePacker.packLong(this.mode);
            messagePacker.packLong(this.target_type);
            messagePacker.packLong(this.day_of_week);
            messagePacker.packLong(this.reserve);
            return true;
        }

        public Sport_plan load(MessageUnpacker messageUnpacker) throws IOException {
            this.target_value = (int) messageUnpacker.unpackLong();
            this.reach_value = (int) messageUnpacker.unpackLong();
            this.mode = (int) messageUnpacker.unpackLong();
            this.target_type = (int) messageUnpacker.unpackLong();
            this.day_of_week = (int) messageUnpacker.unpackLong();
            this.reserve = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.plans != null) {
            messagePacker.packLong(r0.length);
            Sport_plan[] sport_planArr = this.plans;
            if (sport_planArr.length > 0) {
                for (Sport_plan sport_plan : sport_planArr) {
                    sport_plan.put(messagePacker);
                }
            }
        } else {
            messagePacker.packLong(0L);
        }
        messagePacker.packLong(this.enable);
        messagePacker.packLong(this.reserve1);
        messagePacker.packLong(this.reserve2);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Sport_plans load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.plans = new Sport_plan[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.plans[i] = new Sport_plan();
                this.plans[i].load(messageUnpacker);
            }
        }
        this.enable = (int) messageUnpacker.unpackLong();
        this.reserve1 = (int) messageUnpacker.unpackLong();
        this.reserve2 = (int) messageUnpacker.unpackLong();
        return this;
    }

    public Sport_plans() {
        this.catagory = CatagoryEnum.SPORT_PLANS;
    }
}
