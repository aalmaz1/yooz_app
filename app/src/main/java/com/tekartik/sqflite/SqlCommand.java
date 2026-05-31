package com.tekartik.sqflite;

import android.database.sqlite.SQLiteProgram;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes2.dex */
public class SqlCommand {
    private final List<Object> rawArguments;
    private final String sql;

    public SqlCommand(String str, List<Object> list) {
        this.sql = str;
        this.rawArguments = list == null ? new ArrayList<>() : list;
    }

    private static Object toValue(Object obj) {
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof List)) {
            return obj;
        }
        List list = (List) obj;
        byte[] bArr = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            bArr[i] = (byte) ((Integer) list.get(i)).intValue();
        }
        return bArr;
    }

    public String getSql() {
        return this.sql;
    }

    private Object[] getSqlArguments(List<Object> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            Iterator<Object> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(toValue(it.next()));
            }
        }
        return arrayList.toArray(new Object[0]);
    }

    public void bindTo(SQLiteProgram sQLiteProgram) {
        List<Object> list = this.rawArguments;
        if (list != null) {
            int size = list.size();
            int i = 0;
            while (i < size) {
                Object value = toValue(this.rawArguments.get(i));
                int i2 = i + 1;
                if (value == null) {
                    sQLiteProgram.bindNull(i2);
                } else if (value instanceof byte[]) {
                    sQLiteProgram.bindBlob(i2, (byte[]) value);
                } else if (value instanceof Double) {
                    sQLiteProgram.bindDouble(i2, ((Double) value).doubleValue());
                } else if (value instanceof Integer) {
                    sQLiteProgram.bindLong(i2, ((Integer) value).intValue());
                } else if (value instanceof Long) {
                    sQLiteProgram.bindLong(i2, ((Long) value).longValue());
                } else if (value instanceof String) {
                    sQLiteProgram.bindString(i2, (String) value);
                } else if (value instanceof Boolean) {
                    sQLiteProgram.bindLong(i2, ((Boolean) value).booleanValue() ? 1L : 0L);
                } else {
                    throw new IllegalArgumentException("Could not bind " + value + " from index " + i + ": Supported types are null, byte[], double, long, boolean and String");
                }
                i = i2;
            }
        }
    }

    public String toString() {
        StringBuilder sbAppend = new StringBuilder().append(this.sql);
        List<Object> list = this.rawArguments;
        return sbAppend.append((list == null || list.isEmpty()) ? "" : StringUtils.SPACE + this.rawArguments).toString();
    }

    public Object[] getSqlArguments() {
        return getSqlArguments(this.rawArguments);
    }

    public List<Object> getRawSqlArguments() {
        return this.rawArguments;
    }

    public int hashCode() {
        String str = this.sql;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SqlCommand)) {
            return false;
        }
        SqlCommand sqlCommand = (SqlCommand) obj;
        String str = this.sql;
        if (str != null) {
            if (!str.equals(sqlCommand.sql)) {
                return false;
            }
        } else if (sqlCommand.sql != null) {
            return false;
        }
        if (this.rawArguments.size() != sqlCommand.rawArguments.size()) {
            return false;
        }
        for (int i = 0; i < this.rawArguments.size(); i++) {
            if ((this.rawArguments.get(i) instanceof byte[]) && (sqlCommand.rawArguments.get(i) instanceof byte[])) {
                if (!Arrays.equals((byte[]) this.rawArguments.get(i), (byte[]) sqlCommand.rawArguments.get(i))) {
                    return false;
                }
            } else if (!this.rawArguments.get(i).equals(sqlCommand.rawArguments.get(i))) {
                return false;
            }
        }
        return true;
    }
}
