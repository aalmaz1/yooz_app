package com.google.android.gms.location;

/* JADX INFO: compiled from: com.google.android.gms:play-services-location@@21.0.1 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzai {
    public static String zza(int i) {
        if (i == 0) {
            return "THROTTLE_BACKGROUND";
        }
        if (i == 1) {
            return "THROTTLE_ALWAYS";
        }
        if (i == 2) {
            return "THROTTLE_NEVER";
        }
        throw new IllegalArgumentException();
    }
}
