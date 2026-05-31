package cn.baos.watch.sdk.bluetooth.bt.callback;

import android.bluetooth.BluetoothDevice;

/* JADX INFO: loaded from: classes.dex */
public interface BaseConfigCallback {
    void onBondStatus(BluetoothDevice bluetoothDevice);

    void onConnect(BluetoothDevice bluetoothDevice);

    void onFindDevice(BluetoothDevice bluetoothDevice);

    void onScanStop();

    void onStateSwitch(int i);
}
