package com.sun.mail.imap;

import com.sun.mail.iap.ByteArray;
import com.sun.mail.iap.ConnectionException;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.protocol.BODY;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.util.FolderClosedIOException;
import com.sun.mail.util.MessageRemovedIOException;
import java.io.IOException;
import java.io.InputStream;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.MessagingException;
import kotlin.UByte;

/* JADX INFO: loaded from: classes2.dex */
public class IMAPInputStream extends InputStream {
    private static final int slop = 64;
    private int blksize;
    private byte[] buf;
    private int bufcount;
    private int bufpos;
    private boolean lastBuffer;
    private int max;
    private IMAPMessage msg;
    private boolean peek;
    private int pos = 0;
    private ByteArray readbuf;
    private String section;

    public IMAPInputStream(IMAPMessage iMAPMessage, String str, int i, boolean z) {
        this.msg = iMAPMessage;
        this.section = str;
        this.max = i;
        this.peek = z;
        this.blksize = iMAPMessage.getFetchBlockSize();
    }

    private void forceCheckExpunged() throws MessageRemovedIOException, FolderClosedIOException {
        synchronized (this.msg.getMessageCacheLock()) {
            try {
                try {
                    this.msg.getProtocol().noop();
                } catch (ProtocolException unused) {
                } catch (FolderClosedException e) {
                    throw new FolderClosedIOException(e.getFolder(), e.getMessage());
                }
            } catch (ConnectionException e2) {
                throw new FolderClosedIOException(this.msg.getFolder(), e2.getMessage());
            }
        }
        if (this.msg.isExpunged()) {
            throw new MessageRemovedIOException();
        }
    }

    private void fill() throws IOException {
        int i;
        int i2;
        BODY bodyFetchBody;
        ByteArray byteArray;
        if (this.lastBuffer || ((i = this.max) != -1 && this.pos >= i)) {
            if (this.pos == 0) {
                checkSeen();
            }
            this.readbuf = null;
            return;
        }
        if (this.readbuf == null) {
            this.readbuf = new ByteArray(this.blksize + 64);
        }
        synchronized (this.msg.getMessageCacheLock()) {
            try {
                try {
                    IMAPProtocol protocol = this.msg.getProtocol();
                    if (this.msg.isExpunged()) {
                        throw new MessageRemovedIOException("No content for expunged message");
                    }
                    int sequenceNumber = this.msg.getSequenceNumber();
                    i2 = this.blksize;
                    int i3 = this.max;
                    if (i3 != -1) {
                        int i4 = this.pos;
                        if (i4 + i2 > i3) {
                            i2 = i3 - i4;
                        }
                    }
                    if (this.peek) {
                        bodyFetchBody = protocol.peekBody(sequenceNumber, this.section, this.pos, i2, this.readbuf);
                    } else {
                        bodyFetchBody = protocol.fetchBody(sequenceNumber, this.section, this.pos, i2, this.readbuf);
                    }
                    if (bodyFetchBody == null || (byteArray = bodyFetchBody.getByteArray()) == null) {
                        forceCheckExpunged();
                        byteArray = new ByteArray(0);
                    }
                } catch (FolderClosedException e) {
                    throw new FolderClosedIOException(e.getFolder(), e.getMessage());
                }
            } catch (ProtocolException e2) {
                forceCheckExpunged();
                throw new IOException(e2.getMessage());
            }
        }
        if (this.pos == 0) {
            checkSeen();
        }
        this.buf = byteArray.getBytes();
        this.bufpos = byteArray.getStart();
        int count = byteArray.getCount();
        this.lastBuffer = count < i2;
        this.bufcount = this.bufpos + count;
        this.pos += count;
    }

    @Override // java.io.InputStream
    public synchronized int read() throws IOException {
        if (this.bufpos >= this.bufcount) {
            fill();
            if (this.bufpos >= this.bufcount) {
                return -1;
            }
        }
        byte[] bArr = this.buf;
        int i = this.bufpos;
        this.bufpos = i + 1;
        return bArr[i] & UByte.MAX_VALUE;
    }

    @Override // java.io.InputStream
    public synchronized int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.bufcount - this.bufpos;
        if (i3 <= 0) {
            fill();
            i3 = this.bufcount - this.bufpos;
            if (i3 <= 0) {
                return -1;
            }
        }
        if (i3 < i2) {
            i2 = i3;
        }
        System.arraycopy(this.buf, this.bufpos, bArr, i, i2);
        this.bufpos += i2;
        return i2;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public synchronized int available() throws IOException {
        return this.bufcount - this.bufpos;
    }

    private void checkSeen() {
        if (this.peek) {
            return;
        }
        try {
            Folder folder = this.msg.getFolder();
            if (folder == null || folder.getMode() == 1 || this.msg.isSet(Flags.Flag.SEEN)) {
                return;
            }
            this.msg.setFlag(Flags.Flag.SEEN, true);
        } catch (MessagingException unused) {
        }
    }
}
