package cn.baos.watch.sdk.old;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import cn.baos.watch.sdk.old.mail.SendMailCallback;
import cn.baos.watch.sdk.old.mail.SendMailUtil;
import cn.baos.watch.sdk.utils.FileUtils;
import cn.baos.watch.sdk.utils.LogUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.IOUtils;

/* JADX INFO: loaded from: classes.dex */
public class ProcessCrashLogThread extends Thread {
    public static final String CRASH_LOG_SUFFIX = "crash-";
    private Context mCtx;

    public ProcessCrashLogThread(Context context) {
        this.mCtx = context;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws Throwable {
        File[] fileArrListFiles;
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
        if (!file.exists() || (fileArrListFiles = file.listFiles(new FileFilter() { // from class: cn.baos.watch.sdk.old.ProcessCrashLogThread.1
            @Override // java.io.FileFilter
            public boolean accept(File file2) {
                String name = file2.getName();
                return name.startsWith("crash-") && name.endsWith(".log");
            }
        })) == null || fileArrListFiles.length == 0) {
            return;
        }
        for (final File file2 : fileArrListFiles) {
            try {
                SendMailUtil.send("APP奔溃了: " + file2.getName().replace("crash-", "").replace(".log", ""), readFile(file2), new SendMailCallback() { // from class: cn.baos.watch.sdk.old.ProcessCrashLogThread.2
                    @Override // cn.baos.watch.sdk.old.mail.SendMailCallback
                    public void onSuccess() {
                        Log.e("w100log", "APP奔溃时邮件发送成功: " + file2.getName());
                        file2.delete();
                    }

                    @Override // cn.baos.watch.sdk.old.mail.SendMailCallback
                    public void onFail(Exception exc) {
                        Log.e("w100log", "APP奔溃时邮件发送失败: " + file2.getName());
                    }
                });
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            } catch (IOException e3) {
                e3.printStackTrace();
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
