package cn.baos.watch.sdk.interfac.ble;

/* JADX INFO: loaded from: classes.dex */
public interface IBleClient extends IPhoneAdapter {
    void registerBleStatusChangeHandler(IBleStatusCallback iBleStatusCallback);
}
