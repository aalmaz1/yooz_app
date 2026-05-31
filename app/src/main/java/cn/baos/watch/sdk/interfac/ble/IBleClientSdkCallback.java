package cn.baos.watch.sdk.interfac.ble;

import android.bluetooth.le.ScanResult;

/* JADX INFO: loaded from: classes.dex */
public interface IBleClientSdkCallback {
    void onBLEConnectFail();

    void onBLEConnectTimeOut();

    void onBLEConnected();

    void onBLEConnecting(String str);

    void onBLEDisConnected();

    void onBLEManualDisConnected();

    void onBLEScanning(ScanResult scanResult);

    void onBLEStartConnect(String str);

    void onBLEStartScan();

    void onBleDeviceStateChanged(boolean z);

    void onBtNoDelDialog();

    void onGpsNotOpen();
}
