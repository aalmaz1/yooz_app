package cn.baos.watch.sdk.bluetooth;

import cn.baos.watch.sdk.interfac.ble.BleStatusEnum;
import cn.baos.watch.sdk.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class BleConnectProgressMonitor {
    private StateChangeTimeoutConfig curConfig;
    private BleStatusEnum curStatus;
    private long eventTimestamp = System.currentTimeMillis();
    private StateChangeTimeoutConfig mConfig;

    protected void initDefaultTimeoutContext() {
    }

    public static class StateChangeTimeoutConfig {
        private BleStatusEnum curStatus;
        private BleStatusEnum expectStatus;
        int maxRetryCount;
        int retryNum = 0;
        int timeoutMs;

        public StateChangeTimeoutConfig(BleStatusEnum bleStatusEnum, BleStatusEnum bleStatusEnum2, int i, int i2) {
            this.maxRetryCount = i;
            this.timeoutMs = i2;
            this.curStatus = bleStatusEnum;
            this.expectStatus = bleStatusEnum2;
        }
    }

    public BleConnectProgressMonitor() {
        initDefaultTimeoutContext();
    }

    public boolean checkStatusTimeout(StateChangeTimeoutConfig stateChangeTimeoutConfig) {
        LogUtil.d("--mo-checkStatusTimeout-1-" + this.mConfig.curStatus);
        LogUtil.d("--mo-checkStatusTimeout-2-" + stateChangeTimeoutConfig.curStatus);
        this.mConfig.retryNum++;
        LogUtil.d("--mo--checkStatusTimeout--" + this.mConfig.retryNum);
        if (this.mConfig.retryNum > this.mConfig.maxRetryCount) {
            LogUtil.d(String.format("timeout: call:%s,%s", String.valueOf(this.mConfig.curStatus), String.valueOf(this.mConfig.expectStatus)));
            LogUtil.d("--mo--checkStatusTimeout--" + this.mConfig.curStatus + "----" + this.mConfig.expectStatus);
            BleService.getInstance().onCurrectStatusChangeFailed(this.mConfig.curStatus, this.mConfig.expectStatus);
            return true;
        }
        BleService.getInstance().mConnectTask.runSessionAsyncDelayJob(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleConnectProgressMonitor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$checkStatusTimeout$0();
            }
        }, this.mConfig.timeoutMs * 10);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkStatusTimeout$0() {
        checkStatusTimeout(this.mConfig);
    }

    public void monitorTimeOut(StateChangeTimeoutConfig stateChangeTimeoutConfig) {
        LogUtil.d("--monitorTimeOut-a-" + stateChangeTimeoutConfig.curStatus);
        StateChangeTimeoutConfig stateChangeTimeoutConfig2 = this.mConfig;
        if (stateChangeTimeoutConfig2 != null && stateChangeTimeoutConfig2.curStatus == stateChangeTimeoutConfig.curStatus && this.mConfig.expectStatus == stateChangeTimeoutConfig.expectStatus) {
            return;
        }
        this.mConfig = stateChangeTimeoutConfig;
        LogUtil.d("--monitorTimeOut-b-" + stateChangeTimeoutConfig.curStatus);
        this.eventTimestamp = System.currentTimeMillis();
        if (stateChangeTimeoutConfig != null) {
            this.mConfig.retryNum = 0;
            BleService.getInstance().mConnectTask.runSessionAsyncDelayJob(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleConnectProgressMonitor$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$monitorTimeOut$1();
                }
            }, this.mConfig.timeoutMs * 10);
            return;
        }
        throw new RuntimeException("不用处理不关心的状态");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$monitorTimeOut$1() {
        checkStatusTimeout(this.mConfig);
    }
}
