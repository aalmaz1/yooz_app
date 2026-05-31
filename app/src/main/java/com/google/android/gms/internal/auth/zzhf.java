package com.google.android.gms.internal.auth;

import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* JADX INFO: loaded from: classes2.dex */
final class zzhf extends zzhh {
    zzhf(Unsafe unsafe) {
        super(unsafe);
    }

    @Override // com.google.android.gms.internal.auth.zzhh
    public final double zza(Object obj, long j) {
        return Double.longBitsToDouble(zzj(obj, j));
    }

    @Override // com.google.android.gms.internal.auth.zzhh
    public final float zzb(Object obj, long j) {
        return Float.intBitsToFloat(zzi(obj, j));
    }

    @Override // com.google.android.gms.internal.auth.zzhh
    public final void zzc(Object obj, long j, boolean z) {
        if (zzhi.zza) {
            zzhi.zzi(obj, j, z);
        } else {
            zzhi.zzj(obj, j, z);
        }
    }

    @Override // com.google.android.gms.internal.auth.zzhh
    public final void zzd(Object obj, long j, double d) {
        zzn(obj, j, Double.doubleToLongBits(d));
    }

    @Override // com.google.android.gms.internal.auth.zzhh
    public final void zze(Object obj, long j, float f) {
        zzm(obj, j, Float.floatToIntBits(f));
    }

    @Override // com.google.android.gms.internal.auth.zzhh
    public final boolean zzf(Object obj, long j) {
        return zzhi.zza ? zzhi.zzq(obj, j) : zzhi.zzr(obj, j);
    }
}
