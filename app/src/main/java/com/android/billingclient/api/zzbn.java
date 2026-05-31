package com.android.billingclient.api;

import android.content.Context;
import com.google.android.gms.internal.play_billing.zzhy;
import com.google.android.gms.internal.play_billing.zzic;
import com.google.android.gms.internal.play_billing.zzio;
import com.google.android.gms.internal.play_billing.zziu;
import com.google.android.gms.internal.play_billing.zziv;
import com.google.android.gms.internal.play_billing.zziz;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes.dex */
final class zzbn implements zzbi {
    private final zzio zza;
    private final zzbp zzb;

    zzbn(Context context, zzio zzioVar) {
        this.zzb = new zzbp(context);
        this.zza = zzioVar;
    }

    @Override // com.android.billingclient.api.zzbi
    public final void zza(zzhy zzhyVar) {
        if (zzhyVar == null) {
            return;
        }
        try {
            zziu zziuVarZzv = zziv.zzv();
            zzio zzioVar = this.zza;
            if (zzioVar != null) {
                zziuVarZzv.zzk(zzioVar);
            }
            zziuVarZzv.zzi(zzhyVar);
            this.zzb.zza((zziv) zziuVarZzv.zzc());
        } catch (Throwable unused) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingLogger", "Unable to log.");
        }
    }

    @Override // com.android.billingclient.api.zzbi
    public final void zzb(zzic zzicVar) {
        if (zzicVar == null) {
            return;
        }
        try {
            zziu zziuVarZzv = zziv.zzv();
            zzio zzioVar = this.zza;
            if (zzioVar != null) {
                zziuVarZzv.zzk(zzioVar);
            }
            zziuVarZzv.zzj(zzicVar);
            this.zzb.zza((zziv) zziuVarZzv.zzc());
        } catch (Throwable unused) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingLogger", "Unable to log.");
        }
    }

    @Override // com.android.billingclient.api.zzbi
    public final void zzc(zziz zzizVar) {
        if (zzizVar == null) {
            return;
        }
        try {
            zziu zziuVarZzv = zziv.zzv();
            zzio zzioVar = this.zza;
            if (zzioVar != null) {
                zziuVarZzv.zzk(zzioVar);
            }
            zziuVarZzv.zzl(zzizVar);
            this.zzb.zza((zziv) zziuVarZzv.zzc());
        } catch (Throwable unused) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingLogger", "Unable to log.");
        }
    }
}
