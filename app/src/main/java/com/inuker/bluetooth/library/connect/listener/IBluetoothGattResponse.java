package com.inuker.bluetooth.library.connect.listener;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;

/* JADX INFO: loaded from: classes2.dex */
public interface IBluetoothGattResponse {
    void onCharacteristicChanged(BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr);

    void onCharacteristicRead(BluetoothGattCharacteristic bluetoothGattCharacteristic, int i, byte[] bArr);

    void onCharacteristicWrite(BluetoothGattCharacteristic bluetoothGattCharacteristic, int i, byte[] bArr);

    void onConnectionStateChange(int i, int i2);

    void onDescriptorRead(BluetoothGattDescriptor bluetoothGattDescriptor, int i, byte[] bArr);

    void onDescriptorWrite(BluetoothGattDescriptor bluetoothGattDescriptor, int i);

    void onMtuChanged(int i, int i2);

    void onReadRemoteRssi(int i, int i2);

    void onServicesDiscovered(int i);
}
