package com.google.android.play.core.appupdate;

import android.content.Context;

/* JADX INFO: compiled from: com.google.android.play:app-update@@2.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zzb {
    private static zza zza;

    static synchronized zza zza(Context context) {
        if (zza == null) {
            zzab zzabVar = new zzab(null);
            zzabVar.zzb(new zzi(com.google.android.play.core.appupdate.internal.zzz.zza(context)));
            zza = zzabVar.zza();
        }
        return zza;
    }
}
