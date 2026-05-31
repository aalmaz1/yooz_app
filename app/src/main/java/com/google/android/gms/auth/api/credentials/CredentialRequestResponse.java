package com.google.android.gms.auth.api.credentials;

import com.google.android.gms.common.api.Response;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth@@20.6.0 */
/* JADX INFO: loaded from: classes2.dex */
@Deprecated
public class CredentialRequestResponse extends Response<CredentialRequestResult> {
    public Credential getCredential() {
        return getResult().getCredential();
    }
}
