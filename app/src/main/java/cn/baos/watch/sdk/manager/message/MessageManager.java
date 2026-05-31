package cn.baos.watch.sdk.manager.message;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.provider.ContactsContract;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import androidx.core.content.PermissionChecker;
import androidx.recyclerview.widget.ItemTouchHelper;
import cn.baos.message.CatagoryEnum;
import cn.baos.message.MessagePackerUtils;
import cn.baos.message.Serializable;
import cn.baos.watch.sdk.base.AppDataConfig;
import cn.baos.watch.sdk.bluetooth.BlueToothManager;
import cn.baos.watch.sdk.bluetooth.DataUtils;
import cn.baos.watch.sdk.bluetooth.callback.IBtBindRequestCallback;
import cn.baos.watch.sdk.bluetooth.callback.IBtWatchConfigInfoCallback;
import cn.baos.watch.sdk.code.MainHandler;
import cn.baos.watch.sdk.code.callcontroller.CallStateManager;
import cn.baos.watch.sdk.code.test.TestHandler;
import cn.baos.watch.sdk.entitiy.AppMarekEntity;
import cn.baos.watch.sdk.entitiy.CallInfoEntity;
import cn.baos.watch.sdk.entitiy.CommonRemindEntity;
import cn.baos.watch.sdk.entitiy.Constant;
import cn.baos.watch.sdk.entitiy.ContactInfoEntity;
import cn.baos.watch.sdk.entitiy.GpsBsseEntity;
import cn.baos.watch.sdk.entitiy.NlpEntity;
import cn.baos.watch.sdk.entitiy.PrayerGpsEntity;
import cn.baos.watch.sdk.entitiy.PrayerTimeEntity;
import cn.baos.watch.sdk.entitiy.QrEntity;
import cn.baos.watch.sdk.entitiy.SportPlansEntity;
import cn.baos.watch.sdk.entitiy.WeatherEntity;
import cn.baos.watch.sdk.entitiy.WorldEntity;
import cn.baos.watch.sdk.huabaoImpl.sportcontrol.SportControlManager;
import cn.baos.watch.sdk.huabaoImpl.syncdata.SyncDataImpl;
import cn.baos.watch.sdk.huabaoImpl.translate.OnCommandBleOTAEachPackageCallBack;
import cn.baos.watch.sdk.huabaoImpl.translate.OnCommandBleOTAFileInfoCallBack;
import cn.baos.watch.sdk.interfac.app.OnAppMarkListener;
import cn.baos.watch.sdk.interfac.app.OnRemindListener;
import cn.baos.watch.sdk.interfac.ble.BleStatusEnum;
import cn.baos.watch.sdk.interfac.ble.HbBtClientManager;
import cn.baos.watch.sdk.interfac.clock.OnCrudClockDataListener;
import cn.baos.watch.sdk.interfac.clock.OnGetClockDataListener;
import cn.baos.watch.sdk.interfac.contact.OnContactListener;
import cn.baos.watch.sdk.interfac.fall.OnFallListener;
import cn.baos.watch.sdk.interfac.moslem.OnMoslemGpsListener;
import cn.baos.watch.sdk.interfac.moslem.OnMoslemListener;
import cn.baos.watch.sdk.interfac.moslem.OnQrImageListener;
import cn.baos.watch.sdk.interfac.reminder.OnCrudReminderDataListener;
import cn.baos.watch.sdk.interfac.reminder.OnGetReminderDataListener;
import cn.baos.watch.sdk.interfac.watchbattery.OnOilBatteryListener;
import cn.baos.watch.sdk.interfac.watchbattery.OnWatchBatteryListener;
import cn.baos.watch.sdk.interfac.watchinfo.OnWatchInfoListener;
import cn.baos.watch.sdk.interfac.world.OnWorldListener;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.manager.musiccontroller.MusicControlManager;
import cn.baos.watch.sdk.manager.ota.OtaManager;
import cn.baos.watch.sdk.manager.timer.PhoneBind888Timer;
import cn.baos.watch.sdk.util.EncryptionUtils;
import cn.baos.watch.sdk.util.FileUtils;
import cn.baos.watch.sdk.util.LocalAudioPlayManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.Murmur2;
import cn.baos.watch.sdk.util.SharePreferenceUtils;
import cn.baos.watch.sdk.util.TimeUtils;
import cn.baos.watch.sdk.util.W100Utils;
import cn.baos.watch.w100.messages.Action_sync;
import cn.baos.watch.w100.messages.AddAlarm;
import cn.baos.watch.w100.messages.AddReminder;
import cn.baos.watch.w100.messages.AppSystemNotification;
import cn.baos.watch.w100.messages.AppSystemPhone;
import cn.baos.watch.w100.messages.CommandAction;
import cn.baos.watch.w100.messages.CommandAutoTest;
import cn.baos.watch.w100.messages.CommandBleOTAFileData;
import cn.baos.watch.w100.messages.CommandBleOTAFileInfo;
import cn.baos.watch.w100.messages.CommandBleOTAResult;
import cn.baos.watch.w100.messages.CommandContentReturnRequest;
import cn.baos.watch.w100.messages.CommandGetWatchVersion;
import cn.baos.watch.w100.messages.CommandJournalRequest;
import cn.baos.watch.w100.messages.CommandJournalResponse;
import cn.baos.watch.w100.messages.CommandNlpResult;
import cn.baos.watch.w100.messages.CommandPhoneAskPair;
import cn.baos.watch.w100.messages.CommandPhonePairResult;
import cn.baos.watch.w100.messages.CommandTestRequest;
import cn.baos.watch.w100.messages.CommandTestResponse;
import cn.baos.watch.w100.messages.CommandTimeSync;
import cn.baos.watch.w100.messages.CommandTouchEvent;
import cn.baos.watch.w100.messages.CommandWatchVersionResponse;
import cn.baos.watch.w100.messages.Common_contact_info;
import cn.baos.watch.w100.messages.Common_remind_config;
import cn.baos.watch.w100.messages.Config_items;
import cn.baos.watch.w100.messages.Device_app_config;
import cn.baos.watch.w100.messages.Device_base_info;
import cn.baos.watch.w100.messages.Device_resource_info;
import cn.baos.watch.w100.messages.FindMobileRequest;
import cn.baos.watch.w100.messages.FindMobileResponse;
import cn.baos.watch.w100.messages.MessageBase;
import cn.baos.watch.w100.messages.MusicControlRequest;
import cn.baos.watch.w100.messages.MusicControlResponse;
import cn.baos.watch.w100.messages.PhoneControlRequest;
import cn.baos.watch.w100.messages.Prayer_time_params;
import cn.baos.watch.w100.messages.QueryAlarm;
import cn.baos.watch.w100.messages.QueryReminder;
import cn.baos.watch.w100.messages.Regular_remind_config;
import cn.baos.watch.w100.messages.Request_get_data;
import cn.baos.watch.w100.messages.Response_msg;
import cn.baos.watch.w100.messages.Response_no_data;
import cn.baos.watch.w100.messages.Sedentary_monitor_config;
import cn.baos.watch.w100.messages.Sensor_data_battery;
import cn.baos.watch.w100.messages.Sensor_data_gps1;
import cn.baos.watch.w100.messages.Sensor_data_gps_base;
import cn.baos.watch.w100.messages.Sensor_data_meteorological;
import cn.baos.watch.w100.messages.Sensor_data_weather;
import cn.baos.watch.w100.messages.Set_qrcode;
import cn.baos.watch.w100.messages.Sport_plans;
import cn.baos.watch.w100.messages.Sport_trace;
import cn.baos.watch.w100.messages.SyncMessage;
import cn.baos.watch.w100.messages.Update_device_resource;
import cn.baos.watch.w100.messages.User_info_config;
import cn.baos.watch.w100.messages.World_clock_config;
import cn.baos.watch.w100.messages.Wrist_lightup_config;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.UUID;
import org.apache.commons.lang3.ArrayUtils;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

/* JADX INFO: loaded from: classes.dex */
public class MessageManager {
    private static MessageManager sInstance;
    private CommandTimeSync customerCommandTimeSync;
    private Context mContext;
    private int mCurrentIndex;
    private IBtBindRequestCallback mIBtBindRequestCallback;
    private IBtWatchConfigInfoCallback mIBtWatchConfigInfoCallback;
    private IMessageCallback mIMessageCallback;
    private OnAppMarkListener mOnAppListener;
    private OnCommandBleOTAEachPackageCallBack mOnCommandBleOTAEachPackageCallBack;
    private OnCommandBleOTAFileInfoCallBack mOnCommandBleOTAFileInfoCallBack;
    private OnContactListener mOnContactListener;
    private OnCrudClockDataListener mOnCrudClockDataListener;
    private OnCrudReminderDataListener mOnCrudReminderDataListener;
    private OnFallListener mOnFallInfoListener;
    private OnGetClockDataListener mOnGetClockDataListener;
    private OnGetReminderDataListener mOnGetReminderDataListener;
    private OnMoslemGpsListener mOnMoslemGpsListener;
    private OnOilBatteryListener mOnOilListener;
    private OnQrImageListener mOnQrImageListener;
    private OnRemindListener mOnRemindListener;
    private OnWatchBatteryListener mOnWatchBatteryListener;
    private OnWatchInfoListener mOnWatchInfoListener;
    private OnWorldListener mOnWorldListener;
    private OnMoslemListener mOnmoslemListener;
    private boolean sppTransLateData = false;
    int previous = 4;
    Runnable phoneStateResetRunnable = new Runnable() { // from class: cn.baos.watch.sdk.manager.message.MessageManager.1
        @Override // java.lang.Runnable
        public void run() {
            new Thread(new Runnable() { // from class: cn.baos.watch.sdk.manager.message.MessageManager.1.1
                @Override // java.lang.Runnable
                public void run() {
                    MessageManager.this.log("phone 来电状态存储值重置");
                    MessageManager.this.previous = 4;
                }
            }).start();
        }
    };

    private static int calcRank(MessageBase messageBase) {
        return 1;
    }

    public void sendAutoTestResultToWatch(int i, String str) {
    }

    public boolean isSppTransLateData() {
        return this.sppTransLateData;
    }

    public void setSppTransLateData(boolean z) {
        this.sppTransLateData = z;
    }

    private MessageManager() {
    }

    public static MessageManager getInstance() {
        if (sInstance == null) {
            sInstance = new MessageManager();
        }
        return sInstance;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setMessageCallback(IMessageCallback iMessageCallback) {
        this.mIMessageCallback = iMessageCallback;
    }

    public Context getContext() {
        return this.mContext;
    }

    public static void makeMessageToSend(MessageBase messageBase, String str) {
        messageBase.id = Math.abs(Murmur2.hash32(str.getBytes()));
        messageBase.tag = 0;
    }

    public static Serializable unpackMessage(byte[] bArr) {
        try {
            return MessagePackerUtils.uppackObject(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("unpack 异常" + e.getMessage() + " :" + ArrayUtils.toString(e.getStackTrace()));
            return null;
        }
    }

    public static byte[] packMessage(Serializable serializable) {
        if (serializable == null) {
            return new byte[0];
        }
        MessageBufferPacker messageBufferPackerNewDefaultBufferPacker = MessagePack.newDefaultBufferPacker();
        try {
            serializable.put(messageBufferPackerNewDefaultBufferPacker);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] byteArray = messageBufferPackerNewDefaultBufferPacker.toByteArray();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(byteArray.length + 4);
        byteBufferAllocate.putInt(byteArray.length + 4);
        byteBufferAllocate.put(byteArray);
        return byteBufferAllocate.compact().array();
    }

    public void receiveMessageFromDevice(byte[] bArr) {
        Serializable serializableUnpackMessage = unpackMessage(bArr);
        LogUtil.d("蓝牙原始收到消息:" + W100Utils.toString(serializableUnpackMessage));
        if (serializableUnpackMessage == null) {
            log("receiveMessageFromDevice serializable received invalidate message, ignore");
            return;
        }
        if (serializableUnpackMessage.catagory == 90210 || serializableUnpackMessage.catagory == 90211 || serializableUnpackMessage.catagory == 90212 || serializableUnpackMessage.catagory == 90213 || serializableUnpackMessage.catagory == 90214 || serializableUnpackMessage.catagory == 90215 || serializableUnpackMessage.catagory == 90216 || serializableUnpackMessage.catagory == 90217 || serializableUnpackMessage.catagory == 90218 || serializableUnpackMessage.catagory == 30101 || serializableUnpackMessage.catagory == 90230 || serializableUnpackMessage.catagory == 90223 || serializableUnpackMessage.catagory == 90224 || serializableUnpackMessage.catagory == 90226 || serializableUnpackMessage.catagory == 30104 || serializableUnpackMessage.catagory == 130004 || serializableUnpackMessage.catagory == 30303 || serializableUnpackMessage.catagory == 130003 || serializableUnpackMessage.catagory == 30301 || serializableUnpackMessage.catagory == 30200 || serializableUnpackMessage.catagory == 30100 || serializableUnpackMessage.catagory == 30300 || serializableUnpackMessage.catagory == 130005 || serializableUnpackMessage.catagory == 140000 || serializableUnpackMessage.catagory == 30302 || serializableUnpackMessage.catagory == 150000 || serializableUnpackMessage.catagory == 130006 || serializableUnpackMessage.catagory == 90235 || serializableUnpackMessage.catagory == 90237 || serializableUnpackMessage.catagory == 30106 || serializableUnpackMessage.catagory == 90236) {
            receiveSerializableFromDevice(serializableUnpackMessage);
        } else {
            receiveMessageFromDevice((MessageBase) serializableUnpackMessage);
        }
    }

    private void receiveSerializableFromDevice(Serializable serializable) {
        if (serializable == null) {
            LogUtil.d("receiveSerializableFromDevice serializable received invalidate message, ignore");
            return;
        }
        int i = serializable.catagory;
        if (i == 30100) {
            Request_get_data request_get_data = (Request_get_data) serializable;
            switch (request_get_data.data_catagary) {
                case CatagoryEnum.SENSOR_DATA_GPS /* 130002 */:
                    LogUtil.d("手表端获取卫星");
                    new SportControlManager().handleGpsRequestFromWatch(this.mContext, request_get_data);
                    this.mIMessageCallback.onRequestGetData(request_get_data);
                    break;
                case CatagoryEnum.SENSOR_DATA_GPS_BASE /* 130007 */:
                    LogUtil.d("手表端获取GPS基础数据(例如经纬度和海拔)");
                    this.mIMessageCallback.onRequestGpsData();
                    break;
                case CatagoryEnum.SENSOR_DATA_METEOROLOGICAL /* 130008 */:
                    this.mIMessageCallback.onRequestMeteorologicalData();
                    break;
            }
            return;
        }
        if (i == 30101) {
            int i2 = ((Response_no_data) serializable).data_catagary;
            if (i2 != 90235) {
                switch (i2) {
                }
                return;
            }
            SyncDataImpl.getInstance().onGetSyncDataFromWatch(this.mContext, serializable);
            return;
        }
        if (i == 30104) {
            Device_base_info device_base_info = (Device_base_info) serializable;
            LogUtil.d("手表返回手表信息:" + W100Utils.toString(device_base_info));
            OnWatchInfoListener onWatchInfoListener = this.mOnWatchInfoListener;
            if (onWatchInfoListener != null) {
                onWatchInfoListener.onWatchInfo(device_base_info);
                return;
            }
            return;
        }
        if (i == 30106) {
            Device_app_config device_app_config = (Device_app_config) serializable;
            LogUtil.d("手表返回应用市场:" + W100Utils.toString(device_app_config));
            if (this.mOnAppListener != null) {
                LogUtil.e("-------" + device_app_config.app_id_mask);
                LogUtil.e("-------" + W100Utils.bin10T2Dec(device_app_config.app_id_mask));
                this.mOnAppListener.onAppMark(W100Utils.getAppMark(device_app_config.app_id_mask));
                return;
            }
            return;
        }
        if (i != 30200) {
            if (i != 90226 && i != 90230) {
                int i3 = 0;
                if (i != 140000) {
                    switch (i) {
                        case CatagoryEnum.RESPONSE_MSG /* 30300 */:
                            Response_msg response_msg = (Response_msg) serializable;
                            switch (response_msg.act_catagory) {
                                case CatagoryEnum.SET_QRCODE /* 12007 */:
                                    OnQrImageListener onQrImageListener = this.mOnQrImageListener;
                                    if (onQrImageListener != null) {
                                        onQrImageListener.onQrImage(response_msg.result);
                                    }
                                    break;
                                case CatagoryEnum.DEVICE_APP_CONFIG /* 30106 */:
                                    LogUtil.d("应用市场设反馈:" + new Gson().toJson(response_msg));
                                    break;
                                case CatagoryEnum.REGULAR_REMIND_CONFIG /* 90202 */:
                                    LogUtil.d("喝水提醒设置反馈:" + new Gson().toJson(response_msg));
                                    break;
                                case CatagoryEnum.MENSTRUAL_REMIND_CONFIG /* 90203 */:
                                    LogUtil.d("女性健康设置反馈:" + new Gson().toJson(response_msg));
                                    break;
                                case CatagoryEnum.COMMON_REMIND_CONFIG /* 90236 */:
                                    LogUtil.d("5项提醒设置反馈:" + new Gson().toJson(response_msg));
                                    break;
                                case CatagoryEnum.SPORT_PLANS /* 90237 */:
                                    LogUtil.d("训练计划反馈:" + new Gson().toJson(response_msg));
                                    break;
                                case CatagoryEnum.SENSOR_DATA_GPS1 /* 130006 */:
                                    if (this.mOnMoslemGpsListener != null) {
                                        if (response_msg.result == 1) {
                                            this.mOnMoslemGpsListener.onMoslem(true);
                                        } else {
                                            this.mOnMoslemGpsListener.onMoslem(false);
                                        }
                                    }
                                    break;
                                case CatagoryEnum.PRAYER_TIME_PARAMS /* 150000 */:
                                    if (this.mOnmoslemListener != null) {
                                        if (response_msg.result == 1) {
                                            this.mOnmoslemListener.onMoslem(true);
                                        } else {
                                            this.mOnmoslemListener.onMoslem(false);
                                        }
                                    }
                                    break;
                            }
                            return;
                        case CatagoryEnum.ACTION_SYNC /* 30301 */:
                            Action_sync action_sync = (Action_sync) serializable;
                            LogUtil.d("运动控制，相机状态:" + W100Utils.toString(action_sync));
                            this.mIMessageCallback.onActionSync(action_sync);
                            int i4 = action_sync.action_type;
                            if (i4 == 1) {
                                LogUtil.d("ACTION_SYNC开始运动");
                                new SportControlManager().responseActionSync(action_sync);
                                return;
                            } else {
                                if (i4 == 2 || i4 == 3 || i4 == 4) {
                                    new SportControlManager().responseActionSync(action_sync);
                                    return;
                                }
                                return;
                            }
                        case CatagoryEnum.WORLD_CLOCK_CONFIG /* 30302 */:
                            World_clock_config world_clock_config = (World_clock_config) serializable;
                            LogUtil.d("手表返回世界时间:" + W100Utils.toString(world_clock_config));
                            if (this.mOnWorldListener != null) {
                                ArrayList arrayList = new ArrayList();
                                if (world_clock_config.clocks != null && world_clock_config.clocks.length > 0) {
                                    World_clock_config.World_clock[] world_clockArr = world_clock_config.clocks;
                                    int length = world_clockArr.length;
                                    while (i3 < length) {
                                        World_clock_config.World_clock world_clock = world_clockArr[i3];
                                        WorldEntity worldEntity = new WorldEntity();
                                        worldEntity.name = world_clock.name;
                                        worldEntity.timezone = world_clock.timezone;
                                        worldEntity.reserve = world_clock.reserve;
                                        arrayList.add(worldEntity);
                                        i3++;
                                    }
                                }
                                this.mOnWorldListener.onWorld(arrayList);
                                return;
                            }
                            return;
                        case CatagoryEnum.CONFIG_ITEMS /* 30303 */:
                            Config_items config_items = (Config_items) serializable;
                            LogUtil.d("手表返回手表油量:" + W100Utils.toString(config_items));
                            OnOilBatteryListener onOilBatteryListener = this.mOnOilListener;
                            if (onOilBatteryListener != null) {
                                onOilBatteryListener.onOilBattery(config_items);
                                return;
                            }
                            return;
                        default:
                            switch (i) {
                                case CatagoryEnum.SPORT_RECORD_ARRAY /* 90210 */:
                                case CatagoryEnum.SENSOR_DATA_SPORT_MODE_ARRAY /* 90211 */:
                                case CatagoryEnum.SENSOR_DATA_SPORT_HRATE_ARRAY /* 90212 */:
                                case CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_ARRAY /* 90213 */:
                                case CatagoryEnum.SENSOR_DATA_SLEEP_STATS_ARRAY /* 90214 */:
                                case CatagoryEnum.SENSOR_DATA_SLEEP_STATUS_ARRAY /* 90215 */:
                                case CatagoryEnum.SENSOR_DATA_DAILY_HRATE_ARRAY /* 90216 */:
                                case CatagoryEnum.SENSOR_DATA_DAILY_SPO_ARRAY /* 90217 */:
                                case CatagoryEnum.SENSOR_DATA_DAILY_RHR_ARRAY /* 90218 */:
                                    SyncDataImpl.getInstance().onGetSyncDataFromWatch(this.mContext, serializable);
                                    return;
                                default:
                                    switch (i) {
                                        case CatagoryEnum.SENSOR_DATA_DAILY_ACTIVE_SUM /* 90223 */:
                                        case CatagoryEnum.SENSOR_DATA_DAILY_HRATE /* 90224 */:
                                            break;
                                        default:
                                            switch (i) {
                                                case CatagoryEnum.SENSOR_DATA_ARRAY /* 90235 */:
                                                    LogUtil.d("332-6项数据展示");
                                                    SyncDataImpl.getInstance().onGetSyncDataFromWatchSix(this.mContext, serializable);
                                                    break;
                                                case CatagoryEnum.COMMON_REMIND_CONFIG /* 90236 */:
                                                    Common_remind_config common_remind_config = (Common_remind_config) serializable;
                                                    LogUtil.d("手表返回提醒:" + W100Utils.toString(common_remind_config));
                                                    OnRemindListener onRemindListener = this.mOnRemindListener;
                                                    if (onRemindListener != null) {
                                                        onRemindListener.onAppRemind(common_remind_config);
                                                    }
                                                    break;
                                                case CatagoryEnum.SPORT_PLANS /* 90237 */:
                                                    LogUtil.d("训练计划信息:" + W100Utils.toString((Sport_plans) serializable));
                                                    break;
                                                default:
                                                    switch (i) {
                                                        case CatagoryEnum.SENSOR_DATA_WEATHER /* 130003 */:
                                                            Sensor_data_weather sensor_data_weather = (Sensor_data_weather) serializable;
                                                            if (this.mIMessageCallback != null) {
                                                                LogUtil.d("手表请求天气数据:" + W100Utils.toString(sensor_data_weather));
                                                                this.mIMessageCallback.onRequestWeather();
                                                            }
                                                            break;
                                                        case CatagoryEnum.SENSOR_DATA_BATTERY /* 130004 */:
                                                            Sensor_data_battery sensor_data_battery = (Sensor_data_battery) serializable;
                                                            LogUtil.d("手表返回手表电量:" + W100Utils.toString(sensor_data_battery));
                                                            OnWatchBatteryListener onWatchBatteryListener = this.mOnWatchBatteryListener;
                                                            if (onWatchBatteryListener != null) {
                                                                onWatchBatteryListener.onWatchBattery(sensor_data_battery);
                                                            }
                                                            break;
                                                        case CatagoryEnum.USER_INFO_CONFIG /* 130005 */:
                                                            this.mIMessageCallback.onUserInfoConfig((User_info_config) serializable);
                                                            break;
                                                    }
                                                    break;
                                            }
                                            break;
                                    }
                                    return;
                            }
                    }
                }
                Common_contact_info common_contact_info = (Common_contact_info) serializable;
                LogUtil.d("手表返回通讯录信息:" + W100Utils.toString(common_contact_info));
                if (this.mOnContactListener != null) {
                    ArrayList arrayList2 = new ArrayList();
                    if (common_contact_info.contacts != null && common_contact_info.contacts.length > 0) {
                        Common_contact_info.Contact_info[] contact_infoArr = common_contact_info.contacts;
                        int length2 = contact_infoArr.length;
                        while (i3 < length2) {
                            Common_contact_info.Contact_info contact_info = contact_infoArr[i3];
                            ContactInfoEntity contactInfoEntity = new ContactInfoEntity();
                            contactInfoEntity.flag = contact_info.flag;
                            contactInfoEntity.name = contact_info.name;
                            contactInfoEntity.number = contact_info.number;
                            arrayList2.add(contactInfoEntity);
                            i3++;
                        }
                    }
                    this.mOnContactListener.onContact(arrayList2);
                    return;
                }
                return;
            }
            SyncDataImpl.getInstance().onGetSyncRightNowDataFromWatch(this.mContext, serializable);
            return;
        }
        SyncMessage syncMessage = (SyncMessage) serializable;
        LogUtil.d("数据同步->手表传过来的data:" + W100Utils.toString(syncMessage));
        IMessageCallback iMessageCallback = this.mIMessageCallback;
        if (iMessageCallback != null) {
            iMessageCallback.onSyncMessageRequest(packMessage(syncMessage));
        }
    }

    public void receiveMessageFromDevice(MessageBase messageBase) {
        if (messageBase == null) {
            log("receiveMessageFromDevice received invalidate message, ignore");
        }
        LogUtil.d("蓝牙收到消息:" + W100Utils.toString(messageBase));
        switch (messageBase.catagory) {
            case CatagoryEnum.COMMANDPHONEPAIRRESULT /* 10020 */:
                PhoneBind888Timer.getInstance().endWaitTimeOut();
                CommandPhonePairResult commandPhonePairResult = (CommandPhonePairResult) messageBase;
                if (commandPhonePairResult.pair_result == 1) {
                    AppDataConfig.getInstance().put(SharePreferenceUtils.KEY_PAIR_CODE, commandPhonePairResult.rank);
                    IBtBindRequestCallback iBtBindRequestCallback = this.mIBtBindRequestCallback;
                    if (iBtBindRequestCallback != null) {
                        iBtBindRequestCallback.onBindSuccess();
                    }
                } else {
                    IBtBindRequestCallback iBtBindRequestCallback2 = this.mIBtBindRequestCallback;
                    if (iBtBindRequestCallback2 != null) {
                        iBtBindRequestCallback2.onUnbindSuccess();
                    }
                }
                break;
            case CatagoryEnum.COMMANDPHONEASKPAIR /* 10021 */:
                IMessageCallback iMessageCallback = this.mIMessageCallback;
                if (iMessageCallback != null) {
                    iMessageCallback.onBindRequestByPhone();
                }
                LogUtil.d("手表端主动发起配对请求");
                break;
            case CatagoryEnum.MUSICCONTROLREQUEST /* 10804 */:
                AppDataConfig.getInstance().isMusicSwatch();
                MusicControlRequest musicControlRequest = (MusicControlRequest) messageBase;
                LogUtil.d("手表音乐控制指令" + new Gson().toJson(musicControlRequest));
                MusicControlManager.getInstance().handleCommandFromWatchToControlMusic(musicControlRequest);
                break;
            case CatagoryEnum.FINDMOBILEREQUEST /* 11701 */:
                if (AppDataConfig.getInstance().isFindPhone()) {
                    FindMobileResponse findMobileResponse = new FindMobileResponse();
                    FindMobileRequest findMobileRequest = (FindMobileRequest) messageBase;
                    if (findMobileRequest.is_find == 1) {
                        LocalAudioPlayManager.getInstance().playAudio();
                        findMobileResponse.status = 1;
                    } else if (findMobileRequest.is_find == 0) {
                        LocalAudioPlayManager.getInstance().stopAudio();
                        findMobileResponse.status = 0;
                    }
                    sendMessage((MessageBase) findMobileResponse);
                }
                break;
            case CatagoryEnum.DEVICE_RESOURCE_INFO /* 12003 */:
                Device_resource_info device_resource_info = (Device_resource_info) messageBase;
                IMessageCallback iMessageCallback2 = this.mIMessageCallback;
                if (iMessageCallback2 != null) {
                    iMessageCallback2.onDeviceResourceInfo(device_resource_info);
                }
                break;
            case CatagoryEnum.COMMANDTIMESYNC /* 30007 */:
                CommandTimeSync commandTimeSync = (CommandTimeSync) messageBase;
                LogUtil.d("手表主动获取时间:" + W100Utils.toString(commandTimeSync));
                timeSyncToWatch(commandTimeSync.sync_id);
                DataUtils.setTimeFormat(this.mContext);
                break;
            case CatagoryEnum.COMMANDCONTENTRETURNREQUEST /* 50000 */:
                CommandContentReturnRequest commandContentReturnRequest = (CommandContentReturnRequest) messageBase;
                if (commandContentReturnRequest.content.length() <= 240) {
                    TestHandler.getInstance().obtainMessage(1009, commandContentReturnRequest).sendToTarget();
                } else {
                    TestHandler.getInstance().obtainMessage(1019, commandContentReturnRequest).sendToTarget();
                }
                break;
            case CatagoryEnum.COMMANDAUTOTEST /* 50002 */:
                LogUtil.d("日志:" + ((CommandAutoTest) messageBase).test_result);
                Constant.isIntegrationMode = true;
                break;
            case CatagoryEnum.COMMANDTESTRESPONSE /* 50004 */:
                CommandTestResponse commandTestResponse = (CommandTestResponse) messageBase;
                LogUtil.d("手表测试结果-手机接收到commandTestResponse:" + W100Utils.toString(commandTestResponse));
                if (commandTestResponse.result >= 6) {
                    LogUtil.d("手表测试结果-type大于等于6,手机写入本地文件,以及生成zpl文件");
                    FileUtils.writeValueToLocal(this.mContext, HbBtClientManager.getInstance().getCurrentConnectConfig().macAddress + "  状态:" + commandTestResponse.result);
                } else {
                    LogUtil.d("手表测试结果-type为" + commandTestResponse.result + ",手机不会写入本地文件");
                }
                break;
            case CatagoryEnum.COMMANDJOURNALRESPONSE /* 50006 */:
                CommandJournalResponse commandJournalResponse = (CommandJournalResponse) messageBase;
                IMessageCallback iMessageCallback3 = this.mIMessageCallback;
                if (iMessageCallback3 != null) {
                    iMessageCallback3.onCollectWatchLoggerRequest(packMessage(messageBase));
                    this.mIMessageCallback.onCollectWatchLoggerRequestData(commandJournalResponse.response);
                }
                break;
            case CatagoryEnum.COMMANDBLEOTARESULT /* 60002 */:
                otaResultHandle((CommandBleOTAResult) messageBase);
                break;
            case CatagoryEnum.COMMANDWATCHVERSIONRESPONSE /* 60004 */:
                CommandWatchVersionResponse commandWatchVersionResponse = (CommandWatchVersionResponse) messageBase;
                log(W100Utils.toString(commandWatchVersionResponse));
                OtaManager.getInstance().handleWatchVersion(commandWatchVersionResponse);
                break;
            case CatagoryEnum.PHONECONTROLREQUEST /* 80230 */:
                PhoneControlRequest phoneControlRequest = (PhoneControlRequest) messageBase;
                LogUtil.d("手表电话控制:" + new Gson().toJson(phoneControlRequest));
                IMessageCallback iMessageCallback4 = this.mIMessageCallback;
                if (iMessageCallback4 != null) {
                    iMessageCallback4.onPhoneStatus(phoneControlRequest.action);
                }
                if (phoneControlRequest.action == 4) {
                    CallStateManager.getInstance().quietCallOn();
                } else if (phoneControlRequest.action == 2) {
                    MainHandler.getInstance().obtainMessage(104).sendToTarget();
                    CallStateManager.getInstance().quietCallOff();
                }
                break;
        }
    }

    public void timeSyncToWatch(int i) {
        timeFromLocal(i);
    }

    public void timeSyncToWatch(int i, long j, int i2) {
        this.customerCommandTimeSync = new CommandTimeSync();
        LogUtil.d("自定义同步时间戳:" + j + ",syncId=" + i);
        this.customerCommandTimeSync.timestamp = (int) (j / 1000);
        this.customerCommandTimeSync.tv_nsec = 1;
        this.customerCommandTimeSync.sync_id = i;
        this.customerCommandTimeSync.time_zone = String.valueOf(i2);
        makeMessageToSend(this.customerCommandTimeSync, CatagoryEnum.classCatagoryMap.get(Integer.valueOf(this.customerCommandTimeSync.catagory)));
        if (i != 0) {
            sendMessage((MessageBase) this.customerCommandTimeSync);
        } else {
            LogUtil.e("syncId不能为0");
        }
    }

    private void timeFromLocal(int i) {
        CommandTimeSync commandTimeSync = new CommandTimeSync();
        long jCurrentTimeMillis = System.currentTimeMillis();
        LogUtil.d("当前时间时间戳:" + jCurrentTimeMillis);
        commandTimeSync.timestamp = (int) (jCurrentTimeMillis / 1000);
        commandTimeSync.tv_nsec = 0;
        commandTimeSync.sync_id = i;
        commandTimeSync.time_zone = String.valueOf(TimeUtils.getTimezoneDifferenceInSecond(TimeZone.getDefault().getID()));
        makeMessageToSend(commandTimeSync, CatagoryEnum.classCatagoryMap.get(Integer.valueOf(commandTimeSync.catagory)));
        log("发送同步时间戳:" + W100Utils.toString(commandTimeSync));
        log("手机时间:" + TimeUtils.getTimeZoneTimeInt() + " time:" + TimeUtils.getTimeZoneTime());
        TimeZone timeZone = TimeZone.getDefault();
        log("timeZone:" + timeZone.getDisplayName(false, 0) + "timeZone id:" + timeZone.getID());
        if (i != 0) {
            sendMessage((MessageBase) commandTimeSync);
        } else {
            LogUtil.e("同步时间syncId: =" + i + " 为0，不同步)");
        }
    }

    public void sendMusicStatusAndVolume(MusicControlResponse musicControlResponse) {
        makeMessageToSend(musicControlResponse, String.valueOf((int) (System.currentTimeMillis() / 1000)));
        LogUtil.d("蓝牙发送歌曲状态和音量:" + new Gson().toJson(musicControlResponse));
        sendMessage((MessageBase) musicControlResponse);
    }

    public void pushNotification(StatusBarNotification statusBarNotification) {
        LogUtil.d("push to watch");
        if (!AppDataConfig.getInstance().isBindWatch()) {
            LogUtil.d("watch has not bind,not push message!");
            return;
        }
        String packageName = statusBarNotification.getPackageName();
        packageName.hashCode();
        String contactNameFromPhoneBook = "";
        if (packageName.equals("com.android.incallui")) {
            CallInfoEntity callInfoEntity = new CallInfoEntity();
            String strValueOf = String.valueOf(statusBarNotification.getNotification().extras.getCharSequence(NotificationCompat.EXTRA_TITLE));
            try {
                contactNameFromPhoneBook = getContactNameFromPhoneBook(this.mContext, strValueOf);
            } catch (Exception unused) {
                LogUtil.d("需要通讯录权限才能查询手机号对应名称");
            }
            callInfoEntity.setIncomingNum(strValueOf);
            callInfoEntity.setPhoneState(0);
            callInfoEntity.setGetIncomingNumName(contactNameFromPhoneBook);
            LogUtil.d("phone 响铃来源通知栏" + callInfoEntity.toString());
            if (!callInfoEntity.getIncomingNum().isEmpty()) {
                LogUtil.d("phone 响铃来源，有数据");
                MainHandler.getInstance().obtainMessage(101, callInfoEntity).sendToTarget();
                return;
            }
            if (callInfoEntity.getGetIncomingNumName().isEmpty() && !callInfoEntity.getIncomingNum().isEmpty()) {
                if (W100Utils.isNumeric(callInfoEntity.getIncomingNum())) {
                    LogUtil.d("phone 电话号码取出来是数字，该条处理下...");
                    LogUtil.d("phone 来电电话" + callInfoEntity.getIncomingNum() + "，名字为空数据存在空值");
                    callInfoEntity.setGetIncomingNumName(String.valueOf(statusBarNotification.getNotification().extras.getCharSequence(NotificationCompat.EXTRA_TEXT)));
                    if (!callInfoEntity.getGetIncomingNumName().equals("当前通话")) {
                        LogUtil.d("phone 名字不为默认：来电，该条通知发送，通过1s过滤机制保证不重复:" + callInfoEntity.toString());
                        MainHandler.getInstance().obtainMessage(101, callInfoEntity).sendToTarget();
                        return;
                    } else {
                        LogUtil.d("phone 名字为默认：当前通话，该条通知不发送，播出电话，oppo有时会有这个通知");
                        return;
                    }
                }
                LogUtil.d("phone 电话号码取出来为名字，不是数字，该条忽略");
                return;
            }
            LogUtil.d("phone 响铃来源通知栏，取出数据存在空值");
            return;
        }
        if (packageName.equals(Constant.PACKAGE_NAME_PHONE_CALL)) {
            CallInfoEntity callInfoEntity2 = new CallInfoEntity();
            String strValueOf2 = String.valueOf(statusBarNotification.getNotification().extras.getCharSequence(NotificationCompat.EXTRA_TEXT));
            try {
                contactNameFromPhoneBook = getContactNameFromPhoneBook(this.mContext, strValueOf2);
            } catch (Exception unused2) {
                LogUtil.d("需要通讯录权限才能查询手机号对应名称");
            }
            callInfoEntity2.setIncomingNum(strValueOf2);
            callInfoEntity2.setPhoneState(0);
            callInfoEntity2.setGetIncomingNumName(contactNameFromPhoneBook);
            if (callInfoEntity2.getIncomingNum().isEmpty()) {
                return;
            }
            LogUtil.d("phone 响铃来源，有数据");
            MainHandler.getInstance().obtainMessage(101, callInfoEntity2).sendToTarget();
            return;
        }
        AppSystemNotification appSystemNotification = new AppSystemNotification();
        String strValueOf3 = String.valueOf(statusBarNotification.getNotification().extras.getCharSequence(NotificationCompat.EXTRA_TITLE));
        if (strValueOf3 != null && strValueOf3.length() > 64) {
            strValueOf3 = strValueOf3.substring(0, 64);
        }
        appSystemNotification.title = strValueOf3;
        String strValueOf4 = String.valueOf(statusBarNotification.getNotification().extras.getCharSequence(NotificationCompat.EXTRA_TEXT));
        if (strValueOf4.length() > 251) {
            appSystemNotification.content = strValueOf4.substring(0, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        } else {
            appSystemNotification.content = strValueOf4;
        }
        appSystemNotification.start_time_s = (int) (System.currentTimeMillis() / 1000);
        appSystemNotification.package_name = statusBarNotification.getPackageName();
        appSystemNotification.highlight = 1;
        makeMessageToSend(appSystemNotification, statusBarNotification.getKey());
        if (appSystemNotification.content == null || appSystemNotification.content.equals("独立运动")) {
            return;
        }
        LogUtil.d("发送系统通知:" + W100Utils.toString(appSystemNotification));
        sendMessage((MessageBase) appSystemNotification);
    }

    public void pushNotification(String str, String str2, String str3) {
        LogUtil.d("push to watch content");
        str.hashCode();
        if (str.equals("com.android.incallui")) {
            return;
        }
        AppSystemNotification appSystemNotification = new AppSystemNotification();
        if (str2 != null && str2.length() > 64) {
            str2 = str2.substring(0, 64);
        }
        appSystemNotification.title = str2;
        String strValueOf = String.valueOf(str3);
        if (strValueOf.length() > 251) {
            appSystemNotification.content = strValueOf.substring(0, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        } else {
            appSystemNotification.content = strValueOf;
        }
        appSystemNotification.start_time_s = (int) (System.currentTimeMillis() / 1000);
        appSystemNotification.package_name = str;
        appSystemNotification.highlight = 1;
        makeMessageToSend(appSystemNotification, UUID.randomUUID().toString());
        LogUtil.d("发送短信通知:" + W100Utils.toString(appSystemNotification));
        sendMessage((MessageBase) appSystemNotification);
    }

    public String getContactNameFromPhoneBook(Context context, String str) {
        boolean z = true;
        if (context.getApplicationInfo().targetSdkVersion >= 23 ? PermissionChecker.checkSelfPermission(this.mContext, "android.permission.READ_CONTACTS") != 0 : PermissionChecker.checkPermission(context, "android.permission.READ_CONTACTS", Binder.getCallingPid(), Binder.getCallingUid(), context.getPackageName()) != 0) {
            z = false;
        }
        if (!z) {
            return "";
        }
        Cursor cursorQuery = context.getContentResolver().query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(str)), new String[]{"display_name", "number"}, null, null, null);
        if (!cursorQuery.moveToFirst()) {
            return "";
        }
        String string = cursorQuery.getString(cursorQuery.getColumnIndex("display_name"));
        cursorQuery.close();
        return string;
    }

    public void handleXiaoAiToWatch(NlpEntity nlpEntity) {
        String type = nlpEntity.getType();
        type.hashCode();
        switch (type) {
            case "reminder":
                sendNlpCommand(nlpEntity);
                AddReminder addReminder = new AddReminder();
                addReminder.verison = Constant.reminder_version;
                addReminder.circle_type = nlpEntity.getCircleModel().getCircleType();
                addReminder.circle_extra = nlpEntity.getCircleModel().getCircleExtra();
                addReminder.mask_wday = nlpEntity.getCircleModel().getDayOfWeek();
                addReminder.mask_mday = nlpEntity.getCircleModel().getDayOfMouth();
                addReminder.mask_mweek = nlpEntity.getCircleModel().getWeekOfMouth();
                addReminder.mask_ymonth = nlpEntity.getCircleModel().getMouthOfYear();
                addReminder.time_zone = TimeUtils.getTimeZoneChange();
                addReminder.start_time = nlpEntity.getValue();
                addReminder.reminder = nlpEntity.getReminder();
                addReminder.event = nlpEntity.getEvent();
                makeMessageToSend(addReminder, String.valueOf((System.currentTimeMillis() / 1000) + 1));
                log("小爱发送reminder:" + W100Utils.toString(addReminder));
                sendMessage((MessageBase) addReminder);
                break;
            case "alarm":
                sendNlpCommand(nlpEntity);
                AddAlarm addAlarm = new AddAlarm();
                addAlarm.verison = Constant.alarm_version;
                addAlarm.circle_type = nlpEntity.getCircleModel().getCircleType();
                addAlarm.circle_extra = nlpEntity.getCircleModel().getCircleExtra();
                addAlarm.mask_wday = nlpEntity.getCircleModel().getDayOfWeek();
                addAlarm.mask_mday = nlpEntity.getCircleModel().getDayOfMouth();
                addAlarm.mask_mweek = nlpEntity.getCircleModel().getWeekOfMouth();
                addAlarm.mask_ymonth = nlpEntity.getCircleModel().getMouthOfYear();
                addAlarm.time_zone = TimeUtils.getTimeZoneChange();
                addAlarm.start_time = nlpEntity.getValue();
                addAlarm.reminder = nlpEntity.getReminder();
                addAlarm.event = nlpEntity.getEvent();
                makeMessageToSend(addAlarm, String.valueOf((System.currentTimeMillis() / 1000) + 1));
                log("小爱发送alarm:" + W100Utils.toString(addAlarm));
                sendMessage((MessageBase) addAlarm);
                break;
            case "toast":
                sendNlpCommand(nlpEntity);
                break;
        }
    }

    public boolean sendPhoneBindRequest(int i, String str, IBtBindRequestCallback iBtBindRequestCallback) {
        this.mIBtBindRequestCallback = iBtBindRequestCallback;
        CommandPhoneAskPair commandPhoneAskPair = new CommandPhoneAskPair();
        commandPhoneAskPair.match_code = i;
        commandPhoneAskPair.phone_type = str;
        commandPhoneAskPair.rank = 20;
        log("手机发起绑定请求:" + W100Utils.toString(commandPhoneAskPair));
        return sendMessage((MessageBase) commandPhoneAskPair);
    }

    public boolean sendPhoneBindRequest111(int i, String str) {
        CommandPhoneAskPair commandPhoneAskPair = new CommandPhoneAskPair();
        commandPhoneAskPair.match_code = i;
        commandPhoneAskPair.phone_type = str;
        commandPhoneAskPair.rank = 20;
        log("手机发起绑定请求:" + W100Utils.toString(commandPhoneAskPair));
        return sendMessage((MessageBase) commandPhoneAskPair);
    }

    private void sendNlpCommand(NlpEntity nlpEntity) {
        CommandNlpResult commandNlpResult = new CommandNlpResult();
        commandNlpResult.show_text = nlpEntity.getTextShow();
        commandNlpResult.action_id = nlpEntity.getActionId();
        makeMessageToSend(commandNlpResult, String.valueOf(System.currentTimeMillis() / 1000));
        log("小爱发送nlp:" + W100Utils.toString(commandNlpResult));
        sendMessage((MessageBase) commandNlpResult);
    }

    public void sendTestRequestCommand(int i) {
        CommandTestRequest commandTestRequest = new CommandTestRequest();
        commandTestRequest.test_function = i;
        makeMessageToSend(commandTestRequest, String.valueOf(System.currentTimeMillis() / 1000));
        sendMessage((MessageBase) commandTestRequest);
    }

    public void sendAppSystemPhone(CallInfoEntity callInfoEntity) {
        AppSystemPhone appSystemPhone = new AppSystemPhone();
        appSystemPhone.phone_num = callInfoEntity.getIncomingNum();
        appSystemPhone.contacter = callInfoEntity.getGetIncomingNumName();
        appSystemPhone.phone_state = callInfoEntity.getPhoneState();
        appSystemPhone.start_time_s = (int) (System.currentTimeMillis() / 1000);
        makeMessageToSend(appSystemPhone, String.valueOf(System.currentTimeMillis() / 1000));
        if (this.previous == appSystemPhone.phone_state) {
            log("phone 来电状态重复发送过滤");
            return;
        }
        LogUtil.d("phone 发送:" + W100Utils.toString(appSystemPhone));
        this.previous = appSystemPhone.phone_state;
        sendMessage((MessageBase) appSystemPhone);
    }

    public void sendFingerCoordinateToWatch(int i, int i2, int i3) {
        CommandTouchEvent commandTouchEvent = new CommandTouchEvent();
        commandTouchEvent.state = i;
        commandTouchEvent.touch_x = i2;
        commandTouchEvent.touch_y = i3;
        makeMessageToSend(commandTouchEvent, String.valueOf(System.currentTimeMillis() / 1000));
        LogUtil.d("发送手指坐标给手表:" + commandTouchEvent.toString());
        sendMessage((MessageBase) commandTouchEvent);
    }

    public boolean sendMessage(MessageBase messageBase) {
        messageBase.addtime = (int) (System.currentTimeMillis() / 1000);
        return sendMessage(2, messageBase);
    }

    public boolean sendMessage(Serializable serializable) {
        return sendMessage(2, serializable);
    }

    private boolean sendMessage(int i, Serializable serializable) {
        String json = new Gson().toJson(serializable);
        LogUtil.d("sendMessage--" + json);
        boolean z = !TextUtils.isEmpty(json) && json.contains("match_code");
        if (this.mContext != null && !AppDataConfig.getInstance().isBindWatch() && !z) {
            LogUtil.e("ble 未绑定前-不可发送业务信息)");
            return false;
        }
        if (BleStatusEnum.isBleConnected(HbBtClientManager.getInstance().getCurrentStatus())) {
            LogUtil.d("sendMessage->isBleConnected==" + HbBtClientManager.getInstance().getCurrentStatus());
            LogUtil.d("sendMessage->isDisconnectReal=" + (!BleStatusEnum.isBleConnected(HbBtClientManager.getInstance().getCurrentStatus())));
            if (!BlueToothManager.getInstance().sendMessage(i, serializable)) {
                LogUtil.e("send failed");
                return false;
            }
            LogUtil.e("send -> sendMessage-- success");
            return true;
        }
        LogUtil.e("ble is not ready(5s not reconnect)");
        return false;
    }

    public boolean sendMessage(MessageBase messageBase, IMessageSendCallback iMessageSendCallback) {
        messageBase.addtime = (int) (System.currentTimeMillis() / 1000);
        return sendMessage(2, messageBase, iMessageSendCallback);
    }

    private boolean sendMessage(int i, Serializable serializable, IMessageSendCallback iMessageSendCallback) {
        String json = new Gson().toJson(serializable);
        LogUtil.d("sendMessage--" + json);
        boolean z = !TextUtils.isEmpty(json) && json.contains("match_code");
        if (this.mContext != null && !AppDataConfig.getInstance().isBindWatch() && !z) {
            LogUtil.e("ble 未绑定前-不可发送业务信息)");
            return false;
        }
        if (BleStatusEnum.isBleConnected(HbBtClientManager.getInstance().getCurrentStatus())) {
            LogUtil.d("sendMessage->isBleConnected==" + HbBtClientManager.getInstance().getCurrentStatus());
            LogUtil.d("sendMessage->isDisconnectReal=" + (!BleStatusEnum.isBleConnected(HbBtClientManager.getInstance().getCurrentStatus())));
            if (!BlueToothManager.getInstance().sendMessage(i, serializable, iMessageSendCallback)) {
                LogUtil.e("send failed");
                return false;
            }
            LogUtil.e("send -> sendMessage-- success");
            return true;
        }
        LogUtil.e("ble is not ready(5s not reconnect)");
        return false;
    }

    public void requestClockListToWatch(OnGetClockDataListener onGetClockDataListener) {
        this.mOnGetClockDataListener = onGetClockDataListener;
        QueryAlarm queryAlarm = new QueryAlarm();
        queryAlarm.query_mode = 1;
        makeMessageToSend(queryAlarm, String.valueOf(System.currentTimeMillis() / 1000));
        LogUtil.d("闹钟管理请求闹钟列表:" + W100Utils.toString(queryAlarm));
        sendMessage((MessageBase) queryAlarm);
    }

    public boolean setSwitchRaiseWristLightUp(boolean z) {
        Wrist_lightup_config wrist_lightup_config = new Wrist_lightup_config();
        if (z) {
            wrist_lightup_config.is_enable = 1;
        } else {
            wrist_lightup_config.is_enable = 0;
        }
        wrist_lightup_config.begin_hour = 0;
        wrist_lightup_config.duration_hours = 24;
        return sendMessage(wrist_lightup_config);
    }

    public void setCrudClockListener(int i, OnCrudClockDataListener onCrudClockDataListener) {
        this.mOnCrudReminderDataListener = null;
        this.mOnCrudClockDataListener = onCrudClockDataListener;
    }

    public void requestReminderListToWatch(QueryReminder queryReminder, OnGetReminderDataListener onGetReminderDataListener) {
        this.mOnGetReminderDataListener = onGetReminderDataListener;
        makeMessageToSend(queryReminder, String.valueOf(System.currentTimeMillis() / 1000));
        LogUtil.d("闹钟管理请求提醒列表:" + W100Utils.toString(queryReminder));
        sendMessage((MessageBase) queryReminder);
    }

    public void setCrudReminderListener(int i, OnCrudReminderDataListener onCrudReminderDataListener) {
        this.mOnCrudReminderDataListener = onCrudReminderDataListener;
    }

    public boolean sendGpsBaseToWatch(GpsBsseEntity gpsBsseEntity) {
        Sensor_data_gps_base sensor_data_gps_base = new Sensor_data_gps_base();
        sensor_data_gps_base.latitude = (int) (gpsBsseEntity.lat * 1.0E7f);
        sensor_data_gps_base.longitude = (int) (gpsBsseEntity.lng * 1.0E7f);
        sensor_data_gps_base.height = (int) gpsBsseEntity.altitude;
        makeMessageToSend(sensor_data_gps_base, String.valueOf(System.currentTimeMillis() / 1000));
        LogUtil.d("sensor_data_gps_base:" + W100Utils.toString(sensor_data_gps_base));
        return sendMessage((MessageBase) sensor_data_gps_base);
    }

    public boolean sendComomnRemindToWatch(CommonRemindEntity commonRemindEntity) {
        if (commonRemindEntity.type == 98 || commonRemindEntity.type == 99) {
            if (commonRemindEntity.type == 98) {
                Sedentary_monitor_config sedentary_monitor_config = new Sedentary_monitor_config();
                sedentary_monitor_config.is_enable = commonRemindEntity.enable;
                Sedentary_monitor_config.Sedentary_rule sedentary_rule = new Sedentary_monitor_config.Sedentary_rule();
                sedentary_rule.remind_count = 1;
                sedentary_rule.remind_interval_min = 0;
                sedentary_rule.sedentary_min = commonRemindEntity.interval_sec;
                sedentary_rule.target_steps = 50;
                Sedentary_monitor_config.Sedentary_timespan sedentary_timespan = new Sedentary_monitor_config.Sedentary_timespan();
                sedentary_timespan.begin_hour = commonRemindEntity.timespan.begin_hour;
                sedentary_timespan.begin_min = commonRemindEntity.timespan.begin_min;
                sedentary_timespan.end_hour = commonRemindEntity.timespan.end_hour;
                sedentary_timespan.end_min = commonRemindEntity.timespan.end_min;
                sedentary_monitor_config.timespan = sedentary_timespan;
                sedentary_monitor_config.rule = sedentary_rule;
                LogUtil.d("久坐提醒:" + new Gson().toJson(sedentary_monitor_config));
                return getInstance().sendMessage(sedentary_monitor_config);
            }
            Regular_remind_config regular_remind_config = new Regular_remind_config();
            regular_remind_config.remind_item = 1;
            regular_remind_config.is_enable = commonRemindEntity.enable;
            Regular_remind_config.Regular_remind_cfg regular_remind_cfg = new Regular_remind_config.Regular_remind_cfg();
            regular_remind_cfg.interval_sec = commonRemindEntity.interval_sec;
            Regular_remind_config.Regular_remind_cfg.Regular_timespan regular_timespan = new Regular_remind_config.Regular_remind_cfg.Regular_timespan();
            regular_timespan.begin_hour = commonRemindEntity.timespan.begin_hour;
            regular_timespan.begin_min = commonRemindEntity.timespan.begin_min;
            regular_timespan.end_hour = commonRemindEntity.timespan.end_hour;
            regular_timespan.end_min = commonRemindEntity.timespan.end_min;
            regular_remind_cfg.timespan = regular_timespan;
            regular_remind_config.cfg = regular_remind_cfg;
            LogUtil.d("常规提醒:喝水、洗手:" + new Gson().toJson(regular_remind_config));
            return getInstance().sendMessage(regular_remind_config);
        }
        Common_remind_config common_remind_config = new Common_remind_config();
        if (commonRemindEntity.type == 103 || commonRemindEntity.type == 104 || commonRemindEntity.type == 105) {
            common_remind_config.interval_sec = Integer.MAX_VALUE;
            common_remind_config.week_day_mask = W100Utils.bin2Dec(commonRemindEntity.weekDayMaskValue);
        } else {
            common_remind_config.interval_sec = commonRemindEntity.interval_sec;
            common_remind_config.week_day_mask = 127;
        }
        common_remind_config.type = commonRemindEntity.type;
        common_remind_config.enable = commonRemindEntity.enable;
        Common_remind_config.CRC_timespan cRC_timespan = new Common_remind_config.CRC_timespan();
        cRC_timespan.begin_hour = commonRemindEntity.timespan.begin_hour;
        cRC_timespan.begin_min = commonRemindEntity.timespan.begin_min;
        cRC_timespan.end_hour = commonRemindEntity.timespan.end_hour;
        cRC_timespan.end_min = commonRemindEntity.timespan.end_min;
        common_remind_config.timespan = cRC_timespan;
        LogUtil.d("common_remind_config:" + W100Utils.toString(common_remind_config));
        return sendMessage(common_remind_config);
    }

    public boolean sendSportsPlanToWatch(SportPlansEntity sportPlansEntity) {
        Sport_plans sport_plans = new Sport_plans();
        sport_plans.enable = sportPlansEntity.enable;
        if (sportPlansEntity.plans.size() > 0) {
            Sport_plans.Sport_plan[] sport_planArr = new Sport_plans.Sport_plan[sportPlansEntity.plans.size()];
            for (int i = 0; i < sportPlansEntity.plans.size(); i++) {
                SportPlansEntity.Plans plans = sportPlansEntity.plans.get(i);
                Sport_plans.Sport_plan sport_plan = new Sport_plans.Sport_plan();
                sport_plan.day_of_week = plans.dayOfWeek;
                sport_plan.mode = plans.mode;
                sport_plan.target_type = plans.targetType;
                sport_planArr[i] = sport_plan;
            }
            sport_plans.plans = sport_planArr;
        }
        LogUtil.d("Sport_plans:" + W100Utils.toString(sport_plans));
        return sendMessage(sport_plans);
    }

    public boolean sendAppMarkeToWatch(AppMarekEntity appMarekEntity) {
        if (appMarekEntity == null) {
            return false;
        }
        Device_app_config device_app_config = new Device_app_config();
        String str = appMarekEntity.isMyFriends + "" + appMarekEntity.isBreathingRate + appMarekEntity.isBodyTemperature + appMarekEntity.isBloodSugar + "" + appMarekEntity.isMeteorological + "" + appMarekEntity.isVideoCtrl + "";
        int iBin2Dec = W100Utils.bin2Dec(str);
        LogUtil.e("-----by=" + str + "-----" + iBin2Dec);
        device_app_config.app_id_mask = iBin2Dec;
        device_app_config.type = 2;
        LogUtil.d("device_app_config:" + W100Utils.toString(device_app_config));
        return sendMessage(device_app_config);
    }

    public boolean getCommonRemindConfig(int i, OnRemindListener onRemindListener) {
        boolean zSendMessage;
        synchronized (LockerManager.getInstance().getGetContactInfoLocker()) {
            this.mOnRemindListener = onRemindListener;
            Request_get_data request_get_data = new Request_get_data();
            request_get_data.data_catagary = CatagoryEnum.COMMON_REMIND_CONFIG;
            request_get_data.enum_param = i;
            LogUtil.d("源生->获取打开喝水/洗手提醒, :" + W100Utils.toString(request_get_data));
            zSendMessage = sendMessage(request_get_data);
        }
        return zSendMessage;
    }

    public boolean setDeviceLangeuage(int i, int i2) {
        Update_device_resource update_device_resource = new Update_device_resource();
        update_device_resource.resource_id = 65536 * i;
        update_device_resource.location_index = i;
        update_device_resource.update_type = 2;
        LogUtil.d("源生->设置语言:" + W100Utils.toString(update_device_resource));
        return sendMessage((MessageBase) update_device_resource);
    }

    public boolean getAppMarkInfo(OnAppMarkListener onAppMarkListener) {
        boolean zSendMessage;
        synchronized (LockerManager.getInstance().getGetContactInfoLocker()) {
            this.mOnAppListener = onAppMarkListener;
            Request_get_data request_get_data = new Request_get_data();
            request_get_data.data_catagary = CatagoryEnum.DEVICE_APP_CONFIG;
            request_get_data.enum_param = 2;
            LogUtil.d("源生->获取应用市场:" + W100Utils.toString(request_get_data));
            zSendMessage = sendMessage(request_get_data);
        }
        return zSendMessage;
    }

    public boolean sendWeatherInfoToWatch(WeatherEntity weatherEntity) {
        Sensor_data_weather sensor_data_weather = new Sensor_data_weather();
        sensor_data_weather.provider = "花豹提供";
        sensor_data_weather.location = weatherEntity.getData().getTodayWeather().getArea();
        sensor_data_weather.current_weather_type = weatherEntity.getData().getTodayWeather().getWeatherType();
        sensor_data_weather.current_temperature = weatherEntity.getData().getTodayWeather().getCurrentTemperature();
        sensor_data_weather.today_high_temperature = weatherEntity.getData().getTodayWeather().getMaxTemperature();
        sensor_data_weather.today_low_temperature = weatherEntity.getData().getTodayWeather().getMinTemperature();
        sensor_data_weather.daily_Weathers = new Sensor_data_weather.daily_Weather[weatherEntity.getData().getFutureWeather().size()];
        sensor_data_weather.update_timestamp = (int) (System.currentTimeMillis() / 1000);
        for (int i = 0; i < weatherEntity.getData().getFutureWeather().size(); i++) {
            Sensor_data_weather.daily_Weather daily_weather = new Sensor_data_weather.daily_Weather();
            daily_weather.weather_type = weatherEntity.getData().getFutureWeather().get(i).getWeatherType();
            daily_weather.low_temperature = weatherEntity.getData().getFutureWeather().get(i).getMinTemperature();
            daily_weather.high_temperature = weatherEntity.getData().getFutureWeather().get(i).getMaxTemperature();
            sensor_data_weather.daily_Weathers[i] = daily_weather;
        }
        makeMessageToSend(sensor_data_weather, String.valueOf(System.currentTimeMillis() / 1000));
        LogUtil.d("sensor_data_weather:" + W100Utils.toString(sensor_data_weather));
        return sendMessage((MessageBase) sensor_data_weather);
    }

    public boolean sendWeatherMeteorological(WeatherEntity weatherEntity) {
        if (weatherEntity == null || weatherEntity.getData() == null || weatherEntity.getData().getTodayWeather() == null) {
            return false;
        }
        Sensor_data_meteorological sensor_data_meteorological = new Sensor_data_meteorological();
        Sensor_data_meteorological.Meteorological meteorological = new Sensor_data_meteorological.Meteorological();
        meteorological.air_pressure = weatherEntity.getData().getTodayWeather().pressure * 10;
        meteorological.UVI = weatherEntity.getData().getTodayWeather().uvIndex;
        sensor_data_meteorological.datas = new Sensor_data_meteorological.Meteorological[]{meteorological};
        LogUtil.d("sendWeatherMeteorological:" + W100Utils.toString(sensor_data_meteorological));
        return sendMessage((MessageBase) sensor_data_meteorological);
    }

    private void otaResultHandle(CommandBleOTAResult commandBleOTAResult) {
        int i = commandBleOTAResult.func;
        if (i == 0) {
            log("获取otaFileInfo反馈:" + W100Utils.toString(commandBleOTAResult));
            OnCommandBleOTAFileInfoCallBack onCommandBleOTAFileInfoCallBack = this.mOnCommandBleOTAFileInfoCallBack;
            if (onCommandBleOTAFileInfoCallBack != null) {
                onCommandBleOTAFileInfoCallBack.onCommandBleOTAFileInfoCallBack(commandBleOTAResult);
                return;
            }
            return;
        }
        if (i != 1) {
            return;
        }
        log("获取ota每包反馈:" + W100Utils.toString(commandBleOTAResult));
        OnCommandBleOTAEachPackageCallBack onCommandBleOTAEachPackageCallBack = this.mOnCommandBleOTAEachPackageCallBack;
        if (onCommandBleOTAEachPackageCallBack != null) {
            onCommandBleOTAEachPackageCallBack.onCommandBleOTAEachPackageCallBack(commandBleOTAResult);
        }
    }

    public void sendOtaUpdateStartInfo(CommandBleOTAFileInfo commandBleOTAFileInfo, OnCommandBleOTAFileInfoCallBack onCommandBleOTAFileInfoCallBack) {
        log("sendOtaUpdateData--ota确认是否可升级开始发送:" + W100Utils.toString(commandBleOTAFileInfo));
        this.mOnCommandBleOTAFileInfoCallBack = onCommandBleOTAFileInfoCallBack;
        sendMessage((MessageBase) commandBleOTAFileInfo);
    }

    public void sendOtaUpdateDataEachPackage(byte[] bArr, int i, OnCommandBleOTAEachPackageCallBack onCommandBleOTAEachPackageCallBack) {
        this.mOnCommandBleOTAEachPackageCallBack = onCommandBleOTAEachPackageCallBack;
        log("sendOtaUpdateData--ota单个包开始发送,开始发送角标位置:" + i);
        this.mCurrentIndex = i;
        CommandBleOTAFileData commandBleOTAFileData = new CommandBleOTAFileData();
        commandBleOTAFileData.index = i;
        commandBleOTAFileData.file_data = bArr;
        commandBleOTAFileData.md5 = EncryptionUtils.getFileMD5Byte(bArr);
        log("发送ota单个包坐标:" + i + " 单个包内容大小" + commandBleOTAFileData.file_data.length);
        sendMessage((MessageBase) commandBleOTAFileData);
    }

    public void sendOtaUpdateDataEachPackage(byte[] bArr, int i, OnCommandBleOTAEachPackageCallBack onCommandBleOTAEachPackageCallBack, IMessageSendCallback iMessageSendCallback) {
        this.mOnCommandBleOTAEachPackageCallBack = onCommandBleOTAEachPackageCallBack;
        log("sendOtaUpdateData--ota单个包开始发送,开始发送角标位置:" + i);
        this.mCurrentIndex = i;
        CommandBleOTAFileData commandBleOTAFileData = new CommandBleOTAFileData();
        commandBleOTAFileData.index = i;
        commandBleOTAFileData.file_data = bArr;
        commandBleOTAFileData.md5 = EncryptionUtils.getFileMD5Byte(bArr);
        log("发送ota单个包坐标:" + i + " 单个包内容大小" + commandBleOTAFileData.file_data.length);
        sendMessage(commandBleOTAFileData, iMessageSendCallback);
    }

    public void requestWatchVersion() {
        CommandGetWatchVersion commandGetWatchVersion = new CommandGetWatchVersion();
        makeMessageToSend(commandGetWatchVersion, String.valueOf(System.currentTimeMillis() / 1000));
        LogUtil.d("请求软件版本号:" + W100Utils.toString(commandGetWatchVersion));
        sendMessage((MessageBase) commandGetWatchVersion);
    }

    public boolean getWatchInfo(OnWatchInfoListener onWatchInfoListener) {
        boolean zSendMessage;
        synchronized (LockerManager.getInstance().getGetWatchInfoLocker()) {
            this.mOnWatchInfoListener = onWatchInfoListener;
            Request_get_data request_get_data = new Request_get_data();
            request_get_data.data_catagary = CatagoryEnum.DEVICE_BASE_INFO;
            LogUtil.d("源生->获取手表信息:" + W100Utils.toString(request_get_data));
            zSendMessage = sendMessage(request_get_data);
        }
        return zSendMessage;
    }

    public boolean getFallInfo(OnFallListener onFallListener) {
        boolean zSendMessage;
        synchronized (LockerManager.getInstance().getGetFallLocker()) {
            this.mOnFallInfoListener = onFallListener;
            Request_get_data request_get_data = new Request_get_data();
            request_get_data.data_catagary = CatagoryEnum.DEVICE_BASE_INFO;
            LogUtil.d("源生->跌倒检测:" + W100Utils.toString(request_get_data));
            zSendMessage = sendMessage(request_get_data);
        }
        return zSendMessage;
    }

    public boolean getWatchBattery(OnWatchBatteryListener onWatchBatteryListener) {
        this.mOnWatchBatteryListener = onWatchBatteryListener;
        Request_get_data request_get_data = new Request_get_data();
        request_get_data.data_catagary = CatagoryEnum.SENSOR_DATA_BATTERY;
        LogUtil.d("源生->获取手表电量:" + W100Utils.toString(request_get_data));
        return sendMessage(request_get_data);
    }

    public boolean getOilBattery(OnOilBatteryListener onOilBatteryListener) {
        this.mOnOilListener = onOilBatteryListener;
        Request_get_data request_get_data = new Request_get_data();
        request_get_data.data_catagary = CatagoryEnum.CONFIG_ITEMS;
        LogUtil.d("源生->获取手表油量:" + W100Utils.toString(request_get_data));
        return sendMessage(request_get_data);
    }

    public void requestStJournal() {
        CommandJournalRequest commandJournalRequest = new CommandJournalRequest();
        makeMessageToSend(commandJournalRequest, CatagoryEnum.classCatagoryMap.get(Integer.valueOf(commandJournalRequest.catagory)));
        sendMessage((MessageBase) commandJournalRequest);
    }

    public void sendIntegrationTestResultToWatch(int i, String str) {
        CommandAutoTest commandAutoTest = new CommandAutoTest();
        commandAutoTest.state = i;
        commandAutoTest.test_result = str;
        makeMessageToSend(commandAutoTest, String.valueOf(System.currentTimeMillis() / 1000));
        log("sendIntegrationTestResultToWatch:" + W100Utils.toString(commandAutoTest));
        log("整合测试结果:" + str);
        sendMessage((MessageBase) commandAutoTest);
    }

    public void requestSyncDataFromWatch(int i) {
        SyncDataImpl.getInstance().setSyncDataCurrentCategoryId(i);
        Request_get_data request_get_data = new Request_get_data();
        request_get_data.data_catagary = i;
        request_get_data.last_data_timestamp = SyncDataImpl.getInstance().queryLastedTime(i);
        LogUtil.d("数据同步请求数据列表:" + W100Utils.toString(request_get_data));
        sendMessage(request_get_data);
    }

    public void requestSyncDataFromWatchSix(int i) {
        SyncDataImpl.getInstance().setSyncDataCurrentCategoryId(i);
        Request_get_data request_get_data = new Request_get_data();
        request_get_data.data_catagary = CatagoryEnum.SENSOR_DATA_ARRAY;
        request_get_data.enum_param = i;
        request_get_data.last_data_timestamp = SyncDataImpl.getInstance().queryLastedTime(i);
        LogUtil.d("数据同步请求数据列表:" + W100Utils.toString(request_get_data));
        sendMessage(request_get_data);
    }

    public void requestSyncRightNowDataFromWatch(int i, SyncDataImpl.SYNC_RIGHT_NOW_DATA_CONTROL sync_right_now_data_control) {
        Request_get_data request_get_data = new Request_get_data();
        request_get_data.data_catagary = i;
        if (sync_right_now_data_control == SyncDataImpl.SYNC_RIGHT_NOW_DATA_CONTROL.OPEN) {
            request_get_data.last_data_timestamp = Integer.MAX_VALUE;
        } else if (sync_right_now_data_control == SyncDataImpl.SYNC_RIGHT_NOW_DATA_CONTROL.CLOSE) {
            request_get_data.last_data_timestamp = 0;
        }
        LogUtil.d("实时数据请求数据列表:" + W100Utils.toString(request_get_data));
        sendMessage(request_get_data);
    }

    public void log(String str) {
        LogUtil.d(str);
    }

    public boolean setPrayerTime(PrayerTimeEntity prayerTimeEntity, OnMoslemListener onMoslemListener) {
        this.mOnmoslemListener = onMoslemListener;
        Prayer_time_params prayer_time_params = new Prayer_time_params();
        prayer_time_params.calc_method = prayerTimeEntity.calc_method;
        prayer_time_params.asr_juristic = prayerTimeEntity.asr_juristic;
        prayer_time_params.adjust_high_lats = prayerTimeEntity.adjust_high_lats;
        prayer_time_params.reserve1 = prayerTimeEntity.reserve1;
        prayer_time_params.fajr_angle = prayerTimeEntity.fajr_angle;
        prayer_time_params.maghrib_value = prayerTimeEntity.maghrib_value;
        prayer_time_params.isha_value = prayerTimeEntity.isha_value;
        prayer_time_params.maghrib_is_minutes = prayerTimeEntity.maghrib_is_minutes;
        prayer_time_params.reserve2 = prayerTimeEntity.reserve2;
        prayer_time_params.isha_is_minutes = prayerTimeEntity.isha_is_minutes;
        return sendMessage(prayer_time_params);
    }

    public boolean setSensorDataGps(PrayerGpsEntity prayerGpsEntity, OnMoslemGpsListener onMoslemGpsListener) {
        this.mOnMoslemGpsListener = onMoslemGpsListener;
        Sensor_data_gps1 sensor_data_gps1 = new Sensor_data_gps1();
        sensor_data_gps1.latitude = prayerGpsEntity.latitude;
        sensor_data_gps1.longitude = prayerGpsEntity.longitude;
        sensor_data_gps1.satellite_count = prayerGpsEntity.satellite_count;
        sensor_data_gps1.reserve = prayerGpsEntity.reserve;
        return sendMessage((MessageBase) sensor_data_gps1);
    }

    public boolean getWorldTimeInfo(OnWorldListener onWorldListener) {
        boolean zSendMessage;
        synchronized (LockerManager.getInstance().getGetWorldLocaker()) {
            this.mOnWorldListener = onWorldListener;
            Request_get_data request_get_data = new Request_get_data();
            request_get_data.data_catagary = CatagoryEnum.WORLD_CLOCK_CONFIG;
            LogUtil.d("源生->获取手表获取世界时间:" + W100Utils.toString(request_get_data));
            zSendMessage = sendMessage(request_get_data);
        }
        return zSendMessage;
    }

    public boolean setWorldTimeInfo(World_clock_config world_clock_config) {
        LogUtil.d("源生->世界时间:" + W100Utils.toString(world_clock_config));
        return sendMessage(world_clock_config);
    }

    public boolean getContactInfo(OnContactListener onContactListener) {
        boolean zSendMessage;
        synchronized (LockerManager.getInstance().getGetContactInfoLocker()) {
            this.mOnContactListener = onContactListener;
            Request_get_data request_get_data = new Request_get_data();
            request_get_data.data_catagary = CatagoryEnum.COMMON_CONTACT_INFO;
            LogUtil.d("源生->获取手表通讯录:" + W100Utils.toString(request_get_data));
            zSendMessage = sendMessage(request_get_data);
        }
        return zSendMessage;
    }

    public boolean setContactInfo(Common_contact_info common_contact_info) {
        LogUtil.d("源生->手表通讯录:" + W100Utils.toString(common_contact_info));
        return sendMessage(common_contact_info);
    }

    public boolean setQrImages(QrEntity qrEntity, OnQrImageListener onQrImageListener) {
        this.mOnQrImageListener = onQrImageListener;
        Set_qrcode set_qrcode = new Set_qrcode();
        set_qrcode.type = qrEntity.type;
        set_qrcode.text = qrEntity.url;
        return sendMessage(set_qrcode);
    }

    public boolean setMapTrace(Sport_trace sport_trace) {
        LogUtil.d("源生->设置地图轨迹:" + W100Utils.toString(sport_trace));
        return sendMessage((MessageBase) sport_trace);
    }

    public boolean findDevice(int i) {
        LogUtil.d("源生->查找设备:" + W100Utils.toString(Integer.valueOf(i)));
        CommandAction commandAction = new CommandAction();
        commandAction.action_id = i;
        return sendMessage((MessageBase) commandAction);
    }
}
