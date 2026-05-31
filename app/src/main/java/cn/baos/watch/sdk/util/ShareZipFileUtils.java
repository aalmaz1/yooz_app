package cn.baos.watch.sdk.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.core.content.FileProvider;
import com.google.gson.Gson;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class ShareZipFileUtils {
    public static final String LOG_DIR_NAME = "watchSdkZip";
    private static ShareZipFileUtils instance;
    private static SimpleDateFormat logFileSdf = new SimpleDateFormat("yyyyMMddhhmmss'.zip'");
    private static Activity mContext;
    private String filePath;
    private String mCurFilename = "";

    public static ShareZipFileUtils getInstance() {
        if (instance == null) {
            synchronized (ShareZipFileUtils.class) {
                if (instance == null) {
                    instance = new ShareZipFileUtils();
                }
            }
        }
        return instance;
    }

    public void setContext(Activity activity) {
        mContext = activity;
        this.filePath = activity.getExternalCacheDir().getAbsolutePath() + "/watchSdkZip";
        cn.baos.watch.sdk.utils.LogUtil.e("-----<<<>>>>" + this.filePath);
        if (!new File(this.filePath).exists()) {
            new File(this.filePath).mkdirs();
        }
        try {
            this.mCurFilename = logFileSdf.format(new Date());
            String str = this.filePath;
            if (TextUtils.isEmpty(str)) {
                return;
            }
            this.mCurFilename = new File(str, this.mCurFilename).getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shareZipFile(final int i, final ZipShareCallback zipShareCallback) {
        new Thread(new Runnable() { // from class: cn.baos.watch.sdk.util.ShareZipFileUtils$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$shareZipFile$1(zipShareCallback, i);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$shareZipFile$1(final ZipShareCallback zipShareCallback, int i) {
        if (zipShareCallback != null) {
            try {
                zipShareCallback.onShareStart();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        cn.baos.watch.sdk.utils.LogcatThread logcatThread = new cn.baos.watch.sdk.utils.LogcatThread(mContext);
        logcatThread.endThread();
        ArrayList<String> allDataFileName = logcatThread.getAllDataFileName();
        cn.baos.watch.sdk.utils.LogUtil.d("历史文件：" + new Gson().toJson(allDataFileName));
        List<String> listFilterRecentFiles = filterRecentFiles(allDataFileName, i);
        cn.baos.watch.sdk.utils.LogUtil.d("历史文件   批量：" + new Gson().toJson(listFilterRecentFiles));
        try {
            this.mCurFilename = logFileSdf.format(new Date());
            String str = this.filePath;
            if (TextUtils.isEmpty(str)) {
                return;
            } else {
                this.mCurFilename = new File(str, i + "hour:" + this.mCurFilename).getPath();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        cn.baos.watch.sdk.utils.LogUtil.d("---zipStatus-->file=" + listFilterRecentFiles + "----mCurFilename=" + this.mCurFilename);
        cn.baos.watch.sdk.utils.LogUtil.d("---zipStatus-->" + cn.baos.watch.sdk.utils.ZipUtils.zipFiles(listFilterRecentFiles, this.mCurFilename) + "");
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: cn.baos.watch.sdk.util.ShareZipFileUtils$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$shareZipFile$0(zipShareCallback);
            }
        });
        logcatThread.startThread();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$shareZipFile$0(ZipShareCallback zipShareCallback) {
        shareFile(new File(this.mCurFilename));
        if (zipShareCallback != null) {
            zipShareCallback.onShareEnd();
        }
    }

    public static List<String> filterRecentFiles(List<String> list, int i) {
        ArrayList arrayList = new ArrayList();
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (String str : list) {
            String name = new File(str).getName();
            try {
                Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(name);
                long time = jCurrentTimeMillis - (date.getTime() + 0);
                long j = ((long) i) * DateUtils.MILLIS_PER_HOUR;
                cn.baos.watch.sdk.utils.LogUtil.d("File date:  currentTimeMillis: " + jCurrentTimeMillis + "----x:" + date.getTime() + "----" + time + "----hour_x:" + j);
                long j2 = time / DateUtils.MILLIS_PER_HOUR;
                if (time <= j) {
                    arrayList.add(str);
                    cn.baos.watch.sdk.utils.LogUtil.d("File date:  add" + str);
                }
            } catch (ParseException unused) {
                cn.baos.watch.sdk.utils.LogUtil.d("Unable to parse date from file: " + name);
            }
        }
        return arrayList;
    }

    public void shareFile(File file) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            Uri uriForFile = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", file);
            intent.addFlags(268435456);
            intent.putExtra("android.intent.extra.STREAM", uriForFile);
            intent.setType("*/*");
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
