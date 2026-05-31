package com.baseflow.permissionhandler;

import android.app.Activity;
import android.content.Context;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;

/* JADX INFO: loaded from: classes.dex */
public final class PermissionHandlerPlugin implements FlutterPlugin, ActivityAware {
    private MethodCallHandlerImpl methodCallHandler;
    private MethodChannel methodChannel;
    private PermissionManager permissionManager;
    private ActivityPluginBinding pluginBinding;

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.permissionManager = new PermissionManager(flutterPluginBinding.getApplicationContext());
        startListening(flutterPluginBinding.getApplicationContext(), flutterPluginBinding.getBinaryMessenger());
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        stopListening();
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(ActivityPluginBinding activityPluginBinding) {
        startListeningToActivity(activityPluginBinding.getActivity());
        this.pluginBinding = activityPluginBinding;
        registerListeners();
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding activityPluginBinding) {
        onAttachedToActivity(activityPluginBinding);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        stopListeningToActivity();
        deregisterListeners();
        this.pluginBinding = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity();
    }

    private void startListening(Context context, BinaryMessenger binaryMessenger) {
        this.methodChannel = new MethodChannel(binaryMessenger, "flutter.baseflow.com/permissions/methods");
        MethodCallHandlerImpl methodCallHandlerImpl = new MethodCallHandlerImpl(context, new AppSettingsManager(), this.permissionManager, new ServiceManager());
        this.methodCallHandler = methodCallHandlerImpl;
        this.methodChannel.setMethodCallHandler(methodCallHandlerImpl);
    }

    private void stopListening() {
        this.methodChannel.setMethodCallHandler(null);
        this.methodChannel = null;
        this.methodCallHandler = null;
    }

    private void startListeningToActivity(Activity activity) {
        PermissionManager permissionManager = this.permissionManager;
        if (permissionManager != null) {
            permissionManager.setActivity(activity);
        }
    }

    private void stopListeningToActivity() {
        PermissionManager permissionManager = this.permissionManager;
        if (permissionManager != null) {
            permissionManager.setActivity(null);
        }
    }

    private void registerListeners() {
        ActivityPluginBinding activityPluginBinding = this.pluginBinding;
        if (activityPluginBinding != null) {
            activityPluginBinding.addActivityResultListener(this.permissionManager);
            this.pluginBinding.addRequestPermissionsResultListener(this.permissionManager);
        }
    }

    private void deregisterListeners() {
        ActivityPluginBinding activityPluginBinding = this.pluginBinding;
        if (activityPluginBinding != null) {
            activityPluginBinding.removeActivityResultListener(this.permissionManager);
            this.pluginBinding.removeRequestPermissionsResultListener(this.permissionManager);
        }
    }
}
