package com.google.android.gms.internal.play_billing;

import java.util.Set;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzci extends zzby {
    private final zzbd zza;
    private final Level zzb;
    private final Set zzc;
    private final zzbq zzd;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzci(String str, @NullableDecl String str2, boolean z, zzbd zzbdVar, boolean z2, boolean z3) {
        super(str2);
        Level level = Level.ALL;
        Set set = zzck.zza;
        zzbq zzbqVar = zzck.zzb;
        this.zza = zzbdVar;
        this.zzb = level;
        this.zzc = set;
        this.zzd = zzbqVar;
    }
}
