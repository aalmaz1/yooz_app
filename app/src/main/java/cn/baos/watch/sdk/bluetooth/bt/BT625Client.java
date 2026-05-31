package cn.baos.watch.sdk.bluetooth.bt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import cn.baos.watch.sdk.base.AppDataConfig;
import cn.baos.watch.sdk.bluetooth.BleService;
import cn.baos.watch.sdk.code.MainHandler;
import cn.baos.watch.sdk.interfac.ble.BleStatusEnum;
import cn.baos.watch.sdk.interfac.ble.BtStatusEnum;
import cn.baos.watch.sdk.interfac.ble.ConnectConfig;
import cn.baos.watch.sdk.interfac.ble.HbBtClientManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SharePreferenceUtils;
import com.google.gson.Gson;
import java.lang.reflect.Method;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class BT625Client {
    public static BT625Client instance;
    private BluetoothDevice currentBluetoothDevice;
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    public String mContentDisTip = "connect";
    private int connectBtNumber = 0;
    private final BluetoothProfile.ServiceListener disconnectProfileServiceListener = new BluetoothProfile.ServiceListener() { // from class: cn.baos.watch.sdk.bluetooth.bt.BT625Client.1
        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            if (i == 1) {
                try {
                    BluetoothHeadset bluetoothHeadset = (BluetoothHeadset) bluetoothProfile;
                    boolean zBooleanValue = false;
                    try {
                        Method declaredMethod = bluetoothHeadset.getClass().getDeclaredMethod(BT625Client.this.mContentDisTip, BluetoothDevice.class);
                        declaredMethod.setAccessible(true);
                        zBooleanValue = ((Boolean) declaredMethod.invoke(bluetoothHeadset, BT625Client.this.currentBluetoothDevice)).booleanValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    LogUtil.d("蓝牙：BT -连接-状态 - ：HEADSET:" + zBooleanValue);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    };

    public static BT625Client getInstance() {
        if (instance == null) {
            synchronized (BT625Client.class) {
                if (instance == null) {
                    instance = new BT625Client();
                }
            }
        }
        return instance;
    }

    public void connect(Context context, String str) {
        bondedDevices(context, str, this.mBluetoothAdapter.getRemoteDevice(str));
    }

    public void bondedDevices(final Context context, String str, final BluetoothDevice bluetoothDevice) {
        boolean z;
        ArrayList<BluetoothDevice> bondedDevices = BleService.getInstance().getBondedDevices();
        String str2 = HbBtClientManager.getInstance().getCurrentConnectConfig().macAddress;
        LogUtil.d("蓝牙：连接地址-----macAddress--" + str2);
        LogUtil.d("蓝牙：已配对列表-----bondedDevices--" + new Gson().toJson(bondedDevices));
        if (bondedDevices == null || bondedDevices.size() <= 0) {
            z = false;
        } else {
            z = false;
            for (BluetoothDevice bluetoothDevice2 : bondedDevices) {
                LogUtil.d("蓝牙：已配对 详情-----bondedDevices--device:" + new Gson().toJson(bluetoothDevice2));
                if (bluetoothDevice2 != null && bluetoothDevice2.getAddress() != null && str2.equals(bluetoothDevice2.getAddress())) {
                    AppDataConfig.getInstance().put(SharePreferenceUtils.KEY_BOND_BT_SUCCESS, true);
                    z = true;
                }
            }
        }
        this.connectBtNumber = 0;
        if (z) {
            LogUtil.d("蓝牙：-系统已配对:" + bluetoothDevice.getAddress());
            boolean z2 = BleService.getInstance().isConnectStatus;
            int iBtIsConnect = btIsConnect();
            LogUtil.d("蓝牙：-bt-连接状态-" + iBtIsConnect);
            if (iBtIsConnect == 1) {
                if (z2) {
                    BleService.getInstance().bindDevice();
                    return;
                } else {
                    BleService.getInstance().directConnect();
                    return;
                }
            }
            if (AppDataConfig.getInstance().isDeviceLock() || BleService.getInstance().mBleConnectNum > 2) {
                BleService.getInstance().onDisconnected();
            }
            BleService.getInstance().setManualConnectTime(System.currentTimeMillis());
            LogUtil.d("蓝牙：-bt- 手动断开 BLE-");
            BleService.getInstance().getNotificationHandler().onBLEManualDisConnected();
            MainHandler.getInstance().postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.bt.BT625Client$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$bondedDevices$0(context, bluetoothDevice);
                }
            }, 1000L);
            return;
        }
        BleService.getInstance().getBtStatusCallback().onBtStatusChange(BtStatusEnum.HB_BT_PAIR_FAILED);
        LogUtil.d("蓝牙：-BT-系统未配对");
        bondedNoBindDevices(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bondedDevices$0(Context context, BluetoothDevice bluetoothDevice) {
        connectBt(context, bluetoothDevice);
        restartConnect625bt(context, bluetoothDevice);
    }

    public void bondedNoBindDevices(String str) {
        boolean z = BleService.getInstance().isConnectStatus;
        LogUtil.d("蓝牙：BT-未配对- ble 连接状态=" + z);
        if (z) {
            BleService.getInstance().setManualConnectTime(System.currentTimeMillis());
            LogUtil.d("蓝牙：-bt- 手动断开 BLE-");
            BleService.getInstance().getNotificationHandler().onBLEManualDisConnected();
            BleService.getInstance().onDisconnected();
            bondNoBind(str);
            return;
        }
        BleService.getInstance().requestConnectBt(str);
        BleService.getInstance().mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.bt.BT625Client$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BT625Client.lambda$bondedNoBindDevices$1();
            }
        }, 20000L);
    }

    static /* synthetic */ void lambda$bondedNoBindDevices$1() {
        if (HbBtClientManager.getInstance().getCurrentStatus() == BleStatusEnum.HB_BLE_PAIR_ING) {
            LogUtil.d("蓝牙：BT-兼容BT配对不上 停留界面得情况");
            BleService.getInstance().disconnect();
        }
    }

    private void bondNoBind(final String str) {
        BleService.getInstance().mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.bt.BT625Client$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$bondNoBind$2(str);
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bondNoBind$2(String str) {
        boolean z = BleService.getInstance().isConnectStatus;
        if (BleService.getInstance().isUnbind) {
            return;
        }
        LogUtil.d("蓝牙：BT---ble connect status--" + z);
        if (!z) {
            LogUtil.d("蓝牙：BT---发起系统配对--requestConnectBt---" + str);
            BleService.getInstance().requestConnectBt(str);
        } else {
            bondNoBind(str);
        }
    }

    public void restartConnect625bt(final Context context, final BluetoothDevice bluetoothDevice) {
        LogUtil.d("蓝牙：BT -连接-start");
        BleService.getInstance().mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.bt.BT625Client$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$restartConnect625bt$3(context, bluetoothDevice);
            }
        }, 5000L);
        BleService.getInstance().mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.bt.BT625Client$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                BT625Client.lambda$restartConnect625bt$4();
            }
        }, 20000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$restartConnect625bt$3(Context context, BluetoothDevice bluetoothDevice) {
        if (BleService.getInstance().isUnbind) {
            return;
        }
        int iBtIsConnect = btIsConnect();
        ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
        LogUtil.d("蓝牙：BT -连接-状态a2dpIsConnect：" + iBtIsConnect);
        LogUtil.d("蓝牙：BT --config-" + new Gson().toJson(currentConnectConfig));
        if (currentConnectConfig != null) {
            boolean z = BleService.getInstance().isConnectStatus;
            if (iBtIsConnect == 1) {
                if (z) {
                    BleService.getInstance().bindDevice();
                    return;
                } else {
                    BleService.getInstance().directConnect();
                    return;
                }
            }
            int i = this.connectBtNumber + 1;
            this.connectBtNumber = i;
            if (i < 4) {
                LogUtil.d("蓝牙：BT -连接-状态 - 重新连接：");
                connectBt(context, bluetoothDevice);
                restartConnect625bt(context, bluetoothDevice);
                return;
            }
            BleService.getInstance().onBleDisconnected();
            return;
        }
        LogUtil.d("蓝牙：BT -连接-状态 - 失败");
        BleService.getInstance().onDisconnected();
    }

    static /* synthetic */ void lambda$restartConnect625bt$4() {
        if (HbBtClientManager.getInstance().getCurrentStatus() == BleStatusEnum.HB_BLE_PAIR_ING) {
            BleService.getInstance().notifyBleStatusChange(BleStatusEnum.HB_BLE_DISCONNECTED);
        }
    }

    public int btIsConnect() {
        Method declaredMethod;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        int i = 2;
        try {
            declaredMethod = defaultAdapter.getClass().getDeclaredMethod("getConnectionState", null);
            declaredMethod.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (((Integer) declaredMethod.invoke(defaultAdapter, null)).intValue() == 2) {
            return 1;
        }
        i = 0;
        LogUtil.d("蓝牙：BT -连接-状态 - ：" + i);
        return i;
    }

    public void connectBt(Context context, BluetoothDevice bluetoothDevice) {
        LogUtil.d("蓝牙：BT -连接-状态 - ：connectBt");
        BleService.getInstance().notifyBleStatusChange(BleStatusEnum.HB_BLE_PAIR_ING);
        this.currentBluetoothDevice = bluetoothDevice;
        this.mBluetoothAdapter.getProfileProxy(context, this.disconnectProfileServiceListener, 1);
    }

    public boolean isBondedDevices() {
        boolean z = false;
        try {
            ArrayList<BluetoothDevice> bondedDevices = BleService.getInstance().getBondedDevices();
            String str = HbBtClientManager.getInstance().getCurrentConnectConfig().macAddress;
            LogUtil.d("蓝牙：BT --状态-----macAddress--" + str);
            LogUtil.d("蓝牙：BT --状态--已配对 历史---bondedDevices--" + new Gson().toJson(bondedDevices));
            if (bondedDevices != null && bondedDevices.size() > 0) {
                for (BluetoothDevice bluetoothDevice : bondedDevices) {
                    LogUtil.d("蓝牙：BT --状态--已配对  历史---info--" + new Gson().toJson(bluetoothDevice));
                    if (bluetoothDevice != null && bluetoothDevice.getAddress() != null && str.equals(bluetoothDevice.getAddress())) {
                        z = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z;
    }
}
