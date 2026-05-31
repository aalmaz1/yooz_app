package com.fluttercandies.photo_manager.core.utils;

import android.media.MediaPlayer;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: VideoUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0007B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\b"}, d2 = {"Lcom/fluttercandies/photo_manager/core/utils/VideoUtils;", "", "()V", "getPropertiesUseMediaPlayer", "Lcom/fluttercandies/photo_manager/core/utils/VideoUtils$VideoInfo;", "path", "", "VideoInfo", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VideoUtils {
    public static final VideoUtils INSTANCE = new VideoUtils();

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean getPropertiesUseMediaPlayer$lambda$0(MediaPlayer mediaPlayer, int i, int i2) {
        return true;
    }

    /* JADX INFO: compiled from: VideoUtils.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\bJ\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\bJ\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\bJ2\u0010\u0013\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010\u0014J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u001e\u0010\u0005\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u000b\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001e\u0010\u0004\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u000b\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u001e\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u000b\u001a\u0004\b\u000e\u0010\b\"\u0004\b\u000f\u0010\n¨\u0006\u001b"}, d2 = {"Lcom/fluttercandies/photo_manager/core/utils/VideoUtils$VideoInfo;", "", "width", "", "height", "duration", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)V", "getDuration", "()Ljava/lang/Integer;", "setDuration", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getHeight", "setHeight", "getWidth", "setWidth", "component1", "component2", "component3", "copy", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;)Lcom/fluttercandies/photo_manager/core/utils/VideoUtils$VideoInfo;", "equals", "", "other", "hashCode", "toString", "", "photo_manager_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class VideoInfo {
        private Integer duration;
        private Integer height;
        private Integer width;

        public static /* synthetic */ VideoInfo copy$default(VideoInfo videoInfo, Integer num, Integer num2, Integer num3, int i, Object obj) {
            if ((i & 1) != 0) {
                num = videoInfo.width;
            }
            if ((i & 2) != 0) {
                num2 = videoInfo.height;
            }
            if ((i & 4) != 0) {
                num3 = videoInfo.duration;
            }
            return videoInfo.copy(num, num2, num3);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final Integer getWidth() {
            return this.width;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Integer getHeight() {
            return this.height;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final Integer getDuration() {
            return this.duration;
        }

        public final VideoInfo copy(Integer width, Integer height, Integer duration) {
            return new VideoInfo(width, height, duration);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof VideoInfo)) {
                return false;
            }
            VideoInfo videoInfo = (VideoInfo) other;
            return Intrinsics.areEqual(this.width, videoInfo.width) && Intrinsics.areEqual(this.height, videoInfo.height) && Intrinsics.areEqual(this.duration, videoInfo.duration);
        }

        public int hashCode() {
            Integer num = this.width;
            int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
            Integer num2 = this.height;
            int iHashCode2 = (iHashCode + (num2 == null ? 0 : num2.hashCode())) * 31;
            Integer num3 = this.duration;
            return iHashCode2 + (num3 != null ? num3.hashCode() : 0);
        }

        public String toString() {
            return "VideoInfo(width=" + this.width + ", height=" + this.height + ", duration=" + this.duration + ")";
        }

        public VideoInfo(Integer num, Integer num2, Integer num3) {
            this.width = num;
            this.height = num2;
            this.duration = num3;
        }

        public final Integer getDuration() {
            return this.duration;
        }

        public final Integer getHeight() {
            return this.height;
        }

        public final Integer getWidth() {
            return this.width;
        }

        public final void setDuration(Integer num) {
            this.duration = num;
        }

        public final void setHeight(Integer num) {
            this.height = num;
        }

        public final void setWidth(Integer num) {
            this.width = num;
        }
    }

    private VideoUtils() {
    }

    public final VideoInfo getPropertiesUseMediaPlayer(String path) throws IOException {
        Intrinsics.checkNotNullParameter(path, "path");
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(path);
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.fluttercandies.photo_manager.core.utils.VideoUtils$$ExternalSyntheticLambda0
            @Override // android.media.MediaPlayer.OnErrorListener
            public final boolean onError(MediaPlayer mediaPlayer2, int i, int i2) {
                return VideoUtils.getPropertiesUseMediaPlayer$lambda$0(mediaPlayer2, i, i2);
            }
        });
        try {
            mediaPlayer.prepare();
            mediaPlayer.getVideoHeight();
            VideoInfo videoInfo = new VideoInfo(Integer.valueOf(mediaPlayer.getVideoWidth()), Integer.valueOf(mediaPlayer.getVideoHeight()), Integer.valueOf(mediaPlayer.getDuration()));
            mediaPlayer.stop();
            mediaPlayer.release();
            return videoInfo;
        } catch (Throwable unused) {
            mediaPlayer.release();
            return new VideoInfo(null, null, null);
        }
    }
}
