package com.android.billingclient.api;

import android.content.Context;
import android.content.IntentFilter;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes.dex */
final class zzh {
    private final Context zza;
    private final zzg zzb;

    zzh(Context context, zzbq zzbqVar, zzbi zzbiVar) {
        this.zza = context;
        this.zzb = new zzg(this, null, zzbiVar, 0 == true ? 1 : 0);
    }

    final zzbq zzc() {
        zzg.zza(this.zzb);
        return null;
    }

    final PurchasesUpdatedListener zzd() {
        return this.zzb.zzb;
    }

    final void zze() {
        this.zzb.zzd(this.zza);
    }

    final void zzf(boolean z) {
        IntentFilter intentFilter = new IntentFilter("com.android.vending.billing.PURCHASES_UPDATED");
        this.zza.getApplicationContext().getPackageName();
        intentFilter.addAction("com.android.vending.billing.ALTERNATIVE_BILLING");
        this.zzb.zzc(this.zza, intentFilter, null, null);
    }

    zzh(Context context, PurchasesUpdatedListener purchasesUpdatedListener, AlternativeBillingListener alternativeBillingListener, zzbi zzbiVar) {
        this.zza = context;
        this.zzb = new zzg(this, purchasesUpdatedListener, alternativeBillingListener, zzbiVar, (zzf) null);
    }

    zzh(Context context, PurchasesUpdatedListener purchasesUpdatedListener, UserChoiceBillingListener userChoiceBillingListener, zzbi zzbiVar) {
        this.zza = context;
        this.zzb = new zzg(this, purchasesUpdatedListener, userChoiceBillingListener, zzbiVar, (zzf) null);
    }
}
