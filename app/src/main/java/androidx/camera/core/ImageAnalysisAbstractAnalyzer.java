package androidx.camera.core;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ImageWriter;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.utils.TransformUtils;
import androidx.camera.core.internal.compat.ImageWriterCompat;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.os.OperationCanceledException;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
abstract class ImageAnalysisAbstractAnalyzer implements ImageReaderProxy.OnImageAvailableListener {
    private static final String TAG = "ImageAnalysisAnalyzer";
    private volatile boolean mOnePixelShiftEnabled;
    private volatile boolean mOutputImageRotationEnabled;
    private volatile int mPrevBufferRotationDegrees;
    private SafeCloseImageReaderProxy mProcessedImageReaderProxy;
    private ImageWriter mProcessedImageWriter;
    ByteBuffer mRGBConvertedBuffer;
    private volatile int mRelativeRotation;
    private ImageAnalysis.Analyzer mSubscribedAnalyzer;
    ByteBuffer mURotatedBuffer;
    private Executor mUserExecutor;
    ByteBuffer mVRotatedBuffer;
    ByteBuffer mYRotatedBuffer;
    private volatile int mOutputImageFormat = 1;
    private Rect mOriginalViewPortCropRect = new Rect();
    private Rect mUpdatedViewPortCropRect = new Rect();
    private Matrix mOriginalSensorToBufferTransformMatrix = new Matrix();
    private Matrix mUpdatedSensorToBufferTransformMatrix = new Matrix();
    private final Object mAnalyzerLock = new Object();
    protected boolean mIsAttached = true;

    abstract ImageProxy acquireImage(ImageReaderProxy imageReaderProxy);

    abstract void clearCache();

    abstract void onValidImageAvailable(ImageProxy imageProxy);

    ImageAnalysisAbstractAnalyzer() {
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener
    public void onImageAvailable(ImageReaderProxy imageReaderProxy) {
        try {
            ImageProxy imageProxyAcquireImage = acquireImage(imageReaderProxy);
            if (imageProxyAcquireImage != null) {
                onValidImageAvailable(imageProxyAcquireImage);
            }
        } catch (IllegalStateException e) {
            Logger.e(TAG, "Failed to acquire image.", e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0068  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    com.google.common.util.concurrent.ListenableFuture<java.lang.Void> analyzeImage(final androidx.camera.core.ImageProxy r15) {
        /*
            r14 = this;
            boolean r0 = r14.mOutputImageRotationEnabled
            r1 = 0
            if (r0 == 0) goto L8
            int r0 = r14.mRelativeRotation
            goto L9
        L8:
            r0 = r1
        L9:
            java.lang.Object r2 = r14.mAnalyzerLock
            monitor-enter(r2)
            java.util.concurrent.Executor r9 = r14.mUserExecutor     // Catch: java.lang.Throwable -> Lc0
            androidx.camera.core.ImageAnalysis$Analyzer r10 = r14.mSubscribedAnalyzer     // Catch: java.lang.Throwable -> Lc0
            boolean r3 = r14.mOutputImageRotationEnabled     // Catch: java.lang.Throwable -> Lc0
            r11 = 1
            if (r3 == 0) goto L1b
            int r3 = r14.mPrevBufferRotationDegrees     // Catch: java.lang.Throwable -> Lc0
            if (r0 == r3) goto L1b
            r12 = r11
            goto L1c
        L1b:
            r12 = r1
        L1c:
            if (r12 == 0) goto L21
            r14.recreateImageReaderProxy(r15, r0)     // Catch: java.lang.Throwable -> Lc0
        L21:
            boolean r3 = r14.mOutputImageRotationEnabled     // Catch: java.lang.Throwable -> Lc0
            if (r3 == 0) goto L28
            r14.createHelperBuffer(r15)     // Catch: java.lang.Throwable -> Lc0
        L28:
            androidx.camera.core.SafeCloseImageReaderProxy r3 = r14.mProcessedImageReaderProxy     // Catch: java.lang.Throwable -> Lc0
            android.media.ImageWriter r4 = r14.mProcessedImageWriter     // Catch: java.lang.Throwable -> Lc0
            java.nio.ByteBuffer r5 = r14.mRGBConvertedBuffer     // Catch: java.lang.Throwable -> Lc0
            java.nio.ByteBuffer r6 = r14.mYRotatedBuffer     // Catch: java.lang.Throwable -> Lc0
            java.nio.ByteBuffer r7 = r14.mURotatedBuffer     // Catch: java.lang.Throwable -> Lc0
            java.nio.ByteBuffer r8 = r14.mVRotatedBuffer     // Catch: java.lang.Throwable -> Lc0
            monitor-exit(r2)     // Catch: java.lang.Throwable -> Lc0
            if (r10 == 0) goto Lb4
            if (r9 == 0) goto Lb4
            boolean r2 = r14.mIsAttached
            if (r2 == 0) goto Lb4
            if (r3 == 0) goto L68
            int r2 = r14.mOutputImageFormat
            r13 = 2
            if (r2 != r13) goto L4b
            boolean r2 = r14.mOnePixelShiftEnabled
            androidx.camera.core.ImageProxy r2 = androidx.camera.core.ImageProcessingUtil.convertYUVToRGB(r15, r3, r5, r0, r2)
            goto L69
        L4b:
            int r2 = r14.mOutputImageFormat
            if (r2 != r11) goto L68
            boolean r2 = r14.mOnePixelShiftEnabled
            if (r2 == 0) goto L56
            androidx.camera.core.ImageProcessingUtil.applyPixelShiftForYUV(r15)
        L56:
            if (r4 == 0) goto L68
            if (r6 == 0) goto L68
            if (r7 == 0) goto L68
            if (r8 == 0) goto L68
            r2 = r15
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r0
            androidx.camera.core.ImageProxy r2 = androidx.camera.core.ImageProcessingUtil.rotateYUV(r2, r3, r4, r5, r6, r7, r8)
            goto L69
        L68:
            r2 = 0
        L69:
            if (r2 != 0) goto L6c
            r1 = r11
        L6c:
            if (r1 == 0) goto L70
            r8 = r15
            goto L71
        L70:
            r8 = r2
        L71:
            android.graphics.Rect r2 = new android.graphics.Rect
            r2.<init>()
            android.graphics.Matrix r7 = new android.graphics.Matrix
            r7.<init>()
            java.lang.Object r3 = r14.mAnalyzerLock
            monitor-enter(r3)
            if (r12 == 0) goto L95
            if (r1 != 0) goto L95
            int r1 = r15.getWidth()     // Catch: java.lang.Throwable -> Lb1
            int r4 = r15.getHeight()     // Catch: java.lang.Throwable -> Lb1
            int r5 = r8.getWidth()     // Catch: java.lang.Throwable -> Lb1
            int r6 = r8.getHeight()     // Catch: java.lang.Throwable -> Lb1
            r14.recalculateTransformMatrixAndCropRect(r1, r4, r5, r6)     // Catch: java.lang.Throwable -> Lb1
        L95:
            r14.mPrevBufferRotationDegrees = r0     // Catch: java.lang.Throwable -> Lb1
            android.graphics.Rect r0 = r14.mUpdatedViewPortCropRect     // Catch: java.lang.Throwable -> Lb1
            r2.set(r0)     // Catch: java.lang.Throwable -> Lb1
            android.graphics.Matrix r0 = r14.mUpdatedSensorToBufferTransformMatrix     // Catch: java.lang.Throwable -> Lb1
            r7.set(r0)     // Catch: java.lang.Throwable -> Lb1
            monitor-exit(r3)     // Catch: java.lang.Throwable -> Lb1
            androidx.camera.core.ImageAnalysisAbstractAnalyzer$$ExternalSyntheticLambda0 r0 = new androidx.camera.core.ImageAnalysisAbstractAnalyzer$$ExternalSyntheticLambda0
            r3 = r0
            r4 = r14
            r5 = r9
            r6 = r15
            r9 = r2
            r3.<init>()
            com.google.common.util.concurrent.ListenableFuture r15 = androidx.concurrent.futures.CallbackToFutureAdapter.getFuture(r0)
            goto Lbf
        Lb1:
            r15 = move-exception
            monitor-exit(r3)     // Catch: java.lang.Throwable -> Lb1
            throw r15
        Lb4:
            androidx.core.os.OperationCanceledException r15 = new androidx.core.os.OperationCanceledException
            java.lang.String r0 = "No analyzer or executor currently set."
            r15.<init>(r0)
            com.google.common.util.concurrent.ListenableFuture r15 = androidx.camera.core.impl.utils.futures.Futures.immediateFailedFuture(r15)
        Lbf:
            return r15
        Lc0:
            r15 = move-exception
            monitor-exit(r2)     // Catch: java.lang.Throwable -> Lc0
            throw r15
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.ImageAnalysisAbstractAnalyzer.analyzeImage(androidx.camera.core.ImageProxy):com.google.common.util.concurrent.ListenableFuture");
    }

    /* JADX INFO: renamed from: lambda$analyzeImage$1$androidx-camera-core-ImageAnalysisAbstractAnalyzer, reason: not valid java name */
    /* synthetic */ Object m123x9959ff20(Executor executor, final ImageProxy imageProxy, final Matrix matrix, final ImageProxy imageProxy2, final Rect rect, final ImageAnalysis.Analyzer analyzer, final CallbackToFutureAdapter.Completer completer) throws Exception {
        executor.execute(new Runnable() { // from class: androidx.camera.core.ImageAnalysisAbstractAnalyzer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m122x6b8164c1(imageProxy, matrix, imageProxy2, rect, analyzer, completer);
            }
        });
        return "analyzeImage";
    }

    /* JADX INFO: renamed from: lambda$analyzeImage$0$androidx-camera-core-ImageAnalysisAbstractAnalyzer, reason: not valid java name */
    /* synthetic */ void m122x6b8164c1(ImageProxy imageProxy, Matrix matrix, ImageProxy imageProxy2, Rect rect, ImageAnalysis.Analyzer analyzer, CallbackToFutureAdapter.Completer completer) {
        if (this.mIsAttached) {
            SettableImageProxy settableImageProxy = new SettableImageProxy(imageProxy2, ImmutableImageInfo.create(imageProxy.getImageInfo().getTagBundle(), imageProxy.getImageInfo().getTimestamp(), this.mOutputImageRotationEnabled ? 0 : this.mRelativeRotation, matrix));
            if (!rect.isEmpty()) {
                settableImageProxy.setCropRect(rect);
            }
            analyzer.analyze(settableImageProxy);
            completer.set(null);
            return;
        }
        completer.setException(new OperationCanceledException("ImageAnalysis is detached"));
    }

    private static SafeCloseImageReaderProxy createImageReaderProxy(int i, int i2, int i3, int i4, int i5) {
        boolean z = i3 == 90 || i3 == 270;
        int i6 = z ? i2 : i;
        if (!z) {
            i = i2;
        }
        return new SafeCloseImageReaderProxy(ImageReaderProxys.createIsolatedReader(i6, i, i4, i5));
    }

    void setRelativeRotation(int i) {
        this.mRelativeRotation = i;
    }

    void setOutputImageRotationEnabled(boolean z) {
        this.mOutputImageRotationEnabled = z;
    }

    void setOutputImageFormat(int i) {
        this.mOutputImageFormat = i;
    }

    void setOnePixelShiftEnabled(boolean z) {
        this.mOnePixelShiftEnabled = z;
    }

    void setViewPortCropRect(Rect rect) {
        synchronized (this.mAnalyzerLock) {
            this.mOriginalViewPortCropRect = rect;
            this.mUpdatedViewPortCropRect = new Rect(this.mOriginalViewPortCropRect);
        }
    }

    void setSensorToBufferTransformMatrix(Matrix matrix) {
        synchronized (this.mAnalyzerLock) {
            this.mOriginalSensorToBufferTransformMatrix = matrix;
            this.mUpdatedSensorToBufferTransformMatrix = new Matrix(this.mOriginalSensorToBufferTransformMatrix);
        }
    }

    void setProcessedImageReaderProxy(SafeCloseImageReaderProxy safeCloseImageReaderProxy) {
        synchronized (this.mAnalyzerLock) {
            this.mProcessedImageReaderProxy = safeCloseImageReaderProxy;
        }
    }

    void setAnalyzer(Executor executor, ImageAnalysis.Analyzer analyzer) {
        if (analyzer == null) {
            clearCache();
        }
        synchronized (this.mAnalyzerLock) {
            this.mSubscribedAnalyzer = analyzer;
            this.mUserExecutor = executor;
        }
    }

    void attach() {
        this.mIsAttached = true;
    }

    void detach() {
        this.mIsAttached = false;
        clearCache();
    }

    private void createHelperBuffer(ImageProxy imageProxy) {
        if (this.mOutputImageFormat == 1) {
            if (this.mYRotatedBuffer == null) {
                this.mYRotatedBuffer = ByteBuffer.allocateDirect(imageProxy.getWidth() * imageProxy.getHeight());
            }
            this.mYRotatedBuffer.position(0);
            if (this.mURotatedBuffer == null) {
                this.mURotatedBuffer = ByteBuffer.allocateDirect((imageProxy.getWidth() * imageProxy.getHeight()) / 4);
            }
            this.mURotatedBuffer.position(0);
            if (this.mVRotatedBuffer == null) {
                this.mVRotatedBuffer = ByteBuffer.allocateDirect((imageProxy.getWidth() * imageProxy.getHeight()) / 4);
            }
            this.mVRotatedBuffer.position(0);
            return;
        }
        if (this.mOutputImageFormat == 2 && this.mRGBConvertedBuffer == null) {
            this.mRGBConvertedBuffer = ByteBuffer.allocateDirect(imageProxy.getWidth() * imageProxy.getHeight() * 4);
        }
    }

    private void recreateImageReaderProxy(ImageProxy imageProxy, int i) {
        SafeCloseImageReaderProxy safeCloseImageReaderProxy = this.mProcessedImageReaderProxy;
        if (safeCloseImageReaderProxy == null) {
            return;
        }
        safeCloseImageReaderProxy.safeClose();
        this.mProcessedImageReaderProxy = createImageReaderProxy(imageProxy.getWidth(), imageProxy.getHeight(), i, this.mProcessedImageReaderProxy.getImageFormat(), this.mProcessedImageReaderProxy.getMaxImages());
        if (this.mOutputImageFormat == 1) {
            ImageWriter imageWriter = this.mProcessedImageWriter;
            if (imageWriter != null) {
                ImageWriterCompat.close(imageWriter);
            }
            this.mProcessedImageWriter = ImageWriterCompat.newInstance(this.mProcessedImageReaderProxy.getSurface(), this.mProcessedImageReaderProxy.getMaxImages());
        }
    }

    private void recalculateTransformMatrixAndCropRect(int i, int i2, int i3, int i4) {
        Matrix additionalTransformMatrixAppliedByProcessor = getAdditionalTransformMatrixAppliedByProcessor(i, i2, i3, i4, this.mRelativeRotation);
        this.mUpdatedViewPortCropRect = getUpdatedCropRect(this.mOriginalViewPortCropRect, additionalTransformMatrixAppliedByProcessor);
        this.mUpdatedSensorToBufferTransformMatrix.setConcat(this.mOriginalSensorToBufferTransformMatrix, additionalTransformMatrixAppliedByProcessor);
    }

    static Rect getUpdatedCropRect(Rect rect, Matrix matrix) {
        RectF rectF = new RectF(rect);
        matrix.mapRect(rectF);
        Rect rect2 = new Rect();
        rectF.round(rect2);
        return rect2;
    }

    static Matrix getAdditionalTransformMatrixAppliedByProcessor(int i, int i2, int i3, int i4, int i5) {
        Matrix matrix = new Matrix();
        if (i5 > 0) {
            matrix.setRectToRect(new RectF(0.0f, 0.0f, i, i2), TransformUtils.NORMALIZED_RECT, Matrix.ScaleToFit.FILL);
            matrix.postRotate(i5);
            matrix.postConcat(TransformUtils.getNormalizedToBuffer(new RectF(0.0f, 0.0f, i3, i4)));
        }
        return matrix;
    }
}
