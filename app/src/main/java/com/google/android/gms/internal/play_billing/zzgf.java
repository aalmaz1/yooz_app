package com.google.android.gms.internal.play_billing;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzgf<T> implements zzgm<T> {
    private static final int[] zza = new int[0];
    private static final Unsafe zzb = zzhn.zzg();
    private final int[] zzc;
    private final Object[] zzd;
    private final int zze;
    private final int zzf;
    private final zzgc zzg;
    private final boolean zzh;
    private final int[] zzi;
    private final int zzj;
    private final int zzk;
    private final zzfq zzl;
    private final zzhd zzm;
    private final zzek zzn;
    private final zzgh zzo;
    private final zzfx zzp;

    private zzgf(int[] iArr, Object[] objArr, int i, int i2, zzgc zzgcVar, int i3, boolean z, int[] iArr2, int i4, int i5, zzgh zzghVar, zzfq zzfqVar, zzhd zzhdVar, zzek zzekVar, zzfx zzfxVar) {
        this.zzc = iArr;
        this.zzd = objArr;
        this.zze = i;
        this.zzf = i2;
        boolean z2 = false;
        if (zzekVar != null && zzekVar.zzc(zzgcVar)) {
            z2 = true;
        }
        this.zzh = z2;
        this.zzi = iArr2;
        this.zzj = i4;
        this.zzk = i5;
        this.zzo = zzghVar;
        this.zzl = zzfqVar;
        this.zzm = zzhdVar;
        this.zzn = zzekVar;
        this.zzg = zzgcVar;
        this.zzp = zzfxVar;
    }

    private static void zzA(Object obj) {
        if (!zzL(obj)) {
            throw new IllegalArgumentException("Mutating immutable message: ".concat(String.valueOf(String.valueOf(obj))));
        }
    }

    private final void zzB(Object obj, Object obj2, int i) {
        if (zzI(obj2, i)) {
            int iZzs = zzs(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = iZzs;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i] + " is present but null: " + obj2.toString());
            }
            zzgm zzgmVarZzv = zzv(i);
            if (!zzI(obj, i)) {
                if (zzL(object)) {
                    Object objZze = zzgmVarZzv.zze();
                    zzgmVarZzv.zzg(objZze, object);
                    unsafe.putObject(obj, j, objZze);
                } else {
                    unsafe.putObject(obj, j, object);
                }
                zzD(obj, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzL(object2)) {
                Object objZze2 = zzgmVarZzv.zze();
                zzgmVarZzv.zzg(objZze2, object2);
                unsafe.putObject(obj, j, objZze2);
                object2 = objZze2;
            }
            zzgmVarZzv.zzg(object2, object);
        }
    }

    private final void zzC(Object obj, Object obj2, int i) {
        int i2 = this.zzc[i];
        if (zzM(obj2, i2, i)) {
            int iZzs = zzs(i) & 1048575;
            Unsafe unsafe = zzb;
            long j = iZzs;
            Object object = unsafe.getObject(obj2, j);
            if (object == null) {
                throw new IllegalStateException("Source subfield " + this.zzc[i] + " is present but null: " + obj2.toString());
            }
            zzgm zzgmVarZzv = zzv(i);
            if (!zzM(obj, i2, i)) {
                if (zzL(object)) {
                    Object objZze = zzgmVarZzv.zze();
                    zzgmVarZzv.zzg(objZze, object);
                    unsafe.putObject(obj, j, objZze);
                } else {
                    unsafe.putObject(obj, j, object);
                }
                zzE(obj, i2, i);
                return;
            }
            Object object2 = unsafe.getObject(obj, j);
            if (!zzL(object2)) {
                Object objZze2 = zzgmVarZzv.zze();
                zzgmVarZzv.zzg(objZze2, object2);
                unsafe.putObject(obj, j, objZze2);
                object2 = objZze2;
            }
            zzgmVarZzv.zzg(object2, object);
        }
    }

    private final void zzD(Object obj, int i) {
        int iZzp = zzp(i);
        long j = 1048575 & iZzp;
        if (j == 1048575) {
            return;
        }
        zzhn.zzq(obj, j, (1 << (iZzp >>> 20)) | zzhn.zzc(obj, j));
    }

    private final void zzE(Object obj, int i, int i2) {
        zzhn.zzq(obj, zzp(i2) & 1048575, i);
    }

    private final void zzF(Object obj, int i, Object obj2) {
        zzb.putObject(obj, zzs(i) & 1048575, obj2);
        zzD(obj, i);
    }

    private final void zzG(Object obj, int i, int i2, Object obj2) {
        zzb.putObject(obj, zzs(i2) & 1048575, obj2);
        zzE(obj, i, i2);
    }

    private final boolean zzH(Object obj, Object obj2, int i) {
        return zzI(obj, i) == zzI(obj2, i);
    }

    private final boolean zzI(Object obj, int i) {
        int iZzp = zzp(i);
        long j = iZzp & 1048575;
        if (j != 1048575) {
            return (zzhn.zzc(obj, j) & (1 << (iZzp >>> 20))) != 0;
        }
        int iZzs = zzs(i);
        long j2 = iZzs & 1048575;
        switch (zzr(iZzs)) {
            case 0:
                return Double.doubleToRawLongBits(zzhn.zza(obj, j2)) != 0;
            case 1:
                return Float.floatToRawIntBits(zzhn.zzb(obj, j2)) != 0;
            case 2:
                return zzhn.zzd(obj, j2) != 0;
            case 3:
                return zzhn.zzd(obj, j2) != 0;
            case 4:
                return zzhn.zzc(obj, j2) != 0;
            case 5:
                return zzhn.zzd(obj, j2) != 0;
            case 6:
                return zzhn.zzc(obj, j2) != 0;
            case 7:
                return zzhn.zzw(obj, j2);
            case 8:
                Object objZzf = zzhn.zzf(obj, j2);
                if (objZzf instanceof String) {
                    return !((String) objZzf).isEmpty();
                }
                if (objZzf instanceof zzdw) {
                    return !zzdw.zzb.equals(objZzf);
                }
                throw new IllegalArgumentException();
            case 9:
                return zzhn.zzf(obj, j2) != null;
            case 10:
                return !zzdw.zzb.equals(zzhn.zzf(obj, j2));
            case 11:
                return zzhn.zzc(obj, j2) != 0;
            case 12:
                return zzhn.zzc(obj, j2) != 0;
            case 13:
                return zzhn.zzc(obj, j2) != 0;
            case 14:
                return zzhn.zzd(obj, j2) != 0;
            case 15:
                return zzhn.zzc(obj, j2) != 0;
            case 16:
                return zzhn.zzd(obj, j2) != 0;
            case 17:
                return zzhn.zzf(obj, j2) != null;
            default:
                throw new IllegalArgumentException();
        }
    }

    private final boolean zzJ(Object obj, int i, int i2, int i3, int i4) {
        return i2 == 1048575 ? zzI(obj, i) : (i3 & i4) != 0;
    }

    private static boolean zzK(Object obj, int i, zzgm zzgmVar) {
        return zzgmVar.zzk(zzhn.zzf(obj, i & 1048575));
    }

    private static boolean zzL(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof zzex) {
            return ((zzex) obj).zzt();
        }
        return true;
    }

    private final boolean zzM(Object obj, int i, int i2) {
        return zzhn.zzc(obj, (long) (zzp(i2) & 1048575)) == i;
    }

    private static boolean zzN(Object obj, long j) {
        return ((Boolean) zzhn.zzf(obj, j)).booleanValue();
    }

    private static final void zzO(int i, Object obj, zzhv zzhvVar) throws IOException {
        if (obj instanceof String) {
            zzhvVar.zzF(i, (String) obj);
        } else {
            zzhvVar.zzd(i, (zzdw) obj);
        }
    }

    static zzhe zzd(Object obj) {
        zzex zzexVar = (zzex) obj;
        zzhe zzheVar = zzexVar.zzc;
        if (zzheVar != zzhe.zzc()) {
            return zzheVar;
        }
        zzhe zzheVarZzf = zzhe.zzf();
        zzexVar.zzc = zzheVarZzf;
        return zzheVarZzf;
    }

    /* JADX WARN: Removed duplicated region for block: B:125:0x0265  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0268  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0282  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static com.google.android.gms.internal.play_billing.zzgf zzl(java.lang.Class r33, com.google.android.gms.internal.play_billing.zzfz r34, com.google.android.gms.internal.play_billing.zzgh r35, com.google.android.gms.internal.play_billing.zzfq r36, com.google.android.gms.internal.play_billing.zzhd r37, com.google.android.gms.internal.play_billing.zzek r38, com.google.android.gms.internal.play_billing.zzfx r39) {
        /*
            Method dump skipped, instruction units count: 1030
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.play_billing.zzgf.zzl(java.lang.Class, com.google.android.gms.internal.play_billing.zzfz, com.google.android.gms.internal.play_billing.zzgh, com.google.android.gms.internal.play_billing.zzfq, com.google.android.gms.internal.play_billing.zzhd, com.google.android.gms.internal.play_billing.zzek, com.google.android.gms.internal.play_billing.zzfx):com.google.android.gms.internal.play_billing.zzgf");
    }

    private static double zzm(Object obj, long j) {
        return ((Double) zzhn.zzf(obj, j)).doubleValue();
    }

    private static float zzn(Object obj, long j) {
        return ((Float) zzhn.zzf(obj, j)).floatValue();
    }

    private static int zzo(Object obj, long j) {
        return ((Integer) zzhn.zzf(obj, j)).intValue();
    }

    private final int zzp(int i) {
        return this.zzc[i + 2];
    }

    private final int zzq(int i, int i2) {
        int length = (this.zzc.length / 3) - 1;
        while (i2 <= length) {
            int i3 = (length + i2) >>> 1;
            int i4 = i3 * 3;
            int i5 = this.zzc[i4];
            if (i == i5) {
                return i4;
            }
            if (i < i5) {
                length = i3 - 1;
            } else {
                i2 = i3 + 1;
            }
        }
        return -1;
    }

    private static int zzr(int i) {
        return (i >>> 20) & 255;
    }

    private final int zzs(int i) {
        return this.zzc[i + 1];
    }

    private static long zzt(Object obj, long j) {
        return ((Long) zzhn.zzf(obj, j)).longValue();
    }

    private final zzfb zzu(int i) {
        int i2 = i / 3;
        return (zzfb) this.zzd[i2 + i2 + 1];
    }

    private final zzgm zzv(int i) {
        Object[] objArr = this.zzd;
        int i2 = i / 3;
        int i3 = i2 + i2;
        zzgm zzgmVar = (zzgm) objArr[i3];
        if (zzgmVar != null) {
            return zzgmVar;
        }
        zzgm zzgmVarZzb = zzgk.zza().zzb((Class) objArr[i3 + 1]);
        this.zzd[i3] = zzgmVarZzb;
        return zzgmVarZzb;
    }

    private final Object zzw(int i) {
        int i2 = i / 3;
        return this.zzd[i2 + i2];
    }

    private final Object zzx(Object obj, int i) {
        zzgm zzgmVarZzv = zzv(i);
        int iZzs = zzs(i) & 1048575;
        if (!zzI(obj, i)) {
            return zzgmVarZzv.zze();
        }
        Object object = zzb.getObject(obj, iZzs);
        if (zzL(object)) {
            return object;
        }
        Object objZze = zzgmVarZzv.zze();
        if (object != null) {
            zzgmVarZzv.zzg(objZze, object);
        }
        return objZze;
    }

    private final Object zzy(Object obj, int i, int i2) {
        zzgm zzgmVarZzv = zzv(i2);
        if (!zzM(obj, i, i2)) {
            return zzgmVarZzv.zze();
        }
        Object object = zzb.getObject(obj, zzs(i2) & 1048575);
        if (zzL(object)) {
            return object;
        }
        Object objZze = zzgmVarZzv.zze();
        if (object != null) {
            zzgmVarZzv.zzg(objZze, object);
        }
        return objZze;
    }

    private static Field zzz(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            throw new RuntimeException("Field " + str + " for " + cls.getName() + " not found. Known fields are " + Arrays.toString(declaredFields));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:207:0x0551  */
    /* JADX WARN: Type inference failed for: r0v108, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v109, types: [com.google.android.gms.internal.play_billing.zzfk] */
    /* JADX WARN: Type inference failed for: r0v111, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v113, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v130 */
    /* JADX WARN: Type inference failed for: r0v178, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v248, types: [int] */
    /* JADX WARN: Type inference failed for: r0v256 */
    /* JADX WARN: Type inference failed for: r0v258 */
    /* JADX WARN: Type inference failed for: r0v259 */
    /* JADX WARN: Type inference failed for: r0v260 */
    /* JADX WARN: Type inference failed for: r0v261 */
    /* JADX WARN: Type inference failed for: r0v262 */
    /* JADX WARN: Type inference failed for: r0v263 */
    /* JADX WARN: Type inference failed for: r0v264 */
    /* JADX WARN: Type inference failed for: r0v265 */
    /* JADX WARN: Type inference failed for: r0v266 */
    /* JADX WARN: Type inference failed for: r0v267 */
    /* JADX WARN: Type inference failed for: r0v268 */
    /* JADX WARN: Type inference failed for: r0v269 */
    /* JADX WARN: Type inference failed for: r0v270 */
    /* JADX WARN: Type inference failed for: r0v271 */
    /* JADX WARN: Type inference failed for: r0v272 */
    /* JADX WARN: Type inference failed for: r0v273 */
    /* JADX WARN: Type inference failed for: r0v274 */
    /* JADX WARN: Type inference failed for: r0v275 */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r15v3 */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v110, types: [int] */
    /* JADX WARN: Type inference failed for: r1v113, types: [int] */
    /* JADX WARN: Type inference failed for: r1v149 */
    /* JADX WARN: Type inference failed for: r1v152 */
    /* JADX WARN: Type inference failed for: r1v153 */
    /* JADX WARN: Type inference failed for: r1v155 */
    /* JADX WARN: Type inference failed for: r1v156 */
    /* JADX WARN: Type inference failed for: r1v157 */
    /* JADX WARN: Type inference failed for: r1v70, types: [int] */
    /* JADX WARN: Type inference failed for: r1v72 */
    /* JADX WARN: Type inference failed for: r2v30, types: [int] */
    /* JADX WARN: Type inference failed for: r2v35 */
    /* JADX WARN: Type inference failed for: r2v36, types: [int] */
    /* JADX WARN: Type inference failed for: r2v40, types: [int] */
    /* JADX WARN: Type inference failed for: r2v44, types: [int] */
    /* JADX WARN: Type inference failed for: r2v52 */
    /* JADX WARN: Type inference failed for: r2v53, types: [int] */
    /* JADX WARN: Type inference failed for: r2v90 */
    /* JADX WARN: Type inference failed for: r2v91 */
    /* JADX WARN: Type inference failed for: r2v92 */
    /* JADX WARN: Type inference failed for: r2v93 */
    /* JADX WARN: Type inference failed for: r2v94 */
    /* JADX WARN: Type inference failed for: r3v22 */
    /* JADX WARN: Type inference failed for: r3v23, types: [int] */
    /* JADX WARN: Type inference failed for: r3v25 */
    /* JADX WARN: Type inference failed for: r3v26, types: [int] */
    /* JADX WARN: Type inference failed for: r3v31 */
    /* JADX WARN: Type inference failed for: r3v35, types: [int] */
    /* JADX WARN: Type inference failed for: r3v36 */
    /* JADX WARN: Type inference failed for: r3v42, types: [int] */
    /* JADX WARN: Type inference failed for: r3v47 */
    /* JADX WARN: Type inference failed for: r3v48 */
    /* JADX WARN: Type inference failed for: r3v49 */
    /* JADX WARN: Type inference failed for: r3v50 */
    /* JADX WARN: Type inference failed for: r3v51 */
    /* JADX WARN: Type inference failed for: r3v52 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v16 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v29 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v30, types: [int] */
    /* JADX WARN: Type inference failed for: r4v34 */
    /* JADX WARN: Type inference failed for: r4v35 */
    /* JADX WARN: Type inference failed for: r4v37, types: [int] */
    /* JADX WARN: Type inference failed for: r4v38 */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v58 */
    /* JADX WARN: Type inference failed for: r4v59 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v20 */
    /* JADX WARN: Type inference failed for: r5v3, types: [int] */
    @Override // com.google.android.gms.internal.play_billing.zzgm
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int zza(java.lang.Object r19) {
        /*
            Method dump skipped, instruction units count: 2104
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.play_billing.zzgf.zza(java.lang.Object):int");
    }

    @Override // com.google.android.gms.internal.play_billing.zzgm
    public final int zzb(Object obj) {
        int i;
        long jDoubleToLongBits;
        int iFloatToIntBits;
        int i2;
        int i3 = 0;
        for (int i4 = 0; i4 < this.zzc.length; i4 += 3) {
            int iZzs = zzs(i4);
            int[] iArr = this.zzc;
            int i5 = 1048575 & iZzs;
            int iZzr = zzr(iZzs);
            int i6 = iArr[i4];
            long j = i5;
            int iHashCode = 37;
            switch (iZzr) {
                case 0:
                    i = i3 * 53;
                    jDoubleToLongBits = Double.doubleToLongBits(zzhn.zza(obj, j));
                    byte[] bArr = zzfd.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i + iFloatToIntBits;
                    break;
                case 1:
                    i = i3 * 53;
                    iFloatToIntBits = Float.floatToIntBits(zzhn.zzb(obj, j));
                    i3 = i + iFloatToIntBits;
                    break;
                case 2:
                    i = i3 * 53;
                    jDoubleToLongBits = zzhn.zzd(obj, j);
                    byte[] bArr2 = zzfd.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i + iFloatToIntBits;
                    break;
                case 3:
                    i = i3 * 53;
                    jDoubleToLongBits = zzhn.zzd(obj, j);
                    byte[] bArr3 = zzfd.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i + iFloatToIntBits;
                    break;
                case 4:
                    i = i3 * 53;
                    iFloatToIntBits = zzhn.zzc(obj, j);
                    i3 = i + iFloatToIntBits;
                    break;
                case 5:
                    i = i3 * 53;
                    jDoubleToLongBits = zzhn.zzd(obj, j);
                    byte[] bArr4 = zzfd.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i + iFloatToIntBits;
                    break;
                case 6:
                    i = i3 * 53;
                    iFloatToIntBits = zzhn.zzc(obj, j);
                    i3 = i + iFloatToIntBits;
                    break;
                case 7:
                    i = i3 * 53;
                    iFloatToIntBits = zzfd.zza(zzhn.zzw(obj, j));
                    i3 = i + iFloatToIntBits;
                    break;
                case 8:
                    i = i3 * 53;
                    iFloatToIntBits = ((String) zzhn.zzf(obj, j)).hashCode();
                    i3 = i + iFloatToIntBits;
                    break;
                case 9:
                    i2 = i3 * 53;
                    Object objZzf = zzhn.zzf(obj, j);
                    if (objZzf != null) {
                        iHashCode = objZzf.hashCode();
                    }
                    i3 = i2 + iHashCode;
                    break;
                case 10:
                    i = i3 * 53;
                    iFloatToIntBits = zzhn.zzf(obj, j).hashCode();
                    i3 = i + iFloatToIntBits;
                    break;
                case 11:
                    i = i3 * 53;
                    iFloatToIntBits = zzhn.zzc(obj, j);
                    i3 = i + iFloatToIntBits;
                    break;
                case 12:
                    i = i3 * 53;
                    iFloatToIntBits = zzhn.zzc(obj, j);
                    i3 = i + iFloatToIntBits;
                    break;
                case 13:
                    i = i3 * 53;
                    iFloatToIntBits = zzhn.zzc(obj, j);
                    i3 = i + iFloatToIntBits;
                    break;
                case 14:
                    i = i3 * 53;
                    jDoubleToLongBits = zzhn.zzd(obj, j);
                    byte[] bArr5 = zzfd.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i + iFloatToIntBits;
                    break;
                case 15:
                    i = i3 * 53;
                    iFloatToIntBits = zzhn.zzc(obj, j);
                    i3 = i + iFloatToIntBits;
                    break;
                case 16:
                    i = i3 * 53;
                    jDoubleToLongBits = zzhn.zzd(obj, j);
                    byte[] bArr6 = zzfd.zzd;
                    iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                    i3 = i + iFloatToIntBits;
                    break;
                case 17:
                    i2 = i3 * 53;
                    Object objZzf2 = zzhn.zzf(obj, j);
                    if (objZzf2 != null) {
                        iHashCode = objZzf2.hashCode();
                    }
                    i3 = i2 + iHashCode;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = i3 * 53;
                    iFloatToIntBits = zzhn.zzf(obj, j).hashCode();
                    i3 = i + iFloatToIntBits;
                    break;
                case 50:
                    i = i3 * 53;
                    iFloatToIntBits = zzhn.zzf(obj, j).hashCode();
                    i3 = i + iFloatToIntBits;
                    break;
                case 51:
                    if (zzM(obj, i6, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = Double.doubleToLongBits(zzm(obj, j));
                        byte[] bArr7 = zzfd.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 52:
                    if (zzM(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = Float.floatToIntBits(zzn(obj, j));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 53:
                    if (zzM(obj, i6, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzt(obj, j);
                        byte[] bArr8 = zzfd.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 54:
                    if (zzM(obj, i6, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzt(obj, j);
                        byte[] bArr9 = zzfd.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 55:
                    if (zzM(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzo(obj, j);
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 56:
                    if (zzM(obj, i6, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzt(obj, j);
                        byte[] bArr10 = zzfd.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 57:
                    if (zzM(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzo(obj, j);
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 58:
                    if (zzM(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzfd.zza(zzN(obj, j));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 59:
                    if (zzM(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = ((String) zzhn.zzf(obj, j)).hashCode();
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 60:
                    if (zzM(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzhn.zzf(obj, j).hashCode();
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 61:
                    if (zzM(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzhn.zzf(obj, j).hashCode();
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 62:
                    if (zzM(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzo(obj, j);
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 63:
                    if (zzM(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzo(obj, j);
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 64:
                    if (zzM(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzo(obj, j);
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 65:
                    if (zzM(obj, i6, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzt(obj, j);
                        byte[] bArr11 = zzfd.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 66:
                    if (zzM(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzo(obj, j);
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 67:
                    if (zzM(obj, i6, i4)) {
                        i = i3 * 53;
                        jDoubleToLongBits = zzt(obj, j);
                        byte[] bArr12 = zzfd.zzd;
                        iFloatToIntBits = (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
                        i3 = i + iFloatToIntBits;
                    }
                    break;
                case 68:
                    if (zzM(obj, i6, i4)) {
                        i = i3 * 53;
                        iFloatToIntBits = zzhn.zzf(obj, j).hashCode();
                        i3 = i + iFloatToIntBits;
                    }
                    break;
            }
        }
        int iHashCode2 = (i3 * 53) + this.zzm.zzd(obj).hashCode();
        if (!this.zzh) {
            return iHashCode2;
        }
        this.zzn.zza(obj);
        throw null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:561:0x0cf0, code lost:
    
        if (r6 == 1048575) goto L563;
     */
    /* JADX WARN: Code restructure failed: missing block: B:562:0x0cf2, code lost:
    
        r13.putInt(r7, r6, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:563:0x0cf6, code lost:
    
        r3 = r0.zzj;
     */
    /* JADX WARN: Code restructure failed: missing block: B:565:0x0cfa, code lost:
    
        if (r3 >= r0.zzk) goto L680;
     */
    /* JADX WARN: Code restructure failed: missing block: B:566:0x0cfc, code lost:
    
        r5 = r0.zzi;
        r6 = r0.zzc;
        r5 = r5[r3];
        r6 = r6[r5];
        r6 = com.google.android.gms.internal.play_billing.zzhn.zzf(r7, r0.zzs(r5) & 1048575);
     */
    /* JADX WARN: Code restructure failed: missing block: B:567:0x0d0e, code lost:
    
        if (r6 != null) goto L569;
     */
    /* JADX WARN: Code restructure failed: missing block: B:570:0x0d15, code lost:
    
        if (r0.zzu(r5) != null) goto L681;
     */
    /* JADX WARN: Code restructure failed: missing block: B:571:0x0d17, code lost:
    
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:572:0x0d1a, code lost:
    
        r6 = (com.google.android.gms.internal.play_billing.zzfw) r6;
        r1 = (com.google.android.gms.internal.play_billing.zzfv) r0.zzw(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:573:0x0d22, code lost:
    
        throw null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:574:0x0d23, code lost:
    
        if (r8 != 0) goto L580;
     */
    /* JADX WARN: Code restructure failed: missing block: B:576:0x0d27, code lost:
    
        if (r1 != r37) goto L578;
     */
    /* JADX WARN: Code restructure failed: missing block: B:579:0x0d2e, code lost:
    
        throw com.google.android.gms.internal.play_billing.zzff.zze();
     */
    /* JADX WARN: Code restructure failed: missing block: B:581:0x0d31, code lost:
    
        if (r1 > r37) goto L584;
     */
    /* JADX WARN: Code restructure failed: missing block: B:582:0x0d33, code lost:
    
        if (r4 != r8) goto L584;
     */
    /* JADX WARN: Code restructure failed: missing block: B:583:0x0d35, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:585:0x0d3a, code lost:
    
        throw com.google.android.gms.internal.play_billing.zzff.zze();
     */
    /* JADX WARN: Removed duplicated region for block: B:27:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:449:0x09b8 A[PHI: r0 r7 r8 r9 r10 r11 r13
      0x09b8: PHI (r0v32 com.google.android.gms.internal.play_billing.zzgf<T>) = 
      (r0v1 com.google.android.gms.internal.play_billing.zzgf<T>)
      (r0v1 com.google.android.gms.internal.play_billing.zzgf<T>)
      (r0v1 com.google.android.gms.internal.play_billing.zzgf<T>)
      (r0v1 com.google.android.gms.internal.play_billing.zzgf<T>)
      (r0v8 com.google.android.gms.internal.play_billing.zzgf<T>)
      (r0v31 com.google.android.gms.internal.play_billing.zzgf<T>)
      (r0v1 com.google.android.gms.internal.play_billing.zzgf<T>)
     binds: [B:439:0x0971, B:423:0x0919, B:407:0x08c8, B:332:0x0770, B:283:0x06aa, B:250:0x0609, B:183:0x0489] A[DONT_GENERATE, DONT_INLINE]
      0x09b8: PHI (r7v28 int) = (r7v7 int), (r7v8 int), (r7v9 int), (r7v14 int), (r7v18 int), (r7v23 int), (r7v32 int) binds: [B:439:0x0971, B:423:0x0919, B:407:0x08c8, B:332:0x0770, B:283:0x06aa, B:250:0x0609, B:183:0x0489] A[DONT_GENERATE, DONT_INLINE]
      0x09b8: PHI (r8v73 int) = (r8v52 int), (r8v53 int), (r8v54 int), (r8v56 int), (r8v63 int), (r8v71 int), (r8v76 int) binds: [B:439:0x0971, B:423:0x0919, B:407:0x08c8, B:332:0x0770, B:283:0x06aa, B:250:0x0609, B:183:0x0489] A[DONT_GENERATE, DONT_INLINE]
      0x09b8: PHI (r9v59 int) = (r9v32 int), (r9v33 int), (r9v34 int), (r9v39 int), (r9v46 int), (r9v54 int), (r9v61 int) binds: [B:439:0x0971, B:423:0x0919, B:407:0x08c8, B:332:0x0770, B:283:0x06aa, B:250:0x0609, B:183:0x0489] A[DONT_GENERATE, DONT_INLINE]
      0x09b8: PHI (r10v76 int) = (r10v37 int), (r10v38 int), (r10v39 int), (r10v53 int), (r10v67 int), (r10v74 int), (r10v79 int) binds: [B:439:0x0971, B:423:0x0919, B:407:0x08c8, B:332:0x0770, B:283:0x06aa, B:250:0x0609, B:183:0x0489] A[DONT_GENERATE, DONT_INLINE]
      0x09b8: PHI (r11v34 sun.misc.Unsafe) = 
      (r11v10 sun.misc.Unsafe)
      (r11v11 sun.misc.Unsafe)
      (r11v12 sun.misc.Unsafe)
      (r11v14 sun.misc.Unsafe)
      (r11v22 sun.misc.Unsafe)
      (r11v29 sun.misc.Unsafe)
      (r11v36 sun.misc.Unsafe)
     binds: [B:439:0x0971, B:423:0x0919, B:407:0x08c8, B:332:0x0770, B:283:0x06aa, B:250:0x0609, B:183:0x0489] A[DONT_GENERATE, DONT_INLINE]
      0x09b8: PHI (r13v52 com.google.android.gms.internal.play_billing.zzdj) = 
      (r13v36 com.google.android.gms.internal.play_billing.zzdj)
      (r13v37 com.google.android.gms.internal.play_billing.zzdj)
      (r13v38 com.google.android.gms.internal.play_billing.zzdj)
      (r13v43 com.google.android.gms.internal.play_billing.zzdj)
      (r13v48 com.google.android.gms.internal.play_billing.zzdj)
      (r13v50 com.google.android.gms.internal.play_billing.zzdj)
      (r13v54 com.google.android.gms.internal.play_billing.zzdj)
     binds: [B:439:0x0971, B:423:0x0919, B:407:0x08c8, B:332:0x0770, B:283:0x06aa, B:250:0x0609, B:183:0x0489] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:541:0x0c6a A[PHI: r1 r4 r5 r6 r9 r20
      0x0c6a: PHI (r1v181 int) = 
      (r1v157 int)
      (r1v158 int)
      (r1v159 int)
      (r1v160 int)
      (r1v161 int)
      (r1v162 int)
      (r1v165 int)
      (r1v174 int)
      (r1v182 int)
     binds: [B:539:0x0c53, B:536:0x0c32, B:532:0x0c11, B:529:0x0bf4, B:526:0x0bd7, B:523:0x0bb9, B:521:0x0bac, B:499:0x0b3e, B:466:0x0a29] A[DONT_GENERATE, DONT_INLINE]
      0x0c6a: PHI (r4v89 int) = (r4v60 int), (r4v61 int), (r4v62 int), (r4v63 int), (r4v64 int), (r4v65 int), (r4v68 int), (r4v81 int), (r4v90 int) binds: [B:539:0x0c53, B:536:0x0c32, B:532:0x0c11, B:529:0x0bf4, B:526:0x0bd7, B:523:0x0bb9, B:521:0x0bac, B:499:0x0b3e, B:466:0x0a29] A[DONT_GENERATE, DONT_INLINE]
      0x0c6a: PHI (r5v113 com.google.android.gms.internal.play_billing.zzdj) = 
      (r5v97 com.google.android.gms.internal.play_billing.zzdj)
      (r5v98 com.google.android.gms.internal.play_billing.zzdj)
      (r5v99 com.google.android.gms.internal.play_billing.zzdj)
      (r5v100 com.google.android.gms.internal.play_billing.zzdj)
      (r5v101 com.google.android.gms.internal.play_billing.zzdj)
      (r5v102 com.google.android.gms.internal.play_billing.zzdj)
      (r5v105 com.google.android.gms.internal.play_billing.zzdj)
      (r5v109 com.google.android.gms.internal.play_billing.zzdj)
      (r5v114 com.google.android.gms.internal.play_billing.zzdj)
     binds: [B:539:0x0c53, B:536:0x0c32, B:532:0x0c11, B:529:0x0bf4, B:526:0x0bd7, B:523:0x0bb9, B:521:0x0bac, B:499:0x0b3e, B:466:0x0a29] A[DONT_GENERATE, DONT_INLINE]
      0x0c6a: PHI (r6v98 byte[]) = 
      (r6v78 byte[])
      (r6v79 byte[])
      (r6v80 byte[])
      (r6v81 byte[])
      (r6v82 byte[])
      (r6v83 byte[])
      (r6v86 byte[])
      (r6v91 byte[])
      (r6v99 byte[])
     binds: [B:539:0x0c53, B:536:0x0c32, B:532:0x0c11, B:529:0x0bf4, B:526:0x0bd7, B:523:0x0bb9, B:521:0x0bac, B:499:0x0b3e, B:466:0x0a29] A[DONT_GENERATE, DONT_INLINE]
      0x0c6a: PHI (r9v90 int) = (r9v64 int), (r9v65 int), (r9v66 int), (r9v67 int), (r9v68 int), (r9v69 int), (r9v72 int), (r9v82 int), (r9v91 int) binds: [B:539:0x0c53, B:536:0x0c32, B:532:0x0c11, B:529:0x0bf4, B:526:0x0bd7, B:523:0x0bb9, B:521:0x0bac, B:499:0x0b3e, B:466:0x0a29] A[DONT_GENERATE, DONT_INLINE]
      0x0c6a: PHI (r20v36 int) = 
      (r20v16 int)
      (r20v17 int)
      (r20v18 int)
      (r20v19 int)
      (r20v20 int)
      (r20v21 int)
      (r20v24 int)
      (r20v30 int)
      (r20v37 int)
     binds: [B:539:0x0c53, B:536:0x0c32, B:532:0x0c11, B:529:0x0bf4, B:526:0x0bd7, B:523:0x0bb9, B:521:0x0bac, B:499:0x0b3e, B:466:0x0a29] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:546:0x0c8b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:550:0x0c9a  */
    /* JADX WARN: Removed duplicated region for block: B:557:0x0cc1  */
    /* JADX WARN: Removed duplicated region for block: B:623:0x09bb A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:626:0x0c6d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:629:0x0059 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:667:0x09c9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:669:0x0c84 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0207  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final int zzc(java.lang.Object r34, byte[] r35, int r36, int r37, int r38, com.google.android.gms.internal.play_billing.zzdj r39) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 3532
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.play_billing.zzgf.zzc(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.play_billing.zzdj):int");
    }

    @Override // com.google.android.gms.internal.play_billing.zzgm
    public final Object zze() {
        return ((zzex) this.zzg).zzi();
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x006d  */
    @Override // com.google.android.gms.internal.play_billing.zzgm
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zzf(java.lang.Object r8) {
        /*
            Method dump skipped, instruction units count: 218
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.play_billing.zzgf.zzf(java.lang.Object):void");
    }

    @Override // com.google.android.gms.internal.play_billing.zzgm
    public final void zzg(Object obj, Object obj2) {
        zzA(obj);
        obj2.getClass();
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzs = zzs(i);
            int i2 = 1048575 & iZzs;
            int[] iArr = this.zzc;
            int iZzr = zzr(iZzs);
            int i3 = iArr[i];
            long j = i2;
            switch (iZzr) {
                case 0:
                    if (zzI(obj2, i)) {
                        zzhn.zzo(obj, j, zzhn.zza(obj2, j));
                        zzD(obj, i);
                    }
                    break;
                case 1:
                    if (zzI(obj2, i)) {
                        zzhn.zzp(obj, j, zzhn.zzb(obj2, j));
                        zzD(obj, i);
                    }
                    break;
                case 2:
                    if (zzI(obj2, i)) {
                        zzhn.zzr(obj, j, zzhn.zzd(obj2, j));
                        zzD(obj, i);
                    }
                    break;
                case 3:
                    if (zzI(obj2, i)) {
                        zzhn.zzr(obj, j, zzhn.zzd(obj2, j));
                        zzD(obj, i);
                    }
                    break;
                case 4:
                    if (zzI(obj2, i)) {
                        zzhn.zzq(obj, j, zzhn.zzc(obj2, j));
                        zzD(obj, i);
                    }
                    break;
                case 5:
                    if (zzI(obj2, i)) {
                        zzhn.zzr(obj, j, zzhn.zzd(obj2, j));
                        zzD(obj, i);
                    }
                    break;
                case 6:
                    if (zzI(obj2, i)) {
                        zzhn.zzq(obj, j, zzhn.zzc(obj2, j));
                        zzD(obj, i);
                    }
                    break;
                case 7:
                    if (zzI(obj2, i)) {
                        zzhn.zzm(obj, j, zzhn.zzw(obj2, j));
                        zzD(obj, i);
                    }
                    break;
                case 8:
                    if (zzI(obj2, i)) {
                        zzhn.zzs(obj, j, zzhn.zzf(obj2, j));
                        zzD(obj, i);
                    }
                    break;
                case 9:
                    zzB(obj, obj2, i);
                    break;
                case 10:
                    if (zzI(obj2, i)) {
                        zzhn.zzs(obj, j, zzhn.zzf(obj2, j));
                        zzD(obj, i);
                    }
                    break;
                case 11:
                    if (zzI(obj2, i)) {
                        zzhn.zzq(obj, j, zzhn.zzc(obj2, j));
                        zzD(obj, i);
                    }
                    break;
                case 12:
                    if (zzI(obj2, i)) {
                        zzhn.zzq(obj, j, zzhn.zzc(obj2, j));
                        zzD(obj, i);
                    }
                    break;
                case 13:
                    if (zzI(obj2, i)) {
                        zzhn.zzq(obj, j, zzhn.zzc(obj2, j));
                        zzD(obj, i);
                    }
                    break;
                case 14:
                    if (zzI(obj2, i)) {
                        zzhn.zzr(obj, j, zzhn.zzd(obj2, j));
                        zzD(obj, i);
                    }
                    break;
                case 15:
                    if (zzI(obj2, i)) {
                        zzhn.zzq(obj, j, zzhn.zzc(obj2, j));
                        zzD(obj, i);
                    }
                    break;
                case 16:
                    if (zzI(obj2, i)) {
                        zzhn.zzr(obj, j, zzhn.zzd(obj2, j));
                        zzD(obj, i);
                    }
                    break;
                case 17:
                    zzB(obj, obj2, i);
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    this.zzl.zzb(obj, obj2, j);
                    break;
                case 50:
                    int i4 = zzgo.zza;
                    zzhn.zzs(obj, j, zzfx.zza(zzhn.zzf(obj, j), zzhn.zzf(obj2, j)));
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (zzM(obj2, i3, i)) {
                        zzhn.zzs(obj, j, zzhn.zzf(obj2, j));
                        zzE(obj, i3, i);
                    }
                    break;
                case 60:
                    zzC(obj, obj2, i);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zzM(obj2, i3, i)) {
                        zzhn.zzs(obj, j, zzhn.zzf(obj2, j));
                        zzE(obj, i3, i);
                    }
                    break;
                case 68:
                    zzC(obj, obj2, i);
                    break;
            }
        }
        zzgo.zzp(this.zzm, obj, obj2);
        if (this.zzh) {
            this.zzn.zza(obj2);
            throw null;
        }
    }

    @Override // com.google.android.gms.internal.play_billing.zzgm
    public final void zzh(Object obj, byte[] bArr, int i, int i2, zzdj zzdjVar) throws IOException {
        zzc(obj, bArr, i, i2, 0, zzdjVar);
    }

    @Override // com.google.android.gms.internal.play_billing.zzgm
    public final void zzi(Object obj, zzhv zzhvVar) throws IOException {
        int i;
        int i2;
        int i3;
        if (this.zzh) {
            this.zzn.zza(obj);
            throw null;
        }
        int[] iArr = this.zzc;
        Unsafe unsafe = zzb;
        int i4 = 1048575;
        int i5 = 1048575;
        int i6 = 0;
        int i7 = 0;
        while (i7 < iArr.length) {
            int iZzs = zzs(i7);
            int[] iArr2 = this.zzc;
            int iZzr = zzr(iZzs);
            int i8 = iArr2[i7];
            if (iZzr <= 17) {
                int i9 = iArr2[i7 + 2];
                int i10 = i9 & i4;
                if (i10 != i5) {
                    i6 = i10 == i4 ? 0 : unsafe.getInt(obj, i10);
                    i5 = i10;
                }
                i = i5;
                i2 = i6;
                i3 = 1 << (i9 >>> 20);
            } else {
                i = i5;
                i2 = i6;
                i3 = 0;
            }
            long j = iZzs & i4;
            switch (iZzr) {
                case 0:
                    if (zzJ(obj, i7, i, i2, i3)) {
                        zzhvVar.zzf(i8, zzhn.zza(obj, j));
                    }
                    break;
                case 1:
                    if (zzJ(obj, i7, i, i2, i3)) {
                        zzhvVar.zzo(i8, zzhn.zzb(obj, j));
                    }
                    break;
                case 2:
                    if (zzJ(obj, i7, i, i2, i3)) {
                        zzhvVar.zzt(i8, unsafe.getLong(obj, j));
                    }
                    break;
                case 3:
                    if (zzJ(obj, i7, i, i2, i3)) {
                        zzhvVar.zzJ(i8, unsafe.getLong(obj, j));
                    }
                    break;
                case 4:
                    if (zzJ(obj, i7, i, i2, i3)) {
                        zzhvVar.zzr(i8, unsafe.getInt(obj, j));
                    }
                    break;
                case 5:
                    if (zzJ(obj, i7, i, i2, i3)) {
                        zzhvVar.zzm(i8, unsafe.getLong(obj, j));
                    }
                    break;
                case 6:
                    if (zzJ(obj, i7, i, i2, i3)) {
                        zzhvVar.zzk(i8, unsafe.getInt(obj, j));
                    }
                    break;
                case 7:
                    if (zzJ(obj, i7, i, i2, i3)) {
                        zzhvVar.zzb(i8, zzhn.zzw(obj, j));
                    }
                    break;
                case 8:
                    if (zzJ(obj, i7, i, i2, i3)) {
                        zzO(i8, unsafe.getObject(obj, j), zzhvVar);
                    }
                    break;
                case 9:
                    if (zzJ(obj, i7, i, i2, i3)) {
                        zzhvVar.zzv(i8, unsafe.getObject(obj, j), zzv(i7));
                    }
                    break;
                case 10:
                    if (zzJ(obj, i7, i, i2, i3)) {
                        zzhvVar.zzd(i8, (zzdw) unsafe.getObject(obj, j));
                    }
                    break;
                case 11:
                    if (zzJ(obj, i7, i, i2, i3)) {
                        zzhvVar.zzH(i8, unsafe.getInt(obj, j));
                    }
                    break;
                case 12:
                    if (zzJ(obj, i7, i, i2, i3)) {
                        zzhvVar.zzi(i8, unsafe.getInt(obj, j));
                    }
                    break;
                case 13:
                    if (zzJ(obj, i7, i, i2, i3)) {
                        zzhvVar.zzw(i8, unsafe.getInt(obj, j));
                    }
                    break;
                case 14:
                    if (zzJ(obj, i7, i, i2, i3)) {
                        zzhvVar.zzy(i8, unsafe.getLong(obj, j));
                    }
                    break;
                case 15:
                    if (zzJ(obj, i7, i, i2, i3)) {
                        zzhvVar.zzA(i8, unsafe.getInt(obj, j));
                    }
                    break;
                case 16:
                    if (zzJ(obj, i7, i, i2, i3)) {
                        zzhvVar.zzC(i8, unsafe.getLong(obj, j));
                    }
                    break;
                case 17:
                    if (zzJ(obj, i7, i, i2, i3)) {
                        zzhvVar.zzq(i8, unsafe.getObject(obj, j), zzv(i7));
                    }
                    break;
                case 18:
                    zzgo.zzs(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, false);
                    break;
                case 19:
                    zzgo.zzw(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, false);
                    break;
                case 20:
                    zzgo.zzy(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, false);
                    break;
                case 21:
                    zzgo.zzE(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, false);
                    break;
                case 22:
                    zzgo.zzx(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, false);
                    break;
                case 23:
                    zzgo.zzv(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, false);
                    break;
                case 24:
                    zzgo.zzu(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, false);
                    break;
                case 25:
                    zzgo.zzr(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, false);
                    break;
                case 26:
                    int i11 = this.zzc[i7];
                    List list = (List) unsafe.getObject(obj, j);
                    int i12 = zzgo.zza;
                    if (list != null && !list.isEmpty()) {
                        zzhvVar.zzG(i11, list);
                    }
                    break;
                case 27:
                    int i13 = this.zzc[i7];
                    List list2 = (List) unsafe.getObject(obj, j);
                    zzgm zzgmVarZzv = zzv(i7);
                    int i14 = zzgo.zza;
                    if (list2 != null && !list2.isEmpty()) {
                        for (int i15 = 0; i15 < list2.size(); i15++) {
                            ((zzef) zzhvVar).zzv(i13, list2.get(i15), zzgmVarZzv);
                        }
                    }
                    break;
                case 28:
                    int i16 = this.zzc[i7];
                    List list3 = (List) unsafe.getObject(obj, j);
                    int i17 = zzgo.zza;
                    if (list3 != null && !list3.isEmpty()) {
                        zzhvVar.zze(i16, list3);
                    }
                    break;
                case 29:
                    zzgo.zzD(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, false);
                    break;
                case 30:
                    zzgo.zzt(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, false);
                    break;
                case 31:
                    zzgo.zzz(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, false);
                    break;
                case 32:
                    zzgo.zzA(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, false);
                    break;
                case 33:
                    zzgo.zzB(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, false);
                    break;
                case 34:
                    zzgo.zzC(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, false);
                    break;
                case 35:
                    zzgo.zzs(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, true);
                    break;
                case 36:
                    zzgo.zzw(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, true);
                    break;
                case 37:
                    zzgo.zzy(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, true);
                    break;
                case 38:
                    zzgo.zzE(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, true);
                    break;
                case 39:
                    zzgo.zzx(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, true);
                    break;
                case 40:
                    zzgo.zzv(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, true);
                    break;
                case 41:
                    zzgo.zzu(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, true);
                    break;
                case 42:
                    zzgo.zzr(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, true);
                    break;
                case 43:
                    zzgo.zzD(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, true);
                    break;
                case 44:
                    zzgo.zzt(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, true);
                    break;
                case 45:
                    zzgo.zzz(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, true);
                    break;
                case 46:
                    zzgo.zzA(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, true);
                    break;
                case 47:
                    zzgo.zzB(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, true);
                    break;
                case 48:
                    zzgo.zzC(this.zzc[i7], (List) unsafe.getObject(obj, j), zzhvVar, true);
                    break;
                case 49:
                    int i18 = this.zzc[i7];
                    List list4 = (List) unsafe.getObject(obj, j);
                    zzgm zzgmVarZzv2 = zzv(i7);
                    int i19 = zzgo.zza;
                    if (list4 != null && !list4.isEmpty()) {
                        for (int i20 = 0; i20 < list4.size(); i20++) {
                            ((zzef) zzhvVar).zzq(i18, list4.get(i20), zzgmVarZzv2);
                        }
                    }
                    break;
                case 50:
                    if (unsafe.getObject(obj, j) != null) {
                        throw null;
                    }
                    break;
                    break;
                case 51:
                    if (zzM(obj, i8, i7)) {
                        zzhvVar.zzf(i8, zzm(obj, j));
                    }
                    break;
                case 52:
                    if (zzM(obj, i8, i7)) {
                        zzhvVar.zzo(i8, zzn(obj, j));
                    }
                    break;
                case 53:
                    if (zzM(obj, i8, i7)) {
                        zzhvVar.zzt(i8, zzt(obj, j));
                    }
                    break;
                case 54:
                    if (zzM(obj, i8, i7)) {
                        zzhvVar.zzJ(i8, zzt(obj, j));
                    }
                    break;
                case 55:
                    if (zzM(obj, i8, i7)) {
                        zzhvVar.zzr(i8, zzo(obj, j));
                    }
                    break;
                case 56:
                    if (zzM(obj, i8, i7)) {
                        zzhvVar.zzm(i8, zzt(obj, j));
                    }
                    break;
                case 57:
                    if (zzM(obj, i8, i7)) {
                        zzhvVar.zzk(i8, zzo(obj, j));
                    }
                    break;
                case 58:
                    if (zzM(obj, i8, i7)) {
                        zzhvVar.zzb(i8, zzN(obj, j));
                    }
                    break;
                case 59:
                    if (zzM(obj, i8, i7)) {
                        zzO(i8, unsafe.getObject(obj, j), zzhvVar);
                    }
                    break;
                case 60:
                    if (zzM(obj, i8, i7)) {
                        zzhvVar.zzv(i8, unsafe.getObject(obj, j), zzv(i7));
                    }
                    break;
                case 61:
                    if (zzM(obj, i8, i7)) {
                        zzhvVar.zzd(i8, (zzdw) unsafe.getObject(obj, j));
                    }
                    break;
                case 62:
                    if (zzM(obj, i8, i7)) {
                        zzhvVar.zzH(i8, zzo(obj, j));
                    }
                    break;
                case 63:
                    if (zzM(obj, i8, i7)) {
                        zzhvVar.zzi(i8, zzo(obj, j));
                    }
                    break;
                case 64:
                    if (zzM(obj, i8, i7)) {
                        zzhvVar.zzw(i8, zzo(obj, j));
                    }
                    break;
                case 65:
                    if (zzM(obj, i8, i7)) {
                        zzhvVar.zzy(i8, zzt(obj, j));
                    }
                    break;
                case 66:
                    if (zzM(obj, i8, i7)) {
                        zzhvVar.zzA(i8, zzo(obj, j));
                    }
                    break;
                case 67:
                    if (zzM(obj, i8, i7)) {
                        zzhvVar.zzC(i8, zzt(obj, j));
                    }
                    break;
                case 68:
                    if (zzM(obj, i8, i7)) {
                        zzhvVar.zzq(i8, unsafe.getObject(obj, j), zzv(i7));
                    }
                    break;
            }
            i7 += 3;
            i5 = i;
            i6 = i2;
            i4 = 1048575;
        }
        zzhd zzhdVar = this.zzm;
        zzhdVar.zzi(zzhdVar.zzd(obj), zzhvVar);
    }

    @Override // com.google.android.gms.internal.play_billing.zzgm
    public final boolean zzj(Object obj, Object obj2) {
        boolean zZzF;
        for (int i = 0; i < this.zzc.length; i += 3) {
            int iZzs = zzs(i);
            long j = iZzs & 1048575;
            switch (zzr(iZzs)) {
                case 0:
                    if (!zzH(obj, obj2, i) || Double.doubleToLongBits(zzhn.zza(obj, j)) != Double.doubleToLongBits(zzhn.zza(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 1:
                    if (!zzH(obj, obj2, i) || Float.floatToIntBits(zzhn.zzb(obj, j)) != Float.floatToIntBits(zzhn.zzb(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 2:
                    if (!zzH(obj, obj2, i) || zzhn.zzd(obj, j) != zzhn.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 3:
                    if (!zzH(obj, obj2, i) || zzhn.zzd(obj, j) != zzhn.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 4:
                    if (!zzH(obj, obj2, i) || zzhn.zzc(obj, j) != zzhn.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 5:
                    if (!zzH(obj, obj2, i) || zzhn.zzd(obj, j) != zzhn.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 6:
                    if (!zzH(obj, obj2, i) || zzhn.zzc(obj, j) != zzhn.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 7:
                    if (!zzH(obj, obj2, i) || zzhn.zzw(obj, j) != zzhn.zzw(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 8:
                    if (!zzH(obj, obj2, i) || !zzgo.zzF(zzhn.zzf(obj, j), zzhn.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 9:
                    if (!zzH(obj, obj2, i) || !zzgo.zzF(zzhn.zzf(obj, j), zzhn.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 10:
                    if (!zzH(obj, obj2, i) || !zzgo.zzF(zzhn.zzf(obj, j), zzhn.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 11:
                    if (!zzH(obj, obj2, i) || zzhn.zzc(obj, j) != zzhn.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 12:
                    if (!zzH(obj, obj2, i) || zzhn.zzc(obj, j) != zzhn.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 13:
                    if (!zzH(obj, obj2, i) || zzhn.zzc(obj, j) != zzhn.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 14:
                    if (!zzH(obj, obj2, i) || zzhn.zzd(obj, j) != zzhn.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 15:
                    if (!zzH(obj, obj2, i) || zzhn.zzc(obj, j) != zzhn.zzc(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 16:
                    if (!zzH(obj, obj2, i) || zzhn.zzd(obj, j) != zzhn.zzd(obj2, j)) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 17:
                    if (!zzH(obj, obj2, i) || !zzgo.zzF(zzhn.zzf(obj, j), zzhn.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    zZzF = zzgo.zzF(zzhn.zzf(obj, j), zzhn.zzf(obj2, j));
                    break;
                case 50:
                    zZzF = zzgo.zzF(zzhn.zzf(obj, j), zzhn.zzf(obj2, j));
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                    long jZzp = zzp(i) & 1048575;
                    if (zzhn.zzc(obj, jZzp) != zzhn.zzc(obj2, jZzp) || !zzgo.zzF(zzhn.zzf(obj, j), zzhn.zzf(obj2, j))) {
                        return false;
                    }
                    continue;
                    break;
                    break;
                default:
                    break;
            }
            if (!zZzF) {
                return false;
            }
        }
        if (!this.zzm.zzd(obj).equals(this.zzm.zzd(obj2))) {
            return false;
        }
        if (!this.zzh) {
            return true;
        }
        this.zzn.zza(obj);
        this.zzn.zza(obj2);
        throw null;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x009e  */
    @Override // com.google.android.gms.internal.play_billing.zzgm
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean zzk(java.lang.Object r19) {
        /*
            Method dump skipped, instruction units count: 244
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.play_billing.zzgf.zzk(java.lang.Object):boolean");
    }
}
