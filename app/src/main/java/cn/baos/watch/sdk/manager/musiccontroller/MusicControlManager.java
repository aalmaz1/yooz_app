package cn.baos.watch.sdk.manager.musiccontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import android.os.SystemClock;
import android.view.KeyEvent;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.util.LogUtil;
import cn.baos.watch.sdk.util.W100Utils;
import cn.baos.watch.w100.messages.MusicControlRequest;
import cn.baos.watch.w100.messages.MusicControlResponse;

/* JADX INFO: loaded from: classes.dex */
public class MusicControlManager {
    private static MusicControlManager instance;
    private AudioManager mAudioManager;
    private Context mContext;
    private int mCurrentVolume;
    private KeyEvent mKeyEvent;
    private int mMaxValue;
    private int mMinValue;
    private MusicControlResponse mMusicControlResponse = new MusicControlResponse();
    Handler handler = new Handler();
    Runnable sendCommandToWatchResponse = new Runnable() { // from class: cn.baos.watch.sdk.manager.musiccontroller.MusicControlManager.1
        @Override // java.lang.Runnable
        public void run() {
            MessageManager.getInstance().sendMusicStatusAndVolume(MusicControlManager.this.mMusicControlResponse);
        }
    };
    Runnable sendCommandToWatch2 = new Runnable() { // from class: cn.baos.watch.sdk.manager.musiccontroller.MusicControlManager.2
        @Override // java.lang.Runnable
        public void run() {
            MessageManager.getInstance().sendMusicStatusAndVolume(MusicControlManager.this.mMusicControlResponse);
        }
    };
    boolean isNeedPushVolume = true;

    public static MusicControlManager getInstance() {
        if (instance == null) {
            synchronized (MusicControlManager.class) {
                if (instance == null) {
                    instance = new MusicControlManager();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        this.mContext = context;
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        this.mAudioManager = audioManager;
        this.mMaxValue = audioManager.getStreamMaxVolume(3);
        this.mMinValue = this.mAudioManager.getStreamMinVolume(3);
        this.mCurrentVolume = this.mAudioManager.getStreamVolume(3);
        LogUtil.d("最大音量:" + this.mMaxValue + " 最小音量:" + this.mMinValue + "当前音量:" + this.mCurrentVolume);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
        context.registerReceiver(new VolumeReceiver(), intentFilter);
    }

    public void playMusic() {
        LogUtil.d("playMusic");
        doMusicControlKeyEvent(126);
    }

    public void stopMusic() {
        LogUtil.d("stopMusic");
        doMusicControlKeyEvent(127);
    }

    public void nextMusic() {
        LogUtil.d("nextMusic");
        doMusicControlKeyEvent(87);
    }

    public void previousMusic() {
        LogUtil.d("previousMusic");
        doMusicControlKeyEvent(88);
    }

    public boolean doMusicControlKeyEvent(int i) {
        if (RemoteControllerManager.getInstance().getRemoteController() != null) {
            LogUtil.d("RemoteControllerManager control");
            this.mKeyEvent = new KeyEvent(0, i);
            boolean zSendMediaKeyEvent = RemoteControllerManager.getInstance().getRemoteController().sendMediaKeyEvent(this.mKeyEvent);
            this.mKeyEvent = new KeyEvent(1, i);
            return zSendMediaKeyEvent && RemoteControllerManager.getInstance().getRemoteController().sendMediaKeyEvent(this.mKeyEvent);
        }
        LogUtil.d("AudioManager control");
        long jUptimeMillis = SystemClock.uptimeMillis();
        KeyEvent keyEvent = new KeyEvent(jUptimeMillis, jUptimeMillis, 0, i, 0);
        dispatchMediaKeyToAudioService(keyEvent);
        dispatchMediaKeyToAudioService(KeyEvent.changeAction(keyEvent, 1));
        return false;
    }

    private void dispatchMediaKeyToAudioService(KeyEvent keyEvent) {
        AudioManager audioManager = this.mAudioManager;
        if (audioManager != null) {
            try {
                audioManager.dispatchMediaKeyEvent(keyEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isMusicPlaying() {
        LogUtil.d("当前播放状态:" + this.mAudioManager.isMusicActive());
        return this.mAudioManager.isMusicActive();
    }

    public int getCurrentVolume() {
        AudioManager audioManager = this.mAudioManager;
        if (audioManager != null) {
            this.mCurrentVolume = audioManager.getStreamVolume(3);
        }
        LogUtil.d("当前音量:" + this.mCurrentVolume);
        return this.mCurrentVolume;
    }

    public void turnUpVolume() {
        int streamVolume = this.mAudioManager.getStreamVolume(3);
        this.mCurrentVolume = streamVolume;
        if (streamVolume < this.mMaxValue) {
            this.mCurrentVolume = streamVolume + 1;
        }
        LogUtil.d("调大音量到:" + this.mCurrentVolume);
        this.mAudioManager.setStreamVolume(3, this.mCurrentVolume, 1);
    }

    public void turnDownVolume() {
        int streamVolume = this.mAudioManager.getStreamVolume(3);
        this.mCurrentVolume = streamVolume;
        if (streamVolume > this.mMinValue) {
            this.mCurrentVolume = streamVolume - 1;
        }
        LogUtil.d("调小音量到:" + this.mCurrentVolume);
        this.mAudioManager.setStreamVolume(3, this.mCurrentVolume, 1);
    }

    public void setVolume(int i) {
        LogUtil.d("收到音量:" + i + " 音量最大值:" + this.mMaxValue);
        this.mCurrentVolume = (i * this.mMaxValue) / 100;
        LogUtil.d("设置音量:" + this.mCurrentVolume + " 音量最大值:" + this.mMaxValue);
        this.mAudioManager.setStreamVolume(3, this.mCurrentVolume, 1);
    }

    public void sendSongStatusAndVolumeToWatchResponse() {
        if (this.mMaxValue != 0) {
            this.mMusicControlResponse.volumn = (getCurrentVolume() * 100) / this.mMaxValue;
            this.mMusicControlResponse.rank = this.mMaxValue;
        }
        this.handler.removeCallbacks(this.sendCommandToWatchResponse);
        this.handler.postDelayed(this.sendCommandToWatchResponse, 100L);
    }

    public void sendSongStatusAndVolumeToWatch(MusicControlResponse musicControlResponse) {
        if (this.mMaxValue != 0) {
            musicControlResponse.volumn = (getCurrentVolume() * 100) / this.mMaxValue;
            musicControlResponse.rank = this.mMaxValue;
        }
        this.mMusicControlResponse = musicControlResponse;
        this.handler.removeCallbacks(this.sendCommandToWatch2);
        this.handler.postDelayed(this.sendCommandToWatch2, 200L);
    }

    public void sendVolumeWhenChange() {
        MusicControlResponse musicControlResponse;
        if (!this.isNeedPushVolume || (musicControlResponse = this.mMusicControlResponse) == null) {
            return;
        }
        musicControlResponse.volumn = (getCurrentVolume() * 100) / this.mMaxValue;
        this.mMusicControlResponse.rank = this.mMaxValue;
        MessageManager.getInstance().sendMusicStatusAndVolume(this.mMusicControlResponse);
    }

    public void handleCommandFromWatchToControlMusic(MusicControlRequest musicControlRequest) {
        LogUtil.d("手表端音乐请求指令:" + W100Utils.toString(musicControlRequest));
        int i = musicControlRequest.action;
        if (i == 0) {
            getInstance().sendSongStatusAndVolumeToWatchResponse();
            return;
        }
        if (i == 1) {
            playMusic();
            return;
        }
        if (i == 2) {
            stopMusic();
            return;
        }
        if (i == 3) {
            previousMusic();
            return;
        }
        if (i == 4) {
            nextMusic();
            return;
        }
        if (i == 5) {
            setVolume(musicControlRequest.volumn);
        } else if (i == 20) {
            turnUpVolume();
        } else {
            if (i != 21) {
                return;
            }
            turnDownVolume();
        }
    }

    public MusicInfo getMusicInfo() {
        return new MusicInfo();
    }

    private class MusicInfo {
        private String musicName;
        private String singerName;

        private MusicInfo() {
        }

        public String getSingerName() {
            return this.singerName;
        }

        public void setSingerName(String str) {
            this.singerName = str;
        }

        public String getMusicName() {
            return this.musicName;
        }

        public void setMusicName(String str) {
            this.musicName = str;
        }
    }

    class VolumeReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
        }

        VolumeReceiver() {
        }
    }
}
