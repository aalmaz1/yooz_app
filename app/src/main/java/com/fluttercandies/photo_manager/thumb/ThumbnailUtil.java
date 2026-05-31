package com.fluttercandies.photo_manager.thumb;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.fluttercandies.photo_manager.core.entity.AssetEntity;
import com.fluttercandies.photo_manager.core.entity.ThumbLoadOption;
import com.fluttercandies.photo_manager.util.ResultHandler;
import java.io.ByteArrayOutputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ThumbnailUtil.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006JF\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J$\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aJ$\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0019\u001a\u00020\u001a¨\u0006\u001d"}, d2 = {"Lcom/fluttercandies/photo_manager/thumb/ThumbnailUtil;", "", "()V", "clearCache", "", "context", "Landroid/content/Context;", "getThumbnail", "entity", "Lcom/fluttercandies/photo_manager/core/entity/AssetEntity;", "width", "", "height", "format", "Landroid/graphics/Bitmap$CompressFormat;", "quality", "frame", "", "resultHandler", "Lcom/fluttercandies/photo_manager/util/ResultHandler;", "requestCacheThumb", "Lcom/bumptech/glide/request/FutureTarget;", "Landroid/graphics/Bitmap;", "uri", "Landroid/net/Uri;", "thumbLoadOption", "Lcom/fluttercandies/photo_manager/core/entity/ThumbLoadOption;", "path", "", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ThumbnailUtil {
    public static final ThumbnailUtil INSTANCE = new ThumbnailUtil();

    private ThumbnailUtil() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void getThumbnail(Context context, AssetEntity entity, int width, int height, Bitmap.CompressFormat format, int quality, long frame, ResultHandler resultHandler) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(entity, "entity");
        Intrinsics.checkNotNullParameter(format, "format");
        Intrinsics.checkNotNullParameter(resultHandler, "resultHandler");
        try {
            Bitmap bitmap = (Bitmap) Glide.with(context).asBitmap().apply((BaseRequestOptions<?>) new RequestOptions().frame(frame).priority(Priority.IMMEDIATE)).load(entity.getUri()).signature(new ObjectKey(Long.valueOf(entity.getModifiedDate()))).submit(width, height).get();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(format, quality, byteArrayOutputStream);
            resultHandler.reply(byteArrayOutputStream.toByteArray());
        } catch (Exception e) {
            ResultHandler.replyError$default(resultHandler, "Thumbnail request error", e.toString(), null, 4, null);
        }
    }

    public final FutureTarget<Bitmap> requestCacheThumb(Context context, Uri uri, ThumbLoadOption thumbLoadOption) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(uri, "uri");
        Intrinsics.checkNotNullParameter(thumbLoadOption, "thumbLoadOption");
        FutureTarget<Bitmap> futureTargetSubmit = Glide.with(context).asBitmap().apply((BaseRequestOptions<?>) new RequestOptions().frame(thumbLoadOption.getFrame()).priority(Priority.LOW)).load(uri).submit(thumbLoadOption.getWidth(), thumbLoadOption.getHeight());
        Intrinsics.checkNotNullExpressionValue(futureTargetSubmit, "submit(...)");
        return futureTargetSubmit;
    }

    public final FutureTarget<Bitmap> requestCacheThumb(Context context, String path, ThumbLoadOption thumbLoadOption) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(thumbLoadOption, "thumbLoadOption");
        FutureTarget<Bitmap> futureTargetSubmit = Glide.with(context).asBitmap().apply((BaseRequestOptions<?>) new RequestOptions().frame(thumbLoadOption.getFrame()).priority(Priority.LOW)).load(path).submit(thumbLoadOption.getWidth(), thumbLoadOption.getHeight());
        Intrinsics.checkNotNullExpressionValue(futureTargetSubmit, "submit(...)");
        return futureTargetSubmit;
    }

    public final void clearCache(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Glide.get(context).clearDiskCache();
    }
}
