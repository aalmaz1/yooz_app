package cn.baos.watch.sdk.manager.notification.db;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import androidx.core.os.EnvironmentCompat;
import cn.baos.watch.sdk.R;
import cn.baos.watch.sdk.database.notification.DatabaseNotificationHandler;
import cn.baos.watch.sdk.entitiy.Constant;
import cn.baos.watch.sdk.entitiy.NotificationAppListEntity;
import cn.baos.watch.sdk.interfac.ble.ConnectConfig;
import cn.baos.watch.sdk.interfac.ble.HbBtClientManager;
import cn.baos.watch.sdk.manager.locker.LockerManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SharePreferenceUtils;
import cn.baos.watch.sdk.util.W100Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inuker.bluetooth.library.utils.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class NotificationDbManager implements INotificationDbManager {
    private static NotificationDbManager instance;
    private Context mContext;
    private DatabaseNotificationHandler mDatabaseHandler;

    public static NotificationDbManager getInstance(Context context) {
        if (instance == null) {
            synchronized (NotificationDbManager.class) {
                if (instance == null) {
                    instance = new NotificationDbManager(context);
                }
            }
        }
        return instance;
    }

    private NotificationDbManager(Context context) {
        this.mContext = context;
        if (this.mDatabaseHandler == null) {
            DatabaseNotificationHandler databaseNotificationHandler = new DatabaseNotificationHandler(context);
            this.mDatabaseHandler = databaseNotificationHandler;
            databaseNotificationHandler.createDatabase();
            initNotificationDb();
        }
    }

    public boolean isSystemApp(String str) {
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 64);
            PackageInfo packageInfo2 = packageManager.getPackageInfo("android", 64);
            if (packageInfo == null || packageInfo.signatures == null) {
                return false;
            }
            return packageInfo2.signatures[0].equals(packageInfo.signatures[0]);
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    @Override // cn.baos.watch.sdk.manager.notification.db.INotificationDbManager
    public void initNotificationDb() {
        initDefaultAppList();
        getAllNotification();
    }

    private void initDefaultAppList() {
        if (!queryCheckStateLightDb(Constant.NOTIFICATION_INIT_FINISH_KEY)) {
            LogUtil.d("NOTIFICATION DB INIT START");
            for (NotificationAppListEntity notificationAppListEntity : getAppNotificationListDefaultEntities()) {
                if (!hasNotification(notificationAppListEntity)) {
                    LogUtil.d("int notification db add:" + notificationAppListEntity.toString());
                    insertNotification(notificationAppListEntity);
                } else {
                    LogUtil.d("int notification db add but has already:" + notificationAppListEntity.toString());
                }
            }
            saveCheckStateLightDb(Constant.NOTIFICATION_INIT_FINISH_KEY, true);
            return;
        }
        String appName = W100Utils.getAppName(this.mContext, "com.android.incallui");
        NotificationAppListEntity notificationAppListEntity2 = new NotificationAppListEntity("com.android.incallui", appName);
        if (queryNotification(notificationAppListEntity2) == null) {
            LogUtil.d("phone notificationAppListEntity 丢失重新插入:" + W100Utils.toString(notificationAppListEntity2));
            if (appName.equals(EnvironmentCompat.MEDIA_UNKNOWN)) {
                notificationAppListEntity2.setAppName("Phone");
            }
            insertNotification(notificationAppListEntity2);
        }
        LogUtil.d("NOTIFICATION DB HAS BEEN INITED");
    }

    private void updateAppName(ArrayList<NotificationAppListEntity> arrayList) {
        for (NotificationAppListEntity notificationAppListEntity : arrayList) {
            if (!notificationAppListEntity.getAppPackageName().contains("com.android.incallui")) {
                String appName = W100Utils.getAppName(this.mContext, notificationAppListEntity.getAppPackageName());
                LogUtil.d("get app new name:" + appName);
                if (appName.equals(EnvironmentCompat.MEDIA_UNKNOWN)) {
                    deleteNotification(notificationAppListEntity);
                } else if (!notificationAppListEntity.getAppName().equals(appName)) {
                    LogUtil.d("update name:" + appName);
                    updateNotification(notificationAppListEntity.setAppName(appName));
                }
            } else {
                String appName2 = W100Utils.getAppName(this.mContext, notificationAppListEntity.getAppPackageName());
                LogUtil.d("get app new name:" + appName2 + " 当前为来电包名");
                if (!appName2.equals(EnvironmentCompat.MEDIA_UNKNOWN) && !notificationAppListEntity.getAppName().equals(appName2)) {
                    LogUtil.d("update name:" + appName2);
                    updateNotification(notificationAppListEntity.setAppName(appName2));
                }
            }
        }
    }

    @Override // cn.baos.watch.sdk.manager.notification.db.INotificationDbManager
    public void insertNotification(NotificationAppListEntity notificationAppListEntity) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            LogUtil.d("insertNotification:" + notificationAppListEntity.toString());
            this.mDatabaseHandler.open();
            this.mDatabaseHandler.insert(notificationAppListEntity);
            this.mDatabaseHandler.close();
        }
    }

    @Override // cn.baos.watch.sdk.manager.notification.db.INotificationDbManager
    public void updateNotification(NotificationAppListEntity notificationAppListEntity) {
        ArrayList<NotificationAppListEntity> allNotification;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            this.mDatabaseHandler.open();
            this.mDatabaseHandler.update(notificationAppListEntity);
            this.mDatabaseHandler.close();
        }
        ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
        if (currentConnectConfig != null) {
            String str = currentConnectConfig.macAddress;
            if (StringUtils.isNotBlank(str)) {
                String strQueryStringByKey = SharePreferenceUtils.queryStringByKey(this.mContext, "notification-" + str);
                if (StringUtils.isNotBlank(strQueryStringByKey)) {
                    allNotification = (ArrayList) new Gson().fromJson(strQueryStringByKey, new TypeToken<ArrayList<NotificationAppListEntity>>() { // from class: cn.baos.watch.sdk.manager.notification.db.NotificationDbManager.1
                    }.getType());
                } else {
                    allNotification = getInstance(this.mContext).getAllNotification();
                }
                if (allNotification == null || allNotification.size() <= 0) {
                    return;
                }
                for (NotificationAppListEntity notificationAppListEntity2 : allNotification) {
                    if (notificationAppListEntity2.getAppPackageName().equals(notificationAppListEntity.getAppPackageName())) {
                        notificationAppListEntity2.setChecked(notificationAppListEntity.isChecked());
                        notificationAppListEntity2.setEnabled(notificationAppListEntity.isEnabled());
                    }
                }
                SharePreferenceUtils.saveStringByKey(this.mContext, "notification-" + str, new Gson().toJson(allNotification));
            }
        }
    }

    @Override // cn.baos.watch.sdk.manager.notification.db.INotificationDbManager
    public void updateNotifications(ArrayList<NotificationAppListEntity> arrayList, boolean z) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            this.mDatabaseHandler.open();
            for (NotificationAppListEntity notificationAppListEntity : arrayList) {
                notificationAppListEntity.setChecked(z);
                this.mDatabaseHandler.update(notificationAppListEntity);
            }
            this.mDatabaseHandler.close();
        }
    }

    public void updateAllNotifications(boolean z) {
        ArrayList<NotificationAppListEntity> allNotification = getAllNotification();
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            this.mDatabaseHandler.open();
            for (NotificationAppListEntity notificationAppListEntity : allNotification) {
                notificationAppListEntity.setChecked(z);
                this.mDatabaseHandler.update(notificationAppListEntity);
            }
            this.mDatabaseHandler.close();
        }
    }

    @Override // cn.baos.watch.sdk.manager.notification.db.INotificationDbManager
    public void deleteNotification(NotificationAppListEntity notificationAppListEntity) {
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            this.mDatabaseHandler.open();
            this.mDatabaseHandler.delete(notificationAppListEntity);
            this.mDatabaseHandler.close();
        }
    }

    @Override // cn.baos.watch.sdk.manager.notification.db.INotificationDbManager
    public NotificationAppListEntity queryNotification(NotificationAppListEntity notificationAppListEntity) {
        NotificationAppListEntity notificationAppListEntityQuery;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            this.mDatabaseHandler.open();
            notificationAppListEntityQuery = this.mDatabaseHandler.query(notificationAppListEntity);
            this.mDatabaseHandler.close();
        }
        return notificationAppListEntityQuery;
    }

    @Override // cn.baos.watch.sdk.manager.notification.db.INotificationDbManager
    public boolean hasNotification(NotificationAppListEntity notificationAppListEntity) {
        boolean zHasNotification;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            this.mDatabaseHandler.open();
            zHasNotification = this.mDatabaseHandler.hasNotification(notificationAppListEntity);
            this.mDatabaseHandler.close();
        }
        return zHasNotification;
    }

    @Override // cn.baos.watch.sdk.manager.notification.db.INotificationDbManager
    public ArrayList<NotificationAppListEntity> getAllNotification() {
        ArrayList<NotificationAppListEntity> allNotificationAppListEntities;
        ArrayList<NotificationAppListEntity> arrayList;
        synchronized (LockerManager.getInstance().getDataBaseLocker()) {
            this.mDatabaseHandler.open();
            allNotificationAppListEntities = this.mDatabaseHandler.getAllNotificationAppListEntities();
            this.mDatabaseHandler.close();
            ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
            if (currentConnectConfig != null) {
                String str = currentConnectConfig.macAddress;
                if (StringUtils.isNotBlank(str)) {
                    String strQueryStringByKey = SharePreferenceUtils.queryStringByKey(this.mContext, "notification-" + str);
                    if (StringUtils.isNotBlank(strQueryStringByKey) && (arrayList = (ArrayList) new Gson().fromJson(strQueryStringByKey, new TypeToken<ArrayList<NotificationAppListEntity>>() { // from class: cn.baos.watch.sdk.manager.notification.db.NotificationDbManager.2
                    }.getType())) != null && arrayList.size() > 0) {
                        allNotificationAppListEntities = arrayList;
                    }
                }
            }
        }
        return allNotificationAppListEntities;
    }

    private ArrayList<NotificationAppListEntity> getAppNotificationListDefaultEntities() {
        ArrayList<NotificationAppListEntity> arrayList = new ArrayList<>();
        ArrayList arrayList2 = new ArrayList(Arrays.asList(this.mContext.getResources().getStringArray(R.array.str_app_name_list_default_check)));
        String[] stringArray = this.mContext.getResources().getStringArray(R.array.str_app_name_list);
        String[] stringArray2 = this.mContext.getResources().getStringArray(R.array.str_app_package_name_list);
        ResolveInfo resolveInfoResolveActivity = this.mContext.getPackageManager().resolveActivity(new Intent("android.intent.action.SENDTO", Uri.parse("smsto:")), 65536);
        String str = (resolveInfoResolveActivity == null || TextUtils.isEmpty(resolveInfoResolveActivity.activityInfo.packageName)) ? Constant.PACKAGE_NAME_SHORT_MESSAGE : resolveInfoResolveActivity.activityInfo.packageName;
        for (int i = 0; i < stringArray.length; i++) {
            String str2 = stringArray2[i];
            if (str2.contains(Constant.PACKAGE_NAME_SHORT_MESSAGE)) {
                str2 = str;
            }
            String str3 = stringArray[i];
            arrayList.add(new NotificationAppListEntity(str2, str3, arrayList2.contains(str3)));
        }
        return arrayList;
    }

    @Override // cn.baos.watch.sdk.manager.notification.db.INotificationDbManager
    public void saveCheckStateLightDb(String str, boolean z) {
        SharedPreferences.Editor editorEdit = this.mContext.getSharedPreferences(Constant.NOTIFICATION_LIGHT_DB_NAME, 0).edit();
        editorEdit.putBoolean(str, z);
        LogUtil.d("saveCheckStateLightDb->" + str + ":" + z);
        editorEdit.commit();
        saveCheck(str, z);
    }

    public void saveCheck(String str, boolean z) {
        try {
            ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
            if (currentConnectConfig != null) {
                String str2 = currentConnectConfig.macAddress;
                if (StringUtils.isNotBlank(str2)) {
                    SharedPreferences.Editor editorEdit = this.mContext.getSharedPreferences(Constant.NOTIFICATION_LIGHT_DB_NAME, 0).edit();
                    editorEdit.putBoolean("manager_" + str2, z);
                    LogUtil.d("saveCheck->" + str + ":" + z);
                    editorEdit.commit();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003c  */
    @Override // cn.baos.watch.sdk.manager.notification.db.INotificationDbManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean queryCheckStateLightDb(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r0 = "manager_"
            android.content.Context r1 = r5.mContext
            java.lang.String r2 = "notificationLiteDb"
            r3 = 0
            android.content.SharedPreferences r1 = r1.getSharedPreferences(r2, r3)
            java.lang.String r2 = "notificationManageKey"
            boolean r2 = r2.equals(r6)
            if (r2 == 0) goto L45
            cn.baos.watch.sdk.interfac.ble.HbBtClientManager r2 = cn.baos.watch.sdk.interfac.ble.HbBtClientManager.getInstance()     // Catch: java.lang.Exception -> L38
            cn.baos.watch.sdk.interfac.ble.ConnectConfig r2 = r2.getCurrentConnectConfig()     // Catch: java.lang.Exception -> L38
            if (r2 == 0) goto L3c
            java.lang.String r2 = r2.macAddress     // Catch: java.lang.Exception -> L38
            boolean r4 = com.inuker.bluetooth.library.utils.StringUtils.isNotBlank(r2)     // Catch: java.lang.Exception -> L38
            if (r4 == 0) goto L3c
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L38
            r4.<init>(r0)     // Catch: java.lang.Exception -> L38
            java.lang.StringBuilder r0 = r4.append(r2)     // Catch: java.lang.Exception -> L38
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Exception -> L38
            boolean r0 = r1.getBoolean(r0, r3)     // Catch: java.lang.Exception -> L38
            r2 = 1
            goto L3e
        L38:
            r0 = move-exception
            r0.printStackTrace()
        L3c:
            r0 = r3
            r2 = r0
        L3e:
            if (r2 != 0) goto L49
            boolean r0 = r1.getBoolean(r6, r3)
            goto L49
        L45:
            boolean r0 = r1.getBoolean(r6, r3)
        L49:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "queryCheckStateLightDb 通知总开关->"
            r1.<init>(r2)
            java.lang.StringBuilder r6 = r1.append(r6)
            java.lang.String r1 = ":"
            java.lang.StringBuilder r6 = r6.append(r1)
            java.lang.StringBuilder r6 = r6.append(r0)
            java.lang.String r6 = r6.toString()
            cn.baos.watch.sdk.util.LogUtil.d(r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.baos.watch.sdk.manager.notification.db.NotificationDbManager.queryCheckStateLightDb(java.lang.String):boolean");
    }

    public boolean querySwitchOnlyNotice() {
        boolean zQueryBooleanByKeySetBoolean = SharePreferenceUtils.queryBooleanByKeySetBoolean(this.mContext, cn.baos.watch.sdk.constant.Constant.SWITCH_ONLY_NOTICE_NOTIFICATION, false);
        try {
            ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
            if (currentConnectConfig == null) {
                return zQueryBooleanByKeySetBoolean;
            }
            String str = currentConnectConfig.macAddress;
            return StringUtils.isNotBlank(str) ? SharePreferenceUtils.queryBooleanByKeySetBoolean(this.mContext, "switch_only" + str, zQueryBooleanByKeySetBoolean) : zQueryBooleanByKeySetBoolean;
        } catch (Exception e) {
            e.printStackTrace();
            return zQueryBooleanByKeySetBoolean;
        }
    }

    public void saveSwitchOnlyNotice(boolean z) {
        SharePreferenceUtils.saveBooleanByKey(this.mContext, cn.baos.watch.sdk.constant.Constant.SWITCH_ONLY_NOTICE_NOTIFICATION, z);
        try {
            ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
            if (currentConnectConfig != null) {
                String str = currentConnectConfig.macAddress;
                if (StringUtils.isNotBlank(str)) {
                    SharePreferenceUtils.saveBooleanByKey(this.mContext, "switch_only" + str, z);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
