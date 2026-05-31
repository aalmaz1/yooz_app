package com.tekartik.sqflite;

import android.database.Cursor;
import android.util.Log;
import com.tekartik.sqflite.dev.Debug;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes2.dex */
public class Utils {
    public static List<Object> cursorRowToList(Cursor cursor, int i) {
        String name;
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            Object objCursorValue = cursorValue(cursor, i2);
            if (Debug.EXTRA_LOGV) {
                if (objCursorValue == null) {
                    name = null;
                } else if (objCursorValue.getClass().isArray()) {
                    name = "array(" + objCursorValue.getClass().getComponentType().getName() + ")";
                } else {
                    name = objCursorValue.getClass().getName();
                }
                Log.d(Constant.TAG, "column " + i2 + StringUtils.SPACE + cursor.getType(i2) + ": " + objCursorValue + (name == null ? "" : " (" + name + ")"));
            }
            arrayList.add(objCursorValue);
        }
        return arrayList;
    }

    public static Object cursorValue(Cursor cursor, int i) {
        int type = cursor.getType(i);
        if (type == 1) {
            return Long.valueOf(cursor.getLong(i));
        }
        if (type == 2) {
            return Double.valueOf(cursor.getDouble(i));
        }
        if (type == 3) {
            return cursor.getString(i);
        }
        if (type != 4) {
            return null;
        }
        return cursor.getBlob(i);
    }

    static Locale localeForLanguateTag(String str) {
        return localeForLanguageTag21(str);
    }

    static Locale localeForLanguageTag21(String str) {
        return Locale.forLanguageTag(str);
    }

    static Locale localeForLanguageTagPre21(String str) {
        String str2;
        String str3;
        String str4;
        String[] strArrSplit = str.split("-");
        str2 = "";
        if (strArrSplit.length > 0) {
            String str5 = strArrSplit[0];
            if (strArrSplit.length > 1) {
                str4 = strArrSplit[1];
                str3 = strArrSplit.length > 2 ? strArrSplit[strArrSplit.length - 1] : "";
            } else {
                str3 = "";
                str4 = str3;
            }
            str2 = str5;
        } else {
            str3 = "";
            str4 = str3;
        }
        return new Locale(str2, str4, str3);
    }
}
