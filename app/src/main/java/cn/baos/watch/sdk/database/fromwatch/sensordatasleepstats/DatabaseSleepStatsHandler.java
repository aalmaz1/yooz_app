package cn.baos.watch.sdk.database.fromwatch.sensordatasleepstats;

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
public class DatabaseSleepStatsHandler extends DataBaseFartherHandler implements IDatabaseSleepStatsHandler {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;
    private String mTableName = DatabaseHelper.getSleepStatsTableName();
    private String mColumeTimeStamp = DatabaseHelper.getSleepStatsColumnTime();

    public DatabaseSleepStatsHandler(Context context) {
        this.mContext = context;
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasleepstats.IDatabaseSleepStatsHandler
    public void createDatabase() {
        this.dbHelper = new DatabaseHelper(this.mContext);
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasleepstats.IDatabaseSleepStatsHandler
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

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasleepstats.IDatabaseSleepStatsHandler
    public void insert(SleepStatsEntity sleepStatsEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getSleepStatsUserId(), Long.valueOf(sleepStatsEntity.getUserId()));
        contentValues.put(DatabaseHelper.getSleepStatsDeviceId(), sleepStatsEntity.getDevId());
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(sleepStatsEntity.getSensor_data_sleep_stats().update_timestamp));
        contentValues.put(DatabaseHelper.getSLEEP_STATS_COLUMN_begin_timestamp(), Integer.valueOf(sleepStatsEntity.getSensor_data_sleep_stats().begin_timestamp));
        contentValues.put(DatabaseHelper.getSLEEP_STATS_COLUMN_end_timestamp(), Integer.valueOf(sleepStatsEntity.getSensor_data_sleep_stats().end_timestamp));
        contentValues.put(DatabaseHelper.getSLEEP_STATS_COLUMN_total_sec(), Integer.valueOf(sleepStatsEntity.getSensor_data_sleep_stats().total_sec));
        contentValues.put(DatabaseHelper.getSLEEP_STATS_COLUMN_light_sec(), Integer.valueOf(sleepStatsEntity.getSensor_data_sleep_stats().light_sec));
        contentValues.put(DatabaseHelper.getSLEEP_STATS_COLUMN_deep_sec(), Integer.valueOf(sleepStatsEntity.getSensor_data_sleep_stats().deep_sec));
        contentValues.put(DatabaseHelper.getSLEEP_STATS_COLUMN_wakeup_sec(), Integer.valueOf(sleepStatsEntity.getSensor_data_sleep_stats().wakeup_sec));
        contentValues.put(DatabaseHelper.getSLEEP_STATS_COLUMN_eyesmove_sec(), Integer.valueOf(sleepStatsEntity.getSensor_data_sleep_stats().eyesmove_sec));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.insert(this.mTableName, this.mColumeTimeStamp, contentValues);
        LogUtil.d("localDb->插入 insert successfully:" + sleepStatsEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasleepstats.IDatabaseSleepStatsHandler
    public void delete(SleepStatsEntity sleepStatsEntity) {
        this.database.delete(this.mTableName, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(sleepStatsEntity.getSensor_data_sleep_stats().update_timestamp)});
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasleepstats.IDatabaseSleepStatsHandler
    public void update(SleepStatsEntity sleepStatsEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getSleepStatsUserId(), Long.valueOf(sleepStatsEntity.getUserId()));
        contentValues.put(DatabaseHelper.getSleepStatsDeviceId(), sleepStatsEntity.getDevId());
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(sleepStatsEntity.getSensor_data_sleep_stats().update_timestamp));
        contentValues.put(DatabaseHelper.getSLEEP_STATS_COLUMN_begin_timestamp(), Integer.valueOf(sleepStatsEntity.getSensor_data_sleep_stats().begin_timestamp));
        contentValues.put(DatabaseHelper.getSLEEP_STATS_COLUMN_end_timestamp(), Integer.valueOf(sleepStatsEntity.getSensor_data_sleep_stats().end_timestamp));
        contentValues.put(DatabaseHelper.getSLEEP_STATS_COLUMN_total_sec(), Integer.valueOf(sleepStatsEntity.getSensor_data_sleep_stats().total_sec));
        contentValues.put(DatabaseHelper.getSLEEP_STATS_COLUMN_light_sec(), Integer.valueOf(sleepStatsEntity.getSensor_data_sleep_stats().light_sec));
        contentValues.put(DatabaseHelper.getSLEEP_STATS_COLUMN_deep_sec(), Integer.valueOf(sleepStatsEntity.getSensor_data_sleep_stats().deep_sec));
        contentValues.put(DatabaseHelper.getSLEEP_STATS_COLUMN_wakeup_sec(), Integer.valueOf(sleepStatsEntity.getSensor_data_sleep_stats().wakeup_sec));
        contentValues.put(DatabaseHelper.getSLEEP_STATS_COLUMN_eyesmove_sec(), Integer.valueOf(sleepStatsEntity.getSensor_data_sleep_stats().eyesmove_sec));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.update(this.mTableName, contentValues, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(sleepStatsEntity.getSensor_data_sleep_stats().update_timestamp)});
        LogUtil.d("localDb->更新数据:" + sleepStatsEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasleepstats.IDatabaseSleepStatsHandler
    public SleepStatsEntity query(int i) {
        try {
            Cursor cursorQuery = this.database.query(this.mTableName, null, null, null, null, null, null);
            cursorQuery.moveToLast();
            while (!cursorQuery.isBeforeFirst()) {
                if (i == cursorQuery.getInt(3)) {
                    SleepStatsEntity sleepStatsEntity = new SleepStatsEntity();
                    sleepStatsEntity.setId(cursorQuery.getInt(0));
                    sleepStatsEntity.setUserId(cursorQuery.getLong(1));
                    sleepStatsEntity.setDevId(cursorQuery.getString(2));
                    sleepStatsEntity.getSensor_data_sleep_stats().update_timestamp = cursorQuery.getInt(3);
                    sleepStatsEntity.getSensor_data_sleep_stats().begin_timestamp = cursorQuery.getInt(4);
                    sleepStatsEntity.getSensor_data_sleep_stats().end_timestamp = cursorQuery.getInt(5);
                    sleepStatsEntity.getSensor_data_sleep_stats().total_sec = cursorQuery.getInt(6);
                    sleepStatsEntity.getSensor_data_sleep_stats().light_sec = cursorQuery.getInt(7);
                    sleepStatsEntity.getSensor_data_sleep_stats().deep_sec = cursorQuery.getInt(8);
                    sleepStatsEntity.getSensor_data_sleep_stats().wakeup_sec = cursorQuery.getInt(9);
                    sleepStatsEntity.getSensor_data_sleep_stats().eyesmove_sec = cursorQuery.getInt(10);
                    cursorQuery.close();
                    LogUtil.d("localDb->时间戳查询单个数据:" + sleepStatsEntity.toString());
                    return sleepStatsEntity;
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

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasleepstats.IDatabaseSleepStatsHandler
    public ArrayList<SleepStatsEntity> queryArrayBetween(int i, int i2) throws Exception {
        LogUtil.d("localDb->queryArrayBetween left:" + String.valueOf(i) + " right:" + String.valueOf(i2));
        ArrayList<SleepStatsEntity> arrayList = new ArrayList<>();
        try {
            Cursor cursorRawQuery = this.database.rawQuery("select * from " + this.mTableName + " where " + this.mColumeTimeStamp + " between ? and ? ", new String[]{String.valueOf(i), String.valueOf(i2)});
            cursorRawQuery.moveToLast();
            LogUtil.d("localDb->queryArrayBetween cursor num:" + cursorRawQuery.getCount());
            while (!cursorRawQuery.isBeforeFirst()) {
                SleepStatsEntity sleepStatsEntity = new SleepStatsEntity();
                sleepStatsEntity.setId(cursorRawQuery.getInt(0));
                sleepStatsEntity.setUserId(cursorRawQuery.getLong(1));
                sleepStatsEntity.setDevId(cursorRawQuery.getString(2));
                sleepStatsEntity.getSensor_data_sleep_stats().update_timestamp = cursorRawQuery.getInt(3);
                sleepStatsEntity.getSensor_data_sleep_stats().begin_timestamp = cursorRawQuery.getInt(4);
                sleepStatsEntity.getSensor_data_sleep_stats().end_timestamp = cursorRawQuery.getInt(5);
                sleepStatsEntity.getSensor_data_sleep_stats().total_sec = cursorRawQuery.getInt(6);
                sleepStatsEntity.getSensor_data_sleep_stats().light_sec = cursorRawQuery.getInt(7);
                sleepStatsEntity.getSensor_data_sleep_stats().deep_sec = cursorRawQuery.getInt(8);
                sleepStatsEntity.getSensor_data_sleep_stats().wakeup_sec = cursorRawQuery.getInt(9);
                sleepStatsEntity.getSensor_data_sleep_stats().eyesmove_sec = cursorRawQuery.getInt(10);
                sleepStatsEntity.mac = cursorRawQuery.getString(11);
                arrayList.add(sleepStatsEntity);
                cursorRawQuery.moveToPrevious();
            }
            cursorRawQuery.close();
            return arrayList;
        } catch (Exception e) {
            LogUtil.e("localDb->db exception");
            throw e;
        }
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasleepstats.IDatabaseSleepStatsHandler
    public void close() {
        this.dbHelper.close();
    }
}
