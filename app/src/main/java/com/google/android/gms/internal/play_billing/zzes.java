package com.google.android.gms.internal.play_billing;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzes implements zzga {
    private static final zzes zza = new zzes();

    private zzes() {
    }

    public static zzes zza() {
        return zza;
    }

    @Override // com.google.android.gms.internal.play_billing.zzga
    public final zzfz zzb(Class cls) {
        if (!zzex.class.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Unsupported message type: ".concat(String.valueOf(cls.getName())));
        }
        try {
            return (zzfz) zzex.zzh(cls.asSubclass(zzex.class)).zzu(3, null, null);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get message info for ".concat(String.valueOf(cls.getName())), e);
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzga
    public final boolean zzc(Class cls) {
        return zzex.class.isAssignableFrom(cls);
    }
}
