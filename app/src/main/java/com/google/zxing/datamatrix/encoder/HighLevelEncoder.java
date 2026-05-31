package com.google.zxing.datamatrix.encoder;

import cn.baos.watch.sdk.entitiy.Constant;
import com.google.zxing.Dimension;
import java.util.Arrays;

/* JADX INFO: loaded from: classes2.dex */
public final class HighLevelEncoder {
    static final int ASCII_ENCODATION = 0;
    static final int BASE256_ENCODATION = 5;
    static final int C40_ENCODATION = 1;
    static final char C40_UNLATCH = 254;
    static final int EDIFACT_ENCODATION = 4;
    static final char LATCH_TO_ANSIX12 = 238;
    static final char LATCH_TO_BASE256 = 231;
    static final char LATCH_TO_C40 = 230;
    static final char LATCH_TO_EDIFACT = 240;
    static final char LATCH_TO_TEXT = 239;
    private static final char MACRO_05 = 236;
    static final String MACRO_05_HEADER = "[)>\u001e05\u001d";
    private static final char MACRO_06 = 237;
    static final String MACRO_06_HEADER = "[)>\u001e06\u001d";
    static final String MACRO_TRAILER = "\u001e\u0004";
    private static final char PAD = 129;
    static final int TEXT_ENCODATION = 2;
    static final char UPPER_SHIFT = 235;
    static final int X12_ENCODATION = 3;
    static final char X12_UNLATCH = 254;

    static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    static boolean isExtendedASCII(char c) {
        return c >= 128 && c <= 255;
    }

    static boolean isNativeC40(char c) {
        return c == ' ' || (c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z');
    }

    static boolean isNativeEDIFACT(char c) {
        return c >= ' ' && c <= '^';
    }

    static boolean isNativeText(char c) {
        return c == ' ' || (c >= '0' && c <= '9') || (c >= 'a' && c <= 'z');
    }

    private static boolean isSpecialB256(char c) {
        return false;
    }

    private static boolean isX12TermSep(char c) {
        return c == '\r' || c == '*' || c == '>';
    }

    private HighLevelEncoder() {
    }

    private static char randomize253State(int i) {
        int i2 = ((i * 149) % Constant.MESSAGE_ID_OTA_UPLOAD_UPGRADE_PACKAGE) + 1 + 129;
        if (i2 > 254) {
            i2 -= 254;
        }
        return (char) i2;
    }

    public static String encodeHighLevel(String str) {
        return encodeHighLevel(str, SymbolShapeHint.FORCE_NONE, null, null, false);
    }

    public static String encodeHighLevel(String str, SymbolShapeHint symbolShapeHint, Dimension dimension, Dimension dimension2) {
        return encodeHighLevel(str, symbolShapeHint, dimension, dimension2, false);
    }

    public static String encodeHighLevel(String str, SymbolShapeHint symbolShapeHint, Dimension dimension, Dimension dimension2, boolean z) {
        C40Encoder c40Encoder = new C40Encoder();
        int newEncoding = 0;
        Encoder[] encoderArr = {new ASCIIEncoder(), c40Encoder, new TextEncoder(), new X12Encoder(), new EdifactEncoder(), new Base256Encoder()};
        EncoderContext encoderContext = new EncoderContext(str);
        encoderContext.setSymbolShape(symbolShapeHint);
        encoderContext.setSizeConstraints(dimension, dimension2);
        if (str.startsWith(MACRO_05_HEADER) && str.endsWith(MACRO_TRAILER)) {
            encoderContext.writeCodeword(MACRO_05);
            encoderContext.setSkipAtEnd(2);
            encoderContext.pos += 7;
        } else if (str.startsWith(MACRO_06_HEADER) && str.endsWith(MACRO_TRAILER)) {
            encoderContext.writeCodeword(MACRO_06);
            encoderContext.setSkipAtEnd(2);
            encoderContext.pos += 7;
        }
        if (z) {
            c40Encoder.encodeMaximal(encoderContext);
            newEncoding = encoderContext.getNewEncoding();
            encoderContext.resetEncoderSignal();
        }
        while (encoderContext.hasMoreCharacters()) {
            encoderArr[newEncoding].encode(encoderContext);
            if (encoderContext.getNewEncoding() >= 0) {
                newEncoding = encoderContext.getNewEncoding();
                encoderContext.resetEncoderSignal();
            }
        }
        int codewordCount = encoderContext.getCodewordCount();
        encoderContext.updateSymbolInfo();
        int dataCapacity = encoderContext.getSymbolInfo().getDataCapacity();
        if (codewordCount < dataCapacity && newEncoding != 0 && newEncoding != 5 && newEncoding != 4) {
            encoderContext.writeCodeword((char) 254);
        }
        StringBuilder codewords = encoderContext.getCodewords();
        if (codewords.length() < dataCapacity) {
            codewords.append(PAD);
        }
        while (codewords.length() < dataCapacity) {
            codewords.append(randomize253State(codewords.length() + 1));
        }
        return encoderContext.getCodewords().toString();
    }

    static int lookAheadTest(CharSequence charSequence, int i, int i2) {
        int iLookAheadTestIntern = lookAheadTestIntern(charSequence, i, i2);
        if (i2 == 3 && iLookAheadTestIntern == 3) {
            int iMin = Math.min(i + 3, charSequence.length());
            while (i < iMin) {
                if (!isNativeX12(charSequence.charAt(i))) {
                    return 0;
                }
                i++;
            }
        } else if (i2 == 4 && iLookAheadTestIntern == 4) {
            int iMin2 = Math.min(i + 4, charSequence.length());
            while (i < iMin2) {
                if (!isNativeEDIFACT(charSequence.charAt(i))) {
                    return 0;
                }
                i++;
            }
        }
        return iLookAheadTestIntern;
    }

    static int lookAheadTestIntern(CharSequence charSequence, int i, int i2) {
        float[] fArr;
        char c;
        if (i >= charSequence.length()) {
            return i2;
        }
        if (i2 == 0) {
            fArr = new float[]{0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.25f};
        } else {
            fArr = new float[]{1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.25f};
            fArr[i2] = 0.0f;
        }
        byte[] bArr = new byte[6];
        int[] iArr = new int[6];
        int i3 = 0;
        while (true) {
            int i4 = i + i3;
            if (i4 == charSequence.length()) {
                Arrays.fill(bArr, (byte) 0);
                Arrays.fill(iArr, 0);
                int iFindMinimums = findMinimums(fArr, iArr, Integer.MAX_VALUE, bArr);
                int minimumCount = getMinimumCount(bArr);
                if (iArr[0] == iFindMinimums) {
                    return 0;
                }
                if (minimumCount == 1) {
                    if (bArr[5] > 0) {
                        return 5;
                    }
                    if (bArr[4] > 0) {
                        return 4;
                    }
                    if (bArr[2] > 0) {
                        return 2;
                    }
                    if (bArr[3] > 0) {
                        return 3;
                    }
                }
                return 1;
            }
            char cCharAt = charSequence.charAt(i4);
            i3++;
            if (isDigit(cCharAt)) {
                fArr[0] = fArr[0] + 0.5f;
            } else if (isExtendedASCII(cCharAt)) {
                float fCeil = (float) Math.ceil(fArr[0]);
                fArr[0] = fCeil;
                fArr[0] = fCeil + 2.0f;
            } else {
                float fCeil2 = (float) Math.ceil(fArr[0]);
                fArr[0] = fCeil2;
                fArr[0] = fCeil2 + 1.0f;
            }
            if (isNativeC40(cCharAt)) {
                fArr[1] = fArr[1] + 0.6666667f;
            } else if (isExtendedASCII(cCharAt)) {
                fArr[1] = fArr[1] + 2.6666667f;
            } else {
                fArr[1] = fArr[1] + 1.3333334f;
            }
            if (isNativeText(cCharAt)) {
                fArr[2] = fArr[2] + 0.6666667f;
            } else if (isExtendedASCII(cCharAt)) {
                fArr[2] = fArr[2] + 2.6666667f;
            } else {
                fArr[2] = fArr[2] + 1.3333334f;
            }
            if (isNativeX12(cCharAt)) {
                fArr[3] = fArr[3] + 0.6666667f;
            } else if (isExtendedASCII(cCharAt)) {
                fArr[3] = fArr[3] + 4.3333335f;
            } else {
                fArr[3] = fArr[3] + 3.3333333f;
            }
            if (isNativeEDIFACT(cCharAt)) {
                fArr[4] = fArr[4] + 0.75f;
            } else if (isExtendedASCII(cCharAt)) {
                fArr[4] = fArr[4] + 4.25f;
            } else {
                fArr[4] = fArr[4] + 3.25f;
            }
            if (isSpecialB256(cCharAt)) {
                c = 5;
                fArr[5] = fArr[5] + 4.0f;
            } else {
                c = 5;
                fArr[5] = fArr[5] + 1.0f;
            }
            if (i3 >= 4) {
                Arrays.fill(bArr, (byte) 0);
                Arrays.fill(iArr, 0);
                findMinimums(fArr, iArr, Integer.MAX_VALUE, bArr);
                if (iArr[0] < min(iArr[c], iArr[1], iArr[2], iArr[3], iArr[4])) {
                    return 0;
                }
                int i5 = iArr[c];
                if (i5 < iArr[0] || i5 + 1 < min(iArr[1], iArr[2], iArr[3], iArr[4])) {
                    return 5;
                }
                if (iArr[4] + 1 < min(iArr[5], iArr[1], iArr[2], iArr[3], iArr[0])) {
                    return 4;
                }
                if (iArr[2] + 1 < min(iArr[5], iArr[1], iArr[4], iArr[3], iArr[0])) {
                    return 2;
                }
                if (iArr[3] + 1 < min(iArr[5], iArr[1], iArr[4], iArr[2], iArr[0])) {
                    return 3;
                }
                if (iArr[1] + 1 >= min(iArr[0], iArr[5], iArr[4], iArr[2])) {
                    continue;
                } else {
                    int i6 = iArr[1];
                    int i7 = iArr[3];
                    if (i6 < i7) {
                        return 1;
                    }
                    if (i6 == i7) {
                        for (int i8 = i + i3 + 1; i8 < charSequence.length(); i8++) {
                            char cCharAt2 = charSequence.charAt(i8);
                            if (isX12TermSep(cCharAt2)) {
                                return 3;
                            }
                            if (!isNativeX12(cCharAt2)) {
                                break;
                            }
                        }
                        return 1;
                    }
                }
            }
        }
    }

    private static int min(int i, int i2, int i3, int i4, int i5) {
        return Math.min(min(i, i2, i3, i4), i5);
    }

    private static int min(int i, int i2, int i3, int i4) {
        return Math.min(i, Math.min(i2, Math.min(i3, i4)));
    }

    private static int findMinimums(float[] fArr, int[] iArr, int i, byte[] bArr) {
        for (int i2 = 0; i2 < 6; i2++) {
            int iCeil = (int) Math.ceil(fArr[i2]);
            iArr[i2] = iCeil;
            if (i > iCeil) {
                Arrays.fill(bArr, (byte) 0);
                i = iCeil;
            }
            if (i == iCeil) {
                bArr[i2] = (byte) (bArr[i2] + 1);
            }
        }
        return i;
    }

    private static int getMinimumCount(byte[] bArr) {
        int i = 0;
        for (int i2 = 0; i2 < 6; i2++) {
            i += bArr[i2];
        }
        return i;
    }

    static boolean isNativeX12(char c) {
        return isX12TermSep(c) || c == ' ' || (c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z');
    }

    public static int determineConsecutiveDigitCount(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = i;
        while (i2 < length && isDigit(charSequence.charAt(i2))) {
            i2++;
        }
        return i2 - i;
    }

    static void illegalCharacter(char c) {
        String hexString = Integer.toHexString(c);
        throw new IllegalArgumentException("Illegal character: " + c + " (0x" + ("0000".substring(0, 4 - hexString.length()) + hexString) + ')');
    }
}
