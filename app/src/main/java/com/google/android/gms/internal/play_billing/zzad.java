package com.google.android.gms.internal.play_billing;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzad extends zzz {
    private final zzaf zza;

    zzad(zzaf zzafVar, int i) {
        super(zzafVar.size(), i);
        this.zza = zzafVar;
    }

    @Override // com.google.android.gms.internal.play_billing.zzz
    protected final Object zza(int i) {
        return this.zza.get(i);
    }
}
