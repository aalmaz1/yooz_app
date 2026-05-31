package com.sun.mail.util;

import cn.baos.watch.sdk.entitiy.Constant;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import kotlin.UByte;

/* JADX INFO: loaded from: classes2.dex */
public class UUDecoderStream extends FilterInputStream {
    private byte[] buffer;
    private int bufsize;
    private boolean gotEnd;
    private boolean gotPrefix;
    private boolean ignoreErrors;
    private boolean ignoreMissingBeginEnd;
    private int index;
    private LineInputStream lin;
    private int mode;
    private String name;
    private String readAhead;

    @Override // java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return false;
    }

    public UUDecoderStream(InputStream inputStream) {
        super(inputStream);
        this.buffer = new byte[45];
        this.bufsize = 0;
        this.index = 0;
        this.gotPrefix = false;
        this.gotEnd = false;
        this.lin = new LineInputStream(inputStream);
        this.ignoreErrors = PropUtil.getBooleanSystemProperty("mail.mime.uudecode.ignoreerrors", false);
        this.ignoreMissingBeginEnd = PropUtil.getBooleanSystemProperty("mail.mime.uudecode.ignoremissingbeginend", false);
    }

    public UUDecoderStream(InputStream inputStream, boolean z, boolean z2) {
        super(inputStream);
        this.buffer = new byte[45];
        this.bufsize = 0;
        this.index = 0;
        this.gotPrefix = false;
        this.gotEnd = false;
        this.lin = new LineInputStream(inputStream);
        this.ignoreErrors = z;
        this.ignoreMissingBeginEnd = z2;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        if (this.index >= this.bufsize) {
            readPrefix();
            if (!decode()) {
                return -1;
            }
            this.index = 0;
        }
        byte[] bArr = this.buffer;
        int i = this.index;
        this.index = i + 1;
        return bArr[i] & UByte.MAX_VALUE;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        while (i3 < i2) {
            int i4 = read();
            if (i4 == -1) {
                if (i3 == 0) {
                    return -1;
                }
                return i3;
            }
            bArr[i + i3] = (byte) i4;
            i3++;
        }
        return i3;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        return ((this.in.available() * 3) / 4) + (this.bufsize - this.index);
    }

    public String getName() throws IOException {
        readPrefix();
        return this.name;
    }

    public int getMode() throws IOException {
        readPrefix();
        return this.mode;
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ae, code lost:
    
        r8.readAhead = r0;
        r8.gotPrefix = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00b2, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void readPrefix() throws java.io.IOException {
        /*
            r8 = this;
            boolean r0 = r8.gotPrefix
            if (r0 == 0) goto L5
            return
        L5:
            r0 = 438(0x1b6, float:6.14E-43)
            r8.mode = r0
            java.lang.String r0 = "encoder.buf"
            r8.name = r0
        Ld:
            com.sun.mail.util.LineInputStream r0 = r8.lin
            java.lang.String r0 = r0.readLine()
            r7 = 1
            if (r0 != 0) goto L28
            boolean r0 = r8.ignoreMissingBeginEnd
            if (r0 == 0) goto L20
            r8.gotPrefix = r7
            r8.gotEnd = r7
            goto Lb2
        L20:
            com.sun.mail.util.DecodingException r0 = new com.sun.mail.util.DecodingException
            java.lang.String r1 = "UUDecoder: Missing begin"
            r0.<init>(r1)
            throw r0
        L28:
            r2 = 0
            r3 = 0
            java.lang.String r4 = "begin"
            r5 = 0
            r6 = 5
            r1 = r0
            boolean r1 = r1.regionMatches(r2, r3, r4, r5, r6)
            r2 = 6
            if (r1 == 0) goto L8c
            r1 = 9
            java.lang.String r1 = r0.substring(r2, r1)     // Catch: java.lang.NumberFormatException -> L43
            int r1 = java.lang.Integer.parseInt(r1)     // Catch: java.lang.NumberFormatException -> L43
            r8.mode = r1     // Catch: java.lang.NumberFormatException -> L43
            goto L48
        L43:
            r1 = move-exception
            boolean r2 = r8.ignoreErrors
            if (r2 == 0) goto L73
        L48:
            int r1 = r0.length()
            r2 = 10
            if (r1 <= r2) goto L57
            java.lang.String r0 = r0.substring(r2)
            r8.name = r0
            goto L5b
        L57:
            boolean r1 = r8.ignoreErrors
            if (r1 == 0) goto L5e
        L5b:
            r8.gotPrefix = r7
            goto Lb2
        L5e:
            com.sun.mail.util.DecodingException r1 = new com.sun.mail.util.DecodingException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "UUDecoder: Missing name: "
            r2.<init>(r3)
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            throw r1
        L73:
            com.sun.mail.util.DecodingException r0 = new com.sun.mail.util.DecodingException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "UUDecoder: Error in mode: "
            r2.<init>(r3)
            java.lang.String r1 = r1.toString()
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L8c:
            boolean r1 = r8.ignoreMissingBeginEnd
            if (r1 == 0) goto Ld
            int r1 = r0.length()
            if (r1 == 0) goto Ld
            r1 = 0
            char r1 = r0.charAt(r1)
            int r1 = r1 + (-32)
            r1 = r1 & 63
            int r1 = r1 * 8
            int r1 = r1 + 5
            int r1 = r1 / r2
            if (r1 == 0) goto Lae
            int r2 = r0.length()
            int r1 = r1 + 1
            if (r2 < r1) goto Ld
        Lae:
            r8.readAhead = r0
            r8.gotPrefix = r7
        Lb2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.mail.util.UUDecoderStream.readPrefix():void");
    }

    private boolean decode() throws IOException {
        if (this.gotEnd) {
            return false;
        }
        this.bufsize = 0;
        while (true) {
            String line = this.readAhead;
            if (line != null) {
                this.readAhead = null;
            } else {
                line = this.lin.readLine();
            }
            if (line == null) {
                if (!this.ignoreMissingBeginEnd) {
                    throw new DecodingException("UUDecoder: Missing end at EOF");
                }
                this.gotEnd = true;
                return false;
            }
            if (line.equals("end")) {
                this.gotEnd = true;
                return false;
            }
            if (line.length() != 0) {
                char cCharAt = line.charAt(0);
                if (cCharAt >= ' ') {
                    int i = (cCharAt - ' ') & 63;
                    if (i == 0) {
                        String line2 = this.lin.readLine();
                        if ((line2 == null || !line2.equals("end")) && !this.ignoreMissingBeginEnd) {
                            throw new DecodingException("UUDecoder: Missing End after count 0 line");
                        }
                        this.gotEnd = true;
                        return false;
                    }
                    if (line.length() < (((i * 8) + 5) / 6) + 1) {
                        if (!this.ignoreErrors) {
                            throw new DecodingException("UUDecoder: Short buffer error");
                        }
                    } else {
                        int i2 = 1;
                        while (this.bufsize < i) {
                            int i3 = i2 + 1;
                            byte bCharAt = (byte) ((line.charAt(i2) - ' ') & 63);
                            int i4 = i3 + 1;
                            byte bCharAt2 = (byte) ((line.charAt(i3) - ' ') & 63);
                            byte[] bArr = this.buffer;
                            int i5 = this.bufsize;
                            int i6 = i5 + 1;
                            this.bufsize = i6;
                            bArr[i5] = (byte) (((bCharAt << 2) & Constant.MESSAGE_ID_OTA_LIST_REQUEST) | ((bCharAt2 >>> 4) & 3));
                            if (i6 < i) {
                                i2 = i4 + 1;
                                byte bCharAt3 = (byte) ((line.charAt(i4) - ' ') & 63);
                                byte[] bArr2 = this.buffer;
                                int i7 = this.bufsize;
                                this.bufsize = i7 + 1;
                                bArr2[i7] = (byte) (((bCharAt2 << 4) & 240) | ((bCharAt3 >>> 2) & 15));
                                bCharAt2 = bCharAt3;
                            } else {
                                i2 = i4;
                            }
                            if (this.bufsize < i) {
                                int i8 = i2 + 1;
                                byte bCharAt4 = (byte) ((line.charAt(i2) - ' ') & 63);
                                byte[] bArr3 = this.buffer;
                                int i9 = this.bufsize;
                                this.bufsize = i9 + 1;
                                bArr3[i9] = (byte) ((bCharAt4 & 63) | ((bCharAt2 << 6) & Constant.MESSAGE_ID_INIT_NFC));
                                i2 = i8;
                            }
                        }
                        return true;
                    }
                } else if (!this.ignoreErrors) {
                    throw new DecodingException("UUDecoder: Buffer format error");
                }
            }
        }
    }
}
