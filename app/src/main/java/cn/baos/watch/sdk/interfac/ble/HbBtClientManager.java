package cn.baos.watch.sdk.interfac.ble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import cn.baos.watch.sdk.base.AppDataConfig;
import cn.baos.watch.sdk.bluetooth.BleService;
import cn.baos.watch.sdk.bluetooth.bt.BTCommonClient;
import cn.baos.watch.sdk.interfac.ble.ConnectConfig;
import cn.baos.watch.sdk.interfac.spp.BLESPPUtils;
import cn.baos.watch.sdk.manager.jni.BlueToothJniManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.utils.W100Utils;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.io.IOUtils;

/* JADX INFO: loaded from: classes.dex */
public class HbBtClientManager implements IBleStatusCallback, IConnectScanResultCallback, IDeviceAdapter {
    private static HbBtClientManager instance;
    private BleStatusEnum btCurrentStatus;
    private BleStatusEnum btOldStatus;
    private BleStatusEnum currentStatus;
    public BLESPPUtils mBLESPPUtils;
    private Context mContext;
    private BleStatusEnum oldStatus;
    private ArrayList<IBleClient> clients = new ArrayList<>();
    private IBleClient activeClient = null;
    private ConnectConfig activeConfig = null;
    private boolean isScan = false;
    private HashMap<BleStatusEnum, IBleStatusAction> actions = new HashMap<>();

    public interface IBleStatusAction {
        void action();
    }

    public boolean isScan() {
        return this.isScan;
    }

    public void setScan(boolean z) {
        this.isScan = z;
    }

    public ConnectConfig getCurrentConnectConfig() {
        return this.activeConfig;
    }

    @Override // cn.baos.watch.sdk.interfac.ble.IDeviceAdapter
    public void onBlePair() {
        ConnectConfig connectConfig = this.activeConfig;
        if (connectConfig == null || TextUtils.isEmpty(connectConfig.deviceName)) {
            return;
        }
        if (this.activeConfig.deviceName.startsWith("W200")) {
            BleService.getInstance().notifyBleStatusChange(BleStatusEnum.HB_BLE_PAIRED);
        } else {
            BTCommonClient.getInstance().initBtClient(this.mContext, this.activeConfig);
        }
    }

    public static HbBtClientManager getInstance() {
        if (instance == null) {
            synchronized (HbBtClientManager.class) {
                if (instance == null) {
                    instance = new HbBtClientManager();
                }
            }
        }
        return instance;
    }

    public BleStatusEnum getCurrentStatus() {
        return this.currentStatus;
    }

    @Override // cn.baos.watch.sdk.interfac.ble.IBleStatusCallback
    public void onBleStatusChange(BleStatusEnum bleStatusEnum) {
        this.oldStatus = this.currentStatus;
        LogUtil.d("onBleStatusChange:--->" + bleStatusEnum);
        this.currentStatus = bleStatusEnum;
    }

    @Override // cn.baos.watch.sdk.interfac.ble.IConnectScanResultCallback
    public void onBleDeviceDiscoverdAndAction(ConnectConfig connectConfig) {
        if (connectConfig.deviceType == ConnectConfig.DeviceType.DeviceTypeBle) {
            ConnectConfig connectConfig2 = this.activeConfig;
            if (connectConfig2 == null) {
                this.activeConfig = connectConfig;
                return;
            }
            connectConfig2.macAddress = connectConfig.macAddress;
            this.activeConfig.deviceName = connectConfig.deviceName;
            this.activeConfig.deviceType = connectConfig.deviceType;
            this.activeConfig.maxBleMtuSize = connectConfig.maxBleMtuSize;
            this.activeConfig.SERVICE_UUID = connectConfig.SERVICE_UUID;
            this.activeConfig.CHAR_NOTIFICATION_UUID = connectConfig.CHAR_NOTIFICATION_UUID;
            this.activeConfig.CHAR_WRITE_WITHOUT_RESPONSE_NOTIFY_UUID = connectConfig.CHAR_WRITE_WITHOUT_RESPONSE_NOTIFY_UUID;
            this.activeConfig.UUID_CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR = connectConfig.UUID_CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR;
        }
    }

    public void handleBleStatusBleUnbind() {
        try {
            AppDataConfig.getInstance().deleteCurrentConfig(this.activeConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.activeConfig = null;
    }

    public void init(Context context) {
        this.mContext = context;
        AppDataConfig.getInstance().initData(context);
        this.activeConfig = AppDataConfig.getInstance().loadConnectConfig();
        initHb();
        LogUtil.d("HbBtClientManager 初始化 init");
        if (BleService.getInstance().bleStatusHandler == null) {
            BleService.getInstance().init(context);
        }
        this.currentStatus = BleStatusEnum.HB_BLE_DISCONNECTED;
        BleService.getInstance().registerBleStatusCallback(this);
        BleService.getInstance().registerBlePairCallback(this);
        initSpp();
    }

    public void initHb() {
        LogUtil.d("HbBtClientManager 初始化");
        try {
            Bundle bundle = this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 128).metaData;
            if (bundle == null || bundle.getStringArrayList("bleClientList") == null || bundle.getStringArrayList("bleClientList").size() <= 0) {
                return;
            }
            for (String str : bundle.getStringArrayList("bleClientList")) {
                IBleClient iBleClient = (IBleClient) ClassLoader.getSystemClassLoader().loadClass("android.app.admin.DevicePolicyManager").newInstance();
                this.clients.add(iBleClient);
                iBleClient.registerBleStatusChangeHandler(this);
            }
            this.currentStatus = BleStatusEnum.HB_BLE_DISCONNECTED;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("initHb" + e.toString());
        }
    }

    public boolean startConnect(String str, boolean z) {
        ConnectConfig connectConfig = new ConnectConfig();
        this.activeConfig = connectConfig;
        connectConfig.deviceType = ConnectConfig.DeviceType.DeviceTypeBle;
        this.activeConfig.macAddress = str;
        this.activeConfig.isScan = z;
        this.isScan = z;
        LogUtil.d("--hb-startConnect");
        return BleService.getInstance().startConnect();
    }

    public void updateConnectConfig() {
        this.activeConfig = AppDataConfig.getInstance().loadConnectConfig();
    }

    public void updateConnectConfigFalse() {
        ConnectConfig connectConfigLoadConnectConfig = AppDataConfig.getInstance().loadConnectConfig();
        this.activeConfig = connectConfigLoadConnectConfig;
        if (connectConfigLoadConnectConfig != null) {
            connectConfigLoadConnectConfig.isActive = false;
        }
    }

    public void initSpp() {
        try {
            BLESPPUtils bLESPPUtils = new BLESPPUtils(this.mContext, new BLESPPUtils.OnBluetoothAction() { // from class: cn.baos.watch.sdk.interfac.ble.HbBtClientManager.1
                @Override // cn.baos.watch.sdk.interfac.spp.BLESPPUtils.OnBluetoothAction
                public void onConnectFailed(String str) {
                }

                @Override // cn.baos.watch.sdk.interfac.spp.BLESPPUtils.OnBluetoothAction
                public void onConnectSuccess(BluetoothDevice bluetoothDevice) {
                }

                @Override // cn.baos.watch.sdk.interfac.spp.BLESPPUtils.OnBluetoothAction
                public void onFinishFoundDevice() {
                }

                @Override // cn.baos.watch.sdk.interfac.spp.BLESPPUtils.OnBluetoothAction
                public void onFoundDevice(BluetoothDevice bluetoothDevice) {
                }

                @Override // cn.baos.watch.sdk.interfac.spp.BLESPPUtils.OnBluetoothAction
                public void onSendBytes(byte[] bArr) {
                }

                @Override // cn.baos.watch.sdk.interfac.spp.BLESPPUtils.OnBluetoothAction
                public void onReceiveBytes(byte[] bArr) {
                    LogUtil.d("Spp:原始数据注入so库数据长度:" + bArr.length);
                    LogUtil.d("Spp:原始数据注入so库数据:" + W100Utils.byte2hex(bArr));
                    BlueToothJniManager.bleFrameArrived(bArr, bArr.length);
                }
            });
            this.mBLESPPUtils = bLESPPUtils;
            bLESPPUtils.enableBluetooth();
            this.mBLESPPUtils.setStopString(IOUtils.LINE_SEPARATOR_WINDOWS);
            this.mBLESPPUtils.onCreate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
