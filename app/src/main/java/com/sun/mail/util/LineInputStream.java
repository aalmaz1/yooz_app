package com.sun.mail.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes2.dex */
public class LineInputStream extends FilterInputStream {
    private static int MAX_INCR = 1048576;
    private boolean allowutf8;
    private byte[] lineBuffer;

    public LineInputStream(InputStream inputStream) {
        this(inputStream, false);
    }

    public LineInputStream(InputStream inputStream, boolean z) {
        super(inputStream);
        this.lineBuffer = null;
        this.allowutf8 = z;
    }

    public String readLine() throws IOException {
        int i;
        byte[] bArr = this.lineBuffer;
        if (bArr == null) {
            bArr = new byte[128];
            this.lineBuffer = bArr;
        }
        int length = bArr.length;
        int i2 = 0;
        while (true) {
            i = this.in.read();
            if (i == -1 || i == 10) {
                break;
            }
            boolean z = true;
            if (i == 13) {
                if (this.in.markSupported()) {
                    this.in.mark(2);
                }
                int i3 = this.in.read();
                if (i3 == 13) {
                    i3 = this.in.read();
                } else {
                    z = false;
                }
                if (i3 != 10) {
                    if (this.in.markSupported()) {
                        this.in.reset();
                    } else {
                        if (!(this.in instanceof PushbackInputStream)) {
                            this.in = new PushbackInputStream(this.in, 2);
                        }
                        if (i3 != -1) {
                            ((PushbackInputStream) this.in).unread(i3);
                        }
                        if (z) {
                            ((PushbackInputStream) this.in).unread(13);
                        }
                    }
                }
            } else {
                length--;
                if (length < 0) {
                    int length2 = bArr.length;
                    int i4 = MAX_INCR;
                    if (length2 < i4) {
                        bArr = new byte[bArr.length * 2];
                    } else {
                        bArr = new byte[bArr.length + i4];
                    }
                    length = (bArr.length - i2) - 1;
                    System.arraycopy(this.lineBuffer, 0, bArr, 0, i2);
                    this.lineBuffer = bArr;
                }
                bArr[i2] = (byte) i;
                i2++;
            }
        }
        if (i == -1 && i2 == 0) {
            return null;
        }
        if (this.allowutf8) {
            return new String(bArr, 0, i2, StandardCharsets.UTF_8);
        }
        return new String(bArr, 0, 0, i2);
    }
}
