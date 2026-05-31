package androidx.camera.camera2.internal;

import androidx.camera.core.SafeCloseImageReaderProxy;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ZslControlImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ SafeCloseImageReaderProxy f$0;

    public /* synthetic */ ZslControlImpl$$ExternalSyntheticLambda1(SafeCloseImageReaderProxy safeCloseImageReaderProxy) {
        this.f$0 = safeCloseImageReaderProxy;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.safeClose();
    }
}
