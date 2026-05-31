package com.fluttercandies.photo_manager.core.entity;

import android.net.Uri;
import cn.yoozworld.watch.utils.notifi.NotificationManager;
import com.fluttercandies.photo_manager.core.utils.IDBUtils;
import com.fluttercandies.photo_manager.core.utils.MediaStoreUtils;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AssetEntity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b/\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B\u0085\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\t\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\t\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0010\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0014J\t\u0010/\u001a\u00020\u0003HÆ\u0003J\t\u00100\u001a\u00020\tHÆ\u0003J\u0010\u00101\u001a\u0004\u0018\u00010\u0010HÆ\u0003¢\u0006\u0002\u0010\u001fJ\u0010\u00102\u001a\u0004\u0018\u00010\u0010HÆ\u0003¢\u0006\u0002\u0010\u001fJ\u000b\u00103\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u00105\u001a\u00020\u0005HÆ\u0003J\t\u00106\u001a\u00020\u0003HÆ\u0003J\t\u00107\u001a\u00020\u0003HÆ\u0003J\t\u00108\u001a\u00020\tHÆ\u0003J\t\u00109\u001a\u00020\tHÆ\u0003J\t\u0010:\u001a\u00020\tHÆ\u0003J\t\u0010;\u001a\u00020\u0005HÆ\u0003J\t\u0010<\u001a\u00020\u0003HÆ\u0003J¢\u0001\u0010=\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\t2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0005HÆ\u0001¢\u0006\u0002\u0010>J\u0013\u0010?\u001a\u00020@2\b\u0010A\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\u0006\u0010B\u001a\u00020CJ\t\u0010D\u001a\u00020\tHÖ\u0001J\t\u0010E\u001a\u00020\u0005HÖ\u0001R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0018R\u001e\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\"\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001e\u0010\u0011\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\"\u001a\u0004\b#\u0010\u001f\"\u0004\b$\u0010!R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0016R\u0011\u0010\r\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0018R\u0011\u0010\u000e\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u001cR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0016\"\u0004\b)\u0010*R\u0013\u0010+\u001a\u0004\u0018\u00010\u00058F¢\u0006\u0006\u001a\u0004\b,\u0010\u0016R\u0011\u0010\u000b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001cR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\u001c¨\u0006F"}, d2 = {"Lcom/fluttercandies/photo_manager/core/entity/AssetEntity;", "", "id", "", "path", "", "duration", "createDt", "width", "", "height", NotificationManager.BUNDLE_TYPE, "displayName", "modifiedDate", "orientation", "lat", "", "lng", "androidQRelativePath", "mimeType", "(JLjava/lang/String;JJIIILjava/lang/String;JILjava/lang/Double;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/String;)V", "getAndroidQRelativePath", "()Ljava/lang/String;", "getCreateDt", "()J", "getDisplayName", "getDuration", "getHeight", "()I", "getId", "getLat", "()Ljava/lang/Double;", "setLat", "(Ljava/lang/Double;)V", "Ljava/lang/Double;", "getLng", "setLng", "getMimeType", "getModifiedDate", "getOrientation", "getPath", "setPath", "(Ljava/lang/String;)V", "relativePath", "getRelativePath", "getType", "getWidth", "component1", "component10", "component11", "component12", "component13", "component14", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JLjava/lang/String;JJIIILjava/lang/String;JILjava/lang/Double;Ljava/lang/Double;Ljava/lang/String;Ljava/lang/String;)Lcom/fluttercandies/photo_manager/core/entity/AssetEntity;", "equals", "", "other", "getUri", "Landroid/net/Uri;", "hashCode", "toString", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class AssetEntity {
    private final String androidQRelativePath;
    private final long createDt;
    private final String displayName;
    private final long duration;
    private final int height;
    private final long id;
    private Double lat;
    private Double lng;
    private final String mimeType;
    private final long modifiedDate;
    private final int orientation;
    private String path;
    private final int type;
    private final int width;

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final long getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component10, reason: from getter */
    public final int getOrientation() {
        return this.orientation;
    }

    /* JADX INFO: renamed from: component11, reason: from getter */
    public final Double getLat() {
        return this.lat;
    }

    /* JADX INFO: renamed from: component12, reason: from getter */
    public final Double getLng() {
        return this.lng;
    }

    /* JADX INFO: renamed from: component13, reason: from getter */
    public final String getAndroidQRelativePath() {
        return this.androidQRelativePath;
    }

    /* JADX INFO: renamed from: component14, reason: from getter */
    public final String getMimeType() {
        return this.mimeType;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getPath() {
        return this.path;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final long getDuration() {
        return this.duration;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final long getCreateDt() {
        return this.createDt;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final int getWidth() {
        return this.width;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final int getHeight() {
        return this.height;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final int getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final String getDisplayName() {
        return this.displayName;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final long getModifiedDate() {
        return this.modifiedDate;
    }

    public final AssetEntity copy(long id, String path, long duration, long createDt, int width, int height, int type, String displayName, long modifiedDate, int orientation, Double lat, Double lng, String androidQRelativePath, String mimeType) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(displayName, "displayName");
        return new AssetEntity(id, path, duration, createDt, width, height, type, displayName, modifiedDate, orientation, lat, lng, androidQRelativePath, mimeType);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AssetEntity)) {
            return false;
        }
        AssetEntity assetEntity = (AssetEntity) other;
        return this.id == assetEntity.id && Intrinsics.areEqual(this.path, assetEntity.path) && this.duration == assetEntity.duration && this.createDt == assetEntity.createDt && this.width == assetEntity.width && this.height == assetEntity.height && this.type == assetEntity.type && Intrinsics.areEqual(this.displayName, assetEntity.displayName) && this.modifiedDate == assetEntity.modifiedDate && this.orientation == assetEntity.orientation && Intrinsics.areEqual((Object) this.lat, (Object) assetEntity.lat) && Intrinsics.areEqual((Object) this.lng, (Object) assetEntity.lng) && Intrinsics.areEqual(this.androidQRelativePath, assetEntity.androidQRelativePath) && Intrinsics.areEqual(this.mimeType, assetEntity.mimeType);
    }

    public int hashCode() {
        int iHashCode = ((((((((((((((((((Long.hashCode(this.id) * 31) + this.path.hashCode()) * 31) + Long.hashCode(this.duration)) * 31) + Long.hashCode(this.createDt)) * 31) + Integer.hashCode(this.width)) * 31) + Integer.hashCode(this.height)) * 31) + Integer.hashCode(this.type)) * 31) + this.displayName.hashCode()) * 31) + Long.hashCode(this.modifiedDate)) * 31) + Integer.hashCode(this.orientation)) * 31;
        Double d = this.lat;
        int iHashCode2 = (iHashCode + (d == null ? 0 : d.hashCode())) * 31;
        Double d2 = this.lng;
        int iHashCode3 = (iHashCode2 + (d2 == null ? 0 : d2.hashCode())) * 31;
        String str = this.androidQRelativePath;
        int iHashCode4 = (iHashCode3 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.mimeType;
        return iHashCode4 + (str2 != null ? str2.hashCode() : 0);
    }

    public String toString() {
        return "AssetEntity(id=" + this.id + ", path=" + this.path + ", duration=" + this.duration + ", createDt=" + this.createDt + ", width=" + this.width + ", height=" + this.height + ", type=" + this.type + ", displayName=" + this.displayName + ", modifiedDate=" + this.modifiedDate + ", orientation=" + this.orientation + ", lat=" + this.lat + ", lng=" + this.lng + ", androidQRelativePath=" + this.androidQRelativePath + ", mimeType=" + this.mimeType + ")";
    }

    public AssetEntity(long j, String path, long j2, long j3, int i, int i2, int i3, String displayName, long j4, int i4, Double d, Double d2, String str, String str2) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(displayName, "displayName");
        this.id = j;
        this.path = path;
        this.duration = j2;
        this.createDt = j3;
        this.width = i;
        this.height = i2;
        this.type = i3;
        this.displayName = displayName;
        this.modifiedDate = j4;
        this.orientation = i4;
        this.lat = d;
        this.lng = d2;
        this.androidQRelativePath = str;
        this.mimeType = str2;
    }

    public /* synthetic */ AssetEntity(long j, String str, long j2, long j3, int i, int i2, int i3, String str2, long j4, int i4, Double d, Double d2, String str3, String str4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, str, j2, j3, i, i2, i3, str2, j4, i4, (i5 & 1024) != 0 ? null : d, (i5 & 2048) != 0 ? null : d2, (i5 & 4096) != 0 ? null : str3, (i5 & 8192) != 0 ? null : str4);
    }

    public final long getId() {
        return this.id;
    }

    public final String getPath() {
        return this.path;
    }

    public final void setPath(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.path = str;
    }

    public final long getDuration() {
        return this.duration;
    }

    public final long getCreateDt() {
        return this.createDt;
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getType() {
        return this.type;
    }

    public final String getDisplayName() {
        return this.displayName;
    }

    public final long getModifiedDate() {
        return this.modifiedDate;
    }

    public final int getOrientation() {
        return this.orientation;
    }

    public final Double getLat() {
        return this.lat;
    }

    public final void setLat(Double d) {
        this.lat = d;
    }

    public final Double getLng() {
        return this.lng;
    }

    public final void setLng(Double d) {
        this.lng = d;
    }

    public final String getAndroidQRelativePath() {
        return this.androidQRelativePath;
    }

    public final String getMimeType() {
        return this.mimeType;
    }

    public final Uri getUri() {
        return MediaStoreUtils.INSTANCE.getUri(this.id, MediaStoreUtils.INSTANCE.convertTypeToMediaType(this.type));
    }

    public final String getRelativePath() {
        if (IDBUtils.INSTANCE.isAboveAndroidQ()) {
            return this.androidQRelativePath;
        }
        return new File(this.path).getParent();
    }
}
