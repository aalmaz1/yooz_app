package com.inuker.bluetooth.library.receiver.listener;

/* JADX INFO: loaded from: classes2.dex */
public abstract class BleConnectStatusChangeListener extends BluetoothReceiverListener {
    @Override // com.inuker.bluetooth.library.receiver.listener.BluetoothReceiverListener
    public String getName() {
        return "BleConnectStatusChangeListener";
    }

    protected abstract void onConnectStatusChanged(String str, int i);

    @Override // com.inuker.bluetooth.library.receiver.listener.AbsBluetoothListener
    public void onInvoke(Object... objArr) {
        onConnectStatusChanged((String) objArr[0], ((Integer) objArr[1]).intValue());
    }
}
