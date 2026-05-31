package cn.baos.watch.sdk.database.gps;

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
public class GpsLocHandler extends DataBaseFartherHandler implements IDatabaseGpsLocHandler {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context mContext;
    private String mTableName = DatabaseHelper.getGpsLocationTableName();
    private String mColumeTimeStamp = DatabaseHelper.getGpsLocationTime();

    public GpsLocHandler(Context context) {
        this.mContext = context;
    }

    @Override // cn.baos.watch.sdk.database.gps.IDatabaseGpsLocHandler
    public void createDatabase() {
        this.dbHelper = new DatabaseHelper(this.mContext);
    }

    @Override // cn.baos.watch.sdk.database.gps.IDatabaseGpsLocHandler
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

    @Override // cn.baos.watch.sdk.database.gps.IDatabaseGpsLocHandler
    public void insert(GpslocEntity gpslocEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getGpsLocationTime(), Long.valueOf(gpslocEntity.timeStamp));
        contentValues.put(DatabaseHelper.getGpsLocationLat(), gpslocEntity.lat);
        contentValues.put(DatabaseHelper.getGpsLocationLon(), gpslocEntity.lon);
        contentValues.put(this.mColumeTimeStamp, Long.valueOf(gpslocEntity.timeStamp));
        contentValues.put(DatabaseHelper.getGpsLocationSource(), gpslocEntity.source);
        LogUtil.d("localDb->插入 insert successfully:" + this.database.insert(this.mTableName, this.mColumeTimeStamp, contentValues) + gpslocEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.gps.IDatabaseGpsLocHandler
    public void delete(GpslocEntity gpslocEntity) {
        this.database.delete(this.mTableName, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(gpslocEntity.timeStamp)});
    }

    @Override // cn.baos.watch.sdk.database.gps.IDatabaseGpsLocHandler
    public void update(GpslocEntity gpslocEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.getGpsLocationTime(), Long.valueOf(gpslocEntity.timeStamp));
        contentValues.put(DatabaseHelper.getGpsLocationLat(), gpslocEntity.lat);
        contentValues.put(DatabaseHelper.getGpsLocationLon(), gpslocEntity.lon);
        contentValues.put(this.mColumeTimeStamp, Long.valueOf(gpslocEntity.timeStamp));
        contentValues.put(DatabaseHelper.getGpsLocationSource(), gpslocEntity.source);
        contentValues.put(DatabaseHelper.COLUME_MAC, BleUtils.getCurrentMac());
        this.database.update(this.mTableName, contentValues, this.mColumeTimeStamp + "= ?", new String[]{String.valueOf(gpslocEntity.timeStamp)});
        LogUtil.d("localDb->更新数据:" + gpslocEntity.toString());
    }

    @Override // cn.baos.watch.sdk.database.gps.IDatabaseGpsLocHandler
    public GpslocEntity query(int i) {
        try {
            Cursor cursorQuery = this.database.query(this.mTableName, null, null, null, null, null, null);
            cursorQuery.moveToLast();
            while (!cursorQuery.isBeforeFirst()) {
                if (i == cursorQuery.getInt(3)) {
                    GpslocEntity gpslocEntity = new GpslocEntity();
                    gpslocEntity.id = cursorQuery.getInt(0);
                    gpslocEntity.timeStamp = cursorQuery.getLong(1);
                    gpslocEntity.lat = cursorQuery.getString(2);
                    gpslocEntity.lon = cursorQuery.getString(3);
                    gpslocEntity.source = cursorQuery.getString(4);
                    cursorQuery.close();
                    LogUtil.d("localDb->时间戳查询单个数据:" + gpslocEntity.toString());
                    return gpslocEntity;
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

    @Override // cn.baos.watch.sdk.database.gps.IDatabaseGpsLocHandler
    public ArrayList<GpslocEntity> queryArrayBetween(int i, int i2) throws Exception {
        LogUtil.d("localDb->queryArrayBetween left:" + String.valueOf(i) + " right:" + String.valueOf(i2));
        ArrayList<GpslocEntity> arrayList = new ArrayList<>();
        try {
            Cursor cursorRawQuery = this.database.rawQuery("select * from " + this.mTableName + " where " + this.mColumeTimeStamp + " between ? and ?", new String[]{String.valueOf(i), String.valueOf(i2)});
            cursorRawQuery.moveToLast();
            LogUtil.d("localDb->queryArrayBetween cursor num:" + cursorRawQuery.getCount());
            while (!cursorRawQuery.isBeforeFirst()) {
                GpslocEntity gpslocEntity = new GpslocEntity();
                gpslocEntity.id = cursorRawQuery.getInt(0);
                gpslocEntity.timeStamp = cursorRawQuery.getInt(1);
                gpslocEntity.lat = cursorRawQuery.getString(2);
                gpslocEntity.lon = cursorRawQuery.getString(3);
                gpslocEntity.source = cursorRawQuery.getString(4);
                arrayList.add(gpslocEntity);
                cursorRawQuery.moveToPrevious();
            }
            cursorRawQuery.close();
            return arrayList;
        } catch (Exception e) {
            LogUtil.e("localDb->db exception");
            throw e;
        }
    }

    @Override // cn.baos.watch.sdk.database.gps.IDatabaseGpsLocHandler
    public void close() {
        this.dbHelper.close();
    }
}
