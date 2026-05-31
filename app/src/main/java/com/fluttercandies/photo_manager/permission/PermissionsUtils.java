package com.fluttercandies.photo_manager.permission;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.core.content.PermissionChecker;
import cn.yoozworld.watch.utils.notifi.NotificationManager;
import com.fluttercandies.photo_manager.constant.Methods;
import com.fluttercandies.photo_manager.core.entity.PermissionResult;
import com.fluttercandies.photo_manager.util.LogUtils;
import com.fluttercandies.photo_manager.util.ResultHandler;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PermissionsUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\tJ)\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\t0\u001e2\u0006\u0010\u001f\u001a\u00020 ¢\u0006\u0002\u0010!J\b\u0010\"\u001a\u0004\u0018\u00010\u0010J\u0010\u0010#\u001a\u00020$2\b\u0010\u0003\u001a\u0004\u0018\u00010%J\u0016\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020\u001c2\u0006\u0010)\u001a\u00020\fJ\u000e\u0010*\u001a\u00020\f2\u0006\u0010+\u001a\u00020%J\u0016\u0010,\u001a\u00020$2\u0006\u0010-\u001a\u00020\u001c2\u0006\u0010.\u001a\u00020/J\u001e\u00100\u001a\u00020\u00002\u0006\u0010+\u001a\u00020%2\u0006\u0010(\u001a\u00020\u001c2\u0006\u0010)\u001a\u00020\fJ\b\u00101\u001a\u00020$H\u0002J\u0010\u00102\u001a\u00020\u00002\b\u00103\u001a\u0004\u0018\u00010\u0013J\u0014\u00104\u001a\u00020$2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\t0\bJ\u0010\u00105\u001a\u00020\u00002\b\u00106\u001a\u0004\u0018\u00010\u0010R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u00067"}, d2 = {"Lcom/fluttercandies/photo_manager/permission/PermissionsUtils;", "", "()V", "context", "Landroid/app/Application;", "delegate", "Lcom/fluttercandies/photo_manager/permission/PermissionDelegate;", "deniedPermissionsList", "", "", "grantedPermissionsList", "<set-?>", "", "isRequesting", "()Z", "mActivity", "Landroid/app/Activity;", "needToRequestPermissionsList", "permissionsListener", "Lcom/fluttercandies/photo_manager/permission/PermissionsListener;", "getPermissionsListener", "()Lcom/fluttercandies/photo_manager/permission/PermissionsListener;", "setPermissionsListener", "(Lcom/fluttercandies/photo_manager/permission/PermissionsListener;)V", "checkCallingOrSelfPermission", "permission", "dealResult", "requestCode", "", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)Lcom/fluttercandies/photo_manager/permission/PermissionsUtils;", "getActivity", "getAppDetailSettingIntent", "", "Landroid/content/Context;", "getAuthValue", "Lcom/fluttercandies/photo_manager/core/entity/PermissionResult;", "requestType", "mediaLocation", "haveLocationPermission", "applicationContext", Methods.presentLimited, NotificationManager.BUNDLE_TYPE, "resultHandler", "Lcom/fluttercandies/photo_manager/util/ResultHandler;", "requestPermission", "resetStatus", "setListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "setNeedToRequestPermissionsList", "withActivity", "activity", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PermissionsUtils {
    private Application context;
    private boolean isRequesting;
    private Activity mActivity;
    private PermissionsListener permissionsListener;
    private final PermissionDelegate delegate = PermissionDelegate.INSTANCE.create();
    private final List<String> needToRequestPermissionsList = new ArrayList();
    private final List<String> deniedPermissionsList = new ArrayList();
    private final List<String> grantedPermissionsList = new ArrayList();

    /* JADX INFO: renamed from: isRequesting, reason: from getter */
    public final boolean getIsRequesting() {
        return this.isRequesting;
    }

    public final PermissionsListener getPermissionsListener() {
        return this.permissionsListener;
    }

    public final void setPermissionsListener(PermissionsListener permissionsListener) {
        this.permissionsListener = permissionsListener;
    }

    public final PermissionsUtils withActivity(Activity activity) {
        this.mActivity = activity;
        this.context = activity != null ? activity.getApplication() : null;
        return this;
    }

    /* JADX INFO: renamed from: getActivity, reason: from getter */
    public final Activity getMActivity() {
        return this.mActivity;
    }

    public final PermissionsUtils setListener(PermissionsListener listener) {
        this.permissionsListener = listener;
        return this;
    }

    public final PermissionsUtils requestPermission(Context applicationContext, int requestType, boolean mediaLocation) {
        Intrinsics.checkNotNullParameter(applicationContext, "applicationContext");
        this.delegate.requestPermission(this, applicationContext, requestType, mediaLocation);
        return this;
    }

    public final boolean checkCallingOrSelfPermission(String permission) {
        Intrinsics.checkNotNullParameter(permission, "permission");
        Application application = this.context;
        if (application == null) {
            throw new NullPointerException("Context for the permission request is not exist.");
        }
        Intrinsics.checkNotNull(application);
        return PermissionChecker.checkCallingOrSelfPermission(application, permission) == 0;
    }

    public final PermissionsUtils dealResult(int requestCode, String[] permissions, int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        if (requestCode == 3001 || requestCode == 3002) {
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                LogUtils.info("Returned permissions: " + permissions[i]);
                int i2 = grantResults[i];
                if (i2 == -1) {
                    this.deniedPermissionsList.add(permissions[i]);
                } else if (i2 == 0) {
                    this.grantedPermissionsList.add(permissions[i]);
                }
            }
            LogUtils.debug("dealResult: ");
            LogUtils.debug("  permissions: " + permissions);
            LogUtils.debug("  grantResults: " + grantResults);
            LogUtils.debug("  deniedPermissionsList: " + this.deniedPermissionsList);
            LogUtils.debug("  grantedPermissionsList: " + this.grantedPermissionsList);
            if (this.delegate.isHandlePermissionResult()) {
                PermissionDelegate permissionDelegate = this.delegate;
                Application application = this.context;
                Intrinsics.checkNotNull(application);
                permissionDelegate.handlePermissionResult(this, application, permissions, grantResults, this.needToRequestPermissionsList, this.deniedPermissionsList, this.grantedPermissionsList, requestCode);
            } else if (!this.deniedPermissionsList.isEmpty()) {
                PermissionsListener permissionsListener = this.permissionsListener;
                Intrinsics.checkNotNull(permissionsListener);
                permissionsListener.onDenied(this.deniedPermissionsList, this.grantedPermissionsList, this.needToRequestPermissionsList);
            } else {
                PermissionsListener permissionsListener2 = this.permissionsListener;
                Intrinsics.checkNotNull(permissionsListener2);
                permissionsListener2.onGranted(this.needToRequestPermissionsList);
            }
        }
        resetStatus();
        this.isRequesting = false;
        return this;
    }

    private final void resetStatus() {
        if (!this.deniedPermissionsList.isEmpty()) {
            this.deniedPermissionsList.clear();
        }
        if (!this.needToRequestPermissionsList.isEmpty()) {
            this.needToRequestPermissionsList.clear();
        }
    }

    public final void getAppDetailSettingIntent(Context context) {
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.addFlags(BasicMeasure.EXACTLY);
        intent.addFlags(8388608);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        Intrinsics.checkNotNull(context);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(intent);
    }

    public final boolean haveLocationPermission(Context applicationContext) {
        Intrinsics.checkNotNullParameter(applicationContext, "applicationContext");
        return this.delegate.haveMediaLocation(applicationContext);
    }

    public final void setNeedToRequestPermissionsList(List<String> permission) {
        Intrinsics.checkNotNullParameter(permission, "permission");
        this.needToRequestPermissionsList.clear();
        this.needToRequestPermissionsList.addAll(permission);
    }

    public final void presentLimited(int type, ResultHandler resultHandler) {
        Intrinsics.checkNotNullParameter(resultHandler, "resultHandler");
        PermissionDelegate permissionDelegate = this.delegate;
        Application application = this.context;
        Intrinsics.checkNotNull(application);
        permissionDelegate.presentLimited(this, application, type, resultHandler);
    }

    public final PermissionResult getAuthValue(int requestType, boolean mediaLocation) {
        PermissionDelegate permissionDelegate = this.delegate;
        Application application = this.context;
        Intrinsics.checkNotNull(application);
        return permissionDelegate.getAuthValue(application, requestType, mediaLocation);
    }
}
