package com.inuker.bluetooth.library.channel.packet;

/* JADX INFO: loaded from: classes2.dex */
public class InvalidPacket extends Packet {
    @Override // com.inuker.bluetooth.library.channel.packet.Packet
    public String getName() {
        return "invalid";
    }

    @Override // com.inuker.bluetooth.library.channel.packet.Packet
    public byte[] toBytes() {
        return new byte[0];
    }

    public String toString() {
        return "InvalidPacket{}";
    }
}
