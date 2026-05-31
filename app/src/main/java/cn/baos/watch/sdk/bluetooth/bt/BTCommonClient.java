package cn.baos.watch.sdk.bluetooth.bt;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;
import cn.baos.watch.sdk.bluetooth.BleService;
import cn.baos.watch.sdk.bluetooth.bt.callback.BondDeviceCallback;
import cn.baos.watch.sdk.interfac.ble.BtStatusEnum;
import cn.baos.watch.sdk.interfac.ble.ConnectConfig;
import cn.baos.watch.sdk.interfac.ble.HbBtClientManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.MacUtils;

/* JADX INFO: loaded from: classes.dex */
public class BTCommonClient {
    public static BTCommonClient instance;
    private ConnectConfig activeConfig;
    private Context mContext;

    public static BTCommonClient getInstance() {
        if (instance == null) {
            synchronized (BTCommonClient.class) {
                if (instance == null) {
                    instance = new BTCommonClient();
                }
            }
        }
        return instance;
    }

    public void initBtClient(Context context, ConnectConfig connectConfig) {
        this.mContext = context;
        this.activeConfig = connectConfig;
        initBindBluetooth();
        requestPairDevice();
    }

    public void requestPairDevice() {
        LogUtil.d("蓝牙：requestPairDevice");
        if (this.activeConfig.isJLW6) {
            BT625Client.getInstance().connect(this.mContext, this.activeConfig.macAddress);
        } else {
            BT625Client.getInstance().connect(this.mContext, this.activeConfig.macAddress);
        }
    }

    public void requestPairBtDevice() {
        if (this.activeConfig.isJLW6) {
            BleService.getInstance().requestConnectBt(this.activeConfig.macAddress);
        } else {
            BleService.getInstance().requestConnectBt(this.activeConfig.macAddress);
        }
    }

    /* JADX INFO: renamed from: cn.baos.watch.sdk.bluetooth.bt.BTCommonClient$1, reason: invalid class name */
    class AnonymousClass1 implements BondDeviceCallback {
        AnonymousClass1() {
        }

        @Override // cn.baos.watch.sdk.bluetooth.bt.callback.BondDeviceCallback
        public void bondStatus(BluetoothDevice bluetoothDevice) {
            ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
            if (bluetoothDevice == null || currentConnectConfig == null || TextUtils.isEmpty(currentConnectConfig.macAddress)) {
                return;
            }
            LogUtil.e("蓝牙：配对状态 -: " + bluetoothDevice.getBondState() + " --mac: " + bluetoothDevice.getAddress() + " (10=取消，11=弹出，12=成功)");
            String strBleMacToBtMac = MacUtils.bleMacToBtMac(BTCommonClient.this.activeConfig.macAddress);
            if (bluetoothDevice.getAddress().equals(currentConnectConfig.macAddress) || bluetoothDevice.getAddress().equals(strBleMacToBtMac)) {
                switch (bluetoothDevice.getBondState()) {
                    case 10:
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        long pairSuccessTime = BleService.getInstance().getPairSuccessTime();
                        long pairPopTime = BleService.getInstance().getPairPopTime();
                        long pairConnectTime = BleService.getInstance().getPairConnectTime();
                        ConnectConfig currentConnectConfig2 = HbBtClientManager.getInstance().getCurrentConnectConfig();
                        HbBtClientManager.getInstance().getCurrentStatus();
                        long j = jCurrentTimeMillis - pairSuccessTime;
                        StringBuilder sbAppend = new StringBuilder("蓝牙：配对状态 -: 上次配对时间：").append(pairSuccessTime).append("-当前时间：").append(jCurrentTimeMillis).append("  time配对 ").append(j).append("  time 弹窗 ");
                        long j2 = jCurrentTimeMillis - pairPopTime;
                        long j3 = jCurrentTimeMillis - pairConnectTime;
                        LogUtil.e(sbAppend.append(j2).append("  time 连接 ").append(j3).toString());
                        if (j < 5000 && !currentConnectConfig2.isActive) {
                            LogUtil.e("蓝牙：配对状态 -: 上次配对时间：" + pairSuccessTime + "-当前时间：" + jCurrentTimeMillis + "检测为异常状态：重新发起 流程");
                            BleService.getInstance().mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.bt.BTCommonClient$1$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BleService.getInstance().directConnect();
                                }
                            }, 1000L);
                        } else if (j2 < 5000 && !currentConnectConfig2.isActive) {
                            LogUtil.e("蓝牙：配对状态 -: 上次弹窗时间：" + pairPopTime + "-当前时间：" + jCurrentTimeMillis + "检测为异常状态：重新发起 连接");
                        } else if (j3 < 5000 && !currentConnectConfig2.isActive) {
                            LogUtil.e("蓝牙：配对状态 -: 上次BT连接时间：" + pairPopTime + "-当前时间：" + jCurrentTimeMillis + "检测为异常状态：重新发起 连接");
                        } else if (BTCommonClient.this.activeConfig.isJLW6) {
                            BleService.getInstance().isConnectStatus = false;
                            BleService.getInstance().onConnectFailed();
                            BleService.getInstance().getNotificationHandler().onBLEDisConnected();
                            BleService.getInstance().isScanAuto = false;
                            if (!currentConnectConfig.isActive) {
                                BleService.getInstance().mConnectTask.cleaSessionAsyncDelayJob();
                            }
                            BleService.getInstance().getBtStatusCallback().onBtStatusChange(BtStatusEnum.HB_BT_PAIR_FAILED);
                        } else {
                            BleService.getInstance().onConnectFailed();
                            BleService.getInstance().getNotificationHandler().onBLEDisConnected();
                            BleService.getInstance().isScanAuto = false;
                            if (!currentConnectConfig.isActive) {
                                BleService.getInstance().mConnectTask.cleaSessionAsyncDelayJob();
                            }
                            BleService.getInstance().getBtStatusCallback().onBtStatusChange(BtStatusEnum.HB_BT_PAIR_FAILED);
                        }
                        break;
                    case 11:
                        BleService.getInstance().setPairPopTime(System.currentTimeMillis());
                        break;
                    case 12:
                        BleService.getInstance().setPairSuccessTime(System.currentTimeMillis());
                        if (BTCommonClient.this.activeConfig.isJLW6) {
                            BleService.getInstance().getBtStatusCallback().onBtStatusChange(BtStatusEnum.HB_BT_PAIRED);
                            BleService.getInstance().mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.bt.BTCommonClient$1$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BleService.getInstance().directConnect();
                                }
                            }, 1000L);
                        } else {
                            BleService.getInstance().getBtStatusCallback().onBtStatusChange(BtStatusEnum.HB_BT_PAIRED);
                            BleService.getInstance().mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.bt.BTCommonClient$1$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BleService.getInstance().directConnect();
                                }
                            }, 1000L);
                        }
                        break;
                }
            }
        }

        @Override // cn.baos.watch.sdk.bluetooth.bt.callback.BondDeviceCallback
        public void connectStatus(BluetoothDevice bluetoothDevice) {
            ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
            if (bluetoothDevice == null || currentConnectConfig == null) {
                return;
            }
            if (!TextUtils.isEmpty(currentConnectConfig.macAddress) || currentConnectConfig.isActive) {
                LogUtil.e("蓝牙：连接状态 --" + bluetoothDevice.getAddress() + "--" + bluetoothDevice.getBondState());
                if (bluetoothDevice.getAddress().equals(currentConnectConfig.macAddress) && bluetoothDevice.getBondState() == 12) {
                    BleService.getInstance().setPairConnectTime(System.currentTimeMillis());
                }
            }
        }
    }

    public void initBindBluetooth() {
        CbtManager.getInstance().init(this.mContext).bondCallBack(new AnonymousClass1()).enableLog(false);
    }
}
