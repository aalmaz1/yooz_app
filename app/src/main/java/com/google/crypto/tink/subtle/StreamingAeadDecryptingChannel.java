package com.google.crypto.tink.subtle;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.security.GeneralSecurityException;
import java.util.Arrays;

/* JADX INFO: loaded from: classes2.dex */
class StreamingAeadDecryptingChannel implements ReadableByteChannel {
    private static final int PLAINTEXT_SEGMENT_EXTRA_SIZE = 16;
    private final byte[] associatedData;
    private ReadableByteChannel ciphertextChannel;
    private ByteBuffer ciphertextSegment;
    private final int ciphertextSegmentSize;
    private final StreamSegmentDecrypter decrypter;
    private boolean definedState;
    private boolean endOfCiphertext;
    private boolean endOfPlaintext;
    private final int firstCiphertextSegmentSize;
    private ByteBuffer header;
    private boolean headerRead;
    private ByteBuffer plaintextSegment;
    private int segmentNr;

    public StreamingAeadDecryptingChannel(NonceBasedStreamingAead streamAead, ReadableByteChannel ciphertextChannel, byte[] associatedData) throws GeneralSecurityException, IOException {
        this.decrypter = streamAead.newStreamSegmentDecrypter();
        this.ciphertextChannel = ciphertextChannel;
        this.header = ByteBuffer.allocate(streamAead.getHeaderLength());
        this.associatedData = Arrays.copyOf(associatedData, associatedData.length);
        int ciphertextSegmentSize = streamAead.getCiphertextSegmentSize();
        this.ciphertextSegmentSize = ciphertextSegmentSize;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(ciphertextSegmentSize + 1);
        this.ciphertextSegment = byteBufferAllocate;
        byteBufferAllocate.limit(0);
        this.firstCiphertextSegmentSize = ciphertextSegmentSize - streamAead.getCiphertextOffset();
        ByteBuffer byteBufferAllocate2 = ByteBuffer.allocate(streamAead.getPlaintextSegmentSize() + 16);
        this.plaintextSegment = byteBufferAllocate2;
        byteBufferAllocate2.limit(0);
        this.headerRead = false;
        this.endOfCiphertext = false;
        this.endOfPlaintext = false;
        this.segmentNr = 0;
        this.definedState = true;
    }

    private void readSomeCiphertext(ByteBuffer buffer) throws IOException {
        int i;
        do {
            i = this.ciphertextChannel.read(buffer);
            if (i <= 0) {
                break;
            }
        } while (buffer.remaining() > 0);
        if (i == -1) {
            this.endOfCiphertext = true;
        }
    }

    private boolean tryReadHeader() throws IOException {
        if (this.endOfCiphertext) {
            throw new IOException("Ciphertext is too short");
        }
        readSomeCiphertext(this.header);
        if (this.header.remaining() > 0) {
            return false;
        }
        this.header.flip();
        try {
            this.decrypter.init(this.header, this.associatedData);
            this.headerRead = true;
            return true;
        } catch (GeneralSecurityException e) {
            setUndefinedState();
            throw new IOException(e);
        }
    }

    private void setUndefinedState() {
        this.definedState = false;
        this.plaintextSegment.limit(0);
    }

    private boolean tryLoadSegment() throws IOException {
        if (!this.endOfCiphertext) {
            readSomeCiphertext(this.ciphertextSegment);
        }
        byte b = 0;
        if (this.ciphertextSegment.remaining() > 0 && !this.endOfCiphertext) {
            return false;
        }
        if (!this.endOfCiphertext) {
            ByteBuffer byteBuffer = this.ciphertextSegment;
            b = byteBuffer.get(byteBuffer.position() - 1);
            ByteBuffer byteBuffer2 = this.ciphertextSegment;
            byteBuffer2.position(byteBuffer2.position() - 1);
        }
        this.ciphertextSegment.flip();
        this.plaintextSegment.clear();
        try {
            this.decrypter.decryptSegment(this.ciphertextSegment, this.segmentNr, this.endOfCiphertext, this.plaintextSegment);
            this.segmentNr++;
            this.plaintextSegment.flip();
            this.ciphertextSegment.clear();
            if (!this.endOfCiphertext) {
                this.ciphertextSegment.clear();
                this.ciphertextSegment.limit(this.ciphertextSegmentSize + 1);
                this.ciphertextSegment.put(b);
            }
            return true;
        } catch (GeneralSecurityException e) {
            setUndefinedState();
            throw new IOException(e.getMessage() + "\n" + toString() + "\nsegmentNr:" + this.segmentNr + " endOfCiphertext:" + this.endOfCiphertext, e);
        }
    }

    @Override // java.nio.channels.ReadableByteChannel
    public synchronized int read(ByteBuffer dst) throws IOException {
        if (!this.definedState) {
            throw new IOException("This StreamingAeadDecryptingChannel is in an undefined state");
        }
        if (!this.headerRead) {
            if (!tryReadHeader()) {
                return 0;
            }
            this.ciphertextSegment.clear();
            this.ciphertextSegment.limit(this.firstCiphertextSegmentSize + 1);
        }
        if (this.endOfPlaintext) {
            return -1;
        }
        int iPosition = dst.position();
        while (true) {
            if (dst.remaining() <= 0) {
                break;
            }
            if (this.plaintextSegment.remaining() == 0) {
                if (this.endOfCiphertext) {
                    this.endOfPlaintext = true;
                    break;
                }
                if (!tryLoadSegment()) {
                    break;
                }
            }
            if (this.plaintextSegment.remaining() <= dst.remaining()) {
                dst.put(this.plaintextSegment);
            } else {
                int iRemaining = dst.remaining();
                ByteBuffer byteBufferDuplicate = this.plaintextSegment.duplicate();
                byteBufferDuplicate.limit(byteBufferDuplicate.position() + iRemaining);
                dst.put(byteBufferDuplicate);
                ByteBuffer byteBuffer = this.plaintextSegment;
                byteBuffer.position(byteBuffer.position() + iRemaining);
            }
        }
        int iPosition2 = dst.position() - iPosition;
        if (iPosition2 == 0 && this.endOfPlaintext) {
            return -1;
        }
        return iPosition2;
    }

    @Override // java.nio.channels.Channel, java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() throws IOException {
        this.ciphertextChannel.close();
    }

    @Override // java.nio.channels.Channel
    public synchronized boolean isOpen() {
        return this.ciphertextChannel.isOpen();
    }

    public synchronized String toString() {
        StringBuilder sb;
        sb = new StringBuilder("StreamingAeadDecryptingChannel\nsegmentNr:");
        sb.append(this.segmentNr).append("\nciphertextSegmentSize:").append(this.ciphertextSegmentSize).append("\nheaderRead:").append(this.headerRead).append("\nendOfCiphertext:").append(this.endOfCiphertext).append("\nendOfPlaintext:").append(this.endOfPlaintext).append("\ndefinedState:").append(this.definedState).append("\nHeader position:").append(this.header.position()).append(" limit:").append(this.header.position()).append("\nciphertextSgement position:").append(this.ciphertextSegment.position()).append(" limit:").append(this.ciphertextSegment.limit()).append("\nplaintextSegment position:").append(this.plaintextSegment.position()).append(" limit:").append(this.plaintextSegment.limit());
        return sb.toString();
    }
}
