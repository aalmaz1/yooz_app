package cn.baos.watch.sdk.base;

import android.content.Context;
import android.text.TextUtils;
import cn.baos.watch.sdk.constant.AccountConstant;
import cn.baos.watch.sdk.constant.Constant;
import cn.baos.watch.sdk.interfac.ble.ConnectConfig;
import cn.baos.watch.sdk.interfac.ble.HbBtClientManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.SharePreferenceUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class AppDataConfig {
    public static AppDataConfig instance;
    private String aMapTemid;
    private String aMapUuid;
    private boolean btBondStatus;
    private boolean btDialog;
    private boolean btDialogTwo;
    private String btMac;
    private String connectingMac;
    private boolean deviceLock;
    private String deviceResource;
    private boolean findPhone;
    private String localLanguage;
    public Context mContext;
    private boolean mtuSetting;
    private boolean musicSwatch;
    private int pairCode;
    private String phoneDeviceId;
    private String phoneUserId;
    private boolean reloadStatus;
    private String stepSum;
    private String unHandleDeviceList;
    private String watchListJson;
    private boolean watchLuangh;

    enum DataConfigEnum {
        KEY_CONNECT_CONFIG
    }

    public static AppDataConfig getInstance() {
        if (instance == null) {
            synchronized (AppDataConfig.class) {
                if (instance == null) {
                    instance = new AppDataConfig();
                }
            }
        }
        return instance;
    }

    public void initData(Context context) {
        if (this.mContext != null) {
            return;
        }
        this.mContext = context;
        this.btMac = SharePreferenceUtils.queryStringByKey(context, SharePreferenceUtils.KEY_CONNECT_BT_MAC, "");
        this.pairCode = SharePreferenceUtils.queryIntByKey(context, SharePreferenceUtils.KEY_PAIR_CODE, 20);
        this.reloadStatus = SharePreferenceUtils.queryBooleanByKeySetBoolean(context, SharePreferenceUtils.KEY_RELOAD_CLOSE, false);
        this.connectingMac = SharePreferenceUtils.queryStringByKey(context, SharePreferenceUtils.KEY_CONNECT_MAC, "");
        this.localLanguage = SharePreferenceUtils.queryStringByKey(context, SharePreferenceUtils.KEY_LOCAL_Language, "");
        this.stepSum = SharePreferenceUtils.queryStringByKey(context, SharePreferenceUtils.KEY_STEP_SUM, "");
        this.btDialog = SharePreferenceUtils.queryBooleanByKeySetBoolean(context, SharePreferenceUtils.KEY_BOND_BT_DIALOG, false);
        this.btBondStatus = SharePreferenceUtils.queryBooleanByKeySetBoolean(context, SharePreferenceUtils.KEY_BOND_BT_SUCCESS, false);
        this.btDialogTwo = SharePreferenceUtils.queryBooleanByKeySetBoolean(context, SharePreferenceUtils.KEY_BOND_BT_DIALOG_TWO, true);
        this.aMapUuid = SharePreferenceUtils.queryStringByKey(context, SharePreferenceUtils.KEY_AMAP_UUID, "");
        this.aMapTemid = SharePreferenceUtils.queryStringByKey(context, SharePreferenceUtils.KEY_AMAP_TERMINALID, "");
        this.mtuSetting = SharePreferenceUtils.queryBooleanByKeySetBoolean(context, SharePreferenceUtils.KEY_MTU_SETTING, true);
        this.phoneUserId = SharePreferenceUtils.queryStringByKey(context, SharePreferenceUtils.KEY_PHONE_TYPE_USERID, "");
        this.phoneDeviceId = SharePreferenceUtils.queryStringByKey(context, SharePreferenceUtils.KEY_PHONE_TYPE_DEVICEID, "");
        this.deviceResource = SharePreferenceUtils.queryStringByKey(context, SharePreferenceUtils.KEY_DEVICE_RESOURCE_LANGUAGE, "");
        this.unHandleDeviceList = SharePreferenceUtils.queryStringByKey(context, AccountConstant.KEY_WATCH_DIS_HANDLE_MAC_ADDRESS, "");
        this.deviceLock = SharePreferenceUtils.queryBooleanByKeySetBoolean(context, Constant.SUOPING, true);
        this.findPhone = SharePreferenceUtils.queryBooleanByKeySetBoolean(context, cn.baos.watch.sdk.entitiy.Constant.KEY_WATCH_FIND_PHONE_SWITCH, true);
        this.musicSwatch = SharePreferenceUtils.queryBooleanByKeySetBoolean(context, cn.baos.watch.sdk.entitiy.Constant.KEY_WATCH_MUSIC_SWITCH, false);
        this.watchLuangh = SharePreferenceUtils.queryBooleanByKeySetBoolean(context, SharePreferenceUtils.KEY_WATCH_LUANGH, false);
    }

    public void put(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (SharePreferenceUtils.KEY_CONNECT_BT_MAC.equals(str)) {
            this.btMac = str2;
        } else if (SharePreferenceUtils.KEY_LOCAL_Language.equals(str)) {
            this.localLanguage = str2;
        } else if (SharePreferenceUtils.KEY_STEP_SUM.equals(str)) {
            this.stepSum = str2;
        } else if (SharePreferenceUtils.KEY_CONNECT_MAC.equals(str)) {
            this.connectingMac = str2;
        } else if (SharePreferenceUtils.KEY_AMAP_UUID.equals(str)) {
            this.aMapUuid = str2;
        } else if (SharePreferenceUtils.KEY_AMAP_TERMINALID.equals(str)) {
            this.aMapTemid = str2;
        } else if (SharePreferenceUtils.KEY_PHONE_TYPE_USERID.equals(str)) {
            this.phoneUserId = str2;
        } else if (SharePreferenceUtils.KEY_PHONE_TYPE_DEVICEID.equals(str)) {
            this.phoneDeviceId = str2;
        } else if (SharePreferenceUtils.KEY_DEVICE_RESOURCE_LANGUAGE.equals(str)) {
            this.deviceResource = str2;
        } else if (AccountConstant.KEY_WATCH_DIS_HANDLE_MAC_ADDRESS.equals(str)) {
            this.unHandleDeviceList = str2;
        }
        SharePreferenceUtils.saveStringByKey(this.mContext, str, str2);
    }

    public boolean saveCurrentConfig(ConnectConfig connectConfig) {
        String strQueryStringByKey = SharePreferenceUtils.queryStringByKey(this.mContext, String.valueOf(DataConfigEnum.KEY_CONNECT_CONFIG));
        LogUtil.d("--loadConnectConfig--app--data-l-loadConnectConfig-" + strQueryStringByKey);
        Gson gson = new Gson();
        Type type = new TypeToken<LinkedList<ConnectConfig>>() { // from class: cn.baos.watch.sdk.base.AppDataConfig.1
        }.getType();
        new LinkedList();
        List linkedList = (List) gson.fromJson(strQueryStringByKey, type);
        if (linkedList != null && linkedList.size() > 0 && connectConfig != null) {
            int i = 0;
            while (i < linkedList.size()) {
                if (((ConnectConfig) linkedList.get(i)).macAddress.equals(connectConfig.macAddress)) {
                    linkedList.remove(i);
                    i--;
                }
                i++;
            }
        } else {
            linkedList = new LinkedList();
        }
        linkedList.add(connectConfig);
        String json = gson.toJson(linkedList);
        SharePreferenceUtils.saveStringByKey(this.mContext, String.valueOf(DataConfigEnum.KEY_CONNECT_CONFIG), json);
        LogUtil.d("--saveCurrentConfig--app--data-l-" + new Gson().toJson(json));
        return true;
    }

    public boolean deleteCurrentConfig(ConnectConfig connectConfig) {
        String strQueryStringByKey = SharePreferenceUtils.queryStringByKey(this.mContext, String.valueOf(DataConfigEnum.KEY_CONNECT_CONFIG));
        LogUtil.d("--loadConnectConfig--app--deleteCurrentConfig-" + strQueryStringByKey);
        LogUtil.d("--loadConnectConfig--app--deleteCurrentConfig-delete->" + new Gson().toJson(connectConfig));
        List<ConnectConfig> list = (List) new Gson().fromJson(strQueryStringByKey, new TypeToken<LinkedList<ConnectConfig>>() { // from class: cn.baos.watch.sdk.base.AppDataConfig.2
        }.getType());
        ArrayList arrayList = new ArrayList();
        if (list != null && list.size() > 0 && connectConfig != null) {
            for (ConnectConfig connectConfig2 : list) {
                if (StringUtils.isNotEmpty(connectConfig2.macAddress) && StringUtils.isNotEmpty(connectConfig.macAddress) && !connectConfig2.macAddress.equals(connectConfig.macAddress)) {
                    arrayList.add(connectConfig2);
                }
            }
        }
        LogUtil.d("--loadConnectConfig--app--deleteCurrentConfig-delete-end>" + new Gson().toJson(arrayList));
        SharePreferenceUtils.saveStringByKey(this.mContext, String.valueOf(DataConfigEnum.KEY_CONNECT_CONFIG), new Gson().toJson(arrayList));
        return true;
    }

    public boolean updateCurrentConfig(String str, boolean z) {
        String strQueryStringByKey = SharePreferenceUtils.queryStringByKey(this.mContext, String.valueOf(DataConfigEnum.KEY_CONNECT_CONFIG));
        LogUtil.d("--loadConnectConfig--app--data-l -update-" + strQueryStringByKey);
        List<ConnectConfig> list = (List) new Gson().fromJson(strQueryStringByKey, new TypeToken<LinkedList<ConnectConfig>>() { // from class: cn.baos.watch.sdk.base.AppDataConfig.3
        }.getType());
        if (list != null && list.size() > 0) {
            for (ConnectConfig connectConfig : list) {
                connectConfig.isActive = false;
                if (StringUtils.isNotEmpty(connectConfig.macAddress) && StringUtils.isNotEmpty(str) && str.equals(connectConfig.macAddress)) {
                    connectConfig.isActive = z;
                }
            }
        }
        SharePreferenceUtils.saveStringByKey(this.mContext, String.valueOf(DataConfigEnum.KEY_CONNECT_CONFIG), new Gson().toJson(list));
        LogUtil.d("--saveCurrentConfig--app--data-l-" + new Gson().toJson(list));
        return true;
    }

    public boolean deleteCurrentConfig() {
        SharePreferenceUtils.saveStringByKey(this.mContext, String.valueOf(DataConfigEnum.KEY_CONNECT_CONFIG), "");
        return true;
    }

    public boolean isBindLast(String str) {
        List<ConnectConfig> list;
        String strQueryStringByKey = SharePreferenceUtils.queryStringByKey(this.mContext, String.valueOf(DataConfigEnum.KEY_CONNECT_CONFIG));
        LogUtil.d("--loadConnectConfig--app--data-l-isBindLast-" + strQueryStringByKey);
        if (!StringUtils.isBlank(strQueryStringByKey) && !StringUtils.isBlank(str) && (list = (List) new Gson().fromJson(strQueryStringByKey, new TypeToken<LinkedList<ConnectConfig>>() { // from class: cn.baos.watch.sdk.base.AppDataConfig.4
        }.getType())) != null && list.size() > 0) {
            for (ConnectConfig connectConfig : list) {
                if (connectConfig != null && connectConfig.macAddress.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<ConnectConfig> getAllListConfig() {
        String strQueryStringByKey = SharePreferenceUtils.queryStringByKey(this.mContext, String.valueOf(DataConfigEnum.KEY_CONNECT_CONFIG));
        LogUtil.d("--loadConnectConfig--app--data-l-getAllListConfig-" + strQueryStringByKey);
        List<ConnectConfig> list = (List) new Gson().fromJson(strQueryStringByKey, new TypeToken<LinkedList<ConnectConfig>>() { // from class: cn.baos.watch.sdk.base.AppDataConfig.5
        }.getType());
        if (list != null && list.size() > 0) {
            for (ConnectConfig connectConfig : list) {
                connectConfig.deviceAddress = connectConfig.macAddress;
            }
        }
        LogUtil.d("--loadConnectConfig--app--data-l-getAllListConfig-configList---" + new Gson().toJson(list));
        return list;
    }

    public String getAllConfig() {
        String strQueryStringByKey = SharePreferenceUtils.queryStringByKey(this.mContext, String.valueOf(DataConfigEnum.KEY_CONNECT_CONFIG));
        LogUtil.d("--loadConnectConfig--app--data-l-getAllConfig-" + strQueryStringByKey);
        List<ConnectConfig> list = (List) new Gson().fromJson(strQueryStringByKey, new TypeToken<LinkedList<ConnectConfig>>() { // from class: cn.baos.watch.sdk.base.AppDataConfig.6
        }.getType());
        if (list != null && list.size() > 0) {
            for (ConnectConfig connectConfig : list) {
                connectConfig.deviceAddress = connectConfig.macAddress;
            }
        }
        return new Gson().toJson(list);
    }

    public ConnectConfig loadConnectConfig() {
        String strQueryStringByKey = SharePreferenceUtils.queryStringByKey(this.mContext, String.valueOf(DataConfigEnum.KEY_CONNECT_CONFIG));
        LogUtil.d("--loadConnectConfig--app--data-l-loadConnectConfig-" + strQueryStringByKey);
        Gson gson = new Gson();
        Type type = new TypeToken<LinkedList<ConnectConfig>>() { // from class: cn.baos.watch.sdk.base.AppDataConfig.7
        }.getType();
        new LinkedList();
        List<ConnectConfig> list = (List) gson.fromJson(strQueryStringByKey, type);
        ConnectConfig connectConfig = null;
        if (list != null && list.size() > 0) {
            Iterator it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ConnectConfig connectConfig2 = (ConnectConfig) it.next();
                if (connectConfig2.isActive) {
                    LogUtil.d("--loadConnectConfig--app--data-l-loadConnectConfig end-" + new Gson().toJson(connectConfig2));
                    connectConfig = connectConfig2;
                    break;
                }
            }
            if (connectConfig != null) {
                return connectConfig;
            }
        }
        if (connectConfig == null && list != null && list.size() > 0) {
            String strQueryStringByKey2 = SharePreferenceUtils.queryStringByKey(this.mContext, SharePreferenceUtils.KEY_LAST_MAC, "");
            if (list.size() == 1) {
                connectConfig = (ConnectConfig) list.get(0);
                connectConfig.isActive = true;
            } else {
                list.size();
                for (ConnectConfig connectConfig3 : list) {
                    if (connectConfig3 != null) {
                        connectConfig3.isActive = false;
                        if (StringUtils.isNotEmpty(connectConfig3.macAddress) && StringUtils.isNotEmpty(strQueryStringByKey2) && strQueryStringByKey2.equals(connectConfig3.macAddress)) {
                            connectConfig3.isActive = true;
                            connectConfig = connectConfig3;
                        }
                    }
                }
            }
            if (connectConfig == null) {
                connectConfig = (ConnectConfig) list.get(0);
                connectConfig.isActive = true;
            }
            SharePreferenceUtils.saveStringByKey(this.mContext, String.valueOf(DataConfigEnum.KEY_CONNECT_CONFIG), new Gson().toJson(list));
        }
        return connectConfig;
    }

    public void put(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (SharePreferenceUtils.KEY_RELOAD_CLOSE.equals(str)) {
            this.reloadStatus = z;
        } else if (SharePreferenceUtils.KEY_BOND_BT_DIALOG.equals(str)) {
            this.btDialog = z;
        } else if (SharePreferenceUtils.KEY_BOND_BT_SUCCESS.equals(str)) {
            this.btBondStatus = z;
        } else if (SharePreferenceUtils.KEY_BOND_BT_DIALOG_TWO.equals(str)) {
            this.btDialogTwo = z;
        } else if (SharePreferenceUtils.KEY_MTU_SETTING.equals(str)) {
            this.mtuSetting = z;
        } else if (Constant.SUOPING.equals(str)) {
            this.deviceLock = z;
        } else if (cn.baos.watch.sdk.entitiy.Constant.KEY_WATCH_FIND_PHONE_SWITCH.equals(str)) {
            this.findPhone = z;
        } else if (cn.baos.watch.sdk.entitiy.Constant.KEY_WATCH_MUSIC_SWITCH.equals(str)) {
            this.musicSwatch = z;
        } else if (SharePreferenceUtils.KEY_WATCH_LUANGH.equals(str)) {
            this.watchLuangh = z;
        }
        SharePreferenceUtils.saveBooleanByKey(this.mContext, str, z);
    }

    public void put(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (SharePreferenceUtils.KEY_PAIR_CODE.equals(str)) {
            this.pairCode = i;
        }
        SharePreferenceUtils.saveIntByKey(this.mContext, str, i);
    }

    public String getBtMac() {
        return this.btMac;
    }

    public boolean isBindWatch() {
        ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
        if (currentConnectConfig != null) {
            return currentConnectConfig.isActive;
        }
        return false;
    }

    public String getWatchListJson() {
        return this.watchListJson;
    }

    public int getPairCode() {
        return this.pairCode;
    }

    public String getLocalLanguage() {
        return this.localLanguage;
    }

    public boolean isReloadStatus() {
        return this.reloadStatus;
    }

    public String getStepSum() {
        return this.stepSum;
    }

    public String getConnectingMac() {
        return this.connectingMac;
    }

    public boolean isBtDialog() {
        return this.btDialog;
    }

    public boolean isBtBondStatus() {
        return this.btBondStatus;
    }

    public boolean isBtDialogTwo() {
        return this.btDialogTwo;
    }

    public String getaMapUuid() {
        return this.aMapUuid;
    }

    public String getaMapTemid() {
        return this.aMapTemid;
    }

    public boolean isMtuSetting() {
        return this.mtuSetting;
    }

    public String getPhoneUserId() {
        return this.phoneUserId;
    }

    public String getPhoneDeviceId() {
        return this.phoneDeviceId;
    }

    public String getDeviceResource() {
        return this.deviceResource;
    }

    public String getUnHandleDeviceList() {
        return this.unHandleDeviceList;
    }

    public boolean isDeviceLock() {
        return this.deviceLock;
    }

    public boolean isFindPhone() {
        return this.findPhone;
    }

    public boolean isMusicSwatch() {
        return this.musicSwatch;
    }

    public boolean isWatchLuangh() {
        return this.watchLuangh;
    }
}
