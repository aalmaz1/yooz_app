package cn.yoozworld.watch.ui;

import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import cn.baos.watch.sdk.base.AppDataConfig;
import cn.baos.watch.sdk.bluetooth.BleService;
import cn.baos.watch.sdk.bluetooth.DataUtils;
import cn.baos.watch.sdk.bluetooth.entity.BleDeviceInfo;
import cn.baos.watch.sdk.bluetooth.reload.BleReloadManager;
import cn.baos.watch.sdk.code.GpsStateManager;
import cn.baos.watch.sdk.code.HttpHandler;
import cn.baos.watch.sdk.code.MainHandler;
import cn.baos.watch.sdk.code.callcontroller.CallStateManager;
import cn.baos.watch.sdk.code.volume.VolumeManager;
import cn.baos.watch.sdk.entitiy.DILanguageEntity;
import cn.baos.watch.sdk.interfac.ble.BtStatusEnum;
import cn.baos.watch.sdk.interfac.ble.ConnectConfig;
import cn.baos.watch.sdk.interfac.ble.HbBtClientManager;
import cn.baos.watch.sdk.interfac.ble.IBindAdapter;
import cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback;
import cn.baos.watch.sdk.interfac.ble.IBtStatusCallback;
import cn.baos.watch.sdk.manager.gps.GpsManager;
import cn.baos.watch.sdk.manager.message.IMessageCallback;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.manager.musiccontroller.MusicControlManager;
import cn.baos.watch.sdk.manager.notification.NotificationHuabaoManager;
import cn.baos.watch.sdk.util.LocalAudioPlayManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SharePreferenceUtils;
import cn.baos.watch.sdk.util.TimeUtils;
import cn.baos.watch.sdk.util.W100Utils;
import cn.baos.watch.w100.messages.Action_sync;
import cn.baos.watch.w100.messages.Device_resource_info;
import cn.baos.watch.w100.messages.Request_get_data;
import cn.baos.watch.w100.messages.User_info_config;
import cn.yoozworld.watch.utils.BtConstant;
import cn.yoozworld.watch.utils.track.SportTraceUtils;
import com.google.gson.Gson;
import com.inuker.bluetooth.library.utils.StringUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SsManager {
    private static final SsManager ourInstance = new SsManager();
    private Context mContext;
    private IBleClientSdkCallback notifcation;
    private int mBtNumber = 0;
    private int sportTime = -1;
    private boolean isManualConnect = false;

    public static SsManager getInstance() {
        return ourInstance;
    }

    private SsManager() {
    }

    class BleEventNotification implements IBleClientSdkCallback {
        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBleDeviceStateChanged(boolean z) {
        }

        BleEventNotification() {
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onGpsNotOpen() {
            BLfLst.isBleConnect = false;
            LogUtil.d("Gps未打开,无法启动扫描");
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$BleEventNotification$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethod(BtConstant.gpsNotOpen, 0);
                }
            });
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$BleEventNotification$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethodEventByJSON(BtConstant.bleStatus, 3);
                }
            });
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBLEStartScan() {
            BLfLst.isBleConnect = false;
            LogUtil.d("蓝牙开始扫描");
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$BleEventNotification$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethodEventByJSON(BtConstant.bleStatus, 0);
                }
            });
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBLEScanning(ScanResult scanResult) {
            BLfLst.isBleConnect = false;
            if (scanResult == null || scanResult.getDevice() == null) {
                return;
            }
            List<ConnectConfig> allListConfig = AppDataConfig.getInstance().getAllListConfig();
            StringBuilder sb = new StringBuilder();
            if (allListConfig != null && allListConfig.size() > 0) {
                Iterator<ConnectConfig> it = allListConfig.iterator();
                while (it.hasNext()) {
                    sb.append(it.next().macAddress);
                }
            }
            String string = sb.toString();
            if (StringUtils.isNotBlank(string) && string.contains(scanResult.getDevice().getAddress())) {
                return;
            }
            final BleDeviceInfo bleDeviceInfo = new BleDeviceInfo();
            bleDeviceInfo.setBondState(scanResult.getDevice().getBondState());
            bleDeviceInfo.setDeviceAddress(scanResult.getDevice().getAddress());
            bleDeviceInfo.setDeviceName(scanResult.getDevice().getName());
            String name = scanResult.getDevice().getName();
            if (!TextUtils.isEmpty(name)) {
                if (name.contains("W200_G50_HB")) {
                    bleDeviceInfo.setDeviceName("W200" + DataUtils.changeMacAddressToFourNumber(scanResult.getDevice().getAddress()));
                } else if (name.contains("QW01")) {
                    bleDeviceInfo.setDeviceName(name);
                }
            }
            bleDeviceInfo.setRssi(scanResult.getRssi());
            bleDeviceInfo.setTimeStamp((int) (System.currentTimeMillis() / 1000));
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$BleEventNotification$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethodEventByJSONRes(BtConstant.BleScanningInfo, new Gson().toJson(bleDeviceInfo));
                }
            });
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBLEStartConnect(String str) {
            BLfLst.isBleConnect = false;
            SsManager.this.isManualConnect = false;
            LogUtil.d(String.format("蓝牙开始连接:%s", str));
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$BleEventNotification$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethodEventByJSON(BtConstant.bleStatus, 0);
                }
            });
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBLEConnecting(String str) {
            BLfLst.isBleConnect = false;
            SsManager.this.isManualConnect = false;
            LogUtil.d(String.format("蓝牙连接中:%s", str));
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$BleEventNotification$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethodEventByJSON(BtConstant.bleStatus, 1);
                }
            });
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBLEConnected() {
            SsManager.this.isManualConnect = false;
            BLfLst.isBleConnect = false;
            LogUtil.d("蓝牙连接成功,进入绑定状态-time-" + TimeUtils.nowTime());
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$BleEventNotification$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethodEventByJSON(BtConstant.bleStatus, 1);
                }
            });
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBLEManualDisConnected() {
            LogUtil.d("蓝牙连接成功,手动断开连接:" + TimeUtils.nowTime());
            SsManager.this.isManualConnect = true;
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBLEConnectFail() {
            BLfLst.isBleConnect = true;
            ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
            if (currentConnectConfig != null && currentConnectConfig.isActive) {
                MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$BleEventNotification$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        BLfLst.getInstance().invokeFlutterMethodEventByJSON(BtConstant.bleStatus, 3);
                    }
                });
            }
            LogUtil.d("蓝牙连接失败");
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBLEDisConnected() {
            ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
            if (currentConnectConfig == null || !currentConnectConfig.isActive) {
                return;
            }
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$BleEventNotification$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethodEventByJSON(BtConstant.bleStatus, 3, BtConstant.bleDisconnectInfo, NotificationCompat.CATEGORY_MESSAGE);
                }
            });
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBLEConnectTimeOut() {
            SsManager.this.isManualConnect = false;
            BLfLst.isBleConnect = true;
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$BleEventNotification$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethodEventByJSON(BtConstant.bleStatus, 5);
                }
            });
            LogUtil.d("蓝牙连接超时");
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback
        public void onBtNoDelDialog() {
            BLfLst.isBleConnect = true;
            LogUtil.d("Bt-未删除成功-系统删除");
        }
    }

    public void initBleServiceManager(Context context) {
        this.mContext = context;
        try {
            context.startService(new Intent(this.mContext, (Class<?>) BleService.class));
            AppDataConfig.getInstance().initData(this.mContext);
            MusicControlManager.getInstance().setContext(this.mContext);
            LocalAudioPlayManager.getInstance().setContext(this.mContext);
            CallStateManager.getInstance().setContext(this.mContext);
            VolumeManager.getInstance().setContext(this.mContext);
            VolumeManager.getInstance().registerReceiver();
            MainHandler.getInstance().setContext(this.mContext);
            HttpHandler.getInstance().setContext(this.mContext);
            GpsManager.getInstance().setContext(this.mContext);
            GpsStateManager.getInstance().register(this.mContext);
            BleReloadManager.getInstance().setContext(this.mContext);
            HbBtClientManager.getInstance().init(this.mContext);
            BleService.getInstance().setIBtBindSdkCallback(new IBtStatusCallback() { // from class: cn.yoozworld.watch.ui.SsManager$$ExternalSyntheticLambda0
                @Override // cn.baos.watch.sdk.interfac.ble.IBtStatusCallback
                public final void onBtStatusChange(BtStatusEnum btStatusEnum) {
                    SsManager.lambda$initBleServiceManager$0(btStatusEnum);
                }
            });
            BleService.getInstance().setIBleBindSdkCallback(new AnonymousClass1(context));
            this.notifcation = new BleEventNotification();
            BleService.getInstance().setSdkNotifcation(this.notifcation);
            MessageManager.getInstance().setContext(this.mContext);
            MessageManager.getInstance().setMessageCallback(new AnonymousClass2());
            boolean zIsBindWatch = AppDataConfig.getInstance().isBindWatch();
            boolean zIsBleOpen = BleService.getInstance().isBleOpen();
            if (zIsBindWatch && zIsBleOpen) {
                LogUtil.e("----app-start-ble-service");
                MainHandler.getInstance().postDelayed(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        BleService.getInstance().startConnect();
                    }
                }, 5000L);
                BLfLst.mPhoneGet = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("BleService 源生android 主页面加载 中 error" + e.getMessage());
        }
    }

    /* JADX INFO: renamed from: cn.yoozworld.watch.ui.SsManager$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$cn$baos$watch$sdk$interfac$ble$BtStatusEnum;

        static {
            int[] iArr = new int[BtStatusEnum.values().length];
            $SwitchMap$cn$baos$watch$sdk$interfac$ble$BtStatusEnum = iArr;
            try {
                iArr[BtStatusEnum.HB_BT_PAIRING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$baos$watch$sdk$interfac$ble$BtStatusEnum[BtStatusEnum.HB_BT_PAIR_FAILED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$cn$baos$watch$sdk$interfac$ble$BtStatusEnum[BtStatusEnum.HB_BT_DIALOG.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$cn$baos$watch$sdk$interfac$ble$BtStatusEnum[BtStatusEnum.HB_BT_PAIRED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    static /* synthetic */ void lambda$initBleServiceManager$0(BtStatusEnum btStatusEnum) {
        int i = AnonymousClass3.$SwitchMap$cn$baos$watch$sdk$interfac$ble$BtStatusEnum[btStatusEnum.ordinal()];
    }

    /* JADX INFO: renamed from: cn.yoozworld.watch.ui.SsManager$1, reason: invalid class name */
    class AnonymousClass1 implements IBindAdapter {
        final /* synthetic */ Context val$context;

        AnonymousClass1(Context context) {
            this.val$context = context;
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBindAdapter
        public void onBindStart(final int i) {
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethodEventByJSON(BtConstant.bleBindCode, i);
                }
            });
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBindAdapter
        public void onBindSuccess() {
            SsManager.this.isManualConnect = false;
            LogUtil.d("-onBindSuccess-");
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethodEventByJSON(BtConstant.bleStatus, 2);
                }
            });
            NotificationHuabaoManager.getInstance().requestRebindNotificationService(this.val$context);
        }

        @Override // cn.baos.watch.sdk.interfac.ble.IBindAdapter
        public void onBindFail() {
            SsManager.this.isManualConnect = false;
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethodEventByJSON(BtConstant.bleStatus, 8);
                }
            });
        }
    }

    /* JADX INFO: renamed from: cn.yoozworld.watch.ui.SsManager$2, reason: invalid class name */
    class AnonymousClass2 implements IMessageCallback {
        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onPhoneStatus(int i) {
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onRequestMeteorologicalData() {
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void requestGetTime() {
        }

        AnonymousClass2() {
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onBindRequestByPhone() {
            LogUtil.d("手表端发起绑定请求，手机端同步为未绑定状态");
            BleService.getInstance().bindDeviceBindRequestByPhone();
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onDeviceResourceInfo(final Device_resource_info device_resource_info) {
            LogUtil.d("手表资源同步:" + new Gson().toJson(device_resource_info));
            if (device_resource_info != null && device_resource_info.resource_type == 2) {
                AppDataConfig.getInstance().put(SharePreferenceUtils.KEY_WATCH_LUANGH, false);
                if (device_resource_info.active_res_location == 393216) {
                    AppDataConfig.getInstance().put(SharePreferenceUtils.KEY_WATCH_LUANGH, true);
                }
                ArrayList arrayList = new ArrayList();
                if (device_resource_info.resource_array != null && device_resource_info.resource_array.length > 0) {
                    for (Device_resource_info.Dev_res dev_res : device_resource_info.resource_array) {
                        DILanguageEntity dILanguageEntity = new DILanguageEntity();
                        dILanguageEntity.resourceId = dev_res.resource_id / 65536;
                        dILanguageEntity.locationIndex = dev_res.location_index;
                        arrayList.add(dILanguageEntity);
                    }
                }
                AppDataConfig.getInstance().put(SharePreferenceUtils.KEY_DEVICE_RESOURCE_LANGUAGE, arrayList.size() > 0 ? new Gson().toJson(arrayList) : "");
            }
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$2$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethodEventByJSONRes(BtConstant.syncDeviceResourceInfo, new Gson().toJson(device_resource_info));
                }
            });
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onRequestWeather() {
            LogUtil.d("手表端发起天气请求");
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onCollectWatchLoggerRequest(final byte[] bArr) {
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$2$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethod(BtConstant.collectWatchLoggerRequest, bArr);
                }
            });
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onCollectWatchLoggerRequestData(String str) {
            LogUtil.d("手表返回日志:" + str);
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onSyncMessageRequest(final byte[] bArr) {
            LogUtil.d("数据同步->onSyncMessageRequest:" + W100Utils.bytesToHexString(bArr));
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$2$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethod(BtConstant.syncMessageRequest, bArr);
                }
            });
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onActionSync(final Action_sync action_sync) {
            if (action_sync != null) {
                try {
                    int i = action_sync.action_type;
                    if (i == 1) {
                        LogUtil.d("onActionSync-运动-采集开始");
                        BleReloadManager.getInstance().startService();
                    } else if (i == 4) {
                        LogUtil.d("onActionSync-运动-采集结束");
                        BleReloadManager.getInstance().stopService();
                        if (action_sync.reserve == 1) {
                            LogUtil.d("onActionSync-运动-采集结束sportTime:" + SsManager.this.sportTime);
                            if (SsManager.this.sportTime > 0) {
                                MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$2$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        this.f$0.lambda$onActionSync$3(action_sync);
                                    }
                                });
                                MainHandler.getInstance().postDelayed(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager.2.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        SsManager.this.sportTime = -1;
                                    }
                                }, 1000L);
                            }
                        }
                    }
                } catch (Exception e) {
                    LogUtil.d("onActionSync-运动-采集报错");
                    LogUtil.d(e.getMessage());
                    e.printStackTrace();
                }
            }
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethod(BtConstant.actionSync, new Gson().toJson(action_sync));
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onActionSync$3(Action_sync action_sync) {
            SportTraceUtils.sendGpsToWatch(SsManager.this.sportTime, action_sync.timestamp);
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onUserInfoConfig(final User_info_config user_info_config) {
            LogUtil.d("个人信息修改-手表发给手机:" + new Gson().toJson(user_info_config));
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$2$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethod(BtConstant.userInfoConfig, new Gson().toJson(user_info_config));
                }
            });
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onRequestGetData(final Request_get_data request_get_data) {
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$2$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethod(BtConstant.requestGetData, new Gson().toJson(request_get_data));
                }
            });
        }

        @Override // cn.baos.watch.sdk.manager.message.IMessageCallback
        public void onRequestGpsData() {
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.SsManager$2$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethod("getGPSBase", null);
                }
            });
        }
    }
}
