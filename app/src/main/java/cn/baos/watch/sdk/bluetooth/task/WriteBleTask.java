package cn.baos.watch.sdk.bluetooth.task;

import cn.baos.watch.sdk.bluetooth.BleService;
import cn.baos.watch.sdk.bluetooth.utils.TimeManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.W100Utils;

/* JADX INFO: loaded from: classes.dex */
public class WriteBleTask extends Thread {
    public boolean clearData() {
        return true;
    }

    public WriteBleTask() {
        super("bluetooth write task");
        LogUtil.e("bluetooth write init\n");
    }

    public boolean writeData(byte[] bArr) {
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            long sendDataTime = BleService.getInstance().getSendDataTime();
            int defTime = TimeManager.getInstance().getDefTime();
            LogUtil.d("通道写数据线程启动 睡眠: def: " + defTime);
            long j = jCurrentTimeMillis - sendDataTime;
            long j2 = defTime;
            if (j < j2) {
                long j3 = j2 - j;
                try {
                    LogUtil.d("通道写数据线程启动 睡眠: sleep: start---- " + j3 + " ms---------->last success：" + sendDataTime + "---- now success:" + jCurrentTimeMillis);
                    Thread.sleep(j3);
                    LogUtil.d("通道写数据线程启动 睡眠: sleep: end---- " + j3 + " ms");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    LogUtil.d("通道写数据线程启动 睡眠: sleep: error---- " + j3 + " ms");
                }
            }
            BleService.getInstance().setSendDataTime(System.currentTimeMillis());
            LogUtil.d("writeData -> 写蓝牙通道数据加一:" + W100Utils.bytesToHexString(bArr));
            LogUtil.d("通道写数据长度:" + bArr.length);
            LogUtil.d("通道写数据状态:" + BleService.getInstance().writeCharacteristic(bArr));
            return true;
        } catch (Exception e2) {
            LogUtil.e(e2.getMessage());
            return false;
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        LogUtil.d("通道写数据线程启动");
    }
}
