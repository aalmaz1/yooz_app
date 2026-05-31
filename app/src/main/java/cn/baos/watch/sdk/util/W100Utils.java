package cn.baos.watch.sdk.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import android.text.TextUtils;
import androidx.core.os.EnvironmentCompat;
import cn.baos.watch.sdk.entitiy.AppMarekEntity;
import com.google.gson.Gson;
import java.util.List;
import java.util.regex.Pattern;
import kotlin.UByte;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/* JADX INFO: loaded from: classes.dex */
public class W100Utils {
    public static byte[] int2bytes(int i) {
        byte[] bArr = new byte[2];
        if (i < 256) {
            bArr[1] = (byte) i;
        } else {
            bArr[1] = (byte) (i & 255);
            bArr[0] = (byte) (i >> 8);
        }
        return bArr;
    }

    public static AppMarekEntity getAppMark(int i) {
        String strSubstring;
        String strBin10T2Dec = bin10T2Dec(i);
        AppMarekEntity appMarekEntity = new AppMarekEntity();
        if (strBin10T2Dec != null && strBin10T2Dec.length() > 0) {
            int i2 = 0;
            for (int length = strBin10T2Dec.length(); length > 0; length--) {
                if (length == strBin10T2Dec.length()) {
                    strSubstring = strBin10T2Dec.substring(length - 1);
                } else {
                    strSubstring = strBin10T2Dec.substring(length - 1, length);
                }
                int i3 = Integer.parseInt(strSubstring);
                if (i2 == 0) {
                    appMarekEntity.isVideoCtrl = i3;
                }
                if (i2 == 1) {
                    appMarekEntity.isMeteorological = i3;
                }
                if (i2 == 2) {
                    appMarekEntity.isBloodSugar = i3;
                }
                if (i2 == 3) {
                    appMarekEntity.isBodyTemperature = i3;
                }
                if (i2 == 4) {
                    appMarekEntity.isBreathingRate = i3;
                }
                if (i2 == 5) {
                    appMarekEntity.isMyFriends = i3;
                }
                i2++;
                LogUtil.e("app_id_mask----" + strSubstring);
            }
        }
        return appMarekEntity;
    }

    public static String bin10T2Dec(int i) {
        String str = "";
        while (i != 0) {
            str = (i % 2) + str;
            i /= 2;
        }
        return str;
    }

    public static int bin2Dec(String str) {
        if (str == null || str.equals("")) {
            return 0;
        }
        int iCharAt = 0;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt > '2' || cCharAt < '0') {
                throw new NumberFormatException(String.valueOf(i));
            }
            iCharAt = (iCharAt * 2) + (str.charAt(i) - '0');
        }
        return iCharAt;
    }

    public static int getLocalVersion(Context context) {
        int i = 0;
        try {
            i = context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            LogUtil.d("本软件的版本号：" + i);
            return i;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return i;
        }
    }

    public static String getLocalVersionName(Context context) {
        String str = "";
        try {
            str = context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            LogUtil.d("本软件的版本名：" + str);
            return str;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return str;
        }
    }

    public static String toString(Object obj) {
        return obj == null ? "对象为空" : new Gson().toJson(obj);
    }

    public static Activity findActivity(Context context) {
        synchronized (W100Utils.class) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            if (!(context instanceof ContextWrapper)) {
                return null;
            }
            return findActivity(((ContextWrapper) context).getBaseContext());
        }
    }

    public static String toStringFanshe(Object obj) {
        return obj == null ? "对象为空" : ReflectionToStringBuilder.toString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static String getAppName(Context context, String str) {
        ApplicationInfo applicationInfo;
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        try {
            applicationInfo = packageManager.getApplicationInfo(str, 128);
        } catch (PackageManager.NameNotFoundException unused) {
            applicationInfo = null;
        }
        return (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : EnvironmentCompat.MEDIA_UNKNOWN);
    }

    public static String getAppProcessName(Context context) {
        int iMyPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == iMyPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return "";
    }

    public static boolean isForeground(Activity activity) {
        return isForeground(activity, activity.getClass().getName());
    }

    public static boolean isForeground(Context context, String str) {
        List<ActivityManager.RunningTaskInfo> runningTasks;
        return (context == null || TextUtils.isEmpty(str) || (runningTasks = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1)) == null || runningTasks.size() <= 0 || !str.equals(runningTasks.get(0).topActivity.getClassName())) ? false : true;
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder("");
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & UByte.MAX_VALUE);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    public static final String byte2hex(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        stringBuffer.append(" 0x");
        for (byte b : bArr) {
            String upperCase = Integer.toHexString(b & UByte.MAX_VALUE).toUpperCase();
            if (upperCase.length() == 1) {
                stringBuffer.append("0").append(upperCase);
            } else {
                stringBuffer.append(upperCase);
            }
        }
        return stringBuffer.toString();
    }

    public static final String byte2hex(byte[] bArr, int i) {
        if (bArr.length <= i) {
            i = bArr.length;
        }
        if (bArr == null) {
            throw new IllegalArgumentException("Argument b ( byte array ) is null! ");
        }
        String str = "";
        for (int i2 = 0; i2 < i; i2++) {
            String hexString = Integer.toHexString(bArr[i2] & UByte.MAX_VALUE);
            if (hexString.length() == 1) {
                str = str + "0" + hexString;
            } else {
                str = str + hexString;
            }
        }
        return str.toUpperCase();
    }

    public static boolean isNumeric(String str) {
        return Pattern.compile("-?[0-9]+\\.?[0-9]*").matcher(str).matches();
    }

    public static byte hexTobyte(String str) {
        return (byte) Integer.parseInt(str, 16);
    }

    public static byte[] hexTobytes(String str) {
        if (str.length() < 1) {
            return null;
        }
        byte[] bArr = new byte[str.length() / 2];
        int i = 0;
        int i2 = 0;
        while (i < str.length()) {
            int i3 = i + 2;
            bArr[i2] = (byte) Integer.parseInt(str.substring(i, i3), 16);
            i2++;
            i = i3;
        }
        return bArr;
    }
}
