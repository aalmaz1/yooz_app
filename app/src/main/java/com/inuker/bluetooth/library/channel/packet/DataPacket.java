package com.inuker.bluetooth.library.channel.packet;

import com.inuker.bluetooth.library.channel.packet.Packet;
import com.inuker.bluetooth.library.utils.ByteUtils;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* JADX INFO: loaded from: classes2.dex */
public class DataPacket extends Packet {
    private Packet.Bytes bytes;
    private byte[] crc;
    private int seq;

    @Override // com.inuker.bluetooth.library.channel.packet.Packet
    public String getName() {
        return "data";
    }

    public DataPacket(int i, Packet.Bytes bytes) {
        this.seq = i;
        this.bytes = bytes;
    }

    public DataPacket(int i, byte[] bArr, int i2, int i3) {
        this(i, new Packet.Bytes(bArr, i2, i3));
    }

    public int getSeq() {
        return this.seq;
    }

    public int getDataLength() {
        return this.bytes.getSize();
    }

    public void setLastFrame() {
        this.bytes.end -= 2;
        this.crc = ByteUtils.get(this.bytes.value, this.bytes.end, 2);
    }

    public byte[] getCrc() {
        return this.crc;
    }

    @Override // com.inuker.bluetooth.library.channel.packet.Packet
    public byte[] toBytes() {
        ByteBuffer byteBufferAllocate;
        int dataLength = getDataLength() + 2;
        if (dataLength == 20) {
            Arrays.fill(BUFFER, (byte) 0);
            byteBufferAllocate = ByteBuffer.wrap(BUFFER);
        } else {
            byteBufferAllocate = ByteBuffer.allocate(dataLength);
        }
        byteBufferAllocate.putShort((short) this.seq);
        fillByteBuffer(byteBufferAllocate);
        return byteBufferAllocate.array();
    }

    public void fillByteBuffer(ByteBuffer byteBuffer) {
        byteBuffer.put(this.bytes.value, this.bytes.start, getDataLength());
    }

    public String toString() {
        return "DataPacket{seq=" + this.seq + ", size=" + this.bytes.getSize() + '}';
    }
}
