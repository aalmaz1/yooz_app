package com.google.android.gms.internal.play_billing;

import java.util.Comparator;
import kotlin.UByte;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzdo implements Comparator {
    zzdo() {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ int compare(Object obj, Object obj2) {
        zzdw zzdwVar = (zzdw) obj;
        zzdw zzdwVar2 = (zzdw) obj2;
        zzdn zzdnVar = new zzdn(zzdwVar);
        zzdn zzdnVar2 = new zzdn(zzdwVar2);
        while (zzdnVar.hasNext() && zzdnVar2.hasNext()) {
            int iCompareTo = Integer.valueOf(zzdnVar.zza() & UByte.MAX_VALUE).compareTo(Integer.valueOf(zzdnVar2.zza() & UByte.MAX_VALUE));
            if (iCompareTo != 0) {
                return iCompareTo;
            }
        }
        return Integer.valueOf(zzdwVar.zzd()).compareTo(Integer.valueOf(zzdwVar2.zzd()));
    }
}
