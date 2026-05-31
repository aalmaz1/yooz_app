package com.king.camera.scan.manager;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import com.king.camera.scan.R;
import com.king.camera.scan.util.LogUtils;
import java.io.Closeable;

/* JADX INFO: loaded from: classes2.dex */
public final class BeepManager implements MediaPlayer.OnErrorListener, Closeable {
    private static final long VIBRATE_DURATION = 200;
    private final Context context;
    private MediaPlayer mediaPlayer = null;
    private boolean playBeep;
    private boolean vibrate;
    private Vibrator vibrator;

    public BeepManager(Context context) {
        this.context = context;
        updatePrefs();
    }

    public void setVibrate(boolean z) {
        this.vibrate = z;
    }

    public void setPlayBeep(boolean z) {
        this.playBeep = z;
    }

    private synchronized void updatePrefs() {
        if (this.mediaPlayer == null) {
            this.mediaPlayer = buildMediaPlayer(this.context);
        }
        if (this.vibrator == null) {
            if (Build.VERSION.SDK_INT >= 31) {
                this.vibrator = ((VibratorManager) this.context.getSystemService("vibrator_manager")).getDefaultVibrator();
            } else {
                this.vibrator = (Vibrator) this.context.getSystemService("vibrator");
            }
        }
    }

    public synchronized void playBeepSoundAndVibrate() {
        MediaPlayer mediaPlayer;
        if (this.playBeep && (mediaPlayer = this.mediaPlayer) != null) {
            mediaPlayer.start();
        }
        if (this.vibrate && this.vibrator.hasVibrator()) {
            this.vibrator.vibrate(VibrationEffect.createOneShot(VIBRATE_DURATION, -1));
        }
    }

    private MediaPlayer buildMediaPlayer(Context context) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor assetFileDescriptorOpenRawResourceFd = context.getResources().openRawResourceFd(R.raw.camera_scan_beep);
            mediaPlayer.setDataSource(assetFileDescriptorOpenRawResourceFd.getFileDescriptor(), assetFileDescriptorOpenRawResourceFd.getStartOffset(), assetFileDescriptorOpenRawResourceFd.getLength());
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setLooping(false);
            mediaPlayer.prepare();
            return mediaPlayer;
        } catch (Exception e) {
            LogUtils.w(e);
            mediaPlayer.release();
            return null;
        }
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public synchronized boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        close();
        updatePrefs();
        return true;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
        try {
            MediaPlayer mediaPlayer = this.mediaPlayer;
            if (mediaPlayer != null) {
                mediaPlayer.release();
                this.mediaPlayer = null;
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
    }
}
