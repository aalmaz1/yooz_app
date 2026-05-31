package cn.baos.watch.sdk.database.notification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cn.baos.watch.sdk.bluetooth.bt.BleUtils;
import cn.baos.watch.sdk.database.DatabaseHelper;
import cn.baos.watch.sdk.entitiy.NotificationAppListEntity;
import cn.baos.watch.sdk.util.LogUtil;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class DatabaseNotificationHandler implements IDatabaseNotificationHandler {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;

    public DatabaseNotificationHandler(Context context) {
        this.mContext = context;
    }

    @Override // cn.baos.watch.sdk.database.notification.IDatabaseNotificationHandler
    public void createDatabase() {
        this.dbHelper = new DatabaseHelper(this.mContext);
    }

    @Override // cn.baos.watch.sdk.database.notification.IDatabaseNotificationHandler
    public void open() throws SQLException {
        this.database = this.dbHelper.getWritableDatabase();
    }

    @Override // cn.baos.watch.sdk.database.notification.IDatabaseNotificationHandler
    public void insert(NotificationAppListEntity notificationAppListEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getNotificationColumnAppPackageName(), notificationAppListEntity.getAppPackageName());
        contentValues.put(DatabaseHelper.getNotificationColumnAppName(), notificationAppListEntity.getAppName());
        contentValues.put(DatabaseHelper.getNotificationColumnIsChecked(), String.valueOf(notificationAppListEntity.isChecked()));
        contentValues.put(DatabaseHelper.getNotificationColumnIsSynchronizeNetwork(), Boolean.valueOf(notificationAppListEntity.isSynchronizeNetwork()));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        LogUtil.d(DatabaseHelper.getNotificationColumnId() + "-" + notificationAppListEntity.getId() + ":" + notificationAppListEntity.toString());
        this.database.insert(DatabaseHelper.getNotificationTableName(), DatabaseHelper.getNotificationColumnAppPackageName(), contentValues);
        LogUtil.d("notification added successfully.");
    }

    @Override // cn.baos.watch.sdk.database.notification.IDatabaseNotificationHandler
    public void update(NotificationAppListEntity notificationAppListEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getNotificationColumnAppPackageName(), notificationAppListEntity.getAppPackageName());
        contentValues.put(DatabaseHelper.getNotificationColumnAppName(), notificationAppListEntity.getAppName());
        contentValues.put(DatabaseHelper.getNotificationColumnIsChecked(), String.valueOf(notificationAppListEntity.isChecked()));
        contentValues.put(DatabaseHelper.getNotificationColumnIsSynchronizeNetwork(), Boolean.valueOf(notificationAppListEntity.isSynchronizeNetwork()));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        LogUtil.d(DatabaseHelper.getNotificationColumnAppPackageName() + "-" + notificationAppListEntity.getAppPackageName() + ":" + notificationAppListEntity.toString());
        this.database.update(DatabaseHelper.getNotificationTableName(), contentValues, DatabaseHelper.getNotificationColumnAppPackageName() + "= ?", new String[]{String.valueOf(notificationAppListEntity.getAppPackageName())});
    }

    @Override // cn.baos.watch.sdk.database.notification.IDatabaseNotificationHandler
    public NotificationAppListEntity query(NotificationAppListEntity notificationAppListEntity) {
        try {
            Cursor cursorQuery = this.database.query(DatabaseHelper.getNotificationTableName(), null, null, null, null, null, null);
            cursorQuery.moveToFirst();
            while (!cursorQuery.isAfterLast()) {
                if (notificationAppListEntity.getAppPackageName().equals(cursorQuery.getString(1))) {
                    NotificationAppListEntity notificationAppListEntity2 = new NotificationAppListEntity();
                    notificationAppListEntity2.setId(cursorQuery.getInt(0));
                    notificationAppListEntity2.setAppPackageName(cursorQuery.getString(1));
                    notificationAppListEntity2.setAppName(cursorQuery.getString(2));
                    notificationAppListEntity2.setChecked(Boolean.parseBoolean(cursorQuery.getString(3)));
                    notificationAppListEntity2.setSynchronizeNetwork(Boolean.parseBoolean(cursorQuery.getString(4)));
                    cursorQuery.close();
                    return notificationAppListEntity2;
                }
                cursorQuery.moveToNext();
            }
            cursorQuery.close();
            return null;
        } catch (Exception unused) {
            LogUtil.d("db exception");
            return null;
        }
    }

    @Override // cn.baos.watch.sdk.database.notification.IDatabaseNotificationHandler
    public void delete(NotificationAppListEntity notificationAppListEntity) {
        this.database.delete(DatabaseHelper.getNotificationTableName(), DatabaseHelper.getNotificationColumnId() + "= ?", new String[]{String.valueOf(notificationAppListEntity.getId())});
    }

    @Override // cn.baos.watch.sdk.database.notification.IDatabaseNotificationHandler
    public boolean hasNotification(NotificationAppListEntity notificationAppListEntity) {
        try {
            Cursor cursorQuery = this.database.query(DatabaseHelper.getNotificationTableName(), null, null, null, null, null, null);
            cursorQuery.moveToFirst();
            while (!cursorQuery.isAfterLast()) {
                if (notificationAppListEntity.getAppPackageName().equals(cursorQuery.getString(1))) {
                    cursorQuery.close();
                    return true;
                }
                cursorQuery.moveToNext();
            }
            cursorQuery.close();
            return false;
        } catch (Exception unused) {
            LogUtil.d("db exception");
            return false;
        }
    }

    @Override // cn.baos.watch.sdk.database.notification.IDatabaseNotificationHandler
    public ArrayList<NotificationAppListEntity> getAllNotificationAppListEntities() {
        ArrayList<NotificationAppListEntity> arrayList = new ArrayList<>();
        try {
            Cursor cursorQuery = this.database.query(DatabaseHelper.getNotificationTableName(), null, null, null, null, null, null);
            cursorQuery.moveToFirst();
            LogUtil.d("get all cursor num:" + cursorQuery.getCount());
            while (!cursorQuery.isAfterLast()) {
                NotificationAppListEntity notificationAppListEntity = new NotificationAppListEntity();
                notificationAppListEntity.setId(cursorQuery.getInt(0));
                notificationAppListEntity.setAppPackageName(cursorQuery.getString(1));
                notificationAppListEntity.setAppName(cursorQuery.getString(2));
                notificationAppListEntity.setChecked(Boolean.parseBoolean(cursorQuery.getString(3)));
                notificationAppListEntity.setSynchronizeNetwork(Boolean.parseBoolean(cursorQuery.getString(4)));
                notificationAppListEntity.mac = cursorQuery.getString(5);
                arrayList.add(notificationAppListEntity);
                cursorQuery.moveToNext();
            }
            cursorQuery.close();
        } catch (Exception unused) {
            LogUtil.d("db exception");
        }
        return arrayList;
    }

    @Override // cn.baos.watch.sdk.database.notification.IDatabaseNotificationHandler
    public void close() {
        this.dbHelper.close();
    }

    public void clearTable() {
        this.database.delete(DatabaseHelper.getNotificationTableName(), null, null);
    }
}
