package com.google.android.gms.internal.play_billing;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzfo extends zzfq {
    private zzfo() {
        super(null);
    }

    /* synthetic */ zzfo(zzfn zzfnVar) {
        super(null);
    }

    @Override // com.google.android.gms.internal.play_billing.zzfq
    final void zza(Object obj, long j) {
        ((zzfc) zzhn.zzf(obj, j)).zzb();
    }

    @Override // com.google.android.gms.internal.play_billing.zzfq
    final void zzb(Object obj, Object obj2, long j) {
        zzfc zzfcVarZzd = (zzfc) zzhn.zzf(obj, j);
        zzfc zzfcVar = (zzfc) zzhn.zzf(obj2, j);
        int size = zzfcVarZzd.size();
        int size2 = zzfcVar.size();
        if (size > 0 && size2 > 0) {
            if (!zzfcVarZzd.zzc()) {
                zzfcVarZzd = zzfcVarZzd.zzd(size2 + size);
            }
            zzfcVarZzd.addAll(zzfcVar);
        }
        if (size > 0) {
            zzfcVar = zzfcVarZzd;
        }
        zzhn.zzs(obj, j, zzfcVar);
    }
}
