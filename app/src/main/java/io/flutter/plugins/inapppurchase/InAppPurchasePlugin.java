package io.flutter.plugins.inapppurchase;

import android.app.Application;
import android.content.Context;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugins.inapppurchase.Messages;

/* JADX INFO: loaded from: classes2.dex */
public class InAppPurchasePlugin implements FlutterPlugin, ActivityAware {
    static final String PROXY_PACKAGE_KEY = "PROXY_PACKAGE";
    static final String PROXY_VALUE = "io.flutter.plugins.inapppurchase";
    private MethodCallHandlerImpl methodCallHandler;

    public static void registerWith(PluginRegistry.Registrar registrar) {
        InAppPurchasePlugin inAppPurchasePlugin = new InAppPurchasePlugin();
        registrar.activity().getIntent().putExtra(PROXY_PACKAGE_KEY, "io.flutter.plugins.inapppurchase");
        ((Application) registrar.context().getApplicationContext()).registerActivityLifecycleCallbacks(inAppPurchasePlugin.methodCallHandler);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        setUpMethodChannel(flutterPluginBinding.getBinaryMessenger(), flutterPluginBinding.getApplicationContext());
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        teardownMethodChannel(flutterPluginBinding.getBinaryMessenger());
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(ActivityPluginBinding activityPluginBinding) {
        activityPluginBinding.getActivity().getIntent().putExtra(PROXY_PACKAGE_KEY, "io.flutter.plugins.inapppurchase");
        this.methodCallHandler.setActivity(activityPluginBinding.getActivity());
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        this.methodCallHandler.setActivity(null);
        this.methodCallHandler.onDetachedFromActivity();
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding activityPluginBinding) {
        onAttachedToActivity(activityPluginBinding);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        this.methodCallHandler.setActivity(null);
    }

    private void setUpMethodChannel(BinaryMessenger binaryMessenger, Context context) {
        MethodCallHandlerImpl methodCallHandlerImpl = new MethodCallHandlerImpl(null, context, new Messages.InAppPurchaseCallbackApi(binaryMessenger), new BillingClientFactoryImpl());
        this.methodCallHandler = methodCallHandlerImpl;
        Messages.InAppPurchaseApi.setUp(binaryMessenger, methodCallHandlerImpl);
    }

    private void teardownMethodChannel(BinaryMessenger binaryMessenger) {
        Messages.InAppPurchaseApi.setUp(binaryMessenger, null);
        this.methodCallHandler = null;
    }

    void setMethodCallHandler(MethodCallHandlerImpl methodCallHandlerImpl) {
        this.methodCallHandler = methodCallHandlerImpl;
    }
}
