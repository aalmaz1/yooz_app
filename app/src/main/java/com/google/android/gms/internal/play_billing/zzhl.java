package com.google.android.gms.internal.play_billing;

import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzhl extends zzhm {
    zzhl(Unsafe unsafe) {
        super(unsafe);
    }

    @Override // com.google.android.gms.internal.play_billing.zzhm
    public final double zza(Object obj, long j) {
        return Double.longBitsToDouble(this.zza.getLong(obj, j));
    }

    @Override // com.google.android.gms.internal.play_billing.zzhm
    public final float zzb(Object obj, long j) {
        return Float.intBitsToFloat(this.zza.getInt(obj, j));
    }

    @Override // com.google.android.gms.internal.play_billing.zzhm
    public final void zzc(Object obj, long j, boolean z) {
        if (zzhn.zzb) {
            zzhn.zzD(obj, j, z ? (byte) 1 : (byte) 0);
        } else {
            zzhn.zzE(obj, j, z ? (byte) 1 : (byte) 0);
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzhm
    public final void zzd(Object obj, long j, byte b) {
        if (zzhn.zzb) {
            zzhn.zzD(obj, j, b);
        } else {
            zzhn.zzE(obj, j, b);
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzhm
    public final void zze(Object obj, long j, double d) {
        this.zza.putLong(obj, j, Double.doubleToLongBits(d));
    }

    @Override // com.google.android.gms.internal.play_billing.zzhm
    public final void zzf(Object obj, long j, float f) {
        this.zza.putInt(obj, j, Float.floatToIntBits(f));
    }

    @Override // com.google.android.gms.internal.play_billing.zzhm
    public final boolean zzg(Object obj, long j) {
        return zzhn.zzb ? zzhn.zzt(obj, j) : zzhn.zzu(obj, j);
    }
}
