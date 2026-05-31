package cn.baos.watch.sdk.bluetooth.bledatahandler;

import cn.baos.watch.sdk.manager.jni.init.IBleDataNotificationCallBack;

/* JADX INFO: loaded from: classes.dex */
public class BleDataNotificationCb implements IBleDataNotificationCallBack {
    private StDateHandler mStDateHandler = new StDateHandler();

    @Override // cn.baos.watch.sdk.manager.jni.init.IBleDataNotificationCallBack
    public boolean onBleDataNotification(int i, int i2) {
        this.mStDateHandler.handleStBleDate(i, i2);
        return true;
    }
}
