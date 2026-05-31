package cn.baos.watch.sdk.interfac.ble;

import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class ConnectConfig {
    public UUID CHAR_NOTIFICATION_UUID;
    public UUID CHAR_WRITE_WITHOUT_RESPONSE_NOTIFY_UUID;
    public UUID SERVICE_UUID;
    public UUID UUID_CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR;
    public String bleConnectImplName;
    public boolean bond;
    public String deviceAddress;
    public String deviceName;
    public DeviceType deviceType;
    public boolean isActive;
    public boolean isJLW6;
    public boolean isScan = false;
    public String macAddress;
    public int maxBleMtuSize;
    public int minRssi;
    public boolean paired;
    public String peerUUID;

    public enum DeviceType {
        DeviceTypeBT,
        DeviceTypeBle,
        DeviceTypeAny
    }
}
