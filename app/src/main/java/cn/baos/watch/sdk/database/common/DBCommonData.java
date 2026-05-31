package cn.baos.watch.sdk.database.common;

import cn.baos.watch.sdk.bluetooth.bt.BleUtils;

/* JADX INFO: loaded from: classes.dex */
public class DBCommonData {
    public static String queryArrayBetween(String str, String str2) {
        return "select * from " + str + " where " + str2 + " between ? and ? and (mac = '" + BleUtils.getCurrentMac() + "' or mac is null)";
    }

    public static String queryAllData(String str) {
        return "SELECT *  FROM (    SELECT *    FROM  " + str + "  WHERE mac =  " + BleUtils.getCurrentMac() + "  UNION ALL    SELECT *    FROM your_table    WHERE mac != " + BleUtils.getCurrentMac() + " AND time NOT IN (SELECT time FROM " + str + " WHERE mac = " + BleUtils.getCurrentMac() + ")  ) AS combined_data  WHERE time BETWEEN ? AND ?";
    }
}
