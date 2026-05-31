package cn.baos.watch.sdk.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class SyncDataUtils {
    public static String changeSportIntToName(int i) {
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? "未知项目" : "自由锻炼" : "户外骑行" : "健走" : "室内跑" : "户外跑";
    }

    public static int getZeroTimeStamp(int i) {
        long time;
        LogUtil.d("localDb->传入时间戳:" + i);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String str = simpleDateFormat.format(new Date(((long) i) * 1000));
        LogUtil.d("localDb->传入时间戳对应日期:" + str);
        try {
            time = simpleDateFormat.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            time = 0;
        }
        int i2 = (int) (time / 1000);
        LogUtil.d("localDb->时间戳:" + i + " 转成当天0点时间戳:" + i2);
        return i2;
    }

    public static int getZeroHourTimeStamp(int i) {
        long time;
        LogUtil.d("localDb->传入时间戳:" + i);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        String str = simpleDateFormat.format(new Date(((long) i) * 1000));
        LogUtil.d("localDb->传入时间戳对应日期:" + str);
        try {
            time = simpleDateFormat.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            time = 0;
        }
        int i2 = (int) (time / 1000);
        LogUtil.d("localDb->时间戳:" + i + " 转成小时整点时间戳:" + i2);
        return i2;
    }

    public static String changeIntArrToString(int[] iArr) {
        if (iArr == null) {
            return "0";
        }
        String str = "";
        for (int i = 0; i < iArr.length; i++) {
            if (i != iArr.length - 1) {
                str = str + iArr[i] + ",";
            } else {
                str = str + iArr[i];
            }
        }
        return str;
    }

    public static String[] changeStringToStringArr(String str) {
        return str.split(",");
    }

    public static int changeStringToIntSum(String str) {
        int i = 0;
        for (String str2 : changeStringToStringArr(str)) {
            i += Integer.parseInt(str2);
        }
        return i;
    }

    public static int changeStringToIntAverage(String str) {
        String[] strArrChangeStringToStringArr = changeStringToStringArr(str);
        int i = 0;
        for (String str2 : strArrChangeStringToStringArr) {
            i += Integer.parseInt(str2);
        }
        return i / strArrChangeStringToStringArr.length;
    }

    public static int getStringToIntFindMax(String str) {
        String[] strArrChangeStringToStringArr = changeStringToStringArr(str);
        int i = 0;
        for (int i2 = 0; i2 < strArrChangeStringToStringArr.length; i2++) {
            if (i < Integer.parseInt(strArrChangeStringToStringArr[i2])) {
                i = Integer.parseInt(strArrChangeStringToStringArr[i2]);
            }
        }
        return i;
    }

    public static String changeSecondToMMss(int i) {
        if (i <= 0) {
            return "00'00\"";
        }
        int i2 = i / 60;
        if (i2 < 60) {
            return unitFormat(i2) + "'" + unitFormat(i % 60) + "\"";
        }
        int i3 = i2 / 60;
        if (i3 > 99) {
            return "59':59\"";
        }
        int i4 = i2 % 60;
        return unitFormat(i4) + "':" + unitFormat((i - (i3 * 3600)) - (i4 * 60)) + "\"";
    }

    public static String changeSecondToHHmm(int i) {
        if (i <= 0) {
            return "00:00'";
        }
        int i2 = i / 60;
        if (i2 < 60) {
            return unitFormat(0) + ":" + unitFormat(i2) + "'";
        }
        int i3 = i2 / 60;
        return i3 > 99 ? "99:59'" : unitFormat(i3) + ":" + unitFormat(i2 % 60) + "'";
    }

    public static String changeSecondToHHMMSS(int i) {
        if (i <= 0) {
            return "00:00'";
        }
        int i2 = i / 60;
        if (i2 < 60) {
            return unitFormat(0) + ":" + unitFormat(i2) + "'" + unitFormat(i % 60) + "\"";
        }
        int i3 = i2 / 60;
        if (i3 > 99) {
            return "99:59'59\"";
        }
        int i4 = i2 % 60;
        return unitFormat(i3) + ":" + unitFormat(i4) + "'" + unitFormat((i - (i3 * 3600)) - (i4 * 60)) + "\"";
    }

    public static String unitFormat(int i) {
        if (i >= 0 && i < 10) {
            return "0" + Integer.toString(i);
        }
        return "" + i;
    }
}
