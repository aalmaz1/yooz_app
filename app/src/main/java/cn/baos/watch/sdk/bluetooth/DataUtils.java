package cn.baos.watch.sdk.bluetooth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.text.format.DateFormat;
import androidx.core.content.FileProvider;
import cn.baos.message.CatagoryEnum;
import cn.baos.watch.sdk.base.AppDataConfig;
import cn.baos.watch.sdk.entitiy.DILanguageEntity;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.w100.messages.Action_sync;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DataUtils {
    /* JADX WARN: Removed duplicated region for block: B:10:0x0028 A[Catch: Exception -> 0x0037, TRY_LEAVE, TryCatch #1 {Exception -> 0x0037, blocks: (B:8:0x0022, B:10:0x0028), top: B:31:0x0022 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getPhoneType(android.content.Context r5) {
        /*
            java.lang.String r0 = ""
            java.lang.String r1 = cn.baos.watch.sdk.util.W100Utils.getLocalVersionName(r5)     // Catch: java.lang.Exception -> L57
            cn.baos.watch.sdk.base.AppDataConfig r2 = cn.baos.watch.sdk.base.AppDataConfig.getInstance()     // Catch: java.lang.Exception -> L52
            java.lang.String r2 = r2.getPhoneUserId()     // Catch: java.lang.Exception -> L52
            cn.baos.watch.sdk.base.AppDataConfig r3 = cn.baos.watch.sdk.base.AppDataConfig.getInstance()     // Catch: java.lang.Exception -> L52
            java.lang.String r3 = r3.getPhoneDeviceId()     // Catch: java.lang.Exception -> L52
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch: java.lang.Exception -> L52
            if (r4 == 0) goto L3a
            java.lang.String r5 = cn.baos.watch.sdk.util.DeviceIdUtil.getDeviceId(r5)     // Catch: java.lang.Exception -> L52
            if (r5 == 0) goto L28
            boolean r3 = android.text.TextUtils.isEmpty(r5)     // Catch: java.lang.Exception -> L37
            if (r3 == 0) goto L2c
        L28:
            java.lang.String r5 = cn.baos.watch.sdk.util.DeviceIdUtil.uuid()     // Catch: java.lang.Exception -> L37
        L2c:
            r3 = r5
            cn.baos.watch.sdk.base.AppDataConfig r5 = cn.baos.watch.sdk.base.AppDataConfig.getInstance()     // Catch: java.lang.Exception -> L4f
            java.lang.String r4 = cn.baos.watch.sdk.util.SharePreferenceUtils.KEY_PHONE_TYPE_DEVICEID     // Catch: java.lang.Exception -> L4f
            r5.put(r4, r3)     // Catch: java.lang.Exception -> L4f
            goto L3a
        L37:
            r2 = move-exception
            r3 = r5
            goto L50
        L3a:
            boolean r5 = android.text.TextUtils.isEmpty(r2)     // Catch: java.lang.Exception -> L4f
            if (r5 == 0) goto L5f
            java.lang.String r0 = cn.baos.watch.sdk.util.DeviceIdUtil.uuid()     // Catch: java.lang.Exception -> L4f
            cn.baos.watch.sdk.base.AppDataConfig r5 = cn.baos.watch.sdk.base.AppDataConfig.getInstance()     // Catch: java.lang.Exception -> L4f
            java.lang.String r2 = cn.baos.watch.sdk.util.SharePreferenceUtils.KEY_PHONE_TYPE_USERID     // Catch: java.lang.Exception -> L4f
            r5.put(r2, r0)     // Catch: java.lang.Exception -> L4f
            r2 = r0
            goto L5f
        L4f:
            r2 = move-exception
        L50:
            r5 = r0
            goto L55
        L52:
            r2 = move-exception
            r5 = r0
            r3 = r5
        L55:
            r0 = r1
            goto L5a
        L57:
            r2 = move-exception
            r5 = r0
            r3 = r5
        L5a:
            r2.printStackTrace()
            r2 = r5
            r1 = r0
        L5f:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r0 = "userid--"
            r5.<init>(r0)
            java.lang.StringBuilder r5 = r5.append(r2)
            java.lang.String r5 = r5.toString()
            cn.baos.watch.sdk.util.LogUtil.e(r5)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r0 = "deviceId--"
            r5.<init>(r0)
            java.lang.StringBuilder r5 = r5.append(r3)
            java.lang.String r5 = r5.toString()
            cn.baos.watch.sdk.util.LogUtil.e(r5)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r0 = " 调用方法:getUserInfoModel app当前版本号:"
            r5.<init>(r0)
            java.lang.StringBuilder r5 = r5.append(r1)
            java.lang.String r0 = " 手机IMEI号:"
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.StringBuilder r5 = r5.append(r3)
            java.lang.String r5 = r5.toString()
            cn.baos.watch.sdk.util.LogUtil.d(r5)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.StringBuilder r5 = r5.append(r2)
            java.lang.String r0 = "+"
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.StringBuilder r5 = r5.append(r3)
            java.lang.String r0 = ",,1,,"
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.StringBuilder r5 = r5.append(r1)
            java.lang.String r5 = r5.toString()
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.baos.watch.sdk.bluetooth.DataUtils.getPhoneType(android.content.Context):java.lang.String");
    }

    public static void setTimeFormat(Context context) {
        try {
            setTimeFormat(DateFormat.is24HourFormat(context) ? 1 : 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean setTimeFormat(int i) {
        Action_sync action_sync = new Action_sync();
        action_sync.action_type = 9;
        if (i == 0) {
            action_sync.action_param = 12;
        } else {
            action_sync.action_param = 24;
        }
        return MessageManager.getInstance().sendMessage(action_sync);
    }

    public static String changeMacAddressToFourNumber(String str) {
        if (str != null && str != "") {
            try {
                if (str.contains(":")) {
                    String[] strArrSplit = str.split(":");
                    String str2 = (Integer.parseInt(strArrSplit[strArrSplit.length - 2] + strArrSplit[strArrSplit.length - 1], 16) % CatagoryEnum.APPSYSTEMNOTIFICATION) + "";
                    if (str2.length() < 4) {
                        str2 = "0" + str2;
                    }
                    return "-" + str2.toString();
                }
                return "-" + str.substring(0, 4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static boolean checkWatchLanguage(Context context, int i) {
        List list;
        boolean z = false;
        try {
            String deviceResource = AppDataConfig.getInstance().getDeviceResource();
            LogUtil.e("----checkWatchLanguage-" + deviceResource);
            if (!TextUtils.isEmpty(deviceResource) && (list = (List) new Gson().fromJson(deviceResource, new TypeToken<List<DILanguageEntity>>() { // from class: cn.baos.watch.sdk.bluetooth.DataUtils.1
            }.getType())) != null && list.size() > 0) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (i == ((DILanguageEntity) it.next()).resourceId) {
                        z = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z;
    }

    public static int getWatchLanguageIndex(Context context, int i) {
        List<DILanguageEntity> list;
        try {
            String deviceResource = AppDataConfig.getInstance().getDeviceResource();
            if (TextUtils.isEmpty(deviceResource) || (list = (List) new Gson().fromJson(deviceResource, new TypeToken<List<DILanguageEntity>>() { // from class: cn.baos.watch.sdk.bluetooth.DataUtils.2
            }.getType())) == null || list.size() <= 0) {
                return 0;
            }
            int i2 = 0;
            for (DILanguageEntity dILanguageEntity : list) {
                if (i == dILanguageEntity.resourceId) {
                    i2 = dILanguageEntity.locationIndex;
                }
            }
            return i2;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void updatePiture(Activity activity, String str) {
        try {
            LogUtil.d("保存图片到相册-" + str);
            activity.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(str))));
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("保存图片到相册-error" + e.getMessage());
        }
    }

    public static void installApk(Activity activity, String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        Uri uriForFile = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", new File(str));
        intent.addFlags(1);
        intent.setDataAndType(uriForFile, "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }
}
