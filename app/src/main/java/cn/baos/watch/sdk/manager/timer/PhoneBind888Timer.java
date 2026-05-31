package cn.baos.watch.sdk.manager.timer;

import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
public class PhoneBind888Timer {
    private static PhoneBind888Timer mPhoneBind888Timer;
    private PhoneBind888TimerOutListener mPhoneBind888TimerOutListener;
    private PhoneBind888TimerOutRunnable mPhoneBind888TimerOutRunnable;
    private Timer mTimer = new Timer();

    public interface PhoneBind888TimerOutListener {
        void onCallBack();
    }

    public static PhoneBind888Timer getInstance() {
        if (mPhoneBind888Timer == null) {
            synchronized (PhoneBind888Timer.class) {
                if (mPhoneBind888Timer == null) {
                    mPhoneBind888Timer = new PhoneBind888Timer();
                }
            }
        }
        return mPhoneBind888Timer;
    }

    private PhoneBind888Timer() {
    }

    public void startWaitTimeOut(PhoneBind888TimerOutListener phoneBind888TimerOutListener) {
        this.mPhoneBind888TimerOutListener = phoneBind888TimerOutListener;
        PhoneBind888TimerOutRunnable phoneBind888TimerOutRunnable = this.mPhoneBind888TimerOutRunnable;
        if (phoneBind888TimerOutRunnable != null) {
            phoneBind888TimerOutRunnable.setRun(false);
        }
        PhoneBind888TimerOutRunnable phoneBind888TimerOutRunnable2 = new PhoneBind888TimerOutRunnable();
        this.mPhoneBind888TimerOutRunnable = phoneBind888TimerOutRunnable2;
        this.mTimer.schedule(phoneBind888TimerOutRunnable2, 3000L);
    }

    public void endWaitTimeOut() {
        PhoneBind888TimerOutRunnable phoneBind888TimerOutRunnable = this.mPhoneBind888TimerOutRunnable;
        if (phoneBind888TimerOutRunnable != null) {
            phoneBind888TimerOutRunnable.setRun(false);
        }
    }

    class PhoneBind888TimerOutRunnable extends TimerTask {
        private boolean isRun = true;

        PhoneBind888TimerOutRunnable() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            if (!this.isRun || PhoneBind888Timer.this.mPhoneBind888TimerOutListener == null) {
                return;
            }
            PhoneBind888Timer.this.mPhoneBind888TimerOutListener.onCallBack();
        }

        public void setRun(boolean z) {
            this.isRun = z;
        }
    }
}
