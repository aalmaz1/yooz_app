package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class User_info_config extends Serializable {
    public int birth_day;
    public int birth_month;
    public int birth_year;
    public int gender;
    public int height_cm;
    public String user_name;
    public int weight_kg;

    @Override // cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        messagePacker.packLong(this.gender);
        messagePacker.packLong(this.height_cm);
        messagePacker.packLong(this.weight_kg);
        messagePacker.packLong(this.birth_year);
        messagePacker.packLong(this.birth_month);
        messagePacker.packLong(this.birth_day);
        if (this.user_name == null) {
            this.user_name = "";
        }
        messagePacker.packString(this.user_name);
        return true;
    }

    @Override // cn.baos.message.Serializable
    public User_info_config load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        this.gender = (int) messageUnpacker.unpackLong();
        this.height_cm = (int) messageUnpacker.unpackLong();
        this.weight_kg = (int) messageUnpacker.unpackLong();
        this.birth_year = (int) messageUnpacker.unpackLong();
        this.birth_month = (int) messageUnpacker.unpackLong();
        this.birth_day = (int) messageUnpacker.unpackLong();
        this.user_name = messageUnpacker.unpackString();
        return this;
    }

    public User_info_config() {
        this.catagory = CatagoryEnum.USER_INFO_CONFIG;
    }
}
