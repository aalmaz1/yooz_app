package cn.baos.watch.sdk.utils;

import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public class Murmur2 {
    private static final int DEFAULT_SEED = 0;
    private static final int M_32 = 1540483477;
    private static final long M_64 = -4132994306676758123L;
    private static final int R_32 = 24;
    private static final int R_64 = 47;

    public static int hash32(byte[] bArr) {
        return hash32(bArr, bArr.length, 0);
    }

    public static int hash32(byte[] bArr, int i, int i2) {
        int i3 = i2 ^ i;
        int i4 = i >> 2;
        for (int i5 = 0; i5 < i4; i5++) {
            int i6 = i5 << 2;
            int i7 = (((bArr[i6 + 3] & 255) << 24) | (bArr[i6] & 255) | ((bArr[i6 + 1] & 255) << 8) | ((bArr[i6 + 2] & 255) << 16)) * M_32;
            i3 = (i3 * M_32) ^ ((i7 ^ (i7 >>> 24)) * M_32);
        }
        int i8 = i - (i4 << 2);
        if (i8 != 0) {
            if (i8 >= 3) {
                i3 ^= bArr[i - 3] << 16;
            }
            if (i8 >= 2) {
                i3 ^= bArr[i - 2] << 8;
            }
            if (i8 >= 1) {
                i3 ^= bArr[i - 1];
            }
            i3 *= M_32;
        }
        int i9 = ((i3 >>> 13) ^ i3) * M_32;
        return i9 ^ (i9 >>> 15);
    }

    public static long hash64(byte[] bArr) {
        return hash64(bArr, bArr.length, 0);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static long hash64(byte[] bArr, int i, int i2) {
        long j;
        long j2 = (((long) i2) & 4294967295L) ^ (((long) i) * M_64);
        int i3 = i >> 3;
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = i4 << 3;
            long j3 = ((((long) bArr[i5]) & 255) | ((((long) bArr[i5 + 1]) & 255) << 8) | ((((long) bArr[i5 + 2]) & 255) << 16) | ((((long) bArr[i5 + 3]) & 255) << 24) | ((((long) bArr[i5 + 4]) & 255) << 32) | ((((long) bArr[i5 + 5]) & 255) << 40) | ((((long) bArr[i5 + 6]) & 255) << 48) | ((((long) bArr[i5 + 7]) & 255) << 56)) * M_64;
            j2 = (j2 ^ ((j3 ^ (j3 >>> 47)) * M_64)) * M_64;
        }
        int i6 = i3 << 3;
        switch (i - i6) {
            case 1:
                long j4 = j2 ^ ((long) (bArr[i6] & UByte.MAX_VALUE));
                j = M_64;
                j2 = j4 * M_64;
                break;
            case 2:
                j2 ^= ((long) (bArr[i6 + 1] & UByte.MAX_VALUE)) << 8;
                long j42 = j2 ^ ((long) (bArr[i6] & UByte.MAX_VALUE));
                j = M_64;
                j2 = j42 * M_64;
                break;
            case 3:
                j2 ^= ((long) (bArr[i6 + 2] & UByte.MAX_VALUE)) << 16;
                j2 ^= ((long) (bArr[i6 + 1] & UByte.MAX_VALUE)) << 8;
                long j422 = j2 ^ ((long) (bArr[i6] & UByte.MAX_VALUE));
                j = M_64;
                j2 = j422 * M_64;
                break;
            case 4:
                j2 ^= ((long) (bArr[i6 + 3] & UByte.MAX_VALUE)) << 24;
                j2 ^= ((long) (bArr[i6 + 2] & UByte.MAX_VALUE)) << 16;
                j2 ^= ((long) (bArr[i6 + 1] & UByte.MAX_VALUE)) << 8;
                long j4222 = j2 ^ ((long) (bArr[i6] & UByte.MAX_VALUE));
                j = M_64;
                j2 = j4222 * M_64;
                break;
            case 5:
                j2 ^= ((long) (bArr[i6 + 4] & UByte.MAX_VALUE)) << 32;
                j2 ^= ((long) (bArr[i6 + 3] & UByte.MAX_VALUE)) << 24;
                j2 ^= ((long) (bArr[i6 + 2] & UByte.MAX_VALUE)) << 16;
                j2 ^= ((long) (bArr[i6 + 1] & UByte.MAX_VALUE)) << 8;
                long j42222 = j2 ^ ((long) (bArr[i6] & UByte.MAX_VALUE));
                j = M_64;
                j2 = j42222 * M_64;
                break;
            case 6:
                j2 ^= ((long) (bArr[i6 + 5] & UByte.MAX_VALUE)) << 40;
                j2 ^= ((long) (bArr[i6 + 4] & UByte.MAX_VALUE)) << 32;
                j2 ^= ((long) (bArr[i6 + 3] & UByte.MAX_VALUE)) << 24;
                j2 ^= ((long) (bArr[i6 + 2] & UByte.MAX_VALUE)) << 16;
                j2 ^= ((long) (bArr[i6 + 1] & UByte.MAX_VALUE)) << 8;
                long j422222 = j2 ^ ((long) (bArr[i6] & UByte.MAX_VALUE));
                j = M_64;
                j2 = j422222 * M_64;
                break;
            case 7:
                j2 ^= ((long) (bArr[i6 + 6] & UByte.MAX_VALUE)) << 48;
                j2 ^= ((long) (bArr[i6 + 5] & UByte.MAX_VALUE)) << 40;
                j2 ^= ((long) (bArr[i6 + 4] & UByte.MAX_VALUE)) << 32;
                j2 ^= ((long) (bArr[i6 + 3] & UByte.MAX_VALUE)) << 24;
                j2 ^= ((long) (bArr[i6 + 2] & UByte.MAX_VALUE)) << 16;
                j2 ^= ((long) (bArr[i6 + 1] & UByte.MAX_VALUE)) << 8;
                long j4222222 = j2 ^ ((long) (bArr[i6] & UByte.MAX_VALUE));
                j = M_64;
                j2 = j4222222 * M_64;
                break;
            default:
                j = M_64;
                break;
        }
        long j5 = (j2 ^ (j2 >>> 47)) * j;
        return j5 ^ (j5 >>> 47);
    }
}
