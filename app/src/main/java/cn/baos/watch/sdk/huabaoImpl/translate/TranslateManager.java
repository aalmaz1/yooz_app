package cn.baos.watch.sdk.huabaoImpl.translate;

import androidx.work.WorkRequest;
import cn.baos.watch.sdk.manager.message.IMessageSendCallback;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.W100Utils;
import cn.baos.watch.w100.messages.CommandBleOTAFile;
import cn.baos.watch.w100.messages.CommandBleOTAFileInfo;
import cn.baos.watch.w100.messages.CommandBleOTAResult;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class TranslateManager implements ITranslateManager, OnCommandBleOTAFileInfoCallBack, OnCommandBleOTAEachPackageCallBack {
    public static TranslateManager instance;
    ScheduledExecutorService executor;
    private EchoTimeOutRunnable mEchoTimeOutRunnable;
    private ArrayList<byte[]> mOtaByteArray;
    private File mOtaFile;
    private TranslateCallback mTranslateCallback;
    private boolean stopTransmission = true;
    private boolean isTransFinish = false;
    public int mNowIndex = -1;
    public int mInAdvanceIndex = -1;
    public boolean isSend = true;
    public long mFileSizeLength = 0;
    public int normalSendNum = 0;
    private Timer timer = new Timer();

    public static TranslateManager getInstance() {
        if (instance == null) {
            synchronized (TranslateManager.class) {
                if (instance == null) {
                    instance = new TranslateManager();
                }
            }
        }
        return instance;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.translate.ITranslateManager
    public void transferFile(File file, int i, TranslateCallback translateCallback) {
        this.mNowIndex = -1;
        this.stopTransmission = false;
        this.isTransFinish = false;
        this.mTranslateCallback = translateCallback;
        this.mOtaFile = file;
        if (!file.exists()) {
            LogUtil.d(this.mOtaFile + "文件不存在,路径:" + this.mOtaFile.getPath());
            return;
        }
        int length = (int) this.mOtaFile.length();
        byte[] bArr = new byte[length];
        this.mFileSizeLength = length;
        LogUtil.d("ota文件大小:" + length);
        try {
            LogUtil.d("mOtaFile文件路径:" + this.mOtaFile.getPath());
            FileInputStream fileInputStream = new FileInputStream(this.mOtaFile);
            int i2 = 0;
            while (i2 < length) {
                int i3 = fileInputStream.read(bArr, i2, length - i2);
                if (i3 < 0) {
                    break;
                } else {
                    i2 += i3;
                }
            }
            if (i2 != length && translateCallback != null) {
                translateCallback.onLoadFileFail();
            }
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        CommandBleOTAFile commandBleOTAFile = (CommandBleOTAFile) MessageManager.unpackMessage(bArr);
        if (commandBleOTAFile == null) {
            LogUtil.e("升级文件unpack异常");
            this.mTranslateCallback.onLoadFileFail();
            return;
        }
        CommandBleOTAFileInfo commandBleOTAFileInfo = new CommandBleOTAFileInfo();
        commandBleOTAFileInfo.file_type = commandBleOTAFile.file_type;
        commandBleOTAFileInfo.file_size = commandBleOTAFile.file_size;
        commandBleOTAFileInfo.file_md5 = commandBleOTAFile.file_md5;
        commandBleOTAFileInfo.old_version = commandBleOTAFile.old_version;
        commandBleOTAFileInfo.new_version = commandBleOTAFile.new_version;
        commandBleOTAFileInfo.hardware_id = commandBleOTAFile.hardware_id;
        commandBleOTAFileInfo.name = commandBleOTAFile.name;
        commandBleOTAFileInfo.desc = commandBleOTAFile.desc;
        commandBleOTAFileInfo.file_id = commandBleOTAFile.file_id;
        commandBleOTAFileInfo.old_addr = commandBleOTAFile.old_addr;
        commandBleOTAFileInfo.dest_addr = commandBleOTAFile.dest_addr;
        LogUtil.d("ota 升级参数对应数组大小 CommandBleOTAFile->data:" + commandBleOTAFile.data.length);
        LogUtil.d("ota 升级参数确认对象 commandBleOTAFileInfo:" + commandBleOTAFileInfo);
        byte[] bArr2 = commandBleOTAFile.data;
        this.mOtaByteArray = new ArrayList<>();
        int i4 = 0;
        while (i4 < bArr2.length) {
            int length2 = 1024;
            if (bArr2.length - i4 >= 1024) {
                byte[] bArr3 = new byte[1024];
                try {
                    System.arraycopy(bArr2, i4, bArr3, 0, 1024);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    LogUtil.d("ota读取-崩溃了:1024---e" + e2.getMessage());
                }
                this.mOtaByteArray.add(bArr3);
                LogUtil.d("ota读取数据够1024时,读取长度:1024");
            } else {
                length2 = bArr2.length - i4;
                byte[] bArr4 = new byte[length2];
                LogUtil.d("ota读取数据不够1024时,读取长度=剩余长度:" + length2);
                try {
                    System.arraycopy(bArr2, i4, bArr4, 0, length2);
                } catch (Exception e3) {
                    e3.printStackTrace();
                    LogUtil.d("ota读取-崩溃了<1024----:" + length2 + "---e" + e3.getMessage());
                }
                this.mOtaByteArray.add(bArr4);
            }
            i4 += length2;
            int length3 = (int) (((i4 * 1.0f) / bArr2.length) * 100.0f);
            LogUtil.d("ota读取数据进度:" + length3);
            LogUtil.d("OTA  offset=" + i4 + "------numRead=" + length2 + "---progressLoad=" + length3);
            if (translateCallback != null) {
                translateCallback.onLoadFile(length3);
            }
        }
        if (translateCallback != null) {
            translateCallback.onWaitWatchStartTranslate();
        }
        LogUtil.d("ota文件内部传输包的长度:" + commandBleOTAFile.data.length);
        ArrayList<byte[]> arrayList = this.mOtaByteArray;
        if (arrayList != null && arrayList.size() > 0) {
            LogUtil.d("ota文件内部传输包集合长度:" + this.mOtaByteArray.size());
        }
        startEchoTimeOutTimer("是否可升级echo超时");
        MessageManager.getInstance().sendOtaUpdateStartInfo(commandBleOTAFileInfo, this);
    }

    public void transferFileUnpackCheck(File file, int i, TranslateCallback translateCallback) {
        this.mOtaFile = file;
        if (!file.exists()) {
            LogUtil.d(this.mOtaFile + "文件不存在,路径:" + this.mOtaFile.getPath());
            return;
        }
        int length = (int) this.mOtaFile.length();
        byte[] bArr = new byte[length];
        LogUtil.d("ota文件大小:" + length);
        try {
            LogUtil.d("mOtaFile文件路径:" + this.mOtaFile.getPath());
            FileInputStream fileInputStream = new FileInputStream(this.mOtaFile);
            int i2 = 0;
            while (i2 < length) {
                int i3 = fileInputStream.read(bArr, i2, length - i2);
                if (i3 < 0) {
                    break;
                } else {
                    i2 += i3;
                }
            }
            if (i2 != length && translateCallback != null) {
                translateCallback.onLoadFileFail();
            }
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (((CommandBleOTAFile) MessageManager.unpackMessage(bArr)) == null) {
            LogUtil.e("升级文件unpack异常");
            translateCallback.onTransferFail(0);
        } else {
            translateCallback.onTransferFail(1);
            LogUtil.e("升级文件unpack成功");
        }
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.translate.ITranslateManager
    public void stopTransmission() {
        this.stopTransmission = true;
        ScheduledExecutorService scheduledExecutorService = this.executor;
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.translate.ITranslateManager
    public boolean isInTransmission() {
        return !this.stopTransmission;
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.translate.OnCommandBleOTAFileInfoCallBack
    public void onCommandBleOTAFileInfoCallBack(final CommandBleOTAResult commandBleOTAResult) {
        LogUtil.d("onCommandBleOTAFileInfoCallBack：" + commandBleOTAResult.result);
        stopEchoTimeOutTimer();
        if (commandBleOTAResult.index == -1) {
            LogUtil.d("不可升级,result：" + commandBleOTAResult.result);
            TranslateCallback translateCallback = this.mTranslateCallback;
            if (translateCallback != null) {
                translateCallback.onTransferFail(commandBleOTAResult.result);
                return;
            }
            return;
        }
        LogUtil.d("可升级,准备开始传输数据,result：" + commandBleOTAResult);
        TranslateCallback translateCallback2 = this.mTranslateCallback;
        if (translateCallback2 != null) {
            translateCallback2.onTranslateStart();
        }
        if (this.stopTransmission && !this.isTransFinish) {
            LogUtil.d("本次传输已被停止，不继续传输");
            TranslateCallback translateCallback3 = this.mTranslateCallback;
            if (translateCallback3 != null) {
                translateCallback3.onTransferFail(100);
                return;
            }
            return;
        }
        if (this.mOtaByteArray == null) {
            return;
        }
        if (commandBleOTAResult.index >= this.mOtaByteArray.size()) {
            LogUtil.d("首次询问角标直接越界,不需要数据传输,等待手表升级中..:" + commandBleOTAResult.index);
            TranslateCallback translateCallback4 = this.mTranslateCallback;
            if (translateCallback4 != null) {
                translateCallback4.onTransferFinish();
                stopTransmission();
                return;
            }
            return;
        }
        this.mNowIndex = commandBleOTAResult.index;
        if (commandBleOTAResult.index == -1 || commandBleOTAResult.index >= this.mOtaByteArray.size()) {
            return;
        }
        startEchoTimeOutTimer("升级数据包echo超时");
        LogUtil.d("单包开始传输角标:" + commandBleOTAResult.index + " 总包个数:" + this.mOtaByteArray.size());
        MessageManager.getInstance().sendOtaUpdateDataEachPackage(this.mOtaByteArray.get(commandBleOTAResult.index), commandBleOTAResult.index, this, new IMessageSendCallback() { // from class: cn.baos.watch.sdk.huabaoImpl.translate.TranslateManager.1
            @Override // cn.baos.watch.sdk.manager.message.IMessageSendCallback
            public void IMessageSendStatus(int i, boolean z) {
                if (z) {
                    TranslateManager.this.sendInAdvanceData(commandBleOTAResult.index + 1);
                }
            }
        });
    }

    @Override // cn.baos.watch.sdk.huabaoImpl.translate.OnCommandBleOTAEachPackageCallBack
    public void onCommandBleOTAEachPackageCallBack(final CommandBleOTAResult commandBleOTAResult) {
        int size;
        stopEchoTimeOutTimer();
        if (commandBleOTAResult.index == -1) {
            LogUtil.d("单包不可升级,result：" + commandBleOTAResult.result);
            TranslateCallback translateCallback = this.mTranslateCallback;
            if (translateCallback != null) {
                translateCallback.onTransferFail(commandBleOTAResult.result);
                return;
            }
            return;
        }
        if (this.stopTransmission && !this.isTransFinish) {
            LogUtil.d("本次传输已被停止，不继续传输");
            TranslateCallback translateCallback2 = this.mTranslateCallback;
            if (translateCallback2 != null) {
                translateCallback2.onTransferFail(100);
                return;
            }
            return;
        }
        this.mNowIndex = commandBleOTAResult.index;
        LogUtil.d("当前包的反馈:" + W100Utils.toString(commandBleOTAResult));
        ArrayList<byte[]> arrayList = this.mOtaByteArray;
        if (arrayList == null || arrayList.size() <= 0) {
            size = 0;
        } else {
            LogUtil.d("当前包进度：---index==" + commandBleOTAResult.index + "----mOtaByteArray.Size==" + this.mOtaByteArray.size());
            size = (int) (((commandBleOTAResult.index * 1.0f) / this.mOtaByteArray.size()) * 100.0f);
            LogUtil.d("当前进度:" + size);
        }
        TranslateCallback translateCallback3 = this.mTranslateCallback;
        if (translateCallback3 != null) {
            translateCallback3.onTransferProgress(size);
        }
        if (size < 100 && this.mOtaByteArray != null) {
            if (commandBleOTAResult.index == -1 || commandBleOTAResult.index >= this.mOtaByteArray.size()) {
                return;
            }
            startEchoTimeOutTimer("升级数据包echo超时");
            LogUtil.d("单包开始传输角标:" + commandBleOTAResult.index + " 总包个数:" + this.mOtaByteArray.size());
            if (this.mNowIndex == this.mInAdvanceIndex) {
                this.isSend = true;
                LogUtil.d("OTA- 发送中 mNowIndex=" + this.mNowIndex + "____mInAdvanceIndex = " + this.mInAdvanceIndex);
            } else {
                this.isSend = false;
                LogUtil.d("OTA- 重置发送 mNowIndex=" + this.mNowIndex + "____mInAdvanceIndex = " + this.mInAdvanceIndex);
                MessageManager.getInstance().sendOtaUpdateDataEachPackage(this.mOtaByteArray.get(commandBleOTAResult.index), commandBleOTAResult.index, this, new IMessageSendCallback() { // from class: cn.baos.watch.sdk.huabaoImpl.translate.TranslateManager.2
                    @Override // cn.baos.watch.sdk.manager.message.IMessageSendCallback
                    public void IMessageSendStatus(int i, boolean z) {
                        LogUtil.e("OTA---- STRING" + i + StringUtils.SPACE + z);
                        TranslateManager.this.normalSendNum++;
                        if (!z || TranslateManager.this.normalSendNum <= 2) {
                            return;
                        }
                        TranslateManager.this.normalSendNum = 0;
                        TranslateManager.this.sendInAdvanceData(commandBleOTAResult.index + 1);
                    }
                });
            }
            if (this.mOtaByteArray.get(commandBleOTAResult.index).length < 1024) {
                LogUtil.d("单包开始传输角标:");
                TranslateCallback translateCallback4 = this.mTranslateCallback;
                if (translateCallback4 != null) {
                    this.isTransFinish = true;
                    translateCallback4.onTransferProgress(100);
                    stopTransmission();
                    return;
                }
                return;
            }
            return;
        }
        this.stopTransmission = true;
        this.isTransFinish = true;
        LogUtil.d("传输完成,开始进入ota等待结果状态");
        TranslateCallback translateCallback5 = this.mTranslateCallback;
        if (translateCallback5 != null) {
            translateCallback5.onTransferFinish();
            stopTransmission();
        }
        ArrayList<byte[]> arrayList2 = this.mOtaByteArray;
        if (arrayList2 == null || arrayList2.size() <= 0) {
            return;
        }
        LogUtil.d("清空缓存大小:" + this.mOtaByteArray.size() + "不删除本地文件");
        this.mOtaByteArray.clear();
        this.mOtaByteArray = null;
    }

    public void sendInAdvanceData(int i) {
        LogUtil.d("OTA-sendInAdvanceData:  " + i);
        this.mInAdvanceIndex = i;
        if (i != -1) {
            try {
                ArrayList<byte[]> arrayList = this.mOtaByteArray;
                if (arrayList != null && !arrayList.isEmpty() && this.mInAdvanceIndex >= this.mOtaByteArray.size()) {
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        MessageManager.getInstance().sendOtaUpdateDataEachPackage(this.mOtaByteArray.get(i), i, this, new IMessageSendCallback() { // from class: cn.baos.watch.sdk.huabaoImpl.translate.TranslateManager.3
            @Override // cn.baos.watch.sdk.manager.message.IMessageSendCallback
            public void IMessageSendStatus(int i2, boolean z) {
                if (z) {
                    LogUtil.d("OTA-+++: mNowIndex=" + TranslateManager.this.mNowIndex + "____mInAdvanceIndex = " + TranslateManager.this.mInAdvanceIndex);
                    if (TranslateManager.this.mNowIndex == TranslateManager.this.mInAdvanceIndex && TranslateManager.this.isSend) {
                        TranslateManager translateManager = TranslateManager.this;
                        translateManager.sendInAdvanceData(translateManager.mInAdvanceIndex + 1);
                    }
                }
            }
        });
    }

    public boolean isReceiveIndex(int i) {
        LogUtil.d("当前包需要包：" + this.mNowIndex + "----重发包：" + i);
        return this.mNowIndex > i;
    }

    private void startEchoTimeOutTimer(final String str) {
        EchoTimeOutRunnable echoTimeOutRunnable = new EchoTimeOutRunnable(new EchoTimeOutCallback() { // from class: cn.baos.watch.sdk.huabaoImpl.translate.TranslateManager$$ExternalSyntheticLambda0
            @Override // cn.baos.watch.sdk.huabaoImpl.translate.EchoTimeOutCallback
            public final void onEchoTimeOut() {
                this.f$0.lambda$startEchoTimeOutTimer$0(str);
            }
        });
        this.mEchoTimeOutRunnable = echoTimeOutRunnable;
        this.timer.schedule(echoTimeOutRunnable, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS);
        LogUtil.d(str + ",启动超时计时器:" + this.mEchoTimeOutRunnable.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startEchoTimeOutTimer$0(String str) {
        LogUtil.d(str);
        this.mTranslateCallback.onTransferFail(11);
    }

    private void stopEchoTimeOutTimer() {
        EchoTimeOutRunnable echoTimeOutRunnable = this.mEchoTimeOutRunnable;
        if (echoTimeOutRunnable != null) {
            echoTimeOutRunnable.stop();
            LogUtil.d("反馈成功,取消超时计时器:" + this.mEchoTimeOutRunnable.toString());
        }
    }

    public long getFileSizeLength() {
        return this.mFileSizeLength;
    }

    public boolean isStopTransmission() {
        return this.stopTransmission;
    }
}
