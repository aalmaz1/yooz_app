package cn.baos.watch.sdk.database.fromwatch.sensordatasleepstatus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cn.baos.watch.sdk.bluetooth.bt.BleUtils;
import cn.baos.watch.sdk.database.DatabaseHelper;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.util.LogUtil;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class DatabaseSleepStatusHandler extends DataBaseFartherHandler implements IDatabaseSleepStatusHandler {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;
    private String mTableName = DatabaseHelper.getSleepStatusTableName();
    private String mColumeTimeStamp = DatabaseHelper.getSleepStatusColumnTime();

    public DatabaseSleepStatusHandler(Context context) {
        this.mContext = context;
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasleepstatus.IDatabaseSleepStatusHandler
    public void createDatabase() {
        this.dbHelper = new DatabaseHelper(this.mContext);
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasleepstatus.IDatabaseSleepStatusHandler
    public void open() throws SQLException {
        this.database = this.dbHelper.getWritableDatabase();
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler
    public SQLiteDatabase getDatabase() {
        return this.database;
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler
    public String getTableName() {
        return this.mTableName;
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasleepstatus.IDatabaseSleepStatusHandler
    public void insert(SleepStatusEntity sleepStatusEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getSleepStatusUserId(), Long.valueOf(sleepStatusEntity.getUserId()));
        contentValues.put(DatabaseHelper.getSleepStatusDeviceId(), sleepStatusEntity.getDevId());
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(sleepStatusEntity.getSensor_data_sleep_status().update_timestamp));
        contentValues.put(DatabaseHelper.getSLEEP_STATUS_COLUMN_sleep_status(), Integer.valueOf(sleepStatusEntity.getSensor_data_sleep_status().sleep_status));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.insert(this.mTableName, this.mColumeTimeStamp, contentValues);
        LogUtil.d("localDb->插入 insert successfully:" + sleepStatusEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasleepstatus.IDatabaseSleepStatusHandler
    public void delete(SleepStatusEntity sleepStatusEntity) {
        this.database.delete(this.mTableName, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(sleepStatusEntity.getSensor_data_sleep_status().update_timestamp)});
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasleepstatus.IDatabaseSleepStatusHandler
    public void update(SleepStatusEntity sleepStatusEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getSleepStatusUserId(), Long.valueOf(sleepStatusEntity.getUserId()));
        contentValues.put(DatabaseHelper.getSleepStatusDeviceId(), sleepStatusEntity.getDevId());
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(sleepStatusEntity.getSensor_data_sleep_status().update_timestamp));
        contentValues.put(DatabaseHelper.getSLEEP_STATUS_COLUMN_sleep_status(), Integer.valueOf(sleepStatusEntity.getSensor_data_sleep_status().sleep_status));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.update(this.mTableName, contentValues, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(sleepStatusEntity.getSensor_data_sleep_status().update_timestamp)});
        LogUtil.d("localDb->更新数据:" + sleepStatusEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasleepstatus.IDatabaseSleepStatusHandler
    public SleepStatusEntity query(int i) {
        try {
            Cursor cursorQuery = this.database.query(this.mTableName, null, null, null, null, null, null);
            cursorQuery.moveToLast();
            while (!cursorQuery.isBeforeFirst()) {
                if (i == cursorQuery.getInt(3)) {
                    SleepStatusEntity sleepStatusEntity = new SleepStatusEntity();
                    sleepStatusEntity.setId(cursorQuery.getInt(0));
                    sleepStatusEntity.setUserId(cursorQuery.getLong(1));
                    sleepStatusEntity.setDevId(cursorQuery.getString(2));
                    sleepStatusEntity.getSensor_data_sleep_status().update_timestamp = cursorQuery.getInt(3);
                    sleepStatusEntity.getSensor_data_sleep_status().sleep_status = cursorQuery.getInt(4);
                    cursorQuery.close();
                    LogUtil.d("localDb->时间戳查询单个数据:" + sleepStatusEntity.toString());
                    return sleepStatusEntity;
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

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasleepstatus.IDatabaseSleepStatusHandler
    public ArrayList<SleepStatusEntity> queryArrayBetween(int i, int i2) throws Exception {
        LogUtil.d("localDb->queryArrayBetween left:" + String.valueOf(i) + " right:" + String.valueOf(i2));
        ArrayList<SleepStatusEntity> arrayList = new ArrayList<>();
        try {
            Cursor cursorRawQuery = this.database.rawQuery("select * from " + this.mTableName + " where " + this.mColumeTimeStamp + " between ? and ? ", new String[]{String.valueOf(i), String.valueOf(i2)});
            cursorRawQuery.moveToLast();
            LogUtil.d("localDb->queryArrayBetween cursor num:" + cursorRawQuery.getCount());
            while (!cursorRawQuery.isBeforeFirst()) {
                SleepStatusEntity sleepStatusEntity = new SleepStatusEntity();
                sleepStatusEntity.setId(cursorRawQuery.getInt(0));
                sleepStatusEntity.setUserId(cursorRawQuery.getLong(1));
                sleepStatusEntity.setDevId(cursorRawQuery.getString(2));
                sleepStatusEntity.getSensor_data_sleep_status().update_timestamp = cursorRawQuery.getInt(3);
                sleepStatusEntity.getSensor_data_sleep_status().sleep_status = cursorRawQuery.getInt(4);
                sleepStatusEntity.mac = cursorRawQuery.getString(5);
                arrayList.add(sleepStatusEntity);
                cursorRawQuery.moveToPrevious();
            }
            cursorRawQuery.close();
            return arrayList;
        } catch (Exception e) {
            LogUtil.e("localDb->db exception");
            throw e;
        }
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasleepstatus.IDatabaseSleepStatusHandler
    public void close() {
        this.dbHelper.close();
    }
}
