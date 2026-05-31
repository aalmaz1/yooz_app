package com.fluttercandies.photo_manager.core.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import cn.yoozworld.watch.utils.notifi.NotificationManager;
import com.fluttercandies.photo_manager.constant.Methods;
import com.fluttercandies.photo_manager.core.PhotoManager;
import com.fluttercandies.photo_manager.core.entity.AssetEntity;
import com.fluttercandies.photo_manager.core.entity.AssetPathEntity;
import com.fluttercandies.photo_manager.core.entity.filter.FilterOption;
import com.fluttercandies.photo_manager.core.utils.VideoUtils;
import com.fluttercandies.photo_manager.util.LogUtils;
import com.google.android.gms.fido.u2f.api.common.ClientData;
import com.tekartik.sqflite.Constant;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: IDBUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000¤\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\f\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\bf\u0018\u0000 l2\u00020\u0001:\u0001lJ\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0007H\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\"\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u0007H&J \u0010\u0018\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0012H\u0016J(\u0010\u0018\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0007H\u0016J$\u0010\u001c\u001a\u0004\u0018\u00010\u00152\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00072\b\b\u0002\u0010\u001d\u001a\u00020\u000bH&J@\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00150\u001f2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010 \u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u00122\u0006\u0010\"\u001a\u00020\u00122\b\b\u0002\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001aH&J>\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00150\u001f2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u00072\u0006\u0010$\u001a\u00020\u00122\u0006\u0010%\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001aH&J*\u0010&\u001a\u0004\u0018\u00010'2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010 \u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001aH&J(\u0010(\u001a\b\u0012\u0004\u0012\u00020'0\u001f2\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001aH&J6\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00150\u001f2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010$\u001a\u00020\u00122\u0006\u0010%\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u0012H\u0016J$\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00070\u001f2\u0006\u0010\f\u001a\u00020\r2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00070\u001fH\u0016J\u0016\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00070\u001f2\u0006\u0010\f\u001a\u00020\rH\u0016J\u001a\u0010-\u001a\u0004\u0018\u00010.2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0007H&J\"\u0010/\u001a\u0004\u0018\u00010\u00072\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u00100\u001a\u00020\u000bH&J&\u00101\u001a\b\u0012\u0004\u0012\u00020'0\u001f2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001aH&J\u0010\u00102\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J \u00103\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u0002042\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J \u00105\u001a\u0002062\u0006\u0010\f\u001a\u00020\r2\u0006\u00107\u001a\u00020\u00152\u0006\u00108\u001a\u00020\u000bH&J\u001f\u00109\u001a\u0004\u0018\u0001042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010 \u001a\u00020\u0007H\u0016¢\u0006\u0002\u0010:J(\u0010;\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010<2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u0007H&J\"\u0010=\u001a\u0004\u0018\u00010\u00072\u0006\u0010$\u001a\u00020\u00122\u0006\u0010>\u001a\u00020\u00122\u0006\u0010?\u001a\u00020\u001aH\u0016J\u0010\u0010@\u001a\u00020\u00122\u0006\u0010A\u001a\u00020\u0012H\u0016J\"\u0010B\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u0002042\u0006\u0010\u0013\u001a\u00020\u00122\b\b\u0002\u0010C\u001a\u00020\u000bH\u0016J\u0018\u0010D\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\r2\u0006\u0010E\u001a\u00020'H\u0016J4\u0010F\u001a\u0004\u0018\u00010\u00152\u0006\u0010\f\u001a\u00020\r2\u0006\u0010G\u001a\u00020H2\u0006\u0010I\u001a\u00020\u00032\u0006\u0010J\u001a\u00020K2\b\b\u0002\u0010L\u001a\u00020\u000bH\u0002J\u0013\u0010M\u001a\b\u0012\u0004\u0012\u00020\u00070NH&¢\u0006\u0002\u0010OJ\u0018\u0010P\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0007H\u0016J\"\u0010Q\u001a\u0004\u0018\u00010\u00152\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u0007H&J\u0010\u0010R\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&J4\u0010S\u001a\u0004\u0018\u00010\u00152\u0006\u0010\f\u001a\u00020\r2\u0006\u0010T\u001a\u0002062\u0006\u0010U\u001a\u00020\u00072\u0006\u0010V\u001a\u00020\u00072\b\u0010W\u001a\u0004\u0018\u00010\u0007H\u0016J4\u0010S\u001a\u0004\u0018\u00010\u00152\u0006\u0010\f\u001a\u00020\r2\u0006\u0010X\u001a\u00020\u00072\u0006\u0010U\u001a\u00020\u00072\u0006\u0010V\u001a\u00020\u00072\b\u0010W\u001a\u0004\u0018\u00010\u0007H\u0016J4\u0010Y\u001a\u0004\u0018\u00010\u00152\u0006\u0010\f\u001a\u00020\r2\u0006\u0010X\u001a\u00020\u00072\u0006\u0010U\u001a\u00020\u00072\u0006\u0010V\u001a\u00020\u00072\b\u0010W\u001a\u0004\u0018\u00010\u0007H\u0016J\u0010\u0010Z\u001a\u00020[2\u0006\u0010\\\u001a\u00020\u0007H\u0016J\u0014\u0010]\u001a\u00020\u0012*\u00020^2\u0006\u0010_\u001a\u00020\u0007H\u0016J\u0014\u0010`\u001a\u000204*\u00020^2\u0006\u0010_\u001a\u00020\u0007H\u0016J\u0014\u0010a\u001a\u00020\u0007*\u00020^2\u0006\u0010_\u001a\u00020\u0007H\u0016J\u0016\u0010b\u001a\u0004\u0018\u00010\u0007*\u00020^2\u0006\u0010_\u001a\u00020\u0007H\u0016JO\u0010c\u001a\u0004\u0018\u00010^*\u00020d2\u0006\u0010e\u001a\u00020\u00032\u000e\u0010f\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010N2\b\u0010g\u001a\u0004\u0018\u00010\u00072\u000e\u0010h\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010N2\b\u0010i\u001a\u0004\u0018\u00010\u0007H\u0016¢\u0006\u0002\u0010jJ \u0010k\u001a\u0004\u0018\u00010\u0015*\u00020^2\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u001d\u001a\u00020\u000bH\u0016R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006m"}, d2 = {"Lcom/fluttercandies/photo_manager/core/utils/IDBUtils;", "", "allUri", "Landroid/net/Uri;", "getAllUri", "()Landroid/net/Uri;", "idSelection", "", "getIdSelection", "()Ljava/lang/String;", Methods.assetExists, "", "context", "Landroid/content/Context;", "id", Methods.clearFileCache, "", "convertTypeToMediaType", "", NotificationManager.BUNDLE_TYPE, "copyToGallery", "Lcom/fluttercandies/photo_manager/core/entity/AssetEntity;", "assetId", "galleryId", Methods.getAssetCount, "option", "Lcom/fluttercandies/photo_manager/core/entity/filter/FilterOption;", "requestType", "getAssetEntity", "checkIfExists", Methods.getAssetListPaged, "", "pathId", "page", "size", Methods.getAssetListRange, "start", "end", "getAssetPathEntityFromId", "Lcom/fluttercandies/photo_manager/core/entity/AssetPathEntity;", Methods.getAssetPathList, Methods.getAssetsByRange, "getAssetsPath", "ids", Methods.getColumnNames, "getExif", "Landroidx/exifinterface/media/ExifInterface;", "getFilePath", ClientData.KEY_ORIGIN, "getMainAssetPathEntity", "getMediaType", "getMediaUri", "", Methods.getOriginBytes, "", "asset", "needLocationPermission", "getPathModifiedDate", "(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/Long;", "getSomeInfo", "Lkotlin/Pair;", "getSortOrder", "pageSize", "filterOption", "getTypeFromMediaType", "mediaType", "getUri", "isOrigin", "injectModifiedDate", "entity", "insertUri", "inputStream", "Ljava/io/InputStream;", "contentUri", "values", "Landroid/content/ContentValues;", "shouldKeepPath", "keys", "", "()[Ljava/lang/String;", "logRowWithId", "moveToGallery", "removeAllExistsAssets", Methods.saveImage, "bytes", "title", "desc", "relativePath", "fromPath", Methods.saveVideo, "throwMsg", "", NotificationCompat.CATEGORY_MESSAGE, "getInt", "Landroid/database/Cursor;", "columnName", "getLong", "getString", "getStringOrNull", "logQuery", "Landroid/content/ContentResolver;", "uri", "projection", "selection", "selectionArgs", "sortOrder", "(Landroid/content/ContentResolver;Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;", "toAssetEntity", "Companion", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IDBUtils {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    boolean assetExists(Context context, String id);

    void clearFileCache(Context context);

    int convertTypeToMediaType(int type);

    AssetEntity copyToGallery(Context context, String assetId, String galleryId);

    Uri getAllUri();

    int getAssetCount(Context context, FilterOption option, int requestType);

    int getAssetCount(Context context, FilterOption option, int requestType, String galleryId);

    AssetEntity getAssetEntity(Context context, String id, boolean checkIfExists);

    List<AssetEntity> getAssetListPaged(Context context, String pathId, int page, int size, int requestType, FilterOption option);

    List<AssetEntity> getAssetListRange(Context context, String galleryId, int start, int end, int requestType, FilterOption option);

    AssetPathEntity getAssetPathEntityFromId(Context context, String pathId, int type, FilterOption option);

    List<AssetPathEntity> getAssetPathList(Context context, int requestType, FilterOption option);

    List<AssetEntity> getAssetsByRange(Context context, FilterOption option, int start, int end, int requestType);

    List<String> getAssetsPath(Context context, List<String> ids);

    List<String> getColumnNames(Context context);

    ExifInterface getExif(Context context, String id);

    String getFilePath(Context context, String id, boolean origin);

    String getIdSelection();

    int getInt(Cursor cursor, String str);

    long getLong(Cursor cursor, String str);

    List<AssetPathEntity> getMainAssetPathEntity(Context context, int requestType, FilterOption option);

    int getMediaType(int type);

    String getMediaUri(Context context, long id, int type);

    byte[] getOriginBytes(Context context, AssetEntity asset, boolean needLocationPermission);

    Long getPathModifiedDate(Context context, String pathId);

    Pair<String, String> getSomeInfo(Context context, String assetId);

    String getSortOrder(int start, int pageSize, FilterOption filterOption);

    String getString(Cursor cursor, String str);

    String getStringOrNull(Cursor cursor, String str);

    int getTypeFromMediaType(int mediaType);

    Uri getUri(long id, int type, boolean isOrigin);

    void injectModifiedDate(Context context, AssetPathEntity entity);

    String[] keys();

    Cursor logQuery(ContentResolver contentResolver, Uri uri, String[] strArr, String str, String[] strArr2, String str2);

    void logRowWithId(Context context, String id);

    AssetEntity moveToGallery(Context context, String assetId, String galleryId);

    boolean removeAllExistsAssets(Context context);

    AssetEntity saveImage(Context context, String fromPath, String title, String desc, String relativePath);

    AssetEntity saveImage(Context context, byte[] bytes, String title, String desc, String relativePath);

    AssetEntity saveVideo(Context context, String fromPath, String title, String desc, String relativePath);

    Void throwMsg(String msg);

    AssetEntity toAssetEntity(Cursor cursor, Context context, boolean z);

    /* JADX INFO: compiled from: IDBUtils.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\tR\u0019\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0019\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\f0\u000b¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\u0017\u0010\u000e¨\u0006\u0018"}, d2 = {"Lcom/fluttercandies/photo_manager/core/utils/IDBUtils$Companion;", "", "()V", "allUri", "Landroid/net/Uri;", "getAllUri", "()Landroid/net/Uri;", "isAboveAndroidQ", "", "()Z", "storeBucketKeys", "", "", "getStoreBucketKeys", "()[Ljava/lang/String;", "[Ljava/lang/String;", "storeImageKeys", "", "getStoreImageKeys", "()Ljava/util/List;", "storeVideoKeys", "getStoreVideoKeys", "typeKeys", "getTypeKeys", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final boolean isAboveAndroidQ;
        private static final String[] storeBucketKeys;
        private static final List<String> storeImageKeys;
        private static final List<String> storeVideoKeys;
        private static final String[] typeKeys;

        private Companion() {
        }

        static {
            isAboveAndroidQ = Build.VERSION.SDK_INT >= 29;
            List<String> listMutableListOf = CollectionsKt.mutableListOf("_display_name", "_data", "_id", "title", "bucket_id", "bucket_display_name", "width", "height", "orientation", "date_added", "date_modified", "mime_type", "datetaken");
            if (Build.VERSION.SDK_INT >= 29) {
                listMutableListOf.add("datetaken");
            }
            storeImageKeys = listMutableListOf;
            List<String> listMutableListOf2 = CollectionsKt.mutableListOf("_display_name", "_data", "_id", "title", "bucket_id", "bucket_display_name", "date_added", "width", "height", "orientation", "date_modified", "mime_type", "duration");
            if (Build.VERSION.SDK_INT >= 29) {
                listMutableListOf2.add("datetaken");
            }
            storeVideoKeys = listMutableListOf2;
            typeKeys = new String[]{"media_type", "_display_name"};
            storeBucketKeys = new String[]{"bucket_id", "bucket_display_name"};
        }

        public final boolean isAboveAndroidQ() {
            return isAboveAndroidQ;
        }

        public final List<String> getStoreImageKeys() {
            return storeImageKeys;
        }

        public final List<String> getStoreVideoKeys() {
            return storeVideoKeys;
        }

        public final String[] getTypeKeys() {
            return typeKeys;
        }

        public final String[] getStoreBucketKeys() {
            return storeBucketKeys;
        }

        public final Uri getAllUri() {
            Uri contentUri = MediaStore.Files.getContentUri("external");
            Intrinsics.checkNotNullExpressionValue(contentUri, "getContentUri(...)");
            return contentUri;
        }
    }

    /* JADX INFO: compiled from: IDBUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static void clearFileCache(IDBUtils iDBUtils, Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
        }

        public static String getIdSelection(IDBUtils iDBUtils) {
            return "_id = ?";
        }

        public static int getMediaType(IDBUtils iDBUtils, int i) {
            if (i == 1) {
                return 1;
            }
            if (i != 2) {
                return i != 3 ? 0 : 2;
            }
            return 3;
        }

        public static int getTypeFromMediaType(IDBUtils iDBUtils, int i) {
            if (i == 1) {
                return 1;
            }
            if (i != 2) {
                return i != 3 ? 0 : 2;
            }
            return 3;
        }

        public static Uri getAllUri(IDBUtils iDBUtils) {
            return IDBUtils.INSTANCE.getAllUri();
        }

        public static /* synthetic */ List getAssetPathList$default(IDBUtils iDBUtils, Context context, int i, FilterOption filterOption, int i2, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getAssetPathList");
            }
            if ((i2 & 2) != 0) {
                i = 0;
            }
            return iDBUtils.getAssetPathList(context, i, filterOption);
        }

        public static /* synthetic */ List getAssetListPaged$default(IDBUtils iDBUtils, Context context, String str, int i, int i2, int i3, FilterOption filterOption, int i4, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getAssetListPaged");
            }
            if ((i4 & 16) != 0) {
                i3 = 0;
            }
            return iDBUtils.getAssetListPaged(context, str, i, i2, i3, filterOption);
        }

        public static /* synthetic */ AssetEntity getAssetEntity$default(IDBUtils iDBUtils, Context context, String str, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getAssetEntity");
            }
            if ((i & 4) != 0) {
                z = true;
            }
            return iDBUtils.getAssetEntity(context, str, z);
        }

        public static int convertTypeToMediaType(IDBUtils iDBUtils, int i) {
            return MediaStoreUtils.INSTANCE.convertTypeToMediaType(i);
        }

        public static int getInt(IDBUtils iDBUtils, Cursor receiver, String columnName) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(columnName, "columnName");
            return receiver.getInt(receiver.getColumnIndex(columnName));
        }

        public static String getString(IDBUtils iDBUtils, Cursor receiver, String columnName) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(columnName, "columnName");
            String string = receiver.getString(receiver.getColumnIndex(columnName));
            return string == null ? "" : string;
        }

        public static String getStringOrNull(IDBUtils iDBUtils, Cursor receiver, String columnName) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(columnName, "columnName");
            return receiver.getString(receiver.getColumnIndex(columnName));
        }

        public static long getLong(IDBUtils iDBUtils, Cursor receiver, String columnName) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(columnName, "columnName");
            return receiver.getLong(receiver.getColumnIndex(columnName));
        }

        public static /* synthetic */ AssetEntity toAssetEntity$default(IDBUtils iDBUtils, Cursor cursor, Context context, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toAssetEntity");
            }
            if ((i & 2) != 0) {
                z = true;
            }
            return iDBUtils.toAssetEntity(cursor, context, z);
        }

        /* JADX WARN: Removed duplicated region for block: B:51:0x0110  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static com.fluttercandies.photo_manager.core.entity.AssetEntity toAssetEntity(com.fluttercandies.photo_manager.core.utils.IDBUtils r32, android.database.Cursor r33, android.content.Context r34, boolean r35) {
            /*
                Method dump skipped, instruction units count: 376
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.fluttercandies.photo_manager.core.utils.IDBUtils.DefaultImpls.toAssetEntity(com.fluttercandies.photo_manager.core.utils.IDBUtils, android.database.Cursor, android.content.Context, boolean):com.fluttercandies.photo_manager.core.entity.AssetEntity");
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r7v0, types: [T, java.io.ByteArrayInputStream] */
        public static AssetEntity saveImage(IDBUtils iDBUtils, Context context, byte[] bytes, String title, String desc, String str) throws IOException {
            Pair pair;
            Pair pair2;
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(bytes, "bytes");
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(desc, "desc");
            Ref.ObjectRef objectRef = new Ref.ObjectRef();
            objectRef.element = new ByteArrayInputStream(bytes);
            long j = 1000;
            long jCurrentTimeMillis = System.currentTimeMillis() / j;
            try {
                Bitmap bitmapDecodeStream = BitmapFactory.decodeStream((InputStream) objectRef.element);
                pair = new Pair(Integer.valueOf(bitmapDecodeStream.getWidth()), Integer.valueOf(bitmapDecodeStream.getHeight()));
            } catch (Exception unused) {
                pair = new Pair(0, 0);
            }
            int iIntValue = ((Number) pair.component1()).intValue();
            int iIntValue2 = ((Number) pair.component2()).intValue();
            String strGuessContentTypeFromName = URLConnection.guessContentTypeFromName(title);
            if (strGuessContentTypeFromName == null) {
                strGuessContentTypeFromName = URLConnection.guessContentTypeFromStream((InputStream) objectRef.element);
            }
            if (strGuessContentTypeFromName == null) {
                strGuessContentTypeFromName = "image/*";
            }
            try {
                ExifInterface exifInterface = new ExifInterface((InputStream) objectRef.element);
                pair2 = new Pair(Integer.valueOf(IDBUtils.INSTANCE.isAboveAndroidQ() ? exifInterface.getRotationDegrees() : 0), IDBUtils.INSTANCE.isAboveAndroidQ() ? null : exifInterface.getLatLong());
            } catch (Exception unused2) {
                pair2 = new Pair(0, null);
            }
            int iIntValue3 = ((Number) pair2.component1()).intValue();
            double[] dArr = (double[]) pair2.component2();
            saveImage$refreshInputStream(objectRef, bytes);
            ContentValues contentValues = new ContentValues();
            contentValues.put("media_type", (Integer) 1);
            contentValues.put("description", desc);
            contentValues.put("_display_name", title);
            contentValues.put("mime_type", strGuessContentTypeFromName);
            contentValues.put("title", title);
            contentValues.put("date_added", Long.valueOf(jCurrentTimeMillis));
            contentValues.put("date_modified", Long.valueOf(jCurrentTimeMillis));
            contentValues.put("width", Integer.valueOf(iIntValue));
            contentValues.put("height", Integer.valueOf(iIntValue2));
            if (IDBUtils.INSTANCE.isAboveAndroidQ()) {
                contentValues.put("datetaken", Long.valueOf(jCurrentTimeMillis * j));
                contentValues.put("orientation", Integer.valueOf(iIntValue3));
                if (str != null) {
                    contentValues.put("relative_path", str);
                }
            }
            if (dArr != null) {
                contentValues.put("latitude", Double.valueOf(ArraysKt.first(dArr)));
                contentValues.put("longitude", Double.valueOf(ArraysKt.last(dArr)));
            }
            InputStream inputStream = (InputStream) objectRef.element;
            Uri EXTERNAL_CONTENT_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Intrinsics.checkNotNullExpressionValue(EXTERNAL_CONTENT_URI, "EXTERNAL_CONTENT_URI");
            return insertUri$default(iDBUtils, context, inputStream, EXTERNAL_CONTENT_URI, contentValues, false, 16, null);
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [T, java.io.ByteArrayInputStream] */
        private static void saveImage$refreshInputStream(Ref.ObjectRef<ByteArrayInputStream> objectRef, byte[] bArr) {
            objectRef.element = new ByteArrayInputStream(bArr);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r8v0, types: [T, java.io.FileInputStream] */
        public static AssetEntity saveImage(IDBUtils iDBUtils, Context context, String fromPath, String title, String desc, String str) throws IOException {
            Pair pair;
            Pair pair2;
            double[] dArr;
            Ref.ObjectRef objectRef;
            boolean zStartsWith$default;
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(fromPath, "fromPath");
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(desc, "desc");
            CommonExtKt.checkDirs(fromPath);
            File file = new File(fromPath);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = new FileInputStream(file);
            long j = 1000;
            long jCurrentTimeMillis = System.currentTimeMillis() / j;
            try {
                Bitmap bitmapDecodeStream = BitmapFactory.decodeStream((InputStream) objectRef2.element);
                pair = new Pair(Integer.valueOf(bitmapDecodeStream.getWidth()), Integer.valueOf(bitmapDecodeStream.getHeight()));
            } catch (Exception unused) {
                pair = new Pair(0, 0);
            }
            int iIntValue = ((Number) pair.component1()).intValue();
            int iIntValue2 = ((Number) pair.component2()).intValue();
            String strGuessContentTypeFromName = URLConnection.guessContentTypeFromName(title);
            if (strGuessContentTypeFromName == null && (strGuessContentTypeFromName = URLConnection.guessContentTypeFromName(fromPath)) == null) {
                strGuessContentTypeFromName = URLConnection.guessContentTypeFromStream((InputStream) objectRef2.element);
            }
            if (strGuessContentTypeFromName == null) {
                strGuessContentTypeFromName = "image/*";
            }
            try {
                ExifInterface exifInterface = new ExifInterface((InputStream) objectRef2.element);
                pair2 = new Pair(Integer.valueOf(IDBUtils.INSTANCE.isAboveAndroidQ() ? exifInterface.getRotationDegrees() : 0), IDBUtils.INSTANCE.isAboveAndroidQ() ? null : exifInterface.getLatLong());
            } catch (Exception unused2) {
                pair2 = new Pair(0, null);
            }
            int iIntValue3 = ((Number) pair2.component1()).intValue();
            double[] dArr2 = (double[]) pair2.component2();
            saveImage$refreshInputStream$4(objectRef2, file);
            if (IDBUtils.INSTANCE.isAboveAndroidQ()) {
                dArr = dArr2;
                objectRef = objectRef2;
                zStartsWith$default = false;
            } else {
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                String absolutePath = file.getAbsolutePath();
                objectRef = objectRef2;
                Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                String path = externalStorageDirectory.getPath();
                Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
                dArr = dArr2;
                zStartsWith$default = StringsKt.startsWith$default(absolutePath, path, false, 2, (Object) null);
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("media_type", (Integer) 1);
            contentValues.put("description", desc);
            contentValues.put("_display_name", title);
            contentValues.put("mime_type", strGuessContentTypeFromName);
            contentValues.put("title", title);
            contentValues.put("date_added", Long.valueOf(jCurrentTimeMillis));
            contentValues.put("date_modified", Long.valueOf(jCurrentTimeMillis));
            contentValues.put("width", Integer.valueOf(iIntValue));
            contentValues.put("height", Integer.valueOf(iIntValue2));
            if (IDBUtils.INSTANCE.isAboveAndroidQ()) {
                contentValues.put("datetaken", Long.valueOf(jCurrentTimeMillis * j));
                contentValues.put("orientation", Integer.valueOf(iIntValue3));
                if (str != null) {
                    contentValues.put("relative_path", str);
                }
            }
            if (dArr != null) {
                contentValues.put("latitude", Double.valueOf(ArraysKt.first(dArr)));
                contentValues.put("longitude", Double.valueOf(ArraysKt.last(dArr)));
            }
            if (zStartsWith$default) {
                contentValues.put("_data", fromPath);
            }
            InputStream inputStream = (InputStream) objectRef.element;
            Uri EXTERNAL_CONTENT_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Intrinsics.checkNotNullExpressionValue(EXTERNAL_CONTENT_URI, "EXTERNAL_CONTENT_URI");
            return insertUri(iDBUtils, context, inputStream, EXTERNAL_CONTENT_URI, contentValues, zStartsWith$default);
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [T, java.io.FileInputStream] */
        private static void saveImage$refreshInputStream$4(Ref.ObjectRef<FileInputStream> objectRef, File file) {
            objectRef.element = new FileInputStream(file);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r8v0, types: [T, java.io.FileInputStream] */
        public static AssetEntity saveVideo(IDBUtils iDBUtils, Context context, String fromPath, String title, String desc, String str) throws IOException {
            Pair pair;
            Ref.ObjectRef objectRef;
            double[] dArr;
            boolean zStartsWith$default;
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(fromPath, "fromPath");
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(desc, "desc");
            CommonExtKt.checkDirs(fromPath);
            File file = new File(fromPath);
            Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
            objectRef2.element = new FileInputStream(file);
            long j = 1000;
            long jCurrentTimeMillis = System.currentTimeMillis() / j;
            VideoUtils.VideoInfo propertiesUseMediaPlayer = VideoUtils.INSTANCE.getPropertiesUseMediaPlayer(fromPath);
            String strGuessContentTypeFromName = URLConnection.guessContentTypeFromName(title);
            if (strGuessContentTypeFromName == null) {
                strGuessContentTypeFromName = URLConnection.guessContentTypeFromName(fromPath);
            }
            if (strGuessContentTypeFromName == null) {
                strGuessContentTypeFromName = "video/*";
            }
            try {
                ExifInterface exifInterface = new ExifInterface((InputStream) objectRef2.element);
                pair = new Pair(Integer.valueOf(IDBUtils.INSTANCE.isAboveAndroidQ() ? exifInterface.getRotationDegrees() : 0), IDBUtils.INSTANCE.isAboveAndroidQ() ? null : exifInterface.getLatLong());
            } catch (Exception unused) {
                pair = new Pair(0, null);
            }
            int iIntValue = ((Number) pair.component1()).intValue();
            double[] dArr2 = (double[]) pair.component2();
            saveVideo$refreshInputStream$6(objectRef2, file);
            if (IDBUtils.INSTANCE.isAboveAndroidQ()) {
                objectRef = objectRef2;
                dArr = dArr2;
                zStartsWith$default = false;
            } else {
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                String absolutePath = file.getAbsolutePath();
                objectRef = objectRef2;
                Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                String path = externalStorageDirectory.getPath();
                Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
                dArr = dArr2;
                zStartsWith$default = StringsKt.startsWith$default(absolutePath, path, false, 2, (Object) null);
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("media_type", (Integer) 3);
            contentValues.put("description", desc);
            contentValues.put("title", title);
            contentValues.put("_display_name", title);
            contentValues.put("mime_type", strGuessContentTypeFromName);
            contentValues.put("date_added", Long.valueOf(jCurrentTimeMillis));
            contentValues.put("date_modified", Long.valueOf(jCurrentTimeMillis));
            contentValues.put("duration", propertiesUseMediaPlayer.getDuration());
            contentValues.put("width", propertiesUseMediaPlayer.getWidth());
            contentValues.put("height", propertiesUseMediaPlayer.getHeight());
            if (IDBUtils.INSTANCE.isAboveAndroidQ()) {
                contentValues.put("datetaken", Long.valueOf(jCurrentTimeMillis * j));
                contentValues.put("orientation", Integer.valueOf(iIntValue));
                if (str != null) {
                    contentValues.put("relative_path", str);
                }
            }
            if (dArr != null) {
                contentValues.put("latitude", Double.valueOf(ArraysKt.first(dArr)));
                contentValues.put("longitude", Double.valueOf(ArraysKt.last(dArr)));
            }
            if (zStartsWith$default) {
                contentValues.put("_data", fromPath);
            }
            InputStream inputStream = (InputStream) objectRef.element;
            Uri EXTERNAL_CONTENT_URI = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            Intrinsics.checkNotNullExpressionValue(EXTERNAL_CONTENT_URI, "EXTERNAL_CONTENT_URI");
            return insertUri(iDBUtils, context, inputStream, EXTERNAL_CONTENT_URI, contentValues, zStartsWith$default);
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [T, java.io.FileInputStream] */
        private static void saveVideo$refreshInputStream$6(Ref.ObjectRef<FileInputStream> objectRef, File file) {
            objectRef.element = new FileInputStream(file);
        }

        public static /* synthetic */ AssetEntity insertUri$default(IDBUtils iDBUtils, Context context, InputStream inputStream, Uri uri, ContentValues contentValues, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: insertUri");
            }
            if ((i & 16) != 0) {
                z = false;
            }
            return insertUri(iDBUtils, context, inputStream, uri, contentValues, z);
        }

        private static AssetEntity insertUri(IDBUtils iDBUtils, Context context, InputStream inputStream, Uri uri, ContentValues contentValues, boolean z) throws FileNotFoundException {
            ContentResolver contentResolver = context.getContentResolver();
            Uri uriInsert = contentResolver.insert(uri, contentValues);
            if (uriInsert == null) {
                throw new RuntimeException("Cannot insert the new asset.");
            }
            long id = ContentUris.parseId(uriInsert);
            if (!z) {
                OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(uriInsert);
                if (outputStreamOpenOutputStream == null) {
                    throw new RuntimeException("Cannot open the output stream for " + uriInsert + ".");
                }
                InputStream inputStream2 = outputStreamOpenOutputStream;
                try {
                    inputStream2 = inputStream;
                    try {
                        ByteStreamsKt.copyTo$default(inputStream2, inputStream2, 0, 2, null);
                        CloseableKt.closeFinally(inputStream2, null);
                        CloseableKt.closeFinally(inputStream2, null);
                    } finally {
                    }
                } finally {
                }
            }
            contentResolver.notifyChange(uriInsert, null);
            return getAssetEntity$default(iDBUtils, context, String.valueOf(id), false, 4, null);
        }

        public static boolean assetExists(IDBUtils iDBUtils, Context context, String id) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(id, "id");
            ContentResolver contentResolver = context.getContentResolver();
            Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
            Cursor cursorLogQuery = iDBUtils.logQuery(contentResolver, iDBUtils.getAllUri(), new String[]{"_id"}, "_id = ?", new String[]{id}, null);
            try {
                Cursor cursor = cursorLogQuery;
                if (cursor == null) {
                    CloseableKt.closeFinally(cursorLogQuery, null);
                    return false;
                }
                boolean z = cursor.getCount() >= 1;
                CloseableKt.closeFinally(cursorLogQuery, null);
                return z;
            } finally {
            }
        }

        public static void logRowWithId(IDBUtils iDBUtils, Context context, String id) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(id, "id");
            if (LogUtils.INSTANCE.isLog()) {
                String strPadStart = StringsKt.padStart("", 40, '-');
                LogUtils.info("log error row " + id + " start " + strPadStart);
                ContentResolver contentResolver = context.getContentResolver();
                Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
                Cursor cursorLogQuery = iDBUtils.logQuery(contentResolver, iDBUtils.getAllUri(), null, "_id = ?", new String[]{id}, null);
                if (cursorLogQuery != null) {
                    Cursor cursor = cursorLogQuery;
                    try {
                        Cursor cursor2 = cursor;
                        String[] columnNames = cursor2.getColumnNames();
                        if (cursor2.moveToNext()) {
                            Intrinsics.checkNotNull(columnNames);
                            int length = columnNames.length;
                            for (int i = 0; i < length; i++) {
                                LogUtils.info(columnNames[i] + " : " + cursor2.getString(i));
                            }
                        }
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(cursor, null);
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(cursor, th);
                            throw th2;
                        }
                    }
                }
                LogUtils.info("log error row " + id + " end " + strPadStart);
            }
        }

        public static String getMediaUri(IDBUtils iDBUtils, Context context, long j, int i) {
            Intrinsics.checkNotNullParameter(context, "context");
            String string = iDBUtils.getUri(j, i, false).toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }

        public static String getSortOrder(IDBUtils iDBUtils, int i, int i2, FilterOption filterOption) {
            Intrinsics.checkNotNullParameter(filterOption, "filterOption");
            return filterOption.orderByCondString() + " LIMIT " + i2 + " OFFSET " + i;
        }

        public static /* synthetic */ Uri getUri$default(IDBUtils iDBUtils, long j, int i, boolean z, int i2, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getUri");
            }
            if ((i2 & 4) != 0) {
                z = false;
            }
            return iDBUtils.getUri(j, i, z);
        }

        public static Uri getUri(IDBUtils iDBUtils, long j, int i, boolean z) {
            Uri uriWithAppendedId;
            if (i == 1) {
                uriWithAppendedId = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, j);
            } else if (i == 2) {
                uriWithAppendedId = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, j);
            } else if (i == 3) {
                uriWithAppendedId = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, j);
            } else {
                Uri EMPTY = Uri.EMPTY;
                Intrinsics.checkNotNullExpressionValue(EMPTY, "EMPTY");
                return EMPTY;
            }
            Intrinsics.checkNotNull(uriWithAppendedId);
            if (!z) {
                return uriWithAppendedId;
            }
            Uri requireOriginal = MediaStore.setRequireOriginal(uriWithAppendedId);
            Intrinsics.checkNotNullExpressionValue(requireOriginal, "setRequireOriginal(...)");
            return requireOriginal;
        }

        public static Void throwMsg(IDBUtils iDBUtils, String msg) {
            Intrinsics.checkNotNullParameter(msg, "msg");
            throw new RuntimeException(msg);
        }

        public static List<String> getAssetsPath(IDBUtils iDBUtils, Context context, List<String> ids) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(ids, "ids");
            List<String> list = ids;
            int i = 0;
            if (list.size() > 500) {
                ArrayList arrayList = new ArrayList();
                int size = list.size();
                int i2 = size / 500;
                if (size % 500 != 0) {
                    i2++;
                }
                while (i < i2) {
                    arrayList.addAll(iDBUtils.getAssetsPath(context, ids.subList(i * 500, i == i2 + (-1) ? list.size() : ((i + 1) * 500) - 1)));
                    i++;
                }
                return arrayList;
            }
            String str = "_id in (" + CollectionsKt.joinToString$default(ids, ",", null, null, 0, null, new Function1<String, CharSequence>() { // from class: com.fluttercandies.photo_manager.core.utils.IDBUtils$getAssetsPath$idSelection$1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return "?";
                }
            }, 30, null) + ")";
            ContentResolver contentResolver = context.getContentResolver();
            Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
            Cursor cursorLogQuery = iDBUtils.logQuery(contentResolver, iDBUtils.getAllUri(), new String[]{"_id", "media_type", "_data"}, str, (String[]) list.toArray(new String[0]), null);
            if (cursorLogQuery == null) {
                return CollectionsKt.emptyList();
            }
            ArrayList arrayList2 = new ArrayList();
            HashMap map = new HashMap();
            Cursor cursor = cursorLogQuery;
            try {
                Cursor cursor2 = cursor;
                while (cursor2.moveToNext()) {
                    map.put(iDBUtils.getString(cursor2, "_id"), iDBUtils.getString(cursor2, "_data"));
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(cursor, null);
                Iterator<String> it = ids.iterator();
                while (it.hasNext()) {
                    String str2 = (String) map.get(it.next());
                    if (str2 != null) {
                        arrayList2.add(str2);
                    }
                }
                return arrayList2;
            } finally {
            }
        }

        public static void injectModifiedDate(IDBUtils iDBUtils, Context context, AssetPathEntity entity) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(entity, "entity");
            Long pathModifiedDate = iDBUtils.getPathModifiedDate(context, entity.getId());
            if (pathModifiedDate != null) {
                entity.setModifiedDate(Long.valueOf(pathModifiedDate.longValue()));
            }
        }

        public static Long getPathModifiedDate(IDBUtils iDBUtils, Context context, String pathId) {
            Cursor cursorLogQuery;
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(pathId, "pathId");
            String[] strArr = {"date_modified"};
            if (Intrinsics.areEqual(pathId, PhotoManager.ALL_ID)) {
                ContentResolver contentResolver = context.getContentResolver();
                Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
                cursorLogQuery = iDBUtils.logQuery(contentResolver, iDBUtils.getAllUri(), strArr, null, null, "date_modified desc");
            } else {
                ContentResolver contentResolver2 = context.getContentResolver();
                Intrinsics.checkNotNullExpressionValue(contentResolver2, "getContentResolver(...)");
                cursorLogQuery = iDBUtils.logQuery(contentResolver2, iDBUtils.getAllUri(), strArr, "bucket_id = ?", new String[]{pathId}, "date_modified desc");
            }
            if (cursorLogQuery == null) {
                return null;
            }
            Cursor cursor = cursorLogQuery;
            try {
                Cursor cursor2 = cursor;
                if (cursor2.moveToNext()) {
                    Long lValueOf = Long.valueOf(iDBUtils.getLong(cursor2, "date_modified"));
                    CloseableKt.closeFinally(cursor, null);
                    return lValueOf;
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(cursor, null);
                return null;
            } finally {
            }
        }

        public static List<String> getColumnNames(IDBUtils iDBUtils, Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            ContentResolver contentResolver = context.getContentResolver();
            Intrinsics.checkNotNull(contentResolver);
            Cursor cursorLogQuery = iDBUtils.logQuery(contentResolver, iDBUtils.getAllUri(), null, null, null, null);
            if (cursorLogQuery != null) {
                Cursor cursor = cursorLogQuery;
                try {
                    String[] columnNames = cursor.getColumnNames();
                    Intrinsics.checkNotNullExpressionValue(columnNames, "getColumnNames(...)");
                    List<String> list = ArraysKt.toList(columnNames);
                    CloseableKt.closeFinally(cursor, null);
                    return list;
                } finally {
                }
            } else {
                return CollectionsKt.emptyList();
            }
        }

        private static void logQuery$log(Uri uri, String[] strArr, String str, String[] strArr2, String str2, Function1<? super String, Unit> function1, Cursor cursor) {
            String str3;
            String strReplace$default;
            if (LogUtils.INSTANCE.isLog()) {
                StringBuilder sb = new StringBuilder();
                StringBuilder sbAppend = sb.append("uri: " + uri);
                Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
                Intrinsics.checkNotNullExpressionValue(sbAppend.append('\n'), "append(...)");
                StringBuilder sbAppend2 = sb.append("projection: " + (strArr != null ? ArraysKt.joinToString$default(strArr, ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null) : null));
                Intrinsics.checkNotNullExpressionValue(sbAppend2, "append(...)");
                Intrinsics.checkNotNullExpressionValue(sbAppend2.append('\n'), "append(...)");
                StringBuilder sbAppend3 = sb.append("selection: " + str);
                Intrinsics.checkNotNullExpressionValue(sbAppend3, "append(...)");
                Intrinsics.checkNotNullExpressionValue(sbAppend3.append('\n'), "append(...)");
                StringBuilder sbAppend4 = sb.append("selectionArgs: " + (strArr2 != null ? ArraysKt.joinToString$default(strArr2, ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null) : null));
                Intrinsics.checkNotNullExpressionValue(sbAppend4, "append(...)");
                Intrinsics.checkNotNullExpressionValue(sbAppend4.append('\n'), "append(...)");
                StringBuilder sbAppend5 = sb.append("sortOrder: " + str2);
                Intrinsics.checkNotNullExpressionValue(sbAppend5, "append(...)");
                Intrinsics.checkNotNullExpressionValue(sbAppend5.append('\n'), "append(...)");
                if (str == null || (strReplace$default = StringsKt.replace$default(str, "?", "%s", false, 4, (Object) null)) == null) {
                    str3 = null;
                } else {
                    Object[] objArr = strArr2 == null ? new Object[0] : strArr2;
                    Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length);
                    str3 = String.format(strReplace$default, Arrays.copyOf(objArrCopyOf, objArrCopyOf.length));
                    Intrinsics.checkNotNullExpressionValue(str3, "format(...)");
                }
                StringBuilder sbAppend6 = sb.append("sql: " + str3);
                Intrinsics.checkNotNullExpressionValue(sbAppend6, "append(...)");
                Intrinsics.checkNotNullExpressionValue(sbAppend6.append('\n'), "append(...)");
                StringBuilder sbAppend7 = sb.append("cursor count: " + (cursor != null ? Integer.valueOf(cursor.getCount()) : null));
                Intrinsics.checkNotNullExpressionValue(sbAppend7, "append(...)");
                Intrinsics.checkNotNullExpressionValue(sbAppend7.append('\n'), "append(...)");
                String string = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                function1.invoke(string);
            }
        }

        public static Cursor logQuery(IDBUtils iDBUtils, ContentResolver receiver, Uri uri, String[] strArr, String str, String[] strArr2, String str2) throws Exception {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(uri, "uri");
            try {
                Cursor cursorQuery = receiver.query(uri, strArr, str, strArr2, str2);
                logQuery$log(uri, strArr, str, strArr2, str2, new AnonymousClass1(LogUtils.INSTANCE), cursorQuery);
                return cursorQuery;
            } catch (Exception e) {
                logQuery$log(uri, strArr, str, strArr2, str2, new AnonymousClass2(LogUtils.INSTANCE), null);
                LogUtils.error("happen query error", e);
                throw e;
            }
        }

        public static int getAssetCount(IDBUtils iDBUtils, Context context, FilterOption option, int i) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(option, "option");
            ContentResolver contentResolver = context.getContentResolver();
            ArrayList<String> arrayList = new ArrayList<>();
            String strMakeWhere = option.makeWhere(i, arrayList, false);
            String strOrderByCondString = option.orderByCondString();
            Intrinsics.checkNotNull(contentResolver);
            Cursor cursorLogQuery = iDBUtils.logQuery(contentResolver, iDBUtils.getAllUri(), new String[]{"_id"}, strMakeWhere, (String[]) arrayList.toArray(new String[0]), strOrderByCondString);
            try {
                Cursor cursor = cursorLogQuery;
                int count = cursor != null ? cursor.getCount() : 0;
                CloseableKt.closeFinally(cursorLogQuery, null);
                return count;
            } finally {
            }
        }

        public static int getAssetCount(IDBUtils iDBUtils, Context context, FilterOption option, int i, String galleryId) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(option, "option");
            Intrinsics.checkNotNullParameter(galleryId, "galleryId");
            ContentResolver contentResolver = context.getContentResolver();
            ArrayList<String> arrayList = new ArrayList<>();
            StringBuilder sb = new StringBuilder(option.makeWhere(i, arrayList, false));
            if (!Intrinsics.areEqual(galleryId, PhotoManager.ALL_ID)) {
                if (StringsKt.trim(sb).length() > 0) {
                    sb.append(" AND ");
                }
                sb.append("bucket_id = ?");
                arrayList.add(galleryId);
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            String strOrderByCondString = option.orderByCondString();
            Intrinsics.checkNotNull(contentResolver);
            Cursor cursorLogQuery = iDBUtils.logQuery(contentResolver, iDBUtils.getAllUri(), new String[]{"_id"}, string, (String[]) arrayList.toArray(new String[0]), strOrderByCondString);
            try {
                Cursor cursor = cursorLogQuery;
                int count = cursor != null ? cursor.getCount() : 0;
                CloseableKt.closeFinally(cursorLogQuery, null);
                return count;
            } finally {
            }
        }

        public static List<AssetEntity> getAssetsByRange(IDBUtils iDBUtils, Context context, FilterOption option, int i, int i2, int i3) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(option, "option");
            ContentResolver contentResolver = context.getContentResolver();
            ArrayList<String> arrayList = new ArrayList<>();
            String strMakeWhere = option.makeWhere(i3, arrayList, false);
            String strOrderByCondString = option.orderByCondString();
            Intrinsics.checkNotNull(contentResolver);
            Cursor cursorLogQuery = iDBUtils.logQuery(contentResolver, iDBUtils.getAllUri(), iDBUtils.keys(), strMakeWhere, (String[]) arrayList.toArray(new String[0]), strOrderByCondString);
            if (cursorLogQuery != null) {
                Cursor cursor = cursorLogQuery;
                try {
                    Cursor cursor2 = cursor;
                    ArrayList arrayList2 = new ArrayList();
                    cursor2.moveToPosition(i - 1);
                    while (cursor2.moveToNext()) {
                        AssetEntity assetEntity = iDBUtils.toAssetEntity(cursor2, context, false);
                        if (assetEntity != null) {
                            arrayList2.add(assetEntity);
                            if (arrayList2.size() == i2 - i) {
                                break;
                            }
                        }
                    }
                    ArrayList arrayList3 = arrayList2;
                    CloseableKt.closeFinally(cursor, null);
                    return arrayList3;
                } finally {
                }
            } else {
                return CollectionsKt.emptyList();
            }
        }
    }

    /* JADX INFO: renamed from: com.fluttercandies.photo_manager.core.utils.IDBUtils$logQuery$1, reason: invalid class name */
    /* JADX INFO: compiled from: IDBUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1<Object, Unit> {
        AnonymousClass1(Object obj) {
            super(1, obj, LogUtils.class, "info", "info(Ljava/lang/Object;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
            invoke2(obj);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Object obj) {
            LogUtils.info(obj);
        }
    }

    /* JADX INFO: renamed from: com.fluttercandies.photo_manager.core.utils.IDBUtils$logQuery$2, reason: invalid class name */
    /* JADX INFO: compiled from: IDBUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass2 extends FunctionReferenceImpl implements Function1<Object, Unit> {
        AnonymousClass2(Object obj) {
            super(1, obj, LogUtils.class, Constant.PARAM_ERROR, "error(Ljava/lang/Object;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
            invoke2(obj);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(Object obj) {
            LogUtils.error(obj);
        }
    }
}
