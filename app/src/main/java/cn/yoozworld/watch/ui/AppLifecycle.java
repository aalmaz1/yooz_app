package cn.yoozworld.watch.ui;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Process;
import cn.baos.watch.sdk.util.LogUtil;
import cn.yoozworld.watch.APP;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppLifecycle.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0004J\b\u0010\f\u001a\u0004\u0018\u00010\bJ\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\bH\u0002J\u001a\u0010\u000f\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\bH\u0016J\u0010\u0010\u0013\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\bH\u0016J\u0010\u0010\u0014\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\bH\u0016J\u0018\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u0011H\u0016J\u0010\u0010\u0017\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\bH\u0016J\u0010\u0010\u0018\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\bH\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcn/yoozworld/watch/ui/AppLifecycle;", "Landroid/app/Application$ActivityLifecycleCallbacks;", "()V", "mAppApplication", "Lcn/yoozworld/watch/APP;", "mBoostLifecycleListener", "Lcn/yoozworld/watch/ui/BLfLst;", "mCurrentActivity", "Landroid/app/Activity;", "AppLifecycles", "", "appApplication", "getCurrentActivity", "initFlutter", "activity", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onActivityDestroyed", "onActivityPaused", "onActivityResumed", "onActivitySaveInstanceState", "outState", "onActivityStarted", "onActivityStopped", "app_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AppLifecycle implements Application.ActivityLifecycleCallbacks {
    private APP mAppApplication;
    private BLfLst mBoostLifecycleListener;
    private Activity mCurrentActivity;

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(outState, "outState");
    }

    public final void AppLifecycles(APP appApplication) {
        LogUtil.d("生命周期--->AppLifecycleTracker:初始化");
        this.mAppApplication = appApplication;
    }

    /* JADX INFO: renamed from: getCurrentActivity, reason: from getter */
    public final Activity getMCurrentActivity() {
        return this.mCurrentActivity;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.mCurrentActivity = activity;
        LogUtil.d("生命周期--->创建activity:" + activity);
        if (activity instanceof HomeActivity) {
            LogUtil.d("onActivityCreated 进程 " + Process.myPid() + " Thread: " + Process.myTid() + " name " + Thread.currentThread().getName());
            LogUtil.d("生命周期--->创建MainActivity进行初始化flutter:" + activity);
            initFlutter(activity);
        }
    }

    private final void initFlutter(Activity activity) {
        LogUtil.d("初始化flutter");
        BLfLst bLfLst = BLfLst.getInstance();
        this.mBoostLifecycleListener = bLfLst;
        Intrinsics.checkNotNull(bLfLst);
        bLfLst.setContext(this.mAppApplication);
        BLfLst bLfLst2 = this.mBoostLifecycleListener;
        Intrinsics.checkNotNull(bLfLst2);
        bLfLst2.setMainActivity(activity);
        BLfLst bLfLst3 = this.mBoostLifecycleListener;
        Intrinsics.checkNotNull(bLfLst3);
        bLfLst3.onEngineCreated();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        LogUtil.d("生命周期--->打开onActivityStarted :" + activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        this.mCurrentActivity = activity;
        LogUtil.d("生命周期--->可见onActivityResumed :" + activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        LogUtil.d("生命周期--->可见不可点击onActivityPaused :" + activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        LogUtil.d("生命周期--->不可见onActivityStopped :" + activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        LogUtil.d("生命周期--->销毁onActivityDestroyed :" + activity);
        LogUtil.d("生命周期--->销毁onActivityDestroyed :" + activity);
        if (activity instanceof HomeActivity) {
            System.exit(0);
        }
    }
}
