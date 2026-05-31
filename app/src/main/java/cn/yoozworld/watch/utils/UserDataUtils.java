package cn.yoozworld.watch.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import cn.baos.watch.sdk.code.MainHandler;
import cn.baos.watch.sdk.util.FileUtils;
import cn.baos.watch.sdk.util.LogUtil;
import cn.yoozworld.watch.APP;
import cn.yoozworld.watch.ui.BLfLst;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class UserDataUtils {
    public static void callPhone(Context context, String str) {
        Activity currentActivity = ((APP) context.getApplicationContext()).getMApp().getAppLifecycleTracker().getMCurrentActivity();
        if (currentActivity == null || currentActivity.isDestroyed() || currentActivity.isFinishing()) {
            return;
        }
        currentActivity.startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + str)));
    }

    /* JADX INFO: renamed from: cn.yoozworld.watch.utils.UserDataUtils$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        final /* synthetic */ String val$content;
        final /* synthetic */ Context val$context;

        AnonymousClass1(Context context, String str) {
            this.val$context = context;
            this.val$content = str;
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            String dirAndCreate = FileUtils.getDirAndCreate(this.val$context, "zpl");
            LogUtil.d("写入本地文件目录:" + dirAndCreate);
            if (dirAndCreate == null) {
                LogUtil.d("make dir failed!");
                return;
            }
            final File file = new File(dirAndCreate, "android_flutter" + System.currentTimeMillis() + ".txt");
            LogUtil.d("写入本地文件路径:" + file.getPath());
            if (file.exists()) {
                LogUtil.d("watchType.txt已存在,开始写入");
            } else {
                LogUtil.d("watchType.txt不存在,写入开始");
            }
            BufferedWriter bufferedWriter = null;
            try {
                try {
                    try {
                        BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(file, true));
                        try {
                            bufferedWriter2.write(this.val$content + "\n");
                            LogUtil.d("写入文件内容:\n" + this.val$content);
                            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.utils.UserDataUtils$1$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BLfLst.getInstance().invokeFlutterMethod(BtConstant.crashFlutterLogFile, file.getPath());
                                }
                            });
                            bufferedWriter2.flush();
                            bufferedWriter2.close();
                        } catch (Exception e) {
                            e = e;
                            bufferedWriter = bufferedWriter2;
                            e.printStackTrace();
                            if (bufferedWriter == null) {
                                return;
                            }
                            bufferedWriter.flush();
                            bufferedWriter.close();
                        } catch (Throwable th) {
                            th = th;
                            bufferedWriter = bufferedWriter2;
                            if (bufferedWriter != null) {
                                try {
                                    bufferedWriter.flush();
                                    bufferedWriter.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (IOException e4) {
                e4.printStackTrace();
            }
        }
    }

    public static void writeFlutterCrashToLocal(Context context, String str) {
        new Thread(new AnonymousClass1(context, str)).start();
    }
}
