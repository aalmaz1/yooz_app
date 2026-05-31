package cn.baos.watch.sdk.database.phone.active;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cn.baos.watch.sdk.bluetooth.bt.BleUtils;
import cn.baos.watch.sdk.database.DatabaseHelper;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.database.fromwatch.sensordatadailyactive.DailyActiveEntity;
import cn.baos.watch.sdk.util.LogUtil;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class DatabaseDailyActivePhoneHandler extends DataBaseFartherHandler implements IDatabaseDailyActivePhoneHandler {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;
    private String mTableName = DatabaseHelper.getDailyActivePhoneTableName();
    private String mColumeTimeStamp = DatabaseHelper.getDailyActiveColumnTime();

    public DatabaseDailyActivePhoneHandler(Context context) {
        this.mContext = context;
    }

    @Override // cn.baos.watch.sdk.database.phone.active.IDatabaseDailyActivePhoneHandler
    public void createDatabase() {
        this.dbHelper = new DatabaseHelper(this.mContext);
    }

    @Override // cn.baos.watch.sdk.database.phone.active.IDatabaseDailyActivePhoneHandler
    public void open() throws SQLException {
        this.database = this.dbHelper.getWritableDatabase();
    }

    @Override // cn.baos.watch.sdk.database.phone.active.IDatabaseDailyActivePhoneHandler
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

    @Override // cn.baos.watch.sdk.database.phone.active.IDatabaseDailyActivePhoneHandler
    public void insert(DailyActiveEntity dailyActiveEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getDailyActiveUserId(), Long.valueOf(dailyActiveEntity.getUserId()));
        contentValues.put(DatabaseHelper.getDailyActiveDeviceId(), dailyActiveEntity.getDevId());
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(dailyActiveEntity.getSensor_data_daily_active_sum().update_timestamp));
        contentValues.put(DatabaseHelper.getDAILY_ACTIVE_COLUMN_sum_distance_m(), Integer.valueOf(dailyActiveEntity.getSensor_data_daily_active_sum().sum_distance_m));
        contentValues.put(DatabaseHelper.getDAILY_ACTIVE_COLUMN_sum_step(), Integer.valueOf(dailyActiveEntity.getSensor_data_daily_active_sum().sum_step));
        contentValues.put(DatabaseHelper.getDAILY_ACTIVE_COLUMN_sum_calorie(), Integer.valueOf(dailyActiveEntity.getSensor_data_daily_active_sum().sum_calorie));
        contentValues.put(DatabaseHelper.getDAILY_ACTIVE_COLUMN_sum_times(), Integer.valueOf(dailyActiveEntity.getSensor_data_daily_active_sum().sum_times));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.insert(this.mTableName, this.mColumeTimeStamp, contentValues);
        LogUtil.d("localDb->插入 insert successfully:" + dailyActiveEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.phone.active.IDatabaseDailyActivePhoneHandler
    public void delete(DailyActiveEntity dailyActiveEntity) {
        this.database.delete(this.mTableName, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(dailyActiveEntity.getSensor_data_daily_active_sum().update_timestamp)});
    }

    @Override // cn.baos.watch.sdk.database.phone.active.IDatabaseDailyActivePhoneHandler
    public void update(DailyActiveEntity dailyActiveEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getDailyActiveUserId(), Long.valueOf(dailyActiveEntity.getUserId()));
        contentValues.put(DatabaseHelper.getDailyActiveDeviceId(), dailyActiveEntity.getDevId());
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(dailyActiveEntity.getSensor_data_daily_active_sum().update_timestamp));
        contentValues.put(DatabaseHelper.getDAILY_ACTIVE_COLUMN_sum_distance_m(), Integer.valueOf(dailyActiveEntity.getSensor_data_daily_active_sum().sum_distance_m));
        contentValues.put(DatabaseHelper.getDAILY_ACTIVE_COLUMN_sum_step(), Integer.valueOf(dailyActiveEntity.getSensor_data_daily_active_sum().sum_step));
        contentValues.put(DatabaseHelper.getDAILY_ACTIVE_COLUMN_sum_calorie(), Integer.valueOf(dailyActiveEntity.getSensor_data_daily_active_sum().sum_calorie));
        contentValues.put(DatabaseHelper.getDAILY_ACTIVE_COLUMN_sum_times(), Integer.valueOf(dailyActiveEntity.getSensor_data_daily_active_sum().sum_times));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.update(this.mTableName, contentValues, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(dailyActiveEntity.getSensor_data_daily_active_sum().update_timestamp)});
        LogUtil.d("localDb->更新数据:" + dailyActiveEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.phone.active.IDatabaseDailyActivePhoneHandler
    public DailyActiveEntity query(int i) {
        try {
            Cursor cursorQuery = this.database.query(this.mTableName, null, null, null, null, null, null);
            cursorQuery.moveToLast();
            while (!cursorQuery.isBeforeFirst()) {
                if (i == cursorQuery.getInt(3)) {
                    DailyActiveEntity dailyActiveEntity = new DailyActiveEntity();
                    dailyActiveEntity.setId(cursorQuery.getInt(0));
                    dailyActiveEntity.setUserId(cursorQuery.getLong(1));
                    dailyActiveEntity.setDevId(cursorQuery.getString(2));
                    dailyActiveEntity.getSensor_data_daily_active_sum().update_timestamp = cursorQuery.getInt(3);
                    dailyActiveEntity.getSensor_data_daily_active_sum().sum_distance_m = cursorQuery.getInt(4);
                    dailyActiveEntity.getSensor_data_daily_active_sum().sum_step = cursorQuery.getInt(5);
                    dailyActiveEntity.getSensor_data_daily_active_sum().sum_calorie = cursorQuery.getInt(6);
                    dailyActiveEntity.getSensor_data_daily_active_sum().sum_times = cursorQuery.getInt(7);
                    cursorQuery.close();
                    LogUtil.d("localDb->时间戳查询单个数据:" + dailyActiveEntity.toString());
                    return dailyActiveEntity;
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

    @Override // cn.baos.watch.sdk.database.phone.active.IDatabaseDailyActivePhoneHandler
    public ArrayList<DailyActiveEntity> queryArrayBetween(int i, int i2) throws Exception {
        LogUtil.d("localDb->queryArrayBetween left:" + String.valueOf(i) + " right:" + String.valueOf(i2));
        ArrayList<DailyActiveEntity> arrayList = new ArrayList<>();
        try {
            Cursor cursorRawQuery = this.database.rawQuery("select * from " + this.mTableName + " where " + this.mColumeTimeStamp + " between ? and ? ", new String[]{String.valueOf(i), String.valueOf(i2)});
            cursorRawQuery.moveToLast();
            LogUtil.d("localDb->queryArrayBetween cursor num:" + cursorRawQuery.getCount());
            while (!cursorRawQuery.isBeforeFirst()) {
                DailyActiveEntity dailyActiveEntity = new DailyActiveEntity();
                dailyActiveEntity.setId(cursorRawQuery.getInt(0));
                dailyActiveEntity.setUserId(cursorRawQuery.getLong(1));
                dailyActiveEntity.setDevId(cursorRawQuery.getString(2));
                dailyActiveEntity.getSensor_data_daily_active_sum().update_timestamp = cursorRawQuery.getInt(3);
                dailyActiveEntity.getSensor_data_daily_active_sum().sum_distance_m = cursorRawQuery.getInt(4);
                dailyActiveEntity.getSensor_data_daily_active_sum().sum_step = cursorRawQuery.getInt(5);
                dailyActiveEntity.getSensor_data_daily_active_sum().sum_calorie = cursorRawQuery.getInt(6);
                dailyActiveEntity.getSensor_data_daily_active_sum().sum_times = cursorRawQuery.getInt(7);
                dailyActiveEntity.mac = cursorRawQuery.getString(8);
                arrayList.add(dailyActiveEntity);
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
