package com.aboutyou.dart_packages.sign_in_with_apple;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import io.flutter.Log;
import io.flutter.plugin.common.MethodChannel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: SignInWithApplePlugin.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007\b\u0016¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lcom/aboutyou/dart_packages/sign_in_with_apple/SignInWithAppleCallback;", "Landroid/app/Activity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "sign_in_with_apple_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SignInWithAppleCallback extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        Uri data;
        super.onCreate(savedInstanceState);
        MethodChannel.Result lastAuthorizationRequestResult = SignInWithApplePlugin.INSTANCE.getLastAuthorizationRequestResult();
        if (lastAuthorizationRequestResult != null) {
            Intent intent = getIntent();
            lastAuthorizationRequestResult.success((intent == null || (data = intent.getData()) == null) ? null : data.toString());
            SignInWithApplePlugin.INSTANCE.setLastAuthorizationRequestResult(null);
        } else {
            SignInWithApplePlugin.INSTANCE.setTriggerMainActivityToHideChromeCustomTab(null);
            Log.e(SignInWithApplePluginKt.getTAG(), "Received Sign in with Apple callback, but 'lastAuthorizationRequestResult' function was `null`");
        }
        Function0<Unit> triggerMainActivityToHideChromeCustomTab = SignInWithApplePlugin.INSTANCE.getTriggerMainActivityToHideChromeCustomTab();
        if (triggerMainActivityToHideChromeCustomTab != null) {
            triggerMainActivityToHideChromeCustomTab.invoke();
            SignInWithApplePlugin.INSTANCE.setTriggerMainActivityToHideChromeCustomTab(null);
        } else {
            Log.e(SignInWithApplePluginKt.getTAG(), "Received Sign in with Apple callback, but 'triggerMainActivityToHideChromeCustomTab' function was `null`");
        }
        finish();
    }
}
