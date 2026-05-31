package com.fluttercandies.photo_manager.core.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import cn.yoozworld.watch.utils.notifi.NotificationManager;
import com.fluttercandies.photo_manager.constant.Methods;
import com.fluttercandies.photo_manager.core.PhotoManager;
import com.fluttercandies.photo_manager.core.entity.AssetEntity;
import com.fluttercandies.photo_manager.core.entity.AssetPathEntity;
import com.fluttercandies.photo_manager.core.entity.filter.FilterOption;
import com.fluttercandies.photo_manager.core.utils.IDBUtils;
import com.google.android.gms.fido.u2f.api.common.ClientData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: DBUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001:\u00014B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0007H\u0016J\"\u0010\u000f\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J>\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\n0\u00142\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J>\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\n0\u00142\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J*\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00072\u0006\u0010!\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J&\u0010\"\u001a\b\u0012\u0004\u0012\u00020 0\u00142\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u001a\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0007H\u0016J\"\u0010%\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00072\u0006\u0010&\u001a\u00020\u0012H\u0016J\u001a\u0010'\u001a\u0004\u0018\u00010(2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u0007H\u0002J&\u0010)\u001a\b\u0012\u0004\u0012\u00020 0\u00142\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J \u0010*\u001a\u00020+2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010,\u001a\u00020\n2\u0006\u0010-\u001a\u00020\u0012H\u0016J(\u0010.\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0018\u00010/2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0007H\u0016J\u0013\u00100\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u00101J\"\u00102\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0007H\u0016J\u0010\u00103\u001a\u00020\u00122\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\b¨\u00065"}, d2 = {"Lcom/fluttercandies/photo_manager/core/utils/DBUtils;", "Lcom/fluttercandies/photo_manager/core/utils/IDBUtils;", "()V", "deleteLock", "Ljava/util/concurrent/locks/ReentrantLock;", "locationKeys", "", "", "[Ljava/lang/String;", "copyToGallery", "Lcom/fluttercandies/photo_manager/core/entity/AssetEntity;", "context", "Landroid/content/Context;", "assetId", "galleryId", "getAssetEntity", "id", "checkIfExists", "", Methods.getAssetListPaged, "", "pathId", "page", "", "size", "requestType", "option", "Lcom/fluttercandies/photo_manager/core/entity/filter/FilterOption;", Methods.getAssetListRange, "start", "end", "getAssetPathEntityFromId", "Lcom/fluttercandies/photo_manager/core/entity/AssetPathEntity;", NotificationManager.BUNDLE_TYPE, Methods.getAssetPathList, "getExif", "Landroidx/exifinterface/media/ExifInterface;", "getFilePath", ClientData.KEY_ORIGIN, "getGalleryInfo", "Lcom/fluttercandies/photo_manager/core/utils/DBUtils$GalleryInfo;", "getMainAssetPathEntity", Methods.getOriginBytes, "", "asset", "needLocationPermission", "getSomeInfo", "Lkotlin/Pair;", "keys", "()[Ljava/lang/String;", "moveToGallery", "removeAllExistsAssets", "GalleryInfo", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DBUtils implements IDBUtils {
    public static final DBUtils INSTANCE = new DBUtils();
    private static final String[] locationKeys = {"longitude", "latitude"};
    private static final ReentrantLock deleteLock = new ReentrantLock();

    private DBUtils() {
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public boolean assetExists(Context context, String str) {
        return IDBUtils.DefaultImpls.assetExists(this, context, str);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public void clearFileCache(Context context) {
        IDBUtils.DefaultImpls.clearFileCache(this, context);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public int convertTypeToMediaType(int i) {
        return IDBUtils.DefaultImpls.convertTypeToMediaType(this, i);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public Uri getAllUri() {
        return IDBUtils.DefaultImpls.getAllUri(this);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public int getAssetCount(Context context, FilterOption filterOption, int i) {
        return IDBUtils.DefaultImpls.getAssetCount(this, context, filterOption, i);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public int getAssetCount(Context context, FilterOption filterOption, int i, String str) {
        return IDBUtils.DefaultImpls.getAssetCount(this, context, filterOption, i, str);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public List<AssetEntity> getAssetsByRange(Context context, FilterOption filterOption, int i, int i2, int i3) {
        return IDBUtils.DefaultImpls.getAssetsByRange(this, context, filterOption, i, i2, i3);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public List<String> getAssetsPath(Context context, List<String> list) {
        return IDBUtils.DefaultImpls.getAssetsPath(this, context, list);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public List<String> getColumnNames(Context context) {
        return IDBUtils.DefaultImpls.getColumnNames(this, context);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public String getIdSelection() {
        return IDBUtils.DefaultImpls.getIdSelection(this);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public int getInt(Cursor cursor, String str) {
        return IDBUtils.DefaultImpls.getInt(this, cursor, str);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public long getLong(Cursor cursor, String str) {
        return IDBUtils.DefaultImpls.getLong(this, cursor, str);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public int getMediaType(int i) {
        return IDBUtils.DefaultImpls.getMediaType(this, i);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public String getMediaUri(Context context, long j, int i) {
        return IDBUtils.DefaultImpls.getMediaUri(this, context, j, i);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public Long getPathModifiedDate(Context context, String str) {
        return IDBUtils.DefaultImpls.getPathModifiedDate(this, context, str);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public String getSortOrder(int i, int i2, FilterOption filterOption) {
        return IDBUtils.DefaultImpls.getSortOrder(this, i, i2, filterOption);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public String getString(Cursor cursor, String str) {
        return IDBUtils.DefaultImpls.getString(this, cursor, str);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public String getStringOrNull(Cursor cursor, String str) {
        return IDBUtils.DefaultImpls.getStringOrNull(this, cursor, str);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public int getTypeFromMediaType(int i) {
        return IDBUtils.DefaultImpls.getTypeFromMediaType(this, i);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public Uri getUri(long j, int i, boolean z) {
        return IDBUtils.DefaultImpls.getUri(this, j, i, z);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public void injectModifiedDate(Context context, AssetPathEntity assetPathEntity) {
        IDBUtils.DefaultImpls.injectModifiedDate(this, context, assetPathEntity);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public Cursor logQuery(ContentResolver contentResolver, Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return IDBUtils.DefaultImpls.logQuery(this, contentResolver, uri, strArr, str, strArr2, str2);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public void logRowWithId(Context context, String str) {
        IDBUtils.DefaultImpls.logRowWithId(this, context, str);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public AssetEntity saveImage(Context context, String str, String str2, String str3, String str4) {
        return IDBUtils.DefaultImpls.saveImage(this, context, str, str2, str3, str4);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public AssetEntity saveImage(Context context, byte[] bArr, String str, String str2, String str3) {
        return IDBUtils.DefaultImpls.saveImage(this, context, bArr, str, str2, str3);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public AssetEntity saveVideo(Context context, String str, String str2, String str3, String str4) {
        return IDBUtils.DefaultImpls.saveVideo(this, context, str, str2, str3, str4);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public Void throwMsg(String str) {
        return IDBUtils.DefaultImpls.throwMsg(this, str);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public AssetEntity toAssetEntity(Cursor cursor, Context context, boolean z) {
        return IDBUtils.DefaultImpls.toAssetEntity(this, cursor, context, z);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public String[] keys() {
        return (String[]) CollectionsKt.distinct(CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) IDBUtils.INSTANCE.getStoreImageKeys(), (Iterable) IDBUtils.INSTANCE.getStoreVideoKeys()), (Object[]) IDBUtils.INSTANCE.getTypeKeys()), (Object[]) locationKeys)).toArray(new String[0]);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public List<AssetPathEntity> getAssetPathList(Context context, int requestType, FilterOption option) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(option, "option");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        String str = "bucket_id IS NOT NULL " + FilterOption.makeWhere$default(option, requestType, arrayList2, false, 4, null) + ") GROUP BY (bucket_id";
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        Cursor cursorLogQuery = logQuery(contentResolver, getAllUri(), (String[]) ArraysKt.plus((Object[]) IDBUtils.INSTANCE.getStoreBucketKeys(), (Object[]) new String[]{"count(1)"}), str, (String[]) arrayList2.toArray(new String[0]), null);
        if (cursorLogQuery == null) {
            return arrayList;
        }
        Cursor cursor = cursorLogQuery;
        try {
            Cursor cursor2 = cursor;
            while (cursor2.moveToNext()) {
                String string = cursor2.getString(0);
                String string2 = cursor2.getString(1);
                if (string2 == null) {
                    string2 = "";
                } else {
                    Intrinsics.checkNotNull(string2);
                }
                int i = cursor2.getInt(2);
                Intrinsics.checkNotNull(string);
                AssetPathEntity assetPathEntity = new AssetPathEntity(string, string2, i, 0, false, null, 48, null);
                if (option.getContainsPathModified()) {
                    INSTANCE.injectModifiedDate(context, assetPathEntity);
                }
                arrayList.add(assetPathEntity);
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(cursor, null);
            return arrayList;
        } finally {
        }
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public List<AssetPathEntity> getMainAssetPathEntity(Context context, int requestType, FilterOption option) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(option, "option");
        ArrayList arrayList = new ArrayList();
        String[] strArr = (String[]) ArraysKt.plus((Object[]) IDBUtils.INSTANCE.getStoreBucketKeys(), (Object[]) new String[]{"count(1)"});
        ArrayList arrayList2 = new ArrayList();
        String str = "bucket_id IS NOT NULL " + FilterOption.makeWhere$default(option, requestType, arrayList2, false, 4, null);
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        Cursor cursorLogQuery = logQuery(contentResolver, getAllUri(), strArr, str, (String[]) arrayList2.toArray(new String[0]), null);
        if (cursorLogQuery == null) {
            return arrayList;
        }
        Cursor cursor = cursorLogQuery;
        try {
            Cursor cursor2 = cursor;
            if (cursor2.moveToNext()) {
                arrayList.add(new AssetPathEntity(PhotoManager.ALL_ID, PhotoManager.ALL_ALBUM_NAME, cursor2.getInt(ArraysKt.indexOf(strArr, "count(1)")), requestType, true, null, 32, null));
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(cursor, null);
            return arrayList;
        } finally {
        }
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public AssetPathEntity getAssetPathEntityFromId(Context context, String pathId, int type, FilterOption option) {
        String str;
        AssetPathEntity assetPathEntity;
        String str2;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(pathId, "pathId");
        Intrinsics.checkNotNullParameter(option, "option");
        ArrayList arrayList = new ArrayList();
        if (Intrinsics.areEqual(pathId, "")) {
            str = "";
        } else {
            arrayList.add(pathId);
            str = "AND bucket_id = ?";
        }
        String str3 = "bucket_id IS NOT NULL " + FilterOption.makeWhere$default(option, type, arrayList, false, 4, null) + StringUtils.SPACE + str + ") GROUP BY (bucket_id";
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        Cursor cursorLogQuery = logQuery(contentResolver, getAllUri(), (String[]) ArraysKt.plus((Object[]) IDBUtils.INSTANCE.getStoreBucketKeys(), (Object[]) new String[]{"count(1)"}), str3, (String[]) arrayList.toArray(new String[0]), null);
        if (cursorLogQuery == null) {
            return null;
        }
        Cursor cursor = cursorLogQuery;
        try {
            Cursor cursor2 = cursor;
            if (cursor2.moveToNext()) {
                String string = cursor2.getString(0);
                String string2 = cursor2.getString(1);
                if (string2 == null) {
                    str2 = "";
                } else {
                    Intrinsics.checkNotNull(string2);
                    str2 = string2;
                }
                int i = cursor2.getInt(2);
                Intrinsics.checkNotNull(string);
                assetPathEntity = new AssetPathEntity(string, str2, i, 0, false, null, 48, null);
            } else {
                assetPathEntity = null;
            }
            CloseableKt.closeFinally(cursor, null);
            return assetPathEntity;
        } finally {
        }
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public List<AssetEntity> getAssetListPaged(Context context, String pathId, int page, int size, int requestType, FilterOption option) {
        String str;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(pathId, "pathId");
        Intrinsics.checkNotNullParameter(option, "option");
        boolean z = pathId.length() == 0;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (!z) {
            arrayList2.add(pathId);
        }
        String strMakeWhere$default = FilterOption.makeWhere$default(option, requestType, arrayList2, false, 4, null);
        String[] strArrKeys = keys();
        if (z) {
            str = "bucket_id IS NOT NULL " + strMakeWhere$default;
        } else {
            str = "bucket_id = ? " + strMakeWhere$default;
        }
        String sortOrder = getSortOrder(page * size, size, option);
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        Cursor cursorLogQuery = logQuery(contentResolver, getAllUri(), strArrKeys, str, (String[]) arrayList2.toArray(new String[0]), sortOrder);
        if (cursorLogQuery == null) {
            return arrayList;
        }
        Cursor cursor = cursorLogQuery;
        try {
            Cursor cursor2 = cursor;
            while (cursor2.moveToNext()) {
                AssetEntity assetEntity$default = IDBUtils.DefaultImpls.toAssetEntity$default(INSTANCE, cursor2, context, false, 2, null);
                if (assetEntity$default != null) {
                    arrayList.add(assetEntity$default);
                }
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(cursor, null);
            return arrayList;
        } finally {
        }
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public List<AssetEntity> getAssetListRange(Context context, String galleryId, int start, int end, int requestType, FilterOption option) {
        String str;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(galleryId, "galleryId");
        Intrinsics.checkNotNullParameter(option, "option");
        boolean z = galleryId.length() == 0;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (!z) {
            arrayList2.add(galleryId);
        }
        String strMakeWhere$default = FilterOption.makeWhere$default(option, requestType, arrayList2, false, 4, null);
        String[] strArrKeys = keys();
        if (z) {
            str = "bucket_id IS NOT NULL " + strMakeWhere$default;
        } else {
            str = "bucket_id = ? " + strMakeWhere$default;
        }
        String sortOrder = getSortOrder(start, end - start, option);
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        Cursor cursorLogQuery = logQuery(contentResolver, getAllUri(), strArrKeys, str, (String[]) arrayList2.toArray(new String[0]), sortOrder);
        if (cursorLogQuery == null) {
            return arrayList;
        }
        Cursor cursor = cursorLogQuery;
        try {
            Cursor cursor2 = cursor;
            while (cursor2.moveToNext()) {
                AssetEntity assetEntity$default = IDBUtils.DefaultImpls.toAssetEntity$default(INSTANCE, cursor2, context, false, 2, null);
                if (assetEntity$default != null) {
                    arrayList.add(assetEntity$default);
                }
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(cursor, null);
            return arrayList;
        } finally {
        }
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public AssetEntity getAssetEntity(Context context, String id, boolean checkIfExists) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(id, "id");
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        Cursor cursorLogQuery = logQuery(contentResolver, getAllUri(), (String[]) CollectionsKt.distinct(CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) IDBUtils.INSTANCE.getStoreImageKeys(), (Iterable) IDBUtils.INSTANCE.getStoreVideoKeys()), (Object[]) locationKeys), (Object[]) IDBUtils.INSTANCE.getTypeKeys())).toArray(new String[0]), "_id = ?", new String[]{id}, null);
        if (cursorLogQuery == null) {
            return null;
        }
        Cursor cursor = cursorLogQuery;
        try {
            Cursor cursor2 = cursor;
            AssetEntity assetEntity = cursor2.moveToNext() ? INSTANCE.toAssetEntity(cursor2, context, checkIfExists) : null;
            CloseableKt.closeFinally(cursor, null);
            return assetEntity;
        } finally {
        }
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public byte[] getOriginBytes(Context context, AssetEntity asset, boolean needLocationPermission) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(asset, "asset");
        return FilesKt.readBytes(new File(asset.getPath()));
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public ExifInterface getExif(Context context, String id) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(id, "id");
        AssetEntity assetEntity$default = IDBUtils.DefaultImpls.getAssetEntity$default(this, context, id, false, 4, null);
        if (assetEntity$default != null && new File(assetEntity$default.getPath()).exists()) {
            return new ExifInterface(assetEntity$default.getPath());
        }
        return null;
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public String getFilePath(Context context, String id, boolean origin) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(id, "id");
        AssetEntity assetEntity$default = IDBUtils.DefaultImpls.getAssetEntity$default(this, context, id, false, 4, null);
        if (assetEntity$default == null) {
            return null;
        }
        return assetEntity$default.getPath();
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public AssetEntity copyToGallery(Context context, String assetId, String galleryId) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(assetId, "assetId");
        Intrinsics.checkNotNullParameter(galleryId, "galleryId");
        Pair<String, String> someInfo = getSomeInfo(context, assetId);
        if (someInfo == null) {
            throw new RuntimeException("Cannot get gallery id of " + assetId);
        }
        if (Intrinsics.areEqual(galleryId, someInfo.component1())) {
            throw new RuntimeException("No copy required, because the target gallery is the same as the current one.");
        }
        ContentResolver contentResolver = context.getContentResolver();
        DBUtils dBUtils = this;
        AssetEntity assetEntity$default = IDBUtils.DefaultImpls.getAssetEntity$default(dBUtils, context, assetId, false, 4, null);
        if (assetEntity$default == null) {
            throw new RuntimeException("No copy required, because the target gallery is the same as the current one.");
        }
        ArrayList<String> arrayListArrayListOf = CollectionsKt.arrayListOf("_display_name", "title", "date_added", "date_modified", "duration", "longitude", "latitude", "width", "height");
        int iConvertTypeToMediaType = convertTypeToMediaType(assetEntity$default.getType());
        if (iConvertTypeToMediaType != 2) {
            arrayListArrayListOf.add("description");
        }
        Intrinsics.checkNotNull(contentResolver);
        Cursor cursorLogQuery = logQuery(contentResolver, getAllUri(), (String[]) ArraysKt.plus(arrayListArrayListOf.toArray(new String[0]), (Object[]) new String[]{"_data"}), getIdSelection(), new String[]{assetId}, null);
        if (cursorLogQuery == null) {
            throw new RuntimeException("Cannot find asset .");
        }
        if (!cursorLogQuery.moveToNext()) {
            throw new RuntimeException("Cannot find asset .");
        }
        Uri insertUri = MediaStoreUtils.INSTANCE.getInsertUri(iConvertTypeToMediaType);
        GalleryInfo galleryInfo = getGalleryInfo(context, galleryId);
        if (galleryInfo == null) {
            throwMsg("Cannot find gallery info");
            throw new KotlinNothingValueException();
        }
        String str = galleryInfo.getPath() + "/" + assetEntity$default.getDisplayName();
        ContentValues contentValues = new ContentValues();
        for (String str2 : arrayListArrayListOf) {
            DBUtils dBUtils2 = INSTANCE;
            Intrinsics.checkNotNull(str2);
            contentValues.put(str2, dBUtils2.getString(cursorLogQuery, str2));
        }
        contentValues.put("media_type", Integer.valueOf(iConvertTypeToMediaType));
        contentValues.put("_data", str);
        Uri uriInsert = contentResolver.insert(insertUri, contentValues);
        if (uriInsert == null) {
            throw new RuntimeException("Cannot insert new asset.");
        }
        OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(uriInsert);
        if (outputStreamOpenOutputStream == null) {
            throw new RuntimeException("Cannot open output stream for " + uriInsert + ".");
        }
        FileInputStream fileInputStream = new FileInputStream(new File(assetEntity$default.getPath()));
        OutputStream outputStream = fileInputStream;
        try {
            FileInputStream fileInputStream2 = outputStream;
            outputStream = outputStreamOpenOutputStream;
            try {
                ByteStreamsKt.copyTo$default(fileInputStream, outputStreamOpenOutputStream, 0, 2, null);
                CloseableKt.closeFinally(outputStream, null);
                CloseableKt.closeFinally(outputStream, null);
                cursorLogQuery.close();
                String lastPathSegment = uriInsert.getLastPathSegment();
                if (lastPathSegment == null) {
                    throw new RuntimeException("Cannot open output stream for " + uriInsert + ".");
                }
                return IDBUtils.DefaultImpls.getAssetEntity$default(dBUtils, context, lastPathSegment, false, 4, null);
            } finally {
            }
        } finally {
        }
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public AssetEntity moveToGallery(Context context, String assetId, String galleryId) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(assetId, "assetId");
        Intrinsics.checkNotNullParameter(galleryId, "galleryId");
        Pair<String, String> someInfo = getSomeInfo(context, assetId);
        if (someInfo == null) {
            throwMsg("Cannot get gallery id of " + assetId);
            throw new KotlinNothingValueException();
        }
        String strComponent1 = someInfo.component1();
        GalleryInfo galleryInfo = getGalleryInfo(context, galleryId);
        if (galleryInfo == null) {
            throwMsg("Cannot get target gallery info");
            throw new KotlinNothingValueException();
        }
        if (Intrinsics.areEqual(galleryId, strComponent1)) {
            throwMsg("No move required, because the target gallery is the same as the current one.");
            throw new KotlinNothingValueException();
        }
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNull(contentResolver);
        Cursor cursorLogQuery = logQuery(contentResolver, getAllUri(), new String[]{"_data"}, getIdSelection(), new String[]{assetId}, null);
        if (cursorLogQuery == null) {
            throwMsg("Cannot find " + assetId + " path");
            throw new KotlinNothingValueException();
        }
        if (cursorLogQuery.moveToNext()) {
            String string = cursorLogQuery.getString(0);
            cursorLogQuery.close();
            String str = galleryInfo.getPath() + "/" + new File(string).getName();
            new File(string).renameTo(new File(str));
            ContentValues contentValues = new ContentValues();
            contentValues.put("_data", str);
            contentValues.put("bucket_id", galleryId);
            contentValues.put("bucket_display_name", galleryInfo.getGalleryName());
            if (contentResolver.update(getAllUri(), contentValues, getIdSelection(), new String[]{assetId}) > 0) {
                return IDBUtils.DefaultImpls.getAssetEntity$default(this, context, assetId, false, 4, null);
            }
            throwMsg("Cannot update " + assetId + " relativePath");
            throw new KotlinNothingValueException();
        }
        throwMsg("Cannot find " + assetId + " path");
        throw new KotlinNothingValueException();
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public boolean removeAllExistsAssets(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        ReentrantLock reentrantLock = deleteLock;
        if (reentrantLock.isLocked()) {
            return false;
        }
        ReentrantLock reentrantLock2 = reentrantLock;
        reentrantLock2.lock();
        try {
            ArrayList arrayList = new ArrayList();
            ContentResolver contentResolver = context.getContentResolver();
            DBUtils dBUtils = INSTANCE;
            Intrinsics.checkNotNull(contentResolver);
            Cursor cursorLogQuery = dBUtils.logQuery(contentResolver, dBUtils.getAllUri(), new String[]{"_id", "_data"}, null, null, null);
            if (cursorLogQuery == null) {
                return false;
            }
            Cursor cursor = cursorLogQuery;
            try {
                Cursor cursor2 = cursor;
                while (cursor2.moveToNext()) {
                    DBUtils dBUtils2 = INSTANCE;
                    String string = dBUtils2.getString(cursor2, "_id");
                    String string2 = dBUtils2.getString(cursor2, "_data");
                    if (!new File(string2).exists()) {
                        arrayList.add(string);
                        Log.i("PhotoManagerPlugin", "The " + string2 + " was not exists. ");
                    }
                }
                Log.i("PhotoManagerPlugin", "will be delete ids = " + arrayList);
                CloseableKt.closeFinally(cursor, null);
                Log.i("PhotoManagerPlugin", "Delete rows: " + contentResolver.delete(INSTANCE.getAllUri(), "_id in ( " + CollectionsKt.joinToString$default(arrayList, ",", null, null, 0, null, new Function1<String, CharSequence>() { // from class: com.fluttercandies.photo_manager.core.utils.DBUtils$removeAllExistsAssets$1$idWhere$1
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(String it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return "?";
                    }
                }, 30, null) + " )", (String[]) arrayList.toArray(new String[0])));
                reentrantLock2.unlock();
                return true;
            } finally {
            }
        } finally {
            reentrantLock2.unlock();
        }
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public Pair<String, String> getSomeInfo(Context context, String assetId) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(assetId, "assetId");
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        Cursor cursorLogQuery = logQuery(contentResolver, getAllUri(), new String[]{"bucket_id", "_data"}, "_id = ?", new String[]{assetId}, null);
        if (cursorLogQuery == null) {
            return null;
        }
        Cursor cursor = cursorLogQuery;
        try {
            Cursor cursor2 = cursor;
            if (!cursor2.moveToNext()) {
                CloseableKt.closeFinally(cursor, null);
                return null;
            }
            Pair<String, String> pair = new Pair<>(cursor2.getString(0), new File(cursor2.getString(1)).getParent());
            CloseableKt.closeFinally(cursor, null);
            return pair;
        } finally {
        }
    }

    private final GalleryInfo getGalleryInfo(Context context, String galleryId) {
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        Cursor cursorLogQuery = logQuery(contentResolver, getAllUri(), new String[]{"bucket_id", "bucket_display_name", "_data"}, "bucket_id = ?", new String[]{galleryId}, null);
        if (cursorLogQuery == null) {
            return null;
        }
        Cursor cursor = cursorLogQuery;
        try {
            Cursor cursor2 = cursor;
            if (!cursor2.moveToNext()) {
                CloseableKt.closeFinally(cursor, null);
                return null;
            }
            DBUtils dBUtils = INSTANCE;
            String stringOrNull = dBUtils.getStringOrNull(cursor2, "_data");
            if (stringOrNull == null) {
                CloseableKt.closeFinally(cursor, null);
                return null;
            }
            String stringOrNull2 = dBUtils.getStringOrNull(cursor2, "bucket_display_name");
            if (stringOrNull2 == null) {
                CloseableKt.closeFinally(cursor, null);
                return null;
            }
            File parentFile = new File(stringOrNull).getParentFile();
            String absolutePath = parentFile != null ? parentFile.getAbsolutePath() : null;
            if (absolutePath == null) {
                CloseableKt.closeFinally(cursor, null);
                return null;
            }
            Intrinsics.checkNotNull(absolutePath);
            GalleryInfo galleryInfo = new GalleryInfo(absolutePath, galleryId, stringOrNull2);
            CloseableKt.closeFinally(cursor, null);
            return galleryInfo;
        } finally {
        }
    }

    /* JADX INFO: compiled from: DBUtils.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0015"}, d2 = {"Lcom/fluttercandies/photo_manager/core/utils/DBUtils$GalleryInfo;", "", "path", "", "galleryId", "galleryName", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getGalleryId", "()Ljava/lang/String;", "getGalleryName", "getPath", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class GalleryInfo {
        private final String galleryId;
        private final String galleryName;
        private final String path;

        public static /* synthetic */ GalleryInfo copy$default(GalleryInfo galleryInfo, String str, String str2, String str3, int i, Object obj) {
            if ((i & 1) != 0) {
                str = galleryInfo.path;
            }
            if ((i & 2) != 0) {
                str2 = galleryInfo.galleryId;
            }
            if ((i & 4) != 0) {
                str3 = galleryInfo.galleryName;
            }
            return galleryInfo.copy(str, str2, str3);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getPath() {
            return this.path;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getGalleryId() {
            return this.galleryId;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final String getGalleryName() {
            return this.galleryName;
        }

        public final GalleryInfo copy(String path, String galleryId, String galleryName) {
            Intrinsics.checkNotNullParameter(path, "path");
            Intrinsics.checkNotNullParameter(galleryId, "galleryId");
            Intrinsics.checkNotNullParameter(galleryName, "galleryName");
            return new GalleryInfo(path, galleryId, galleryName);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof GalleryInfo)) {
                return false;
            }
            GalleryInfo galleryInfo = (GalleryInfo) other;
            return Intrinsics.areEqual(this.path, galleryInfo.path) && Intrinsics.areEqual(this.galleryId, galleryInfo.galleryId) && Intrinsics.areEqual(this.galleryName, galleryInfo.galleryName);
        }

        public int hashCode() {
            return (((this.path.hashCode() * 31) + this.galleryId.hashCode()) * 31) + this.galleryName.hashCode();
        }

        public String toString() {
            return "GalleryInfo(path=" + this.path + ", galleryId=" + this.galleryId + ", galleryName=" + this.galleryName + ")";
        }

        public GalleryInfo(String path, String galleryId, String galleryName) {
            Intrinsics.checkNotNullParameter(path, "path");
            Intrinsics.checkNotNullParameter(galleryId, "galleryId");
            Intrinsics.checkNotNullParameter(galleryName, "galleryName");
            this.path = path;
            this.galleryId = galleryId;
            this.galleryName = galleryName;
        }

        public final String getGalleryId() {
            return this.galleryId;
        }

        public final String getGalleryName() {
            return this.galleryName;
        }

        public final String getPath() {
            return this.path;
        }
    }
}
