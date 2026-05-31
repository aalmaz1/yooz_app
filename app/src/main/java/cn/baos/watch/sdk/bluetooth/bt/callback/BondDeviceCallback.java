package cn.baos.watch.sdk.bluetooth.bt.callback;

import android.bluetooth.BluetoothDevice;

/* JADX INFO: loaded from: classes.dex */
public interface BondDeviceCallback {
    void bondStatus(BluetoothDevice bluetoothDevice);

    void connectStatus(BluetoothDevice bluetoothDevice);
}
