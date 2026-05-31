package com.fluttercandies.photo_manager.core;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.core.app.NotificationCompat;
import cn.baos.watch.sdk.entitiy.NotificationConstant;
import cn.yoozworld.watch.utils.notifi.NotificationManager;
import com.bumptech.glide.Glide;
import com.fluttercandies.photo_manager.constant.Methods;
import com.fluttercandies.photo_manager.core.PhotoManagerPlugin;
import com.fluttercandies.photo_manager.core.entity.AssetEntity;
import com.fluttercandies.photo_manager.core.entity.AssetPathEntity;
import com.fluttercandies.photo_manager.core.entity.ThumbLoadOption;
import com.fluttercandies.photo_manager.core.entity.filter.FilterOption;
import com.fluttercandies.photo_manager.core.utils.ConvertUtils;
import com.fluttercandies.photo_manager.permission.PermissionsListener;
import com.fluttercandies.photo_manager.permission.PermissionsUtils;
import com.fluttercandies.photo_manager.util.LogUtils;
import com.fluttercandies.photo_manager.util.ResultHandler;
import com.tekartik.sqflite.Constant;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PhotoManagerPlugin.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 +2\u00020\u0001:\u0001+B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u0010\u0015\u001a\u00020\u00162\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007J\u0018\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0010H\u0002J\u0010\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0018\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0014\u0010$\u001a\u00020%*\u00020 2\u0006\u0010&\u001a\u00020'H\u0002J\f\u0010(\u001a\u00020)*\u00020 H\u0002J\u0014\u0010*\u001a\u00020'*\u00020 2\u0006\u0010&\u001a\u00020'H\u0002R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006,"}, d2 = {"Lcom/fluttercandies/photo_manager/core/PhotoManagerPlugin;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", "applicationContext", "Landroid/content/Context;", "messenger", "Lio/flutter/plugin/common/BinaryMessenger;", "activity", "Landroid/app/Activity;", "permissionsUtils", "Lcom/fluttercandies/photo_manager/permission/PermissionsUtils;", "(Landroid/content/Context;Lio/flutter/plugin/common/BinaryMessenger;Landroid/app/Activity;Lcom/fluttercandies/photo_manager/permission/PermissionsUtils;)V", "deleteManager", "Lcom/fluttercandies/photo_manager/core/PhotoManagerDeleteManager;", "getDeleteManager", "()Lcom/fluttercandies/photo_manager/core/PhotoManagerDeleteManager;", Methods.ignorePermissionCheck, "", "notifyChannel", "Lcom/fluttercandies/photo_manager/core/PhotoManagerNotifyChannel;", "photoManager", "Lcom/fluttercandies/photo_manager/core/PhotoManager;", "bindActivity", "", "handleMethodResult", "resultHandler", "Lcom/fluttercandies/photo_manager/util/ResultHandler;", "needLocationPermission", "handleNotNeedPermissionMethod", "handleOtherMethods", "handlePermissionMethod", "onMethodCall", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", Constant.PARAM_RESULT, "Lio/flutter/plugin/common/MethodChannel$Result;", "replyPermissionError", "getInt", "", NotificationConstant.EXTRA_KEY, "", "getOption", "Lcom/fluttercandies/photo_manager/core/entity/filter/FilterOption;", "getString", "Companion", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PhotoManagerPlugin implements MethodChannel.MethodCallHandler {
    private static final int poolSize = 8;
    private Activity activity;
    private final Context applicationContext;
    private final PhotoManagerDeleteManager deleteManager;
    private boolean ignorePermissionCheck;
    private final PhotoManagerNotifyChannel notifyChannel;
    private final PermissionsUtils permissionsUtils;
    private final PhotoManager photoManager;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(8, Integer.MAX_VALUE, 1, TimeUnit.MINUTES, new LinkedBlockingQueue());

    public PhotoManagerPlugin(Context applicationContext, BinaryMessenger messenger, Activity activity, PermissionsUtils permissionsUtils) {
        Intrinsics.checkNotNullParameter(applicationContext, "applicationContext");
        Intrinsics.checkNotNullParameter(messenger, "messenger");
        Intrinsics.checkNotNullParameter(permissionsUtils, "permissionsUtils");
        this.applicationContext = applicationContext;
        this.activity = activity;
        this.permissionsUtils = permissionsUtils;
        permissionsUtils.setPermissionsListener(new PermissionsListener() { // from class: com.fluttercandies.photo_manager.core.PhotoManagerPlugin.1
            @Override // com.fluttercandies.photo_manager.permission.PermissionsListener
            public void onDenied(List<String> deniedPermissions, List<String> grantedPermissions, List<String> needPermissions) {
                Intrinsics.checkNotNullParameter(deniedPermissions, "deniedPermissions");
                Intrinsics.checkNotNullParameter(grantedPermissions, "grantedPermissions");
                Intrinsics.checkNotNullParameter(needPermissions, "needPermissions");
            }

            @Override // com.fluttercandies.photo_manager.permission.PermissionsListener
            public void onGranted(List<String> needPermissions) {
                Intrinsics.checkNotNullParameter(needPermissions, "needPermissions");
            }
        });
        this.deleteManager = new PhotoManagerDeleteManager(applicationContext, this.activity);
        this.notifyChannel = new PhotoManagerNotifyChannel(applicationContext, messenger, new Handler(Looper.getMainLooper()));
        this.photoManager = new PhotoManager(applicationContext);
    }

    /* JADX INFO: compiled from: PhotoManagerPlugin.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/fluttercandies/photo_manager/core/PhotoManagerPlugin$Companion;", "", "()V", "poolSize", "", "threadPool", "Ljava/util/concurrent/ThreadPoolExecutor;", "runOnBackground", "", "runnable", "Lkotlin/Function0;", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void runOnBackground$lambda$0(Function0 tmp0) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            tmp0.invoke();
        }

        public final void runOnBackground(final Function0<Unit> runnable) {
            Intrinsics.checkNotNullParameter(runnable, "runnable");
            PhotoManagerPlugin.threadPool.execute(new Runnable() { // from class: com.fluttercandies.photo_manager.core.PhotoManagerPlugin$Companion$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PhotoManagerPlugin.Companion.runOnBackground$lambda$0(runnable);
                }
            });
        }
    }

    public final PhotoManagerDeleteManager getDeleteManager() {
        return this.deleteManager;
    }

    public final void bindActivity(Activity activity) {
        this.activity = activity;
        this.deleteManager.bindActivity(activity);
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        ResultHandler resultHandler = new ResultHandler(result, call);
        String str = call.method;
        Methods.Companion companion = Methods.INSTANCE;
        Intrinsics.checkNotNull(str);
        if (companion.isNotNeedPermissionMethod(str)) {
            handleNotNeedPermissionMethod(resultHandler);
            return;
        }
        if (Methods.INSTANCE.isPermissionMethod(str)) {
            handlePermissionMethod(resultHandler);
        } else if (this.ignorePermissionCheck) {
            handleOtherMethods(resultHandler);
        } else {
            handleOtherMethods(resultHandler);
        }
    }

    private final void handlePermissionMethod(final ResultHandler resultHandler) {
        MethodCall call = resultHandler.getCall();
        String str = call.method;
        if (Intrinsics.areEqual(str, Methods.requestPermissionExtend)) {
            Object objArgument = call.argument("androidPermission");
            Intrinsics.checkNotNull(objArgument);
            Map map = (Map) objArgument;
            Object obj = map.get(NotificationManager.BUNDLE_TYPE);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
            final int iIntValue = ((Integer) obj).intValue();
            Object obj2 = map.get("mediaLocation");
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Boolean");
            final boolean zBooleanValue = ((Boolean) obj2).booleanValue();
            this.permissionsUtils.withActivity(this.activity).setListener(new PermissionsListener() { // from class: com.fluttercandies.photo_manager.core.PhotoManagerPlugin.handlePermissionMethod.1
                @Override // com.fluttercandies.photo_manager.permission.PermissionsListener
                public void onGranted(List<String> needPermissions) {
                    Intrinsics.checkNotNullParameter(needPermissions, "needPermissions");
                    resultHandler.reply(Integer.valueOf(this.permissionsUtils.getAuthValue(iIntValue, zBooleanValue).getValue()));
                }

                @Override // com.fluttercandies.photo_manager.permission.PermissionsListener
                public void onDenied(List<String> deniedPermissions, List<String> grantedPermissions, List<String> needPermissions) {
                    Intrinsics.checkNotNullParameter(deniedPermissions, "deniedPermissions");
                    Intrinsics.checkNotNullParameter(grantedPermissions, "grantedPermissions");
                    Intrinsics.checkNotNullParameter(needPermissions, "needPermissions");
                    resultHandler.reply(Integer.valueOf(this.permissionsUtils.getAuthValue(iIntValue, zBooleanValue).getValue()));
                }
            }).requestPermission(this.applicationContext, iIntValue, zBooleanValue);
            return;
        }
        if (Intrinsics.areEqual(str, Methods.presentLimited)) {
            Object objArgument2 = call.argument(NotificationManager.BUNDLE_TYPE);
            Intrinsics.checkNotNull(objArgument2);
            this.permissionsUtils.presentLimited(((Number) objArgument2).intValue(), resultHandler);
        }
    }

    private final void handleOtherMethods(final ResultHandler resultHandler) {
        INSTANCE.runOnBackground(new Function0<Unit>() { // from class: com.fluttercandies.photo_manager.core.PhotoManagerPlugin.handleOtherMethods.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                try {
                    PhotoManagerPlugin.this.handleMethodResult(resultHandler, PhotoManagerPlugin.this.permissionsUtils.haveLocationPermission(PhotoManagerPlugin.this.applicationContext));
                } catch (Exception e) {
                    MethodCall call = resultHandler.getCall();
                    String str = call.method;
                    resultHandler.replyError("The " + str + " method has an error: " + e.getMessage(), ExceptionsKt.stackTraceToString(e), call.arguments);
                }
            }
        });
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final void handleNotNeedPermissionMethod(final ResultHandler resultHandler) {
        MethodCall call = resultHandler.getCall();
        String str = call.method;
        if (str != null) {
            switch (str.hashCode()) {
                case -1914421335:
                    if (str.equals(Methods.systemVersion)) {
                        resultHandler.reply(String.valueOf(Build.VERSION.SDK_INT));
                        break;
                    }
                    break;
                case -582375106:
                    if (str.equals(Methods.forceOldAPI)) {
                        this.photoManager.setUseOldApi(true);
                        resultHandler.reply(1);
                        break;
                    }
                    break;
                case 107332:
                    if (str.equals(Methods.log)) {
                        LogUtils logUtils = LogUtils.INSTANCE;
                        Boolean bool = (Boolean) call.arguments();
                        logUtils.setLog(bool == null ? false : bool.booleanValue());
                        resultHandler.reply(1);
                        break;
                    }
                    break;
                case 1138660423:
                    if (str.equals(Methods.ignorePermissionCheck)) {
                        Object objArgument = call.argument("ignore");
                        Intrinsics.checkNotNull(objArgument);
                        boolean zBooleanValue = ((Boolean) objArgument).booleanValue();
                        this.ignorePermissionCheck = zBooleanValue;
                        resultHandler.reply(Boolean.valueOf(zBooleanValue));
                        break;
                    }
                    break;
                case 1541932953:
                    if (str.equals(Methods.clearFileCache)) {
                        Glide.get(this.applicationContext).clearMemory();
                        INSTANCE.runOnBackground(new Function0<Unit>() { // from class: com.fluttercandies.photo_manager.core.PhotoManagerPlugin.handleNotNeedPermissionMethod.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public /* bridge */ /* synthetic */ Unit invoke() {
                                invoke2();
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2() {
                                PhotoManagerPlugin.this.photoManager.clearFileCache();
                                resultHandler.reply(1);
                            }
                        });
                        break;
                    }
                    break;
                case 1789114534:
                    if (str.equals("openSetting")) {
                        this.permissionsUtils.getAppDetailSettingIntent(this.activity);
                        resultHandler.reply(1);
                        break;
                    }
                    break;
                case 1920532602:
                    if (str.equals(Methods.releaseMemoryCache)) {
                        resultHandler.reply(1);
                        break;
                    }
                    break;
            }
        }
    }

    private final void replyPermissionError(ResultHandler resultHandler) {
        resultHandler.replyError("Request for permission failed.", "User denied permission.", null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final void handleMethodResult(ResultHandler resultHandler, boolean needLocationPermission) {
        boolean zBooleanValue;
        MethodCall call = resultHandler.getCall();
        String str = call.method;
        if (str != null) {
            switch (str.hashCode()) {
                case -2060338679:
                    if (str.equals(Methods.saveImageWithPath)) {
                        try {
                            Object objArgument = call.argument("path");
                            Intrinsics.checkNotNull(objArgument);
                            String str2 = (String) objArgument;
                            String str3 = (String) call.argument("title");
                            if (str3 == null) {
                                str3 = "";
                            }
                            String str4 = (String) call.argument("desc");
                            if (str4 == null) {
                                str4 = "";
                            }
                            String str5 = (String) call.argument("relativePath");
                            AssetEntity assetEntitySaveImage = this.photoManager.saveImage(str2, str3, str4, str5 == null ? "" : str5);
                            if (assetEntitySaveImage == null) {
                                resultHandler.reply(null);
                                return;
                            } else {
                                resultHandler.reply(ConvertUtils.INSTANCE.convertAsset(assetEntitySaveImage));
                                return;
                            }
                        } catch (Exception e) {
                            LogUtils.error("save image error", e);
                            resultHandler.reply(null);
                            return;
                        }
                    }
                    break;
                case -1793329916:
                    if (str.equals(Methods.removeNoExistsAssets)) {
                        this.photoManager.removeAllExistsAssets(resultHandler);
                        return;
                    }
                    break;
                case -1701237244:
                    if (str.equals(Methods.getAssetCountFromPath)) {
                        this.photoManager.getAssetCount(resultHandler, getOption(call), getInt(call, NotificationManager.BUNDLE_TYPE), getString(call, "id"));
                        return;
                    }
                    break;
                case -1491271588:
                    if (str.equals(Methods.getColumnNames)) {
                        this.photoManager.getColumnNames(resultHandler);
                        return;
                    }
                    break;
                case -1283288098:
                    if (str.equals(Methods.getLatLng)) {
                        Object objArgument2 = call.argument("id");
                        Intrinsics.checkNotNull(objArgument2);
                        resultHandler.reply(this.photoManager.getLocation((String) objArgument2));
                        return;
                    }
                    break;
                case -1167306339:
                    if (str.equals(Methods.getAssetListPaged)) {
                        Object objArgument3 = call.argument("id");
                        Intrinsics.checkNotNull(objArgument3);
                        String str6 = (String) objArgument3;
                        Object objArgument4 = call.argument(NotificationManager.BUNDLE_TYPE);
                        Intrinsics.checkNotNull(objArgument4);
                        int iIntValue = ((Number) objArgument4).intValue();
                        Object objArgument5 = call.argument("page");
                        Intrinsics.checkNotNull(objArgument5);
                        int iIntValue2 = ((Number) objArgument5).intValue();
                        Object objArgument6 = call.argument("size");
                        Intrinsics.checkNotNull(objArgument6);
                        resultHandler.reply(ConvertUtils.INSTANCE.convertAssets(this.photoManager.getAssetListPaged(str6, iIntValue, iIntValue2, ((Number) objArgument6).intValue(), getOption(call))));
                        return;
                    }
                    break;
                case -1165452507:
                    if (str.equals(Methods.getAssetListRange)) {
                        resultHandler.reply(ConvertUtils.INSTANCE.convertAssets(this.photoManager.getAssetListRange(getString(call, "id"), getInt(call, NotificationManager.BUNDLE_TYPE), getInt(call, "start"), getInt(call, "end"), getOption(call))));
                        return;
                    }
                    break;
                case -1039689911:
                    if (str.equals(Methods.notify)) {
                        if (Intrinsics.areEqual(call.argument(Methods.notify), (Object) true)) {
                            this.notifyChannel.startNotify();
                        } else {
                            this.notifyChannel.stopNotify();
                        }
                        resultHandler.reply(null);
                        return;
                    }
                    break;
                case -1033607060:
                    if (str.equals(Methods.moveToTrash)) {
                        try {
                            Object objArgument7 = call.argument("ids");
                            Intrinsics.checkNotNull(objArgument7);
                            List list = (List) objArgument7;
                            if (Build.VERSION.SDK_INT < 30) {
                                LogUtils.error("The API 29 or lower have not the IS_TRASHED row in MediaStore.");
                                resultHandler.replyError("The api not support 29 or lower.", "", new UnsupportedOperationException("The api cannot be used in 29 or lower."));
                                return;
                            }
                            List list2 = list;
                            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                            Iterator it = list2.iterator();
                            while (it.hasNext()) {
                                arrayList.add(this.photoManager.getUri((String) it.next()));
                            }
                            this.deleteManager.moveToTrashInApi30(CollectionsKt.toList(arrayList), resultHandler);
                            return;
                        } catch (Exception e2) {
                            LogUtils.error("deleteWithIds failed", e2);
                            ResultHandler.replyError$default(resultHandler, "deleteWithIds failed", null, null, 6, null);
                            return;
                        }
                    }
                    break;
                case -948382752:
                    if (str.equals(Methods.requestCacheAssetsThumbnail)) {
                        Object objArgument8 = call.argument("ids");
                        Intrinsics.checkNotNull(objArgument8);
                        Object objArgument9 = call.argument("option");
                        Intrinsics.checkNotNull(objArgument9);
                        this.photoManager.requestCache((List) objArgument8, ThumbLoadOption.INSTANCE.fromMap((Map) objArgument9), resultHandler);
                        return;
                    }
                    break;
                case -886445535:
                    if (str.equals(Methods.getFullFile)) {
                        Object objArgument10 = call.argument("id");
                        Intrinsics.checkNotNull(objArgument10);
                        String str7 = (String) objArgument10;
                        if (needLocationPermission) {
                            Object objArgument11 = call.argument("isOrigin");
                            Intrinsics.checkNotNull(objArgument11);
                            zBooleanValue = ((Boolean) objArgument11).booleanValue();
                        } else {
                            zBooleanValue = false;
                        }
                        this.photoManager.getFile(str7, zBooleanValue, resultHandler);
                        return;
                    }
                    break;
                case -626940993:
                    if (str.equals(Methods.moveAssetToPath)) {
                        Object objArgument12 = call.argument("assetId");
                        Intrinsics.checkNotNull(objArgument12);
                        Object objArgument13 = call.argument("albumId");
                        Intrinsics.checkNotNull(objArgument13);
                        this.photoManager.moveToGallery((String) objArgument12, (String) objArgument13, resultHandler);
                        return;
                    }
                    break;
                case -151967598:
                    if (str.equals(Methods.fetchPathProperties)) {
                        Object objArgument14 = call.argument("id");
                        Intrinsics.checkNotNull(objArgument14);
                        Object objArgument15 = call.argument(NotificationManager.BUNDLE_TYPE);
                        Intrinsics.checkNotNull(objArgument15);
                        AssetPathEntity assetPathEntityFetchPathProperties = this.photoManager.fetchPathProperties((String) objArgument14, ((Number) objArgument15).intValue(), getOption(call));
                        if (assetPathEntityFetchPathProperties != null) {
                            resultHandler.reply(ConvertUtils.INSTANCE.convertPaths(CollectionsKt.listOf(assetPathEntityFetchPathProperties)));
                            return;
                        } else {
                            resultHandler.reply(null);
                            return;
                        }
                    }
                    break;
                case 163601886:
                    if (str.equals(Methods.saveImage)) {
                        try {
                            Object objArgument16 = call.argument("image");
                            Intrinsics.checkNotNull(objArgument16);
                            byte[] bArr = (byte[]) objArgument16;
                            String str8 = (String) call.argument("title");
                            if (str8 == null) {
                                str8 = "";
                            }
                            String str9 = (String) call.argument("desc");
                            if (str9 == null) {
                                str9 = "";
                            }
                            String str10 = (String) call.argument("relativePath");
                            AssetEntity assetEntitySaveImage2 = this.photoManager.saveImage(bArr, str8, str9, str10 == null ? "" : str10);
                            if (assetEntitySaveImage2 == null) {
                                resultHandler.reply(null);
                                return;
                            } else {
                                resultHandler.reply(ConvertUtils.INSTANCE.convertAsset(assetEntitySaveImage2));
                                return;
                            }
                        } catch (Exception e3) {
                            LogUtils.error("save image error", e3);
                            resultHandler.reply(null);
                            return;
                        }
                    }
                    break;
                case 175491326:
                    if (str.equals(Methods.saveVideo)) {
                        try {
                            Object objArgument17 = call.argument("path");
                            Intrinsics.checkNotNull(objArgument17);
                            String str11 = (String) objArgument17;
                            Object objArgument18 = call.argument("title");
                            Intrinsics.checkNotNull(objArgument18);
                            String str12 = (String) objArgument18;
                            String str13 = (String) call.argument("desc");
                            if (str13 == null) {
                                str13 = "";
                            }
                            String str14 = (String) call.argument("relativePath");
                            AssetEntity assetEntitySaveVideo = this.photoManager.saveVideo(str11, str12, str13, str14 == null ? "" : str14);
                            if (assetEntitySaveVideo == null) {
                                resultHandler.reply(null);
                                return;
                            } else {
                                resultHandler.reply(ConvertUtils.INSTANCE.convertAsset(assetEntitySaveVideo));
                                return;
                            }
                        } catch (Exception e4) {
                            LogUtils.error("save video error", e4);
                            resultHandler.reply(null);
                            return;
                        }
                    }
                    break;
                case 326673488:
                    if (str.equals(Methods.fetchEntityProperties)) {
                        Object objArgument19 = call.argument("id");
                        Intrinsics.checkNotNull(objArgument19);
                        AssetEntity assetEntityFetchEntityProperties = this.photoManager.fetchEntityProperties((String) objArgument19);
                        resultHandler.reply(assetEntityFetchEntityProperties != null ? ConvertUtils.INSTANCE.convertAsset(assetEntityFetchEntityProperties) : null);
                        return;
                    }
                    break;
                case 624480877:
                    if (str.equals(Methods.getAssetsByRange)) {
                        this.photoManager.getAssetsByRange(resultHandler, getOption(call), getInt(call, "start"), getInt(call, "end"), getInt(call, NotificationManager.BUNDLE_TYPE));
                        return;
                    }
                    break;
                case 857200492:
                    if (str.equals(Methods.assetExists)) {
                        Object objArgument20 = call.argument("id");
                        Intrinsics.checkNotNull(objArgument20);
                        this.photoManager.assetExists((String) objArgument20, resultHandler);
                        return;
                    }
                    break;
                case 972925196:
                    if (str.equals(Methods.cancelCacheRequests)) {
                        this.photoManager.cancelCacheRequests();
                        resultHandler.reply(null);
                        return;
                    }
                    break;
                case 1063055279:
                    if (str.equals(Methods.getOriginBytes)) {
                        Object objArgument21 = call.argument("id");
                        Intrinsics.checkNotNull(objArgument21);
                        this.photoManager.getOriginBytes((String) objArgument21, resultHandler, needLocationPermission);
                        return;
                    }
                    break;
                case 1150344167:
                    if (str.equals(Methods.deleteWithIds)) {
                        try {
                            Object objArgument22 = call.argument("ids");
                            Intrinsics.checkNotNull(objArgument22);
                            List<String> list3 = (List) objArgument22;
                            if (Build.VERSION.SDK_INT >= 30) {
                                List<String> list4 = list3;
                                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list4, 10));
                                Iterator<T> it2 = list4.iterator();
                                while (it2.hasNext()) {
                                    arrayList2.add(this.photoManager.getUri((String) it2.next()));
                                }
                                this.deleteManager.deleteInApi30(CollectionsKt.toList(arrayList2), resultHandler);
                                return;
                            }
                            if (Build.VERSION.SDK_INT != 29) {
                                this.deleteManager.deleteInApi28(list3);
                                resultHandler.reply(list3);
                                return;
                            }
                            HashMap<String, Uri> map = new HashMap<>();
                            for (String str15 : list3) {
                                map.put(str15, this.photoManager.getUri(str15));
                            }
                            this.deleteManager.deleteJustInApi29(map, resultHandler);
                            return;
                        } catch (Exception e5) {
                            LogUtils.error("deleteWithIds failed", e5);
                            ResultHandler.replyError$default(resultHandler, "deleteWithIds failed", null, null, 6, null);
                            return;
                        }
                    }
                    break;
                case 1177116769:
                    if (str.equals(Methods.getMediaUrl)) {
                        Object objArgument23 = call.argument("id");
                        Intrinsics.checkNotNull(objArgument23);
                        Object objArgument24 = call.argument(NotificationManager.BUNDLE_TYPE);
                        Intrinsics.checkNotNull(objArgument24);
                        resultHandler.reply(this.photoManager.getMediaUri(Long.parseLong((String) objArgument23), ((Number) objArgument24).intValue()));
                        return;
                    }
                    break;
                case 1375013309:
                    if (str.equals(Methods.getAssetPathList)) {
                        Object objArgument25 = call.argument(NotificationManager.BUNDLE_TYPE);
                        Intrinsics.checkNotNull(objArgument25);
                        int iIntValue3 = ((Number) objArgument25).intValue();
                        Object objArgument26 = call.argument("hasAll");
                        Intrinsics.checkNotNull(objArgument26);
                        boolean zBooleanValue2 = ((Boolean) objArgument26).booleanValue();
                        FilterOption option = getOption(call);
                        Object objArgument27 = call.argument("onlyAll");
                        Intrinsics.checkNotNull(objArgument27);
                        resultHandler.reply(ConvertUtils.INSTANCE.convertPaths(this.photoManager.getAssetPathList(iIntValue3, zBooleanValue2, ((Boolean) objArgument27).booleanValue(), option)));
                        return;
                    }
                    break;
                case 1477946491:
                    if (str.equals(Methods.copyAsset)) {
                        Object objArgument28 = call.argument("assetId");
                        Intrinsics.checkNotNull(objArgument28);
                        Object objArgument29 = call.argument("galleryId");
                        Intrinsics.checkNotNull(objArgument29);
                        this.photoManager.copyToGallery((String) objArgument28, (String) objArgument29, resultHandler);
                        return;
                    }
                    break;
                case 1806009333:
                    if (str.equals(Methods.getAssetCount)) {
                        this.photoManager.getAssetCount(resultHandler, getOption(call), getInt(call, NotificationManager.BUNDLE_TYPE));
                        return;
                    }
                    break;
                case 1966168096:
                    if (str.equals(Methods.getThumbnail)) {
                        Object objArgument30 = call.argument("id");
                        Intrinsics.checkNotNull(objArgument30);
                        Object objArgument31 = call.argument("option");
                        Intrinsics.checkNotNull(objArgument31);
                        this.photoManager.getThumb((String) objArgument30, ThumbLoadOption.INSTANCE.fromMap((Map) objArgument31), resultHandler);
                        return;
                    }
                    break;
            }
        }
        resultHandler.notImplemented();
    }

    private final String getString(MethodCall methodCall, String str) {
        Object objArgument = methodCall.argument(str);
        Intrinsics.checkNotNull(objArgument);
        return (String) objArgument;
    }

    private final int getInt(MethodCall methodCall, String str) {
        Object objArgument = methodCall.argument(str);
        Intrinsics.checkNotNull(objArgument);
        return ((Number) objArgument).intValue();
    }

    private final FilterOption getOption(MethodCall methodCall) {
        Object objArgument = methodCall.argument("option");
        Intrinsics.checkNotNull(objArgument);
        return ConvertUtils.INSTANCE.convertToFilterOptions((Map) objArgument);
    }
}
