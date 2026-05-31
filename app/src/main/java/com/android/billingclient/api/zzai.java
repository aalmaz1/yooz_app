package com.android.billingclient.api;

import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes.dex */
final class zzai implements Callable {
    final /* synthetic */ String zza;
    final /* synthetic */ PurchaseHistoryResponseListener zzb;
    final /* synthetic */ BillingClientImpl zzc;

    zzai(BillingClientImpl billingClientImpl, String str, PurchaseHistoryResponseListener purchaseHistoryResponseListener) {
        this.zzc = billingClientImpl;
        this.zza = str;
        this.zzb = purchaseHistoryResponseListener;
    }

    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Object call() throws Exception {
        zzaz zzazVarZzg = BillingClientImpl.zzg(this.zzc, this.zza);
        this.zzb.onPurchaseHistoryResponse(zzazVarZzg.zza(), zzazVarZzg.zzb());
        return null;
    }
}
