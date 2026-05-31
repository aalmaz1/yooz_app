package com.inuker.bluetooth.library.channel;

/* JADX INFO: loaded from: classes2.dex */
public interface IChannel {
    void onRead(byte[] bArr);

    void onRecv(byte[] bArr);

    void send(byte[] bArr, ChannelCallback channelCallback);

    void write(byte[] bArr, ChannelCallback channelCallback);
}
