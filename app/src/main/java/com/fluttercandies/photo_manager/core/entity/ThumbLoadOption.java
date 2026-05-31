package com.fluttercandies.photo_manager.core.entity;

import android.graphics.Bitmap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ThumbLoadOption.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\tHÆ\u0003J;\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010¨\u0006 "}, d2 = {"Lcom/fluttercandies/photo_manager/core/entity/ThumbLoadOption;", "", "width", "", "height", "format", "Landroid/graphics/Bitmap$CompressFormat;", "quality", "frame", "", "(IILandroid/graphics/Bitmap$CompressFormat;IJ)V", "getFormat", "()Landroid/graphics/Bitmap$CompressFormat;", "getFrame", "()J", "getHeight", "()I", "getQuality", "getWidth", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "", "Factory", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ThumbLoadOption {

    /* JADX INFO: renamed from: Factory, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Bitmap.CompressFormat format;
    private final long frame;
    private final int height;
    private final int quality;
    private final int width;

    public static /* synthetic */ ThumbLoadOption copy$default(ThumbLoadOption thumbLoadOption, int i, int i2, Bitmap.CompressFormat compressFormat, int i3, long j, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = thumbLoadOption.width;
        }
        if ((i4 & 2) != 0) {
            i2 = thumbLoadOption.height;
        }
        int i5 = i2;
        if ((i4 & 4) != 0) {
            compressFormat = thumbLoadOption.format;
        }
        Bitmap.CompressFormat compressFormat2 = compressFormat;
        if ((i4 & 8) != 0) {
            i3 = thumbLoadOption.quality;
        }
        int i6 = i3;
        if ((i4 & 16) != 0) {
            j = thumbLoadOption.frame;
        }
        return thumbLoadOption.copy(i, i5, compressFormat2, i6, j);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getWidth() {
        return this.width;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getHeight() {
        return this.height;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Bitmap.CompressFormat getFormat() {
        return this.format;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getQuality() {
        return this.quality;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final long getFrame() {
        return this.frame;
    }

    public final ThumbLoadOption copy(int width, int height, Bitmap.CompressFormat format, int quality, long frame) {
        Intrinsics.checkNotNullParameter(format, "format");
        return new ThumbLoadOption(width, height, format, quality, frame);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ThumbLoadOption)) {
            return false;
        }
        ThumbLoadOption thumbLoadOption = (ThumbLoadOption) other;
        return this.width == thumbLoadOption.width && this.height == thumbLoadOption.height && this.format == thumbLoadOption.format && this.quality == thumbLoadOption.quality && this.frame == thumbLoadOption.frame;
    }

    public int hashCode() {
        return (((((((Integer.hashCode(this.width) * 31) + Integer.hashCode(this.height)) * 31) + this.format.hashCode()) * 31) + Integer.hashCode(this.quality)) * 31) + Long.hashCode(this.frame);
    }

    public String toString() {
        return "ThumbLoadOption(width=" + this.width + ", height=" + this.height + ", format=" + this.format + ", quality=" + this.quality + ", frame=" + this.frame + ")";
    }

    public ThumbLoadOption(int i, int i2, Bitmap.CompressFormat format, int i3, long j) {
        Intrinsics.checkNotNullParameter(format, "format");
        this.width = i;
        this.height = i2;
        this.format = format;
        this.quality = i3;
        this.frame = j;
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    public final Bitmap.CompressFormat getFormat() {
        return this.format;
    }

    public final int getQuality() {
        return this.quality;
    }

    public final long getFrame() {
        return this.frame;
    }

    /* JADX INFO: renamed from: com.fluttercandies.photo_manager.core.entity.ThumbLoadOption$Factory, reason: from kotlin metadata */
    /* JADX INFO: compiled from: ThumbLoadOption.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006¨\u0006\u0007"}, d2 = {"Lcom/fluttercandies/photo_manager/core/entity/ThumbLoadOption$Factory;", "", "()V", "fromMap", "Lcom/fluttercandies/photo_manager/core/entity/ThumbLoadOption;", "map", "", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ThumbLoadOption fromMap(Map<?, ?> map) {
            Bitmap.CompressFormat compressFormat;
            Intrinsics.checkNotNullParameter(map, "map");
            Object obj = map.get("width");
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
            int iIntValue = ((Integer) obj).intValue();
            Object obj2 = map.get("height");
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Int");
            int iIntValue2 = ((Integer) obj2).intValue();
            Object obj3 = map.get("format");
            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Int");
            int iIntValue3 = ((Integer) obj3).intValue();
            Object obj4 = map.get("quality");
            Intrinsics.checkNotNull(obj4, "null cannot be cast to non-null type kotlin.Int");
            int iIntValue4 = ((Integer) obj4).intValue();
            Object obj5 = map.get("frame");
            Intrinsics.checkNotNull(obj5, "null cannot be cast to non-null type kotlin.Int");
            long jIntValue = ((Integer) obj5).intValue();
            if (iIntValue3 == 0) {
                compressFormat = Bitmap.CompressFormat.JPEG;
            } else {
                compressFormat = Bitmap.CompressFormat.PNG;
            }
            return new ThumbLoadOption(iIntValue, iIntValue2, compressFormat, iIntValue4, jIntValue);
        }
    }
}
