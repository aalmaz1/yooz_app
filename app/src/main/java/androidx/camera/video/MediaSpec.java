package androidx.camera.video;

import androidx.camera.video.AudioSpec;
import androidx.camera.video.AutoValue_MediaSpec;
import androidx.camera.video.VideoSpec;
import androidx.core.util.Consumer;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class MediaSpec {
    private static final int AAC_DEFAULT_PROFILE = 2;
    private static final String AUDIO_ENCODER_MIME_MPEG4_DEFAULT = "audio/mp4a-latm";
    private static final String AUDIO_ENCODER_MIME_WEBM_DEFAULT = "audio/vorbis";
    public static final int OUTPUT_FORMAT_AUTO = -1;
    public static final int OUTPUT_FORMAT_MPEG_4 = 0;
    public static final int OUTPUT_FORMAT_WEBM = 1;
    private static final String VIDEO_ENCODER_MIME_MPEG4_DEFAULT = "video/avc";
    private static final String VIDEO_ENCODER_MIME_WEBM_DEFAULT = "video/x-vnd.on2.vp8";

    @Retention(RetentionPolicy.SOURCE)
    public @interface OutputFormat {
    }

    public static String outputFormatToAudioMime(int i) {
        return i != 1 ? AUDIO_ENCODER_MIME_MPEG4_DEFAULT : AUDIO_ENCODER_MIME_WEBM_DEFAULT;
    }

    static int outputFormatToMuxerFormat(int i) {
        return i != 1 ? 0 : 1;
    }

    public static String outputFormatToVideoMime(int i) {
        return i != 1 ? VIDEO_ENCODER_MIME_MPEG4_DEFAULT : VIDEO_ENCODER_MIME_WEBM_DEFAULT;
    }

    public abstract AudioSpec getAudioSpec();

    public abstract int getOutputFormat();

    public abstract VideoSpec getVideoSpec();

    public abstract Builder toBuilder();

    public static int outputFormatToAudioProfile(int i) {
        return Objects.equals(outputFormatToAudioMime(i), AUDIO_ENCODER_MIME_MPEG4_DEFAULT) ? 2 : -1;
    }

    MediaSpec() {
    }

    public static Builder builder() {
        return new AutoValue_MediaSpec.Builder().setOutputFormat(-1).setAudioSpec(AudioSpec.builder().build()).setVideoSpec(VideoSpec.builder().build());
    }

    public static abstract class Builder {
        public abstract MediaSpec build();

        abstract AudioSpec getAudioSpec();

        abstract VideoSpec getVideoSpec();

        public abstract Builder setAudioSpec(AudioSpec audioSpec);

        public abstract Builder setOutputFormat(int i);

        public abstract Builder setVideoSpec(VideoSpec videoSpec);

        Builder() {
        }

        public Builder configureAudio(Consumer<AudioSpec.Builder> consumer) {
            AudioSpec.Builder builder = getAudioSpec().toBuilder();
            consumer.accept(builder);
            setAudioSpec(builder.build());
            return this;
        }

        public Builder configureVideo(Consumer<VideoSpec.Builder> consumer) {
            VideoSpec.Builder builder = getVideoSpec().toBuilder();
            consumer.accept(builder);
            setVideoSpec(builder.build());
            return this;
        }
    }
}
