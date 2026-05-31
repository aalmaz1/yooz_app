package cn.baos.watch.sdk.bluetooth.bt.receiver;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import cn.baos.watch.sdk.bluetooth.bt.callback.ScanDeviceCallback;
import cn.baos.watch.sdk.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class BluetoothScanReceiver extends BroadcastReceiver {
    private ScanDeviceCallback mCallback;

    public BluetoothScanReceiver(Context context, ScanDeviceCallback scanDeviceCallback) {
        this.mCallback = scanDeviceCallback;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_STARTED");
        intentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        intentFilter.addAction("android.bluetooth.device.action.FOUND");
        context.registerReceiver(this, intentFilter);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == null) {
        }
        action.hashCode();
        switch (action) {
            case "android.bluetooth.adapter.action.DISCOVERY_FINISHED":
                LogUtil.e("蓝牙扫描结束");
                this.mCallback.onScanStop();
                break;
            case "android.bluetooth.adapter.action.DISCOVERY_STARTED":
                LogUtil.e("蓝牙开始搜索");
                this.mCallback.onScanStart();
                break;
            case "android.bluetooth.device.action.FOUND":
                this.mCallback.onFindDevice((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE"));
                break;
        }
    }
}
