package cn.baos.watch.sdk.bluetooth.entity;

/* JADX INFO: loaded from: classes.dex */
public class BleDeviceInfo {
    private int bondState;
    private String deviceAddress;
    private String deviceAddressBt;
    private String deviceName;
    private int is300Or200Other;
    private int rssi;
    private int timeStamp;

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String str) {
        this.deviceName = str;
    }

    public String getDeviceAddress() {
        return this.deviceAddress;
    }

    public void setDeviceAddress(String str) {
        this.deviceAddress = str;
    }

    public int getRssi() {
        return this.rssi;
    }

    public void setRssi(int i) {
        this.rssi = i;
    }

    public int getBondState() {
        return this.bondState;
    }

    public void setBondState(int i) {
        this.bondState = i;
    }

    public int getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(int i) {
        this.timeStamp = i;
    }

    public int getIs300Or200Other() {
        return this.is300Or200Other;
    }

    public void setIs300Or200Other(int i) {
        this.is300Or200Other = i;
    }

    public String getDeviceAddressBt() {
        return this.deviceAddressBt;
    }

    public void setDeviceAddressBt(String str) {
        this.deviceAddressBt = str;
    }
}
