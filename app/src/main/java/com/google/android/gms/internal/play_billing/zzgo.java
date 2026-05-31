package com.google.android.gms.internal.play_billing;

import java.io.IOException;
import java.util.List;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzgo {
    public static final /* synthetic */ int zza = 0;
    private static final Class zzb;
    private static final zzhd zzc;
    private static final zzhd zzd;

    static {
        Class<?> cls;
        Class<?> cls2;
        zzhd zzhdVar = null;
        try {
            cls = Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            cls = null;
        }
        zzb = cls;
        try {
            cls2 = Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused2) {
            cls2 = null;
        }
        if (cls2 != null) {
            try {
                zzhdVar = (zzhd) cls2.getConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (Throwable unused3) {
            }
        }
        zzc = zzhdVar;
        zzd = new zzhf();
    }

    public static void zzA(int i, List list, zzhv zzhvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhvVar.zzz(i, list, z);
    }

    public static void zzB(int i, List list, zzhv zzhvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhvVar.zzB(i, list, z);
    }

    public static void zzC(int i, List list, zzhv zzhvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhvVar.zzD(i, list, z);
    }

    public static void zzD(int i, List list, zzhv zzhvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhvVar.zzI(i, list, z);
    }

    public static void zzE(int i, List list, zzhv zzhvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhvVar.zzK(i, list, z);
    }

    static boolean zzF(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    static int zza(List list) {
        int iZzu;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzey) {
            zzey zzeyVar = (zzey) list;
            iZzu = 0;
            while (i < size) {
                iZzu += zzee.zzu(zzeyVar.zze(i));
                i++;
            }
        } else {
            iZzu = 0;
            while (i < size) {
                iZzu += zzee.zzu(((Integer) list.get(i)).intValue());
                i++;
            }
        }
        return iZzu;
    }

    static int zzb(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzee.zzx(i << 3) + 4);
    }

    static int zzc(List list) {
        return list.size() * 4;
    }

    static int zzd(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzee.zzx(i << 3) + 8);
    }

    static int zze(List list) {
        return list.size() * 8;
    }

    static int zzf(List list) {
        int iZzu;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzey) {
            zzey zzeyVar = (zzey) list;
            iZzu = 0;
            while (i < size) {
                iZzu += zzee.zzu(zzeyVar.zze(i));
                i++;
            }
        } else {
            iZzu = 0;
            while (i < size) {
                iZzu += zzee.zzu(((Integer) list.get(i)).intValue());
                i++;
            }
        }
        return iZzu;
    }

    static int zzg(List list) {
        int iZzy;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfr) {
            zzfr zzfrVar = (zzfr) list;
            iZzy = 0;
            while (i < size) {
                iZzy += zzee.zzy(zzfrVar.zze(i));
                i++;
            }
        } else {
            iZzy = 0;
            while (i < size) {
                iZzy += zzee.zzy(((Long) list.get(i)).longValue());
                i++;
            }
        }
        return iZzy;
    }

    static int zzh(int i, Object obj, zzgm zzgmVar) {
        int i2 = i << 3;
        if (!(obj instanceof zzfi)) {
            return zzee.zzx(i2) + zzee.zzv((zzgc) obj, zzgmVar);
        }
        int i3 = zzee.zzb;
        int iZza = ((zzfi) obj).zza();
        return zzee.zzx(i2) + zzee.zzx(iZza) + iZza;
    }

    static int zzi(List list) {
        int iZzx;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzey) {
            zzey zzeyVar = (zzey) list;
            iZzx = 0;
            while (i < size) {
                int iZze = zzeyVar.zze(i);
                iZzx += zzee.zzx((iZze >> 31) ^ (iZze + iZze));
                i++;
            }
        } else {
            iZzx = 0;
            while (i < size) {
                int iIntValue = ((Integer) list.get(i)).intValue();
                iZzx += zzee.zzx((iIntValue >> 31) ^ (iIntValue + iIntValue));
                i++;
            }
        }
        return iZzx;
    }

    static int zzj(List list) {
        int iZzy;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfr) {
            zzfr zzfrVar = (zzfr) list;
            iZzy = 0;
            while (i < size) {
                long jZze = zzfrVar.zze(i);
                iZzy += zzee.zzy((jZze >> 63) ^ (jZze + jZze));
                i++;
            }
        } else {
            iZzy = 0;
            while (i < size) {
                long jLongValue = ((Long) list.get(i)).longValue();
                iZzy += zzee.zzy((jLongValue >> 63) ^ (jLongValue + jLongValue));
                i++;
            }
        }
        return iZzy;
    }

    static int zzk(List list) {
        int iZzx;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzey) {
            zzey zzeyVar = (zzey) list;
            iZzx = 0;
            while (i < size) {
                iZzx += zzee.zzx(zzeyVar.zze(i));
                i++;
            }
        } else {
            iZzx = 0;
            while (i < size) {
                iZzx += zzee.zzx(((Integer) list.get(i)).intValue());
                i++;
            }
        }
        return iZzx;
    }

    static int zzl(List list) {
        int iZzy;
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzfr) {
            zzfr zzfrVar = (zzfr) list;
            iZzy = 0;
            while (i < size) {
                iZzy += zzee.zzy(zzfrVar.zze(i));
                i++;
            }
        } else {
            iZzy = 0;
            while (i < size) {
                iZzy += zzee.zzy(((Long) list.get(i)).longValue());
                i++;
            }
        }
        return iZzy;
    }

    public static zzhd zzm() {
        return zzc;
    }

    public static zzhd zzn() {
        return zzd;
    }

    static Object zzo(Object obj, int i, int i2, Object obj2, zzhd zzhdVar) {
        if (obj2 == null) {
            obj2 = zzhdVar.zzc(obj);
        }
        zzhdVar.zzf(obj2, i, i2);
        return obj2;
    }

    static void zzp(zzhd zzhdVar, Object obj, Object obj2) {
        zzhdVar.zzh(obj, zzhdVar.zze(zzhdVar.zzd(obj), zzhdVar.zzd(obj2)));
    }

    public static void zzq(Class cls) {
        Class cls2;
        if (!zzex.class.isAssignableFrom(cls) && (cls2 = zzb) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zzr(int i, List list, zzhv zzhvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhvVar.zzc(i, list, z);
    }

    public static void zzs(int i, List list, zzhv zzhvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhvVar.zzg(i, list, z);
    }

    public static void zzt(int i, List list, zzhv zzhvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhvVar.zzj(i, list, z);
    }

    public static void zzu(int i, List list, zzhv zzhvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhvVar.zzl(i, list, z);
    }

    public static void zzv(int i, List list, zzhv zzhvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhvVar.zzn(i, list, z);
    }

    public static void zzw(int i, List list, zzhv zzhvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhvVar.zzp(i, list, z);
    }

    public static void zzx(int i, List list, zzhv zzhvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhvVar.zzs(i, list, z);
    }

    public static void zzy(int i, List list, zzhv zzhvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhvVar.zzu(i, list, z);
    }

    public static void zzz(int i, List list, zzhv zzhvVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhvVar.zzx(i, list, z);
    }
}
