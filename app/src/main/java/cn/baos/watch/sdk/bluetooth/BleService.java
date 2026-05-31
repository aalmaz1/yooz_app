package cn.baos.watch.sdk.bluetooth;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import androidx.work.WorkRequest;
import cn.baos.watch.sdk.BasSdk;
import cn.baos.watch.sdk.base.AppDataConfig;
import cn.baos.watch.sdk.bluetooth.BleConnectProgressMonitor;
import cn.baos.watch.sdk.bluetooth.HbBleConnectStatusCallback;
import cn.baos.watch.sdk.bluetooth.bt.BT625Client;
import cn.baos.watch.sdk.bluetooth.bt.BleUtils;
import cn.baos.watch.sdk.bluetooth.callback.IBtBindRequestCallback;
import cn.baos.watch.sdk.bluetooth.constant.BTConstant;
import cn.baos.watch.sdk.bluetooth.utils.BleScanUtils;
import cn.baos.watch.sdk.interfac.ble.BleNativeStatusEnum;
import cn.baos.watch.sdk.interfac.ble.BleStatusEnum;
import cn.baos.watch.sdk.interfac.ble.BleStatusStateMachine;
import cn.baos.watch.sdk.interfac.ble.ConnectConfig;
import cn.baos.watch.sdk.interfac.ble.HbBtClientManager;
import cn.baos.watch.sdk.interfac.ble.IBindAdapter;
import cn.baos.watch.sdk.interfac.ble.IBleClientSdkCallback;
import cn.baos.watch.sdk.interfac.ble.IBleStatusCallback;
import cn.baos.watch.sdk.interfac.ble.IBtStatusCallback;
import cn.baos.watch.sdk.interfac.ble.IDeviceAdapter;
import cn.baos.watch.sdk.manager.jni.BlueToothJniManager;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.util.AppUtils;
import cn.baos.watch.sdk.util.DeviceIdUtil;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SharePreferenceUtils;
import cn.baos.watch.sdk.util.W100Utils;
import com.google.gson.Gson;
import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class BleService extends Service implements IBtBindRequestCallback {
    private static BleService instance;
    private static BluetoothClient mClient;
    private Set<IBleStatusCallback> bleStatusCallbackList;
    public BluetoothGattCallback bleStatusHandler;
    private BleStatusEnum curStatus;
    protected HbBleConnectStatusCallback.BleConnectEventHandler eventHandler;
    private IBindAdapter iBindAdapter;
    private IBtStatusCallback iBtStatusCallback;
    private IDeviceAdapter iDeviceCallback;
    public BluetoothAdapter mBluetoothAdapter;
    protected BluetoothGatt mBluetoothGatt;
    public BleConnectTask mConnectTask;
    public Context mContext;
    private IBleClientSdkCallback mIBleClientSdkCallback;
    private ScanCallback mScanCallBack;
    protected BleConnectProgressMonitor monitor;
    private IBleClientSdkCallback notificationHandler;
    protected Set<BleStatusStateMachine> statusHandlers;
    protected boolean isManualCloseBl = false;
    public boolean isScanAuto = false;
    public boolean isConnectStatus = false;
    public boolean isUnbind = false;
    public Boolean isScanBtStatus = null;
    public Boolean isScanBleStatus = null;
    private boolean isScanResultData = false;
    private boolean isReConnect = true;
    public int mBleConnectNum = 0;
    private long sendDataTime = 0;
    private long pairSuccessTime = 0;
    private long pairPopTime = 0;
    private long manualConnectTime = 0;
    public long pairConnectTime = 0;
    public long scanTime = 0;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        instance = this;
        init(this);
    }

    public long getScanTime() {
        return this.scanTime;
    }

    public void setScanTime(long j) {
        this.scanTime = j;
    }

    public long getSendDataTime() {
        return this.sendDataTime;
    }

    public void setSendDataTime(long j) {
        this.sendDataTime = j;
    }

    public long getPairSuccessTime() {
        return this.pairSuccessTime;
    }

    public void setPairSuccessTime(long j) {
        this.pairSuccessTime = j;
    }

    public long getManualConnectTime() {
        return this.manualConnectTime;
    }

    public void setManualConnectTime(long j) {
        this.manualConnectTime = j;
    }

    public long getPairConnectTime() {
        return this.pairConnectTime;
    }

    public void setPairConnectTime(long j) {
        this.pairConnectTime = j;
    }

    public long getPairPopTime() {
        return this.pairPopTime;
    }

    public void setPairPopTime(long j) {
        this.pairPopTime = j;
    }

    public void setSdkNotifcation(IBleClientSdkCallback iBleClientSdkCallback) {
        LogUtil.d("蓝牙：HbBtClientManager 初始化 setIBleClientSdkCallback");
        this.notificationHandler = iBleClientSdkCallback;
    }

    public IBleClientSdkCallback getNotificationHandler() {
        return this.notificationHandler;
    }

    public void setIBtBindSdkCallback(IBtStatusCallback iBtStatusCallback) {
        LogUtil.d("蓝牙：HbBtClientManager 初始化 iBtStatusCallback");
        this.iBtStatusCallback = iBtStatusCallback;
    }

    public IBtStatusCallback getBtStatusCallback() {
        return this.iBtStatusCallback;
    }

    public void setIBleBindSdkCallback(IBindAdapter iBindAdapter) {
        LogUtil.d("蓝牙：HbBtClientManager 初始化 iBleClientCallback");
        this.iBindAdapter = iBindAdapter;
    }

    public void notifyBleStatusChange(BleStatusEnum bleStatusEnum) {
        BleStatusEnum bleStatusEnum2;
        BleStatusEnum bleStatusEnum3 = this.curStatus;
        Iterator<IBleStatusCallback> it = this.bleStatusCallbackList.iterator();
        while (it.hasNext()) {
            it.next().onBleStatusChange(bleStatusEnum);
        }
        boolean z = false;
        LogUtil.d(String.format("notifyBleStatusChange:%s->%s", bleStatusEnum3, bleStatusEnum));
        Iterator<BleStatusStateMachine> it2 = this.statusHandlers.iterator();
        while (true) {
            if (!it2.hasNext()) {
                bleStatusEnum2 = null;
                break;
            }
            BleStatusStateMachine next = it2.next();
            if (next.curStatus == bleStatusEnum3) {
                bleStatusEnum2 = next.SucceedNextStatus;
                break;
            }
        }
        for (BleStatusStateMachine bleStatusStateMachine : this.statusHandlers) {
            if (bleStatusStateMachine.curStatus == bleStatusEnum) {
                LogUtil.e("蓝牙：---->oldStatus:" + bleStatusEnum3);
                LogUtil.e("蓝牙：---->oldStatus-Sccess:" + bleStatusEnum2);
                LogUtil.e("蓝牙：---->status:" + bleStatusEnum);
                LogUtil.e("蓝牙：---->def:" + new Gson().toJson(bleStatusStateMachine));
                if (bleStatusEnum2 == null || bleStatusEnum2 == bleStatusEnum || bleStatusEnum == BleStatusEnum.HB_BLE_BOND) {
                    z = true;
                }
                LogUtil.e("蓝牙：---->def isNext:" + z);
                if (bleStatusStateMachine.isAutoStateChange() && z) {
                    this.mConnectTask.mHandler.post(bleStatusStateMachine.stateChangeHandler);
                    this.curStatus = bleStatusEnum;
                    return;
                }
            }
        }
        for (BleStatusStateMachine bleStatusStateMachine2 : this.statusHandlers) {
            if (bleStatusStateMachine2.curStatus == bleStatusEnum3 && bleStatusStateMachine2.failedNextStatus == bleStatusEnum) {
                this.mConnectTask.mHandler.post(bleStatusStateMachine2.stateChangeFailedHandler);
            }
        }
        this.curStatus = bleStatusEnum;
    }

    public void registerBleStatusCallback(IBleStatusCallback iBleStatusCallback) {
        if (this.bleStatusCallbackList == null) {
            synchronized (this) {
                if (this.bleStatusCallbackList == null) {
                    this.bleStatusCallbackList = new HashSet();
                }
            }
        }
        this.bleStatusCallbackList.add(iBleStatusCallback);
    }

    public void registerBlePairCallback(IDeviceAdapter iDeviceAdapter) {
        this.iDeviceCallback = iDeviceAdapter;
    }

    public static BleService getInstance() {
        if (instance == null) {
            synchronized (BleService.class) {
                if (instance == null) {
                    instance = new BleService();
                }
            }
        }
        return instance;
    }

    public static BluetoothClient getClientInstance() {
        if (mClient == null) {
            synchronized (BluetoothClient.class) {
                if (mClient == null) {
                    mClient = new BluetoothClient(instance.mContext);
                }
            }
        }
        return mClient;
    }

    public boolean isConnectSuccess() {
        return BleStatusEnum.isBleConnected(HbBtClientManager.getInstance().getCurrentStatus());
    }

    protected void intStatusHandlerMap() {
        this.statusHandlers = new HashSet();
        BleStatusStateMachine bleStatusStateMachine = new BleStatusStateMachine(BleStatusEnum.HB_BLE_DISCONNECTED, BleStatusEnum.HB_BLE_DISCONNECTED, BleStatusEnum.HB_BLE_DEVICE_FOUND, new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda33
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onBleDisconnected();
            }
        }, new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda35
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onConnectFailed();
            }
        });
        bleStatusStateMachine.setAutoStateChange(false);
        this.statusHandlers.add(bleStatusStateMachine);
        this.statusHandlers.add(new BleStatusStateMachine(BleStatusEnum.HB_BLE_DEVICE_FOUND, BleStatusEnum.HB_BLE_DISCONNECTED, BleStatusEnum.HB_BLE_CONNECT_SUCCESS, new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.directConnect();
            }
        }, new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda35
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onConnectFailed();
            }
        }));
        this.statusHandlers.add(new BleStatusStateMachine(BleStatusEnum.HB_BLE_CONNECT_SUCCESS, BleStatusEnum.HB_BLE_DISCONNECTED, BleStatusEnum.HB_BLE_MTU_CHANGED, new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onBleConnected();
            }
        }, new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda35
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onConnectFailed();
            }
        }));
        this.statusHandlers.add(new BleStatusStateMachine(BleStatusEnum.HB_BLE_MTU_CHANGED, BleStatusEnum.HB_BLE_DISCONNECTED, BleStatusEnum.HB_BLE_SERVICE_DISCOVERED, new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.setDiscoverServices();
            }
        }, new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda35
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onConnectFailed();
            }
        }));
        this.statusHandlers.add(new BleStatusStateMachine(BleStatusEnum.HB_BLE_SERVICE_DISCOVERED, BleStatusEnum.HB_BLE_DISCONNECTED, BleStatusEnum.HB_BLE_TX_OPENED, new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.setTxWritable();
            }
        }, new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda35
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onConnectFailed();
            }
        }));
        this.statusHandlers.add(new BleStatusStateMachine(BleStatusEnum.HB_BLE_TX_OPENED, BleStatusEnum.HB_BLE_DISCONNECTED, BleStatusEnum.HB_BLE_PAIRED, new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda34
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.requestBleSuccess();
            }
        }, new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda35
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onConnectFailed();
            }
        }));
        this.statusHandlers.add(new BleStatusStateMachine(BleStatusEnum.HB_BLE_PAIRED, BleStatusEnum.HB_BLE_DISCONNECTED, BleStatusEnum.HB_BLE_BOND, new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda36
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.bindDevice();
            }
        }, new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda35
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onConnectFailed();
            }
        }));
        this.statusHandlers.add(new BleStatusStateMachine(BleStatusEnum.HB_BLE_BOND, BleStatusEnum.HB_BLE_UNBIND_SUCCESS, BleStatusEnum.HB_BLE_UNBIND_SUCCESS, new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda37
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.unBindWatch();
            }
        }, new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda38
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.unBindWatchFailed();
            }
        }));
        this.statusHandlers.add(new BleStatusStateMachine(BleStatusEnum.HB_BLE_UNBIND_SUCCESS, BleStatusEnum.HB_BLE_DISCONNECTED, BleStatusEnum.HB_BLE_DISCONNECTED, new BleService$$ExternalSyntheticLambda1(this), new BleService$$ExternalSyntheticLambda1(this)));
        getStatusChangeDef(BleStatusEnum.HB_BLE_BOND, BleStatusEnum.HB_BLE_UNBIND_SUCCESS).setAutoStateChange(false);
    }

    private BleStatusStateMachine getStatusChangeDef(BleStatusEnum bleStatusEnum, BleStatusEnum bleStatusEnum2) {
        BleStatusStateMachine bleStatusStateMachine = new BleStatusStateMachine(bleStatusEnum, bleStatusEnum, bleStatusEnum2, null, null);
        for (BleStatusStateMachine bleStatusStateMachine2 : this.statusHandlers) {
            if (bleStatusStateMachine2.equals(bleStatusStateMachine)) {
                return bleStatusStateMachine2;
            }
        }
        throw new RuntimeException();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unBindWatchFailed() {
        onUnbindSuccess();
    }

    public void init(Context context) {
        LogUtil.d("蓝牙：BTClient-init");
        this.mContext = context;
        BlueToothManager.getInstance();
        this.mBluetoothAdapter = ((BluetoothManager) context.getSystemService("bluetooth")).getAdapter();
        if (this.mConnectTask == null) {
            BleConnectTask bleConnectTask = new BleConnectTask();
            this.mConnectTask = bleConnectTask;
            bleConnectTask.start();
        }
        this.bleStatusHandler = new HbBleConnectStatusCallback(BlueToothManager.getInstance().mReceiveTask, new DefaultConnectImpl(this.mContext));
        this.monitor = new BleConnectProgressMonitor();
        intStatusHandlerMap();
        this.mScanCallBack = new ScanCallback() { // from class: cn.baos.watch.sdk.bluetooth.BleService.1
            @Override // android.bluetooth.le.ScanCallback
            public void onScanResult(int i, ScanResult scanResult) {
                BleService.this.onScanResultData(i, scanResult);
            }

            @Override // android.bluetooth.le.ScanCallback
            public void onBatchScanResults(List<ScanResult> list) {
                BleService.this.onBatchScanResultsData(list);
            }

            @Override // android.bluetooth.le.ScanCallback
            public void onScanFailed(int i) {
                BleService.this.onScanFailedData(i);
            }
        };
        getClientInstance();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: cancelScanDo, reason: merged with bridge method [inline-methods] */
    public boolean lambda$stopScan$8() {
        if (BleStatusEnum.HB_BLE_DISCONNECTED == HbBtClientManager.getInstance().getCurrentStatus()) {
            return true;
        }
        BluetoothLeScanner bluetoothLeScanner = this.mBluetoothAdapter.getBluetoothLeScanner();
        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.stopScan(this.mScanCallBack);
            notifyBleStatusChange(BleStatusEnum.HB_BLE_DISCONNECTED);
            LogUtil.d("蓝牙：低版本mMyScanCallBack,蓝牙停止扫描");
            return true;
        }
        LogUtil.d("蓝牙：cancelScanDo-RuntimeException");
        return true;
    }

    public void stopScanOnly() {
        BluetoothLeScanner bluetoothLeScanner = this.mBluetoothAdapter.getBluetoothLeScanner();
        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.stopScan(this.mScanCallBack);
        }
    }

    private ScanSettings buildScanSettings() {
        ScanSettings.Builder builder = new ScanSettings.Builder();
        builder.setScanMode(2);
        builder.setMatchMode(1);
        builder.setCallbackType(1);
        builder.setLegacy(true);
        return builder.build();
    }

    protected boolean checkBleDeviceEnabled() {
        if (this.mBluetoothAdapter.isEnabled()) {
            return true;
        }
        LogUtil.d("蓝牙：扫描scan:蓝牙未打开");
        IBleClientSdkCallback iBleClientSdkCallback = this.mIBleClientSdkCallback;
        if (iBleClientSdkCallback != null) {
            iBleClientSdkCallback.onBleDeviceStateChanged(false);
        }
        HbBtClientManager.getInstance().onBleStatusChange(BleStatusEnum.HB_BLE_DISCONNECTED);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: scanDo, reason: merged with bridge method [inline-methods] */
    public boolean lambda$requestScanDevice$3() {
        if (HbBtClientManager.getInstance().getCurrentStatus() == BleStatusEnum.HB_BLE_SCANNING) {
            return true;
        }
        this.isScanBleStatus = null;
        LogUtil.d("蓝牙：蓝牙->scan()");
        if (!checkBleDeviceEnabled()) {
            return false;
        }
        this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$scanDo$1();
            }
        }, DateUtils.MILLIS_PER_MINUTE);
        LogUtil.d("蓝牙：---isScanAuto> :" + this.isScanAuto);
        if (this.isScanAuto) {
            onDeviceFound();
            return false;
        }
        scanDoBt();
        BleStatusEnum currentStatus = HbBtClientManager.getInstance().getCurrentStatus();
        if (currentStatus == BleStatusEnum.HB_BLE_SCANNING || currentStatus == BleStatusEnum.HB_BLE_DISCONNECTED) {
            lambda$stopScan$8();
        }
        BluetoothLeScanner bluetoothLeScanner = this.mBluetoothAdapter.getBluetoothLeScanner();
        this.isScanResultData = false;
        this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.startScanError();
            }
        }, WorkRequest.MIN_BACKOFF_MILLIS);
        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.stopScan(this.mScanCallBack);
            LogUtil.d("蓝牙：蓝牙scanner不为空");
            LogUtil.d("蓝牙：低版本扫描回调连接,蓝牙开始扫描");
            bluetoothLeScanner.startScan(new ArrayList(), buildScanSettings(), this.mScanCallBack);
            ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
            if (currentConnectConfig != null && !currentConnectConfig.isScan) {
                getNotificationHandler().onBLEConnecting("");
            } else {
                getNotificationHandler().onBLEStartScan();
            }
            notifyBleStatusChange(BleStatusEnum.HB_BLE_SCANNING);
        } else {
            LogUtil.d("蓝牙：蓝牙scanner为空RuntimeException");
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scanDo$1() {
        BleStatusEnum currentStatus = HbBtClientManager.getInstance().getCurrentStatus();
        if (currentStatus == BleStatusEnum.HB_BLE_SCANNING || currentStatus == BleStatusEnum.HB_BLE_CONNECTING) {
            onDisconnected();
            this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda24
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$scanDo$0();
                }
            }, 100L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scanDo$0() {
        getNotificationHandler().onBLEConnectTimeOut();
    }

    public void startScan() {
        LogUtil.d("蓝牙：蓝牙->startScan()");
        BluetoothLeScanner bluetoothLeScanner = this.mBluetoothAdapter.getBluetoothLeScanner();
        if (bluetoothLeScanner != null) {
            LogUtil.d("蓝牙：蓝牙scanner不为空");
            LogUtil.d("蓝牙：低版本扫描回调连接,蓝牙开始扫描");
            bluetoothLeScanner.startScan(new ArrayList(), buildScanSettings(), this.mScanCallBack);
            getNotificationHandler().onBLEStartScan();
            return;
        }
        LogUtil.d("蓝牙：蓝牙scanner为空RuntimeException");
    }

    public void stopSearchScan() {
        BluetoothLeScanner bluetoothLeScanner = this.mBluetoothAdapter.getBluetoothLeScanner();
        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.stopScan(this.mScanCallBack);
            LogUtil.d("蓝牙：蓝牙停止扫描");
        } else {
            LogUtil.d("蓝牙：-scanner=null-RuntimeException");
        }
        BleStatusEnum currentStatus = HbBtClientManager.getInstance().getCurrentStatus();
        if (currentStatus == null || currentStatus != BleStatusEnum.HB_BLE_SCANNING) {
            return;
        }
        notifyBleStatusChange(BleStatusEnum.HB_BLE_DISCONNECTED);
    }

    public void scanDoBt() {
        this.isScanBtStatus = null;
        this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$scanDoBt$2();
            }
        }, 1000L);
        getClientInstance().search(new SearchRequest.Builder().searchBluetoothClassicDevice(1000).build(), new SearchResponse() { // from class: cn.baos.watch.sdk.bluetooth.BleService.2
            @Override // com.inuker.bluetooth.library.search.response.SearchResponse
            public void onSearchCanceled() {
            }

            @Override // com.inuker.bluetooth.library.search.response.SearchResponse
            public void onSearchStarted() {
            }

            @Override // com.inuker.bluetooth.library.search.response.SearchResponse
            public void onSearchStopped() {
            }

            @Override // com.inuker.bluetooth.library.search.response.SearchResponse
            public void onDeviceFounded(SearchResult searchResult) {
                LogUtil.e("蓝牙：---onDeviceFounded:" + searchResult.getAddress());
                ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
                if (currentConnectConfig != null && StringUtils.isNotEmpty(currentConnectConfig.macAddress) && currentConnectConfig.macAddress.equals(searchResult.getAddress())) {
                    LogUtil.e("蓝牙：onDeviceFounded-sure=" + currentConnectConfig.macAddress);
                    BleService.this.isScanBtStatus = true;
                    BleService.this.scanFinish();
                    BleService.getClientInstance().stopSearch();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scanDoBt$2() {
        if (this.isScanBtStatus == null) {
            this.isScanBtStatus = false;
            scanFinish();
            getClientInstance().stopSearch();
        }
    }

    public void requestDisconnerDevice() {
        LogUtil.d("蓝牙：requestDisconnerDevice");
        disconnect();
    }

    public void requestScanDevice() {
        LogUtil.d("蓝牙：requestScanDevice");
        this.mConnectTask.mHandler.post(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$requestScanDevice$3();
            }
        });
    }

    protected void requestMtuChange() {
        this.mConnectTask.mHandler.post(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$requestMtuChange$6();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestMtuChange$6() {
        BleStatusEnum currentStatus = HbBtClientManager.getInstance().getCurrentStatus();
        LogUtil.d("蓝牙：--requestMtuChange");
        LogUtil.d("蓝牙：--requestMtuChange : curStatus :" + currentStatus);
        if (currentStatus == BleStatusEnum.HB_BLE_MTU_CHANGING || this.mBluetoothGatt == null) {
            return;
        }
        notifyBleStatusChange(BleStatusEnum.HB_BLE_MTU_CHANGING);
        HbBtClientManager.getInstance().onBleStatusChange(BleStatusEnum.HB_BLE_MTU_CHANGING);
        LogUtil.d("蓝牙：---HB_BLE_MTU_CHANGING--->>>currentStatus:" + HbBtClientManager.getInstance().getCurrentStatus());
        this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda27
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$requestMtuChange$4();
            }
        }, 200L);
        this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$requestMtuChange$5();
            }
        }, 20000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestMtuChange$4() {
        LogUtil.d("蓝牙：--requestMtuChange-start=" + HbBtClientManager.getInstance().getCurrentConnectConfig().maxBleMtuSize);
        HbBtClientManager.getInstance().onBleStatusChange(BleStatusEnum.HB_BLE_MTU_CHANGING);
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt != null) {
            LogUtil.d("蓝牙：--requestMtu-mtu-status=" + bluetoothGatt.requestMtu(BTConstant.MAX_BLE_MTU_SIZE));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestMtuChange$5() {
        BleStatusEnum currentStatus = HbBtClientManager.getInstance().getCurrentStatus();
        if (currentStatus == BleStatusEnum.HB_BLE_MTU_CHANGING) {
            LogUtil.d("蓝牙：--requestMtu-mtu-current=" + currentStatus);
            notifyBleStatusChange(BleStatusEnum.HB_BLE_MTU_CHANGED);
        }
    }

    public void setDiscoverServices() {
        BleStatusEnum currentStatus = HbBtClientManager.getInstance().getCurrentStatus();
        LogUtil.d("蓝牙：----setDiscoverServices==" + currentStatus);
        if (currentStatus == BleStatusEnum.HB_BLE_MTU_CHANGED) {
            notifyBleStatusChange(BleStatusEnum.HB_BLE_SERVICE_DISCOVEREDING);
            LogUtil.d("蓝牙：----setDiscoverServices==discoverServices");
            if (this.mBluetoothGatt != null) {
                LogUtil.d("蓝牙：----setDiscoverServices==real");
                this.mBluetoothGatt.discoverServices();
            }
        }
    }

    public void setTxWritable() {
        this.mConnectTask.mHandler.post(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda30
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setTxWritable$7();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setTxWritable$7() {
        ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt == null || currentConnectConfig == null) {
            return;
        }
        BluetoothGattService service = bluetoothGatt.getService(currentConnectConfig.SERVICE_UUID);
        if (service != null) {
            for (BluetoothGattCharacteristic bluetoothGattCharacteristic : service.getCharacteristics()) {
                LogUtil.d("蓝牙：蓝牙characteristic.getUuid():" + bluetoothGattCharacteristic.getUuid());
                if (bluetoothGattCharacteristic.getUuid().equals(currentConnectConfig.CHAR_NOTIFICATION_UUID)) {
                    LogUtil.d("蓝牙：蓝牙isEnableNotification:" + Boolean.valueOf(this.mBluetoothGatt.setCharacteristicNotification(bluetoothGattCharacteristic, true)));
                    BluetoothGattDescriptor descriptor = bluetoothGattCharacteristic.getDescriptor(currentConnectConfig.UUID_CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR);
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    LogUtil.d("蓝牙：蓝牙onServicesDiscovered--writeDescriptor=>" + this.mBluetoothGatt.writeDescriptor(descriptor));
                    return;
                }
            }
            return;
        }
        LogUtil.d("蓝牙：蓝牙can't find service ->:" + currentConnectConfig.SERVICE_UUID);
    }

    public boolean startReConnect() {
        ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
        if (currentConnectConfig == null || !currentConnectConfig.isActive) {
            return false;
        }
        startConnect();
        return true;
    }

    public boolean startConnect() {
        this.isReConnect = true;
        ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
        BleStatusEnum currentStatus = HbBtClientManager.getInstance().getCurrentStatus();
        if (currentConnectConfig == null) {
            return false;
        }
        LogUtil.d("蓝牙：---startConnect--" + currentStatus + "-----" + this.isUnbind);
        if (currentStatus == BleStatusEnum.HB_BLE_SCANNING || this.isUnbind || currentStatus == BleStatusEnum.HB_BLE_CONNECTING) {
            LogUtil.d("蓝牙：---startConnect--连接中-----" + this.isUnbind);
            return true;
        }
        if (currentStatus == BleStatusEnum.HB_BLE_PAIR_ING) {
            LogUtil.e("蓝牙：配对状态  start-: ing");
            return true;
        }
        LogUtil.d("蓝牙：---startConnect--" + currentStatus + "-----" + getInstance().isConnectStatus);
        if (getInstance().isConnectStatus) {
            if (getInstance().isConnectSuccess() && currentStatus == BleStatusEnum.HB_BLE_BOND) {
                IBindAdapter iBindAdapter = this.iBindAdapter;
                if (iBindAdapter != null) {
                    iBindAdapter.onBindSuccess();
                }
                LogUtil.d("蓝牙：--- 已完成配对");
            }
            return true;
        }
        if (currentConnectConfig.isActive) {
            LogUtil.d("蓝牙：---startConnect--connectConfig.isJLW6:" + currentConnectConfig.isJLW6 + "");
            try {
                LogUtil.d("蓝牙：---startConnect--connectConfig.isPair:" + BT625Client.getInstance().isBondedDevices() + "");
                directConnect();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            LogUtil.d("蓝牙：---startConnect-scan");
            requestScanDevice();
        }
        return true;
    }

    public boolean writeCharacteristic(byte[] bArr) {
        BluetoothGatt bluetoothGatt;
        ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
        if (!BleStatusEnum.isBleConnected(HbBtClientManager.getInstance().getCurrentStatus()) || (bluetoothGatt = this.mBluetoothGatt) == null) {
            return false;
        }
        BluetoothGattService service = bluetoothGatt.getService(currentConnectConfig.SERVICE_UUID);
        if (service != null) {
            BluetoothGattCharacteristic characteristic = service.getCharacteristic(currentConnectConfig.CHAR_WRITE_WITHOUT_RESPONSE_NOTIFY_UUID);
            characteristic.setValue(bArr);
            LogUtil.d("蓝牙：send-device: 发送给设备的日志:" + W100Utils.byte2hex(bArr));
            this.mBluetoothGatt.writeCharacteristic(characteristic);
            return true;
        }
        LogUtil.d("蓝牙：write characteristic failed, service no founded");
        return false;
    }

    public boolean stopScan() {
        this.mConnectTask.mHandler.post(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$stopScan$8();
            }
        });
        return true;
    }

    public void disconnect() {
        LogUtil.d("蓝牙：disconnect");
        this.mConnectTask.mHandler.post(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$disconnect$9();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$disconnect$9() {
        try {
            new AppUtils().unregister(this.mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtil.d("蓝牙：蓝牙:disconnect");
        BleStatusEnum currentStatus = HbBtClientManager.getInstance().getCurrentStatus();
        if (currentStatus != null) {
            LogUtil.d("蓝牙：蓝牙:disconnect:" + currentStatus.toString());
        }
        if (currentStatus == BleStatusEnum.HB_BLE_DISCONNECTED) {
            return;
        }
        LogUtil.d("蓝牙：蓝牙:mBluetoothGatt()" + this.mBluetoothGatt);
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt != null) {
            bluetoothGatt.disconnect();
        }
        this.isManualCloseBl = true;
        notifyBleStatusChange(BleStatusEnum.HB_BLE_DISCONNECTED);
        try {
            BasSdk.registerKeepLive(this.mContext);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void onConnectFailed() {
        this.isConnectStatus = false;
        LogUtil.d("蓝牙：onConnectFailed");
        getNotificationHandler().onBLEConnectFail();
        notifyBleStatusChange(BleStatusEnum.HB_BLE_CONNECT_FAIL);
        LogUtil.d("蓝牙：onConnectFailed-->mBluetoothGatt:= " + this.mBluetoothGatt);
        if (this.mBluetoothGatt != null) {
            LogUtil.d("蓝牙：onConnectFailed-->mBluetoothGatt:= yes");
            try {
                this.mBluetoothGatt.disconnect();
                this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda20
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onConnectFailed$10();
                    }
                }, WorkRequest.MIN_BACKOFF_MILLIS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LogUtil.d("蓝牙：onConnectFailed-->mBluetoothGatt:= no");
            this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onConnectFailed$11();
                }
            }, 5000L);
        }
        BlueToothJniManager.bleWritableNotify(BleNativeStatusEnum.HB_BLE_DISCONNECTED.mValue);
        LogUtil.d("蓝牙：BlueToothJniManager.bleWritableNotify(HB_BLE_DISCONNECTED)=;" + BleNativeStatusEnum.HB_BLE_DISCONNECTED);
        this.isManualCloseBl = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onConnectFailed$10() {
        BleStatusEnum currentStatus = HbBtClientManager.getInstance().getCurrentStatus();
        LogUtil.d("蓝牙：onConnectFailed-->onConnectFailed:= no :" + currentStatus);
        if (currentStatus == BleStatusEnum.HB_BLE_CONNECT_FAIL) {
            LogUtil.d("蓝牙：onConnectFailed-->onConnectFailed:= no :disabled");
            onBleDisconnected();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onConnectFailed$11() {
        BleStatusEnum currentStatus = HbBtClientManager.getInstance().getCurrentStatus();
        LogUtil.d("蓝牙：onConnectFailed-->mBluetoothGatt:= no :" + currentStatus);
        if (currentStatus != BleStatusEnum.HB_BLE_DISCONNECTED) {
            LogUtil.d("蓝牙：onConnectFailed-->mBluetoothGatt:= no :disabled");
            onBleDisconnected();
        }
    }

    protected void onBleConnected() {
        this.mConnectTask.mHandler.post(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda25
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onBleConnected$12();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBleConnected$12() {
        LogUtil.d("蓝牙：--onBleConnected--HB_BLE_CONNECT_SUCCESS");
        requestMtuChange();
        try {
            BasSdk.registerKeepLive(this.mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void directConnect() {
        LogUtil.d("蓝牙：call direct connect.");
        this.monitor.monitorTimeOut(new BleConnectProgressMonitor.StateChangeTimeoutConfig(BleStatusEnum.HB_BLE_DEVICE_FOUND, BleStatusEnum.HB_BLE_CONNECT_SUCCESS, 1, 2000));
        this.mConnectTask.mHandler.post(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$directConnect$13();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: connectNoScanDo, reason: merged with bridge method [inline-methods] */
    public boolean lambda$directConnect$13() {
        BluetoothAdapter bluetoothAdapter;
        if (!checkBleDeviceEnabled()) {
            getNotificationHandler().onGpsNotOpen();
            return false;
        }
        LogUtil.d("蓝牙：connectNoScanDo-start");
        if (HbBtClientManager.getInstance().getCurrentConnectConfig() == null) {
            LogUtil.d("蓝牙：ConnectConfig is null,not ready.");
            return false;
        }
        String str = HbBtClientManager.getInstance().getCurrentConnectConfig().macAddress;
        if (StringUtils.isEmpty(str) || (bluetoothAdapter = this.mBluetoothAdapter) == null) {
            LogUtil.d("蓝牙：---macAddress=null-RuntimeException");
            return false;
        }
        try {
            BluetoothDevice remoteDevice = bluetoothAdapter.getRemoteDevice(str);
            if (remoteDevice == null) {
                LogUtil.d("蓝牙：蓝牙gatt device not found:" + str);
                return false;
            }
            BluetoothLeScanner bluetoothLeScanner = this.mBluetoothAdapter.getBluetoothLeScanner();
            if (bluetoothLeScanner != null) {
                bluetoothLeScanner.stopScan(this.mScanCallBack);
                LogUtil.d("蓝牙：蓝牙停止扫描启动连接");
            } else {
                LogUtil.d("蓝牙：-scanner=null-RuntimeException");
            }
            LogUtil.d("蓝牙：connectGatt" + str);
            if (BleStatusEnum.HB_BLE_CONNECTING != HbBtClientManager.getInstance().getCurrentStatus()) {
                LogUtil.e("蓝牙：----connect-start");
                getNotificationHandler().onBLEConnecting("");
                this.mBluetoothGatt = remoteDevice.connectGatt(this.mContext, false, this.bleStatusHandler, 2);
                notifyBleStatusChange(BleStatusEnum.HB_BLE_CONNECTING);
                return true;
            }
            LogUtil.e("蓝牙：----connect-no：" + HbBtClientManager.getInstance().getCurrentStatus());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public void onDisconnected() {
        LogUtil.d("蓝牙：蓝牙onDisconnected");
        lambda$stopScan$8();
        disconnect();
        notifyBleStatusChange(BleStatusEnum.HB_BLE_DISCONNECTED);
        BlueToothJniManager.bleWritableNotify(BleNativeStatusEnum.HB_BLE_DISCONNECTED.mValue);
        LogUtil.e("蓝牙：BlueToothJniManager.bleWritableNotify();=" + BleNativeStatusEnum.HB_BLE_DISCONNECTED);
    }

    protected void requestBleSuccess() {
        this.mBleConnectNum++;
        getNotificationHandler().onBLEConnected();
        BlueToothJniManager.bleWritableNotify(BleNativeStatusEnum.HB_BLE_WRITABLE.mValue);
        LogUtil.d("蓝牙：BlueToothJniManager.bleWritableNotify(BleNativeStatusEnum.HB_BLE_WRITABLE);=" + BleNativeStatusEnum.HB_BLE_WRITABLE);
        LogUtil.d("蓝牙：--requestBleSuccess");
        MessageManager.getInstance().sendPhoneBindRequest111(1111, DataUtils.getPhoneType(this.mContext));
        LogUtil.d("蓝牙：---startConnect-onBlePair - send -llll");
        this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$requestBleSuccess$14();
            }
        }, 2000L);
        this.monitor.monitorTimeOut(new BleConnectProgressMonitor.StateChangeTimeoutConfig(BleStatusEnum.HB_BLE_TX_OPENED, BleStatusEnum.HB_BLE_DISCONNECTED, 0, 6000));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestBleSuccess$14() {
        this.iDeviceCallback.onBlePair();
    }

    public void onCurrectStatusChangeFailed(BleStatusEnum bleStatusEnum, BleStatusEnum bleStatusEnum2) {
        LogUtil.d("蓝牙：call failed handler.");
        for (BleStatusStateMachine bleStatusStateMachine : this.statusHandlers) {
            if (bleStatusStateMachine.curStatus == bleStatusEnum && bleStatusStateMachine.SucceedNextStatus == bleStatusEnum2) {
                this.mConnectTask.mHandler.post(bleStatusStateMachine.stateChangeFailedHandler);
            }
        }
        if (bleStatusEnum == BleStatusEnum.HB_BLE_DEVICE_FOUND || bleStatusEnum == BleStatusEnum.HB_BLE_CONNECTING || bleStatusEnum == BleStatusEnum.HB_BLE_TX_OPENED) {
            if (bleStatusEnum2 == BleStatusEnum.HB_BLE_DISCONNECTED || bleStatusEnum2 == BleStatusEnum.HB_BLE_CONNECT_FAIL) {
                BluetoothLeScanner bluetoothLeScanner = this.mBluetoothAdapter.getBluetoothLeScanner();
                if (bluetoothLeScanner != null) {
                    bluetoothLeScanner.stopScan(this.mScanCallBack);
                    notifyBleStatusChange(BleStatusEnum.HB_BLE_DISCONNECTED);
                    LogUtil.d("蓝牙：低版本mMyScanCallBack,蓝牙停止扫描");
                }
                onBleDisconnected();
            }
        }
    }

    public void onServicesDiscovered() {
        BleStatusEnum currentStatus = HbBtClientManager.getInstance().getCurrentStatus();
        if (currentStatus == BleStatusEnum.HB_BLE_DISCONNECTED || currentStatus == BleStatusEnum.HB_BLE_CONNECT_FAIL) {
            return;
        }
        notifyBleStatusChange(BleStatusEnum.HB_BLE_SERVICE_DISCOVERED);
    }

    public void requestOpenBle() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter != null) {
            bluetoothAdapter.enable();
        }
    }

    public boolean isBleOpen() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter != null) {
            return bluetoothAdapter.isEnabled();
        }
        return false;
    }

    public void setIBleClientSdkCallback(IBleClientSdkCallback iBleClientSdkCallback) {
        this.mIBleClientSdkCallback = iBleClientSdkCallback;
    }

    class DefaultConnectImpl extends BroadcastReceiver implements HbBleConnectStatusCallback.BleConnectEventHandler {
        @Override // cn.baos.watch.sdk.bluetooth.HbBleConnectStatusCallback.BleConnectEventHandler
        public void handleBleConnectEvent(HbBleConnectStatusCallback hbBleConnectStatusCallback, HbBleConnectStatusCallback.BleConnectEvent bleConnectEvent) {
            LogUtil.d("蓝牙：------------handleBleConnectEvent----" + bleConnectEvent);
            int i = AnonymousClass5.$SwitchMap$cn$baos$watch$sdk$bluetooth$HbBleConnectStatusCallback$BleConnectEvent[bleConnectEvent.ordinal()];
            boolean z = true;
            if (i != 1) {
                if (i == 3) {
                    if (hbBleConnectStatusCallback.writeSucceed) {
                        BlueToothJniManager.bleWritableNotify(BleNativeStatusEnum.HB_BLE_WRITABLE.mValue);
                        LogUtil.d("蓝牙：BlueToothJniManager.bleWritableNotify(BleNativeStatusEnum.HB_BLE_WRITABLE)=;" + BleNativeStatusEnum.HB_BLE_WRITABLE);
                        return;
                    }
                    return;
                }
                if (i != 4) {
                    if (i == 5) {
                        BleService.this.onServicesDiscovered();
                        return;
                    } else {
                        if (i == 6 && hbBleConnectStatusCallback.txWritable) {
                            onTxWritable();
                            return;
                        }
                        return;
                    }
                }
                if (hbBleConnectStatusCallback.mtuChanged) {
                    LogUtil.d("蓝牙：---MTU_CHANGE_RESULT--- auto");
                    BleStatusEnum currentStatus = HbBtClientManager.getInstance().getCurrentStatus();
                    LogUtil.d("蓝牙：---MTU_CHANGE_RESULT--->>>currentStatus:" + currentStatus);
                    if (currentStatus == BleStatusEnum.HB_BLE_MTU_CHANGING) {
                        BleService.this.notifyBleStatusChange(BleStatusEnum.HB_BLE_MTU_CHANGED);
                        return;
                    }
                    return;
                }
                return;
            }
            if (hbBleConnectStatusCallback.connected) {
                BleService.this.isConnectStatus = true;
                LogUtil.e("蓝牙：------------连接成功----");
                BleService.this.isScanAuto = true;
                BleStatusEnum currentStatus2 = HbBtClientManager.getInstance().getCurrentStatus();
                boolean zIsBondedDevices = BT625Client.getInstance().isBondedDevices();
                if (!BleStatusEnum.hasBleConnectError(currentStatus2) && !zIsBondedDevices) {
                    LogUtil.d("蓝牙：---startConnect-onBlePair - send -success");
                    BleService.this.iDeviceCallback.onBlePair();
                } else if (zIsBondedDevices) {
                    int iBtIsConnect = BT625Client.getInstance().btIsConnect();
                    LogUtil.d("蓝牙：---startConnect-isPair - success -> BT CONNECT:" + iBtIsConnect);
                    if (iBtIsConnect != 1) {
                        if (HbBtClientManager.getInstance().getCurrentConnectConfig().isActive) {
                            BleService.this.iDeviceCallback.onBlePair();
                        }
                    } else {
                        LogUtil.d("蓝牙：---startConnect-isPair - success -> BT CONNECT:已连接");
                    }
                    z = false;
                } else {
                    z = false;
                }
                if (!BleStatusEnum.hasBleConnectError(currentStatus2) && !z) {
                    LogUtil.d("蓝牙：---startConnect-正常流程");
                    BleService.this.notifyBleStatusChange(BleStatusEnum.HB_BLE_CONNECT_SUCCESS);
                    HbBtClientManager.getInstance().onBleStatusChange(BleStatusEnum.HB_BLE_CONNECT_SUCCESS);
                    BleService.this.stopScanOnly();
                    return;
                }
                LogUtil.d("蓝牙：---startConnect-正常流程->2");
                if (z) {
                    return;
                }
                LogUtil.d("蓝牙：---startConnect-正常流程->des");
                BleService.this.iDeviceCallback.onBlePair();
                return;
            }
            LogUtil.e("蓝牙：------------连接断开----");
            MessageManager.getInstance().setSppTransLateData(false);
            ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
            if (currentConnectConfig != null) {
                if (currentConnectConfig.isActive) {
                    BleService.this.onBleDisconnected();
                } else if (System.currentTimeMillis() - BleService.getInstance().getManualConnectTime() < 5000) {
                    LogUtil.d("蓝牙：断开- 判断为手动断开，但是系統已配對 鏈接一次");
                    boolean zIsBondedDevices2 = BT625Client.getInstance().isBondedDevices();
                    int iBtIsConnect2 = BT625Client.getInstance().btIsConnect();
                    LogUtil.d("蓝牙：断开- 判断为手动断开，但是系統已配對 : isPair:" + zIsBondedDevices2 + "--- isBt" + iBtIsConnect2);
                    if (zIsBondedDevices2 && iBtIsConnect2 == 1) {
                        BleService.this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$DefaultConnectImpl$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleBleConnectEvent$0();
                            }
                        }, 500L);
                    }
                } else {
                    LogUtil.d("蓝牙：断开- 直接连接");
                    if (BleService.this.isReConnect) {
                        BleService.this.notifyBleStatusChange(BleStatusEnum.HB_BLE_SCANNING);
                        BleService.this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$DefaultConnectImpl$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$handleBleConnectEvent$1();
                            }
                        }, 5000L);
                        BleService.this.isReConnect = false;
                    } else {
                        BleService.this.onBleDisconnected();
                    }
                }
                BleService.this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$DefaultConnectImpl$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$handleBleConnectEvent$2();
                    }
                }, 2000L);
                BleService.this.isScanAuto = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleBleConnectEvent$0() {
            BleService.this.directConnect();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleBleConnectEvent$1() {
            BleService.this.directConnect();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleBleConnectEvent$2() {
            BleService.this.isConnectStatus = false;
        }

        protected void onTxWritable() {
            BleService.this.notifyBleStatusChange(BleStatusEnum.HB_BLE_TX_OPENED);
        }

        public DefaultConnectImpl(Context context) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
            context.registerReceiver(this, intentFilter);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0);
                if (intExtra == 10) {
                    LogUtil.d("蓝牙：蓝牙设备已关闭");
                    BleService.this.isConnectStatus = false;
                    BleService.this.onBleDisconnected();
                    BleService.this.getNotificationHandler().onGpsNotOpen();
                    return;
                }
                if (intExtra != 12) {
                    return;
                }
                LogUtil.d("蓝牙：蓝牙设备已打开");
                boolean zIsBindWatch = AppDataConfig.getInstance().isBindWatch();
                if (BleService.this.isConnectStatus || !zIsBindWatch) {
                    return;
                }
                Handler handler = BleService.this.mConnectTask.mHandler;
                final BleService bleService = BleService.this;
                handler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$DefaultConnectImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        bleService.startConnect();
                    }
                }, 2000L);
            }
        }
    }

    /* JADX INFO: renamed from: cn.baos.watch.sdk.bluetooth.BleService$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$cn$baos$watch$sdk$bluetooth$HbBleConnectStatusCallback$BleConnectEvent;

        static {
            int[] iArr = new int[HbBleConnectStatusCallback.BleConnectEvent.values().length];
            $SwitchMap$cn$baos$watch$sdk$bluetooth$HbBleConnectStatusCallback$BleConnectEvent = iArr;
            try {
                iArr[HbBleConnectStatusCallback.BleConnectEvent.CONNECT_STATE_CHANGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$baos$watch$sdk$bluetooth$HbBleConnectStatusCallback$BleConnectEvent[HbBleConnectStatusCallback.BleConnectEvent.DATA_ARRIVED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$cn$baos$watch$sdk$bluetooth$HbBleConnectStatusCallback$BleConnectEvent[HbBleConnectStatusCallback.BleConnectEvent.WRITE_RESULT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$cn$baos$watch$sdk$bluetooth$HbBleConnectStatusCallback$BleConnectEvent[HbBleConnectStatusCallback.BleConnectEvent.MTU_CHANGE_RESULT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$cn$baos$watch$sdk$bluetooth$HbBleConnectStatusCallback$BleConnectEvent[HbBleConnectStatusCallback.BleConnectEvent.SERVICE_DISCOVERED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$cn$baos$watch$sdk$bluetooth$HbBleConnectStatusCallback$BleConnectEvent[HbBleConnectStatusCallback.BleConnectEvent.TX_WRITABLE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public void onBleDisconnected() {
        LogUtil.d("蓝牙：----onBleDisconnected");
        BluetoothGatt bluetoothGatt = this.mBluetoothGatt;
        if (bluetoothGatt != null) {
            bluetoothGatt.close();
            this.mBluetoothGatt = null;
        }
        this.mConnectTask.mHandler.post(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                BleService.lambda$onBleDisconnected$15();
            }
        });
        notifyBleStatusChange(BleStatusEnum.HB_BLE_DISCONNECTED);
        getNotificationHandler().onBLEDisConnected();
        LogUtil.d("蓝牙：---------------start--time");
        this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onBleDisconnected$17();
            }
        }, 20000L);
    }

    static /* synthetic */ void lambda$onBleDisconnected$15() {
        BleStatusEnum currentStatus = HbBtClientManager.getInstance().getCurrentStatus();
        LogUtil.d("蓝牙：----disconnct---" + currentStatus);
        if (currentStatus == BleStatusEnum.HB_BLE_DISCONNECTED) {
            BlueToothJniManager.bleWritableNotify(BleNativeStatusEnum.HB_BLE_DISCONNECTED.mValue);
            LogUtil.e("蓝牙：BlueToothJniManager.bleWritableNotify(BleNativeStatusEnum.HB_BLE_DISCONNECTED);=" + BleNativeStatusEnum.HB_BLE_DISCONNECTED);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBleDisconnected$17() {
        ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
        LogUtil.d("蓝牙：---------------start--config :" + currentConnectConfig + "--->");
        if (currentConnectConfig == null) {
            return;
        }
        if (!currentConnectConfig.isActive) {
            LogUtil.d("蓝牙：---------------start--config isActive:" + (!currentConnectConfig.isActive) + "--->");
            return;
        }
        BleStatusEnum currentStatus = HbBtClientManager.getInstance().getCurrentStatus();
        if (currentStatus == BleStatusEnum.HB_BLE_DISCONNECTED || currentStatus == BleStatusEnum.HB_BLE_CONNECT_FAIL) {
            LogUtil.d("蓝牙：---onBleDisconnected-startConnect");
            LogUtil.d("蓝牙：---------------end--time");
            startConnect();
            this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onBleDisconnected$16();
                }
            }, WorkRequest.MIN_BACKOFF_MILLIS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBleDisconnected$16() {
        if (HbBtClientManager.getInstance().getCurrentStatus() == BleStatusEnum.HB_BLE_CONNECTING) {
            onBleDisconnected();
        }
    }

    public class BleConnectTask extends Thread {
        private UUID connect_session;
        public Handler mHandler;

        public BleConnectTask() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Looper.prepare();
            this.mHandler = new Handler(Looper.myLooper()) { // from class: cn.baos.watch.sdk.bluetooth.BleService.BleConnectTask.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    message.getCallback().run();
                }
            };
            Looper.loop();
        }

        public void runSessionAsyncDelayJob(Runnable runnable, int i) {
            if (this.connect_session == null) {
                synchronized (this) {
                    if (this.connect_session == null) {
                        this.connect_session = UUID.randomUUID();
                    }
                }
            }
            BleService.this.mConnectTask.mHandler.postDelayed(runnable, this.connect_session, i);
        }

        public void cleaSessionAsyncDelayJob() {
            if (this.connect_session != null) {
                BleService.this.mConnectTask.mHandler.removeCallbacksAndMessages(this.connect_session);
                this.connect_session = null;
            }
            BleService.this.mConnectTask.mHandler.removeCallbacks(null);
            BleService.this.mConnectTask.mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override // cn.baos.watch.sdk.bluetooth.callback.IBtBindRequestCallback
    public void onBindSuccess() {
        this.isReConnect = true;
        this.mBleConnectNum = 0;
        BlueToothManager.getInstance().cleanMessageQueue();
        this.mConnectTask.cleaSessionAsyncDelayJob();
        BleStatusEnum currentStatus = HbBtClientManager.getInstance().getCurrentStatus();
        if (currentStatus == BleStatusEnum.HB_BLE_UNBINDING || currentStatus == BleStatusEnum.HB_BLE_UNBIND_SUCCESS) {
            return;
        }
        IBindAdapter iBindAdapter = this.iBindAdapter;
        if (iBindAdapter != null) {
            iBindAdapter.onBindSuccess();
        }
        notifyBleStatusChange(BleStatusEnum.HB_BLE_BOND);
        ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
        if (currentConnectConfig == null) {
            return;
        }
        currentConnectConfig.isActive = true;
        SharePreferenceUtils.saveStringByKey(this.mContext, SharePreferenceUtils.KEY_LAST_MAC, currentConnectConfig.macAddress);
        AppDataConfig.getInstance().saveCurrentConfig(currentConnectConfig);
    }

    public void initSppConnect() {
        LogUtil.e("initSppConnect start");
        boolean zQueryBooleanByKeySetBoolean = SharePreferenceUtils.queryBooleanByKeySetBoolean(this.mContext, SharePreferenceUtils.KEY_SPP_IS_OPEN, false);
        if (zQueryBooleanByKeySetBoolean) {
            LogUtil.e("initSppConnect start is not supported" + zQueryBooleanByKeySetBoolean);
            this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService.3
                @Override // java.lang.Runnable
                public void run() {
                    LogUtil.e("initSppConnect start is not supported connect staus");
                    if (HbBtClientManager.getInstance().mBLESPPUtils.getConnectStatus()) {
                        return;
                    }
                    LogUtil.e("initSppConnect start is not supported connect status - start ing");
                    HbBtClientManager.getInstance().mBLESPPUtils.connect(HbBtClientManager.getInstance().getCurrentConnectConfig().macAddress);
                }
            }, 1000L);
        }
    }

    @Override // cn.baos.watch.sdk.bluetooth.callback.IBtBindRequestCallback
    public void onUnbindSuccess() {
        BlueToothManager.getInstance().cleanMessageQueue();
        LogUtil.d("蓝牙：current status is wrongonUnbindSuccess");
        IBindAdapter iBindAdapter = this.iBindAdapter;
        if (iBindAdapter != null) {
            iBindAdapter.onBindFail();
        }
        notifyBleStatusChange(BleStatusEnum.HB_BLE_UNBIND_SUCCESS);
        this.monitor.monitorTimeOut(new BleConnectProgressMonitor.StateChangeTimeoutConfig(BleStatusEnum.HB_BLE_UNBIND_SUCCESS, BleStatusEnum.HB_BLE_DISCONNECTED, 0, 100));
    }

    protected boolean requestRemoveBt() {
        LogUtil.d("蓝牙：--requestRemoveBt");
        this.mConnectTask.mHandler.post(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$requestRemoveBt$20();
            }
        });
        this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda32
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$requestRemoveBt$21();
            }
        }, 1000L);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestRemoveBt$20() {
        BlueToothManager.getInstance().cleanMessageQueue();
        BlueToothJniManager.bleWritableNotify(BleNativeStatusEnum.BLE_NETWORK_DOWN.mValue);
        LogUtil.e("蓝牙：BlueToothJniManager.bleWritableNotify=" + BleNativeStatusEnum.BLE_NETWORK_DOWN);
        ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
        LogUtil.e("蓝牙：BlueToothJniManager.config=" + new Gson().toJson(currentConnectConfig));
        if (currentConnectConfig != null && this.mBluetoothAdapter != null) {
            final String str = currentConnectConfig.macAddress;
            try {
                try {
                    LogUtil.d("蓝牙：--config--move--mac=-start" + str);
                    final BluetoothDevice remoteDevice = this.mBluetoothAdapter.getRemoteDevice(str);
                    LogUtil.d("蓝牙：--config--move--mac=" + str);
                    this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda12
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$requestRemoveBt$18(remoteDevice);
                        }
                    }, 500L);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.d("蓝牙：--config--move--mac=-end == error");
                }
                this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$requestRemoveBt$19(str);
                    }
                }, 5000L);
                this.isConnectStatus = false;
                this.isUnbind = false;
                HbBtClientManager.getInstance().handleBleStatusBleUnbind();
                disconnect();
            } catch (Exception unused) {
                LogUtil.d("蓝牙：---requestRemoveBt--RuntimeException");
            }
        }
        LogUtil.d("蓝牙：--handleBleStatusBleUnbind");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestRemoveBt$18(BluetoothDevice bluetoothDevice) {
        try {
            if (BT625Client.getInstance().btIsConnect() == 1) {
                new BleUtils().disConnectBt(this.mContext, bluetoothDevice);
            }
            removeBtDevice(bluetoothDevice);
            Boolean bool = (Boolean) BluetoothDevice.class.getMethod("removeBond", new Class[0]).invoke(bluetoothDevice, new Object[0]);
            if (bool == null || bool.booleanValue()) {
                return;
            }
            LogUtil.d("蓝牙：unbind failed.");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("蓝牙：---requestRemoveBt--Exception");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestRemoveBt$21() {
        HbBtClientManager.getInstance().handleBleStatusBleUnbind();
        this.isConnectStatus = false;
    }

    private void removeBtDevice(final BluetoothDevice bluetoothDevice) {
        this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda31
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$removeBtDevice$22(bluetoothDevice);
            }
        }, 500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeBtDevice$22(BluetoothDevice bluetoothDevice) {
        try {
            if (BT625Client.getInstance().btIsConnect() == 1) {
                removeBtDevice(bluetoothDevice);
            } else {
                Boolean bool = (Boolean) BluetoothDevice.class.getMethod("removeBond", new Class[0]).invoke(bluetoothDevice, new Object[0]);
                if (bool != null && !bool.booleanValue()) {
                    LogUtil.d("蓝牙：unbind failed.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("蓝牙：---requestRemoveBt--Exception");
        }
    }

    public boolean requestConnectBt(String str) {
        try {
            LogUtil.d("蓝牙：requestConnectBt");
            if (this.mContext == null) {
                LogUtil.d("蓝牙：requestConnectBt--mContext--null");
                return false;
            }
            notifyBleStatusChange(BleStatusEnum.HB_BLE_PAIR_ING);
            return ((Boolean) BluetoothDevice.class.getMethod("createBond", new Class[0]).invoke(((BluetoothManager) this.mContext.getSystemService("bluetooth")).getAdapter().getRemoteDevice(str), new Object[0])).booleanValue();
        } catch (Exception e) {
            LogUtil.d("蓝牙：requestConnectBt---Exception-" + e.toString());
            e.printStackTrace();
            notifyBleStatusChange(BleStatusEnum.HB_BLE_DISCONNECTED);
            return false;
        }
    }

    public boolean bindDevice() {
        LogUtil.d("蓝牙：--bindDevice");
        this.mConnectTask.mHandler.post(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$bindDevice$24();
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindDevice$24() {
        int iRandom;
        BleStatusEnum currentStatus = HbBtClientManager.getInstance().getCurrentStatus();
        LogUtil.d("蓝牙：--bindDevice" + currentStatus);
        if (BleStatusEnum.hasBleConnectError(currentStatus)) {
            LogUtil.d("蓝牙：connect error happened. shouldn't call bindDevice at this time");
            startConnect();
            return;
        }
        if (!BleStatusEnum.isBleConnected(currentStatus)) {
            LogUtil.d("蓝牙：invalid status:%d. shouldn't call bindDevice at this time.");
            startConnect();
            return;
        }
        String phoneType = DataUtils.getPhoneType(this.mContext);
        ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
        if (currentConnectConfig == null || !StringUtils.isNotEmpty(currentConnectConfig.deviceName) || !currentConnectConfig.deviceName.startsWith("W200") || AppDataConfig.getInstance().isBindWatch()) {
            iRandom = 888;
        } else {
            iRandom = (int) ((Math.random() * 9000.0d) + 1000.0d);
            IBindAdapter iBindAdapter = this.iBindAdapter;
            if (iBindAdapter != null) {
                iBindAdapter.onBindStart(iRandom);
            }
        }
        this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$bindDevice$23();
            }
        }, 15000L);
        MessageManager.getInstance().sendPhoneBindRequest(iRandom, phoneType, this);
        notifyBleStatusChange(BleStatusEnum.HB_BLE_BINDING);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindDevice$23() {
        if (HbBtClientManager.getInstance().getCurrentStatus() == BleStatusEnum.HB_BLE_BINDING) {
            onBleDisconnected();
            this.isConnectStatus = false;
        }
    }

    public void bindDeviceBindRequestByPhone() {
        String phoneType = DataUtils.getPhoneType(this.mContext);
        int iRandom = (int) ((Math.random() * 9000.0d) + 1000.0d);
        IBindAdapter iBindAdapter = this.iBindAdapter;
        if (iBindAdapter != null) {
            iBindAdapter.onBindStart(iRandom);
        }
        MessageManager.getInstance().sendPhoneBindRequest(iRandom, phoneType, this);
        notifyBleStatusChange(BleStatusEnum.HB_BLE_BINDING);
    }

    public void unBindWatch() {
        this.isUnbind = true;
        BlueToothManager.getInstance().cleanMessageQueue();
        ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
        ConnectConfig connectConfig = new ConnectConfig();
        connectConfig.macAddress = currentConnectConfig.macAddress;
        AppDataConfig.getInstance().deleteCurrentConfig(connectConfig);
        this.mConnectTask.mHandler.post(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$unBindWatch$25();
            }
        });
        try {
            this.mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.BleService$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$unBindWatch$26();
                }
            }, 1500L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mConnectTask.mHandler.postDelayed(new BleService$$ExternalSyntheticLambda1(this), 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindWatch$25() {
        String phoneType = DataUtils.getPhoneType(this.mContext);
        this.isScanAuto = false;
        notifyBleStatusChange(BleStatusEnum.HB_BLE_UNBINDING);
        MessageManager.getInstance().sendPhoneBindRequest(777, phoneType, new IBtBindRequestCallback() { // from class: cn.baos.watch.sdk.bluetooth.BleService.4
            @Override // cn.baos.watch.sdk.bluetooth.callback.IBtBindRequestCallback
            public void onBindSuccess() {
            }

            @Override // cn.baos.watch.sdk.bluetooth.callback.IBtBindRequestCallback
            public void onUnbindSuccess() {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unBindWatch$26() {
        lambda$stopScan$8();
        this.monitor.monitorTimeOut(new BleConnectProgressMonitor.StateChangeTimeoutConfig(BleStatusEnum.HB_BLE_BOND, BleStatusEnum.HB_BLE_UNBIND_SUCCESS, 0, 500));
    }

    protected void onDeviceFound() {
        notifyBleStatusChange(BleStatusEnum.HB_BLE_DEVICE_FOUND);
        this.monitor.monitorTimeOut(new BleConnectProgressMonitor.StateChangeTimeoutConfig(BleStatusEnum.HB_BLE_DEVICE_FOUND, BleStatusEnum.HB_BLE_CONNECT_SUCCESS, 1, 6000));
    }

    public void onScanResultData(int i, ScanResult scanResult) {
        LogUtil.d("蓝牙：蓝牙Rssi信号强度足够" + scanResult.getRssi() + " 设备名称:" + scanResult.getDevice().getName() + " 设备地址:" + scanResult.getDevice().getAddress() + " 设备绑定状态:" + scanResult.getDevice().getBondState());
        if (StringUtils.isNotEmpty(scanResult.getDevice().getName()) && new BleUtils().isHuabaoDevice(scanResult)) {
            getInstance().getNotificationHandler().onBLEScanning(scanResult);
        }
        this.isScanResultData = true;
        ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
        BleStatusEnum currentStatus = HbBtClientManager.getInstance().getCurrentStatus();
        if (currentStatus == BleStatusEnum.HB_BLE_UNBINDING || currentStatus == BleStatusEnum.HB_BLE_UNBIND_SUCCESS) {
            return;
        }
        if (currentConnectConfig != null && StringUtils.isNotEmpty(currentConnectConfig.macAddress) && currentConnectConfig.macAddress.equals(scanResult.getDevice().getAddress())) {
            LogUtil.d("蓝牙：connectConfig-sure=" + currentConnectConfig.macAddress);
        }
        if (currentConnectConfig == null || !StringUtils.isNotEmpty(currentConnectConfig.macAddress) || !currentConnectConfig.macAddress.equals(scanResult.getDevice().getAddress()) || TextUtils.isEmpty(scanResult.getDevice().getName())) {
            return;
        }
        ConnectConfig connectConfig = new ConnectConfig();
        connectConfig.macAddress = scanResult.getDevice().getAddress();
        connectConfig.deviceName = scanResult.getDevice().getName();
        connectConfig.deviceType = ConnectConfig.DeviceType.DeviceTypeBle;
        connectConfig.maxBleMtuSize = BTConstant.MAX_BLE_MTU_SIZE;
        isModelVerify(connectConfig, scanResult);
        connectConfig.SERVICE_UUID = BTConstant.SERVICE_UUID;
        connectConfig.UUID_CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR = BTConstant.UUID_CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR;
        connectConfig.CHAR_WRITE_WITHOUT_RESPONSE_NOTIFY_UUID = BTConstant.CHAR_WRITE_WITHOUT_RESPONSE_NOTIFY_UUID;
        connectConfig.CHAR_NOTIFICATION_UUID = BTConstant.CHAR_NOTIFICATION_UUID;
        if (connectConfig.deviceName.startsWith("W200")) {
            connectConfig.deviceName = "W200" + DataUtils.changeMacAddressToFourNumber(scanResult.getDevice().getAddress());
        }
        this.isScanAuto = true;
        HbBtClientManager.getInstance().onBleDeviceDiscoverdAndAction(connectConfig);
        this.isScanBleStatus = true;
        BluetoothLeScanner bluetoothLeScanner = this.mBluetoothAdapter.getBluetoothLeScanner();
        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.stopScan(this.mScanCallBack);
            LogUtil.d("蓝牙：蓝牙停止扫描----等待BT扫描");
        } else {
            LogUtil.d("蓝牙：-scanner=null-RuntimeException");
        }
        scanFinish();
    }

    public void isModelVerify(ConnectConfig connectConfig, ScanResult scanResult) {
        if (scanResult.getScanRecord() != null) {
            byte[] bytes = scanResult.getScanRecord().getBytes();
            connectConfig.isJLW6 = false;
            if (bytes == null || bytes.length <= 15) {
                return;
            }
            try {
                String strBytesToHex = DeviceIdUtil.bytesToHex(bytes);
                int iIndexOf = strBytesToHex.indexOf(connectConfig.macAddress.replace(":", ""));
                int i = iIndexOf - 2;
                String strSubstring = strBytesToHex.substring(iIndexOf - 4, i);
                String strSubstring2 = strBytesToHex.substring(i, iIndexOf);
                LogUtil.e("蓝牙：isModelVerify-huabaoCompany1=" + strSubstring + " huabaoCompany2=" + strSubstring2);
                if (strSubstring.equals(BTConstant.COMPANY_ID) && strSubstring2.equals(BTConstant.COMPANY_ID_TWO)) {
                    connectConfig.isJLW6 = true;
                }
                LogUtil.e("蓝牙：isModelVerify-isJLW6=" + connectConfig.isJLW6);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void scanFinish() {
        if (this.isScanBtStatus == null || this.isScanBleStatus == null) {
            return;
        }
        LogUtil.e("蓝牙：-=-scanFinish-isScanBtStatus=" + this.isScanBtStatus + " isScanBleStatus=" + this.isScanBleStatus);
        ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
        if (HbBtClientManager.getInstance().isScan() && currentConnectConfig != null && !currentConnectConfig.isActive) {
            LogUtil.d("蓝牙：---startConnect-scan-> 发起配对");
            this.iDeviceCallback.onBlePair();
        } else {
            LogUtil.d("蓝牙：---startConnect-scan-> normal - > pair");
            onDeviceFound();
        }
    }

    public void onBatchScanResultsData(List<ScanResult> list) {
        LogUtil.d("蓝牙：onBatchScanResults:" + list);
    }

    public void onScanFailedData(int i) {
        LogUtil.d("蓝牙：onScanFailed:" + i);
        if (i == 1) {
            LogUtil.d("蓝牙：errorCode=1;Fails to start scan as BLE scan with the same settings is already started by the app.");
        } else if (i == 2) {
            LogUtil.d("蓝牙：errorCode=2;Fails to start scan as app cannot be registered.");
            BleScanUtils.releaseAllScanClient();
        } else if (i == 3) {
            LogUtil.d("蓝牙：errorCode=3;Fails to start scan due an internal error");
        } else if (i == 4) {
            LogUtil.d("蓝牙：errorCode=4;Fails to start power optimized scan as this feature is not supported");
        }
        BluetoothLeScanner bluetoothLeScanner = this.mBluetoothAdapter.getBluetoothLeScanner();
        if (bluetoothLeScanner != null) {
            bluetoothLeScanner.stopScan(this.mScanCallBack);
        }
        LogUtil.d("蓝牙：---onScanFailed-");
    }

    public ArrayList<BluetoothDevice> getBondedDevices() {
        return new ArrayList<>(this.mBluetoothAdapter.getBondedDevices());
    }

    /* JADX INFO: renamed from: VerifyBtDel, reason: merged with bridge method [inline-methods] */
    public void lambda$requestRemoveBt$19(String str) {
        this.isUnbind = false;
        LogUtil.d("蓝牙：校验BT-mac：" + str);
        ArrayList<BluetoothDevice> bondedDevices = getBondedDevices();
        boolean z = true;
        if (bondedDevices != null && bondedDevices.size() > 0) {
            for (BluetoothDevice bluetoothDevice : bondedDevices) {
                if (bluetoothDevice != null && !TextUtils.isEmpty(bluetoothDevice.getAddress()) && !TextUtils.isEmpty(str) && str.equals(bluetoothDevice.getAddress())) {
                    z = false;
                }
            }
        }
        notifyBleStatusChange(null);
        this.mConnectTask.cleaSessionAsyncDelayJob();
        LogUtil.d("蓝牙：校验BT是否删除；" + z);
        if (z) {
            return;
        }
        getNotificationHandler().onBtNoDelDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startScanError() {
        LogUtil.d("蓝牙：startScanError:" + this.isScanResultData);
        LogUtil.d("蓝牙：startScanError->config=" + new Gson().toJson(HbBtClientManager.getInstance().getCurrentConnectConfig()));
        if (this.isScanResultData) {
            return;
        }
        LogUtil.d("蓝牙：startScanError-start-connect");
        stopScan();
        lambda$directConnect$13();
    }
}
