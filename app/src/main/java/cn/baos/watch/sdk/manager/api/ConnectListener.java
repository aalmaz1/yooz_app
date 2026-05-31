package cn.baos.watch.sdk.manager.api;

/* JADX INFO: loaded from: classes.dex */
public interface ConnectListener {
    void onBLEConnectFail();

    void onBLEConnected();

    void onBLEConnecting();

    void onBLEDisConnected();
}
