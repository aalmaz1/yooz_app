package cn.baos.watch.sdk.interfac.ble;

/* JADX INFO: loaded from: classes.dex */
public class BleStatusStateMachine {
    public BleStatusEnum SucceedNextStatus;
    public BleStatusEnum curStatus;
    public BleStatusEnum failedNextStatus;
    boolean isAutoStateChange = true;
    public Runnable stateChangeFailedHandler;
    public Runnable stateChangeHandler;

    public BleStatusStateMachine(BleStatusEnum bleStatusEnum, BleStatusEnum bleStatusEnum2, BleStatusEnum bleStatusEnum3, Runnable runnable, Runnable runnable2) {
        this.curStatus = bleStatusEnum;
        this.failedNextStatus = bleStatusEnum2;
        this.SucceedNextStatus = bleStatusEnum3;
        this.stateChangeHandler = runnable;
        this.stateChangeFailedHandler = runnable2;
    }

    public void setAutoStateChange(boolean z) {
        this.isAutoStateChange = z;
    }

    public boolean isAutoStateChange() {
        return this.isAutoStateChange;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BleStatusStateMachine bleStatusStateMachine = (BleStatusStateMachine) obj;
        return this.curStatus == bleStatusStateMachine.curStatus && this.SucceedNextStatus == bleStatusStateMachine.SucceedNextStatus;
    }
}
