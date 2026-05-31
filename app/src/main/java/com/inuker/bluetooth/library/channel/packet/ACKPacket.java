package com.inuker.bluetooth.library.channel.packet;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes2.dex */
public class ACKPacket extends Packet {
    public static final int BUSY = 2;
    public static final int CANCEL = 4;
    public static final int READY = 1;
    public static final int SUCCESS = 0;
    public static final int SYNC = 5;
    public static final int TIMEOUT = 3;
    private int seq;
    private int status;

    @Override // com.inuker.bluetooth.library.channel.packet.Packet
    public String getName() {
        return Packet.ACK;
    }

    public ACKPacket(int i) {
        this(i, 0);
    }

    public ACKPacket(int i, int i2) {
        this.status = i;
        this.seq = i2;
    }

    public int getStatus() {
        return this.status;
    }

    public int getSeq() {
        return this.seq;
    }

    @Override // com.inuker.bluetooth.library.channel.packet.Packet
    public byte[] toBytes() {
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(BUFFER);
        byteBufferWrap.putShort((short) 0);
        byteBufferWrap.put((byte) 1);
        byteBufferWrap.put((byte) 0);
        byteBufferWrap.putShort((short) this.status);
        byteBufferWrap.putShort((short) this.seq);
        return byteBufferWrap.array();
    }

    public String toString() {
        return "ACKPacket{status=" + getStatusDesc(this.status) + ", seq=" + this.seq + '}';
    }

    private String getStatusDesc(int i) {
        for (Field field : getClass().getDeclaredFields()) {
            if ((field.getModifiers() & 24) > 0) {
                try {
                    if (field.get(null) == Integer.valueOf(i)) {
                        return field.getName();
                    }
                    continue;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return i + "";
    }
}
