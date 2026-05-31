package cn.baos.watch.sdk.interfac.spp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import cn.baos.watch.sdk.bluetooth.BlueToothManager;
import cn.baos.watch.sdk.interfac.ble.BleNativeStatusEnum;
import cn.baos.watch.sdk.manager.jni.BlueToothJniManager;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.utils.LogUtil;
import com.tekartik.sqflite.Constant;
import java.io.IOException;
import java.io.InputStream;
import java.util.Formatter;
import java.util.UUID;
import org.apache.commons.io.IOUtils;

/* JADX INFO: loaded from: classes.dex */
public class BLESPPUtils {
    private static boolean mEnableLogOut = false;
    private Context mContext;
    private OnBluetoothAction mOnBluetoothAction;
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private ConnectTask mConnectTask = new ConnectTask();
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: cn.baos.watch.sdk.interfac.spp.BLESPPUtils.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.bluetooth.device.action.FOUND".equals(intent.getAction())) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (BLESPPUtils.this.mOnBluetoothAction != null) {
                    BLESPPUtils.this.mOnBluetoothAction.onFoundDevice(bluetoothDevice);
                }
            }
        }
    };
    private final BroadcastReceiver mFinishFoundReceiver = new BroadcastReceiver() { // from class: cn.baos.watch.sdk.interfac.spp.BLESPPUtils.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (!"android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(intent.getAction()) || BLESPPUtils.this.mOnBluetoothAction == null) {
                return;
            }
            BLESPPUtils.this.mOnBluetoothAction.onFinishFoundDevice();
        }
    };

    public interface OnBluetoothAction {
        void onConnectFailed(String str);

        void onConnectSuccess(BluetoothDevice bluetoothDevice);

        void onFinishFoundDevice();

        void onFoundDevice(BluetoothDevice bluetoothDevice);

        void onReceiveBytes(byte[] bArr);

        void onSendBytes(byte[] bArr);
    }

    private static class ConnectTask extends AsyncTask<String, Byte[], Void> {
        private BluetoothAdapter bluetoothAdapter;
        BluetoothSocket bluetoothSocket;
        boolean isConnectStatus;
        boolean isRunning;
        OnBluetoothAction onBluetoothAction;
        BluetoothDevice romoteDevice;
        String stopString;

        private ConnectTask() {
            this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            this.isRunning = false;
            this.stopString = IOUtils.LINE_SEPARATOR_WINDOWS;
            this.isConnectStatus = false;
        }

        public void destory() {
            BluetoothSocket bluetoothSocket = this.bluetoothSocket;
            if (bluetoothSocket != null) {
                try {
                    bluetoothSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(String... strArr) {
            byte[] bArr;
            Exception e;
            this.isRunning = true;
            try {
                BLESPPUtils.logD("开始连接");
                UUID uuidFromString = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                BluetoothDevice remoteDevice = this.bluetoothAdapter.getRemoteDevice(strArr[0]);
                this.romoteDevice = remoteDevice;
                BluetoothSocket bluetoothSocketCreateRfcommSocketToServiceRecord = remoteDevice.createRfcommSocketToServiceRecord(uuidFromString);
                this.bluetoothSocket = bluetoothSocketCreateRfcommSocketToServiceRecord;
                if (bluetoothSocketCreateRfcommSocketToServiceRecord == null) {
                    BLESPPUtils.logD("连接失败 获取Socket失败");
                    this.onBluetoothAction.onConnectFailed("连接失败:获取Socket失败");
                    this.isRunning = false;
                    return null;
                }
                try {
                    bluetoothSocketCreateRfcommSocketToServiceRecord.connect();
                    this.isConnectStatus = true;
                    MessageManager.getInstance().setSppTransLateData(true);
                    BLESPPUtils.logD("Spp 连接成功");
                    this.onBluetoothAction.onConnectSuccess(this.romoteDevice);
                    try {
                        InputStream inputStream = this.bluetoothSocket.getInputStream();
                        byte[] bArr2 = new byte[0];
                        while (this.isRunning) {
                            BLESPPUtils.logD("looping");
                            byte[] bArr3 = new byte[256];
                            while (inputStream.available() == 0 && this.isRunning && System.currentTimeMillis() >= 0) {
                            }
                            while (this.isRunning) {
                                try {
                                    int i = inputStream.read(bArr3);
                                    bArr = new byte[bArr2.length + i];
                                    System.arraycopy(bArr2, 0, bArr, 0, bArr2.length);
                                    System.arraycopy(bArr3, 0, bArr, bArr2.length, i);
                                    try {
                                    } catch (Exception e2) {
                                        e = e2;
                                        e.printStackTrace();
                                        this.onBluetoothAction.onConnectFailed("接收数据单次失败：" + e.getMessage());
                                    }
                                } catch (Exception e3) {
                                    bArr = bArr2;
                                    e = e3;
                                }
                                if (inputStream.available() == 0) {
                                    bArr2 = bArr;
                                    break;
                                }
                                bArr2 = bArr;
                            }
                            try {
                                BLESPPUtils.logD("当前累计收到的数据=>" + BLESPPUtils.byte2Hex(bArr2));
                                BLESPPUtils.logD("标志位为：" + BLESPPUtils.byte2Hex(this.stopString.getBytes()));
                                this.onBluetoothAction.onReceiveBytes(bArr2);
                                bArr2 = new byte[0];
                            } catch (Exception e4) {
                                e4.printStackTrace();
                                this.onBluetoothAction.onConnectFailed("验证收到数据结束标志出错：" + e4.getMessage());
                            }
                        }
                    } catch (Exception e5) {
                        e5.printStackTrace();
                        this.isConnectStatus = false;
                        MessageManager.getInstance().setSppTransLateData(false);
                        this.onBluetoothAction.onConnectFailed("接收数据失败：" + e5.getMessage());
                    }
                    return null;
                } catch (Exception e6) {
                    e6.printStackTrace();
                    BLESPPUtils.logD("Spp 连接失败:" + e6.getMessage());
                    this.isConnectStatus = false;
                    MessageManager.getInstance().setSppTransLateData(false);
                    this.onBluetoothAction.onConnectFailed("连接失败:" + e6.getMessage());
                    return null;
                }
            } catch (Exception e7) {
                BLESPPUtils.logD("获取Socket失败");
                this.isRunning = false;
                e7.printStackTrace();
                return null;
            }
        }

        @Override // android.os.AsyncTask
        protected void onCancelled() {
            try {
                BLESPPUtils.logD("AsyncTask 开始释放资源");
                this.isRunning = false;
                BluetoothSocket bluetoothSocket = this.bluetoothSocket;
                if (bluetoothSocket != null) {
                    bluetoothSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void send(byte[] bArr) {
            try {
                this.bluetoothSocket.getOutputStream().write(bArr);
                this.onBluetoothAction.onSendBytes(bArr);
                BlueToothJniManager.bleWritableNotify(BleNativeStatusEnum.HB_BLE_WRITABLE.mValue);
            } catch (Exception e) {
                e.printStackTrace();
                this.isConnectStatus = false;
                MessageManager.getInstance().setSppTransLateData(false);
                LogUtil.d("spp-通道断开");
                try {
                    BlueToothManager.getInstance().writeData(bArr);
                    this.isRunning = false;
                    cancel(true);
                    this.bluetoothSocket.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public void setStopString(String str) {
        try {
            this.mConnectTask.stopString = str;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BLESPPUtils(Context context, OnBluetoothAction onBluetoothAction) {
        this.mContext = context;
        this.mOnBluetoothAction = onBluetoothAction;
    }

    public void onCreate() {
        this.mContext.registerReceiver(this.mReceiver, new IntentFilter("android.bluetooth.device.action.FOUND"));
        this.mContext.registerReceiver(this.mFinishFoundReceiver, new IntentFilter("android.bluetooth.device.action.FOUND"));
    }

    public void onDestroy() {
        try {
            logD("onDestroy，开始释放资源");
            this.mConnectTask.isRunning = false;
            this.mConnectTask.cancel(true);
            this.mConnectTask = new ConnectTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCancel() {
        try {
            logD("onDestroy，开始释放资源");
            this.mConnectTask.isRunning = false;
            this.mConnectTask.destory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startDiscovery() {
        try {
            if (this.mBluetoothAdapter.isDiscovering()) {
                this.mBluetoothAdapter.cancelDiscovery();
            }
            this.mBluetoothAdapter.startDiscovery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect(String str) {
        try {
            logD("start-connect" + str);
            this.mBluetoothAdapter.cancelDiscovery();
            connectx(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connectx(String str) {
        try {
            if (this.mConnectTask.getStatus() == AsyncTask.Status.RUNNING && this.mConnectTask.isRunning) {
                OnBluetoothAction onBluetoothAction = this.mOnBluetoothAction;
                if (onBluetoothAction != null) {
                    onBluetoothAction.onConnectFailed("有正在连接的任务");
                }
                logD("start-connect 有正在连接的任务");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logD(Constant.PARAM_ERROR);
        }
        this.mConnectTask = new ConnectTask();
        logD("start-connect ing");
        this.mConnectTask.onBluetoothAction = this.mOnBluetoothAction;
        try {
            this.mConnectTask.execute(str);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void send(byte[] bArr) {
        logD("spp-send-" + byte2Hex(bArr));
        try {
            ConnectTask connectTask = this.mConnectTask;
            if (connectTask != null) {
                connectTask.send(bArr);
            }
            try {
                Thread.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public boolean isBluetoothEnable() {
        try {
            return this.mBluetoothAdapter.isEnabled();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void enableBluetooth() {
        try {
            this.mBluetoothAdapter.enable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String byte2Hex(byte b) {
        StringBuilder sb = new StringBuilder(Integer.toHexString(b));
        if (sb.length() > 2) {
            sb = new StringBuilder(sb.substring(sb.length() - 2));
        }
        while (sb.length() < 2) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    public static String byte2Hex(byte[] bArr) {
        Formatter formatter = new Formatter();
        for (byte b : bArr) {
            formatter.format("%02x", Byte.valueOf(b));
        }
        String string = formatter.toString();
        formatter.close();
        return string;
    }

    static void setEnableLogOut() {
        mEnableLogOut = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logD(String str) {
        LogUtil.e("xxx spp--> " + str);
    }

    public boolean getConnectStatus() {
        try {
            return this.mConnectTask.isConnectStatus;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
