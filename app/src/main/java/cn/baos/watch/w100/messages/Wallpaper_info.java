package cn.baos.watch.w100.messages;

import cn.baos.message.CatagoryEnum;
import java.io.IOException;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;

/* JADX INFO: loaded from: classes.dex */
public class Wallpaper_info extends MessageBase {
    public Control_color bgColor;
    public int bg_img_left;
    public int bg_img_top;
    public Control_info[] controls;

    public static class Control_color {
        public int alpha;
        public int blue;
        public int green;
        public int red;

        public boolean put(MessagePacker messagePacker) throws IOException {
            messagePacker.packLong(this.alpha);
            messagePacker.packLong(this.red);
            messagePacker.packLong(this.green);
            messagePacker.packLong(this.blue);
            return true;
        }

        public Control_color load(MessageUnpacker messageUnpacker) throws IOException {
            this.alpha = (int) messageUnpacker.unpackLong();
            this.red = (int) messageUnpacker.unpackLong();
            this.green = (int) messageUnpacker.unpackLong();
            this.blue = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    public static class Control_info {
        public int bottom;
        public int id;
        public int left;
        public int right;
        public Control_color text_color;
        public int top;
        public int visible;

        public boolean put(MessagePacker messagePacker) throws IOException {
            messagePacker.packLong(this.id);
            messagePacker.packLong(this.visible);
            this.text_color.put(messagePacker);
            messagePacker.packLong(this.left);
            messagePacker.packLong(this.right);
            messagePacker.packLong(this.top);
            messagePacker.packLong(this.bottom);
            return true;
        }

        public Control_info load(MessageUnpacker messageUnpacker) throws IOException {
            this.id = (int) messageUnpacker.unpackLong();
            this.visible = (int) messageUnpacker.unpackLong();
            Control_color control_color = new Control_color();
            this.text_color = control_color;
            control_color.load(messageUnpacker);
            this.left = (int) messageUnpacker.unpackLong();
            this.right = (int) messageUnpacker.unpackLong();
            this.top = (int) messageUnpacker.unpackLong();
            this.bottom = (int) messageUnpacker.unpackLong();
            return this;
        }
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public boolean put(MessagePacker messagePacker) throws IOException {
        super.put(messagePacker);
        this.bgColor.put(messagePacker);
        messagePacker.packLong(this.bg_img_left);
        messagePacker.packLong(this.bg_img_top);
        if (this.controls != null) {
            messagePacker.packLong(r0.length);
            Control_info[] control_infoArr = this.controls;
            if (control_infoArr.length <= 0) {
                return true;
            }
            for (Control_info control_info : control_infoArr) {
                control_info.put(messagePacker);
            }
            return true;
        }
        messagePacker.packLong(0L);
        return true;
    }

    @Override // cn.baos.watch.w100.messages.MessageBase, cn.baos.message.Serializable
    public Wallpaper_info load(MessageUnpacker messageUnpacker) throws IOException {
        super.load(messageUnpacker);
        Control_color control_color = new Control_color();
        this.bgColor = control_color;
        control_color.load(messageUnpacker);
        this.bg_img_left = (int) messageUnpacker.unpackLong();
        this.bg_img_top = (int) messageUnpacker.unpackLong();
        int iUnpackLong = (int) messageUnpacker.unpackLong();
        if (iUnpackLong > 0) {
            this.controls = new Control_info[iUnpackLong];
            for (int i = 0; i < iUnpackLong; i++) {
                this.controls[i] = new Control_info();
                this.controls[i].load(messageUnpacker);
            }
        }
        return this;
    }

    public Wallpaper_info() {
        this.catagory = CatagoryEnum.WALLPAPER_INFO;
    }
}
