package com.google.android.gms.internal.play_billing;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzck extends zzby {
    private static final Set zza;
    private static final zzbq zzb;
    private static final zzch zzc;
    private final String zzd;
    private final zzbd zze;
    private final Level zzf;
    private final Set zzg;
    private final zzbq zzh;

    static {
        Set setUnmodifiableSet = Collections.unmodifiableSet(new HashSet(Arrays.asList(zzax.zza, zzbc.zza)));
        zza = setUnmodifiableSet;
        zzb = zzbt.zza(setUnmodifiableSet).zzd();
        zzc = new zzch();
    }

    /* synthetic */ zzck(String str, String str2, boolean z, zzbd zzbdVar, Level level, Set set, zzbq zzbqVar, zzcj zzcjVar) {
        super(str2);
        if (str2.length() > 23) {
            int i = -1;
            for (int length = str2.length() - 1; length >= 0; length--) {
                char cCharAt = str2.charAt(length);
                if (cCharAt == '.' || cCharAt == '$') {
                    i = length;
                    break;
                }
            }
            str2 = str2.substring(i + 1);
        }
        String strConcat = "".concat(String.valueOf(str2));
        this.zzd = strConcat.substring(0, Math.min(strConcat.length(), 23));
        this.zze = zzbdVar;
        this.zzf = level;
        this.zzg = set;
        this.zzh = zzbqVar;
    }

    public static zzch zzc() {
        return zzc;
    }
}
