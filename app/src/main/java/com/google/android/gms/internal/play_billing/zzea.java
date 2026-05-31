package com.google.android.gms.internal.play_billing;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public class zzea {
    public static final /* synthetic */ int zza = 0;
    private static volatile int zzb = 100;

    /* synthetic */ zzea(zzdz zzdzVar) {
    }

    public static int zzb(int i) {
        return (i >>> 1) ^ (-(i & 1));
    }

    public static long zzc(long j) {
        return (j >>> 1) ^ (-(1 & j));
    }
}
