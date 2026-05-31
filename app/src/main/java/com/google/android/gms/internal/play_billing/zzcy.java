package com.google.android.gms.internal.play_billing;

import java.util.Collections;
import java.util.Comparator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.android.billingclient:billing@@6.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzcy {
    private static final Comparator zza = new zzcq();
    private static final Comparator zzb = new zzcr();
    private static final zzcy zzc = new zzcy(new zzcw(Collections.emptyList()));
    private final zzcw zzd;

    private zzcy(zzcw zzcwVar) {
        this.zzd = zzcwVar;
    }

    public static zzcy zza() {
        return zzc;
    }

    public final boolean equals(@NullableDecl Object obj) {
        return (obj instanceof zzcy) && ((zzcy) obj).zzd.equals(this.zzd);
    }

    public final int hashCode() {
        return ~this.zzd.hashCode();
    }

    public final String toString() {
        return this.zzd.toString();
    }
}
