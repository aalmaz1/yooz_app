package fr.w3blog.zpl.model;

import fr.w3blog.zpl.constant.ZebraFont;
import fr.w3blog.zpl.constant.ZebraPrintMode;
import fr.w3blog.zpl.utils.ZplUtils;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class ZebraLabel {
    private Integer heightDots;
    private PrinterOptions printerOptions;
    private Integer widthDots;
    private List<ZebraElement> zebraElements;
    private ZebraPrintMode zebraPrintMode;

    public ZebraLabel() {
        this.zebraPrintMode = ZebraPrintMode.TEAR_OFF;
        this.printerOptions = new PrinterOptions();
        this.zebraElements = new ArrayList();
        this.zebraElements = new ArrayList();
    }

    public ZebraLabel(PrinterOptions printerOptions) {
        this.zebraPrintMode = ZebraPrintMode.TEAR_OFF;
        this.printerOptions = new PrinterOptions();
        this.zebraElements = new ArrayList();
        this.printerOptions = printerOptions;
    }

    public ZebraLabel(int i, int i2) {
        this.zebraPrintMode = ZebraPrintMode.TEAR_OFF;
        this.printerOptions = new PrinterOptions();
        this.zebraElements = new ArrayList();
        this.widthDots = Integer.valueOf(i);
        this.heightDots = Integer.valueOf(i2);
    }

    public ZebraLabel(int i, int i2, PrinterOptions printerOptions) {
        this.zebraPrintMode = ZebraPrintMode.TEAR_OFF;
        this.printerOptions = new PrinterOptions();
        this.zebraElements = new ArrayList();
        this.widthDots = Integer.valueOf(i);
        this.heightDots = Integer.valueOf(i2);
        this.printerOptions = printerOptions;
    }

    public ZebraLabel addElement(ZebraElement zebraElement) {
        this.zebraElements.add(zebraElement);
        return this;
    }

    public ZebraLabel setDefaultZebraFont(ZebraFont zebraFont) {
        this.printerOptions.setDefaultZebraFont(zebraFont);
        return this;
    }

    public ZebraLabel setDefaultFontSize(Integer num) {
        this.printerOptions.setDefaultFontSize(num);
        return this;
    }

    public Integer getWidthDots() {
        return this.widthDots;
    }

    public ZebraLabel setWidthDots(Integer num) {
        this.widthDots = num;
        return this;
    }

    public Integer getHeightDots() {
        return this.heightDots;
    }

    public ZebraLabel setHeightDots(Integer num) {
        this.heightDots = num;
        return this;
    }

    public PrinterOptions getPrinterOptions() {
        return this.printerOptions;
    }

    public void setPrinterOptions(PrinterOptions printerOptions) {
        this.printerOptions = printerOptions;
    }

    public ZebraPrintMode getZebraPrintMode() {
        return this.zebraPrintMode;
    }

    public ZebraLabel setZebraPrintMode(ZebraPrintMode zebraPrintMode) {
        this.zebraPrintMode = zebraPrintMode;
        return this;
    }

    public List<ZebraElement> getZebraElements() {
        return this.zebraElements;
    }

    public void setZebraElements(List<ZebraElement> list) {
        this.zebraElements = list;
    }

    public String getZplCode() {
        StringBuilder sb = new StringBuilder();
        sb.append((CharSequence) ZplUtils.zplCommandSautLigne("XA"));
        sb.append(this.zebraPrintMode.getZplCode());
        Integer num = this.widthDots;
        if (num != null) {
            sb.append((CharSequence) ZplUtils.zplCommandSautLigne("PW", num));
        }
        Integer num2 = this.heightDots;
        if (num2 != null) {
            sb.append((CharSequence) ZplUtils.zplCommandSautLigne("LL", num2));
        }
        if (this.printerOptions.getDefaultZebraFont() != null && this.printerOptions.getDefaultFontSize() != null) {
            sb.append((CharSequence) ZplUtils.zplCommandSautLigne("CF", ZplUtils.extractDotsFromFont(this.printerOptions.getDefaultZebraFont(), this.printerOptions.getDefaultFontSize().intValue(), this.printerOptions.getZebraPPP())));
        }
        Iterator<ZebraElement> it = this.zebraElements.iterator();
        while (it.hasNext()) {
            sb.append(it.next().getZplCode(this.printerOptions));
        }
        sb.append((CharSequence) ZplUtils.zplCommandSautLigne("XZ"));
        return sb.toString();
    }

    public BufferedImage getImagePreview() {
        Integer num = this.widthDots;
        if (num != null && this.heightDots != null) {
            int iIntValue = ZplUtils.convertPointInPixel(num.intValue()).intValue();
            int iIntValue2 = ZplUtils.convertPointInPixel(this.heightDots.intValue()).intValue();
            BufferedImage bufferedImage = new BufferedImage(iIntValue, iIntValue2, 2);
            Graphics2D graphics2DCreateGraphics = bufferedImage.createGraphics();
            graphics2DCreateGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2DCreateGraphics.setComposite(AlphaComposite.Src);
            graphics2DCreateGraphics.fillRect(0, 0, iIntValue, iIntValue2);
            graphics2DCreateGraphics.setColor(Color.BLACK);
            graphics2DCreateGraphics.setFont(new Font("Arial", 1, 11));
            Iterator<ZebraElement> it = this.zebraElements.iterator();
            while (it.hasNext()) {
                it.next().drawPreviewGraphic(this.printerOptions, graphics2DCreateGraphics);
            }
            return bufferedImage;
        }
        throw new UnsupportedOperationException("Graphics Preview is only available ont label sized");
    }
}
