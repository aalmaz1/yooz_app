package cn.baos.watch.sdk.util;

import android.content.Context;
import android.os.Binder;
import androidx.core.content.PermissionChecker;
import com.google.android.gms.common.internal.ImagesContract;
import com.tekartik.sqflite.Constant;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0007J\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\n\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u000e\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u000f"}, d2 = {"Lcn/baos/watch/sdk/util/AppUtils;", "", "()V", "checkPermissions", "", "mContext", "Landroid/content/Context;", "permission", "", "isKeepLive", ImagesContract.LOCAL, "registerKeepLive", "", Constant.METHOD_UPDATE, "unregister", "lib_watch_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AppUtils {
    public final void registerKeepLive(boolean update, Context mContext) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
    }

    public final void unregister(Context mContext) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
    }

    public final boolean isKeepLive(Context mContext) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        return checkPermissions(mContext, "android.permission.ACCESS_COARSE_LOCATION") && checkPermissions(mContext, "android.permission.ACCESS_FINE_LOCATION");
    }

    public final boolean checkPermissions(Context mContext, String permission) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        if (mContext.getApplicationInfo().targetSdkVersion < 23) {
            Intrinsics.checkNotNull(permission);
            if (PermissionChecker.checkPermission(mContext, permission, Binder.getCallingPid(), Binder.getCallingUid(), mContext.getPackageName()) == 0) {
                return true;
            }
        }
        Intrinsics.checkNotNull(permission);
        return mContext.checkSelfPermission(permission) == 0;
    }

    public final String local(Context mContext) {
        Intrinsics.checkNotNullParameter(mContext, "mContext");
        try {
            Locale locale = mContext.getResources().getConfiguration().getLocales().get(0);
            Intrinsics.checkNotNull(locale);
            return locale.getLanguage() + "-" + locale.getCountry();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
