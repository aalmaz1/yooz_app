package dev.fluttercommunity.plus.packageinfo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.sun.mail.imap.IMAPStore;
import com.tekartik.sqflite.Constant;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PackageInfoPlugin.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000  2\u00020\u00012\u00020\u0002:\u0001 B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0012\u0010\f\u001a\u0004\u0018\u00010\t2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\n\u0010\u000f\u001a\u0004\u0018\u00010\tH\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\t2\u0006\u0010\u001f\u001a\u00020\u000bH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Ldev/fluttercommunity/plus/packageinfo/PackageInfoPlugin;", "Lio/flutter/plugin/common/MethodChannel$MethodCallHandler;", "Lio/flutter/embedding/engine/plugins/FlutterPlugin;", "()V", "applicationContext", "Landroid/content/Context;", "methodChannel", "Lio/flutter/plugin/common/MethodChannel;", "bytesToHex", "", "bytes", "", "getBuildSignature", "pm", "Landroid/content/pm/PackageManager;", "getInstallerPackageName", "getLongVersionCode", "", "info", "Landroid/content/pm/PackageInfo;", "onAttachedToEngine", "", "binding", "Lio/flutter/embedding/engine/plugins/FlutterPlugin$FlutterPluginBinding;", "onDetachedFromEngine", "onMethodCall", NotificationCompat.CATEGORY_CALL, "Lio/flutter/plugin/common/MethodCall;", Constant.PARAM_RESULT, "Lio/flutter/plugin/common/MethodChannel$Result;", "signatureToSha1", "sig", "Companion", "package_info_plus_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PackageInfoPlugin implements MethodChannel.MethodCallHandler, FlutterPlugin {
    private static final String CHANNEL_NAME = "dev.fluttercommunity.plus/package_info";
    private Context applicationContext;
    private MethodChannel methodChannel;

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.applicationContext = binding.getApplicationContext();
        MethodChannel methodChannel = new MethodChannel(binding.getBinaryMessenger(), CHANNEL_NAME);
        this.methodChannel = methodChannel;
        Intrinsics.checkNotNull(methodChannel);
        methodChannel.setMethodCallHandler(this);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding binding) {
        Intrinsics.checkNotNullParameter(binding, "binding");
        this.applicationContext = null;
        MethodChannel methodChannel = this.methodChannel;
        Intrinsics.checkNotNull(methodChannel);
        methodChannel.setMethodCallHandler(null);
        this.methodChannel = null;
    }

    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(result, "result");
        try {
            if (Intrinsics.areEqual(call.method, "getAll")) {
                Context context = this.applicationContext;
                Intrinsics.checkNotNull(context);
                PackageManager packageManager = context.getPackageManager();
                Context context2 = this.applicationContext;
                Intrinsics.checkNotNull(context2);
                PackageInfo packageInfo = packageManager.getPackageInfo(context2.getPackageName(), 0);
                Intrinsics.checkNotNull(packageManager);
                String buildSignature = getBuildSignature(packageManager);
                String installerPackageName = getInstallerPackageName();
                HashMap map = new HashMap();
                map.put("appName", packageInfo.applicationInfo.loadLabel(packageManager).toString());
                Context context3 = this.applicationContext;
                Intrinsics.checkNotNull(context3);
                map.put("packageName", context3.getPackageName());
                map.put(IMAPStore.ID_VERSION, packageInfo.versionName);
                Intrinsics.checkNotNull(packageInfo);
                map.put("buildNumber", String.valueOf(getLongVersionCode(packageInfo)));
                if (buildSignature != null) {
                    map.put("buildSignature", buildSignature);
                }
                if (installerPackageName != null) {
                    map.put("installerStore", installerPackageName);
                }
                result.success(map);
                return;
            }
            result.notImplemented();
        } catch (PackageManager.NameNotFoundException e) {
            result.error("Name not found", e.getMessage(), null);
        }
    }

    private final String getInstallerPackageName() {
        Context context = this.applicationContext;
        Intrinsics.checkNotNull(context);
        PackageManager packageManager = context.getPackageManager();
        Context context2 = this.applicationContext;
        Intrinsics.checkNotNull(context2);
        String packageName = context2.getPackageName();
        if (Build.VERSION.SDK_INT >= 30) {
            return packageManager.getInstallSourceInfo(packageName).getInitiatingPackageName();
        }
        return packageManager.getInstallerPackageName(packageName);
    }

    private final long getLongVersionCode(PackageInfo info) {
        return info.getLongVersionCode();
    }

    private final String getBuildSignature(PackageManager pm) {
        String strSignatureToSha1;
        try {
            Context context = this.applicationContext;
            Intrinsics.checkNotNull(context);
            SigningInfo signingInfo = pm.getPackageInfo(context.getPackageName(), 134217728).signingInfo;
            if (signingInfo == null) {
                return null;
            }
            if (signingInfo.hasMultipleSigners()) {
                Signature[] apkContentsSigners = signingInfo.getApkContentsSigners();
                Intrinsics.checkNotNullExpressionValue(apkContentsSigners, "getApkContentsSigners(...)");
                byte[] byteArray = ((Signature) ArraysKt.first(apkContentsSigners)).toByteArray();
                Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
                strSignatureToSha1 = signatureToSha1(byteArray);
            } else {
                Signature[] signingCertificateHistory = signingInfo.getSigningCertificateHistory();
                Intrinsics.checkNotNullExpressionValue(signingCertificateHistory, "getSigningCertificateHistory(...)");
                byte[] byteArray2 = ((Signature) ArraysKt.first(signingCertificateHistory)).toByteArray();
                Intrinsics.checkNotNullExpressionValue(byteArray2, "toByteArray(...)");
                strSignatureToSha1 = signatureToSha1(byteArray2);
            }
            return strSignatureToSha1;
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException unused) {
            return null;
        }
    }

    private final String signatureToSha1(byte[] sig) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
        messageDigest.update(sig);
        byte[] bArrDigest = messageDigest.digest();
        Intrinsics.checkNotNull(bArrDigest);
        return bytesToHex(bArrDigest);
    }

    private final String bytesToHex(byte[] bytes) {
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] cArr2 = new char[bytes.length * 2];
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            int i2 = bytes[i] & UByte.MAX_VALUE;
            int i3 = i * 2;
            cArr2[i3] = cArr[i2 >>> 4];
            cArr2[i3 + 1] = cArr[i2 & 15];
        }
        return new String(cArr2);
    }
}
