package com.fluttercandies.photo_manager.permission.impl;

import android.app.Application;
import android.content.Context;
import com.fluttercandies.photo_manager.core.entity.PermissionResult;
import com.fluttercandies.photo_manager.permission.PermissionDelegate;
import com.fluttercandies.photo_manager.permission.PermissionsListener;
import com.fluttercandies.photo_manager.permission.PermissionsUtils;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PermissionDelegate29.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\fH\u0016J(\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u0012"}, d2 = {"Lcom/fluttercandies/photo_manager/permission/impl/PermissionDelegate29;", "Lcom/fluttercandies/photo_manager/permission/impl/PermissionDelegate23;", "()V", "getAuthValue", "Lcom/fluttercandies/photo_manager/core/entity/PermissionResult;", "context", "Landroid/app/Application;", "requestType", "", "mediaLocation", "", "haveMediaLocation", "Landroid/content/Context;", "requestPermission", "", "permissionsUtils", "Lcom/fluttercandies/photo_manager/permission/PermissionsUtils;", "Companion", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PermissionDelegate29 extends PermissionDelegate23 {
    private static final String mediaLocationPermission = "android.permission.ACCESS_MEDIA_LOCATION";
    private static final String readPermission = "android.permission.READ_EXTERNAL_STORAGE";
    private static final String writePermission = "android.permission.WRITE_EXTERNAL_STORAGE";

    @Override // com.fluttercandies.photo_manager.permission.impl.PermissionDelegate23, com.fluttercandies.photo_manager.permission.PermissionDelegate
    public void requestPermission(PermissionsUtils permissionsUtils, Context context, int requestType, boolean mediaLocation) {
        Intrinsics.checkNotNullParameter(permissionsUtils, "permissionsUtils");
        Intrinsics.checkNotNullParameter(context, "context");
        List<String> listMutableListOf = CollectionsKt.mutableListOf(readPermission, writePermission);
        if (mediaLocation) {
            listMutableListOf.add(mediaLocationPermission);
        }
        String[] strArr = (String[]) listMutableListOf.toArray(new String[0]);
        if (havePermissions(context, (String[]) Arrays.copyOf(strArr, strArr.length))) {
            PermissionsListener permissionsListener = permissionsUtils.getPermissionsListener();
            if (permissionsListener != null) {
                permissionsListener.onGranted(listMutableListOf);
                return;
            }
            return;
        }
        PermissionDelegate.requestPermission$default(this, permissionsUtils, listMutableListOf, 0, 4, null);
    }

    @Override // com.fluttercandies.photo_manager.permission.impl.PermissionDelegate23, com.fluttercandies.photo_manager.permission.PermissionDelegate
    public boolean haveMediaLocation(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return havePermission(context, mediaLocationPermission);
    }

    @Override // com.fluttercandies.photo_manager.permission.impl.PermissionDelegate23, com.fluttercandies.photo_manager.permission.PermissionDelegate
    public PermissionResult getAuthValue(Application context, int requestType, boolean mediaLocation) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (havePermissions(context, readPermission, writePermission)) {
            return PermissionResult.Authorized;
        }
        return PermissionResult.Denied;
    }
}
