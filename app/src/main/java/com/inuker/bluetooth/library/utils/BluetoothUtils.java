package com.inuker.bluetooth.library.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.inuker.bluetooth.library.BluetoothContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
public class BluetoothUtils {
    private static BluetoothAdapter mBluetoothAdapter;
    private static BluetoothManager mBluetoothManager;
    private static Handler mHandler;

    public static Context getContext() {
        return BluetoothContext.get();
    }

    private static Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

    public static void post(Runnable runnable) {
        getHandler().post(runnable);
    }

    public static void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        registerGlobalReceiver(broadcastReceiver, intentFilter);
    }

    private static void registerGlobalReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        getContext().registerReceiver(broadcastReceiver, intentFilter, 4);
    }

    public static void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        unregisterGlobalReceiver(broadcastReceiver);
    }

    private static void unregisterGlobalReceiver(BroadcastReceiver broadcastReceiver) {
        getContext().unregisterReceiver(broadcastReceiver);
    }

    public static void sendBroadcast(Intent intent) {
        sendGlobalBroadcast(intent);
    }

    public static void sendBroadcast(String str) {
        sendGlobalBroadcast(new Intent(str));
    }

    private static void sendGlobalBroadcast(Intent intent) {
        getContext().sendBroadcast(intent);
    }

    public static boolean isBleSupported() {
        return getContext() != null && getContext().getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
    }

    public static boolean isBluetoothEnabled() {
        return getBluetoothState() == 12;
    }

    public static int getBluetoothState() {
        BluetoothAdapter bluetoothAdapter = getBluetoothAdapter();
        if (bluetoothAdapter != null) {
            return bluetoothAdapter.getState();
        }
        return 0;
    }

    public static boolean openBluetooth() {
        BluetoothAdapter bluetoothAdapter = getBluetoothAdapter();
        if (bluetoothAdapter != null) {
            return bluetoothAdapter.enable();
        }
        return false;
    }

    public static boolean closeBluetooth() {
        BluetoothAdapter bluetoothAdapter = getBluetoothAdapter();
        if (bluetoothAdapter != null) {
            return bluetoothAdapter.disable();
        }
        return false;
    }

    public static BluetoothManager getBluetoothManager() {
        if (!isBleSupported()) {
            return null;
        }
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getContext().getSystemService("bluetooth");
        }
        return mBluetoothManager;
    }

    public static BluetoothAdapter getBluetoothAdapter() {
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
        return mBluetoothAdapter;
    }

    public static BluetoothDevice getRemoteDevice(String str) {
        BluetoothAdapter bluetoothAdapter;
        if (TextUtils.isEmpty(str) || (bluetoothAdapter = getBluetoothAdapter()) == null) {
            return null;
        }
        return bluetoothAdapter.getRemoteDevice(str);
    }

    public static List<BluetoothDevice> getConnectedBluetoothLeDevices() {
        ArrayList arrayList = new ArrayList();
        BluetoothManager bluetoothManager = getBluetoothManager();
        if (bluetoothManager != null) {
            arrayList.addAll(bluetoothManager.getConnectedDevices(7));
        }
        return arrayList;
    }

    public static int getConnectStatus(String str) {
        BluetoothManager bluetoothManager = getBluetoothManager();
        if (bluetoothManager == null) {
            return -1;
        }
        try {
            return bluetoothManager.getConnectionState(getRemoteDevice(str), 7);
        } catch (Throwable th) {
            BluetoothLog.e(th);
            return -1;
        }
    }

    public static int getBondState(String str) {
        if (getBluetoothManager() == null) {
            return 10;
        }
        try {
            return getRemoteDevice(str).getBondState();
        } catch (Throwable th) {
            BluetoothLog.e(th);
            return 10;
        }
    }

    public static List<BluetoothDevice> getBondedBluetoothClassicDevices() {
        Set<BluetoothDevice> bondedDevices;
        BluetoothAdapter bluetoothAdapter = getBluetoothAdapter();
        ArrayList arrayList = new ArrayList();
        if (bluetoothAdapter != null && (bondedDevices = bluetoothAdapter.getBondedDevices()) != null) {
            arrayList.addAll(bondedDevices);
        }
        return arrayList;
    }

    public static boolean isDeviceConnected(String str) {
        if (TextUtils.isEmpty(str) || !isBleSupported()) {
            return false;
        }
        return getBluetoothManager().getConnectionState(getBluetoothAdapter().getRemoteDevice(str), 7) == 2;
    }

    public static boolean checkMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean refreshGattCache(android.bluetooth.BluetoothGatt r5) {
        /*
            r0 = 1
            r1 = 0
            if (r5 == 0) goto L24
            java.lang.Class<android.bluetooth.BluetoothGatt> r2 = android.bluetooth.BluetoothGatt.class
            java.lang.String r3 = "refresh"
            java.lang.Class[] r4 = new java.lang.Class[r1]     // Catch: java.lang.Exception -> L20
            java.lang.reflect.Method r2 = r2.getMethod(r3, r4)     // Catch: java.lang.Exception -> L20
            if (r2 == 0) goto L24
            r2.setAccessible(r0)     // Catch: java.lang.Exception -> L20
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch: java.lang.Exception -> L20
            java.lang.Object r5 = r2.invoke(r5, r3)     // Catch: java.lang.Exception -> L20
            java.lang.Boolean r5 = (java.lang.Boolean) r5     // Catch: java.lang.Exception -> L20
            boolean r5 = r5.booleanValue()     // Catch: java.lang.Exception -> L20
            goto L25
        L20:
            r5 = move-exception
            com.inuker.bluetooth.library.utils.BluetoothLog.e(r5)
        L24:
            r5 = r1
        L25:
            java.lang.Object[] r0 = new java.lang.Object[r0]
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r5)
            r0[r1] = r2
            java.lang.String r1 = "refreshDeviceCache return %b"
            java.lang.String r0 = java.lang.String.format(r1, r0)
            com.inuker.bluetooth.library.utils.BluetoothLog.v(r0)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.inuker.bluetooth.library.utils.BluetoothUtils.refreshGattCache(android.bluetooth.BluetoothGatt):boolean");
    }
}
