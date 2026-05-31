package com.google.android.gms.internal.play_billing;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzcv extends AbstractSet {
    final int zza = -1;
    final /* synthetic */ zzcw zzb;

    zzcv(zzcw zzcwVar, int i) {
        this.zzb = zzcwVar;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return Arrays.binarySearch(this.zzb.zzb, zzb(), zza(), obj, this.zza == -1 ? zzcw.zza : zzcy.zza) >= 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return new zzcu(this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return zza() - zzb();
    }

    final int zza() {
        return this.zzb.zzc[this.zza + 1];
    }

    final int zzb() {
        if (this.zza == -1) {
            return 0;
        }
        return this.zzb.zzc[0];
    }
}
