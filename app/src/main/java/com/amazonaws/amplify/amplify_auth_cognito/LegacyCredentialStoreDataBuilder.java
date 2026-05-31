package com.amazonaws.amplify.amplify_auth_cognito;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: AmplifyAuthCognitoPlugin.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001Be\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\fJ\u0006\u0010\"\u001a\u00020#R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001c\u0010\t\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010R\u001e\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0017\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001c\u0010\u000b\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u000e\"\u0004\b\u0019\u0010\u0010R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u000e\"\u0004\b\u001b\u0010\u0010R\u001c\u0010\n\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u000e\"\u0004\b\u001d\u0010\u0010R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u000e\"\u0004\b\u001f\u0010\u0010R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u000e\"\u0004\b!\u0010\u0010¨\u0006$"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyCredentialStoreDataBuilder;", "", "identityId", "", "accessKeyId", "secretAccessKey", "sessionToken", "expirationMsSinceEpoch", "", "accessToken", "refreshToken", "idToken", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAccessKeyId", "()Ljava/lang/String;", "setAccessKeyId", "(Ljava/lang/String;)V", "getAccessToken", "setAccessToken", "getExpirationMsSinceEpoch", "()Ljava/lang/Long;", "setExpirationMsSinceEpoch", "(Ljava/lang/Long;)V", "Ljava/lang/Long;", "getIdToken", "setIdToken", "getIdentityId", "setIdentityId", "getRefreshToken", "setRefreshToken", "getSecretAccessKey", "setSecretAccessKey", "getSessionToken", "setSessionToken", "build", "Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyCredentialStoreData;", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LegacyCredentialStoreDataBuilder {
    private String accessKeyId;
    private String accessToken;
    private Long expirationMsSinceEpoch;
    private String idToken;
    private String identityId;
    private String refreshToken;
    private String secretAccessKey;
    private String sessionToken;

    public LegacyCredentialStoreDataBuilder() {
        this(null, null, null, null, null, null, null, null, 255, null);
    }

    public LegacyCredentialStoreDataBuilder(String str, String str2, String str3, String str4, Long l, String str5, String str6, String str7) {
        this.identityId = str;
        this.accessKeyId = str2;
        this.secretAccessKey = str3;
        this.sessionToken = str4;
        this.expirationMsSinceEpoch = l;
        this.accessToken = str5;
        this.refreshToken = str6;
        this.idToken = str7;
    }

    public /* synthetic */ LegacyCredentialStoreDataBuilder(String str, String str2, String str3, String str4, Long l, String str5, String str6, String str7, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : str4, (i & 16) != 0 ? null : l, (i & 32) != 0 ? null : str5, (i & 64) != 0 ? null : str6, (i & 128) == 0 ? str7 : null);
    }

    public final String getIdentityId() {
        return this.identityId;
    }

    public final void setIdentityId(String str) {
        this.identityId = str;
    }

    public final String getAccessKeyId() {
        return this.accessKeyId;
    }

    public final void setAccessKeyId(String str) {
        this.accessKeyId = str;
    }

    public final String getSecretAccessKey() {
        return this.secretAccessKey;
    }

    public final void setSecretAccessKey(String str) {
        this.secretAccessKey = str;
    }

    public final String getSessionToken() {
        return this.sessionToken;
    }

    public final void setSessionToken(String str) {
        this.sessionToken = str;
    }

    public final Long getExpirationMsSinceEpoch() {
        return this.expirationMsSinceEpoch;
    }

    public final void setExpirationMsSinceEpoch(Long l) {
        this.expirationMsSinceEpoch = l;
    }

    public final String getAccessToken() {
        return this.accessToken;
    }

    public final void setAccessToken(String str) {
        this.accessToken = str;
    }

    public final String getRefreshToken() {
        return this.refreshToken;
    }

    public final void setRefreshToken(String str) {
        this.refreshToken = str;
    }

    public final String getIdToken() {
        return this.idToken;
    }

    public final void setIdToken(String str) {
        this.idToken = str;
    }

    public final LegacyCredentialStoreData build() {
        return new LegacyCredentialStoreData(this.identityId, this.accessKeyId, this.secretAccessKey, this.sessionToken, this.expirationMsSinceEpoch, this.accessToken, this.refreshToken, this.idToken);
    }
}
