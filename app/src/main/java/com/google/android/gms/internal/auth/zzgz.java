package com.google.android.gms.internal.auth;

import java.util.Arrays;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzgz {
    private static final zzgz zza = new zzgz(0, new int[0], new Object[0], false);
    private int zzb;
    private int[] zzc;
    private Object[] zzd;
    private boolean zze;

    private zzgz() {
        this(0, new int[8], new Object[8], true);
    }

    private zzgz(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zzb = i;
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = z;
    }

    public static zzgz zza() {
        return zza;
    }

    static zzgz zzb(zzgz zzgzVar, zzgz zzgzVar2) {
        int i = zzgzVar.zzb + zzgzVar2.zzb;
        int[] iArrCopyOf = Arrays.copyOf(zzgzVar.zzc, i);
        System.arraycopy(zzgzVar2.zzc, 0, iArrCopyOf, zzgzVar.zzb, zzgzVar2.zzb);
        Object[] objArrCopyOf = Arrays.copyOf(zzgzVar.zzd, i);
        System.arraycopy(zzgzVar2.zzd, 0, objArrCopyOf, zzgzVar.zzb, zzgzVar2.zzb);
        return new zzgz(i, iArrCopyOf, objArrCopyOf, true);
    }

    static zzgz zzc() {
        return new zzgz(0, new int[8], new Object[8], true);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzgz)) {
            return false;
        }
        zzgz zzgzVar = (zzgz) obj;
        int i = this.zzb;
        if (i == zzgzVar.zzb) {
            int[] iArr = this.zzc;
            int[] iArr2 = zzgzVar.zzc;
            int i2 = 0;
            while (true) {
                if (i2 >= i) {
                    Object[] objArr = this.zzd;
                    Object[] objArr2 = zzgzVar.zzd;
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
        int i2 = (i + 527) * 31;
        int[] iArr = this.zzc;
        int iHashCode = 17;
        int i3 = 17;
        for (int i4 = 0; i4 < i; i4++) {
            i3 = (i3 * 31) + iArr[i4];
        }
        int i5 = (i2 + i3) * 31;
        Object[] objArr = this.zzd;
        int i6 = this.zzb;
        for (int i7 = 0; i7 < i6; i7++) {
            iHashCode = (iHashCode * 31) + objArr[i7].hashCode();
        }
        return i5 + iHashCode;
    }

    public final void zzd() {
        this.zze = false;
    }

    final void zze(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.zzb; i2++) {
            zzfy.zzb(sb, i, String.valueOf(this.zzc[i2] >>> 3), this.zzd[i2]);
        }
    }

    final void zzf(int i, Object obj) {
        if (!this.zze) {
            throw new UnsupportedOperationException();
        }
        int i2 = this.zzb;
        int[] iArr = this.zzc;
        if (i2 == iArr.length) {
            int i3 = i2 + (i2 < 4 ? 8 : i2 >> 1);
            this.zzc = Arrays.copyOf(iArr, i3);
            this.zzd = Arrays.copyOf(this.zzd, i3);
        }
        int[] iArr2 = this.zzc;
        int i4 = this.zzb;
        iArr2[i4] = i;
        this.zzd[i4] = obj;
        this.zzb = i4 + 1;
    }
}
