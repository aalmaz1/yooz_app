package com.google.android.play.core.appupdate.internal;

import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: compiled from: com.google.android.play:app-update@@2.1.0 */
/* JADX INFO: loaded from: classes2.dex */
public abstract class zzn implements Runnable {
    private final TaskCompletionSource zza;

    zzn() {
        this.zza = null;
    }

    public zzn(TaskCompletionSource taskCompletionSource) {
        this.zza = taskCompletionSource;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            zza();
        } catch (Exception e) {
            zzc(e);
        }
    }

    protected abstract void zza();

    final TaskCompletionSource zzb() {
        return this.zza;
    }

    public final void zzc(Exception exc) {
        TaskCompletionSource taskCompletionSource = this.zza;
        if (taskCompletionSource != null) {
            taskCompletionSource.trySetException(exc);
        }
    }
}
