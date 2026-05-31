package com.google.android.gms.internal.auth;

import java.util.Comparator;
import kotlin.UByte;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* JADX INFO: loaded from: classes2.dex */
final class zzdw implements Comparator {
    zzdw() {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ int compare(Object obj, Object obj2) {
        zzee zzeeVar = (zzee) obj;
        zzee zzeeVar2 = (zzee) obj2;
        zzdv zzdvVar = new zzdv(zzeeVar);
        zzdv zzdvVar2 = new zzdv(zzeeVar2);
        while (zzdvVar.hasNext() && zzdvVar2.hasNext()) {
            int iCompareTo = Integer.valueOf(zzdvVar.zza() & UByte.MAX_VALUE).compareTo(Integer.valueOf(zzdvVar2.zza() & UByte.MAX_VALUE));
            if (iCompareTo != 0) {
                return iCompareTo;
            }
        }
        return Integer.valueOf(zzeeVar.zzd()).compareTo(Integer.valueOf(zzeeVar2.zzd()));
    }
}
