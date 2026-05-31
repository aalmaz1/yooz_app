package cn.baos.watch.sdk.bluetooth.bt;

import android.bluetooth.BluetoothDevice;
import android.text.TextUtils;
import cn.baos.watch.sdk.base.AppDataConfig;
import cn.baos.watch.sdk.bluetooth.BleService;
import cn.baos.watch.sdk.interfac.ble.BtStatusEnum;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.MacUtils;
import cn.baos.watch.sdk.util.SharePreferenceUtils;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class BT300Client {
    public static BT300Client instance;

    public static BT300Client getInstance() {
        if (instance == null) {
            synchronized (BT300Client.class) {
                if (instance == null) {
                    instance = new BT300Client();
                }
            }
        }
        return instance;
    }

    public void connect(String str) {
        bondedDevices(str);
    }

    public void bondedDevices(String str) {
        boolean z;
        boolean z2;
        ArrayList<BluetoothDevice> bondedDevices = BleService.getInstance().getBondedDevices();
        String strBleMacToBtMac = MacUtils.bleMacToBtMac(str);
        AppDataConfig.getInstance().put(SharePreferenceUtils.KEY_CONNECT_BT_MAC, strBleMacToBtMac);
        if (bondedDevices == null || bondedDevices.size() <= 0) {
            z = false;
            z2 = false;
        } else {
            z = false;
            z2 = false;
            for (BluetoothDevice bluetoothDevice : bondedDevices) {
                if (bluetoothDevice != null) {
                    if (bluetoothDevice.getAddress() != null && str.equals(bluetoothDevice.getAddress())) {
                        z2 = true;
                    }
                    if (!TextUtils.isEmpty(bluetoothDevice.getAddress()) && bluetoothDevice.getAddress().equals(strBleMacToBtMac)) {
                        strBleMacToBtMac = bluetoothDevice.getAddress();
                        AppDataConfig.getInstance().put(SharePreferenceUtils.KEY_BOND_BT_SUCCESS, true);
                        AppDataConfig.getInstance().put(SharePreferenceUtils.KEY_CONNECT_BT_MAC, strBleMacToBtMac);
                        z = true;
                    }
                }
            }
        }
        if (z) {
            AppDataConfig.getInstance().put(SharePreferenceUtils.KEY_BOND_BT_SUCCESS, true);
            BleService.getInstance().getBtStatusCallback().onBtStatusChange(BtStatusEnum.HB_BT_PAIRED);
        } else {
            AppDataConfig.getInstance().put(SharePreferenceUtils.KEY_BOND_BT_SUCCESS, false);
            BleService.getInstance().getBtStatusCallback().onBtStatusChange(BtStatusEnum.HB_BT_PAIR_FAILED);
        }
        if (!AppDataConfig.getInstance().isBtDialog() && AppDataConfig.getInstance().isBindWatch()) {
            BleService.getInstance().getBtStatusCallback().onBtStatusChange(BtStatusEnum.HB_BT_DIALOG);
        }
        if (!z2) {
            LogUtil.d("BT300Client-" + str);
            BleService.getInstance().requestConnectBt(str);
        } else {
            BleService.getInstance().bindDevice();
        }
    }

    public void removeBtDevice(String str) {
        try {
            Boolean bool = (Boolean) BluetoothDevice.class.getMethod("removeBond", new Class[0]).invoke(BleService.getInstance().mBluetoothAdapter.getRemoteDevice(MacUtils.bleMacToBtMac(str)), new Object[0]);
            if (bool == null || bool.booleanValue()) {
                return;
            }
            LogUtil.d("bt unbind failed.");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("bt---removeBtDevice--Exception");
        }
    }
}
