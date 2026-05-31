package com.google.android.gms.internal.play_billing;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public class zzfi {
    private static final zzej zzb = zzej.zza;
    protected volatile zzgc zza;
    private volatile zzdw zzc;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzfi)) {
            return false;
        }
        zzfi zzfiVar = (zzfi) obj;
        zzgc zzgcVar = this.zza;
        zzgc zzgcVar2 = zzfiVar.zza;
        if (zzgcVar == null && zzgcVar2 == null) {
            return zzb().equals(zzfiVar.zzb());
        }
        if (zzgcVar != null && zzgcVar2 != null) {
            return zzgcVar.equals(zzgcVar2);
        }
        if (zzgcVar != null) {
            zzfiVar.zzc(zzgcVar.zzf());
            return zzgcVar.equals(zzfiVar.zza);
        }
        zzc(zzgcVar2.zzf());
        return this.zza.equals(zzgcVar2);
    }

    public int hashCode() {
        return 1;
    }

    public final int zza() {
        if (this.zzc != null) {
            return ((zzdt) this.zzc).zza.length;
        }
        if (this.zza != null) {
            return this.zza.zze();
        }
        return 0;
    }

    public final zzdw zzb() {
        if (this.zzc != null) {
            return this.zzc;
        }
        synchronized (this) {
            if (this.zzc != null) {
                return this.zzc;
            }
            if (this.zza == null) {
                this.zzc = zzdw.zzb;
            } else {
                this.zzc = this.zza.zzb();
            }
            return this.zzc;
        }
    }

    protected final void zzc(zzgc zzgcVar) {
        if (this.zza != null) {
            return;
        }
        synchronized (this) {
            if (this.zza != null) {
                return;
            }
            try {
                this.zza = zzgcVar;
                this.zzc = zzdw.zzb;
            } catch (zzff unused) {
                this.zza = zzgcVar;
                this.zzc = zzdw.zzb;
            }
        }
    }
}
