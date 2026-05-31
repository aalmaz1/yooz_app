package com.google.android.gms.internal.play_billing;

import java.util.AbstractMap;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzam extends zzaf {
    final /* synthetic */ zzan zza;

    zzam(zzan zzanVar) {
        this.zza = zzanVar;
    }

    @Override // java.util.List
    public final /* bridge */ /* synthetic */ Object get(int i) {
        zzx.zza(i, this.zza.zzc, "index");
        zzan zzanVar = this.zza;
        int i2 = i + i;
        Object obj = zzanVar.zzb[i2];
        obj.getClass();
        Object obj2 = zzanVar.zzb[i2 + 1];
        obj2.getClass();
        return new AbstractMap.SimpleImmutableEntry(obj, obj2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zza.zzc;
    }

    @Override // com.google.android.gms.internal.play_billing.zzac
    public final boolean zzf() {
        return true;
    }
}
