package com.lib.flutter_blue_plus;

import android.app.Application;
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
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import cn.yoozworld.watch.utils.BtConstant;
import com.fluttercandies.photo_manager.constant.Methods;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import kotlin.UByte;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: loaded from: classes2.dex */
public class FlutterBluePlusPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler, PluginRegistry.RequestPermissionsResultListener, PluginRegistry.ActivityResultListener, ActivityAware {
    private static final String CCCD = "2902";
    private static final String NAMESPACE = "flutter_blue_plus";
    private static final String TAG = "[FBP-Android]";
    private ActivityPluginBinding activityBinding;
    private Context context;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothManager mBluetoothManager;
    private MethodChannel methodChannel;
    private FlutterPlugin.FlutterPluginBinding pluginBinding;
    private ScanCallback scanCallback;
    private LogLevel logLevel = LogLevel.DEBUG;
    private boolean mIsScanning = false;
    private final Semaphore mMethodCallMutex = new Semaphore(1);
    private final Map<String, BluetoothGatt> mConnectedDevices = new ConcurrentHashMap();
    private final Map<String, BluetoothGatt> mCurrentlyConnectingDevices = new ConcurrentHashMap();
    private final Map<String, BluetoothDevice> mBondingDevices = new ConcurrentHashMap();
    private final Map<String, Integer> mMtu = new ConcurrentHashMap();
    private final Map<String, BluetoothGatt> mAutoConnected = new ConcurrentHashMap();
    private final Map<String, String> mWriteChr = new ConcurrentHashMap();
    private final Map<String, String> mWriteDesc = new ConcurrentHashMap();
    private final Map<String, String> mAdvSeen = new ConcurrentHashMap();
    private final Map<String, Integer> mScanCounts = new ConcurrentHashMap();
    private HashMap<String, Object> mScanFilters = new HashMap<>();
    private final Map<Integer, OperationOnPermission> operationsOnPermission = new HashMap();
    private int lastEventId = 1452;
    private final int enableBluetoothRequestCode = 1879842617;
    private final BroadcastReceiver mBluetoothAdapterStateReceiver = new BroadcastReceiver() { // from class: com.lib.flutter_blue_plus.FlutterBluePlusPlugin.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            BluetoothLeScanner bluetoothLeScanner;
            String action = intent.getAction();
            if (action == null || !"android.bluetooth.adapter.action.STATE_CHANGED".equals(action)) {
                return;
            }
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
            FlutterBluePlusPlugin.this.log(LogLevel.DEBUG, "OnAdapterStateChanged: " + FlutterBluePlusPlugin.adapterStateString(intExtra));
            if (intExtra != 12 && FlutterBluePlusPlugin.this.mBluetoothAdapter != null && FlutterBluePlusPlugin.this.mIsScanning && (bluetoothLeScanner = FlutterBluePlusPlugin.this.mBluetoothAdapter.getBluetoothLeScanner()) != null) {
                bluetoothLeScanner.stopScan(FlutterBluePlusPlugin.this.getScanCallback());
                FlutterBluePlusPlugin.this.mIsScanning = false;
            }
            HashMap map = new HashMap();
            map.put("adapter_state", Integer.valueOf(FlutterBluePlusPlugin.bmAdapterStateEnum(intExtra)));
            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnAdapterStateChanged", map);
            if (intExtra == 13 || intExtra == 10) {
                FlutterBluePlusPlugin.this.disconnectAllDevices("adapterTurnOff");
            }
        }
    };
    private final BroadcastReceiver mBluetoothBondStateReceiver = new BroadcastReceiver() { // from class: com.lib.flutter_blue_plus.FlutterBluePlusPlugin.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            BluetoothDevice bluetoothDevice;
            String action = intent.getAction();
            if (action == null || !action.equals("android.bluetooth.device.action.BOND_STATE_CHANGED")) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 33) {
                bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE", BluetoothDevice.class);
            } else {
                bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            }
            int intExtra = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", Integer.MIN_VALUE);
            int intExtra2 = intent.getIntExtra("android.bluetooth.device.extra.PREVIOUS_BOND_STATE", -1);
            FlutterBluePlusPlugin.this.log(LogLevel.DEBUG, "OnBondStateChanged: " + FlutterBluePlusPlugin.bondStateString(intExtra) + " prev: " + FlutterBluePlusPlugin.bondStateString(intExtra2));
            String address = bluetoothDevice.getAddress();
            if (intExtra == 11) {
                FlutterBluePlusPlugin.this.mBondingDevices.put(address, bluetoothDevice);
            } else {
                FlutterBluePlusPlugin.this.mBondingDevices.remove(address);
            }
            HashMap map = new HashMap();
            map.put("remote_id", address);
            map.put("bond_state", Integer.valueOf(FlutterBluePlusPlugin.bmBondStateEnum(intExtra)));
            map.put("prev_state", Integer.valueOf(FlutterBluePlusPlugin.bmBondStateEnum(intExtra2)));
            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnBondStateChanged", map);
        }
    };
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() { // from class: com.lib.flutter_blue_plus.FlutterBluePlusPlugin.4
        @Override // android.bluetooth.BluetoothGattCallback
        public void onConnectionStateChange(BluetoothGatt bluetoothGatt, int i, int i2) {
            try {
                FlutterBluePlusPlugin flutterBluePlusPlugin = FlutterBluePlusPlugin.this;
                flutterBluePlusPlugin.acquireMutex(flutterBluePlusPlugin.mMethodCallMutex);
                FlutterBluePlusPlugin.this.log(LogLevel.DEBUG, "onConnectionStateChange:" + FlutterBluePlusPlugin.connectionStateString(i2));
                FlutterBluePlusPlugin.this.log(LogLevel.DEBUG, "  status: " + FlutterBluePlusPlugin.hciStatusString(i));
                if (i2 == 2 || i2 == 0) {
                    String address = bluetoothGatt.getDevice().getAddress();
                    if (!handleUnexpectedConnectionEvents(bluetoothGatt, i2, address)) {
                        if (i2 == 2) {
                            FlutterBluePlusPlugin.this.mConnectedDevices.put(address, bluetoothGatt);
                            FlutterBluePlusPlugin.this.mCurrentlyConnectingDevices.remove(address);
                            FlutterBluePlusPlugin.this.mMtu.put(address, 23);
                        }
                        if (i2 == 0) {
                            FlutterBluePlusPlugin.this.mConnectedDevices.remove(address);
                            FlutterBluePlusPlugin.this.mCurrentlyConnectingDevices.remove(address);
                            FlutterBluePlusPlugin.this.mBondingDevices.remove(address);
                            if (FlutterBluePlusPlugin.this.mAutoConnected.containsKey(address)) {
                                FlutterBluePlusPlugin.this.log(LogLevel.DEBUG, "autoconnect is true. skipping gatt.close()");
                            } else {
                                bluetoothGatt.close();
                            }
                        }
                        HashMap map = new HashMap();
                        map.put("remote_id", address);
                        map.put("connection_state", Integer.valueOf(FlutterBluePlusPlugin.bmConnectionStateEnum(i2)));
                        map.put("disconnect_reason_code", Integer.valueOf(i));
                        map.put("disconnect_reason_string", FlutterBluePlusPlugin.hciStatusString(i));
                        FlutterBluePlusPlugin.this.invokeMethodUIThread("OnConnectionStateChanged", map);
                    }
                }
            } finally {
                FlutterBluePlusPlugin.this.mMethodCallMutex.release();
            }
        }

        private boolean handleUnexpectedConnectionEvents(BluetoothGatt bluetoothGatt, int i, String str) {
            if (i == 2) {
                if (FlutterBluePlusPlugin.this.mCurrentlyConnectingDevices.get(str) == null && FlutterBluePlusPlugin.this.mAutoConnected.get(str) == null) {
                    FlutterBluePlusPlugin.this.log(LogLevel.DEBUG, "keeping device disconnected, disconnecting now");
                    FlutterBluePlusPlugin.this.mConnectedDevices.remove(str);
                    FlutterBluePlusPlugin.this.mBondingDevices.remove(str);
                    bluetoothGatt.disconnect();
                    bluetoothGatt.close();
                    return true;
                }
            } else if (i == 0 && FlutterBluePlusPlugin.this.mCurrentlyConnectingDevices.get(str) == null && FlutterBluePlusPlugin.this.mConnectedDevices.get(str) == null && FlutterBluePlusPlugin.this.mAutoConnected.get(str) == null) {
                FlutterBluePlusPlugin.this.mBondingDevices.remove(str);
                bluetoothGatt.close();
                return true;
            }
            return false;
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onServicesDiscovered(BluetoothGatt bluetoothGatt, int i) {
            LogLevel logLevel = i == 0 ? LogLevel.DEBUG : LogLevel.ERROR;
            FlutterBluePlusPlugin.this.log(logLevel, "onServicesDiscovered:");
            FlutterBluePlusPlugin.this.log(logLevel, "  count: " + bluetoothGatt.getServices().size());
            FlutterBluePlusPlugin.this.log(logLevel, "  status: " + i + FlutterBluePlusPlugin.gattErrorString(i));
            ArrayList arrayList = new ArrayList();
            Iterator<BluetoothGattService> it = bluetoothGatt.getServices().iterator();
            while (it.hasNext()) {
                arrayList.add(FlutterBluePlusPlugin.this.bmBluetoothService(bluetoothGatt.getDevice(), it.next(), bluetoothGatt));
            }
            HashMap map = new HashMap();
            map.put("remote_id", bluetoothGatt.getDevice().getAddress());
            map.put("services", arrayList);
            map.put("success", Integer.valueOf(i == 0 ? 1 : 0));
            map.put("error_code", Integer.valueOf(i));
            map.put("error_string", FlutterBluePlusPlugin.gattErrorString(i));
            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnDiscoveredServices", map);
        }

        public void onCharacteristicReceived(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr, int i) {
            ServicePair servicePair = FlutterBluePlusPlugin.getServicePair(bluetoothGatt, bluetoothGattCharacteristic);
            if (FlutterBluePlusPlugin.this.uuidStr(servicePair.primary) == "1800" && FlutterBluePlusPlugin.this.uuidStr(bluetoothGattCharacteristic.getUuid()) == "2A05") {
                FlutterBluePlusPlugin.this.invokeMethodUIThread("OnServicesReset", FlutterBluePlusPlugin.this.bmBluetoothDevice(bluetoothGatt.getDevice()));
            }
            HashMap map = new HashMap();
            map.put("remote_id", bluetoothGatt.getDevice().getAddress());
            map.put("service_uuid", FlutterBluePlusPlugin.this.uuidStr(servicePair.primary));
            if (servicePair.secondary != null) {
                map.put("secondary_service_uuid", FlutterBluePlusPlugin.this.uuidStr(servicePair.secondary));
            }
            map.put("characteristic_uuid", FlutterBluePlusPlugin.this.uuidStr(bluetoothGattCharacteristic.getUuid()));
            map.put("value", FlutterBluePlusPlugin.bytesToHex(bArr));
            map.put("success", Integer.valueOf(i == 0 ? 1 : 0));
            map.put("error_code", Integer.valueOf(i));
            map.put("error_string", FlutterBluePlusPlugin.gattErrorString(i));
            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnCharacteristicReceived", map);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr) {
            LogLevel logLevel = LogLevel.DEBUG;
            FlutterBluePlusPlugin.this.log(logLevel, "onCharacteristicChanged:");
            FlutterBluePlusPlugin.this.log(logLevel, "  chr: " + FlutterBluePlusPlugin.this.uuidStr(bluetoothGattCharacteristic.getUuid()));
            onCharacteristicReceived(bluetoothGatt, bluetoothGattCharacteristic, bArr, 0);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, byte[] bArr, int i) {
            LogLevel logLevel = i == 0 ? LogLevel.DEBUG : LogLevel.ERROR;
            FlutterBluePlusPlugin.this.log(logLevel, "onCharacteristicRead:");
            FlutterBluePlusPlugin.this.log(logLevel, "  chr: " + FlutterBluePlusPlugin.this.uuidStr(bluetoothGattCharacteristic.getUuid()));
            FlutterBluePlusPlugin.this.log(logLevel, "  status: " + FlutterBluePlusPlugin.gattErrorString(i) + " (" + i + ")");
            onCharacteristicReceived(bluetoothGatt, bluetoothGattCharacteristic, bArr, 0);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicWrite(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            LogLevel logLevel = i == 0 ? LogLevel.DEBUG : LogLevel.ERROR;
            FlutterBluePlusPlugin.this.log(logLevel, "onCharacteristicWrite:");
            FlutterBluePlusPlugin.this.log(logLevel, "  chr: " + FlutterBluePlusPlugin.this.uuidStr(bluetoothGattCharacteristic.getUuid()));
            FlutterBluePlusPlugin.this.log(logLevel, "  status: " + FlutterBluePlusPlugin.gattErrorString(i) + " (" + i + ")");
            ServicePair servicePair = FlutterBluePlusPlugin.getServicePair(bluetoothGatt, bluetoothGattCharacteristic);
            String address = bluetoothGatt.getDevice().getAddress();
            String strUuidStr = FlutterBluePlusPlugin.this.uuidStr(servicePair.primary);
            String strUuidStr2 = servicePair.secondary != null ? FlutterBluePlusPlugin.this.uuidStr(servicePair.secondary) : null;
            String strUuidStr3 = FlutterBluePlusPlugin.this.uuidStr(bluetoothGattCharacteristic.getUuid());
            String str = address + ":" + strUuidStr + ":" + strUuidStr3;
            String str2 = FlutterBluePlusPlugin.this.mWriteChr.get(str) != null ? (String) FlutterBluePlusPlugin.this.mWriteChr.get(str) : "";
            FlutterBluePlusPlugin.this.mWriteChr.remove(str);
            HashMap map = new HashMap();
            map.put("remote_id", address);
            map.put("service_uuid", strUuidStr);
            if (strUuidStr2 != null) {
                map.put("secondary_service_uuid", strUuidStr2);
            }
            map.put("characteristic_uuid", strUuidStr3);
            map.put("value", str2);
            map.put("success", Integer.valueOf(i == 0 ? 1 : 0));
            map.put("error_code", Integer.valueOf(i));
            map.put("error_string", FlutterBluePlusPlugin.gattErrorString(i));
            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnCharacteristicWritten", map);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i, byte[] bArr) {
            LogLevel logLevel = i == 0 ? LogLevel.DEBUG : LogLevel.ERROR;
            FlutterBluePlusPlugin.this.log(logLevel, "onDescriptorRead:");
            FlutterBluePlusPlugin.this.log(logLevel, "  chr: " + FlutterBluePlusPlugin.this.uuidStr(bluetoothGattDescriptor.getCharacteristic().getUuid()));
            FlutterBluePlusPlugin.this.log(logLevel, "  desc: " + FlutterBluePlusPlugin.this.uuidStr(bluetoothGattDescriptor.getUuid()));
            FlutterBluePlusPlugin.this.log(logLevel, "  status: " + FlutterBluePlusPlugin.gattErrorString(i) + " (" + i + ")");
            ServicePair servicePair = FlutterBluePlusPlugin.getServicePair(bluetoothGatt, bluetoothGattDescriptor.getCharacteristic());
            HashMap map = new HashMap();
            map.put("remote_id", bluetoothGatt.getDevice().getAddress());
            map.put("service_uuid", FlutterBluePlusPlugin.this.uuidStr(servicePair.primary));
            if (servicePair.secondary != null) {
                map.put("secondary_service_uuid", FlutterBluePlusPlugin.this.uuidStr(servicePair.secondary));
            }
            map.put("characteristic_uuid", FlutterBluePlusPlugin.this.uuidStr(bluetoothGattDescriptor.getCharacteristic().getUuid()));
            map.put("descriptor_uuid", FlutterBluePlusPlugin.this.uuidStr(bluetoothGattDescriptor.getUuid()));
            map.put("value", FlutterBluePlusPlugin.bytesToHex(bArr));
            map.put("success", Integer.valueOf(i == 0 ? 1 : 0));
            map.put("error_code", Integer.valueOf(i));
            map.put("error_string", FlutterBluePlusPlugin.gattErrorString(i));
            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnDescriptorRead", map);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onDescriptorWrite(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            LogLevel logLevel = i == 0 ? LogLevel.DEBUG : LogLevel.ERROR;
            FlutterBluePlusPlugin.this.log(logLevel, "onDescriptorWrite:");
            FlutterBluePlusPlugin.this.log(logLevel, "  chr: " + FlutterBluePlusPlugin.this.uuidStr(bluetoothGattDescriptor.getCharacteristic().getUuid()));
            FlutterBluePlusPlugin.this.log(logLevel, "  desc: " + FlutterBluePlusPlugin.this.uuidStr(bluetoothGattDescriptor.getUuid()));
            FlutterBluePlusPlugin.this.log(logLevel, "  status: " + FlutterBluePlusPlugin.gattErrorString(i) + " (" + i + ")");
            ServicePair servicePair = FlutterBluePlusPlugin.getServicePair(bluetoothGatt, bluetoothGattDescriptor.getCharacteristic());
            String address = bluetoothGatt.getDevice().getAddress();
            String strUuidStr = FlutterBluePlusPlugin.this.uuidStr(servicePair.primary);
            String strUuidStr2 = servicePair.secondary != null ? FlutterBluePlusPlugin.this.uuidStr(servicePair.secondary) : null;
            String strUuidStr3 = FlutterBluePlusPlugin.this.uuidStr(bluetoothGattDescriptor.getCharacteristic().getUuid());
            String strUuidStr4 = FlutterBluePlusPlugin.this.uuidStr(bluetoothGattDescriptor.getUuid());
            String str = address + ":" + strUuidStr + ":" + strUuidStr3 + ":" + strUuidStr4;
            String str2 = FlutterBluePlusPlugin.this.mWriteDesc.get(str) != null ? (String) FlutterBluePlusPlugin.this.mWriteDesc.get(str) : "";
            FlutterBluePlusPlugin.this.mWriteDesc.remove(str);
            HashMap map = new HashMap();
            map.put("remote_id", address);
            map.put("service_uuid", strUuidStr);
            if (strUuidStr2 != null) {
                map.put("secondary_service_uuid", strUuidStr2);
            }
            map.put("characteristic_uuid", strUuidStr3);
            map.put("descriptor_uuid", strUuidStr4);
            map.put("value", str2);
            map.put("success", Integer.valueOf(i == 0 ? 1 : 0));
            map.put("error_code", Integer.valueOf(i));
            map.put("error_string", FlutterBluePlusPlugin.gattErrorString(i));
            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnDescriptorWritten", map);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onReliableWriteCompleted(BluetoothGatt bluetoothGatt, int i) {
            LogLevel logLevel = i == 0 ? LogLevel.DEBUG : LogLevel.ERROR;
            FlutterBluePlusPlugin.this.log(logLevel, "onReliableWriteCompleted:");
            FlutterBluePlusPlugin.this.log(logLevel, "  status: " + FlutterBluePlusPlugin.gattErrorString(i) + " (" + i + ")");
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onReadRemoteRssi(BluetoothGatt bluetoothGatt, int i, int i2) {
            LogLevel logLevel = i2 == 0 ? LogLevel.DEBUG : LogLevel.ERROR;
            FlutterBluePlusPlugin.this.log(logLevel, "onReadRemoteRssi:");
            FlutterBluePlusPlugin.this.log(logLevel, "  rssi: " + i);
            FlutterBluePlusPlugin.this.log(logLevel, "  status: " + FlutterBluePlusPlugin.gattErrorString(i2) + " (" + i2 + ")");
            HashMap map = new HashMap();
            map.put("remote_id", bluetoothGatt.getDevice().getAddress());
            map.put("rssi", Integer.valueOf(i));
            map.put("success", Integer.valueOf(i2 == 0 ? 1 : 0));
            map.put("error_code", Integer.valueOf(i2));
            map.put("error_string", FlutterBluePlusPlugin.gattErrorString(i2));
            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnReadRssi", map);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onMtuChanged(BluetoothGatt bluetoothGatt, int i, int i2) {
            LogLevel logLevel = i2 == 0 ? LogLevel.DEBUG : LogLevel.ERROR;
            FlutterBluePlusPlugin.this.log(logLevel, "onMtuChanged:");
            FlutterBluePlusPlugin.this.log(logLevel, "  mtu: " + i);
            FlutterBluePlusPlugin.this.log(logLevel, "  status: " + FlutterBluePlusPlugin.gattErrorString(i2) + " (" + i2 + ")");
            String address = bluetoothGatt.getDevice().getAddress();
            FlutterBluePlusPlugin.this.mMtu.put(address, Integer.valueOf(i));
            HashMap map = new HashMap();
            map.put("remote_id", address);
            map.put("mtu", Integer.valueOf(i));
            map.put("success", Integer.valueOf(i2 == 0 ? 1 : 0));
            map.put("error_code", Integer.valueOf(i2));
            map.put("error_string", FlutterBluePlusPlugin.gattErrorString(i2));
            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnMtuChanged", map);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicChanged(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
            onCharacteristicChanged(bluetoothGatt, bluetoothGattCharacteristic, bluetoothGattCharacteristic.getValue());
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onCharacteristicRead(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic, int i) {
            onCharacteristicRead(bluetoothGatt, bluetoothGattCharacteristic, bluetoothGattCharacteristic.getValue(), i);
        }

        @Override // android.bluetooth.BluetoothGattCallback
        public void onDescriptorRead(BluetoothGatt bluetoothGatt, BluetoothGattDescriptor bluetoothGattDescriptor, int i) {
            onDescriptorRead(bluetoothGatt, bluetoothGattDescriptor, i, bluetoothGattDescriptor.getValue());
        }
    };

    enum LogLevel {
        NONE,
        ERROR,
        WARNING,
        INFO,
        DEBUG,
        VERBOSE
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface OperationOnPermission {
        void op(boolean z, String str);
    }

    public static class ServicePair {
        public UUID primary;
        public UUID secondary;
    }

    static int bmAdapterStateEnum(int i) {
        switch (i) {
            case 10:
                return 6;
            case 11:
                return 3;
            case 12:
                return 4;
            case 13:
                return 5;
            default:
                return 0;
        }
    }

    static int bmBondStateEnum(int i) {
        if (i != 11) {
            return i != 12 ? 0 : 2;
        }
        return 1;
    }

    static int bmConnectionPriorityParse(int i) {
        if (i != 0) {
            return i != 1 ? 2 : 1;
        }
        return 0;
    }

    static int bmConnectionStateEnum(int i) {
        return i != 2 ? 0 : 1;
    }

    public String uuid128(Object obj) {
        if (!(obj instanceof UUID) && !(obj instanceof String)) {
            throw new IllegalArgumentException("input must be UUID or String");
        }
        String string = obj.toString();
        if (string.length() == 4) {
            return String.format("0000%s-0000-1000-8000-00805f9b34fb", string).toLowerCase();
        }
        if (string.length() == 8) {
            return String.format("%s-0000-1000-8000-00805f9b34fb", string).toLowerCase();
        }
        return string.toLowerCase();
    }

    public String uuidStr(Object obj) {
        String strUuid128 = uuid128(obj);
        boolean zStartsWith = strUuid128.startsWith("0000");
        boolean zEndsWith = strUuid128.endsWith("-0000-1000-8000-00805f9b34fb");
        if (zStartsWith && zEndsWith) {
            return strUuid128.substring(4, 8);
        }
        return zEndsWith ? strUuid128.substring(0, 8) : strUuid128;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acquireMutex(Semaphore semaphore) {
        boolean z = false;
        while (!z) {
            try {
                semaphore.acquire();
                z = true;
            } catch (InterruptedException unused) {
                log(LogLevel.ERROR, "failed to acquire mutex, retrying");
            }
        }
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        log(LogLevel.DEBUG, "onAttachedToEngine");
        this.pluginBinding = flutterPluginBinding;
        this.context = (Application) flutterPluginBinding.getApplicationContext();
        MethodChannel methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_blue_plus/methods");
        this.methodChannel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        this.context.registerReceiver(this.mBluetoothAdapterStateReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        this.context.registerReceiver(this.mBluetoothBondStateReceiver, new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED"));
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        BluetoothLeScanner bluetoothLeScanner;
        log(LogLevel.DEBUG, "onDetachedFromEngine");
        invokeMethodUIThread("OnDetachedFromEngine", new HashMap<>());
        this.pluginBinding = null;
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter != null && this.mIsScanning && (bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner()) != null) {
            bluetoothLeScanner.stopScan(getScanCallback());
            this.mIsScanning = false;
        }
        disconnectAllDevices("onDetachedFromEngine");
        this.context.unregisterReceiver(this.mBluetoothBondStateReceiver);
        this.context.unregisterReceiver(this.mBluetoothAdapterStateReceiver);
        this.context = null;
        this.methodChannel.setMethodCallHandler(null);
        this.methodChannel = null;
        this.mBluetoothAdapter = null;
        this.mBluetoothManager = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(ActivityPluginBinding activityPluginBinding) {
        log(LogLevel.DEBUG, "onAttachedToActivity");
        this.activityBinding = activityPluginBinding;
        activityPluginBinding.addRequestPermissionsResultListener(this);
        this.activityBinding.addActivityResultListener(this);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        log(LogLevel.DEBUG, "onDetachedFromActivityForConfigChanges");
        onDetachedFromActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding activityPluginBinding) {
        log(LogLevel.DEBUG, "onReattachedToActivityForConfigChanges");
        onAttachedToActivity(activityPluginBinding);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        log(LogLevel.DEBUG, "onDetachedFromActivity");
        this.activityBinding.removeRequestPermissionsResultListener(this);
        this.activityBinding = null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:127:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x0633  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x08ce A[Catch: Exception -> 0x0b66, all -> 0x0d74, TryCatch #1 {all -> 0x0d74, blocks: (B:3:0x0024, B:5:0x0043, B:7:0x0058, B:9:0x005e, B:13:0x0066, B:16:0x006c, B:18:0x0074, B:20:0x007e, B:22:0x0088, B:24:0x0092, B:26:0x009c, B:28:0x00a6, B:31:0x00b4, B:35:0x00e3, B:131:0x0248, B:448:0x0d7a, B:133:0x024d, B:137:0x0260, B:138:0x0265, B:140:0x0276, B:141:0x0280, B:142:0x028f, B:144:0x02a4, B:145:0x02b4, B:147:0x02cc, B:148:0x02d4, B:149:0x02de, B:151:0x02ef, B:152:0x02f7, B:154:0x0305, B:155:0x0315, B:157:0x031d, B:158:0x032e, B:160:0x0334, B:161:0x033e, B:162:0x0348, B:163:0x0378, B:164:0x0389, B:166:0x038f, B:167:0x039d, B:168:0x03ac, B:170:0x03e8, B:171:0x03f0, B:172:0x0400, B:173:0x042a, B:175:0x044e, B:176:0x0454, B:178:0x0461, B:179:0x0469, B:180:0x0473, B:182:0x0483, B:183:0x0489, B:185:0x0492, B:186:0x049a, B:187:0x04a4, B:189:0x04c8, B:190:0x04ce, B:192:0x04d7, B:193:0x04df, B:194:0x04e9, B:196:0x052c, B:197:0x0532, B:199:0x053d, B:200:0x0545, B:202:0x054d, B:203:0x0568, B:205:0x0574, B:207:0x059b, B:211:0x05a6, B:216:0x05b3, B:219:0x05bf, B:221:0x05c9, B:224:0x05cf, B:226:0x05d3, B:228:0x05d8, B:230:0x060a, B:232:0x0610, B:240:0x0651, B:234:0x0635, B:236:0x063b, B:237:0x0643, B:239:0x0649, B:227:0x05d6, B:241:0x065a, B:243:0x0694, B:244:0x069a, B:246:0x06a5, B:247:0x06ad, B:249:0x06b9, B:250:0x06de, B:252:0x06f3, B:253:0x0717, B:255:0x0747, B:257:0x0751, B:265:0x0796, B:259:0x0776, B:261:0x0780, B:262:0x0788, B:264:0x078e, B:266:0x079f, B:268:0x07d1, B:269:0x07d9, B:271:0x07e6, B:272:0x07ee, B:274:0x07fa, B:275:0x081f, B:277:0x0825, B:278:0x082d, B:279:0x0837, B:286:0x0882, B:288:0x088c, B:289:0x0894, B:291:0x08a1, B:292:0x08a9, B:294:0x08ae, B:296:0x08b6, B:300:0x08ce, B:310:0x08ec, B:311:0x091f, B:313:0x0947, B:315:0x0951, B:323:0x0999, B:317:0x0976, B:319:0x0980, B:320:0x0988, B:322:0x0991, B:297:0x08be, B:299:0x08c6, B:324:0x09a2, B:326:0x09cc, B:327:0x09d4, B:329:0x09e1, B:330:0x09e9, B:332:0x09f3, B:333:0x09fb, B:335:0x0a01, B:336:0x0a09, B:337:0x0a13, B:339:0x0a23, B:340:0x0a2b, B:342:0x0a34, B:343:0x0a3e, B:344:0x0a48, B:346:0x0a59, B:348:0x0a62, B:350:0x0a6c, B:352:0x0a76, B:354:0x0a93, B:355:0x0aa3, B:357:0x0ab3, B:358:0x0ae8, B:359:0x0af2, B:363:0x0b12, B:365:0x0b1d, B:366:0x0b22, B:367:0x0b2c, B:369:0x0b3b, B:370:0x0b3e, B:371:0x0b48, B:373:0x0b53, B:374:0x0b5c, B:379:0x0b6f, B:381:0x0be6, B:383:0x0bed, B:384:0x0bf2, B:385:0x0bf5, B:387:0x0bfb, B:388:0x0c00, B:389:0x0c14, B:391:0x0c21, B:392:0x0c24, B:394:0x0c2a, B:395:0x0c2f, B:397:0x0c33, B:398:0x0c3b, B:400:0x0c4a, B:401:0x0c4d, B:403:0x0c53, B:404:0x0c58, B:407:0x0c64, B:410:0x0c6c, B:411:0x0c83, B:413:0x0c92, B:414:0x0c95, B:416:0x0c9b, B:417:0x0ca0, B:418:0x0caa, B:422:0x0cb4, B:423:0x0cbd, B:424:0x0cd9, B:426:0x0cfd, B:427:0x0d04, B:428:0x0d12, B:429:0x0d1d, B:431:0x0d24, B:432:0x0d2c, B:434:0x0d32, B:436:0x0d36, B:437:0x0d3f, B:38:0x00ef, B:41:0x00fa, B:44:0x0106, B:47:0x0111, B:50:0x011d, B:53:0x0129, B:56:0x0134, B:59:0x013e, B:62:0x014a, B:65:0x0156, B:68:0x0162, B:71:0x016e, B:74:0x0178, B:77:0x0184, B:80:0x018e, B:83:0x0198, B:86:0x01a3, B:89:0x01ad, B:92:0x01b8, B:95:0x01c3, B:98:0x01cc, B:101:0x01d5, B:104:0x01e0, B:107:0x01e9, B:110:0x01f2, B:113:0x01fa, B:116:0x0205, B:119:0x020f, B:122:0x021a), top: B:453:0x0024 }] */
    /* JADX WARN: Type inference failed for: r2v128 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v19 */
    /* JADX WARN: Type inference failed for: r2v21 */
    /* JADX WARN: Type inference failed for: r2v23 */
    /* JADX WARN: Type inference failed for: r2v25 */
    /* JADX WARN: Type inference failed for: r2v27 */
    /* JADX WARN: Type inference failed for: r2v29 */
    /* JADX WARN: Type inference failed for: r2v31 */
    /* JADX WARN: Type inference failed for: r2v33 */
    /* JADX WARN: Type inference failed for: r2v35 */
    /* JADX WARN: Type inference failed for: r2v37 */
    /* JADX WARN: Type inference failed for: r2v39 */
    /* JADX WARN: Type inference failed for: r2v41 */
    /* JADX WARN: Type inference failed for: r2v43 */
    /* JADX WARN: Type inference failed for: r2v45 */
    /* JADX WARN: Type inference failed for: r2v47 */
    /* JADX WARN: Type inference failed for: r2v49 */
    /* JADX WARN: Type inference failed for: r2v51 */
    /* JADX WARN: Type inference failed for: r2v53 */
    /* JADX WARN: Type inference failed for: r2v55 */
    /* JADX WARN: Type inference failed for: r2v57 */
    /* JADX WARN: Type inference failed for: r2v59 */
    /* JADX WARN: Type inference failed for: r2v61 */
    /* JADX WARN: Type inference failed for: r2v63 */
    /* JADX WARN: Type inference failed for: r2v65 */
    /* JADX WARN: Type inference failed for: r2v67 */
    /* JADX WARN: Type inference failed for: r2v69 */
    /* JADX WARN: Type inference failed for: r2v71 */
    /* JADX WARN: Type inference failed for: r2v73 */
    /* JADX WARN: Type inference failed for: r2v75 */
    /* JADX WARN: Type inference failed for: r2v76 */
    /* JADX WARN: Type inference failed for: r34v0, types: [com.lib.flutter_blue_plus.FlutterBluePlusPlugin] */
    /* JADX WARN: Type inference failed for: r36v0, types: [io.flutter.plugin.common.MethodChannel$Result] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v163 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v3, types: [io.flutter.plugin.common.MethodChannel$Result] */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v8, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v9 */
    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onMethodCall(io.flutter.plugin.common.MethodCall r35, final io.flutter.plugin.common.MethodChannel.Result r36) {
        /*
            Method dump skipped, instruction units count: 3670
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lib.flutter_blue_plus.FlutterBluePlusPlugin.onMethodCall(io.flutter.plugin.common.MethodCall, io.flutter.plugin.common.MethodChannel$Result):void");
    }

    /* JADX INFO: renamed from: lambda$onMethodCall$0$com-lib-flutter_blue_plus-FlutterBluePlusPlugin, reason: not valid java name */
    /* synthetic */ void m634x4faa10e1(MethodChannel.Result result, boolean z, String str) {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        String name = bluetoothAdapter != null ? bluetoothAdapter.getName() : "N/A";
        if (name == null) {
            name = "";
        }
        result.success(name);
    }

    /* JADX INFO: renamed from: lambda$onMethodCall$1$com-lib-flutter_blue_plus-FlutterBluePlusPlugin, reason: not valid java name */
    /* synthetic */ void m635x4f33aae2(MethodChannel.Result result, boolean z, String str) {
        if (!z) {
            result.error("turnOn", String.format("FlutterBluePlus requires %s permission", str), null);
        } else if (this.mBluetoothAdapter.isEnabled()) {
            result.success(false);
        } else {
            this.activityBinding.getActivity().startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 1879842617);
            result.success(true);
        }
    }

    /* JADX INFO: renamed from: lambda$onMethodCall$2$com-lib-flutter_blue_plus-FlutterBluePlusPlugin, reason: not valid java name */
    /* synthetic */ void m636x4ebd44e3(MethodChannel.Result result, boolean z, String str) {
        if (!z) {
            result.error("turnOff", String.format("FlutterBluePlus requires %s permission", str), null);
        } else if (!this.mBluetoothAdapter.isEnabled()) {
            result.success(true);
        } else {
            result.success(Boolean.valueOf(this.mBluetoothAdapter.disable()));
        }
    }

    /* JADX INFO: renamed from: lambda$onMethodCall$3$com-lib-flutter_blue_plus-FlutterBluePlusPlugin, reason: not valid java name */
    /* synthetic */ void m637x4e46dee4(MethodChannel.Result result, int i, boolean z, List list, List list2, List list3, List list4, List list5, List list6, HashMap map, boolean z2, String str) {
        ScanFilter scanFilterBuild;
        ScanFilter scanFilterBuild2;
        if (!z2) {
            result.error(BtConstant.startScan, String.format("FlutterBluePlus requires %s permission", str), null);
            return;
        }
        if (!isAdapterOn()) {
            result.error(BtConstant.startScan, String.format("bluetooth must be turned on", new Object[0]), null);
            return;
        }
        BluetoothLeScanner bluetoothLeScanner = this.mBluetoothAdapter.getBluetoothLeScanner();
        if (bluetoothLeScanner == null) {
            result.error(BtConstant.startScan, String.format("getBluetoothLeScanner() is null. Is the Adapter on?", new Object[0]), null);
            return;
        }
        ScanSettings.Builder builder = new ScanSettings.Builder();
        builder.setScanMode(i);
        builder.setPhy(255);
        builder.setLegacy(z);
        ScanSettings scanSettingsBuild = builder.build();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            arrayList.add(new ScanFilter.Builder().setServiceUuid(ParcelUuid.fromString(uuid128(list.get(i2)))).build());
        }
        for (int i3 = 0; i3 < list2.size(); i3++) {
            arrayList.add(new ScanFilter.Builder().setDeviceAddress((String) list2.get(i3)).build());
        }
        for (int i4 = 0; i4 < list3.size(); i4++) {
            arrayList.add(new ScanFilter.Builder().setDeviceName((String) list3.get(i4)).build());
        }
        if (Build.VERSION.SDK_INT >= 33 && list4.size() > 0) {
            ScanFilter scanFilterBuild3 = new ScanFilter.Builder().setAdvertisingDataType(8).build();
            ScanFilter scanFilterBuild4 = new ScanFilter.Builder().setAdvertisingDataType(9).build();
            arrayList.add(scanFilterBuild3);
            arrayList.add(scanFilterBuild4);
        }
        for (int i5 = 0; i5 < list5.size(); i5++) {
            HashMap map2 = (HashMap) list5.get(i5);
            int iIntValue = ((Integer) map2.get("manufacturer_id")).intValue();
            byte[] bArrHexToBytes = hexToBytes((String) map2.get("data"));
            byte[] bArrHexToBytes2 = hexToBytes((String) map2.get("mask"));
            if (bArrHexToBytes2.length == 0) {
                scanFilterBuild2 = new ScanFilter.Builder().setManufacturerData(iIntValue, bArrHexToBytes).build();
            } else {
                scanFilterBuild2 = new ScanFilter.Builder().setManufacturerData(iIntValue, bArrHexToBytes, bArrHexToBytes2).build();
            }
            arrayList.add(scanFilterBuild2);
        }
        for (int i6 = 0; i6 < list6.size(); i6++) {
            HashMap map3 = (HashMap) list6.get(i6);
            ParcelUuid parcelUuidFromString = ParcelUuid.fromString((String) map3.get(NotificationCompat.CATEGORY_SERVICE));
            byte[] bArrHexToBytes3 = hexToBytes((String) map3.get("data"));
            byte[] bArrHexToBytes4 = hexToBytes((String) map3.get("mask"));
            if (bArrHexToBytes4.length == 0) {
                scanFilterBuild = new ScanFilter.Builder().setServiceData(parcelUuidFromString, bArrHexToBytes3).build();
            } else {
                scanFilterBuild = new ScanFilter.Builder().setServiceData(parcelUuidFromString, bArrHexToBytes3, bArrHexToBytes4).build();
            }
            arrayList.add(scanFilterBuild);
        }
        this.mScanFilters = map;
        this.mAdvSeen.clear();
        this.mScanCounts.clear();
        bluetoothLeScanner.startScan(arrayList, scanSettingsBuild, getScanCallback());
        this.mIsScanning = true;
        result.success(true);
    }

    /* JADX INFO: renamed from: lambda$onMethodCall$4$com-lib-flutter_blue_plus-FlutterBluePlusPlugin, reason: not valid java name */
    /* synthetic */ void m638x4dd078e5(MethodChannel.Result result, boolean z, String str) {
        if (!z) {
            result.error("getSystemDevices", String.format("FlutterBluePlus requires %s permission", str), null);
            return;
        }
        List<BluetoothDevice> connectedDevices = this.mBluetoothManager.getConnectedDevices(7);
        ArrayList arrayList = new ArrayList();
        Iterator<BluetoothDevice> it = connectedDevices.iterator();
        while (it.hasNext()) {
            arrayList.add(bmBluetoothDevice(it.next()));
        }
        HashMap map = new HashMap();
        map.put("devices", arrayList);
        result.success(map);
    }

    /* JADX INFO: renamed from: lambda$onMethodCall$5$com-lib-flutter_blue_plus-FlutterBluePlusPlugin, reason: not valid java name */
    /* synthetic */ void m639x4d5a12e6(MethodChannel.Result result, String str, boolean z, boolean z2, String str2) {
        if (!z2) {
            result.error("connect", String.format("FlutterBluePlus requires %s for new connection", str2), null);
            return;
        }
        if (!isAdapterOn()) {
            result.error("connect", String.format("bluetooth must be turned on", new Object[0]), null);
            return;
        }
        if (this.mCurrentlyConnectingDevices.get(str) != null) {
            log(LogLevel.DEBUG, "already connecting");
            result.success(true);
            return;
        }
        if (this.mConnectedDevices.get(str) != null) {
            log(LogLevel.DEBUG, "already connected");
            result.success(false);
            return;
        }
        waitIfBonding();
        BluetoothGatt bluetoothGattConnectGatt = this.mBluetoothAdapter.getRemoteDevice(str).connectGatt(this.context, z, this.mGattCallback, 2);
        if (bluetoothGattConnectGatt == null) {
            result.error("connect", String.format("device.connectGatt returned null", new Object[0]), null);
            return;
        }
        this.mCurrentlyConnectingDevices.put(str, bluetoothGattConnectGatt);
        if (z) {
            this.mAutoConnected.put(str, bluetoothGattConnectGatt);
        } else {
            this.mAutoConnected.remove(str);
        }
        result.success(true);
    }

    @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
    public boolean onActivityResult(int i, int i2, Intent intent) {
        if (i != 1879842617) {
            return false;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_accepted", Boolean.valueOf(i2 == -1));
        invokeMethodUIThread("OnTurnOnResponse", map);
        return true;
    }

    @Override // io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener
    public boolean onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        OperationOnPermission operationOnPermission = this.operationsOnPermission.get(Integer.valueOf(i));
        if (operationOnPermission == null || iArr.length <= 0) {
            return false;
        }
        operationOnPermission.op(iArr[0] == 0, strArr[0]);
        return true;
    }

    private void ensurePermissions(List<String> list, OperationOnPermission operationOnPermission) {
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            if (str != null && ContextCompat.checkSelfPermission(this.context, str) != 0) {
                arrayList.add(str);
            }
        }
        if (arrayList.isEmpty()) {
            operationOnPermission.op(true, null);
        } else {
            askPermission(arrayList, operationOnPermission);
        }
    }

    private void askPermission(final List<String> list, final OperationOnPermission operationOnPermission) {
        if (list.isEmpty()) {
            operationOnPermission.op(true, null);
            return;
        }
        String strRemove = list.remove(0);
        this.operationsOnPermission.put(Integer.valueOf(this.lastEventId), new OperationOnPermission() { // from class: com.lib.flutter_blue_plus.FlutterBluePlusPlugin$$ExternalSyntheticLambda1
            @Override // com.lib.flutter_blue_plus.FlutterBluePlusPlugin.OperationOnPermission
            public final void op(boolean z, String str) {
                this.f$0.m632x420ea817(operationOnPermission, list, z, str);
            }
        });
        ActivityCompat.requestPermissions(this.activityBinding.getActivity(), new String[]{strRemove}, this.lastEventId);
        this.lastEventId++;
    }

    /* JADX INFO: renamed from: lambda$askPermission$6$com-lib-flutter_blue_plus-FlutterBluePlusPlugin, reason: not valid java name */
    /* synthetic */ void m632x420ea817(OperationOnPermission operationOnPermission, List list, boolean z, String str) {
        this.operationsOnPermission.remove(Integer.valueOf(this.lastEventId));
        if (!z) {
            operationOnPermission.op(false, str);
        } else {
            askPermission(list, operationOnPermission);
        }
    }

    private void waitIfBonding() {
        char c;
        if (this.mBondingDevices.isEmpty()) {
            c = 0;
        } else {
            log(LogLevel.DEBUG, "[FBP] waiting for bonding to complete...");
            try {
                Thread.sleep(50L);
            } catch (Exception unused) {
            }
            c = 1;
        }
        if (c > 0) {
            log(LogLevel.DEBUG, "[FBP] bonding completed");
        }
    }

    class ChrFound {
        public BluetoothGattCharacteristic characteristic;
        public String error;

        public ChrFound(BluetoothGattCharacteristic bluetoothGattCharacteristic, String str) {
            this.characteristic = bluetoothGattCharacteristic;
            this.error = str;
        }
    }

    private ChrFound locateCharacteristic(BluetoothGatt bluetoothGatt, String str, String str2, String str3) {
        BluetoothGattService serviceFromArray;
        BluetoothGattService serviceFromArray2 = getServiceFromArray(str, bluetoothGatt.getServices());
        if (serviceFromArray2 == null) {
            return new ChrFound(null, "service not found '" + str + "'");
        }
        if (str2 == null || str2.length() <= 0) {
            serviceFromArray = null;
        } else {
            serviceFromArray = getServiceFromArray(str, serviceFromArray2.getIncludedServices());
            if (serviceFromArray == null) {
                return new ChrFound(null, "secondaryService not found '" + str2 + "'");
            }
        }
        if (serviceFromArray != null) {
            serviceFromArray2 = serviceFromArray;
        }
        BluetoothGattCharacteristic characteristicFromArray = getCharacteristicFromArray(str3, serviceFromArray2.getCharacteristics());
        if (characteristicFromArray == null) {
            return new ChrFound(null, "characteristic not found in service (chr: '" + str3 + "' svc: '" + str + "')");
        }
        return new ChrFound(characteristicFromArray, null);
    }

    private BluetoothGattService getServiceFromArray(String str, List<BluetoothGattService> list) {
        for (BluetoothGattService bluetoothGattService : list) {
            if (uuid128(bluetoothGattService.getUuid()).equals(uuid128(str))) {
                return bluetoothGattService;
            }
        }
        return null;
    }

    private BluetoothGattCharacteristic getCharacteristicFromArray(String str, List<BluetoothGattCharacteristic> list) {
        for (BluetoothGattCharacteristic bluetoothGattCharacteristic : list) {
            if (uuid128(bluetoothGattCharacteristic.getUuid()).equals(uuid128(str))) {
                return bluetoothGattCharacteristic;
            }
        }
        return null;
    }

    private BluetoothGattDescriptor getDescriptorFromArray(String str, List<BluetoothGattDescriptor> list) {
        for (BluetoothGattDescriptor bluetoothGattDescriptor : list) {
            if (uuid128(bluetoothGattDescriptor.getUuid()).equals(uuid128(str))) {
                return bluetoothGattDescriptor;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean filterKeywords(List<String> list, String str) {
        if (list.isEmpty()) {
            return true;
        }
        if (str == null) {
            return false;
        }
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            if (str.contains(it.next())) {
                return true;
            }
        }
        return false;
    }

    private int getMaxPayload(String str, int i, boolean z) {
        if (i != 1 && z) {
            return 512;
        }
        Integer num = this.mMtu.get(str);
        if (num == null) {
            num = 23;
        }
        return Math.min(num.intValue() - 3, 512);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disconnectAllDevices(String str) {
        log(LogLevel.DEBUG, "disconnectAllDevices(" + str + ")");
        for (BluetoothGatt bluetoothGatt : this.mConnectedDevices.values()) {
            if (str == "adapterTurnOff") {
                this.mGattCallback.onConnectionStateChange(bluetoothGatt, 0, 0);
            } else {
                String address = bluetoothGatt.getDevice().getAddress();
                log(LogLevel.DEBUG, "calling disconnect: " + address);
                bluetoothGatt.disconnect();
                log(LogLevel.DEBUG, "calling close: " + address);
                bluetoothGatt.close();
            }
        }
        this.mConnectedDevices.clear();
        this.mCurrentlyConnectingDevices.clear();
        this.mBondingDevices.clear();
        this.mMtu.clear();
        this.mWriteChr.clear();
        this.mWriteDesc.clear();
        this.mAutoConnected.clear();
    }

    int getAppearanceFromScanRecord(ScanRecord scanRecord) {
        int i;
        int i2;
        if (Build.VERSION.SDK_INT >= 33) {
            Map<Integer, byte[]> advertisingDataMap = scanRecord.getAdvertisingDataMap();
            if (advertisingDataMap.containsKey(25)) {
                byte[] bArr = advertisingDataMap.get(25);
                if (bArr.length == 2) {
                    return ((bArr[1] & UByte.MAX_VALUE) * 256) + (bArr[0] & UByte.MAX_VALUE);
                }
            }
            return 0;
        }
        byte[] bytes = scanRecord.getBytes();
        int i3 = 0;
        while (i3 < bytes.length && (i = bytes[i3] & UByte.MAX_VALUE) > 0 && i3 + i < bytes.length && (i2 = bytes[i3 + 1] & UByte.MAX_VALUE) != 0) {
            if (i2 == 25 && i == 3) {
                return (bytes[i3 + 2] & UByte.MAX_VALUE) | ((bytes[i3 + 3] & UByte.MAX_VALUE) << 8);
            }
            i3 += i + 1;
        }
        return 0;
    }

    Map<Integer, byte[]> getManufacturerSpecificData(ScanRecord scanRecord) {
        int i;
        byte[] bytes = scanRecord.getBytes();
        HashMap map = new HashMap();
        int i2 = 0;
        while (i2 < bytes.length && (i = bytes[i2] & 255) > 0 && i2 + i < bytes.length) {
            if ((bytes[i2 + 1] & UByte.MAX_VALUE) == 255 && i >= 3) {
                int i3 = (255 & bytes[i2 + 2]) | ((bytes[i2 + 3] & UByte.MAX_VALUE) << 8);
                int i4 = i - 3;
                int i5 = i2 + 4;
                if (map.containsKey(Integer.valueOf(i3))) {
                    byte[] bArr = (byte[]) map.get(Integer.valueOf(i3));
                    byte[] bArr2 = new byte[bArr.length + i4];
                    System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                    System.arraycopy(bytes, i5, bArr2, bArr.length, i4);
                    map.put(Integer.valueOf(i3), bArr2);
                } else {
                    byte[] bArr3 = new byte[i4];
                    System.arraycopy(bytes, i5, bArr3, 0, i4);
                    map.put(Integer.valueOf(i3), bArr3);
                }
            }
            i2 += i + 1;
        }
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int scanCountIncrement(String str) {
        if (this.mScanCounts.get(str) == null) {
            this.mScanCounts.put(str, 0);
        }
        int iIntValue = this.mScanCounts.get(str).intValue();
        this.mScanCounts.put(str, Integer.valueOf(iIntValue + 1));
        return iIntValue;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ScanCallback getScanCallback() {
        if (this.scanCallback == null) {
            this.scanCallback = new ScanCallback() { // from class: com.lib.flutter_blue_plus.FlutterBluePlusPlugin.3
                @Override // android.bluetooth.le.ScanCallback
                public void onScanResult(int i, ScanResult scanResult) {
                    FlutterBluePlusPlugin.this.log(LogLevel.VERBOSE, "onScanResult");
                    super.onScanResult(i, scanResult);
                    BluetoothDevice device = scanResult.getDevice();
                    String address = device.getAddress();
                    ScanRecord scanRecord = scanResult.getScanRecord();
                    String strBytesToHex = scanRecord != null ? FlutterBluePlusPlugin.bytesToHex(scanRecord.getBytes()) : "";
                    if (!((Boolean) FlutterBluePlusPlugin.this.mScanFilters.get("continuous_updates")).booleanValue()) {
                        boolean z = FlutterBluePlusPlugin.this.mAdvSeen.containsKey(address) && ((String) FlutterBluePlusPlugin.this.mAdvSeen.get(address)).equals(strBytesToHex);
                        FlutterBluePlusPlugin.this.mAdvSeen.put(address, strBytesToHex);
                        if (z) {
                            return;
                        }
                    }
                    if (FlutterBluePlusPlugin.this.filterKeywords((List) FlutterBluePlusPlugin.this.mScanFilters.get("with_keywords"), scanRecord != null ? scanRecord.getDeviceName() : "")) {
                        if (!((Boolean) FlutterBluePlusPlugin.this.mScanFilters.get("continuous_updates")).booleanValue() || FlutterBluePlusPlugin.this.scanCountIncrement(address) % ((Integer) FlutterBluePlusPlugin.this.mScanFilters.get("continuous_divisor")).intValue() == 0) {
                            HashMap map = new HashMap();
                            map.put("advertisements", Arrays.asList(FlutterBluePlusPlugin.this.bmScanAdvertisement(device, scanResult)));
                            FlutterBluePlusPlugin.this.invokeMethodUIThread("OnScanResponse", map);
                        }
                    }
                }

                @Override // android.bluetooth.le.ScanCallback
                public void onBatchScanResults(List<ScanResult> list) {
                    super.onBatchScanResults(list);
                }

                @Override // android.bluetooth.le.ScanCallback
                public void onScanFailed(int i) {
                    FlutterBluePlusPlugin.this.log(LogLevel.ERROR, "onScanFailed: " + FlutterBluePlusPlugin.scanFailedString(i));
                    super.onScanFailed(i);
                    HashMap map = new HashMap();
                    map.put("advertisements", new ArrayList());
                    map.put("success", 0);
                    map.put("error_code", Integer.valueOf(i));
                    map.put("error_string", FlutterBluePlusPlugin.scanFailedString(i));
                    FlutterBluePlusPlugin.this.invokeMethodUIThread("OnScanResponse", map);
                }
            };
        }
        return this.scanCallback;
    }

    HashMap<String, Object> bmScanAdvertisement(BluetoothDevice bluetoothDevice, ScanResult scanResult) {
        ScanRecord scanRecord = scanResult.getScanRecord();
        boolean zIsConnectable = scanResult.isConnectable();
        String deviceName = scanRecord != null ? scanRecord.getDeviceName() : null;
        int txPowerLevel = scanRecord != null ? scanRecord.getTxPowerLevel() : Integer.MIN_VALUE;
        int appearanceFromScanRecord = scanRecord != null ? getAppearanceFromScanRecord(scanRecord) : 0;
        Map<Integer, byte[]> manufacturerSpecificData = scanRecord != null ? getManufacturerSpecificData(scanRecord) : null;
        List<ParcelUuid> serviceUuids = scanRecord != null ? scanRecord.getServiceUuids() : null;
        Map<ParcelUuid, byte[]> serviceData = scanRecord != null ? scanRecord.getServiceData() : null;
        HashMap map = new HashMap();
        if (manufacturerSpecificData != null) {
            for (Map.Entry<Integer, byte[]> entry : manufacturerSpecificData.entrySet()) {
                map.put(entry.getKey(), bytesToHex(entry.getValue()));
            }
        }
        HashMap map2 = new HashMap();
        if (serviceData != null) {
            for (Map.Entry<ParcelUuid, byte[]> entry2 : serviceData.entrySet()) {
                map2.put(uuidStr(entry2.getKey().getUuid()), bytesToHex(entry2.getValue()));
            }
        }
        ArrayList arrayList = new ArrayList();
        if (serviceUuids != null) {
            Iterator<ParcelUuid> it = serviceUuids.iterator();
            while (it.hasNext()) {
                arrayList.add(uuidStr(it.next().getUuid()));
            }
        }
        HashMap<String, Object> map3 = new HashMap<>();
        if (bluetoothDevice.getAddress() != null) {
            map3.put("remote_id", bluetoothDevice.getAddress());
        }
        if (bluetoothDevice.getName() != null) {
            map3.put("platform_name", bluetoothDevice.getName());
        }
        if (zIsConnectable) {
            map3.put("connectable", 1);
        }
        if (deviceName != null) {
            map3.put("adv_name", deviceName);
        }
        if (txPowerLevel != Integer.MIN_VALUE) {
            map3.put("tx_power_level", Integer.valueOf(txPowerLevel));
        }
        if (appearanceFromScanRecord != 0) {
            map3.put("appearance", Integer.valueOf(appearanceFromScanRecord));
        }
        if (manufacturerSpecificData != null) {
            map3.put("manufacturer_data", map);
        }
        if (serviceData != null) {
            map3.put("service_data", map2);
        }
        if (serviceUuids != null) {
            map3.put("service_uuids", arrayList);
        }
        if (scanResult.getRssi() != 0) {
            map3.put("rssi", Integer.valueOf(scanResult.getRssi()));
        }
        return map3;
    }

    HashMap<String, Object> bmBluetoothDevice(BluetoothDevice bluetoothDevice) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("remote_id", bluetoothDevice.getAddress());
        map.put("platform_name", bluetoothDevice.getName());
        return map;
    }

    HashMap<String, Object> bmBluetoothService(BluetoothDevice bluetoothDevice, BluetoothGattService bluetoothGattService, BluetoothGatt bluetoothGatt) {
        ArrayList arrayList = new ArrayList();
        Iterator<BluetoothGattCharacteristic> it = bluetoothGattService.getCharacteristics().iterator();
        while (it.hasNext()) {
            arrayList.add(bmBluetoothCharacteristic(bluetoothDevice, it.next(), bluetoothGatt));
        }
        ArrayList arrayList2 = new ArrayList();
        for (BluetoothGattService bluetoothGattService2 : bluetoothGattService.getIncludedServices()) {
            if (!bluetoothGattService2.getUuid().equals(bluetoothGattService.getUuid())) {
                arrayList2.add(bmBluetoothService(bluetoothDevice, bluetoothGattService2, bluetoothGatt));
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("remote_id", bluetoothDevice.getAddress());
        map.put("service_uuid", uuidStr(bluetoothGattService.getUuid()));
        map.put("is_primary", Integer.valueOf(bluetoothGattService.getType() == 0 ? 1 : 0));
        map.put("characteristics", arrayList);
        map.put("included_services", arrayList2);
        return map;
    }

    HashMap<String, Object> bmBluetoothCharacteristic(BluetoothDevice bluetoothDevice, BluetoothGattCharacteristic bluetoothGattCharacteristic, BluetoothGatt bluetoothGatt) {
        ServicePair servicePair = getServicePair(bluetoothGatt, bluetoothGattCharacteristic);
        ArrayList arrayList = new ArrayList();
        Iterator<BluetoothGattDescriptor> it = bluetoothGattCharacteristic.getDescriptors().iterator();
        while (it.hasNext()) {
            arrayList.add(bmBluetoothDescriptor(bluetoothDevice, it.next()));
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("remote_id", bluetoothDevice.getAddress());
        map.put("service_uuid", uuidStr(servicePair.primary));
        if (servicePair.secondary != null) {
            map.put("secondary_service_uuid", uuidStr(servicePair.secondary));
        }
        map.put("characteristic_uuid", uuidStr(bluetoothGattCharacteristic.getUuid()));
        map.put("descriptors", arrayList);
        map.put("properties", bmCharacteristicProperties(bluetoothGattCharacteristic.getProperties()));
        return map;
    }

    HashMap<String, Object> bmBluetoothDescriptor(BluetoothDevice bluetoothDevice, BluetoothGattDescriptor bluetoothGattDescriptor) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("remote_id", bluetoothDevice.getAddress());
        map.put("descriptor_uuid", uuidStr(bluetoothGattDescriptor.getUuid()));
        map.put("characteristic_uuid", uuidStr(bluetoothGattDescriptor.getCharacteristic().getUuid()));
        map.put("service_uuid", uuidStr(bluetoothGattDescriptor.getCharacteristic().getService().getUuid()));
        return map;
    }

    HashMap<String, Object> bmCharacteristicProperties(int i) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("broadcast", Integer.valueOf((i & 1) != 0 ? 1 : 0));
        map.put("read", Integer.valueOf((i & 2) != 0 ? 1 : 0));
        map.put("write_without_response", Integer.valueOf((i & 4) != 0 ? 1 : 0));
        map.put("write", Integer.valueOf((i & 8) != 0 ? 1 : 0));
        map.put(Methods.notify, Integer.valueOf((i & 16) != 0 ? 1 : 0));
        map.put("indicate", Integer.valueOf((i & 32) != 0 ? 1 : 0));
        map.put("authenticated_signed_writes", Integer.valueOf((i & 64) != 0 ? 1 : 0));
        map.put("extended_properties", Integer.valueOf((i & 128) != 0 ? 1 : 0));
        map.put("notify_encryption_required", Integer.valueOf((i & 256) != 0 ? 1 : 0));
        map.put("indicate_encryption_required", Integer.valueOf((i & 512) != 0 ? 1 : 0));
        return map;
    }

    static ServicePair getServicePair(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        ServicePair servicePair = new ServicePair();
        BluetoothGattService service = bluetoothGattCharacteristic.getService();
        if (service.getType() == 0) {
            servicePair.primary = service.getUuid();
            return servicePair;
        }
        Iterator<BluetoothGattService> it = bluetoothGatt.getServices().iterator();
        loop0: while (true) {
            if (!it.hasNext()) {
                break;
            }
            BluetoothGattService next = it.next();
            for (BluetoothGattService bluetoothGattService : next.getIncludedServices()) {
                if (bluetoothGattService.getUuid().equals(service.getUuid())) {
                    servicePair.primary = next.getUuid();
                    servicePair.secondary = bluetoothGattService.getUuid();
                    break loop0;
                }
            }
        }
        return servicePair;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void log(LogLevel logLevel, String str) {
        if (logLevel.ordinal() > this.logLevel.ordinal()) {
            return;
        }
        int i = AnonymousClass5.$SwitchMap$com$lib$flutter_blue_plus$FlutterBluePlusPlugin$LogLevel[logLevel.ordinal()];
        if (i == 1) {
            Log.d(TAG, "[FBP] " + str);
            return;
        }
        if (i == 2) {
            Log.w(TAG, "[FBP] " + str);
        } else if (i == 3) {
            Log.e(TAG, "[FBP] " + str);
        } else {
            Log.d(TAG, "[FBP] " + str);
        }
    }

    /* JADX INFO: renamed from: com.lib.flutter_blue_plus.FlutterBluePlusPlugin$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$lib$flutter_blue_plus$FlutterBluePlusPlugin$LogLevel;

        static {
            int[] iArr = new int[LogLevel.values().length];
            $SwitchMap$com$lib$flutter_blue_plus$FlutterBluePlusPlugin$LogLevel = iArr;
            try {
                iArr[LogLevel.DEBUG.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$lib$flutter_blue_plus$FlutterBluePlusPlugin$LogLevel[LogLevel.WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$lib$flutter_blue_plus$FlutterBluePlusPlugin$LogLevel[LogLevel.ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeMethodUIThread(final String str, final HashMap<String, Object> map) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.lib.flutter_blue_plus.FlutterBluePlusPlugin$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m633xb8d41fc1(str, map);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$invokeMethodUIThread$7$com-lib-flutter_blue_plus-FlutterBluePlusPlugin, reason: not valid java name */
    /* synthetic */ void m633xb8d41fc1(String str, HashMap map) {
        MethodChannel methodChannel = this.methodChannel;
        if (methodChannel != null) {
            methodChannel.invokeMethod(str, map);
        } else {
            log(LogLevel.WARNING, "invokeMethodUIThread: tried to call method on closed channel: " + str);
        }
    }

    private boolean isAdapterOn() {
        try {
            return this.mBluetoothAdapter.getState() == 12;
        } catch (Exception unused) {
            return false;
        }
    }

    private static byte[] hexToBytes(String str) {
        if (str == null) {
            return new byte[0];
        }
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String bytesToHex(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append(Character.forDigit((b >> 4) & 15, 16));
            sb.append(Character.forDigit(b & 15, 16));
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String connectionStateString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? "UNKNOWN_CONNECTION_STATE (" + i + ")" : "disconnecting" : "connected" : "connecting" : "disconnected";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String adapterStateString(int i) {
        switch (i) {
            case 10:
                return DebugKt.DEBUG_PROPERTY_VALUE_OFF;
            case 11:
                return "turningOn";
            case 12:
                return DebugKt.DEBUG_PROPERTY_VALUE_ON;
            case 13:
                return "turningOff";
            default:
                return "UNKNOWN_ADAPTER_STATE (" + i + ")";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String bondStateString(int i) {
        switch (i) {
            case 10:
                return "bond-none";
            case 11:
                return "bonding";
            case 12:
                return "bonded";
            default:
                return "UNKNOWN_BOND_STATE (" + i + ")";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String gattErrorString(int i) {
        if (i == 143) {
            return "GATT_CONNECTION_CONGESTED";
        }
        if (i == 257) {
            return "GATT_FAILURE";
        }
        switch (i) {
            case 0:
                return "GATT_SUCCESS";
            case 1:
                return "GATT_INVALID_HANDLE";
            case 2:
                return "GATT_READ_NOT_PERMITTED";
            case 3:
                return "GATT_WRITE_NOT_PERMITTED";
            case 4:
                return "GATT_INVALID_PDU";
            case 5:
                return "GATT_INSUFFICIENT_AUTHENTICATION";
            case 6:
                return "GATT_REQUEST_NOT_SUPPORTED";
            case 7:
                return "GATT_INVALID_OFFSET";
            case 8:
                return "GATT_INSUFFICIENT_AUTHORIZATION";
            case 9:
                return "GATT_PREPARE_QUEUE_FULL";
            case 10:
                return "GATT_ATTR_NOT_FOUND";
            case 11:
                return "GATT_ATTR_NOT_LONG";
            case 12:
                return "GATT_INSUFFICIENT_KEY_SIZE";
            case 13:
                return "GATT_INVALID_ATTRIBUTE_LENGTH";
            case 14:
                return "GATT_UNLIKELY";
            case 15:
                return "GATT_INSUFFICIENT_ENCRYPTION";
            case 16:
                return "GATT_UNSUPPORTED_GROUP";
            case 17:
                return "GATT_INSUFFICIENT_RESOURCES";
            default:
                return "UNKNOWN_GATT_ERROR (" + i + ")";
        }
    }

    private static String bluetoothStatusString(int i) {
        if (i == 0) {
            return "SUCCESS";
        }
        if (i == 1) {
            return "ERROR_BLUETOOTH_NOT_ENABLED";
        }
        if (i == 2) {
            return "ERROR_BLUETOOTH_NOT_ALLOWED";
        }
        if (i == 3) {
            return "ERROR_DEVICE_NOT_BONDED";
        }
        if (i == 6) {
            return "ERROR_MISSING_BLUETOOTH_CONNECT_PERMISSION";
        }
        if (i == Integer.MAX_VALUE) {
            return "ERROR_UNKNOWN";
        }
        if (i == 200) {
            return "ERROR_GATT_WRITE_NOT_ALLOWED";
        }
        if (i == 201) {
            return "ERROR_GATT_WRITE_REQUEST_BUSY";
        }
        switch (i) {
            case 9:
                return "ERROR_PROFILE_SERVICE_NOT_BOUND";
            case 10:
                return "FEATURE_SUPPORTED";
            case 11:
                return "FEATURE_NOT_SUPPORTED";
            default:
                return "UNKNOWN_BLE_ERROR (" + i + ")";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String scanFailedString(int i) {
        switch (i) {
            case 1:
                return "SCAN_FAILED_ALREADY_STARTED";
            case 2:
                return "SCAN_FAILED_APPLICATION_REGISTRATION_FAILED";
            case 3:
                return "SCAN_FAILED_INTERNAL_ERROR";
            case 4:
                return "SCAN_FAILED_FEATURE_UNSUPPORTED";
            case 5:
                return "SCAN_FAILED_OUT_OF_HARDWARE_RESOURCES";
            case 6:
                return "SCAN_FAILED_SCANNING_TOO_FREQUENTLY";
            default:
                return "UNKNOWN_SCAN_ERROR (" + i + ")";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String hciStatusString(int i) {
        if (i == 133) {
            return "ANDROID_SPECIFIC_ERROR";
        }
        if (i == 257) {
            return "FAILURE_REGISTERING_CLIENT";
        }
        switch (i) {
            case 0:
                return "SUCCESS";
            case 1:
                return "UNKNOWN_COMMAND";
            case 2:
                return "UNKNOWN_CONNECTION_IDENTIFIER";
            case 3:
                return "HARDWARE_FAILURE";
            case 4:
                return "PAGE_TIMEOUT";
            case 5:
                return "AUTHENTICATION_FAILURE";
            case 6:
                return "PIN_OR_KEY_MISSING";
            case 7:
                return "MEMORY_FULL";
            case 8:
                return "LINK_SUPERVISION_TIMEOUT";
            case 9:
                return "CONNECTION_LIMIT_EXCEEDED";
            case 10:
                return "MAX_NUM_OF_CONNECTIONS_EXCEEDED";
            case 11:
                return "CONNECTION_ALREADY_EXISTS";
            case 12:
                return "COMMAND_DISALLOWED";
            case 13:
                return "CONNECTION_REJECTED_LIMITED_RESOURCES";
            case 14:
                return "CONNECTION_REJECTED_SECURITY_REASONS";
            case 15:
                return "CONNECTION_REJECTED_UNACCEPTABLE_MAC_ADDRESS";
            case 16:
                return "CONNECTION_ACCEPT_TIMEOUT_EXCEEDED";
            case 17:
                return "UNSUPPORTED_PARAMETER_VALUE";
            case 18:
                return "INVALID_COMMAND_PARAMETERS";
            case 19:
                return "REMOTE_USER_TERMINATED_CONNECTION";
            case 20:
                return "REMOTE_DEVICE_TERMINATED_CONNECTION_LOW_RESOURCES";
            case 21:
                return "REMOTE_DEVICE_TERMINATED_CONNECTION_POWER_OFF";
            case 22:
                return "CONNECTION_TERMINATED_BY_LOCAL_HOST";
            case 23:
                return "REPEATED_ATTEMPTS";
            case 24:
                return "PAIRING_NOT_ALLOWED";
            case 25:
                return "UNKNOWN_LMP_PDU";
            case 26:
                return "UNSUPPORTED_REMOTE_FEATURE";
            case 27:
                return "SCO_OFFSET_REJECTED";
            case 28:
                return "SCO_INTERVAL_REJECTED";
            case 29:
                return "SCO_AIR_MODE_REJECTED";
            case 30:
                return "INVALID_LMP_OR_LL_PARAMETERS";
            case 31:
                return "UNSPECIFIED";
            case 32:
                return "UNSUPPORTED_LMP_OR_LL_PARAMETER_VALUE";
            case 33:
                return "ROLE_CHANGE_NOT_ALLOWED";
            case 34:
                return "LMP_OR_LL_RESPONSE_TIMEOUT";
            case 35:
                return "LMP_OR_LL_ERROR_TRANS_COLLISION";
            case 36:
                return "LMP_PDU_NOT_ALLOWED";
            case 37:
                return "ENCRYPTION_MODE_NOT_ACCEPTABLE";
            case 38:
                return "LINK_KEY_CANNOT_BE_EXCHANGED";
            case 39:
                return "REQUESTED_QOS_NOT_SUPPORTED";
            case 40:
                return "INSTANT_PASSED";
            case 41:
                return "PAIRING_WITH_UNIT_KEY_NOT_SUPPORTED";
            case 42:
                return "DIFFERENT_TRANSACTION_COLLISION";
            case 43:
                return "UNDEFINED_0x2B";
            case 44:
                return "QOS_UNACCEPTABLE_PARAMETER";
            case 45:
                return "QOS_REJECTED";
            case 46:
                return "CHANNEL_CLASSIFICATION_NOT_SUPPORTED";
            case 47:
                return "INSUFFICIENT_SECURITY";
            case 48:
                return "PARAMETER_OUT_OF_RANGE";
            case 49:
                return "UNDEFINED_0x31";
            case 50:
                return "ROLE_SWITCH_PENDING";
            case 51:
                return "UNDEFINED_0x33";
            case 52:
                return "RESERVED_SLOT_VIOLATION";
            case 53:
                return "ROLE_SWITCH_FAILED";
            case 54:
                return "INQUIRY_RESPONSE_TOO_LARGE";
            case 55:
                return "SECURE_SIMPLE_PAIRING_NOT_SUPPORTED";
            case 56:
                return "HOST_BUSY_PAIRING";
            case 57:
                return "CONNECTION_REJECTED_NO_SUITABLE_CHANNEL";
            case 58:
                return "CONTROLLER_BUSY";
            case 59:
                return "UNACCEPTABLE_CONNECTION_PARAMETERS";
            case 60:
                return "ADVERTISING_TIMEOUT";
            case 61:
                return "CONNECTION_TERMINATED_MIC_FAILURE";
            case 62:
                return "CONNECTION_FAILED_ESTABLISHMENT";
            case 63:
                return "MAC_CONNECTION_FAILED";
            case 64:
                return "COARSE_CLOCK_ADJUSTMENT_REJECTED";
            case 65:
                return "TYPE0_SUBMAP_NOT_DEFINED";
            case 66:
                return "UNKNOWN_ADVERTISING_IDENTIFIER";
            case 67:
                return "LIMIT_REACHED";
            case 68:
                return "OPERATION_CANCELLED_BY_HOST";
            case 69:
                return "PACKET_TOO_LONG";
            default:
                return "UNKNOWN_HCI_ERROR (" + i + ")";
        }
    }
}
