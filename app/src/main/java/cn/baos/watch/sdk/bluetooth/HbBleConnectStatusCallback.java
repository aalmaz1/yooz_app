package cn.baos.watch.sdk.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import cn.baos.watch.sdk.interfac.ble.ConnectConfig;
import cn.baos.watch.sdk.interfac.ble.HbBtClientManager;
import cn.baos.watch.sdk.util.HexUtil;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.W100Utils;

/* JADX INFO: loaded from: classes.dex */
public class HbBleConnectStatusCallback extends BluetoothGattCallback {
    public boolean connected;
    private final BleDataReceiver mDataReceiver;
    private final BleConnectEventHandler mEventHandler;
    public boolean mtuChanged;
    public boolean txWritable;
    public boolean writeSucceed;

    public enum BleConnectEvent {
        DATA_ARRIVED,
        WRITE_RESULT,
        MTU_CHANGE_RESULT,
        SERVICE_DISCOVERED,
        TX_WRITABLE,
        CONNECT_STATE_CHANGE
    }

    public interface BleConnectEventHandler {
        void handleBleConnectEvent(HbBleConnectStatusCallback hbBleConnectStatusCallback, BleConnectEvent bleConnectEvent);
    }

    public interface BleDataReceiver {
        boolean receiveData(byte[] bArr);
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
        LogUtil.d("蓝牙onConnectionStateChange status:" + i + " newState:" + i2);
        if (i2 == 2) {
            this.connected = true;
        } else if (i2 == 0) {
            this.connected = false;
        }
        this.mEventHandler.handleBleConnectEvent(this, BleConnectEvent.CONNECT_STATE_CHANGE);
        super.onConnectionStateChange(bluetoothGatt, i, i2);
    }

    public HbBleConnectStatusCallback(BleDataReceiver bleDataReceiver, BleConnectEventHandler bleConnectEventHandler) {
        this.mDataReceiver = bleDataReceiver;
        this.mEventHandler = bleConnectEventHandler;
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
        LogUtil.d("-onServicesDiscovered- status=" + i);
        if (i == 0) {
            ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
            BluetoothGattService service = bluetoothGatt.getService(currentConnectConfig.SERVICE_UUID);
            if (service != null) {
                for (BluetoothGattCharacteristic bluetoothGattCharacteristic : service.getCharacteristics()) {
                    LogUtil.d("蓝牙characteristic.getUuid():" + bluetoothGattCharacteristic.getUuid());
                    if (bluetoothGattCharacteristic.getUuid().equals(currentConnectConfig.CHAR_NOTIFICATION_UUID)) {
                        this.mEventHandler.handleBleConnectEvent(this, BleConnectEvent.SERVICE_DISCOVERED);
                    }
                }
                return;
            }
            LogUtil.d("蓝牙can't find service:" + currentConnectConfig.SERVICE_UUID);
            BleService.getInstance().onDisconnected();
            return;
        }
        LogUtil.d("蓝牙ble service discovery failed");
        BleService.getInstance().onDisconnected();
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
        LogUtil.d("onDescriptorWrite---");
        if (i == 0) {
            this.txWritable = true;
            this.mEventHandler.handleBleConnectEvent(this, BleConnectEvent.TX_WRITABLE);
        } else {
            LogUtil.d("蓝牙监听成功，可写数据,通道关闭--重启服务试试:");
        }
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onMtuChanged(BluetoothGatt bluetoothGatt, int i, int i2) {
        this.mtuChanged = true;
        this.mEventHandler.handleBleConnectEvent(this, BleConnectEvent.MTU_CHANGE_RESULT);
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
        throw new RuntimeException();
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        LogUtil.d("-onCharacteristicChanged--" + HexUtil.formatHexString(bluetoothGattCharacteristic.getValue(), true));
        LogUtil.d("蓝牙测试新版-原始数据:" + W100Utils.byte2hex(bluetoothGattCharacteristic.getValue()));
        this.mEventHandler.handleBleConnectEvent(this, BleConnectEvent.DATA_ARRIVED);
        this.mDataReceiver.receiveData(bluetoothGattCharacteristic.getValue());
    }

    @Override // android.bluetooth.BluetoothGattCallback
    public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
        super.onCharacteristicWrite(bluetoothGatt, bluetoothGattCharacteristic, i);
        if (i == 0) {
            this.writeSucceed = true;
            this.mEventHandler.handleBleConnectEvent(this, BleConnectEvent.WRITE_RESULT);
        } else {
            this.writeSucceed = false;
            LogUtil.d("通道写数据 write fail:" + i);
        }
    }
}
