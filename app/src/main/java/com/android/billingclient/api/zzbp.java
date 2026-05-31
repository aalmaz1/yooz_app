package com.android.billingclient.api;

import android.content.Context;
import com.google.android.datatransport.Encoding;
import com.google.android.datatransport.Event;
import com.google.android.datatransport.Transformer;
import com.google.android.datatransport.Transport;
import com.google.android.datatransport.cct.CCTDestination;
import com.google.android.datatransport.runtime.TransportRuntime;
import com.google.android.gms.internal.play_billing.zziv;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes.dex */
final class zzbp {
    private boolean zza;
    private Transport zzb;

    zzbp(Context context) {
        try {
            TransportRuntime.initialize(context);
            this.zzb = TransportRuntime.getInstance().newFactory(CCTDestination.INSTANCE).getTransport("PLAY_BILLING_LIBRARY", zziv.class, Encoding.of("proto"), new Transformer() { // from class: com.android.billingclient.api.zzbo
                @Override // com.google.android.datatransport.Transformer
                public final Object apply(Object obj) {
                    return ((zziv) obj).zzc();
                }
            });
        } catch (Throwable unused) {
            this.zza = true;
        }
    }

    public final void zza(zziv zzivVar) {
        if (this.zza) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingLogger", "Skipping logging since initialization failed.");
            return;
        }
        try {
            this.zzb.send(Event.ofData(zzivVar));
        } catch (Throwable unused) {
            com.google.android.gms.internal.play_billing.zzb.zzk("BillingLogger", "logging failed.");
        }
    }
}
