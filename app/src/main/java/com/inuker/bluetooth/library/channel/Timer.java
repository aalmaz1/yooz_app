package com.inuker.bluetooth.library.channel;

import android.os.Handler;
import android.os.Looper;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes2.dex */
public class Timer {
    private static TimerCallback mCallback;
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static abstract class TimerCallback implements Runnable {
        private String name;

        public abstract void onTimerCallback() throws TimeoutException;

        public TimerCallback(String str) {
            this.name = str;
        }

        public String getName() {
            return this.name;
        }

        @Override // java.lang.Runnable
        public final void run() {
            BluetoothLog.e(String.format("%s: Timer expired!!!", this.name));
            try {
                onTimerCallback();
            } catch (TimeoutException e) {
                BluetoothLog.e(e);
            }
            Timer.mCallback = null;
        }
    }

    public static synchronized void stop() {
        mHandler.removeCallbacksAndMessages(null);
        mCallback = null;
    }

    public static synchronized boolean isRunning() {
        return mCallback != null;
    }

    public static synchronized String getName() {
        return isRunning() ? mCallback.getName() : "";
    }

    public static synchronized void start(TimerCallback timerCallback, long j) {
        mHandler.removeCallbacksAndMessages(null);
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper == null) {
            looperMyLooper = Looper.getMainLooper();
        }
        Handler handler = new Handler(looperMyLooper);
        mHandler = handler;
        handler.postDelayed(timerCallback, j);
        mCallback = timerCallback;
    }
}
