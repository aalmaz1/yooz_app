package com.fluttercandies.photo_manager.permission.impl;

import android.app.Application;
import android.content.Context;
import cn.yoozworld.watch.utils.notifi.NotificationManager;
import com.fluttercandies.photo_manager.constant.Methods;
import com.fluttercandies.photo_manager.core.entity.PermissionResult;
import com.fluttercandies.photo_manager.core.utils.RequestTypeUtils;
import com.fluttercandies.photo_manager.permission.PermissionDelegate;
import com.fluttercandies.photo_manager.permission.PermissionsListener;
import com.fluttercandies.photo_manager.permission.PermissionsUtils;
import com.fluttercandies.photo_manager.util.LogUtils;
import com.fluttercandies.photo_manager.util.ResultHandler;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;

/* JADX INFO: compiled from: PermissionDelegate34.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010!\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 #2\u00020\u0001:\u0001#B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016Je\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00120\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00120\u00162\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00120\u00162\u0006\u0010\u0019\u001a\u00020\bH\u0016¢\u0006\u0002\u0010\u001aJ\u0010\u0010\u001b\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u000fH\u0016J\u0018\u0010\u001c\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\u001d\u001a\u00020\nH\u0016J(\u0010\u001e\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\b2\u0006\u0010 \u001a\u00020!H\u0016J(\u0010\"\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006$"}, d2 = {"Lcom/fluttercandies/photo_manager/permission/impl/PermissionDelegate34;", "Lcom/fluttercandies/photo_manager/permission/PermissionDelegate;", "()V", "getAuthValue", "Lcom/fluttercandies/photo_manager/core/entity/PermissionResult;", "context", "Landroid/app/Application;", "requestType", "", "mediaLocation", "", "handlePermissionResult", "", "permissionsUtils", "Lcom/fluttercandies/photo_manager/permission/PermissionsUtils;", "Landroid/content/Context;", "permissions", "", "", "grantResults", "", "needToRequestPermissionsList", "", "deniedPermissionsList", "grantedPermissionsList", "requestCode", "(Lcom/fluttercandies/photo_manager/permission/PermissionsUtils;Landroid/content/Context;[Ljava/lang/String;[ILjava/util/List;Ljava/util/List;Ljava/util/List;I)V", "haveMediaLocation", "havePermissions", "isHandlePermissionResult", Methods.presentLimited, NotificationManager.BUNDLE_TYPE, "resultHandler", "Lcom/fluttercandies/photo_manager/util/ResultHandler;", "requestPermission", "Companion", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PermissionDelegate34 extends PermissionDelegate {
    private static final String mediaAudio = "android.permission.READ_MEDIA_AUDIO";
    private static final String mediaImage = "android.permission.READ_MEDIA_IMAGES";
    private static final String mediaLocationPermission = "android.permission.ACCESS_MEDIA_LOCATION";
    private static final String mediaVideo = "android.permission.READ_MEDIA_VIDEO";
    private static final String mediaVisualUserSelected = "android.permission.READ_MEDIA_VISUAL_USER_SELECTED";

    /* JADX INFO: compiled from: PermissionDelegate34.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PermissionResult.values().length];
            try {
                iArr[PermissionResult.Denied.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PermissionResult.Authorized.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[PermissionResult.Limited.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // com.fluttercandies.photo_manager.permission.PermissionDelegate
    public boolean isHandlePermissionResult() {
        return true;
    }

    @Override // com.fluttercandies.photo_manager.permission.PermissionDelegate
    public void requestPermission(PermissionsUtils permissionsUtils, Context context, int requestType, boolean mediaLocation) {
        boolean zHavePermissionForUser;
        Intrinsics.checkNotNullParameter(permissionsUtils, "permissionsUtils");
        Intrinsics.checkNotNullParameter(context, "context");
        if (havePermissions(context, requestType)) {
            PermissionsListener permissionsListener = permissionsUtils.getPermissionsListener();
            if (permissionsListener != null) {
                permissionsListener.onGranted(new ArrayList());
                return;
            }
            return;
        }
        LogUtils.info("requestPermission");
        boolean zContainsImage = RequestTypeUtils.INSTANCE.containsImage(requestType);
        boolean zContainsVideo = RequestTypeUtils.INSTANCE.containsVideo(requestType);
        boolean zContainsAudio = RequestTypeUtils.INSTANCE.containsAudio(requestType);
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        if (zContainsVideo || zContainsImage) {
            arrayList.add(mediaVisualUserSelected);
            zHavePermissionForUser = havePermissionForUser(context, mediaVisualUserSelected);
            if (mediaLocation) {
                arrayList.add(mediaLocationPermission);
                zHavePermissionForUser = zHavePermissionForUser && havePermission(context, mediaLocationPermission);
            }
            if (zContainsVideo) {
                arrayList.add(mediaVideo);
            }
            if (zContainsImage) {
                arrayList.add(mediaImage);
            }
        } else {
            zHavePermissionForUser = true;
        }
        if (zContainsAudio) {
            arrayList.add(mediaAudio);
            if (zHavePermissionForUser && havePermission(context, mediaAudio)) {
                z = true;
            }
            zHavePermissionForUser = z;
        }
        LogUtils.info("Current permissions: " + arrayList);
        LogUtils.info("havePermission: " + zHavePermissionForUser);
        if (zHavePermissionForUser) {
            PermissionsListener permissionsListener2 = permissionsUtils.getPermissionsListener();
            if (permissionsListener2 != null) {
                permissionsListener2.onGranted(arrayList);
                return;
            }
            return;
        }
        PermissionDelegate.requestPermission$default(this, permissionsUtils, arrayList, 0, 4, null);
    }

    @Override // com.fluttercandies.photo_manager.permission.PermissionDelegate
    public boolean havePermissions(Context context, int requestType) {
        Intrinsics.checkNotNullParameter(context, "context");
        boolean zContainsImage = RequestTypeUtils.INSTANCE.containsImage(requestType);
        boolean zContainsVideo = RequestTypeUtils.INSTANCE.containsVideo(requestType);
        boolean zContainsAudio = RequestTypeUtils.INSTANCE.containsAudio(requestType);
        boolean zHavePermission = (zContainsVideo || zContainsImage) ? havePermission(context, mediaVisualUserSelected) : true;
        if (zContainsAudio) {
            return zHavePermission && havePermission(context, mediaAudio);
        }
        return zHavePermission;
    }

    @Override // com.fluttercandies.photo_manager.permission.PermissionDelegate
    public boolean haveMediaLocation(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return havePermission(context, mediaLocationPermission);
    }

    @Override // com.fluttercandies.photo_manager.permission.PermissionDelegate
    public void handlePermissionResult(PermissionsUtils permissionsUtils, Context context, String[] permissions, int[] grantResults, List<String> needToRequestPermissionsList, List<String> deniedPermissionsList, List<String> grantedPermissionsList, int requestCode) {
        Intrinsics.checkNotNullParameter(permissionsUtils, "permissionsUtils");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        Intrinsics.checkNotNullParameter(needToRequestPermissionsList, "needToRequestPermissionsList");
        Intrinsics.checkNotNullParameter(deniedPermissionsList, "deniedPermissionsList");
        Intrinsics.checkNotNullParameter(grantedPermissionsList, "grantedPermissionsList");
        if (requestCode == 3002) {
            ResultHandler resultHandler = getResultHandler();
            if (resultHandler == null) {
                return;
            }
            setResultHandler(null);
            resultHandler.reply(1);
            return;
        }
        boolean zContains = needToRequestPermissionsList.contains(mediaImage);
        boolean zContains2 = needToRequestPermissionsList.contains(mediaVideo);
        boolean zContains3 = needToRequestPermissionsList.contains(mediaAudio);
        boolean zContains4 = needToRequestPermissionsList.contains(mediaLocationPermission);
        boolean zHaveAnyPermissionForUser = (zContains || zContains2 || needToRequestPermissionsList.contains(mediaVisualUserSelected)) ? haveAnyPermissionForUser(context, mediaVisualUserSelected, mediaImage, mediaVideo) : true;
        if (zContains3) {
            zHaveAnyPermissionForUser = zHaveAnyPermissionForUser && havePermission(context, mediaAudio);
        }
        if (zContains4) {
            zHaveAnyPermissionForUser = zHaveAnyPermissionForUser && havePermissionForUser(context, mediaLocationPermission);
        }
        PermissionsListener permissionsListener = permissionsUtils.getPermissionsListener();
        if (permissionsListener == null) {
            return;
        }
        if (zHaveAnyPermissionForUser) {
            permissionsListener.onGranted(needToRequestPermissionsList);
        } else {
            permissionsListener.onDenied(deniedPermissionsList, grantedPermissionsList, needToRequestPermissionsList);
        }
    }

    @Override // com.fluttercandies.photo_manager.permission.PermissionDelegate
    public void presentLimited(PermissionsUtils permissionsUtils, Application context, int type, ResultHandler resultHandler) {
        Intrinsics.checkNotNullParameter(permissionsUtils, "permissionsUtils");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(resultHandler, "resultHandler");
        setResultHandler(resultHandler);
        boolean zContainsImage = RequestTypeUtils.INSTANCE.containsImage(type);
        boolean zContainsVideo = RequestTypeUtils.INSTANCE.containsVideo(type);
        ArrayList arrayList = new ArrayList();
        if (zContainsVideo || zContainsImage) {
            arrayList.add(mediaVisualUserSelected);
        }
        if (zContainsVideo) {
            arrayList.add(mediaVideo);
        }
        if (zContainsImage) {
            arrayList.add(mediaImage);
        }
        requestPermission(permissionsUtils, arrayList, 3002);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [T, com.fluttercandies.photo_manager.core.entity.PermissionResult] */
    @Override // com.fluttercandies.photo_manager.permission.PermissionDelegate
    public PermissionResult getAuthValue(Application context, int requestType, boolean mediaLocation) {
        PermissionResult permissionResult;
        PermissionResult permissionResult2;
        PermissionResult permissionResult3;
        Intrinsics.checkNotNullParameter(context, "context");
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = PermissionResult.NotDetermined;
        boolean zContainsImage = RequestTypeUtils.INSTANCE.containsImage(requestType);
        boolean zContainsVideo = RequestTypeUtils.INSTANCE.containsVideo(requestType);
        if (RequestTypeUtils.INSTANCE.containsAudio(requestType)) {
            if (havePermissions(context, mediaAudio)) {
                permissionResult3 = PermissionResult.Authorized;
            } else {
                permissionResult3 = PermissionResult.Denied;
            }
            getAuthValue$changeResult(objectRef, permissionResult3);
        }
        if (zContainsVideo) {
            Application application = context;
            if (havePermissions(application, mediaVideo)) {
                permissionResult2 = PermissionResult.Authorized;
            } else if (havePermissionForUser(application, mediaVisualUserSelected)) {
                permissionResult2 = PermissionResult.Limited;
            } else {
                permissionResult2 = PermissionResult.Denied;
            }
            getAuthValue$changeResult(objectRef, permissionResult2);
        }
        if (zContainsImage) {
            Application application2 = context;
            if (havePermissions(application2, mediaImage)) {
                permissionResult = PermissionResult.Authorized;
            } else if (havePermissionForUser(application2, mediaVisualUserSelected)) {
                permissionResult = PermissionResult.Limited;
            } else {
                permissionResult = PermissionResult.Denied;
            }
            getAuthValue$changeResult(objectRef, permissionResult);
        }
        return (PermissionResult) objectRef.element;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [T, com.fluttercandies.photo_manager.core.entity.PermissionResult] */
    /* JADX WARN: Type inference failed for: r3v2, types: [T, com.fluttercandies.photo_manager.core.entity.PermissionResult] */
    /* JADX WARN: Type inference failed for: r3v4, types: [T, com.fluttercandies.photo_manager.core.entity.PermissionResult] */
    private static final void getAuthValue$changeResult(Ref.ObjectRef<PermissionResult> objectRef, PermissionResult permissionResult) {
        if (objectRef.element == PermissionResult.NotDetermined) {
            objectRef.element = permissionResult;
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[objectRef.element.ordinal()];
        if (i == 1) {
            if (permissionResult == PermissionResult.Limited || permissionResult == PermissionResult.Authorized) {
                objectRef.element = PermissionResult.Limited;
                return;
            }
            return;
        }
        if (i != 2) {
            if (i != 3) {
                return;
            }
            objectRef.element = PermissionResult.Limited;
        } else if (permissionResult == PermissionResult.Limited || permissionResult == PermissionResult.Denied) {
            objectRef.element = PermissionResult.Limited;
        }
    }
}
