package com.google.android.play.core.appupdate.internal;

import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: compiled from: com.google.android.play:app-update@@2.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzq extends zzn {
    final /* synthetic */ TaskCompletionSource zza;
    final /* synthetic */ zzn zzb;
    final /* synthetic */ zzx zzc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzq(zzx zzxVar, TaskCompletionSource taskCompletionSource, TaskCompletionSource taskCompletionSource2, zzn zznVar) {
        super(taskCompletionSource);
        this.zzc = zzxVar;
        this.zza = taskCompletionSource2;
        this.zzb = zznVar;
    }

    @Override // com.google.android.play.core.appupdate.internal.zzn
    public final void zza() {
        synchronized (this.zzc.zzg) {
            zzx.zzn(this.zzc, this.zza);
            if (this.zzc.zzl.getAndIncrement() > 0) {
                this.zzc.zzc.zzd("Already connected to the service.", new Object[0]);
            }
            zzx.zzp(this.zzc, this.zzb);
        }
    }
}
