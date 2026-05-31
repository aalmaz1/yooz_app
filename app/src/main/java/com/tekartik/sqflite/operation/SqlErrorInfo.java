package com.tekartik.sqflite.operation;

import com.tekartik.sqflite.Constant;
import com.tekartik.sqflite.SqlCommand;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class SqlErrorInfo {
    public static Map<String, Object> getMap(Operation operation) {
        SqlCommand sqlCommand = operation.getSqlCommand();
        if (sqlCommand == null) {
            return null;
        }
        HashMap map = new HashMap();
        map.put(Constant.PARAM_SQL, sqlCommand.getSql());
        map.put("arguments", sqlCommand.getRawSqlArguments());
        return map;
    }
}
