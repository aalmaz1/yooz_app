package com.google.android.gms.internal.play_billing;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzis extends zzex implements zzgd {
    private static final zzis zzb;
    private int zzd;
    private int zze;

    static {
        zzis zzisVar = new zzis();
        zzb = zzisVar;
        zzex.zzp(zzis.class, zzisVar);
    }

    private zzis() {
    }

    public static zziq zzv() {
        return (zziq) zzb.zzg();
    }

    static /* synthetic */ void zzx(zzis zzisVar, int i) {
        zzisVar.zze = i - 1;
        zzisVar.zzd |= 1;
    }

    @Override // com.google.android.gms.internal.play_billing.zzex
    protected final Object zzu(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzm(zzb, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001᠌\u0000", new Object[]{"zzd", "zze", zzir.zza});
        }
        if (i2 == 3) {
            return new zzis();
        }
        zzip zzipVar = null;
        if (i2 == 4) {
            return new zziq(zzipVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
