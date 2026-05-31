package com.google.android.gms.internal.play_billing;

import java.io.IOException;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzdk {
    static int zza(byte[] bArr, int i, zzdj zzdjVar) throws zzff {
        int iZzh = zzh(bArr, i, zzdjVar);
        int i2 = zzdjVar.zza;
        if (i2 < 0) {
            throw zzff.zzd();
        }
        if (i2 > bArr.length - iZzh) {
            throw zzff.zzg();
        }
        if (i2 == 0) {
            zzdjVar.zzc = zzdw.zzb;
            return iZzh;
        }
        zzdjVar.zzc = zzdw.zzl(bArr, iZzh, i2);
        return iZzh + i2;
    }

    static int zzb(byte[] bArr, int i) {
        int i2 = bArr[i] & UByte.MAX_VALUE;
        int i3 = bArr[i + 1] & UByte.MAX_VALUE;
        int i4 = bArr[i + 2] & UByte.MAX_VALUE;
        return ((bArr[i + 3] & UByte.MAX_VALUE) << 24) | (i3 << 8) | i2 | (i4 << 16);
    }

    static int zzc(zzgm zzgmVar, byte[] bArr, int i, int i2, int i3, zzdj zzdjVar) throws IOException {
        Object objZze = zzgmVar.zze();
        int iZzl = zzl(objZze, zzgmVar, bArr, i, i2, i3, zzdjVar);
        zzgmVar.zzf(objZze);
        zzdjVar.zzc = objZze;
        return iZzl;
    }

    static int zzd(zzgm zzgmVar, byte[] bArr, int i, int i2, zzdj zzdjVar) throws IOException {
        Object objZze = zzgmVar.zze();
        int iZzm = zzm(objZze, zzgmVar, bArr, i, i2, zzdjVar);
        zzgmVar.zzf(objZze);
        zzdjVar.zzc = objZze;
        return iZzm;
    }

    static int zze(zzgm zzgmVar, int i, byte[] bArr, int i2, int i3, zzfc zzfcVar, zzdj zzdjVar) throws IOException {
        int iZzd = zzd(zzgmVar, bArr, i2, i3, zzdjVar);
        zzfcVar.add(zzdjVar.zzc);
        while (iZzd < i3) {
            int iZzh = zzh(bArr, iZzd, zzdjVar);
            if (i != zzdjVar.zza) {
                break;
            }
            iZzd = zzd(zzgmVar, bArr, iZzh, i3, zzdjVar);
            zzfcVar.add(zzdjVar.zzc);
        }
        return iZzd;
    }

    static int zzf(byte[] bArr, int i, zzfc zzfcVar, zzdj zzdjVar) throws IOException {
        zzey zzeyVar = (zzey) zzfcVar;
        int iZzh = zzh(bArr, i, zzdjVar);
        int i2 = zzdjVar.zza + iZzh;
        while (iZzh < i2) {
            iZzh = zzh(bArr, iZzh, zzdjVar);
            zzeyVar.zzf(zzdjVar.zza);
        }
        if (iZzh == i2) {
            return iZzh;
        }
        throw zzff.zzg();
    }

    static int zzg(int i, byte[] bArr, int i2, int i3, zzhe zzheVar, zzdj zzdjVar) throws zzff {
        if ((i >>> 3) == 0) {
            throw zzff.zzb();
        }
        int i4 = i & 7;
        if (i4 == 0) {
            int iZzk = zzk(bArr, i2, zzdjVar);
            zzheVar.zzj(i, Long.valueOf(zzdjVar.zzb));
            return iZzk;
        }
        if (i4 == 1) {
            zzheVar.zzj(i, Long.valueOf(zzn(bArr, i2)));
            return i2 + 8;
        }
        if (i4 == 2) {
            int iZzh = zzh(bArr, i2, zzdjVar);
            int i5 = zzdjVar.zza;
            if (i5 < 0) {
                throw zzff.zzd();
            }
            if (i5 > bArr.length - iZzh) {
                throw zzff.zzg();
            }
            if (i5 == 0) {
                zzheVar.zzj(i, zzdw.zzb);
            } else {
                zzheVar.zzj(i, zzdw.zzl(bArr, iZzh, i5));
            }
            return iZzh + i5;
        }
        if (i4 != 3) {
            if (i4 != 5) {
                throw zzff.zzb();
            }
            zzheVar.zzj(i, Integer.valueOf(zzb(bArr, i2)));
            return i2 + 4;
        }
        int i6 = (i & (-8)) | 4;
        zzhe zzheVarZzf = zzhe.zzf();
        int i7 = 0;
        while (true) {
            if (i2 >= i3) {
                break;
            }
            int iZzh2 = zzh(bArr, i2, zzdjVar);
            int i8 = zzdjVar.zza;
            i7 = i8;
            if (i8 == i6) {
                i2 = iZzh2;
                break;
            }
            int iZzg = zzg(i7, bArr, iZzh2, i3, zzheVarZzf, zzdjVar);
            i7 = i8;
            i2 = iZzg;
        }
        if (i2 > i3 || i7 != i6) {
            throw zzff.zze();
        }
        zzheVar.zzj(i, zzheVarZzf);
        return i2;
    }

    static int zzh(byte[] bArr, int i, zzdj zzdjVar) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return zzi(b, bArr, i2, zzdjVar);
        }
        zzdjVar.zza = b;
        return i2;
    }

    static int zzi(int i, byte[] bArr, int i2, zzdj zzdjVar) {
        byte b = bArr[i2];
        int i3 = i2 + 1;
        int i4 = i & 127;
        if (b >= 0) {
            zzdjVar.zza = i4 | (b << 7);
            return i3;
        }
        int i5 = i4 | ((b & ByteCompanionObject.MAX_VALUE) << 7);
        int i6 = i3 + 1;
        byte b2 = bArr[i3];
        if (b2 >= 0) {
            zzdjVar.zza = i5 | (b2 << 14);
            return i6;
        }
        int i7 = i5 | ((b2 & ByteCompanionObject.MAX_VALUE) << 14);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzdjVar.zza = i7 | (b3 << 21);
            return i8;
        }
        int i9 = i7 | ((b3 & ByteCompanionObject.MAX_VALUE) << 21);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzdjVar.zza = i9 | (b4 << 28);
            return i10;
        }
        int i11 = i9 | ((b4 & ByteCompanionObject.MAX_VALUE) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzdjVar.zza = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    static int zzj(int i, byte[] bArr, int i2, int i3, zzfc zzfcVar, zzdj zzdjVar) {
        zzey zzeyVar = (zzey) zzfcVar;
        int iZzh = zzh(bArr, i2, zzdjVar);
        zzeyVar.zzf(zzdjVar.zza);
        while (iZzh < i3) {
            int iZzh2 = zzh(bArr, iZzh, zzdjVar);
            if (i != zzdjVar.zza) {
                break;
            }
            iZzh = zzh(bArr, iZzh2, zzdjVar);
            zzeyVar.zzf(zzdjVar.zza);
        }
        return iZzh;
    }

    static int zzk(byte[] bArr, int i, zzdj zzdjVar) {
        long j = bArr[i];
        int i2 = i + 1;
        if (j >= 0) {
            zzdjVar.zzb = j;
            return i2;
        }
        int i3 = i2 + 1;
        byte b = bArr[i2];
        long j2 = (j & 127) | (((long) (b & ByteCompanionObject.MAX_VALUE)) << 7);
        int i4 = 7;
        while (b < 0) {
            int i5 = i3 + 1;
            byte b2 = bArr[i3];
            i4 += 7;
            j2 |= ((long) (b2 & ByteCompanionObject.MAX_VALUE)) << i4;
            i3 = i5;
            b = b2;
        }
        zzdjVar.zzb = j2;
        return i3;
    }

    static int zzl(Object obj, zzgm zzgmVar, byte[] bArr, int i, int i2, int i3, zzdj zzdjVar) throws IOException {
        int iZzc = ((zzgf) zzgmVar).zzc(obj, bArr, i, i2, i3, zzdjVar);
        zzdjVar.zzc = obj;
        return iZzc;
    }

    static int zzm(Object obj, zzgm zzgmVar, byte[] bArr, int i, int i2, zzdj zzdjVar) throws IOException {
        int iZzi = i + 1;
        int i3 = bArr[i];
        if (i3 < 0) {
            iZzi = zzi(i3, bArr, iZzi, zzdjVar);
            i3 = zzdjVar.zza;
        }
        int i4 = iZzi;
        if (i3 < 0 || i3 > i2 - i4) {
            throw zzff.zzg();
        }
        int i5 = i3 + i4;
        zzgmVar.zzh(obj, bArr, i4, i5, zzdjVar);
        zzdjVar.zzc = obj;
        return i5;
    }

    static long zzn(byte[] bArr, int i) {
        return (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48) | ((((long) bArr[i + 7]) & 255) << 56);
    }
}
