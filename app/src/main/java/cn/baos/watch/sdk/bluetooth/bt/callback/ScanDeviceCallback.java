package cn.baos.watch.sdk.bluetooth.bt.callback;

import android.bluetooth.BluetoothDevice;

/* JADX INFO: loaded from: classes.dex */
public interface ScanDeviceCallback {
    void onFindDevice(BluetoothDevice bluetoothDevice);

    void onScanStart();

    void onScanStop();
}
