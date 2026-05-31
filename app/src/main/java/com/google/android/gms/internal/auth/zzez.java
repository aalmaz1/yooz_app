package com.google.android.gms.internal.auth;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.apache.commons.lang3.CharEncoding;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzez {
    static final Charset zza = Charset.forName(CharEncoding.US_ASCII);
    static final Charset zzb = Charset.forName("UTF-8");
    static final Charset zzc = Charset.forName(CharEncoding.ISO_8859_1);
    public static final byte[] zzd;
    public static final ByteBuffer zze;
    public static final zzei zzf;

    static {
        byte[] bArr = new byte[0];
        zzd = bArr;
        zze = ByteBuffer.wrap(bArr);
        int i = zzei.zza;
        zzeg zzegVar = new zzeg(bArr, 0, 0, false, null);
        try {
            zzegVar.zza(0);
            zzf = zzegVar;
        } catch (zzfa e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static int zza(boolean z) {
        return z ? 1231 : 1237;
    }

    public static int zzb(byte[] bArr) {
        int length = bArr.length;
        int iZzd = zzd(length, bArr, 0, length);
        if (iZzd == 0) {
            return 1;
        }
        return iZzd;
    }

    public static int zzc(long j) {
        return (int) (j ^ (j >>> 32));
    }

    static int zzd(int i, byte[] bArr, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            i = (i * 31) + bArr[i4];
        }
        return i;
    }

    static Object zze(Object obj) {
        obj.getClass();
        return obj;
    }

    static Object zzf(Object obj, String str) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException(str);
    }

    static Object zzg(Object obj, Object obj2) {
        return ((zzfw) obj).zzd().zzc((zzfw) obj2).zzg();
    }

    public static String zzh(byte[] bArr) {
        return new String(bArr, zzb);
    }

    public static boolean zzi(byte[] bArr) {
        return zzhm.zzc(bArr);
    }
}
