package cn.yoozworld.watch;

import android.app.NotificationManager;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import androidx.multidex.MultiDex;
import cn.baos.watch.sdk.bluetooth.BleService;
import cn.baos.watch.sdk.code.CrashHandler;
import cn.baos.watch.sdk.code.MainHandler;
import cn.baos.watch.sdk.util.AppUtils;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.W100Utils;
import cn.yoozworld.watch.ui.AppLifecycle;
import cn.yoozworld.watch.ui.SsManager;
import io.flutter.app.FlutterApplication;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: APP.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010%\u001a\u00020&2\b\u0010'\u001a\u0004\u0018\u00010(H\u0014J\b\u0010)\u001a\u0004\u0018\u00010\u0004J\b\u0010*\u001a\u0004\u0018\u00010\u0000J\b\u0010+\u001a\u00020&H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0000X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001c\u0010\u001f\u001a\u0004\u0018\u00010 X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$¨\u0006,"}, d2 = {"Lcn/yoozworld/watch/APP;", "Lio/flutter/app/FlutterApplication;", "()V", "appLifecycleTracker", "Lcn/yoozworld/watch/ui/AppLifecycle;", "getAppLifecycleTracker", "()Lcn/yoozworld/watch/ui/AppLifecycle;", "setAppLifecycleTracker", "(Lcn/yoozworld/watch/ui/AppLifecycle;)V", "builder", "Landroidx/core/app/NotificationCompat$Builder;", "getBuilder", "()Landroidx/core/app/NotificationCompat$Builder;", "setBuilder", "(Landroidx/core/app/NotificationCompat$Builder;)V", "isAppSysTimeBle", "", "()Z", "setAppSysTimeBle", "(Z)V", "mApp", "getMApp", "()Lcn/yoozworld/watch/APP;", "setMApp", "(Lcn/yoozworld/watch/APP;)V", "mNowTrackId", "", "getMNowTrackId", "()J", "setMNowTrackId", "(J)V", "notificationManager", "Landroid/app/NotificationManager;", "getNotificationManager", "()Landroid/app/NotificationManager;", "setNotificationManager", "(Landroid/app/NotificationManager;)V", "attachBaseContext", "", "base", "Landroid/content/Context;", "getAppLifecycleTrack", "getInstance", "onCreate", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class APP extends FlutterApplication {
    private AppLifecycle appLifecycleTracker;
    private NotificationCompat.Builder builder;
    private boolean isAppSysTimeBle;
    private APP mApp;
    private long mNowTrackId = -1;
    private NotificationManager notificationManager;

    public final APP getMApp() {
        return this.mApp;
    }

    public final void setMApp(APP app) {
        this.mApp = app;
    }

    public final AppLifecycle getAppLifecycleTracker() {
        return this.appLifecycleTracker;
    }

    public final void setAppLifecycleTracker(AppLifecycle appLifecycle) {
        this.appLifecycleTracker = appLifecycle;
    }

    /* JADX INFO: renamed from: isAppSysTimeBle, reason: from getter */
    public final boolean getIsAppSysTimeBle() {
        return this.isAppSysTimeBle;
    }

    public final void setAppSysTimeBle(boolean z) {
        this.isAppSysTimeBle = z;
    }

    public final long getMNowTrackId() {
        return this.mNowTrackId;
    }

    public final void setMNowTrackId(long j) {
        this.mNowTrackId = j;
    }

    /* JADX INFO: renamed from: getInstance, reason: from getter */
    public final APP getMApp() {
        return this.mApp;
    }

    public final NotificationCompat.Builder getBuilder() {
        return this.builder;
    }

    public final void setBuilder(NotificationCompat.Builder builder) {
        this.builder = builder;
    }

    public final NotificationManager getNotificationManager() {
        return this.notificationManager;
    }

    public final void setNotificationManager(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    @Override // android.content.ContextWrapper
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override // io.flutter.app.FlutterApplication, android.app.Application
    public void onCreate() {
        super.onCreate();
        LogUtil.d("AppApplication 主程序 onCreate start");
        this.mApp = this;
        AppLifecycle appLifecycle = new AppLifecycle();
        this.appLifecycleTracker = appLifecycle;
        Intrinsics.checkNotNull(appLifecycle);
        appLifecycle.AppLifecycles(this);
        registerActivityLifecycleCallbacks(this.appLifecycleTracker);
        APP app = this;
        new AppUtils().registerKeepLive(false, app);
        LogUtil.d("---AppApplication--onCreate--" + W100Utils.getAppProcessName(app) + "----" + getPackageName());
        if (StringsKt.equals(W100Utils.getAppProcessName(app), getPackageName(), true)) {
            SsManager.getInstance().initBleServiceManager(app);
            MainHandler.getInstance().postDelayed(new Runnable() { // from class: cn.yoozworld.watch.APP$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    APP.onCreate$lambda$0();
                }
            }, 1500L);
        }
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler());
        LogUtil.d("AppApplication 主程序 onCreate finish");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0() {
        BleService.getInstance().startReConnect();
    }

    /* JADX INFO: renamed from: getAppLifecycleTrack, reason: from getter */
    public final AppLifecycle getAppLifecycleTracker() {
        return this.appLifecycleTracker;
    }
}
