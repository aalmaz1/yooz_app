package com.google.android.gms.internal.auth;

import com.sun.mail.imap.IMAPStore;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* JADX INFO: loaded from: classes2.dex */
final class zzhi {
    static final boolean zza;
    private static final Unsafe zzb;
    private static final Class zzc;
    private static final boolean zzd;
    private static final zzhh zze;
    private static final boolean zzf;
    private static final boolean zzg;

    /* JADX WARN: Removed duplicated region for block: B:11:0x0038  */
    static {
        /*
            Method dump skipped, instruction units count: 338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.auth.zzhi.<clinit>():void");
    }

    private zzhi() {
    }

    static double zza(Object obj, long j) {
        return zze.zza(obj, j);
    }

    static float zzb(Object obj, long j) {
        return zze.zzb(obj, j);
    }

    static int zzc(Object obj, long j) {
        return zze.zzi(obj, j);
    }

    static long zzd(Object obj, long j) {
        return zze.zzj(obj, j);
    }

    static Object zze(Class cls) {
        try {
            return zzb.allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    static Object zzf(Object obj, long j) {
        return zze.zzl(obj, j);
    }

    static Unsafe zzg() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzhe());
        } catch (Throwable unused) {
            return null;
        }
    }

    static /* bridge */ /* synthetic */ void zzh(Throwable th) {
        Logger.getLogger(zzhi.class.getName()).logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "logMissingMethod", "platform method missing - proto runtime falling back to safer methods: ".concat(th.toString()));
    }

    static /* synthetic */ void zzi(Object obj, long j, boolean z) {
        long j2 = (-4) & j;
        zzhh zzhhVar = zze;
        int iZzi = zzhhVar.zzi(obj, j2);
        int i = ((~((int) j)) & 3) << 3;
        zzhhVar.zzm(obj, j2, ((z ? 1 : 0) << i) | ((~(255 << i)) & iZzi));
    }

    static /* synthetic */ void zzj(Object obj, long j, boolean z) {
        long j2 = (-4) & j;
        zzhh zzhhVar = zze;
        int i = (((int) j) & 3) << 3;
        zzhhVar.zzm(obj, j2, ((z ? 1 : 0) << i) | ((~(255 << i)) & zzhhVar.zzi(obj, j2)));
    }

    static void zzk(Object obj, long j, boolean z) {
        zze.zzc(obj, j, z);
    }

    static void zzl(Object obj, long j, double d) {
        zze.zzd(obj, j, d);
    }

    static void zzm(Object obj, long j, float f) {
        zze.zze(obj, j, f);
    }

    static void zzn(Object obj, long j, int i) {
        zze.zzm(obj, j, i);
    }

    static void zzo(Object obj, long j, long j2) {
        zze.zzn(obj, j, j2);
    }

    static void zzp(Object obj, long j, Object obj2) {
        zze.zzo(obj, j, obj2);
    }

    static /* bridge */ /* synthetic */ boolean zzq(Object obj, long j) {
        return ((byte) ((zze.zzi(obj, (-4) & j) >>> ((int) (((~j) & 3) << 3))) & 255)) != 0;
    }

    static /* bridge */ /* synthetic */ boolean zzr(Object obj, long j) {
        return ((byte) ((zze.zzi(obj, (-4) & j) >>> ((int) ((j & 3) << 3))) & 255)) != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static boolean zzs(Class cls) {
        int i = zzdr.zza;
        try {
            Class cls2 = zzc;
            cls2.getMethod("peekLong", cls, Boolean.TYPE);
            cls2.getMethod("pokeLong", cls, Long.TYPE, Boolean.TYPE);
            cls2.getMethod("pokeInt", cls, Integer.TYPE, Boolean.TYPE);
            cls2.getMethod("peekInt", cls, Boolean.TYPE);
            cls2.getMethod("pokeByte", cls, Byte.TYPE);
            cls2.getMethod("peekByte", cls);
            cls2.getMethod("pokeByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            cls2.getMethod("peekByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    static boolean zzt(Object obj, long j) {
        return zze.zzf(obj, j);
    }

    static boolean zzu() {
        return zzg;
    }

    static boolean zzv() {
        return zzf;
    }

    private static int zzw(Class cls) {
        if (zzg) {
            return zze.zzg(cls);
        }
        return -1;
    }

    private static int zzx(Class cls) {
        if (zzg) {
            return zze.zzh(cls);
        }
        return -1;
    }

    private static Field zzy() {
        int i = zzdr.zza;
        Field fieldZzz = zzz(Buffer.class, "effectiveDirectAddress");
        if (fieldZzz != null) {
            return fieldZzz;
        }
        Field fieldZzz2 = zzz(Buffer.class, IMAPStore.ID_ADDRESS);
        if (fieldZzz2 == null || fieldZzz2.getType() != Long.TYPE) {
            return null;
        }
        return fieldZzz2;
    }

    private static Field zzz(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (Throwable unused) {
            return null;
        }
    }
}
