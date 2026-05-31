package com.google.android.gms.internal.play_billing;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzft implements zzga {
    private final zzga[] zza;

    zzft(zzga... zzgaVarArr) {
        this.zza = zzgaVarArr;
    }

    @Override // com.google.android.gms.internal.play_billing.zzga
    public final zzfz zzb(Class cls) {
        for (int i = 0; i < 2; i++) {
            zzga zzgaVar = this.zza[i];
            if (zzgaVar.zzc(cls)) {
                return zzgaVar.zzb(cls);
            }
        }
        throw new UnsupportedOperationException("No factory is available for message type: ".concat(String.valueOf(cls.getName())));
    }

    @Override // com.google.android.gms.internal.play_billing.zzga
    public final boolean zzc(Class cls) {
        for (int i = 0; i < 2; i++) {
            if (this.zza[i].zzc(cls)) {
                return true;
            }
        }
        return false;
    }
}
