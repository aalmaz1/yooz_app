package com.fluttercandies.photo_manager.core.entity.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CommonFilterOption.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\b\u0018\u0000 \u001b2\u00020\u0001:\u0003\u001b\u001c\u001dB\u0005¢\u0006\u0002\u0010\u0002J\u0011\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015¢\u0006\u0002\u0010\u0017J\u0006\u0010\u0018\u001a\u00020\u0016J\u0011\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015¢\u0006\u0002\u0010\u0017J\u0006\u0010\u001a\u001a\u00020\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006\u001e"}, d2 = {"Lcom/fluttercandies/photo_manager/core/entity/filter/FilterCond;", "", "()V", "durationConstraint", "Lcom/fluttercandies/photo_manager/core/entity/filter/FilterCond$DurationConstraint;", "getDurationConstraint", "()Lcom/fluttercandies/photo_manager/core/entity/filter/FilterCond$DurationConstraint;", "setDurationConstraint", "(Lcom/fluttercandies/photo_manager/core/entity/filter/FilterCond$DurationConstraint;)V", "isShowTitle", "", "()Z", "setShowTitle", "(Z)V", "sizeConstraint", "Lcom/fluttercandies/photo_manager/core/entity/filter/FilterCond$SizeConstraint;", "getSizeConstraint", "()Lcom/fluttercandies/photo_manager/core/entity/filter/FilterCond$SizeConstraint;", "setSizeConstraint", "(Lcom/fluttercandies/photo_manager/core/entity/filter/FilterCond$SizeConstraint;)V", "durationArgs", "", "", "()[Ljava/lang/String;", "durationCond", "sizeArgs", "sizeCond", "Companion", "DurationConstraint", "SizeConstraint", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FilterCond {
    private static final String durationKey = "duration";
    private static final String heightKey = "height";
    private static final String widthKey = "width";
    public DurationConstraint durationConstraint;
    private boolean isShowTitle;
    public SizeConstraint sizeConstraint;

    public final String sizeCond() {
        return "width >= ? AND width <= ? AND height >= ? AND height <=?";
    }

    /* JADX INFO: renamed from: isShowTitle, reason: from getter */
    public final boolean getIsShowTitle() {
        return this.isShowTitle;
    }

    public final void setShowTitle(boolean z) {
        this.isShowTitle = z;
    }

    public final SizeConstraint getSizeConstraint() {
        SizeConstraint sizeConstraint = this.sizeConstraint;
        if (sizeConstraint != null) {
            return sizeConstraint;
        }
        Intrinsics.throwUninitializedPropertyAccessException("sizeConstraint");
        return null;
    }

    public final void setSizeConstraint(SizeConstraint sizeConstraint) {
        Intrinsics.checkNotNullParameter(sizeConstraint, "<set-?>");
        this.sizeConstraint = sizeConstraint;
    }

    public final DurationConstraint getDurationConstraint() {
        DurationConstraint durationConstraint = this.durationConstraint;
        if (durationConstraint != null) {
            return durationConstraint;
        }
        Intrinsics.throwUninitializedPropertyAccessException("durationConstraint");
        return null;
    }

    public final void setDurationConstraint(DurationConstraint durationConstraint) {
        Intrinsics.checkNotNullParameter(durationConstraint, "<set-?>");
        this.durationConstraint = durationConstraint;
    }

    public final String[] sizeArgs() {
        List list = ArraysKt.toList(new Integer[]{Integer.valueOf(getSizeConstraint().getMinWidth()), Integer.valueOf(getSizeConstraint().getMaxWidth()), Integer.valueOf(getSizeConstraint().getMinHeight()), Integer.valueOf(getSizeConstraint().getMaxHeight())});
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(String.valueOf(((Number) it.next()).intValue()));
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public final String durationCond() {
        return getDurationConstraint().getAllowNullable() ? "( duration IS NULL OR ( duration >=? AND duration <=? ) )" : "duration >=? AND duration <=?";
    }

    public final String[] durationArgs() {
        Long[] lArr = {Long.valueOf(getDurationConstraint().getMin()), Long.valueOf(getDurationConstraint().getMax())};
        ArrayList arrayList = new ArrayList(2);
        for (int i = 0; i < 2; i++) {
            arrayList.add(String.valueOf(lArr[i].longValue()));
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    /* JADX INFO: compiled from: CommonFilterOption.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u000e\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001a\u0010\u0012\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000eR\u001a\u0010\u0015\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000e¨\u0006\u0018"}, d2 = {"Lcom/fluttercandies/photo_manager/core/entity/filter/FilterCond$SizeConstraint;", "", "()V", "ignoreSize", "", "getIgnoreSize", "()Z", "setIgnoreSize", "(Z)V", "maxHeight", "", "getMaxHeight", "()I", "setMaxHeight", "(I)V", "maxWidth", "getMaxWidth", "setMaxWidth", "minHeight", "getMinHeight", "setMinHeight", "minWidth", "getMinWidth", "setMinWidth", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SizeConstraint {
        private boolean ignoreSize;
        private int maxHeight;
        private int maxWidth;
        private int minHeight;
        private int minWidth;

        public final int getMinWidth() {
            return this.minWidth;
        }

        public final void setMinWidth(int i) {
            this.minWidth = i;
        }

        public final int getMaxWidth() {
            return this.maxWidth;
        }

        public final void setMaxWidth(int i) {
            this.maxWidth = i;
        }

        public final int getMinHeight() {
            return this.minHeight;
        }

        public final void setMinHeight(int i) {
            this.minHeight = i;
        }

        public final int getMaxHeight() {
            return this.maxHeight;
        }

        public final void setMaxHeight(int i) {
            this.maxHeight = i;
        }

        public final boolean getIgnoreSize() {
            return this.ignoreSize;
        }

        public final void setIgnoreSize(boolean z) {
            this.ignoreSize = z;
        }
    }

    /* JADX INFO: compiled from: CommonFilterOption.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000e¨\u0006\u0012"}, d2 = {"Lcom/fluttercandies/photo_manager/core/entity/filter/FilterCond$DurationConstraint;", "", "()V", "allowNullable", "", "getAllowNullable", "()Z", "setAllowNullable", "(Z)V", "max", "", "getMax", "()J", "setMax", "(J)V", "min", "getMin", "setMin", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DurationConstraint {
        private boolean allowNullable;
        private long max;
        private long min;

        public final long getMin() {
            return this.min;
        }

        public final void setMin(long j) {
            this.min = j;
        }

        public final long getMax() {
            return this.max;
        }

        public final void setMax(long j) {
            this.max = j;
        }

        public final boolean getAllowNullable() {
            return this.allowNullable;
        }

        public final void setAllowNullable(boolean z) {
            this.allowNullable = z;
        }
    }
}
