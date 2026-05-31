package cn.baos.watch.sdk.interfac.ble;

/* JADX INFO: loaded from: classes.dex */
public interface IPhoneAdapter extends IPermissionAdapter {
    boolean requestBTPair(ConnectConfig connectConfig);

    boolean requestBTPairCancel(ConnectConfig connectConfig);

    boolean requestBTScan(ConnectConfig connectConfig, IConnectScanResultCallback iConnectScanResultCallback);

    boolean requestBleBind(ConnectConfig connectConfig);

    boolean requestBleBindCancel(ConnectConfig connectConfig);

    boolean requestBleDisconnect(ConnectConfig connectConfig);

    boolean requestBlePair(ConnectConfig connectConfig);

    boolean requestBlePairCancel(ConnectConfig connectConfig);

    boolean requestBleScan(ConnectConfig connectConfig, IConnectScanResultCallback iConnectScanResultCallback);

    boolean requestBleUnBind(ConnectConfig connectConfig);

    boolean requestBleUnpair(ConnectConfig connectConfig);

    boolean requestBtDisconnect(ConnectConfig connectConfig);

    boolean requestBtUnpair(ConnectConfig connectConfig);
}
