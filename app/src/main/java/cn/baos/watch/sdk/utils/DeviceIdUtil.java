package cn.baos.watch.sdk.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.os.EnvironmentCompat;
import com.tekartik.sqflite.Constant;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.UUID;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public class DeviceIdUtil {
    public static String getDeviceId(Context context) {
        try {
            if (context.getApplicationInfo().targetSdkVersion > 28 && Build.VERSION.SDK_INT > 28) {
                return getUniqueID(context);
            }
            StringBuilder sb = new StringBuilder();
            String imei = getIMEI(context);
            String androidId = getAndroidId(context);
            String serial = getSerial();
            String deviceUUID = getDeviceUUID();
            if (imei != null && imei.length() > 0) {
                sb.append(imei);
                sb.append("|");
            }
            if (androidId != null && androidId.length() > 0) {
                sb.append(androidId);
                sb.append("|");
            }
            if (serial != null && serial.length() > 0) {
                sb.append(serial);
                sb.append("|");
            }
            if (deviceUUID != null && deviceUUID.length() > 0) {
                sb.append(deviceUUID);
            }
            if (sb.length() <= 0) {
                return null;
            }
            try {
                String strBytesToHex = bytesToHex(getHashByString(sb.toString()));
                if (strBytesToHex == null) {
                    return null;
                }
                if (strBytesToHex.length() > 0) {
                    return strBytesToHex;
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String uuid() {
        UUID uuidRandomUUID = UUID.randomUUID();
        String string = uuidRandomUUID.toString();
        Log.d(Constant.METHOD_DEBUG, "----->UUID" + uuidRandomUUID);
        return string;
    }

    public static String bytesToHex(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & UByte.MAX_VALUE);
            if (hexString.length() == 1) {
                sb.append("0");
            }
            sb.append(hexString);
        }
        return sb.toString().toUpperCase(Locale.CHINA);
    }

    public static String byteToHex(byte b) {
        StringBuilder sb = new StringBuilder();
        String hexString = Integer.toHexString(b & UByte.MAX_VALUE);
        if (hexString.length() == 1) {
            sb.append("0");
        }
        sb.append(hexString);
        return sb.toString().toUpperCase(Locale.CHINA);
    }

    private static byte[] getHashByString(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
            return messageDigest.digest();
        } catch (Exception unused) {
            return "".getBytes();
        }
    }

    private static String getDeviceUUID() {
        return new UUID(("9527" + Build.ID + Build.DEVICE + Build.BOARD + Build.BRAND + Build.HARDWARE + Build.PRODUCT + Build.MODEL + Build.SERIAL).hashCode(), Build.SERIAL.hashCode()).toString().replace("-", "");
    }

    private static String getSerial() {
        try {
            return Build.getSerial();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getAndroidId(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getIMEI(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getTelId(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
    }

    public static String getUniqueID(Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
        String string2 = (TextUtils.isEmpty(string) || "9774d56d682e549c".equals(string)) ? null : UUID.nameUUIDFromBytes(string.getBytes(StandardCharsets.UTF_8)).toString();
        if (TextUtils.isEmpty(string2)) {
            string2 = getUUID();
        }
        return TextUtils.isEmpty(string2) ? UUID.randomUUID().toString() : string2;
    }

    private static String getUUID() {
        String str;
        String str2 = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + ((Build.SUPPORTED_ABIS != null ? Build.SUPPORTED_ABIS.length : 0) % 10) + (Build.DEVICE.length() % 10) + (Build.DISPLAY.length() % 10) + (Build.HOST.length() % 10) + (Build.ID.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10) + (Build.TAGS.length() % 10) + (Build.TYPE.length() % 10) + (Build.USER.length() % 10);
        if (Build.VERSION.SDK_INT <= 28) {
            try {
                return new UUID(str2.hashCode(), Build.getSerial().hashCode()).toString();
            } catch (Exception unused) {
                str = "serial" + UUID.randomUUID().toString();
            }
        } else {
            str = EnvironmentCompat.MEDIA_UNKNOWN + UUID.randomUUID().toString();
        }
        return new UUID(str2.hashCode(), str.hashCode()).toString();
    }

    public static byte hexToByte(String str) {
        return (byte) Integer.parseInt(str, 16);
    }
}
