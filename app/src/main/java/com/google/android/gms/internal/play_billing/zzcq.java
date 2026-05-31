package com.google.android.gms.internal.play_billing;

import java.util.Comparator;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzcq implements Comparator {
    zzcq() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        zzcx zzcxVarZza = zzcx.zza(obj);
        zzcx zzcxVarZza2 = zzcx.zza(obj2);
        if (zzcxVarZza != zzcxVarZza2) {
            return zzcxVarZza.compareTo(zzcxVarZza2);
        }
        int iOrdinal = zzcxVarZza.ordinal();
        if (iOrdinal == 0) {
            return ((Boolean) obj).compareTo((Boolean) obj2);
        }
        if (iOrdinal == 1) {
            return ((String) obj).compareTo((String) obj2);
        }
        if (iOrdinal == 2) {
            return ((Long) obj).compareTo((Long) obj2);
        }
        if (iOrdinal == 3) {
            return ((Double) obj).compareTo((Double) obj2);
        }
        throw null;
    }
}
