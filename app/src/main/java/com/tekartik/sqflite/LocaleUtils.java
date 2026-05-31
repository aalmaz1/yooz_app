package com.tekartik.sqflite;

import java.util.Locale;

/* JADX INFO: loaded from: classes2.dex */
public class LocaleUtils {
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
