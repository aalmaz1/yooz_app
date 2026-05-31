package cn.baos.watch.sdk.bluetooth.task;

import cn.baos.watch.sdk.bluetooth.HbBleConnectStatusCallback;
import cn.baos.watch.sdk.manager.jni.BlueToothJniManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.W100Utils;
import java.util.concurrent.LinkedBlockingQueue;

/* JADX INFO: loaded from: classes.dex */
public class ReceiveBleTask extends Thread implements HbBleConnectStatusCallback.BleDataReceiver {
    public static LinkedBlockingQueue<byte[]> mReceiveDataQueue = new LinkedBlockingQueue<>();

    public ReceiveBleTask() {
        super("bluetooth receive task");
        LogUtil.d("原始数据注入so库线程启动");
    }

    @Override // cn.baos.watch.sdk.bluetooth.HbBleConnectStatusCallback.BleDataReceiver
    public boolean receiveData(byte[] bArr) {
        try {
            LogUtil.d("接收到手表传来的byte数组,存入队列,当前接收消息队列长度:" + mReceiveDataQueue.size() + " 数据长度:" + bArr.length);
            mReceiveDataQueue.put(bArr);
            return true;
        } catch (InterruptedException e) {
            LogUtil.d(e.getMessage());
            return false;
        }
    }

    public boolean clearData() {
        mReceiveDataQueue.clear();
        return true;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws InterruptedException {
        while (true) {
            byte[] bArrTake = new byte[0];
            try {
                bArrTake = mReceiveDataQueue.take();
            } catch (InterruptedException e) {
                LogUtil.d("读取接收数据线程InterruptedException:" + e.getMessage());
                e.printStackTrace();
            }
            LogUtil.d("原始数据注入so库数据长度:" + bArrTake.length);
            LogUtil.d("原始数据注入so库数据:" + W100Utils.byte2hex(bArrTake));
            BlueToothJniManager.bleFrameArrived(bArrTake, bArrTake.length);
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            LogUtil.d("原始数据注入完成");
        }
    }
}
