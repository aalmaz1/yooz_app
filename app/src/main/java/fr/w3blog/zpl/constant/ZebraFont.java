package fr.w3blog.zpl.constant;

import androidx.exifinterface.media.ExifInterface;

/* JADX INFO: loaded from: classes2.dex */
public enum ZebraFont {
    ZEBRA_ZERO("0"),
    ZEBRA_A(ExifInterface.GPS_MEASUREMENT_IN_PROGRESS),
    ZEBRA_B("B"),
    ZEBRA_C("C"),
    ZEBRA_D("D"),
    ZEBRA_F("F"),
    ZEBRA_G("G");

    String letter;

    public static String findBestEquivalentFontForPreview(ZebraFont zebraFont) {
        return "Arial";
    }

    ZebraFont(String str) {
        this.letter = str;
    }

    public String getLetter() {
        return this.letter;
    }
}
