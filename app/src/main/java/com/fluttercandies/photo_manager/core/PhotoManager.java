package com.fluttercandies.photo_manager.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.camera.video.AudioStats;
import androidx.exifinterface.media.ExifInterface;
import cn.yoozworld.watch.utils.notifi.NotificationManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.fluttercandies.photo_manager.constant.Methods;
import com.fluttercandies.photo_manager.core.entity.AssetEntity;
import com.fluttercandies.photo_manager.core.entity.AssetPathEntity;
import com.fluttercandies.photo_manager.core.entity.ThumbLoadOption;
import com.fluttercandies.photo_manager.core.entity.filter.FilterOption;
import com.fluttercandies.photo_manager.core.utils.AndroidQDBUtils;
import com.fluttercandies.photo_manager.core.utils.ConvertUtils;
import com.fluttercandies.photo_manager.core.utils.DBUtils;
import com.fluttercandies.photo_manager.core.utils.IDBUtils;
import com.fluttercandies.photo_manager.thumb.ThumbnailUtil;
import com.fluttercandies.photo_manager.util.LogUtils;
import com.fluttercandies.photo_manager.util.ResultHandler;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PhotoManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u000e\n\u0002\u0010$\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0012\n\u0002\b\b\u0018\u0000 Q2\u00020\u0001:\u0001QB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u0006\u0010\u001a\u001a\u00020\u0015J\u0006\u0010\u001b\u001a\u00020\u0015J\u001e\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u0010\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010\u0016\u001a\u00020\u0017J \u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&J\u001e\u0010'\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&2\u0006\u0010(\u001a\u00020$J&\u0010'\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&2\u0006\u0010(\u001a\u00020$2\u0006\u0010\u001e\u001a\u00020\u0017J6\u0010)\u001a\b\u0012\u0004\u0012\u00020 0*2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010+\u001a\u00020$2\u0006\u0010,\u001a\u00020$2\u0006\u0010-\u001a\u00020$2\u0006\u0010%\u001a\u00020&J4\u0010.\u001a\b\u0012\u0004\u0012\u00020 0*2\u0006\u0010\u001e\u001a\u00020\u00172\u0006\u0010#\u001a\u00020$2\u0006\u0010/\u001a\u00020$2\u0006\u00100\u001a\u00020$2\u0006\u0010%\u001a\u00020&J,\u00101\u001a\b\u0012\u0004\u0012\u00020\"0*2\u0006\u0010#\u001a\u00020$2\u0006\u00102\u001a\u00020\u000f2\u0006\u00103\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020&J.\u00104\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010%\u001a\u00020&2\u0006\u0010/\u001a\u00020$2\u0006\u00100\u001a\u00020$2\u0006\u0010(\u001a\u00020$J\u000e\u00105\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0019J\u001e\u00106\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u00107\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0019J\u001a\u00108\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020:092\u0006\u0010\u0016\u001a\u00020\u0017J\u0016\u0010;\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020<2\u0006\u0010#\u001a\u00020$J\u001e\u0010=\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010>\u001a\u00020\u000fJ\u001e\u0010?\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010%\u001a\u00020@2\u0006\u0010\u0018\u001a\u00020\u0019J\u0010\u0010A\u001a\u0004\u0018\u00010B2\u0006\u0010\u0016\u001a\u00020\u0017J\u001e\u0010C\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u00172\u0006\u0010D\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010E\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0019J$\u0010F\u001a\u00020\u00152\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00170*2\u0006\u0010%\u001a\u00020@2\u0006\u0010\u0018\u001a\u00020\u0019J*\u0010H\u001a\u0004\u0018\u00010 2\u0006\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020\u00172\u0006\u0010L\u001a\u00020\u00172\b\u0010M\u001a\u0004\u0018\u00010\u0017J*\u0010H\u001a\u0004\u0018\u00010 2\u0006\u0010N\u001a\u00020\u00172\u0006\u0010K\u001a\u00020\u00172\u0006\u0010L\u001a\u00020\u00172\b\u0010M\u001a\u0004\u0018\u00010\u0017J*\u0010O\u001a\u0004\u0018\u00010 2\u0006\u0010N\u001a\u00020\u00172\u0006\u0010K\u001a\u00020\u00172\u0006\u0010P\u001a\u00020\u00172\b\u0010M\u001a\u0004\u0018\u00010\u0017R*\u0010\u0005\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006R"}, d2 = {"Lcom/fluttercandies/photo_manager/core/PhotoManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "cacheFutures", "Ljava/util/ArrayList;", "Lcom/bumptech/glide/request/FutureTarget;", "Landroid/graphics/Bitmap;", "Lkotlin/collections/ArrayList;", "dbUtils", "Lcom/fluttercandies/photo_manager/core/utils/IDBUtils;", "getDbUtils", "()Lcom/fluttercandies/photo_manager/core/utils/IDBUtils;", "useOldApi", "", "getUseOldApi", "()Z", "setUseOldApi", "(Z)V", Methods.assetExists, "", "id", "", "resultHandler", "Lcom/fluttercandies/photo_manager/util/ResultHandler;", Methods.cancelCacheRequests, Methods.clearFileCache, "copyToGallery", "assetId", "galleryId", Methods.fetchEntityProperties, "Lcom/fluttercandies/photo_manager/core/entity/AssetEntity;", Methods.fetchPathProperties, "Lcom/fluttercandies/photo_manager/core/entity/AssetPathEntity;", NotificationManager.BUNDLE_TYPE, "", "option", "Lcom/fluttercandies/photo_manager/core/entity/filter/FilterOption;", Methods.getAssetCount, "requestType", Methods.getAssetListPaged, "", "typeInt", "page", "size", Methods.getAssetListRange, "start", "end", Methods.getAssetPathList, "hasAll", "onlyAll", Methods.getAssetsByRange, Methods.getColumnNames, "getFile", "isOrigin", "getLocation", "", "", "getMediaUri", "", Methods.getOriginBytes, "needLocationPermission", Methods.getThumbnail, "Lcom/fluttercandies/photo_manager/core/entity/ThumbLoadOption;", "getUri", "Landroid/net/Uri;", "moveToGallery", "albumId", "removeAllExistsAssets", "requestCache", "ids", Methods.saveImage, "image", "", "title", "description", "relativePath", "path", Methods.saveVideo, "desc", "Companion", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PhotoManager {
    public static final String ALL_ALBUM_NAME = "Recent";
    public static final String ALL_ID = "isAll";
    private static final ExecutorService threadPool = Executors.newFixedThreadPool(5);
    private final ArrayList<FutureTarget<Bitmap>> cacheFutures;
    private final Context context;
    private boolean useOldApi;

    public PhotoManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.cacheFutures = new ArrayList<>();
    }

    public final boolean getUseOldApi() {
        return this.useOldApi;
    }

    public final void setUseOldApi(boolean z) {
        this.useOldApi = z;
    }

    private final IDBUtils getDbUtils() {
        if (this.useOldApi || Build.VERSION.SDK_INT < 29) {
            return DBUtils.INSTANCE;
        }
        return AndroidQDBUtils.INSTANCE;
    }

    public final List<AssetPathEntity> getAssetPathList(int type, boolean hasAll, boolean onlyAll, FilterOption option) {
        Intrinsics.checkNotNullParameter(option, "option");
        if (onlyAll) {
            return getDbUtils().getMainAssetPathEntity(this.context, type, option);
        }
        List<AssetPathEntity> assetPathList = getDbUtils().getAssetPathList(this.context, type, option);
        if (!hasAll) {
            return assetPathList;
        }
        Iterator<AssetPathEntity> it = assetPathList.iterator();
        int assetCount = 0;
        while (it.hasNext()) {
            assetCount += it.next().getAssetCount();
        }
        return CollectionsKt.plus((Collection) CollectionsKt.listOf(new AssetPathEntity(ALL_ID, ALL_ALBUM_NAME, assetCount, type, true, null, 32, null)), (Iterable) assetPathList);
    }

    public static /* synthetic */ List getAssetListPaged$default(PhotoManager photoManager, String str, int i, int i2, int i3, FilterOption filterOption, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        return photoManager.getAssetListPaged(str, i, i2, i3, filterOption);
    }

    public final List<AssetEntity> getAssetListPaged(String id, int typeInt, int page, int size, FilterOption option) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(option, "option");
        if (Intrinsics.areEqual(id, ALL_ID)) {
            id = "";
        }
        return getDbUtils().getAssetListPaged(this.context, id, page, size, typeInt, option);
    }

    public final List<AssetEntity> getAssetListRange(String galleryId, int type, int start, int end, FilterOption option) {
        Intrinsics.checkNotNullParameter(galleryId, "galleryId");
        Intrinsics.checkNotNullParameter(option, "option");
        if (Intrinsics.areEqual(galleryId, ALL_ID)) {
            galleryId = "";
        }
        return getDbUtils().getAssetListRange(this.context, galleryId, start, end, type, option);
    }

    public final void getThumb(String id, ThumbLoadOption option, ResultHandler resultHandler) {
        int i;
        int i2;
        ResultHandler resultHandler2;
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(option, "option");
        Intrinsics.checkNotNullParameter(resultHandler, "resultHandler");
        int width = option.getWidth();
        int height = option.getHeight();
        int quality = option.getQuality();
        Bitmap.CompressFormat format = option.getFormat();
        long frame = option.getFrame();
        try {
            AssetEntity assetEntity$default = IDBUtils.DefaultImpls.getAssetEntity$default(getDbUtils(), this.context, id, false, 4, null);
            if (assetEntity$default == null) {
                ResultHandler.replyError$default(resultHandler, "The asset not found!", null, null, 6, null);
                return;
            }
            i = height;
            i2 = width;
            resultHandler2 = resultHandler;
            try {
                ThumbnailUtil.INSTANCE.getThumbnail(this.context, assetEntity$default, option.getWidth(), option.getHeight(), format, quality, frame, resultHandler);
            } catch (Exception e) {
                e = e;
                Log.e(LogUtils.TAG, "get " + id + " thumbnail error, width : " + i2 + ", height: " + i, e);
                getDbUtils().logRowWithId(this.context, id);
                resultHandler2.replyError("201", "get thumb error", e);
            }
        } catch (Exception e2) {
            e = e2;
            i = height;
            i2 = width;
            resultHandler2 = resultHandler;
        }
    }

    public final void getOriginBytes(String id, ResultHandler resultHandler, boolean needLocationPermission) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(resultHandler, "resultHandler");
        AssetEntity assetEntity$default = IDBUtils.DefaultImpls.getAssetEntity$default(getDbUtils(), this.context, id, false, 4, null);
        if (assetEntity$default == null) {
            ResultHandler.replyError$default(resultHandler, "The asset not found", null, null, 6, null);
            return;
        }
        try {
            resultHandler.reply(getDbUtils().getOriginBytes(this.context, assetEntity$default, needLocationPermission));
        } catch (Exception e) {
            getDbUtils().logRowWithId(this.context, id);
            resultHandler.replyError("202", "get originBytes error", e);
        }
    }

    public final void clearFileCache() {
        ThumbnailUtil.INSTANCE.clearCache(this.context);
        getDbUtils().clearFileCache(this.context);
    }

    public final AssetPathEntity fetchPathProperties(String id, int type, FilterOption option) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(option, "option");
        if (Intrinsics.areEqual(id, ALL_ID)) {
            List<AssetPathEntity> assetPathList = getDbUtils().getAssetPathList(this.context, type, option);
            if (assetPathList.isEmpty()) {
                return null;
            }
            Iterator<AssetPathEntity> it = assetPathList.iterator();
            int assetCount = 0;
            while (it.hasNext()) {
                assetCount += it.next().getAssetCount();
            }
            AssetPathEntity assetPathEntity = new AssetPathEntity(ALL_ID, ALL_ALBUM_NAME, assetCount, type, true, null, 32, null);
            if (!option.getContainsPathModified()) {
                return assetPathEntity;
            }
            getDbUtils().injectModifiedDate(this.context, assetPathEntity);
            return assetPathEntity;
        }
        AssetPathEntity assetPathEntityFromId = getDbUtils().getAssetPathEntityFromId(this.context, id, type, option);
        if (assetPathEntityFromId != null && option.getContainsPathModified()) {
            getDbUtils().injectModifiedDate(this.context, assetPathEntityFromId);
        }
        return assetPathEntityFromId;
    }

    public final void getFile(String id, boolean isOrigin, ResultHandler resultHandler) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(resultHandler, "resultHandler");
        resultHandler.reply(getDbUtils().getFilePath(this.context, id, isOrigin));
    }

    public final AssetEntity saveImage(byte[] image, String title, String description, String relativePath) {
        Intrinsics.checkNotNullParameter(image, "image");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(description, "description");
        return getDbUtils().saveImage(this.context, image, title, description, relativePath);
    }

    public final AssetEntity saveImage(String path, String title, String description, String relativePath) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(description, "description");
        return getDbUtils().saveImage(this.context, path, title, description, relativePath);
    }

    public final AssetEntity saveVideo(String path, String title, String desc, String relativePath) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(desc, "desc");
        if (new File(path).exists()) {
            return getDbUtils().saveVideo(this.context, path, title, desc, relativePath);
        }
        return null;
    }

    public final void assetExists(String id, ResultHandler resultHandler) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(resultHandler, "resultHandler");
        resultHandler.reply(Boolean.valueOf(getDbUtils().assetExists(this.context, id)));
    }

    public final Map<String, Double> getLocation(String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        ExifInterface exif = getDbUtils().getExif(this.context, id);
        double[] latLong = exif != null ? exif.getLatLong() : null;
        return latLong == null ? MapsKt.mapOf(TuplesKt.to("lat", Double.valueOf(AudioStats.AUDIO_AMPLITUDE_NONE)), TuplesKt.to("lng", Double.valueOf(AudioStats.AUDIO_AMPLITUDE_NONE))) : MapsKt.mapOf(TuplesKt.to("lat", Double.valueOf(latLong[0])), TuplesKt.to("lng", Double.valueOf(latLong[1])));
    }

    public final String getMediaUri(long id, int type) {
        return getDbUtils().getMediaUri(this.context, id, type);
    }

    public final void copyToGallery(String assetId, String galleryId, ResultHandler resultHandler) {
        Intrinsics.checkNotNullParameter(assetId, "assetId");
        Intrinsics.checkNotNullParameter(galleryId, "galleryId");
        Intrinsics.checkNotNullParameter(resultHandler, "resultHandler");
        try {
            AssetEntity assetEntityCopyToGallery = getDbUtils().copyToGallery(this.context, assetId, galleryId);
            if (assetEntityCopyToGallery == null) {
                resultHandler.reply(null);
            } else {
                resultHandler.reply(ConvertUtils.INSTANCE.convertAsset(assetEntityCopyToGallery));
            }
        } catch (Exception e) {
            LogUtils.error(e);
            resultHandler.reply(null);
        }
    }

    public final void moveToGallery(String assetId, String albumId, ResultHandler resultHandler) {
        Intrinsics.checkNotNullParameter(assetId, "assetId");
        Intrinsics.checkNotNullParameter(albumId, "albumId");
        Intrinsics.checkNotNullParameter(resultHandler, "resultHandler");
        try {
            AssetEntity assetEntityMoveToGallery = getDbUtils().moveToGallery(this.context, assetId, albumId);
            if (assetEntityMoveToGallery == null) {
                resultHandler.reply(null);
            } else {
                resultHandler.reply(ConvertUtils.INSTANCE.convertAsset(assetEntityMoveToGallery));
            }
        } catch (Exception e) {
            LogUtils.error(e);
            resultHandler.reply(null);
        }
    }

    public final void removeAllExistsAssets(ResultHandler resultHandler) {
        Intrinsics.checkNotNullParameter(resultHandler, "resultHandler");
        resultHandler.reply(Boolean.valueOf(getDbUtils().removeAllExistsAssets(this.context)));
    }

    public final AssetEntity fetchEntityProperties(String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        return IDBUtils.DefaultImpls.getAssetEntity$default(getDbUtils(), this.context, id, false, 4, null);
    }

    public final Uri getUri(String id) {
        Intrinsics.checkNotNullParameter(id, "id");
        AssetEntity assetEntity$default = IDBUtils.DefaultImpls.getAssetEntity$default(getDbUtils(), this.context, id, false, 4, null);
        if (assetEntity$default != null) {
            return assetEntity$default.getUri();
        }
        return null;
    }

    public final void requestCache(List<String> ids, ThumbLoadOption option, ResultHandler resultHandler) {
        Intrinsics.checkNotNullParameter(ids, "ids");
        Intrinsics.checkNotNullParameter(option, "option");
        Intrinsics.checkNotNullParameter(resultHandler, "resultHandler");
        Iterator<String> it = getDbUtils().getAssetsPath(this.context, ids).iterator();
        while (it.hasNext()) {
            this.cacheFutures.add(ThumbnailUtil.INSTANCE.requestCacheThumb(this.context, it.next(), option));
        }
        resultHandler.reply(1);
        for (final FutureTarget futureTarget : CollectionsKt.toList(this.cacheFutures)) {
            threadPool.execute(new Runnable() { // from class: com.fluttercandies.photo_manager.core.PhotoManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PhotoManager.requestCache$lambda$3(futureTarget);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void requestCache$lambda$3(FutureTarget cacheFuture) {
        Intrinsics.checkNotNullParameter(cacheFuture, "$cacheFuture");
        if (cacheFuture.isCancelled()) {
            return;
        }
        try {
            cacheFuture.get();
        } catch (Exception e) {
            LogUtils.error(e);
        }
    }

    public final void cancelCacheRequests() {
        List list = CollectionsKt.toList(this.cacheFutures);
        this.cacheFutures.clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Glide.with(this.context).clear((FutureTarget) it.next());
        }
    }

    public final void getColumnNames(ResultHandler resultHandler) {
        Intrinsics.checkNotNullParameter(resultHandler, "resultHandler");
        resultHandler.reply(getDbUtils().getColumnNames(this.context));
    }

    public final void getAssetCount(ResultHandler resultHandler, FilterOption option, int requestType) {
        Intrinsics.checkNotNullParameter(resultHandler, "resultHandler");
        Intrinsics.checkNotNullParameter(option, "option");
        resultHandler.reply(Integer.valueOf(getDbUtils().getAssetCount(this.context, option, requestType)));
    }

    public final void getAssetCount(ResultHandler resultHandler, FilterOption option, int requestType, String galleryId) {
        Intrinsics.checkNotNullParameter(resultHandler, "resultHandler");
        Intrinsics.checkNotNullParameter(option, "option");
        Intrinsics.checkNotNullParameter(galleryId, "galleryId");
        resultHandler.reply(Integer.valueOf(getDbUtils().getAssetCount(this.context, option, requestType, galleryId)));
    }

    public final void getAssetsByRange(ResultHandler resultHandler, FilterOption option, int start, int end, int requestType) {
        Intrinsics.checkNotNullParameter(resultHandler, "resultHandler");
        Intrinsics.checkNotNullParameter(option, "option");
        resultHandler.reply(ConvertUtils.INSTANCE.convertAssets(getDbUtils().getAssetsByRange(this.context, option, start, end, requestType)));
    }
}
