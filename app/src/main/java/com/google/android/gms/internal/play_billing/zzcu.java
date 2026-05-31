package com.google.android.gms.internal.play_billing;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzcu implements Iterator {
    final /* synthetic */ zzcv zza;
    private int zzb = 0;

    zzcu(zzcv zzcvVar) {
        this.zza = zzcvVar;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        int i = this.zzb;
        zzcv zzcvVar = this.zza;
        return i < zzcvVar.zza() - zzcvVar.zzb();
    }

    @Override // java.util.Iterator
    public final Object next() {
        int i = this.zzb;
        zzcv zzcvVar = this.zza;
        if (i >= zzcvVar.zza() - zzcvVar.zzb()) {
            throw new NoSuchElementException();
        }
        zzcv zzcvVar2 = this.zza;
        Object obj = zzcvVar2.zzb.zzb[zzcvVar2.zzb() + i];
        this.zzb = i + 1;
        return obj;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
