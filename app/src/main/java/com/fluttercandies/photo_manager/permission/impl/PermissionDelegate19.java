package com.fluttercandies.photo_manager.permission.impl;

import android.app.Application;
import android.content.Context;
import com.fluttercandies.photo_manager.core.entity.PermissionResult;
import com.fluttercandies.photo_manager.permission.PermissionDelegate;
import com.fluttercandies.photo_manager.permission.PermissionsListener;
import com.fluttercandies.photo_manager.permission.PermissionsUtils;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PermissionDelegate19.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\fH\u0016J\u0018\u0010\r\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bH\u0016J(\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u0012"}, d2 = {"Lcom/fluttercandies/photo_manager/permission/impl/PermissionDelegate19;", "Lcom/fluttercandies/photo_manager/permission/PermissionDelegate;", "()V", "getAuthValue", "Lcom/fluttercandies/photo_manager/core/entity/PermissionResult;", "context", "Landroid/app/Application;", "requestType", "", "mediaLocation", "", "haveMediaLocation", "Landroid/content/Context;", "havePermissions", "requestPermission", "", "permissionsUtils", "Lcom/fluttercandies/photo_manager/permission/PermissionsUtils;", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PermissionDelegate19 extends PermissionDelegate {
    @Override // com.fluttercandies.photo_manager.permission.PermissionDelegate
    public boolean haveMediaLocation(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return true;
    }

    @Override // com.fluttercandies.photo_manager.permission.PermissionDelegate
    public boolean havePermissions(Context context, int requestType) {
        Intrinsics.checkNotNullParameter(context, "context");
        return true;
    }

    @Override // com.fluttercandies.photo_manager.permission.PermissionDelegate
    public void requestPermission(PermissionsUtils permissionsUtils, Context context, int requestType, boolean mediaLocation) {
        Intrinsics.checkNotNullParameter(permissionsUtils, "permissionsUtils");
        Intrinsics.checkNotNullParameter(context, "context");
        PermissionsListener permissionsListener = permissionsUtils.getPermissionsListener();
        if (permissionsListener != null) {
            permissionsListener.onGranted(new ArrayList());
        }
    }

    @Override // com.fluttercandies.photo_manager.permission.PermissionDelegate
    public PermissionResult getAuthValue(Application context, int requestType, boolean mediaLocation) {
        Intrinsics.checkNotNullParameter(context, "context");
        return PermissionResult.Authorized;
    }
}
