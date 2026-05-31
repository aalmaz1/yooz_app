package cn.baos.watch.sdk.old.callcontroller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.core.app.ActivityCompat;
import cn.baos.watch.sdk.utils.LogUtil;
import cn.yoozworld.watch.utils.BtConstant;
import com.android.internal.telephony.ITelephony;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class CallStateManager {
    private static CallStateManager instance;
    private CallStateReceiver mCallStateReceiver;
    private Context mContext;
    boolean isQuietByWatch = false;
    int oldRingerMode = 2;

    public static CallStateManager getInstance() {
        if (instance == null) {
            synchronized (CallStateManager.class) {
                if (instance == null) {
                    instance = new CallStateManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        try {
            context.startService(new Intent(context, (Class<?>) CallListenService.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopService() {
        LogUtil.d("终止电话服务监听");
        this.mContext.stopService(new Intent(this.mContext, (Class<?>) CallListenService.class));
    }

    public void registerCallStateListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        CallStateReceiver callStateReceiver = new CallStateReceiver();
        this.mCallStateReceiver = callStateReceiver;
        this.mContext.registerReceiver(callStateReceiver, intentFilter);
    }

    public void unRegisterCallStateListener() {
        this.mContext.unregisterReceiver(this.mCallStateReceiver);
        LogUtil.d("取消来电监听广播");
    }

    public void endCall() {
        try {
            ITelephony.Stub.asInterface((IBinder) Class.forName("android.os.ServiceManager").getMethod("getService", String.class).invoke(null, "phone")).endCall();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e2) {
            LogUtil.d("exception:" + e2.getMessage());
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        } catch (NoSuchMethodException e4) {
            LogUtil.d("exception:" + e4.getMessage());
        } catch (SecurityException e5) {
            e5.printStackTrace();
        } catch (InvocationTargetException e6) {
            e6.printStackTrace();
        } catch (Exception e7) {
            e7.printStackTrace();
        } catch (NoSuchMethodError e8) {
            e8.printStackTrace();
        }
    }

    public static void endCall(Context context) {
        try {
            TelecomManager telecomManager = (TelecomManager) context.getSystemService("telecom");
            if (ActivityCompat.checkSelfPermission(context, "android.permission.ANSWER_PHONE_CALLS") != 0) {
                return;
            }
            telecomManager.endCall();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager == null) {
                return;
            }
            Method declaredMethod = telephonyManager.getClass().getDeclaredMethod("getITelephony", new Class[0]);
            declaredMethod.setAccessible(true);
            if (declaredMethod.invoke(context, new Object[0]) == null) {
                return;
            }
            Method method = context.getClass().getMethod(BtConstant.endCall, new Class[0]);
            method.setAccessible(true);
            method.invoke(context, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void quietCallOn() {
        AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
        this.oldRingerMode = audioManager.getRingerMode();
        LogUtil.d("静音旧模式:" + this.oldRingerMode);
        if (audioManager != null) {
            audioManager.setRingerMode(0);
            audioManager.getStreamVolume(2);
            LogUtil.d("RINGING 已被静音");
        }
        this.isQuietByWatch = true;
    }

    public void quietCallOff() {
        Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() { // from class: cn.baos.watch.sdk.old.callcontroller.CallStateManager.1
            @Override // java.lang.Runnable
            public void run() {
                if (CallStateManager.this.isQuietByWatch) {
                    AudioManager audioManager = (AudioManager) CallStateManager.this.mContext.getSystemService("audio");
                    if (audioManager != null) {
                        audioManager.setRingerMode(CallStateManager.this.oldRingerMode);
                        LogUtil.d("静音恢复静音前模式:" + CallStateManager.this.oldRingerMode);
                        audioManager.getStreamVolume(2);
                        LogUtil.d("RINGING 取消静音,恢复原模式");
                    }
                } else {
                    LogUtil.d("静音未主动设置不做恢复");
                }
                CallStateManager.this.isQuietByWatch = false;
            }
        }, 2L, TimeUnit.SECONDS);
    }

    public void answerRingingCall() {
        try {
            LogUtil.d("4.1之前版本接听电话");
            ITelephony.Stub.asInterface((IBinder) Class.forName("android.os.ServiceManager").getMethod("getService", String.class).invoke(null, "phone")).answerRingingCall();
        } catch (Exception e) {
            e.printStackTrace();
            answerRingingCall_4_1(this.mContext);
        }
    }

    private void answerRingingCall_4_1(Context context) {
        LogUtil.d("4.1之后版本接听电话");
        boolean z = "HTC".equalsIgnoreCase(Build.MANUFACTURER) && !((AudioManager) context.getSystemService("audio")).isWiredHeadsetOn();
        if (z) {
            broadcastHeadsetConnected(context);
        }
        try {
            try {
                Runtime.getRuntime().exec("input keyevent " + Integer.toString(79));
            } catch (IOException unused) {
                Intent intentPutExtra = new Intent("android.intent.action.MEDIA_BUTTON").putExtra("android.intent.extra.KEY_EVENT", new KeyEvent(0, 79));
                Intent intentPutExtra2 = new Intent("android.intent.action.MEDIA_BUTTON").putExtra("android.intent.extra.KEY_EVENT", new KeyEvent(1, 79));
                context.sendOrderedBroadcast(intentPutExtra, "android.permission.CALL_PRIVILEGED");
                context.sendOrderedBroadcast(intentPutExtra2, "android.permission.CALL_PRIVILEGED");
            }
        } finally {
            if (z) {
                broadcastHeadsetConnected(context);
            }
        }
    }

    private void broadcastHeadsetConnected(Context context) {
        Intent intent = new Intent("android.intent.action.HEADSET_PLUG");
        intent.addFlags(BasicMeasure.EXACTLY);
        intent.putExtra("state", 0);
        intent.putExtra("name", "mysms");
        try {
            context.sendOrderedBroadcast(intent, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
