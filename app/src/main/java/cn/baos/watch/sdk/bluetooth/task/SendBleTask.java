package cn.baos.watch.sdk.bluetooth.task;

import android.os.Handler;
import android.os.Looper;
import cn.baos.message.Serializable;
import cn.baos.watch.sdk.bluetooth.BleService;
import cn.baos.watch.sdk.bluetooth.BlueToothManager;
import cn.baos.watch.sdk.bluetooth.utils.TimeManager;
import cn.baos.watch.sdk.entitiy.MessageAndTargetId;
import cn.baos.watch.sdk.huabaoImpl.translate.TranslateManager;
import cn.baos.watch.sdk.interfac.ble.BleStatusEnum;
import cn.baos.watch.sdk.interfac.ble.HbBtClientManager;
import cn.baos.watch.sdk.manager.jni.BlueToothJniManager;
import cn.baos.watch.sdk.manager.jni.write.IWriteAckedAsyncCallback;
import cn.baos.watch.sdk.manager.message.IMessageSendCallback;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.W100Utils;
import cn.baos.watch.w100.messages.CommandBleOTAFileData;
import java.util.Timer;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.lang3.time.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class SendBleTask extends Thread implements IWriteAckedAsyncCallback {
    private final Condition condition;
    private final ReentrantLock lock;
    private int mFailTime;
    private MessageAndTargetId mMessageAndTargetId;
    private LinkedBlockingDeque<MessageAndTargetId> mMessageSenderQueue;
    private final AtomicInteger mSendCounter;
    private Handler mainHandler;
    private IMessageSendCallback messageSendCallback;
    private Timer timer;
    private boolean waitConnect;

    public SendBleTask() {
        super("bluetooth send task");
        this.mMessageSenderQueue = new LinkedBlockingDeque<>();
        this.mFailTime = 0;
        this.waitConnect = false;
        this.mainHandler = new Handler(Looper.getMainLooper());
        ReentrantLock reentrantLock = new ReentrantLock();
        this.lock = reentrantLock;
        this.condition = reentrantLock.newCondition();
        this.mSendCounter = new AtomicInteger(0);
        this.timer = new Timer();
    }

    public boolean sendMessage(int i, Serializable serializable) {
        try {
            if (serializable != null) {
                this.mMessageSenderQueue.put(new MessageAndTargetId(i, System.currentTimeMillis(), serializable));
                LogUtil.d("发送队列加一,当前发送对象队列长度:" + this.mMessageSenderQueue.size());
                return true;
            }
            LogUtil.d("SendBleTask,当前发送对象队列长度:serializable =null");
            return false;
        } catch (InterruptedException e) {
            LogUtil.d(e.getMessage());
            LogUtil.d("SendBleTask,:InterruptedException ");
            return false;
        }
    }

    public boolean sendMessage(int i, Serializable serializable, IMessageSendCallback iMessageSendCallback) {
        try {
            this.messageSendCallback = iMessageSendCallback;
            if (serializable != null) {
                this.mMessageSenderQueue.put(new MessageAndTargetId(i, System.currentTimeMillis(), serializable));
                LogUtil.d("发送队列加一,当前发送对象队列长度:" + this.mMessageSenderQueue.size());
                return true;
            }
            LogUtil.d("SendBleTask,当前发送对象队列长度:serializable =null");
            return false;
        } catch (InterruptedException e) {
            LogUtil.d(e.getMessage());
            LogUtil.d("SendBleTask,:InterruptedException ");
            return false;
        }
    }

    public boolean reSendMessage(int i, long j, Serializable serializable) {
        if (serializable != null) {
            try {
                this.mMessageSenderQueue.putFirst(new MessageAndTargetId(i, j, serializable));
                LogUtil.d("发送队列加一,重新发送，插入队列头位置，当前发送对象队列长度:" + this.mMessageSenderQueue.size());
                return true;
            } catch (InterruptedException e) {
                LogUtil.d(e.getMessage());
            }
        }
        return false;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        LogUtil.d("发送线程启动");
        while (true) {
            this.lock.lock();
            while (this.mSendCounter.get() > 1) {
                try {
                    try {
                        try {
                            this.condition.await(30L, TimeUnit.SECONDS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                        LogUtil.d("message发送既崩溃:" + e2.getMessage());
                    }
                } catch (Throwable th) {
                    this.lock.unlock();
                    throw th;
                }
            }
            this.mMessageAndTargetId = this.mMessageSenderQueue.take();
            LogUtil.d("message发送:mMessageAndTargetId==" + this.mMessageAndTargetId);
            if (!BleStatusEnum.isBleConnected(HbBtClientManager.getInstance().getCurrentStatus())) {
                LogUtil.d("当前蓝牙未连接，睡眠五秒后，轮询等待，直接蓝牙状态未连接再实际发送");
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e3) {
                    e3.printStackTrace();
                }
                LogUtil.d("message未发送重新放入队列:" + W100Utils.toString(this.mMessageAndTargetId));
                this.mMessageSenderQueue.push(this.mMessageAndTargetId);
            } else if (System.currentTimeMillis() - this.mMessageAndTargetId.getTimeStamp() < DateUtils.MILLIS_PER_MINUTE) {
                if (this.mMessageAndTargetId.toString().length() < 400) {
                    LogUtil.d("耗时测试 发送队列长度:" + this.mMessageSenderQueue.size() + "message 内容:" + this.mMessageAndTargetId.toString());
                } else {
                    LogUtil.d("耗时测试 发送队列长度:" + this.mMessageSenderQueue.size() + "message 内容过长本地打印长度:" + this.mMessageAndTargetId.toString().length());
                }
                MessageAndTargetId messageAndTargetId = this.mMessageAndTargetId;
                if (messageAndTargetId == null) {
                    LogUtil.d("bt client get send timeout--vmMessageAndTargetId==null");
                } else {
                    byte[] bArrPackMessage = MessageManager.packMessage(messageAndTargetId.getSerializable());
                    LogUtil.d("送入jni之前的数据长度:" + bArrPackMessage.length);
                    int iWriteBleData = writeBleData(this.mMessageAndTargetId.getTargetId(), bArrPackMessage, bArrPackMessage.length, this);
                    if (iWriteBleData != 0) {
                        this.mSendCounter.incrementAndGet();
                        LogUtil.d("session_id:" + iWriteBleData + " 等待发送回调结果:" + this.mSendCounter.get());
                    } else {
                        LogUtil.d("蓝牙so库反馈session_id为0，so库队列已满..");
                        this.mMessageSenderQueue.clear();
                        BlueToothManager.getInstance().cleanMessageQueue();
                    }
                }
            } else {
                LogUtil.d("该消息已过期,当前时间:" + System.currentTimeMillis() + " 消息创建时间:" + this.mMessageAndTargetId.getTimeStamp());
                LogUtil.d("该消息已过期，直接丢弃:" + W100Utils.toString(this.mMessageAndTargetId));
            }
            this.lock.unlock();
        }
    }

    @Override // cn.baos.watch.sdk.manager.jni.write.IWriteAckedAsyncCallback
    public void onWriteAckedAsyncCallback(int i, boolean z) {
        LogUtil.d("耗时测试 write->finish:对方确认收到这次发送:session_id:" + i + "success:%b" + z);
        if (z) {
            LogUtil.d("发送成功");
            this.waitConnect = false;
            this.mainHandler.removeCallbacksAndMessages(null);
        } else {
            LogUtil.d("发送失败，消息重新发送");
            LogUtil.d("发送失败重新发送:" + W100Utils.toString(this.mMessageAndTargetId));
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.task.SendBleTask.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Serializable serializable = SendBleTask.this.mMessageAndTargetId.getSerializable();
                        if (serializable instanceof CommandBleOTAFileData) {
                            if (!TranslateManager.getInstance().isReceiveIndex(((CommandBleOTAFileData) serializable).index)) {
                                SendBleTask sendBleTask = SendBleTask.this;
                                sendBleTask.reSendMessage(sendBleTask.mMessageAndTargetId.getTargetId(), SendBleTask.this.mMessageAndTargetId.getTimeStamp(), SendBleTask.this.mMessageAndTargetId.getSerializable());
                            }
                        } else {
                            SendBleTask sendBleTask2 = SendBleTask.this;
                            sendBleTask2.reSendMessage(sendBleTask2.mMessageAndTargetId.getTargetId(), SendBleTask.this.mMessageAndTargetId.getTimeStamp(), SendBleTask.this.mMessageAndTargetId.getSerializable());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 1000L);
        }
        if (!z) {
            this.mFailTime++;
            LogUtil.d("蓝牙重发失败，累计次数:" + this.mFailTime);
            if (this.mFailTime >= 10 && !this.waitConnect) {
                LogUtil.d("蓝牙重发失败" + this.mFailTime + "次，蓝牙自动断开重连");
                this.mFailTime = 0;
                this.waitConnect = true;
                this.mainHandler.removeCallbacksAndMessages(null);
                this.mainHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.task.SendBleTask.2
                    @Override // java.lang.Runnable
                    public void run() {
                        BleService.getInstance().disconnect();
                        LogUtil.d("200ms后自动断开蓝牙");
                    }
                }, 200L);
            }
        } else {
            this.mFailTime = 0;
            this.mainHandler.removeCallbacksAndMessages(null);
            this.waitConnect = false;
            LogUtil.d("蓝牙重发成功，失败累计次数重制:" + this.mFailTime);
        }
        this.mSendCounter.decrementAndGet();
        this.lock.lock();
        try {
            this.condition.signal();
        } finally {
            this.lock.unlock();
        }
    }

    public int writeBleData(int i, byte[] bArr, int i2, IWriteAckedAsyncCallback iWriteAckedAsyncCallback) {
        if (InitBleTask.blueToothStatus == 0) {
            LogUtil.e("当前包写入jni通道:" + i + "____size:" + i2);
            return BlueToothJniManager.bleWriteData(i, bArr, i2, new AnonymousClass3(iWriteAckedAsyncCallback, System.currentTimeMillis()));
        }
        LogUtil.d("蓝牙so库反馈异常或未初始化完成，不可写入");
        return 0;
    }

    /* JADX INFO: renamed from: cn.baos.watch.sdk.bluetooth.task.SendBleTask$3, reason: invalid class name */
    class AnonymousClass3 implements IWriteAckedAsyncCallback {
        final /* synthetic */ IWriteAckedAsyncCallback val$iWriteAckedAsyncCallback;
        final /* synthetic */ long val$sendStart;

        AnonymousClass3(IWriteAckedAsyncCallback iWriteAckedAsyncCallback, long j) {
            this.val$iWriteAckedAsyncCallback = iWriteAckedAsyncCallback;
            this.val$sendStart = j;
        }

        @Override // cn.baos.watch.sdk.manager.jni.write.IWriteAckedAsyncCallback
        public void onWriteAckedAsyncCallback(final int i, final boolean z) {
            LogUtil.e("当前包写入jni通道:session_id=" + i + "success:" + z);
            final IWriteAckedAsyncCallback iWriteAckedAsyncCallback = this.val$iWriteAckedAsyncCallback;
            new Thread(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.task.SendBleTask$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    iWriteAckedAsyncCallback.onWriteAckedAsyncCallback(i, z);
                }
            }).start();
            if (SendBleTask.this.messageSendCallback != null) {
                SendBleTask.this.messageSendCallback.IMessageSendStatus(i, z);
            }
            long jCurrentTimeMillis = System.currentTimeMillis() - this.val$sendStart;
            LogUtil.d("耗时测试 write->finish:耗时:" + jCurrentTimeMillis + "ms");
            if (z) {
                TimeManager.getInstance().initManager(jCurrentTimeMillis);
            }
        }
    }

    public boolean clearData() {
        LogUtil.e("---->clearData");
        try {
            LinkedBlockingDeque<MessageAndTargetId> linkedBlockingDeque = this.mMessageSenderQueue;
            if (linkedBlockingDeque == null) {
                return true;
            }
            linkedBlockingDeque.clear();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
}
