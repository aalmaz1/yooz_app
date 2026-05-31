package com.aboutyou.dart_packages.sign_in_with_apple;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.common.internal.ImagesContract;
import com.tekartik.sqflite.Constant;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SignInWithApplePlugin.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 $2\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004:\u0001$B\u0005¢\u0006\u0002\u0010\u0005J\"\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\b\u001a\u00020\tH\u0016J\u0012\u0010\u0018\u001a\u00020\u00172\b\b\u0001\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0017H\u0016J\b\u0010\u001c\u001a\u00020\u0017H\u0016J\u0012\u0010\u001d\u001a\u00020\u00172\b\b\u0001\u0010\b\u001a\u00020\u001aH\u0016J\u001c\u0010\u001e\u001a\u00020\u00172\b\b\u0001\u0010\u001f\u001a\u00020 2\b\b\u0001\u0010!\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\u00172\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082D¢\u0006\u0002\n\u0000R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/aboutyou/dart_packages/sign_in_with_apple/SignInWithApplePlugin;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", "Lio/flutter/embedding/engine/plugins/activity/ActivityAware;", "Lio/flutter/plugin/common/PluginRegistry$ActivityResultListener;", "()V", "CUSTOM_TABS_REQUEST_CODE", "", "binding", "Lio/flutter/embedding/engine/plugins/activity/ActivityPluginBinding;", "getBinding", "()Lio/flutter/embedding/engine/plugins/activity/ActivityPluginBinding;", "setBinding", "(Lio/flutter/embedding/engine/plugins/activity/ActivityPluginBinding;)V", "channel", "Lio/flutter/plugin/common/MethodChannel;", "onActivityResult", "", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onAttachedToActivity", "", "onAttachedToEngine", "flutterPluginBinding", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onDetachedFromActivity", "onDetachedFromActivityForConfigChanges", "onDetachedFromEngine", "onMethodCall", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", Constant.PARAM_RESULT, "Lio/flutter/plugin/common/MethodChannel$Result;", "onReattachedToActivityForConfigChanges", "Companion", "sign_in_with_apple_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SignInWithApplePlugin implements FlutterPlugin, MethodChannel.MethodCallHandler, ActivityAware, PluginRegistry.ActivityResultListener {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static MethodChannel.Result lastAuthorizationRequestResult;
    private static Function0<Unit> triggerMainActivityToHideChromeCustomTab;
    private final int CUSTOM_TABS_REQUEST_CODE = 1001;
    private ActivityPluginBinding binding;
    private MethodChannel channel;

    @JvmStatic
    public static final void registerWith(PluginRegistry.Registrar registrar) {
        INSTANCE.registerWith(registrar);
    }

    public final ActivityPluginBinding getBinding() {
        return this.binding;
    }

    public final void setBinding(ActivityPluginBinding activityPluginBinding) {
        this.binding = activityPluginBinding;
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        Intrinsics.checkNotNullParameter(flutterPluginBinding, "flutterPluginBinding");
        MethodChannel methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), BuildConfig.LIBRARY_PACKAGE_NAME);
        this.channel = methodChannel;
        methodChannel.setMethodCallHandler(this);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        MethodChannel methodChannel = this.channel;
        if (methodChannel != null) {
            methodChannel.setMethodCallHandler(null);
        }
        this.channel = null;
    }

    /* JADX INFO: compiled from: SignInWithApplePlugin.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0007R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\"\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0013"}, d2 = {"Lcom/aboutyou/dart_packages/sign_in_with_apple/SignInWithApplePlugin$Companion;", "", "()V", "lastAuthorizationRequestResult", "Lio/flutter/plugin/common/MethodChannel$Result;", "getLastAuthorizationRequestResult", "()Lio/flutter/plugin/common/MethodChannel$Result;", "setLastAuthorizationRequestResult", "(Lio/flutter/plugin/common/MethodChannel$Result;)V", "triggerMainActivityToHideChromeCustomTab", "Lkotlin/Function0;", "", "getTriggerMainActivityToHideChromeCustomTab", "()Lkotlin/jvm/functions/Function0;", "setTriggerMainActivityToHideChromeCustomTab", "(Lkotlin/jvm/functions/Function0;)V", "registerWith", "registrar", "Lio/flutter/plugin/common/PluginRegistry$Registrar;", "sign_in_with_apple_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MethodChannel.Result getLastAuthorizationRequestResult() {
            return SignInWithApplePlugin.lastAuthorizationRequestResult;
        }

        public final void setLastAuthorizationRequestResult(MethodChannel.Result result) {
            SignInWithApplePlugin.lastAuthorizationRequestResult = result;
        }

        public final Function0<Unit> getTriggerMainActivityToHideChromeCustomTab() {
            return SignInWithApplePlugin.triggerMainActivityToHideChromeCustomTab;
        }

        public final void setTriggerMainActivityToHideChromeCustomTab(Function0<Unit> function0) {
            SignInWithApplePlugin.triggerMainActivityToHideChromeCustomTab = function0;
        }

        @JvmStatic
        public final void registerWith(PluginRegistry.Registrar registrar) {
            Intrinsics.checkNotNullParameter(registrar, "registrar");
            new MethodChannel(registrar.messenger(), BuildConfig.LIBRARY_PACKAGE_NAME).setMethodCallHandler(new SignInWithApplePlugin());
        }
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        String str = call.method;
        if (Intrinsics.areEqual(str, "isAvailable")) {
            result.success(true);
            return;
        }
        if (Intrinsics.areEqual(str, "performAuthorizationRequest")) {
            ActivityPluginBinding activityPluginBinding = this.binding;
            final Activity activity = activityPluginBinding != null ? activityPluginBinding.getActivity() : null;
            if (activity == null) {
                result.error("MISSING_ACTIVITY", "Plugin is not attached to an activity", call.arguments);
                return;
            }
            String str2 = (String) call.argument(ImagesContract.URL);
            if (str2 == null) {
                result.error("MISSING_ARG", "Missing 'url' argument", call.arguments);
                return;
            }
            MethodChannel.Result result2 = lastAuthorizationRequestResult;
            if (result2 != null) {
                result2.error("NEW_REQUEST", "A new request came in while this was still pending. The previous request (this one) was then cancelled.", null);
            }
            Function0<Unit> function0 = triggerMainActivityToHideChromeCustomTab;
            if (function0 != null) {
                Intrinsics.checkNotNull(function0);
                function0.invoke();
            }
            lastAuthorizationRequestResult = result;
            triggerMainActivityToHideChromeCustomTab = new Function0<Unit>() { // from class: com.aboutyou.dart_packages.sign_in_with_apple.SignInWithApplePlugin.onMethodCall.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                    Intent launchIntentForPackage = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
                    if (launchIntentForPackage != null) {
                        launchIntentForPackage.setPackage(null);
                    }
                    if (launchIntentForPackage != null) {
                        launchIntentForPackage.setFlags(67108864);
                    }
                    activity.startActivity(launchIntentForPackage);
                }
            };
            CustomTabsIntent customTabsIntentBuild = new CustomTabsIntent.Builder().build();
            Intrinsics.checkNotNullExpressionValue(customTabsIntentBuild, "build(...)");
            customTabsIntentBuild.intent.setData(Uri.parse(str2));
            activity.startActivityForResult(customTabsIntentBuild.intent, this.CUSTOM_TABS_REQUEST_CODE, customTabsIntentBuild.startAnimationBundle);
            return;
        }
        result.notImplemented();
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(ActivityPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.binding = binding;
        binding.addActivityResultListener(this);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        onAttachedToActivity(binding);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        ActivityPluginBinding activityPluginBinding = this.binding;
        if (activityPluginBinding != null) {
            activityPluginBinding.removeActivityResultListener(this);
        }
        this.binding = null;
    }

    @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        MethodChannel.Result result;
        if (requestCode != this.CUSTOM_TABS_REQUEST_CODE || (result = lastAuthorizationRequestResult) == null) {
            return false;
        }
        result.error("authorization-error/canceled", "The user closed the Custom Tab", null);
        lastAuthorizationRequestResult = null;
        triggerMainActivityToHideChromeCustomTab = null;
        return false;
    }
}
