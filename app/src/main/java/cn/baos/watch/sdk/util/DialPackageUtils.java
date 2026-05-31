package cn.baos.watch.sdk.util;

/* JADX INFO: loaded from: classes.dex */
public class DialPackageUtils {
    static int change(char c) {
        return c;
    }

    public static byte[] packageHeader() {
        return new byte[]{(byte) change('W'), (byte) change('A'), (byte) change('L'), (byte) change('L'), (byte) 100, (byte) 0, (byte) 0, (byte) 0, (byte) 17, (byte) 39, (byte) 0, (byte) 0, 0, 0, 0, 0, -16, 0, 24, 1, 1, 0, 0, 0};
    }

    public static byte[] packageLayoutMagic() {
        return new byte[]{(byte) change('W'), (byte) change('L'), (byte) change('A'), (byte) change('Y')};
    }
}
