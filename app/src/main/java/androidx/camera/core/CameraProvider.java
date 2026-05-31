package androidx.camera.core;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface CameraProvider {
    List<CameraInfo> getAvailableCameraInfos();

    boolean hasCamera(CameraSelector cameraSelector) throws CameraInfoUnavailableException;
}
