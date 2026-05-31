package androidx.work.impl.utils;

import android.content.ComponentName;
import android.content.Context;
import androidx.work.Logger;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class PackageManagerHelper {
    private static final String TAG = Logger.tagWithPrefix("PackageManagerHelper");

    private PackageManagerHelper() {
    }

    public static void setComponentEnabled(Context context, Class<?> klazz, boolean enabled) {
        try {
            context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, klazz.getName()), enabled ? 1 : 2, 1);
            Logger.get().debug(TAG, klazz.getName() + StringUtils.SPACE + (enabled ? "enabled" : "disabled"));
        } catch (Exception e) {
            Logger.get().debug(TAG, klazz.getName() + "could not be " + (enabled ? "enabled" : "disabled"), e);
        }
    }

    public static boolean isComponentExplicitlyEnabled(Context context, Class<?> klazz) {
        return isComponentExplicitlyEnabled(context, klazz.getName());
    }

    public static boolean isComponentExplicitlyEnabled(Context context, String className) {
        return context.getPackageManager().getComponentEnabledSetting(new ComponentName(context, className)) == 1;
    }
}
