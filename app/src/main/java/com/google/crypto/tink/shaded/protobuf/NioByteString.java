package com.google.crypto.tink.shaded.protobuf;

import com.google.crypto.tink.shaded.protobuf.ByteString;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.InvalidMarkException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import kotlin.UByte;

/* JADX INFO: loaded from: classes2.dex */
final class NioByteString extends ByteString.LeafByteString {
    private final ByteBuffer buffer;

    NioByteString(ByteBuffer buffer) {
        Internal.checkNotNull(buffer, "buffer");
        this.buffer = buffer.slice().order(ByteOrder.nativeOrder());
    }

    private Object writeReplace() {
        return ByteString.copyFrom(this.buffer.slice());
    }

    private void readObject(ObjectInputStream in) throws IOException {
        throw new InvalidObjectException("NioByteString instances are not to be serialized directly");
    }

    @Override // com.google.crypto.tink.shaded.protobuf.ByteString
    public byte byteAt(int index) {
        try {
            return this.buffer.get(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        } catch (IndexOutOfBoundsException e2) {
            throw new ArrayIndexOutOfBoundsException(e2.getMessage());
        }
    }

    @Override // com.google.crypto.tink.shaded.protobuf.ByteString
    public byte internalByteAt(int index) {
        return byteAt(index);
    }

    @Override // com.google.crypto.tink.shaded.protobuf.ByteString
    public int size() {
        return this.buffer.remaining();
    }

    @Override // com.google.crypto.tink.shaded.protobuf.ByteString
    public ByteString substring(int beginIndex, int endIndex) {
        try {
            return new NioByteString(slice(beginIndex, endIndex));
        } catch (ArrayIndexOutOfBoundsException e) {
            throw e;
        } catch (IndexOutOfBoundsException e2) {
            throw new ArrayIndexOutOfBoundsException(e2.getMessage());
        }
    }

    @Override // com.google.crypto.tink.shaded.protobuf.ByteString
    protected void copyToInternal(byte[] target, int sourceOffset, int targetOffset, int numberToCopy) {
        ByteBuffer byteBufferSlice = this.buffer.slice();
        Java8Compatibility.position(byteBufferSlice, sourceOffset);
        byteBufferSlice.get(target, targetOffset, numberToCopy);
    }

    @Override // com.google.crypto.tink.shaded.protobuf.ByteString
    public void copyTo(ByteBuffer target) {
        target.put(this.buffer.slice());
    }

    @Override // com.google.crypto.tink.shaded.protobuf.ByteString
    public void writeTo(OutputStream out) throws IOException {
        out.write(toByteArray());
    }

    @Override // com.google.crypto.tink.shaded.protobuf.ByteString.LeafByteString
    boolean equalsRange(ByteString other, int offset, int length) {
        return substring(0, length).equals(other.substring(offset, length + offset));
    }

    @Override // com.google.crypto.tink.shaded.protobuf.ByteString
    void writeToInternal(OutputStream out, int sourceOffset, int numberToWrite) throws IOException {
        if (this.buffer.hasArray()) {
            out.write(this.buffer.array(), this.buffer.arrayOffset() + this.buffer.position() + sourceOffset, numberToWrite);
        } else {
            ByteBufferWriter.write(slice(sourceOffset, numberToWrite + sourceOffset), out);
        }
    }

    @Override // com.google.crypto.tink.shaded.protobuf.ByteString
    void writeTo(ByteOutput output) throws IOException {
        output.writeLazy(this.buffer.slice());
    }

    @Override // com.google.crypto.tink.shaded.protobuf.ByteString
    public ByteBuffer asReadOnlyByteBuffer() {
        return this.buffer.asReadOnlyBuffer();
    }

    @Override // com.google.crypto.tink.shaded.protobuf.ByteString
    public List<ByteBuffer> asReadOnlyByteBufferList() {
        return Collections.singletonList(asReadOnlyByteBuffer());
    }

    @Override // com.google.crypto.tink.shaded.protobuf.ByteString
    protected String toStringInternal(Charset charset) {
        byte[] byteArray;
        int length;
        int iArrayOffset;
        if (this.buffer.hasArray()) {
            byteArray = this.buffer.array();
            iArrayOffset = this.buffer.arrayOffset() + this.buffer.position();
            length = this.buffer.remaining();
        } else {
            byteArray = toByteArray();
            length = byteArray.length;
            iArrayOffset = 0;
        }
        return new String(byteArray, iArrayOffset, length, charset);
    }

    @Override // com.google.crypto.tink.shaded.protobuf.ByteString
    public boolean isValidUtf8() {
        return Utf8.isValidUtf8(this.buffer);
    }

    @Override // com.google.crypto.tink.shaded.protobuf.ByteString
    protected int partialIsValidUtf8(int state, int offset, int length) {
        return Utf8.partialIsValidUtf8(state, this.buffer, offset, length + offset);
    }

    @Override // com.google.crypto.tink.shaded.protobuf.ByteString
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ByteString)) {
            return false;
        }
        ByteString byteString = (ByteString) other;
        if (size() != byteString.size()) {
            return false;
        }
        if (size() == 0) {
            return true;
        }
        if (other instanceof NioByteString) {
            return this.buffer.equals(((NioByteString) other).buffer);
        }
        if (other instanceof RopeByteString) {
            return other.equals(this);
        }
        return this.buffer.equals(byteString.asReadOnlyByteBuffer());
    }

    @Override // com.google.crypto.tink.shaded.protobuf.ByteString
    protected int partialHash(int h, int offset, int length) {
        for (int i = offset; i < offset + length; i++) {
            h = (h * 31) + this.buffer.get(i);
        }
        return h;
    }

    @Override // com.google.crypto.tink.shaded.protobuf.ByteString
    public InputStream newInput() {
        return new InputStream() { // from class: com.google.crypto.tink.shaded.protobuf.NioByteString.1
            private final ByteBuffer buf;

            @Override // java.io.InputStream
            public boolean markSupported() {
                return true;
            }

            {
                this.buf = NioByteString.this.buffer.slice();
            }

            @Override // java.io.InputStream
            public void mark(int readlimit) {
                Java8Compatibility.mark(this.buf);
            }

            @Override // java.io.InputStream
            public void reset() throws IOException {
                try {
                    Java8Compatibility.reset(this.buf);
                } catch (InvalidMarkException e) {
                    throw new IOException(e);
                }
            }

            @Override // java.io.InputStream
            public int available() throws IOException {
                return this.buf.remaining();
            }

            @Override // java.io.InputStream
            public int read() throws IOException {
                if (this.buf.hasRemaining()) {
                    return this.buf.get() & UByte.MAX_VALUE;
                }
                return -1;
            }

            @Override // java.io.InputStream
            public int read(byte[] bytes, int off, int len) throws IOException {
                if (!this.buf.hasRemaining()) {
                    return -1;
                }
                int iMin = Math.min(len, this.buf.remaining());
                this.buf.get(bytes, off, iMin);
                return iMin;
            }
        };
    }

    @Override // com.google.crypto.tink.shaded.protobuf.ByteString
    public CodedInputStream newCodedInput() {
        return CodedInputStream.newInstance(this.buffer, true);
    }

    private ByteBuffer slice(int beginIndex, int endIndex) {
        if (beginIndex < this.buffer.position() || endIndex > this.buffer.limit() || beginIndex > endIndex) {
            throw new IllegalArgumentException(String.format("Invalid indices [%d, %d]", Integer.valueOf(beginIndex), Integer.valueOf(endIndex)));
        }
        ByteBuffer byteBufferSlice = this.buffer.slice();
        Java8Compatibility.position(byteBufferSlice, beginIndex - this.buffer.position());
        Java8Compatibility.limit(byteBufferSlice, endIndex - this.buffer.position());
        return byteBufferSlice;
    }
}
