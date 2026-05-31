package com.king.zxing.analyze;

import android.graphics.Rect;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.king.zxing.DecodeConfig;
import com.king.zxing.DecodeFormatManager;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public abstract class AreaRectAnalyzer extends ImageAnalyzer {
    boolean isMultiDecode;
    private int mAreaRectHorizontalOffset;
    private float mAreaRectRatio;
    private int mAreaRectVerticalOffset;
    DecodeConfig mDecodeConfig;
    Map<DecodeHintType, ?> mHints;

    public abstract Result analyze(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6);

    public AreaRectAnalyzer(DecodeConfig decodeConfig) {
        this.isMultiDecode = true;
        this.mAreaRectRatio = 0.8f;
        this.mAreaRectHorizontalOffset = 0;
        this.mAreaRectVerticalOffset = 0;
        this.mDecodeConfig = decodeConfig;
        if (decodeConfig != null) {
            this.mHints = decodeConfig.getHints();
            this.isMultiDecode = decodeConfig.isMultiDecode();
            this.mAreaRectRatio = decodeConfig.getAreaRectRatio();
            this.mAreaRectHorizontalOffset = decodeConfig.getAreaRectHorizontalOffset();
            this.mAreaRectVerticalOffset = decodeConfig.getAreaRectVerticalOffset();
            return;
        }
        this.mHints = DecodeFormatManager.DEFAULT_HINTS;
    }

    @Override // com.king.zxing.analyze.ImageAnalyzer
    public Result analyze(byte[] bArr, int i, int i2) {
        DecodeConfig decodeConfig = this.mDecodeConfig;
        if (decodeConfig != null) {
            if (decodeConfig.isFullAreaScan()) {
                return analyze(bArr, i, i2, 0, 0, i, i2);
            }
            Rect analyzeAreaRect = this.mDecodeConfig.getAnalyzeAreaRect();
            if (analyzeAreaRect != null) {
                return analyze(bArr, i, i2, analyzeAreaRect.left, analyzeAreaRect.top, analyzeAreaRect.width(), analyzeAreaRect.height());
            }
        }
        int iMin = (int) (Math.min(i, i2) * this.mAreaRectRatio);
        return analyze(bArr, i, i2, ((i - iMin) / 2) + this.mAreaRectHorizontalOffset, ((i2 - iMin) / 2) + this.mAreaRectVerticalOffset, iMin, iMin);
    }
}
