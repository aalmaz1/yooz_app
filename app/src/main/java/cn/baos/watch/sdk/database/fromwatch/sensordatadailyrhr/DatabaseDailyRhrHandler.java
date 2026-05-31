package cn.baos.watch.sdk.database.fromwatch.sensordatadailyrhr;

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
public class DatabaseDailyRhrHandler extends DataBaseFartherHandler implements IDatabaseDailyRhrHandler {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;
    private String mTableName = DatabaseHelper.getDailyRhrTableName();
    private String mColumeTimeStamp = DatabaseHelper.getDailyRhrColumnTime();

    public DatabaseDailyRhrHandler(Context context) {
        this.mContext = context;
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyrhr.IDatabaseDailyRhrHandler
    public void createDatabase() {
        this.dbHelper = new DatabaseHelper(this.mContext);
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyrhr.IDatabaseDailyRhrHandler
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

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyrhr.IDatabaseDailyRhrHandler
    public void insert(DailyRhrEntity dailyRhrEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getDailyRhrUserId(), Long.valueOf(dailyRhrEntity.getUserId()));
        contentValues.put(DatabaseHelper.getDailyRhrDeviceId(), dailyRhrEntity.getDevId());
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(dailyRhrEntity.getSensor_data_daily_rhr().update_timestamp));
        contentValues.put(DatabaseHelper.getDAILY_DAILY_COLUMN_rhr(), Integer.valueOf(dailyRhrEntity.getSensor_data_daily_rhr().rhr));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.insert(this.mTableName, this.mColumeTimeStamp, contentValues);
        LogUtil.d("localDb->插入 insert successfully:" + dailyRhrEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyrhr.IDatabaseDailyRhrHandler
    public void delete(DailyRhrEntity dailyRhrEntity) {
        this.database.delete(this.mTableName, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(dailyRhrEntity.getSensor_data_daily_rhr().update_timestamp)});
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyrhr.IDatabaseDailyRhrHandler
    public void update(DailyRhrEntity dailyRhrEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getDailyRhrUserId(), Long.valueOf(dailyRhrEntity.getUserId()));
        contentValues.put(DatabaseHelper.getDailyRhrDeviceId(), dailyRhrEntity.getDevId());
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(dailyRhrEntity.getSensor_data_daily_rhr().update_timestamp));
        contentValues.put(DatabaseHelper.getDAILY_DAILY_COLUMN_rhr(), Integer.valueOf(dailyRhrEntity.getSensor_data_daily_rhr().rhr));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.update(this.mTableName, contentValues, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(dailyRhrEntity.getSensor_data_daily_rhr().update_timestamp)});
        LogUtil.d("localDb->更新数据:" + dailyRhrEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyrhr.IDatabaseDailyRhrHandler
    public ArrayList<DailyRhrEntity> queryArrayBetween(int i, int i2) throws Exception {
        LogUtil.d("localDb->queryArrayBetween left:" + String.valueOf(i) + " right:" + String.valueOf(i2));
        ArrayList<DailyRhrEntity> arrayList = new ArrayList<>();
        try {
            Cursor cursorRawQuery = this.database.rawQuery("select * from " + this.mTableName + " where " + this.mColumeTimeStamp + " between ? and ? ", new String[]{String.valueOf(i), String.valueOf(i2)});
            cursorRawQuery.moveToLast();
            LogUtil.d("localDb->queryArrayBetween cursor num:" + cursorRawQuery.getCount());
            while (!cursorRawQuery.isBeforeFirst()) {
                DailyRhrEntity dailyRhrEntity = new DailyRhrEntity();
                dailyRhrEntity.setId(cursorRawQuery.getInt(0));
                dailyRhrEntity.setUserId(cursorRawQuery.getLong(1));
                dailyRhrEntity.setDevId(cursorRawQuery.getString(2));
                dailyRhrEntity.getSensor_data_daily_rhr().update_timestamp = cursorRawQuery.getInt(3);
                dailyRhrEntity.getSensor_data_daily_rhr().rhr = cursorRawQuery.getInt(4);
                dailyRhrEntity.mac = cursorRawQuery.getString(5);
                arrayList.add(dailyRhrEntity);
                cursorRawQuery.moveToPrevious();
            }
            cursorRawQuery.close();
            return arrayList;
        } catch (Exception e) {
            LogUtil.e("localDb->db exception");
            throw e;
        }
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyrhr.IDatabaseDailyRhrHandler
    public void close() {
        this.dbHelper.close();
    }
}
