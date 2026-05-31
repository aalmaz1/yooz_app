package com.fluttercandies.photo_manager;

import android.content.Context;
import com.fluttercandies.photo_manager.PhotoManagerPlugin;
import com.fluttercandies.photo_manager.permission.PermissionsUtils;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PhotoManagerPlugin.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u00182\u00020\u00012\u00020\u0002:\u0001\u0018B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u0010\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u0010\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\rH\u0016J\b\u0010\u0013\u001a\u00020\rH\u0016J\u0010\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0011H\u0016J\u0010\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u0005H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/fluttercandies/photo_manager/PhotoManagerPlugin;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "Lio/flutter/embedding/engine/plugins/activity/ActivityAware;", "()V", "binding", "Lio/flutter/embedding/engine/plugins/activity/ActivityPluginBinding;", "permissionsUtils", "Lcom/fluttercandies/photo_manager/permission/PermissionsUtils;", "plugin", "Lcom/fluttercandies/photo_manager/core/PhotoManagerPlugin;", "requestPermissionsResultListener", "Lio/flutter/plugin/common/PluginRegistry$RequestPermissionsResultListener;", "activityAttached", "", "addRequestPermissionsResultListener", "onAttachedToActivity", "onAttachedToEngine", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onDetachedFromActivity", "onDetachedFromActivityForConfigChanges", "onDetachedFromEngine", "onReattachedToActivityForConfigChanges", "onRemoveRequestPermissionResultListener", "oldBinding", "Companion", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PhotoManagerPlugin implements FlutterPlugin, ActivityAware {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private ActivityPluginBinding binding;
    private final PermissionsUtils permissionsUtils = new PermissionsUtils();
    private com.fluttercandies.photo_manager.core.PhotoManagerPlugin plugin;
    private PluginRegistry.RequestPermissionsResultListener requestPermissionsResultListener;

    /* JADX INFO: compiled from: PhotoManagerPlugin.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f¨\u0006\r"}, d2 = {"Lcom/fluttercandies/photo_manager/PhotoManagerPlugin$Companion;", "", "()V", "createAddRequestPermissionsResultListener", "Lio/flutter/plugin/common/PluginRegistry$RequestPermissionsResultListener;", "permissionsUtils", "Lcom/fluttercandies/photo_manager/permission/PermissionsUtils;", "register", "", "plugin", "Lcom/fluttercandies/photo_manager/core/PhotoManagerPlugin;", "messenger", "Lio/flutter/plugin/common/BinaryMessenger;", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void register(com.fluttercandies.photo_manager.core.PhotoManagerPlugin plugin, BinaryMessenger messenger) {
            Intrinsics.checkNotNullParameter(plugin, "plugin");
            Intrinsics.checkNotNullParameter(messenger, "messenger");
            new MethodChannel(messenger, "com.fluttercandies/photo_manager").setMethodCallHandler(plugin);
        }

        public final PluginRegistry.RequestPermissionsResultListener createAddRequestPermissionsResultListener(final PermissionsUtils permissionsUtils) {
            Intrinsics.checkNotNullParameter(permissionsUtils, "permissionsUtils");
            return new PluginRegistry.RequestPermissionsResultListener() { // from class: com.fluttercandies.photo_manager.PhotoManagerPlugin$Companion$$ExternalSyntheticLambda0
                @Override // io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener
                public final boolean onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
                    return PhotoManagerPlugin.Companion.createAddRequestPermissionsResultListener$lambda$1(permissionsUtils, i, strArr, iArr);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean createAddRequestPermissionsResultListener$lambda$1(PermissionsUtils permissionsUtils, int i, String[] permissions, int[] grantResults) {
            Intrinsics.checkNotNullParameter(permissionsUtils, "$permissionsUtils");
            Intrinsics.checkNotNullParameter(permissions, "permissions");
            Intrinsics.checkNotNullParameter(grantResults, "grantResults");
            permissionsUtils.dealResult(i, permissions, grantResults);
            return false;
        }
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        Context applicationContext = binding.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        BinaryMessenger binaryMessenger = binding.getBinaryMessenger();
        Intrinsics.checkNotNullExpressionValue(binaryMessenger, "getBinaryMessenger(...)");
        com.fluttercandies.photo_manager.core.PhotoManagerPlugin photoManagerPlugin = new com.fluttercandies.photo_manager.core.PhotoManagerPlugin(applicationContext, binaryMessenger, null, this.permissionsUtils);
        Companion companion = INSTANCE;
        BinaryMessenger binaryMessenger2 = binding.getBinaryMessenger();
        Intrinsics.checkNotNullExpressionValue(binaryMessenger2, "getBinaryMessenger(...)");
        companion.register(photoManagerPlugin, binaryMessenger2);
        this.plugin = photoManagerPlugin;
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.plugin = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivity() {
        ActivityPluginBinding activityPluginBinding = this.binding;
        if (activityPluginBinding != null) {
            onRemoveRequestPermissionResultListener(activityPluginBinding);
        }
        com.fluttercandies.photo_manager.core.PhotoManagerPlugin photoManagerPlugin = this.plugin;
        if (photoManagerPlugin != null) {
            photoManagerPlugin.bindActivity(null);
        }
        this.binding = null;
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onAttachedToActivity(ActivityPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        activityAttached(binding);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        activityAttached(binding);
    }

    @Override // io.flutter.embedding.engine.plugins.activity.ActivityAware
    public void onDetachedFromActivityForConfigChanges() {
        com.fluttercandies.photo_manager.core.PhotoManagerPlugin photoManagerPlugin = this.plugin;
        if (photoManagerPlugin != null) {
            photoManagerPlugin.bindActivity(null);
        }
    }

    private final void activityAttached(ActivityPluginBinding binding) {
        ActivityPluginBinding activityPluginBinding = this.binding;
        if (activityPluginBinding != null) {
            onRemoveRequestPermissionResultListener(activityPluginBinding);
        }
        this.binding = binding;
        com.fluttercandies.photo_manager.core.PhotoManagerPlugin photoManagerPlugin = this.plugin;
        if (photoManagerPlugin != null) {
            photoManagerPlugin.bindActivity(binding.getActivity());
        }
        addRequestPermissionsResultListener(binding);
    }

    private final void addRequestPermissionsResultListener(ActivityPluginBinding binding) {
        PluginRegistry.RequestPermissionsResultListener requestPermissionsResultListenerCreateAddRequestPermissionsResultListener = INSTANCE.createAddRequestPermissionsResultListener(this.permissionsUtils);
        this.requestPermissionsResultListener = requestPermissionsResultListenerCreateAddRequestPermissionsResultListener;
        binding.addRequestPermissionsResultListener(requestPermissionsResultListenerCreateAddRequestPermissionsResultListener);
        com.fluttercandies.photo_manager.core.PhotoManagerPlugin photoManagerPlugin = this.plugin;
        if (photoManagerPlugin != null) {
            binding.addActivityResultListener(photoManagerPlugin.getDeleteManager());
        }
    }

    private final void onRemoveRequestPermissionResultListener(ActivityPluginBinding oldBinding) {
        PluginRegistry.RequestPermissionsResultListener requestPermissionsResultListener = this.requestPermissionsResultListener;
        if (requestPermissionsResultListener != null) {
            oldBinding.removeRequestPermissionsResultListener(requestPermissionsResultListener);
        }
        com.fluttercandies.photo_manager.core.PhotoManagerPlugin photoManagerPlugin = this.plugin;
        if (photoManagerPlugin != null) {
            oldBinding.removeActivityResultListener(photoManagerPlugin.getDeleteManager());
        }
    }
}
