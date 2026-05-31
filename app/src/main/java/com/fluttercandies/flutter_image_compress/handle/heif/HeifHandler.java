package com.fluttercandies.flutter_image_compress.handle.heif;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.heifwriter.HeifWriter;
import cn.yoozworld.watch.utils.notifi.NotificationManager;
import com.fluttercandies.flutter_image_compress.ext.BitmapCompressExtKt;
import com.fluttercandies.flutter_image_compress.handle.FormatHandler;
import com.fluttercandies.flutter_image_compress.logger.LogExtKt;
import com.fluttercandies.flutter_image_compress.util.TmpFileUtil;
import java.io.File;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HeifHandler.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002JB\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\b\b\u0002\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\bH\u0002JB\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\b\b\u0002\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\bH\u0002J8\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0004H\u0002JP\u0010\u0019\u001a\u00020\f2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0013\u001a\u00020\u0004H\u0016JX\u0010!\u001a\u00020\f2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u0004H\u0016J\u0010\u0010#\u001a\u00020$2\u0006\u0010\u0013\u001a\u00020\u0004H\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006%"}, d2 = {"Lcom/fluttercandies/flutter_image_compress/handle/heif/HeifHandler;", "Lcom/fluttercandies/flutter_image_compress/handle/FormatHandler;", "()V", NotificationManager.BUNDLE_TYPE, "", "getType", "()I", "typeName", "", "getTypeName", "()Ljava/lang/String;", "compress", "", "arr", "", "minWidth", "minHeight", "quality", "rotate", "inSampleSize", "targetPath", "path", "convertToHeif", "bitmap", "Landroid/graphics/Bitmap;", "handleByteArray", "context", "Landroid/content/Context;", "byteArray", "outputStream", "Ljava/io/OutputStream;", "keepExif", "", "handleFile", "numberOfRetries", "makeOption", "Landroid/graphics/BitmapFactory$Options;", "flutter_image_compress_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class HeifHandler implements FormatHandler {
    @Override // com.fluttercandies.flutter_image_compress.handle.FormatHandler
    public int getType() {
        return 2;
    }

    @Override // com.fluttercandies.flutter_image_compress.handle.FormatHandler
    public String getTypeName() {
        return "heif";
    }

    @Override // com.fluttercandies.flutter_image_compress.handle.FormatHandler
    public void handleByteArray(Context context, byte[] byteArray, OutputStream outputStream, int minWidth, int minHeight, int quality, int rotate, boolean keepExif, int inSampleSize) throws Exception {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(byteArray, "byteArray");
        Intrinsics.checkNotNullParameter(outputStream, "outputStream");
        File fileCreateTmpFile = TmpFileUtil.INSTANCE.createTmpFile(context);
        String absolutePath = fileCreateTmpFile.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        compress(byteArray, minWidth, minHeight, quality, rotate, inSampleSize, absolutePath);
        outputStream.write(FilesKt.readBytes(fileCreateTmpFile));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void compress(byte[] arr, int minWidth, int minHeight, int quality, int rotate, int inSampleSize, String targetPath) throws Exception {
        Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(arr, 0, arr.length, makeOption(inSampleSize));
        Intrinsics.checkNotNull(bitmapDecodeByteArray);
        convertToHeif(bitmapDecodeByteArray, minWidth, minHeight, rotate, targetPath, quality);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void compress(String path, int minWidth, int minHeight, int quality, int rotate, int inSampleSize, String targetPath) throws Exception {
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(path, makeOption(inSampleSize));
        Intrinsics.checkNotNull(bitmapDecodeFile);
        convertToHeif(bitmapDecodeFile, minWidth, minHeight, rotate, targetPath, quality);
    }

    private final BitmapFactory.Options makeOption(int inSampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = inSampleSize;
        return options;
    }

    private final void convertToHeif(Bitmap bitmap, int minWidth, int minHeight, int rotate, String targetPath, int quality) throws Exception {
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        LogExtKt.log("src width = " + width);
        LogExtKt.log("src height = " + height);
        float fCalcScale = BitmapCompressExtKt.calcScale(bitmap, minWidth, minHeight);
        LogExtKt.log("scale = " + fCalcScale);
        float f = width / fCalcScale;
        float f2 = height / fCalcScale;
        LogExtKt.log("dst width = " + f);
        LogExtKt.log("dst height = " + f2);
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) f, (int) f2, true);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateScaledBitmap, "createScaledBitmap(...)");
        Bitmap bitmapRotate = BitmapCompressExtKt.rotate(bitmapCreateScaledBitmap, rotate);
        HeifWriter heifWriterBuild = new HeifWriter.Builder(targetPath, bitmapRotate.getWidth(), bitmapRotate.getHeight(), 2).setQuality(quality).setMaxImages(1).build();
        heifWriterBuild.start();
        heifWriterBuild.addBitmap(bitmapRotate);
        heifWriterBuild.stop(5000L);
        heifWriterBuild.close();
    }

    @Override // com.fluttercandies.flutter_image_compress.handle.FormatHandler
    public void handleFile(Context context, String path, OutputStream outputStream, int minWidth, int minHeight, int quality, int rotate, boolean keepExif, int inSampleSize, int numberOfRetries) throws Exception {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(outputStream, "outputStream");
        File fileCreateTmpFile = TmpFileUtil.INSTANCE.createTmpFile(context);
        String absolutePath = fileCreateTmpFile.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        compress(path, minWidth, minHeight, quality, rotate, inSampleSize, absolutePath);
        outputStream.write(FilesKt.readBytes(fileCreateTmpFile));
    }
}
