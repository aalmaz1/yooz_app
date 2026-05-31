package cn.baos.watch.sdk.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import kotlin.UByte;

/* JADX INFO: loaded from: classes.dex */
public class EncryptionUtils {
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        byte[] bArr = new byte[1024];
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            FileInputStream fileInputStream = new FileInputStream(file);
            while (true) {
                int i = fileInputStream.read(bArr, 0, 1024);
                if (i != -1) {
                    messageDigest.update(bArr, 0, i);
                } else {
                    fileInputStream.close();
                    return bytesToHexString(messageDigest.digest());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFileMD5ArrayCache(ArrayList<byte[]> arrayList) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            for (int i = 0; i < arrayList.size(); i++) {
                messageDigest.update(arrayList.get(i), 0, arrayList.get(i).length);
            }
            return bytesToHexString(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFileMD5Byte(byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bArr, 0, bArr.length);
            return bytesToHexString(messageDigest.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] getFileMD5ByteReturnByteArray(byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bArr, 0, bArr.length);
            return messageDigest.digest();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder("");
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & UByte.MAX_VALUE);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    public static int Make_CRC16(String str) {
        byte[] bytes = str.getBytes();
        int length = bytes.length;
        byte[] bArr = new byte[length];
        for (int i = 0; i < bytes.length; i++) {
            bArr[i] = bytes[i];
        }
        int i2 = 65535;
        for (int i3 = 0; i3 < length; i3++) {
            int i4 = bArr[i3];
            if (i4 < 0) {
                i4 *= 256;
            }
            i2 ^= i4;
            for (int i5 = 8; i5 != 0; i5--) {
                i2 = (i2 & 1) != 0 ? (i2 >> 1) ^ 40961 : i2 >> 1;
            }
        }
        return i2;
    }
}
