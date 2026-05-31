package cn.baos.watch.sdk.huabaoImpl.syncdata;

import android.content.Context;
import cn.baos.message.CatagoryEnum;
import cn.baos.message.Serializable;
import cn.baos.watch.sdk.base.AppDataConfig;
import cn.baos.watch.sdk.huabaoImpl.syncdata.dailyactive.DailyActiveManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.dailyhrate.DailyHrateManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.dailyrhr.DailyRhrManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.dailyspo.DailySpoManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.six.bp.BpManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.six.bs.BsManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.six.meto.MetoManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.six.rh.RhManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.six.temp.TempManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sleepstats.SleepStatsManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sleepstatus.SleepStatusManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sporthrate.SportHeartManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sportmode.SportModeManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sportrecord.SportRecordFromWatchManager;
import cn.baos.watch.sdk.interfac.syncdata.SyncDataInterface;
import cn.baos.watch.sdk.interfac.syncdata.SyncRightNowDataCallback;
import cn.baos.watch.sdk.interfac.syncdata.SyncStatusCallback;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SharePreferenceUtils;
import cn.baos.watch.sdk.util.W100Utils;
import cn.baos.watch.w100.messages.Response_no_data;
import cn.baos.watch.w100.messages.Sensor_data_array;
import cn.baos.watch.w100.messages.Sensor_data_blood_pressure;
import cn.baos.watch.w100.messages.Sensor_data_blood_sugar;
import cn.baos.watch.w100.messages.Sensor_data_daily_active_array;
import cn.baos.watch.w100.messages.Sensor_data_daily_active_sum;
import cn.baos.watch.w100.messages.Sensor_data_daily_active_sum_v2;
import cn.baos.watch.w100.messages.Sensor_data_daily_hrate;
import cn.baos.watch.w100.messages.Sensor_data_daily_hrate_array;
import cn.baos.watch.w100.messages.Sensor_data_daily_rhr_array;
import cn.baos.watch.w100.messages.Sensor_data_daily_spo;
import cn.baos.watch.w100.messages.Sensor_data_daily_spo_array;
import cn.baos.watch.w100.messages.Sensor_data_general_health;
import cn.baos.watch.w100.messages.Sensor_data_sleep_stats_array;
import cn.baos.watch.w100.messages.Sensor_data_sleep_status_array;
import cn.baos.watch.w100.messages.Sensor_data_sport_hrate_array;
import cn.baos.watch.w100.messages.Sensor_data_sport_mode_array;
import cn.baos.watch.w100.messages.Sensor_data_temperature;
import cn.baos.watch.w100.messages.Sport_record_array;
import com.google.gson.Gson;

/* JADX INFO: loaded from: classes.dex */
public class SyncDataImpl implements SyncDataInterface {
    private static SyncDataImpl instance;
    private int mSyncDataCurrentCategoryId;
    private SYNC_DATA_STATUS mSyncDataStatus = SYNC_DATA_STATUS.SYNC_DATA_END;
    private SyncRightNowDataCallback mSyncRightNowDataCallback;
    private SyncStatusCallback mSyncStatusCallback;

    enum SYNC_DATA_STATUS {
        SYNC_DATA_START,
        SYNC_DATA_END
    }

    public enum SYNC_RIGHT_NOW_DATA_CONTROL {
        OPEN,
        CLOSE
    }

    public static SyncDataImpl getInstance() {
        if (instance == null) {
            synchronized (SyncDataImpl.class) {
                if (instance == null) {
                    instance = new SyncDataImpl();
                }
            }
        }
        return instance;
    }

    @Override // cn.baos.watch.sdk.interfac.syncdata.SyncDataInterface
    public void startSyncDateFromWatch(Context context, SyncStatusCallback syncStatusCallback) {
        this.mSyncStatusCallback = syncStatusCallback;
        this.mSyncDataStatus = SYNC_DATA_STATUS.SYNC_DATA_START;
        SyncStatusCallback syncStatusCallback2 = this.mSyncStatusCallback;
        if (syncStatusCallback2 != null) {
            syncStatusCallback2.onStartSync();
        }
        MessageManager.getInstance().requestSyncDataFromWatch(CatagoryEnum.SPORT_RECORD_ARRAY);
    }

    public void onGetSyncDataFromWatchSix(Context context, Serializable serializable) {
        Sensor_data_array sensor_data_array = (Sensor_data_array) serializable;
        int i = 0;
        switch (sensor_data_array.data_catagory) {
            case CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_SUM_V2 /* 90230 */:
                LogUtil.d("数据同步From手表->SENSOR_DATA_GENERAL_HEALTH:" + W100Utils.toString(serializable));
                while (i < sensor_data_array.datas.length) {
                    Serializable serializableUnpackMessage = MessageManager.unpackMessage(sensor_data_array.datas[i].obj);
                    LogUtil.e("-->>>-meto>" + new Gson().toJson(serializableUnpackMessage));
                    Sensor_data_daily_active_sum_v2 sensor_data_daily_active_sum_v2 = (Sensor_data_daily_active_sum_v2) serializableUnpackMessage;
                    long jQueryLastedTime = queryLastedTime(CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_SUM_V2);
                    if (sensor_data_daily_active_sum_v2 != null && sensor_data_daily_active_sum_v2.update_timestamp > jQueryLastedTime) {
                        MetoManager.getInstance().saveMetoModeEntitiesToDb(sensor_data_daily_active_sum_v2);
                    }
                    i++;
                }
                MessageManager.getInstance().requestSyncDataFromWatchSix(CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_SUM_V2);
                break;
            case CatagoryEnum.SENSOR_DATA_GENERAL_HEALTH /* 90231 */:
                LogUtil.d("数据同步From手表->SENSOR_DATA_GENERAL_HEALTH:" + W100Utils.toString(serializable));
                while (i < sensor_data_array.datas.length) {
                    Serializable serializableUnpackMessage2 = MessageManager.unpackMessage(sensor_data_array.datas[i].obj);
                    LogUtil.e("-->>>-rh>" + new Gson().toJson(serializableUnpackMessage2));
                    Sensor_data_general_health sensor_data_general_health = (Sensor_data_general_health) serializableUnpackMessage2;
                    long jQueryLastedTime2 = queryLastedTime(CatagoryEnum.SENSOR_DATA_GENERAL_HEALTH);
                    if (sensor_data_general_health != null && sensor_data_general_health.update_timestamp > jQueryLastedTime2) {
                        RhManager.getInstance().savRhModeEntitiesToDb(sensor_data_general_health);
                    }
                    i++;
                }
                MessageManager.getInstance().requestSyncDataFromWatchSix(CatagoryEnum.SENSOR_DATA_GENERAL_HEALTH);
                break;
            case CatagoryEnum.SENSOR_DATA_BLOOD_PRESSURE /* 90232 */:
                LogUtil.d("数据同步From手表->SENSOR_DATA_BLOOD_PRESSURE:" + W100Utils.toString(serializable));
                while (i < sensor_data_array.datas.length) {
                    Serializable serializableUnpackMessage3 = MessageManager.unpackMessage(sensor_data_array.datas[i].obj);
                    LogUtil.e("-->>>-bp>" + new Gson().toJson(serializableUnpackMessage3));
                    Sensor_data_blood_pressure sensor_data_blood_pressure = (Sensor_data_blood_pressure) serializableUnpackMessage3;
                    long jQueryLastedTime3 = queryLastedTime(CatagoryEnum.SENSOR_DATA_BLOOD_PRESSURE);
                    if (sensor_data_blood_pressure != null && sensor_data_blood_pressure.update_timestamp > jQueryLastedTime3) {
                        BpManager.getInstance().saveBpModeEntitiesToDb(sensor_data_blood_pressure);
                    }
                    i++;
                }
                MessageManager.getInstance().requestSyncDataFromWatchSix(CatagoryEnum.SENSOR_DATA_BLOOD_PRESSURE);
                break;
            case CatagoryEnum.SENSOR_DATA_TEMPERATURE /* 90233 */:
                LogUtil.d("数据同步From手表->SENSOR_DATA_TEMPERATURE:" + W100Utils.toString(serializable));
                while (i < sensor_data_array.datas.length) {
                    Serializable serializableUnpackMessage4 = MessageManager.unpackMessage(sensor_data_array.datas[i].obj);
                    LogUtil.e("-->>>-temp>" + new Gson().toJson(serializableUnpackMessage4));
                    Sensor_data_temperature sensor_data_temperature = (Sensor_data_temperature) serializableUnpackMessage4;
                    long jQueryLastedTime4 = queryLastedTime(CatagoryEnum.SENSOR_DATA_TEMPERATURE);
                    if (sensor_data_temperature != null && sensor_data_temperature.update_timestamp > jQueryLastedTime4) {
                        TempManager.getInstance().savTempModeEntitiesToDb(sensor_data_temperature);
                    }
                    i++;
                }
                MessageManager.getInstance().requestSyncDataFromWatchSix(CatagoryEnum.SENSOR_DATA_TEMPERATURE);
                break;
            case CatagoryEnum.SENSOR_DATA_BLOOD_SUGAR /* 90234 */:
                LogUtil.d("数据同步From手表->SENSOR_DATA_BLOOD_SUGAR:" + W100Utils.toString(serializable));
                while (i < sensor_data_array.datas.length) {
                    Serializable serializableUnpackMessage5 = MessageManager.unpackMessage(sensor_data_array.datas[i].obj);
                    LogUtil.e("-->>>-bs>" + new Gson().toJson(serializableUnpackMessage5));
                    Sensor_data_blood_sugar sensor_data_blood_sugar = (Sensor_data_blood_sugar) serializableUnpackMessage5;
                    long jQueryLastedTime5 = queryLastedTime(CatagoryEnum.SENSOR_DATA_BLOOD_SUGAR);
                    if (sensor_data_blood_sugar != null && sensor_data_blood_sugar.update_timestamp > jQueryLastedTime5) {
                        BsManager.getInstance().saveBsModeEntitiesToDb(sensor_data_blood_sugar);
                    }
                    i++;
                }
                MessageManager.getInstance().requestSyncDataFromWatchSix(CatagoryEnum.SENSOR_DATA_BLOOD_SUGAR);
                break;
        }
    }

    public void onGetSyncDataFromWatch(Context context, Serializable serializable) {
        int i = serializable.catagory;
        if (i != 30101) {
            switch (i) {
                case CatagoryEnum.SPORT_RECORD_ARRAY /* 90210 */:
                    LogUtil.d("数据同步From手表->SPORT_RECORD_ARRAY:" + W100Utils.toString(serializable));
                    Sport_record_array sport_record_array = (Sport_record_array) serializable;
                    LogUtil.d("数据同步From手表->SPORT_RECORD_ARRAY 开始时间:" + sport_record_array.datas[0].update_timestamp + " 结束时间:" + sport_record_array.datas[sport_record_array.datas.length - 1].update_timestamp);
                    SportRecordFromWatchManager.getInstance().saveSportRecordFromWatchEntitiesToDb(sport_record_array);
                    MessageManager.getInstance().requestSyncDataFromWatch(CatagoryEnum.SPORT_RECORD_ARRAY);
                    break;
                case CatagoryEnum.SENSOR_DATA_SPORT_MODE_ARRAY /* 90211 */:
                    LogUtil.d("数据同步From手表->SENSOR_DATA_SPORT_MODE_ARRAY:" + W100Utils.toString(serializable));
                    Sensor_data_sport_mode_array sensor_data_sport_mode_array = (Sensor_data_sport_mode_array) serializable;
                    LogUtil.d("数据同步From手表->SENSOR_DATA_SPORT_MODE_ARRAY 开始时间:" + sensor_data_sport_mode_array.datas[0].update_timestamp + " 结束时间:" + sensor_data_sport_mode_array.datas[sensor_data_sport_mode_array.datas.length - 1].update_timestamp);
                    SportModeManager.getInstance().saveSportModeEntitiesToDb(sensor_data_sport_mode_array);
                    MessageManager.getInstance().requestSyncDataFromWatch(CatagoryEnum.SENSOR_DATA_SPORT_MODE_ARRAY);
                    break;
                case CatagoryEnum.SENSOR_DATA_SPORT_HRATE_ARRAY /* 90212 */:
                    LogUtil.d("数据同步From手表->SENSOR_DATA_SPORT_HRATE_ARRAY:" + W100Utils.toString(serializable));
                    Sensor_data_sport_hrate_array sensor_data_sport_hrate_array = (Sensor_data_sport_hrate_array) serializable;
                    LogUtil.d("数据同步From手表->SENSOR_DATA_SPORT_HRATE_ARRAY 开始时间:" + sensor_data_sport_hrate_array.datas[0].update_timestamp + " 结束时间:" + sensor_data_sport_hrate_array.datas[sensor_data_sport_hrate_array.datas.length - 1].update_timestamp);
                    SportHeartManager.getInstance().saveSportHrateEntitiesToDb(sensor_data_sport_hrate_array);
                    MessageManager.getInstance().requestSyncDataFromWatch(CatagoryEnum.SENSOR_DATA_SPORT_HRATE_ARRAY);
                    break;
                case CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_ARRAY /* 90213 */:
                    LogUtil.d("数据同步From手表->SENSOR_DATA_DAILY_ACTIVE_ARRAY:" + W100Utils.toString(serializable));
                    Sensor_data_daily_active_array sensor_data_daily_active_array = (Sensor_data_daily_active_array) serializable;
                    LogUtil.d("数据同步From手表->SENSOR_DATA_DAILY_ACTIVE_ARRAY 开始时间:" + sensor_data_daily_active_array.datas[0].update_timestamp + " 结束时间:" + sensor_data_daily_active_array.datas[sensor_data_daily_active_array.datas.length - 1].update_timestamp);
                    DailyActiveManager.getInstance().saveData(sensor_data_daily_active_array);
                    MessageManager.getInstance().requestSyncDataFromWatch(CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_ARRAY);
                    break;
                case CatagoryEnum.SENSOR_DATA_SLEEP_STATS_ARRAY /* 90214 */:
                    LogUtil.d("数据同步From手表->SENSOR_DATA_SLEEP_STATS_ARRAY:" + W100Utils.toString(serializable));
                    Sensor_data_sleep_stats_array sensor_data_sleep_stats_array = (Sensor_data_sleep_stats_array) serializable;
                    LogUtil.d("数据同步From手表->SENSOR_DATA_SLEEP_STATS_ARRAY 开始时间:" + sensor_data_sleep_stats_array.datas[0].update_timestamp + " 结束时间:" + sensor_data_sleep_stats_array.datas[sensor_data_sleep_stats_array.datas.length - 1].update_timestamp);
                    SleepStatsManager.getInstance().saveSleepStatsEntitiesToDb(sensor_data_sleep_stats_array);
                    MessageManager.getInstance().requestSyncDataFromWatch(CatagoryEnum.SENSOR_DATA_SLEEP_STATS_ARRAY);
                    break;
                case CatagoryEnum.SENSOR_DATA_SLEEP_STATUS_ARRAY /* 90215 */:
                    LogUtil.d("数据同步From手表->SENSOR_DATA_SLEEP_STATUS_ARRAY:" + W100Utils.toString(serializable));
                    Sensor_data_sleep_status_array sensor_data_sleep_status_array = (Sensor_data_sleep_status_array) serializable;
                    LogUtil.d("数据同步From手表->SENSOR_DATA_SLEEP_STATUS_ARRAY 开始时间:" + sensor_data_sleep_status_array.datas[0].update_timestamp + " 结束时间:" + sensor_data_sleep_status_array.datas[sensor_data_sleep_status_array.datas.length - 1].update_timestamp);
                    SleepStatusManager.getInstance().saveSleepStatusEntitiesToDb(sensor_data_sleep_status_array);
                    MessageManager.getInstance().requestSyncDataFromWatch(CatagoryEnum.SENSOR_DATA_SLEEP_STATUS_ARRAY);
                    break;
                case CatagoryEnum.SENSOR_DATA_DAILY_HRATE_ARRAY /* 90216 */:
                    LogUtil.d("数据同步From手表->SENSOR_DATA_DAILY_HRATE_ARRAY:" + W100Utils.toString(serializable));
                    Sensor_data_daily_hrate_array sensor_data_daily_hrate_array = (Sensor_data_daily_hrate_array) serializable;
                    LogUtil.d("数据同步From手表->SENSOR_DATA_DAILY_HRATE_ARRAY 开始时间:" + sensor_data_daily_hrate_array.datas[0].update_timestamp + " 结束时间:" + sensor_data_daily_hrate_array.datas[sensor_data_daily_hrate_array.datas.length - 1].update_timestamp);
                    DailyHrateManager.getInstance().saveDailyHrateEntitiesToDb(sensor_data_daily_hrate_array);
                    MessageManager.getInstance().requestSyncDataFromWatch(CatagoryEnum.SENSOR_DATA_DAILY_HRATE_ARRAY);
                    break;
                case CatagoryEnum.SENSOR_DATA_DAILY_SPO_ARRAY /* 90217 */:
                    LogUtil.d("数据同步From手表->SENSOR_DATA_DAILY_SPO_ARRAY:" + W100Utils.toString(serializable));
                    Sensor_data_daily_spo_array sensor_data_daily_spo_array = (Sensor_data_daily_spo_array) serializable;
                    LogUtil.d("数据同步From手表->SENSOR_DATA_DAILY_SPO_ARRAY 开始时间:" + sensor_data_daily_spo_array.datas[0].update_timestamp + " 结束时间:" + sensor_data_daily_spo_array.datas[sensor_data_daily_spo_array.datas.length - 1].update_timestamp);
                    DailySpoManager.getInstance().saveDailySpoEntitiesToDb(sensor_data_daily_spo_array);
                    MessageManager.getInstance().requestSyncDataFromWatch(CatagoryEnum.SENSOR_DATA_DAILY_SPO_ARRAY);
                    break;
                case CatagoryEnum.SENSOR_DATA_DAILY_RHR_ARRAY /* 90218 */:
                    LogUtil.d("数据同步From手表->SENSOR_DATA_DAILY_RHR_ARRAY:" + W100Utils.toString(serializable));
                    Sensor_data_daily_rhr_array sensor_data_daily_rhr_array = (Sensor_data_daily_rhr_array) serializable;
                    LogUtil.d("数据同步From手表->SENSOR_DATA_DAILY_RHR_ARRAY 开始时间:" + sensor_data_daily_rhr_array.datas[0].update_timestamp + " 结束时间:" + sensor_data_daily_rhr_array.datas[sensor_data_daily_rhr_array.datas.length - 1].update_timestamp);
                    DailyRhrManager.getInstance().saveDailyRhrEntitiesToDb(sensor_data_daily_rhr_array);
                    MessageManager.getInstance().requestSyncDataFromWatch(CatagoryEnum.SENSOR_DATA_DAILY_RHR_ARRAY);
                    break;
            }
        }
        Response_no_data response_no_data = (Response_no_data) serializable;
        int i2 = response_no_data.data_catagary;
        if (i2 != 90235) {
            switch (i2) {
                case CatagoryEnum.SPORT_RECORD_ARRAY /* 90210 */:
                    LogUtil.d("数据同步From手表->SPORT_RECORD_ARRAY 结束" + this.mSyncDataCurrentCategoryId);
                    MessageManager.getInstance().requestSyncDataFromWatch(CatagoryEnum.SENSOR_DATA_SPORT_MODE_ARRAY);
                    break;
                case CatagoryEnum.SENSOR_DATA_SPORT_MODE_ARRAY /* 90211 */:
                    LogUtil.d("数据同步From手表->SENSOR_DATA_SPORT_MODE_ARRAY 结束" + this.mSyncDataCurrentCategoryId);
                    MessageManager.getInstance().requestSyncDataFromWatch(CatagoryEnum.SENSOR_DATA_SPORT_HRATE_ARRAY);
                    break;
                case CatagoryEnum.SENSOR_DATA_SPORT_HRATE_ARRAY /* 90212 */:
                    LogUtil.d("数据同步From手表->SENSOR_DATA_SPORT_HRATE_ARRAY 结束" + this.mSyncDataCurrentCategoryId);
                    MessageManager.getInstance().requestSyncDataFromWatch(CatagoryEnum.SENSOR_DATA_SLEEP_STATS_ARRAY);
                    break;
                case CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_ARRAY /* 90213 */:
                    LogUtil.d("数据同步From手表->SENSOR_DATA_DAILY_ACTIVE_ARRAY 结束" + this.mSyncDataCurrentCategoryId);
                    MessageManager.getInstance().requestSyncDataFromWatch(CatagoryEnum.SENSOR_DATA_DAILY_HRATE_ARRAY);
                    break;
                case CatagoryEnum.SENSOR_DATA_SLEEP_STATS_ARRAY /* 90214 */:
                    LogUtil.d("数据同步From手表->SENSOR_DATA_SLEEP_STATS_ARRAY 结束" + this.mSyncDataCurrentCategoryId);
                    MessageManager.getInstance().requestSyncDataFromWatch(CatagoryEnum.SENSOR_DATA_SLEEP_STATUS_ARRAY);
                    break;
                case CatagoryEnum.SENSOR_DATA_SLEEP_STATUS_ARRAY /* 90215 */:
                    LogUtil.d("数据同步From手表->SENSOR_DATA_SLEEP_STATUS_ARRAY 结束" + this.mSyncDataCurrentCategoryId);
                    MessageManager.getInstance().requestSyncDataFromWatch(CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_ARRAY);
                    break;
                case CatagoryEnum.SENSOR_DATA_DAILY_HRATE_ARRAY /* 90216 */:
                    LogUtil.d("数据同步From手表->SENSOR_DATA_DAILY_HRATE_ARRAY 结束" + this.mSyncDataCurrentCategoryId);
                    MessageManager.getInstance().requestSyncDataFromWatch(CatagoryEnum.SENSOR_DATA_DAILY_SPO_ARRAY);
                    break;
                case CatagoryEnum.SENSOR_DATA_DAILY_SPO_ARRAY /* 90217 */:
                    LogUtil.d("数据同步From手表->SENSOR_DATA_DAILY_SPO_ARRAY 结束" + this.mSyncDataCurrentCategoryId);
                    MessageManager.getInstance().requestSyncDataFromWatch(CatagoryEnum.SENSOR_DATA_DAILY_RHR_ARRAY);
                    break;
                case CatagoryEnum.SENSOR_DATA_DAILY_RHR_ARRAY /* 90218 */:
                    LogUtil.d("数据同步From手表->SENSOR_DATA_DAILY_RHR_ARRAY 整体结束" + this.mSyncDataCurrentCategoryId);
                    if (AppDataConfig.getInstance().getPairCode() >= 20) {
                        MessageManager.getInstance().requestSyncDataFromWatchSix(CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_SUM_V2);
                    } else {
                        this.mSyncDataStatus = SYNC_DATA_STATUS.SYNC_DATA_END;
                        SyncStatusCallback syncStatusCallback = this.mSyncStatusCallback;
                        if (syncStatusCallback != null) {
                            syncStatusCallback.onSyncFinish();
                        }
                    }
                    break;
            }
        }
        switch (response_no_data.enum_param) {
            case CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_SUM_V2 /* 90230 */:
                LogUtil.d("数据同步From手表->SENSOR_DATA_DAILY_ACTIVE_SUM_V2 结束" + this.mSyncDataCurrentCategoryId);
                MessageManager.getInstance().requestSyncDataFromWatchSix(CatagoryEnum.SENSOR_DATA_GENERAL_HEALTH);
                break;
            case CatagoryEnum.SENSOR_DATA_GENERAL_HEALTH /* 90231 */:
                LogUtil.d("数据同步From手表->SENSOR_DATA_GENERAL_HEALTH 结束" + this.mSyncDataCurrentCategoryId);
                MessageManager.getInstance().requestSyncDataFromWatchSix(CatagoryEnum.SENSOR_DATA_BLOOD_PRESSURE);
                break;
            case CatagoryEnum.SENSOR_DATA_BLOOD_PRESSURE /* 90232 */:
                LogUtil.d("数据同步From手表->SENSOR_DATA_BLOOD_PRESSURE 结束" + this.mSyncDataCurrentCategoryId);
                MessageManager.getInstance().requestSyncDataFromWatchSix(CatagoryEnum.SENSOR_DATA_TEMPERATURE);
                break;
            case CatagoryEnum.SENSOR_DATA_TEMPERATURE /* 90233 */:
                LogUtil.d("数据同步From手表->SENSOR_DATA_TEMPERATURE 结束" + this.mSyncDataCurrentCategoryId);
                MessageManager.getInstance().requestSyncDataFromWatchSix(CatagoryEnum.SENSOR_DATA_BLOOD_SUGAR);
                break;
            case CatagoryEnum.SENSOR_DATA_BLOOD_SUGAR /* 90234 */:
                LogUtil.d("数据同步From手表->SENSOR_DATA_BLOOD_SUGAR 结束" + this.mSyncDataCurrentCategoryId);
                this.mSyncDataStatus = SYNC_DATA_STATUS.SYNC_DATA_END;
                SyncStatusCallback syncStatusCallback2 = this.mSyncStatusCallback;
                if (syncStatusCallback2 != null) {
                    syncStatusCallback2.onSyncFinish();
                }
                break;
        }
    }

    @Override // cn.baos.watch.sdk.interfac.syncdata.SyncDataInterface
    public int getSyncStatus() {
        return this.mSyncDataStatus.ordinal();
    }

    @Override // cn.baos.watch.sdk.interfac.syncdata.SyncDataInterface
    public void startSyncRightNowData(Context context, int i, SyncRightNowDataCallback syncRightNowDataCallback) {
        this.mSyncRightNowDataCallback = syncRightNowDataCallback;
        int pairCode = AppDataConfig.getInstance().getPairCode();
        if (i == SYNC_RIGHT_NOW_DATA_CONTROL.OPEN.ordinal()) {
            if (pairCode >= 20) {
                LogUtil.e("同步实时数据开始V2");
                MessageManager.getInstance().requestSyncRightNowDataFromWatch(CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_SUM_V2, SYNC_RIGHT_NOW_DATA_CONTROL.OPEN);
            } else {
                LogUtil.d("同步实时数据开始");
                MessageManager.getInstance().requestSyncRightNowDataFromWatch(CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_SUM, SYNC_RIGHT_NOW_DATA_CONTROL.OPEN);
            }
            MessageManager.getInstance().requestSyncRightNowDataFromWatch(CatagoryEnum.SENSOR_DATA_DAILY_HRATE, SYNC_RIGHT_NOW_DATA_CONTROL.OPEN);
            MessageManager.getInstance().requestSyncRightNowDataFromWatch(CatagoryEnum.SENSOR_DATA_DAILY_SPO, SYNC_RIGHT_NOW_DATA_CONTROL.OPEN);
            return;
        }
        if (i == SYNC_RIGHT_NOW_DATA_CONTROL.CLOSE.ordinal()) {
            LogUtil.d("同步实时数据结束");
            if (pairCode >= 20) {
                MessageManager.getInstance().requestSyncRightNowDataFromWatch(CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_SUM_V2, SYNC_RIGHT_NOW_DATA_CONTROL.CLOSE);
            } else {
                MessageManager.getInstance().requestSyncRightNowDataFromWatch(CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_SUM, SYNC_RIGHT_NOW_DATA_CONTROL.CLOSE);
            }
            MessageManager.getInstance().requestSyncRightNowDataFromWatch(CatagoryEnum.SENSOR_DATA_DAILY_HRATE, SYNC_RIGHT_NOW_DATA_CONTROL.CLOSE);
            MessageManager.getInstance().requestSyncRightNowDataFromWatch(CatagoryEnum.SENSOR_DATA_DAILY_SPO, SYNC_RIGHT_NOW_DATA_CONTROL.CLOSE);
        }
    }

    public void onGetSyncRightNowDataFromWatch(Context context, Serializable serializable) {
        SyncRightNowDataCallback syncRightNowDataCallback;
        LogUtil.d("实时数据返回:" + W100Utils.toString(serializable));
        if (serializable.catagory == 90230) {
            LogUtil.e("实时数据返回:" + W100Utils.toString(serializable));
            if (this.mSyncRightNowDataCallback != null) {
                Sensor_data_daily_active_sum_v2 sensor_data_daily_active_sum_v2 = (Sensor_data_daily_active_sum_v2) serializable;
                Sensor_data_daily_active_sum sensor_data_daily_active_sum = new Sensor_data_daily_active_sum();
                sensor_data_daily_active_sum.sum_times = sensor_data_daily_active_sum_v2.sum_times;
                sensor_data_daily_active_sum.sum_step = sensor_data_daily_active_sum_v2.sum_step;
                sensor_data_daily_active_sum.sum_calorie = sensor_data_daily_active_sum_v2.sum_calorie;
                sensor_data_daily_active_sum.sum_distance_m = sensor_data_daily_active_sum_v2.sum_distance_m;
                sensor_data_daily_active_sum.update_timestamp = sensor_data_daily_active_sum_v2.update_timestamp;
                this.mSyncRightNowDataCallback.onDailyActiveSum(sensor_data_daily_active_sum);
                AppDataConfig.getInstance().put(SharePreferenceUtils.KEY_STEP_SUM, sensor_data_daily_active_sum.sum_step + "");
            }
        }
        if (serializable.catagory == 90223) {
            SyncRightNowDataCallback syncRightNowDataCallback2 = this.mSyncRightNowDataCallback;
            if (syncRightNowDataCallback2 != null) {
                Sensor_data_daily_active_sum sensor_data_daily_active_sum2 = (Sensor_data_daily_active_sum) serializable;
                syncRightNowDataCallback2.onDailyActiveSum(sensor_data_daily_active_sum2);
                AppDataConfig.getInstance().put(SharePreferenceUtils.KEY_STEP_SUM, sensor_data_daily_active_sum2.sum_step + "");
                return;
            }
            return;
        }
        if (serializable.catagory == 90224) {
            SyncRightNowDataCallback syncRightNowDataCallback3 = this.mSyncRightNowDataCallback;
            if (syncRightNowDataCallback3 != null) {
                syncRightNowDataCallback3.onDailyHrate((Sensor_data_daily_hrate) serializable);
                return;
            }
            return;
        }
        if (serializable.catagory != 90226 || (syncRightNowDataCallback = this.mSyncRightNowDataCallback) == null) {
            return;
        }
        syncRightNowDataCallback.onDailySpo((Sensor_data_daily_spo) serializable);
    }

    public int getSyncDataCurrentIndex() {
        return this.mSyncDataCurrentCategoryId;
    }

    public void setSyncDataCurrentCategoryId(int i) {
        this.mSyncDataCurrentCategoryId = i;
    }

    public int queryLastedTime(int i) {
        switch (i) {
            case CatagoryEnum.SPORT_RECORD_ARRAY /* 90210 */:
                return SportRecordFromWatchManager.getInstance().queryLatestTime();
            case CatagoryEnum.SENSOR_DATA_SPORT_MODE_ARRAY /* 90211 */:
                return SportModeManager.getInstance().queryLatestTime();
            case CatagoryEnum.SENSOR_DATA_SPORT_HRATE_ARRAY /* 90212 */:
                return SportHeartManager.getInstance().queryLatestTime();
            case CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_ARRAY /* 90213 */:
                return DailyActiveManager.getInstance().queryLatestTime();
            case CatagoryEnum.SENSOR_DATA_SLEEP_STATS_ARRAY /* 90214 */:
                return SleepStatsManager.getInstance().queryLatestTime();
            case CatagoryEnum.SENSOR_DATA_SLEEP_STATUS_ARRAY /* 90215 */:
                return SleepStatusManager.getInstance().queryLatestTime();
            case CatagoryEnum.SENSOR_DATA_DAILY_HRATE_ARRAY /* 90216 */:
                return DailyHrateManager.getInstance().queryLatestTime();
            case CatagoryEnum.SENSOR_DATA_DAILY_SPO_ARRAY /* 90217 */:
                return DailySpoManager.getInstance().queryLatestTime();
            case CatagoryEnum.SENSOR_DATA_DAILY_RHR_ARRAY /* 90218 */:
                return DailyRhrManager.getInstance().queryLatestTime();
            default:
                switch (i) {
                    case CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_SUM_V2 /* 90230 */:
                        return MetoManager.getInstance().queryLatestTime(1);
                    case CatagoryEnum.SENSOR_DATA_GENERAL_HEALTH /* 90231 */:
                        return RhManager.getInstance().queryLatestTime(1);
                    case CatagoryEnum.SENSOR_DATA_BLOOD_PRESSURE /* 90232 */:
                        return BpManager.getInstance().queryLatestTime(1);
                    case CatagoryEnum.SENSOR_DATA_TEMPERATURE /* 90233 */:
                        return TempManager.getInstance().queryLatestTime(1);
                    case CatagoryEnum.SENSOR_DATA_BLOOD_SUGAR /* 90234 */:
                        return BsManager.getInstance().queryLatestTime(1);
                    default:
                        return 1588057233;
                }
        }
    }
}
