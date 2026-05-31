package androidx.heifwriter;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.media.Image;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.Surface;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class HeifEncoder implements AutoCloseable, SurfaceTexture.OnFrameAvailableListener {
    private static final boolean DEBUG = false;
    private static final int GRID_HEIGHT = 512;
    private static final int GRID_WIDTH = 512;
    private static final int INPUT_BUFFER_POOL_SIZE = 2;
    public static final int INPUT_MODE_BITMAP = 2;
    public static final int INPUT_MODE_BUFFER = 0;
    public static final int INPUT_MODE_SURFACE = 1;
    private static final double MAX_COMPRESS_RATIO = 0.25d;
    private static final String TAG = "HeifEncoder";
    final Callback mCallback;
    private ByteBuffer mCurrentBuffer;
    private final Rect mDstRect;
    SurfaceEOSTracker mEOSTracker;
    MediaCodec mEncoder;
    private EglWindowSurface mEncoderEglSurface;
    private Surface mEncoderSurface;
    final int mGridCols;
    final int mGridHeight;
    final int mGridRows;
    final int mGridWidth;
    final Handler mHandler;
    private final HandlerThread mHandlerThread;
    final int mHeight;
    boolean mInputEOS;
    private int mInputIndex;
    private final int mInputMode;
    private Surface mInputSurface;
    private SurfaceTexture mInputTexture;
    private final int mNumTiles;
    private EglRectBlt mRectBlt;
    private final Rect mSrcRect;
    private int mTextureId;
    final boolean mUseGrid;
    final int mWidth;
    private final ArrayList<ByteBuffer> mEmptyBuffers = new ArrayList<>();
    private final ArrayList<ByteBuffer> mFilledBuffers = new ArrayList<>();
    final ArrayList<Integer> mCodecInputBuffers = new ArrayList<>();
    private final float[] mTmpMatrix = new float[16];

    public static abstract class Callback {
        public abstract void onComplete(HeifEncoder heifEncoder);

        public abstract void onDrainOutputBuffer(HeifEncoder heifEncoder, ByteBuffer byteBuffer);

        public abstract void onError(HeifEncoder heifEncoder, MediaCodec.CodecException codecException);

        public abstract void onOutputFormatChanged(HeifEncoder heifEncoder, MediaFormat mediaFormat);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface InputMode {
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0237  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public HeifEncoder(int r20, int r21, boolean r22, int r23, int r24, android.os.Handler r25, androidx.heifwriter.HeifEncoder.Callback r26) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 624
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.heifwriter.HeifEncoder.<init>(int, int, boolean, int, int, android.os.Handler, androidx.heifwriter.HeifEncoder$Callback):void");
    }

    private void copyTilesGL() {
        GLES20.glViewport(0, 0, this.mGridWidth, this.mGridHeight);
        for (int i = 0; i < this.mGridRows; i++) {
            for (int i2 = 0; i2 < this.mGridCols; i2++) {
                int i3 = this.mGridWidth;
                int i4 = i2 * i3;
                int i5 = this.mGridHeight;
                int i6 = i * i5;
                this.mSrcRect.set(i4, i6, i3 + i4, i5 + i6);
                this.mRectBlt.copyRect(this.mTextureId, Texture2dProgram.V_FLIP_MATRIX, this.mSrcRect);
                EglWindowSurface eglWindowSurface = this.mEncoderEglSurface;
                int i7 = this.mInputIndex;
                this.mInputIndex = i7 + 1;
                eglWindowSurface.setPresentationTime(computePresentationTime(i7) * 1000);
                this.mEncoderEglSurface.swapBuffers();
            }
        }
    }

    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        synchronized (this) {
            EglWindowSurface eglWindowSurface = this.mEncoderEglSurface;
            if (eglWindowSurface == null) {
                return;
            }
            eglWindowSurface.makeCurrent();
            surfaceTexture.updateTexImage();
            surfaceTexture.getTransformMatrix(this.mTmpMatrix);
            if (this.mEOSTracker.updateLastInputAndEncoderTime(surfaceTexture.getTimestamp(), computePresentationTime((this.mInputIndex + this.mNumTiles) - 1))) {
                copyTilesGL();
            }
            surfaceTexture.releaseTexImage();
            this.mEncoderEglSurface.makeUnCurrent();
        }
    }

    public void start() {
        this.mEncoder.start();
    }

    public void addYuvBuffer(int i, byte[] bArr) {
        if (this.mInputMode != 0) {
            throw new IllegalStateException("addYuvBuffer is only allowed in buffer input mode");
        }
        if (bArr == null || bArr.length != ((this.mWidth * this.mHeight) * 3) / 2) {
            throw new IllegalArgumentException("invalid data");
        }
        addYuvBufferInternal(bArr);
    }

    public Surface getInputSurface() {
        if (this.mInputMode != 1) {
            throw new IllegalStateException("getInputSurface is only allowed in surface input mode");
        }
        return this.mInputSurface;
    }

    public void setEndOfInputStreamTimestamp(long j) {
        if (this.mInputMode != 1) {
            throw new IllegalStateException("setEndOfInputStreamTimestamp is only allowed in surface input mode");
        }
        SurfaceEOSTracker surfaceEOSTracker = this.mEOSTracker;
        if (surfaceEOSTracker != null) {
            surfaceEOSTracker.updateInputEOSTime(j);
        }
    }

    public void addBitmap(Bitmap bitmap) {
        if (this.mInputMode != 2) {
            throw new IllegalStateException("addBitmap is only allowed in bitmap input mode");
        }
        if (this.mEOSTracker.updateLastInputAndEncoderTime(computePresentationTime(this.mInputIndex) * 1000, computePresentationTime((this.mInputIndex + this.mNumTiles) - 1))) {
            synchronized (this) {
                EglWindowSurface eglWindowSurface = this.mEncoderEglSurface;
                if (eglWindowSurface == null) {
                    return;
                }
                eglWindowSurface.makeCurrent();
                this.mRectBlt.loadTexture(this.mTextureId, bitmap);
                copyTilesGL();
                this.mEncoderEglSurface.makeUnCurrent();
            }
        }
    }

    public void stopAsync() {
        int i = this.mInputMode;
        if (i == 2) {
            this.mEOSTracker.updateInputEOSTime(0L);
        } else if (i == 0) {
            addYuvBufferInternal(null);
        }
    }

    private long computePresentationTime(int i) {
        return ((((long) i) * 1000000) / ((long) this.mNumTiles)) + 132;
    }

    private void addYuvBufferInternal(byte[] bArr) {
        ByteBuffer byteBufferAcquireEmptyBuffer = acquireEmptyBuffer();
        if (byteBufferAcquireEmptyBuffer == null) {
            return;
        }
        byteBufferAcquireEmptyBuffer.clear();
        if (bArr != null) {
            byteBufferAcquireEmptyBuffer.put(bArr);
        }
        byteBufferAcquireEmptyBuffer.flip();
        synchronized (this.mFilledBuffers) {
            this.mFilledBuffers.add(byteBufferAcquireEmptyBuffer);
        }
        this.mHandler.post(new Runnable() { // from class: androidx.heifwriter.HeifEncoder.1
            @Override // java.lang.Runnable
            public void run() {
                HeifEncoder.this.maybeCopyOneTileYUV();
            }
        });
    }

    void maybeCopyOneTileYUV() {
        while (true) {
            ByteBuffer currentBuffer = getCurrentBuffer();
            if (currentBuffer == null || this.mCodecInputBuffers.isEmpty()) {
                return;
            }
            int iIntValue = this.mCodecInputBuffers.remove(0).intValue();
            boolean z = this.mInputIndex % this.mNumTiles == 0 && currentBuffer.remaining() == 0;
            if (!z) {
                Image inputImage = this.mEncoder.getInputImage(iIntValue);
                int i = this.mGridWidth;
                int i2 = this.mInputIndex;
                int i3 = this.mGridCols;
                int i4 = (i2 % i3) * i;
                int i5 = this.mGridHeight;
                int i6 = ((i2 / i3) % this.mGridRows) * i5;
                this.mSrcRect.set(i4, i6, i + i4, i5 + i6);
                copyOneTileYUV(currentBuffer, inputImage, this.mWidth, this.mHeight, this.mSrcRect, this.mDstRect);
            }
            MediaCodec mediaCodec = this.mEncoder;
            int iCapacity = z ? 0 : mediaCodec.getInputBuffer(iIntValue).capacity();
            int i7 = this.mInputIndex;
            this.mInputIndex = i7 + 1;
            mediaCodec.queueInputBuffer(iIntValue, 0, iCapacity, computePresentationTime(i7), z ? 4 : 0);
            if (z || this.mInputIndex % this.mNumTiles == 0) {
                returnEmptyBufferAndNotify(z);
            }
        }
    }

    private static void copyOneTileYUV(ByteBuffer byteBuffer, Image image, int i, int i2, Rect rect, Rect rect2) {
        int i3;
        int i4;
        if (rect.width() != rect2.width() || rect.height() != rect2.height()) {
            throw new IllegalArgumentException("src and dst rect size are different!");
        }
        if (i % 2 == 0 && i2 % 2 == 0) {
            int i5 = 2;
            if (rect.left % 2 == 0 && rect.top % 2 == 0 && rect.right % 2 == 0 && rect.bottom % 2 == 0 && rect2.left % 2 == 0 && rect2.top % 2 == 0 && rect2.right % 2 == 0 && rect2.bottom % 2 == 0) {
                Image.Plane[] planes = image.getPlanes();
                int i6 = 0;
                while (i6 < planes.length) {
                    ByteBuffer buffer = planes[i6].getBuffer();
                    int pixelStride = planes[i6].getPixelStride();
                    int iMin = Math.min(rect.width(), i - rect.left);
                    int iMin2 = Math.min(rect.height(), i2 - rect.top);
                    if (i6 > 0) {
                        i4 = ((i * i2) * (i6 + 3)) / 4;
                        i3 = i5;
                    } else {
                        i3 = 1;
                        i4 = 0;
                    }
                    for (int i7 = 0; i7 < iMin2 / i3; i7++) {
                        byteBuffer.position(((((rect.top / i3) + i7) * i) / i3) + i4 + (rect.left / i3));
                        buffer.position((((rect2.top / i3) + i7) * planes[i6].getRowStride()) + ((rect2.left * pixelStride) / i3));
                        int i8 = 0;
                        while (true) {
                            int i9 = iMin / i3;
                            if (i8 < i9) {
                                buffer.put(byteBuffer.get());
                                if (pixelStride > 1 && i8 != i9 - 1) {
                                    buffer.position((buffer.position() + pixelStride) - 1);
                                }
                                i8++;
                            }
                        }
                    }
                    i6++;
                    i5 = 2;
                }
                return;
            }
        }
        throw new IllegalArgumentException("src or dst are not aligned!");
    }

    private ByteBuffer acquireEmptyBuffer() {
        ByteBuffer byteBufferRemove;
        synchronized (this.mEmptyBuffers) {
            while (!this.mInputEOS && this.mEmptyBuffers.isEmpty()) {
                try {
                    this.mEmptyBuffers.wait();
                } catch (InterruptedException unused) {
                }
            }
            byteBufferRemove = this.mInputEOS ? null : this.mEmptyBuffers.remove(0);
        }
        return byteBufferRemove;
    }

    private ByteBuffer getCurrentBuffer() {
        if (!this.mInputEOS && this.mCurrentBuffer == null) {
            synchronized (this.mFilledBuffers) {
                this.mCurrentBuffer = this.mFilledBuffers.isEmpty() ? null : this.mFilledBuffers.remove(0);
            }
        }
        if (this.mInputEOS) {
            return null;
        }
        return this.mCurrentBuffer;
    }

    private void returnEmptyBufferAndNotify(boolean z) {
        synchronized (this.mEmptyBuffers) {
            this.mInputEOS = z | this.mInputEOS;
            this.mEmptyBuffers.add(this.mCurrentBuffer);
            this.mEmptyBuffers.notifyAll();
        }
        this.mCurrentBuffer = null;
    }

    void stopInternal() {
        MediaCodec mediaCodec = this.mEncoder;
        if (mediaCodec != null) {
            mediaCodec.stop();
            this.mEncoder.release();
            this.mEncoder = null;
        }
        synchronized (this.mEmptyBuffers) {
            this.mInputEOS = true;
            this.mEmptyBuffers.notifyAll();
        }
        synchronized (this) {
            EglRectBlt eglRectBlt = this.mRectBlt;
            if (eglRectBlt != null) {
                eglRectBlt.release(false);
                this.mRectBlt = null;
            }
            EglWindowSurface eglWindowSurface = this.mEncoderEglSurface;
            if (eglWindowSurface != null) {
                eglWindowSurface.release();
                this.mEncoderEglSurface = null;
            }
            SurfaceTexture surfaceTexture = this.mInputTexture;
            if (surfaceTexture != null) {
                surfaceTexture.release();
                this.mInputTexture = null;
            }
        }
    }

    private class SurfaceEOSTracker {
        private static final boolean DEBUG_EOS = false;
        final boolean mCopyTiles;
        boolean mSignaled;
        long mInputEOSTimeNs = -1;
        long mLastInputTimeNs = -1;
        long mEncoderEOSTimeUs = -1;
        long mLastEncoderTimeUs = -1;
        long mLastOutputTimeUs = -1;

        SurfaceEOSTracker(boolean z) {
            this.mCopyTiles = z;
        }

        synchronized void updateInputEOSTime(long j) {
            if (this.mCopyTiles) {
                if (this.mInputEOSTimeNs < 0) {
                    this.mInputEOSTimeNs = j;
                }
            } else if (this.mEncoderEOSTimeUs < 0) {
                this.mEncoderEOSTimeUs = j / 1000;
            }
            updateEOSLocked();
        }

        synchronized boolean updateLastInputAndEncoderTime(long j, long j2) {
            boolean z;
            long j3 = this.mInputEOSTimeNs;
            z = j3 < 0 || j <= j3;
            if (z) {
                this.mLastEncoderTimeUs = j2;
            }
            this.mLastInputTimeNs = j;
            updateEOSLocked();
            return z;
        }

        synchronized void updateLastOutputTime(long j) {
            this.mLastOutputTimeUs = j;
            updateEOSLocked();
        }

        private void updateEOSLocked() {
            if (this.mSignaled) {
                return;
            }
            if (this.mEncoderEOSTimeUs < 0) {
                long j = this.mInputEOSTimeNs;
                if (j >= 0 && this.mLastInputTimeNs >= j) {
                    long j2 = this.mLastEncoderTimeUs;
                    if (j2 < 0) {
                        doSignalEOSLocked();
                        return;
                    }
                    this.mEncoderEOSTimeUs = j2;
                }
            }
            long j3 = this.mEncoderEOSTimeUs;
            if (j3 < 0 || j3 > this.mLastOutputTimeUs) {
                return;
            }
            doSignalEOSLocked();
        }

        private void doSignalEOSLocked() {
            HeifEncoder.this.mHandler.post(new Runnable() { // from class: androidx.heifwriter.HeifEncoder.SurfaceEOSTracker.1
                @Override // java.lang.Runnable
                public void run() {
                    if (HeifEncoder.this.mEncoder != null) {
                        HeifEncoder.this.mEncoder.signalEndOfInputStream();
                    }
                }
            });
            this.mSignaled = true;
        }
    }

    class EncoderCallback extends MediaCodec.Callback {
        private boolean mOutputEOS;

        EncoderCallback() {
        }

        @Override // android.media.MediaCodec.Callback
        public void onOutputFormatChanged(MediaCodec mediaCodec, MediaFormat mediaFormat) {
            if (mediaCodec != HeifEncoder.this.mEncoder) {
                return;
            }
            if (!"image/vnd.android.heic".equals(mediaFormat.getString("mime"))) {
                mediaFormat.setString("mime", "image/vnd.android.heic");
                mediaFormat.setInteger("width", HeifEncoder.this.mWidth);
                mediaFormat.setInteger("height", HeifEncoder.this.mHeight);
                if (HeifEncoder.this.mUseGrid) {
                    mediaFormat.setInteger("tile-width", HeifEncoder.this.mGridWidth);
                    mediaFormat.setInteger("tile-height", HeifEncoder.this.mGridHeight);
                    mediaFormat.setInteger("grid-rows", HeifEncoder.this.mGridRows);
                    mediaFormat.setInteger("grid-cols", HeifEncoder.this.mGridCols);
                }
            }
            HeifEncoder.this.mCallback.onOutputFormatChanged(HeifEncoder.this, mediaFormat);
        }

        @Override // android.media.MediaCodec.Callback
        public void onInputBufferAvailable(MediaCodec mediaCodec, int i) {
            if (mediaCodec != HeifEncoder.this.mEncoder || HeifEncoder.this.mInputEOS) {
                return;
            }
            HeifEncoder.this.mCodecInputBuffers.add(Integer.valueOf(i));
            HeifEncoder.this.maybeCopyOneTileYUV();
        }

        @Override // android.media.MediaCodec.Callback
        public void onOutputBufferAvailable(MediaCodec mediaCodec, int i, MediaCodec.BufferInfo bufferInfo) {
            if (mediaCodec != HeifEncoder.this.mEncoder || this.mOutputEOS) {
                return;
            }
            if (bufferInfo.size > 0 && (bufferInfo.flags & 2) == 0) {
                ByteBuffer outputBuffer = mediaCodec.getOutputBuffer(i);
                outputBuffer.position(bufferInfo.offset);
                outputBuffer.limit(bufferInfo.offset + bufferInfo.size);
                if (HeifEncoder.this.mEOSTracker != null) {
                    HeifEncoder.this.mEOSTracker.updateLastOutputTime(bufferInfo.presentationTimeUs);
                }
                HeifEncoder.this.mCallback.onDrainOutputBuffer(HeifEncoder.this, outputBuffer);
            }
            this.mOutputEOS = ((bufferInfo.flags & 4) != 0) | this.mOutputEOS;
            mediaCodec.releaseOutputBuffer(i, false);
            if (this.mOutputEOS) {
                stopAndNotify(null);
            }
        }

        @Override // android.media.MediaCodec.Callback
        public void onError(MediaCodec mediaCodec, MediaCodec.CodecException codecException) {
            if (mediaCodec != HeifEncoder.this.mEncoder) {
                return;
            }
            Log.e(HeifEncoder.TAG, "onError: " + codecException);
            stopAndNotify(codecException);
        }

        private void stopAndNotify(MediaCodec.CodecException codecException) {
            HeifEncoder.this.stopInternal();
            if (codecException == null) {
                HeifEncoder.this.mCallback.onComplete(HeifEncoder.this);
            } else {
                HeifEncoder.this.mCallback.onError(HeifEncoder.this, codecException);
            }
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this.mEmptyBuffers) {
            this.mInputEOS = true;
            this.mEmptyBuffers.notifyAll();
        }
        this.mHandler.postAtFrontOfQueue(new Runnable() { // from class: androidx.heifwriter.HeifEncoder.2
            @Override // java.lang.Runnable
            public void run() {
                HeifEncoder.this.stopInternal();
            }
        });
    }
}
