package com.inuker.bluetooth.library.channel;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;
import com.inuker.bluetooth.library.BluetoothContext;
import com.inuker.bluetooth.library.channel.Timer;
import com.inuker.bluetooth.library.channel.packet.ACKPacket;
import com.inuker.bluetooth.library.channel.packet.CTRPacket;
import com.inuker.bluetooth.library.channel.packet.DataPacket;
import com.inuker.bluetooth.library.channel.packet.Packet;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.ByteUtils;
import com.inuker.bluetooth.library.utils.proxy.ProxyBulk;
import com.inuker.bluetooth.library.utils.proxy.ProxyInterceptor;
import com.inuker.bluetooth.library.utils.proxy.ProxyUtils;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes2.dex */
public abstract class Channel implements IChannel, ProxyInterceptor {
    private static final int MSG_WRITE_CALLBACK = 1;
    private static final long TIMEOUT = 5000;
    private static final String TIMER_EXCEPTION = "exception";
    private final ChannelStateBlock[] STATE_MACHINE;
    private byte[] mBytesToWrite;
    private final Handler.Callback mCallback;
    private IChannel mChannel;
    private ChannelCallback mChannelCallback;
    private final IChannel mChannelImpl;
    private ChannelState mCurrentState = ChannelState.IDLE;
    private int mCurrentSync;
    private int mFrameCount;
    private int mLastSync;
    private SparseArray<Packet> mPacketRecv;
    private final IChannelStateHandler mRecvACKHandler;
    private final IChannelStateHandler mRecvCTRHandler;
    private final IChannelStateHandler mRecvDataHandler;
    private final IChannelStateHandler mSyncPacketHandler;
    private final Timer.TimerCallback mTimeoutHandler;
    private int mTotalBytes;
    private final IChannelStateHandler mWaitStartACKHandler;
    private Handler mWorkerHandler;

    public Channel() {
        IChannelStateHandler iChannelStateHandler = new IChannelStateHandler() { // from class: com.inuker.bluetooth.library.channel.Channel.1
            @Override // com.inuker.bluetooth.library.channel.IChannelStateHandler
            public void handleState(Object... objArr) {
                Channel.this.assertRuntime(false);
                DataPacket dataPacket = (DataPacket) objArr[0];
                if (dataPacket.getSeq() != Channel.this.mCurrentSync) {
                    BluetoothLog.w(String.format("sync packet not matched!!", new Object[0]));
                    return;
                }
                if (!Channel.this.onDataPacketRecvd(dataPacket)) {
                    BluetoothLog.w(String.format("sync packet repeated!!", new Object[0]));
                    return;
                }
                Channel channel = Channel.this;
                channel.mLastSync = channel.mCurrentSync;
                Channel.this.mCurrentSync = 0;
                Channel.this.startSyncPacket();
            }
        };
        this.mSyncPacketHandler = iChannelStateHandler;
        IChannelStateHandler iChannelStateHandler2 = new IChannelStateHandler() { // from class: com.inuker.bluetooth.library.channel.Channel.2
            @Override // com.inuker.bluetooth.library.channel.IChannelStateHandler
            public void handleState(Object... objArr) {
                Channel.this.assertRuntime(false);
                DataPacket dataPacket = (DataPacket) objArr[0];
                if (!Channel.this.onDataPacketRecvd(dataPacket)) {
                    BluetoothLog.w(String.format("dataPacket repeated!!", new Object[0]));
                } else if (dataPacket.getSeq() == Channel.this.mFrameCount) {
                    Channel.this.startSyncPacket();
                } else {
                    Channel.this.startTimer(Channel.TIMEOUT, new Timer.TimerCallback("WaitData") { // from class: com.inuker.bluetooth.library.channel.Channel.2.1
                        @Override // com.inuker.bluetooth.library.channel.Timer.TimerCallback
                        public void onTimerCallback() {
                            Channel.this.startSyncPacket();
                        }
                    });
                }
            }
        };
        this.mRecvDataHandler = iChannelStateHandler2;
        IChannelStateHandler iChannelStateHandler3 = new IChannelStateHandler() { // from class: com.inuker.bluetooth.library.channel.Channel.3
            @Override // com.inuker.bluetooth.library.channel.IChannelStateHandler
            public void handleState(Object... objArr) {
                Channel.this.assertRuntime(false);
                CTRPacket cTRPacket = (CTRPacket) objArr[0];
                Channel.this.mFrameCount = cTRPacket.getFrameCount();
                ACKPacket aCKPacket = new ACKPacket(1);
                Channel.this.setCurrentState(ChannelState.READY);
                Channel.this.performWrite(aCKPacket, new ChannelCallback() { // from class: com.inuker.bluetooth.library.channel.Channel.3.1
                    @Override // com.inuker.bluetooth.library.channel.ChannelCallback
                    public void onCallback(int i) {
                        Channel.this.assertRuntime(false);
                        if (i == 0) {
                            Channel.this.setCurrentState(ChannelState.READING);
                            Channel.this.startTimer();
                        } else {
                            Channel.this.resetChannelStatus();
                        }
                    }
                });
            }
        };
        this.mRecvCTRHandler = iChannelStateHandler3;
        IChannelStateHandler iChannelStateHandler4 = new IChannelStateHandler() { // from class: com.inuker.bluetooth.library.channel.Channel.4
            @Override // com.inuker.bluetooth.library.channel.IChannelStateHandler
            public void handleState(Object... objArr) {
                Channel.this.assertRuntime(false);
                Channel.this.setCurrentState(ChannelState.WAIT_START_ACK);
                Channel.this.startTimer();
            }
        };
        this.mWaitStartACKHandler = iChannelStateHandler4;
        this.mTimeoutHandler = new Timer.TimerCallback(getClass().getSimpleName()) { // from class: com.inuker.bluetooth.library.channel.Channel.5
            @Override // com.inuker.bluetooth.library.channel.Timer.TimerCallback
            public void onTimerCallback() {
                Channel.this.assertRuntime(false);
                Channel.this.onSendCallback(-2);
                Channel.this.resetChannelStatus();
            }
        };
        IChannelStateHandler iChannelStateHandler5 = new IChannelStateHandler() { // from class: com.inuker.bluetooth.library.channel.Channel.6
            @Override // com.inuker.bluetooth.library.channel.IChannelStateHandler
            public void handleState(Object... objArr) {
                Channel.this.assertRuntime(false);
                ACKPacket aCKPacket = (ACKPacket) objArr[0];
                int status = aCKPacket.getStatus();
                if (status == 0) {
                    Channel.this.onSendCallback(0);
                    Channel.this.resetChannelStatus();
                    return;
                }
                if (status == 1) {
                    Channel.this.stopTimer();
                    Channel.this.setCurrentState(ChannelState.WRITING);
                    Channel.this.sendDataPacket(0, true);
                } else {
                    if (status == 5) {
                        int seq = aCKPacket.getSeq();
                        if (seq < 1 || seq > Channel.this.mFrameCount) {
                            return;
                        }
                        Channel.this.sendDataPacket(seq - 1, false);
                        Channel.this.startTimer();
                        return;
                    }
                    Channel.this.onSendCallback(-1);
                    Channel.this.resetChannelStatus();
                }
            }
        };
        this.mRecvACKHandler = iChannelStateHandler5;
        this.STATE_MACHINE = new ChannelStateBlock[]{new ChannelStateBlock(ChannelState.READY, ChannelEvent.SEND_CTR, iChannelStateHandler4), new ChannelStateBlock(ChannelState.WAIT_START_ACK, ChannelEvent.RECV_ACK, iChannelStateHandler5), new ChannelStateBlock(ChannelState.SYNC, ChannelEvent.RECV_ACK, iChannelStateHandler5), new ChannelStateBlock(ChannelState.IDLE, ChannelEvent.RECV_CTR, iChannelStateHandler3), new ChannelStateBlock(ChannelState.READING, ChannelEvent.RECV_DATA, iChannelStateHandler2), new ChannelStateBlock(ChannelState.SYNC_ACK, ChannelEvent.RECV_DATA, iChannelStateHandler)};
        IChannel iChannel = new IChannel() { // from class: com.inuker.bluetooth.library.channel.Channel.12
            @Override // com.inuker.bluetooth.library.channel.IChannel
            public void write(byte[] bArr, ChannelCallback channelCallback) {
                throw new UnsupportedOperationException();
            }

            @Override // com.inuker.bluetooth.library.channel.IChannel
            public void onRead(byte[] bArr) {
                Channel.this.performOnRead(bArr);
            }

            @Override // com.inuker.bluetooth.library.channel.IChannel
            public void onRecv(byte[] bArr) {
                throw new UnsupportedOperationException();
            }

            @Override // com.inuker.bluetooth.library.channel.IChannel
            public void send(byte[] bArr, ChannelCallback channelCallback) {
                Channel.this.performSend(bArr, channelCallback);
            }
        };
        this.mChannelImpl = iChannel;
        Handler.Callback callback = new Handler.Callback() { // from class: com.inuker.bluetooth.library.channel.Channel.13
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                if (message.what == 1) {
                    ((ChannelCallback) message.obj).onCallback(message.arg1);
                    return false;
                }
                ProxyBulk.safeInvoke(message.obj);
                return false;
            }
        };
        this.mCallback = callback;
        this.mPacketRecv = new SparseArray<>();
        this.mChannel = (IChannel) ProxyUtils.getProxy(iChannel, this);
        HandlerThread handlerThread = new HandlerThread(getClass().getSimpleName());
        handlerThread.start();
        this.mWorkerHandler = new Handler(handlerThread.getLooper(), callback);
    }

    @Override // com.inuker.bluetooth.library.channel.IChannel
    public final void onRead(byte[] bArr) {
        this.mChannel.onRead(bArr);
    }

    @Override // com.inuker.bluetooth.library.channel.IChannel
    public final void send(byte[] bArr, ChannelCallback channelCallback) {
        BluetoothLog.e(String.format(">>> send %s", new String(bArr)));
        this.mChannel.send(bArr, channelCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performWrite(Packet packet, final ChannelCallback channelCallback) {
        assertRuntime(false);
        if (channelCallback == null) {
            throw new NullPointerException("callback can't be null");
        }
        if (!isTimerOn()) {
            startExceptionTimer();
        }
        final byte[] bytes = packet.toBytes();
        BluetoothLog.w(String.format("%s: %s", getLogTag(), packet));
        BluetoothContext.post(new Runnable() { // from class: com.inuker.bluetooth.library.channel.Channel.7
            @Override // java.lang.Runnable
            public void run() {
                Channel.this.write(bytes, Channel.this.new WriteCallback(channelCallback));
            }
        });
    }

    private class WriteCallback implements ChannelCallback {
        ChannelCallback callback;

        WriteCallback(ChannelCallback channelCallback) {
            this.callback = channelCallback;
        }

        @Override // com.inuker.bluetooth.library.channel.ChannelCallback
        public void onCallback(int i) {
            if (Channel.this.isExceptionTimerOn()) {
                Channel.this.stopTimer();
            }
            Channel.this.mWorkerHandler.obtainMessage(1, i, 0, this.callback).sendToTarget();
        }
    }

    private void sendStartFlowPacket() {
        assertRuntime(false);
        performWrite(new CTRPacket(this.mFrameCount), new ChannelCallback() { // from class: com.inuker.bluetooth.library.channel.Channel.8
            @Override // com.inuker.bluetooth.library.channel.ChannelCallback
            public void onCallback(int i) {
                Channel.this.assertRuntime(false);
                if (i == 0) {
                    Channel.this.onPostState(ChannelEvent.SEND_CTR, new Object[0]);
                } else {
                    Channel.this.onSendCallback(-1);
                    Channel.this.resetChannelStatus();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSendCallback(int i) {
        assertRuntime(false);
        BluetoothLog.v(String.format("%s: code = %d", getLogTag(), Integer.valueOf(i)));
        ChannelCallback channelCallback = this.mChannelCallback;
        if (channelCallback != null) {
            channelCallback.onCallback(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onDataPacketRecvd(DataPacket dataPacket) {
        assertRuntime(false);
        if (this.mPacketRecv.get(dataPacket.getSeq()) != null) {
            return false;
        }
        if (dataPacket.getSeq() == this.mFrameCount) {
            dataPacket.setLastFrame();
        }
        this.mPacketRecv.put(dataPacket.getSeq(), dataPacket);
        this.mTotalBytes += dataPacket.getDataLength();
        stopTimer();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startSyncPacket() {
        assertRuntime(false);
        BluetoothLog.v(getLogTag());
        startTimer();
        setCurrentState(ChannelState.SYNC);
        if (syncLostPacket()) {
            return;
        }
        final byte[] totalRecvdBytes = getTotalRecvdBytes();
        if (!ByteUtils.isEmpty(totalRecvdBytes)) {
            performWrite(new ACKPacket(0), new ChannelCallback() { // from class: com.inuker.bluetooth.library.channel.Channel.9
                @Override // com.inuker.bluetooth.library.channel.ChannelCallback
                public void onCallback(int i) {
                    Channel.this.assertRuntime(false);
                    Channel.this.resetChannelStatus();
                    if (i == 0) {
                        Channel.this.dispatchOnReceive(totalRecvdBytes);
                    }
                }
            });
        } else {
            resetChannelStatus();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchOnReceive(byte[] bArr) {
        BluetoothLog.e(String.format(">>> receive: %s", new String(bArr)));
        BluetoothContext.post(new RecvCallback(bArr));
    }

    private class RecvCallback implements Runnable {
        private byte[] bytes;

        RecvCallback(byte[] bArr) {
            this.bytes = bArr;
        }

        @Override // java.lang.Runnable
        public void run() {
            Channel.this.onRecv(this.bytes);
        }
    }

    private byte[] getTotalRecvdBytes() {
        assertRuntime(false);
        if (this.mPacketRecv.size() != this.mFrameCount) {
            throw new IllegalStateException();
        }
        BluetoothLog.v(String.format("%s: totalBytes = %d", getLogTag(), Integer.valueOf(this.mTotalBytes)));
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(this.mTotalBytes);
        for (int i = 1; i <= this.mFrameCount; i++) {
            DataPacket dataPacket = (DataPacket) this.mPacketRecv.get(i);
            dataPacket.fillByteBuffer(byteBufferAllocate);
            if (i == this.mFrameCount && !checkCRC(byteBufferAllocate.array(), dataPacket.getCrc())) {
                BluetoothLog.e(String.format("check crc failed!!", new Object[0]));
                return ByteUtils.EMPTY_BYTES;
            }
        }
        return byteBufferAllocate.array();
    }

    private boolean syncLostPacket() {
        assertRuntime(false);
        BluetoothLog.v(getLogTag());
        int i = this.mLastSync + 1;
        while (i <= this.mFrameCount && this.mPacketRecv.get(i) != null) {
            i++;
        }
        if (i > this.mFrameCount) {
            return false;
        }
        this.mCurrentSync = i;
        performWrite(new ACKPacket(5, i), new ChannelCallback() { // from class: com.inuker.bluetooth.library.channel.Channel.10
            @Override // com.inuker.bluetooth.library.channel.ChannelCallback
            public void onCallback(int i2) {
                Channel.this.assertRuntime(false);
                if (i2 == 0) {
                    Channel.this.setCurrentState(ChannelState.SYNC_ACK);
                    Channel.this.startTimer();
                } else {
                    Channel.this.resetChannelStatus();
                }
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetChannelStatus() {
        assertRuntime(false);
        BluetoothLog.v(getLogTag());
        stopTimer();
        setCurrentState(ChannelState.IDLE);
        this.mBytesToWrite = null;
        this.mFrameCount = 0;
        this.mChannelCallback = null;
        this.mPacketRecv.clear();
        this.mCurrentSync = 0;
        this.mLastSync = 0;
        this.mTotalBytes = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendDataPacket(final int i, final boolean z) {
        assertRuntime(false);
        if (i >= this.mFrameCount) {
            BluetoothLog.v(String.format("%s: all packets sended!!", getLogTag()));
            setCurrentState(ChannelState.SYNC);
            startTimer(15000L);
        } else {
            int i2 = i + 1;
            BluetoothLog.v(String.format("%s: index = %d, looped = %b", getLogTag(), Integer.valueOf(i2), Boolean.valueOf(z)));
            performWrite(new DataPacket(i2, this.mBytesToWrite, i * 18, Math.min(this.mBytesToWrite.length, i2 * 18)), new ChannelCallback() { // from class: com.inuker.bluetooth.library.channel.Channel.11
                @Override // com.inuker.bluetooth.library.channel.ChannelCallback
                public void onCallback(int i3) {
                    Channel.this.assertRuntime(false);
                    if (i3 != 0) {
                        BluetoothLog.w(String.format(">>> packet %d write failed", Integer.valueOf(i)));
                    }
                    boolean z2 = z;
                    if (z2) {
                        Channel.this.sendDataPacket(i + 1, z2);
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCurrentState(ChannelState channelState) {
        assertRuntime(false);
        BluetoothLog.v(String.format("%s: state = %s", getLogTag(), channelState));
        this.mCurrentState = channelState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPostState(ChannelEvent channelEvent, Object... objArr) {
        assertRuntime(false);
        BluetoothLog.v(String.format("%s: state = %s, event = %s", getLogTag(), this.mCurrentState, channelEvent));
        for (ChannelStateBlock channelStateBlock : this.STATE_MACHINE) {
            if (channelStateBlock.state == this.mCurrentState && channelStateBlock.event == channelEvent) {
                channelStateBlock.handler.handleState(objArr);
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void assertRuntime(boolean z) {
        if (Looper.myLooper() != (z ? Looper.getMainLooper() : this.mWorkerHandler.getLooper())) {
            throw new RuntimeException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performOnRead(byte[] bArr) {
        Packet packet;
        assertRuntime(false);
        packet = Packet.getPacket(bArr);
        BluetoothLog.w(String.format("%s: %s", getLogTag(), packet));
        String name = packet.getName();
        name.hashCode();
        switch (name) {
            case "ack":
                onPostState(ChannelEvent.RECV_ACK, packet);
                break;
            case "ctr":
                onPostState(ChannelEvent.RECV_CTR, packet);
                break;
            case "data":
                onPostState(ChannelEvent.RECV_DATA, packet);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performSend(byte[] bArr, ChannelCallback channelCallback) {
        assertRuntime(false);
        if (this.mCurrentState != ChannelState.IDLE) {
            channelCallback.onCallback(-3);
            return;
        }
        this.mCurrentState = ChannelState.READY;
        this.mChannelCallback = (ChannelCallback) ProxyUtils.getUIProxy(channelCallback);
        int length = bArr.length;
        this.mTotalBytes = length;
        this.mFrameCount = getFrameCount(length);
        BluetoothLog.v(String.format("%s: totalBytes = %d, frameCount = %d", getLogTag(), Integer.valueOf(this.mTotalBytes), Integer.valueOf(this.mFrameCount)));
        this.mBytesToWrite = Arrays.copyOf(bArr, bArr.length + 2);
        System.arraycopy(CRC16.get(bArr), 0, this.mBytesToWrite, bArr.length, 2);
        sendStartFlowPacket();
    }

    @Override // com.inuker.bluetooth.library.utils.proxy.ProxyInterceptor
    public boolean onIntercept(Object obj, Method method, Object[] objArr) {
        this.mWorkerHandler.obtainMessage(0, new ProxyBulk(obj, method, objArr)).sendToTarget();
        return true;
    }

    private String getLogTag() {
        return String.format("%s.%s", getClass().getSimpleName(), BluetoothContext.getCurrentMethodName());
    }

    private int getFrameCount(int i) {
        return (((i + 2) - 1) / 18) + 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startTimer() {
        startTimer(TIMEOUT);
    }

    private void startExceptionTimer() {
        startTimer(TIMEOUT, new Timer.TimerCallback(TIMER_EXCEPTION) { // from class: com.inuker.bluetooth.library.channel.Channel.14
            @Override // com.inuker.bluetooth.library.channel.Timer.TimerCallback
            public void onTimerCallback() throws TimeoutException {
                throw new TimeoutException();
            }
        });
    }

    private void startTimer(long j) {
        startTimer(j, this.mTimeoutHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startTimer(long j, Timer.TimerCallback timerCallback) {
        BluetoothLog.v(String.format("%s: duration = %d", getLogTag(), Long.valueOf(j)));
        Timer.start(timerCallback, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopTimer() {
        BluetoothLog.v(getLogTag());
        Timer.stop();
    }

    private boolean isTimerOn() {
        return Timer.isRunning();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isExceptionTimerOn() {
        return TIMER_EXCEPTION.equals(Timer.getName());
    }

    private boolean checkCRC(byte[] bArr, byte[] bArr2) {
        return ByteUtils.equals(bArr2, CRC16.get(bArr));
    }
}
