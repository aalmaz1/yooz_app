package com.google.android.gms.internal.play_billing;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzil extends zzex implements zzgd {
    private static final zzil zzb;
    private int zzd;
    private String zze = "";

    static {
        zzil zzilVar = new zzil();
        zzb = zzilVar;
        zzex.zzp(zzil.class, zzilVar);
    }

    private zzil() {
    }

    @Override // com.google.android.gms.internal.play_billing.zzex
    protected final Object zzu(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzm(zzb, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဈ\u0000", new Object[]{"zzd", "zze"});
        }
        if (i2 == 3) {
            return new zzil();
        }
        zzij zzijVar = null;
        if (i2 == 4) {
            return new zzik(zzijVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
