package cn.baos.watch.sdk.bluetooth.task;

import cn.baos.watch.sdk.bluetooth.bledatahandler.BleDataNotificationCb;
import cn.baos.watch.sdk.manager.jni.BlueToothJniManager;
import cn.baos.watch.sdk.manager.jni.init.IDeviceStatusCallback;
import cn.baos.watch.sdk.util.LogUtil;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class InitBleTask extends Thread {
    public static int blueToothStatus = 1;

    public InitBleTask() {
        super("bluetooth int task");
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        LogUtil.d("初始化蓝牙线程启动");
        try {
            blueToothStatus = 1;
            LogUtil.d("init status:" + BlueToothJniManager.bleInit(new IDeviceStatusCallback() { // from class: cn.baos.watch.sdk.bluetooth.task.InitBleTask.1
                @Override // cn.baos.watch.sdk.manager.jni.init.IDeviceStatusCallback
                public void onDeviceStatus(int i) {
                    LogUtil.d("接收到的status:" + i);
                    if (i == 2) {
                        InitBleTask.blueToothStatus = i;
                        LogUtil.e("蓝牙传输异常:" + InitBleTask.blueToothStatus);
                    }
                }
            }, new BleDataNotificationCb()));
            blueToothStatus = 0;
            LogUtil.d("ble write thread begin to wait data ");
            BlueToothJniManager.handleBleWrite();
            LogUtil.e("底层通信线程退出，需要重新启动app来建立连接！");
            System.exit(-1);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("发送数据线程中断:" + ArrayUtils.toString(e.getStackTrace()));
        }
    }
}
