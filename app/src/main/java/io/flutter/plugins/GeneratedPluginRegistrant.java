package io.flutter.plugins;

import com.aboutyou.dart_packages.sign_in_with_apple.SignInWithApplePlugin;
import com.amazonaws.amplify.amplify_analytics_pinpoint.AmplifyAnalyticsPinpointPlugin;
import com.amazonaws.amplify.amplify_auth_cognito.AmplifyAuthCognitoPlugin;
import com.amazonaws.amplify.amplify_db_common.AmplifyDbCommonPlugin;
import com.amazonaws.amplify.amplify_secure_storage.AmplifySecureStoragePlugin;
import com.baseflow.geolocator.GeolocatorPlugin;
import com.baseflow.permissionhandler.PermissionHandlerPlugin;
import com.fluttercandies.flutter_image_compress.ImageCompressPlugin;
import com.fluttercandies.photo_manager.PhotoManagerPlugin;
import com.lib.flutter_blue_plus.FlutterBluePlusPlugin;
import com.tekartik.sqflite.SqflitePlugin;
import de.ffuf.in_app_update.InAppUpdatePlugin;
import dev.fluttercommunity.plus.connectivity.ConnectivityPlugin;
import dev.fluttercommunity.plus.device_info.DeviceInfoPlusPlugin;
import dev.fluttercommunity.plus.packageinfo.PackageInfoPlugin;
import io.flutter.Log;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.flutter_plugin_android_lifecycle.FlutterAndroidLifecyclePlugin;
import io.flutter.plugins.imagepicker.ImagePickerPlugin;
import io.flutter.plugins.inapppurchase.InAppPurchasePlugin;
import io.flutter.plugins.pathprovider.PathProviderPlugin;
import io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin;
import io.flutter.plugins.urllauncher.UrlLauncherPlugin;
import io.flutter.plugins.webviewflutter.WebViewFlutterPlugin;

/* JADX INFO: loaded from: classes2.dex */
public final class GeneratedPluginRegistrant {
    private static final String TAG = "GeneratedPluginRegistrant";

    public static void registerWith(FlutterEngine flutterEngine) {
        try {
            flutterEngine.getPlugins().add(new AmplifyAnalyticsPinpointPlugin());
        } catch (Exception e) {
            Log.e(TAG, "Error registering plugin amplify_analytics_pinpoint, com.amazonaws.amplify.amplify_analytics_pinpoint.AmplifyAnalyticsPinpointPlugin", e);
        }
        try {
            flutterEngine.getPlugins().add(new AmplifyAuthCognitoPlugin());
        } catch (Exception e2) {
            Log.e(TAG, "Error registering plugin amplify_auth_cognito, com.amazonaws.amplify.amplify_auth_cognito.AmplifyAuthCognitoPlugin", e2);
        }
        try {
            flutterEngine.getPlugins().add(new AmplifyDbCommonPlugin());
        } catch (Exception e3) {
            Log.e(TAG, "Error registering plugin amplify_db_common, com.amazonaws.amplify.amplify_db_common.AmplifyDbCommonPlugin", e3);
        }
        try {
            flutterEngine.getPlugins().add(new AmplifySecureStoragePlugin());
        } catch (Exception e4) {
            Log.e(TAG, "Error registering plugin amplify_secure_storage, com.amazonaws.amplify.amplify_secure_storage.AmplifySecureStoragePlugin", e4);
        }
        try {
            flutterEngine.getPlugins().add(new ConnectivityPlugin());
        } catch (Exception e5) {
            Log.e(TAG, "Error registering plugin connectivity_plus, dev.fluttercommunity.plus.connectivity.ConnectivityPlugin", e5);
        }
        try {
            flutterEngine.getPlugins().add(new DeviceInfoPlusPlugin());
        } catch (Exception e6) {
            Log.e(TAG, "Error registering plugin device_info_plus, dev.fluttercommunity.plus.device_info.DeviceInfoPlusPlugin", e6);
        }
        try {
            flutterEngine.getPlugins().add(new FlutterBluePlusPlugin());
        } catch (Exception e7) {
            Log.e(TAG, "Error registering plugin flutter_blue_plus, com.lib.flutter_blue_plus.FlutterBluePlusPlugin", e7);
        }
        try {
            flutterEngine.getPlugins().add(new ImageCompressPlugin());
        } catch (Exception e8) {
            Log.e(TAG, "Error registering plugin flutter_image_compress_common, com.fluttercandies.flutter_image_compress.ImageCompressPlugin", e8);
        }
        try {
            flutterEngine.getPlugins().add(new FlutterAndroidLifecyclePlugin());
        } catch (Exception e9) {
            Log.e(TAG, "Error registering plugin flutter_plugin_android_lifecycle, io.flutter.plugins.flutter_plugin_android_lifecycle.FlutterAndroidLifecyclePlugin", e9);
        }
        try {
            flutterEngine.getPlugins().add(new GeolocatorPlugin());
        } catch (Exception e10) {
            Log.e(TAG, "Error registering plugin geolocator_android, com.baseflow.geolocator.GeolocatorPlugin", e10);
        }
        try {
            flutterEngine.getPlugins().add(new ImagePickerPlugin());
        } catch (Exception e11) {
            Log.e(TAG, "Error registering plugin image_picker_android, io.flutter.plugins.imagepicker.ImagePickerPlugin", e11);
        }
        try {
            flutterEngine.getPlugins().add(new InAppPurchasePlugin());
        } catch (Exception e12) {
            Log.e(TAG, "Error registering plugin in_app_purchase_android, io.flutter.plugins.inapppurchase.InAppPurchasePlugin", e12);
        }
        try {
            flutterEngine.getPlugins().add(new InAppUpdatePlugin());
        } catch (Exception e13) {
            Log.e(TAG, "Error registering plugin in_app_update, de.ffuf.in_app_update.InAppUpdatePlugin", e13);
        }
        try {
            flutterEngine.getPlugins().add(new PackageInfoPlugin());
        } catch (Exception e14) {
            Log.e(TAG, "Error registering plugin package_info_plus, dev.fluttercommunity.plus.packageinfo.PackageInfoPlugin", e14);
        }
        try {
            flutterEngine.getPlugins().add(new PathProviderPlugin());
        } catch (Exception e15) {
            Log.e(TAG, "Error registering plugin path_provider_android, io.flutter.plugins.pathprovider.PathProviderPlugin", e15);
        }
        try {
            flutterEngine.getPlugins().add(new PermissionHandlerPlugin());
        } catch (Exception e16) {
            Log.e(TAG, "Error registering plugin permission_handler_android, com.baseflow.permissionhandler.PermissionHandlerPlugin", e16);
        }
        try {
            flutterEngine.getPlugins().add(new PhotoManagerPlugin());
        } catch (Exception e17) {
            Log.e(TAG, "Error registering plugin photo_manager, com.fluttercandies.photo_manager.PhotoManagerPlugin", e17);
        }
        try {
            flutterEngine.getPlugins().add(new SharedPreferencesPlugin());
        } catch (Exception e18) {
            Log.e(TAG, "Error registering plugin shared_preferences_android, io.flutter.plugins.sharedpreferences.SharedPreferencesPlugin", e18);
        }
        try {
            flutterEngine.getPlugins().add(new SignInWithApplePlugin());
        } catch (Exception e19) {
            Log.e(TAG, "Error registering plugin sign_in_with_apple, com.aboutyou.dart_packages.sign_in_with_apple.SignInWithApplePlugin", e19);
        }
        try {
            flutterEngine.getPlugins().add(new SqflitePlugin());
        } catch (Exception e20) {
            Log.e(TAG, "Error registering plugin sqflite, com.tekartik.sqflite.SqflitePlugin", e20);
        }
        try {
            flutterEngine.getPlugins().add(new UrlLauncherPlugin());
        } catch (Exception e21) {
            Log.e(TAG, "Error registering plugin url_launcher_android, io.flutter.plugins.urllauncher.UrlLauncherPlugin", e21);
        }
        try {
            flutterEngine.getPlugins().add(new WebViewFlutterPlugin());
        } catch (Exception e22) {
            Log.e(TAG, "Error registering plugin webview_flutter_android, io.flutter.plugins.webviewflutter.WebViewFlutterPlugin", e22);
        }
    }
}
