package com.google.android.gms.internal.play_billing;

import java.util.Iterator;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzao extends zzaj {
    private final transient zzai zza;
    private final transient zzaf zzb;

    zzao(zzai zzaiVar, zzaf zzafVar) {
        this.zza = zzaiVar;
        this.zzb = zzafVar;
    }

    @Override // com.google.android.gms.internal.play_billing.zzac, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(@CheckForNull Object obj) {
        return this.zza.get(obj) != null;
    }

    @Override // com.google.android.gms.internal.play_billing.zzaj, com.google.android.gms.internal.play_billing.zzac, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* synthetic */ Iterator iterator() {
        return this.zzb.listIterator(0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zza.size();
    }

    @Override // com.google.android.gms.internal.play_billing.zzac
    final int zza(Object[] objArr, int i) {
        return this.zzb.zza(objArr, 0);
    }

    @Override // com.google.android.gms.internal.play_billing.zzaj, com.google.android.gms.internal.play_billing.zzac
    public final zzaf zzd() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.play_billing.zzaj, com.google.android.gms.internal.play_billing.zzac
    /* JADX INFO: renamed from: zze */
    public final zzas iterator() {
        return this.zzb.listIterator(0);
    }

    @Override // com.google.android.gms.internal.play_billing.zzac
    final boolean zzf() {
        throw null;
    }
}
