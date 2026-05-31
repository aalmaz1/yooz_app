package cn.baos.watch.sdk.bluetooth.bt;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Build;
import androidx.core.os.EnvironmentCompat;
import cn.baos.watch.sdk.base.AppDataConfig;
import cn.baos.watch.sdk.bluetooth.BleService;
import cn.baos.watch.sdk.bluetooth.constant.BTConstant;
import cn.baos.watch.sdk.interfac.ble.HbBtClientManager;
import cn.baos.watch.sdk.util.DeviceIdUtil;
import cn.baos.watch.sdk.util.LogUtil;
import cn.yoozworld.watch.utils.BtConstant;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class BleUtils {
    private BluetoothDevice currentBluetoothDevice;
    private int number;
    public String mContentDisTip = BtConstant.disconnect;
    private boolean isScan = false;
    private final BluetoothProfile.ServiceListener disconnectProfileServiceListener = new BluetoothProfile.ServiceListener() { // from class: cn.baos.watch.sdk.bluetooth.bt.BleUtils.1
        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            boolean zBooleanValue;
            boolean zBooleanValue2 = false;
            if (i == 1) {
                try {
                    BluetoothHeadset bluetoothHeadset = (BluetoothHeadset) bluetoothProfile;
                    try {
                        Method declaredMethod = bluetoothHeadset.getClass().getDeclaredMethod(BleUtils.this.mContentDisTip, BluetoothDevice.class);
                        declaredMethod.setAccessible(true);
                        zBooleanValue = ((Boolean) declaredMethod.invoke(bluetoothHeadset, BleUtils.this.currentBluetoothDevice)).booleanValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                        zBooleanValue = false;
                    }
                    LogUtil.d("jili-HEADSET:" + zBooleanValue);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            if (i == 2) {
                BluetoothA2dp bluetoothA2dp = (BluetoothA2dp) bluetoothProfile;
                try {
                    Method declaredMethod2 = bluetoothA2dp.getClass().getDeclaredMethod(BleUtils.this.mContentDisTip, BluetoothDevice.class);
                    declaredMethod2.setAccessible(true);
                    zBooleanValue2 = ((Boolean) declaredMethod2.invoke(bluetoothA2dp, BleUtils.this.currentBluetoothDevice)).booleanValue();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                LogUtil.d("jili-A2DP:" + zBooleanValue2);
            }
        }
    };
    private BluetoothAdapter mBluetoothAdapter = BleService.getInstance().mBluetoothAdapter;
    List<BluetoothDevice> bondedDevices = BleService.getInstance().getBondedDevices();

    public BleUtils() {
        this.number = 0;
        this.number = 0;
    }

    public void startConnect(Context context, String str, boolean z) {
        this.isScan = z;
        startConnect(context, str);
    }

    private void startConnect(Context context, String str) {
        if (AppDataConfig.getInstance().isBindLast(str)) {
            AppDataConfig.getInstance().updateCurrentConfig(str, true);
            HbBtClientManager.getInstance().updateConnectConfig();
            BleService.getInstance().startConnect();
            return;
        }
        BleService.getInstance().mConnectTask.cleaSessionAsyncDelayJob();
        if (!isSystemBonded(context, str)) {
            HbBtClientManager.getInstance().startConnect(str, this.isScan);
        } else {
            if (BT625Client.getInstance().btIsConnect() == 1) {
                HbBtClientManager.getInstance().startConnect(str, this.isScan);
                return;
            }
            BleService.getInstance().getNotificationHandler().onBLEStartScan();
            removeSystem(context, str);
            startConectb(context, str);
        }
    }

    public void startConectb(Context context, final String str) {
        BleService.getInstance().mConnectTask.mHandler.postDelayed(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.bt.BleUtils$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$startConectb$0(str);
            }
        }, 3000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startConectb$0(String str) {
        HbBtClientManager.getInstance().startConnect(str, this.isScan);
    }

    public boolean removeSystem(Context context, String str) {
        if (!isSystemBonded(context, str)) {
            return true;
        }
        remove(this.mBluetoothAdapter.getRemoteDevice(str));
        return false;
    }

    public boolean isSystemBonded(Context context, String str) {
        LogUtil.d("-----start--isSystemBonded--" + str);
        ArrayList<BluetoothDevice> bondedDevices = BleService.getInstance().getBondedDevices();
        this.bondedDevices = bondedDevices;
        if (bondedDevices != null && bondedDevices.size() > 0) {
            for (BluetoothDevice bluetoothDevice : this.bondedDevices) {
                LogUtil.d("-----start--isSystemBonded-device-" + bluetoothDevice);
                if (bluetoothDevice.getAddress().equals(str)) {
                    LogUtil.d("-----end--isSystemBonded--" + str);
                    return true;
                }
            }
        }
        LogUtil.d("-----end--isSystemBonded--未配对");
        return false;
    }

    public void remove(final BluetoothDevice bluetoothDevice) {
        LogUtil.d("-----start--remove--" + bluetoothDevice);
        BleService.getInstance().mConnectTask.mHandler.post(new Runnable() { // from class: cn.baos.watch.sdk.bluetooth.bt.BleUtils$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BleUtils.lambda$remove$1(bluetoothDevice);
            }
        });
    }

    static /* synthetic */ void lambda$remove$1(BluetoothDevice bluetoothDevice) {
        try {
            Boolean bool = (Boolean) BluetoothDevice.class.getMethod("removeBond", new Class[0]).invoke(bluetoothDevice, new Object[0]);
            if (bool == null || bool.booleanValue()) {
                return;
            }
            LogUtil.d("unbind failed.");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("---requestRemoveBt--Exception");
        }
    }

    public void delRemove(String str) {
        try {
            Boolean bool = (Boolean) BluetoothDevice.class.getMethod("removeBond", new Class[0]).invoke(this.mBluetoothAdapter.getRemoteDevice(str), new Object[0]);
            if (bool == null || bool.booleanValue()) {
                return;
            }
            LogUtil.d("unbind failed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disBtConnect(Context context, String str) {
        BluetoothDevice remoteDevice = this.mBluetoothAdapter.getRemoteDevice(str);
        LogUtil.d("--config--move--mac=" + str);
        try {
            if (BT625Client.getInstance().btIsConnect() == 1) {
                new BleUtils().disConnectBt(context, remoteDevice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void realRemove(Context context, BluetoothDevice bluetoothDevice) {
        LogUtil.d("-----start--realRemove--" + bluetoothDevice);
        try {
            Boolean bool = (Boolean) BluetoothDevice.class.getMethod("removeBond", new Class[0]).invoke(bluetoothDevice, new Object[0]);
            if (bool == null || bool.booleanValue()) {
                return;
            }
            LogUtil.d("unbind failed realRemove.");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("---realRemove--Exception");
        }
    }

    public void disConnectBt(Context context, BluetoothDevice bluetoothDevice) {
        LogUtil.d("jili---bt-----BT-断开连接");
        this.currentBluetoothDevice = bluetoothDevice;
        this.mBluetoothAdapter.getProfileProxy(context, this.disconnectProfileServiceListener, 1);
        this.mBluetoothAdapter.getProfileProxy(context, this.disconnectProfileServiceListener, 2);
    }

    public static String getCurrentMac() {
        if (HbBtClientManager.getInstance().getCurrentConnectConfig() == null) {
            return "";
        }
        String str = HbBtClientManager.getInstance().getCurrentConnectConfig().macAddress;
        return StringUtils.isBlank(str) ? "" : str;
    }

    public boolean isHuabaoDevice(ScanResult scanResult) {
        String strSubstring = "";
        if (scanResult.getScanRecord() != null) {
            String address = scanResult.getDevice().getAddress();
            String name = scanResult.getDevice().getName();
            byte[] bytes = scanResult.getScanRecord().getBytes();
            if (bytes != null && bytes.length > 15) {
                try {
                    String strBytesToHex = DeviceIdUtil.bytesToHex(bytes);
                    String strReplace = address.replace(":", "");
                    int iIndexOf = strBytesToHex.indexOf(strReplace);
                    if (iIndexOf < 0) {
                        return false;
                    }
                    int i = iIndexOf - 2;
                    String strSubstring2 = strBytesToHex.substring(iIndexOf - 4, i);
                    String strSubstring3 = strBytesToHex.substring(i, iIndexOf);
                    try {
                        strSubstring = strBytesToHex.substring(strReplace.length() + iIndexOf, iIndexOf + strReplace.length() + 2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    LogUtil.e("isHuabaoDevice-huabaoCompany1=" + strSubstring2 + "-huabaoCompany2=" + strSubstring3 + "isHuabaoDevice-huabao=" + strSubstring + "-mac:" + address + "macName:" + name);
                    if (strSubstring2.equals(BTConstant.COMPANY_ID) && strSubstring3.equals(BTConstant.COMPANY_ID_TWO) && StringUtils.isNotEmpty(strSubstring)) {
                        if (strSubstring.equals("01")) {
                            return true;
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        return false;
    }

    public static String getPhoneBrand() {
        String str = Build.MANUFACTURER;
        return (str == null || str.length() <= 0) ? EnvironmentCompat.MEDIA_UNKNOWN : str.toLowerCase();
    }

    public static boolean isHuaWei() {
        String phoneBrand = getPhoneBrand();
        return phoneBrand.contains("HUAWEI") || phoneBrand.contains("OCE") || phoneBrand.contains("huawei") || phoneBrand.contains("honor");
    }
}
