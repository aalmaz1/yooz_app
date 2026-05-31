package cn.baos.watch.sdk.code;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import cn.baos.watch.sdk.util.FileUtils;
import cn.baos.watch.sdk.util.LogUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/* JADX INFO: loaded from: classes.dex */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String CRASH_REPORTER_EXTENSION = ".cr";
    public static final boolean DEBUG = false;
    private static CrashHandler INSTANCE = null;
    private static final String STACK_TRACE = "STACK_TRACE";
    public static final String TAG = "w100log";
    private static final String VERSION_CODE = "versionCode";
    private static final String VERSION_NAME = "versionName";
    private static Object syncRoot = new Object();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Properties mDeviceCrashInfo = new Properties();

    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (syncRoot) {
                if (INSTANCE == null) {
                    INSTANCE = new CrashHandler();
                }
            }
        }
        return INSTANCE;
    }

    public void init(Context context) {
        this.mContext = context;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        collectCrashDeviceInfo(this.mContext);
        LogUtil.e("Crash Handler Init");
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
        try {
            LogUtil.d("--uncaughtException");
            LogUtil.d("--uncaughtException:" + thread.getStackTrace());
            LogUtil.d("--uncaughtException Throwable:" + th.getMessage());
            LogUtil.d("--uncaughtException Throwable:" + th.getCause());
            LogUtil.d("--uncaughtException Throwable:" + th.getStackTrace());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!handleException(th) && (uncaughtExceptionHandler = this.mDefaultHandler) != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
            return;
        }
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e2) {
            LogUtil.e("Error : " + e2);
        }
        Process.killProcess(Process.myPid());
        System.exit(1);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [cn.baos.watch.sdk.code.CrashHandler$1] */
    private boolean handleException(Throwable th) {
        if (th == null) {
            return true;
        }
        saveCrashInfo(th);
        new Thread() { // from class: cn.baos.watch.sdk.code.CrashHandler.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Looper.prepare();
                if (CrashHandler.this.mContext != null) {
                    Toast toastMakeText = Toast.makeText(CrashHandler.this.mContext, "程序异常，即将退出", 0);
                    toastMakeText.setGravity(17, 0, 0);
                    toastMakeText.show();
                }
                Looper.loop();
            }
        }.start();
        return true;
    }

    private String saveCrashInfo(Throwable th) {
        String str;
        Exception e;
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry entry : this.mDeviceCrashInfo.entrySet()) {
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "\n");
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        for (Throwable cause = th.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        printWriter.close();
        stringBuffer.append(stringWriter.toString());
        try {
            str = "crash-" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".log";
            try {
                String dirAndCreate = FileUtils.getDirAndCreate(this.mContext, "watchSdkLog");
                if (TextUtils.isEmpty(dirAndCreate)) {
                    return null;
                }
                File file = new File(dirAndCreate, str);
                Log.e("w100log", "Crash log: " + file.getAbsolutePath());
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(stringBuffer.toString().getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (Exception e2) {
                e = e2;
                Log.e("w100log", "an error occured while writing file...", e);
            }
        } catch (Exception e3) {
            str = null;
            e = e3;
        }
        return str;
    }

    private String collectCrashDeviceInfo(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 1);
            if (packageInfo != null) {
                this.mDeviceCrashInfo.put(VERSION_NAME, packageInfo.versionName == null ? "not set" : packageInfo.versionName);
                this.mDeviceCrashInfo.put(VERSION_CODE, "" + packageInfo.versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("w100log", "Error while collect package info" + e);
        }
        Field[] declaredFields = Build.class.getDeclaredFields();
        StringBuffer stringBuffer = new StringBuffer();
        for (Field field : declaredFields) {
            try {
                field.setAccessible(true);
                this.mDeviceCrashInfo.put(field.getName(), "" + field.get(null));
            } catch (Exception e2) {
                Log.e("w100log", "Error while collect crash info" + e2);
            }
        }
        return stringBuffer.toString();
    }
}
