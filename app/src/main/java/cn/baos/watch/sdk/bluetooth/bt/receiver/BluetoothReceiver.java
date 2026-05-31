package cn.baos.watch.sdk.bluetooth.bt.receiver;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import cn.baos.watch.sdk.bluetooth.bt.callback.BaseConfigCallback;
import cn.baos.watch.sdk.bluetooth.bt.utils.CbtLogs;
import cn.baos.watch.sdk.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class BluetoothReceiver extends BroadcastReceiver {
    private BaseConfigCallback mCallback;

    public BluetoothReceiver(Context context, BaseConfigCallback baseConfigCallback) {
        this.mCallback = baseConfigCallback;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        intentFilter.addAction("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
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
                CbtLogs.i("蓝牙扫描结束");
                this.mCallback.onScanStop();
                break;
            case "android.bluetooth.adapter.action.STATE_CHANGED":
                this.mCallback.onStateSwitch(intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0));
                break;
            case "android.bluetooth.device.action.ACL_CONNECTED":
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                LogUtil.e("设备建立连接：" + bluetoothDevice.getBondState());
                this.mCallback.onConnect(bluetoothDevice);
                break;
            case "android.bluetooth.adapter.action.DISCOVERY_STARTED":
                CbtLogs.i("蓝牙开始搜索");
                break;
            case "android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED":
                BluetoothDevice bluetoothDevice2 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                LogUtil.d("STATE: " + intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0));
                LogUtil.d("BluetoothDevice: " + bluetoothDevice2.getName() + ", " + bluetoothDevice2.getAddress());
                break;
            case "android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED":
                BluetoothDevice bluetoothDevice3 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                LogUtil.d("STATE: " + intent.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", 0));
                LogUtil.d("BluetoothDevice: " + bluetoothDevice3.getName() + ", " + bluetoothDevice3.getAddress());
                break;
            case "android.bluetooth.device.action.FOUND":
                this.mCallback.onFindDevice((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE"));
                break;
            case "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED":
                BluetoothDevice bluetoothDevice4 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                CbtLogs.i("STATE: " + intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0));
                CbtLogs.i("BluetoothDevice: " + bluetoothDevice4.getName() + ", " + bluetoothDevice4.getAddress());
                break;
            case "android.bluetooth.device.action.ACL_DISCONNECTED":
                break;
            case "android.bluetooth.device.action.BOND_STATE_CHANGED":
                this.mCallback.onBondStatus((BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE"));
                break;
        }
    }
}
