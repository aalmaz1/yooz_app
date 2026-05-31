package cn.baos.watch.sdk.database.six.bp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cn.baos.watch.sdk.bluetooth.bt.BleUtils;
import cn.baos.watch.sdk.database.DatabaseHelper;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.w100.messages.Sensor_data_blood_pressure;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class BpHandler extends DataBaseFartherHandler implements IDatabaseBpHandler {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;
    private String mTableName = DatabaseHelper.BP_TABLE_NAME;
    private String mColumeTimeStamp = "time";

    public BpHandler(Context context) {
        this.mContext = context;
    }

    @Override // cn.baos.watch.sdk.database.six.bp.IDatabaseBpHandler
    public void createDatabase() {
        this.dbHelper = new DatabaseHelper(this.mContext);
    }

    @Override // cn.baos.watch.sdk.database.six.bp.IDatabaseBpHandler
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

    @Override // cn.baos.watch.sdk.database.six.bp.IDatabaseBpHandler
    public void insert(Sensor_data_blood_pressure sensor_data_blood_pressure) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(sensor_data_blood_pressure.update_timestamp));
        contentValues.put(DatabaseHelper.BP_COLUMN_HIGH, Integer.valueOf(sensor_data_blood_pressure.high));
        contentValues.put(DatabaseHelper.BP_COLUMN_LOW, Integer.valueOf(sensor_data_blood_pressure.low));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        LogUtil.d("localDb->插入 insert successfully:" + this.database.insert(this.mTableName, this.mColumeTimeStamp, contentValues) + sensor_data_blood_pressure.toString());
    }

    @Override // cn.baos.watch.sdk.database.six.bp.IDatabaseBpHandler
    public void delete(Sensor_data_blood_pressure sensor_data_blood_pressure) {
        this.database.delete(this.mTableName, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(sensor_data_blood_pressure.update_timestamp)});
    }

    @Override // cn.baos.watch.sdk.database.six.bp.IDatabaseBpHandler
    public void update(Sensor_data_blood_pressure sensor_data_blood_pressure) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(sensor_data_blood_pressure.update_timestamp));
        contentValues.put(DatabaseHelper.BP_COLUMN_HIGH, Integer.valueOf(sensor_data_blood_pressure.high));
        contentValues.put(DatabaseHelper.BP_COLUMN_LOW, Integer.valueOf(sensor_data_blood_pressure.low));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.update(this.mTableName, contentValues, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(sensor_data_blood_pressure.update_timestamp)});
        LogUtil.d("localDb->更新数据:" + sensor_data_blood_pressure.toString());
    }

    @Override // cn.baos.watch.sdk.database.six.bp.IDatabaseBpHandler
    public ArrayList<BpEntity> queryArrayBetween(int i, int i2) throws Exception {
        LogUtil.d("localDb->queryArrayBetween left:" + String.valueOf(i) + " right:" + String.valueOf(i2));
        ArrayList<BpEntity> arrayList = new ArrayList<>();
        try {
            Cursor cursorRawQuery = this.database.rawQuery("select * from " + this.mTableName + " where " + this.mColumeTimeStamp + " between ? and ? ", new String[]{String.valueOf(i), String.valueOf(i2)});
            cursorRawQuery.moveToLast();
            LogUtil.d("localDb->queryArrayBetween cursor num:" + cursorRawQuery.getCount());
            while (!cursorRawQuery.isBeforeFirst()) {
                BpEntity bpEntity = new BpEntity();
                bpEntity.id = cursorRawQuery.getInt(0);
                bpEntity.timeStamp = cursorRawQuery.getLong(1);
                bpEntity.high = cursorRawQuery.getInt(2);
                bpEntity.low = cursorRawQuery.getInt(3);
                bpEntity.mac = cursorRawQuery.getString(4);
                arrayList.add(bpEntity);
                cursorRawQuery.moveToPrevious();
            }
            cursorRawQuery.close();
            return arrayList;
        } catch (Exception e) {
            LogUtil.e("localDb->db exception");
            throw e;
        }
    }

    @Override // cn.baos.watch.sdk.database.six.bp.IDatabaseBpHandler
    public void close() {
        this.dbHelper.close();
    }
}
