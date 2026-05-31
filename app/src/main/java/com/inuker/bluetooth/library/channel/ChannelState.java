package com.inuker.bluetooth.library.channel;

/* JADX INFO: loaded from: classes2.dex */
public enum ChannelState {
    IDLE,
    READY,
    WAIT_START_ACK,
    WRITING,
    SYNC,
    SYNC_ACK,
    SYNC_WAIT_PACKET,
    READING
}
