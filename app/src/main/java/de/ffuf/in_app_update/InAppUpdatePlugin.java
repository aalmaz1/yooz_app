package de.ffuf.in_app_update;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.tekartik.sqflite.Constant;
import de.ffuf.in_app_update.InAppUpdatePlugin;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: compiled from: InAppUpdatePlugin.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000¦\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 K2\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u00052\u00020\u0006:\u0001KB\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u000fH\u0002J\u001e\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u001a2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001c0!H\u0002J\u0010\u0010\"\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u001aH\u0002J\u0010\u0010#\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u001aH\u0002J\u001a\u0010$\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&2\b\u0010'\u001a\u0004\u0018\u00010(H\u0016J\u0010\u0010)\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&H\u0016J\u0010\u0010*\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&H\u0016J\"\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\u000f2\u0006\u0010.\u001a\u00020\u000f2\b\u0010/\u001a\u0004\u0018\u000100H\u0016J\u0010\u00101\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&H\u0016J\u0018\u00102\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&2\u0006\u00103\u001a\u00020(H\u0016J\u0010\u00104\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&H\u0016J\u0010\u00105\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&H\u0016J\u0010\u00106\u001a\u00020\u001c2\u0006\u00107\u001a\u000208H\u0016J\u0010\u00109\u001a\u00020\u001c2\u0006\u0010:\u001a\u00020;H\u0016J\u0012\u0010<\u001a\u00020\u001c2\b\u0010=\u001a\u0004\u0018\u00010>H\u0016J\b\u0010?\u001a\u00020\u001cH\u0016J\b\u0010@\u001a\u00020\u001cH\u0016J\u0010\u0010A\u001a\u00020\u001c2\u0006\u0010B\u001a\u00020;H\u0016J\u001c\u0010C\u001a\u00020\u001c2\b\u0010=\u001a\u0004\u0018\u00010>2\b\u0010D\u001a\u0004\u0018\u00010\u0016H\u0016J\u0018\u0010E\u001a\u00020\u001c2\u0006\u0010F\u001a\u00020G2\u0006\u0010\u001f\u001a\u00020\u001aH\u0016J\u0010\u0010H\u001a\u00020\u001c2\u0006\u00107\u001a\u000208H\u0016J\u0010\u0010I\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u001aH\u0002J\u0010\u0010J\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u001aH\u0002R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006L"}, d2 = {"Lde/ffuf/in_app_update/InAppUpdatePlugin;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", "Lio/flutter/plugin/common/PluginRegistry$ActivityResultListener;", "Landroid/app/Application$ActivityLifecycleCallbacks;", "Lio/flutter/embedding/engine/plugins/activity/ActivityAware;", "Lio/flutter/plugin/common/EventChannel$StreamHandler;", "()V", "activityProvider", "Lde/ffuf/in_app_update/ActivityProvider;", "appUpdateInfo", "Lcom/google/android/play/core/appupdate/AppUpdateInfo;", "appUpdateManager", "Lcom/google/android/play/core/appupdate/AppUpdateManager;", "appUpdateType", "", "Ljava/lang/Integer;", "channel", "Lio/flutter/plugin/common/MethodChannel;", NotificationCompat.CATEGORY_EVENT, "Lio/flutter/plugin/common/EventChannel;", "installStateSink", "Lio/flutter/plugin/common/EventChannel$EventSink;", "installStateUpdatedListener", "Lcom/google/android/play/core/install/InstallStateUpdatedListener;", "updateResult", "Lio/flutter/plugin/common/MethodChannel$Result;", "addState", "", NotificationCompat.CATEGORY_STATUS, "checkAppState", Constant.PARAM_RESULT, "block", "Lkotlin/Function0;", "checkForUpdate", "completeFlexibleUpdate", "onActivityCreated", "activity", "Landroid/app/Activity;", "savedInstanceState", "Landroid/os/Bundle;", "onActivityDestroyed", "onActivityPaused", "onActivityResult", "", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onActivityResumed", "onActivitySaveInstanceState", "outState", "onActivityStarted", "onActivityStopped", "onAttachedToActivity", "activityPluginBinding", "Lio/flutter/embedding/engine/plugins/activity/ActivityPluginBinding;", "onAttachedToEngine", "flutterPluginBinding", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onCancel", "arguments", "", "onDetachedFromActivity", "onDetachedFromActivityForConfigChanges", "onDetachedFromEngine", "binding", "onListen", "events", "onMethodCall", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "onReattachedToActivityForConfigChanges", "performImmediateUpdate", "startFlexibleUpdate", "Companion", "in_app_update_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InAppUpdatePlugin implements FlutterPlugin, MethodChannel.MethodCallHandler, PluginRegistry.ActivityResultListener, Application.ActivityLifecycleCallbacks, ActivityAware, EventChannel.StreamHandler {
    private static final int REQUEST_CODE_START_UPDATE = 1276;
    private ActivityProvider activityProvider;
    private AppUpdateInfo appUpdateInfo;
    private AppUpdateManager appUpdateManager;
    private Integer appUpdateType;
    private MethodChannel channel;
    private EventChannel event;
    private EventChannel.EventSink installStateSink;
    private InstallStateUpdatedListener installStateUpdatedListener;
    private MethodChannel.Result updateResult;

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(activity, "activity");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(outState, "outState");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onListen(Object arguments, EventChannel.EventSink events) {
        this.installStateSink = events;
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onCancel(Object arguments) {
        this.installStateSink = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addState(int status) {
        EventChannel.EventSink eventSink = this.installStateSink;
        if (eventSink != null) {
            eventSink.success(Integer.valueOf(status));
        }
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        Intrinsics.checkNotNullParameter(flutterPluginBinding, "flutterPluginBinding");
        MethodChannel methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "de.ffuf.in_app_update/methods");
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(this);
        EventChannel eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "de.ffuf.in_app_update/stateEvents");
        this.event = eventChannel;
        eventChannel.setStreamHandler(this);
        InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() { // from class: de.ffuf.in_app_update.InAppUpdatePlugin$$ExternalSyntheticLambda3
            @Override // com.google.android.play.core.listener.StateUpdatedListener
            public final void onStateUpdate(InstallState installState) {
                InAppUpdatePlugin.onAttachedToEngine$lambda$0(this.f$0, installState);
            }
        };
        this.installStateUpdatedListener = installStateUpdatedListener;
        AppUpdateManager appUpdateManager = this.appUpdateManager;
        if (appUpdateManager != null) {
            appUpdateManager.registerListener(installStateUpdatedListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onAttachedToEngine$lambda$0(InAppUpdatePlugin this$0, InstallState installState) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(installState, "installState");
        this$0.addState(installState.installStatus());
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        MethodChannel methodChannel = this.channel;
        InstallStateUpdatedListener installStateUpdatedListener = null;
        if (methodChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("channel");
            methodChannel = null;
        }
        methodChannel.setMethodCallHandler(null);
        EventChannel eventChannel = this.event;
        if (eventChannel == null) {
            Intrinsics.throwUninitializedPropertyAccessException(NotificationCompat.CATEGORY_EVENT);
            eventChannel = null;
        }
        eventChannel.setStreamHandler(null);
        AppUpdateManager appUpdateManager = this.appUpdateManager;
        if (appUpdateManager != null) {
            InstallStateUpdatedListener installStateUpdatedListener2 = this.installStateUpdatedListener;
            if (installStateUpdatedListener2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("installStateUpdatedListener");
            } else {
                installStateUpdatedListener = installStateUpdatedListener2;
            }
            appUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        String str = call.method;
        if (str != null) {
            switch (str.hashCode()) {
                case -1873373639:
                    if (str.equals("performImmediateUpdate")) {
                        performImmediateUpdate(result);
                        return;
                    }
                    break;
                case -1541164682:
                    if (str.equals("startFlexibleUpdate")) {
                        startFlexibleUpdate(result);
                        return;
                    }
                    break;
                case -1317168438:
                    if (str.equals("checkForUpdate")) {
                        checkForUpdate(result);
                        return;
                    }
                    break;
                case -193504755:
                    if (str.equals("completeFlexibleUpdate")) {
                        completeFlexibleUpdate(result);
                        return;
                    }
                    break;
            }
        }
        result.notImplemented();
    }

    @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        MethodChannel.Result result;
        if (requestCode != REQUEST_CODE_START_UPDATE) {
            return false;
        }
        Integer num = this.appUpdateType;
        if (num != null && num.intValue() == 1) {
            if (resultCode == -1) {
                MethodChannel.Result result2 = this.updateResult;
                if (result2 != null) {
                    result2.success(null);
                }
            } else if (resultCode == 0) {
                MethodChannel.Result result3 = this.updateResult;
                if (result3 != null) {
                    result3.error("USER_DENIED_UPDATE", String.valueOf(resultCode), null);
                }
            } else if (resultCode == 1 && (result = this.updateResult) != null) {
                result.error("IN_APP_UPDATE_FAILED", "Some other error prevented either the user from providing consent or the update to proceed.", null);
            }
            this.updateResult = null;
            return true;
        }
        Integer num2 = this.appUpdateType;
        if (num2 == null || num2.intValue() != 0) {
            return false;
        }
        if (resultCode == 0) {
            MethodChannel.Result result4 = this.updateResult;
            if (result4 != null) {
                result4.error("USER_DENIED_UPDATE", String.valueOf(resultCode), null);
            }
            this.updateResult = null;
        } else if (resultCode == 1) {
            MethodChannel.Result result5 = this.updateResult;
            if (result5 != null) {
                result5.error("IN_APP_UPDATE_FAILED", String.valueOf(resultCode), null);
            }
            this.updateResult = null;
        }
        return true;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(final ActivityPluginBinding activityPluginBinding) {
        Intrinsics.checkNotNullParameter(activityPluginBinding, "activityPluginBinding");
        this.activityProvider = new ActivityProvider() { // from class: de.ffuf.in_app_update.InAppUpdatePlugin.onAttachedToActivity.1
            @Override // de.ffuf.in_app_update.ActivityProvider
            public void addActivityResultListener(PluginRegistry.ActivityResultListener callback) {
                Intrinsics.checkNotNullParameter(callback, "callback");
                activityPluginBinding.addActivityResultListener(callback);
            }

            @Override // de.ffuf.in_app_update.ActivityProvider
            public Activity activity() {
                Activity activity = activityPluginBinding.getActivity();
                Intrinsics.checkNotNullExpressionValue(activity, "getActivity(...)");
                return activity;
            }
        };
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        this.activityProvider = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(final ActivityPluginBinding activityPluginBinding) {
        Intrinsics.checkNotNullParameter(activityPluginBinding, "activityPluginBinding");
        this.activityProvider = new ActivityProvider() { // from class: de.ffuf.in_app_update.InAppUpdatePlugin.onReattachedToActivityForConfigChanges.1
            @Override // de.ffuf.in_app_update.ActivityProvider
            public void addActivityResultListener(PluginRegistry.ActivityResultListener callback) {
                Intrinsics.checkNotNullParameter(callback, "callback");
                activityPluginBinding.addActivityResultListener(callback);
            }

            @Override // de.ffuf.in_app_update.ActivityProvider
            public Activity activity() {
                Activity activity = activityPluginBinding.getActivity();
                Intrinsics.checkNotNullExpressionValue(activity, "getActivity(...)");
                return activity;
            }
        };
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        this.activityProvider = null;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(final Activity activity) {
        Task<AppUpdateInfo> appUpdateInfo;
        Intrinsics.checkNotNullParameter(activity, "activity");
        AppUpdateManager appUpdateManager = this.appUpdateManager;
        if (appUpdateManager == null || (appUpdateInfo = appUpdateManager.getAppUpdateInfo()) == null) {
            return;
        }
        final Function1<AppUpdateInfo, Unit> function1 = new Function1<AppUpdateInfo, Unit>() { // from class: de.ffuf.in_app_update.InAppUpdatePlugin.onActivityResumed.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AppUpdateInfo appUpdateInfo2) {
                invoke2(appUpdateInfo2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(AppUpdateInfo appUpdateInfo2) {
                Integer num;
                if (appUpdateInfo2.updateAvailability() == 3 && (num = InAppUpdatePlugin.this.appUpdateType) != null && num.intValue() == 1) {
                    try {
                        AppUpdateManager appUpdateManager2 = InAppUpdatePlugin.this.appUpdateManager;
                        if (appUpdateManager2 != null) {
                            appUpdateManager2.startUpdateFlowForResult(appUpdateInfo2, 1, activity, InAppUpdatePlugin.REQUEST_CODE_START_UPDATE);
                        }
                    } catch (IntentSender.SendIntentException e) {
                        Log.e("in_app_update", "Could not start update flow", e);
                    }
                }
            }
        };
        appUpdateInfo.addOnSuccessListener(new OnSuccessListener() { // from class: de.ffuf.in_app_update.InAppUpdatePlugin$$ExternalSyntheticLambda2
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                InAppUpdatePlugin.onActivityResumed$lambda$1(function1, obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityResumed$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    private final void performImmediateUpdate(final MethodChannel.Result result) {
        checkAppState(result, new Function0<Unit>() { // from class: de.ffuf.in_app_update.InAppUpdatePlugin.performImmediateUpdate.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() throws IntentSender.SendIntentException {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() throws IntentSender.SendIntentException {
                InAppUpdatePlugin.this.appUpdateType = 1;
                InAppUpdatePlugin.this.updateResult = result;
                AppUpdateManager appUpdateManager = InAppUpdatePlugin.this.appUpdateManager;
                if (appUpdateManager != null) {
                    AppUpdateInfo appUpdateInfo = InAppUpdatePlugin.this.appUpdateInfo;
                    Intrinsics.checkNotNull(appUpdateInfo);
                    ActivityProvider activityProvider = InAppUpdatePlugin.this.activityProvider;
                    Intrinsics.checkNotNull(activityProvider);
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, activityProvider.activity(), AppUpdateOptions.defaultOptions(1), InAppUpdatePlugin.REQUEST_CODE_START_UPDATE);
                }
            }
        });
    }

    private final void checkAppState(MethodChannel.Result result, Function0<Unit> block) {
        if (this.appUpdateInfo == null) {
            result.error("REQUIRE_CHECK_FOR_UPDATE", "Call checkForUpdate first!", null);
            throw new IllegalArgumentException(Unit.INSTANCE.toString());
        }
        ActivityProvider activityProvider = this.activityProvider;
        if ((activityProvider != null ? activityProvider.activity() : null) == null) {
            result.error("REQUIRE_FOREGROUND_ACTIVITY", "in_app_update requires a foreground activity", null);
            throw new IllegalArgumentException(Unit.INSTANCE.toString());
        }
        if (this.appUpdateManager == null) {
            result.error("REQUIRE_CHECK_FOR_UPDATE", "Call checkForUpdate first!", null);
            throw new IllegalArgumentException(Unit.INSTANCE.toString());
        }
        block.invoke();
    }

    /* JADX INFO: renamed from: de.ffuf.in_app_update.InAppUpdatePlugin$startFlexibleUpdate$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: InAppUpdatePlugin.kt */
    @Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
    static final class C00531 extends Lambda implements Function0<Unit> {
        final /* synthetic */ MethodChannel.Result $result;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00531(MethodChannel.Result result) {
            super(0);
            this.$result = result;
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Unit invoke() throws IntentSender.SendIntentException {
            invoke2();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2() throws IntentSender.SendIntentException {
            InAppUpdatePlugin.this.appUpdateType = 0;
            InAppUpdatePlugin.this.updateResult = this.$result;
            AppUpdateManager appUpdateManager = InAppUpdatePlugin.this.appUpdateManager;
            if (appUpdateManager != null) {
                AppUpdateInfo appUpdateInfo = InAppUpdatePlugin.this.appUpdateInfo;
                Intrinsics.checkNotNull(appUpdateInfo);
                ActivityProvider activityProvider = InAppUpdatePlugin.this.activityProvider;
                Intrinsics.checkNotNull(activityProvider);
                appUpdateManager.startUpdateFlowForResult(appUpdateInfo, activityProvider.activity(), AppUpdateOptions.defaultOptions(0), InAppUpdatePlugin.REQUEST_CODE_START_UPDATE);
            }
            AppUpdateManager appUpdateManager2 = InAppUpdatePlugin.this.appUpdateManager;
            if (appUpdateManager2 != null) {
                final InAppUpdatePlugin inAppUpdatePlugin = InAppUpdatePlugin.this;
                appUpdateManager2.registerListener(new InstallStateUpdatedListener() { // from class: de.ffuf.in_app_update.InAppUpdatePlugin$startFlexibleUpdate$1$$ExternalSyntheticLambda0
                    @Override // com.google.android.play.core.listener.StateUpdatedListener
                    public final void onStateUpdate(InstallState installState) {
                        InAppUpdatePlugin.C00531.invoke$lambda$0(inAppUpdatePlugin, installState);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(InAppUpdatePlugin this$0, InstallState state) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(state, "state");
            this$0.addState(state.installStatus());
            if (state.installStatus() == 11) {
                MethodChannel.Result result = this$0.updateResult;
                if (result != null) {
                    result.success(null);
                }
                this$0.updateResult = null;
                return;
            }
            if (state.installErrorCode() != 0) {
                MethodChannel.Result result2 = this$0.updateResult;
                if (result2 != null) {
                    result2.error("Error during installation", String.valueOf(state.installErrorCode()), null);
                }
                this$0.updateResult = null;
            }
        }
    }

    private final void startFlexibleUpdate(MethodChannel.Result result) {
        checkAppState(result, new C00531(result));
    }

    private final void completeFlexibleUpdate(MethodChannel.Result result) {
        checkAppState(result, new Function0<Unit>() { // from class: de.ffuf.in_app_update.InAppUpdatePlugin.completeFlexibleUpdate.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                AppUpdateManager appUpdateManager = InAppUpdatePlugin.this.appUpdateManager;
                if (appUpdateManager != null) {
                    appUpdateManager.completeUpdate();
                }
            }
        });
    }

    private final void checkForUpdate(final MethodChannel.Result result) {
        Activity activity;
        Application application;
        ActivityProvider activityProvider = this.activityProvider;
        if ((activityProvider != null ? activityProvider.activity() : null) == null) {
            result.error("REQUIRE_FOREGROUND_ACTIVITY", "in_app_update requires a foreground activity", null);
            throw new IllegalArgumentException(Unit.INSTANCE.toString());
        }
        ActivityProvider activityProvider2 = this.activityProvider;
        if (activityProvider2 != null) {
            activityProvider2.addActivityResultListener(this);
        }
        ActivityProvider activityProvider3 = this.activityProvider;
        if (activityProvider3 != null && (activity = activityProvider3.activity()) != null && (application = activity.getApplication()) != null) {
            application.registerActivityLifecycleCallbacks(this);
        }
        ActivityProvider activityProvider4 = this.activityProvider;
        Intrinsics.checkNotNull(activityProvider4);
        AppUpdateManager appUpdateManagerCreate = AppUpdateManagerFactory.create(activityProvider4.activity());
        this.appUpdateManager = appUpdateManagerCreate;
        Intrinsics.checkNotNull(appUpdateManagerCreate);
        Task<AppUpdateInfo> appUpdateInfo = appUpdateManagerCreate.getAppUpdateInfo();
        Intrinsics.checkNotNullExpressionValue(appUpdateInfo, "getAppUpdateInfo(...)");
        final Function1<AppUpdateInfo, Unit> function1 = new Function1<AppUpdateInfo, Unit>() { // from class: de.ffuf.in_app_update.InAppUpdatePlugin.checkForUpdate.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AppUpdateInfo appUpdateInfo2) {
                invoke2(appUpdateInfo2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(AppUpdateInfo appUpdateInfo2) {
                InAppUpdatePlugin.this.appUpdateInfo = appUpdateInfo2;
                MethodChannel.Result result2 = result;
                Pair[] pairArr = new Pair[10];
                pairArr[0] = TuplesKt.to("updateAvailability", Integer.valueOf(appUpdateInfo2.updateAvailability()));
                pairArr[1] = TuplesKt.to("immediateAllowed", Boolean.valueOf(appUpdateInfo2.isUpdateTypeAllowed(1)));
                Set<Integer> failedUpdatePreconditions = appUpdateInfo2.getFailedUpdatePreconditions(AppUpdateOptions.defaultOptions(1));
                Intrinsics.checkNotNullExpressionValue(failedUpdatePreconditions, "getFailedUpdatePreconditions(...)");
                Set<Integer> set = failedUpdatePreconditions;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
                Iterator<T> it = set.iterator();
                while (it.hasNext()) {
                    arrayList.add(Integer.valueOf(((Integer) it.next()).intValue()));
                }
                pairArr[2] = TuplesKt.to("immediateAllowedPreconditions", CollectionsKt.toList(arrayList));
                pairArr[3] = TuplesKt.to("flexibleAllowed", Boolean.valueOf(appUpdateInfo2.isUpdateTypeAllowed(0)));
                Set<Integer> failedUpdatePreconditions2 = appUpdateInfo2.getFailedUpdatePreconditions(AppUpdateOptions.defaultOptions(0));
                Intrinsics.checkNotNullExpressionValue(failedUpdatePreconditions2, "getFailedUpdatePreconditions(...)");
                Set<Integer> set2 = failedUpdatePreconditions2;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(set2, 10));
                Iterator<T> it2 = set2.iterator();
                while (it2.hasNext()) {
                    arrayList2.add(Integer.valueOf(((Integer) it2.next()).intValue()));
                }
                pairArr[4] = TuplesKt.to("flexibleAllowedPreconditions", CollectionsKt.toList(arrayList2));
                pairArr[5] = TuplesKt.to("availableVersionCode", Integer.valueOf(appUpdateInfo2.availableVersionCode()));
                pairArr[6] = TuplesKt.to("installStatus", Integer.valueOf(appUpdateInfo2.installStatus()));
                pairArr[7] = TuplesKt.to("packageName", appUpdateInfo2.packageName());
                pairArr[8] = TuplesKt.to("clientVersionStalenessDays", appUpdateInfo2.clientVersionStalenessDays());
                pairArr[9] = TuplesKt.to("updatePriority", Integer.valueOf(appUpdateInfo2.updatePriority()));
                result2.success(MapsKt.mapOf(pairArr));
            }
        };
        appUpdateInfo.addOnSuccessListener(new OnSuccessListener() { // from class: de.ffuf.in_app_update.InAppUpdatePlugin$$ExternalSyntheticLambda0
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                InAppUpdatePlugin.checkForUpdate$lambda$6(function1, obj);
            }
        });
        appUpdateInfo.addOnFailureListener(new OnFailureListener() { // from class: de.ffuf.in_app_update.InAppUpdatePlugin$$ExternalSyntheticLambda1
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                InAppUpdatePlugin.checkForUpdate$lambda$7(result, exc);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkForUpdate$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkForUpdate$lambda$7(MethodChannel.Result result, Exception it) {
        Intrinsics.checkNotNullParameter(result, "$result");
        Intrinsics.checkNotNullParameter(it, "it");
        result.error("TASK_FAILURE", it.getMessage(), null);
    }
}
