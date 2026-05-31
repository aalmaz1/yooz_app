package io.flutter.plugins.webviewflutter;

import android.content.res.AssetManager;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.PluginRegistry;
import java.io.IOException;

/* JADX INFO: loaded from: classes2.dex */
abstract class FlutterAssetManager {
    final AssetManager assetManager;

    abstract String getAssetFilePathByName(String str);

    public FlutterAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public String[] list(String str) throws IOException {
        return this.assetManager.list(str);
    }

    @Deprecated
    static class RegistrarFlutterAssetManager extends FlutterAssetManager {
        final PluginRegistry.Registrar registrar;

        RegistrarFlutterAssetManager(AssetManager assetManager, PluginRegistry.Registrar registrar) {
            super(assetManager);
            this.registrar = registrar;
        }

        @Override // io.flutter.plugins.webviewflutter.FlutterAssetManager
        public String getAssetFilePathByName(String str) {
            return this.registrar.lookupKeyForAsset(str);
        }
    }

    static class PluginBindingFlutterAssetManager extends FlutterAssetManager {
        final FlutterPlugin.FlutterAssets flutterAssets;

        PluginBindingFlutterAssetManager(AssetManager assetManager, FlutterPlugin.FlutterAssets flutterAssets) {
            super(assetManager);
            this.flutterAssets = flutterAssets;
        }

        @Override // io.flutter.plugins.webviewflutter.FlutterAssetManager
        public String getAssetFilePathByName(String str) {
            return this.flutterAssets.getAssetFilePathByName(str);
        }
    }
}
