package com.king.zxing.analyze;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.king.zxing.DecodeConfig;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class MultiFormatAnalyzer extends AreaRectAnalyzer {
    MultiFormatReader mReader;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MultiFormatAnalyzer() {
        this((DecodeConfig) null);
    }

    public MultiFormatAnalyzer(Map<DecodeHintType, Object> map) {
        this(new DecodeConfig().setHints(map));
    }

    public MultiFormatAnalyzer(DecodeConfig decodeConfig) {
        super(decodeConfig);
        initReader();
    }

    private void initReader() {
        this.mReader = new MultiFormatReader();
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0099 A[PHI: r16
      0x0099: PHI (r16v5 com.google.zxing.Result) = 
      (r16v1 com.google.zxing.Result)
      (r16v1 com.google.zxing.Result)
      (r16v3 com.google.zxing.Result)
      (r16v3 com.google.zxing.Result)
     binds: [B:4:0x0032, B:6:0x0036, B:16:0x007c, B:18:0x0084] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x009d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.king.zxing.analyze.AreaRectAnalyzer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.zxing.Result analyze(byte[] r18, int r19, int r20, int r21, int r22, int r23, int r24) {
        /*
            Method dump skipped, instruction units count: 205
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.king.zxing.analyze.MultiFormatAnalyzer.analyze(byte[], int, int, int, int, int, int):com.google.zxing.Result");
    }

    private Result decodeInternal(LuminanceSource luminanceSource, boolean z) {
        Result resultDecodeWithState;
        try {
            resultDecodeWithState = this.mReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(luminanceSource)));
        } catch (Exception unused) {
            resultDecodeWithState = null;
        }
        if (!z || resultDecodeWithState != null) {
            return resultDecodeWithState;
        }
        try {
            return this.mReader.decodeWithState(new BinaryBitmap(new GlobalHistogramBinarizer(luminanceSource)));
        } catch (Exception unused2) {
            return resultDecodeWithState;
        }
    }
}
