package com.google.android.gms.internal.play_billing;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzgk {
    private static final zzgk zza = new zzgk();
    private final ConcurrentMap zzc = new ConcurrentHashMap();
    private final zzgn zzb = new zzfu();

    private zzgk() {
    }

    public static zzgk zza() {
        return zza;
    }

    public final zzgm zzb(Class cls) {
        zzfd.zzc(cls, "messageType");
        zzgm zzgmVarZza = (zzgm) this.zzc.get(cls);
        if (zzgmVarZza == null) {
            zzgmVarZza = this.zzb.zza(cls);
            zzfd.zzc(cls, "messageType");
            zzgm zzgmVar = (zzgm) this.zzc.putIfAbsent(cls, zzgmVarZza);
            if (zzgmVar != null) {
                return zzgmVar;
            }
        }
        return zzgmVarZza;
    }
}
