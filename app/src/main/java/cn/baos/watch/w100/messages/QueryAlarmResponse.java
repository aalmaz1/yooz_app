package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class QueryAlarmResponse extends MessageBase {
    public AlarmData[] alarm_datas;
    public int current_page;
    public int has_next_page;

    public static class AlarmData {
        public int alarm_id;
        public int circle_extra;
        public int circle_type;
        public String event;
        public int mask_mday;
        public int mask_mweek;
        public int mask_wday;
        public int mask_ymonth;
        public String reminder;
        public int start_time;
        public int status;
        public int time_zone;
        public int trigger_time;
        public int verison;

        public boolean put(MessagePacker messagePacker) throws IOException {
            messagePacker.packLong(this.verison);
            messagePacker.packLong(this.alarm_id);
            messagePacker.packLong(this.circle_type);
            messagePacker.packLong(this.circle_extra);
            messagePacker.packLong(this.mask_wday);
            messagePacker.packLong(this.mask_mday);
            messagePacker.packLong(this.mask_mweek);
            messagePacker.packLong(this.mask_ymonth);
            messagePacker.packLong(this.time_zone);
            messagePacker.packLong(this.start_time);
            messagePacker.packLong(this.trigger_time);
            if (this.reminder == null) {
                this.reminder = "";
            }
            messagePacker.packString(this.reminder);
            if (this.event == null) {
                this.event = "";
            }
            messagePacker.packString(this.event);
            messagePacker.packLong(this.status);
            return true;
        }

        public AlarmData load(MessageUnpacker messageUnpacker) throws IOException {
            this.verison = (int) messageUnpacker.unpackLong();
            this.alarm_id = (int) messageUnpacker.unpackLong();
            this.circle_type = (int) messageUnpacker.unpackLong();
            this.circle_extra = (int) messageUnpacker.unpackLong();
            this.mask_wday = (int) messageUnpacker.unpackLong();
            this.mask_mday = (int) messageUnpacker.unpackLong();
            this.mask_mweek = (int) messageUnpacker.unpackLong();
            this.mask_ymonth = (int) messageUnpacker.unpackLong();
            this.time_zone = (int) messageUnpacker.unpackLong();
            this.start_time = (int) messageUnpacker.unpackLong();
            this.trigger_time = (int) messageUnpacker.unpackLong();
            this.reminder = messageUnpacker.unpackString();
            this.event = messageUnpacker.unpackString();
            this.status = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        if (this.alarm_datas != null) {
            messagePacker.packLong(r0.length);
            AlarmData[] alarmDataArr = this.alarm_datas;
            if (alarmDataArr.length > 0) {
                for (AlarmData alarmData : alarmDataArr) {
                    alarmData.put(messagePacker);
                }
            }
        } else {
            messagePacker.packLong(0L);
        }
        messagePacker.packLong(this.current_page);
        messagePacker.packLong(this.has_next_page);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public QueryAlarmResponse load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.alarm_datas = new AlarmData[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.alarm_datas[i] = new AlarmData();
                this.alarm_datas[i].load(messageUnpacker);
            }
        }
        this.current_page = (int) messageUnpacker.unpackLong();
        this.has_next_page = (int) messageUnpacker.unpackLong();
        return this;
    }

    public QueryAlarmResponse() {
        this.catagory = CatagoryEnum.QUERYALARMRESPONSE;
    }
}
