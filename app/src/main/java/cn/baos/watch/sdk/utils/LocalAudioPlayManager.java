package cn.baos.watch.sdk.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class LocalAudioPlayManager implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {
    private static LocalAudioPlayManager instance;
    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private long startPlayTime;

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        return false;
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mediaPlayer) {
    }

    public static LocalAudioPlayManager getInstance() {
        if (instance == null) {
            instance = new LocalAudioPlayManager();
        }
        return instance;
    }

    private LocalAudioPlayManager() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.mMediaPlayer = mediaPlayer;
        mediaPlayer.setOnPreparedListener(this);
        this.mMediaPlayer.setOnCompletionListener(this);
        this.mMediaPlayer.setOnErrorListener(this);
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void playAudio() {
        try {
            LogUtil.d("开始播放音频");
            if (this.mMediaPlayer.isPlaying()) {
                return;
            }
            AssetFileDescriptor assetFileDescriptorOpenFd = this.mContext.getResources().getAssets().openFd("findphone.wav");
            this.mMediaPlayer.setAudioStreamType(4);
            this.mMediaPlayer.setDataSource(assetFileDescriptorOpenFd.getFileDescriptor(), assetFileDescriptorOpenFd.getStartOffset(), assetFileDescriptorOpenFd.getLength());
            this.mMediaPlayer.prepare();
            this.mMediaPlayer.start();
            this.startPlayTime = System.currentTimeMillis();
            this.mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: cn.baos.watch.sdk.utils.LocalAudioPlayManager.1
                @Override // android.media.MediaPlayer.OnCompletionListener
                public void onCompletion(MediaPlayer mediaPlayer) {
                    LogUtil.d("音频播放完成，开始循环播放");
                    if (System.currentTimeMillis() - LocalAudioPlayManager.this.startPlayTime > 1000) {
                        LocalAudioPlayManager.this.startPlayTime = System.currentTimeMillis();
                        LocalAudioPlayManager.this.mMediaPlayer.start();
                        LocalAudioPlayManager.this.mMediaPlayer.setLooping(true);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopAudio() {
        LogUtil.d("停止播放音频");
        if (this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.stop();
            this.mMediaPlayer.reset();
        }
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) {
        this.mMediaPlayer.stop();
        this.mMediaPlayer.reset();
    }
}
