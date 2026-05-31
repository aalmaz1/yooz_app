package com.android.billingclient.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ResultReceiver;
import java.lang.ref.WeakReference;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes.dex */
final class zzas extends com.google.android.gms.internal.play_billing.zze {
    final WeakReference zza;
    final ResultReceiver zzb;
    final zzbi zzc;

    /* synthetic */ zzas(WeakReference weakReference, ResultReceiver resultReceiver, zzbi zzbiVar, zzar zzarVar) {
        this.zza = weakReference;
        this.zzb = resultReceiver;
        this.zzc = zzbiVar;
    }

    @Override // com.google.android.gms.internal.play_billing.zzf
    public final void zza(Bundle bundle) throws RemoteException {
        ResultReceiver resultReceiver = this.zzb;
        if (resultReceiver == null) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Unable to send result for alternative billing only dialog");
            this.zzc.zza(zzbh.zza(75, 16, zzbk.zzj));
            return;
        }
        if (bundle == null) {
            resultReceiver.send(6, null);
            return;
        }
        Activity activity = (Activity) this.zza.get();
        if (activity == null) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "The activity from which to launch alternative billing only dialog is null");
            this.zzb.send(6, null);
            return;
        }
        if (!bundle.containsKey("RESPONSE_CODE")) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Response bundle doesn't contain a response code");
            this.zzb.send(6, bundle);
            return;
        }
        int iZzb = com.google.android.gms.internal.play_billing.zzb.zzb(bundle, "BillingClient");
        if (iZzb != 0) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingClient", "Unable to launch intent for alternative billing only dialog" + iZzb);
            this.zzb.send(iZzb, bundle);
            return;
        }
        PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable("ALTERNATIVE_BILLING_ONLY_DIALOG_INTENT");
        if (pendingIntent == null) {
            com.google.android.gms.internal.play_billing.zzb.zzj("BillingClient", "User has acknowledged the alternative billing only dialog before.");
            this.zzb.send(0, bundle);
            return;
        }
        try {
            Intent intent = new Intent(activity, (Class<?>) ProxyBillingActivity.class);
            intent.putExtra("alternative_billing_only_dialog_result_receiver", this.zzb);
            intent.putExtra("ALTERNATIVE_BILLING_ONLY_DIALOG_INTENT", pendingIntent);
            activity.startActivity(intent);
        } catch (RuntimeException e) {
            com.google.android.gms.internal.play_billing.zzb.zzl("BillingClient", "Runtime error while launching intent for alternative billing only dialog.", e);
            Bundle bundle2 = new Bundle();
            bundle2.putInt("RESPONSE_CODE", 6);
            bundle2.putString("DEBUG_MESSAGE", "An internal error occurred.");
            bundle2.putInt("INTERNAL_LOG_ERROR_REASON", 75);
            bundle2.putString("INTERNAL_LOG_ERROR_ADDITIONAL_DETAILS", String.format("%s: %s", e.getClass().getName(), com.google.android.gms.internal.play_billing.zzy.zzb(e.getMessage())));
            this.zzb.send(6, bundle2);
        }
    }
}
