package com.google.android.gms.internal.auth;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.4 */
/* JADX INFO: loaded from: classes2.dex */
abstract class zzhk {
    zzhk() {
    }

    abstract int zza(int i, byte[] bArr, int i2, int i3);

    final boolean zzb(byte[] bArr, int i, int i2) {
        return zza(0, bArr, i, i2) == 0;
    }
}
