package cn.baos.watch.sdk.manager.location;

import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
public class LocationTimer {
    private static LocationTimer mPhoneBind888Timer;
    private PhoneBind888TimerOutListener mPhoneBind888TimerOutListener;
    private PhoneBind888TimerOutRunnable mPhoneBind888TimerOutRunnable;
    private Timer mTimer = new Timer();

    public interface PhoneBind888TimerOutListener {
        void onCallBack();
    }

    public static LocationTimer getInstance() {
        if (mPhoneBind888Timer == null) {
            synchronized (LocationTimer.class) {
                if (mPhoneBind888Timer == null) {
                    mPhoneBind888Timer = new LocationTimer();
                }
            }
        }
        return mPhoneBind888Timer;
    }

    private LocationTimer() {
    }

    public void startWaitTimeOut(PhoneBind888TimerOutListener phoneBind888TimerOutListener) {
        this.mPhoneBind888TimerOutListener = phoneBind888TimerOutListener;
        PhoneBind888TimerOutRunnable phoneBind888TimerOutRunnable = this.mPhoneBind888TimerOutRunnable;
        if (phoneBind888TimerOutRunnable != null) {
            phoneBind888TimerOutRunnable.setRun(false);
        }
        PhoneBind888TimerOutRunnable phoneBind888TimerOutRunnable2 = new PhoneBind888TimerOutRunnable();
        this.mPhoneBind888TimerOutRunnable = phoneBind888TimerOutRunnable2;
        this.mTimer.schedule(phoneBind888TimerOutRunnable2, 25000L);
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
            if (!this.isRun || LocationTimer.this.mPhoneBind888TimerOutListener == null) {
                return;
            }
            LocationTimer.this.mPhoneBind888TimerOutListener.onCallBack();
        }

        public void setRun(boolean z) {
            this.isRun = z;
        }
    }
}
