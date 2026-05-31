package cn.baos.watch.sdk.database.fromwatch.sportrecord;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cn.baos.watch.sdk.bluetooth.bt.BleUtils;
import cn.baos.watch.sdk.database.DatabaseHelper;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sport.SportPhoneRecordEntity;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.w100.messages.Sport_record;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class DatabaseSportRecordFromWatchHandler extends DataBaseFartherHandler implements IDatabaseSportRecordFromWatchHandler {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;
    private String mTableName = DatabaseHelper.getSportRecordFromWatchTableName();
    private String mColumeTimeStamp = DatabaseHelper.getSportRecordFromWatchColumnTime();

    public DatabaseSportRecordFromWatchHandler(Context context) {
        this.mContext = context;
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sportrecord.IDatabaseSportRecordFromWatchHandler
    public void createDatabase() {
        this.dbHelper = new DatabaseHelper(this.mContext);
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sportrecord.IDatabaseSportRecordFromWatchHandler
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

    @Override // cn.baos.watch.sdk.database.fromwatch.sportrecord.IDatabaseSportRecordFromWatchHandler
    public void insert(SportRecordFromWatchEntity sportRecordFromWatchEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getSportRecordFromWatchUserId(), Long.valueOf(sportRecordFromWatchEntity.getUserId()));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchDeviceId(), sportRecordFromWatchEntity.getDevId());
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(sportRecordFromWatchEntity.getSport_record().update_timestamp));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnBeginTimestamp(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().begin_timestamp));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnEndTimestamp(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().end_timestamp));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnTimezone(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().timezone));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnInitiator(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().initiator));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnMode(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().mode));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnStatus(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().status));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnSportDistanceM(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().target.distance_m));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnSportCalories(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().target.calories));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnSportTimesS(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().target.times_s));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnSportDistancePerM(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().remind.distance_per_m));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnSportTimesPerS(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().remind.times_per_s));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnSportMaxPaceS(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().remind.max_pace_s));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnSportMinPaceS(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().remind.min_pace_s));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnSportMaxHeartrate(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().remind.max_heartrate));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.insert(this.mTableName, this.mColumeTimeStamp, contentValues);
        LogUtil.d("localDb->插入 insert 运动记录 successfully:" + sportRecordFromWatchEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sportrecord.IDatabaseSportRecordFromWatchHandler
    public void insertPhone(SportPhoneRecordEntity sportPhoneRecordEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getSportRecordFromWatchUserId(), "userid");
        contentValues.put(DatabaseHelper.getSportRecordFromWatchDeviceId(), "device");
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(sportPhoneRecordEntity.update_timestamp));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnBeginTimestamp(), Integer.valueOf(sportPhoneRecordEntity.begin_timestamp));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnEndTimestamp(), Integer.valueOf(sportPhoneRecordEntity.end_timestamp));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnTimezone(), Integer.valueOf(sportPhoneRecordEntity.timezone));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnInitiator(), Integer.valueOf(sportPhoneRecordEntity.initiator));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnMode(), Integer.valueOf(sportPhoneRecordEntity.mode));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnStatus(), Integer.valueOf(sportPhoneRecordEntity.status));
        this.database.insert(this.mTableName, this.mColumeTimeStamp, contentValues);
        LogUtil.d("localDb->插入 insert 运动记录 successfully:" + sportPhoneRecordEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sportrecord.IDatabaseSportRecordFromWatchHandler
    public void update(SportRecordFromWatchEntity sportRecordFromWatchEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getSportRecordFromWatchUserId(), Long.valueOf(sportRecordFromWatchEntity.getUserId()));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchDeviceId(), sportRecordFromWatchEntity.getDevId());
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(sportRecordFromWatchEntity.getSport_record().update_timestamp));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnBeginTimestamp(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().begin_timestamp));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnEndTimestamp(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().end_timestamp));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnTimezone(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().timezone));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnInitiator(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().initiator));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnMode(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().mode));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnStatus(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().status));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnSportDistanceM(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().target.distance_m));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnSportCalories(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().target.calories));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnSportTimesS(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().target.times_s));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnSportDistancePerM(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().remind.distance_per_m));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnSportTimesPerS(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().remind.times_per_s));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnSportMaxPaceS(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().remind.max_pace_s));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnSportMinPaceS(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().remind.min_pace_s));
        contentValues.put(DatabaseHelper.getSportRecordFromWatchColumnSportMaxHeartrate(), Integer.valueOf(sportRecordFromWatchEntity.getSport_record().remind.max_heartrate));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.update(this.mTableName, contentValues, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(sportRecordFromWatchEntity.getSport_record().update_timestamp)});
        LogUtil.d("localDb->更新运动记录数据:" + sportRecordFromWatchEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sportrecord.IDatabaseSportRecordFromWatchHandler
    public SportRecordFromWatchEntity query(int i) {
        try {
            Cursor cursorQuery = this.database.query(this.mTableName, null, null, null, null, null, null);
            cursorQuery.moveToLast();
            while (!cursorQuery.isBeforeFirst()) {
                if (i == cursorQuery.getInt(3)) {
                    SportRecordFromWatchEntity sportRecordFromWatchEntity = new SportRecordFromWatchEntity();
                    sportRecordFromWatchEntity.setId(cursorQuery.getInt(0));
                    sportRecordFromWatchEntity.setUserId(cursorQuery.getLong(1));
                    sportRecordFromWatchEntity.setDevId(cursorQuery.getString(2));
                    sportRecordFromWatchEntity.getSport_record().update_timestamp = cursorQuery.getInt(3);
                    sportRecordFromWatchEntity.getSport_record().begin_timestamp = cursorQuery.getInt(4);
                    sportRecordFromWatchEntity.getSport_record().end_timestamp = cursorQuery.getInt(5);
                    sportRecordFromWatchEntity.getSport_record().timezone = cursorQuery.getInt(6);
                    sportRecordFromWatchEntity.getSport_record().initiator = cursorQuery.getInt(7);
                    sportRecordFromWatchEntity.getSport_record().mode = cursorQuery.getInt(8);
                    sportRecordFromWatchEntity.getSport_record().status = cursorQuery.getInt(9);
                    sportRecordFromWatchEntity.getSport_record().target.distance_m = cursorQuery.getInt(10);
                    sportRecordFromWatchEntity.getSport_record().target.calories = cursorQuery.getInt(11);
                    sportRecordFromWatchEntity.getSport_record().target.times_s = cursorQuery.getInt(12);
                    sportRecordFromWatchEntity.getSport_record().remind.distance_per_m = cursorQuery.getInt(13);
                    sportRecordFromWatchEntity.getSport_record().remind.times_per_s = cursorQuery.getInt(14);
                    sportRecordFromWatchEntity.getSport_record().remind.max_pace_s = cursorQuery.getInt(15);
                    sportRecordFromWatchEntity.getSport_record().remind.min_pace_s = cursorQuery.getInt(16);
                    sportRecordFromWatchEntity.getSport_record().remind.max_heartrate = cursorQuery.getInt(17);
                    cursorQuery.close();
                    LogUtil.d("localDb->时间戳查询单个运动记录数据:" + sportRecordFromWatchEntity.toString());
                    return sportRecordFromWatchEntity;
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

    @Override // cn.baos.watch.sdk.database.fromwatch.sportrecord.IDatabaseSportRecordFromWatchHandler
    public ArrayList<SportRecordFromWatchEntity> queryArrayBetween(int i, int i2) throws Exception {
        LogUtil.d("localDb->queryArrayBetween left:" + String.valueOf(i) + " right:" + String.valueOf(i2));
        ArrayList<SportRecordFromWatchEntity> arrayList = new ArrayList<>();
        try {
            Cursor cursorRawQuery = this.database.rawQuery("select * from " + this.mTableName + " where " + this.mColumeTimeStamp + " between ? and ? ", new String[]{String.valueOf(i), String.valueOf(i2)});
            cursorRawQuery.moveToLast();
            LogUtil.d("localDb->queryArrayBetween cursor num:" + cursorRawQuery.getCount());
            while (!cursorRawQuery.isBeforeFirst()) {
                SportRecordFromWatchEntity sportRecordFromWatchEntity = new SportRecordFromWatchEntity();
                sportRecordFromWatchEntity.setId(cursorRawQuery.getInt(0));
                sportRecordFromWatchEntity.setUserId(cursorRawQuery.getLong(1));
                sportRecordFromWatchEntity.setDevId(cursorRawQuery.getString(2));
                sportRecordFromWatchEntity.getSport_record().update_timestamp = cursorRawQuery.getInt(3);
                sportRecordFromWatchEntity.getSport_record().begin_timestamp = cursorRawQuery.getInt(4);
                sportRecordFromWatchEntity.getSport_record().end_timestamp = cursorRawQuery.getInt(5);
                sportRecordFromWatchEntity.getSport_record().timezone = cursorRawQuery.getInt(6);
                sportRecordFromWatchEntity.getSport_record().initiator = cursorRawQuery.getInt(7);
                sportRecordFromWatchEntity.getSport_record().mode = cursorRawQuery.getInt(8);
                sportRecordFromWatchEntity.getSport_record().status = cursorRawQuery.getInt(9);
                sportRecordFromWatchEntity.getSport_record().target = new Sport_record.Sport_target();
                sportRecordFromWatchEntity.getSport_record().target.distance_m = cursorRawQuery.getInt(10);
                sportRecordFromWatchEntity.getSport_record().target.calories = cursorRawQuery.getInt(11);
                sportRecordFromWatchEntity.getSport_record().target.times_s = cursorRawQuery.getInt(12);
                sportRecordFromWatchEntity.getSport_record().remind = new Sport_record.Sporting_remind();
                sportRecordFromWatchEntity.getSport_record().remind.distance_per_m = cursorRawQuery.getInt(13);
                sportRecordFromWatchEntity.getSport_record().remind.times_per_s = cursorRawQuery.getInt(14);
                sportRecordFromWatchEntity.getSport_record().remind.max_pace_s = cursorRawQuery.getInt(15);
                sportRecordFromWatchEntity.getSport_record().remind.min_pace_s = cursorRawQuery.getInt(16);
                sportRecordFromWatchEntity.getSport_record().remind.max_heartrate = cursorRawQuery.getInt(17);
                sportRecordFromWatchEntity.mac = cursorRawQuery.getString(18);
                arrayList.add(sportRecordFromWatchEntity);
                cursorRawQuery.moveToPrevious();
            }
            cursorRawQuery.close();
            return arrayList;
        } catch (Exception e) {
            LogUtil.e("localDb->db exception");
            throw e;
        }
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sportrecord.IDatabaseSportRecordFromWatchHandler
    public void delete(SportRecordFromWatchEntity sportRecordFromWatchEntity) {
        this.database.delete(this.mTableName, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(sportRecordFromWatchEntity.getSport_record().update_timestamp)});
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sportrecord.IDatabaseSportRecordFromWatchHandler
    public void close() {
        this.dbHelper.close();
    }

    public void clearTable() {
        this.database.delete(this.mTableName, null, null);
    }
}
