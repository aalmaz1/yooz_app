package com.google.android.gms.internal.p000authapi;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.common.api.Status;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth@@20.6.0 */
/* JADX INFO: loaded from: classes2.dex */
public final class zbe implements CredentialRequestResult {
    private final Status zba;
    private final Credential zbb;

    public zbe(Status status, Credential credential) {
        this.zba = status;
        this.zbb = credential;
    }

    @Override // com.google.android.gms.auth.api.credentials.CredentialRequestResult
    public final Credential getCredential() {
        return this.zbb;
    }

    @Override // com.google.android.gms.common.api.Result
    public final Status getStatus() {
        return this.zba;
    }
}
