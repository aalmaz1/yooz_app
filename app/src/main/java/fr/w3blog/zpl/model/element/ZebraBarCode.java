package fr.w3blog.zpl.model.element;

import fr.w3blog.zpl.constant.ZebraRotation;
import fr.w3blog.zpl.model.PrinterOptions;
import fr.w3blog.zpl.model.ZebraElement;
import fr.w3blog.zpl.utils.ZplUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ZebraBarCode extends ZebraElement {
    Integer barCodeHeigth;
    Integer moduleWidth;
    boolean showTextInterpretation;
    boolean showTextInterpretationAbove;
    String text;
    Integer wideBarRatio;
    ZebraRotation zebraRotation;

    public ZebraBarCode(int i, int i2, String str, int i3) {
        this.zebraRotation = ZebraRotation.NORMAL;
        this.showTextInterpretationAbove = false;
        this.positionX = Integer.valueOf(i);
        this.positionY = Integer.valueOf(i2);
        this.barCodeHeigth = Integer.valueOf(i3);
        this.text = str;
    }

    public ZebraBarCode(int i, int i2, String str, int i3, int i4, int i5) {
        this.zebraRotation = ZebraRotation.NORMAL;
        this.showTextInterpretationAbove = false;
        this.positionX = Integer.valueOf(i);
        this.positionY = Integer.valueOf(i2);
        this.barCodeHeigth = Integer.valueOf(i3);
        this.text = str;
        this.moduleWidth = Integer.valueOf(i4);
        this.wideBarRatio = Integer.valueOf(i5);
    }

    public ZebraBarCode(int i, int i2, String str, int i3, boolean z, boolean z2) {
        this.zebraRotation = ZebraRotation.NORMAL;
        this.showTextInterpretationAbove = false;
        this.positionX = Integer.valueOf(i);
        this.positionY = Integer.valueOf(i2);
        this.barCodeHeigth = Integer.valueOf(i3);
        this.text = str;
        this.showTextInterpretation = z;
        this.showTextInterpretationAbove = z2;
    }

    public StringBuilder getStartZplCodeBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append(getZplCodePosition());
        sb.append("\n");
        Integer num = this.moduleWidth;
        if (num != null) {
            sb.append((CharSequence) ZplUtils.zplCommandSautLigne("BY", num, this.wideBarRatio, this.barCodeHeigth));
        }
        return sb;
    }

    @Override // fr.w3blog.zpl.model.ZebraElement
    public void drawPreviewGraphic(PrinterOptions printerOptions, Graphics2D graphics2D) {
        int iIntValue = this.positionX != null ? ZplUtils.convertPointInPixel(this.positionX.intValue()).intValue() : 0;
        int iIntValue2 = this.positionY != null ? ZplUtils.convertPointInPixel(this.positionY.intValue()).intValue() : 0;
        graphics2D.setColor(Color.BLACK);
        Font font = new Font("Arial", 1, this.barCodeHeigth.intValue() / 2);
        graphics2D.drawRect(iIntValue, iIntValue2, ZplUtils.convertPointInPixel(Math.round(this.moduleWidth.intValue() * this.wideBarRatio.intValue() * 9 * this.text.length())).intValue(), ZplUtils.convertPointInPixel(this.barCodeHeigth.intValue()).intValue());
        drawTopString(graphics2D, font, this.text, iIntValue, iIntValue2);
    }

    public Integer getBarCodeWidth() {
        return this.moduleWidth;
    }

    public Integer getBarCodeHeigth() {
        return this.barCodeHeigth;
    }

    public Integer getWideBarRatio() {
        return this.wideBarRatio;
    }

    public ZebraRotation getZebraRotation() {
        return this.zebraRotation;
    }

    public boolean isShowTextInterpretation() {
        return this.showTextInterpretation;
    }

    public boolean isShowTextInterpretationAbove() {
        return this.showTextInterpretationAbove;
    }

    public String getText() {
        return this.text;
    }

    public ZebraBarCode setBarCodeWidth(Integer num) {
        this.moduleWidth = num;
        return this;
    }

    public ZebraBarCode setBarCodeHeigth(Integer num) {
        this.barCodeHeigth = num;
        return this;
    }

    public ZebraBarCode setWideBarRatio(Integer num) {
        this.wideBarRatio = num;
        return this;
    }

    public ZebraBarCode setZebraRotation(ZebraRotation zebraRotation) {
        this.zebraRotation = zebraRotation;
        return this;
    }

    public ZebraBarCode setShowTextInterpretation(boolean z) {
        this.showTextInterpretation = z;
        return this;
    }

    public ZebraBarCode setShowTextInterpretationAbove(boolean z) {
        this.showTextInterpretationAbove = z;
        return this;
    }

    public ZebraBarCode setText(String str) {
        this.text = str;
        return this;
    }
}
