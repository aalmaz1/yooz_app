package com.google.android.gms.internal.p000authapi;

import android.os.RemoteException;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth@@20.6.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zbau extends zbx {
    final /* synthetic */ TaskCompletionSource zba;

    zbau(zbay zbayVar, TaskCompletionSource taskCompletionSource) {
        this.zba = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.p000authapi.zby
    public final void zbb(Status status, BeginSignInResult beginSignInResult) throws RemoteException {
        TaskUtil.setResultOrApiException(status, beginSignInResult, this.zba);
    }
}
