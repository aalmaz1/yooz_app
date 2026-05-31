package com.google.android.play.core.appupdate;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.play.core.install.InstallException;

/* JADX INFO: compiled from: com.google.android.play:app-update@@2.1.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zzp extends zzo {
    zzp(zzr zzrVar, TaskCompletionSource taskCompletionSource) {
        super(zzrVar, new com.google.android.play.core.appupdate.internal.zzm("OnCompleteUpdateCallback"), taskCompletionSource);
    }

    @Override // com.google.android.play.core.appupdate.zzo, com.google.android.play.core.appupdate.internal.zzh
    public final void zzb(Bundle bundle) throws RemoteException {
        super.zzb(bundle);
        if (bundle.getInt("error.code", -2) != 0) {
            this.zzb.trySetException(new InstallException(bundle.getInt("error.code", -2)));
        } else {
            this.zzb.trySetResult(null);
        }
    }
}
