package cn.baos.watch.sdk.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import androidx.core.content.FileProvider;
import cn.baos.watch.sdk.util.ZipShareCallback;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class ShareZipFileUtils {
    public static final String LOG_DIR_NAME = "watchSdkZip";
    private static ShareZipFileUtils instance;
    private static SimpleDateFormat logFileSdf = new SimpleDateFormat("yyyyMMddhhmmss'.zip'");
    private String filePath;
    private Activity mContext;
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
        this.mContext = activity;
        this.filePath = activity.getExternalCacheDir().getAbsolutePath() + "/watchSdkZip";
        LogUtil.e("-----<<<>>>>" + this.filePath);
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

    public void shareZipFile(final ZipShareCallback zipShareCallback) {
        new Thread(new Runnable() { // from class: cn.baos.watch.sdk.utils.ShareZipFileUtils$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$shareZipFile$1(zipShareCallback);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$shareZipFile$1(final ZipShareCallback zipShareCallback) {
        if (zipShareCallback != null) {
            try {
                zipShareCallback.onShareStart();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        String str = new LogcatThread(this.mContext).getAllDataFileName().get(0);
        LogUtil.d("---zipStatus-->file=" + str + "----mCurFilename=" + this.mCurFilename);
        LogUtil.d("---zipStatus-->" + ZipUtils.zipFile(str, this.mCurFilename) + "");
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: cn.baos.watch.sdk.utils.ShareZipFileUtils$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$shareZipFile$0(zipShareCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$shareZipFile$0(ZipShareCallback zipShareCallback) {
        shareFile(new File(this.mCurFilename));
        if (zipShareCallback != null) {
            zipShareCallback.onShareEnd();
        }
    }

    public void shareFile(File file) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            Uri uriForFile = FileProvider.getUriForFile(this.mContext, this.mContext.getApplicationContext().getPackageName() + ".provider", file);
            intent.addFlags(268435456);
            intent.putExtra("android.intent.extra.STREAM", uriForFile);
            intent.setType("*/*");
            this.mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
