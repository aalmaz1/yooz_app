package com.fluttercandies.photo_manager.permission;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import cn.yoozworld.watch.utils.notifi.NotificationManager;
import com.fluttercandies.photo_manager.constant.Methods;
import com.fluttercandies.photo_manager.core.entity.PermissionResult;
import com.fluttercandies.photo_manager.permission.impl.PermissionDelegate23;
import com.fluttercandies.photo_manager.permission.impl.PermissionDelegate29;
import com.fluttercandies.photo_manager.permission.impl.PermissionDelegate30;
import com.fluttercandies.photo_manager.permission.impl.PermissionDelegate33;
import com.fluttercandies.photo_manager.permission.impl.PermissionDelegate34;
import com.fluttercandies.photo_manager.util.LogUtils;
import com.fluttercandies.photo_manager.util.ResultHandler;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PermissionDelegate.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010!\n\u0002\b\u0013\b&\u0018\u0000 12\u00020\u0001:\u00011B\u0005¢\u0006\u0002\u0010\u0002J \u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H&Je\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000f\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\n0\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\n0\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\n0\u001f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\n0\u001f2\u0006\u0010\"\u001a\u00020\u0012H\u0016¢\u0006\u0002\u0010#J)\u0010$\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u00192\u0012\u0010\u001a\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u001b\"\u00020\nH\u0004¢\u0006\u0002\u0010%J\u0010\u0010&\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u0019H&J\u0016\u0010'\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u00192\u0006\u0010(\u001a\u00020\nJ\u0018\u0010)\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u00192\u0006\u0010(\u001a\u00020\nH\u0004J\u0018\u0010*\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u00192\u0006\u0010(\u001a\u00020\nH\u0002J'\u0010+\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u00192\u0012\u0010(\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u001b\"\u00020\n¢\u0006\u0002\u0010%J\u0018\u0010+\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u00192\u0006\u0010\u0011\u001a\u00020\u0012H&J)\u0010,\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u00192\u0012\u0010\u001a\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u001b\"\u00020\nH\u0004¢\u0006\u0002\u0010%J\b\u0010-\u001a\u00020\u0014H\u0016J(\u0010.\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010/\u001a\u00020\u00122\u0006\u0010\u0003\u001a\u00020\u0004H\u0016J(\u00100\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000f\u001a\u00020\u00192\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H&J(\u00100\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\n0\u001f2\b\b\u0002\u0010\"\u001a\u00020\u0012H\u0004R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u00062"}, d2 = {"Lcom/fluttercandies/photo_manager/permission/PermissionDelegate;", "", "()V", "resultHandler", "Lcom/fluttercandies/photo_manager/util/ResultHandler;", "getResultHandler", "()Lcom/fluttercandies/photo_manager/util/ResultHandler;", "setResultHandler", "(Lcom/fluttercandies/photo_manager/util/ResultHandler;)V", "tag", "", "getTag", "()Ljava/lang/String;", "getAuthValue", "Lcom/fluttercandies/photo_manager/core/entity/PermissionResult;", "context", "Landroid/app/Application;", "requestType", "", "mediaLocation", "", "handlePermissionResult", "", "permissionsUtils", "Lcom/fluttercandies/photo_manager/permission/PermissionsUtils;", "Landroid/content/Context;", "permissions", "", "grantResults", "", "needToRequestPermissionsList", "", "deniedPermissionsList", "grantedPermissionsList", "requestCode", "(Lcom/fluttercandies/photo_manager/permission/PermissionsUtils;Landroid/content/Context;[Ljava/lang/String;[ILjava/util/List;Ljava/util/List;Ljava/util/List;I)V", "haveAnyPermissionForUser", "(Landroid/content/Context;[Ljava/lang/String;)Z", "haveMediaLocation", "havePermission", "permission", "havePermissionForUser", "havePermissionInManifest", "havePermissions", "havePermissionsForUser", "isHandlePermissionResult", Methods.presentLimited, NotificationManager.BUNDLE_TYPE, "requestPermission", "Companion", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PermissionDelegate {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int limitedRequestCode = 3002;
    public static final int requestCode = 3001;
    private ResultHandler resultHandler;

    public abstract PermissionResult getAuthValue(Application context, int requestType, boolean mediaLocation);

    public abstract boolean haveMediaLocation(Context context);

    public abstract boolean havePermissions(Context context, int requestType);

    public boolean isHandlePermissionResult() {
        return false;
    }

    public abstract void requestPermission(PermissionsUtils permissionsUtils, Context context, int requestType, boolean mediaLocation);

    protected final ResultHandler getResultHandler() {
        return this.resultHandler;
    }

    protected final void setResultHandler(ResultHandler resultHandler) {
        this.resultHandler = resultHandler;
    }

    private final String getTag() {
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        return simpleName;
    }

    public static /* synthetic */ void requestPermission$default(PermissionDelegate permissionDelegate, PermissionsUtils permissionsUtils, List list, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: requestPermission");
        }
        if ((i2 & 4) != 0) {
            i = 3001;
        }
        permissionDelegate.requestPermission(permissionsUtils, list, i);
    }

    protected final void requestPermission(PermissionsUtils permissionsUtils, List<String> permission, int requestCode2) {
        Intrinsics.checkNotNullParameter(permissionsUtils, "permissionsUtils");
        Intrinsics.checkNotNullParameter(permission, "permission");
        Activity activity = permissionsUtils.getMActivity();
        if (activity == null) {
            throw new NullPointerException("Activity for the permission request is not exist.");
        }
        permissionsUtils.setNeedToRequestPermissionsList(permission);
        ActivityCompat.requestPermissions(activity, (String[]) permission.toArray(new String[0]), requestCode2);
        LogUtils.debug("requestPermission: " + permission + " for code " + requestCode2);
    }

    private final boolean havePermissionInManifest(Context context, String permission) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo;
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (Build.VERSION.SDK_INT >= 33) {
            packageInfo = context.getPackageManager().getPackageInfo(applicationInfo.packageName, PackageManager.PackageInfoFlags.of(4096L));
        } else {
            packageInfo = context.getPackageManager().getPackageInfo(applicationInfo.packageName, 4096);
        }
        String[] requestedPermissions = packageInfo.requestedPermissions;
        Intrinsics.checkNotNullExpressionValue(requestedPermissions, "requestedPermissions");
        return ArraysKt.contains(requestedPermissions, permission);
    }

    protected final boolean havePermissionForUser(Context context, String permission) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(permission, "permission");
        return ActivityCompat.checkSelfPermission(context, permission) == 0;
    }

    public final boolean havePermission(Context context, String permission) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(permission, "permission");
        return havePermissionInManifest(context, permission) && havePermissionForUser(context, permission);
    }

    /* JADX INFO: compiled from: PermissionDelegate.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0006\u001a\u00020\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/fluttercandies/photo_manager/permission/PermissionDelegate$Companion;", "", "()V", "limitedRequestCode", "", "requestCode", "create", "Lcom/fluttercandies/photo_manager/permission/PermissionDelegate;", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final PermissionDelegate create() {
            int i = Build.VERSION.SDK_INT;
            if (i < 29) {
                return new PermissionDelegate23();
            }
            if (i == 29) {
                return new PermissionDelegate29();
            }
            if (30 <= i && i < 33) {
                return new PermissionDelegate30();
            }
            if (i == 33) {
                return new PermissionDelegate33();
            }
            if (34 <= i && i < Integer.MAX_VALUE) {
                return new PermissionDelegate34();
            }
            throw new UnsupportedOperationException("This sdk version is not supported yet.");
        }
    }

    public void handlePermissionResult(PermissionsUtils permissionsUtils, Context context, String[] permissions, int[] grantResults, List<String> needToRequestPermissionsList, List<String> deniedPermissionsList, List<String> grantedPermissionsList, int requestCode2) {
        Intrinsics.checkNotNullParameter(permissionsUtils, "permissionsUtils");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        Intrinsics.checkNotNullParameter(needToRequestPermissionsList, "needToRequestPermissionsList");
        Intrinsics.checkNotNullParameter(deniedPermissionsList, "deniedPermissionsList");
        Intrinsics.checkNotNullParameter(grantedPermissionsList, "grantedPermissionsList");
        throw new UnsupportedOperationException("handlePermissionResult is not implemented, please implement it in your delegate.");
    }

    public void presentLimited(PermissionsUtils permissionsUtils, Application context, int type, ResultHandler resultHandler) {
        Intrinsics.checkNotNullParameter(permissionsUtils, "permissionsUtils");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(resultHandler, "resultHandler");
        LogUtils.debug("[" + getTag() + "] presentLimited is not implemented");
        resultHandler.reply(null);
    }

    protected final boolean havePermissionsForUser(Context context, String... permissions) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        for (String str : permissions) {
            if (!havePermissionForUser(context, str)) {
                return false;
            }
        }
        return true;
    }

    protected final boolean haveAnyPermissionForUser(Context context, String... permissions) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        for (String str : permissions) {
            if (havePermissionForUser(context, str)) {
                return true;
            }
        }
        return false;
    }

    public final boolean havePermissions(Context context, String... permission) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(permission, "permission");
        int length = permission.length;
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = true;
                break;
            }
            if (!havePermission(context, permission[i])) {
                break;
            }
            i++;
        }
        LogUtils.debug("[" + getTag() + "] havePermissions: " + ArraysKt.toList(permission) + ", result: " + z);
        return z;
    }
}
