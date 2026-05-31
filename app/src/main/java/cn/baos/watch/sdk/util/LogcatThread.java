package cn.baos.watch.sdk.util;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes.dex */
public class LogcatThread extends Thread {
    public static final String LOG_DIR_NAME = "watchSdkLog";
    private static final int LOG_FILE_MAX_COUNT = 2;
    private static final int LOG_FILE_MAX_SIZE = 1073741824;
    private static final String LOG_TAG = "w100";
    private static SimpleDateFormat logFileSdf = new SimpleDateFormat("yyyyMMddhhmmss'.log'");
    private String fileLogPath;
    private String filePath;
    private Context mCtx;
    private String mCurFilename = "";
    private boolean runLogFlag = true;
    private boolean runFlag = true;

    public LogcatThread(Context context) {
        this.mCtx = context;
        this.filePath = context.getExternalCacheDir().getAbsolutePath() + "/watchSdkLog";
        LogUtil.e("-----<<<>>>>" + this.filePath);
        if (new File(this.filePath).exists()) {
            return;
        }
        new File(this.filePath).mkdirs();
    }

    public void setLogDir(String str) {
        this.filePath = str;
        if (new File(this.filePath).exists()) {
            return;
        }
        new File(this.filePath).mkdirs();
    }

    public void startrunFlag() {
        this.runLogFlag = false;
    }

    public void endrunFlag() {
        this.runLogFlag = true;
    }

    public void startThread() {
        this.runFlag = true;
        start();
    }

    public void startRunThread() {
        this.runFlag = true;
    }

    public void endThread() {
        this.runFlag = false;
    }

    public String getLogFilePath() {
        return this.fileLogPath;
    }

    public String getmCurFilename() {
        return this.mCurFilename;
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x00c1 A[Catch: Exception -> 0x00c5, TRY_ENTER, TryCatch #5 {Exception -> 0x00c5, blocks: (B:33:0x008a, B:35:0x008f, B:50:0x00c1, B:54:0x00c9), top: B:74:0x008a }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00c9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x000e A[SYNTHETIC] */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void run() throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 237
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.baos.watch.sdk.util.LogcatThread.run():void");
    }

    public void cleanLogFiles() {
        File[] fileArrListFiles;
        String str = this.filePath;
        if (TextUtils.isEmpty(str) || (fileArrListFiles = new File(str).listFiles()) == null || fileArrListFiles.length <= 0) {
            return;
        }
        List listAsList = Arrays.asList(fileArrListFiles);
        LogUtil.d("日志集合的列表:" + ArrayUtils.toString(listAsList));
        Collections.sort(listAsList, new Comparator<File>() { // from class: cn.baos.watch.sdk.util.LogcatThread.1
            @Override // java.util.Comparator
            public int compare(File file, File file2) {
                Date date;
                String name = file.getName();
                String name2 = file2.getName();
                Date date2 = null;
                try {
                    date = LogcatThread.logFileSdf.parse(name);
                    try {
                        date2 = LogcatThread.logFileSdf.parse(name2);
                    } catch (Exception e) {
                        e = e;
                        e.printStackTrace();
                    }
                } catch (Exception e2) {
                    e = e2;
                    date = null;
                }
                if (date == null || date2 == null) {
                    return 1;
                }
                return (int) (date.getTime() - date2.getTime());
            }
        });
        LogUtil.d("日志文件列表:" + ArrayUtils.toString(listAsList));
        if (listAsList.size() > 2) {
            LogUtil.d("delete fname: " + ((File) listAsList.get(0)).getName());
            ((File) listAsList.get(0)).delete();
        }
        if (listAsList.size() > 0) {
            try {
                Iterator it = listAsList.iterator();
                while (it.hasNext()) {
                    ((File) it.next()).delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00bd A[Catch: Exception -> 0x00c1, TRY_ENTER, TRY_LEAVE, TryCatch #4 {Exception -> 0x00c1, blocks: (B:17:0x00ab, B:28:0x00bd), top: B:44:0x0019 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void writeTofile(java.lang.String r10) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 209
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.baos.watch.sdk.util.LogcatThread.writeTofile(java.lang.String):void");
    }

    public ArrayList<String> getAllDataFileName() {
        ArrayList<String> arrayList = new ArrayList<>();
        File[] fileArrListFiles = new File(this.filePath).listFiles();
        if (fileArrListFiles != null && fileArrListFiles.length > 0) {
            for (int i = 0; i < fileArrListFiles.length; i++) {
                if (fileArrListFiles[i].isFile()) {
                    System.out.println("文     件：" + fileArrListFiles[i].getName());
                    String name = fileArrListFiles[i].getName();
                    if (new File(this.filePath + "/" + name).length() <= org.apache.commons.io.FileUtils.ONE_GB) {
                        arrayList.add(this.filePath + "/" + name);
                    }
                }
            }
        }
        return arrayList;
    }
}
