package com.google.android.gms.internal.play_billing;

import java.util.NoSuchElementException;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzdn extends zzdp {
    final /* synthetic */ zzdw zza;
    private int zzb = 0;
    private final int zzc;

    zzdn(zzdw zzdwVar) {
        this.zza = zzdwVar;
        this.zzc = zzdwVar.zzd();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzb < this.zzc;
    }

    @Override // com.google.android.gms.internal.play_billing.zzdr
    public final byte zza() {
        int i = this.zzb;
        if (i >= this.zzc) {
            throw new NoSuchElementException();
        }
        this.zzb = i + 1;
        return this.zza.zzb(i);
    }
}
