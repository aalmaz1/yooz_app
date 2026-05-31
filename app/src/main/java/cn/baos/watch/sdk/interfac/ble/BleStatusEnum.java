package cn.baos.watch.sdk.interfac.ble;

import androidx.core.view.InputDeviceCompat;
import androidx.fragment.app.FragmentTransaction;
import cn.baos.watch.sdk.entitiy.Constant;

/* JADX INFO: loaded from: classes.dex */
public enum BleStatusEnum {
    HB_BLE_CONNECT_FAIL(1),
    HB_BLE_PAIR_FAILED(2),
    HB_BLE_BIND_FAILED(3),
    HB_BLE_PAIR_ING(65545),
    HB_BLE_DISCONNECTED(256),
    HB_BLE_DEVICE_FOUND(257),
    HB_BLE_CONNECTING(Constant.MESSAGE_ID_OTA_CURRENT_VERSION_IS_NEWEST),
    HB_BLE_DISCONNECTING(Constant.MESSAGE_ID_OTA_GET_OTA_VERSION_FAIL),
    HB_BLE_SCANNING(Constant.MESSAGE_ID_OTA_PUSH_TO_WATCH_FINISH),
    HB_BLE_UNBIND_SUCCESS(Constant.MESSAGE_ID_OTA_PUSH_TO_WATCH_FINISH),
    HB_BLE_CONNECT_SUCCESS(4096),
    HB_BLE_PAIRING(FragmentTransaction.TRANSIT_FRAGMENT_OPEN),
    HB_BLE_SERVICE_DISCOVERED(InputDeviceCompat.SOURCE_TOUCHSCREEN),
    HB_BLE_SERVICE_DISCOVEREDING(4102),
    HB_BLE_TX_OPENED(InputDeviceCompat.SOURCE_TOUCHSCREEN),
    HB_BLE_PAIRED(65536),
    HB_BLE_MTU_CHANGING(65537),
    HB_BLE_MTU_CHANGED(65539),
    HB_BLE_BINDING(65541),
    HB_BLE_UNBINDING(65543),
    HB_BLE_BOND(1048576);

    private int mValue;

    BleStatusEnum(int i) {
        this.mValue = i;
    }

    public static boolean isBleConnected(BleStatusEnum bleStatusEnum) {
        return bleStatusEnum != null && bleStatusEnum.mValue > HB_BLE_CONNECT_SUCCESS.mValue;
    }

    public static boolean hasBleConnectError(BleStatusEnum bleStatusEnum) {
        return bleStatusEnum != null && bleStatusEnum.mValue <= HB_BLE_DISCONNECTED.mValue;
    }

    public static boolean hasBleConnectIng(BleStatusEnum bleStatusEnum) {
        int i;
        return bleStatusEnum != null && (i = bleStatusEnum.mValue) > HB_BLE_SERVICE_DISCOVERED.mValue && i <= HB_BLE_MTU_CHANGED.mValue;
    }
}
