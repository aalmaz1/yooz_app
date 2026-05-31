package com.google.android.gms.internal.play_billing;

import java.util.Iterator;
import java.util.Map;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzan extends zzaj {
    private final transient zzai zza;
    private final transient Object[] zzb;
    private final transient int zzc;

    zzan(zzai zzaiVar, Object[] objArr, int i, int i2) {
        this.zza = zzaiVar;
        this.zzb = objArr;
        this.zzc = i2;
    }

    @Override // com.google.android.gms.internal.play_billing.zzac, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(@CheckForNull Object obj) {
        if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (value != null && value.equals(this.zza.get(key))) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.gms.internal.play_billing.zzaj, com.google.android.gms.internal.play_billing.zzac, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* synthetic */ Iterator iterator() {
        return zzd().listIterator(0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.play_billing.zzac
    final int zza(Object[] objArr, int i) {
        return zzd().zza(objArr, 0);
    }

    @Override // com.google.android.gms.internal.play_billing.zzaj, com.google.android.gms.internal.play_billing.zzac
    /* JADX INFO: renamed from: zze */
    public final zzas iterator() {
        return zzd().listIterator(0);
    }

    @Override // com.google.android.gms.internal.play_billing.zzac
    final boolean zzf() {
        throw null;
    }

    @Override // com.google.android.gms.internal.play_billing.zzaj
    final zzaf zzh() {
        return new zzam(this);
    }
}
