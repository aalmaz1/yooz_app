package com.android.billingclient.api;

import android.os.Bundle;
import android.os.RemoteException;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes.dex */
final class zzaw extends com.google.android.gms.internal.play_billing.zzi {
    final AlternativeBillingOnlyAvailabilityListener zza;
    final zzbi zzb;

    /* synthetic */ zzaw(AlternativeBillingOnlyAvailabilityListener alternativeBillingOnlyAvailabilityListener, zzbi zzbiVar, zzav zzavVar) {
        this.zza = alternativeBillingOnlyAvailabilityListener;
        this.zzb = zzbiVar;
    }

    @Override // com.google.android.gms.internal.play_billing.zzj
    public final void zza(Bundle bundle) throws RemoteException {
        if (bundle == null) {
            this.zzb.zza(zzbh.zza(67, 14, zzbk.zzj));
            this.zza.onAlternativeBillingOnlyAvailabilityResponse(zzbk.zzj);
            return;
        }
        int iZzb = com.google.android.gms.internal.play_billing.zzb.zzb(bundle, "BillingClient");
        BillingResult billingResultZza = zzbk.zza(iZzb, com.google.android.gms.internal.play_billing.zzb.zzg(bundle, "BillingClient"));
        if (iZzb != 0) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "isAlternativeBillingOnlyAvailableAsync() failed. Response code: " + iZzb);
            this.zzb.zza(zzbh.zza(23, 14, billingResultZza));
        }
        this.zza.onAlternativeBillingOnlyAvailabilityResponse(billingResultZza);
    }
}
