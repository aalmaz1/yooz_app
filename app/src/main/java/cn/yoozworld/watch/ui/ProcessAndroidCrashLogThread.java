package cn.yoozworld.watch.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import cn.baos.watch.sdk.util.FileUtils;
import cn.baos.watch.sdk.util.LogUtil;
import cn.yoozworld.watch.utils.BtConstant;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.IOUtils;

/* JADX INFO: loaded from: classes.dex */
public class ProcessAndroidCrashLogThread extends Thread {
    public static final String CRASH_LOG_SUFFIX = "crash-";
    private Context mCtx;

    public ProcessAndroidCrashLogThread(Context context) {
        this.mCtx = context;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        File[] fileArrListFiles;
        if (this.mCtx == null) {
            return;
        }
        LogUtil.e("processCrashedLogs");
        String dirAndCreate = FileUtils.getDirAndCreate(this.mCtx, "watchSdkLog");
        while (TextUtils.isEmpty(dirAndCreate)) {
            dirAndCreate = FileUtils.getDirAndCreate(this.mCtx, "watchSdkLog");
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        File file = new File(dirAndCreate);
        if (!file.exists() || (fileArrListFiles = file.listFiles(new FileFilter() { // from class: cn.yoozworld.watch.ui.ProcessAndroidCrashLogThread.1
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                String name = file2.getName();
                return name.startsWith("crash-") && name.endsWith(".log");
            }
        })) == null || fileArrListFiles.length == 0) {
            return;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        for (final File file2 : fileArrListFiles) {
            LogUtil.d("crash-" + file2.getName().replace("crash-", "").replace(".log", ""));
            handler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.ProcessAndroidCrashLogThread$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethod(BtConstant.crashLogFile, file2.getPath());
                }
            });
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static String readFile(File file) throws Throwable {
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file));
            while (true) {
                try {
                    String line = bufferedReader2.readLine();
                    if (line != null) {
                        stringBuffer.append(line + IOUtils.LINE_SEPARATOR_WINDOWS);
                    } else {
                        bufferedReader2.close();
                        return stringBuffer.toString();
                    }
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
