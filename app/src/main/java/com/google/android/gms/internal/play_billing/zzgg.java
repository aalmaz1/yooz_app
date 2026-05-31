package com.google.android.gms.internal.play_billing;

import java.io.IOException;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzgg implements zzgm {
    private final zzgc zza;
    private final zzhd zzb;
    private final boolean zzc;
    private final zzek zzd;

    private zzgg(zzhd zzhdVar, zzek zzekVar, zzgc zzgcVar) {
        this.zzb = zzhdVar;
        this.zzc = zzekVar.zzc(zzgcVar);
        this.zzd = zzekVar;
        this.zza = zzgcVar;
    }

    static zzgg zzc(zzhd zzhdVar, zzek zzekVar, zzgc zzgcVar) {
        return new zzgg(zzhdVar, zzekVar, zzgcVar);
    }

    @Override // com.google.android.gms.internal.play_billing.zzgm
    public final int zza(Object obj) {
        zzhd zzhdVar = this.zzb;
        int iZzb = zzhdVar.zzb(zzhdVar.zzd(obj));
        if (!this.zzc) {
            return iZzb;
        }
        this.zzd.zza(obj);
        throw null;
    }

    @Override // com.google.android.gms.internal.play_billing.zzgm
    public final int zzb(Object obj) {
        int iHashCode = this.zzb.zzd(obj).hashCode();
        if (!this.zzc) {
            return iHashCode;
        }
        this.zzd.zza(obj);
        throw null;
    }

    @Override // com.google.android.gms.internal.play_billing.zzgm
    public final Object zze() {
        zzgc zzgcVar = this.zza;
        return zzgcVar instanceof zzex ? ((zzex) zzgcVar).zzi() : zzgcVar.zzk().zze();
    }

    @Override // com.google.android.gms.internal.play_billing.zzgm
    public final void zzf(Object obj) {
        this.zzb.zzg(obj);
        this.zzd.zzb(obj);
    }

    @Override // com.google.android.gms.internal.play_billing.zzgm
    public final void zzg(Object obj, Object obj2) {
        zzgo.zzp(this.zzb, obj, obj2);
        if (this.zzc) {
            this.zzd.zza(obj2);
            throw null;
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzgm
    public final void zzh(Object obj, byte[] bArr, int i, int i2, zzdj zzdjVar) throws IOException {
        zzex zzexVar = (zzex) obj;
        if (zzexVar.zzc == zzhe.zzc()) {
            zzexVar.zzc = zzhe.zzf();
        }
        throw null;
    }

    @Override // com.google.android.gms.internal.play_billing.zzgm
    public final void zzi(Object obj, zzhv zzhvVar) throws IOException {
        this.zzd.zza(obj);
        throw null;
    }

    @Override // com.google.android.gms.internal.play_billing.zzgm
    public final boolean zzj(Object obj, Object obj2) {
        zzhd zzhdVar = this.zzb;
        if (!zzhdVar.zzd(obj).equals(zzhdVar.zzd(obj2))) {
            return false;
        }
        if (!this.zzc) {
            return true;
        }
        this.zzd.zza(obj);
        this.zzd.zza(obj2);
        throw null;
    }

    @Override // com.google.android.gms.internal.play_billing.zzgm
    public final boolean zzk(Object obj) {
        this.zzd.zza(obj);
        throw null;
    }
}
