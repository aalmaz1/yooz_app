package com.google.crypto.tink.internal;

import com.google.crypto.tink.subtle.Bytes;
import com.google.crypto.tink.subtle.EngineFactory;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Arrays;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;
import org.apache.commons.io.FileUtils;
import org.msgpack.core.MessagePack;

/* JADX INFO: loaded from: classes2.dex */
public final class Ed25519 {
    public static final int PUBLIC_KEY_LEN = 32;
    public static final int SECRET_KEY_LEN = 32;
    public static final int SIGNATURE_LEN = 64;
    private static final CachedXYT CACHED_NEUTRAL = new CachedXYT(new long[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, new long[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, new long[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    private static final PartialXYZT NEUTRAL = new PartialXYZT(new XYZ(new long[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, new long[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, new long[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0}), new long[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    static final byte[] GROUP_ORDER = {-19, MessagePack.Code.INT64, -11, 92, 26, 99, 18, 88, MessagePack.Code.FIXEXT4, -100, -9, -94, MessagePack.Code.MAP16, -7, MessagePack.Code.MAP16, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16};

    private static int eq(int a, int b) {
        int i = (~(a ^ b)) & 255;
        int i2 = i & (i << 4);
        int i3 = i2 & (i2 << 2);
        return ((i3 & (i3 << 1)) >> 7) & 1;
    }

    private static class XYZ {
        final long[] x;
        final long[] y;
        final long[] z;

        XYZ() {
            this(new long[10], new long[10], new long[10]);
        }

        XYZ(long[] x, long[] y, long[] z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        XYZ(XYZ xyz) {
            this.x = Arrays.copyOf(xyz.x, 10);
            this.y = Arrays.copyOf(xyz.y, 10);
            this.z = Arrays.copyOf(xyz.z, 10);
        }

        XYZ(PartialXYZT partialXYZT) {
            this();
            fromPartialXYZT(this, partialXYZT);
        }

        static XYZ fromPartialXYZT(XYZ out, PartialXYZT in) {
            Field25519.mult(out.x, in.xyz.x, in.t);
            Field25519.mult(out.y, in.xyz.y, in.xyz.z);
            Field25519.mult(out.z, in.xyz.z, in.t);
            return out;
        }

        byte[] toBytes() {
            long[] jArr = new long[10];
            long[] jArr2 = new long[10];
            long[] jArr3 = new long[10];
            Field25519.inverse(jArr, this.z);
            Field25519.mult(jArr2, this.x, jArr);
            Field25519.mult(jArr3, this.y, jArr);
            byte[] bArrContract = Field25519.contract(jArr3);
            bArrContract[31] = (byte) ((Ed25519.getLsb(jArr2) << 7) ^ bArrContract[31]);
            return bArrContract;
        }

        boolean isOnCurve() {
            long[] jArr = new long[10];
            Field25519.square(jArr, this.x);
            long[] jArr2 = new long[10];
            Field25519.square(jArr2, this.y);
            long[] jArr3 = new long[10];
            Field25519.square(jArr3, this.z);
            long[] jArr4 = new long[10];
            Field25519.square(jArr4, jArr3);
            long[] jArr5 = new long[10];
            Field25519.sub(jArr5, jArr2, jArr);
            Field25519.mult(jArr5, jArr5, jArr3);
            long[] jArr6 = new long[10];
            Field25519.mult(jArr6, jArr, jArr2);
            Field25519.mult(jArr6, jArr6, Ed25519Constants.D);
            Field25519.sum(jArr6, jArr4);
            Field25519.reduce(jArr6, jArr6);
            return Bytes.equal(Field25519.contract(jArr5), Field25519.contract(jArr6));
        }
    }

    private static class XYZT {
        final long[] t;
        final XYZ xyz;

        XYZT() {
            this(new XYZ(), new long[10]);
        }

        XYZT(XYZ xyz, long[] t) {
            this.xyz = xyz;
            this.t = t;
        }

        XYZT(PartialXYZT partialXYZT) {
            this();
            fromPartialXYZT(this, partialXYZT);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static XYZT fromPartialXYZT(XYZT out, PartialXYZT in) {
            Field25519.mult(out.xyz.x, in.xyz.x, in.t);
            Field25519.mult(out.xyz.y, in.xyz.y, in.xyz.z);
            Field25519.mult(out.xyz.z, in.xyz.z, in.t);
            Field25519.mult(out.t, in.xyz.x, in.xyz.y);
            return out;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static XYZT fromBytesNegateVarTime(byte[] s) throws GeneralSecurityException {
            long[] jArr = new long[10];
            long[] jArrExpand = Field25519.expand(s);
            long[] jArr2 = new long[10];
            jArr2[0] = 1;
            long[] jArr3 = new long[10];
            long[] jArr4 = new long[10];
            long[] jArr5 = new long[10];
            long[] jArr6 = new long[10];
            long[] jArr7 = new long[10];
            Field25519.square(jArr4, jArrExpand);
            Field25519.mult(jArr5, jArr4, Ed25519Constants.D);
            Field25519.sub(jArr4, jArr4, jArr2);
            Field25519.sum(jArr5, jArr5, jArr2);
            long[] jArr8 = new long[10];
            Field25519.square(jArr8, jArr5);
            Field25519.mult(jArr8, jArr8, jArr5);
            Field25519.square(jArr, jArr8);
            Field25519.mult(jArr, jArr, jArr5);
            Field25519.mult(jArr, jArr, jArr4);
            Ed25519.pow2252m3(jArr, jArr);
            Field25519.mult(jArr, jArr, jArr8);
            Field25519.mult(jArr, jArr, jArr4);
            Field25519.square(jArr6, jArr);
            Field25519.mult(jArr6, jArr6, jArr5);
            Field25519.sub(jArr7, jArr6, jArr4);
            if (Ed25519.isNonZeroVarTime(jArr7)) {
                Field25519.sum(jArr7, jArr6, jArr4);
                if (Ed25519.isNonZeroVarTime(jArr7)) {
                    throw new GeneralSecurityException("Cannot convert given bytes to extended projective coordinates. No square root exists for modulo 2^255-19");
                }
                Field25519.mult(jArr, jArr, Ed25519Constants.SQRTM1);
            }
            if (Ed25519.isNonZeroVarTime(jArr) || ((s[31] & UByte.MAX_VALUE) >> 7) == 0) {
                if (Ed25519.getLsb(jArr) == ((s[31] & UByte.MAX_VALUE) >> 7)) {
                    Ed25519.neg(jArr, jArr);
                }
                Field25519.mult(jArr3, jArr, jArrExpand);
                return new XYZT(new XYZ(jArr, jArrExpand, jArr2), jArr3);
            }
            throw new GeneralSecurityException("Cannot convert given bytes to extended projective coordinates. Computed x is zero and encoded x's least significant bit is not zero");
        }
    }

    private static class PartialXYZT {
        final long[] t;
        final XYZ xyz;

        PartialXYZT() {
            this(new XYZ(), new long[10]);
        }

        PartialXYZT(XYZ xyz, long[] t) {
            this.xyz = xyz;
            this.t = t;
        }

        PartialXYZT(PartialXYZT other) {
            this.xyz = new XYZ(other.xyz);
            this.t = Arrays.copyOf(other.t, 10);
        }
    }

    static class CachedXYT {
        final long[] t2d;
        final long[] yMinusX;
        final long[] yPlusX;

        CachedXYT() {
            this(new long[10], new long[10], new long[10]);
        }

        CachedXYT(long[] yPlusX, long[] yMinusX, long[] t2d) {
            this.yPlusX = yPlusX;
            this.yMinusX = yMinusX;
            this.t2d = t2d;
        }

        CachedXYT(CachedXYT other) {
            this.yPlusX = Arrays.copyOf(other.yPlusX, 10);
            this.yMinusX = Arrays.copyOf(other.yMinusX, 10);
            this.t2d = Arrays.copyOf(other.t2d, 10);
        }

        void multByZ(long[] output, long[] in) {
            System.arraycopy(in, 0, output, 0, 10);
        }

        void copyConditional(CachedXYT other, int icopy) {
            Curve25519.copyConditional(this.yPlusX, other.yPlusX, icopy);
            Curve25519.copyConditional(this.yMinusX, other.yMinusX, icopy);
            Curve25519.copyConditional(this.t2d, other.t2d, icopy);
        }
    }

    private static class CachedXYZT extends CachedXYT {
        private final long[] z;

        CachedXYZT() {
            this(new long[10], new long[10], new long[10], new long[10]);
        }

        CachedXYZT(XYZT xyzt) {
            this();
            Field25519.sum(this.yPlusX, xyzt.xyz.y, xyzt.xyz.x);
            Field25519.sub(this.yMinusX, xyzt.xyz.y, xyzt.xyz.x);
            System.arraycopy(xyzt.xyz.z, 0, this.z, 0, 10);
            Field25519.mult(this.t2d, xyzt.t, Ed25519Constants.D2);
        }

        CachedXYZT(long[] yPlusX, long[] yMinusX, long[] z, long[] t2d) {
            super(yPlusX, yMinusX, t2d);
            this.z = z;
        }

        @Override // com.google.crypto.tink.internal.Ed25519.CachedXYT
        public void multByZ(long[] output, long[] in) {
            Field25519.mult(output, in, this.z);
        }
    }

    private static void add(PartialXYZT partialXYZT, XYZT extended, CachedXYT cached) {
        long[] jArr = new long[10];
        Field25519.sum(partialXYZT.xyz.x, extended.xyz.y, extended.xyz.x);
        Field25519.sub(partialXYZT.xyz.y, extended.xyz.y, extended.xyz.x);
        Field25519.mult(partialXYZT.xyz.y, partialXYZT.xyz.y, cached.yMinusX);
        Field25519.mult(partialXYZT.xyz.z, partialXYZT.xyz.x, cached.yPlusX);
        Field25519.mult(partialXYZT.t, extended.t, cached.t2d);
        cached.multByZ(partialXYZT.xyz.x, extended.xyz.z);
        Field25519.sum(jArr, partialXYZT.xyz.x, partialXYZT.xyz.x);
        Field25519.sub(partialXYZT.xyz.x, partialXYZT.xyz.z, partialXYZT.xyz.y);
        Field25519.sum(partialXYZT.xyz.y, partialXYZT.xyz.z, partialXYZT.xyz.y);
        Field25519.sum(partialXYZT.xyz.z, jArr, partialXYZT.t);
        Field25519.sub(partialXYZT.t, jArr, partialXYZT.t);
    }

    private static void sub(PartialXYZT partialXYZT, XYZT extended, CachedXYT cached) {
        long[] jArr = new long[10];
        Field25519.sum(partialXYZT.xyz.x, extended.xyz.y, extended.xyz.x);
        Field25519.sub(partialXYZT.xyz.y, extended.xyz.y, extended.xyz.x);
        Field25519.mult(partialXYZT.xyz.y, partialXYZT.xyz.y, cached.yPlusX);
        Field25519.mult(partialXYZT.xyz.z, partialXYZT.xyz.x, cached.yMinusX);
        Field25519.mult(partialXYZT.t, extended.t, cached.t2d);
        cached.multByZ(partialXYZT.xyz.x, extended.xyz.z);
        Field25519.sum(jArr, partialXYZT.xyz.x, partialXYZT.xyz.x);
        Field25519.sub(partialXYZT.xyz.x, partialXYZT.xyz.z, partialXYZT.xyz.y);
        Field25519.sum(partialXYZT.xyz.y, partialXYZT.xyz.z, partialXYZT.xyz.y);
        Field25519.sub(partialXYZT.xyz.z, jArr, partialXYZT.t);
        Field25519.sum(partialXYZT.t, jArr, partialXYZT.t);
    }

    private static void doubleXYZ(PartialXYZT partialXYZT, XYZ p) {
        long[] jArr = new long[10];
        Field25519.square(partialXYZT.xyz.x, p.x);
        Field25519.square(partialXYZT.xyz.z, p.y);
        Field25519.square(partialXYZT.t, p.z);
        Field25519.sum(partialXYZT.t, partialXYZT.t, partialXYZT.t);
        Field25519.sum(partialXYZT.xyz.y, p.x, p.y);
        Field25519.square(jArr, partialXYZT.xyz.y);
        Field25519.sum(partialXYZT.xyz.y, partialXYZT.xyz.z, partialXYZT.xyz.x);
        Field25519.sub(partialXYZT.xyz.z, partialXYZT.xyz.z, partialXYZT.xyz.x);
        Field25519.sub(partialXYZT.xyz.x, jArr, partialXYZT.xyz.y);
        Field25519.sub(partialXYZT.t, partialXYZT.t, partialXYZT.xyz.z);
    }

    private static void doubleXYZT(PartialXYZT partialXYZT, XYZT p) {
        doubleXYZ(partialXYZT, p.xyz);
    }

    private static void select(CachedXYT t, int pos, byte b) {
        int i = (b & UByte.MAX_VALUE) >> 7;
        int i2 = b - (((-i) & b) << 1);
        t.copyConditional(Ed25519Constants.B_TABLE[pos][0], eq(i2, 1));
        t.copyConditional(Ed25519Constants.B_TABLE[pos][1], eq(i2, 2));
        t.copyConditional(Ed25519Constants.B_TABLE[pos][2], eq(i2, 3));
        t.copyConditional(Ed25519Constants.B_TABLE[pos][3], eq(i2, 4));
        t.copyConditional(Ed25519Constants.B_TABLE[pos][4], eq(i2, 5));
        t.copyConditional(Ed25519Constants.B_TABLE[pos][5], eq(i2, 6));
        t.copyConditional(Ed25519Constants.B_TABLE[pos][6], eq(i2, 7));
        t.copyConditional(Ed25519Constants.B_TABLE[pos][7], eq(i2, 8));
        long[] jArrCopyOf = Arrays.copyOf(t.yMinusX, 10);
        long[] jArrCopyOf2 = Arrays.copyOf(t.yPlusX, 10);
        long[] jArrCopyOf3 = Arrays.copyOf(t.t2d, 10);
        neg(jArrCopyOf3, jArrCopyOf3);
        t.copyConditional(new CachedXYT(jArrCopyOf, jArrCopyOf2, jArrCopyOf3), i);
    }

    private static XYZ scalarMultWithBase(byte[] a) {
        int i;
        byte[] bArr = new byte[64];
        int i2 = 0;
        while (true) {
            if (i2 >= 32) {
                break;
            }
            int i3 = i2 * 2;
            bArr[i3 + 0] = (byte) (((a[i2] & UByte.MAX_VALUE) >> 0) & 15);
            bArr[i3 + 1] = (byte) (((a[i2] & UByte.MAX_VALUE) >> 4) & 15);
            i2++;
        }
        int i4 = 0;
        int i5 = 0;
        while (i4 < 63) {
            byte b = (byte) (bArr[i4] + i5);
            bArr[i4] = b;
            int i6 = (b + 8) >> 4;
            bArr[i4] = (byte) (b - (i6 << 4));
            i4++;
            i5 = i6;
        }
        bArr[63] = (byte) (bArr[63] + i5);
        PartialXYZT partialXYZT = new PartialXYZT(NEUTRAL);
        XYZT xyzt = new XYZT();
        for (i = 1; i < 64; i += 2) {
            CachedXYT cachedXYT = new CachedXYT(CACHED_NEUTRAL);
            select(cachedXYT, i / 2, bArr[i]);
            add(partialXYZT, XYZT.fromPartialXYZT(xyzt, partialXYZT), cachedXYT);
        }
        XYZ xyz = new XYZ();
        doubleXYZ(partialXYZT, XYZ.fromPartialXYZT(xyz, partialXYZT));
        doubleXYZ(partialXYZT, XYZ.fromPartialXYZT(xyz, partialXYZT));
        doubleXYZ(partialXYZT, XYZ.fromPartialXYZT(xyz, partialXYZT));
        doubleXYZ(partialXYZT, XYZ.fromPartialXYZT(xyz, partialXYZT));
        for (int i7 = 0; i7 < 64; i7 += 2) {
            CachedXYT cachedXYT2 = new CachedXYT(CACHED_NEUTRAL);
            select(cachedXYT2, i7 / 2, bArr[i7]);
            add(partialXYZT, XYZT.fromPartialXYZT(xyzt, partialXYZT), cachedXYT2);
        }
        XYZ xyz2 = new XYZ(partialXYZT);
        if (xyz2.isOnCurve()) {
            return xyz2;
        }
        throw new IllegalStateException("arithmetic error in scalar multiplication");
    }

    public static byte[] scalarMultWithBaseToBytes(byte[] a) {
        return scalarMultWithBase(a).toBytes();
    }

    private static byte[] slide(byte[] a) {
        int i;
        byte[] bArr = new byte[256];
        for (int i2 = 0; i2 < 256; i2++) {
            bArr[i2] = (byte) (1 & ((a[i2 >> 3] & UByte.MAX_VALUE) >> (i2 & 7)));
        }
        for (int i3 = 0; i3 < 256; i3++) {
            if (bArr[i3] != 0) {
                for (int i4 = 1; i4 <= 6 && (i = i3 + i4) < 256; i4++) {
                    byte b = bArr[i];
                    if (b != 0) {
                        byte b2 = bArr[i3];
                        if ((b << i4) + b2 <= 15) {
                            bArr[i3] = (byte) (b2 + (b << i4));
                            bArr[i] = 0;
                        } else if (b2 - (b << i4) >= -15) {
                            bArr[i3] = (byte) (b2 - (b << i4));
                            while (true) {
                                if (i >= 256) {
                                    break;
                                }
                                if (bArr[i] == 0) {
                                    bArr[i] = 1;
                                    break;
                                }
                                bArr[i] = 0;
                                i++;
                            }
                        }
                    }
                }
            }
        }
        return bArr;
    }

    private static XYZ doubleScalarMultVarTime(byte[] a, XYZT pointA, byte[] b) {
        CachedXYZT[] cachedXYZTArr = new CachedXYZT[8];
        cachedXYZTArr[0] = new CachedXYZT(pointA);
        PartialXYZT partialXYZT = new PartialXYZT();
        doubleXYZT(partialXYZT, pointA);
        XYZT xyzt = new XYZT(partialXYZT);
        for (int i = 1; i < 8; i++) {
            add(partialXYZT, xyzt, cachedXYZTArr[i - 1]);
            cachedXYZTArr[i] = new CachedXYZT(new XYZT(partialXYZT));
        }
        byte[] bArrSlide = slide(a);
        byte[] bArrSlide2 = slide(b);
        PartialXYZT partialXYZT2 = new PartialXYZT(NEUTRAL);
        XYZT xyzt2 = new XYZT();
        int i2 = 255;
        while (i2 >= 0 && bArrSlide[i2] == 0 && bArrSlide2[i2] == 0) {
            i2--;
        }
        while (i2 >= 0) {
            doubleXYZ(partialXYZT2, new XYZ(partialXYZT2));
            byte b2 = bArrSlide[i2];
            if (b2 > 0) {
                add(partialXYZT2, XYZT.fromPartialXYZT(xyzt2, partialXYZT2), cachedXYZTArr[bArrSlide[i2] / 2]);
            } else if (b2 < 0) {
                sub(partialXYZT2, XYZT.fromPartialXYZT(xyzt2, partialXYZT2), cachedXYZTArr[(-bArrSlide[i2]) / 2]);
            }
            byte b3 = bArrSlide2[i2];
            if (b3 > 0) {
                add(partialXYZT2, XYZT.fromPartialXYZT(xyzt2, partialXYZT2), Ed25519Constants.B2[bArrSlide2[i2] / 2]);
            } else if (b3 < 0) {
                sub(partialXYZT2, XYZT.fromPartialXYZT(xyzt2, partialXYZT2), Ed25519Constants.B2[(-bArrSlide2[i2]) / 2]);
            }
            i2--;
        }
        return new XYZ(partialXYZT2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isNonZeroVarTime(long[] in) {
        long[] jArr = new long[in.length + 1];
        System.arraycopy(in, 0, jArr, 0, in.length);
        Field25519.reduceCoefficients(jArr);
        for (byte b : Field25519.contract(jArr)) {
            if (b != 0) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getLsb(long[] in) {
        return Field25519.contract(in)[0] & 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void neg(long[] out, long[] in) {
        for (int i = 0; i < in.length; i++) {
            out[i] = -in[i];
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void pow2252m3(long[] out, long[] in) {
        long[] jArr = new long[10];
        long[] jArr2 = new long[10];
        long[] jArr3 = new long[10];
        Field25519.square(jArr, in);
        Field25519.square(jArr2, jArr);
        Field25519.square(jArr2, jArr2);
        Field25519.mult(jArr2, in, jArr2);
        Field25519.mult(jArr, jArr, jArr2);
        Field25519.square(jArr, jArr);
        Field25519.mult(jArr, jArr2, jArr);
        Field25519.square(jArr2, jArr);
        for (int i = 1; i < 5; i++) {
            Field25519.square(jArr2, jArr2);
        }
        Field25519.mult(jArr, jArr2, jArr);
        Field25519.square(jArr2, jArr);
        for (int i2 = 1; i2 < 10; i2++) {
            Field25519.square(jArr2, jArr2);
        }
        Field25519.mult(jArr2, jArr2, jArr);
        Field25519.square(jArr3, jArr2);
        for (int i3 = 1; i3 < 20; i3++) {
            Field25519.square(jArr3, jArr3);
        }
        Field25519.mult(jArr2, jArr3, jArr2);
        Field25519.square(jArr2, jArr2);
        for (int i4 = 1; i4 < 10; i4++) {
            Field25519.square(jArr2, jArr2);
        }
        Field25519.mult(jArr, jArr2, jArr);
        Field25519.square(jArr2, jArr);
        for (int i5 = 1; i5 < 50; i5++) {
            Field25519.square(jArr2, jArr2);
        }
        Field25519.mult(jArr2, jArr2, jArr);
        Field25519.square(jArr3, jArr2);
        for (int i6 = 1; i6 < 100; i6++) {
            Field25519.square(jArr3, jArr3);
        }
        Field25519.mult(jArr2, jArr3, jArr2);
        Field25519.square(jArr2, jArr2);
        for (int i7 = 1; i7 < 50; i7++) {
            Field25519.square(jArr2, jArr2);
        }
        Field25519.mult(jArr, jArr2, jArr);
        Field25519.square(jArr, jArr);
        Field25519.square(jArr, jArr);
        Field25519.mult(out, jArr, in);
    }

    private static long load3(byte[] in, int idx) {
        return (((long) (in[idx + 2] & UByte.MAX_VALUE)) << 16) | (((long) in[idx]) & 255) | (((long) (in[idx + 1] & UByte.MAX_VALUE)) << 8);
    }

    private static long load4(byte[] in, int idx) {
        return (((long) (in[idx + 3] & UByte.MAX_VALUE)) << 24) | load3(in, idx);
    }

    private static void reduce(byte[] s) {
        long jLoad3 = load3(s, 0) & 2097151;
        long jLoad4 = (load4(s, 2) >> 5) & 2097151;
        long jLoad32 = (load3(s, 5) >> 2) & 2097151;
        long jLoad42 = (load4(s, 7) >> 7) & 2097151;
        long jLoad43 = (load4(s, 10) >> 4) & 2097151;
        long jLoad33 = (load3(s, 13) >> 1) & 2097151;
        long jLoad44 = (load4(s, 15) >> 6) & 2097151;
        long jLoad34 = (load3(s, 18) >> 3) & 2097151;
        long jLoad35 = load3(s, 21) & 2097151;
        long jLoad45 = (load4(s, 23) >> 5) & 2097151;
        long jLoad36 = (load3(s, 26) >> 2) & 2097151;
        long jLoad46 = (load4(s, 28) >> 7) & 2097151;
        long jLoad47 = (load4(s, 31) >> 4) & 2097151;
        long jLoad37 = (load3(s, 34) >> 1) & 2097151;
        long jLoad48 = (load4(s, 36) >> 6) & 2097151;
        long jLoad38 = (load3(s, 39) >> 3) & 2097151;
        long jLoad39 = load3(s, 42) & 2097151;
        long jLoad49 = (load4(s, 44) >> 5) & 2097151;
        long jLoad310 = (load3(s, 47) >> 2) & 2097151;
        long jLoad410 = (load4(s, 49) >> 7) & 2097151;
        long jLoad411 = (load4(s, 52) >> 4) & 2097151;
        long jLoad311 = (load3(s, 55) >> 1) & 2097151;
        long jLoad412 = (load4(s, 57) >> 6) & 2097151;
        long jLoad413 = load4(s, 60) >> 3;
        long j = jLoad39 - (jLoad413 * 683901);
        long j2 = ((jLoad48 - (jLoad413 * 997805)) + (jLoad412 * 136657)) - (jLoad311 * 683901);
        long j3 = ((((jLoad47 + (jLoad413 * 470296)) + (jLoad412 * 654183)) - (jLoad311 * 997805)) + (jLoad411 * 136657)) - (jLoad410 * 683901);
        long j4 = jLoad44 + (jLoad310 * 666643);
        long j5 = jLoad34 + (jLoad410 * 666643) + (jLoad310 * 470296);
        long j6 = jLoad35 + (jLoad411 * 666643) + (jLoad410 * 470296) + (jLoad310 * 654183);
        long j7 = (((jLoad45 + (jLoad311 * 666643)) + (jLoad411 * 470296)) + (jLoad410 * 654183)) - (jLoad310 * 997805);
        long j8 = ((((jLoad36 + (jLoad412 * 666643)) + (jLoad311 * 470296)) + (jLoad411 * 654183)) - (jLoad410 * 997805)) + (jLoad310 * 136657);
        long j9 = (((((jLoad46 + (jLoad413 * 666643)) + (jLoad412 * 470296)) + (jLoad311 * 654183)) - (jLoad411 * 997805)) + (jLoad410 * 136657)) - (jLoad310 * 683901);
        long j10 = (j4 + FileUtils.ONE_MB) >> 21;
        long j11 = j5 + j10;
        long j12 = j4 - (j10 << 21);
        long j13 = (j6 + FileUtils.ONE_MB) >> 21;
        long j14 = j7 + j13;
        long j15 = j6 - (j13 << 21);
        long j16 = (j8 + FileUtils.ONE_MB) >> 21;
        long j17 = j9 + j16;
        long j18 = j8 - (j16 << 21);
        long j19 = (j3 + FileUtils.ONE_MB) >> 21;
        long j20 = ((((jLoad37 + (jLoad413 * 654183)) - (jLoad412 * 997805)) + (jLoad311 * 136657)) - (jLoad411 * 683901)) + j19;
        long j21 = j3 - (j19 << 21);
        long j22 = (j2 + FileUtils.ONE_MB) >> 21;
        long j23 = ((jLoad38 + (jLoad413 * 136657)) - (jLoad412 * 683901)) + j22;
        long j24 = j2 - (j22 << 21);
        long j25 = (j + FileUtils.ONE_MB) >> 21;
        long j26 = jLoad49 + j25;
        long j27 = j - (j25 << 21);
        long j28 = (j11 + FileUtils.ONE_MB) >> 21;
        long j29 = j15 + j28;
        long j30 = j11 - (j28 << 21);
        long j31 = (j14 + FileUtils.ONE_MB) >> 21;
        long j32 = j18 + j31;
        long j33 = j14 - (j31 << 21);
        long j34 = (j17 + FileUtils.ONE_MB) >> 21;
        long j35 = j21 + j34;
        long j36 = j17 - (j34 << 21);
        long j37 = (j20 + FileUtils.ONE_MB) >> 21;
        long j38 = j24 + j37;
        long j39 = j20 - (j37 << 21);
        long j40 = (j23 + FileUtils.ONE_MB) >> 21;
        long j41 = j27 + j40;
        long j42 = j23 - (j40 << 21);
        long j43 = j32 - (j26 * 683901);
        long j44 = ((j29 - (j26 * 997805)) + (j41 * 136657)) - (j42 * 683901);
        long j45 = ((((j12 + (j26 * 470296)) + (j41 * 654183)) - (j42 * 997805)) + (j38 * 136657)) - (j39 * 683901);
        long j46 = jLoad3 + (j35 * 666643);
        long j47 = jLoad4 + (j39 * 666643) + (j35 * 470296);
        long j48 = jLoad32 + (j38 * 666643) + (j39 * 470296) + (j35 * 654183);
        long j49 = (((jLoad42 + (j42 * 666643)) + (j38 * 470296)) + (j39 * 654183)) - (j35 * 997805);
        long j50 = ((((jLoad43 + (j41 * 666643)) + (j42 * 470296)) + (j38 * 654183)) - (j39 * 997805)) + (j35 * 136657);
        long j51 = (((((jLoad33 + (j26 * 666643)) + (j41 * 470296)) + (j42 * 654183)) - (j38 * 997805)) + (j39 * 136657)) - (j35 * 683901);
        long j52 = (j46 + FileUtils.ONE_MB) >> 21;
        long j53 = j47 + j52;
        long j54 = j46 - (j52 << 21);
        long j55 = (j48 + FileUtils.ONE_MB) >> 21;
        long j56 = j49 + j55;
        long j57 = j48 - (j55 << 21);
        long j58 = (j50 + FileUtils.ONE_MB) >> 21;
        long j59 = j51 + j58;
        long j60 = j50 - (j58 << 21);
        long j61 = (j45 + FileUtils.ONE_MB) >> 21;
        long j62 = ((((j30 + (j26 * 654183)) - (j41 * 997805)) + (j42 * 136657)) - (j38 * 683901)) + j61;
        long j63 = j45 - (j61 << 21);
        long j64 = (j44 + FileUtils.ONE_MB) >> 21;
        long j65 = ((j33 + (j26 * 136657)) - (j41 * 683901)) + j64;
        long j66 = j44 - (j64 << 21);
        long j67 = (j43 + FileUtils.ONE_MB) >> 21;
        long j68 = j36 + j67;
        long j69 = j43 - (j67 << 21);
        long j70 = (j53 + FileUtils.ONE_MB) >> 21;
        long j71 = j57 + j70;
        long j72 = j53 - (j70 << 21);
        long j73 = (j56 + FileUtils.ONE_MB) >> 21;
        long j74 = j60 + j73;
        long j75 = j56 - (j73 << 21);
        long j76 = (j59 + FileUtils.ONE_MB) >> 21;
        long j77 = j63 + j76;
        long j78 = j59 - (j76 << 21);
        long j79 = (j62 + FileUtils.ONE_MB) >> 21;
        long j80 = j66 + j79;
        long j81 = j62 - (j79 << 21);
        long j82 = (j65 + FileUtils.ONE_MB) >> 21;
        long j83 = j69 + j82;
        long j84 = j65 - (j82 << 21);
        long j85 = (j68 + FileUtils.ONE_MB) >> 21;
        long j86 = j85 + 0;
        long j87 = j54 + (j86 * 666643);
        long j88 = j72 + (j86 * 470296);
        long j89 = j71 + (j86 * 654183);
        long j90 = j75 - (j86 * 997805);
        long j91 = j74 + (j86 * 136657);
        long j92 = j78 - (j86 * 683901);
        long j93 = j87 >> 21;
        long j94 = j88 + j93;
        long j95 = j87 - (j93 << 21);
        long j96 = j94 >> 21;
        long j97 = j89 + j96;
        long j98 = j94 - (j96 << 21);
        long j99 = j97 >> 21;
        long j100 = j90 + j99;
        long j101 = j97 - (j99 << 21);
        long j102 = j100 >> 21;
        long j103 = j91 + j102;
        long j104 = j100 - (j102 << 21);
        long j105 = j103 >> 21;
        long j106 = j92 + j105;
        long j107 = j103 - (j105 << 21);
        long j108 = j106 >> 21;
        long j109 = j77 + j108;
        long j110 = j106 - (j108 << 21);
        long j111 = j109 >> 21;
        long j112 = j81 + j111;
        long j113 = j109 - (j111 << 21);
        long j114 = j112 >> 21;
        long j115 = j80 + j114;
        long j116 = j112 - (j114 << 21);
        long j117 = j115 >> 21;
        long j118 = j84 + j117;
        long j119 = j115 - (j117 << 21);
        long j120 = j118 >> 21;
        long j121 = j83 + j120;
        long j122 = j118 - (j120 << 21);
        long j123 = j121 >> 21;
        long j124 = (j68 - (j85 << 21)) + j123;
        long j125 = j121 - (j123 << 21);
        long j126 = j124 >> 21;
        long j127 = j126 + 0;
        long j128 = j124 - (j126 << 21);
        long j129 = j95 + (666643 * j127);
        long j130 = j129 >> 21;
        long j131 = j98 + (470296 * j127) + j130;
        long j132 = j129 - (j130 << 21);
        long j133 = j131 >> 21;
        long j134 = j101 + (654183 * j127) + j133;
        long j135 = j131 - (j133 << 21);
        long j136 = j134 >> 21;
        long j137 = (j104 - (997805 * j127)) + j136;
        long j138 = j134 - (j136 << 21);
        long j139 = j137 >> 21;
        long j140 = j107 + (136657 * j127) + j139;
        long j141 = j137 - (j139 << 21);
        long j142 = j140 >> 21;
        long j143 = (j110 - (j127 * 683901)) + j142;
        long j144 = j140 - (j142 << 21);
        long j145 = j143 >> 21;
        long j146 = j113 + j145;
        long j147 = j143 - (j145 << 21);
        long j148 = j146 >> 21;
        long j149 = j116 + j148;
        long j150 = j146 - (j148 << 21);
        long j151 = j149 >> 21;
        long j152 = j119 + j151;
        long j153 = j149 - (j151 << 21);
        long j154 = j152 >> 21;
        long j155 = j122 + j154;
        long j156 = j155 >> 21;
        long j157 = j125 + j156;
        long j158 = j155 - (j156 << 21);
        long j159 = j157 >> 21;
        long j160 = j128 + j159;
        long j161 = j157 - (j159 << 21);
        s[0] = (byte) j132;
        s[1] = (byte) (j132 >> 8);
        s[2] = (byte) ((j132 >> 16) | (j135 << 5));
        s[3] = (byte) (j135 >> 3);
        s[4] = (byte) (j135 >> 11);
        s[5] = (byte) ((j135 >> 19) | (j138 << 2));
        s[6] = (byte) (j138 >> 6);
        s[7] = (byte) ((j138 >> 14) | (j141 << 7));
        s[8] = (byte) (j141 >> 1);
        s[9] = (byte) (j141 >> 9);
        s[10] = (byte) ((j141 >> 17) | (j144 << 4));
        s[11] = (byte) (j144 >> 4);
        s[12] = (byte) (j144 >> 12);
        s[13] = (byte) ((j144 >> 20) | (j147 << 1));
        s[14] = (byte) (j147 >> 7);
        s[15] = (byte) ((j147 >> 15) | (j150 << 6));
        s[16] = (byte) (j150 >> 2);
        s[17] = (byte) (j150 >> 10);
        s[18] = (byte) ((j150 >> 18) | (j153 << 3));
        s[19] = (byte) (j153 >> 5);
        s[20] = (byte) (j153 >> 13);
        s[21] = (byte) (j152 - (j154 << 21));
        s[22] = (byte) (r11 >> 8);
        s[23] = (byte) ((r11 >> 16) | (j158 << 5));
        s[24] = (byte) (j158 >> 3);
        s[25] = (byte) (j158 >> 11);
        s[26] = (byte) ((j158 >> 19) | (j161 << 2));
        s[27] = (byte) (j161 >> 6);
        s[28] = (byte) ((j161 >> 14) | (j160 << 7));
        s[29] = (byte) (j160 >> 1);
        s[30] = (byte) (j160 >> 9);
        s[31] = (byte) (j160 >> 17);
    }

    private static void mulAdd(byte[] s, byte[] a, byte[] b, byte[] c) {
        long jLoad3 = load3(a, 0) & 2097151;
        long jLoad4 = (load4(a, 2) >> 5) & 2097151;
        long jLoad32 = (load3(a, 5) >> 2) & 2097151;
        long jLoad42 = (load4(a, 7) >> 7) & 2097151;
        long jLoad43 = (load4(a, 10) >> 4) & 2097151;
        long jLoad33 = (load3(a, 13) >> 1) & 2097151;
        long jLoad44 = (load4(a, 15) >> 6) & 2097151;
        long jLoad34 = (load3(a, 18) >> 3) & 2097151;
        long jLoad35 = load3(a, 21) & 2097151;
        long jLoad45 = (load4(a, 23) >> 5) & 2097151;
        long jLoad36 = (load3(a, 26) >> 2) & 2097151;
        long jLoad46 = load4(a, 28) >> 7;
        long jLoad37 = load3(b, 0) & 2097151;
        long jLoad47 = (load4(b, 2) >> 5) & 2097151;
        long jLoad38 = (load3(b, 5) >> 2) & 2097151;
        long jLoad48 = (load4(b, 7) >> 7) & 2097151;
        long jLoad49 = (load4(b, 10) >> 4) & 2097151;
        long jLoad39 = (load3(b, 13) >> 1) & 2097151;
        long jLoad410 = (load4(b, 15) >> 6) & 2097151;
        long jLoad310 = (load3(b, 18) >> 3) & 2097151;
        long jLoad311 = load3(b, 21) & 2097151;
        long jLoad411 = (load4(b, 23) >> 5) & 2097151;
        long jLoad312 = (load3(b, 26) >> 2) & 2097151;
        long jLoad412 = load4(b, 28) >> 7;
        long jLoad313 = load3(c, 0) & 2097151;
        long jLoad413 = (load4(c, 2) >> 5) & 2097151;
        long jLoad314 = (load3(c, 5) >> 2) & 2097151;
        long jLoad414 = (load4(c, 7) >> 7) & 2097151;
        long jLoad415 = (load4(c, 10) >> 4) & 2097151;
        long jLoad315 = (load3(c, 13) >> 1) & 2097151;
        long jLoad416 = (load4(c, 15) >> 6) & 2097151;
        long jLoad316 = (load3(c, 18) >> 3) & 2097151;
        long jLoad317 = load3(c, 21) & 2097151;
        long j = jLoad313 + (jLoad3 * jLoad37);
        long j2 = jLoad413 + (jLoad3 * jLoad47) + (jLoad4 * jLoad37);
        long j3 = jLoad314 + (jLoad3 * jLoad38) + (jLoad4 * jLoad47) + (jLoad32 * jLoad37);
        long j4 = jLoad414 + (jLoad3 * jLoad48) + (jLoad4 * jLoad38) + (jLoad32 * jLoad47) + (jLoad42 * jLoad37);
        long j5 = jLoad415 + (jLoad3 * jLoad49) + (jLoad4 * jLoad48) + (jLoad32 * jLoad38) + (jLoad42 * jLoad47) + (jLoad43 * jLoad37);
        long j6 = jLoad315 + (jLoad3 * jLoad39) + (jLoad4 * jLoad49) + (jLoad32 * jLoad48) + (jLoad42 * jLoad38) + (jLoad43 * jLoad47) + (jLoad33 * jLoad37);
        long j7 = jLoad416 + (jLoad3 * jLoad410) + (jLoad4 * jLoad39) + (jLoad32 * jLoad49) + (jLoad42 * jLoad48) + (jLoad43 * jLoad38) + (jLoad33 * jLoad47) + (jLoad44 * jLoad37);
        long j8 = jLoad316 + (jLoad3 * jLoad310) + (jLoad4 * jLoad410) + (jLoad32 * jLoad39) + (jLoad42 * jLoad49) + (jLoad43 * jLoad48) + (jLoad33 * jLoad38) + (jLoad44 * jLoad47) + (jLoad34 * jLoad37);
        long j9 = jLoad317 + (jLoad3 * jLoad311) + (jLoad4 * jLoad310) + (jLoad32 * jLoad410) + (jLoad42 * jLoad39) + (jLoad43 * jLoad49) + (jLoad33 * jLoad48) + (jLoad44 * jLoad38) + (jLoad34 * jLoad47) + (jLoad35 * jLoad37);
        long jLoad417 = ((load4(c, 23) >> 5) & 2097151) + (jLoad3 * jLoad411) + (jLoad4 * jLoad311) + (jLoad32 * jLoad310) + (jLoad42 * jLoad410) + (jLoad43 * jLoad39) + (jLoad33 * jLoad49) + (jLoad44 * jLoad48) + (jLoad34 * jLoad38) + (jLoad35 * jLoad47) + (jLoad45 * jLoad37);
        long jLoad318 = ((load3(c, 26) >> 2) & 2097151) + (jLoad3 * jLoad312) + (jLoad4 * jLoad411) + (jLoad32 * jLoad311) + (jLoad42 * jLoad310) + (jLoad43 * jLoad410) + (jLoad33 * jLoad39) + (jLoad44 * jLoad49) + (jLoad34 * jLoad48) + (jLoad35 * jLoad38) + (jLoad45 * jLoad47) + (jLoad36 * jLoad37);
        long jLoad418 = (load4(c, 28) >> 7) + (jLoad3 * jLoad412) + (jLoad4 * jLoad312) + (jLoad32 * jLoad411) + (jLoad42 * jLoad311) + (jLoad43 * jLoad310) + (jLoad33 * jLoad410) + (jLoad44 * jLoad39) + (jLoad34 * jLoad49) + (jLoad35 * jLoad48) + (jLoad45 * jLoad38) + (jLoad36 * jLoad47) + (jLoad37 * jLoad46);
        long j10 = (jLoad4 * jLoad412) + (jLoad32 * jLoad312) + (jLoad42 * jLoad411) + (jLoad43 * jLoad311) + (jLoad33 * jLoad310) + (jLoad44 * jLoad410) + (jLoad34 * jLoad39) + (jLoad35 * jLoad49) + (jLoad45 * jLoad48) + (jLoad36 * jLoad38) + (jLoad47 * jLoad46);
        long j11 = (jLoad32 * jLoad412) + (jLoad42 * jLoad312) + (jLoad43 * jLoad411) + (jLoad33 * jLoad311) + (jLoad44 * jLoad310) + (jLoad34 * jLoad410) + (jLoad35 * jLoad39) + (jLoad45 * jLoad49) + (jLoad36 * jLoad48) + (jLoad38 * jLoad46);
        long j12 = (jLoad42 * jLoad412) + (jLoad43 * jLoad312) + (jLoad33 * jLoad411) + (jLoad44 * jLoad311) + (jLoad34 * jLoad310) + (jLoad35 * jLoad410) + (jLoad45 * jLoad39) + (jLoad36 * jLoad49) + (jLoad48 * jLoad46);
        long j13 = (jLoad43 * jLoad412) + (jLoad33 * jLoad312) + (jLoad44 * jLoad411) + (jLoad34 * jLoad311) + (jLoad35 * jLoad310) + (jLoad45 * jLoad410) + (jLoad36 * jLoad39) + (jLoad49 * jLoad46);
        long j14 = (jLoad33 * jLoad412) + (jLoad44 * jLoad312) + (jLoad34 * jLoad411) + (jLoad35 * jLoad311) + (jLoad45 * jLoad310) + (jLoad36 * jLoad410) + (jLoad39 * jLoad46);
        long j15 = (jLoad44 * jLoad412) + (jLoad34 * jLoad312) + (jLoad35 * jLoad411) + (jLoad45 * jLoad311) + (jLoad36 * jLoad310) + (jLoad410 * jLoad46);
        long j16 = (jLoad34 * jLoad412) + (jLoad35 * jLoad312) + (jLoad45 * jLoad411) + (jLoad36 * jLoad311) + (jLoad310 * jLoad46);
        long j17 = (jLoad35 * jLoad412) + (jLoad45 * jLoad312) + (jLoad36 * jLoad411) + (jLoad311 * jLoad46);
        long j18 = (jLoad45 * jLoad412) + (jLoad36 * jLoad312) + (jLoad411 * jLoad46);
        long j19 = (jLoad36 * jLoad412) + (jLoad312 * jLoad46);
        long j20 = jLoad46 * jLoad412;
        long j21 = (j + FileUtils.ONE_MB) >> 21;
        long j22 = j2 + j21;
        long j23 = j - (j21 << 21);
        long j24 = (j3 + FileUtils.ONE_MB) >> 21;
        long j25 = j4 + j24;
        long j26 = j3 - (j24 << 21);
        long j27 = (j5 + FileUtils.ONE_MB) >> 21;
        long j28 = j6 + j27;
        long j29 = j5 - (j27 << 21);
        long j30 = (j7 + FileUtils.ONE_MB) >> 21;
        long j31 = j8 + j30;
        long j32 = j7 - (j30 << 21);
        long j33 = (j9 + FileUtils.ONE_MB) >> 21;
        long j34 = jLoad417 + j33;
        long j35 = j9 - (j33 << 21);
        long j36 = (jLoad318 + FileUtils.ONE_MB) >> 21;
        long j37 = jLoad418 + j36;
        long j38 = jLoad318 - (j36 << 21);
        long j39 = (j10 + FileUtils.ONE_MB) >> 21;
        long j40 = j11 + j39;
        long j41 = j10 - (j39 << 21);
        long j42 = (j12 + FileUtils.ONE_MB) >> 21;
        long j43 = j13 + j42;
        long j44 = j12 - (j42 << 21);
        long j45 = (j14 + FileUtils.ONE_MB) >> 21;
        long j46 = j15 + j45;
        long j47 = j14 - (j45 << 21);
        long j48 = (j16 + FileUtils.ONE_MB) >> 21;
        long j49 = j17 + j48;
        long j50 = j16 - (j48 << 21);
        long j51 = (j18 + FileUtils.ONE_MB) >> 21;
        long j52 = j19 + j51;
        long j53 = j18 - (j51 << 21);
        long j54 = (j20 + FileUtils.ONE_MB) >> 21;
        long j55 = j54 + 0;
        long j56 = j20 - (j54 << 21);
        long j57 = (j22 + FileUtils.ONE_MB) >> 21;
        long j58 = j26 + j57;
        long j59 = j22 - (j57 << 21);
        long j60 = (j25 + FileUtils.ONE_MB) >> 21;
        long j61 = j29 + j60;
        long j62 = j25 - (j60 << 21);
        long j63 = (j28 + FileUtils.ONE_MB) >> 21;
        long j64 = j32 + j63;
        long j65 = j28 - (j63 << 21);
        long j66 = (j31 + FileUtils.ONE_MB) >> 21;
        long j67 = j35 + j66;
        long j68 = j31 - (j66 << 21);
        long j69 = (j34 + FileUtils.ONE_MB) >> 21;
        long j70 = j38 + j69;
        long j71 = j34 - (j69 << 21);
        long j72 = (j37 + FileUtils.ONE_MB) >> 21;
        long j73 = j41 + j72;
        long j74 = j37 - (j72 << 21);
        long j75 = (j40 + FileUtils.ONE_MB) >> 21;
        long j76 = j44 + j75;
        long j77 = j40 - (j75 << 21);
        long j78 = (j43 + FileUtils.ONE_MB) >> 21;
        long j79 = j47 + j78;
        long j80 = j43 - (j78 << 21);
        long j81 = (j46 + FileUtils.ONE_MB) >> 21;
        long j82 = j50 + j81;
        long j83 = j46 - (j81 << 21);
        long j84 = (j49 + FileUtils.ONE_MB) >> 21;
        long j85 = j53 + j84;
        long j86 = j49 - (j84 << 21);
        long j87 = (j52 + FileUtils.ONE_MB) >> 21;
        long j88 = j56 + j87;
        long j89 = j52 - (j87 << 21);
        long j90 = j79 - (j55 * 683901);
        long j91 = ((j76 - (j55 * 997805)) + (j88 * 136657)) - (j89 * 683901);
        long j92 = ((((j73 + (j55 * 470296)) + (j88 * 654183)) - (j89 * 997805)) + (j85 * 136657)) - (j86 * 683901);
        long j93 = j64 + (j82 * 666643);
        long j94 = j68 + (j86 * 666643) + (j82 * 470296);
        long j95 = j67 + (j85 * 666643) + (j86 * 470296) + (j82 * 654183);
        long j96 = (((j71 + (j89 * 666643)) + (j85 * 470296)) + (j86 * 654183)) - (j82 * 997805);
        long j97 = ((((j70 + (j88 * 666643)) + (j89 * 470296)) + (j85 * 654183)) - (j86 * 997805)) + (j82 * 136657);
        long j98 = (((((j74 + (j55 * 666643)) + (j88 * 470296)) + (j89 * 654183)) - (j85 * 997805)) + (j86 * 136657)) - (j82 * 683901);
        long j99 = (j93 + FileUtils.ONE_MB) >> 21;
        long j100 = j94 + j99;
        long j101 = j93 - (j99 << 21);
        long j102 = (j95 + FileUtils.ONE_MB) >> 21;
        long j103 = j96 + j102;
        long j104 = j95 - (j102 << 21);
        long j105 = (j97 + FileUtils.ONE_MB) >> 21;
        long j106 = j98 + j105;
        long j107 = j97 - (j105 << 21);
        long j108 = (j92 + FileUtils.ONE_MB) >> 21;
        long j109 = ((((j77 + (j55 * 654183)) - (j88 * 997805)) + (j89 * 136657)) - (j85 * 683901)) + j108;
        long j110 = j92 - (j108 << 21);
        long j111 = (j91 + FileUtils.ONE_MB) >> 21;
        long j112 = ((j80 + (j55 * 136657)) - (j88 * 683901)) + j111;
        long j113 = j91 - (j111 << 21);
        long j114 = (j90 + FileUtils.ONE_MB) >> 21;
        long j115 = j83 + j114;
        long j116 = j90 - (j114 << 21);
        long j117 = (j100 + FileUtils.ONE_MB) >> 21;
        long j118 = j104 + j117;
        long j119 = j100 - (j117 << 21);
        long j120 = (j103 + FileUtils.ONE_MB) >> 21;
        long j121 = j107 + j120;
        long j122 = j103 - (j120 << 21);
        long j123 = (j106 + FileUtils.ONE_MB) >> 21;
        long j124 = j110 + j123;
        long j125 = j106 - (j123 << 21);
        long j126 = (j109 + FileUtils.ONE_MB) >> 21;
        long j127 = j113 + j126;
        long j128 = j109 - (j126 << 21);
        long j129 = (j112 + FileUtils.ONE_MB) >> 21;
        long j130 = j116 + j129;
        long j131 = j112 - (j129 << 21);
        long j132 = j121 - (j115 * 683901);
        long j133 = ((j118 - (j115 * 997805)) + (j130 * 136657)) - (j131 * 683901);
        long j134 = ((((j101 + (j115 * 470296)) + (j130 * 654183)) - (j131 * 997805)) + (j127 * 136657)) - (j128 * 683901);
        long j135 = j23 + (j124 * 666643);
        long j136 = j59 + (j128 * 666643) + (j124 * 470296);
        long j137 = j58 + (j127 * 666643) + (j128 * 470296) + (j124 * 654183);
        long j138 = (((j62 + (j131 * 666643)) + (j127 * 470296)) + (j128 * 654183)) - (j124 * 997805);
        long j139 = ((((j61 + (j130 * 666643)) + (j131 * 470296)) + (j127 * 654183)) - (j128 * 997805)) + (j124 * 136657);
        long j140 = (((((j65 + (j115 * 666643)) + (j130 * 470296)) + (j131 * 654183)) - (j127 * 997805)) + (j128 * 136657)) - (j124 * 683901);
        long j141 = (j135 + FileUtils.ONE_MB) >> 21;
        long j142 = j136 + j141;
        long j143 = j135 - (j141 << 21);
        long j144 = (j137 + FileUtils.ONE_MB) >> 21;
        long j145 = j138 + j144;
        long j146 = j137 - (j144 << 21);
        long j147 = (j139 + FileUtils.ONE_MB) >> 21;
        long j148 = j140 + j147;
        long j149 = j139 - (j147 << 21);
        long j150 = (j134 + FileUtils.ONE_MB) >> 21;
        long j151 = ((((j119 + (j115 * 654183)) - (j130 * 997805)) + (j131 * 136657)) - (j127 * 683901)) + j150;
        long j152 = j134 - (j150 << 21);
        long j153 = (j133 + FileUtils.ONE_MB) >> 21;
        long j154 = ((j122 + (j115 * 136657)) - (j130 * 683901)) + j153;
        long j155 = j133 - (j153 << 21);
        long j156 = (j132 + FileUtils.ONE_MB) >> 21;
        long j157 = j125 + j156;
        long j158 = j132 - (j156 << 21);
        long j159 = (j142 + FileUtils.ONE_MB) >> 21;
        long j160 = j146 + j159;
        long j161 = j142 - (j159 << 21);
        long j162 = (j145 + FileUtils.ONE_MB) >> 21;
        long j163 = j149 + j162;
        long j164 = j145 - (j162 << 21);
        long j165 = (j148 + FileUtils.ONE_MB) >> 21;
        long j166 = j152 + j165;
        long j167 = j148 - (j165 << 21);
        long j168 = (j151 + FileUtils.ONE_MB) >> 21;
        long j169 = j155 + j168;
        long j170 = j151 - (j168 << 21);
        long j171 = (j154 + FileUtils.ONE_MB) >> 21;
        long j172 = j158 + j171;
        long j173 = j154 - (j171 << 21);
        long j174 = (FileUtils.ONE_MB + j157) >> 21;
        long j175 = 0 + j174;
        long j176 = j143 + (j175 * 666643);
        long j177 = j161 + (j175 * 470296);
        long j178 = j160 + (j175 * 654183);
        long j179 = j164 - (j175 * 997805);
        long j180 = j163 + (j175 * 136657);
        long j181 = j167 - (j175 * 683901);
        long j182 = j176 >> 21;
        long j183 = j177 + j182;
        long j184 = j176 - (j182 << 21);
        long j185 = j183 >> 21;
        long j186 = j178 + j185;
        long j187 = j183 - (j185 << 21);
        long j188 = j186 >> 21;
        long j189 = j179 + j188;
        long j190 = j186 - (j188 << 21);
        long j191 = j189 >> 21;
        long j192 = j180 + j191;
        long j193 = j189 - (j191 << 21);
        long j194 = j192 >> 21;
        long j195 = j181 + j194;
        long j196 = j192 - (j194 << 21);
        long j197 = j195 >> 21;
        long j198 = j166 + j197;
        long j199 = j195 - (j197 << 21);
        long j200 = j198 >> 21;
        long j201 = j170 + j200;
        long j202 = j198 - (j200 << 21);
        long j203 = j201 >> 21;
        long j204 = j169 + j203;
        long j205 = j201 - (j203 << 21);
        long j206 = j204 >> 21;
        long j207 = j173 + j206;
        long j208 = j204 - (j206 << 21);
        long j209 = j207 >> 21;
        long j210 = j172 + j209;
        long j211 = j207 - (j209 << 21);
        long j212 = j210 >> 21;
        long j213 = (j157 - (j174 << 21)) + j212;
        long j214 = j210 - (j212 << 21);
        long j215 = j213 >> 21;
        long j216 = 0 + j215;
        long j217 = j213 - (j215 << 21);
        long j218 = j184 + (666643 * j216);
        long j219 = j218 >> 21;
        long j220 = j187 + (470296 * j216) + j219;
        long j221 = j220 >> 21;
        long j222 = j190 + (654183 * j216) + j221;
        long j223 = j220 - (j221 << 21);
        long j224 = j222 >> 21;
        long j225 = (j193 - (997805 * j216)) + j224;
        long j226 = j222 - (j224 << 21);
        long j227 = j225 >> 21;
        long j228 = j196 + (136657 * j216) + j227;
        long j229 = j225 - (j227 << 21);
        long j230 = j228 >> 21;
        long j231 = (j199 - (j216 * 683901)) + j230;
        long j232 = j228 - (j230 << 21);
        long j233 = j231 >> 21;
        long j234 = j202 + j233;
        long j235 = j231 - (j233 << 21);
        long j236 = j234 >> 21;
        long j237 = j205 + j236;
        long j238 = j234 - (j236 << 21);
        long j239 = j237 >> 21;
        long j240 = j208 + j239;
        long j241 = j237 - (j239 << 21);
        long j242 = j240 >> 21;
        long j243 = j211 + j242;
        long j244 = j243 >> 21;
        long j245 = j214 + j244;
        long j246 = j243 - (j244 << 21);
        long j247 = j245 >> 21;
        long j248 = j217 + j247;
        long j249 = j245 - (j247 << 21);
        s[0] = (byte) (j218 - (j219 << 21));
        s[1] = (byte) (r4 >> 8);
        s[2] = (byte) ((r4 >> 16) | (j223 << 5));
        s[3] = (byte) (j223 >> 3);
        s[4] = (byte) (j223 >> 11);
        s[5] = (byte) ((j223 >> 19) | (j226 << 2));
        s[6] = (byte) (j226 >> 6);
        s[7] = (byte) ((j226 >> 14) | (j229 << 7));
        s[8] = (byte) (j229 >> 1);
        s[9] = (byte) (j229 >> 9);
        s[10] = (byte) ((j229 >> 17) | (j232 << 4));
        s[11] = (byte) (j232 >> 4);
        s[12] = (byte) (j232 >> 12);
        s[13] = (byte) ((j232 >> 20) | (j235 << 1));
        s[14] = (byte) (j235 >> 7);
        s[15] = (byte) ((j235 >> 15) | (j238 << 6));
        s[16] = (byte) (j238 >> 2);
        s[17] = (byte) (j238 >> 10);
        s[18] = (byte) ((j238 >> 18) | (j241 << 3));
        s[19] = (byte) (j241 >> 5);
        s[20] = (byte) (j241 >> 13);
        s[21] = (byte) (j240 - (j242 << 21));
        s[22] = (byte) (r9 >> 8);
        s[23] = (byte) ((r9 >> 16) | (j246 << 5));
        s[24] = (byte) (j246 >> 3);
        s[25] = (byte) (j246 >> 11);
        s[26] = (byte) ((j246 >> 19) | (j249 << 2));
        s[27] = (byte) (j249 >> 6);
        s[28] = (byte) ((j249 >> 14) | (j248 << 7));
        s[29] = (byte) (j248 >> 1);
        s[30] = (byte) (j248 >> 9);
        s[31] = (byte) (j248 >> 17);
    }

    public static byte[] getHashedScalar(final byte[] privateKey) throws GeneralSecurityException {
        MessageDigest engineFactory = EngineFactory.MESSAGE_DIGEST.getInstance("SHA-512");
        engineFactory.update(privateKey, 0, 32);
        byte[] bArrDigest = engineFactory.digest();
        bArrDigest[0] = (byte) (bArrDigest[0] & 248);
        byte b = (byte) (bArrDigest[31] & ByteCompanionObject.MAX_VALUE);
        bArrDigest[31] = b;
        bArrDigest[31] = (byte) (b | 64);
        return bArrDigest;
    }

    public static byte[] sign(final byte[] message, final byte[] publicKey, final byte[] hashedPrivateKey) throws GeneralSecurityException {
        byte[] bArrCopyOfRange = Arrays.copyOfRange(message, 0, message.length);
        MessageDigest engineFactory = EngineFactory.MESSAGE_DIGEST.getInstance("SHA-512");
        engineFactory.update(hashedPrivateKey, 32, 32);
        engineFactory.update(bArrCopyOfRange);
        byte[] bArrDigest = engineFactory.digest();
        reduce(bArrDigest);
        byte[] bArrCopyOfRange2 = Arrays.copyOfRange(scalarMultWithBase(bArrDigest).toBytes(), 0, 32);
        engineFactory.reset();
        engineFactory.update(bArrCopyOfRange2);
        engineFactory.update(publicKey);
        engineFactory.update(bArrCopyOfRange);
        byte[] bArrDigest2 = engineFactory.digest();
        reduce(bArrDigest2);
        byte[] bArr = new byte[32];
        mulAdd(bArr, bArrDigest2, hashedPrivateKey, bArrDigest);
        return Bytes.concat(bArrCopyOfRange2, bArr);
    }

    private static boolean isSmallerThanGroupOrder(byte[] s) {
        for (int i = 31; i >= 0; i--) {
            int i2 = s[i] & UByte.MAX_VALUE;
            int i3 = GROUP_ORDER[i] & UByte.MAX_VALUE;
            if (i2 != i3) {
                return i2 < i3;
            }
        }
        return false;
    }

    public static boolean verify(final byte[] message, final byte[] signature, final byte[] publicKey) throws GeneralSecurityException {
        if (signature.length != 64) {
            return false;
        }
        byte[] bArrCopyOfRange = Arrays.copyOfRange(signature, 32, 64);
        if (!isSmallerThanGroupOrder(bArrCopyOfRange)) {
            return false;
        }
        MessageDigest engineFactory = EngineFactory.MESSAGE_DIGEST.getInstance("SHA-512");
        engineFactory.update(signature, 0, 32);
        engineFactory.update(publicKey);
        engineFactory.update(message);
        byte[] bArrDigest = engineFactory.digest();
        reduce(bArrDigest);
        byte[] bytes = doubleScalarMultVarTime(bArrDigest, XYZT.fromBytesNegateVarTime(publicKey), bArrCopyOfRange).toBytes();
        for (int i = 0; i < 32; i++) {
            if (bytes[i] != signature[i]) {
                return false;
            }
        }
        return true;
    }

    public static void init() {
        if (Ed25519Constants.D == null) {
            throw new IllegalStateException("Could not initialize Ed25519.");
        }
    }

    private Ed25519() {
    }
}
