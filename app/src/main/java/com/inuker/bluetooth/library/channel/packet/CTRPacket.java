package com.inuker.bluetooth.library.channel.packet;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes2.dex */
public class CTRPacket extends Packet {
    private int frameCount;

    @Override // com.inuker.bluetooth.library.channel.packet.Packet
    public String getName() {
        return Packet.CTR;
    }

    public CTRPacket(int i) {
        this.frameCount = i;
    }

    public int getFrameCount() {
        return this.frameCount;
    }

    @Override // com.inuker.bluetooth.library.channel.packet.Packet
    public byte[] toBytes() {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(BUFFER);
        byteBufferWrap.putShort((short) 0);
        byteBufferWrap.put((byte) 0);
        byteBufferWrap.put((byte) 0);
        byteBufferWrap.putShort((short) this.frameCount);
        return byteBufferWrap.array();
    }

    public String toString() {
        return "FlowPacket{frameCount=" + this.frameCount + '}';
    }
}
