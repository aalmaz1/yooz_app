package com.google.zxing.multi.qrcode.detector;

import androidx.camera.video.AudioStats;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.detector.FinderPattern;
import com.google.zxing.qrcode.detector.FinderPatternFinder;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public final class MultiFinderPatternFinder extends FinderPatternFinder {
    private static final float DIFF_MODSIZE_CUTOFF = 0.5f;
    private static final float DIFF_MODSIZE_CUTOFF_PERCENT = 0.05f;
    private static final float MAX_MODULE_COUNT_PER_EDGE = 180.0f;
    private static final float MIN_MODULE_COUNT_PER_EDGE = 9.0f;
    private static final FinderPatternInfo[] EMPTY_RESULT_ARRAY = new FinderPatternInfo[0];
    private static final FinderPattern[] EMPTY_FP_ARRAY = new FinderPattern[0];
    private static final FinderPattern[][] EMPTY_FP_2D_ARRAY = new FinderPattern[0][];

    private static final class ModuleSizeComparator implements Comparator<FinderPattern>, Serializable {
        private ModuleSizeComparator() {
        }

        @Override // java.util.Comparator
        public int compare(FinderPattern finderPattern, FinderPattern finderPattern2) {
            double estimatedModuleSize = finderPattern2.getEstimatedModuleSize() - finderPattern.getEstimatedModuleSize();
            if (estimatedModuleSize < AudioStats.AUDIO_AMPLITUDE_NONE) {
                return -1;
            }
            return estimatedModuleSize > AudioStats.AUDIO_AMPLITUDE_NONE ? 1 : 0;
        }
    }

    public MultiFinderPatternFinder(BitMatrix bitMatrix, ResultPointCallback resultPointCallback) {
        super(bitMatrix, resultPointCallback);
    }

    private FinderPattern[][] selectMultipleBestPatterns() throws NotFoundException {
        char c;
        char c2;
        ArrayList arrayList;
        int i;
        ArrayList arrayList2;
        int i2;
        char c3;
        ArrayList arrayList3 = new ArrayList();
        Iterator<FinderPattern> it = getPossibleCenters().iterator();
        while (true) {
            c = 2;
            if (!it.hasNext()) {
                break;
            }
            FinderPattern next = it.next();
            if (next.getCount() >= 2) {
                arrayList3.add(next);
            }
        }
        int size = arrayList3.size();
        int i3 = 3;
        if (size < 3) {
            throw NotFoundException.getNotFoundInstance();
        }
        char c4 = 0;
        if (size == 3) {
            return new FinderPattern[][]{(FinderPattern[]) arrayList3.toArray(EMPTY_FP_ARRAY)};
        }
        Collections.sort(arrayList3, new ModuleSizeComparator());
        ArrayList arrayList4 = new ArrayList();
        int i4 = 0;
        while (i4 < size - 2) {
            FinderPattern finderPattern = (FinderPattern) arrayList3.get(i4);
            if (finderPattern != null) {
                int i5 = i4 + 1;
                while (i5 < size - 1) {
                    FinderPattern finderPattern2 = (FinderPattern) arrayList3.get(i5);
                    if (finderPattern2 != null) {
                        float estimatedModuleSize = (finderPattern.getEstimatedModuleSize() - finderPattern2.getEstimatedModuleSize()) / Math.min(finderPattern.getEstimatedModuleSize(), finderPattern2.getEstimatedModuleSize());
                        float fAbs = Math.abs(finderPattern.getEstimatedModuleSize() - finderPattern2.getEstimatedModuleSize());
                        float f = 0.5f;
                        float f2 = DIFF_MODSIZE_CUTOFF_PERCENT;
                        if (fAbs > 0.5f && estimatedModuleSize >= DIFF_MODSIZE_CUTOFF_PERCENT) {
                            break;
                        }
                        int i6 = i5 + 1;
                        while (i6 < size) {
                            FinderPattern finderPattern3 = (FinderPattern) arrayList3.get(i6);
                            if (finderPattern3 != null) {
                                float estimatedModuleSize2 = (finderPattern2.getEstimatedModuleSize() - finderPattern3.getEstimatedModuleSize()) / Math.min(finderPattern2.getEstimatedModuleSize(), finderPattern3.getEstimatedModuleSize());
                                if (Math.abs(finderPattern2.getEstimatedModuleSize() - finderPattern3.getEstimatedModuleSize()) > f && estimatedModuleSize2 >= f2) {
                                    arrayList2 = arrayList3;
                                    i2 = size;
                                    c3 = 2;
                                    break;
                                }
                                FinderPattern[] finderPatternArr = new FinderPattern[i3];
                                finderPatternArr[c4] = finderPattern;
                                finderPatternArr[1] = finderPattern2;
                                c2 = 2;
                                finderPatternArr[2] = finderPattern3;
                                ResultPoint.orderBestPatterns(finderPatternArr);
                                FinderPatternInfo finderPatternInfo = new FinderPatternInfo(finderPatternArr);
                                float fDistance = ResultPoint.distance(finderPatternInfo.getTopLeft(), finderPatternInfo.getBottomLeft());
                                float fDistance2 = ResultPoint.distance(finderPatternInfo.getTopRight(), finderPatternInfo.getBottomLeft());
                                float fDistance3 = ResultPoint.distance(finderPatternInfo.getTopLeft(), finderPatternInfo.getTopRight());
                                float estimatedModuleSize3 = (fDistance + fDistance3) / (finderPattern.getEstimatedModuleSize() * 2.0f);
                                if (estimatedModuleSize3 > MAX_MODULE_COUNT_PER_EDGE || estimatedModuleSize3 < MIN_MODULE_COUNT_PER_EDGE || Math.abs((fDistance - fDistance3) / Math.min(fDistance, fDistance3)) >= 0.1f) {
                                    arrayList = arrayList3;
                                    i = size;
                                } else {
                                    double d = fDistance;
                                    arrayList = arrayList3;
                                    i = size;
                                    double d2 = fDistance3;
                                    float fSqrt = (float) Math.sqrt((d * d) + (d2 * d2));
                                    if (Math.abs((fDistance2 - fSqrt) / Math.min(fDistance2, fSqrt)) < 0.1f) {
                                        arrayList4.add(finderPatternArr);
                                    }
                                }
                            } else {
                                arrayList = arrayList3;
                                i = size;
                                c2 = c;
                            }
                            i6++;
                            size = i;
                            c = c2;
                            arrayList3 = arrayList;
                            i3 = 3;
                            c4 = 0;
                            f = 0.5f;
                            f2 = DIFF_MODSIZE_CUTOFF_PERCENT;
                        }
                        arrayList2 = arrayList3;
                        i2 = size;
                        c3 = c;
                    } else {
                        arrayList2 = arrayList3;
                        i2 = size;
                        c3 = c;
                    }
                    i5++;
                    size = i2;
                    c = c3;
                    arrayList3 = arrayList2;
                    i3 = 3;
                    c4 = 0;
                }
            }
            i4++;
            size = size;
            c = c;
            arrayList3 = arrayList3;
            i3 = 3;
            c4 = 0;
        }
        if (!arrayList4.isEmpty()) {
            return (FinderPattern[][]) arrayList4.toArray(EMPTY_FP_2D_ARRAY);
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public FinderPatternInfo[] findMulti(Map<DecodeHintType, ?> map) throws NotFoundException {
        boolean z = map != null && map.containsKey(DecodeHintType.TRY_HARDER);
        BitMatrix image = getImage();
        int height = image.getHeight();
        int width = image.getWidth();
        int i = (height * 3) / 388;
        if (i < 3 || z) {
            i = 3;
        }
        int[] iArr = new int[5];
        for (int i2 = i - 1; i2 < height; i2 += i) {
            doClearCounts(iArr);
            int i3 = 0;
            for (int i4 = 0; i4 < width; i4++) {
                if (image.get(i4, i2)) {
                    if ((i3 & 1) == 1) {
                        i3++;
                    }
                    iArr[i3] = iArr[i3] + 1;
                } else if ((i3 & 1) != 0) {
                    iArr[i3] = iArr[i3] + 1;
                } else if (i3 == 4) {
                    if (foundPatternCross(iArr) && handlePossibleCenter(iArr, i2, i4)) {
                        doClearCounts(iArr);
                        i3 = 0;
                    } else {
                        doShiftCounts2(iArr);
                        i3 = 3;
                    }
                } else {
                    i3++;
                    iArr[i3] = iArr[i3] + 1;
                }
            }
            if (foundPatternCross(iArr)) {
                handlePossibleCenter(iArr, i2, width);
            }
        }
        FinderPattern[][] finderPatternArrSelectMultipleBestPatterns = selectMultipleBestPatterns();
        ArrayList arrayList = new ArrayList();
        for (FinderPattern[] finderPatternArr : finderPatternArrSelectMultipleBestPatterns) {
            ResultPoint.orderBestPatterns(finderPatternArr);
            arrayList.add(new FinderPatternInfo(finderPatternArr));
        }
        if (arrayList.isEmpty()) {
            return EMPTY_RESULT_ARRAY;
        }
        return (FinderPatternInfo[]) arrayList.toArray(EMPTY_RESULT_ARRAY);
    }
}
