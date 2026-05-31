package androidx.camera.video.internal.encoder;

import android.util.Range;

/* JADX INFO: loaded from: classes.dex */
public interface VideoEncoderInfo extends EncoderInfo {
    int getHeightAlignment();

    Range<Integer> getSupportedBitrateRange();

    Range<Integer> getSupportedHeights();

    Range<Integer> getSupportedHeightsFor(int i);

    Range<Integer> getSupportedWidths();

    Range<Integer> getSupportedWidthsFor(int i);

    int getWidthAlignment();

    boolean isSizeSupported(int i, int i2);
}
