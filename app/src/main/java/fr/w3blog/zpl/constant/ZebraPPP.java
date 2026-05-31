package fr.w3blog.zpl.constant;

/* JADX INFO: loaded from: classes2.dex */
public enum ZebraPPP {
    DPI_203(8.0f),
    DPI_300(12.0f),
    DPI_600(23.5f);

    private float dotByMm;

    ZebraPPP(float f) {
        this.dotByMm = f;
    }

    public float getDotByMm() {
        return this.dotByMm;
    }
}
