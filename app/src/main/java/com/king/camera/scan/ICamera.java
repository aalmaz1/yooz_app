package com.king.camera.scan;

import androidx.camera.core.Camera;

/* JADX INFO: loaded from: classes2.dex */
public interface ICamera {
    Camera getCamera();

    void release();

    void startCamera();

    void stopCamera();
}
