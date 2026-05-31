package cn.baos.watch.sdk.database.fromwatch.sensordatasporthrate;

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
public class DatabaseSportHrateHandler extends DataBaseFartherHandler implements IDatabaseSportHrateHandler {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;
    private String mTableName = DatabaseHelper.getSportHrateTableName();
    private String mColumeTimeStamp = DatabaseHelper.getSportHrateColumnTime();

    public DatabaseSportHrateHandler(Context context) {
        this.mContext = context;
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasporthrate.IDatabaseSportHrateHandler
    public void createDatabase() {
        this.dbHelper = new DatabaseHelper(this.mContext);
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasporthrate.IDatabaseSportHrateHandler
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

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasporthrate.IDatabaseSportHrateHandler
    public void insert(SportHrateEntity sportHrateEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getSportHrateUserId(), Long.valueOf(sportHrateEntity.getUserId()));
        contentValues.put(DatabaseHelper.getSportHrateDeviceId(), sportHrateEntity.getDevId());
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(sportHrateEntity.getSensor_data_sport_hrate().update_timestamp));
        contentValues.put(DatabaseHelper.getSPORT_HRATE_COLUMN_heartrate(), Integer.valueOf(sportHrateEntity.getSensor_data_sport_hrate().heartrate));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.insert(this.mTableName, this.mColumeTimeStamp, contentValues);
        LogUtil.d("localDb->插入 insert successfully:" + sportHrateEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasporthrate.IDatabaseSportHrateHandler
    public void delete(SportHrateEntity sportHrateEntity) {
        this.database.delete(this.mTableName, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(sportHrateEntity.getSensor_data_sport_hrate().update_timestamp)});
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasporthrate.IDatabaseSportHrateHandler
    public void update(SportHrateEntity sportHrateEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getSportHrateUserId(), Long.valueOf(sportHrateEntity.getUserId()));
        contentValues.put(DatabaseHelper.getSportHrateDeviceId(), sportHrateEntity.getDevId());
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(sportHrateEntity.getSensor_data_sport_hrate().update_timestamp));
        contentValues.put(DatabaseHelper.getSPORT_HRATE_COLUMN_heartrate(), Integer.valueOf(sportHrateEntity.getSensor_data_sport_hrate().heartrate));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.update(this.mTableName, contentValues, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(sportHrateEntity.getSensor_data_sport_hrate().update_timestamp)});
        LogUtil.d("localDb->更新数据:" + sportHrateEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasporthrate.IDatabaseSportHrateHandler
    public SportHrateEntity query(int i) {
        try {
            Cursor cursorQuery = this.database.query(this.mTableName, null, null, null, null, null, null);
            cursorQuery.moveToLast();
            while (!cursorQuery.isBeforeFirst()) {
                if (i == cursorQuery.getInt(3)) {
                    SportHrateEntity sportHrateEntity = new SportHrateEntity();
                    sportHrateEntity.setId(cursorQuery.getInt(0));
                    sportHrateEntity.setUserId(cursorQuery.getLong(1));
                    sportHrateEntity.setDevId(cursorQuery.getString(2));
                    sportHrateEntity.getSensor_data_sport_hrate().update_timestamp = cursorQuery.getInt(3);
                    sportHrateEntity.getSensor_data_sport_hrate().heartrate = cursorQuery.getInt(4);
                    cursorQuery.close();
                    LogUtil.d("localDb->时间戳查询单个数据:" + sportHrateEntity.toString());
                    return sportHrateEntity;
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

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasporthrate.IDatabaseSportHrateHandler
    public ArrayList<SportHrateEntity> queryArrayBetween(int i, int i2) throws Exception {
        LogUtil.d("localDb->queryArrayBetween left:" + String.valueOf(i) + " right:" + String.valueOf(i2));
        ArrayList<SportHrateEntity> arrayList = new ArrayList<>();
        try {
            Cursor cursorRawQuery = this.database.rawQuery("select * from " + this.mTableName + " where " + this.mColumeTimeStamp + " between ? and ? ", new String[]{String.valueOf(i), String.valueOf(i2)});
            cursorRawQuery.moveToLast();
            LogUtil.d("localDb->queryArrayBetween cursor num:" + cursorRawQuery.getCount());
            while (!cursorRawQuery.isBeforeFirst()) {
                SportHrateEntity sportHrateEntity = new SportHrateEntity();
                sportHrateEntity.setId(cursorRawQuery.getInt(0));
                sportHrateEntity.setUserId(cursorRawQuery.getLong(1));
                sportHrateEntity.setDevId(cursorRawQuery.getString(2));
                sportHrateEntity.getSensor_data_sport_hrate().update_timestamp = cursorRawQuery.getInt(3);
                sportHrateEntity.getSensor_data_sport_hrate().heartrate = cursorRawQuery.getInt(4);
                sportHrateEntity.mac = cursorRawQuery.getString(5);
                arrayList.add(sportHrateEntity);
                cursorRawQuery.moveToPrevious();
            }
            cursorRawQuery.close();
            return arrayList;
        } catch (Exception e) {
            LogUtil.e("localDb->db exception");
            throw e;
        }
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatasporthrate.IDatabaseSportHrateHandler
    public void close() {
        this.dbHelper.close();
    }
}
