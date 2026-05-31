package org.msgpack.core.buffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import org.msgpack.core.Preconditions;

/* JADX INFO: loaded from: classes3.dex */
public class ChannelBufferOutput implements MessageBufferOutput {
    private MessageBuffer buffer;
    private WritableByteChannel channel;

    @Override // java.io.Flushable
    public void flush() throws IOException {
    }

    public ChannelBufferOutput(WritableByteChannel writableByteChannel) {
        this(writableByteChannel, 8192);
    }

    public ChannelBufferOutput(WritableByteChannel writableByteChannel, int i) {
        this.channel = (WritableByteChannel) Preconditions.checkNotNull(writableByteChannel, "output channel is null");
        this.buffer = MessageBuffer.allocate(i);
    }

    public WritableByteChannel reset(WritableByteChannel writableByteChannel) throws IOException {
        WritableByteChannel writableByteChannel2 = this.channel;
        this.channel = writableByteChannel;
        return writableByteChannel2;
    }

    @Override // org.msgpack.core.buffer.MessageBufferOutput
    public MessageBuffer next(int i) throws IOException {
        if (this.buffer.size() < i) {
            this.buffer = MessageBuffer.allocate(i);
        }
        return this.buffer;
    }

    @Override // org.msgpack.core.buffer.MessageBufferOutput
    public void writeBuffer(int i) throws IOException {
        ByteBuffer byteBufferSliceAsByteBuffer = this.buffer.sliceAsByteBuffer(0, i);
        while (byteBufferSliceAsByteBuffer.hasRemaining()) {
            this.channel.write(byteBufferSliceAsByteBuffer);
        }
    }

    @Override // org.msgpack.core.buffer.MessageBufferOutput
    public void write(byte[] bArr, int i, int i2) throws IOException {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr, i, i2);
        while (byteBufferWrap.hasRemaining()) {
            this.channel.write(byteBufferWrap);
        }
    }

    @Override // org.msgpack.core.buffer.MessageBufferOutput
    public void add(byte[] bArr, int i, int i2) throws IOException {
        write(bArr, i, i2);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.channel.close();
    }
}
