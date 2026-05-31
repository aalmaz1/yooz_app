package cn.yoozworld.watch.utils.notifi;

import android.util.Log;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public final class LoggerUtils {
    private static Logger sLogger;

    public interface Logger {
        boolean debugLogEnable();

        void log(String str);
    }

    public static void log(String str) {
        if (sLogger == null) {
            return;
        }
        log("", str);
    }

    public static void log(Object obj, String str) {
        if (sLogger == null) {
            return;
        }
        String str2 = (obj != null ? obj.toString() : "") + StringUtils.SPACE + str;
        sLogger.log(str2);
        if (sLogger.debugLogEnable()) {
            Log.i("LoggerUtils", str2);
        }
    }

    public static void setLogger(Logger logger) {
        sLogger = logger;
    }

    public static Logger getLogger() {
        return sLogger;
    }
}
