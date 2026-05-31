package com.google.android.gms.internal.play_billing;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzbn extends zzbq {
    private final Map zza;
    private final Map zzb;
    private final zzbp zzc;
    private final zzbo zzd;

    /* synthetic */ zzbn(zzbl zzblVar, zzbm zzbmVar) {
        HashMap map = new HashMap();
        this.zza = map;
        HashMap map2 = new HashMap();
        this.zzb = map2;
        map.putAll(zzblVar.zzc);
        map2.putAll(zzblVar.zzd);
        this.zzc = zzblVar.zze;
        this.zzd = zzblVar.zzf;
    }
}
