package com.google.android.gms.internal.play_billing;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public class zzba {
    private final String zza;
    private final Class zzb;
    private final boolean zzc;

    protected zzba(String str, Class cls, boolean z) {
        this(str, cls, z, true);
    }

    public static zzba zza(String str, Class cls) {
        return new zzba(str, cls, false, false);
    }

    public final String toString() {
        Class cls = this.zzb;
        return getClass().getName() + "/" + this.zza + "[" + cls.getName() + "]";
    }

    public final boolean zzb() {
        return this.zzc;
    }

    private zzba(String str, Class cls, boolean z, boolean z2) {
        zzda.zzb(str);
        this.zza = str;
        this.zzb = cls;
        this.zzc = z;
        System.identityHashCode(this);
        for (int i = 0; i < 5; i++) {
        }
    }
}
