package com.google.android.gms.internal.p000authapi;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth@@20.6.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zbh extends zbm {
    final /* synthetic */ Credential zba;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zbh(zbl zblVar, GoogleApiClient googleApiClient, Credential credential) {
        super(googleApiClient);
        this.zba = credential;
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    protected final /* bridge */ /* synthetic */ Result createFailedResult(Status status) {
        return status;
    }

    @Override // com.google.android.gms.internal.p000authapi.zbm
    protected final void zba(Context context, zbt zbtVar) throws RemoteException {
        zbtVar.zbe(new zbk(this), new zbu(this.zba));
    }
}
