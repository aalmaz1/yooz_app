package com.inuker.bluetooth.library.channel.packet;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes2.dex */
public abstract class Packet {
    public static final String ACK = "ack";
    static final byte[] BUFFER = new byte[20];
    static final int BUFFER_SIZE = 20;
    public static final String CTR = "ctr";
    public static final String DATA = "data";
    static final int SN_CTR = 0;
    public static final int TYPE_ACK = 1;
    public static final int TYPE_CMD = 0;

    public abstract String getName();

    public abstract byte[] toBytes();

    private static class Header {
        int command;
        int parameter;
        int sn;
        int type;
        byte[] value;

        private Header() {
        }
    }

    static class Bytes {
        int end;
        int start;
        byte[] value;

        Bytes(byte[] bArr, int i) {
            this(bArr, i, bArr.length);
        }

        Bytes(byte[] bArr, int i, int i2) {
            this.value = bArr;
            this.start = i;
            this.end = i2;
        }

        int getSize() {
            return this.end - this.start;
        }
    }

    private static Header parse(byte[] bArr) {
        Header header = new Header();
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bArr);
        header.sn = byteBufferWrap.getShort();
        header.value = bArr;
        if (header.sn == 0) {
            header.type = byteBufferWrap.get();
            header.command = byteBufferWrap.get();
            header.parameter = byteBufferWrap.getInt();
        }
        return header;
    }

    public static Packet getPacket(byte[] bArr) {
        Header header = parse(bArr);
        if (header.sn == 0) {
            return getFlowPacket(header);
        }
        return getDataPacket(header);
    }

    private static Packet getFlowPacket(Header header) {
        int i = header.parameter;
        int i2 = header.type;
        if (i2 == 0) {
            return new CTRPacket(i >> 16);
        }
        if (i2 == 1) {
            return new ACKPacket(i >> 16, i & 65535);
        }
        return new InvalidPacket();
    }

    private static Packet getDataPacket(Header header) {
        return new DataPacket(header.sn, new Bytes(header.value, 2));
    }
}
