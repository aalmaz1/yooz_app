package cn.baos.watch.sdk.database.alldata;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cn.baos.watch.sdk.database.DatabaseHelper;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class DatabaseAllHandler implements IDatabaseAllHandler {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;

    @Override // cn.baos.watch.sdk.database.alldata.IDatabaseAllHandler
    public void delete(String str) {
    }

    @Override // cn.baos.watch.sdk.database.alldata.IDatabaseAllHandler
    public ArrayList<String> getAlEntities() {
        return null;
    }

    @Override // cn.baos.watch.sdk.database.alldata.IDatabaseAllHandler
    public boolean hasSportRecordEntity(String str) {
        return false;
    }

    @Override // cn.baos.watch.sdk.database.alldata.IDatabaseAllHandler
    public void insert(String str) {
    }

    @Override // cn.baos.watch.sdk.database.alldata.IDatabaseAllHandler
    public String query(long j) {
        return null;
    }

    @Override // cn.baos.watch.sdk.database.alldata.IDatabaseAllHandler
    public ArrayList<String> queryArrayBetween(long j, long j2) {
        return null;
    }

    @Override // cn.baos.watch.sdk.database.alldata.IDatabaseAllHandler
    public void update(String str) {
    }

    public DatabaseAllHandler(Context context) {
        this.mContext = context;
    }

    @Override // cn.baos.watch.sdk.database.alldata.IDatabaseAllHandler
    public void createDatabase() {
        this.dbHelper = new DatabaseHelper(this.mContext);
    }

    @Override // cn.baos.watch.sdk.database.alldata.IDatabaseAllHandler
    public void open() throws SQLException {
        this.database = this.dbHelper.getWritableDatabase();
    }

    @Override // cn.baos.watch.sdk.database.alldata.IDatabaseAllHandler
    public void close() {
        this.dbHelper.close();
    }

    public void clearTable() {
        this.database.delete(DatabaseHelper.getSportRecordTableName(), null, null);
    }
}
