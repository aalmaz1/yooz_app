package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Rpc_param_alarm extends Serializable {
    public int after_trigger_count;
    public int after_trigger_min;
    public String alarm_action;
    public int alarm_id;
    public String alarm_name;
    public int alarm_status;
    public int alarm_type;
    public int alarm_verison;
    public int before_trigger_min;
    public int circle_definition;
    public long circle_mask;
    public int end_definition;
    public int next_delay_def;
    public int next_trigger_time;
    public int start_time;
    public int time_zone;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.circle_mask);
        messagePacker.packLong(this.start_time);
        messagePacker.packLong(this.before_trigger_min);
        messagePacker.packLong(this.after_trigger_count);
        messagePacker.packLong(this.after_trigger_min);
        messagePacker.packLong(this.end_definition);
        messagePacker.packLong(this.next_delay_def);
        messagePacker.packLong(this.next_trigger_time);
        messagePacker.packLong(this.time_zone);
        messagePacker.packLong(this.alarm_id);
        messagePacker.packLong(this.alarm_verison);
        messagePacker.packLong(this.alarm_type);
        messagePacker.packLong(this.alarm_status);
        messagePacker.packLong(this.circle_definition);
        if (this.alarm_name == null) {
            this.alarm_name = "";
        }
        messagePacker.packString(this.alarm_name);
        if (this.alarm_action == null) {
            this.alarm_action = "";
        }
        messagePacker.packString(this.alarm_action);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public Rpc_param_alarm load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.circle_mask = messageUnpacker.unpackLong();
        this.start_time = (int) messageUnpacker.unpackLong();
        this.before_trigger_min = (int) messageUnpacker.unpackLong();
        this.after_trigger_count = (int) messageUnpacker.unpackLong();
        this.after_trigger_min = (int) messageUnpacker.unpackLong();
        this.end_definition = (int) messageUnpacker.unpackLong();
        this.next_delay_def = (int) messageUnpacker.unpackLong();
        this.next_trigger_time = (int) messageUnpacker.unpackLong();
        this.time_zone = (int) messageUnpacker.unpackLong();
        this.alarm_id = (int) messageUnpacker.unpackLong();
        this.alarm_verison = (int) messageUnpacker.unpackLong();
        this.alarm_type = (int) messageUnpacker.unpackLong();
        this.alarm_status = (int) messageUnpacker.unpackLong();
        this.circle_definition = (int) messageUnpacker.unpackLong();
        this.alarm_name = messageUnpacker.unpackString();
        this.alarm_action = messageUnpacker.unpackString();
        return this;
    }

    public Rpc_param_alarm() {
        this.catagory = CatagoryEnum.RPC_PARAM_ALARM;
    }
}
