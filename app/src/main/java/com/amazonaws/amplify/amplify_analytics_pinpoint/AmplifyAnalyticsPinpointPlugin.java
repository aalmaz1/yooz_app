package com.amazonaws.amplify.amplify_analytics_pinpoint;

import android.content.Context;
import android.content.SharedPreferences;
import com.amazonaws.amplify.amplify_analytics_pinpoint.Messages;
import com.tekartik.sqflite.Constant;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AmplifyAnalyticsPinpointPlugin.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00122\u00020\u00012\u00020\u0002:\u0001\u0012B\u0005¢\u0006\u0002\u0010\u0003J \u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u000e\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\rH\u0016J\u0010\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/amazonaws/amplify/amplify_analytics_pinpoint/AmplifyAnalyticsPinpointPlugin;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "Lcom/amazonaws/amplify/amplify_analytics_pinpoint/Messages$PigeonLegacyDataProvider;", "()V", "context", "Landroid/content/Context;", "sharedPrefs", "Landroid/content/SharedPreferences;", "getEndpointId", "", "pinpointAppId", "", Constant.PARAM_RESULT, "Lcom/amazonaws/amplify/amplify_analytics_pinpoint/Messages$Result;", "onAttachedToEngine", "binding", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onDetachedFromEngine", "Companion", "amplify_analytics_pinpoint_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AmplifyAnalyticsPinpointPlugin implements FlutterPlugin, Messages.PigeonLegacyDataProvider {
    private static final String PINPOINT_SHARED_PREFS_SUFFIX = "515d6767-01b7-49e5-8273-c8d11b0f331d";
    private static final String UNIQUE_ID_KEY = "UniqueId";
    private Context context;
    private SharedPreferences sharedPrefs;

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.context = binding.getApplicationContext();
        Messages.PigeonLegacyDataProvider.setup(binding.getBinaryMessenger(), this);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        Messages.PigeonLegacyDataProvider.setup(binding.getBinaryMessenger(), null);
        this.context = null;
    }

    @Override // com.amazonaws.amplify.amplify_analytics_pinpoint.Messages.PigeonLegacyDataProvider
    public void getEndpointId(String pinpointAppId, Messages.Result<String> result) {
        Intrinsics.checkNotNullParameter(pinpointAppId, "pinpointAppId");
        Intrinsics.checkNotNullParameter(result, "result");
        Context context = this.context;
        if (context == null) {
            result.error(new Exception("Application context is null"));
            return;
        }
        SharedPreferences sharedPreferences = this.sharedPrefs;
        if (sharedPreferences == null) {
            Intrinsics.checkNotNull(context);
            sharedPreferences = context.getSharedPreferences(pinpointAppId + PINPOINT_SHARED_PREFS_SUFFIX, 0);
        }
        this.sharedPrefs = sharedPreferences;
        Intrinsics.checkNotNull(sharedPreferences);
        result.success(sharedPreferences.getString(UNIQUE_ID_KEY, null));
    }
}
