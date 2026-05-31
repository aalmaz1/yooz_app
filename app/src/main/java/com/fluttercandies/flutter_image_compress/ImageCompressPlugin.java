package com.fluttercandies.flutter_image_compress;

import android.content.Context;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.fluttercandies.flutter_image_compress.core.CompressFileHandler;
import com.fluttercandies.flutter_image_compress.core.CompressListHandler;
import com.fluttercandies.flutter_image_compress.format.FormatRegister;
import com.fluttercandies.flutter_image_compress.handle.common.CommonHandler;
import com.fluttercandies.flutter_image_compress.handle.heif.HeifHandler;
import com.tekartik.sqflite.Constant;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ImageCompressPlugin.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00142\u00020\u00012\u00020\u0002:\u0001\u0014B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0011\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/fluttercandies/flutter_image_compress/ImageCompressPlugin;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", "()V", "channel", "Lio/flutter/plugin/common/MethodChannel;", "context", "Landroid/content/Context;", "handleLog", "", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", "onAttachedToEngine", "", "binding", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onDetachedFromEngine", "onMethodCall", Constant.PARAM_RESULT, "Lio/flutter/plugin/common/MethodChannel$Result;", "Companion", "flutter_image_compress_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImageCompressPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static boolean showLog;
    private MethodChannel channel;
    private Context context;

    public ImageCompressPlugin() {
        FormatRegister.INSTANCE.registerFormat(new CommonHandler(0));
        FormatRegister.INSTANCE.registerFormat(new CommonHandler(1));
        FormatRegister.INSTANCE.registerFormat(new HeifHandler());
        FormatRegister.INSTANCE.registerFormat(new CommonHandler(3));
    }

    /* JADX INFO: compiled from: ImageCompressPlugin.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lcom/fluttercandies/flutter_image_compress/ImageCompressPlugin$Companion;", "", "()V", "showLog", "", "getShowLog", "()Z", "setShowLog", "(Z)V", "flutter_image_compress_common_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean getShowLog() {
            return ImageCompressPlugin.showLog;
        }

        public final void setShowLog(boolean z) {
            ImageCompressPlugin.showLog = z;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        String str = call.method;
        if (str != null) {
            Context context = null;
            switch (str.hashCode()) {
                case -129880033:
                    if (str.equals("compressWithFileAndGetFile")) {
                        CompressFileHandler compressFileHandler = new CompressFileHandler(call, result);
                        Context context2 = this.context;
                        if (context2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("context");
                        } else {
                            context = context2;
                        }
                        compressFileHandler.handleGetFile(context);
                        return;
                    }
                    break;
                case 86054116:
                    if (str.equals("compressWithFile")) {
                        CompressFileHandler compressFileHandler2 = new CompressFileHandler(call, result);
                        Context context3 = this.context;
                        if (context3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("context");
                        } else {
                            context = context3;
                        }
                        compressFileHandler2.handle(context);
                        return;
                    }
                    break;
                case 86233094:
                    if (str.equals("compressWithList")) {
                        CompressListHandler compressListHandler = new CompressListHandler(call, result);
                        Context context4 = this.context;
                        if (context4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("context");
                        } else {
                            context = context4;
                        }
                        compressListHandler.handle(context);
                        return;
                    }
                    break;
                case 1262746611:
                    if (str.equals("getSystemVersion")) {
                        result.success(Integer.valueOf(Build.VERSION.SDK_INT));
                        return;
                    }
                    break;
                case 2067272455:
                    if (str.equals("showLog")) {
                        result.success(Integer.valueOf(handleLog(call)));
                        return;
                    }
                    break;
            }
        }
        result.notImplemented();
    }

    private final int handleLog(MethodCall call) {
        showLog = Intrinsics.areEqual(call.arguments(), (Object) true);
        return 1;
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        Context applicationContext = binding.getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        this.context = applicationContext;
        MethodChannel methodChannel = new MethodChannel(binding.getBinaryMessenger(), "flutter_image_compress");
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
}
