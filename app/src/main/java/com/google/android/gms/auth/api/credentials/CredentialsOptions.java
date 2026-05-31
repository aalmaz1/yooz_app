package com.google.android.gms.auth.api.credentials;

import com.google.android.gms.auth.api.Auth;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth@@20.6.0 */
/* JADX INFO: loaded from: classes2.dex */
@Deprecated
public final class CredentialsOptions extends Auth.AuthCredentialsOptions {
    public static final CredentialsOptions DEFAULT = new Builder().build();

    /* JADX INFO: compiled from: com.google.android.gms:play-services-auth@@20.6.0 */
    public static final class Builder extends Auth.AuthCredentialsOptions.Builder {
        public CredentialsOptions build() {
            return new CredentialsOptions(this, null);
        }

        @Override // com.google.android.gms.auth.api.Auth.AuthCredentialsOptions.Builder
        public final /* bridge */ /* synthetic */ Auth.AuthCredentialsOptions.Builder forceEnableSaveDialog() {
            forceEnableSaveDialog();
            return this;
        }

        @Override // com.google.android.gms.auth.api.Auth.AuthCredentialsOptions.Builder
        public Builder forceEnableSaveDialog() {
            this.zba = true;
            return this;
        }
    }

    /* synthetic */ CredentialsOptions(Builder builder, zbd zbdVar) {
        super(builder);
    }
}
