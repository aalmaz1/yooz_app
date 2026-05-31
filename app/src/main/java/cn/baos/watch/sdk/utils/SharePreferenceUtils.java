package cn.baos.watch.sdk.utils;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
public class SharePreferenceUtils {
    public static String KEY_AMAP_TERMINALID = "KEY_AMAP_TERMINALID";
    public static String KEY_AMAP_UUID = "KEY_AMAP_UUID";
    public static String KEY_BOND_BT_DIALOG = "KEY_BOND_BT_DIALOG";
    public static String KEY_BOND_BT_DIALOG_TWO = "KEY_BOND_BT_DIALOG_TWO";
    public static String KEY_BOND_BT_SUCCESS = "KEY_BOND_BT_SUCCESS";
    public static String KEY_CONNECT_BT_MAC = "KEY_CONNECT_BT_MAC";
    public static String KEY_CONNECT_MAC = "KEY_CONNECT_MAC";
    public static String KEY_DEVICE_RESOURCE_LANGUAGE = "KEY_DEVICE_RESOURCE_LANGUAGE";
    public static String KEY_LAST_MAC = "KEY_LAST_MAC";
    public static String KEY_LOCAL_Language = "KEY_LOCAL_Language";
    public static String KEY_MTU_SETTING = "KEY_MTU_SETTING";
    public static String KEY_PAIR_CODE = "KEY_PAIR_CODE";
    public static String KEY_PHONE_TYPE_DEVICEID = "KEY_PHONE_TYPE_DEVICEID";
    public static String KEY_PHONE_TYPE_USERID = "KEY_PHONE_TYPE_USERID";
    public static String KEY_RELOAD_CLOSE = "KEY_RELOAD_CLOSE";
    public static String KEY_SENSOR_ACCURACY = "KEY_SENSOR_ACCURACY";
    public static String KEY_STEP_SUM = "KEY_PHONE_CRASH_FILE";
    public static String KEY_WATCH_LUANGH = "KEY_WATCH_LUANGH";
    private static String shareName = "WatchSdkDb";

    public static void clear(Context context) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(shareName, 0).edit();
        editorEdit.clear();
        editorEdit.commit();
    }

    public static void saveIntByKey(Context context, String str, int i) {
        if (context == null) {
            return;
        }
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(shareName, 0).edit();
        editorEdit.putInt(str, i);
        editorEdit.commit();
    }

    public static void saveStringByKey(Context context, String str, String str2) {
        if (context == null) {
            return;
        }
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(shareName, 0).edit();
        editorEdit.putString(str, str2);
        editorEdit.commit();
    }

    public static int queryIntByKey(Context context, String str, int i) {
        return context == null ? i : context.getSharedPreferences(shareName, 0).getInt(str, i);
    }

    public static String queryStringByKey(Context context, String str) {
        if (context == null) {
            return null;
        }
        return context.getSharedPreferences(shareName, 0).getString(str, "");
    }

    public static String queryStringByKey(Context context, String str, String str2) {
        return context == null ? "" : context.getSharedPreferences(shareName, 0).getString(str, str2);
    }

    public static void saveBooleanByKey(Context context, String str, boolean z) {
        try {
            if (context == null) {
                LogUtil.e("saveBooleanByKey->" + str + ":上下文为空,查询值为空字符串");
                return;
            }
            SharedPreferences.Editor editorEdit = context.getSharedPreferences(shareName, 0).edit();
            editorEdit.putBoolean(str, z);
            editorEdit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean queryBooleanByKey(Context context, String str) {
        if (context == null) {
            return false;
        }
        return context.getSharedPreferences(shareName, 0).getBoolean(str, true);
    }

    public static boolean queryBooleanByKeySetBoolean(Context context, String str, boolean z) {
        if (context == null) {
            return false;
        }
        try {
            return context.getSharedPreferences(shareName, 0).getBoolean(str, z);
        } catch (Exception e) {
            e.printStackTrace();
            return z;
        }
    }
}
