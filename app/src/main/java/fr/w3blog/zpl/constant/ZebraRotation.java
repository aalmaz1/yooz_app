package fr.w3blog.zpl.constant;

/* JADX INFO: loaded from: classes2.dex */
public enum ZebraRotation {
    NORMAL("N"),
    ROTATE_90("R"),
    INVERTED("I"),
    READ_FROM_BOTTOM("B");

    String letter;

    ZebraRotation(String str) {
        this.letter = str;
    }

    public String getLetter() {
        return this.letter;
    }
}
