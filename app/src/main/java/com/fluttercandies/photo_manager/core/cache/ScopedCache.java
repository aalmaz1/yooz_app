package com.fluttercandies.photo_manager.core.cache;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import com.fluttercandies.photo_manager.constant.Methods;
import com.fluttercandies.photo_manager.core.entity.AssetEntity;
import com.fluttercandies.photo_manager.core.utils.AndroidQDBUtils;
import com.fluttercandies.photo_manager.util.LogUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: ScopedCache.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J \u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J \u0010\r\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f¨\u0006\u000f"}, d2 = {"Lcom/fluttercandies/photo_manager/core/cache/ScopedCache;", "", "()V", Methods.clearFileCache, "", "context", "Landroid/content/Context;", "getCacheFile", "Ljava/io/File;", "assetEntity", "Lcom/fluttercandies/photo_manager/core/entity/AssetEntity;", "isOrigin", "", "getCacheFileFromEntity", "Companion", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScopedCache {
    private static final String filenamePrefix = "pm_";

    public final File getCacheFileFromEntity(Context context, AssetEntity assetEntity, boolean isOrigin) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(assetEntity, "assetEntity");
        long id = assetEntity.getId();
        File cacheFile = getCacheFile(context, assetEntity, isOrigin);
        if (cacheFile.exists()) {
            return cacheFile;
        }
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = AndroidQDBUtils.INSTANCE.getUri(id, assetEntity.getType(), isOrigin);
        if (Intrinsics.areEqual(uri, Uri.EMPTY)) {
            return null;
        }
        try {
            LogUtils.info("Caching " + id + " [origin: " + isOrigin + "] into " + cacheFile.getAbsolutePath());
            InputStream inputStreamOpenInputStream = contentResolver.openInputStream(uri);
            InputStream fileOutputStream = new FileOutputStream(cacheFile);
            try {
                FileOutputStream fileOutputStream2 = fileOutputStream;
                if (inputStreamOpenInputStream != null) {
                    fileOutputStream = inputStreamOpenInputStream;
                    try {
                        Long.valueOf(ByteStreamsKt.copyTo$default(fileOutputStream, fileOutputStream2, 0, 2, null));
                        CloseableKt.closeFinally(fileOutputStream, null);
                    } finally {
                    }
                }
                CloseableKt.closeFinally(fileOutputStream, null);
                return cacheFile;
            } finally {
            }
        } catch (Exception e) {
            LogUtils.error("Caching " + id + " [origin: " + isOrigin + "] error", e);
            return null;
        }
    }

    private final File getCacheFile(Context context, AssetEntity assetEntity, boolean isOrigin) {
        return new File(context.getCacheDir(), filenamePrefix + assetEntity.getId() + (isOrigin ? "_o" : "") + "_" + assetEntity.getDisplayName());
    }

    public final void clearFileCache(Context context) {
        File[] fileArrListFiles;
        List<File> listFilterNotNull;
        Intrinsics.checkNotNullParameter(context, "context");
        File cacheDir = context.getCacheDir();
        if (cacheDir == null || (fileArrListFiles = cacheDir.listFiles()) == null || (listFilterNotNull = ArraysKt.filterNotNull(fileArrListFiles)) == null) {
            return;
        }
        for (File file : listFilterNotNull) {
            String name = file.getName();
            Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
            if (StringsKt.startsWith$default(name, filenamePrefix, false, 2, (Object) null)) {
                file.delete();
            }
        }
    }
}
