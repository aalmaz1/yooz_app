package com.amazonaws.amplify.amplify_auth_cognito;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NativeAuthPluginBindingsPigeon.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB5\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003HÆ\u0003J9\u0010\u0011\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\u000e\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0018J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u001b"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyDeviceDetailsSecret;", "", "deviceKey", "", "deviceGroupKey", "devicePassword", "asfDeviceId", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAsfDeviceId", "()Ljava/lang/String;", "getDeviceGroupKey", "getDeviceKey", "getDevicePassword", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toList", "", "toString", "Companion", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class LegacyDeviceDetailsSecret {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String asfDeviceId;
    private final String deviceGroupKey;
    private final String deviceKey;
    private final String devicePassword;

    public LegacyDeviceDetailsSecret() {
        this(null, null, null, null, 15, null);
    }

    public static /* synthetic */ LegacyDeviceDetailsSecret copy$default(LegacyDeviceDetailsSecret legacyDeviceDetailsSecret, String str, String str2, String str3, String str4, int i, Object obj) {
        if ((i & 1) != 0) {
            str = legacyDeviceDetailsSecret.deviceKey;
        }
        if ((i & 2) != 0) {
            str2 = legacyDeviceDetailsSecret.deviceGroupKey;
        }
        if ((i & 4) != 0) {
            str3 = legacyDeviceDetailsSecret.devicePassword;
        }
        if ((i & 8) != 0) {
            str4 = legacyDeviceDetailsSecret.asfDeviceId;
        }
        return legacyDeviceDetailsSecret.copy(str, str2, str3, str4);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getDeviceKey() {
        return this.deviceKey;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getDeviceGroupKey() {
        return this.deviceGroupKey;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getDevicePassword() {
        return this.devicePassword;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getAsfDeviceId() {
        return this.asfDeviceId;
    }

    public final LegacyDeviceDetailsSecret copy(String deviceKey, String deviceGroupKey, String devicePassword, String asfDeviceId) {
        return new LegacyDeviceDetailsSecret(deviceKey, deviceGroupKey, devicePassword, asfDeviceId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LegacyDeviceDetailsSecret)) {
            return false;
        }
        LegacyDeviceDetailsSecret legacyDeviceDetailsSecret = (LegacyDeviceDetailsSecret) other;
        return Intrinsics.areEqual(this.deviceKey, legacyDeviceDetailsSecret.deviceKey) && Intrinsics.areEqual(this.deviceGroupKey, legacyDeviceDetailsSecret.deviceGroupKey) && Intrinsics.areEqual(this.devicePassword, legacyDeviceDetailsSecret.devicePassword) && Intrinsics.areEqual(this.asfDeviceId, legacyDeviceDetailsSecret.asfDeviceId);
    }

    public int hashCode() {
        String str = this.deviceKey;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.deviceGroupKey;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.devicePassword;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.asfDeviceId;
        return iHashCode3 + (str4 != null ? str4.hashCode() : 0);
    }

    public String toString() {
        return "LegacyDeviceDetailsSecret(deviceKey=" + this.deviceKey + ", deviceGroupKey=" + this.deviceGroupKey + ", devicePassword=" + this.devicePassword + ", asfDeviceId=" + this.asfDeviceId + ")";
    }

    public LegacyDeviceDetailsSecret(String str, String str2, String str3, String str4) {
        this.deviceKey = str;
        this.deviceGroupKey = str2;
        this.devicePassword = str3;
        this.asfDeviceId = str4;
    }

    public /* synthetic */ LegacyDeviceDetailsSecret(String str, String str2, String str3, String str4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : str4);
    }

    public final String getDeviceKey() {
        return this.deviceKey;
    }

    public final String getDeviceGroupKey() {
        return this.deviceGroupKey;
    }

    public final String getDevicePassword() {
        return this.devicePassword;
    }

    public final String getAsfDeviceId() {
        return this.asfDeviceId;
    }

    /* JADX INFO: compiled from: NativeAuthPluginBindingsPigeon.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006¨\u0006\u0007"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyDeviceDetailsSecret$Companion;", "", "()V", "fromList", "Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyDeviceDetailsSecret;", "list", "", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final LegacyDeviceDetailsSecret fromList(List<? extends Object> list) {
            Intrinsics.checkNotNullParameter(list, "list");
            return new LegacyDeviceDetailsSecret((String) list.get(0), (String) list.get(1), (String) list.get(2), (String) list.get(3));
        }
    }

    public final List<Object> toList() {
        return CollectionsKt.listOf(this.deviceKey, this.deviceGroupKey, this.devicePassword, this.asfDeviceId);
    }
}
