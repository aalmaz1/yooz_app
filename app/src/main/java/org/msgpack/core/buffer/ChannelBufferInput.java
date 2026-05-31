package org.msgpack.core.buffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import org.msgpack.core.Preconditions;

/* JADX INFO: loaded from: classes3.dex */
public class ChannelBufferInput implements MessageBufferInput {
    private final MessageBuffer buffer;
    private ReadableByteChannel channel;

    public ChannelBufferInput(ReadableByteChannel readableByteChannel) {
        this(readableByteChannel, 8192);
    }

    public ChannelBufferInput(ReadableByteChannel readableByteChannel, int i) {
        this.channel = (ReadableByteChannel) Preconditions.checkNotNull(readableByteChannel, "input channel is null");
        Preconditions.checkArgument(i > 0, "buffer size must be > 0: " + i);
        this.buffer = MessageBuffer.allocate(i);
    }

    public ReadableByteChannel reset(ReadableByteChannel readableByteChannel) throws IOException {
        ReadableByteChannel readableByteChannel2 = this.channel;
        this.channel = readableByteChannel;
        return readableByteChannel2;
    }

    @Override // org.msgpack.core.buffer.MessageBufferInput
    public MessageBuffer next() throws IOException {
        ByteBuffer byteBufferSliceAsByteBuffer = this.buffer.sliceAsByteBuffer();
        if (this.channel.read(byteBufferSliceAsByteBuffer) == -1) {
            return null;
        }
        byteBufferSliceAsByteBuffer.flip();
        return this.buffer.slice(0, byteBufferSliceAsByteBuffer.limit());
    }

    @Override // org.msgpack.core.buffer.MessageBufferInput, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.channel.close();
    }
}
