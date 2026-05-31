package com.fluttercandies.photo_manager.core.entity.filter;

import cn.baos.watch.sdk.entitiy.NotificationConstant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: CommonFilterOption.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\u0006\u0010\u0010\u001a\u00020\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lcom/fluttercandies/photo_manager/core/entity/filter/OrderByCond;", "", NotificationConstant.EXTRA_KEY, "", "asc", "", "(Ljava/lang/String;Z)V", "getAsc", "()Z", "getKey", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "other", "getOrder", "hashCode", "", "toString", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class OrderByCond {
    private final boolean asc;
    private final String key;

    public static /* synthetic */ OrderByCond copy$default(OrderByCond orderByCond, String str, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            str = orderByCond.key;
        }
        if ((i & 2) != 0) {
            z = orderByCond.asc;
        }
        return orderByCond.copy(str, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getKey() {
        return this.key;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getAsc() {
        return this.asc;
    }

    public final OrderByCond copy(String key, boolean asc) {
        Intrinsics.checkNotNullParameter(key, "key");
        return new OrderByCond(key, asc);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OrderByCond)) {
            return false;
        }
        OrderByCond orderByCond = (OrderByCond) other;
        return Intrinsics.areEqual(this.key, orderByCond.key) && this.asc == orderByCond.asc;
    }

    public int hashCode() {
        return (this.key.hashCode() * 31) + Boolean.hashCode(this.asc);
    }

    public String toString() {
        return "OrderByCond(key=" + this.key + ", asc=" + this.asc + ")";
    }

    public OrderByCond(String key, boolean z) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.key = key;
        this.asc = z;
    }

    public final boolean getAsc() {
        return this.asc;
    }

    public final String getKey() {
        return this.key;
    }

    public final String getOrder() {
        return this.key + StringUtils.SPACE + (this.asc ? "asc" : "desc");
    }
}
