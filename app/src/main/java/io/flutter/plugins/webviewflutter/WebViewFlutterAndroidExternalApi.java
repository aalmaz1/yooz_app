package io.flutter.plugins.webviewflutter;

import android.webkit.WebView;
import io.flutter.embedding.engine.FlutterEngine;

/* JADX INFO: loaded from: classes2.dex */
public interface WebViewFlutterAndroidExternalApi {
    static WebView getWebView(FlutterEngine flutterEngine, long j) {
        WebViewFlutterPlugin webViewFlutterPlugin = (WebViewFlutterPlugin) flutterEngine.getPlugins().get(WebViewFlutterPlugin.class);
        if (webViewFlutterPlugin == null || webViewFlutterPlugin.getInstanceManager() == null) {
            return null;
        }
        Object instanceManager = webViewFlutterPlugin.getInstanceManager().getInstance(j);
        if (instanceManager instanceof WebView) {
            return (WebView) instanceManager;
        }
        return null;
    }
}
