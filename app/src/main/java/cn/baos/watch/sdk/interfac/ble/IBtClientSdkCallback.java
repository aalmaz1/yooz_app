package cn.baos.watch.sdk.interfac.ble;

/* JADX INFO: loaded from: classes.dex */
public interface IBtClientSdkCallback {
    void onBleCancel();

    void onBtDialog();

    void onBtPair();

    void onBtPairCancel();

    void onBtPairFail();

    void onBtPairIng();
}
