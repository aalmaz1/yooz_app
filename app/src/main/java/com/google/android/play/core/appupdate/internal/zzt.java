package com.google.android.play.core.appupdate.internal;

import android.os.IBinder;
import java.util.Iterator;

/* JADX INFO: compiled from: com.google.android.play:app-update@@2.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzt extends zzn {
    final /* synthetic */ IBinder zza;
    final /* synthetic */ zzw zzb;

    zzt(zzw zzwVar, IBinder iBinder) {
        this.zzb = zzwVar;
        this.zza = iBinder;
    }

    @Override // com.google.android.play.core.appupdate.internal.zzn
    public final void zza() {
        this.zzb.zza.zzn = zze.zzb(this.zza);
        zzx.zzq(this.zzb.zza);
        this.zzb.zza.zzh = false;
        Iterator it = this.zzb.zza.zze.iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
        this.zzb.zza.zze.clear();
    }
}
