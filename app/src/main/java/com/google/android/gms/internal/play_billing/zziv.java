package com.google.android.gms.internal.play_billing;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zziv extends zzex implements zzgd {
    private static final zziv zzb;
    private int zzd;
    private int zze = 0;
    private Object zzf;
    private zzio zzg;

    static {
        zziv zzivVar = new zziv();
        zzb = zzivVar;
        zzex.zzp(zziv.class, zzivVar);
    }

    private zziv() {
    }

    static /* synthetic */ void zzA(zziv zzivVar, zzic zzicVar) {
        zzivVar.zzf = zzicVar;
        zzivVar.zze = 3;
    }

    public static zziu zzv() {
        return (zziu) zzb.zzg();
    }

    static /* synthetic */ void zzx(zziv zzivVar, zziz zzizVar) {
        zzivVar.zzf = zzizVar;
        zzivVar.zze = 4;
    }

    static /* synthetic */ void zzy(zziv zzivVar, zzio zzioVar) {
        zzivVar.zzg = zzioVar;
        zzivVar.zzd |= 1;
    }

    static /* synthetic */ void zzz(zziv zzivVar, zzhy zzhyVar) {
        zzivVar.zzf = zzhyVar;
        zzivVar.zze = 2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzex
    protected final Object zzu(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzm(zzb, "\u0001\u0005\u0001\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ဉ\u0000\u0002<\u0000\u0003<\u0000\u0004<\u0000\u0005<\u0000", new Object[]{"zzf", "zze", "zzd", "zzg", zzhy.class, zzic.class, zziz.class, zzil.class});
        }
        if (i2 == 3) {
            return new zziv();
        }
        zzit zzitVar = null;
        if (i2 == 4) {
            return new zziu(zzitVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
