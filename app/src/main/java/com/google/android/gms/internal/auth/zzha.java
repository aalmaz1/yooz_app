package com.google.android.gms.internal.auth;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* JADX INFO: loaded from: classes2.dex */
final class zzha extends zzgy {
    zzha() {
    }

    @Override // com.google.android.gms.internal.auth.zzgy
    final /* synthetic */ Object zza(Object obj) {
        return ((zzeu) obj).zzc;
    }

    @Override // com.google.android.gms.internal.auth.zzgy
    final /* bridge */ /* synthetic */ Object zzb(Object obj, Object obj2) {
        zzgz zzgzVar = (zzgz) obj2;
        return zzgzVar.equals(zzgz.zza()) ? obj : zzgz.zzb((zzgz) obj, zzgzVar);
    }

    @Override // com.google.android.gms.internal.auth.zzgy
    final /* synthetic */ Object zzc() {
        return zzgz.zzc();
    }

    @Override // com.google.android.gms.internal.auth.zzgy
    final /* bridge */ /* synthetic */ void zzd(Object obj, int i, long j) {
        ((zzgz) obj).zzf(i << 3, Long.valueOf(j));
    }

    @Override // com.google.android.gms.internal.auth.zzgy
    final void zze(Object obj) {
        ((zzeu) obj).zzc.zzd();
    }

    @Override // com.google.android.gms.internal.auth.zzgy
    final /* synthetic */ void zzf(Object obj, Object obj2) {
        ((zzeu) obj).zzc = (zzgz) obj2;
    }
}
