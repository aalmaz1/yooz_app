package com.fluttercandies.photo_manager.core.utils;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.MediaStore;
import cn.yoozworld.watch.utils.notifi.NotificationManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaStoreUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0004J\u0016\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u0004¨\u0006\f"}, d2 = {"Lcom/fluttercandies/photo_manager/core/utils/MediaStoreUtils;", "", "()V", "convertTypeToMediaType", "", NotificationManager.BUNDLE_TYPE, "getInsertUri", "Landroid/net/Uri;", "mediaType", "getUri", "id", "", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MediaStoreUtils {
    public static final MediaStoreUtils INSTANCE = new MediaStoreUtils();

    public final int convertTypeToMediaType(int type) {
        if (type == 1) {
            return 1;
        }
        if (type != 2) {
            return type != 3 ? 0 : 2;
        }
        return 3;
    }

    private MediaStoreUtils() {
    }

    public final Uri getInsertUri(int mediaType) {
        if (mediaType == 1) {
            Uri EXTERNAL_CONTENT_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Intrinsics.checkNotNullExpressionValue(EXTERNAL_CONTENT_URI, "EXTERNAL_CONTENT_URI");
            return EXTERNAL_CONTENT_URI;
        }
        if (mediaType == 2) {
            Uri EXTERNAL_CONTENT_URI2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Intrinsics.checkNotNullExpressionValue(EXTERNAL_CONTENT_URI2, "EXTERNAL_CONTENT_URI");
            return EXTERNAL_CONTENT_URI2;
        }
        if (mediaType == 3) {
            Uri EXTERNAL_CONTENT_URI3 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            Intrinsics.checkNotNullExpressionValue(EXTERNAL_CONTENT_URI3, "EXTERNAL_CONTENT_URI");
            return EXTERNAL_CONTENT_URI3;
        }
        return IDBUtils.INSTANCE.getAllUri();
    }

    public final Uri getUri(long id, int mediaType) {
        Uri uriWithAppendedId = ContentUris.withAppendedId(getInsertUri(mediaType), id);
        Intrinsics.checkNotNullExpressionValue(uriWithAppendedId, "withAppendedId(...)");
        return uriWithAppendedId;
    }
}
