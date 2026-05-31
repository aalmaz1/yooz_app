package cn.yoozworld.watch.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.provider.MediaStore;
import android.text.TextUtils;
import androidx.core.content.PermissionChecker;
import cn.baos.watch.sdk.BasSdk;
import cn.baos.watch.sdk.base.AppDataConfig;
import cn.baos.watch.sdk.bluetooth.BleService;
import cn.baos.watch.sdk.code.HuabaoImpl;
import cn.baos.watch.sdk.code.HuabaoSdkFactory;
import cn.baos.watch.sdk.code.MainHandler;
import cn.baos.watch.sdk.database.gps.GpslocEntity;
import cn.baos.watch.sdk.entitiy.PhoneContactsEntity;
import cn.baos.watch.sdk.entitiy.QrEntity;
import cn.baos.watch.sdk.huabaoImpl.syncdata.gps.GpsModeManager;
import cn.baos.watch.sdk.manager.gps.GpsManager;
import cn.baos.watch.sdk.manager.notification.NotificationHuabaoManager;
import cn.baos.watch.sdk.manager.notification.ScreenBroadcastReceiver;
import cn.baos.watch.sdk.manager.notification.db.NotificationDbManager;
import cn.baos.watch.sdk.util.ContactUtils;
import cn.baos.watch.sdk.util.FileUtils;
import cn.baos.watch.sdk.util.JsonUtils;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.LogcatThread;
import cn.baos.watch.sdk.util.SharePreferenceUtils;
import cn.yoozworld.watch.utils.BtConstant;
import cn.yoozworld.watch.utils.GnssManager;
import cn.yoozworld.watch.utils.GoogleBean;
import cn.yoozworld.watch.utils.WorldUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.gson.Gson;
import com.king.camera.scan.CameraScan;
import com.king.camera.scan.util.LogUtils;
import com.king.zxing.util.CodeUtils;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.plugin.common.BasicMessageChannel;
import io.flutter.plugin.common.JSONMessageCodec;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class HomeActivity extends FlutterActivity {
    public static HomeActivity activity = null;
    public static boolean isBackGround = false;
    public static boolean isShowMap = false;
    BasicMessageChannel jsonMessageChannel;
    private HuabaoImpl mHuabaoImpl;
    MethodChannel methodChannel;
    GoogleSignInOptions googleSignInOptions = null;
    GoogleSignInClient mGoogleSignInClient = null;
    String scan = "";

    public static HomeActivity getInstance() {
        return activity;
    }

    @Override // io.flutter.embedding.android.FlutterActivity, android.app.Activity
    protected void onCreate(Bundle bundle) throws Throwable {
        super.onCreate(bundle);
        GpsManager.getInstance().setContext(this);
        GnssManager.getInstance().setContext(this);
        startScreenBroadcastReceiver();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: cn.yoozworld.watch.ui.HomeActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onCreate$0();
            }
        }, 20000L);
        new LogcatThread(this).startThread();
        WorldUtils.getWorldTime(this);
        String str = "App: " + Build.VERSION.SDK_INT + "\nVersion Name: " + Build.VERSION.RELEASE + "\nAndroid Version: " + Build.VERSION.INCREMENTAL;
        int i = 0;
        try {
            i = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        LogUtil.d("--app-version-info-" + i);
        LogUtil.d("--sdk-version-info-" + str);
        BasSdk.logSdkVersion();
        JsonUtils.readDeviceJson(this);
        MainHandler.getInstance().postDelayed(new Runnable() { // from class: cn.yoozworld.watch.ui.HomeActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                HomeActivity.lambda$onCreate$1();
            }
        }, 10L);
        isBackGround = true;
        GoogleSignInOptions googleSignInOptionsBuild = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        this.googleSignInOptions = googleSignInOptionsBuild;
        this.mGoogleSignInClient = GoogleSignIn.getClient((Activity) activity, googleSignInOptionsBuild);
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity) != 0) {
            GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(activity);
            LogUtil.e("google -- 登录不可使用(请安装Google Play Services或者更新Google Play Services版本)");
        } else {
            LogUtil.e("google -- 登录可使用");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0() {
        new ProcessAndroidCrashLogThread(this).start();
    }

    static /* synthetic */ void lambda$onCreate$1() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        LogUtil.e("-----detestart" + calendar.getTimeInMillis());
        ArrayList<GpslocEntity> arrayListQueryGpsModeInInterval = GpsModeManager.getInstance().queryGpsModeInInterval((int) (calendar.getTimeInMillis() / 1000), ((int) (calendar.getTimeInMillis() / 1000)) + 86400);
        LogUtil.d("---sendSportStracelist>" + arrayListQueryGpsModeInInterval.size() + "");
        LogUtil.d("---sendSportStracelist>" + new Gson().toJson(arrayListQueryGpsModeInInterval) + "");
        if (arrayListQueryGpsModeInInterval.size() > 0) {
            isShowMap = true;
        }
    }

    @Override // io.flutter.embedding.android.FlutterActivity, android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        String stringExtra = getIntent().getStringExtra("loginResult");
        LogUtil.d("海外登录跳转到主页面 MainActivity intent:" + stringExtra);
        if (stringExtra != null && !stringExtra.isEmpty()) {
            LogUtil.d("MainActivity 海外登录登陆结果发送给flutter:" + stringExtra);
            BLfLst.getInstance().invokeFlutterMethodEventByJSON(stringExtra);
        }
        if (getIntent() != null) {
            final int intExtra = getIntent().getIntExtra("sportKcalStatu", 0);
            LogUtil.e("--sportKcalStatu--" + intExtra);
            MainHandler.getInstance().postDelayed(new Runnable() { // from class: cn.yoozworld.watch.ui.HomeActivity$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethodEventByJSON(BtConstant.sport_status_change, intExtra);
                }
            }, 1L);
        }
    }

    @Override // io.flutter.embedding.android.FlutterActivity, io.flutter.embedding.android.FlutterActivityAndFragmentDelegate.Host, io.flutter.embedding.android.FlutterEngineProvider
    public FlutterEngine provideFlutterEngine(Context context) {
        activity = this;
        LogUtil.d("MainActivity 源生android 主页面加载 start");
        LogUtil.d("MainActivity 进程 " + Process.myPid() + " Thread: " + Process.myTid() + " name " + Thread.currentThread().getName());
        HuabaoImpl huabaoImpl = (HuabaoImpl) HuabaoSdkFactory.createMessage(HuabaoSdkFactory.SdkType.SDK_HUABAO);
        this.mHuabaoImpl = huabaoImpl;
        huabaoImpl.init(this);
        NotificationDbManager.getInstance(this).initNotificationDb();
        LogUtil.d("MainActivity 源生android 主页面加载 end");
        return FlutterEngineCache.getInstance().get("d_stack_engine");
    }

    @Override // io.flutter.embedding.android.FlutterActivity, io.flutter.embedding.android.FlutterActivityAndFragmentDelegate.Host, io.flutter.embedding.android.FlutterEngineConfigurator
    public void configureFlutterEngine(FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        MethodChannel methodChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), "com.baos.sdk/channel");
        this.methodChannel = methodChannel;
        methodChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() { // from class: cn.yoozworld.watch.ui.HomeActivity$$ExternalSyntheticLambda15
            @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
            public final void onMethodCall(MethodCall methodCall, MethodChannel.Result result) throws Throwable {
                HomeActivity.lambda$configureFlutterEngine$3(methodCall, result);
            }
        });
        LogUtil.d("MainActivity 源生android 主页面加载 configureFlutterEngine");
        BasicMessageChannel basicMessageChannel = new BasicMessageChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), "com.baos.sdk/messages", JSONMessageCodec.INSTANCE);
        this.jsonMessageChannel = basicMessageChannel;
        basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() { // from class: cn.yoozworld.watch.ui.HomeActivity$$ExternalSyntheticLambda1
            @Override // io.flutter.plugin.common.BasicMessageChannel.MessageHandler
            public final void onMessage(Object obj, BasicMessageChannel.Reply reply) {
                HomeActivity.lambda$configureFlutterEngine$4(obj, reply);
            }
        });
        BLfLst.getInstance().setMethodChannel(this.methodChannel);
        BLfLst.getInstance().setJsonMessageChannel(this.jsonMessageChannel);
    }

    static /* synthetic */ void lambda$configureFlutterEngine$3(MethodCall methodCall, MethodChannel.Result result) throws Throwable {
        LogUtil.d("MainActivity 源生android 主页面加载 configureFlutterEngine call.method:" + methodCall.method);
        BLfLst.getInstance().switchFlutterData(methodCall, result);
    }

    static /* synthetic */ void lambda$configureFlutterEngine$4(Object obj, BasicMessageChannel.Reply reply) {
        LogUtil.d("og.Received message =" + obj);
        reply.reply("Reply from Android");
    }

    public boolean checkPermissions(String str) {
        return (getApplicationInfo().targetSdkVersion < 23 && PermissionChecker.checkPermission(this, str, Binder.getCallingPid(), Binder.getCallingUid(), getPackageName()) == 0) || checkSelfPermission(str) == 0;
    }

    @Override // io.flutter.embedding.android.FlutterActivity, android.app.Activity
    protected void onDestroy() {
        LogUtil.d("花豹app MainActivity onDestroy！！！！！！！！！！！！");
        AppDataConfig.getInstance().put(SharePreferenceUtils.KEY_MTU_SETTING, true);
        new LogcatThread(this).endThread();
        LogUtil.d("花豹app被杀死！！！！！！！！！！！！");
        super.onDestroy();
    }

    @Override // io.flutter.embedding.android.FlutterActivity, android.app.Activity
    protected void onStop() {
        LogUtil.d("前台到后台");
        super.onStop();
    }

    @Override // io.flutter.embedding.android.FlutterActivity, android.app.Activity
    protected void onResume() {
        LogUtil.d("后台切到前台");
        LogUtil.e("----app-start-onResume");
        MainHandler.getInstance().postDelayed(new Runnable() { // from class: cn.yoozworld.watch.ui.HomeActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                BleService.getInstance().startReConnect();
            }
        }, 1000L);
        super.onResume();
        isBackGround = true;
        NotificationHuabaoManager.getInstance().requestRebindNotificationService(this);
    }

    public void requestLocationPermissions() {
        ArrayList arrayList = new ArrayList();
        if (!checkPermissions("android.permission.ACCESS_COARSE_LOCATION")) {
            arrayList.add("android.permission.ACCESS_COARSE_LOCATION");
        }
        if (!checkPermissions("android.permission.ACCESS_FINE_LOCATION")) {
            arrayList.add("android.permission.ACCESS_FINE_LOCATION");
        }
        if (arrayList.isEmpty()) {
            return;
        }
        requestPermissions((String[]) arrayList.toArray(new String[arrayList.size()]), 1);
        for (int i = 0; i < arrayList.size(); i++) {
            LogUtil.d("申请: " + ((String) arrayList.get(i)));
        }
    }

    public void requestReadCallPhonePermissions() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("android.permission.READ_CALL_LOG");
        if (!checkPermissions("android.permission.READ_CALL_LOG")) {
            arrayList.add("android.permission.READ_CALL_LOG");
        }
        if (arrayList.isEmpty()) {
            return;
        }
        LogUtil.d("权限不为空,需要申请");
        requestPermissions((String[]) arrayList.toArray(new String[arrayList.size()]), 1);
        for (int i = 0; i < arrayList.size(); i++) {
            LogUtil.d("申请: " + ((String) arrayList.get(i)));
        }
    }

    public void requestContactsPermissions() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("android.permission.READ_PHONE_STATE");
        if (!checkPermissions("android.permission.READ_CONTACTS")) {
            arrayList.add("android.permission.READ_CONTACTS");
        }
        if (arrayList.isEmpty()) {
            return;
        }
        LogUtil.d("权限不为空,需要申请");
        requestPermissions((String[]) arrayList.toArray(new String[arrayList.size()]), 1);
        for (int i = 0; i < arrayList.size(); i++) {
            LogUtil.d("申请: " + ((String) arrayList.get(i)));
        }
    }

    public void requestPermissions() {
        ArrayList arrayList = new ArrayList();
        if (!checkPermissions("android.permission.READ_LOGS")) {
            arrayList.add("android.permission.READ_LOGS");
        }
        if (!checkPermissions("android.permission.READ_PHONE_STATE")) {
            arrayList.add("android.permission.READ_PHONE_STATE");
        }
        if (!checkPermissions("android.permission.READ_CALL_LOG")) {
            arrayList.add("android.permission.READ_CALL_LOG");
        }
        if (Build.VERSION.SDK_INT >= 29 && !checkPermissions("android.permission.ACCESS_BACKGROUND_LOCATION")) {
            arrayList.add("android.permission.ACCESS_BACKGROUND_LOCATION");
            LogUtil.d("还未申请后台定位权限");
        } else {
            LogUtil.d("已经申请后台定位权限");
        }
        if (!checkPermissions("android.permission.BLUETOOTH_SCAN")) {
            arrayList.add("android.permission.BLUETOOTH_SCAN");
        }
        if (!checkPermissions("android.permission.BLUETOOTH")) {
            arrayList.add("android.permission.BLUETOOTH");
        }
        if (!checkPermissions("android.permission.BLUETOOTH_ADMIN")) {
            arrayList.add("android.permission.BLUETOOTH_ADMIN");
        }
        if (!checkPermissions("android.permission.BLUETOOTH_PRIVILEGED")) {
            arrayList.add("android.permission.BLUETOOTH_PRIVILEGED");
        }
        if (!checkPermissions("android.permission.RECEIVE_BOOT_COMPLETED")) {
            arrayList.add("android.permission.RECEIVE_BOOT_COMPLETED");
        }
        if (!checkPermissions("android.permission.WRITE_EXTERNAL_STORAGE")) {
            arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
        }
        if (!checkPermissions("android.permission.READ_EXTERNAL_STORAGE")) {
            arrayList.add("android.permission.READ_EXTERNAL_STORAGE");
        }
        if (!checkPermissions("android.permission.CALL_PHONE")) {
            arrayList.add("android.permission.CALL_PHONE");
        }
        if (!checkPermissions("android.permission.MODIFY_PHONE_STATE")) {
            arrayList.add("android.permission.MODIFY_PHONE_STATE");
        }
        if (!checkPermissions("android.permission.READ_CONTACTS")) {
            arrayList.add("android.permission.READ_CONTACTS");
        }
        if (!checkPermissions("android.permission.ACCESS_NOTIFICATION_POLICY")) {
            arrayList.add("android.permission.ACCESS_NOTIFICATION_POLICY");
        }
        if (!checkPermissions("android.permission.ANSWER_PHONE_CALLS")) {
            arrayList.add("android.permission.ANSWER_PHONE_CALLS");
        }
        if (!checkPermissions("android.permission.VIBRATE")) {
            arrayList.add("android.permission.VIBRATE");
        }
        if (!checkPermissions("android.permission.ACCESS_COARSE_LOCATION")) {
            arrayList.add("android.permission.ACCESS_COARSE_LOCATION");
        }
        if (!checkPermissions("android.permission.ACCESS_FINE_LOCATION")) {
            arrayList.add("android.permission.ACCESS_FINE_LOCATION");
        }
        if (!arrayList.isEmpty()) {
            LogUtil.d("权限不为空,需要申请");
            requestPermissions((String[]) arrayList.toArray(new String[arrayList.size()]), 1);
            for (int i = 0; i < arrayList.size(); i++) {
                LogUtil.d("申请: " + ((String) arrayList.get(i)));
            }
        }
        NotificationHuabaoManager.getInstance().requestRebindNotificationService(this);
    }

    @Override // io.flutter.embedding.android.FlutterActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        LogUtil.d("requestCode:" + i);
        for (int i2 = 0; i2 < strArr.length; i2++) {
            LogUtil.d("权限回调: " + strArr[i2] + "@" + iArr[i2]);
            if (strArr[i2].equals("android.permission.WRITE_EXTERNAL_STORAGE")) {
                if (iArr[i2] == 0) {
                    LogUtil.d("get write permission");
                } else {
                    LogUtil.d("refuse write permission");
                }
            }
            if (strArr[i2].equals("android.permission.ACCESS_FINE_LOCATION") && iArr[i2] == 0) {
                GnssManager.getInstance().startListenSatellites();
            }
            if (strArr[i2].equals("android.permission.READ_CONTACTS")) {
                if (iArr[i2] == 0) {
                    LogUtil.d("get write permission");
                    final ArrayList<PhoneContactsEntity> allContacts = ContactUtils.getAllContacts(this);
                    MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.HomeActivity$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            List list = allContacts;
                            BLfLst.getInstance().invokeFlutterMethod(BtConstant.getPhoneContacts, list.size() > 0 ? new Gson().toJson(list) : "");
                        }
                    });
                } else {
                    LogUtil.d("refuse write permission");
                }
            }
        }
    }

    public HuabaoImpl getHuabaoImpl() {
        return this.mHuabaoImpl;
    }

    @Override // io.flutter.embedding.android.FlutterActivity, android.app.Activity
    public void onBackPressed() {
        LogUtil.e(BtConstant.onBackPressed);
        MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.HomeActivity$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                BLfLst.getInstance().invokeFlutterMethod(BtConstant.onBackPressed, BtConstant.onBackPressed);
            }
        });
    }

    private void startScreenBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        registerReceiver(new ScreenBroadcastReceiver(), intentFilter);
    }

    @Override // io.flutter.embedding.android.FlutterActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, final Intent intent) {
        super.onActivityResult(i, i2, intent);
        LogUtil.d("onActivityResult");
        this.scan = "";
        LogUtil.e("onActivityResult requestCode:" + i + " resultCode:" + i2 + "----" + (i2 == -1));
        if (i == 12) {
            if (i2 == -1 && intent != null) {
                String scanResult = CameraScan.parseScanResult(intent);
                LogUtil.d("扫描结果为：" + scanResult);
                this.scan = scanResult;
            }
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.HomeActivity$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onActivityResult$8();
                }
            });
        }
        if (i == 100) {
            try {
                final Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData());
                if (bitmap == null) {
                    return;
                }
                final QrEntity qrEntity = new QrEntity();
                asyncThread(new Runnable() { // from class: cn.yoozworld.watch.ui.HomeActivity$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onActivityResult$14(bitmap, qrEntity);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (i == 1001) {
            try {
                new Handler().post(new Runnable() { // from class: cn.yoozworld.watch.ui.HomeActivity$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() throws Throwable {
                        HomeActivity.lambda$onActivityResult$15(intent);
                    }
                });
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onActivityResult$8() {
        BLfLst.getInstance().invokeFlutterMethod(BtConstant.scan_data, this.scan);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onActivityResult$14(Bitmap bitmap, final QrEntity qrEntity) {
        final String code = CodeUtils.parseCode(bitmap);
        if (TextUtils.isEmpty(code) || code.equals("null")) {
            qrEntity.url = "";
            MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.HomeActivity$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    BLfLst.getInstance().invokeFlutterMethod(BtConstant.qr_image, new Gson().toJson(qrEntity));
                }
            });
        } else {
            runOnUiThread(new Runnable() { // from class: cn.yoozworld.watch.ui.HomeActivity$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onActivityResult$13(code, qrEntity);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onActivityResult$13(final String str, final QrEntity qrEntity) {
        LogUtils.d("result:" + str);
        new Thread(new Runnable() { // from class: cn.yoozworld.watch.ui.HomeActivity$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$onActivityResult$12(str, qrEntity);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onActivityResult$12(final String str, final QrEntity qrEntity) throws Throwable {
        Bitmap bitmapCreateQRCode = CodeUtils.createQRCode(str, 171);
        final File file = new File(FileUtils.getDirAndCreate(this, "imgCropper"), "imgCropperMax" + System.currentTimeMillis() + ".png");
        FileUtils.saveBitmap(bitmapCreateQRCode, file.getPath());
        runOnUiThread(new Runnable() { // from class: cn.yoozworld.watch.ui.HomeActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                HomeActivity.lambda$onActivityResult$11(file, qrEntity, str);
            }
        });
    }

    static /* synthetic */ void lambda$onActivityResult$11(File file, final QrEntity qrEntity, String str) {
        LogUtils.e("new path:" + file.getPath());
        qrEntity.maxImg = file.getPath();
        qrEntity.url = str;
        MainHandler.getInstance().post(new Runnable() { // from class: cn.yoozworld.watch.ui.HomeActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                BLfLst.getInstance().invokeFlutterMethod(BtConstant.qr_image, new Gson().toJson(qrEntity));
            }
        });
    }

    static /* synthetic */ void lambda$onActivityResult$15(Intent intent) throws Throwable {
        try {
            GoogleSignInAccount result = GoogleSignIn.getSignedInAccountFromIntent(intent).getResult(ApiException.class);
            LogUtil.e("测试account.email: " + result.getEmail());
            LogUtil.e("测试account.email: " + result.getEmail());
            LogUtil.e("测试account.email: " + result.getEmail());
            LogUtil.e("测试account.email: " + result.getIdToken());
            LogUtil.e("测试account.email: " + new Gson().toJson(result));
            GoogleBean googleBean = new GoogleBean();
            googleBean.email = result.getEmail();
            BLfLst.getInstance().invokeFlutterMethodEventByJSONRes(BtConstant.google, new Gson().toJson(googleBean));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // io.flutter.embedding.android.FlutterActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        isBackGround = false;
    }

    private void asyncThread(Runnable runnable) {
        new Thread(runnable).start();
    }

    public void googleLogin() {
        activity.startActivityForResult(this.mGoogleSignInClient.getSignInIntent(), 1001);
    }
}
