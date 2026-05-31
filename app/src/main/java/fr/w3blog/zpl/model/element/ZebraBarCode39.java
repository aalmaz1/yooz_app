package fr.w3blog.zpl.model.element;

import fr.w3blog.zpl.model.PrinterOptions;
import fr.w3blog.zpl.utils.ZplUtils;

/* JADX INFO: loaded from: classes2.dex */
public class ZebraBarCode39 extends ZebraBarCode {
    private boolean checkDigit43;

    public ZebraBarCode39(int i, int i2, String str, int i3) {
        super(i, i2, str, i3);
        this.checkDigit43 = false;
    }

    public ZebraBarCode39(int i, int i2, String str, int i3, int i4, int i5) {
        super(i, i2, str, i3, i4, i5);
        this.checkDigit43 = false;
    }

    public ZebraBarCode39(int i, int i2, String str, int i3, int i4, int i5, boolean z) {
        super(i, i2, str, i3, i4, i5);
        this.checkDigit43 = false;
        setCheckDigit43(z);
    }

    public ZebraBarCode39(int i, int i2, String str, int i3, boolean z, boolean z2) {
        super(i, i2, str, i3, z, z2);
        this.checkDigit43 = false;
    }

    @Override // fr.w3blog.zpl.model.ZebraElement
    public String getZplCode(PrinterOptions printerOptions) {
        StringBuilder startZplCodeBuilder = getStartZplCodeBuilder();
        startZplCodeBuilder.append((CharSequence) ZplUtils.zplCommandSautLigne("B3", this.zebraRotation.getLetter(), Boolean.valueOf(this.checkDigit43), this.barCodeHeigth, Boolean.valueOf(this.showTextInterpretation), Boolean.valueOf(this.showTextInterpretationAbove)));
        startZplCodeBuilder.append("^FD");
        startZplCodeBuilder.append(this.text);
        startZplCodeBuilder.append((CharSequence) ZplUtils.zplCommandSautLigne("FS"));
        return startZplCodeBuilder.toString();
    }

    public boolean isCheckDigit43() {
        return this.checkDigit43;
    }

    public ZebraBarCode39 setCheckDigit43(boolean z) {
        this.checkDigit43 = z;
        return this;
    }
}
