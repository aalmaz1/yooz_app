package com.amazonaws.amplify.amplify_auth_cognito;

import androidx.core.app.FrameMetricsAggregator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NativeAuthPluginBindingsPigeon.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\b\u0086\b\u0018\u0000 -2\u00020\u0001:\u0001-Bq\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\rJ\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0010\u0010!\u001a\u0004\u0018\u00010\u000bHÆ\u0003¢\u0006\u0002\u0010\u0016J\u0010\u0010\"\u001a\u0004\u0018\u00010\u000bHÆ\u0003¢\u0006\u0002\u0010\u0016Jz\u0010#\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u000bHÆ\u0001¢\u0006\u0002\u0010$J\u0013\u0010%\u001a\u00020&2\b\u0010'\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010(\u001a\u00020)HÖ\u0001J\u000e\u0010*\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010+J\t\u0010,\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000fR\u0015\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R\u0015\u0010\f\u001a\u0004\u0018\u00010\u000b¢\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0018\u0010\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u000f¨\u0006."}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/NativeUserContextData;", "", "deviceName", "", "thirdPartyDeviceId", "deviceFingerprint", "applicationName", "applicationVersion", "deviceLanguage", "deviceOsReleaseVersion", "screenHeightPixels", "", "screenWidthPixels", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/Long;)V", "getApplicationName", "()Ljava/lang/String;", "getApplicationVersion", "getDeviceFingerprint", "getDeviceLanguage", "getDeviceName", "getDeviceOsReleaseVersion", "getScreenHeightPixels", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getScreenWidthPixels", "getThirdPartyDeviceId", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/Long;)Lcom/amazonaws/amplify/amplify_auth_cognito/NativeUserContextData;", "equals", "", "other", "hashCode", "", "toList", "", "toString", "Companion", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class NativeUserContextData {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final String applicationName;
    private final String applicationVersion;
    private final String deviceFingerprint;
    private final String deviceLanguage;
    private final String deviceName;
    private final String deviceOsReleaseVersion;
    private final Long screenHeightPixels;
    private final Long screenWidthPixels;
    private final String thirdPartyDeviceId;

    public NativeUserContextData() {
        this(null, null, null, null, null, null, null, null, null, FrameMetricsAggregator.EVERY_DURATION, null);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getDeviceName() {
        return this.deviceName;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getThirdPartyDeviceId() {
        return this.thirdPartyDeviceId;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getDeviceFingerprint() {
        return this.deviceFingerprint;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getApplicationName() {
        return this.applicationName;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getApplicationVersion() {
        return this.applicationVersion;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getDeviceLanguage() {
        return this.deviceLanguage;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final String getDeviceOsReleaseVersion() {
        return this.deviceOsReleaseVersion;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final Long getScreenHeightPixels() {
        return this.screenHeightPixels;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final Long getScreenWidthPixels() {
        return this.screenWidthPixels;
    }

    public final NativeUserContextData copy(String deviceName, String thirdPartyDeviceId, String deviceFingerprint, String applicationName, String applicationVersion, String deviceLanguage, String deviceOsReleaseVersion, Long screenHeightPixels, Long screenWidthPixels) {
        return new NativeUserContextData(deviceName, thirdPartyDeviceId, deviceFingerprint, applicationName, applicationVersion, deviceLanguage, deviceOsReleaseVersion, screenHeightPixels, screenWidthPixels);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NativeUserContextData)) {
            return false;
        }
        NativeUserContextData nativeUserContextData = (NativeUserContextData) other;
        return Intrinsics.areEqual(this.deviceName, nativeUserContextData.deviceName) && Intrinsics.areEqual(this.thirdPartyDeviceId, nativeUserContextData.thirdPartyDeviceId) && Intrinsics.areEqual(this.deviceFingerprint, nativeUserContextData.deviceFingerprint) && Intrinsics.areEqual(this.applicationName, nativeUserContextData.applicationName) && Intrinsics.areEqual(this.applicationVersion, nativeUserContextData.applicationVersion) && Intrinsics.areEqual(this.deviceLanguage, nativeUserContextData.deviceLanguage) && Intrinsics.areEqual(this.deviceOsReleaseVersion, nativeUserContextData.deviceOsReleaseVersion) && Intrinsics.areEqual(this.screenHeightPixels, nativeUserContextData.screenHeightPixels) && Intrinsics.areEqual(this.screenWidthPixels, nativeUserContextData.screenWidthPixels);
    }

    public int hashCode() {
        String str = this.deviceName;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.thirdPartyDeviceId;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.deviceFingerprint;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.applicationName;
        int iHashCode4 = (iHashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.applicationVersion;
        int iHashCode5 = (iHashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.deviceLanguage;
        int iHashCode6 = (iHashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.deviceOsReleaseVersion;
        int iHashCode7 = (iHashCode6 + (str7 == null ? 0 : str7.hashCode())) * 31;
        Long l = this.screenHeightPixels;
        int iHashCode8 = (iHashCode7 + (l == null ? 0 : l.hashCode())) * 31;
        Long l2 = this.screenWidthPixels;
        return iHashCode8 + (l2 != null ? l2.hashCode() : 0);
    }

    public String toString() {
        return "NativeUserContextData(deviceName=" + this.deviceName + ", thirdPartyDeviceId=" + this.thirdPartyDeviceId + ", deviceFingerprint=" + this.deviceFingerprint + ", applicationName=" + this.applicationName + ", applicationVersion=" + this.applicationVersion + ", deviceLanguage=" + this.deviceLanguage + ", deviceOsReleaseVersion=" + this.deviceOsReleaseVersion + ", screenHeightPixels=" + this.screenHeightPixels + ", screenWidthPixels=" + this.screenWidthPixels + ")";
    }

    public NativeUserContextData(String str, String str2, String str3, String str4, String str5, String str6, String str7, Long l, Long l2) {
        this.deviceName = str;
        this.thirdPartyDeviceId = str2;
        this.deviceFingerprint = str3;
        this.applicationName = str4;
        this.applicationVersion = str5;
        this.deviceLanguage = str6;
        this.deviceOsReleaseVersion = str7;
        this.screenHeightPixels = l;
        this.screenWidthPixels = l2;
    }

    public /* synthetic */ NativeUserContextData(String str, String str2, String str3, String str4, String str5, String str6, String str7, Long l, Long l2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : str4, (i & 16) != 0 ? null : str5, (i & 32) != 0 ? null : str6, (i & 64) != 0 ? null : str7, (i & 128) != 0 ? null : l, (i & 256) == 0 ? l2 : null);
    }

    public final String getDeviceName() {
        return this.deviceName;
    }

    public final String getThirdPartyDeviceId() {
        return this.thirdPartyDeviceId;
    }

    public final String getDeviceFingerprint() {
        return this.deviceFingerprint;
    }

    public final String getApplicationName() {
        return this.applicationName;
    }

    public final String getApplicationVersion() {
        return this.applicationVersion;
    }

    public final String getDeviceLanguage() {
        return this.deviceLanguage;
    }

    public final String getDeviceOsReleaseVersion() {
        return this.deviceOsReleaseVersion;
    }

    public final Long getScreenHeightPixels() {
        return this.screenHeightPixels;
    }

    public final Long getScreenWidthPixels() {
        return this.screenWidthPixels;
    }

    /* JADX INFO: compiled from: NativeAuthPluginBindingsPigeon.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006¨\u0006\u0007"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/NativeUserContextData$Companion;", "", "()V", "fromList", "Lcom/amazonaws/amplify/amplify_auth_cognito/NativeUserContextData;", "list", "", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final NativeUserContextData fromList(List<? extends Object> list) {
            Intrinsics.checkNotNullParameter(list, "list");
            String str = (String) list.get(0);
            String str2 = (String) list.get(1);
            String str3 = (String) list.get(2);
            String str4 = (String) list.get(3);
            String str5 = (String) list.get(4);
            String str6 = (String) list.get(5);
            String str7 = (String) list.get(6);
            Object obj = list.get(7);
            Long lValueOf = obj instanceof Integer ? Long.valueOf(((Number) obj).intValue()) : (Long) obj;
            Object obj2 = list.get(8);
            return new NativeUserContextData(str, str2, str3, str4, str5, str6, str7, lValueOf, obj2 instanceof Integer ? Long.valueOf(((Number) obj2).intValue()) : (Long) obj2);
        }
    }

    public final List<Object> toList() {
        return CollectionsKt.listOf(this.deviceName, this.thirdPartyDeviceId, this.deviceFingerprint, this.applicationName, this.applicationVersion, this.deviceLanguage, this.deviceOsReleaseVersion, this.screenHeightPixels, this.screenWidthPixels);
    }
}
