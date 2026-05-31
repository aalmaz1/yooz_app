package cn.baos.watch.sdk.interfac.ble;

/* JADX INFO: loaded from: classes.dex */
public enum BleNativeStatusEnum {
    HB_BLE_DISCONNECTED(0),
    HB_BLE_WRITABLE(128),
    BLE_NETWORK_DOWN(8);

    public int mValue;

    BleNativeStatusEnum(int i) {
        this.mValue = i;
    }
}
