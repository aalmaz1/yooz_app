package com.android.billingclient.api;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import com.android.billingclient.api.BillingResult;
import com.google.android.gms.internal.play_billing.zzhx;
import com.google.android.gms.internal.play_billing.zzhy;
import com.google.android.gms.internal.play_billing.zzie;
import com.google.android.gms.internal.play_billing.zzih;
import com.google.android.gms.internal.play_billing.zzii;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes.dex */
final class zzak extends ResultReceiver {
    final /* synthetic */ AlternativeBillingOnlyInformationDialogListener zza;
    final /* synthetic */ BillingClientImpl zzb;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzak(BillingClientImpl billingClientImpl, Handler handler, AlternativeBillingOnlyInformationDialogListener alternativeBillingOnlyInformationDialogListener) {
        super(handler);
        this.zzb = billingClientImpl;
        this.zza = alternativeBillingOnlyInformationDialogListener;
    }

    @Override // android.os.ResultReceiver
    public final void onReceiveResult(int i, Bundle bundle) {
        zzhy zzhyVar;
        BillingResult.Builder builderNewBuilder = BillingResult.newBuilder();
        builderNewBuilder.setResponseCode(i);
        if (i != 0) {
            if (bundle == null) {
                this.zzb.zzf.zza(zzbh.zza(73, 16, zzbk.zzj));
                this.zza.onAlternativeBillingOnlyInformationDialogResponse(zzbk.zzj);
                return;
            }
            builderNewBuilder.setDebugMessage(com.google.android.gms.internal.play_billing.zzb.zzg(bundle, "BillingClient"));
            int i2 = bundle.getInt("INTERNAL_LOG_ERROR_REASON");
            zzbi zzbiVar = this.zzb.zzf;
            int iZza = i2 != 0 ? zzih.zza(i2) : 23;
            BillingResult billingResultBuild = builderNewBuilder.build();
            String string = bundle.getString("INTERNAL_LOG_ERROR_ADDITIONAL_DETAILS");
            try {
                zzie zzieVarZzv = zzii.zzv();
                zzieVarZzv.zzk(billingResultBuild.getResponseCode());
                zzieVarZzv.zzj(billingResultBuild.getDebugMessage());
                zzieVarZzv.zzl(iZza);
                if (string != null) {
                    zzieVarZzv.zzi(string);
                }
                zzhx zzhxVarZzv = zzhy.zzv();
                zzhxVarZzv.zzi(zzieVarZzv);
                zzhxVarZzv.zzk(16);
                zzhyVar = (zzhy) zzhxVarZzv.zzc();
            } catch (Exception e) {
                com.google.android.gms.internal.play_billing.zzb.zzl("BillingLogger", "Unable to create logging payload", e);
                zzhyVar = null;
            }
            zzbiVar.zza(zzhyVar);
        }
        this.zza.onAlternativeBillingOnlyInformationDialogResponse(builderNewBuilder.build());
    }
}
