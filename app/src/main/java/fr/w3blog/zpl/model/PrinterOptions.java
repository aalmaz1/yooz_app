package fr.w3blog.zpl.model;

import fr.w3blog.zpl.constant.ZebraFont;
import fr.w3blog.zpl.constant.ZebraPPP;

/* JADX INFO: loaded from: classes2.dex */
public class PrinterOptions {
    private Integer defaultFontSize;
    private ZebraFont defaultZebraFont;
    private ZebraPPP zebraPPP;

    public PrinterOptions() {
        this.zebraPPP = ZebraPPP.DPI_300;
        this.defaultZebraFont = null;
        this.defaultFontSize = null;
    }

    public PrinterOptions(ZebraPPP zebraPPP) {
        ZebraPPP zebraPPP2 = ZebraPPP.DPI_300;
        this.defaultZebraFont = null;
        this.defaultFontSize = null;
        this.zebraPPP = zebraPPP;
    }

    public ZebraPPP getZebraPPP() {
        return this.zebraPPP;
    }

    public PrinterOptions setZebraPPP(ZebraPPP zebraPPP) {
        this.zebraPPP = zebraPPP;
        return this;
    }

    public ZebraFont getDefaultZebraFont() {
        return this.defaultZebraFont;
    }

    public Integer getDefaultFontSize() {
        return this.defaultFontSize;
    }

    public PrinterOptions setDefaultZebraFont(ZebraFont zebraFont) {
        this.defaultZebraFont = zebraFont;
        return this;
    }

    public PrinterOptions setDefaultFontSize(Integer num) {
        this.defaultFontSize = num;
        return this;
    }
}
