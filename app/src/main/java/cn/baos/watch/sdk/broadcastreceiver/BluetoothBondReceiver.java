package cn.baos.watch.sdk.broadcastreceiver;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import cn.baos.watch.sdk.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class BluetoothBondReceiver extends BroadcastReceiver {
    private BletoothBondResultCallback mBletoothBondResultCallback;

    public interface BletoothBondResultCallback {
        void bleBondResult(int i);
    }

    public BluetoothBondReceiver(BletoothBondResultCallback bletoothBondResultCallback) {
        this.mBletoothBondResultCallback = bletoothBondResultCallback;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        LogUtil.d("蓝牙设备的状态" + bluetoothDevice.getBondState());
        this.mBletoothBondResultCallback.bleBondResult(bluetoothDevice.getBondState());
    }
}
