package cn.baos.watch.sdk.database.fromwatch;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/* JADX INFO: loaded from: classes.dex */
public abstract class DataBaseFartherHandler {
    public abstract SQLiteDatabase getDatabase();

    public abstract String getTableName();

    public int queryLatestTime() {
        Cursor cursorQuery = getDatabase().query(getTableName(), null, null, null, null, null, null);
        cursorQuery.moveToLast();
        int i = cursorQuery.getCount() == 0 ? 1588057233 : cursorQuery.getInt(3);
        cursorQuery.close();
        return i;
    }

    public int queryLatestTime(int i) {
        Cursor cursorQuery = getDatabase().query(getTableName(), null, null, null, null, null, null);
        cursorQuery.moveToLast();
        int i2 = cursorQuery.getCount() == 0 ? 1588057233 : cursorQuery.getInt(i);
        cursorQuery.close();
        return i2;
    }
}
