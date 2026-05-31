package cn.baos.watch.sdk.bluetooth;

import cn.baos.message.Serializable;
import cn.baos.watch.sdk.bluetooth.task.InitBleTask;
import cn.baos.watch.sdk.bluetooth.task.ReceiveBleTask;
import cn.baos.watch.sdk.bluetooth.task.SendBleTask;
import cn.baos.watch.sdk.bluetooth.task.WriteBleTask;
import cn.baos.watch.sdk.manager.message.IMessageSendCallback;
import cn.baos.watch.sdk.util.LogUtil;

/* JADX INFO: loaded from: classes.dex */
public class BlueToothManager {
    private static BlueToothManager instance;
    public InitBleTask mInitTask;
    public ReceiveBleTask mReceiveTask;
    public SendBleTask mSendTask;
    public WriteBleTask mWriteBleTask;

    public static BlueToothManager getInstance() {
        if (instance == null) {
            synchronized (BlueToothManager.class) {
                if (instance == null) {
                    instance = new BlueToothManager();
                }
            }
        }
        return instance;
    }

    private BlueToothManager() {
        startBleTask();
    }

    public boolean sendMessage(int i, Serializable serializable) {
        if (this.mSendTask != null) {
            LogUtil.d("BlueToothManager,当前发送对象队列长度:mSendTask != null");
            return this.mSendTask.sendMessage(i, serializable);
        }
        LogUtil.d("BlueToothManager,当前发送对象队列长度:mSendTask == null");
        return false;
    }

    public boolean sendMessage(int i, Serializable serializable, IMessageSendCallback iMessageSendCallback) {
        if (this.mSendTask != null) {
            LogUtil.d("BlueToothManager,当前发送对象队列长度:mSendTask != null");
            return this.mSendTask.sendMessage(i, serializable, iMessageSendCallback);
        }
        LogUtil.d("BlueToothManager,当前发送对象队列长度:mSendTask == null");
        return false;
    }

    public boolean receiveData(byte[] bArr) {
        return this.mReceiveTask.receiveData(bArr);
    }

    public boolean writeData(byte[] bArr) {
        return this.mWriteBleTask.writeData(bArr);
    }

    public void startBleTask() {
        if (this.mInitTask == null) {
            InitBleTask initBleTask = new InitBleTask();
            this.mInitTask = initBleTask;
            initBleTask.start();
        }
        if (this.mSendTask == null) {
            SendBleTask sendBleTask = new SendBleTask();
            this.mSendTask = sendBleTask;
            sendBleTask.start();
        }
        if (this.mReceiveTask == null) {
            ReceiveBleTask receiveBleTask = new ReceiveBleTask();
            this.mReceiveTask = receiveBleTask;
            receiveBleTask.start();
        }
        if (this.mWriteBleTask == null) {
            WriteBleTask writeBleTask = new WriteBleTask();
            this.mWriteBleTask = writeBleTask;
            writeBleTask.start();
        }
    }

    public void cleanMessageQueue() {
        try {
            if (this.mInitTask == null) {
                this.mInitTask = new InitBleTask();
            }
            if (this.mSendTask == null) {
                this.mSendTask = new SendBleTask();
            }
            if (this.mReceiveTask == null) {
                this.mReceiveTask = new ReceiveBleTask();
            }
            if (this.mWriteBleTask == null) {
                this.mWriteBleTask = new WriteBleTask();
            }
            this.mSendTask.clearData();
            this.mReceiveTask.clearData();
            this.mWriteBleTask.clearData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
