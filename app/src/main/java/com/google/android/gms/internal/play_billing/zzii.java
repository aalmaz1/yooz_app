package com.google.android.gms.internal.play_billing;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzii extends zzex implements zzgd {
    private static final zzii zzb;
    private int zzd;
    private int zze;
    private int zzg;
    private String zzf = "";
    private String zzh = "";

    static {
        zzii zziiVar = new zzii();
        zzb = zziiVar;
        zzex.zzp(zzii.class, zziiVar);
    }

    private zzii() {
    }

    static /* synthetic */ void zzA(zzii zziiVar, int i) {
        zziiVar.zzg = i - 1;
        zziiVar.zzd |= 4;
    }

    public static zzie zzv() {
        return (zzie) zzb.zzg();
    }

    static /* synthetic */ void zzx(zzii zziiVar, int i) {
        zziiVar.zzd |= 1;
        zziiVar.zze = i;
    }

    static /* synthetic */ void zzy(zzii zziiVar, String str) {
        str.getClass();
        zziiVar.zzd |= 2;
        zziiVar.zzf = str;
    }

    static /* synthetic */ void zzz(zzii zziiVar, String str) {
        zziiVar.zzd |= 8;
        zziiVar.zzh = str;
    }

    @Override // com.google.android.gms.internal.play_billing.zzex
    protected final Object zzu(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzm(zzb, "\u0001\u0004\u0000\u0001\u0001\u0005\u0004\u0000\u0000\u0000\u0001င\u0000\u0002ဈ\u0001\u0004᠌\u0002\u0005ဈ\u0003", new Object[]{"zzd", "zze", "zzf", "zzg", zzig.zza, "zzh"});
        }
        if (i2 == 3) {
            return new zzii();
        }
        zzid zzidVar = null;
        if (i2 == 4) {
            return new zzie(zzidVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
