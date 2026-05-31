package com.google.android.gms.internal.auth;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzgx extends RuntimeException {
    public zzgx(zzfw zzfwVar) {
        super("Message was missing required fields.  (Lite runtime could not determine which fields were missing).");
    }

    public final zzfa zza() {
        return new zzfa(getMessage());
    }
}
