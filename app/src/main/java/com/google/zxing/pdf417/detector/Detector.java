package com.google.zxing.pdf417.detector;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public final class Detector {
    private static final int BARCODE_MIN_HEIGHT = 10;
    private static final float MAX_AVG_VARIANCE = 0.42f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.8f;
    private static final int MAX_PATTERN_DRIFT = 5;
    private static final int MAX_PIXEL_DRIFT = 3;
    private static final float MAX_STOP_PATTERN_HEIGHT_VARIANCE = 0.5f;
    private static final int ROW_STEP = 5;
    private static final int SKIPPED_ROW_COUNT_MAX = 25;
    private static final int[] INDEXES_START_PATTERN = {0, 4, 1, 5};
    private static final int[] INDEXES_STOP_PATTERN = {6, 2, 7, 3};
    private static final int[] START_PATTERN = {8, 1, 1, 1, 1, 1, 1, 3};
    private static final int[] STOP_PATTERN = {7, 1, 1, 3, 1, 1, 1, 2, 1};
    private static final int[] ROTATIONS = {0, 180, 270, 90};

    private Detector() {
    }

    public static PDF417DetectorResult detect(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map, boolean z) throws NotFoundException {
        BitMatrix blackMatrix = binaryBitmap.getBlackMatrix();
        for (int i : ROTATIONS) {
            BitMatrix bitMatrixApplyRotation = applyRotation(blackMatrix, i);
            List<ResultPoint[]> listDetect = detect(z, bitMatrixApplyRotation);
            if (!listDetect.isEmpty()) {
                return new PDF417DetectorResult(bitMatrixApplyRotation, listDetect, i);
            }
        }
        return new PDF417DetectorResult(blackMatrix, new ArrayList(), 0);
    }

    private static BitMatrix applyRotation(BitMatrix bitMatrix, int i) {
        if (i % 360 == 0) {
            return bitMatrix;
        }
        BitMatrix bitMatrixM570clone = bitMatrix.m570clone();
        bitMatrixM570clone.rotate(i);
        return bitMatrixM570clone;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001d, code lost:
    
        if (r4 != 0) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0020, code lost:
    
        r3 = r0.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0028, code lost:
    
        if (r3.hasNext() == false) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x002a, code lost:
    
        r4 = (com.google.zxing.ResultPoint[]) r3.next();
        r7 = r4[1];
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0032, code lost:
    
        if (r7 == null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0034, code lost:
    
        r2 = (int) java.lang.Math.max(r2, r7.getY());
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x003e, code lost:
    
        r4 = r4[3];
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0040, code lost:
    
        if (r4 == null) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0042, code lost:
    
        r2 = java.lang.Math.max(r2, (int) r4.getY());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.util.List<com.google.zxing.ResultPoint[]> detect(boolean r8, com.google.zxing.common.BitMatrix r9) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            r2 = r1
            r3 = r2
        L8:
            r4 = r3
        L9:
            int r5 = r9.getHeight()
            if (r2 >= r5) goto L79
            com.google.zxing.ResultPoint[] r3 = findVertices(r9, r2, r3)
            r5 = r3[r1]
            r6 = 1
            if (r5 != 0) goto L50
            r5 = 3
            r7 = r3[r5]
            if (r7 != 0) goto L50
            if (r4 != 0) goto L20
            goto L79
        L20:
            java.util.Iterator r3 = r0.iterator()
        L24:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L4c
            java.lang.Object r4 = r3.next()
            com.google.zxing.ResultPoint[] r4 = (com.google.zxing.ResultPoint[]) r4
            r7 = r4[r6]
            if (r7 == 0) goto L3e
            float r2 = (float) r2
            float r7 = r7.getY()
            float r2 = java.lang.Math.max(r2, r7)
            int r2 = (int) r2
        L3e:
            r4 = r4[r5]
            if (r4 == 0) goto L24
            float r4 = r4.getY()
            int r4 = (int) r4
            int r2 = java.lang.Math.max(r2, r4)
            goto L24
        L4c:
            int r2 = r2 + 5
            r3 = r1
            goto L8
        L50:
            r0.add(r3)
            if (r8 != 0) goto L56
            goto L79
        L56:
            r2 = 2
            r4 = r3[r2]
            if (r4 == 0) goto L67
            float r4 = r4.getX()
            int r4 = (int) r4
            r2 = r3[r2]
            float r2 = r2.getY()
            goto L75
        L67:
            r2 = 4
            r4 = r3[r2]
            float r4 = r4.getX()
            int r4 = (int) r4
            r2 = r3[r2]
            float r2 = r2.getY()
        L75:
            int r2 = (int) r2
            r3 = r4
            r4 = r6
            goto L9
        L79:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.detector.Detector.detect(boolean, com.google.zxing.common.BitMatrix):java.util.List");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x004a A[PHI: r12 r13
      0x004a: PHI (r12v1 int) = (r12v0 int), (r12v6 int) binds: [B:3:0x0022, B:5:0x0033] A[DONT_GENERATE, DONT_INLINE]
      0x004a: PHI (r13v1 int) = (r13v0 int), (r13v2 int) binds: [B:3:0x0022, B:5:0x0033] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.google.zxing.ResultPoint[] findVertices(com.google.zxing.common.BitMatrix r11, int r12, int r13) {
        /*
            int r7 = r11.getHeight()
            int r8 = r11.getWidth()
            r0 = 8
            com.google.zxing.ResultPoint[] r9 = new com.google.zxing.ResultPoint[r0]
            r10 = 10
            int[] r6 = com.google.zxing.pdf417.detector.Detector.START_PATTERN
            r0 = r11
            r1 = r7
            r2 = r8
            r3 = r12
            r4 = r13
            r5 = r10
            com.google.zxing.ResultPoint[] r0 = findRowsWithPattern(r0, r1, r2, r3, r4, r5, r6)
            int[] r1 = com.google.zxing.pdf417.detector.Detector.INDEXES_START_PATTERN
            copyToResult(r9, r0, r1)
            r0 = 4
            r1 = r9[r0]
            if (r1 == 0) goto L4a
            float r12 = r1.getX()
            int r13 = (int) r12
            r12 = r9[r0]
            float r12 = r12.getY()
            int r12 = (int) r12
            r0 = 5
            r0 = r9[r0]
            if (r0 == 0) goto L4a
            float r0 = r0.getY()
            int r0 = (int) r0
            int r0 = r0 - r12
            float r0 = (float) r0
            r1 = 1056964608(0x3f000000, float:0.5)
            float r0 = r0 * r1
            r1 = 1092616192(0x41200000, float:10.0)
            float r0 = java.lang.Math.max(r0, r1)
            int r0 = (int) r0
            r3 = r12
            r4 = r13
            r5 = r0
            goto L4d
        L4a:
            r3 = r12
            r4 = r13
            r5 = r10
        L4d:
            int[] r6 = com.google.zxing.pdf417.detector.Detector.STOP_PATTERN
            r0 = r11
            r1 = r7
            r2 = r8
            com.google.zxing.ResultPoint[] r11 = findRowsWithPattern(r0, r1, r2, r3, r4, r5, r6)
            int[] r12 = com.google.zxing.pdf417.detector.Detector.INDEXES_STOP_PATTERN
            copyToResult(r9, r11, r12)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.detector.Detector.findVertices(com.google.zxing.common.BitMatrix, int, int):com.google.zxing.ResultPoint[]");
    }

    private static void copyToResult(ResultPoint[] resultPointArr, ResultPoint[] resultPointArr2, int[] iArr) {
        for (int i = 0; i < iArr.length; i++) {
            resultPointArr[iArr[i]] = resultPointArr2[i];
        }
    }

    private static ResultPoint[] findRowsWithPattern(BitMatrix bitMatrix, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        boolean z;
        int i6;
        int i7;
        ResultPoint[] resultPointArr = new ResultPoint[4];
        int[] iArr2 = new int[iArr.length];
        int i8 = i3;
        while (true) {
            if (i8 >= i) {
                z = false;
                break;
            }
            int[] iArrFindGuardPattern = findGuardPattern(bitMatrix, i4, i8, i2, iArr, iArr2);
            if (iArrFindGuardPattern != null) {
                int i9 = i8;
                int[] iArr3 = iArrFindGuardPattern;
                int i10 = i9;
                while (true) {
                    if (i10 <= 0) {
                        i7 = i10;
                        break;
                    }
                    int i11 = i10 - 1;
                    int[] iArrFindGuardPattern2 = findGuardPattern(bitMatrix, i4, i11, i2, iArr, iArr2);
                    if (iArrFindGuardPattern2 == null) {
                        i7 = i11 + 1;
                        break;
                    }
                    iArr3 = iArrFindGuardPattern2;
                    i10 = i11;
                }
                float f = i7;
                resultPointArr[0] = new ResultPoint(iArr3[0], f);
                resultPointArr[1] = new ResultPoint(iArr3[1], f);
                z = true;
                i8 = i7;
            } else {
                i8 += 5;
            }
        }
        int i12 = i8 + 1;
        if (z) {
            int[] iArr4 = {(int) resultPointArr[0].getX(), (int) resultPointArr[1].getX()};
            int i13 = i12;
            int i14 = 0;
            while (true) {
                if (i13 >= i) {
                    i6 = i14;
                    break;
                }
                i6 = i14;
                int[] iArrFindGuardPattern3 = findGuardPattern(bitMatrix, iArr4[0], i13, i2, iArr, iArr2);
                if (iArrFindGuardPattern3 != null && Math.abs(iArr4[0] - iArrFindGuardPattern3[0]) < 5 && Math.abs(iArr4[1] - iArrFindGuardPattern3[1]) < 5) {
                    iArr4 = iArrFindGuardPattern3;
                    i14 = 0;
                } else {
                    if (i6 > 25) {
                        break;
                    }
                    i14 = i6 + 1;
                }
                i13++;
            }
            i12 = i13 - (i6 + 1);
            float f2 = i12;
            resultPointArr[2] = new ResultPoint(iArr4[0], f2);
            resultPointArr[3] = new ResultPoint(iArr4[1], f2);
        }
        if (i12 - i8 < i5) {
            Arrays.fill(resultPointArr, (Object) null);
        }
        return resultPointArr;
    }

    private static int[] findGuardPattern(BitMatrix bitMatrix, int i, int i2, int i3, int[] iArr, int[] iArr2) {
        Arrays.fill(iArr2, 0, iArr2.length, 0);
        int i4 = 0;
        while (bitMatrix.get(i, i2) && i > 0) {
            int i5 = i4 + 1;
            if (i4 >= 3) {
                break;
            }
            i--;
            i4 = i5;
        }
        int length = iArr.length;
        int i6 = i;
        int i7 = 0;
        boolean z = false;
        while (i < i3) {
            if (bitMatrix.get(i, i2) != z) {
                iArr2[i7] = iArr2[i7] + 1;
            } else {
                if (i7 != length - 1) {
                    i7++;
                } else {
                    if (patternMatchVariance(iArr2, iArr) < MAX_AVG_VARIANCE) {
                        return new int[]{i6, i};
                    }
                    i6 += iArr2[0] + iArr2[1];
                    int i8 = i7 - 1;
                    System.arraycopy(iArr2, 2, iArr2, 0, i8);
                    iArr2[i8] = 0;
                    iArr2[i7] = 0;
                    i7--;
                }
                iArr2[i7] = 1;
                z = !z;
            }
            i++;
        }
        if (i7 != length - 1 || patternMatchVariance(iArr2, iArr) >= MAX_AVG_VARIANCE) {
            return null;
        }
        return new int[]{i6, i - 1};
    }

    private static float patternMatchVariance(int[] iArr, int[] iArr2) {
        int length = iArr.length;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            i += iArr[i3];
            i2 += iArr2[i3];
        }
        if (i < i2) {
            return Float.POSITIVE_INFINITY;
        }
        float f = i;
        float f2 = f / i2;
        float f3 = 0.8f * f2;
        float f4 = 0.0f;
        for (int i4 = 0; i4 < length; i4++) {
            float f5 = iArr2[i4] * f2;
            float f6 = iArr[i4];
            float f7 = f6 > f5 ? f6 - f5 : f5 - f6;
            if (f7 > f3) {
                return Float.POSITIVE_INFINITY;
            }
            f4 += f7;
        }
        return f4 / f;
    }
}
