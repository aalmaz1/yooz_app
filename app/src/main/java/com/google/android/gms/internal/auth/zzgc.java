package com.google.android.gms.internal.auth;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* JADX INFO: loaded from: classes2.dex */
final class zzgc {
    private static final zzgb zza;
    private static final zzgb zzb;

    static {
        zzgb zzgbVar;
        try {
            zzgbVar = (zzgb) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            zzgbVar = null;
        }
        zza = zzgbVar;
        zzb = new zzgb();
    }

    static zzgb zza() {
        return zza;
    }

    static zzgb zzb() {
        return zzb;
    }
}
