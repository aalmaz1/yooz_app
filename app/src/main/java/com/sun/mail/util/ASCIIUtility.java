package com.sun.mail.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import kotlin.UByte;

/* JADX INFO: loaded from: classes2.dex */
public class ASCIIUtility {
    private ASCIIUtility() {
    }

    public static int parseInt(byte[] bArr, int i, int i2, int i3) throws NumberFormatException {
        int i4;
        int i5;
        boolean z;
        if (bArr == null) {
            throw new NumberFormatException("null");
        }
        if (i2 > i) {
            int i6 = 0;
            if (bArr[i] == 45) {
                i5 = i + 1;
                i4 = Integer.MIN_VALUE;
                z = true;
            } else {
                i4 = -2147483647;
                i5 = i;
                z = false;
            }
            int i7 = i4 / i3;
            if (i5 < i2) {
                int i8 = i5 + 1;
                int iDigit = Character.digit((char) bArr[i5], i3);
                if (iDigit < 0) {
                    throw new NumberFormatException("illegal number: " + toString(bArr, i, i2));
                }
                i6 = -iDigit;
                i5 = i8;
            }
            while (i5 < i2) {
                int i9 = i5 + 1;
                int iDigit2 = Character.digit((char) bArr[i5], i3);
                if (iDigit2 < 0) {
                    throw new NumberFormatException("illegal number");
                }
                if (i6 < i7) {
                    throw new NumberFormatException("illegal number");
                }
                int i10 = i6 * i3;
                if (i10 < i4 + iDigit2) {
                    throw new NumberFormatException("illegal number");
                }
                i6 = i10 - iDigit2;
                i5 = i9;
            }
            if (!z) {
                return -i6;
            }
            if (i5 > i + 1) {
                return i6;
            }
            throw new NumberFormatException("illegal number");
        }
        throw new NumberFormatException("illegal number");
    }

    public static int parseInt(byte[] bArr, int i, int i2) throws NumberFormatException {
        return parseInt(bArr, i, i2, 10);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0081  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x0069 -> B:15:0x0033). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static long parseLong(byte[] r18, int r19, int r20, int r21) throws java.lang.NumberFormatException {
        /*
            r0 = r19
            r1 = r20
            r2 = r21
            if (r18 == 0) goto L95
            java.lang.String r3 = "illegal number"
            if (r1 <= r0) goto L8f
            r4 = r18[r0]
            r5 = 45
            r6 = 1
            if (r4 != r5) goto L19
            int r4 = r0 + 1
            r7 = -9223372036854775808
            r5 = r6
            goto L21
        L19:
            r4 = 0
            r7 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r5 = r4
            r4 = r0
        L21:
            long r9 = (long) r2
            long r11 = r7 / r9
            if (r4 >= r1) goto L4e
            int r13 = r4 + 1
            r4 = r18[r4]
            char r4 = (char) r4
            int r4 = java.lang.Character.digit(r4, r2)
            if (r4 < 0) goto L35
            int r4 = -r4
            long r14 = (long) r4
        L33:
            r4 = r13
            goto L50
        L35:
            java.lang.NumberFormatException r2 = new java.lang.NumberFormatException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "illegal number: "
            r3.<init>(r4)
            java.lang.String r0 = toString(r18, r19, r20)
            java.lang.StringBuilder r0 = r3.append(r0)
            java.lang.String r0 = r0.toString()
            r2.<init>(r0)
            throw r2
        L4e:
            r14 = 0
        L50:
            if (r4 >= r1) goto L81
            int r13 = r4 + 1
            r4 = r18[r4]
            char r4 = (char) r4
            int r4 = java.lang.Character.digit(r4, r2)
            if (r4 < 0) goto L7b
            int r16 = (r14 > r11 ? 1 : (r14 == r11 ? 0 : -1))
            if (r16 < 0) goto L75
            long r14 = r14 * r9
            long r1 = (long) r4
            long r16 = r7 + r1
            int r4 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r4 < 0) goto L6f
            long r14 = r14 - r1
            r1 = r20
            r2 = r21
            goto L33
        L6f:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            r0.<init>(r3)
            throw r0
        L75:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            r0.<init>(r3)
            throw r0
        L7b:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            r0.<init>(r3)
            throw r0
        L81:
            if (r5 == 0) goto L8d
            int r0 = r0 + r6
            if (r4 <= r0) goto L87
            return r14
        L87:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            r0.<init>(r3)
            throw r0
        L8d:
            long r0 = -r14
            return r0
        L8f:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            r0.<init>(r3)
            throw r0
        L95:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.String r1 = "null"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.mail.util.ASCIIUtility.parseLong(byte[], int, int, int):long");
    }

    public static long parseLong(byte[] bArr, int i, int i2) throws NumberFormatException {
        return parseLong(bArr, i, i2, 10);
    }

    public static String toString(byte[] bArr, int i, int i2) {
        int i3 = i2 - i;
        char[] cArr = new char[i3];
        int i4 = 0;
        while (i4 < i3) {
            cArr[i4] = (char) (bArr[i] & UByte.MAX_VALUE);
            i4++;
            i++;
        }
        return new String(cArr);
    }

    public static String toString(byte[] bArr) {
        return toString(bArr, 0, bArr.length);
    }

    public static String toString(ByteArrayInputStream byteArrayInputStream) {
        int iAvailable = byteArrayInputStream.available();
        char[] cArr = new char[iAvailable];
        byte[] bArr = new byte[iAvailable];
        byteArrayInputStream.read(bArr, 0, iAvailable);
        for (int i = 0; i < iAvailable; i++) {
            cArr[i] = (char) (bArr[i] & UByte.MAX_VALUE);
        }
        return new String(cArr);
    }

    public static byte[] getBytes(String str) {
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr[i] = (byte) charArray[i];
        }
        return bArr;
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        if (inputStream instanceof ByteArrayInputStream) {
            int iAvailable = inputStream.available();
            byte[] bArr = new byte[iAvailable];
            inputStream.read(bArr, 0, iAvailable);
            return bArr;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr2 = new byte[1024];
        while (true) {
            int i = inputStream.read(bArr2, 0, 1024);
            if (i != -1) {
                byteArrayOutputStream.write(bArr2, 0, i);
            } else {
                return byteArrayOutputStream.toByteArray();
            }
        }
    }
}
