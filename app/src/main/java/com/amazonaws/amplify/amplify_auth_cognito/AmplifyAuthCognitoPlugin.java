package com.amazonaws.amplify.amplify_auth_cognito;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.view.WindowMetrics;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsService;
import androidx.webkit.ProxyConfig;
import com.amazonaws.amplify.amplify_auth_cognito.HostedUiException;
import com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge;
import com.google.android.gms.common.internal.ImagesContract;
import io.flutter.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.PluginRegistry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AmplifyAuthCognitoPlugin.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0016\u0018\u0000 S2\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005:\u0001SB\u0005¢\u0006\u0002\u0010\u0006J\b\u0010*\u001a\u00020(H\u0002J\"\u0010+\u001a\u00020(2\u0018\u0010,\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0&\u0012\u0004\u0012\u00020(0%H\u0016J2\u0010-\u001a\u00020(2\u0006\u0010.\u001a\u00020\u00122\u0006\u0010/\u001a\u00020\u00122\u0018\u0010,\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0&\u0012\u0004\u0012\u00020(0%H\u0016J4\u00100\u001a\u00020(2\u0006\u0010.\u001a\u00020\u00122\u0006\u0010/\u001a\u00020\u00122\u001a\u0010,\u001a\u0016\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001010&\u0012\u0004\u0012\u00020(0%H\u0016J\b\u00102\u001a\u00020\u0012H\u0016J\b\u00103\u001a\u000204H\u0016J6\u00105\u001a\u00020(2\b\u00106\u001a\u0004\u0018\u00010\u00122\b\u00107\u001a\u0004\u0018\u00010\u00122\u0018\u0010,\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002080&\u0012\u0004\u0012\u00020(0%H\u0016J\u0014\u00109\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120'H\u0016J\u001c\u0010:\u001a\u00020;2\u0012\u0010<\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120'H\u0002J\b\u0010=\u001a\u00020;H\u0002J\u001a\u0010>\u001a\u00020(2\u0006\u0010?\u001a\u00020\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\"\u0010@\u001a\u00020;2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020B2\b\u0010D\u001a\u0004\u0018\u00010EH\u0016J\u0010\u0010F\u001a\u00020(2\u0006\u0010G\u001a\u00020\bH\u0016J\u0010\u0010H\u001a\u00020(2\u0006\u0010G\u001a\u00020IH\u0016J\b\u0010J\u001a\u00020(H\u0016J\b\u0010K\u001a\u00020(H\u0016J\u0010\u0010L\u001a\u00020(2\u0006\u0010G\u001a\u00020IH\u0016J\u0010\u0010M\u001a\u00020;2\u0006\u0010D\u001a\u00020EH\u0016J\u0010\u0010N\u001a\u00020(2\u0006\u0010G\u001a\u00020\bH\u0016JP\u0010O\u001a\u00020(2\u0006\u0010?\u001a\u00020\u00122\u0006\u0010P\u001a\u00020\u00122\u0006\u0010Q\u001a\u00020;2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122$\u0010,\u001a \u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120\u00170&\u0012\u0004\u0012\u00020(0%H\u0016JD\u0010R\u001a\u00020(2\u0006\u0010?\u001a\u00020\u00122\u0006\u0010P\u001a\u00020\u00122\u0006\u0010Q\u001a\u00020;2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0018\u0010,\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0&\u0012\u0004\u0012\u00020(0%H\u0016R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u001d\u0010\u0011\u001a\u0004\u0018\u00010\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0010\u001a\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0018\u001a\u00020\u00198BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u0010\u001a\u0004\b\u001a\u0010\u001bR\u001b\u0010\u001d\u001a\u00020\u00198BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\u0010\u001a\u0004\b\u001e\u0010\u001bR\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000R.\u0010$\u001a\"\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120'0&\u0012\u0004\u0012\u00020(\u0018\u00010%X\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010)\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0&\u0012\u0004\u0012\u00020(\u0018\u00010%X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006T"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/AmplifyAuthCognitoPlugin;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "Lio/flutter/embedding/engine/plugins/activity/ActivityAware;", "Lio/flutter/plugin/common/PluginRegistry$NewIntentListener;", "Lio/flutter/plugin/common/PluginRegistry$ActivityResultListener;", "Lcom/amazonaws/amplify/amplify_auth_cognito/NativeAuthBridge;", "()V", "activityBinding", "Lio/flutter/embedding/engine/plugins/activity/ActivityPluginBinding;", "applicationContext", "Landroid/content/Context;", "asfDeviceSecretsStore", "Landroid/content/SharedPreferences;", "getAsfDeviceSecretsStore", "()Landroid/content/SharedPreferences;", "asfDeviceSecretsStore$delegate", "Lkotlin/Lazy;", "browserPackageName", "", "getBrowserPackageName", "()Ljava/lang/String;", "browserPackageName$delegate", "initialParameters", "", "legacyIdentityStore", "Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyKeyValueStore;", "getLegacyIdentityStore", "()Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyKeyValueStore;", "legacyIdentityStore$delegate", "legacyUserPoolStore", "getLegacyUserPoolStore", "legacyUserPoolStore$delegate", "mainActivity", "Landroid/app/Activity;", "nativePlugin", "Lcom/amazonaws/amplify/amplify_auth_cognito/NativeAuthPlugin;", "signInResult", "Lkotlin/Function1;", "Lkotlin/Result;", "", "", "signOutResult", "cancelCurrentOperation", "clearLegacyCredentials", "callback", "deleteLegacyDeviceSecrets", "username", "userPoolId", "fetchLegacyDeviceSecrets", "Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyDeviceDetailsSecret;", "getBundleId", "getContextData", "Lcom/amazonaws/amplify/amplify_auth_cognito/NativeUserContextData;", "getLegacyCredentials", "identityPoolId", "appClientId", "Lcom/amazonaws/amplify/amplify_auth_cognito/LegacyCredentialStoreData;", "getValidationData", "handleSignInResult", "", "queryParameters", "handleSignOutResult", "launchUrl", ImagesContract.URL, "onActivityResult", "requestCode", "", "resultCode", "intent", "Landroid/content/Intent;", "onAttachedToActivity", "binding", "onAttachedToEngine", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onDetachedFromActivity", "onDetachedFromActivityForConfigChanges", "onDetachedFromEngine", "onNewIntent", "onReattachedToActivityForConfigChanges", "signInWithUrl", "callbackUrlScheme", "preferPrivateSession", "signOutWithUrl", "Companion", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class AmplifyAuthCognitoPlugin implements FlutterPlugin, ActivityAware, PluginRegistry.NewIntentListener, PluginRegistry.ActivityResultListener, NativeAuthBridge {

    @Deprecated
    public static final String CUSTOM_TAB_CANCEL_EXTRA = "com.amazonaws.amplify.auth.hosted_ui.cancel";

    @Deprecated
    public static final int CUSTOM_TAB_REQUEST_CODE = 8888;
    private static final Companion Companion = new Companion(null);

    @Deprecated
    public static final String TAG = "AmplifyAuthCognitoPlugin";
    private ActivityPluginBinding activityBinding;
    private Context applicationContext;
    private Map<String, String> initialParameters;
    private Activity mainActivity;
    private NativeAuthPlugin nativePlugin;
    private Function1<? super Result<? extends Map<String, String>>, Unit> signInResult;
    private Function1<? super Result<Unit>, Unit> signOutResult;

    /* JADX INFO: renamed from: legacyUserPoolStore$delegate, reason: from kotlin metadata */
    private final Lazy legacyUserPoolStore = LazyKt.lazy(new Function0<LegacyKeyValueStore>() { // from class: com.amazonaws.amplify.amplify_auth_cognito.AmplifyAuthCognitoPlugin$legacyUserPoolStore$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final LegacyKeyValueStore invoke() {
            Context context = this.this$0.applicationContext;
            Intrinsics.checkNotNull(context);
            return new LegacyKeyValueStore(context, "CognitoIdentityProviderCache");
        }
    });

    /* JADX INFO: renamed from: legacyIdentityStore$delegate, reason: from kotlin metadata */
    private final Lazy legacyIdentityStore = LazyKt.lazy(new Function0<LegacyKeyValueStore>() { // from class: com.amazonaws.amplify.amplify_auth_cognito.AmplifyAuthCognitoPlugin$legacyIdentityStore$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final LegacyKeyValueStore invoke() {
            Context context = this.this$0.applicationContext;
            Intrinsics.checkNotNull(context);
            return new LegacyKeyValueStore(context, "com.amazonaws.android.auth");
        }
    });

    /* JADX INFO: renamed from: asfDeviceSecretsStore$delegate, reason: from kotlin metadata */
    private final Lazy asfDeviceSecretsStore = LazyKt.lazy(new Function0<SharedPreferences>() { // from class: com.amazonaws.amplify.amplify_auth_cognito.AmplifyAuthCognitoPlugin$asfDeviceSecretsStore$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final SharedPreferences invoke() {
            Context context = this.this$0.applicationContext;
            Intrinsics.checkNotNull(context);
            return context.getSharedPreferences("AWS.Cognito.ContextData", 0);
        }
    });

    /* JADX INFO: renamed from: browserPackageName$delegate, reason: from kotlin metadata */
    private final Lazy browserPackageName = LazyKt.lazy(new Function0<String>() { // from class: com.amazonaws.amplify.amplify_auth_cognito.AmplifyAuthCognitoPlugin$browserPackageName$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final String invoke() {
            ResolveInfo resolveInfoResolveActivity;
            List<ResolveInfo> listQueryIntentActivities;
            ResolveInfo resolveInfoResolveService;
            Activity activity = this.this$0.mainActivity;
            Intrinsics.checkNotNull(activity);
            PackageManager packageManager = activity.getPackageManager();
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.BROWSABLE");
            intent.setData(Uri.fromParts(ProxyConfig.MATCH_HTTPS, "", null));
            if (Build.VERSION.SDK_INT >= 33) {
                resolveInfoResolveActivity = packageManager.resolveActivity(intent, PackageManager.ResolveInfoFlags.of(65536L));
            } else {
                resolveInfoResolveActivity = packageManager.resolveActivity(intent, 65536);
            }
            Log.d(AmplifyAuthCognitoPlugin.TAG, "[browserPackageName] Resolved activity info: " + resolveInfoResolveActivity);
            String str = resolveInfoResolveActivity != null ? resolveInfoResolveActivity.activityInfo.packageName : null;
            Log.d(AmplifyAuthCognitoPlugin.TAG, "[browserPackageName] Resolved default package: " + str);
            if (Build.VERSION.SDK_INT >= 33) {
                listQueryIntentActivities = packageManager.queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(131072L));
            } else {
                listQueryIntentActivities = packageManager.queryIntentActivities(intent, 131072);
            }
            Intrinsics.checkNotNull(listQueryIntentActivities);
            ArrayList arrayList = new ArrayList();
            for (ResolveInfo resolveInfo : listQueryIntentActivities) {
                Intent intent2 = new Intent();
                intent2.setAction(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION);
                intent2.setPackage(resolveInfo.activityInfo.packageName);
                if (Build.VERSION.SDK_INT >= 33) {
                    resolveInfoResolveService = packageManager.resolveService(intent2, PackageManager.ResolveInfoFlags.of(0L));
                } else {
                    resolveInfoResolveService = packageManager.resolveService(intent2, 0);
                }
                if (resolveInfoResolveService != null) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    Intrinsics.checkNotNullExpressionValue(packageName, "packageName");
                    arrayList.add(packageName);
                }
            }
            Log.d(AmplifyAuthCognitoPlugin.TAG, "[browserPackageName] Resolved custom tabs handlers: " + arrayList);
            if (arrayList.isEmpty()) {
                return null;
            }
            return (str == null || !arrayList.contains(str)) ? (String) arrayList.get(0) : str;
        }
    });

    /* JADX INFO: compiled from: AmplifyAuthCognitoPlugin.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/amazonaws/amplify/amplify_auth_cognito/AmplifyAuthCognitoPlugin$Companion;", "", "()V", "CUSTOM_TAB_CANCEL_EXTRA", "", "CUSTOM_TAB_REQUEST_CODE", "", "TAG", "amplify_auth_cognito_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private final LegacyKeyValueStore getLegacyUserPoolStore() {
        return (LegacyKeyValueStore) this.legacyUserPoolStore.getValue();
    }

    private final LegacyKeyValueStore getLegacyIdentityStore() {
        return (LegacyKeyValueStore) this.legacyIdentityStore.getValue();
    }

    private final SharedPreferences getAsfDeviceSecretsStore() {
        Object value = this.asfDeviceSecretsStore.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (SharedPreferences) value;
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        Log.d(TAG, "onAttachedToEngine");
        this.applicationContext = binding.getApplicationContext();
        BinaryMessenger binaryMessenger = binding.getBinaryMessenger();
        Intrinsics.checkNotNullExpressionValue(binaryMessenger, "getBinaryMessenger(...)");
        this.nativePlugin = new NativeAuthPlugin(binaryMessenger);
        NativeAuthBridge.Companion companion = NativeAuthBridge.INSTANCE;
        BinaryMessenger binaryMessenger2 = binding.getBinaryMessenger();
        Intrinsics.checkNotNullExpressionValue(binaryMessenger2, "getBinaryMessenger(...)");
        companion.setUp(binaryMessenger2, this);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        Log.d(TAG, "onDetachedFromEngine");
        this.applicationContext = null;
        cancelCurrentOperation();
        this.nativePlugin = null;
        NativeAuthBridge.Companion companion = NativeAuthBridge.INSTANCE;
        BinaryMessenger binaryMessenger = binding.getBinaryMessenger();
        Intrinsics.checkNotNullExpressionValue(binaryMessenger, "getBinaryMessenger(...)");
        companion.setUp(binaryMessenger, null);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(ActivityPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        Log.d(TAG, "onAttachedToActivity");
        this.mainActivity = binding.getActivity();
        this.activityBinding = binding;
        Intent intent = binding.getActivity().getIntent();
        Intrinsics.checkNotNullExpressionValue(intent, "getIntent(...)");
        onNewIntent(intent);
        binding.addOnNewIntentListener(this);
        binding.addActivityResultListener(this);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        Log.d(TAG, "onDetachedFromActivityForConfigChanges");
        ActivityPluginBinding activityPluginBinding = this.activityBinding;
        if (activityPluginBinding != null) {
            activityPluginBinding.removeActivityResultListener(this);
        }
        ActivityPluginBinding activityPluginBinding2 = this.activityBinding;
        if (activityPluginBinding2 != null) {
            activityPluginBinding2.removeOnNewIntentListener(this);
        }
        this.activityBinding = null;
        this.mainActivity = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        Log.d(TAG, "onReattachedToActivityForConfigChanges");
        this.mainActivity = binding.getActivity();
        this.activityBinding = binding;
        binding.addOnNewIntentListener(this);
        binding.addActivityResultListener(this);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        Log.d(TAG, "onDetachedFromActivity");
        ActivityPluginBinding activityPluginBinding = this.activityBinding;
        if (activityPluginBinding != null) {
            activityPluginBinding.removeActivityResultListener(this);
        }
        ActivityPluginBinding activityPluginBinding2 = this.activityBinding;
        if (activityPluginBinding2 != null) {
            activityPluginBinding2.removeOnNewIntentListener(this);
        }
        this.activityBinding = null;
        this.mainActivity = null;
    }

    @Override // com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge
    public Map<String, String> getValidationData() {
        return new LinkedHashMap();
    }

    @Override // com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge
    public NativeUserContextData getContextData() {
        String str;
        int iHeight;
        int iWidth;
        Context context = this.applicationContext;
        Intrinsics.checkNotNull(context);
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        Context context2 = this.applicationContext;
        Intrinsics.checkNotNull(context2);
        PackageManager packageManager = context2.getPackageManager();
        Context context3 = this.applicationContext;
        Intrinsics.checkNotNull(context3);
        String packageName = context3.getPackageName();
        String str2 = Build.DEVICE;
        String str3 = Build.FINGERPRINT;
        String string = packageManager.getApplicationLabel(applicationInfo).toString();
        try {
            if (Build.VERSION.SDK_INT >= 33) {
                str = packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0L)).versionName;
            } else {
                str = packageManager.getPackageInfo(packageName, 0).versionName;
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w(TAG, "Unable to get app version for package: " + packageName);
            str = null;
        }
        String str4 = str;
        String str5 = Build.VERSION.RELEASE;
        String languageTag = Locale.getDefault().toLanguageTag();
        if (Build.VERSION.SDK_INT >= 30) {
            Activity activity = this.mainActivity;
            Intrinsics.checkNotNull(activity);
            WindowMetrics currentWindowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
            Intrinsics.checkNotNullExpressionValue(currentWindowMetrics, "getCurrentWindowMetrics(...)");
            iHeight = currentWindowMetrics.getBounds().height();
        } else {
            Activity activity2 = this.mainActivity;
            Intrinsics.checkNotNull(activity2);
            iHeight = activity2.getResources().getDisplayMetrics().heightPixels;
        }
        if (Build.VERSION.SDK_INT >= 30) {
            Activity activity3 = this.mainActivity;
            Intrinsics.checkNotNull(activity3);
            WindowMetrics currentWindowMetrics2 = activity3.getWindowManager().getCurrentWindowMetrics();
            Intrinsics.checkNotNullExpressionValue(currentWindowMetrics2, "getCurrentWindowMetrics(...)");
            iWidth = currentWindowMetrics2.getBounds().width();
        } else {
            Activity activity4 = this.mainActivity;
            Intrinsics.checkNotNull(activity4);
            iWidth = activity4.getResources().getDisplayMetrics().widthPixels;
        }
        return new NativeUserContextData(str2, "android_id", str3, string, str4, languageTag, str5, Long.valueOf(iHeight), Long.valueOf(iWidth));
    }

    @Override // com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge
    public String getBundleId() {
        Context context = this.applicationContext;
        Intrinsics.checkNotNull(context);
        String packageName = context.getPackageName();
        Intrinsics.checkNotNullExpressionValue(packageName, "getPackageName(...)");
        return packageName;
    }

    @Override // com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge
    public void getLegacyCredentials(String identityPoolId, String appClientId, Function1<? super Result<LegacyCredentialStoreData>, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        LegacyCredentialStoreDataBuilder legacyCredentialStoreDataBuilder = new LegacyCredentialStoreDataBuilder(null, null, null, null, null, null, null, null, 255, null);
        if (appClientId != null) {
            String str = getLegacyUserPoolStore().get("CognitoIdentityProvider." + appClientId + ".LastAuthUser");
            String str2 = getLegacyUserPoolStore().get("CognitoIdentityProvider." + appClientId + "." + str + ".accessToken");
            String str3 = getLegacyUserPoolStore().get("CognitoIdentityProvider." + appClientId + "." + str + ".refreshToken");
            String str4 = getLegacyUserPoolStore().get("CognitoIdentityProvider." + appClientId + "." + str + ".idToken");
            legacyCredentialStoreDataBuilder.setAccessToken(str2);
            legacyCredentialStoreDataBuilder.setRefreshToken(str3);
            legacyCredentialStoreDataBuilder.setIdToken(str4);
        }
        if (identityPoolId != null) {
            String str5 = getLegacyIdentityStore().get(identityPoolId + ".accessKey");
            String str6 = getLegacyIdentityStore().get(identityPoolId + ".secretKey");
            String str7 = getLegacyIdentityStore().get(identityPoolId + ".sessionToken");
            String str8 = getLegacyIdentityStore().get(identityPoolId + ".expirationDate");
            legacyCredentialStoreDataBuilder.setIdentityId(getLegacyIdentityStore().get(identityPoolId + ".identityId"));
            legacyCredentialStoreDataBuilder.setAccessKeyId(str5);
            legacyCredentialStoreDataBuilder.setSecretAccessKey(str6);
            legacyCredentialStoreDataBuilder.setSessionToken(str7);
            legacyCredentialStoreDataBuilder.setExpirationMsSinceEpoch(str8 != null ? Long.valueOf(Long.parseLong(str8)) : null);
        }
        Result.Companion companion = Result.INSTANCE;
        callback.invoke(Result.m683boximpl(Result.m684constructorimpl(legacyCredentialStoreDataBuilder.build())));
    }

    @Override // com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge
    public void fetchLegacyDeviceSecrets(String username, String userPoolId, Function1<? super Result<LegacyDeviceDetailsSecret>, Unit> callback) {
        Intrinsics.checkNotNullParameter(username, "username");
        Intrinsics.checkNotNullParameter(userPoolId, "userPoolId");
        Intrinsics.checkNotNullParameter(callback, "callback");
        LegacyDeviceDetailsBuilder legacyDeviceDetailsBuilder = new LegacyDeviceDetailsBuilder(null, null, null, null, 15, null);
        Context context = this.applicationContext;
        Intrinsics.checkNotNull(context);
        LegacyKeyValueStore legacyKeyValueStore = new LegacyKeyValueStore(context, "CognitoIdentityProviderDeviceCache." + userPoolId + "." + username);
        String str = legacyKeyValueStore.get("DeviceKey");
        String str2 = legacyKeyValueStore.get("DeviceSecret");
        String str3 = legacyKeyValueStore.get("DeviceGroupKey");
        legacyDeviceDetailsBuilder.setDeviceKey(str);
        legacyDeviceDetailsBuilder.setDeviceSecret(str2);
        legacyDeviceDetailsBuilder.setDeviceGroupKey(str3);
        legacyDeviceDetailsBuilder.setAsfDeviceId(getAsfDeviceSecretsStore().getString("CognitoDeviceId", null));
        Result.Companion companion = Result.INSTANCE;
        callback.invoke(Result.m683boximpl(Result.m684constructorimpl(legacyDeviceDetailsBuilder.build())));
    }

    @Override // com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge
    public void deleteLegacyDeviceSecrets(String username, String userPoolId, Function1<? super Result<Unit>, Unit> callback) {
        Intrinsics.checkNotNullParameter(username, "username");
        Intrinsics.checkNotNullParameter(userPoolId, "userPoolId");
        Intrinsics.checkNotNullParameter(callback, "callback");
        Context context = this.applicationContext;
        Intrinsics.checkNotNull(context);
        new LegacyKeyValueStore(context, "CognitoIdentityProviderDeviceCache." + userPoolId + "." + username).clear();
        getAsfDeviceSecretsStore().edit().clear().apply();
        Result.Companion companion = Result.INSTANCE;
        callback.invoke(Result.m683boximpl(Result.m684constructorimpl(Unit.INSTANCE)));
    }

    @Override // com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge
    public void clearLegacyCredentials(Function1<? super Result<Unit>, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        getLegacyUserPoolStore().clear();
        getLegacyIdentityStore().clear();
        Result.Companion companion = Result.INSTANCE;
        callback.invoke(Result.m683boximpl(Result.m684constructorimpl(Unit.INSTANCE)));
    }

    private final boolean handleSignInResult(Map<String, String> queryParameters) {
        Log.d(TAG, "handleSignInResult: " + queryParameters + " (signInResult=" + this.signInResult + ")");
        Function1<? super Result<? extends Map<String, String>>, Unit> function1 = this.signInResult;
        if (function1 != null) {
            Result.Companion companion = Result.INSTANCE;
            function1.invoke(Result.m683boximpl(Result.m684constructorimpl(queryParameters)));
        }
        this.signInResult = null;
        return true;
    }

    private final boolean handleSignOutResult() {
        Log.d(TAG, "handleSignOutResult (signOutResult=" + this.signOutResult + ")");
        Function1<? super Result<Unit>, Unit> function1 = this.signOutResult;
        if (function1 != null) {
            Result.Companion companion = Result.INSTANCE;
            function1.invoke(Result.m683boximpl(Result.m684constructorimpl(Unit.INSTANCE)));
        }
        this.signOutResult = null;
        return true;
    }

    private final String getBrowserPackageName() {
        return (String) this.browserPackageName.getValue();
    }

    public void launchUrl(String url, String browserPackageName) throws HostedUiException.NOBROWSER, HostedUiException.UNKNOWN {
        Intrinsics.checkNotNullParameter(url, "url");
        if (this.mainActivity == null) {
            throw new HostedUiException.UNKNOWN("No activity found");
        }
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setShareState(2);
        CustomTabsIntent customTabsIntentBuild = builder.build();
        Intrinsics.checkNotNullExpressionValue(customTabsIntentBuild, "build(...)");
        if (browserPackageName == null && (browserPackageName = getBrowserPackageName()) == null) {
            throw new HostedUiException.NOBROWSER();
        }
        Log.d(TAG, "[launchUrl] Using browser package: " + browserPackageName);
        customTabsIntentBuild.intent.setPackage(browserPackageName);
        Intent intent = customTabsIntentBuild.intent;
        Activity activity = this.mainActivity;
        Intrinsics.checkNotNull(activity);
        intent.putExtra("android.intent.extra.REFERRER", Uri.parse("android-app://" + activity.getPackageName()));
        customTabsIntentBuild.intent.setData(Uri.parse(url));
        Activity activity2 = this.mainActivity;
        Intrinsics.checkNotNull(activity2);
        activity2.startActivityForResult(customTabsIntentBuild.intent, CUSTOM_TAB_REQUEST_CODE);
    }

    @Override // com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge
    public void signInWithUrl(String url, String callbackUrlScheme, boolean preferPrivateSession, String browserPackageName, Function1<? super Result<? extends Map<String, String>>, Unit> callback) {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(callbackUrlScheme, "callbackUrlScheme");
        Intrinsics.checkNotNullParameter(callback, "callback");
        AtomicResult atomicResult = new AtomicResult(callback, "signIn");
        try {
            launchUrl(url, browserPackageName);
            this.signInResult = atomicResult;
        } catch (Throwable th) {
            Result.Companion companion = Result.INSTANCE;
            atomicResult.invoke2(Result.m684constructorimpl(ResultKt.createFailure(HostedUiException.INSTANCE.fromThrowable(th))));
        }
    }

    @Override // com.amazonaws.amplify.amplify_auth_cognito.NativeAuthBridge
    public void signOutWithUrl(String url, String callbackUrlScheme, boolean preferPrivateSession, String browserPackageName, Function1<? super Result<Unit>, Unit> callback) {
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(callbackUrlScheme, "callbackUrlScheme");
        Intrinsics.checkNotNullParameter(callback, "callback");
        AtomicResult atomicResult = new AtomicResult(callback, "signOut");
        try {
            launchUrl(url, browserPackageName);
            this.signOutResult = atomicResult;
        } catch (Throwable th) {
            Result.Companion companion = Result.INSTANCE;
            atomicResult.invoke2(Result.m684constructorimpl(ResultKt.createFailure(HostedUiException.INSTANCE.fromThrowable(th))));
        }
    }

    @Override // io.flutter.plugin.common.PluginRegistry.NewIntentListener
    public boolean onNewIntent(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        Log.d(TAG, "[onNewIntent] Got intent: " + intent);
        if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.VIEW") && intent.hasCategory("android.intent.category.BROWSABLE")) {
            Uri data = intent.getData();
            if (data == null) {
                Log.e(TAG, "No data associated with intent");
                return false;
            }
            Map<String, String> queryParameters = AmplifyAuthCognitoPluginKt.getQueryParameters(data);
            Log.d(TAG, "[onNewIntent] Handling intent with query parameters: " + queryParameters + " (signInResult=" + this.signInResult + ", signOutResult=" + this.signOutResult + ")");
            Function1<? super Result<? extends Map<String, String>>, Unit> function1 = this.signInResult;
            if (function1 != null && this.signOutResult != null) {
                Log.e(TAG, "Inconsistent state. Pending sign in and sign out.");
                return false;
            }
            if (function1 != null) {
                return handleSignInResult(queryParameters);
            }
            if (this.signOutResult != null) {
                return handleSignOutResult();
            }
            if (!(!queryParameters.isEmpty())) {
                return true;
            }
            this.initialParameters = queryParameters;
            return true;
        }
        if (intent.hasExtra(CUSTOM_TAB_CANCEL_EXTRA)) {
            Log.d(TAG, "[onNewIntent] Cancelling current operation");
            cancelCurrentOperation();
            return true;
        }
        Log.d(TAG, "[onNewIntent] Not handling intent");
        return false;
    }

    @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
    public boolean onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d(TAG, "[onActivityResult] Got result: requestCode=" + requestCode + ", resultCode=" + resultCode + ", intent=" + intent);
        if (requestCode != 8888) {
            return false;
        }
        Context context = this.applicationContext;
        Intrinsics.checkNotNull(context);
        Activity activity = this.mainActivity;
        Intrinsics.checkNotNull(activity);
        Intent intent2 = new Intent(context, activity.getClass());
        intent2.addFlags(268435456);
        intent2.putExtra(CUSTOM_TAB_CANCEL_EXTRA, true);
        Context context2 = this.applicationContext;
        Intrinsics.checkNotNull(context2);
        context2.startActivity(intent2);
        return true;
    }

    private final void cancelCurrentOperation() {
        Log.d(TAG, "[cancelCurrentOperation] Canceling with state: signInResult=" + this.signInResult + ", signOutResult=" + this.signOutResult);
        Function1<? super Result<? extends Map<String, String>>, Unit> function1 = this.signInResult;
        if (function1 == null) {
            Function1<? super Result<Unit>, Unit> function12 = this.signOutResult;
            if (function12 != null && function12 != null) {
                Result.Companion companion = Result.INSTANCE;
                function12.invoke(Result.m683boximpl(Result.m684constructorimpl(ResultKt.createFailure(new HostedUiException.CANCELLED()))));
            }
        } else if (function1 != null) {
            Result.Companion companion2 = Result.INSTANCE;
            function1.invoke(Result.m683boximpl(Result.m684constructorimpl(ResultKt.createFailure(new HostedUiException.CANCELLED()))));
        }
        this.signInResult = null;
        this.signOutResult = null;
    }
}
