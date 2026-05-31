package cn.baos.watch.sdk.manager.ota;

/* JADX INFO: loaded from: classes.dex */
public interface OtaStateCallback {
    void otaFail(int i);

    void otaStart();

    void otaSuccess();
}
