package cn.baos.watch.sdk.manager.api;

import android.bluetooth.le.ScanResult;

/* JADX INFO: loaded from: classes.dex */
public interface ScannerListener {
    void onBLEStartScan();

    void onGpsNotOpen();

    void onScanning(ScanResult scanResult);
}
