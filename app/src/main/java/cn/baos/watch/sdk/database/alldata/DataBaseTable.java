package cn.baos.watch.sdk.database.alldata;

/* JADX INFO: loaded from: classes.dex */
public class DataBaseTable {
    public static String tableName = "TABLE_ALL_DATA";
    public static String createSql = "CREATE TABLE " + tableName + " (dataId INTEGER PRIMARY KEY AUTOINCREMENT,userId TEXT,deviceId TEXT,categaid TEXT,dataJson TEXT);";

    public static void insert(String str, String str2, String str3) {
        String str4 = "INSERT INTO TABLE_NAME [(userId, deviceId, dataJson)] VALUES(" + str + "," + str2 + "," + str3 + ",)";
    }
}
