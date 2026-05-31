package cn.baos.watch.sdk.database.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cn.baos.watch.sdk.database.DatabaseHelper;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.entitiy.PhoneContactsEntity;
import cn.baos.watch.sdk.util.LogUtil;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class DatabaseContactsHandler extends DataBaseFartherHandler implements IDatabaseContactsHandler {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;
    private String mTableName = DatabaseHelper.getContactsTableName();
    private String mColumeTimeStamp = DatabaseHelper.getContactsTime();

    public DatabaseContactsHandler(Context context) {
        this.mContext = context;
    }

    @Override // cn.baos.watch.sdk.database.contacts.IDatabaseContactsHandler
    public void createDatabase() {
        this.dbHelper = new DatabaseHelper(this.mContext);
    }

    @Override // cn.baos.watch.sdk.database.contacts.IDatabaseContactsHandler
    public void open() throws SQLException {
        this.database = this.dbHelper.getWritableDatabase();
    }

    @Override // cn.baos.watch.sdk.database.contacts.IDatabaseContactsHandler
    public void close() {
        this.dbHelper.close();
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler
    public SQLiteDatabase getDatabase() {
        return this.database;
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler
    public String getTableName() {
        return this.mTableName;
    }

    @Override // cn.baos.watch.sdk.database.contacts.IDatabaseContactsHandler
    public void insert(PhoneContactsEntity phoneContactsEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.mColumeTimeStamp, Long.valueOf(System.currentTimeMillis()));
        contentValues.put(DatabaseHelper.getContactsName(), phoneContactsEntity.name);
        contentValues.put(DatabaseHelper.getContactsNote(), phoneContactsEntity.note);
        contentValues.put(DatabaseHelper.getContactsTel(), phoneContactsEntity.phone);
        this.database.insert(this.mTableName, this.mColumeTimeStamp, contentValues);
        LogUtil.d("localDb->插入 insert successfully:" + phoneContactsEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.contacts.IDatabaseContactsHandler
    public void delete(PhoneContactsEntity phoneContactsEntity) {
        this.database.delete(this.mTableName, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(phoneContactsEntity.time)});
    }

    @Override // cn.baos.watch.sdk.database.contacts.IDatabaseContactsHandler
    public void deleteAll() {
        this.database.delete(this.mTableName, null, null);
    }

    @Override // cn.baos.watch.sdk.database.contacts.IDatabaseContactsHandler
    public void update(PhoneContactsEntity phoneContactsEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.mColumeTimeStamp, Long.valueOf(System.currentTimeMillis()));
        contentValues.put(DatabaseHelper.getContactsName(), phoneContactsEntity.name);
        contentValues.put(DatabaseHelper.getContactsNote(), phoneContactsEntity.note);
        contentValues.put(DatabaseHelper.getContactsTel(), phoneContactsEntity.phone);
        this.database.update(this.mTableName, contentValues, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(phoneContactsEntity.time)});
        LogUtil.d("localDb->更新数据:" + phoneContactsEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.contacts.IDatabaseContactsHandler
    public PhoneContactsEntity query(int i) {
        try {
            Cursor cursorQuery = this.database.query(this.mTableName, null, null, null, null, null, null);
            cursorQuery.moveToLast();
            while (!cursorQuery.isBeforeFirst()) {
                if (i == cursorQuery.getInt(3)) {
                    PhoneContactsEntity phoneContactsEntity = new PhoneContactsEntity();
                    phoneContactsEntity.id = cursorQuery.getInt(0);
                    phoneContactsEntity.time = cursorQuery.getLong(1);
                    phoneContactsEntity.name = cursorQuery.getString(2);
                    phoneContactsEntity.note = cursorQuery.getString(3);
                    phoneContactsEntity.phone = cursorQuery.getString(4);
                    cursorQuery.close();
                    LogUtil.d("localDb->时间戳查询单个数据:" + phoneContactsEntity.toString());
                    return phoneContactsEntity;
                }
                cursorQuery.moveToPrevious();
            }
            cursorQuery.close();
            return null;
        } catch (Exception unused) {
            LogUtil.e("localDb->db exception");
            return null;
        }
    }

    @Override // cn.baos.watch.sdk.database.contacts.IDatabaseContactsHandler
    public ArrayList<PhoneContactsEntity> queryArrayBetween(int i, int i2) throws Exception {
        LogUtil.d("localDb->queryArrayBetween left:" + String.valueOf(i) + " right:" + String.valueOf(i2));
        ArrayList<PhoneContactsEntity> arrayList = new ArrayList<>();
        try {
            Cursor cursorRawQuery = this.database.rawQuery("select * from " + this.mTableName + " where " + this.mColumeTimeStamp + " between ? and ?", new String[]{String.valueOf(i), String.valueOf(i2)});
            cursorRawQuery.moveToLast();
            LogUtil.d("localDb->queryArrayBetween cursor num:" + cursorRawQuery.getCount());
            while (!cursorRawQuery.isBeforeFirst()) {
                PhoneContactsEntity phoneContactsEntity = new PhoneContactsEntity();
                phoneContactsEntity.id = cursorRawQuery.getInt(0);
                phoneContactsEntity.time = cursorRawQuery.getLong(1);
                phoneContactsEntity.name = cursorRawQuery.getString(2);
                phoneContactsEntity.note = cursorRawQuery.getString(3);
                phoneContactsEntity.phone = cursorRawQuery.getString(4);
                arrayList.add(phoneContactsEntity);
                cursorRawQuery.moveToPrevious();
            }
            cursorRawQuery.close();
            return arrayList;
        } catch (Exception e) {
            LogUtil.e("localDb->db exception");
            throw e;
        }
    }

    @Override // cn.baos.watch.sdk.database.contacts.IDatabaseContactsHandler
    public ArrayList<PhoneContactsEntity> queryArrayAll() throws Exception {
        ArrayList<PhoneContactsEntity> arrayList = new ArrayList<>();
        try {
            Cursor cursorRawQuery = this.database.rawQuery("select * from " + this.mTableName, null);
            cursorRawQuery.moveToLast();
            LogUtil.d("localDb->queryArrayBetween cursor num:" + cursorRawQuery.getCount());
            while (!cursorRawQuery.isBeforeFirst()) {
                PhoneContactsEntity phoneContactsEntity = new PhoneContactsEntity();
                phoneContactsEntity.id = cursorRawQuery.getInt(0);
                phoneContactsEntity.time = cursorRawQuery.getLong(1);
                phoneContactsEntity.name = cursorRawQuery.getString(2);
                phoneContactsEntity.note = cursorRawQuery.getString(3);
                phoneContactsEntity.phone = cursorRawQuery.getString(4);
                arrayList.add(phoneContactsEntity);
                cursorRawQuery.moveToPrevious();
            }
            cursorRawQuery.close();
            return arrayList;
        } catch (Exception e) {
            LogUtil.e("localDb->db exception");
            throw e;
        }
    }
}
