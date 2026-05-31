package cn.baos.watch.sdk.bluetooth.utils;

import android.bluetooth.BluetoothAdapter;
import cn.yoozworld.watch.utils.BtConstant;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public class BleScanUtils {
    public static boolean releaseAllScanClient() {
        Object iBluetoothGatt;
        Method declaredMethod;
        boolean z;
        try {
            Object iBluetoothManager = getIBluetoothManager(BluetoothAdapter.getDefaultAdapter());
            if (iBluetoothManager == null || (iBluetoothGatt = getIBluetoothGatt(iBluetoothManager)) == null) {
                return false;
            }
            Method declaredMethod2 = getDeclaredMethod(iBluetoothGatt, "unregisterClient", (Class<?>[]) new Class[]{Integer.TYPE});
            try {
                declaredMethod = getDeclaredMethod(iBluetoothGatt, BtConstant.stopScan, (Class<?>[]) new Class[]{Integer.TYPE, Boolean.TYPE});
                z = false;
            } catch (Exception unused) {
                declaredMethod = getDeclaredMethod(iBluetoothGatt, BtConstant.stopScan, (Class<?>[]) new Class[]{Integer.TYPE});
                z = true;
            }
            for (int i = 0; i <= 40; i++) {
                if (!z) {
                    try {
                        declaredMethod.invoke(iBluetoothGatt, Integer.valueOf(i), false);
                    } catch (Exception unused2) {
                    }
                }
                if (z) {
                    try {
                        declaredMethod.invoke(iBluetoothGatt, Integer.valueOf(i));
                    } catch (Exception unused3) {
                    }
                }
                try {
                    declaredMethod2.invoke(iBluetoothGatt, Integer.valueOf(i));
                } catch (Exception unused4) {
                }
            }
            declaredMethod.setAccessible(false);
            declaredMethod2.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Object getIBluetoothGatt(Object obj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method declaredMethod = getDeclaredMethod(obj, "getBluetoothGatt", (Class<?>[]) new Class[0]);
        Object obj2 = new Object();
        try {
            return declaredMethod.invoke(obj, new Object[0]);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return obj2;
        }
    }

    public static Object getIBluetoothManager(BluetoothAdapter bluetoothAdapter) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return getDeclaredMethod((Class<?>) BluetoothAdapter.class, "getBluetoothManager", (Class<?>[]) new Class[0]).invoke(bluetoothAdapter, new Object[0]);
    }

    public static Field getDeclaredField(Class<?> cls, String str) throws NoSuchFieldException {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField;
    }

    public static Method getDeclaredMethod(Class<?> cls, String str, Class<?>... clsArr) throws NoSuchMethodException {
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }

    public static Field getDeclaredField(Object obj, String str) throws NoSuchFieldException {
        Field declaredField = obj.getClass().getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField;
    }

    public static Method getDeclaredMethod(Object obj, String str, Class<?>... clsArr) throws NoSuchMethodException {
        Method declaredMethod = obj.getClass().getDeclaredMethod(str, clsArr);
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }

    private void closeAndroidPDialog() {
        try {
            Class.forName("android.content.pm.PackageParser$Package").getDeclaredConstructor(String.class).setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread", new Class[0]);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(null, new Object[0]);
            Field declaredField = cls.getDeclaredField("mHiddenApiWarningShown");
            declaredField.setAccessible(true);
            declaredField.setBoolean(objInvoke, true);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
