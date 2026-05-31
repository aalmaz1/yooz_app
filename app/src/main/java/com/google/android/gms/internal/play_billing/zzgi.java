package com.google.android.gms.internal.play_billing;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzgi {
    private static final zzgh zza;
    private static final zzgh zzb;

    static {
        zzgh zzghVar;
        try {
            zzghVar = (zzgh) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            zzghVar = null;
        }
        zza = zzghVar;
        zzb = new zzgh();
    }

    static zzgh zza() {
        return zza;
    }

    static zzgh zzb() {
        return zzb;
    }
}
