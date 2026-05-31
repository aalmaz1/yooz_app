package androidx.camera.core.internal.compat;

import android.media.ImageWriter;
import android.util.Log;
import android.view.Surface;
import androidx.core.util.Preconditions;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
final class ImageWriterCompatApi26Impl {
    private static final String TAG = "ImageWriterCompatApi26";
    private static Method sNewInstanceMethod;

    static {
        try {
            sNewInstanceMethod = ImageWriter.class.getMethod("newInstance", Surface.class, Integer.TYPE, Integer.TYPE);
        } catch (NoSuchMethodException e) {
            Log.i(TAG, "Unable to initialize via reflection.", e);
        }
    }

    static ImageWriter newInstance(Surface surface, int i, int i2) {
        try {
            return (ImageWriter) Preconditions.checkNotNull(sNewInstanceMethod.invoke(null, surface, Integer.valueOf(i), Integer.valueOf(i2)));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Unable to invoke newInstance(Surface, int, int) via reflection.", e);
        }
    }

    private ImageWriterCompatApi26Impl() {
    }
}
