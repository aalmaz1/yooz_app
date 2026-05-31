package com.android.billingclient.api;

import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes.dex */
final class zzah implements Callable {
    final /* synthetic */ String zza;
    final /* synthetic */ PurchasesResponseListener zzb;
    final /* synthetic */ BillingClientImpl zzc;

    zzah(BillingClientImpl billingClientImpl, String str, PurchasesResponseListener purchasesResponseListener) {
        this.zzc = billingClientImpl;
        this.zza = str;
        this.zzb = purchasesResponseListener;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Object call() throws Exception {
        zzce zzceVarZzX = BillingClientImpl.zzX(this.zzc, this.zza, 9);
        if (zzceVarZzX.zzb() != null) {
            this.zzb.onQueryPurchasesResponse(zzceVarZzX.zza(), zzceVarZzX.zzb());
            return null;
        }
        this.zzb.onQueryPurchasesResponse(zzceVarZzX.zza(), com.google.android.gms.internal.play_billing.zzaf.zzk());
        return null;
    }
}
