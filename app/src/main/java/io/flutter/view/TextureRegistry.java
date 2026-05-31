package io.flutter.view;

import android.graphics.SurfaceTexture;
import android.media.Image;
import android.view.Surface;

/* JADX INFO: loaded from: classes2.dex */
public interface TextureRegistry {

    public interface ImageConsumer extends TextureEntry {
        Image acquireLatestImage();
    }

    public interface ImageTextureEntry extends TextureEntry {
        @Override // io.flutter.view.TextureRegistry.TextureEntry
        long id();

        void pushImage(Image image);

        @Override // io.flutter.view.TextureRegistry.TextureEntry
        void release();
    }

    public interface OnFrameConsumedListener {
        void onFrameConsumed();
    }

    public interface OnTrimMemoryListener {
        void onTrimMemory(int i);
    }

    public interface SurfaceProducer extends TextureEntry {
        int getHeight();

        Surface getSurface();

        int getWidth();

        @Override // io.flutter.view.TextureRegistry.TextureEntry
        long id();

        @Override // io.flutter.view.TextureRegistry.TextureEntry
        void release();

        void setSize(int i, int i2);
    }

    public interface SurfaceTextureEntry extends TextureEntry {
        @Override // io.flutter.view.TextureRegistry.TextureEntry
        long id();

        @Override // io.flutter.view.TextureRegistry.TextureEntry
        void release();

        default void setOnFrameConsumedListener(OnFrameConsumedListener onFrameConsumedListener) {
        }

        default void setOnTrimMemoryListener(OnTrimMemoryListener onTrimMemoryListener) {
        }

        SurfaceTexture surfaceTexture();
    }

    public interface TextureEntry {
        long id();

        void release();
    }

    ImageTextureEntry createImageTexture();

    SurfaceProducer createSurfaceProducer();

    SurfaceTextureEntry createSurfaceTexture();

    default void onTrimMemory(int i) {
    }

    SurfaceTextureEntry registerSurfaceTexture(SurfaceTexture surfaceTexture);
}
