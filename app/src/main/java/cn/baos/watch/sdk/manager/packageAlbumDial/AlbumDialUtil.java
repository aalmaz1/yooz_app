package cn.baos.watch.sdk.manager.packageAlbumDial;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class AlbumDialUtil {
    private static final char[] digits = "0123456789ABCDEF".toCharArray();

    public static byte charToByte(char c) {
        return (byte) (c & 255);
    }

    public static byte[] intToBytes2(int i) {
        return new byte[]{(byte) ((i >> 24) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 8) & 255), (byte) (i & 255)};
    }

    public static byte[] packageLayoutMagic() {
        return new byte[]{charToByte('W'), charToByte('L'), charToByte('A'), charToByte('Y')};
    }

    public static byte[] packageHeader(Context context, int i, int i2, int i3) {
        byte[] bArr = new byte[24];
        bArr[0] = charToByte('W');
        bArr[1] = charToByte('A');
        bArr[2] = charToByte('L');
        bArr[3] = charToByte('L');
        bArr[4] = (byte) 100;
        bArr[5] = (byte) 0;
        bArr[6] = (byte) 0;
        bArr[7] = (byte) 0;
        bArr[8] = (byte) 17;
        bArr[9] = (byte) 39;
        bArr[10] = (byte) 0;
        bArr[11] = (byte) 0;
        bArr[12] = 0;
        bArr[13] = 0;
        bArr[14] = 0;
        bArr[15] = 0;
        if (i2 >= 256) {
            bArr[16] = (byte) Integer.parseInt(Integer.toHexString(i2 - 256), 16);
            bArr[17] = 1;
        } else {
            bArr[16] = (byte) Integer.parseInt(Integer.toHexString(i2), 16);
            bArr[17] = 0;
        }
        if (i3 >= 256) {
            bArr[18] = (byte) Integer.parseInt(Integer.toHexString(i3 - 256), 16);
            bArr[19] = 1;
        } else {
            bArr[18] = (byte) Integer.parseInt(Integer.toHexString(i3), 16);
            bArr[19] = 0;
        }
        bArr[20] = (byte) (i & 255);
        bArr[21] = 0;
        bArr[22] = 0;
        bArr[23] = 0;
        return bArr;
    }

    public static byte[] hexStringToBytes(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        char[] charArray = str.toCharArray();
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = i2 + 1;
            bArr[i] = (byte) ((toByte(charArray[i2]) << 4) | toByte(charArray[i3]));
            i++;
            i2 = i3 + 1;
        }
        return bArr;
    }

    private static int toByte(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        char c2 = 'A';
        if (c < 'A' || c > 'F') {
            c2 = 'a';
            if (c < 'a' || c > 'f') {
                throw new RuntimeException("invalid hex char '" + c + "'");
            }
        }
        return (c - c2) + 10;
    }

    public static String bytesToHexString(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        int i = 0;
        for (byte b : bArr) {
            int i2 = i + 1;
            char[] cArr2 = digits;
            cArr[i] = cArr2[(b >> 4) & 15];
            i = i2 + 1;
            cArr[i2] = cArr2[b & 15];
        }
        return new String(cArr);
    }
}
