package com.google.android.gms.internal.play_billing;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzhc extends RuntimeException {
    public zzhc(zzgc zzgcVar) {
        super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
    }

    public final zzff zza() {
        return new zzff(getMessage());
    }
}
