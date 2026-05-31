package androidx.camera.core.processing;

import androidx.camera.core.SurfaceProcessor;
import androidx.camera.core.impl.utils.futures.Futures;
import com.google.common.util.concurrent.ListenableFuture;

/* JADX INFO: loaded from: classes.dex */
public interface SurfaceProcessorInternal extends SurfaceProcessor {
    void release();

    default ListenableFuture<Void> snapshot(int i, int i2) {
        return Futures.immediateFuture(null);
    }
}
