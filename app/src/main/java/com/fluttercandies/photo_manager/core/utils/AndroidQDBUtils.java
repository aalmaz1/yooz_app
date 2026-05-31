package com.fluttercandies.photo_manager.core.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import cn.yoozworld.watch.utils.notifi.NotificationManager;
import com.fluttercandies.photo_manager.constant.Methods;
import com.fluttercandies.photo_manager.core.PhotoManager;
import com.fluttercandies.photo_manager.core.cache.ScopedCache;
import com.fluttercandies.photo_manager.core.entity.AssetEntity;
import com.fluttercandies.photo_manager.core.entity.AssetPathEntity;
import com.fluttercandies.photo_manager.core.entity.filter.FilterOption;
import com.fluttercandies.photo_manager.core.utils.IDBUtils;
import com.fluttercandies.photo_manager.util.LogUtils;
import com.google.android.gms.fido.u2f.api.common.ClientData;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: compiled from: AndroidQDBUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0004\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\"\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0004H\u0016JC\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00182!\u0010\u001a\u001a\u001d\u0012\u0013\u0012\u00110\u0016¢\u0006\f\b\u001c\u0012\b\b\u001d\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\r0\u001bH\u0002J\"\u0010\u001e\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\bH\u0016J>\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00110\"2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\u00182\u0006\u0010%\u001a\u00020\u00182\u0006\u0010&\u001a\u00020\u00182\u0006\u0010'\u001a\u00020(H\u0016J>\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00110\"2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010*\u001a\u00020\u00182\u0006\u0010&\u001a\u00020\u00182\u0006\u0010'\u001a\u00020(H\u0016J*\u0010+\u001a\u0004\u0018\u00010,2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u00042\u0006\u0010-\u001a\u00020\u00182\u0006\u0010'\u001a\u00020(H\u0016J&\u0010.\u001a\b\u0012\u0004\u0012\u00020,0\"2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020\u00182\u0006\u0010'\u001a\u00020(H\u0016J\u001a\u0010/\u001a\u0004\u0018\u0001002\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u0004H\u0016J\"\u00101\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u00042\u0006\u00102\u001a\u00020\bH\u0016J&\u00103\u001a\b\u0012\u0004\u0012\u00020,0\"2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020\u00182\u0006\u0010'\u001a\u00020(H\u0016J \u00104\u001a\u0002052\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u00106\u001a\u00020\u00112\u0006\u00107\u001a\u00020\bH\u0016J\u001a\u00108\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0004H\u0002J(\u00109\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010:2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0004H\u0016J\"\u0010;\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010<\u001a\u00020(H\u0016J\u001a\u0010=\u001a\u00020>2\u0006\u00106\u001a\u00020\u00112\b\b\u0002\u0010?\u001a\u00020\bH\u0002J\u0013\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00040AH\u0016¢\u0006\u0002\u0010BJ\"\u0010C\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0004H\u0016J\u0010\u0010D\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006E"}, d2 = {"Lcom/fluttercandies/photo_manager/core/utils/AndroidQDBUtils;", "Lcom/fluttercandies/photo_manager/core/utils/IDBUtils;", "()V", "TAG", "", "deleteLock", "Ljava/util/concurrent/locks/ReentrantLock;", "isQStorageLegacy", "", "scopedCache", "Lcom/fluttercandies/photo_manager/core/cache/ScopedCache;", "shouldUseScopedCache", Methods.clearFileCache, "", "context", "Landroid/content/Context;", "copyToGallery", "Lcom/fluttercandies/photo_manager/core/entity/AssetEntity;", "assetId", "galleryId", "cursorWithRange", "cursor", "Landroid/database/Cursor;", "start", "", "pageSize", "block", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "getAssetEntity", "id", "checkIfExists", Methods.getAssetListPaged, "", "pathId", "page", "size", "requestType", "option", "Lcom/fluttercandies/photo_manager/core/entity/filter/FilterOption;", Methods.getAssetListRange, "end", "getAssetPathEntityFromId", "Lcom/fluttercandies/photo_manager/core/entity/AssetPathEntity;", NotificationManager.BUNDLE_TYPE, Methods.getAssetPathList, "getExif", "Landroidx/exifinterface/media/ExifInterface;", "getFilePath", ClientData.KEY_ORIGIN, "getMainAssetPathEntity", Methods.getOriginBytes, "", "asset", "needLocationPermission", "getRelativePath", "getSomeInfo", "Lkotlin/Pair;", "getSortOrder", "filterOption", "getUri", "Landroid/net/Uri;", "isOrigin", "keys", "", "()[Ljava/lang/String;", "moveToGallery", "removeAllExistsAssets", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AndroidQDBUtils implements IDBUtils {
    private static final String TAG = "PhotoManagerPlugin";
    private static final ReentrantLock deleteLock;
    private static final boolean isQStorageLegacy;
    private static final boolean shouldUseScopedCache;
    public static final AndroidQDBUtils INSTANCE = new AndroidQDBUtils();
    private static final ScopedCache scopedCache = new ScopedCache();

    private AndroidQDBUtils() {
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public boolean assetExists(Context context, String str) {
        return IDBUtils.DefaultImpls.assetExists(this, context, str);
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

    static {
        shouldUseScopedCache = Build.VERSION.SDK_INT == 29 && !Environment.isExternalStorageLegacy();
        isQStorageLegacy = Build.VERSION.SDK_INT == 29 && Environment.isExternalStorageLegacy();
        deleteLock = new ReentrantLock();
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public List<AssetPathEntity> getAssetPathList(Context context, int requestType, FilterOption option) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(option, "option");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        String str = "bucket_id IS NOT NULL " + FilterOption.makeWhere$default(option, requestType, arrayList2, false, 4, null);
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        Cursor cursorLogQuery = logQuery(contentResolver, getAllUri(), IDBUtils.INSTANCE.getStoreBucketKeys(), str, (String[]) arrayList2.toArray(new String[0]), option.orderByCondString());
        if (cursorLogQuery == null) {
            return arrayList;
        }
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        Cursor cursor = cursorLogQuery;
        try {
            Cursor cursor2 = cursor;
            LogUtils.logCursor(cursor2, "bucket_id");
            while (cursor2.moveToNext()) {
                AndroidQDBUtils androidQDBUtils = INSTANCE;
                String string = androidQDBUtils.getString(cursor2, "bucket_id");
                if (map.containsKey(string)) {
                    Object obj = map2.get(string);
                    Intrinsics.checkNotNull(obj);
                    map2.put(string, Integer.valueOf(((Number) obj).intValue() + 1));
                } else {
                    map.put(string, androidQDBUtils.getString(cursor2, "bucket_display_name"));
                    map2.put(string, 1);
                }
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(cursor, null);
            for (Map.Entry entry : map.entrySet()) {
                String str2 = (String) entry.getKey();
                String str3 = (String) entry.getValue();
                Object obj2 = map2.get(str2);
                Intrinsics.checkNotNull(obj2);
                AssetPathEntity assetPathEntity = new AssetPathEntity(str2, str3, ((Number) obj2).intValue(), requestType, false, null, 32, null);
                if (option.getContainsPathModified()) {
                    INSTANCE.injectModifiedDate(context, assetPathEntity);
                }
                arrayList.add(assetPathEntity);
            }
            return arrayList;
        } finally {
        }
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public List<AssetPathEntity> getMainAssetPathEntity(Context context, int requestType, FilterOption option) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(option, "option");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        String str = "bucket_id IS NOT NULL " + FilterOption.makeWhere$default(option, requestType, arrayList2, false, 4, null);
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        Cursor cursorLogQuery = logQuery(contentResolver, getAllUri(), IDBUtils.INSTANCE.getStoreBucketKeys(), str, (String[]) arrayList2.toArray(new String[0]), option.orderByCondString());
        if (cursorLogQuery == null) {
            return arrayList;
        }
        Cursor cursor = cursorLogQuery;
        try {
            arrayList.add(new AssetPathEntity(PhotoManager.ALL_ID, PhotoManager.ALL_ALBUM_NAME, cursor.getCount(), requestType, true, null, 32, null));
            CloseableKt.closeFinally(cursor, null);
            return arrayList;
        } finally {
        }
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public String getSortOrder(int start, int pageSize, FilterOption filterOption) {
        Intrinsics.checkNotNullParameter(filterOption, "filterOption");
        if (isQStorageLegacy) {
            return IDBUtils.DefaultImpls.getSortOrder(this, start, pageSize, filterOption);
        }
        return filterOption.orderByCondString();
    }

    private final void cursorWithRange(Cursor cursor, int start, int pageSize, Function1<? super Cursor, Unit> block) {
        if (!isQStorageLegacy) {
            cursor.moveToPosition(start - 1);
        }
        for (int i = 0; i < pageSize; i++) {
            if (cursor.moveToNext()) {
                block.invoke(cursor);
            }
        }
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public List<AssetEntity> getAssetListPaged(final Context context, String pathId, int page, int size, int requestType, FilterOption option) {
        String str;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(pathId, "pathId");
        Intrinsics.checkNotNullParameter(option, "option");
        boolean z = pathId.length() == 0;
        final ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (!z) {
            arrayList2.add(pathId);
        }
        String strMakeWhere$default = FilterOption.makeWhere$default(option, requestType, arrayList2, false, 4, null);
        if (z) {
            str = "bucket_id IS NOT NULL " + strMakeWhere$default;
        } else {
            str = "bucket_id = ? " + strMakeWhere$default;
        }
        String str2 = str;
        int i = page * size;
        String sortOrder = getSortOrder(i, size, option);
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        Cursor cursorLogQuery = logQuery(contentResolver, getAllUri(), keys(), str2, (String[]) arrayList2.toArray(new String[0]), sortOrder);
        if (cursorLogQuery == null) {
            return arrayList;
        }
        Cursor cursor = cursorLogQuery;
        try {
            INSTANCE.cursorWithRange(cursor, i, size, new Function1<Cursor, Unit>() { // from class: com.fluttercandies.photo_manager.core.utils.AndroidQDBUtils$getAssetListPaged$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Cursor cursor2) {
                    invoke2(cursor2);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Cursor cursor2) {
                    Intrinsics.checkNotNullParameter(cursor2, "cursor");
                    AssetEntity assetEntity$default = IDBUtils.DefaultImpls.toAssetEntity$default(AndroidQDBUtils.INSTANCE, cursor2, context, false, 2, null);
                    if (assetEntity$default != null) {
                        arrayList.add(assetEntity$default);
                    }
                }
            });
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(cursor, null);
            return arrayList;
        } finally {
        }
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public List<AssetEntity> getAssetListRange(final Context context, String galleryId, int start, int end, int requestType, FilterOption option) {
        String str;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(galleryId, "galleryId");
        Intrinsics.checkNotNullParameter(option, "option");
        boolean z = galleryId.length() == 0;
        final ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (!z) {
            arrayList2.add(galleryId);
        }
        String strMakeWhere$default = FilterOption.makeWhere$default(option, requestType, arrayList2, false, 4, null);
        if (z) {
            str = "bucket_id IS NOT NULL " + strMakeWhere$default;
        } else {
            str = "bucket_id = ? " + strMakeWhere$default;
        }
        String str2 = str;
        int i = end - start;
        String sortOrder = getSortOrder(start, i, option);
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        Cursor cursorLogQuery = logQuery(contentResolver, getAllUri(), keys(), str2, (String[]) arrayList2.toArray(new String[0]), sortOrder);
        if (cursorLogQuery == null) {
            return arrayList;
        }
        Cursor cursor = cursorLogQuery;
        try {
            INSTANCE.cursorWithRange(cursor, start, i, new Function1<Cursor, Unit>() { // from class: com.fluttercandies.photo_manager.core.utils.AndroidQDBUtils$getAssetListRange$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Cursor cursor2) {
                    invoke2(cursor2);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Cursor cursor2) {
                    Intrinsics.checkNotNullParameter(cursor2, "cursor");
                    AssetEntity assetEntity$default = IDBUtils.DefaultImpls.toAssetEntity$default(AndroidQDBUtils.INSTANCE, cursor2, context, false, 2, null);
                    if (assetEntity$default != null) {
                        arrayList.add(assetEntity$default);
                    }
                }
            });
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(cursor, null);
            return arrayList;
        } finally {
        }
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public String[] keys() {
        return (String[]) CollectionsKt.distinct(CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) IDBUtils.INSTANCE.getStoreImageKeys(), (Iterable) IDBUtils.INSTANCE.getStoreVideoKeys()), (Object[]) IDBUtils.INSTANCE.getTypeKeys()), (Object[]) new String[]{"relative_path"})).toArray(new String[0]);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public AssetEntity getAssetEntity(Context context, String id, boolean checkIfExists) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(id, "id");
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        Cursor cursorLogQuery = logQuery(contentResolver, getAllUri(), keys(), "_id = ?", new String[]{id}, null);
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
    public AssetPathEntity getAssetPathEntityFromId(Context context, String pathId, int type, FilterOption option) {
        String str;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(pathId, "pathId");
        Intrinsics.checkNotNullParameter(option, "option");
        boolean zAreEqual = Intrinsics.areEqual(pathId, "");
        ArrayList arrayList = new ArrayList();
        String strMakeWhere$default = FilterOption.makeWhere$default(option, type, arrayList, false, 4, null);
        if (zAreEqual) {
            str = "";
        } else {
            arrayList.add(pathId);
            str = "AND bucket_id = ?";
        }
        String str2 = "bucket_id IS NOT NULL " + strMakeWhere$default + StringUtils.SPACE + str;
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNullExpressionValue(contentResolver, "getContentResolver(...)");
        Cursor cursorLogQuery = logQuery(contentResolver, getAllUri(), IDBUtils.INSTANCE.getStoreBucketKeys(), str2, (String[]) arrayList.toArray(new String[0]), null);
        if (cursorLogQuery == null) {
            return null;
        }
        Cursor cursor = cursorLogQuery;
        try {
            Cursor cursor2 = cursor;
            if (cursor2.moveToNext()) {
                String string = cursor2.getString(1);
                if (string == null) {
                    string = "";
                } else {
                    Intrinsics.checkNotNull(string);
                }
                int count = cursor2.getCount();
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(cursor, null);
                return new AssetPathEntity(pathId, string, count, type, zAreEqual, null, 32, null);
            }
            CloseableKt.closeFinally(cursor, null);
            return null;
        } finally {
        }
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public ExifInterface getExif(Context context, String id) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(id, "id");
        try {
            AssetEntity assetEntity$default = IDBUtils.DefaultImpls.getAssetEntity$default(this, context, id, false, 4, null);
            if (assetEntity$default == null) {
                return null;
            }
            Uri requireOriginal = MediaStore.setRequireOriginal(getUri$default(this, assetEntity$default, false, 2, null));
            Intrinsics.checkNotNullExpressionValue(requireOriginal, "setRequireOriginal(...)");
            InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(requireOriginal);
            if (inputStreamOpenInputStream == null) {
                return null;
            }
            return new ExifInterface(inputStreamOpenInputStream);
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public String getFilePath(Context context, String id, boolean origin) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(id, "id");
        AssetEntity assetEntity$default = IDBUtils.DefaultImpls.getAssetEntity$default(this, context, id, false, 4, null);
        if (assetEntity$default == null) {
            return null;
        }
        if (shouldUseScopedCache) {
            File cacheFileFromEntity = scopedCache.getCacheFileFromEntity(context, assetEntity$default, origin);
            if (cacheFileFromEntity != null) {
                return cacheFileFromEntity.getAbsolutePath();
            }
            return null;
        }
        return assetEntity$default.getPath();
    }

    static /* synthetic */ Uri getUri$default(AndroidQDBUtils androidQDBUtils, AssetEntity assetEntity, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return androidQDBUtils.getUri(assetEntity, z);
    }

    private final Uri getUri(AssetEntity asset, boolean isOrigin) {
        return getUri(asset.getId(), asset.getType(), isOrigin);
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public byte[] getOriginBytes(Context context, AssetEntity asset, boolean needLocationPermission) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(asset, "asset");
        InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(getUri(asset, needLocationPermission));
        InputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ByteArrayOutputStream byteArrayOutputStream2 = byteArrayOutputStream;
            if (inputStreamOpenInputStream != null) {
                byteArrayOutputStream = inputStreamOpenInputStream;
                try {
                    byteArrayOutputStream2.write(ByteStreamsKt.readBytes(byteArrayOutputStream));
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(byteArrayOutputStream, null);
                } finally {
                }
            }
            byte[] byteArray = byteArrayOutputStream2.toByteArray();
            if (LogUtils.INSTANCE.isLog()) {
                long id = asset.getId();
                Intrinsics.checkNotNull(byteArray);
                LogUtils.info("The asset " + id + " origin byte length : " + byteArray.length);
            }
            Intrinsics.checkNotNull(byteArray);
            CloseableKt.closeFinally(byteArrayOutputStream, null);
            return byteArray;
        } finally {
        }
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public AssetEntity copyToGallery(Context context, String assetId, String galleryId) throws FileNotFoundException {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(assetId, "assetId");
        Intrinsics.checkNotNullParameter(galleryId, "galleryId");
        Pair<String, String> someInfo = getSomeInfo(context, assetId);
        if (someInfo == null) {
            throwMsg("Cannot get gallery id of " + assetId);
            throw new KotlinNothingValueException();
        }
        if (Intrinsics.areEqual(galleryId, someInfo.component1())) {
            throwMsg("No copy required, because the target gallery is the same as the current one.");
            throw new KotlinNothingValueException();
        }
        AndroidQDBUtils androidQDBUtils = this;
        AssetEntity assetEntity$default = IDBUtils.DefaultImpls.getAssetEntity$default(androidQDBUtils, context, assetId, false, 4, null);
        if (assetEntity$default == null) {
            throwMsg("No copy required, because the target gallery is the same as the current one.");
            throw new KotlinNothingValueException();
        }
        ArrayList<String> arrayListArrayListOf = CollectionsKt.arrayListOf("_display_name", "title", "date_added", "date_modified", "datetaken", "duration", "width", "height", "orientation");
        int iConvertTypeToMediaType = convertTypeToMediaType(assetEntity$default.getType());
        if (iConvertTypeToMediaType == 3) {
            arrayListArrayListOf.add("description");
        }
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNull(contentResolver);
        Cursor cursorLogQuery = logQuery(contentResolver, getAllUri(), (String[]) ArraysKt.plus(arrayListArrayListOf.toArray(new String[0]), (Object[]) new String[]{"relative_path"}), getIdSelection(), new String[]{assetId}, null);
        if (cursorLogQuery == null) {
            throwMsg("Cannot find asset.");
            throw new KotlinNothingValueException();
        }
        if (!cursorLogQuery.moveToNext()) {
            throwMsg("Cannot find asset.");
            throw new KotlinNothingValueException();
        }
        Uri insertUri = MediaStoreUtils.INSTANCE.getInsertUri(iConvertTypeToMediaType);
        String relativePath = getRelativePath(context, galleryId);
        ContentValues contentValues = new ContentValues();
        for (String str : arrayListArrayListOf) {
            AndroidQDBUtils androidQDBUtils2 = INSTANCE;
            Intrinsics.checkNotNull(str);
            contentValues.put(str, androidQDBUtils2.getString(cursorLogQuery, str));
        }
        contentValues.put("media_type", Integer.valueOf(iConvertTypeToMediaType));
        contentValues.put("relative_path", relativePath);
        Uri uriInsert = contentResolver.insert(insertUri, contentValues);
        if (uriInsert == null) {
            throwMsg("Cannot insert new asset.");
            throw new KotlinNothingValueException();
        }
        OutputStream outputStreamOpenOutputStream = contentResolver.openOutputStream(uriInsert);
        if (outputStreamOpenOutputStream == null) {
            throwMsg("Cannot open output stream for " + uriInsert + ".");
            throw new KotlinNothingValueException();
        }
        Uri uri = getUri(assetEntity$default, true);
        InputStream inputStreamOpenInputStream = contentResolver.openInputStream(uri);
        if (inputStreamOpenInputStream == null) {
            throwMsg("Cannot open input stream for " + uri);
            throw new KotlinNothingValueException();
        }
        OutputStream outputStream = inputStreamOpenInputStream;
        try {
            InputStream inputStream = outputStream;
            outputStream = outputStreamOpenOutputStream;
            try {
                ByteStreamsKt.copyTo$default(inputStreamOpenInputStream, outputStreamOpenOutputStream, 0, 2, null);
                CloseableKt.closeFinally(outputStream, null);
                CloseableKt.closeFinally(outputStream, null);
                cursorLogQuery.close();
                String lastPathSegment = uriInsert.getLastPathSegment();
                if (lastPathSegment == null) {
                    throwMsg("Cannot open output stream for " + uriInsert + ".");
                    throw new KotlinNothingValueException();
                }
                return IDBUtils.DefaultImpls.getAssetEntity$default(androidQDBUtils, context, lastPathSegment, false, 4, null);
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
        if (Intrinsics.areEqual(galleryId, someInfo.component1())) {
            throwMsg("No move required, because the target gallery is the same as the current one.");
            throw new KotlinNothingValueException();
        }
        ContentResolver contentResolver = context.getContentResolver();
        String relativePath = getRelativePath(context, galleryId);
        ContentValues contentValues = new ContentValues();
        contentValues.put("relative_path", relativePath);
        if (contentResolver.update(getAllUri(), contentValues, getIdSelection(), new String[]{assetId}) > 0) {
            return IDBUtils.DefaultImpls.getAssetEntity$default(this, context, assetId, false, 4, null);
        }
        throwMsg("Cannot update " + assetId + " relativePath");
        throw new KotlinNothingValueException();
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public boolean removeAllExistsAssets(Context context) {
        boolean z;
        Intrinsics.checkNotNullParameter(context, "context");
        ReentrantLock reentrantLock = deleteLock;
        if (reentrantLock.isLocked()) {
            Log.i(TAG, "The removeAllExistsAssets is running.");
            return false;
        }
        ReentrantLock reentrantLock2 = reentrantLock;
        reentrantLock2.lock();
        try {
            Log.i(TAG, "The removeAllExistsAssets is starting.");
            ArrayList arrayList = new ArrayList();
            ContentResolver contentResolver = context.getContentResolver();
            AndroidQDBUtils androidQDBUtils = INSTANCE;
            Intrinsics.checkNotNull(contentResolver);
            Uri allUri = androidQDBUtils.getAllUri();
            String[] strArr = {"_id", "media_type", "_data"};
            Integer[] numArr = {2, 3, 1};
            ArrayList arrayList2 = new ArrayList(3);
            int i = 0;
            for (int i2 = 3; i < i2; i2 = 3) {
                arrayList2.add(String.valueOf(numArr[i].intValue()));
                i++;
            }
            Cursor cursorLogQuery = androidQDBUtils.logQuery(contentResolver, allUri, strArr, "media_type in ( ?,?,? )", (String[]) arrayList2.toArray(new String[0]), null);
            if (cursorLogQuery == null) {
                return false;
            }
            Cursor cursor = cursorLogQuery;
            try {
                Cursor cursor2 = cursor;
                int i3 = 0;
                while (cursor2.moveToNext()) {
                    AndroidQDBUtils androidQDBUtils2 = INSTANCE;
                    String string = androidQDBUtils2.getString(cursor2, "_id");
                    int i4 = androidQDBUtils2.getInt(cursor2, "media_type");
                    String stringOrNull = androidQDBUtils2.getStringOrNull(cursor2, "_data");
                    try {
                        InputStream inputStreamOpenInputStream = contentResolver.openInputStream(IDBUtils.DefaultImpls.getUri$default(androidQDBUtils2, Long.parseLong(string), androidQDBUtils2.getTypeFromMediaType(i4), false, 4, null));
                        if (inputStreamOpenInputStream != null) {
                            inputStreamOpenInputStream.close();
                        }
                        z = true;
                    } catch (Exception unused) {
                        z = false;
                    }
                    if (!z) {
                        arrayList.add(string);
                        Log.i(TAG, "The " + string + ", " + stringOrNull + " media was not exists. ");
                    }
                    i3++;
                    if (i3 % 300 == 0) {
                        Log.i(TAG, "Current checked count == " + i3);
                    }
                }
                Log.i(TAG, "The removeAllExistsAssets was stopped, will be delete ids = " + arrayList);
                CloseableKt.closeFinally(cursor, null);
                Log.i(TAG, "Delete rows: " + contentResolver.delete(INSTANCE.getAllUri(), "_id in ( " + CollectionsKt.joinToString$default(arrayList, ",", null, null, 0, null, new Function1<String, CharSequence>() { // from class: com.fluttercandies.photo_manager.core.utils.AndroidQDBUtils$removeAllExistsAssets$1$idWhere$1
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(String it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return "?";
                    }
                }, 30, null) + " )", (String[]) arrayList.toArray(new String[0])));
                return true;
            } finally {
            }
        } finally {
            reentrantLock2.unlock();
        }
    }

    private final String getRelativePath(Context context, String galleryId) {
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNull(contentResolver);
        Cursor cursorLogQuery = logQuery(contentResolver, getAllUri(), new String[]{"bucket_id", "relative_path"}, "bucket_id = ?", new String[]{galleryId}, null);
        if (cursorLogQuery == null) {
            return null;
        }
        Cursor cursor = cursorLogQuery;
        try {
            if (!cursorLogQuery.moveToNext()) {
                CloseableKt.closeFinally(cursor, null);
                return null;
            }
            String string = cursorLogQuery.getString(1);
            CloseableKt.closeFinally(cursor, null);
            return string;
        } finally {
        }
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public Pair<String, String> getSomeInfo(Context context, String assetId) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(assetId, "assetId");
        ContentResolver contentResolver = context.getContentResolver();
        Intrinsics.checkNotNull(contentResolver);
        Cursor cursorLogQuery = logQuery(contentResolver, getAllUri(), new String[]{"bucket_id", "relative_path"}, "_id = ?", new String[]{assetId}, null);
        if (cursorLogQuery == null) {
            return null;
        }
        Cursor cursor = cursorLogQuery;
        try {
            if (!cursorLogQuery.moveToNext()) {
                CloseableKt.closeFinally(cursor, null);
                return null;
            }
            Pair<String, String> pair = new Pair<>(cursorLogQuery.getString(0), new File(cursorLogQuery.getString(1)).getParent());
            CloseableKt.closeFinally(cursor, null);
            return pair;
        } finally {
        }
    }

    @Override // com.fluttercandies.photo_manager.core.utils.IDBUtils
    public void clearFileCache(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        IDBUtils.DefaultImpls.clearFileCache(this, context);
        scopedCache.clearFileCache(context);
    }
}
