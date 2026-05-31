package com.fluttercandies.photo_manager.core.entity.filter;

import kotlin.Metadata;

/* JADX INFO: compiled from: CommonFilterOption.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0006HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00062\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0017"}, d2 = {"Lcom/fluttercandies/photo_manager/core/entity/filter/DateCond;", "", "minMs", "", "maxMs", "ignore", "", "(JJZ)V", "getIgnore", "()Z", "getMaxMs", "()J", "getMinMs", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class DateCond {
    private final boolean ignore;
    private final long maxMs;
    private final long minMs;

    public static /* synthetic */ DateCond copy$default(DateCond dateCond, long j, long j2, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            j = dateCond.minMs;
        }
        long j3 = j;
        if ((i & 2) != 0) {
            j2 = dateCond.maxMs;
        }
        long j4 = j2;
        if ((i & 4) != 0) {
            z = dateCond.ignore;
        }
        return dateCond.copy(j3, j4, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final long getMinMs() {
        return this.minMs;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final long getMaxMs() {
        return this.maxMs;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getIgnore() {
        return this.ignore;
    }

    public final DateCond copy(long minMs, long maxMs, boolean ignore) {
        return new DateCond(minMs, maxMs, ignore);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DateCond)) {
            return false;
        }
        DateCond dateCond = (DateCond) other;
        return this.minMs == dateCond.minMs && this.maxMs == dateCond.maxMs && this.ignore == dateCond.ignore;
    }

    public int hashCode() {
        return (((Long.hashCode(this.minMs) * 31) + Long.hashCode(this.maxMs)) * 31) + Boolean.hashCode(this.ignore);
    }

    public String toString() {
        return "DateCond(minMs=" + this.minMs + ", maxMs=" + this.maxMs + ", ignore=" + this.ignore + ")";
    }

    public DateCond(long j, long j2, boolean z) {
        this.minMs = j;
        this.maxMs = j2;
        this.ignore = z;
    }

    public final boolean getIgnore() {
        return this.ignore;
    }

    public final long getMaxMs() {
        return this.maxMs;
    }

    public final long getMinMs() {
        return this.minMs;
    }
}
