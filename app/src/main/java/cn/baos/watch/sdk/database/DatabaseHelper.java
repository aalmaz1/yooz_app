package cn.baos.watch.sdk.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.core.app.NotificationCompat;
import cn.baos.watch.sdk.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String BP_COLUMN_HIGH = "high";
    public static final String BP_COLUMN_ID = "_id";
    public static final String BP_COLUMN_LOW = "low";
    public static final String BP_COLUMN_TIME = "time";
    public static final String BP_TABLE_NAME = "bpDateBase";
    public static final String BSUGAR_TABLE_NAME = "bsDateBase";
    public static final String BS_COLUMN_ID = "_id";
    public static final String BS_COLUMN_TIME = "time";
    public static final String BS_COLUMN_VALUE = "value";
    private static final String CLOCK_COLUMN_ID = "_id";
    private static final String CLOCK_COLUMN_IS_CHECKED = "isChecked";
    private static final String CLOCK_COLUMN_IS_SYNCHRONIZE_NETWORK = "isSynchronizeNetwork";
    private static final String CLOCK_COLUMN_POSITION = "position";
    private static final String CLOCK_COLUMN_TIME = "time";
    private static final String CLOCK_COLUMN_TIME_SLOT = "timeSlot";
    private static final String CLOCK_COLUMN_TIME_WHEN = "timeWhen";
    private static final String CLOCK_TABLE_NAME = "clockDateBase";
    public static final String COLUME_MAC = "mac";
    public static final String CONTACTS_COLUMN_ID = "_id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_NOTE = "note";
    public static final String CONTACTS_COLUMN_TEL = "tel";
    public static final String CONTACTS_COLUMN_TIME = "time";
    public static final String CONTACTS_TABLE_NAME = "contactsDateBase";
    public static final String CREATE_TABLE_BP = "CREATE TABLE bpDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,time INTEGER,high INTEGER,low INTEGER,mac TEXT);";
    public static final String CREATE_TABLE_BS = "CREATE TABLE bsDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,time INTEGER,value INTEGER,mac TEXT);";
    private static final String CREATE_TABLE_CLOCK = "CREATE TABLE clockDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,time TEXT,timeSlot TEXT,timeWhen TEXT,isChecked TEXT,isSynchronizeNetwork TEXT,position TEXT,mac TEXT);";
    public static final String CREATE_TABLE_CONTACTS = "CREATE TABLE contactsDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,time TEXT,name TEXT,note TEXT,tel TEXT);";
    private static final String CREATE_TABLE_DAILY_ACTIVE = "CREATE TABLE DailyActiveDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,sum_distance_m INTEGER,sum_step INTEGER,sum_calorie INTEGER,sum_times INTEGER,mac TEXT);";
    private static final String CREATE_TABLE_DAILY_ACTIVE_PHONE = "CREATE TABLE DailyActiveDatePhoneBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,sum_distance_m INTEGER,sum_step INTEGER,sum_calorie INTEGER,sum_times INTEGER,mac TEXT);";
    private static final String CREATE_TABLE_DAILY_HRATE = "CREATE TABLE DailyHrateDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,heartrate INTEGER,mac TEXT);";
    private static final String CREATE_TABLE_DAILY_RHR = "CREATE TABLE DailyRhrDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,rhr INTEGER,mac TEXT);";
    private static final String CREATE_TABLE_DAILY_SPO = "CREATE TABLE DailySpoDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,spo INTEGER,mac TEXT);";
    private static final String CREATE_TABLE_GPS_LOCATION = "CREATE TABLE GpsLocationDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,timeStamp INTEGER,lat TEXT,lon TEXT,source TEXT,mac TEXT);";
    public static final String CREATE_TABLE_METO = "CREATE TABLE metoDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,time INTEGER,sum_met INTEGER,mac TEXT);";
    private static final String CREATE_TABLE_NOTIFICATION = "CREATE TABLE notificationDateBases (_id INTEGER PRIMARY KEY AUTOINCREMENT,appPackageName TEXT,appName TEXT,isChecked TEXT,isSynchronizeNetwork TEXT,position TEXT,mac TEXT);";
    public static final String CREATE_TABLE_RH = "CREATE TABLE rhDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,time INTEGER,stress INTEGER,breathing_rate INTEGER,reserve1 INTEGER,reserve2 INTEGER,reserve3 INTEGER,reserve4 INTEGER,mac TEXT);";
    private static final String CREATE_TABLE_SLEEP_STATS = "CREATE TABLE SleepStatsDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,begin_timestamp INTEGER,end_timestamp INTEGER,total_sec INTEGER,light_sec INTEGER,deep_sec INTEGER,wakeup_sec INTEGER,eyesmove_sec INTEGER,mac TEXT);";
    private static final String CREATE_TABLE_SLEEP_STATUS = "CREATE TABLE SleepStatusDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,sleep_status INTEGER,mac TEXT);";
    private static final String CREATE_TABLE_SPORT_CALORIE = "CREATE TABLE sportCalorieDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,seqId INTEGER,timeStamp INTEGER,calorieEachHour INTEGER,mac TEXT);";
    private static final String CREATE_TABLE_SPORT_HRATE = "CREATE TABLE SportHrateDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,heartrate INTEGER,mac TEXT);";
    private static final String CREATE_TABLE_SPORT_MODE = "CREATE TABLE SportModeDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,longitude INTEGER,latitude INTEGER,avg_hrate INTEGER,max_hrate INTEGER,min_hrate INTEGER,cur_hrate INTEGER,avg_step_len_cm INTEGER,max_step_len_cm INTEGER,min_step_len_cm INTEGER,cur_step_len_cm INTEGER,avg_frequency_cpm INTEGER,max_frequency_cpm INTEGER,min_frequency_cpm INTEGER,cur_frequency_cpm INTEGER,avg_pace_s INTEGER,max_pace_s INTEGER,min_pace_s INTEGER,cur_pace_s INTEGER,sum_distance_m INTEGER,sum_action_count INTEGER,sum_calories INTEGER,sum_times_s INTEGER,mode INTEGER,status INTEGER,source INTEGER,mac TEXT);";
    private static final String CREATE_TABLE_SPORT_RECORD = "CREATE TABLE sportRecordDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,seqId INTEGER,timeStamp INTEGER,sportType INTEGER,distance INTEGER,stepNumber INTEGER,calories TEXT,curCalorie INTEGER,heartRates TEXT,duration INTEGER,avgFrequency INTEGER,maxFrequency INTEGER,avgPace INTEGER,maxPace INTEGER,avgSpeed INTEGER,maxSpeed INTEGER,locationPoints TEXT,mac TEXT);";
    private static final String CREATE_TABLE_SPORT_RECORD_FROM_WATCH = "CREATE TABLE SportRecordFromWatchDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,begin_timestamp INTEGER,end_timestamp INTEGER,timezone INTEGER,initiator INTEGER,mode INTEGER,status INTEGER,distance_m INTEGER,calories INTEGER,times_s INTEGER,distance_per_m INTEGER,times_per_s INTEGER,max_pace_s INTEGER,min_pace_s INTEGER,max_heartrate INTEGER,mac TEXT);";
    private static final String CREATE_TABLE_SPORT_STEP_NUMBER = "CREATE TABLE sportStepsDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,seqId INTEGER,timeStamp INTEGER,stepEachHour INTEGER,mac TEXT);";
    public static final String CREATE_TABLE_TEMP = "CREATE TABLE tempDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,time INTEGER,value INTEGER,mac TEXT);";
    private static final String DAILY_ACTIVE_COLUMN_ID = "_id";
    private static final String DAILY_ACTIVE_COLUMN_TIME = "timeStamp";
    private static final String DAILY_ACTIVE_COLUMN_sum_calorie = "sum_calorie";
    private static final String DAILY_ACTIVE_COLUMN_sum_distance_m = "sum_distance_m";
    private static final String DAILY_ACTIVE_COLUMN_sum_step = "sum_step";
    private static final String DAILY_ACTIVE_COLUMN_sum_times = "sum_times";
    private static final String DAILY_ACTIVE_DEVICE_ID = "deviceId";
    private static final String DAILY_ACTIVE_TABLE_NAME = "DailyActiveDateBase";
    private static final String DAILY_ACTIVE_TABLE_Phone_NAME = "DailyActiveDatePhoneBase";
    private static final String DAILY_ACTIVE_USER_ID = "userId";
    private static final String DAILY_DAILY_COLUMN_rhr = "rhr";
    private static final String DAILY_DAILY_COLUMN_spo = "spo";
    private static final String DAILY_HRATE_COLUMN_ID = "_id";
    private static final String DAILY_HRATE_COLUMN_TIME = "timeStamp";
    private static final String DAILY_HRATE_COLUMN_heartrate = "heartrate";
    private static final String DAILY_HRATE_DEVICE_ID = "deviceId";
    private static final String DAILY_HRATE_TABLE_NAME = "DailyHrateDateBase";
    private static final String DAILY_HRATE_USER_ID = "userId";
    private static final String DAILY_RHR_COLUMN_ID = "_id";
    private static final String DAILY_RHR_COLUMN_TIME = "timeStamp";
    private static final String DAILY_RHR_DEVICE_ID = "deviceId";
    private static final String DAILY_RHR_TABLE_NAME = "DailyRhrDateBase";
    private static final String DAILY_RHR_USER_ID = "userId";
    private static final String DAILY_SPO_COLUMN_ID = "_id";
    private static final String DAILY_SPO_COLUMN_TIME = "timeStamp";
    private static final String DAILY_SPO_DEVICE_ID = "deviceId";
    private static final String DAILY_SPO_TABLE_NAME = "DailySpoDateBase";
    private static final String DAILY_SPO_USER_ID = "userId";
    public static final String DATABASE_NAME = "localDateBase.db";
    private static final String GPS_LOCATION_COLUMN_ID = "_id";
    private static final String GPS_LOCATION_LAT = "lat";
    private static final String GPS_LOCATION_LON = "lon";
    private static final String GPS_LOCATION_SOURCE = "source";
    private static final String GPS_LOCATION_TABLE_NAME = "GpsLocationDateBase";
    private static final String GPS_LOCATION_TIME = "timeStamp";
    public static final String METO_COLUMN_ID = "_id";
    public static final String METO_COLUMN_MET = "sum_met";
    public static final String METO_COLUMN_TIME = "time";
    public static final String METO_TABLE_NAME = "metoDateBase";
    private static final String NOTIFICATION_COLUMN_APP_NAME = "appName";
    private static final String NOTIFICATION_COLUMN_APP_PACKAGE_NAME = "appPackageName";
    private static final String NOTIFICATION_COLUMN_ID = "_id";
    private static final String NOTIFICATION_COLUMN_IS_CHECKED = "isChecked";
    private static final String NOTIFICATION_COLUMN_IS_SYNCHRONIZE_NETWORK = "isSynchronizeNetwork";
    private static final String NOTIFICATION_COLUMN_POSITION = "position";
    private static final String NOTIFICATION_TABLE_NAME = "notificationDateBases";
    public static final String RH_COLUMN_BREATHING_RATE = "breathing_rate";
    public static final String RH_COLUMN_ID = "_id";
    public static final String RH_COLUMN_R1 = "reserve1";
    public static final String RH_COLUMN_R2 = "reserve2";
    public static final String RH_COLUMN_R3 = "reserve3";
    public static final String RH_COLUMN_R4 = "reserve4";
    public static final String RH_COLUMN_STRESS = "stress";
    public static final String RH_COLUMN_TIME = "time";
    public static final String RH_TABLE_NAME = "rhDateBase";
    private static final String SLEEP_STATS_COLUMN_ID = "_id";
    private static final String SLEEP_STATS_COLUMN_TIME = "timeStamp";
    private static final String SLEEP_STATS_COLUMN_begin_timestamp = "begin_timestamp";
    private static final String SLEEP_STATS_COLUMN_deep_sec = "deep_sec";
    private static final String SLEEP_STATS_COLUMN_end_timestamp = "end_timestamp";
    private static final String SLEEP_STATS_COLUMN_eyesmove_sec = "eyesmove_sec";
    private static final String SLEEP_STATS_COLUMN_light_sec = "light_sec";
    private static final String SLEEP_STATS_COLUMN_total_sec = "total_sec";
    private static final String SLEEP_STATS_COLUMN_wakeup_sec = "wakeup_sec";
    private static final String SLEEP_STATS_DEVICE_ID = "deviceId";
    private static final String SLEEP_STATS_TABLE_NAME = "SleepStatsDateBase";
    private static final String SLEEP_STATS_USER_ID = "userId";
    private static final String SLEEP_STATUS_COLUMN_ID = "_id";
    private static final String SLEEP_STATUS_COLUMN_TIME = "timeStamp";
    private static final String SLEEP_STATUS_COLUMN_sleep_status = "sleep_status";
    private static final String SLEEP_STATUS_DEVICE_ID = "deviceId";
    private static final String SLEEP_STATUS_TABLE_NAME = "SleepStatusDateBase";
    private static final String SLEEP_STATUS_USER_ID = "userId";
    private static final String SPORT_CALORIE_COLUMN_ID = "_id";
    private static final String SPORT_CALORIE_COLUMN_SEQ_ID = "seqId";
    private static final String SPORT_CALORIE_COLUMN_TIME = "timeStamp";
    private static final String SPORT_CALORIE_COLUMN_VALUE_EACH_HOUR = "calorieEachHour";
    private static final String SPORT_CALORIE_DEVICE_ID = "deviceId";
    private static final String SPORT_CALORIE_TABLE_NAME = "sportCalorieDateBase";
    private static final String SPORT_CALORIE_USER_ID = "userId";
    private static final String SPORT_HRATE_COLUMN_ID = "_id";
    private static final String SPORT_HRATE_COLUMN_TIME = "timeStamp";
    private static final String SPORT_HRATE_COLUMN_heartrate = "heartrate";
    private static final String SPORT_HRATE_DEVICE_ID = "deviceId";
    private static final String SPORT_HRATE_TABLE_NAME = "SportHrateDateBase";
    private static final String SPORT_HRATE_USER_ID = "userId";
    private static final String SPORT_MODE_COLUMN_ID = "_id";
    private static final String SPORT_MODE_COLUMN_TIME = "timeStamp";
    private static final String SPORT_MODE_COLUMN_avg_frequency_cpm = "avg_frequency_cpm";
    private static final String SPORT_MODE_COLUMN_avg_hrate = "avg_hrate";
    private static final String SPORT_MODE_COLUMN_avg_pace_s = "avg_pace_s";
    private static final String SPORT_MODE_COLUMN_avg_step_len_cm = "avg_step_len_cm";
    private static final String SPORT_MODE_COLUMN_cur_frequency_cpm = "cur_frequency_cpm";
    private static final String SPORT_MODE_COLUMN_cur_hrate = "cur_hrate";
    private static final String SPORT_MODE_COLUMN_cur_pace_s = "cur_pace_s";
    private static final String SPORT_MODE_COLUMN_cur_step_len_cm = "cur_step_len_cm";
    private static final String SPORT_MODE_COLUMN_latitude = "latitude";
    private static final String SPORT_MODE_COLUMN_longitude = "longitude";
    private static final String SPORT_MODE_COLUMN_max_frequency_cpm = "max_frequency_cpm";
    private static final String SPORT_MODE_COLUMN_max_hrate = "max_hrate";
    private static final String SPORT_MODE_COLUMN_max_pace_s = "max_pace_s";
    private static final String SPORT_MODE_COLUMN_max_step_len_cm = "max_step_len_cm";
    private static final String SPORT_MODE_COLUMN_min_frequency_cpm = "min_frequency_cpm";
    private static final String SPORT_MODE_COLUMN_min_hrate = "min_hrate";
    private static final String SPORT_MODE_COLUMN_min_pace_s = "min_pace_s";
    private static final String SPORT_MODE_COLUMN_min_step_len_cm = "min_step_len_cm";
    private static final String SPORT_MODE_COLUMN_mode = "mode";
    private static final String SPORT_MODE_COLUMN_source = "source";
    private static final String SPORT_MODE_COLUMN_status = "status";
    private static final String SPORT_MODE_COLUMN_sum_action_count = "sum_action_count";
    private static final String SPORT_MODE_COLUMN_sum_calories = "sum_calories";
    private static final String SPORT_MODE_COLUMN_sum_distance_m = "sum_distance_m";
    private static final String SPORT_MODE_COLUMN_sum_times_s = "sum_times_s";
    private static final String SPORT_MODE_DEVICE_ID = "deviceId";
    private static final String SPORT_MODE_TABLE_NAME = "SportModeDateBase";
    private static final String SPORT_MODE_USER_ID = "userId";
    private static final String SPORT_RECORD_COLUMN_ID = "_id";
    private static final String SPORT_RECORD_COLUMN_SEQ_ID = "seqId";
    private static final String SPORT_RECORD_COLUMN_SPORT_AVG_FREQUENCY = "avgFrequency";
    private static final String SPORT_RECORD_COLUMN_SPORT_AVG_PACE = "avgPace";
    private static final String SPORT_RECORD_COLUMN_SPORT_AVG_SPEED = "avgSpeed";
    private static final String SPORT_RECORD_COLUMN_SPORT_CALORIES = "calories";
    private static final String SPORT_RECORD_COLUMN_SPORT_CUR_CALORIES = "curCalorie";
    private static final String SPORT_RECORD_COLUMN_SPORT_DISTANCE = "distance";
    private static final String SPORT_RECORD_COLUMN_SPORT_DURATION = "duration";
    private static final String SPORT_RECORD_COLUMN_SPORT_HEART_RATES = "heartRates";
    private static final String SPORT_RECORD_COLUMN_SPORT_LOCATION_POINTS = "locationPoints";
    private static final String SPORT_RECORD_COLUMN_SPORT_MAX_FREQUENCY = "maxFrequency";
    private static final String SPORT_RECORD_COLUMN_SPORT_MAX_PACE = "maxPace";
    private static final String SPORT_RECORD_COLUMN_SPORT_MAX_SPEED = "maxSpeed";
    private static final String SPORT_RECORD_COLUMN_SPORT_STEP_NUMBER = "stepNumber";
    private static final String SPORT_RECORD_COLUMN_SPORT_TYPE = "sportType";
    private static final String SPORT_RECORD_COLUMN_TIME = "timeStamp";
    private static final String SPORT_RECORD_DEVICE_ID = "deviceId";
    private static final String SPORT_RECORD_FROM_WATCH_COLUMN_BEGIN_TIMESTAMP = "begin_timestamp";
    private static final String SPORT_RECORD_FROM_WATCH_COLUMN_END_TIMESTAMP = "end_timestamp";
    private static final String SPORT_RECORD_FROM_WATCH_COLUMN_ID = "_id";
    private static final String SPORT_RECORD_FROM_WATCH_COLUMN_INITIATOR = "initiator";
    private static final String SPORT_RECORD_FROM_WATCH_COLUMN_MODE = "mode";
    private static final String SPORT_RECORD_FROM_WATCH_COLUMN_SPORT_CALORIES = "calories";
    private static final String SPORT_RECORD_FROM_WATCH_COLUMN_SPORT_DISTANCE_M = "distance_m";
    private static final String SPORT_RECORD_FROM_WATCH_COLUMN_SPORT_DISTANCE_PER_M = "distance_per_m";
    private static final String SPORT_RECORD_FROM_WATCH_COLUMN_SPORT_MAX_HEARTRATE = "max_heartrate";
    private static final String SPORT_RECORD_FROM_WATCH_COLUMN_SPORT_MAX_PACE_S = "max_pace_s";
    private static final String SPORT_RECORD_FROM_WATCH_COLUMN_SPORT_MIN_PACE_S = "min_pace_s";
    private static final String SPORT_RECORD_FROM_WATCH_COLUMN_SPORT_TIMES_PER_S = "times_per_s";
    private static final String SPORT_RECORD_FROM_WATCH_COLUMN_SPORT_TIMES_S = "times_s";
    private static final String SPORT_RECORD_FROM_WATCH_COLUMN_STATUS = "status";
    private static final String SPORT_RECORD_FROM_WATCH_COLUMN_TIME = "timeStamp";
    private static final String SPORT_RECORD_FROM_WATCH_COLUMN_TIMEZONE = "timezone";
    private static final String SPORT_RECORD_FROM_WATCH_DEVICE_ID = "deviceId";
    private static final String SPORT_RECORD_FROM_WATCH_TABLE_NAME = "SportRecordFromWatchDateBase";
    private static final String SPORT_RECORD_FROM_WATCH_USER_ID = "userId";
    private static final String SPORT_RECORD_TABLE_NAME = "sportRecordDateBase";
    private static final String SPORT_RECORD_USER_ID = "userId";
    private static final String SPORT_STEP_NUMBER_COLUMN_ID = "_id";
    private static final String SPORT_STEP_NUMBER_COLUMN_SEQ_ID = "seqId";
    private static final String SPORT_STEP_NUMBER_COLUMN_STEP_NUMBER_EACH_HOUR = "stepEachHour";
    private static final String SPORT_STEP_NUMBER_COLUMN_TIME = "timeStamp";
    private static final String SPORT_STEP_NUMBER_DEVICE_ID = "deviceId";
    private static final String SPORT_STEP_NUMBER_TABLE_NAME = "sportStepsDateBase";
    private static final String SPORT_STEP_NUMBER_USER_ID = "userId";
    public static final String TEMP_COLUMN_ID = "_id";
    public static final String TEMP_COLUMN_TIME = "time";
    public static final String TEMP_COLUMN_VALUE = "value";
    public static final String TEMP_TABLE_NAME = "tempDateBase";

    public static String getClockColumnId() {
        return "_id";
    }

    public static String getClockColumnIsChecked() {
        return "isChecked";
    }

    public static String getClockColumnIsSynchronizeNetwork() {
        return "isSynchronizeNetwork";
    }

    public static String getClockColumnPosition() {
        return "position";
    }

    public static String getClockColumnTime() {
        return "time";
    }

    public static String getClockColumnTimeSlot() {
        return CLOCK_COLUMN_TIME_SLOT;
    }

    public static String getClockColumnTimeWhen() {
        return CLOCK_COLUMN_TIME_WHEN;
    }

    public static String getClockTableName() {
        return CLOCK_TABLE_NAME;
    }

    public static String getContactsName() {
        return "name";
    }

    public static String getContactsNote() {
        return CONTACTS_COLUMN_NOTE;
    }

    public static String getContactsTableName() {
        return CONTACTS_TABLE_NAME;
    }

    public static String getContactsTel() {
        return CONTACTS_COLUMN_TEL;
    }

    public static String getContactsTime() {
        return "time";
    }

    public static String getDAILY_ACTIVE_COLUMN_sum_calorie() {
        return DAILY_ACTIVE_COLUMN_sum_calorie;
    }

    public static String getDAILY_ACTIVE_COLUMN_sum_distance_m() {
        return "sum_distance_m";
    }

    public static String getDAILY_ACTIVE_COLUMN_sum_step() {
        return DAILY_ACTIVE_COLUMN_sum_step;
    }

    public static String getDAILY_ACTIVE_COLUMN_sum_times() {
        return DAILY_ACTIVE_COLUMN_sum_times;
    }

    public static String getDAILY_DAILY_COLUMN_rhr() {
        return DAILY_DAILY_COLUMN_rhr;
    }

    public static String getDAILY_DAILY_COLUMN_spo() {
        return DAILY_DAILY_COLUMN_spo;
    }

    public static String getDAILY_HRATE_COLUMN_heartrate() {
        return "heartrate";
    }

    public static String getDailyActiveColumnId() {
        return "_id";
    }

    public static String getDailyActiveColumnTime() {
        return "timeStamp";
    }

    public static String getDailyActiveDeviceId() {
        return "deviceId";
    }

    public static String getDailyActivePhoneTableName() {
        return DAILY_ACTIVE_TABLE_Phone_NAME;
    }

    public static String getDailyActiveTableName() {
        return DAILY_ACTIVE_TABLE_NAME;
    }

    public static String getDailyActiveUserId() {
        return "userId";
    }

    public static String getDailyHrateColumnId() {
        return "_id";
    }

    public static String getDailyHrateColumnTime() {
        return "timeStamp";
    }

    public static String getDailyHrateDeviceId() {
        return "deviceId";
    }

    public static String getDailyHrateTableName() {
        return DAILY_HRATE_TABLE_NAME;
    }

    public static String getDailyHrateUserId() {
        return "userId";
    }

    public static String getDailyRhrColumnId() {
        return "_id";
    }

    public static String getDailyRhrColumnTime() {
        return "timeStamp";
    }

    public static String getDailyRhrDeviceId() {
        return "deviceId";
    }

    public static String getDailyRhrTableName() {
        return DAILY_RHR_TABLE_NAME;
    }

    public static String getDailyRhrUserId() {
        return "userId";
    }

    public static String getDailySpoColumnId() {
        return "_id";
    }

    public static String getDailySpoColumnTime() {
        return "timeStamp";
    }

    public static String getDailySpoDeviceId() {
        return "deviceId";
    }

    public static String getDailySpoTableName() {
        return DAILY_SPO_TABLE_NAME;
    }

    public static String getDailySpoUserId() {
        return "userId";
    }

    public static String getGpsLocationColumnId() {
        return "_id";
    }

    public static String getGpsLocationLat() {
        return GPS_LOCATION_LAT;
    }

    public static String getGpsLocationLon() {
        return GPS_LOCATION_LON;
    }

    public static String getGpsLocationSource() {
        return "source";
    }

    public static String getGpsLocationTableName() {
        return GPS_LOCATION_TABLE_NAME;
    }

    public static String getGpsLocationTime() {
        return "timeStamp";
    }

    public static String getNotificationColumnAppName() {
        return NOTIFICATION_COLUMN_APP_NAME;
    }

    public static String getNotificationColumnAppPackageName() {
        return NOTIFICATION_COLUMN_APP_PACKAGE_NAME;
    }

    public static String getNotificationColumnId() {
        return "_id";
    }

    public static String getNotificationColumnIsChecked() {
        return "isChecked";
    }

    public static String getNotificationColumnIsSynchronizeNetwork() {
        return "isSynchronizeNetwork";
    }

    public static String getNotificationColumnPosition() {
        return "position";
    }

    public static String getNotificationTableName() {
        return NOTIFICATION_TABLE_NAME;
    }

    public static String getSLEEP_STATS_COLUMN_begin_timestamp() {
        return "begin_timestamp";
    }

    public static String getSLEEP_STATS_COLUMN_deep_sec() {
        return SLEEP_STATS_COLUMN_deep_sec;
    }

    public static String getSLEEP_STATS_COLUMN_end_timestamp() {
        return "end_timestamp";
    }

    public static String getSLEEP_STATS_COLUMN_eyesmove_sec() {
        return SLEEP_STATS_COLUMN_eyesmove_sec;
    }

    public static String getSLEEP_STATS_COLUMN_light_sec() {
        return SLEEP_STATS_COLUMN_light_sec;
    }

    public static String getSLEEP_STATS_COLUMN_total_sec() {
        return SLEEP_STATS_COLUMN_total_sec;
    }

    public static String getSLEEP_STATS_COLUMN_wakeup_sec() {
        return SLEEP_STATS_COLUMN_wakeup_sec;
    }

    public static String getSLEEP_STATUS_COLUMN_sleep_status() {
        return SLEEP_STATUS_COLUMN_sleep_status;
    }

    public static String getSPORT_HRATE_COLUMN_heartrate() {
        return "heartrate";
    }

    public static String getSPORT_MODE_COLUMN_avg_frequency_cpm() {
        return SPORT_MODE_COLUMN_avg_frequency_cpm;
    }

    public static String getSPORT_MODE_COLUMN_avg_hrate() {
        return SPORT_MODE_COLUMN_avg_hrate;
    }

    public static String getSPORT_MODE_COLUMN_avg_pace_s() {
        return SPORT_MODE_COLUMN_avg_pace_s;
    }

    public static String getSPORT_MODE_COLUMN_avg_step_len_cm() {
        return SPORT_MODE_COLUMN_avg_step_len_cm;
    }

    public static String getSPORT_MODE_COLUMN_cur_frequency_cpm() {
        return SPORT_MODE_COLUMN_cur_frequency_cpm;
    }

    public static String getSPORT_MODE_COLUMN_cur_hrate() {
        return SPORT_MODE_COLUMN_cur_hrate;
    }

    public static String getSPORT_MODE_COLUMN_cur_pace_s() {
        return SPORT_MODE_COLUMN_cur_pace_s;
    }

    public static String getSPORT_MODE_COLUMN_cur_step_len_cm() {
        return SPORT_MODE_COLUMN_cur_step_len_cm;
    }

    public static String getSPORT_MODE_COLUMN_latitude() {
        return SPORT_MODE_COLUMN_latitude;
    }

    public static String getSPORT_MODE_COLUMN_longitude() {
        return SPORT_MODE_COLUMN_longitude;
    }

    public static String getSPORT_MODE_COLUMN_max_frequency_cpm() {
        return SPORT_MODE_COLUMN_max_frequency_cpm;
    }

    public static String getSPORT_MODE_COLUMN_max_hrate() {
        return SPORT_MODE_COLUMN_max_hrate;
    }

    public static String getSPORT_MODE_COLUMN_max_pace_s() {
        return "max_pace_s";
    }

    public static String getSPORT_MODE_COLUMN_max_step_len_cm() {
        return SPORT_MODE_COLUMN_max_step_len_cm;
    }

    public static String getSPORT_MODE_COLUMN_min_frequency_cpm() {
        return SPORT_MODE_COLUMN_min_frequency_cpm;
    }

    public static String getSPORT_MODE_COLUMN_min_hrate() {
        return SPORT_MODE_COLUMN_min_hrate;
    }

    public static String getSPORT_MODE_COLUMN_min_pace_s() {
        return "min_pace_s";
    }

    public static String getSPORT_MODE_COLUMN_min_step_len_cm() {
        return SPORT_MODE_COLUMN_min_step_len_cm;
    }

    public static String getSPORT_MODE_COLUMN_mode() {
        return "mode";
    }

    public static String getSPORT_MODE_COLUMN_source() {
        return "source";
    }

    public static String getSPORT_MODE_COLUMN_status() {
        return NotificationCompat.CATEGORY_STATUS;
    }

    public static String getSPORT_MODE_COLUMN_sum_action_count() {
        return SPORT_MODE_COLUMN_sum_action_count;
    }

    public static String getSPORT_MODE_COLUMN_sum_calories() {
        return SPORT_MODE_COLUMN_sum_calories;
    }

    public static String getSPORT_MODE_COLUMN_sum_distance_m() {
        return "sum_distance_m";
    }

    public static String getSPORT_MODE_COLUMN_sum_times_s() {
        return SPORT_MODE_COLUMN_sum_times_s;
    }

    public static String getSleepStatsColumnId() {
        return "_id";
    }

    public static String getSleepStatsColumnTime() {
        return "timeStamp";
    }

    public static String getSleepStatsDeviceId() {
        return "deviceId";
    }

    public static String getSleepStatsTableName() {
        return SLEEP_STATS_TABLE_NAME;
    }

    public static String getSleepStatsUserId() {
        return "userId";
    }

    public static String getSleepStatusColumnId() {
        return "_id";
    }

    public static String getSleepStatusColumnTime() {
        return "timeStamp";
    }

    public static String getSleepStatusDeviceId() {
        return "deviceId";
    }

    public static String getSleepStatusTableName() {
        return SLEEP_STATUS_TABLE_NAME;
    }

    public static String getSleepStatusUserId() {
        return "userId";
    }

    public static String getSportCalorieColumnId() {
        return "_id";
    }

    public static String getSportCalorieColumnSeqId() {
        return "seqId";
    }

    public static String getSportCalorieColumnTime() {
        return "timeStamp";
    }

    public static String getSportCalorieColumnValueEachHour() {
        return SPORT_CALORIE_COLUMN_VALUE_EACH_HOUR;
    }

    public static String getSportCalorieDeviceId() {
        return "deviceId";
    }

    public static String getSportCalorieTableName() {
        return SPORT_CALORIE_TABLE_NAME;
    }

    public static String getSportCalorieUserId() {
        return "userId";
    }

    public static String getSportHrateColumnId() {
        return "_id";
    }

    public static String getSportHrateColumnTime() {
        return "timeStamp";
    }

    public static String getSportHrateDeviceId() {
        return "deviceId";
    }

    public static String getSportHrateTableName() {
        return SPORT_HRATE_TABLE_NAME;
    }

    public static String getSportHrateUserId() {
        return "userId";
    }

    public static String getSportModeColumnId() {
        return "_id";
    }

    public static String getSportModeColumnTime() {
        return "timeStamp";
    }

    public static String getSportModeDeviceId() {
        return "deviceId";
    }

    public static String getSportModeTableName() {
        return SPORT_MODE_TABLE_NAME;
    }

    public static String getSportModeUserId() {
        return "userId";
    }

    public static String getSportRecordColumnSeqId() {
        return "seqId";
    }

    public static String getSportRecordColumnSportAvgFrequency() {
        return SPORT_RECORD_COLUMN_SPORT_AVG_FREQUENCY;
    }

    public static String getSportRecordColumnSportAvgPace() {
        return SPORT_RECORD_COLUMN_SPORT_AVG_PACE;
    }

    public static String getSportRecordColumnSportAvgSpeed() {
        return SPORT_RECORD_COLUMN_SPORT_AVG_SPEED;
    }

    public static String getSportRecordColumnSportCalories() {
        return "calories";
    }

    public static String getSportRecordColumnSportCurCalories() {
        return SPORT_RECORD_COLUMN_SPORT_CUR_CALORIES;
    }

    public static String getSportRecordColumnSportDistance() {
        return SPORT_RECORD_COLUMN_SPORT_DISTANCE;
    }

    public static String getSportRecordColumnSportDuration() {
        return SPORT_RECORD_COLUMN_SPORT_DURATION;
    }

    public static String getSportRecordColumnSportHeartRates() {
        return SPORT_RECORD_COLUMN_SPORT_HEART_RATES;
    }

    public static String getSportRecordColumnSportLocationPoints() {
        return SPORT_RECORD_COLUMN_SPORT_LOCATION_POINTS;
    }

    public static String getSportRecordColumnSportMaxFrequency() {
        return SPORT_RECORD_COLUMN_SPORT_MAX_FREQUENCY;
    }

    public static String getSportRecordColumnSportMaxPace() {
        return SPORT_RECORD_COLUMN_SPORT_MAX_PACE;
    }

    public static String getSportRecordColumnSportMaxSpeed() {
        return SPORT_RECORD_COLUMN_SPORT_MAX_SPEED;
    }

    public static String getSportRecordColumnSportStepNumber() {
        return SPORT_RECORD_COLUMN_SPORT_STEP_NUMBER;
    }

    public static String getSportRecordColumnSportType() {
        return SPORT_RECORD_COLUMN_SPORT_TYPE;
    }

    public static String getSportRecordColumnTime() {
        return "timeStamp";
    }

    public static String getSportRecordDeviceId() {
        return "deviceId";
    }

    public static String getSportRecordFromWatchColumnBeginTimestamp() {
        return "begin_timestamp";
    }

    public static String getSportRecordFromWatchColumnEndTimestamp() {
        return "end_timestamp";
    }

    public static String getSportRecordFromWatchColumnInitiator() {
        return SPORT_RECORD_FROM_WATCH_COLUMN_INITIATOR;
    }

    public static String getSportRecordFromWatchColumnMode() {
        return "mode";
    }

    public static String getSportRecordFromWatchColumnSportCalories() {
        return "calories";
    }

    public static String getSportRecordFromWatchColumnSportDistanceM() {
        return SPORT_RECORD_FROM_WATCH_COLUMN_SPORT_DISTANCE_M;
    }

    public static String getSportRecordFromWatchColumnSportDistancePerM() {
        return SPORT_RECORD_FROM_WATCH_COLUMN_SPORT_DISTANCE_PER_M;
    }

    public static String getSportRecordFromWatchColumnSportMaxHeartrate() {
        return SPORT_RECORD_FROM_WATCH_COLUMN_SPORT_MAX_HEARTRATE;
    }

    public static String getSportRecordFromWatchColumnSportMaxPaceS() {
        return "max_pace_s";
    }

    public static String getSportRecordFromWatchColumnSportMinPaceS() {
        return "min_pace_s";
    }

    public static String getSportRecordFromWatchColumnSportTimesPerS() {
        return SPORT_RECORD_FROM_WATCH_COLUMN_SPORT_TIMES_PER_S;
    }

    public static String getSportRecordFromWatchColumnSportTimesS() {
        return SPORT_RECORD_FROM_WATCH_COLUMN_SPORT_TIMES_S;
    }

    public static String getSportRecordFromWatchColumnStatus() {
        return NotificationCompat.CATEGORY_STATUS;
    }

    public static String getSportRecordFromWatchColumnTime() {
        return "timeStamp";
    }

    public static String getSportRecordFromWatchColumnTimezone() {
        return SPORT_RECORD_FROM_WATCH_COLUMN_TIMEZONE;
    }

    public static String getSportRecordFromWatchDeviceId() {
        return "deviceId";
    }

    public static String getSportRecordFromWatchTableName() {
        return SPORT_RECORD_FROM_WATCH_TABLE_NAME;
    }

    public static String getSportRecordFromWatchUserId() {
        return "userId";
    }

    public static String getSportRecordTableName() {
        return SPORT_RECORD_TABLE_NAME;
    }

    public static String getSportRecordUserId() {
        return "userId";
    }

    public static String getSportStepNumberColumnSeqId() {
        return "seqId";
    }

    public static String getSportStepNumberColumnStepNumberEachHour() {
        return SPORT_STEP_NUMBER_COLUMN_STEP_NUMBER_EACH_HOUR;
    }

    public static String getSportStepNumberColumnTime() {
        return "timeStamp";
    }

    public static String getSportStepNumberDeviceId() {
        return "deviceId";
    }

    public static String getSportStepNumberTableName() {
        return SPORT_STEP_NUMBER_TABLE_NAME;
    }

    public static String getSportStepNumberUserId() {
        return "userId";
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 16);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(CREATE_TABLE_CLOCK);
        LogUtil.d("localDb->clock table onCreate be called:CREATE TABLE clockDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,time TEXT,timeSlot TEXT,timeWhen TEXT,isChecked TEXT,isSynchronizeNetwork TEXT,position TEXT,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_NOTIFICATION);
        LogUtil.d("localDb->notification table onCreate be called:CREATE TABLE notificationDateBases (_id INTEGER PRIMARY KEY AUTOINCREMENT,appPackageName TEXT,appName TEXT,isChecked TEXT,isSynchronizeNetwork TEXT,position TEXT,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_SPORT_STEP_NUMBER);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE sportStepsDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,seqId INTEGER,timeStamp INTEGER,stepEachHour INTEGER,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_SPORT_CALORIE);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE sportCalorieDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,seqId INTEGER,timeStamp INTEGER,calorieEachHour INTEGER,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_SPORT_RECORD);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE sportRecordDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,seqId INTEGER,timeStamp INTEGER,sportType INTEGER,distance INTEGER,stepNumber INTEGER,calories TEXT,curCalorie INTEGER,heartRates TEXT,duration INTEGER,avgFrequency INTEGER,maxFrequency INTEGER,avgPace INTEGER,maxPace INTEGER,avgSpeed INTEGER,maxSpeed INTEGER,locationPoints TEXT,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_SPORT_RECORD_FROM_WATCH);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE SportRecordFromWatchDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,begin_timestamp INTEGER,end_timestamp INTEGER,timezone INTEGER,initiator INTEGER,mode INTEGER,status INTEGER,distance_m INTEGER,calories INTEGER,times_s INTEGER,distance_per_m INTEGER,times_per_s INTEGER,max_pace_s INTEGER,min_pace_s INTEGER,max_heartrate INTEGER,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_SPORT_MODE);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE SportModeDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,longitude INTEGER,latitude INTEGER,avg_hrate INTEGER,max_hrate INTEGER,min_hrate INTEGER,cur_hrate INTEGER,avg_step_len_cm INTEGER,max_step_len_cm INTEGER,min_step_len_cm INTEGER,cur_step_len_cm INTEGER,avg_frequency_cpm INTEGER,max_frequency_cpm INTEGER,min_frequency_cpm INTEGER,cur_frequency_cpm INTEGER,avg_pace_s INTEGER,max_pace_s INTEGER,min_pace_s INTEGER,cur_pace_s INTEGER,sum_distance_m INTEGER,sum_action_count INTEGER,sum_calories INTEGER,sum_times_s INTEGER,mode INTEGER,status INTEGER,source INTEGER,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_SPORT_HRATE);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE SportHrateDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,heartrate INTEGER,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_SLEEP_STATS);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE SleepStatsDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,begin_timestamp INTEGER,end_timestamp INTEGER,total_sec INTEGER,light_sec INTEGER,deep_sec INTEGER,wakeup_sec INTEGER,eyesmove_sec INTEGER,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_SLEEP_STATUS);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE SleepStatusDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,sleep_status INTEGER,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_DAILY_ACTIVE);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE DailyActiveDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,sum_distance_m INTEGER,sum_step INTEGER,sum_calorie INTEGER,sum_times INTEGER,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_DAILY_ACTIVE_PHONE);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE DailyActiveDatePhoneBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,sum_distance_m INTEGER,sum_step INTEGER,sum_calorie INTEGER,sum_times INTEGER,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_DAILY_HRATE);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE DailyHrateDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,heartrate INTEGER,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_DAILY_SPO);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE DailySpoDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,spo INTEGER,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_DAILY_RHR);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE DailyRhrDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,userId INTEGER,deviceId TEXT,timeStamp INTEGER,rhr INTEGER,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_GPS_LOCATION);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE GpsLocationDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,timeStamp INTEGER,lat TEXT,lon TEXT,source TEXT,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_METO);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE metoDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,time INTEGER,sum_met INTEGER,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_RH);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE rhDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,time INTEGER,stress INTEGER,breathing_rate INTEGER,reserve1 INTEGER,reserve2 INTEGER,reserve3 INTEGER,reserve4 INTEGER,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_BP);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE bpDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,time INTEGER,high INTEGER,low INTEGER,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_TEMP);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE tempDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,time INTEGER,value INTEGER,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_BS);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE bsDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,time INTEGER,value INTEGER,mac TEXT);");
        sQLiteDatabase.execSQL(CREATE_TABLE_CONTACTS);
        LogUtil.d("localDb->sport step number table onCreate be called:CREATE TABLE contactsDateBase (_id INTEGER PRIMARY KEY AUTOINCREMENT,time TEXT,name TEXT,note TEXT,tel TEXT);");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        LogUtil.d("localDb->" + DatabaseHelper.class.getName() + " Upgrading database from version " + i + " to " + i2 + ", which will destroy all old data");
        sQLiteDatabase.execSQL("Drop table if exists clockDateBase");
        sQLiteDatabase.execSQL("Drop table if exists notificationDateBases");
        sQLiteDatabase.execSQL("Drop table if exists sportStepsDateBase");
        sQLiteDatabase.execSQL("Drop table if exists sportCalorieDateBase");
        sQLiteDatabase.execSQL("Drop table if exists sportRecordDateBase");
        sQLiteDatabase.execSQL("Drop table if exists SportRecordFromWatchDateBase");
        sQLiteDatabase.execSQL("Drop table if exists SportModeDateBase");
        sQLiteDatabase.execSQL("Drop table if exists SportHrateDateBase");
        sQLiteDatabase.execSQL("Drop table if exists SleepStatsDateBase");
        sQLiteDatabase.execSQL("Drop table if exists SleepStatusDateBase");
        sQLiteDatabase.execSQL("Drop table if exists DailyActiveDateBase");
        sQLiteDatabase.execSQL("Drop table if exists DailyHrateDateBase");
        sQLiteDatabase.execSQL("Drop table if exists DailySpoDateBase");
        sQLiteDatabase.execSQL("Drop table if exists DailyRhrDateBase");
        sQLiteDatabase.execSQL("Drop table if exists GpsLocationDateBase");
        sQLiteDatabase.execSQL("Drop table if exists metoDateBase");
        sQLiteDatabase.execSQL("Drop table if exists rhDateBase");
        sQLiteDatabase.execSQL("Drop table if exists bpDateBase");
        sQLiteDatabase.execSQL("Drop table if exists tempDateBase");
        sQLiteDatabase.execSQL("Drop table if exists bsDateBase");
        sQLiteDatabase.execSQL("Drop table if exists DailyActiveDatePhoneBase");
        sQLiteDatabase.execSQL("Drop table if exists contactsDateBase");
        onCreate(sQLiteDatabase);
    }
}
