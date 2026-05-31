package com.google.android.play.core.appupdate.internal;

/* JADX INFO: compiled from: com.google.android.play:app-update@@2.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzr extends zzn {
    final /* synthetic */ zzx zza;

    zzr(zzx zzxVar) {
        this.zza = zzxVar;
    }

    @Override // com.google.android.play.core.appupdate.internal.zzn
    public final void zza() {
        synchronized (this.zza.zzg) {
            if (this.zza.zzl.get() > 0 && this.zza.zzl.decrementAndGet() > 0) {
                this.zza.zzc.zzd("Leaving the connection open for other ongoing calls.", new Object[0]);
                return;
            }
            zzx zzxVar = this.zza;
            if (zzxVar.zzn != null) {
                zzxVar.zzc.zzd("Unbind from service.", new Object[0]);
                zzx zzxVar2 = this.zza;
                zzxVar2.zzb.unbindService(zzxVar2.zzm);
                this.zza.zzh = false;
                this.zza.zzn = null;
                this.zza.zzm = null;
            }
            this.zza.zzw();
        }
    }
}
