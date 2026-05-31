package com.sun.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes2.dex */
public class LineOutputStream extends FilterOutputStream {
    private static byte[] newline = {13, 10};
    private boolean allowutf8;

    public LineOutputStream(OutputStream outputStream) {
        this(outputStream, false);
    }

    public LineOutputStream(OutputStream outputStream, boolean z) {
        super(outputStream);
        this.allowutf8 = z;
    }

    public void writeln(String str) throws IOException {
        byte[] bytes;
        if (this.allowutf8) {
            bytes = str.getBytes(StandardCharsets.UTF_8);
        } else {
            bytes = ASCIIUtility.getBytes(str);
        }
        this.out.write(bytes);
        this.out.write(newline);
    }

    public void writeln() throws IOException {
        this.out.write(newline);
    }
}
