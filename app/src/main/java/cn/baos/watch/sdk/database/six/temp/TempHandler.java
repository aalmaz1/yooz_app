package cn.baos.watch.sdk.database.six.temp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import cn.baos.watch.sdk.bluetooth.bt.BleUtils;
import cn.baos.watch.sdk.database.DatabaseHelper;
import cn.baos.watch.sdk.database.fromwatch.DataBaseFartherHandler;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.w100.messages.Sensor_data_temperature;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class TempHandler extends DataBaseFartherHandler implements IDatabaseTempHandler {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;
    private String mTableName = DatabaseHelper.TEMP_TABLE_NAME;
    private String mColumeTimeStamp = "time";

    public TempHandler(Context context) {
        this.mContext = context;
    }

    @Override // cn.baos.watch.sdk.database.six.temp.IDatabaseTempHandler
    public void createDatabase() {
        this.dbHelper = new DatabaseHelper(this.mContext);
    }

    @Override // cn.baos.watch.sdk.database.six.temp.IDatabaseTempHandler
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

    @Override // cn.baos.watch.sdk.database.six.temp.IDatabaseTempHandler
    public void insert(Sensor_data_temperature sensor_data_temperature) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(sensor_data_temperature.update_timestamp));
        contentValues.put("value", Integer.valueOf(sensor_data_temperature.value));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        LogUtil.d("localDb->插入 insert successfully:" + this.database.insert(this.mTableName, this.mColumeTimeStamp, contentValues) + sensor_data_temperature.toString());
    }

    @Override // cn.baos.watch.sdk.database.six.temp.IDatabaseTempHandler
    public void delete(Sensor_data_temperature sensor_data_temperature) {
        this.database.delete(this.mTableName, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(sensor_data_temperature.update_timestamp)});
    }

    @Override // cn.baos.watch.sdk.database.six.temp.IDatabaseTempHandler
    public void update(Sensor_data_temperature sensor_data_temperature) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(sensor_data_temperature.update_timestamp));
        contentValues.put("value", Integer.valueOf(sensor_data_temperature.value));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.update(this.mTableName, contentValues, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(sensor_data_temperature.update_timestamp)});
        LogUtil.d("localDb->更新数据:" + sensor_data_temperature.toString());
    }

    @Override // cn.baos.watch.sdk.database.six.temp.IDatabaseTempHandler
    public ArrayList<TempEntity> queryArrayBetween(int i, int i2) throws Exception {
        LogUtil.d("localDb->queryArrayBetween left:" + String.valueOf(i) + " right:" + String.valueOf(i2));
        ArrayList<TempEntity> arrayList = new ArrayList<>();
        try {
            Cursor cursorRawQuery = this.database.rawQuery("select * from " + this.mTableName + " where " + this.mColumeTimeStamp + " between ? and ? ", new String[]{String.valueOf(i), String.valueOf(i2)});
            cursorRawQuery.moveToLast();
            LogUtil.d("localDb->queryArrayBetween cursor num:" + cursorRawQuery.getCount());
            while (!cursorRawQuery.isBeforeFirst()) {
                TempEntity tempEntity = new TempEntity();
                tempEntity.id = cursorRawQuery.getInt(0);
                tempEntity.timeStamp = cursorRawQuery.getLong(1);
                tempEntity.value = cursorRawQuery.getInt(2);
                tempEntity.mac = cursorRawQuery.getString(3);
                arrayList.add(tempEntity);
                cursorRawQuery.moveToPrevious();
            }
            cursorRawQuery.close();
            return arrayList;
        } catch (Exception e) {
            LogUtil.e("localDb->db exception");
            throw e;
        }
    }

    @Override // cn.baos.watch.sdk.database.six.temp.IDatabaseTempHandler
    public void close() {
        this.dbHelper.close();
    }
}
