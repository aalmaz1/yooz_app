package cn.baos.watch.sdk.manager.jni;

import android.os.Process;
import cn.baos.watch.sdk.bluetooth.BleService;
import cn.baos.watch.sdk.bluetooth.BlueToothManager;
import cn.baos.watch.sdk.bluetooth.locker.LockerBleManager;
import cn.baos.watch.sdk.interfac.ble.BleStatusEnum;
import cn.baos.watch.sdk.interfac.ble.HbBtClientManager;
import cn.baos.watch.sdk.manager.jni.init.IBleDataNotificationCallBack;
import cn.baos.watch.sdk.manager.jni.init.IDeviceStatusCallback;
import cn.baos.watch.sdk.manager.jni.write.IWriteAckedAsyncCallback;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SharePreferenceUtils;

/* JADX INFO: loaded from: classes.dex */
public class BlueToothJniManager {
    private static final Object notifier;

    public static native boolean bleFrameArrived(byte[] bArr, int i);

    public static native boolean bleInit(IDeviceStatusCallback iDeviceStatusCallback, IBleDataNotificationCallBack iBleDataNotificationCallBack);

    public static native boolean bleWritableNotify(int i);

    public static native int bleWriteData(int i, byte[] bArr, int i2, IWriteAckedAsyncCallback iWriteAckedAsyncCallback);

    public static String callByC() {
        return "被C代码回调";
    }

    public static native boolean cancelCurrentSendingSession(int i, int i2);

    public static native boolean cleanBleNativeContext();

    public static native boolean handleBleWrite();

    public static native String printStringByJni();

    public static native String printStringByJniCallback();

    public static native int readFromBuffer(int i, byte[] bArr, int i2);

    static {
        System.loadLibrary("JNIControl");
        notifier = new Object();
    }

    public static boolean deviceSendFn(byte[] bArr, int i) {
        LockerBleManager.getBleWriteLockerForC();
        boolean zIsBleConnected = BleStatusEnum.isBleConnected(HbBtClientManager.getInstance().getCurrentStatus());
        boolean connectStatus = HbBtClientManager.getInstance().mBLESPPUtils.getConnectStatus();
        boolean zIsSppTransLateData = MessageManager.getInstance().isSppTransLateData();
        boolean zQueryBooleanByKeySetBoolean = SharePreferenceUtils.queryBooleanByKeySetBoolean(BleService.getInstance().mContext, SharePreferenceUtils.KEY_SPP_IS_OPEN, false);
        LogUtil.d("Bluetooth: bleBond:" + zIsBleConnected + "__sppConnect:" + connectStatus + "__sisSppTransLate:" + zIsSppTransLateData + "---isSppOpen-" + zQueryBooleanByKeySetBoolean);
        if (zIsBleConnected && connectStatus && zIsSppTransLateData && zQueryBooleanByKeySetBoolean) {
            HbBtClientManager.getInstance().mBLESPPUtils.send(bArr);
            return true;
        }
        BlueToothManager.getInstance().writeData(bArr);
        return true;
    }

    public static void notifyBleFlush() {
        LogUtil.d("enter notifyBleFlush");
        Object bleWriteLockerForC = LockerBleManager.getBleWriteLockerForC();
        synchronized (bleWriteLockerForC) {
            bleWriteLockerForC.notify();
        }
    }

    public static void waitforBleWrite() {
        LogUtil.d("enter waitforBleWrite");
        Object bleWriteLockerForC = LockerBleManager.getBleWriteLockerForC();
        try {
            synchronized (bleWriteLockerForC) {
                bleWriteLockerForC.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void threadSleep(int i) {
        LogUtil.d("enter threadSleep,thread id" + Process.myTid() + " 进程id:" + Process.myPid());
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
