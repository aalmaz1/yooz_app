package cn.baos.watch.sdk.bluetooth.locker;

/* JADX INFO: loaded from: classes.dex */
public class LockerBleManager {
    private static final Object bleWriteLockerForC = new Object();
    private static final Object mWriteLocker = new Object();
    private static final Object mSendLocker = new Object();

    public static Object getBleWriteLockerForC() {
        return bleWriteLockerForC;
    }

    public static Object getWriteLocker() {
        return mWriteLocker;
    }

    public static Object getSendLocker() {
        return mSendLocker;
    }
}
