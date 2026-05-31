package com.amazonaws.amplify.amplify_auth_cognito;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NativeAuthPluginBindingsPigeon.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\b\u0086\b\u0018\u0000 *2\u00020\u0001:\u0001*Be\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\fJ\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0010\u0010\u001c\u001a\u0004\u0018\u00010\bHÆ\u0003¢\u0006\u0002\u0010\u0011J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0003HÆ\u0003Jn\u0010 \u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010!J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010%\u001a\u00020&HÖ\u0001J\u000e\u0010'\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010(J\t\u0010)\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0015\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000eR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000eR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000e¨\u0006+"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyCredentialStoreData;", "", "identityId", "", "accessKeyId", "secretAccessKey", "sessionToken", "expirationMsSinceEpoch", "", "accessToken", "refreshToken", "idToken", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAccessKeyId", "()Ljava/lang/String;", "getAccessToken", "getExpirationMsSinceEpoch", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getIdToken", "getIdentityId", "getRefreshToken", "getSecretAccessKey", "getSessionToken", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyCredentialStoreData;", "equals", "", "other", "hashCode", "", "toList", "", "toString", "Companion", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class LegacyCredentialStoreData {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String accessKeyId;
    private final String accessToken;
    private final Long expirationMsSinceEpoch;
    private final String idToken;
    private final String identityId;
    private final String refreshToken;
    private final String secretAccessKey;
    private final String sessionToken;

    public LegacyCredentialStoreData() {
        this(null, null, null, null, null, null, null, null, 255, null);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getIdentityId() {
        return this.identityId;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getAccessKeyId() {
        return this.accessKeyId;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getSecretAccessKey() {
        return this.secretAccessKey;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getSessionToken() {
        return this.sessionToken;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final Long getExpirationMsSinceEpoch() {
        return this.expirationMsSinceEpoch;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getAccessToken() {
        return this.accessToken;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final String getRefreshToken() {
        return this.refreshToken;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final String getIdToken() {
        return this.idToken;
    }

    public final LegacyCredentialStoreData copy(String identityId, String accessKeyId, String secretAccessKey, String sessionToken, Long expirationMsSinceEpoch, String accessToken, String refreshToken, String idToken) {
        return new LegacyCredentialStoreData(identityId, accessKeyId, secretAccessKey, sessionToken, expirationMsSinceEpoch, accessToken, refreshToken, idToken);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LegacyCredentialStoreData)) {
            return false;
        }
        LegacyCredentialStoreData legacyCredentialStoreData = (LegacyCredentialStoreData) other;
        return Intrinsics.areEqual(this.identityId, legacyCredentialStoreData.identityId) && Intrinsics.areEqual(this.accessKeyId, legacyCredentialStoreData.accessKeyId) && Intrinsics.areEqual(this.secretAccessKey, legacyCredentialStoreData.secretAccessKey) && Intrinsics.areEqual(this.sessionToken, legacyCredentialStoreData.sessionToken) && Intrinsics.areEqual(this.expirationMsSinceEpoch, legacyCredentialStoreData.expirationMsSinceEpoch) && Intrinsics.areEqual(this.accessToken, legacyCredentialStoreData.accessToken) && Intrinsics.areEqual(this.refreshToken, legacyCredentialStoreData.refreshToken) && Intrinsics.areEqual(this.idToken, legacyCredentialStoreData.idToken);
    }

    public int hashCode() {
        String str = this.identityId;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.accessKeyId;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.secretAccessKey;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.sessionToken;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        Long l = this.expirationMsSinceEpoch;
        int iHashCode5 = (iHashCode4 + (l == null ? 0 : l.hashCode())) * 31;
        String str5 = this.accessToken;
        int iHashCode6 = (iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.refreshToken;
        int iHashCode7 = (iHashCode6 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.idToken;
        return iHashCode7 + (str7 != null ? str7.hashCode() : 0);
    }

    public String toString() {
        return "LegacyCredentialStoreData(identityId=" + this.identityId + ", accessKeyId=" + this.accessKeyId + ", secretAccessKey=" + this.secretAccessKey + ", sessionToken=" + this.sessionToken + ", expirationMsSinceEpoch=" + this.expirationMsSinceEpoch + ", accessToken=" + this.accessToken + ", refreshToken=" + this.refreshToken + ", idToken=" + this.idToken + ")";
    }

    public LegacyCredentialStoreData(String str, String str2, String str3, String str4, Long l, String str5, String str6, String str7) {
        this.identityId = str;
        this.accessKeyId = str2;
        this.secretAccessKey = str3;
        this.sessionToken = str4;
        this.expirationMsSinceEpoch = l;
        this.accessToken = str5;
        this.refreshToken = str6;
        this.idToken = str7;
    }

    public /* synthetic */ LegacyCredentialStoreData(String str, String str2, String str3, String str4, Long l, String str5, String str6, String str7, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : str4, (i & 16) != 0 ? null : l, (i & 32) != 0 ? null : str5, (i & 64) != 0 ? null : str6, (i & 128) == 0 ? str7 : null);
    }

    public final String getIdentityId() {
        return this.identityId;
    }

    public final String getAccessKeyId() {
        return this.accessKeyId;
    }

    public final String getSecretAccessKey() {
        return this.secretAccessKey;
    }

    public final String getSessionToken() {
        return this.sessionToken;
    }

    public final Long getExpirationMsSinceEpoch() {
        return this.expirationMsSinceEpoch;
    }

    public final String getAccessToken() {
        return this.accessToken;
    }

    public final String getRefreshToken() {
        return this.refreshToken;
    }

    public final String getIdToken() {
        return this.idToken;
    }

    /* JADX INFO: compiled from: NativeAuthPluginBindingsPigeon.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006¨\u0006\u0007"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyCredentialStoreData$Companion;", "", "()V", "fromList", "Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyCredentialStoreData;", "list", "", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final LegacyCredentialStoreData fromList(List<? extends Object> list) {
            Intrinsics.checkNotNullParameter(list, "list");
            String str = (String) list.get(0);
            String str2 = (String) list.get(1);
            String str3 = (String) list.get(2);
            String str4 = (String) list.get(3);
            Object obj = list.get(4);
            return new LegacyCredentialStoreData(str, str2, str3, str4, obj instanceof Integer ? Long.valueOf(((Number) obj).intValue()) : (Long) obj, (String) list.get(5), (String) list.get(6), (String) list.get(7));
        }
    }

    public final List<Object> toList() {
        return CollectionsKt.listOf(this.identityId, this.accessKeyId, this.secretAccessKey, this.sessionToken, this.expirationMsSinceEpoch, this.accessToken, this.refreshToken, this.idToken);
    }
}
