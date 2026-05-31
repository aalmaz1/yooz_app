package com.fluttercandies.flutter_image_compress.ext;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import cn.yoozworld.watch.utils.notifi.NotificationManager;
import com.fluttercandies.flutter_image_compress.ImageCompressPlugin;
import com.fluttercandies.photo_manager.constant.Methods;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BitmapCompressExt.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0012\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002\u001a\u001a\u0010\b\u001a\u00020\t*\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u0003\u001a>\u0010\r\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u001a4\u0010\r\u001a\u00020\u0013*\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0003\u001a\u0012\u0010\u000f\u001a\u00020\n*\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0003¨\u0006\u0014"}, d2 = {"convertFormatIndexToFormat", "Landroid/graphics/Bitmap$CompressFormat;", NotificationManager.BUNDLE_TYPE, "", Methods.log, "", "any", "", "calcScale", "", "Landroid/graphics/Bitmap;", "minWidth", "minHeight", "compress", "quality", "rotate", "outputStream", "Ljava/io/OutputStream;", "format", "", "flutter_image_compress_common_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BitmapCompressExtKt {
    public static /* synthetic */ byte[] compress$default(Bitmap bitmap, int i, int i2, int i3, int i4, int i5, int i6, Object obj) {
        if ((i6 & 8) != 0) {
            i4 = 0;
        }
        return compress(bitmap, i, i2, i3, i4, i5);
    }

    public static final byte[] compress(Bitmap bitmap, int i, int i2, int i3, int i4, int i5) {
        Intrinsics.checkNotNullParameter(bitmap, "<this>");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        compress(bitmap, i, i2, i3, i4, byteArrayOutputStream, i5);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
        return byteArray;
    }

    public static final void compress(Bitmap bitmap, int i, int i2, int i3, int i4, OutputStream outputStream, int i5) {
        Intrinsics.checkNotNullParameter(bitmap, "<this>");
        Intrinsics.checkNotNullParameter(outputStream, "outputStream");
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        log("src width = " + width);
        log("src height = " + height);
        float fCalcScale = calcScale(bitmap, i, i2);
        log("scale = " + fCalcScale);
        float f = width / fCalcScale;
        float f2 = height / fCalcScale;
        log("dst width = " + f);
        log("dst height = " + f2);
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) f, (int) f2, true);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateScaledBitmap, "createScaledBitmap(...)");
        rotate(bitmapCreateScaledBitmap, i4).compress(convertFormatIndexToFormat(i5), i3, outputStream);
    }

    private static final void log(Object obj) {
        if (ImageCompressPlugin.INSTANCE.getShowLog()) {
            if (obj == null) {
                obj = "null";
            }
            System.out.println(obj);
        }
    }

    public static final Bitmap rotate(Bitmap bitmap, int i) {
        Intrinsics.checkNotNullParameter(bitmap, "<this>");
        if (i % 360 == 0) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(i);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        Intrinsics.checkNotNull(bitmapCreateBitmap);
        return bitmapCreateBitmap;
    }

    public static final float calcScale(Bitmap bitmap, int i, int i2) {
        Intrinsics.checkNotNullParameter(bitmap, "<this>");
        float width = bitmap.getWidth() / i;
        float height = bitmap.getHeight() / i2;
        log("width scale = " + width);
        log("height scale = " + height);
        return Math.max(1.0f, Math.min(width, height));
    }

    public static final Bitmap.CompressFormat convertFormatIndexToFormat(int i) {
        if (i == 1) {
            return Bitmap.CompressFormat.PNG;
        }
        if (i == 3) {
            return Bitmap.CompressFormat.WEBP;
        }
        return Bitmap.CompressFormat.JPEG;
    }
}
