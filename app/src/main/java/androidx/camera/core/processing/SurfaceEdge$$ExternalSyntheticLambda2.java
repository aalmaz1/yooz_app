package androidx.camera.core.processing;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class SurfaceEdge$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ SurfaceEdge f$0;

    public /* synthetic */ SurfaceEdge$$ExternalSyntheticLambda2(SurfaceEdge surfaceEdge) {
        this.f$0 = surfaceEdge;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.disconnectWithoutCheckingClosed();
    }
}
