package com.google.android.gms.internal.play_billing;

import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzhe {
    private static final zzhe zza = new zzhe(0, new int[0], new Object[0], false);
    private int zzb;
    private int[] zzc;
    private Object[] zzd;
    private int zze;
    private boolean zzf;

    private zzhe() {
        this(0, new int[8], new Object[8], true);
    }

    private zzhe(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zze = -1;
        this.zzb = i;
        this.zzc = iArr;
        this.zzd = objArr;
        this.zzf = z;
    }

    public static zzhe zzc() {
        return zza;
    }

    static zzhe zze(zzhe zzheVar, zzhe zzheVar2) {
        int i = zzheVar.zzb + zzheVar2.zzb;
        int[] iArrCopyOf = Arrays.copyOf(zzheVar.zzc, i);
        System.arraycopy(zzheVar2.zzc, 0, iArrCopyOf, zzheVar.zzb, zzheVar2.zzb);
        Object[] objArrCopyOf = Arrays.copyOf(zzheVar.zzd, i);
        System.arraycopy(zzheVar2.zzd, 0, objArrCopyOf, zzheVar.zzb, zzheVar2.zzb);
        return new zzhe(i, iArrCopyOf, objArrCopyOf, true);
    }

    static zzhe zzf() {
        return new zzhe(0, new int[8], new Object[8], true);
    }

    private final void zzl(int i) {
        int[] iArr = this.zzc;
        if (i > iArr.length) {
            int i2 = this.zzb;
            int i3 = i2 + (i2 / 2);
            if (i3 >= i) {
                i = i3;
            }
            if (i < 8) {
                i = 8;
            }
            this.zzc = Arrays.copyOf(iArr, i);
            this.zzd = Arrays.copyOf(this.zzd, i);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzhe)) {
            return false;
        }
        zzhe zzheVar = (zzhe) obj;
        int i = this.zzb;
        if (i == zzheVar.zzb) {
            int[] iArr = this.zzc;
            int[] iArr2 = zzheVar.zzc;
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    Object[] objArr = this.zzd;
                    Object[] objArr2 = zzheVar.zzd;
                    int i3 = this.zzb;
                    for (int i4 = 0; i4 < i3; i4++) {
                        if (objArr[i4].equals(objArr2[i4])) {
                        }
                    }
                    return true;
                }
                if (iArr[i2] != iArr2[i2]) {
                    break;
                }
                i2++;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = this.zzb;
        int i2 = i + 527;
        int[] iArr = this.zzc;
        int iHashCode = 17;
        int i3 = 17;
        for (int i4 = 0; i4 < i; i4++) {
            i3 = (i3 * 31) + iArr[i4];
        }
        int i5 = ((i2 * 31) + i3) * 31;
        Object[] objArr = this.zzd;
        int i6 = this.zzb;
        for (int i7 = 0; i7 < i6; i7++) {
            iHashCode = (iHashCode * 31) + objArr[i7].hashCode();
        }
        return i5 + iHashCode;
    }

    public final int zza() {
        int iZzy;
        int iZzx;
        int iZzx2;
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzb; i3++) {
            int i4 = this.zzc[i3];
            int i5 = i4 >>> 3;
            int i6 = i4 & 7;
            if (i6 != 0) {
                if (i6 == 1) {
                    ((Long) this.zzd[i3]).longValue();
                    iZzx2 = zzee.zzx(i5 << 3) + 8;
                } else if (i6 == 2) {
                    int i7 = i5 << 3;
                    zzdw zzdwVar = (zzdw) this.zzd[i3];
                    int i8 = zzee.zzb;
                    int iZzd = zzdwVar.zzd();
                    iZzx2 = zzee.zzx(i7) + zzee.zzx(iZzd) + iZzd;
                } else if (i6 == 3) {
                    int i9 = i5 << 3;
                    int i10 = zzee.zzb;
                    iZzy = ((zzhe) this.zzd[i3]).zza();
                    int iZzx3 = zzee.zzx(i9);
                    iZzx = iZzx3 + iZzx3;
                } else {
                    if (i6 != 5) {
                        throw new IllegalStateException(zzff.zza());
                    }
                    ((Integer) this.zzd[i3]).intValue();
                    iZzx2 = zzee.zzx(i5 << 3) + 4;
                }
                i2 += iZzx2;
            } else {
                int i11 = i5 << 3;
                iZzy = zzee.zzy(((Long) this.zzd[i3]).longValue());
                iZzx = zzee.zzx(i11);
            }
            iZzx2 = iZzx + iZzy;
            i2 += iZzx2;
        }
        this.zze = i2;
        return i2;
    }

    public final int zzb() {
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int iZzx = 0;
        for (int i2 = 0; i2 < this.zzb; i2++) {
            int i3 = this.zzc[i2] >>> 3;
            zzdw zzdwVar = (zzdw) this.zzd[i2];
            int i4 = zzee.zzb;
            int iZzd = zzdwVar.zzd();
            int iZzx2 = zzee.zzx(iZzd) + iZzd;
            int iZzx3 = zzee.zzx(16);
            int iZzx4 = zzee.zzx(i3);
            int iZzx5 = zzee.zzx(8);
            iZzx += iZzx5 + iZzx5 + iZzx3 + iZzx4 + zzee.zzx(24) + iZzx2;
        }
        this.zze = iZzx;
        return iZzx;
    }

    final zzhe zzd(zzhe zzheVar) {
        if (zzheVar.equals(zza)) {
            return this;
        }
        zzg();
        int i = this.zzb + zzheVar.zzb;
        zzl(i);
        System.arraycopy(zzheVar.zzc, 0, this.zzc, this.zzb, zzheVar.zzb);
        System.arraycopy(zzheVar.zzd, 0, this.zzd, this.zzb, zzheVar.zzb);
        this.zzb = i;
        return this;
    }

    final void zzg() {
        if (!this.zzf) {
            throw new UnsupportedOperationException();
        }
    }

    public final void zzh() {
        if (this.zzf) {
            this.zzf = false;
        }
    }

    final void zzi(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.zzb; i2++) {
            zzge.zzb(sb, i, String.valueOf(this.zzc[i2] >>> 3), this.zzd[i2]);
        }
    }

    final void zzj(int i, Object obj) {
        zzg();
        zzl(this.zzb + 1);
        int[] iArr = this.zzc;
        int i2 = this.zzb;
        iArr[i2] = i;
        this.zzd[i2] = obj;
        this.zzb = i2 + 1;
    }

    public final void zzk(zzhv zzhvVar) throws IOException {
        if (this.zzb != 0) {
            for (int i = 0; i < this.zzb; i++) {
                int i2 = this.zzc[i];
                Object obj = this.zzd[i];
                int i3 = i2 & 7;
                int i4 = i2 >>> 3;
                if (i3 == 0) {
                    zzhvVar.zzt(i4, ((Long) obj).longValue());
                } else if (i3 == 1) {
                    zzhvVar.zzm(i4, ((Long) obj).longValue());
                } else if (i3 == 2) {
                    zzhvVar.zzd(i4, (zzdw) obj);
                } else if (i3 == 3) {
                    zzhvVar.zzE(i4);
                    ((zzhe) obj).zzk(zzhvVar);
                    zzhvVar.zzh(i4);
                } else {
                    if (i3 != 5) {
                        throw new RuntimeException(zzff.zza());
                    }
                    zzhvVar.zzk(i4, ((Integer) obj).intValue());
                }
            }
        }
    }
}
