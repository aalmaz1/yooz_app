package com.google.android.gms.internal.p000authapi;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.common.api.Status;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth@@20.6.0 */
/* JADX INFO: loaded from: classes2.dex */
final class zbf extends zbd {
    final /* synthetic */ zbg zba;

    zbf(zbg zbgVar) {
        this.zba = zbgVar;
    }

    @Override // com.google.android.gms.internal.p000authapi.zbd, com.google.android.gms.internal.p000authapi.zbs
    public final void zbb(Status status, Credential credential) {
        this.zba.setResult(new zbe(status, credential));
    }

    @Override // com.google.android.gms.internal.p000authapi.zbd, com.google.android.gms.internal.p000authapi.zbs
    public final void zbc(Status status) {
        this.zba.setResult(new zbe(status, null));
    }
}
