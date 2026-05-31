package fr.w3blog.zpl.constant;

import androidx.exifinterface.media.ExifInterface;

/* JADX INFO: loaded from: classes2.dex */
public enum ZebraPrintMode {
    TEAR_OFF(ExifInterface.GPS_DIRECTION_TRUE),
    REWIND("R"),
    PEEL_OFF_SELECT("P", true),
    PEEL_OFF_NOSELECT("P", false),
    CUTTER("C");

    String desiredMode;
    String prePeelSelect;

    ZebraPrintMode(String str) {
        this.desiredMode = str;
        this.prePeelSelect = "";
    }

    ZebraPrintMode(String str, boolean z) {
        this.desiredMode = str;
        if (z) {
            this.prePeelSelect = ",Y";
        } else {
            this.prePeelSelect = ",N";
        }
    }

    public String getDesiredMode() {
        return this.desiredMode;
    }

    public String getPrePeelSelect() {
        return this.prePeelSelect;
    }

    public String getZplCode() {
        return "^MM" + this.desiredMode + this.prePeelSelect + "\n";
    }
}
