package cn.baos.watch.sdk.database.fromwatch.sensordatasportmode;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cn.baos.watch.sdk.bluetooth.bt.BleUtils;
import cn.baos.watch.sdk.database.DatabaseHelper;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sport.SportPhoneRecordDetailEntity;
import cn.baos.watch.sdk.util.LogUtil;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class DatabaseSportModeHandler extends DataBaseFartherHandler implements IDatabaseSportModeHandler {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;
    private String mTableName = DatabaseHelper.getSportModeTableName();
    private String mColumeTimeStamp = DatabaseHelper.getSportModeColumnTime();

    public DatabaseSportModeHandler(Context context) {
        this.mContext = context;
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasportmode.IDatabaseSportModeHandler
    public void createDatabase() {
        this.dbHelper = new DatabaseHelper(this.mContext);
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasportmode.IDatabaseSportModeHandler
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

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasportmode.IDatabaseSportModeHandler
    public void insert(SportModeEntity sportModeEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getSportModeUserId(), Long.valueOf(sportModeEntity.getUserId()));
        contentValues.put(DatabaseHelper.getSportModeDeviceId(), sportModeEntity.getDevId());
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().update_timestamp));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_longitude(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().longitude));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_latitude(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().latitude));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_avg_hrate(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().avg_hrate));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_max_hrate(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().max_hrate));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_min_hrate(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().min_hrate));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_cur_hrate(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().cur_hrate));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_avg_step_len_cm(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().avg_step_len_cm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_max_step_len_cm(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().max_step_len_cm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_min_step_len_cm(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().min_step_len_cm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_cur_step_len_cm(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().cur_step_len_cm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_avg_frequency_cpm(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().avg_frequency_cpm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_max_frequency_cpm(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().max_frequency_cpm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_min_frequency_cpm(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().min_frequency_cpm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_cur_frequency_cpm(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().cur_frequency_cpm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_avg_pace_s(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().avg_pace_s));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_max_pace_s(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().max_pace_s));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_min_pace_s(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().min_pace_s));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_cur_pace_s(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().cur_pace_s));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_sum_distance_m(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().sum_distance_m));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_sum_action_count(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().sum_action_count));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_sum_calories(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().sum_calories));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_sum_times_s(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().sum_times_s));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_mode(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().mode));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_status(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().status));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_source(), (Integer) 0);
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.insert(this.mTableName, this.mColumeTimeStamp, contentValues);
        LogUtil.d("localDb->插入 insert successfully:" + sportModeEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasportmode.IDatabaseSportModeHandler
    public void insertToPhone(SportPhoneRecordDetailEntity sportPhoneRecordDetailEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getSportModeUserId(), "sportModeEntity.getUserId()");
        contentValues.put(DatabaseHelper.getSportModeDeviceId(), "sportModeEntity.getDevId()");
        contentValues.put(this.mColumeTimeStamp, Long.valueOf(System.currentTimeMillis() / 1000));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_longitude(), Integer.valueOf(sportPhoneRecordDetailEntity.longitude));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_latitude(), Integer.valueOf(sportPhoneRecordDetailEntity.latitude));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_avg_hrate(), Integer.valueOf(sportPhoneRecordDetailEntity.avg_hrate));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_max_hrate(), Integer.valueOf(sportPhoneRecordDetailEntity.max_hrate));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_min_hrate(), Integer.valueOf(sportPhoneRecordDetailEntity.min_hrate));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_cur_hrate(), Integer.valueOf(sportPhoneRecordDetailEntity.cur_hrate));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_avg_step_len_cm(), Integer.valueOf(sportPhoneRecordDetailEntity.avg_step_len_cm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_max_step_len_cm(), Integer.valueOf(sportPhoneRecordDetailEntity.max_step_len_cm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_min_step_len_cm(), Integer.valueOf(sportPhoneRecordDetailEntity.min_step_len_cm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_cur_step_len_cm(), Integer.valueOf(sportPhoneRecordDetailEntity.cur_step_len_cm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_avg_frequency_cpm(), Integer.valueOf(sportPhoneRecordDetailEntity.avg_frequency_cpm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_max_frequency_cpm(), Integer.valueOf(sportPhoneRecordDetailEntity.max_frequency_cpm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_min_frequency_cpm(), Integer.valueOf(sportPhoneRecordDetailEntity.min_frequency_cpm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_cur_frequency_cpm(), Integer.valueOf(sportPhoneRecordDetailEntity.cur_frequency_cpm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_avg_pace_s(), Integer.valueOf(sportPhoneRecordDetailEntity.avg_pace_s));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_max_pace_s(), Integer.valueOf(sportPhoneRecordDetailEntity.max_pace_s));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_min_pace_s(), Integer.valueOf(sportPhoneRecordDetailEntity.min_pace_s));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_cur_pace_s(), Integer.valueOf(sportPhoneRecordDetailEntity.cur_pace_s));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_sum_distance_m(), Integer.valueOf(sportPhoneRecordDetailEntity.sum_distance_m));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_sum_action_count(), Integer.valueOf(sportPhoneRecordDetailEntity.sum_action_count));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_sum_calories(), Integer.valueOf(sportPhoneRecordDetailEntity.sum_calories));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_sum_times_s(), Integer.valueOf(sportPhoneRecordDetailEntity.sum_times_s));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_mode(), Integer.valueOf(sportPhoneRecordDetailEntity.mode));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_status(), Integer.valueOf(sportPhoneRecordDetailEntity.status));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_source(), Integer.valueOf(sportPhoneRecordDetailEntity.source));
        this.database.insert(this.mTableName, this.mColumeTimeStamp, contentValues);
        LogUtil.d("localDb->插入 insert successfully:" + sportPhoneRecordDetailEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasportmode.IDatabaseSportModeHandler
    public void delete(SportModeEntity sportModeEntity) {
        this.database.delete(this.mTableName, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(sportModeEntity.getSensor_data_sport_mode().update_timestamp)});
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasportmode.IDatabaseSportModeHandler
    public void update(SportModeEntity sportModeEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getSportModeUserId(), Long.valueOf(sportModeEntity.getUserId()));
        contentValues.put(DatabaseHelper.getSportModeDeviceId(), sportModeEntity.getDevId());
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().update_timestamp));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_longitude(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().longitude));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_latitude(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().latitude));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_avg_hrate(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().avg_hrate));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_max_hrate(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().max_hrate));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_min_hrate(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().min_hrate));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_cur_hrate(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().cur_hrate));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_avg_step_len_cm(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().avg_step_len_cm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_max_step_len_cm(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().max_step_len_cm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_min_step_len_cm(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().min_step_len_cm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_cur_step_len_cm(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().cur_step_len_cm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_avg_frequency_cpm(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().avg_frequency_cpm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_max_frequency_cpm(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().max_frequency_cpm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_min_frequency_cpm(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().min_frequency_cpm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_cur_frequency_cpm(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().cur_frequency_cpm));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_avg_pace_s(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().avg_pace_s));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_max_pace_s(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().max_pace_s));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_min_pace_s(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().min_pace_s));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_cur_pace_s(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().cur_pace_s));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_sum_distance_m(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().sum_distance_m));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_sum_action_count(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().sum_action_count));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_sum_calories(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().sum_calories));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_sum_times_s(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().sum_times_s));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_mode(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().mode));
        contentValues.put(DatabaseHelper.getSPORT_MODE_COLUMN_status(), Integer.valueOf(sportModeEntity.getSensor_data_sport_mode().status));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.update(this.mTableName, contentValues, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(sportModeEntity.getSensor_data_sport_mode().update_timestamp)});
        LogUtil.d("localDb->更新数据:" + sportModeEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasportmode.IDatabaseSportModeHandler
    public SportModeEntity query(int i) {
        try {
            Cursor cursorQuery = this.database.query(this.mTableName, null, null, null, null, null, null);
            cursorQuery.moveToLast();
            while (!cursorQuery.isBeforeFirst()) {
                if (i == cursorQuery.getInt(3)) {
                    SportModeEntity sportModeEntity = new SportModeEntity();
                    sportModeEntity.setId(cursorQuery.getInt(0));
                    sportModeEntity.setUserId(cursorQuery.getLong(1));
                    sportModeEntity.setDevId(cursorQuery.getString(2));
                    sportModeEntity.getSensor_data_sport_mode().update_timestamp = cursorQuery.getInt(3);
                    sportModeEntity.getSensor_data_sport_mode().longitude = cursorQuery.getInt(4);
                    sportModeEntity.getSensor_data_sport_mode().latitude = cursorQuery.getInt(5);
                    sportModeEntity.getSensor_data_sport_mode().avg_hrate = cursorQuery.getInt(6);
                    sportModeEntity.getSensor_data_sport_mode().max_hrate = cursorQuery.getInt(7);
                    sportModeEntity.getSensor_data_sport_mode().min_hrate = cursorQuery.getInt(8);
                    sportModeEntity.getSensor_data_sport_mode().cur_hrate = cursorQuery.getInt(9);
                    sportModeEntity.getSensor_data_sport_mode().avg_step_len_cm = cursorQuery.getInt(10);
                    sportModeEntity.getSensor_data_sport_mode().max_step_len_cm = cursorQuery.getInt(11);
                    sportModeEntity.getSensor_data_sport_mode().min_step_len_cm = cursorQuery.getInt(12);
                    sportModeEntity.getSensor_data_sport_mode().cur_step_len_cm = cursorQuery.getInt(13);
                    sportModeEntity.getSensor_data_sport_mode().avg_frequency_cpm = cursorQuery.getInt(14);
                    sportModeEntity.getSensor_data_sport_mode().max_frequency_cpm = cursorQuery.getInt(15);
                    sportModeEntity.getSensor_data_sport_mode().min_frequency_cpm = cursorQuery.getInt(16);
                    sportModeEntity.getSensor_data_sport_mode().cur_frequency_cpm = cursorQuery.getInt(17);
                    sportModeEntity.getSensor_data_sport_mode().avg_pace_s = cursorQuery.getInt(18);
                    sportModeEntity.getSensor_data_sport_mode().max_pace_s = cursorQuery.getInt(19);
                    sportModeEntity.getSensor_data_sport_mode().min_pace_s = cursorQuery.getInt(20);
                    sportModeEntity.getSensor_data_sport_mode().cur_pace_s = cursorQuery.getInt(21);
                    sportModeEntity.getSensor_data_sport_mode().sum_distance_m = cursorQuery.getInt(22);
                    sportModeEntity.getSensor_data_sport_mode().sum_action_count = cursorQuery.getInt(23);
                    sportModeEntity.getSensor_data_sport_mode().sum_calories = cursorQuery.getInt(24);
                    sportModeEntity.getSensor_data_sport_mode().sum_times_s = cursorQuery.getInt(25);
                    sportModeEntity.getSensor_data_sport_mode().mode = cursorQuery.getInt(26);
                    sportModeEntity.getSensor_data_sport_mode().status = cursorQuery.getInt(27);
                    cursorQuery.close();
                    LogUtil.d("localDb->时间戳查询单个数据:" + sportModeEntity.toString());
                    return sportModeEntity;
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

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasportmode.IDatabaseSportModeHandler
    public ArrayList<SportModeEntity> queryArrayBetween(int i, int i2) throws Exception {
        LogUtil.d("localDb->queryArrayBetween left:" + String.valueOf(i) + " right:" + String.valueOf(i2));
        ArrayList<SportModeEntity> arrayList = new ArrayList<>();
        try {
            Cursor cursorRawQuery = this.database.rawQuery("select * from " + this.mTableName + " where " + this.mColumeTimeStamp + " between ? and ?", new String[]{String.valueOf(i), String.valueOf(i2)});
            cursorRawQuery.moveToLast();
            LogUtil.d("localDb->queryArrayBetween cursor num:" + cursorRawQuery.getCount());
            while (!cursorRawQuery.isBeforeFirst()) {
                SportModeEntity sportModeEntity = new SportModeEntity();
                sportModeEntity.setId(cursorRawQuery.getInt(0));
                sportModeEntity.setUserId(cursorRawQuery.getLong(1));
                sportModeEntity.setDevId(cursorRawQuery.getString(2));
                sportModeEntity.getSensor_data_sport_mode().update_timestamp = cursorRawQuery.getInt(3);
                sportModeEntity.getSensor_data_sport_mode().longitude = cursorRawQuery.getInt(4);
                sportModeEntity.getSensor_data_sport_mode().latitude = cursorRawQuery.getInt(5);
                sportModeEntity.getSensor_data_sport_mode().avg_hrate = cursorRawQuery.getInt(6);
                sportModeEntity.getSensor_data_sport_mode().max_hrate = cursorRawQuery.getInt(7);
                sportModeEntity.getSensor_data_sport_mode().min_hrate = cursorRawQuery.getInt(8);
                sportModeEntity.getSensor_data_sport_mode().cur_hrate = cursorRawQuery.getInt(9);
                sportModeEntity.getSensor_data_sport_mode().avg_step_len_cm = cursorRawQuery.getInt(10);
                sportModeEntity.getSensor_data_sport_mode().max_step_len_cm = cursorRawQuery.getInt(11);
                sportModeEntity.getSensor_data_sport_mode().min_step_len_cm = cursorRawQuery.getInt(12);
                sportModeEntity.getSensor_data_sport_mode().cur_step_len_cm = cursorRawQuery.getInt(13);
                sportModeEntity.getSensor_data_sport_mode().avg_frequency_cpm = cursorRawQuery.getInt(14);
                sportModeEntity.getSensor_data_sport_mode().max_frequency_cpm = cursorRawQuery.getInt(15);
                sportModeEntity.getSensor_data_sport_mode().min_frequency_cpm = cursorRawQuery.getInt(16);
                sportModeEntity.getSensor_data_sport_mode().cur_frequency_cpm = cursorRawQuery.getInt(17);
                sportModeEntity.getSensor_data_sport_mode().avg_pace_s = cursorRawQuery.getInt(18);
                sportModeEntity.getSensor_data_sport_mode().max_pace_s = cursorRawQuery.getInt(19);
                sportModeEntity.getSensor_data_sport_mode().min_pace_s = cursorRawQuery.getInt(20);
                sportModeEntity.getSensor_data_sport_mode().cur_pace_s = cursorRawQuery.getInt(21);
                sportModeEntity.getSensor_data_sport_mode().sum_distance_m = cursorRawQuery.getInt(22);
                sportModeEntity.getSensor_data_sport_mode().sum_action_count = cursorRawQuery.getInt(23);
                sportModeEntity.getSensor_data_sport_mode().sum_calories = cursorRawQuery.getInt(24);
                sportModeEntity.getSensor_data_sport_mode().sum_times_s = cursorRawQuery.getInt(25);
                sportModeEntity.getSensor_data_sport_mode().mode = cursorRawQuery.getInt(26);
                sportModeEntity.getSensor_data_sport_mode().status = cursorRawQuery.getInt(27);
                sportModeEntity.mac = cursorRawQuery.getString(28);
                arrayList.add(sportModeEntity);
                cursorRawQuery.moveToPrevious();
            }
            cursorRawQuery.close();
            return arrayList;
        } catch (Exception e) {
            LogUtil.e("localDb->db exception");
            throw e;
        }
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasportmode.IDatabaseSportModeHandler
    public ArrayList<SportPhoneRecordDetailEntity> queryArrayBetweenPhone(int i, int i2) throws Exception {
        LogUtil.d("localDb->queryArrayBetween left:" + String.valueOf(i) + " right:" + String.valueOf(i2));
        ArrayList<SportPhoneRecordDetailEntity> arrayList = new ArrayList<>();
        try {
            Cursor cursorRawQuery = this.database.rawQuery("select * from " + this.mTableName + " where " + this.mColumeTimeStamp + " between ? and ? ", new String[]{String.valueOf(i), String.valueOf(i2)});
            cursorRawQuery.moveToLast();
            LogUtil.d("localDb->queryArrayBetween cursor num:" + cursorRawQuery.getCount());
            while (!cursorRawQuery.isBeforeFirst()) {
                SportPhoneRecordDetailEntity sportPhoneRecordDetailEntity = new SportPhoneRecordDetailEntity();
                sportPhoneRecordDetailEntity.id = cursorRawQuery.getInt(0);
                sportPhoneRecordDetailEntity.userId = cursorRawQuery.getLong(1);
                sportPhoneRecordDetailEntity.devId = cursorRawQuery.getString(2);
                sportPhoneRecordDetailEntity.update_timestamp = cursorRawQuery.getInt(3);
                sportPhoneRecordDetailEntity.longitude = cursorRawQuery.getInt(4);
                sportPhoneRecordDetailEntity.latitude = cursorRawQuery.getInt(5);
                sportPhoneRecordDetailEntity.avg_hrate = cursorRawQuery.getInt(6);
                sportPhoneRecordDetailEntity.max_hrate = cursorRawQuery.getInt(7);
                sportPhoneRecordDetailEntity.min_hrate = cursorRawQuery.getInt(8);
                sportPhoneRecordDetailEntity.cur_hrate = cursorRawQuery.getInt(9);
                sportPhoneRecordDetailEntity.avg_step_len_cm = cursorRawQuery.getInt(10);
                sportPhoneRecordDetailEntity.max_step_len_cm = cursorRawQuery.getInt(11);
                sportPhoneRecordDetailEntity.min_step_len_cm = cursorRawQuery.getInt(12);
                sportPhoneRecordDetailEntity.cur_step_len_cm = cursorRawQuery.getInt(13);
                sportPhoneRecordDetailEntity.avg_frequency_cpm = cursorRawQuery.getInt(14);
                sportPhoneRecordDetailEntity.max_frequency_cpm = cursorRawQuery.getInt(15);
                sportPhoneRecordDetailEntity.min_frequency_cpm = cursorRawQuery.getInt(16);
                sportPhoneRecordDetailEntity.cur_frequency_cpm = cursorRawQuery.getInt(17);
                sportPhoneRecordDetailEntity.avg_pace_s = cursorRawQuery.getInt(18);
                sportPhoneRecordDetailEntity.max_pace_s = cursorRawQuery.getInt(19);
                sportPhoneRecordDetailEntity.min_pace_s = cursorRawQuery.getInt(20);
                sportPhoneRecordDetailEntity.cur_pace_s = cursorRawQuery.getInt(21);
                sportPhoneRecordDetailEntity.sum_distance_m = cursorRawQuery.getInt(22);
                sportPhoneRecordDetailEntity.sum_action_count = cursorRawQuery.getInt(23);
                sportPhoneRecordDetailEntity.sum_calories = cursorRawQuery.getInt(24);
                sportPhoneRecordDetailEntity.sum_times_s = cursorRawQuery.getInt(25);
                sportPhoneRecordDetailEntity.mode = cursorRawQuery.getInt(26);
                sportPhoneRecordDetailEntity.status = cursorRawQuery.getInt(27);
                sportPhoneRecordDetailEntity.source = cursorRawQuery.getInt(28);
                arrayList.add(sportPhoneRecordDetailEntity);
                cursorRawQuery.moveToPrevious();
            }
            cursorRawQuery.close();
            return arrayList;
        } catch (Exception e) {
            LogUtil.e("localDb->db exception");
            throw e;
        }
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasportmode.IDatabaseSportModeHandler
    public void close() {
        this.dbHelper.close();
    }
}
