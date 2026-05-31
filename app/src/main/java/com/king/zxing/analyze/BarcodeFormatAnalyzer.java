package com.king.zxing.analyze;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.king.zxing.DecodeConfig;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public abstract class BarcodeFormatAnalyzer extends AreaRectAnalyzer {
    private Reader mReader;

    public abstract Reader createReader();

    public BarcodeFormatAnalyzer(Map<DecodeHintType, Object> map) {
        this(new DecodeConfig().setHints(map));
    }

    public BarcodeFormatAnalyzer(DecodeConfig decodeConfig) {
        super(decodeConfig);
        initReader();
    }

    private void initReader() {
        this.mReader = createReader();
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0092 A[PHI: r16
      0x0092: PHI (r16v5 com.google.zxing.Result) = (r16v1 com.google.zxing.Result), (r16v1 com.google.zxing.Result), (r16v3 com.google.zxing.Result) binds: [B:5:0x002f, B:7:0x0033, B:18:0x007d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0096 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.king.zxing.analyze.AreaRectAnalyzer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.zxing.Result analyze(byte[] r18, int r19, int r20, int r21, int r22, int r23, int r24) {
        /*
            r17 = this;
            r1 = r17
            r0 = r18
            r11 = r19
            r12 = r20
            com.google.zxing.Reader r2 = r1.mReader
            if (r2 == 0) goto Lc6
            long r14 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Lb8 java.lang.Exception -> Lbf
            com.google.zxing.PlanarYUVLuminanceSource r10 = new com.google.zxing.PlanarYUVLuminanceSource     // Catch: java.lang.Throwable -> Lb8 java.lang.Exception -> Lbf
            r16 = 0
            r2 = r10
            r3 = r18
            r4 = r19
            r5 = r20
            r6 = r21
            r7 = r22
            r8 = r23
            r9 = r24
            r13 = r10
            r10 = r16
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> Lb8 java.lang.Exception -> Lbf
            boolean r2 = r1.isMultiDecode     // Catch: java.lang.Throwable -> Lb8 java.lang.Exception -> Lbf
            com.google.zxing.Result r16 = r1.decodeInternal(r13, r2)     // Catch: java.lang.Throwable -> Lb8 java.lang.Exception -> Lbf
            if (r16 != 0) goto L92
            com.king.zxing.DecodeConfig r2 = r1.mDecodeConfig     // Catch: java.lang.Exception -> L8f java.lang.Throwable -> Lb8
            if (r2 == 0) goto L92
            com.king.zxing.DecodeConfig r2 = r1.mDecodeConfig     // Catch: java.lang.Exception -> L8f java.lang.Throwable -> Lb8
            boolean r2 = r2.isSupportVerticalCode()     // Catch: java.lang.Exception -> L8f java.lang.Throwable -> Lb8
            if (r2 == 0) goto L77
            int r2 = r0.length     // Catch: java.lang.Exception -> L8f java.lang.Throwable -> Lb8
            byte[] r3 = new byte[r2]     // Catch: java.lang.Exception -> L8f java.lang.Throwable -> Lb8
            r2 = 0
            r4 = r2
        L42:
            if (r4 >= r12) goto L5a
            r5 = r2
        L45:
            if (r5 >= r11) goto L57
            int r6 = r5 * r12
            int r6 = r6 + r12
            int r6 = r6 - r4
            int r6 = r6 + (-1)
            int r7 = r4 * r11
            int r7 = r7 + r5
            r7 = r0[r7]     // Catch: java.lang.Exception -> L8f java.lang.Throwable -> Lb8
            r3[r6] = r7     // Catch: java.lang.Exception -> L8f java.lang.Throwable -> Lb8
            int r5 = r5 + 1
            goto L45
        L57:
            int r4 = r4 + 1
            goto L42
        L5a:
            com.google.zxing.PlanarYUVLuminanceSource r0 = new com.google.zxing.PlanarYUVLuminanceSource     // Catch: java.lang.Exception -> L8f java.lang.Throwable -> Lb8
            r10 = 0
            r2 = r0
            r4 = r20
            r5 = r19
            r6 = r22
            r7 = r21
            r8 = r24
            r9 = r23
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch: java.lang.Exception -> L8f java.lang.Throwable -> Lb8
            com.king.zxing.DecodeConfig r2 = r1.mDecodeConfig     // Catch: java.lang.Exception -> L8f java.lang.Throwable -> Lb8
            boolean r2 = r2.isSupportVerticalCodeMultiDecode()     // Catch: java.lang.Exception -> L8f java.lang.Throwable -> Lb8
            com.google.zxing.Result r16 = r1.decodeInternal(r0, r2)     // Catch: java.lang.Exception -> L8f java.lang.Throwable -> Lb8
        L77:
            com.king.zxing.DecodeConfig r0 = r1.mDecodeConfig     // Catch: java.lang.Exception -> L8f java.lang.Throwable -> Lb8
            boolean r0 = r0.isSupportLuminanceInvert()     // Catch: java.lang.Exception -> L8f java.lang.Throwable -> Lb8
            if (r0 == 0) goto L92
            com.google.zxing.LuminanceSource r0 = r13.invert()     // Catch: java.lang.Exception -> L8f java.lang.Throwable -> Lb8
            com.king.zxing.DecodeConfig r2 = r1.mDecodeConfig     // Catch: java.lang.Exception -> L8f java.lang.Throwable -> Lb8
            boolean r2 = r2.isSupportLuminanceInvertMultiDecode()     // Catch: java.lang.Exception -> L8f java.lang.Throwable -> Lb8
            com.google.zxing.Result r0 = r1.decodeInternal(r0, r2)     // Catch: java.lang.Exception -> L8f java.lang.Throwable -> Lb8
            r13 = r0
            goto L94
        L8f:
            r13 = r16
            goto Lc0
        L92:
            r13 = r16
        L94:
            if (r13 == 0) goto Lc0
            long r2 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Lb8 java.lang.Exception -> Lc0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lb8 java.lang.Exception -> Lc0
            r0.<init>()     // Catch: java.lang.Throwable -> Lb8 java.lang.Exception -> Lc0
            java.lang.String r4 = "Found barcode in "
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch: java.lang.Throwable -> Lb8 java.lang.Exception -> Lc0
            long r2 = r2 - r14
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch: java.lang.Throwable -> Lb8 java.lang.Exception -> Lc0
            java.lang.String r2 = " ms"
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch: java.lang.Throwable -> Lb8 java.lang.Exception -> Lc0
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> Lb8 java.lang.Exception -> Lc0
            com.king.camera.scan.util.LogUtils.d(r0)     // Catch: java.lang.Throwable -> Lb8 java.lang.Exception -> Lc0
            goto Lc0
        Lb8:
            r0 = move-exception
            com.google.zxing.Reader r2 = r1.mReader
            r2.reset()
            throw r0
        Lbf:
            r13 = 0
        Lc0:
            com.google.zxing.Reader r0 = r1.mReader
            r0.reset()
            goto Lc7
        Lc6:
            r13 = 0
        Lc7:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.king.zxing.analyze.BarcodeFormatAnalyzer.analyze(byte[], int, int, int, int, int, int):com.google.zxing.Result");
    }

    private Result decodeInternal(LuminanceSource luminanceSource, boolean z) {
        Result resultDecode;
        try {
            resultDecode = this.mReader.decode(new BinaryBitmap(new HybridBinarizer(luminanceSource)), this.mHints);
        } catch (Exception unused) {
            resultDecode = null;
        }
        if (!z || resultDecode != null) {
            return resultDecode;
        }
        try {
            return this.mReader.decode(new BinaryBitmap(new GlobalHistogramBinarizer(luminanceSource)), this.mHints);
        } catch (Exception unused2) {
            return resultDecode;
        }
    }
}
