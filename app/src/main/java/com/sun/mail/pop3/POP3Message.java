package com.sun.mail.pop3;

import com.sun.mail.util.ReadableMime;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.util.Enumeration;
import java.util.logging.Level;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.Header;
import javax.mail.IllegalWriteException;
import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.SharedInputStream;

/* JADX INFO: loaded from: classes2.dex */
public class POP3Message extends MimeMessage implements ReadableMime {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final String UNKNOWN = "UNKNOWN";
    private POP3Folder folder;
    private int hdrSize;
    private int msgSize;
    private SoftReference<InputStream> rawData;
    String uid;

    public POP3Message(Folder folder, int i) throws MessagingException {
        super(folder, i);
        this.hdrSize = -1;
        this.msgSize = -1;
        this.uid = UNKNOWN;
        this.rawData = new SoftReference<>(null);
        this.folder = (POP3Folder) folder;
    }

    @Override // javax.mail.internet.MimeMessage, javax.mail.Message
    public synchronized void setFlags(Flags flags, boolean z) throws MessagingException {
        Flags flags2 = (Flags) this.flags.clone();
        super.setFlags(flags, z);
        if (!this.flags.equals(flags2)) {
            this.folder.notifyMessageChangedListeners(1, this);
        }
    }

    @Override // javax.mail.internet.MimeMessage, javax.mail.Part
    public int getSize() throws MessagingException {
        int i;
        try {
            synchronized (this) {
                int i2 = this.msgSize;
                if (i2 > 0) {
                    return i2;
                }
                if (this.headers == null) {
                    loadHeaders();
                }
                synchronized (this) {
                    if (this.msgSize < 0) {
                        this.msgSize = this.folder.getProtocol().list(this.msgnum) - this.hdrSize;
                    }
                    i = this.msgSize;
                }
                return i;
            }
        } catch (EOFException e) {
            this.folder.close(false);
            throw new FolderClosedException(this.folder, e.toString());
        } catch (IOException e2) {
            throw new MessagingException("error getting size", e2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private InputStream getRawStream(boolean z) throws MessagingException {
        Object obj;
        InputStream inputStreamRetr;
        int i;
        try {
            synchronized (this) {
                Object obj2 = (InputStream) this.rawData.get();
                obj = obj2;
                if (obj2 == null) {
                    TempFile fileCache = this.folder.getFileCache();
                    if (fileCache != null) {
                        if (this.folder.logger.isLoggable(Level.FINE)) {
                            this.folder.logger.fine("caching message #" + this.msgnum + " in temp file");
                        }
                        AppendStream appendStream = fileCache.getAppendStream();
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(appendStream);
                        try {
                            this.folder.getProtocol().retr(this.msgnum, bufferedOutputStream);
                            bufferedOutputStream.close();
                            inputStreamRetr = appendStream.getInputStream();
                        } catch (Throwable th) {
                            bufferedOutputStream.close();
                            throw th;
                        }
                    } else {
                        Protocol protocol = this.folder.getProtocol();
                        int i2 = this.msgnum;
                        int i3 = this.msgSize;
                        inputStreamRetr = protocol.retr(i2, i3 > 0 ? i3 + this.hdrSize : 0);
                    }
                    InputStream inputStream = inputStreamRetr;
                    if (inputStream == 0) {
                        this.expunged = true;
                        throw new MessageRemovedException("can't retrieve message #" + this.msgnum + " in POP3Message.getContentStream");
                    }
                    if (this.headers != null) {
                        POP3Store pOP3Store = (POP3Store) this.folder.getStore();
                        if (pOP3Store.forgetTopHeaders) {
                            this.headers = new InternetHeaders(inputStream);
                            this.hdrSize = (int) ((SharedInputStream) inputStream).getPosition();
                            this.msgSize = inputStream.available();
                            this.rawData = new SoftReference<>(inputStream);
                            obj = inputStream;
                        } else {
                            do {
                                i = 0;
                                while (true) {
                                    int i4 = inputStream.read();
                                    if (i4 < 0 || i4 == 10) {
                                        break;
                                    }
                                    if (i4 != 13) {
                                        i++;
                                    } else if (inputStream.available() > 0) {
                                        inputStream.mark(1);
                                        if (inputStream.read() != 10) {
                                            inputStream.reset();
                                        }
                                    }
                                }
                                if (inputStream.available() == 0) {
                                    break;
                                }
                            } while (i != 0);
                            this.hdrSize = (int) ((SharedInputStream) inputStream).getPosition();
                            this.msgSize = inputStream.available();
                            this.rawData = new SoftReference<>(inputStream);
                            obj = inputStream;
                        }
                    } else {
                        this.headers = new InternetHeaders(inputStream);
                        this.hdrSize = (int) ((SharedInputStream) inputStream).getPosition();
                        this.msgSize = inputStream.available();
                        this.rawData = new SoftReference<>(inputStream);
                        obj = inputStream;
                    }
                }
            }
            return ((SharedInputStream) obj).newStream(z ? this.hdrSize : 0L, -1L);
        } catch (EOFException e) {
            this.folder.close(false);
            throw new FolderClosedException(this.folder, e.toString());
        } catch (IOException e2) {
            throw new MessagingException("error fetching POP3 content", e2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:13:0x002f A[Catch: all -> 0x003a, TRY_LEAVE, TryCatch #0 {, blocks: (B:3:0x0001, B:5:0x0009, B:9:0x0014, B:11:0x0020, B:13:0x002f), top: B:19:0x0001 }] */
    @Override // javax.mail.internet.MimeMessage
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected synchronized java.io.InputStream getContentStream() throws javax.mail.MessagingException {
        /*
            r7 = this;
            monitor-enter(r7)
            java.io.InputStream r0 = r7.contentStream     // Catch: java.lang.Throwable -> L3a
            r1 = -1
            r3 = 0
            if (r0 == 0) goto L13
            java.io.InputStream r0 = r7.contentStream     // Catch: java.lang.Throwable -> L3a
            javax.mail.internet.SharedInputStream r0 = (javax.mail.internet.SharedInputStream) r0     // Catch: java.lang.Throwable -> L3a
            java.io.InputStream r0 = r0.newStream(r3, r1)     // Catch: java.lang.Throwable -> L3a
            monitor-exit(r7)
            return r0
        L13:
            r0 = 1
            java.io.InputStream r0 = r7.getRawStream(r0)     // Catch: java.lang.Throwable -> L3a
            com.sun.mail.pop3.POP3Folder r5 = r7.folder     // Catch: java.lang.Throwable -> L3a
            com.sun.mail.pop3.TempFile r5 = r5.getFileCache()     // Catch: java.lang.Throwable -> L3a
            if (r5 != 0) goto L2f
            com.sun.mail.pop3.POP3Folder r5 = r7.folder     // Catch: java.lang.Throwable -> L3a
            javax.mail.Store r5 = r5.getStore()     // Catch: java.lang.Throwable -> L3a
            com.sun.mail.pop3.POP3Store r5 = (com.sun.mail.pop3.POP3Store) r5     // Catch: java.lang.Throwable -> L3a
            r6 = r5
            com.sun.mail.pop3.POP3Store r6 = (com.sun.mail.pop3.POP3Store) r6     // Catch: java.lang.Throwable -> L3a
            boolean r5 = r5.keepMessageContent     // Catch: java.lang.Throwable -> L3a
            if (r5 == 0) goto L38
        L2f:
            r5 = r0
            javax.mail.internet.SharedInputStream r5 = (javax.mail.internet.SharedInputStream) r5     // Catch: java.lang.Throwable -> L3a
            java.io.InputStream r1 = r5.newStream(r3, r1)     // Catch: java.lang.Throwable -> L3a
            r7.contentStream = r1     // Catch: java.lang.Throwable -> L3a
        L38:
            monitor-exit(r7)
            return r0
        L3a:
            r0 = move-exception
            monitor-exit(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sun.mail.pop3.POP3Message.getContentStream():java.io.InputStream");
    }

    @Override // com.sun.mail.util.ReadableMime
    public InputStream getMimeStream() throws MessagingException {
        return getRawStream(false);
    }

    public synchronized void invalidate(boolean z) {
        this.content = null;
        InputStream inputStream = this.rawData.get();
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
            this.rawData = new SoftReference<>(null);
        }
        if (this.contentStream != null) {
            try {
                this.contentStream.close();
            } catch (IOException unused2) {
            }
            this.contentStream = null;
        }
        this.msgSize = -1;
        if (z) {
            this.headers = null;
            this.hdrSize = -1;
        }
    }

    public InputStream top(int i) throws MessagingException {
        InputStream pVar;
        try {
            synchronized (this) {
                pVar = this.folder.getProtocol().top(this.msgnum, i);
            }
            return pVar;
        } catch (EOFException e) {
            this.folder.close(false);
            throw new FolderClosedException(this.folder, e.toString());
        } catch (IOException e2) {
            throw new MessagingException("error getting size", e2);
        }
    }

    @Override // javax.mail.internet.MimeMessage, javax.mail.Part
    public String[] getHeader(String str) throws MessagingException {
        if (this.headers == null) {
            loadHeaders();
        }
        return this.headers.getHeader(str);
    }

    @Override // javax.mail.internet.MimeMessage, javax.mail.internet.MimePart
    public String getHeader(String str, String str2) throws MessagingException {
        if (this.headers == null) {
            loadHeaders();
        }
        return this.headers.getHeader(str, str2);
    }

    @Override // javax.mail.internet.MimeMessage, javax.mail.Part
    public void setHeader(String str, String str2) throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    @Override // javax.mail.internet.MimeMessage, javax.mail.Part
    public void addHeader(String str, String str2) throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    @Override // javax.mail.internet.MimeMessage, javax.mail.Part
    public void removeHeader(String str) throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    @Override // javax.mail.internet.MimeMessage, javax.mail.Part
    public Enumeration<Header> getAllHeaders() throws MessagingException {
        if (this.headers == null) {
            loadHeaders();
        }
        return this.headers.getAllHeaders();
    }

    @Override // javax.mail.internet.MimeMessage, javax.mail.Part
    public Enumeration<Header> getMatchingHeaders(String[] strArr) throws MessagingException {
        if (this.headers == null) {
            loadHeaders();
        }
        return this.headers.getMatchingHeaders(strArr);
    }

    @Override // javax.mail.internet.MimeMessage, javax.mail.Part
    public Enumeration<Header> getNonMatchingHeaders(String[] strArr) throws MessagingException {
        if (this.headers == null) {
            loadHeaders();
        }
        return this.headers.getNonMatchingHeaders(strArr);
    }

    @Override // javax.mail.internet.MimeMessage, javax.mail.internet.MimePart
    public void addHeaderLine(String str) throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    @Override // javax.mail.internet.MimeMessage, javax.mail.internet.MimePart
    public Enumeration<String> getAllHeaderLines() throws MessagingException {
        if (this.headers == null) {
            loadHeaders();
        }
        return this.headers.getAllHeaderLines();
    }

    @Override // javax.mail.internet.MimeMessage, javax.mail.internet.MimePart
    public Enumeration<String> getMatchingHeaderLines(String[] strArr) throws MessagingException {
        if (this.headers == null) {
            loadHeaders();
        }
        return this.headers.getMatchingHeaderLines(strArr);
    }

    @Override // javax.mail.internet.MimeMessage, javax.mail.internet.MimePart
    public Enumeration<String> getNonMatchingHeaderLines(String[] strArr) throws MessagingException {
        if (this.headers == null) {
            loadHeaders();
        }
        return this.headers.getNonMatchingHeaderLines(strArr);
    }

    @Override // javax.mail.internet.MimeMessage, javax.mail.Message
    public void saveChanges() throws MessagingException {
        throw new IllegalWriteException("POP3 messages are read-only");
    }

    @Override // javax.mail.internet.MimeMessage
    public synchronized void writeTo(OutputStream outputStream, String[] strArr) throws MessagingException, IOException {
        Closeable closeable = (InputStream) this.rawData.get();
        if (closeable == null && strArr == null) {
            POP3Store pOP3Store = (POP3Store) this.folder.getStore();
            if (!pOP3Store.cacheWriteTo) {
                if (this.folder.logger.isLoggable(Level.FINE)) {
                    this.folder.logger.fine("streaming msg " + this.msgnum);
                }
                if (!this.folder.getProtocol().retr(this.msgnum, outputStream)) {
                    this.expunged = true;
                    throw new MessageRemovedException("can't retrieve message #" + this.msgnum + " in POP3Message.writeTo");
                }
            }
        }
        if (closeable != null && strArr == null) {
            InputStream inputStreamNewStream = ((SharedInputStream) closeable).newStream(0L, -1L);
            try {
                byte[] bArr = new byte[16384];
                while (true) {
                    int i = inputStreamNewStream.read(bArr);
                    if (i <= 0) {
                        break;
                    } else {
                        outputStream.write(bArr, 0, i);
                    }
                }
                if (inputStreamNewStream != null) {
                    try {
                        inputStreamNewStream.close();
                    } catch (IOException unused) {
                    }
                }
            } catch (Throwable th) {
                if (inputStreamNewStream != null) {
                    try {
                        inputStreamNewStream.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        } else {
            super.writeTo(outputStream, strArr);
        }
    }

    private void loadHeaders() throws MessagingException {
        boolean z;
        InputStream pVar;
        try {
            synchronized (this) {
                if (this.headers != null) {
                    return;
                }
                POP3Store pOP3Store = (POP3Store) this.folder.getStore();
                if (pOP3Store.disableTop || (pVar = this.folder.getProtocol().top(this.msgnum, 0)) == null) {
                    z = true;
                } else {
                    try {
                        this.hdrSize = pVar.available();
                        this.headers = new InternetHeaders(pVar);
                        pVar.close();
                        z = false;
                    } catch (Throwable th) {
                        pVar.close();
                        throw th;
                    }
                }
                if (z) {
                    InputStream contentStream = getContentStream();
                    if (contentStream != null) {
                        contentStream.close();
                    }
                }
            }
        } catch (EOFException e) {
            this.folder.close(false);
            throw new FolderClosedException(this.folder, e.toString());
        } catch (IOException e2) {
            throw new MessagingException("error loading POP3 headers", e2);
        }
    }
}
