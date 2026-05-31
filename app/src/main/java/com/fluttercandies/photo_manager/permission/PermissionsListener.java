package com.fluttercandies.photo_manager.permission;

import java.util.List;
import kotlin.Metadata;

/* JADX INFO: compiled from: PermissionsListener.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J2\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J\u0016\u0010\t\u001a\u00020\u00032\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&¨\u0006\n"}, d2 = {"Lcom/fluttercandies/photo_manager/permission/PermissionsListener;", "", "onDenied", "", "deniedPermissions", "", "", "grantedPermissions", "needPermissions", "onGranted", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PermissionsListener {
    void onDenied(List<String> deniedPermissions, List<String> grantedPermissions, List<String> needPermissions);

    void onGranted(List<String> needPermissions);
}
