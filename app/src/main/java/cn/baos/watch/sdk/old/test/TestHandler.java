package cn.baos.watch.sdk.old.test;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import cn.baos.watch.sdk.bluetooth.BleService;
import cn.baos.watch.sdk.constant.Constant;
import cn.baos.watch.sdk.huabaoImpl.translate.TranslateCallback;
import cn.baos.watch.sdk.huabaoImpl.translate.TranslateManager;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.old.MainHandler;
import cn.baos.watch.sdk.utils.LocalAudioPlayManager;
import cn.baos.watch.sdk.utils.LogUtil;
import cn.baos.watch.w100.messages.CommandContentReturnRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.apache.commons.lang3.time.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class TestHandler extends Handler {
    public static int circleMaxTime = 3000;
    private static TestHandler instance;
    Runnable asrTestPlayAudioRunnable;
    Runnable asrTestTimeOutStartRunnable;
    private int audioCycleMaxNum;
    private int audioCycleNoAsrResult;
    private int audioCycleNoPassNum;
    private int audioCycleNum;
    private int audioCyclePassNum;
    private boolean isTransmissionTesting;
    private long mAutoBigTimeEnd;
    private long mAutoBigTimeStart;
    private long mAutoSmallTimeEnd;
    private long mAutoSmallTimeStart;
    private long mAutoTimeDial;
    private int mBigPackageTime;
    private long mBlueConnectTestCurrentTakeTime;
    private long mBlueConnectTestMaxNum;
    private long mBlueConnectTestNum;
    private Context mContext;
    private boolean mIsBigPackageSame;
    private boolean mIsSmallPackageSame;
    private String mOtaFile1;
    private String mOtaFile2;
    private long mSmallPackageCurrentTakeTime;
    private int mSmallPackageTestCycleMaxNum;
    private int mSmallPackageTestCycleNum;
    private int mSmallPackageTestFailNum;
    private int mSmallPackageTestSameNum;
    private int mSmallPackageTestSuccessNum;
    private int mTargetId;
    private long mTimeBigPackageEnd;
    private long mTimeBigPackageStart;
    private long mTimeBlueBreakOff;
    private long mTimeBlueConnectSuccess;
    private long mTimeBlueScanDevicesStartConnect;
    private long mTimeBlueStartScan;
    private long mTimeSmallPackageEnd;
    private long mTimeSmallPackageStart;
    public int monkeyMaxTime;
    Runnable monkeyTestRunnable;
    public int monkeyTestTime;
    private int number_fail;
    private int number_success;
    int progress;
    public int randomCommandMaxTime;
    Runnable randomCommandTestRunnable;
    public int randomCommandTestTime;
    Runnable smallPackageTimeOutRunnable;
    private int delayTimeNum = 0;
    private String textAudioContent = "定一个八点的闹钟";

    private void startMonkeyTestNowSendResultToWatch(int i, String str) {
    }

    public static TestHandler getInstance() {
        if (instance == null) {
            synchronized (TestHandler.class) {
                if (instance == null) {
                    instance = new TestHandler();
                }
            }
        }
        return instance;
    }

    private TestHandler() {
        int i = circleMaxTime;
        this.audioCycleMaxNum = i;
        this.audioCycleNum = 0;
        this.audioCyclePassNum = 0;
        this.audioCycleNoPassNum = 0;
        this.audioCycleNoAsrResult = 0;
        this.mBlueConnectTestMaxNum = i;
        this.mSmallPackageTestCycleMaxNum = i;
        this.mSmallPackageTestCycleNum = 0;
        this.mSmallPackageTestSameNum = 0;
        this.mSmallPackageTestSuccessNum = 0;
        this.mSmallPackageTestFailNum = 0;
        this.monkeyTestTime = 0;
        this.monkeyMaxTime = i;
        this.randomCommandTestTime = 0;
        this.randomCommandMaxTime = i;
        this.isTransmissionTesting = false;
        this.mBigPackageTime = 0;
        this.mAutoBigTimeStart = -1L;
        this.mAutoBigTimeEnd = -1L;
        this.mAutoSmallTimeStart = -1L;
        this.mAutoSmallTimeEnd = -1L;
        this.mAutoTimeDial = -1L;
        this.number_success = 0;
        this.number_fail = 0;
        this.smallPackageTimeOutRunnable = new Runnable() { // from class: cn.baos.watch.sdk.old.test.TestHandler.1
            @Override // java.lang.Runnable
            public void run() {
                new Thread(new Runnable() { // from class: cn.baos.watch.sdk.old.test.TestHandler.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TestHandler.getInstance().obtainMessage(1010).sendToTarget();
                    }
                }).start();
            }
        };
        this.monkeyTestRunnable = new Runnable() { // from class: cn.baos.watch.sdk.old.test.TestHandler.2
            @Override // java.lang.Runnable
            public void run() {
                new Thread(new Runnable() { // from class: cn.baos.watch.sdk.old.test.TestHandler.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TestHandler.getInstance().obtainMessage(1040).sendToTarget();
                    }
                }).start();
            }
        };
        this.randomCommandTestRunnable = new Runnable() { // from class: cn.baos.watch.sdk.old.test.TestHandler.3
            @Override // java.lang.Runnable
            public void run() {
                new Thread(new Runnable() { // from class: cn.baos.watch.sdk.old.test.TestHandler.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TestHandler.getInstance().obtainMessage(1041).sendToTarget();
                    }
                }).start();
            }
        };
        this.asrTestTimeOutStartRunnable = new Runnable() { // from class: cn.baos.watch.sdk.old.test.TestHandler.4
            @Override // java.lang.Runnable
            public void run() {
                new Thread(new Runnable() { // from class: cn.baos.watch.sdk.old.test.TestHandler.4.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TestHandler.getInstance().obtainMessage(1043).sendToTarget();
                    }
                }).start();
            }
        };
        this.asrTestPlayAudioRunnable = new Runnable() { // from class: cn.baos.watch.sdk.old.test.TestHandler.5
            @Override // java.lang.Runnable
            public void run() {
                new Thread(new Runnable() { // from class: cn.baos.watch.sdk.old.test.TestHandler.5.1
                    @Override // java.lang.Runnable
                    public void run() {
                        LocalAudioPlayManager.getInstance().playAudio();
                    }
                }).start();
            }
        };
        this.progress = 0;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        if (Constant.isTestMode) {
            int i = message.what;
            if (i == 1002) {
                log("蓝牙测试:手机关闭蓝牙");
                BleService.getInstance().disconnect();
                this.mTimeBlueBreakOff = System.currentTimeMillis();
                log("蓝牙测试:蓝牙断开,3s后开始重新搜索设备连接:" + this.mTimeBlueBreakOff);
                MainHandler.getInstance().sendMessageDelayed(Message.obtain(MainHandler.getInstance(), 31), 4000L);
                return;
            }
            if (i == 1003) {
                this.mTimeBlueStartScan = System.currentTimeMillis();
                log("蓝牙测试:开始搜索蓝牙设备:" + this.mTimeBlueStartScan);
                return;
            }
            if (i == 1018) {
                this.mTimeBigPackageStart = System.currentTimeMillis();
                TestMessageManager.getInstance().startBigPackageTest(2);
                return;
            }
            if (i == 1019) {
                if (this.mAutoBigTimeStart > 0 && System.currentTimeMillis() - this.mAutoBigTimeStart >= DateUtils.MILLIS_PER_MINUTE) {
                    log("大小包表盘-大包-end：time=" + getTime());
                    this.mAutoSmallTimeStart = System.currentTimeMillis();
                    log("大小包表盘-小包-start：time=" + getTime());
                    getInstance().obtainMessage(1008, 2).sendToTarget();
                    return;
                }
                if (this.delayTimeNum * 100 >= 10000) {
                    this.delayTimeNum = 0;
                }
                this.delayTimeNum++;
                log("大小包间隔时间:" + (this.delayTimeNum * 100));
                this.mTimeBigPackageEnd = System.currentTimeMillis();
                CommandContentReturnRequest commandContentReturnRequest = (CommandContentReturnRequest) message.obj;
                log("st:接收大包文本长度:" + commandContentReturnRequest.content.length());
                log("st:发送大包文本长度:" + TestMessageManager.getInstance().getCurrentBigPackage().length());
                boolean zEquals = commandContentReturnRequest.content.equals(TestMessageManager.getInstance().getCurrentBigPackage());
                this.mIsBigPackageSame = zEquals;
                if (zEquals) {
                    this.mBigPackageTime++;
                    LogUtil.d("st:大包测试bigPackageTest成功,成功次数:" + this.mBigPackageTime + "传输耗时:" + (this.mTimeBigPackageEnd - this.mTimeBigPackageStart) + " 数据是否相同:" + this.mIsBigPackageSame);
                    getInstance().sendEmptyMessageDelayed(1018, this.delayTimeNum * 100);
                    return;
                }
                LogUtil.d("st:大包测试bigPackageTest失败,传输耗时:" + (this.mTimeBigPackageEnd - this.mTimeBigPackageStart) + " 数据是否相同:" + this.mIsBigPackageSame);
                return;
            }
            if (i == 1030) {
                getInstance().obtainMessage(1008, 2).sendToTarget();
                log("稳定性测试开始");
                return;
            }
            if (i == 10001) {
                this.number_success = 0;
                this.number_fail = 0;
                openAssets();
                getInstance().obtainMessage(1018).sendToTarget();
                log("大小包表盘-大包-开始");
                this.mAutoBigTimeStart = System.currentTimeMillis();
                log("大小包表盘-大包-开始：time=" + getTime());
                return;
            }
            switch (i) {
                case 1005:
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    this.mTimeBlueConnectSuccess = jCurrentTimeMillis;
                    this.mBlueConnectTestNum++;
                    this.mBlueConnectTestCurrentTakeTime += jCurrentTimeMillis - this.mTimeBlueStartScan;
                    log("蓝牙测试:蓝牙连接成功,耗时:" + (this.mTimeBlueConnectSuccess - this.mTimeBlueStartScan) + " 测试次数:" + this.mBlueConnectTestNum + "---平均耗时:" + (this.mBlueConnectTestCurrentTakeTime / this.mBlueConnectTestNum));
                    sendMessageDelayed(Message.obtain(this, 1002), 6000L);
                    break;
                case 1006:
                    log("蓝牙测试:规定时间内未扫描到蓝牙设备");
                    break;
                case 1007:
                    this.mTimeBlueScanDevicesStartConnect = System.currentTimeMillis();
                    log("蓝牙测试:搜索到蓝牙设备开始连接:" + this.mTimeBlueScanDevicesStartConnect);
                    break;
                case 1008:
                    this.mTargetId = 0;
                    this.mTimeSmallPackageStart = System.currentTimeMillis();
                    TestMessageManager.getInstance().startSmallPackageTest(this.mTargetId);
                    boolean z = Constant.isShutDownWhenNoEcho;
                    break;
                case 1009:
                    if (this.mAutoSmallTimeStart > 0 && System.currentTimeMillis() - this.mAutoSmallTimeStart >= DateUtils.MILLIS_PER_MINUTE) {
                        log("大小包表盘-小包-end：time=" + getTime());
                        this.mAutoSmallTimeEnd = System.currentTimeMillis();
                        log("大小包表盘-表盘-start：time=" + getTime());
                        this.mAutoTimeDial = System.currentTimeMillis();
                        this.progress = 0;
                        transferFile(this.mOtaFile1, 3);
                    } else {
                        if (this.delayTimeNum * 100 >= 10000) {
                            this.delayTimeNum = 0;
                        }
                        this.delayTimeNum++;
                        log("大小包间隔时间:" + (this.delayTimeNum * 100));
                        this.mTimeSmallPackageEnd = System.currentTimeMillis();
                        CommandContentReturnRequest commandContentReturnRequest2 = (CommandContentReturnRequest) message.obj;
                        log("小包接收文本:" + commandContentReturnRequest2.content);
                        boolean zEquals2 = commandContentReturnRequest2.content.equals(TestMessageManager.getInstance().getCurrentSmallPackage());
                        this.mIsSmallPackageSame = zEquals2;
                        if (zEquals2) {
                            this.mSmallPackageTestSameNum++;
                        }
                        this.mSmallPackageTestSuccessNum++;
                        this.mSmallPackageTestCycleNum++;
                        this.mSmallPackageCurrentTakeTime += this.mTimeSmallPackageEnd - this.mTimeSmallPackageStart;
                        LogUtil.d("小包smallPackageTest耗时:" + (this.mTimeSmallPackageEnd - this.mTimeSmallPackageStart) + " 测试次数:" + this.mSmallPackageTestCycleNum + " echo成功次数:" + this.mSmallPackageTestSuccessNum + " echo test fail time:" + this.mSmallPackageTestFailNum);
                        getInstance().sendEmptyMessageDelayed(1008, this.delayTimeNum * 100);
                    }
                    break;
                case 1010:
                    LogUtil.d("小包超时");
                    this.mSmallPackageTestFailNum++;
                    this.mSmallPackageTestCycleNum++;
                    LogUtil.d("小包small package test fail,time out");
                    MessageManager.getInstance().sendIntegrationTestResultToWatch(Constant.INTEGRATION_RESULT_FAIL, "小包small package test fail,time out");
                    this.mIsSmallPackageSame = false;
                    this.mSmallPackageTestSameNum = 0;
                    this.mSmallPackageTestCycleNum = 0;
                    this.mSmallPackageCurrentTakeTime = 0L;
                    this.mSmallPackageTestSuccessNum = 0;
                    this.mSmallPackageTestFailNum = 0;
                    break;
            }
        }
    }

    public void log(String str) {
        LogUtil.d(str);
    }

    public String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Long.valueOf(System.currentTimeMillis()));
    }

    public void openAssets() {
        try {
            copyAssets("Dragon_v0.0.1.dial.pkg", 0);
            MainHandler.getInstance().postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.old.test.TestHandler.6
                @Override // java.lang.Runnable
                public void run() {
                    TestHandler.this.copyAssets("GoldMountain_v0.0.1.dial.pkg", 1);
                }
            }, DateUtils.MILLIS_PER_MINUTE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void copyAssets(String str, int i) {
        try {
            InputStream inputStreamOpen = this.mContext.getAssets().open(str);
            String str2 = this.mContext.getExternalCacheDir().getAbsolutePath() + "/test";
            LogUtil.e("-----<<<>>>>" + str2);
            if (!new File(str2).exists()) {
                new File(str2).mkdirs();
            }
            File file = new File(str2);
            String str3 = file.getPath() + "/" + str;
            if (!file.exists()) {
                LogUtil.d("file do not exists");
                file.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file + File.separator + str);
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = inputStreamOpen.read(bArr);
                if (i2 == -1) {
                    break;
                } else {
                    fileOutputStream.write(bArr, 0, i2);
                }
            }
            fileOutputStream.close();
            inputStreamOpen.close();
            LogUtil.d("大小包表盘-表盘-FILE=" + str3);
            if (i == 0) {
                this.mOtaFile1 = str3;
            } else {
                this.mOtaFile2 = str3;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void transferFile(String str, int i) {
        String str2;
        LogUtil.d("transferFile--->" + str + "  fileType--->" + i);
        if (str.equals(this.mOtaFile1)) {
            str2 = this.mOtaFile2;
        } else {
            str2 = this.mOtaFile1;
        }
        TranslateManager.getInstance().transferFile(new File(str2), i, new AnonymousClass7(str2, str2));
    }

    /* JADX INFO: renamed from: cn.baos.watch.sdk.old.test.TestHandler$7, reason: invalid class name */
    class AnonymousClass7 implements TranslateCallback {
        final /* synthetic */ String val$finalFilePath;
        final /* synthetic */ String val$finalFilePath1;

        @Override // cn.baos.watch.sdk.huabaoImpl.translate.TranslateCallback
        public void onLoadFile(int i) {
        }

        @Override // cn.baos.watch.sdk.huabaoImpl.translate.TranslateCallback
        public void onLoadFileFail() {
        }

        @Override // cn.baos.watch.sdk.huabaoImpl.translate.TranslateCallback
        public void onTranslateStart() {
        }

        @Override // cn.baos.watch.sdk.huabaoImpl.translate.TranslateCallback
        public void onWaitWatchStartTranslate() {
        }

        AnonymousClass7(String str, String str2) {
            this.val$finalFilePath = str;
            this.val$finalFilePath1 = str2;
        }

        @Override // cn.baos.watch.sdk.huabaoImpl.translate.TranslateCallback
        public void onTransferProgress(int i) {
            TestHandler.this.progress = i;
        }

        @Override // cn.baos.watch.sdk.huabaoImpl.translate.TranslateCallback
        public void onTransferFinish() {
            TestHandler.this.number_success++;
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - TestHandler.this.mAutoTimeDial >= 600000) {
                TestHandler.this.log("大小包表盘-表盘-end：onTransferFinish time=" + TestHandler.this.getTime());
                TestHandler.this.log("大小包表盘-表盘-成功次数：" + TestHandler.this.number_success + "失败次数：" + TestHandler.this.number_fail);
                return;
            }
            TestHandler.this.log("大小包表盘-表盘-剩余时间：onTransferFinish time=" + ((jCurrentTimeMillis - TestHandler.this.mAutoTimeDial) / 1000));
            if (TestHandler.this.progress >= 99) {
                MainHandler mainHandler = MainHandler.getInstance();
                final String str = this.val$finalFilePath;
                mainHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.old.test.TestHandler$7$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onTransferFinish$0(str);
                    }
                }, 15000L);
                TestHandler.this.progress = 0;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTransferFinish$0(String str) {
            TestHandler.this.transferFile(str, 3);
        }

        @Override // cn.baos.watch.sdk.huabaoImpl.translate.TranslateCallback
        public void onTransferFail(int i) {
            TestHandler.this.number_fail++;
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - TestHandler.this.mAutoTimeDial >= 600000) {
                TestHandler.this.log("大小包表盘-表盘-end onTransferFail：time=" + TestHandler.this.getTime());
                return;
            }
            TestHandler.this.log("大小包表盘-表盘-：onTransferFail");
            TestHandler.this.log("大小包表盘-表盘-剩余时间：time=" + ((jCurrentTimeMillis - TestHandler.this.mAutoTimeDial) / 1000));
            MainHandler mainHandler = MainHandler.getInstance();
            final String str = this.val$finalFilePath1;
            mainHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.old.test.TestHandler$7$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onTransferFail$1(str);
                }
            }, 15000L);
            TestHandler.this.progress = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTransferFail$1(String str) {
            TestHandler.this.transferFile(str, 3);
        }
    }
}
