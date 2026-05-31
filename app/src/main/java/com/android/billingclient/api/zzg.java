package com.android.billingclient.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import com.google.android.gms.internal.play_billing.zzej;
import com.google.android.gms.internal.play_billing.zzhy;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes.dex */
final class zzg extends BroadcastReceiver {
    final /* synthetic */ zzh zza;
    private final PurchasesUpdatedListener zzb;
    private final zzbq zzc;
    private final AlternativeBillingListener zzd;
    private final UserChoiceBillingListener zze;
    private final zzbi zzf;
    private boolean zzg;

    /* synthetic */ zzg(zzh zzhVar, zzbq zzbqVar, zzbi zzbiVar, zzf zzfVar) {
        this.zza = zzhVar;
        this.zzb = null;
        this.zzd = null;
        this.zze = null;
        this.zzc = null;
        this.zzf = zzbiVar;
    }

    static /* bridge */ /* synthetic */ zzbq zza(zzg zzgVar) {
        zzbq zzbqVar = zzgVar.zzc;
        return null;
    }

    private final void zze(Bundle bundle, BillingResult billingResult, int i) {
        if (bundle.getByteArray("FAILURE_LOGGING_PAYLOAD") == null) {
            this.zzf.zza(zzbh.zza(23, i, billingResult));
            return;
        }
        try {
            this.zzf.zza(zzhy.zzx(bundle.getByteArray("FAILURE_LOGGING_PAYLOAD"), zzej.zza()));
        } catch (Throwable unused) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingBroadcastManager", "Failed parsing Api failure.");
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras == null) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingBroadcastManager", "Bundle is null.");
            this.zzf.zza(zzbh.zza(11, 1, zzbk.zzj));
            PurchasesUpdatedListener purchasesUpdatedListener = this.zzb;
            if (purchasesUpdatedListener != null) {
                purchasesUpdatedListener.onPurchasesUpdated(zzbk.zzj, null);
                return;
            }
            return;
        }
        BillingResult billingResultZze = com.google.android.gms.internal.play_billing.zzb.zze(intent, "BillingBroadcastManager");
        String action = intent.getAction();
        String string = extras.getString("INTENT_SOURCE");
        int i = 2;
        if (string != "LAUNCH_BILLING_FLOW" && (string == null || !string.equals("LAUNCH_BILLING_FLOW"))) {
            i = 1;
        }
        if (action.equals("com.android.vending.billing.PURCHASES_UPDATED") || action.equals("com.android.vending.billing.LOCAL_BROADCAST_PURCHASES_UPDATED")) {
            List<Purchase> listZzi = com.google.android.gms.internal.play_billing.zzb.zzi(extras);
            if (billingResultZze.getResponseCode() == 0) {
                this.zzf.zzb(zzbh.zzb(i));
            } else {
                zze(extras, billingResultZze, i);
            }
            this.zzb.onPurchasesUpdated(billingResultZze, listZzi);
            return;
        }
        if (action.equals("com.android.vending.billing.ALTERNATIVE_BILLING")) {
            if (billingResultZze.getResponseCode() != 0) {
                zze(extras, billingResultZze, i);
                this.zzb.onPurchasesUpdated(billingResultZze, com.google.android.gms.internal.play_billing.zzaf.zzk());
                return;
            }
            if (this.zzd == null && this.zze == null) {
                com.google.android.gms.internal.play_billing.zzb.zzk("BillingBroadcastManager", "AlternativeBillingListener and UserChoiceBillingListener is null.");
                this.zzf.zza(zzbh.zza(77, i, zzbk.zzj));
                this.zzb.onPurchasesUpdated(zzbk.zzj, com.google.android.gms.internal.play_billing.zzaf.zzk());
                return;
            }
            String string2 = extras.getString("ALTERNATIVE_BILLING_USER_CHOICE_DATA");
            if (string2 == null) {
                com.google.android.gms.internal.play_billing.zzb.zzk("BillingBroadcastManager", "Couldn't find alternative billing user choice data in bundle.");
                this.zzf.zza(zzbh.zza(16, i, zzbk.zzj));
                this.zzb.onPurchasesUpdated(zzbk.zzj, com.google.android.gms.internal.play_billing.zzaf.zzk());
                return;
            }
            try {
                if (this.zze != null) {
                    this.zze.userSelectedAlternativeBilling(new UserChoiceDetails(string2));
                } else {
                    this.zzd.userSelectedAlternativeBilling(new AlternativeChoiceDetails(string2));
                }
                this.zzf.zzb(zzbh.zzb(i));
            } catch (JSONException unused) {
                com.google.android.gms.internal.play_billing.zzb.zzk("BillingBroadcastManager", String.format("Error when parsing invalid user choice data: [%s]", string2));
                this.zzf.zza(zzbh.zza(17, i, zzbk.zzj));
                this.zzb.onPurchasesUpdated(zzbk.zzj, com.google.android.gms.internal.play_billing.zzaf.zzk());
            }
        }
    }

    public final synchronized void zzc(Context context, IntentFilter intentFilter, String str, IntentFilter intentFilter2) {
        if (this.zzg) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 33) {
            context.registerReceiver(this.zza.zzb, intentFilter, null, null, 2);
        } else {
            this.zza.zza.getApplicationContext().getPackageName();
            context.registerReceiver(this.zza.zzb, intentFilter);
        }
        this.zzg = true;
    }

    public final synchronized void zzd(Context context) {
        if (!this.zzg) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingBroadcastManager", "Receiver is not registered.");
        } else {
            context.unregisterReceiver(this.zza.zzb);
            this.zzg = false;
        }
    }

    /* synthetic */ zzg(zzh zzhVar, PurchasesUpdatedListener purchasesUpdatedListener, AlternativeBillingListener alternativeBillingListener, zzbi zzbiVar, zzf zzfVar) {
        this.zza = zzhVar;
        this.zzb = purchasesUpdatedListener;
        this.zzf = zzbiVar;
        this.zzd = alternativeBillingListener;
        this.zze = null;
        this.zzc = null;
    }

    /* synthetic */ zzg(zzh zzhVar, PurchasesUpdatedListener purchasesUpdatedListener, UserChoiceBillingListener userChoiceBillingListener, zzbi zzbiVar, zzf zzfVar) {
        this.zza = zzhVar;
        this.zzb = purchasesUpdatedListener;
        this.zzf = zzbiVar;
        this.zze = userChoiceBillingListener;
        this.zzd = null;
        this.zzc = null;
    }
}
