package com.google.android.gms.internal.play_billing;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzic extends zzex implements zzgd {
    private static final zzic zzb;
    private int zzd;
    private int zze = 0;
    private Object zzf;
    private int zzg;

    static {
        zzic zzicVar = new zzic();
        zzb = zzicVar;
        zzex.zzp(zzic.class, zzicVar);
    }

    private zzic() {
    }

    public static zzib zzv() {
        return (zzib) zzb.zzg();
    }

    static /* synthetic */ void zzx(zzic zzicVar, zzis zzisVar) {
        zzisVar.getClass();
        zzicVar.zzf = zzisVar;
        zzicVar.zze = 2;
    }

    static /* synthetic */ void zzy(zzic zzicVar, int i) {
        zzicVar.zzg = i - 1;
        zzicVar.zzd |= 1;
    }

    @Override // com.google.android.gms.internal.play_billing.zzex
    protected final Object zzu(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzm(zzb, "\u0001\u0002\u0001\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001᠌\u0000\u0002<\u0000", new Object[]{"zzf", "zze", "zzd", "zzg", zzhz.zza, zzis.class});
        }
        if (i2 == 3) {
            return new zzic();
        }
        zzia zziaVar = null;
        if (i2 == 4) {
            return new zzib(zziaVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
