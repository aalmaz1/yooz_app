package com.android.billingclient.api;

import android.os.Bundle;
import android.os.RemoteException;
import org.json.JSONException;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes.dex */
final class zzaq extends com.google.android.gms.internal.play_billing.zzc {
    final AlternativeBillingOnlyReportingDetailsListener zza;
    final zzbi zzb;

    /* synthetic */ zzaq(AlternativeBillingOnlyReportingDetailsListener alternativeBillingOnlyReportingDetailsListener, zzbi zzbiVar, zzap zzapVar) {
        this.zza = alternativeBillingOnlyReportingDetailsListener;
        this.zzb = zzbiVar;
    }

    @Override // com.google.android.gms.internal.play_billing.zzd
    public final void zza(Bundle bundle) throws RemoteException {
        if (bundle == null) {
            this.zzb.zza(zzbh.zza(71, 15, zzbk.zzj));
            this.zza.onAlternativeBillingOnlyTokenResponse(zzbk.zzj, null);
            return;
        }
        int iZzb = com.google.android.gms.internal.play_billing.zzb.zzb(bundle, "BillingClient");
        BillingResult billingResultZza = zzbk.zza(iZzb, com.google.android.gms.internal.play_billing.zzb.zzg(bundle, "BillingClient"));
        if (iZzb != 0) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "createAlternativeBillingOnlyReportingDetailsAsync() failed. Response code: " + iZzb);
            this.zzb.zza(zzbh.zza(23, 15, billingResultZza));
            this.zza.onAlternativeBillingOnlyTokenResponse(billingResultZza, null);
            return;
        }
        try {
            this.zza.onAlternativeBillingOnlyTokenResponse(billingResultZza, new AlternativeBillingOnlyReportingDetails(bundle.getString("CREATE_ALTERNATIVE_BILLING_ONLY_REPORTING_DETAILS")));
        } catch (JSONException e) {
            com.google.android.gms.internal.play_billing.zzb.zzl("BillingClient", "Error when parsing invalid alternative billing only reporting details. \n Exception: ", e);
            this.zzb.zza(zzbh.zza(72, 15, zzbk.zzj));
            this.zza.onAlternativeBillingOnlyTokenResponse(zzbk.zzj, null);
        }
    }
}
