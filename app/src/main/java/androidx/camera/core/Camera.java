package androidx.camera.core;

import androidx.camera.core.impl.CameraConfig;
import androidx.camera.core.impl.CameraInternal;
import java.util.LinkedHashSet;

/* JADX INFO: loaded from: classes.dex */
public interface Camera {
    CameraControl getCameraControl();

    CameraInfo getCameraInfo();

    LinkedHashSet<CameraInternal> getCameraInternals();

    CameraConfig getExtendedConfig();

    default boolean isUseCasesCombinationSupported(UseCase... useCaseArr) {
        return true;
    }

    void setExtendedConfig(CameraConfig cameraConfig);
}
