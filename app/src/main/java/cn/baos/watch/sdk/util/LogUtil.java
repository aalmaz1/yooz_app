package cn.baos.watch.sdk.util;

import android.text.TextUtils;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class LogUtil {
    public static boolean showD = true;
    public static boolean showE = true;
    public static boolean showI = true;
    public static boolean showV = true;
    public static boolean showW = true;
    public static boolean showWTF = true;
    public static String tagPrefix = "w100log";

    public static void setLogEnable(boolean z) {
        showV = z;
        showD = z;
        showI = z;
        showW = z;
        showE = z;
        showWTF = z;
    }

    private static String generateTag() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
        String className = stackTraceElement.getClassName();
        String str = String.format("%s.%s(L:%d)", className.substring(className.lastIndexOf(".") + 1), stackTraceElement.getMethodName(), Integer.valueOf(stackTraceElement.getLineNumber()));
        return TextUtils.isEmpty(tagPrefix) ? str : tagPrefix + ":" + str;
    }

    public static void v(String str) {
        if (showV) {
            Log.v(generateTag(), changeMsg(str));
        }
    }

    public static void v(String str, Throwable th) {
        if (showV) {
            Log.v(generateTag(), changeMsg(str), th);
        }
    }

    public static void d(String str) {
        if (showD) {
            Log.d(generateTag(), changeMsg(str));
        }
    }

    public static void d(String str, Throwable th) {
        if (showD) {
            Log.d(generateTag(), changeMsg(str), th);
        }
    }

    public static void i(String str) {
        if (showI) {
            Log.i(generateTag(), changeMsg(str));
        }
    }

    public static void i(String str, Throwable th) {
        if (showI) {
            Log.i(generateTag(), changeMsg(str), th);
        }
    }

    public static void w(String str) {
        if (showW) {
            Log.w(generateTag(), changeMsg(str));
        }
    }

    public static void w(String str, Throwable th) {
        if (showW) {
            Log.w(generateTag(), changeMsg(str), th);
        }
    }

    public static void e(String str) {
        if (showE) {
            Log.e(generateTag(), changeMsg(str));
        }
    }

    public static void e(String str, Throwable th) {
        if (showE) {
            Log.e(generateTag(), changeMsg(str), th);
        }
    }

    public static void wtf(String str) {
        if (showWTF) {
            Log.wtf(generateTag(), changeMsg(str));
        }
    }

    public static void wtf(String str, Throwable th) {
        if (showWTF) {
            Log.wtf(generateTag(), changeMsg(str), th);
        }
    }

    public static String changeMsg(String str) {
        return "✡" + str + "✡";
    }
}
