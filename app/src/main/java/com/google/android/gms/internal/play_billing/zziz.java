package com.google.android.gms.internal.play_billing;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zziz extends zzex implements zzgd {
    private static final zziz zzb;
    private int zzd;
    private int zze;

    static {
        zziz zzizVar = new zziz();
        zzb = zzizVar;
        zzex.zzp(zziz.class, zzizVar);
    }

    private zziz() {
    }

    public static zziz zzw() {
        return zzb;
    }

    @Override // com.google.android.gms.internal.play_billing.zzex
    protected final Object zzu(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzm(zzb, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001᠌\u0000", new Object[]{"zzd", "zze", zziy.zza});
        }
        if (i2 == 3) {
            return new zziz();
        }
        zziw zziwVar = null;
        if (i2 == 4) {
            return new zzix(zziwVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
