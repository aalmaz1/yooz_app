package cn.baos.watch.sdk.database;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import cn.baos.watch.sdk.util.FileUtils;
import cn.baos.watch.sdk.util.LogUtil;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class DatabaseContext extends ContextWrapper {
    public DatabaseContext(Context context) {
        super(context);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public File getDatabasePath(String str) {
        File file = null;
        if (!FileUtils.isSDCardAvailable()) {
            LogUtil.d("database--->no sdcard is found");
            return null;
        }
        String dirAndCreate = FileUtils.getDirAndCreate(this, DatabaseHelper.DATABASE_NAME);
        if (dirAndCreate == null) {
            LogUtil.d("parent path make fail!!!");
            return null;
        }
        File file2 = new File(dirAndCreate, str + ".db");
        if (file2.exists()) {
            return file2;
        }
        try {
            if (file2.createNewFile()) {
                try {
                    LogUtil.d("dateBase path new create:" + file2.getAbsolutePath());
                    file = file2;
                } catch (IOException e) {
                    e = e;
                    file = file2;
                    e.printStackTrace();
                }
            }
        } catch (IOException e2) {
            e = e2;
        }
        return file;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public SQLiteDatabase openOrCreateDatabase(String str, int i, SQLiteDatabase.CursorFactory cursorFactory) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(str), (SQLiteDatabase.CursorFactory) null);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public SQLiteDatabase openOrCreateDatabase(String str, int i, SQLiteDatabase.CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(str), (SQLiteDatabase.CursorFactory) null);
    }
}
