package com.google.android.gms.internal.play_billing;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzcw extends AbstractMap {
    private static final Comparator zza = new zzct();
    private final Object[] zzb;
    private final int[] zzc;
    private final Set zzd = new zzcv(this, -1);
    private Integer zze = null;
    private String zzf = null;

    zzcw(List list) {
        Iterator it = list.iterator();
        if (it.hasNext()) {
            zzcs.zza((zzcs) it.next());
            throw null;
        }
        int size = list.size();
        Object[] objArrCopyOf = new Object[size];
        int[] iArr = new int[1];
        Iterator it2 = list.iterator();
        if (it2.hasNext()) {
            zzcs.zza((zzcs) it2.next());
            throw null;
        }
        iArr[0] = 0;
        if (size > 16 && size * 9 > 0) {
            objArrCopyOf = Arrays.copyOf(objArrCopyOf, 0);
        }
        this.zzb = objArrCopyOf;
        this.zzc = iArr;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set entrySet() {
        return this.zzd;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int hashCode() {
        if (this.zze == null) {
            this.zze = Integer.valueOf(super.hashCode());
        }
        return this.zze.intValue();
    }

    @Override // java.util.AbstractMap
    public final String toString() {
        if (this.zzf == null) {
            this.zzf = super.toString();
        }
        return this.zzf;
    }
}
