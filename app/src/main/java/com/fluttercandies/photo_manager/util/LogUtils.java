package com.fluttercandies.photo_manager.util;

import android.database.Cursor;
import android.util.Log;
import com.tekartik.sqflite.Constant;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: LogUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u0007J\u0012\u0010\r\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u0007J\u001c\u0010\r\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u00012\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0007J\u0012\u0010\u000f\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u0007J\u001e\u0010\u0010\u001a\u00020\u000b2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u0014"}, d2 = {"Lcom/fluttercandies/photo_manager/util/LogUtils;", "", "()V", "TAG", "", "isLog", "", "()Z", "setLog", "(Z)V", Constant.METHOD_DEBUG, "", "object", Constant.PARAM_ERROR, "", "info", "logCursor", "cursor", "Landroid/database/Cursor;", "idKey", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LogUtils {
    public static final LogUtils INSTANCE = new LogUtils();
    public static final String TAG = "PhotoManager";
    private static boolean isLog;

    private LogUtils() {
    }

    public final boolean isLog() {
        return isLog;
    }

    public final void setLog(boolean z) {
        isLog = z;
    }

    @JvmStatic
    public static final void info(Object object) {
        String string;
        if (isLog) {
            if (object == null || (string = object.toString()) == null) {
                string = "null";
            }
            Log.i(TAG, string);
        }
    }

    @JvmStatic
    public static final void debug(Object object) {
        String string;
        if (isLog) {
            if (object == null || (string = object.toString()) == null) {
                string = "null";
            }
            Log.d(TAG, string);
        }
    }

    @JvmStatic
    public static final void error(Object object, Throwable error) {
        if (isLog) {
            String localizedMessage = object instanceof Exception ? ((Exception) object).getLocalizedMessage() : object != null ? object.toString() : null;
            if (localizedMessage == null) {
                localizedMessage = "null";
            }
            Log.e(TAG, localizedMessage, error);
        }
    }

    @JvmStatic
    public static final void error(Object object) {
        if (isLog) {
            String localizedMessage = object instanceof Exception ? ((Exception) object).getLocalizedMessage() : object != null ? object.toString() : null;
            if (localizedMessage == null) {
                localizedMessage = "null";
            }
            Log.e(TAG, localizedMessage);
        }
    }

    public static /* synthetic */ void logCursor$default(Cursor cursor, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = "_id";
        }
        logCursor(cursor, str);
    }

    @JvmStatic
    public static final void logCursor(Cursor cursor, String idKey) {
        String string;
        if (cursor == null) {
            debug("The cursor is null");
            return;
        }
        debug("The cursor row: " + cursor.getCount());
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            StringBuilder sb = new StringBuilder();
            int columnIndex = cursor.getColumnIndex(idKey);
            if (columnIndex != -1) {
                sb.append("\nid: ").append(cursor.getString(columnIndex)).append("\n");
            }
            String[] columnNames = cursor.getColumnNames();
            Intrinsics.checkNotNullExpressionValue(columnNames, "getColumnNames(...)");
            for (String str : columnNames) {
                int columnIndex2 = cursor.getColumnIndex(str);
                try {
                    string = cursor.getString(columnIndex2);
                } catch (Exception e) {
                    e.printStackTrace();
                    string = "blob(" + cursor.getBlob(columnIndex2).length + ")";
                }
                if (!StringsKt.equals(str, idKey, true)) {
                    sb.append("|--").append(str).append(" : ").append(string).append("\n");
                }
            }
            debug(sb);
        }
        cursor.moveToPosition(-1);
    }
}
