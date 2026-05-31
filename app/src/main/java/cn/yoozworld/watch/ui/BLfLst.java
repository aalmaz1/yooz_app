package cn.yoozworld.watch.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.widget.Toast;
import cn.baos.watch.sdk.code.MainHandler;
import cn.baos.watch.sdk.contact.ContactHelper;
import cn.baos.watch.sdk.database.contacts.ContactsManager;
import cn.baos.watch.sdk.database.gps.GpslocEntity;
import cn.baos.watch.sdk.entitiy.AppMarekEntity;
import cn.baos.watch.sdk.entitiy.Constant;
import cn.baos.watch.sdk.entitiy.PhoneContactsEntity;
import cn.baos.watch.sdk.entitiy.WorldEntity;
import cn.baos.watch.sdk.huabaoImpl.syncdata.dailyactive.DailyActiveManager;
import cn.baos.watch.sdk.huabaoImpl.translate.TranslateCallback;
import cn.baos.watch.sdk.huabaoImpl.translate.TranslateManager;
import cn.baos.watch.sdk.interfac.app.OnRemindListener;
import cn.baos.watch.sdk.interfac.ble.ConnectConfig;
import cn.baos.watch.sdk.interfac.ble.HbBtClientManager;
import cn.baos.watch.sdk.interfac.moslem.OnQrImageListener;
import cn.baos.watch.sdk.interfac.syncdata.SyncRightNowDataCallback;
import cn.baos.watch.sdk.util.AppUtils;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.LogcatThread;
import cn.baos.watch.sdk.util.SharePreferenceUtils;
import cn.baos.watch.sdk.util.ZipShareCallback;
import cn.baos.watch.w100.messages.Common_remind_config;
import cn.baos.watch.w100.messages.Config_items;
import cn.baos.watch.w100.messages.Device_base_info;
import cn.baos.watch.w100.messages.Sensor_data_battery;
import cn.baos.watch.w100.messages.Sensor_data_daily_active_array;
import cn.baos.watch.w100.messages.Sensor_data_daily_active_sum;
import cn.baos.watch.w100.messages.Sensor_data_daily_hrate;
import cn.baos.watch.w100.messages.Sensor_data_daily_spo;
import cn.yoozworld.watch.ui.HbBst;
import cn.yoozworld.watch.utils.BtConstant;
import cn.yoozworld.watch.utils.CigaretteLevelBean;
import cn.yoozworld.watch.utils.TransFileBean;
import cn.yoozworld.watch.utils.WorldUtils;
import com.google.gson.Gson;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.JSONMessageCodec;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BLfLst implements HbBst.BoostLifecycleListener, MethodChannel.MethodCallHandler {
    public static int bleHisStatus = 0;
    private static BLfLst instance = null;
    public static boolean isAutoDisconnect = false;
    public static boolean isBleConnect = true;
    public static boolean isBleReConnect = false;
    public static boolean mPhoneGet = true;
    private BasicMessageChannel jsonMessageChannel;
    private Activity mActivity;
    private Context mContext;
    LogcatThread mLogcatThread;
    private Handler mMainHandler;
    private MethodChannel methodChannel;
    int progressTest = 0;
    private int addNum = 10;
    private boolean hasLogcatThreadOpen = false;
    public int mBindCode = 1000;

    @Override // cn.yoozworld.watch.ui.HbBst.BoostLifecycleListener
    public void beforeCreateEngine() {
    }

    @Override // cn.yoozworld.watch.ui.HbBst.BoostLifecycleListener
    public void onEngineDestroy() {
    }

    public MethodChannel getMethodChannel() {
        return this.methodChannel;
    }

    public void setMethodChannel(MethodChannel methodChannel) {
        this.methodChannel = methodChannel;
    }

    public BasicMessageChannel getJsonMessageChannel() {
        return this.jsonMessageChannel;
    }

    public void setJsonMessageChannel(BasicMessageChannel basicMessageChannel) {
        this.jsonMessageChannel = basicMessageChannel;
    }

    public static BLfLst getInstance() {
        if (instance == null) {
            synchronized (BLfLst.class) {
                if (instance == null) {
                    instance = new BLfLst();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        LogUtil.d("BoostLifecycleListener 进程 " + Process.myPid() + " Thread: " + Process.myTid() + " name " + Thread.currentThread().getName());
        this.mMainHandler = new Handler(Looper.getMainLooper());
    }

    public void setMainActivity(Activity activity) {
        this.mActivity = activity;
    }

    private MethodChannel createMethodChannel(FlutterEngine flutterEngine) {
        LogUtil.e("BoostLifecycleListener createMethodChannel");
        MethodChannel methodChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), "com.baos.sdk/channel");
        this.methodChannel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        LogUtil.e("BoostLifecycleListener setMethodCallHandler");
        BasicMessageChannel basicMessageChannel = new BasicMessageChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), "com.baos.sdk/messages", JSONMessageCodec.INSTANCE);
        this.jsonMessageChannel = basicMessageChannel;
        basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: cn.yoozworld.watch.ui.BLfLst$$ExternalSyntheticLambda22
            @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
            public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                BLfLst.lambda$createMethodChannel$0(obj, reply);
            }
        });
        return this.methodChannel;
    }

    static /* synthetic */ void lambda$createMethodChannel$0(Object obj, BasicMessageChannel.Reply reply) {
        LogUtil.d("og.Received message =" + obj);
        reply.reply("Reply from Android");
    }

    @Override // cn.yoozworld.watch.ui.HbBst.BoostLifecycleListener
    public void onEngineCreated() {
        createMethodChannel(new FlutterEngine(this.mContext));
    }

    public void invokeFlutterMethod(String str, Object obj) {
        try {
            LogUtil.d("安卓调用flutter接口:" + str + ":" + obj);
            if (HomeActivity.getInstance().jsonMessageChannel == null) {
                LogUtil.d("安卓调用flutter接口,通道未初始化methodChannel");
            } else {
                invokeFlutterMethodEventByJSON(str, obj.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void invokeFlutterMethodEventByJSON(String str) {
        LogUtil.d("安卓调用flutter接口:" + str);
        BasicMessageChannel basicMessageChannel = this.jsonMessageChannel;
        if (basicMessageChannel == null) {
            LogUtil.d("安卓调用flutter接口,通道未初始化jsonMessageChannel");
        } else {
            basicMessageChannel.send(str);
        }
    }

    public void invokeFlutterMethodEventByJSON(String str, String str2) {
        LogUtil.d("安卓调用flutter接口:" + str + ":" + str2);
        if (this.jsonMessageChannel == null) {
            LogUtil.d("安卓调用flutter接口,通道未初始化jsonMessageChannel");
            return;
        }
        String str3 = "{\"method\":\"" + str + "\",\"data\":{\"" + str + "\":\"" + str2 + "\"}}";
        LogUtil.d("安卓调用flutter接口," + str3);
        this.jsonMessageChannel.send(str3);
    }

    public void invokeFlutterMethodEventByJSONRes(String str, String str2) {
        LogUtil.d("安卓调用flutter接口:" + str + ":" + str2);
        if (this.jsonMessageChannel == null) {
            LogUtil.d("安卓调用flutter接口,通道未初始化jsonMessageChannel");
            return;
        }
        String str3 = "{\"method\":\"" + str + "\",\"data\":" + str2 + "}";
        LogUtil.d("安卓调用flutter接口," + str3);
        this.jsonMessageChannel.send(str3);
    }

    public void invokeFlutterMethodEventByJSONStr(String str, String str2) {
        LogUtil.d("安卓调用flutter接口:" + str + ":" + str2);
        if (this.jsonMessageChannel == null) {
            LogUtil.d("安卓调用flutter接口,通道未初始化jsonMessageChannel");
            return;
        }
        String str3 = "{\"method\":\"" + str + "\",\"data\":" + str2 + "}";
        LogUtil.d("安卓调用flutter接口," + str3);
        this.jsonMessageChannel.send(str3);
    }

    public void invokeFlutterMethodEventByJSONData(String str, String str2) {
        LogUtil.d("安卓调用flutter接口:" + str + ":" + str2);
        if (this.jsonMessageChannel == null) {
            LogUtil.d("安卓调用flutter接口,通道未初始化jsonMessageChannel");
            return;
        }
        String str3 = "{\"method\":\"" + str + "\",\"data\":" + str2 + "}";
        LogUtil.d("安卓调用flutter接口," + str3);
        this.jsonMessageChannel.send(str3);
    }

    public void invokeFlutterMethodEventByJSON(String str, int i) {
        if (str.equals(BtConstant.bleStatus) && i != 10) {
            bleHisStatus = i;
        }
        LogUtil.d("安卓调用flutter接口:" + str + ":" + i);
        if (this.jsonMessageChannel == null) {
            LogUtil.d("安卓调用flutter接口,通道未初始化jsonMessageChannel");
            return;
        }
        String str2 = "{\"method\":\"" + str + "\",\"data\":{\"" + str + "\":" + i + "}}";
        LogUtil.d("安卓调用flutter接口," + str2);
        this.jsonMessageChannel.send(str2);
    }

    public void invokeFlutterMethodEventByJSON(String str, int i, String str2, String str3) {
        if (str.equals(BtConstant.bleStatus) && i != 10) {
            bleHisStatus = i;
        }
        if (this.jsonMessageChannel == null) {
            LogUtil.d("安卓调用flutter接口,通道未初始化jsonMessageChannel");
            return;
        }
        String str4 = "{\"method\":\"" + str + "\",\"data\":{\"" + str + "\":" + i + "}}";
        LogUtil.d("安卓调用flutter接口," + str4);
        this.jsonMessageChannel.send(str4);
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) throws Throwable {
        LogUtil.d("onMethodCall:" + methodCall.method);
        switchFlutterData(methodCall, result);
    }

    public void transferFile(String str, int i) {
        LogUtil.d("transferFile--->" + str + "  fileType--->" + i);
        TranslateManager.getInstance().transferFile(new File(str), i, new TranslateCallback() { // from class: cn.yoozworld.watch.ui.BLfLst.1
            @Override // cn.baos.watch.sdk.huabaoImpl.translate.TranslateCallback
            public void onLoadFile(int i2) {
            }

            @Override // cn.baos.watch.sdk.huabaoImpl.translate.TranslateCallback
            public void onLoadFileFail() {
                BLfLst.this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        BLfLst.this.invokeFlutterMethodEventByJSONData(BtConstant.translateStatus, new Gson().toJson(new TransFileBean(5, 0)));
                    }
                });
            }

            @Override // cn.baos.watch.sdk.huabaoImpl.translate.TranslateCallback
            public void onWaitWatchStartTranslate() {
                BLfLst.this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst.1.2
                    @Override // java.lang.Runnable
                    public void run() {
                    }
                });
            }

            @Override // cn.baos.watch.sdk.huabaoImpl.translate.TranslateCallback
            public void onTranslateStart() {
                BLfLst.this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst.1.3
                    @Override // java.lang.Runnable
                    public void run() {
                        BLfLst.this.invokeFlutterMethodEventByJSONData(BtConstant.translateStatus, new Gson().toJson(new TransFileBean(4, 0)));
                    }
                });
            }

            @Override // cn.baos.watch.sdk.huabaoImpl.translate.TranslateCallback
            public void onTransferProgress(final int i2) {
                BLfLst.this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst.1.4
                    @Override // java.lang.Runnable
                    public void run() {
                        BLfLst.this.invokeFlutterMethodEventByJSONData(BtConstant.translateStatus, new Gson().toJson(new TransFileBean(4, i2)));
                    }
                });
            }

            @Override // cn.baos.watch.sdk.huabaoImpl.translate.TranslateCallback
            public void onTransferFinish() {
                BLfLst.this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst.1.5
                    @Override // java.lang.Runnable
                    public void run() {
                        BLfLst.this.invokeFlutterMethodEventByJSONData(BtConstant.translateStatus, new Gson().toJson(new TransFileBean(6, 0)));
                    }
                });
            }

            @Override // cn.baos.watch.sdk.huabaoImpl.translate.TranslateCallback
            public void onTransferFail(final int i2) {
                BLfLst.this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst.1.6
                    @Override // java.lang.Runnable
                    public void run() {
                        BLfLst.this.invokeFlutterMethodEventByJSONData(BtConstant.translateStatus, new Gson().toJson(new TransFileBean(5, i2)));
                    }
                });
            }
        });
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:963:0x1e7c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void switchFlutterData(io.flutter.plugin.common.MethodCall r22, final io.flutter.plugin.common.MethodChannel.Result r23) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 11990
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.yoozworld.watch.ui.BLfLst.switchFlutterData(io.flutter.plugin.common.MethodCall, io.flutter.plugin.common.MethodChannel$Result):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$2(final Device_base_info device_base_info) {
        this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$switchFlutterData$1(device_base_info);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$1(Device_base_info device_base_info) {
        String json = new Gson().toJson(device_base_info);
        SharePreferenceUtils.saveStringByKey(this.mContext, Constant.DEVICE_CONFIG_WATCH, json);
        ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
        if (currentConnectConfig != null) {
            SharePreferenceUtils.saveStringByKey(this.mContext, Constant.DEVICE_INFO + currentConnectConfig.macAddress, json);
        }
        LogUtil.d("源生端->获取手表信息,发送给发送给flutter:" + json);
        invokeFlutterMethodEventByJSONStr("deviceInfo", json);
    }

    /* JADX INFO: renamed from: cn.yoozworld.watch.ui.BLfLst$2, reason: invalid class name */
    class AnonymousClass2 implements OnRemindListener {
        AnonymousClass2() {
        }

        @Override // cn.baos.watch.sdk.interfac.app.OnRemindListener
        public void onAppRemind(final Common_remind_config common_remind_config) {
            LogUtil.d("获取提醒->" + new Gson().toJson(common_remind_config));
            BLfLst.this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onAppRemind$0(common_remind_config);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAppRemind$0(Common_remind_config common_remind_config) {
            BLfLst.this.invokeFlutterMethod(BtConstant.remindConfigData, new Gson().toJson(common_remind_config));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$4(final MethodChannel.Result result, final AppMarekEntity appMarekEntity) {
        LogUtil.d("获取应用市场->" + new Gson().toJson(appMarekEntity));
        this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst$$ExternalSyntheticLambda27
            @Override // java.lang.Runnable
            public final void run() {
                result.success(new Gson().toJson(appMarekEntity));
            }
        });
    }

    /* JADX INFO: renamed from: cn.yoozworld.watch.ui.BLfLst$3, reason: invalid class name */
    class AnonymousClass3 implements OnQrImageListener {
        AnonymousClass3() {
        }

        @Override // cn.baos.watch.sdk.interfac.moslem.OnQrImageListener
        public void onQrImage(final int i) {
            LogUtil.e("qrCodeFile-Success-->" + i);
            BLfLst.this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethod(BtConstant.qr_set_status, Integer.valueOf(i));
                }
            });
        }
    }

    /* JADX INFO: renamed from: cn.yoozworld.watch.ui.BLfLst$5, reason: invalid class name */
    class AnonymousClass5 implements ZipShareCallback {
        AnonymousClass5() {
        }

        @Override // cn.baos.watch.sdk.util.ZipShareCallback
        public void onShareStart() {
            LogUtil.d("手机日志 开始分享");
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst$5$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethod(BtConstant.zipShareStatus, 0);
                }
            });
        }

        @Override // cn.baos.watch.sdk.util.ZipShareCallback
        public void onShareEnd() {
            LogUtil.d("手机日志 分享完成");
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst$5$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethod(BtConstant.zipShareStatus, 1);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$7(final List list) {
        LogUtil.d("获取通讯录-->" + new Gson().toJson(list));
        if (list != null && list.size() > 0) {
            list.remove(0);
        }
        this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst$$ExternalSyntheticLambda25
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$switchFlutterData$6(list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$6(List list) {
        invokeFlutterMethod(BtConstant.getContactInfo, new Gson().toJson(list));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$9(List list) throws Throwable {
        LogUtil.d("手表世界时间-->" + new Gson().toJson(list));
        List<WorldEntity> worldList = WorldUtils.getWorldList(this.mContext);
        final ArrayList arrayList = new ArrayList();
        if (list != null && list.size() > 0 && worldList.size() > 0) {
            for (WorldEntity worldEntity : worldList) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (worldEntity.cityCn.equals(((WorldEntity) it.next()).name)) {
                        arrayList.add(worldEntity);
                    }
                }
            }
        }
        LogUtil.d("手表世界时间-->" + new Gson().toJson(arrayList));
        this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$switchFlutterData$8(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$8(List list) {
        invokeFlutterMethod(BtConstant.getWorldTime, new Gson().toJson(list));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$12(final Device_base_info device_base_info) {
        this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$switchFlutterData$11(device_base_info);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$11(Device_base_info device_base_info) {
        String json = new Gson().toJson(device_base_info);
        SharePreferenceUtils.saveStringByKey(this.mContext, Constant.DEVICE_CONFIG_WATCH, json);
        ConnectConfig currentConnectConfig = HbBtClientManager.getInstance().getCurrentConnectConfig();
        if (currentConnectConfig != null) {
            SharePreferenceUtils.saveStringByKey(this.mContext, Constant.DEVICE_INFO + currentConnectConfig.macAddress, json);
        }
        LogUtil.d("源生端->获取手表信息,发送给发送给flutter:" + json);
        this.jsonMessageChannel.send(json);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$14(final Device_base_info device_base_info) {
        this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$switchFlutterData$13(device_base_info);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$13(Device_base_info device_base_info) {
        String json = new Gson().toJson(device_base_info);
        LogUtil.d("源生端->跌倒检测:" + json);
        this.jsonMessageChannel.send(json);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$16(final Sensor_data_battery sensor_data_battery) {
        this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$switchFlutterData$15(sensor_data_battery);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$15(Sensor_data_battery sensor_data_battery) {
        String json = new Gson().toJson(sensor_data_battery);
        LogUtil.d("源生端->获取手表电量发送给flutter:" + json);
        invokeFlutterMethodEventByJSONStr(BtConstant.getWatchBattery, json);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$18(final Config_items config_items) {
        this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$switchFlutterData$17(config_items);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$17(Config_items config_items) {
        try {
            int i = config_items.datas[134];
            CigaretteLevelBean cigaretteLevelBean = new CigaretteLevelBean();
            cigaretteLevelBean.cigaretteLevel = i;
            String json = new Gson().toJson(cigaretteLevelBean);
            LogUtil.d("源生端->获取手表油量发送给flutter:" + json);
            invokeFlutterMethodEventByJSONStr(BtConstant.cigaretteLevel, json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: cn.yoozworld.watch.ui.BLfLst$9, reason: invalid class name */
    class AnonymousClass9 implements SyncRightNowDataCallback {
        AnonymousClass9() {
        }

        @Override // cn.baos.watch.sdk.interfac.syncdata.SyncRightNowDataCallback
        public void onDailyActiveSum(Sensor_data_daily_active_sum sensor_data_daily_active_sum) {
            final String json = new Gson().toJson(sensor_data_daily_active_sum);
            LogUtil.e("将实时日常数据(步数、卡路里)存入数据库，存入理论:将时间戳转为整点时间，更新数据库整点数据");
            Sensor_data_daily_active_array sensor_data_daily_active_array = new Sensor_data_daily_active_array();
            sensor_data_daily_active_array.datas = new Sensor_data_daily_active_sum[1];
            Sensor_data_daily_active_sum sensor_data_daily_active_sum2 = new Sensor_data_daily_active_sum();
            sensor_data_daily_active_sum2.sum_step = sensor_data_daily_active_sum.sum_step;
            sensor_data_daily_active_sum2.sum_distance_m = sensor_data_daily_active_sum.sum_distance_m;
            sensor_data_daily_active_sum2.sum_calorie = sensor_data_daily_active_sum.sum_calorie;
            sensor_data_daily_active_sum2.sum_times = sensor_data_daily_active_sum.sum_times;
            sensor_data_daily_active_sum2.update_timestamp = sensor_data_daily_active_sum.update_timestamp;
            sensor_data_daily_active_array.datas[0] = sensor_data_daily_active_sum2;
            DailyActiveManager.getInstance().saveData(sensor_data_daily_active_array);
            LogUtil.d("源生端->实时数据日常活动，返回:" + json);
            BLfLst.this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst$9$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDailyActiveSum$0(json);
                }
            });
            BLfLst.this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst$9$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDailyActiveSum$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDailyActiveSum$0(String str) {
            BLfLst.this.jsonMessageChannel.send(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDailyActiveSum$1() {
            new AppUtils().registerKeepLive(false, BLfLst.this.mContext);
        }

        @Override // cn.baos.watch.sdk.interfac.syncdata.SyncRightNowDataCallback
        public void onDailyHrate(Sensor_data_daily_hrate sensor_data_daily_hrate) {
            final String json = new Gson().toJson(sensor_data_daily_hrate);
            LogUtil.d("源生端->实时数据心率数据，返回:" + json);
            BLfLst.this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst$9$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDailyHrate$2(json);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDailyHrate$2(String str) {
            BLfLst.this.jsonMessageChannel.send(str);
        }

        @Override // cn.baos.watch.sdk.interfac.syncdata.SyncRightNowDataCallback
        public void onDailySpo(Sensor_data_daily_spo sensor_data_daily_spo) {
            final String json = new Gson().toJson(sensor_data_daily_spo);
            LogUtil.d("源生端->实时数据血氧数据，返回:" + json);
            BLfLst.this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst$9$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDailySpo$3(json);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDailySpo$3(String str) {
            BLfLst.this.jsonMessageChannel.send(str);
        }
    }

    static /* synthetic */ int lambda$switchFlutterData$19(GpslocEntity gpslocEntity, GpslocEntity gpslocEntity2) {
        long j = gpslocEntity.timeStamp - gpslocEntity2.timeStamp;
        if (j > 0) {
            return 1;
        }
        return j < 0 ? -1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$20() {
        invokeFlutterMethod(BtConstant.getWeather, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$21(byte[] bArr) {
        invokeFlutterMethod(BtConstant.collectWatchLoggerRequest, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$22(byte[] bArr) {
        invokeFlutterMethod(BtConstant.collectWatchLoggerRequest, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$23(byte[] bArr) {
        invokeFlutterMethod(BtConstant.syncMessageRequest, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$24(byte[] bArr) {
        invokeFlutterMethod(BtConstant.syncMessageRequest, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$25(final boolean z) {
        this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst.11
            @Override // java.lang.Runnable
            public void run() {
                Toast.makeText(BLfLst.this.mContext, z + "", 1).show();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchFlutterData$26(final boolean z) {
        this.mMainHandler.post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst.13
            @Override // java.lang.Runnable
            public void run() {
                Toast.makeText(BLfLst.this.mContext, z + "", 1).show();
            }
        });
    }

    static /* synthetic */ void lambda$switchFlutterData$28(ContactHelper contactHelper) {
        Looper.prepare();
        long jCurrentTimeMillis = System.currentTimeMillis();
        LogUtil.e("----------联系开始时间-------" + jCurrentTimeMillis);
        final ArrayList<PhoneContactsEntity> arrayListQueryContactList = contactHelper.queryContactList();
        ContactsManager.getInstance().dlt();
        LogUtil.e("----------联系结束时间-------" + ((System.currentTimeMillis() - jCurrentTimeMillis) / 1000));
        LogUtil.e("----------联系人长度-------" + arrayListQueryContactList.size());
        ContactsManager.getInstance().saveContactsToDb(arrayListQueryContactList);
        MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.BLfLst$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                BLfLst.getInstance().invokeFlutterMethod(BtConstant.getPhoneContacts, new Gson().toJson(arrayListQueryContactList));
            }
        });
        mPhoneGet = true;
        Looper.loop();
    }
}
