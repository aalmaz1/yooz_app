package fr.w3blog.zpl.model.element;

import fr.w3blog.zpl.model.PrinterOptions;
import fr.w3blog.zpl.model.ZebraElement;

/* JADX INFO: loaded from: classes2.dex */
public class ZebraNativeZpl extends ZebraElement {
    private String zplCode;

    public ZebraNativeZpl(String str) {
        this.zplCode = str;
        this.defaultDrawGraphic = false;
    }

    @Override // fr.w3blog.zpl.model.ZebraElement
    public String getZplCode(PrinterOptions printerOptions) {
        return this.zplCode;
    }
}
