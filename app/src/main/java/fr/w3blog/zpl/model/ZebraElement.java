package fr.w3blog.zpl.model;

import androidx.webkit.Profile;
import fr.w3blog.zpl.utils.ZplUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ZebraElement {
    protected boolean defaultDrawGraphic = true;
    protected Integer positionX;
    protected Integer positionY;

    public String getZplCode(PrinterOptions printerOptions) {
        return "";
    }

    public int getPositionX() {
        return this.positionX.intValue();
    }

    public ZebraElement setPositionX(int i) {
        this.positionX = Integer.valueOf(i);
        return this;
    }

    public int getPositionY() {
        return this.positionY.intValue();
    }

    public ZebraElement setPositionY(int i) {
        this.positionY = Integer.valueOf(i);
        return this;
    }

    protected String getZplCodePosition() {
        Integer num;
        StringBuffer stringBuffer = new StringBuffer("");
        Integer num2 = this.positionX;
        if (num2 != null && (num = this.positionY) != null) {
            stringBuffer.append((CharSequence) ZplUtils.zplCommand("FT", num2, num));
        }
        return stringBuffer.toString();
    }

    public void drawPreviewGraphic(PrinterOptions printerOptions, Graphics2D graphics2D) {
        if (this.defaultDrawGraphic) {
            int iRound = this.positionX != null ? Math.round((r0.intValue() / printerOptions.getZebraPPP().getDotByMm()) * 10.0f) : 0;
            int iRound2 = this.positionY != null ? Math.round((r0.intValue() / printerOptions.getZebraPPP().getDotByMm()) * 10.0f) : 0;
            graphics2D.setColor(Color.BLACK);
            graphics2D.drawRect(iRound, iRound2, 100, 20);
            drawTopString(graphics2D, new Font("Arial", 1, 11), Profile.DEFAULT_PROFILE_NAME, iRound, iRound2);
        }
    }

    protected void drawTopString(Graphics2D graphics2D, Font font, String str, int i, int i2) {
        graphics2D.setFont(font);
        graphics2D.drawString(str, i, i2 + ((int) graphics2D.getFontMetrics(font).getStringBounds(str, graphics2D).getHeight()));
    }
}
