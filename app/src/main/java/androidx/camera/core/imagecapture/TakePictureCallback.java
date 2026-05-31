package androidx.camera.core.imagecapture;

import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;

/* JADX INFO: loaded from: classes.dex */
interface TakePictureCallback {
    boolean isAborted();

    void onCaptureFailure(ImageCaptureException imageCaptureException);

    void onFinalResult(ImageCapture.OutputFileResults outputFileResults);

    void onFinalResult(ImageProxy imageProxy);

    void onImageCaptured();

    void onProcessFailure(ImageCaptureException imageCaptureException);
}
