package cn.baos.watch.sdk.database.fromwatch.sensordatadailyspo;

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
public class DatabaseDailySpoHandler extends DataBaseFartherHandler implements IDatabaseDailySpoHandler {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;
    private String mTableName = DatabaseHelper.getDailySpoTableName();
    private String mColumeTimeStamp = DatabaseHelper.getDailySpoColumnTime();

    public DatabaseDailySpoHandler(Context context) {
        this.mContext = context;
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyspo.IDatabaseDailySpoHandler
    public void createDatabase() {
        this.dbHelper = new DatabaseHelper(this.mContext);
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyspo.IDatabaseDailySpoHandler
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

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyspo.IDatabaseDailySpoHandler
    public void insert(DailySpoEntity dailySpoEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getDailySpoUserId(), Long.valueOf(dailySpoEntity.getUserId()));
        contentValues.put(DatabaseHelper.getDailySpoDeviceId(), dailySpoEntity.getDevId());
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(dailySpoEntity.getSensor_data_daily_spo().update_timestamp));
        contentValues.put(DatabaseHelper.getDAILY_DAILY_COLUMN_spo(), Integer.valueOf(dailySpoEntity.getSensor_data_daily_spo().spo));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.insert(this.mTableName, this.mColumeTimeStamp, contentValues);
        LogUtil.d("localDb->插入 insert successfully:" + dailySpoEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyspo.IDatabaseDailySpoHandler
    public void delete(DailySpoEntity dailySpoEntity) {
        this.database.delete(this.mTableName, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(dailySpoEntity.getSensor_data_daily_spo().update_timestamp)});
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyspo.IDatabaseDailySpoHandler
    public void update(DailySpoEntity dailySpoEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getDailySpoUserId(), Long.valueOf(dailySpoEntity.getUserId()));
        contentValues.put(DatabaseHelper.getDailySpoDeviceId(), dailySpoEntity.getDevId());
        contentValues.put(this.mColumeTimeStamp, Integer.valueOf(dailySpoEntity.getSensor_data_daily_spo().update_timestamp));
        contentValues.put(DatabaseHelper.getDAILY_DAILY_COLUMN_spo(), Integer.valueOf(dailySpoEntity.getSensor_data_daily_spo().spo));
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.update(this.mTableName, contentValues, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(dailySpoEntity.getSensor_data_daily_spo().update_timestamp)});
        LogUtil.d("localDb->更新数据:" + dailySpoEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyspo.IDatabaseDailySpoHandler
    public DailySpoEntity query(int i) {
        try {
            Cursor cursorQuery = this.database.query(this.mTableName, null, null, null, null, null, null);
            cursorQuery.moveToLast();
            while (!cursorQuery.isBeforeFirst()) {
                if (i == cursorQuery.getInt(3)) {
                    DailySpoEntity dailySpoEntity = new DailySpoEntity();
                    dailySpoEntity.setId(cursorQuery.getInt(0));
                    dailySpoEntity.setUserId(cursorQuery.getLong(1));
                    dailySpoEntity.setDevId(cursorQuery.getString(2));
                    dailySpoEntity.getSensor_data_daily_spo().update_timestamp = cursorQuery.getInt(3);
                    dailySpoEntity.getSensor_data_daily_spo().spo = cursorQuery.getInt(4);
                    cursorQuery.close();
                    LogUtil.d("localDb->时间戳查询单个数据:" + dailySpoEntity.toString());
                    return dailySpoEntity;
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

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyspo.IDatabaseDailySpoHandler
    public ArrayList<DailySpoEntity> queryArrayBetween(int i, int i2) throws Exception {
        LogUtil.d("localDb->queryArrayBetween left:" + String.valueOf(i) + " right:" + String.valueOf(i2));
        ArrayList<DailySpoEntity> arrayList = new ArrayList<>();
        try {
            Cursor cursorRawQuery = this.database.rawQuery("select * from " + this.mTableName + " where " + this.mColumeTimeStamp + " between ? and ? ", new String[]{String.valueOf(i), String.valueOf(i2)});
            cursorRawQuery.moveToLast();
            LogUtil.d("localDb->queryArrayBetween cursor num:" + cursorRawQuery.getCount());
            while (!cursorRawQuery.isBeforeFirst()) {
                DailySpoEntity dailySpoEntity = new DailySpoEntity();
                dailySpoEntity.setId(cursorRawQuery.getInt(0));
                dailySpoEntity.setUserId(cursorRawQuery.getLong(1));
                dailySpoEntity.setDevId(cursorRawQuery.getString(2));
                dailySpoEntity.getSensor_data_daily_spo().update_timestamp = cursorRawQuery.getInt(3);
                dailySpoEntity.getSensor_data_daily_spo().spo = cursorRawQuery.getInt(4);
                dailySpoEntity.mac = cursorRawQuery.getString(5);
                arrayList.add(dailySpoEntity);
                cursorRawQuery.moveToPrevious();
            }
            cursorRawQuery.close();
            return arrayList;
        } catch (Exception e) {
            LogUtil.e("localDb->db exception");
            throw e;
        }
    }

    @Override // cn.baos.watch.sdk.database.fromwatch.sensordatadailyspo.IDatabaseDailySpoHandler
    public void close() {
        this.dbHelper.close();
    }
}
