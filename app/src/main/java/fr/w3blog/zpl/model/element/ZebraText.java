package fr.w3blog.zpl.model.element;

import androidx.exifinterface.media.ExifInterface;
import fr.w3blog.zpl.constant.ZebraFont;
import fr.w3blog.zpl.constant.ZebraRotation;
import fr.w3blog.zpl.model.PrinterOptions;
import fr.w3blog.zpl.model.ZebraElement;
import fr.w3blog.zpl.utils.ZplUtils;
import java.awt.Font;
import java.awt.Graphics2D;

/* JADX INFO: loaded from: classes2.dex */
public class ZebraText extends ZebraElement {
    Integer fontSize;
    String text;
    ZebraFont zebraFont;
    ZebraRotation zebraRotation;

    public ZebraText(String str) {
        this.zebraFont = null;
        this.fontSize = null;
        this.zebraRotation = ZebraRotation.NORMAL;
        this.text = str;
    }

    public ZebraText(String str, int i) {
        this.zebraFont = null;
        this.fontSize = null;
        this.zebraRotation = ZebraRotation.NORMAL;
        this.fontSize = Integer.valueOf(i);
        this.text = str;
    }

    public ZebraText(String str, ZebraFont zebraFont, int i) {
        this.zebraFont = null;
        this.fontSize = null;
        this.zebraRotation = ZebraRotation.NORMAL;
        this.zebraFont = zebraFont;
        this.fontSize = Integer.valueOf(i);
        this.text = str;
    }

    public ZebraText(String str, ZebraFont zebraFont, int i, ZebraRotation zebraRotation) {
        this.zebraFont = null;
        this.fontSize = null;
        ZebraRotation zebraRotation2 = ZebraRotation.NORMAL;
        this.zebraFont = zebraFont;
        this.zebraRotation = zebraRotation;
        this.fontSize = Integer.valueOf(i);
        this.text = str;
    }

    public ZebraText(int i, int i2, String str) {
        this.zebraFont = null;
        this.fontSize = null;
        this.zebraRotation = ZebraRotation.NORMAL;
        this.text = str;
        this.positionX = Integer.valueOf(i);
        this.positionY = Integer.valueOf(i2);
    }

    public ZebraText(int i, int i2, String str, int i3) {
        this.zebraFont = null;
        this.fontSize = null;
        this.zebraRotation = ZebraRotation.NORMAL;
        this.fontSize = Integer.valueOf(i3);
        this.text = str;
        this.positionX = Integer.valueOf(i);
        this.positionY = Integer.valueOf(i2);
    }

    public ZebraText(int i, int i2, String str, ZebraFont zebraFont, int i3, ZebraRotation zebraRotation) {
        this.zebraFont = null;
        this.fontSize = null;
        this.zebraRotation = ZebraRotation.NORMAL;
        this.zebraFont = zebraFont;
        this.fontSize = Integer.valueOf(i3);
        this.zebraRotation = zebraRotation;
        this.text = str;
        this.positionX = Integer.valueOf(i);
        this.positionY = Integer.valueOf(i2);
    }

    public ZebraText(int i, int i2, String str, ZebraFont zebraFont, int i3) {
        this.zebraFont = null;
        this.fontSize = null;
        this.zebraRotation = ZebraRotation.NORMAL;
        this.zebraFont = zebraFont;
        this.fontSize = Integer.valueOf(i3);
        this.text = str;
        this.positionX = Integer.valueOf(i);
        this.positionY = Integer.valueOf(i2);
    }

    @Override // fr.w3blog.zpl.model.ZebraElement
    public String getZplCode(PrinterOptions printerOptions) {
        ZebraFont zebraFont;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getZplCodePosition());
        Integer num = this.fontSize;
        if (num != null && (zebraFont = this.zebraFont) != null) {
            Integer[] numArrExtractDotsFromFont = ZplUtils.extractDotsFromFont(zebraFont, num.intValue(), printerOptions.getZebraPPP());
            stringBuffer.append((CharSequence) ZplUtils.zplCommand(ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, this.zebraFont.getLetter(), this.zebraRotation.getLetter(), numArrExtractDotsFromFont[0], numArrExtractDotsFromFont[1]));
        } else if (num != null && printerOptions.getDefaultZebraFont() != null) {
            Integer[] numArrExtractDotsFromFont2 = ZplUtils.extractDotsFromFont(printerOptions.getDefaultZebraFont(), this.fontSize.intValue(), printerOptions.getZebraPPP());
            stringBuffer.append((CharSequence) ZplUtils.zplCommand(ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, printerOptions.getDefaultZebraFont().getLetter(), this.zebraRotation.getLetter(), numArrExtractDotsFromFont2[0], numArrExtractDotsFromFont2[1]));
        }
        stringBuffer.append("^FH\\^FD");
        stringBuffer.append(ZplUtils.convertAccentToZplAsciiHexa(this.text));
        stringBuffer.append((CharSequence) ZplUtils.zplCommandSautLigne("FS"));
        return stringBuffer.toString();
    }

    @Override // fr.w3blog.zpl.model.ZebraElement
    public void drawPreviewGraphic(PrinterOptions printerOptions, Graphics2D graphics2D) {
        Font font;
        Font font2;
        if (this.defaultDrawGraphic) {
            int iIntValue = this.positionX != null ? ZplUtils.convertPointInPixel(this.positionX.intValue()).intValue() : 0;
            int iIntValue2 = this.positionY != null ? ZplUtils.convertPointInPixel(this.positionY.intValue()).intValue() : 0;
            Integer num = this.fontSize;
            if (num != null && this.zebraFont != null) {
                font = new Font(ZebraFont.findBestEquivalentFontForPreview(this.zebraFont), 1, ZplUtils.extractDotsFromFont(printerOptions.getDefaultZebraFont(), this.fontSize.intValue(), printerOptions.getZebraPPP())[0].intValue());
            } else {
                if (num != null && printerOptions.getDefaultZebraFont() != null) {
                    font2 = new Font(ZebraFont.findBestEquivalentFontForPreview(printerOptions.getDefaultZebraFont()), 1, Math.round(ZplUtils.extractDotsFromFont(printerOptions.getDefaultZebraFont(), this.fontSize.intValue(), printerOptions.getZebraPPP())[0].intValue() / 1.33f));
                    drawTopString(graphics2D, font2, this.text, iIntValue, iIntValue2);
                }
                font = new Font(ZebraFont.findBestEquivalentFontForPreview(ZebraFont.ZEBRA_A), 1, ZplUtils.extractDotsFromFont(printerOptions.getDefaultZebraFont(), 15, printerOptions.getZebraPPP())[0].intValue());
            }
            font2 = font;
            drawTopString(graphics2D, font2, this.text, iIntValue, iIntValue2);
        }
    }
}
