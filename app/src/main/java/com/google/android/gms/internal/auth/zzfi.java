package com.google.android.gms.internal.auth;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* JADX INFO: loaded from: classes2.dex */
final class zzfi extends zzfk {
    private zzfi() {
        super(null);
    }

    /* synthetic */ zzfi(zzfh zzfhVar) {
        super(null);
    }

    @Override // com.google.android.gms.internal.auth.zzfk
    final void zza(Object obj, long j) {
        ((zzey) zzhi.zzf(obj, j)).zzb();
    }

    @Override // com.google.android.gms.internal.auth.zzfk
    final void zzb(Object obj, Object obj2, long j) {
        zzey zzeyVarZzd = (zzey) zzhi.zzf(obj, j);
        zzey zzeyVar = (zzey) zzhi.zzf(obj2, j);
        int size = zzeyVarZzd.size();
        int size2 = zzeyVar.size();
        if (size > 0 && size2 > 0) {
            if (!zzeyVarZzd.zzc()) {
                zzeyVarZzd = zzeyVarZzd.zzd(size2 + size);
            }
            zzeyVarZzd.addAll(zzeyVar);
        }
        if (size > 0) {
            zzeyVar = zzeyVarZzd;
        }
        zzhi.zzp(obj, j, zzeyVar);
    }
}
