package cn.baos.watch.sdk.database.six.rh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cn.baos.watch.sdk.bluetooth.bt.BleUtils;
import cn.baos.watch.sdk.database.DatabaseHelper;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.w100.messages.Sensor_data_general_health;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class RhHandler extends DataBaseFartherHandler implements IDatabaseRhHandler {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;
    private String mTableName = DatabaseHelper.RH_TABLE_NAME;
    private String mColumeTimeStamp = "time";

    public RhHandler(Context context) {
        this.mContext = context;
    }

    @Override // cn.baos.watch.sdk.database.six.rh.IDatabaseRhHandler
    public void createDatabase() {
        this.dbHelper = new DatabaseHelper(this.mContext);
    }

    @Override // cn.baos.watch.sdk.database.six.rh.IDatabaseRhHandler
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

    @Override // cn.baos.watch.sdk.database.six.rh.IDatabaseRhHandler
    public void insert(Sensor_data_general_health sensor_data_general_health) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(sensor_data_general_health.update_timestamp));
        contentValues.put(DatabaseHelper.RH_COLUMN_STRESS, Integer.valueOf(sensor_data_general_health.stress));
        contentValues.put(DatabaseHelper.RH_COLUMN_BREATHING_RATE, Integer.valueOf(sensor_data_general_health.breathing_rate));
        contentValues.put(DatabaseHelper.RH_COLUMN_R1, Integer.valueOf(sensor_data_general_health.reserve1));
        contentValues.put(DatabaseHelper.RH_COLUMN_R2, Integer.valueOf(sensor_data_general_health.reserve2));
        contentValues.put(DatabaseHelper.RH_COLUMN_R3, Integer.valueOf(sensor_data_general_health.reserve3));
        contentValues.put(DatabaseHelper.RH_COLUMN_R4, Integer.valueOf(sensor_data_general_health.reserve4));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        LogUtil.d("localDb->插入 insert successfully:" + this.database.insert(this.mTableName, this.mColumeTimeStamp, contentValues) + sensor_data_general_health.toString());
    }

    @Override // cn.baos.watch.sdk.database.six.rh.IDatabaseRhHandler
    public void delete(Sensor_data_general_health sensor_data_general_health) {
        this.database.delete(this.mTableName, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(sensor_data_general_health.update_timestamp)});
    }

    @Override // cn.baos.watch.sdk.database.six.rh.IDatabaseRhHandler
    public void update(Sensor_data_general_health sensor_data_general_health) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(sensor_data_general_health.update_timestamp));
        contentValues.put(DatabaseHelper.RH_COLUMN_STRESS, Integer.valueOf(sensor_data_general_health.stress));
        contentValues.put(DatabaseHelper.RH_COLUMN_BREATHING_RATE, Integer.valueOf(sensor_data_general_health.breathing_rate));
        contentValues.put(DatabaseHelper.RH_COLUMN_R1, Integer.valueOf(sensor_data_general_health.reserve1));
        contentValues.put(DatabaseHelper.RH_COLUMN_R2, Integer.valueOf(sensor_data_general_health.reserve2));
        contentValues.put(DatabaseHelper.RH_COLUMN_R3, Integer.valueOf(sensor_data_general_health.reserve3));
        contentValues.put(DatabaseHelper.RH_COLUMN_R4, Integer.valueOf(sensor_data_general_health.reserve4));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.update(this.mTableName, contentValues, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(sensor_data_general_health.update_timestamp)});
        LogUtil.d("localDb->更新数据:" + sensor_data_general_health.toString());
    }

    @Override // cn.baos.watch.sdk.database.six.rh.IDatabaseRhHandler
    public ArrayList<RhEntity> queryArrayBetween(int i, int i2) throws Exception {
        LogUtil.d("localDb->queryArrayBetween left:" + String.valueOf(i) + " right:" + String.valueOf(i2));
        ArrayList<RhEntity> arrayList = new ArrayList<>();
        try {
            Cursor cursorRawQuery = this.database.rawQuery("select * from " + this.mTableName + " where " + this.mColumeTimeStamp + " between ? and ? ", new String[]{String.valueOf(i), String.valueOf(i2)});
            cursorRawQuery.moveToLast();
            LogUtil.d("localDb->queryArrayBetween cursor num:" + cursorRawQuery.getCount());
            while (!cursorRawQuery.isBeforeFirst()) {
                RhEntity rhEntity = new RhEntity();
                rhEntity.id = cursorRawQuery.getInt(0);
                rhEntity.timeStamp = cursorRawQuery.getLong(1);
                rhEntity.stress = cursorRawQuery.getInt(2);
                rhEntity.breathingRate = cursorRawQuery.getInt(3);
                rhEntity.reserve1 = cursorRawQuery.getInt(4);
                rhEntity.reserve2 = cursorRawQuery.getInt(5);
                rhEntity.reserve3 = cursorRawQuery.getInt(6);
                rhEntity.reserve4 = cursorRawQuery.getInt(7);
                rhEntity.mac = cursorRawQuery.getString(8);
                arrayList.add(rhEntity);
                cursorRawQuery.moveToPrevious();
            }
            cursorRawQuery.close();
            return arrayList;
        } catch (Exception e) {
            LogUtil.e("localDb->db exception");
            throw e;
        }
    }

    @Override // cn.baos.watch.sdk.database.six.rh.IDatabaseRhHandler
    public void close() {
        this.dbHelper.close();
    }
}
