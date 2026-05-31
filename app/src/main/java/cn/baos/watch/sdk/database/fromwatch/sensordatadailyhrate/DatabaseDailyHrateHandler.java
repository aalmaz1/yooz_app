package cn.baos.watch.sdk.database.fromwatch.sensordatadailyhrate;

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
public class DatabaseDailyHrateHandler extends DataBaseFartherHandler implements IDatabaseDailyHrateHandler {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;
    private String mTableName = DatabaseHelper.getDailyHrateTableName();
    private String mColumeTimeStamp = DatabaseHelper.getDailyHrateColumnTime();

    public DatabaseDailyHrateHandler(Context context) {
        this.mContext = context;
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyhrate.IDatabaseDailyHrateHandler
    public void createDatabase() {
        this.dbHelper = new DatabaseHelper(this.mContext);
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyhrate.IDatabaseDailyHrateHandler
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

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyhrate.IDatabaseDailyHrateHandler
    public void insert(DailyHrateEntity dailyHrateEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getDailyHrateUserId(), Long.valueOf(dailyHrateEntity.getUserId()));
        contentValues.put(DatabaseHelper.getDailyHrateDeviceId(), dailyHrateEntity.getDevId());
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(dailyHrateEntity.getSensor_data_daily_hrate().update_timestamp));
        contentValues.put(DatabaseHelper.getDAILY_HRATE_COLUMN_heartrate(), Integer.valueOf(dailyHrateEntity.getSensor_data_daily_hrate().heartrate));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.insert(this.mTableName, this.mColumeTimeStamp, contentValues);
        LogUtil.d("localDb->插入 insert successfully:" + dailyHrateEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyhrate.IDatabaseDailyHrateHandler
    public void delete(DailyHrateEntity dailyHrateEntity) {
        this.database.delete(this.mTableName, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(dailyHrateEntity.getSensor_data_daily_hrate().update_timestamp)});
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyhrate.IDatabaseDailyHrateHandler
    public void update(DailyHrateEntity dailyHrateEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getDailyHrateUserId(), Long.valueOf(dailyHrateEntity.getUserId()));
        contentValues.put(DatabaseHelper.getDailyHrateDeviceId(), dailyHrateEntity.getDevId());
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(dailyHrateEntity.getSensor_data_daily_hrate().update_timestamp));
        contentValues.put(DatabaseHelper.getDAILY_HRATE_COLUMN_heartrate(), Integer.valueOf(dailyHrateEntity.getSensor_data_daily_hrate().heartrate));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.update(this.mTableName, contentValues, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(dailyHrateEntity.getSensor_data_daily_hrate().update_timestamp)});
        LogUtil.d("localDb->更新数据:" + dailyHrateEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyhrate.IDatabaseDailyHrateHandler
    public ArrayList<DailyHrateEntity> queryArrayBetween(int i, int i2) throws Exception {
        LogUtil.d("localDb->queryArrayBetween left:" + String.valueOf(i) + " right:" + String.valueOf(i2));
        ArrayList<DailyHrateEntity> arrayList = new ArrayList<>();
        try {
            Cursor cursorRawQuery = this.database.rawQuery("select * from " + this.mTableName + " where " + this.mColumeTimeStamp + " between ? and ? ", new String[]{String.valueOf(i), String.valueOf(i2)});
            cursorRawQuery.moveToLast();
            LogUtil.d("localDb->queryArrayBetween cursor num:" + cursorRawQuery.getCount());
            while (!cursorRawQuery.isBeforeFirst()) {
                DailyHrateEntity dailyHrateEntity = new DailyHrateEntity();
                dailyHrateEntity.setId(cursorRawQuery.getInt(0));
                dailyHrateEntity.setUserId(cursorRawQuery.getLong(1));
                dailyHrateEntity.setDevId(cursorRawQuery.getString(2));
                dailyHrateEntity.getSensor_data_daily_hrate().update_timestamp = cursorRawQuery.getInt(3);
                dailyHrateEntity.getSensor_data_daily_hrate().heartrate = cursorRawQuery.getInt(4);
                dailyHrateEntity.mac = cursorRawQuery.getString(5);
                arrayList.add(dailyHrateEntity);
                cursorRawQuery.moveToPrevious();
            }
            cursorRawQuery.close();
            return arrayList;
        } catch (Exception e) {
            LogUtil.e("localDb->db exception");
            throw e;
        }
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyhrate.IDatabaseDailyHrateHandler
    public void close() {
        this.dbHelper.close();
    }
}
