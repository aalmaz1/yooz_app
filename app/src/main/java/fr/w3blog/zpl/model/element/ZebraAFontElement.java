package fr.w3blog.zpl.model.element;

import androidx.exifinterface.media.ExifInterface;
import fr.w3blog.zpl.constant.ZebraFont;
import fr.w3blog.zpl.constant.ZebraRotation;
import fr.w3blog.zpl.model.PrinterOptions;
import fr.w3blog.zpl.model.ZebraElement;
import fr.w3blog.zpl.utils.ZplUtils;

/* JADX INFO: loaded from: classes2.dex */
public class ZebraAFontElement extends ZebraElement {
    private int dotHeigth;
    private int dotsWidth;
    private ZebraFont zebraFont;
    private ZebraRotation zebraRotation;

    public ZebraAFontElement(ZebraFont zebraFont) {
        this.zebraRotation = ZebraRotation.NORMAL;
        this.zebraFont = zebraFont;
    }

    public ZebraAFontElement(ZebraFont zebraFont, int i, int i2) {
        this.zebraRotation = ZebraRotation.NORMAL;
        this.zebraFont = zebraFont;
        this.dotHeigth = i;
        this.dotsWidth = i2;
    }

    public ZebraAFontElement(ZebraFont zebraFont, ZebraRotation zebraRotation, int i, int i2) {
        ZebraRotation zebraRotation2 = ZebraRotation.NORMAL;
        this.zebraFont = zebraFont;
        this.zebraRotation = zebraRotation;
        this.dotHeigth = i;
        this.dotsWidth = i2;
    }

    @Override // fr.w3blog.zpl.model.ZebraElement
    public String getZplCode(PrinterOptions printerOptions) {
        return ZplUtils.zplCommandSautLigne(ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, this.zebraFont.getLetter(), this.zebraRotation.getLetter(), Integer.valueOf(this.dotHeigth), Integer.valueOf(this.dotsWidth)).toString();
    }
}
