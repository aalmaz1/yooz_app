package com.google.android.gms.internal.play_billing;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
@Deprecated
public final class zzhi extends AbstractList implements RandomAccess, zzfk {
    private final zzfk zza;

    public zzhi(zzfk zzfkVar) {
        this.zza = zzfkVar;
    }

    @Override // java.util.AbstractList, java.util.List
    public final /* bridge */ /* synthetic */ Object get(int i) {
        return ((zzfj) this.zza).get(i);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator iterator() {
        return new zzhh(this);
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator listIterator(int i) {
        return new zzhg(this, i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zza.size();
    }

    @Override // com.google.android.gms.internal.play_billing.zzfk
    public final zzfk zze() {
        return this;
    }

    @Override // com.google.android.gms.internal.play_billing.zzfk
    public final Object zzf(int i) {
        return this.zza.zzf(i);
    }

    @Override // com.google.android.gms.internal.play_billing.zzfk
    public final List zzh() {
        return this.zza.zzh();
    }
}
