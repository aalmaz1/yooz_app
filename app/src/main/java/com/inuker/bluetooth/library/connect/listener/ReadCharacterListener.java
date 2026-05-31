package com.inuker.bluetooth.library.connect.listener;

import android.bluetooth.BluetoothGattCharacteristic;

/* JADX INFO: loaded from: classes2.dex */
public interface ReadCharacterListener extends GattResponseListener {
    void onCharacteristicRead(BluetoothGattCharacteristic bluetoothGattCharacteristic, int i, byte[] bArr);
}
