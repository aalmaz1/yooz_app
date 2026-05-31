package cn.baos.watch.sdk.utils;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public class MacUtils {
    public static String bleMacToBtMac(String str) {
        String hexString;
        String hexString2;
        LogUtil.d("MacUtils--start-->" + str);
        if (!TextUtils.isEmpty(str) && str.startsWith("FB")) {
            try {
                String[] strArrSplit = str.split(":");
                if (strArrSplit.length == 6) {
                    int iDecodeHEX = FileUtils.decodeHEX(strArrSplit[0]) + 1;
                    int iDecodeHEX2 = FileUtils.decodeHEX(strArrSplit[5]) + 1;
                    if (iDecodeHEX > 255) {
                        iDecodeHEX = 0;
                    }
                    if (iDecodeHEX2 > 255) {
                        iDecodeHEX2 = 0;
                    }
                    try {
                        hexString = Integer.toHexString(iDecodeHEX);
                        try {
                            hexString2 = Integer.toHexString(iDecodeHEX2);
                            try {
                                if (hexString.length() == 1) {
                                    hexString = "0" + hexString;
                                }
                                if (hexString2.length() == 1) {
                                    hexString2 = "0" + hexString2;
                                }
                            } catch (Exception e) {
                                e = e;
                                e.printStackTrace();
                            }
                        } catch (Exception e2) {
                            e = e2;
                            hexString2 = "";
                        }
                    } catch (Exception e3) {
                        e = e3;
                        hexString = "";
                        hexString2 = hexString;
                    }
                    strArrSplit[0] = hexString.toUpperCase();
                    strArrSplit[5] = hexString2.toUpperCase();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < strArrSplit.length; i++) {
                        String str2 = strArrSplit[i];
                        if (i != strArrSplit.length - 1) {
                            str2 = str2 + ":";
                        }
                        sb.append(str2);
                    }
                    LogUtil.d("MacUtils--end-->" + sb.toString());
                    return sb.toString();
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
        LogUtil.d("MacUtils--end-->");
        return "";
    }
}
