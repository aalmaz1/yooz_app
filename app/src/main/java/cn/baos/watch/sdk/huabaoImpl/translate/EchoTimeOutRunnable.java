package cn.baos.watch.sdk.huabaoImpl.translate;

import cn.baos.watch.sdk.util.LogUtil;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
public class EchoTimeOutRunnable extends TimerTask {
    private boolean isRun = true;
    private EchoTimeOutCallback mEchoTimeOutCallback;

    public EchoTimeOutRunnable(EchoTimeOutCallback echoTimeOutCallback) {
        this.mEchoTimeOutCallback = echoTimeOutCallback;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        if (this.isRun) {
            LogUtil.d("ota发送数据包,30秒内消息一直没有回复，传输超时");
            this.mEchoTimeOutCallback.onEchoTimeOut();
        }
    }

    public void stop() {
        this.isRun = false;
    }
}
