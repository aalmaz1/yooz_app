package com.google.gson.internal.bind.util;

import cn.baos.message.CatagoryEnum;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes2.dex */
public class ISO8601Utils {
    private static final String UTC_ID = "UTC";
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone(UTC_ID);

    public static String format(Date date) {
        return format(date, false, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean z) {
        return format(date, z, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean z, TimeZone timeZone) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone, Locale.US);
        gregorianCalendar.setTime(date);
        StringBuilder sb = new StringBuilder(19 + (z ? 4 : 0) + (timeZone.getRawOffset() == 0 ? 1 : 6));
        padInt(sb, gregorianCalendar.get(1), 4);
        sb.append('-');
        padInt(sb, gregorianCalendar.get(2) + 1, 2);
        sb.append('-');
        padInt(sb, gregorianCalendar.get(5), 2);
        sb.append('T');
        padInt(sb, gregorianCalendar.get(11), 2);
        sb.append(':');
        padInt(sb, gregorianCalendar.get(12), 2);
        sb.append(':');
        padInt(sb, gregorianCalendar.get(13), 2);
        if (z) {
            sb.append('.');
            padInt(sb, gregorianCalendar.get(14), 3);
        }
        int offset = timeZone.getOffset(gregorianCalendar.getTimeInMillis());
        if (offset != 0) {
            int i = offset / CatagoryEnum.COMMANDBLEOTAFILEINFO;
            int iAbs = Math.abs(i / 60);
            int iAbs2 = Math.abs(i % 60);
            sb.append(offset >= 0 ? '+' : '-');
            padInt(sb, iAbs, 2);
            sb.append(':');
            padInt(sb, iAbs2, 2);
        } else {
            sb.append('Z');
        }
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00d8 A[Catch: IllegalArgumentException -> 0x01c4, NumberFormatException -> 0x01c6, IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01c8, TryCatch #2 {IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01c8, blocks: (B:3:0x000a, B:5:0x001c, B:6:0x001e, B:8:0x002a, B:9:0x002c, B:11:0x003c, B:13:0x0042, B:17:0x0059, B:19:0x0069, B:20:0x006b, B:22:0x0077, B:23:0x0079, B:25:0x007f, B:29:0x0089, B:34:0x0099, B:36:0x00a1, B:48:0x00d2, B:50:0x00d8, B:52:0x00df, B:78:0x018a, B:58:0x00ee, B:59:0x0106, B:60:0x0107, B:64:0x0125, B:66:0x0132, B:69:0x013b, B:71:0x0156, B:74:0x0165, B:75:0x0185, B:77:0x0188, B:63:0x0112, B:80:0x01bc, B:81:0x01c3, B:41:0x00ba, B:42:0x00bd), top: B:97:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01bc A[Catch: IllegalArgumentException -> 0x01c4, NumberFormatException -> 0x01c6, IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01c8, TryCatch #2 {IndexOutOfBoundsException | NumberFormatException | IllegalArgumentException -> 0x01c8, blocks: (B:3:0x000a, B:5:0x001c, B:6:0x001e, B:8:0x002a, B:9:0x002c, B:11:0x003c, B:13:0x0042, B:17:0x0059, B:19:0x0069, B:20:0x006b, B:22:0x0077, B:23:0x0079, B:25:0x007f, B:29:0x0089, B:34:0x0099, B:36:0x00a1, B:48:0x00d2, B:50:0x00d8, B:52:0x00df, B:78:0x018a, B:58:0x00ee, B:59:0x0106, B:60:0x0107, B:64:0x0125, B:66:0x0132, B:69:0x013b, B:71:0x0156, B:74:0x0165, B:75:0x0185, B:77:0x0188, B:63:0x0112, B:80:0x01bc, B:81:0x01c3, B:41:0x00ba, B:42:0x00bd), top: B:97:0x000a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.Date parse(java.lang.String r19, java.text.ParsePosition r20) throws java.text.ParseException {
        /*
            Method dump skipped, instruction units count: 561
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.util.ISO8601Utils.parse(java.lang.String, java.text.ParsePosition):java.util.Date");
    }

    private static boolean checkOffset(String str, int i, char c) {
        return i < str.length() && str.charAt(i) == c;
    }

    private static int parseInt(String str, int i, int i2) throws NumberFormatException {
        int i3;
        int i4;
        if (i < 0 || i2 > str.length() || i > i2) {
            throw new NumberFormatException(str);
        }
        if (i < i2) {
            i4 = i + 1;
            int iDigit = Character.digit(str.charAt(i), 10);
            if (iDigit < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            i3 = -iDigit;
        } else {
            i3 = 0;
            i4 = i;
        }
        while (i4 < i2) {
            int i5 = i4 + 1;
            int iDigit2 = Character.digit(str.charAt(i4), 10);
            if (iDigit2 < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            i3 = (i3 * 10) - iDigit2;
            i4 = i5;
        }
        return -i3;
    }

    private static void padInt(StringBuilder sb, int i, int i2) {
        String string = Integer.toString(i);
        for (int length = i2 - string.length(); length > 0; length--) {
            sb.append('0');
        }
        sb.append(string);
    }

    private static int indexOfNonDigit(String str, int i) {
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt < '0' || cCharAt > '9') {
                return i;
            }
            i++;
        }
        return str.length();
    }
}
