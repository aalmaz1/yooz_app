package cn.baos.watch.sdk;

import android.app.Activity;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.util.Log;
import androidx.core.content.PermissionChecker;
import cn.baos.watch.sdk.base.AppDataConfig;
import cn.baos.watch.sdk.bluetooth.BleService;
import cn.baos.watch.sdk.database.contacts.ContactsManager;
import cn.baos.watch.sdk.database.fromwatch.sensordatadailyactive.DailyActiveEntity;
import cn.baos.watch.sdk.database.fromwatch.sensordatadailyhrate.DailyHrateEntity;
import cn.baos.watch.sdk.database.fromwatch.sensordatadailyrhr.DailyRhrEntity;
import cn.baos.watch.sdk.database.fromwatch.sensordatadailyspo.DailySpoEntity;
import cn.baos.watch.sdk.database.fromwatch.sensordatasleepstats.SleepStatsEntity;
import cn.baos.watch.sdk.database.fromwatch.sensordatasleepstatus.SleepStatusEntity;
import cn.baos.watch.sdk.database.fromwatch.sensordatasporthrate.SportHrateEntity;
import cn.baos.watch.sdk.database.fromwatch.sensordatasportmode.SportModeEntity;
import cn.baos.watch.sdk.database.fromwatch.sportrecord.SportRecordFromWatchEntity;
import cn.baos.watch.sdk.entitiy.ClockListEntity;
import cn.baos.watch.sdk.entitiy.Constant;
import cn.baos.watch.sdk.entitiy.ContactInfoEntity;
import cn.baos.watch.sdk.entitiy.NotificationAppListEntity;
import cn.baos.watch.sdk.entitiy.PrayerGpsEntity;
import cn.baos.watch.sdk.entitiy.PrayerTimeEntity;
import cn.baos.watch.sdk.entitiy.QrEntity;
import cn.baos.watch.sdk.entitiy.ReminderListEntity;
import cn.baos.watch.sdk.entitiy.WeatherEntity;
import cn.baos.watch.sdk.entitiy.WorldEntity;
import cn.baos.watch.sdk.huabaoImpl.clock.ClockManagerImpl;
import cn.baos.watch.sdk.huabaoImpl.reminder.ReminderManagerImpl;
import cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataImpl;
import cn.baos.watch.sdk.huabaoImpl.syncdata.dailyactive.DailyActiveManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.dailyactive.DailyActivePhoneManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.dailyhrate.DailyHrateManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.dailyrhr.DailyRhrManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.dailyspo.DailySpoManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sleepstats.SleepStatsManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sleepstatus.SleepStatusManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sporthrate.SportHeartManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sportmode.SportModeManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.sportrecord.SportRecordFromWatchManager;
import cn.baos.watch.sdk.huabaoImpl.translate.TranslateCallback;
import cn.baos.watch.sdk.huabaoImpl.translate.TranslateManager;
import cn.baos.watch.sdk.interfac.ble.BleStatusEnum;
import cn.baos.watch.sdk.interfac.ble.HbBtClientManager;
import cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback;
import cn.baos.watch.sdk.interfac.clock.OnCrudClockDataListener;
import cn.baos.watch.sdk.interfac.clock.OnGetClockDataListener;
import cn.baos.watch.sdk.interfac.contact.OnContactListener;
import cn.baos.watch.sdk.interfac.moslem.OnMoslemGpsListener;
import cn.baos.watch.sdk.interfac.moslem.OnMoslemListener;
import cn.baos.watch.sdk.interfac.moslem.OnQrImageListener;
import cn.baos.watch.sdk.interfac.reminder.OnCrudReminderDataListener;
import cn.baos.watch.sdk.interfac.reminder.OnGetReminderDataListener;
import cn.baos.watch.sdk.interfac.syncdata.SyncRightNowDataCallback;
import cn.baos.watch.sdk.interfac.syncdata.SyncStatusCallback;
import cn.baos.watch.sdk.interfac.watchbattery.OnWatchBatteryListener;
import cn.baos.watch.sdk.interfac.watchinfo.OnWatchInfoListener;
import cn.baos.watch.sdk.manager.TimeSyncCacheManager;
import cn.baos.watch.sdk.manager.api.BindStatusCallback;
import cn.baos.watch.sdk.manager.api.ConnectListener;
import cn.baos.watch.sdk.manager.api.DeviceCallBack;
import cn.baos.watch.sdk.manager.api.ScannerListener;
import cn.baos.watch.sdk.manager.api.SyncDataListener;
import cn.baos.watch.sdk.manager.message.IMessageCallback;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.manager.musiccontroller.MusicControlManager;
import cn.baos.watch.sdk.manager.notification.NotificationHuabaoManager;
import cn.baos.watch.sdk.manager.notification.db.NotificationDbManager;
import cn.baos.watch.sdk.manager.packageAlbumDial.PackageAlbumDialUtil;
import cn.baos.watch.sdk.manager.packageAlbumDial.WatchImageUtil;
import cn.baos.watch.sdk.util.DialPackageUtils;
import cn.baos.watch.sdk.util.LocalAudioPlayManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.LogcatThread;
import cn.baos.watch.sdk.util.W100Utils;
import cn.baos.watch.w100.messages.Action_sync;
import cn.baos.watch.w100.messages.Common_contact_info;
import cn.baos.watch.w100.messages.Device_base_info;
import cn.baos.watch.w100.messages.Device_resource_info;
import cn.baos.watch.w100.messages.Health_measure_config;
import cn.baos.watch.w100.messages.Menstrual_remind_config;
import cn.baos.watch.w100.messages.MessageBase;
import cn.baos.watch.w100.messages.Not_disturb_config;
import cn.baos.watch.w100.messages.QueryReminder;
import cn.baos.watch.w100.messages.Regular_remind_config;
import cn.baos.watch.w100.messages.Request_get_data;
import cn.baos.watch.w100.messages.Sedentary_monitor_config;
import cn.baos.watch.w100.messages.Sensor_data_daily_active_sum;
import cn.baos.watch.w100.messages.Sensor_data_daily_hrate;
import cn.baos.watch.w100.messages.Sensor_data_daily_spo;
import cn.baos.watch.w100.messages.Sensor_data_gps;
import cn.baos.watch.w100.messages.Sensor_data_gps_base;
import cn.baos.watch.w100.messages.Update_device_resource;
import cn.baos.watch.w100.messages.User_info_config;
import cn.baos.watch.w100.messages.Wallpaper_info;
import cn.baos.watch.w100.messages.World_clock_config;
import com.google.gson.Gson;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class BasSdk {
    private static Context mCtx;
    private static LogcatThread mLogcatThread;
    private static Sensor_data_daily_active_sum mSensor_data_daily_active_sum;
    private static Sensor_data_daily_hrate mSensor_data_daily_hrate;
    private static Sensor_data_daily_spo mSensor_data_daily_spo;
    private static List<ConnectListener> mConnectListeners = new ArrayList();
    private static List<ScannerListener> mScannerListeners = new ArrayList();
    private static List<DeviceCallBack> mDeviceCallBacks = new ArrayList();
    private static IBleClientSdkCallback iBleClientSdkCallback = new IBleClientSdkCallback() { // from class: cn.baos.watch.sdk.BasSdk.1
        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBLEManualDisConnected() {
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBLEStartConnect(String str) {
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBleDeviceStateChanged(boolean z) {
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBtNoDelDialog() {
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onGpsNotOpen() {
            for (ScannerListener scannerListener : BasSdk.mScannerListeners) {
                if (scannerListener != null) {
                    scannerListener.onGpsNotOpen();
                }
            }
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBLEStartScan() {
            for (ScannerListener scannerListener : BasSdk.mScannerListeners) {
                if (scannerListener != null) {
                    scannerListener.onBLEStartScan();
                }
            }
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBLEScanning(ScanResult scanResult) {
            for (ScannerListener scannerListener : BasSdk.mScannerListeners) {
                if (scannerListener != null) {
                    scannerListener.onScanning(scanResult);
                }
            }
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBLEConnecting(String str) {
            if (BasSdk.mConnectListeners.isEmpty()) {
                return;
            }
            Iterator it = BasSdk.mConnectListeners.iterator();
            while (it.hasNext()) {
                ((ConnectListener) it.next()).onBLEConnecting();
            }
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBLEConnected() {
            if (BasSdk.mConnectListeners.isEmpty()) {
                return;
            }
            Iterator it = BasSdk.mConnectListeners.iterator();
            while (it.hasNext()) {
                ((ConnectListener) it.next()).onBLEConnected();
            }
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBLEConnectFail() {
            if (BasSdk.mConnectListeners.isEmpty()) {
                return;
            }
            Iterator it = BasSdk.mConnectListeners.iterator();
            while (it.hasNext()) {
                ((ConnectListener) it.next()).onBLEConnectFail();
            }
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBLEDisConnected() {
            if (BasSdk.mConnectListeners.isEmpty()) {
                return;
            }
            Iterator it = BasSdk.mConnectListeners.iterator();
            while (it.hasNext()) {
                ((ConnectListener) it.next()).onBLEDisConnected();
            }
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBLEConnectTimeOut() {
            if (BasSdk.mConnectListeners.isEmpty()) {
                return;
            }
            Iterator it = BasSdk.mConnectListeners.iterator();
            while (it.hasNext()) {
                ((ConnectListener) it.next()).onBLEConnectFail();
            }
        }
    };
    private static IMessageCallback iMessageCallback = new IMessageCallback() { // from class: cn.baos.watch.sdk.BasSdk.2
        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onBindRequestByPhone() {
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onCollectWatchLoggerRequestData(String str) {
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onRequestGpsData() {
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onRequestMeteorologicalData() {
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onSyncMessageRequest(byte[] bArr) {
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onUserInfoConfig(User_info_config user_info_config) {
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onDeviceResourceInfo(Device_resource_info device_resource_info) {
            Iterator it = BasSdk.mDeviceCallBacks.iterator();
            while (it.hasNext()) {
                ((DeviceCallBack) it.next()).onDeviceResourceInfo(device_resource_info);
            }
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onRequestWeather() {
            Iterator it = BasSdk.mDeviceCallBacks.iterator();
            while (it.hasNext()) {
                ((DeviceCallBack) it.next()).onRequestWeather();
            }
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onCollectWatchLoggerRequest(byte[] bArr) {
            Iterator it = BasSdk.mDeviceCallBacks.iterator();
            while (it.hasNext()) {
                ((DeviceCallBack) it.next()).onCollectWatchLoggerRequest(bArr);
            }
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onActionSync(Action_sync action_sync) {
            Iterator it = BasSdk.mDeviceCallBacks.iterator();
            while (it.hasNext()) {
                ((DeviceCallBack) it.next()).onActionSync(action_sync);
            }
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onRequestGetData(Request_get_data request_get_data) {
            Iterator it = BasSdk.mDeviceCallBacks.iterator();
            while (it.hasNext()) {
                ((DeviceCallBack) it.next()).onRequestGetData(request_get_data);
            }
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onPhoneStatus(int i) {
            Iterator it = BasSdk.mDeviceCallBacks.iterator();
            while (it.hasNext()) {
                ((DeviceCallBack) it.next()).onPhoneStatus(i);
            }
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void requestGetTime() {
            Iterator it = BasSdk.mDeviceCallBacks.iterator();
            while (it.hasNext()) {
                ((DeviceCallBack) it.next()).onRequestTime();
            }
        }
    };

    public static boolean bindDevice(String str, BindStatusCallback bindStatusCallback) {
        return true;
    }

    public static void registerKeepLive(Context context) {
    }

    public static void initSdk(Context context) {
        mCtx = context;
        mLogcatThread = new LogcatThread(mCtx);
        SportRecordFromWatchManager.getInstance().setContext(context);
        SportModeManager.getInstance().setContext(context);
        SportHeartManager.getInstance().setContext(context);
        SleepStatsManager.getInstance().setContext(context);
        SleepStatusManager.getInstance().setContext(context);
        DailyActiveManager.getInstance().setContext(context);
        DailyActivePhoneManager.getInstance().setContext(context);
        DailyHrateManager.getInstance().setContext(context);
        DailySpoManager.getInstance().setContext(context);
        DailyRhrManager.getInstance().setContext(context);
        ContactsManager.getInstance().setContext(context);
        LocalAudioPlayManager.getInstance().setContext(context);
        MusicControlManager.getInstance().setContext(mCtx);
        HbBtClientManager.getInstance().init(mCtx);
        BleService.getInstance().setIBleClientSdkCallback(iBleClientSdkCallback);
        MessageManager.getInstance().setContext(mCtx);
        MessageManager.getInstance().setMessageCallback(iMessageCallback);
    }

    public static void startScan() {
        BleService.getInstance().startConnect();
    }

    public static void addScannerListener(ScannerListener scannerListener) {
        if (scannerListener != null) {
            mScannerListeners.add(scannerListener);
        }
    }

    public static void removeScannerListener(ScannerListener scannerListener) {
        if (scannerListener != null) {
            mScannerListeners.remove(scannerListener);
        }
    }

    public static void stopScan() {
        BleService.getInstance().stopScan();
    }

    public static void addConnectListener(ConnectListener connectListener) {
        if (connectListener != null) {
            mConnectListeners.add(connectListener);
        }
    }

    public static void removeConnectListener(ConnectListener connectListener) {
        if (connectListener != null) {
            mConnectListeners.remove(connectListener);
        }
    }

    public static void addDeviceCallBack(DeviceCallBack deviceCallBack) {
        if (deviceCallBack != null) {
            mDeviceCallBacks.add(deviceCallBack);
        }
    }

    public static void removeDeviceCallBack(DeviceCallBack deviceCallBack) {
        if (deviceCallBack != null) {
            mDeviceCallBacks.remove(deviceCallBack);
        }
    }

    public static void connectDevice(String str) {
        HbBtClientManager.getInstance().startConnect(str, false);
    }

    public static void disConnectDevice() {
        BleService.getInstance().disconnect();
    }

    public static void unBindDevice(String str) {
        BleService.getInstance().unBindWatch();
    }

    public static boolean getWatchInfo(OnWatchInfoListener onWatchInfoListener) {
        return MessageManager.getInstance().getWatchInfo(onWatchInfoListener);
    }

    public static boolean getBatteryInfo(OnWatchBatteryListener onWatchBatteryListener) {
        return MessageManager.getInstance().getWatchBattery(onWatchBatteryListener);
    }

    public static boolean setSedentaryReminder(Sedentary_monitor_config sedentary_monitor_config) {
        LogUtil.d("久坐提醒:" + new Gson().toJson(sedentary_monitor_config));
        return MessageManager.getInstance().sendMessage(sedentary_monitor_config);
    }

    public static boolean setRegularRemind(Regular_remind_config regular_remind_config) {
        LogUtil.d("常规提醒:喝水、洗手:" + new Gson().toJson(regular_remind_config));
        return MessageManager.getInstance().sendMessage(regular_remind_config);
    }

    public static boolean setMenstrualRemind(Menstrual_remind_config menstrual_remind_config) {
        LogUtil.d("女性健康:" + new Gson().toJson(menstrual_remind_config));
        return MessageManager.getInstance().sendMessage(menstrual_remind_config);
    }

    public static boolean setNotDisturb(Not_disturb_config not_disturb_config) {
        LogUtil.d("勿扰模式:" + new Gson().toJson(not_disturb_config));
        return MessageManager.getInstance().sendMessage(not_disturb_config);
    }

    public static boolean setHealthMeasureConfig(Health_measure_config health_measure_config) {
        LogUtil.d("心率检测:" + new Gson().toJson(health_measure_config));
        return MessageManager.getInstance().sendMessage(health_measure_config);
    }

    public static boolean setWeather(WeatherEntity weatherEntity) {
        weatherEntity.setCode("0");
        if (weatherEntity == null || weatherEntity.getCode() == null || !weatherEntity.getCode().equals("0")) {
            return false;
        }
        return MessageManager.getInstance().sendWeatherInfoToWatch(weatherEntity);
    }

    public static boolean setRaiseLightUpSwitch(boolean z) {
        return MessageManager.getInstance().setSwitchRaiseWristLightUp(z);
    }

    public static boolean setUserInfo(User_info_config user_info_config) {
        LogUtil.d("个人信息修改-发送给手表:" + new Gson().toJson(user_info_config));
        return MessageManager.getInstance().sendMessage(user_info_config);
    }

    public static void setTime(long j, int i) {
        TimeSyncCacheManager.getInstance().timeSyncToWatch(j, i);
    }

    public static void responseTimeModify() {
        Action_sync action_sync = new Action_sync();
        action_sync.action_type = 8;
        MessageManager.getInstance().sendMessage(action_sync);
    }

    public static void responseTimeZoneModify() {
        Action_sync action_sync = new Action_sync();
        action_sync.action_type = 7;
        MessageManager.getInstance().sendMessage(action_sync);
    }

    public static boolean setTimeFormat(int i) {
        Action_sync action_sync = new Action_sync();
        action_sync.action_type = 9;
        if (i == 0) {
            action_sync.action_param = 12;
        } else {
            action_sync.action_param = 24;
        }
        return MessageManager.getInstance().sendMessage(action_sync);
    }

    public static boolean sendSensorGpsData(Sensor_data_gps sensor_data_gps) {
        return MessageManager.getInstance().sendMessage((MessageBase) sensor_data_gps);
    }

    public static void addReminder(ReminderListEntity reminderListEntity, OnCrudReminderDataListener onCrudReminderDataListener) {
        ReminderManagerImpl.getInstance().addReminder(reminderListEntity, onCrudReminderDataListener);
    }

    public static void deleteReminder(ReminderListEntity reminderListEntity, OnCrudReminderDataListener onCrudReminderDataListener) {
        ReminderManagerImpl.getInstance().deleteReminder(reminderListEntity, onCrudReminderDataListener);
    }

    public static void updateReminder(ReminderListEntity reminderListEntity, OnCrudReminderDataListener onCrudReminderDataListener) {
        ReminderManagerImpl.getInstance().updateReminder(reminderListEntity, onCrudReminderDataListener);
    }

    public static void queryReminder(QueryReminder queryReminder, OnGetReminderDataListener onGetReminderDataListener) {
        ReminderManagerImpl.getInstance().requestReminderListToWatch(queryReminder, onGetReminderDataListener);
    }

    public static void addAlarm(ClockListEntity clockListEntity, OnCrudClockDataListener onCrudClockDataListener) {
        ClockManagerImpl.getInstance().addAlarm(clockListEntity, onCrudClockDataListener);
    }

    public static void deleteAlarm(ClockListEntity clockListEntity, OnCrudClockDataListener onCrudClockDataListener) {
        ClockManagerImpl.getInstance().deleteAlarm(clockListEntity, onCrudClockDataListener);
    }

    public static void updateAlarm(ClockListEntity clockListEntity, OnCrudClockDataListener onCrudClockDataListener) {
        ClockManagerImpl.getInstance().updateAlarm(clockListEntity, onCrudClockDataListener);
    }

    public static void queryAlarm(OnGetClockDataListener onGetClockDataListener) {
        ClockManagerImpl.getInstance().requestClockListToWatch(onGetClockDataListener);
    }

    public static void setLogEnable(boolean z) {
        if (z) {
            mLogcatThread.startThread();
        } else {
            mLogcatThread.endThread();
        }
    }

    public static void setLogDir(String str) {
        mLogcatThread.setLogDir(str);
    }

    public static Sensor_data_daily_active_sum getRealTimeDailyActiveData() {
        return mSensor_data_daily_active_sum;
    }

    public static Sensor_data_daily_hrate getRealTimeHeartRateData() {
        return mSensor_data_daily_hrate;
    }

    public static Sensor_data_daily_spo getRealTimeDailySpoData() {
        return mSensor_data_daily_spo;
    }

    public static boolean syncData(final SyncDataListener syncDataListener) {
        LogUtil.d("数据同步");
        if (HbBtClientManager.getInstance().getCurrentStatus() != BleStatusEnum.HB_BLE_BOND) {
            LogUtil.d("蓝牙未连接，同步数据失败");
            return false;
        }
        if (syncDataListener != null) {
            syncDataListener.onSyncStart();
        }
        SyncDataImpl.getInstance().startSyncRightNowData(mCtx, 0, new SyncRightNowDataCallback() { // from class: cn.baos.watch.sdk.BasSdk.3
            @Override // cn.baos.watch.sdk.interfac.syncdata.SyncRightNowDataCallback
            public void onDailyActiveSum(Sensor_data_daily_active_sum sensor_data_daily_active_sum) {
                BasSdk.mSensor_data_daily_active_sum = sensor_data_daily_active_sum;
            }

            @Override // cn.baos.watch.sdk.interfac.syncdata.SyncRightNowDataCallback
            public void onDailyHrate(Sensor_data_daily_hrate sensor_data_daily_hrate) {
                BasSdk.mSensor_data_daily_hrate = sensor_data_daily_hrate;
            }

            @Override // cn.baos.watch.sdk.interfac.syncdata.SyncRightNowDataCallback
            public void onDailySpo(Sensor_data_daily_spo sensor_data_daily_spo) {
                BasSdk.mSensor_data_daily_spo = sensor_data_daily_spo;
            }
        });
        SyncDataImpl.getInstance().startSyncDateFromWatch(mCtx, new SyncStatusCallback() { // from class: cn.baos.watch.sdk.BasSdk.4
            @Override // cn.baos.watch.sdk.interfac.syncdata.SyncStatusCallback
            public void onStartSync() {
                LogUtil.d("数据同步开始");
            }

            @Override // cn.baos.watch.sdk.interfac.syncdata.SyncStatusCallback
            public void onSyncFinish() {
                LogUtil.d("数据同步结束");
                SyncDataListener syncDataListener2 = syncDataListener;
                if (syncDataListener2 != null) {
                    syncDataListener2.onSyncFinish();
                }
            }
        });
        return true;
    }

    public static ArrayList<DailyActiveEntity> queryDailyActiveSumData(Date date, Date date2) {
        return DailyActiveManager.getInstance().queryDailyActiveInInterval((int) (date.getTime() / 1000), (int) (date2.getTime() / 1000));
    }

    public static ArrayList<DailyHrateEntity> queryDailyHeartRateData(Date date, Date date2) {
        return DailyHrateManager.getInstance().queryDailyHrateInInterval((int) (date.getTime() / 1000), (int) (date2.getTime() / 1000));
    }

    public static ArrayList<SleepStatsEntity> queryDailySleepSumData(Date date, Date date2) {
        return SleepStatsManager.getInstance().querySleepStatsInInterval((int) (date.getTime() / 1000), (int) (date2.getTime() / 1000));
    }

    public static ArrayList<SportRecordFromWatchEntity> querySportData(Date date, Date date2) {
        return SportRecordFromWatchManager.getInstance().querySportRecordFromWatchInInterval((int) (date.getTime() / 1000), (int) (date2.getTime() / 1000));
    }

    public static ArrayList<DailyRhrEntity> queryDailyResetHeartRateData(Date date, Date date2) {
        return DailyRhrManager.getInstance().queryDailyRhrInInterval((int) (date.getTime() / 1000), (int) (date2.getTime() / 1000));
    }

    public static ArrayList<DailySpoEntity> queryDailyBloodOxygenData(Date date, Date date2) {
        return DailySpoManager.getInstance().queryDailySpoInInterval((int) (date.getTime() / 1000), (int) (date2.getTime() / 1000));
    }

    public static ArrayList<SportModeEntity> querySportModeData(Date date, Date date2) {
        return SportModeManager.getInstance().querySportModeInInterval((int) (date.getTime() / 1000), (int) (date2.getTime() / 1000));
    }

    public static ArrayList<SportHrateEntity> querySportHeartRateData(Date date, Date date2) {
        return SportHeartManager.getInstance().querySportHrateInInterval((int) (date.getTime() / 1000), (int) (date2.getTime() / 1000));
    }

    public static ArrayList<SleepStatusEntity> querySleepStatusData(Date date, Date date2) {
        return SleepStatusManager.getInstance().querySleepStatusInInterval((int) (date.getTime() / 1000), (int) (date2.getTime() / 1000));
    }

    public static void translateFile(File file, int i, TranslateCallback translateCallback) {
        TranslateManager.getInstance().transferFile(file, i, translateCallback);
    }

    public static boolean updateDeviceResource(Update_device_resource update_device_resource) {
        LogUtil.d("修改表盘对象json打印:" + new Gson().toJson(update_device_resource));
        return MessageManager.getInstance().sendMessage((MessageBase) update_device_resource);
    }

    public static String generateWallBin(Device_base_info device_base_info, String str, boolean z, int i, int i2, int i3, int i4, int i5) throws Throwable {
        Wallpaper_info wallpaper_info = new Wallpaper_info();
        Wallpaper_info.Control_color control_color = new Wallpaper_info.Control_color();
        control_color.alpha = Color.alpha(i);
        control_color.red = Color.red(i);
        control_color.green = Color.green(i);
        control_color.blue = Color.blue(i);
        wallpaper_info.bgColor = control_color;
        wallpaper_info.controls = new Wallpaper_info.Control_info[2];
        Wallpaper_info.Control_info control_info = new Wallpaper_info.Control_info();
        control_info.id = 1;
        Wallpaper_info.Control_info control_info2 = new Wallpaper_info.Control_info();
        control_info2.id = 2;
        wallpaper_info.controls[0] = control_info;
        wallpaper_info.controls[1] = control_info2;
        Wallpaper_info.Control_color control_color2 = new Wallpaper_info.Control_color();
        control_info.text_color = control_color2;
        control_info2.text_color = control_color2;
        control_color2.alpha = Color.alpha(i2);
        control_color2.red = Color.red(i2);
        control_color2.green = Color.green(i2);
        control_color2.blue = Color.blue(i2);
        if (z) {
            control_info.visible = 1;
            control_info2.visible = 1;
        } else {
            control_info.visible = 0;
            control_info2.visible = 0;
        }
        if (i3 == 0) {
            control_info.left = 18;
            control_info2.left = 18;
            control_info.top = 20;
            control_info2.top = 65;
        } else if (i3 == 1) {
            control_info.left = 18;
            control_info2.left = 18;
            control_info.top = 90;
            control_info2.top = 135;
        } else {
            control_info.left = 18;
            control_info2.left = 18;
            control_info.top = 172;
            control_info2.top = 217;
        }
        LogUtil.d("表盘json实体结构:" + new Gson().toJson(wallpaper_info));
        WatchImageUtil watchImageUtil = new WatchImageUtil();
        byte[] bArrPackageHeader = DialPackageUtils.packageHeader();
        LogUtil.d("打包表盘参数源生端-表盘json:" + new Gson().toJson(wallpaper_info));
        byte[] bArrPackMessage = MessageManager.packMessage(wallpaper_info);
        LogUtil.d("打包表盘参数源生端-表盘json-unpack:" + W100Utils.toString((Wallpaper_info) MessageManager.unpackMessage(bArrPackMessage)));
        String strCompressImage240280 = watchImageUtil.compressImage240280(mCtx, str, i4, i5);
        String strCompressImage152176 = watchImageUtil.compressImage152176(mCtx, watchImageUtil.drawControlOnCompressed(mCtx, strCompressImage240280, wallpaper_info, i4, i5));
        byte[] bArrPackageLayoutMagic = DialPackageUtils.packageLayoutMagic();
        LogUtil.d("打包表盘参数源生端，header:" + W100Utils.bytesToHexString(bArrPackageHeader) + " 文件地址:" + strCompressImage240280 + " 预览文件地址:" + strCompressImage152176 + " layoutMagic:" + W100Utils.bytesToHexString(bArrPackageLayoutMagic) + " 手表信息:" + new Gson().toJson(device_base_info));
        PackageAlbumDialUtil packageAlbumDialUtil = new PackageAlbumDialUtil();
        return packageAlbumDialUtil.saveAlbumByteLocal(mCtx, device_base_info, packageAlbumDialUtil.packageAlbumDial(bArrPackageHeader, strCompressImage152176, strCompressImage240280, bArrPackageLayoutMagic, bArrPackMessage));
    }

    public static boolean findMobile(boolean z) {
        AppDataConfig.getInstance().put(Constant.KEY_WATCH_FIND_PHONE_SWITCH, z);
        return true;
    }

    public static boolean musicControl(boolean z, Activity activity) {
        boolean zRequestRebindNotificationService = true;
        if (z) {
            try {
                zRequestRebindNotificationService = NotificationHuabaoManager.getInstance().requestRebindNotificationService(activity);
            } catch (Exception e) {
                LogUtil.d("musicControl失败，isOpen=" + z + ",异常：" + e.getMessage());
            }
        }
        if (zRequestRebindNotificationService) {
            AppDataConfig.getInstance().put(Constant.KEY_WATCH_MUSIC_SWITCH, z);
        }
        return zRequestRebindNotificationService;
    }

    public static boolean setAllMessageNotifyEnable(boolean z, Activity activity) {
        boolean zRequestRebindNotificationService = z ? NotificationHuabaoManager.getInstance().requestRebindNotificationService(activity) : true;
        if (zRequestRebindNotificationService) {
            NotificationDbManager.getInstance(mCtx).saveCheckStateLightDb("notificationManageKey", z);
            LogUtil.d("消息通知->设置总开关状态:" + z);
            NotificationDbManager.getInstance(mCtx).updateAllNotifications(z);
        }
        return zRequestRebindNotificationService;
    }

    public static ArrayList<NotificationAppListEntity> getNotificationAppList() {
        ArrayList<NotificationAppListEntity> allNotification = NotificationDbManager.getInstance(mCtx).getAllNotification();
        LogUtil.d("消息通知->获取所有app列表:" + W100Utils.toString(allNotification));
        return allNotification;
    }

    public static boolean setOneMessageNotifyEnable(NotificationAppListEntity notificationAppListEntity) {
        NotificationDbManager.getInstance(mCtx).updateNotification(notificationAppListEntity);
        return true;
    }

    public static boolean addOneMessageNotify(NotificationAppListEntity notificationAppListEntity) {
        NotificationDbManager.getInstance(mCtx).insertNotification(notificationAppListEntity);
        return true;
    }

    public static boolean setPrayerTime(PrayerTimeEntity prayerTimeEntity, OnMoslemListener onMoslemListener) {
        return MessageManager.getInstance().setPrayerTime(prayerTimeEntity, onMoslemListener);
    }

    public static boolean setPrayerGps(PrayerGpsEntity prayerGpsEntity, OnMoslemGpsListener onMoslemGpsListener) {
        return MessageManager.getInstance().setSensorDataGps(prayerGpsEntity, onMoslemGpsListener);
    }

    public static boolean getContactInfo(OnContactListener onContactListener) {
        return MessageManager.getInstance().getContactInfo(onContactListener);
    }

    public static boolean setSensorDataGps(Sensor_data_gps_base sensor_data_gps_base) {
        LogUtil.d("设置经纬度 Sensor_data_gps_base:" + new Gson().toJson(sensor_data_gps_base));
        return MessageManager.getInstance().sendMessage((MessageBase) sensor_data_gps_base);
    }

    public static boolean setContactInfo(List<ContactInfoEntity> list) {
        Common_contact_info.Contact_info[] contact_infoArr;
        Common_contact_info common_contact_info = new Common_contact_info();
        int i = 0;
        if (list == null || list.size() <= 0) {
            contact_infoArr = new Common_contact_info.Contact_info[]{new Common_contact_info.Contact_info()};
        } else {
            contact_infoArr = new Common_contact_info.Contact_info[list.size() + 1];
            while (i < list.size()) {
                if (i == 0) {
                    contact_infoArr[i] = new Common_contact_info.Contact_info();
                }
                Common_contact_info.Contact_info contact_info = new Common_contact_info.Contact_info();
                contact_info.name = list.get(i).name;
                contact_info.flag = list.get(i).flag;
                contact_info.number = list.get(i).number;
                i++;
                contact_infoArr[i] = contact_info;
            }
        }
        common_contact_info.contacts = contact_infoArr;
        Log.d("=commonContactInfo=", new Gson().toJson(common_contact_info));
        return MessageManager.getInstance().setContactInfo(common_contact_info);
    }

    public static boolean setQrImages(QrEntity qrEntity, OnQrImageListener onQrImageListener) {
        return MessageManager.getInstance().setQrImages(qrEntity, onQrImageListener);
    }

    public static boolean setWorldTime(List<WorldEntity> list) {
        World_clock_config world_clock_config = new World_clock_config();
        if (list != null && list.size() > 0) {
            World_clock_config.World_clock[] world_clockArr = new World_clock_config.World_clock[list.size()];
            for (int i = 0; i < list.size(); i++) {
                World_clock_config.World_clock world_clock = new World_clock_config.World_clock();
                world_clock.name = list.get(i).cityCn;
                world_clock.timezone = list.get(i).secondsFromGMT.intValue();
                world_clock.reserve = list.get(i).reserve;
                world_clockArr[i] = world_clock;
            }
            world_clock_config.clocks = world_clockArr;
        }
        Log.d("=commonWorld=", new Gson().toJson(world_clock_config));
        return MessageManager.getInstance().setWorldTimeInfo(world_clock_config);
    }

    public static String local() {
        try {
            Locale locale = mCtx != null ? mCtx.getResources().getConfiguration().getLocales().get(0) : null;
            return locale != null ? locale.getLanguage() + "-" + locale.getCountry() : "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void logSdkVersion() {
        try {
            LogUtil.d("--sdk-version-info-" + ("SDK: " + Build.VERSION.SDK_INT + "\nVersion Name: " + Build.VERSION.RELEASE + "\nAndroid Version: " + Build.VERSION.INCREMENTAL));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void findDevice(int i) {
        MessageManager.getInstance().findDevice(i);
    }

    public static boolean isKeepLive() {
        return checkPermissions("android.permission.ACCESS_COARSE_LOCATION") && checkPermissions("android.permission.ACCESS_FINE_LOCATION");
    }

    public static boolean checkPermissions(String str) {
        return (mCtx.getApplicationInfo().targetSdkVersion < 23 && PermissionChecker.checkPermission(mCtx, str, Binder.getCallingPid(), Binder.getCallingUid(), mCtx.getPackageName()) == 0) || mCtx.checkSelfPermission(str) == 0;
    }
}
