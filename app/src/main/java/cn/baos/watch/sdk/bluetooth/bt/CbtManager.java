package cn.baos.watch.sdk.bluetooth.bt;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import cn.baos.watch.sdk.bluetooth.bt.callback.BaseConfigCallback;
import cn.baos.watch.sdk.bluetooth.bt.callback.BondDeviceCallback;
import cn.baos.watch.sdk.bluetooth.bt.receiver.BluetoothReceiver;
import cn.baos.watch.sdk.bluetooth.bt.utils.CbtLogs;

/* JADX INFO: loaded from: classes.dex */
public class CbtManager implements BaseConfigCallback {
    private BluetoothReceiver mBluetoothReceiver;
    private BondDeviceCallback mBondCallBack;
    private Context mContext;

    @Override // cn.baos.watch.sdk.bluetooth.bt.callback.BaseConfigCallback
    public void onFindDevice(BluetoothDevice bluetoothDevice) {
    }

    @Override // cn.baos.watch.sdk.bluetooth.bt.callback.BaseConfigCallback
    public void onScanStop() {
    }

    @Override // cn.baos.watch.sdk.bluetooth.bt.callback.BaseConfigCallback
    public void onStateSwitch(int i) {
    }

    public static CbtManager getInstance() {
        return CbtManagerHolder.CBT_MANAGER;
    }

    @Override // cn.baos.watch.sdk.bluetooth.bt.callback.BaseConfigCallback
    public void onConnect(BluetoothDevice bluetoothDevice) {
        BondDeviceCallback bondDeviceCallback = this.mBondCallBack;
        if (bondDeviceCallback != null) {
            bondDeviceCallback.connectStatus(bluetoothDevice);
        }
    }

    @Override // cn.baos.watch.sdk.bluetooth.bt.callback.BaseConfigCallback
    public void onBondStatus(BluetoothDevice bluetoothDevice) {
        BondDeviceCallback bondDeviceCallback = this.mBondCallBack;
        if (bondDeviceCallback != null) {
            bondDeviceCallback.bondStatus(bluetoothDevice);
        }
    }

    private static class CbtManagerHolder {
        private static final CbtManager CBT_MANAGER = new CbtManager();

        private CbtManagerHolder() {
        }
    }

    public CbtManager init(Context context) {
        if (this.mContext == null && context != null) {
            this.mContext = context;
            this.mBluetoothReceiver = new BluetoothReceiver(this.mContext, this);
        }
        return this;
    }

    public Context getContext() {
        return this.mContext;
    }

    public CbtManager enableLog(boolean z) {
        CbtLogs.getConfig().setLogSwitch(z).setConsoleSwitch(z);
        return this;
    }

    public CbtManager bondCallBack(BondDeviceCallback bondDeviceCallback) {
        this.mBondCallBack = bondDeviceCallback;
        return this;
    }

    public void onDestroy() {
        this.mContext.unregisterReceiver(this.mBluetoothReceiver);
    }
}
