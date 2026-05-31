package cn.baos.watch.sdk.bluetooth.bledatahandler;

import cn.baos.watch.sdk.manager.jni.BlueToothJniManager;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class StDateHandler {
    private byte[] mStDataFull;

    public void handleStBleDate(int i, int i2) {
        if (i2 > 25600) {
            LogUtil.d("超过25M,异常数据,不处理");
            return;
        }
        if (this.mStDataFull == null) {
            byte[] bArr = new byte[i2];
            this.mStDataFull = bArr;
            int fromBuffer = BlueToothJniManager.readFromBuffer(i, bArr, i2);
            if (fromBuffer <= 4) {
                LogUtil.e("bug: 没有读到任何的数据");
                this.mStDataFull = null;
            } else {
                LogUtil.d("st:接收到一个新包，message总长度:" + fromBuffer);
                MessageManager.getInstance().receiveMessageFromDevice(this.mStDataFull);
                this.mStDataFull = null;
            }
        }
    }
}
