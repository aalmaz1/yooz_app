package com.android.billingclient.api;

import com.google.android.gms.internal.play_billing.zzhx;
import com.google.android.gms.internal.play_billing.zzhy;
import com.google.android.gms.internal.play_billing.zzib;
import com.google.android.gms.internal.play_billing.zzic;
import com.google.android.gms.internal.play_billing.zzie;
import com.google.android.gms.internal.play_billing.zzii;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class zzbh {
    public static zzhy zza(int i, int i2, BillingResult billingResult) {
        try {
            zzhx zzhxVarZzv = zzhy.zzv();
            zzie zzieVarZzv = zzii.zzv();
            zzieVarZzv.zzk(billingResult.getResponseCode());
            zzieVarZzv.zzj(billingResult.getDebugMessage());
            zzieVarZzv.zzl(i);
            zzhxVarZzv.zzi(zzieVarZzv);
            zzhxVarZzv.zzk(i2);
            return (zzhy) zzhxVarZzv.zzc();
        } catch (Exception e) {
            com.google.android.gms.internal.play_billing.zzb.zzl("BillingLogger", "Unable to create logging payload", e);
            return null;
        }
    }

    public static zzic zzb(int i) {
        try {
            zzib zzibVarZzv = zzic.zzv();
            zzibVarZzv.zzj(i);
            return (zzic) zzibVarZzv.zzc();
        } catch (Exception e) {
            com.google.android.gms.internal.play_billing.zzb.zzl("BillingLogger", "Unable to create logging payload", e);
            return null;
        }
    }
}
