package cn.baos.watch.sdk.manager.ota;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import cn.baos.watch.sdk.entitiy.Constant;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.w100.messages.CommandWatchVersionResponse;

/* JADX INFO: loaded from: classes.dex */
public class OtaManager {
    private static OtaManager instance;
    private Context mContext;
    private String mOtaVersion;
    private int mMaxOtaTime = 1800000;
    private Handler handler = new Handler(Looper.getMainLooper());
    Runnable otaTimeOutStartRunnable = new Runnable() { // from class: cn.baos.watch.sdk.manager.ota.OtaManager.1
        @Override // java.lang.Runnable
        public void run() {
            new Thread(new Runnable() { // from class: cn.baos.watch.sdk.manager.ota.OtaManager.1.1
                @Override // java.lang.Runnable
                public void run() {
                    LogUtil.d("升级失败超时");
                    OtaManager.this.endWaitOtaResult();
                    OtaManager.this.handler.obtainMessage(257).sendToTarget();
                }
            }).start();
        }
    };

    public void updateOtaState(int i) {
    }

    public static OtaManager getInstance() {
        if (instance == null) {
            synchronized (OtaManager.class) {
                if (instance == null) {
                    instance = new OtaManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        this.mOtaVersion = "";
        updateOtaState(0);
    }

    public void startOta() {
        LogUtil.d("ota状态进入开始状态");
        updateOtaState(1);
        MessageManager.getInstance().requestWatchVersion();
        this.handler.postDelayed(this.otaTimeOutStartRunnable, this.mMaxOtaTime);
    }

    public void handleWatchVersion(CommandWatchVersionResponse commandWatchVersionResponse) {
        int i = commandWatchVersionResponse.catagory;
        if (i == 0) {
            LogUtil.d("ota状态:初始默认状态");
            return;
        }
        if (i == 1) {
            LogUtil.d("ota状态:开始，开始获取服务器列表");
            updateOtaState(0);
            this.handler.obtainMessage(Constant.MESSAGE_ID_OTA_LIST_REQUEST, commandWatchVersionResponse).sendToTarget();
        } else {
            if (i != 2) {
                return;
            }
            this.handler.removeCallbacks(this.otaTimeOutStartRunnable);
            LogUtil.d("ota状态:结果反馈");
            LogUtil.d("升级前版本:" + this.mOtaVersion + " 升级后版本:" + commandWatchVersionResponse.software_ver);
            if (commandWatchVersionResponse.software_ver.equals(this.mOtaVersion)) {
                LogUtil.d("升级失败");
                this.handler.obtainMessage(257).sendToTarget();
            } else {
                LogUtil.d("升级成功");
                this.handler.obtainMessage(256).sendToTarget();
            }
            endWaitOtaResult();
        }
    }

    public void startWaitOtaResult() {
        LogUtil.d("ota状态:进入等待结果");
        updateOtaState(2);
    }

    public void endWaitOtaResult() {
        updateOtaState(0);
    }

    public void log(String str) {
        LogUtil.d(str);
    }
}
