package com.amazonaws.amplify.amplify_auth_cognito;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: AmplifyAuthCognitoPlugin.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B5\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007J\u0006\u0010\u0012\u001a\u00020\u0013R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\t\"\u0004\b\u000f\u0010\u000bR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u000b¨\u0006\u0014"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyDeviceDetailsBuilder;", "", "deviceKey", "", "deviceGroupKey", "deviceSecret", "asfDeviceId", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAsfDeviceId", "()Ljava/lang/String;", "setAsfDeviceId", "(Ljava/lang/String;)V", "getDeviceGroupKey", "setDeviceGroupKey", "getDeviceKey", "setDeviceKey", "getDeviceSecret", "setDeviceSecret", "build", "Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyDeviceDetailsSecret;", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LegacyDeviceDetailsBuilder {
    private String asfDeviceId;
    private String deviceGroupKey;
    private String deviceKey;
    private String deviceSecret;

    public LegacyDeviceDetailsBuilder() {
        this(null, null, null, null, 15, null);
    }

    public LegacyDeviceDetailsBuilder(String str, String str2, String str3, String str4) {
        this.deviceKey = str;
        this.deviceGroupKey = str2;
        this.deviceSecret = str3;
        this.asfDeviceId = str4;
    }

    public /* synthetic */ LegacyDeviceDetailsBuilder(String str, String str2, String str3, String str4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : str4);
    }

    public final String getDeviceKey() {
        return this.deviceKey;
    }

    public final void setDeviceKey(String str) {
        this.deviceKey = str;
    }

    public final String getDeviceGroupKey() {
        return this.deviceGroupKey;
    }

    public final void setDeviceGroupKey(String str) {
        this.deviceGroupKey = str;
    }

    public final String getDeviceSecret() {
        return this.deviceSecret;
    }

    public final void setDeviceSecret(String str) {
        this.deviceSecret = str;
    }

    public final String getAsfDeviceId() {
        return this.asfDeviceId;
    }

    public final void setAsfDeviceId(String str) {
        this.asfDeviceId = str;
    }

    public final LegacyDeviceDetailsSecret build() {
        return new LegacyDeviceDetailsSecret(this.deviceKey, this.deviceGroupKey, this.deviceSecret, this.asfDeviceId);
    }
}
