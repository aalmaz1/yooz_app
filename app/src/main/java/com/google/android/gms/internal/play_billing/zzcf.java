package com.google.android.gms.internal.play_billing;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzcf extends zzcl {
    private static final zzcf zza = new zzcf(zzcl.zze());
    private final AtomicReference zzb;

    zzcf(zzcl zzclVar) {
        this.zzb = new AtomicReference(zzclVar);
    }

    public static final zzcf zzb() {
        return zza;
    }

    @Override // com.google.android.gms.internal.play_billing.zzcl
    public final zzbh zza() {
        return ((zzcl) this.zzb.get()).zza();
    }

    @Override // com.google.android.gms.internal.play_billing.zzcl
    public final zzcy zzc() {
        return ((zzcl) this.zzb.get()).zzc();
    }

    @Override // com.google.android.gms.internal.play_billing.zzcl
    public final boolean zzd(String str, Level level, boolean z) {
        ((zzcl) this.zzb.get()).zzd(str, level, z);
        return false;
    }
}
