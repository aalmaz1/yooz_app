package com.fluttercandies.photo_manager.constant;

import com.tekartik.sqflite.Constant;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Methods.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/fluttercandies/photo_manager/constant/Methods;", "", "()V", "Companion", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Methods {
    public static final String assetExists = "assetExists";
    public static final String cancelCacheRequests = "cancelCacheRequests";
    public static final String clearFileCache = "clearFileCache";
    public static final String copyAsset = "copyAsset";
    public static final String deleteWithIds = "deleteWithIds";
    public static final String fetchEntityProperties = "fetchEntityProperties";
    public static final String forceOldAPI = "forceOldApi";
    public static final String getColumnNames = "getColumnNames";
    public static final String getMediaUrl = "getMediaUrl";
    public static final String getThumbnail = "getThumb";
    public static final String ignorePermissionCheck = "ignorePermissionCheck";
    public static final String log = "log";
    public static final String moveAssetToPath = "moveAssetToPath";
    public static final String moveToTrash = "moveToTrash";
    public static final String notify = "notify";
    public static final String openSetting = "openSetting";
    public static final String presentLimited = "presentLimited";
    public static final String releaseMemoryCache = "releaseMemoryCache";
    public static final String removeNoExistsAssets = "removeNoExistsAssets";
    public static final String requestCacheAssetsThumbnail = "requestCacheAssetsThumb";
    public static final String requestPermissionExtend = "requestPermissionExtend";
    public static final String saveImage = "saveImage";
    public static final String saveImageWithPath = "saveImageWithPath";
    public static final String saveVideo = "saveVideo";
    public static final String systemVersion = "systemVersion";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String fetchPathProperties = "fetchPathProperties";
    public static final String getAssetPathList = "getAssetPathList";
    public static final String getAssetListPaged = "getAssetListPaged";
    public static final String getAssetCountFromPath = "getAssetCountFromPath";
    public static final String getAssetListRange = "getAssetListRange";
    public static final String getAssetCount = "getAssetCount";
    public static final String getAssetsByRange = "getAssetsByRange";
    private static final String[] haveRequestTypeMethods = {fetchPathProperties, getAssetPathList, getAssetListPaged, getAssetCountFromPath, getAssetListRange, getAssetCount, getAssetsByRange};
    public static final String getLatLng = "getLatLngAndroidQ";
    public static final String getFullFile = "getFullFile";
    public static final String getOriginBytes = "getOriginBytes";
    private static final String[] needMediaLocationMethods = {getLatLng, getFullFile, getOriginBytes};

    /* JADX INFO: compiled from: Methods.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0014\n\u0002\u0010\u0011\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u0004H\u0002J\u0010\u0010.\u001a\u00020,2\u0006\u0010-\u001a\u00020\u0004H\u0002J\u000e\u0010/\u001a\u00020,2\u0006\u0010-\u001a\u00020\u0004J\u000e\u00100\u001a\u00020,2\u0006\u0010-\u001a\u00020\u0004J\u000e\u00101\u001a\u00020,2\u0006\u0010-\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0016\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00040\u0019X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0016\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0019X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001aR\u000e\u0010 \u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u00062"}, d2 = {"Lcom/fluttercandies/photo_manager/constant/Methods$Companion;", "", "()V", Methods.assetExists, "", Methods.cancelCacheRequests, Methods.clearFileCache, Methods.copyAsset, Methods.deleteWithIds, Methods.fetchEntityProperties, Methods.fetchPathProperties, "forceOldAPI", Methods.getAssetCount, Methods.getAssetCountFromPath, Methods.getAssetListPaged, Methods.getAssetListRange, Methods.getAssetPathList, Methods.getAssetsByRange, Methods.getColumnNames, Methods.getFullFile, "getLatLng", Methods.getMediaUrl, Methods.getOriginBytes, "getThumbnail", "haveRequestTypeMethods", "", "[Ljava/lang/String;", Methods.ignorePermissionCheck, Methods.log, Methods.moveAssetToPath, Methods.moveToTrash, "needMediaLocationMethods", Methods.notify, "openSetting", Methods.presentLimited, Methods.releaseMemoryCache, Methods.removeNoExistsAssets, "requestCacheAssetsThumbnail", Methods.requestPermissionExtend, Methods.saveImage, Methods.saveImageWithPath, Methods.saveVideo, Methods.systemVersion, "isHaveRequestTypeMethod", "", Constant.PARAM_METHOD, "isNeedMediaLocationMethod", "isNotNeedPermissionMethod", "isPermissionMethod", "otherMethods", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isNotNeedPermissionMethod(String method) {
            Intrinsics.checkNotNullParameter(method, "method");
            return ArraysKt.contains(new String[]{Methods.log, "openSetting", Methods.forceOldAPI, Methods.systemVersion, Methods.clearFileCache, Methods.releaseMemoryCache, Methods.ignorePermissionCheck}, method);
        }

        public final boolean isPermissionMethod(String method) {
            Intrinsics.checkNotNullParameter(method, "method");
            return ArraysKt.contains(new String[]{Methods.requestPermissionExtend, Methods.presentLimited}, method);
        }

        private final boolean isHaveRequestTypeMethod(String method) {
            return ArraysKt.contains(Methods.haveRequestTypeMethods, method);
        }

        private final boolean isNeedMediaLocationMethod(String method) {
            return ArraysKt.contains(Methods.needMediaLocationMethods, method);
        }

        public final boolean otherMethods(String method) {
            Intrinsics.checkNotNullParameter(method, "method");
            return (isNotNeedPermissionMethod(method) || isPermissionMethod(method) || isHaveRequestTypeMethod(method) || isNeedMediaLocationMethod(method)) ? false : true;
        }
    }
}
