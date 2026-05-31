package com.fluttercandies.photo_manager.core.entity;

import com.fluttercandies.photo_manager.constant.Methods;
import com.fluttercandies.photo_manager.core.PhotoManager;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AssetPathEntity.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u001e\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\t\u0010\u001d\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0006HÆ\u0003J\t\u0010 \u001a\u00020\u0006HÆ\u0003J\t\u0010!\u001a\u00020\tHÆ\u0003J\u0010\u0010\"\u001a\u0004\u0018\u00010\u000bHÆ\u0003¢\u0006\u0002\u0010\u0017JL\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000bHÆ\u0001¢\u0006\u0002\u0010$J\u0013\u0010%\u001a\u00020\t2\b\u0010&\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010'\u001a\u00020\u0006HÖ\u0001J\t\u0010(\u001a\u00020\u0003HÖ\u0001R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001e\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u001a\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0012R\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u000e¨\u0006)"}, d2 = {"Lcom/fluttercandies/photo_manager/core/entity/AssetPathEntity;", "", "id", "", "name", "assetCount", "", "typeInt", PhotoManager.ALL_ID, "", "modifiedDate", "", "(Ljava/lang/String;Ljava/lang/String;IIZLjava/lang/Long;)V", Methods.getAssetCount, "()I", "setAssetCount", "(I)V", "getId", "()Ljava/lang/String;", "()Z", "setAll", "(Z)V", "getModifiedDate", "()Ljava/lang/Long;", "setModifiedDate", "(Ljava/lang/Long;)V", "Ljava/lang/Long;", "getName", "getTypeInt", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "(Ljava/lang/String;Ljava/lang/String;IIZLjava/lang/Long;)Lcom/fluttercandies/photo_manager/core/entity/AssetPathEntity;", "equals", "other", "hashCode", "toString", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class AssetPathEntity {
    private int assetCount;
    private final String id;
    private boolean isAll;
    private Long modifiedDate;
    private final String name;
    private final int typeInt;

    public static /* synthetic */ AssetPathEntity copy$default(AssetPathEntity assetPathEntity, String str, String str2, int i, int i2, boolean z, Long l, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = assetPathEntity.id;
        }
        if ((i3 & 2) != 0) {
            str2 = assetPathEntity.name;
        }
        String str3 = str2;
        if ((i3 & 4) != 0) {
            i = assetPathEntity.assetCount;
        }
        int i4 = i;
        if ((i3 & 8) != 0) {
            i2 = assetPathEntity.typeInt;
        }
        int i5 = i2;
        if ((i3 & 16) != 0) {
            z = assetPathEntity.isAll;
        }
        boolean z2 = z;
        if ((i3 & 32) != 0) {
            l = assetPathEntity.modifiedDate;
        }
        return assetPathEntity.copy(str, str3, i4, i5, z2, l);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getAssetCount() {
        return this.assetCount;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getTypeInt() {
        return this.typeInt;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final boolean getIsAll() {
        return this.isAll;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final Long getModifiedDate() {
        return this.modifiedDate;
    }

    public final AssetPathEntity copy(String id, String name, int assetCount, int typeInt, boolean isAll, Long modifiedDate) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        return new AssetPathEntity(id, name, assetCount, typeInt, isAll, modifiedDate);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AssetPathEntity)) {
            return false;
        }
        AssetPathEntity assetPathEntity = (AssetPathEntity) other;
        return Intrinsics.areEqual(this.id, assetPathEntity.id) && Intrinsics.areEqual(this.name, assetPathEntity.name) && this.assetCount == assetPathEntity.assetCount && this.typeInt == assetPathEntity.typeInt && this.isAll == assetPathEntity.isAll && Intrinsics.areEqual(this.modifiedDate, assetPathEntity.modifiedDate);
    }

    public int hashCode() {
        int iHashCode = ((((((((this.id.hashCode() * 31) + this.name.hashCode()) * 31) + Integer.hashCode(this.assetCount)) * 31) + Integer.hashCode(this.typeInt)) * 31) + Boolean.hashCode(this.isAll)) * 31;
        Long l = this.modifiedDate;
        return iHashCode + (l == null ? 0 : l.hashCode());
    }

    public String toString() {
        return "AssetPathEntity(id=" + this.id + ", name=" + this.name + ", assetCount=" + this.assetCount + ", typeInt=" + this.typeInt + ", isAll=" + this.isAll + ", modifiedDate=" + this.modifiedDate + ")";
    }

    public AssetPathEntity(String id, String name, int i, int i2, boolean z, Long l) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        this.id = id;
        this.name = name;
        this.assetCount = i;
        this.typeInt = i2;
        this.isAll = z;
        this.modifiedDate = l;
    }

    public /* synthetic */ AssetPathEntity(String str, String str2, int i, int i2, boolean z, Long l, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, i, i2, (i3 & 16) != 0 ? false : z, (i3 & 32) != 0 ? null : l);
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final int getAssetCount() {
        return this.assetCount;
    }

    public final void setAssetCount(int i) {
        this.assetCount = i;
    }

    public final int getTypeInt() {
        return this.typeInt;
    }

    public final boolean isAll() {
        return this.isAll;
    }

    public final void setAll(boolean z) {
        this.isAll = z;
    }

    public final Long getModifiedDate() {
        return this.modifiedDate;
    }

    public final void setModifiedDate(Long l) {
        this.modifiedDate = l;
    }
}
