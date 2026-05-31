package androidx.webkit.internal;

import java.net.URLConnection;

/* JADX INFO: loaded from: classes.dex */
class MimeUtil {
    MimeUtil() {
    }

    public static String getMimeFromFileName(String str) {
        if (str == null) {
            return null;
        }
        String strGuessContentTypeFromName = URLConnection.guessContentTypeFromName(str);
        return strGuessContentTypeFromName != null ? strGuessContentTypeFromName : guessHardcodedMime(str);
    }

    private static String guessHardcodedMime(String str) {
        int iLastIndexOf = str.lastIndexOf(46);
        if (iLastIndexOf == -1) {
            return null;
        }
        String lowerCase = str.substring(iLastIndexOf + 1).toLowerCase();
        lowerCase.hashCode();
        switch (lowerCase) {
        }
        return null;
    }
}
