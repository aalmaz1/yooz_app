package com.google.android.gms.internal.p000authapi;

import android.app.PendingIntent;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth@@20.6.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zbax extends zbaa {
    final /* synthetic */ TaskCompletionSource zba;

    zbax(zbay zbayVar, TaskCompletionSource taskCompletionSource) {
        this.zba = taskCompletionSource;
    }

    @Override // com.google.android.gms.internal.p000authapi.zbab
    public final void zbb(Status status, PendingIntent pendingIntent) {
        TaskUtil.setResultOrApiException(status, pendingIntent, this.zba);
    }
}
