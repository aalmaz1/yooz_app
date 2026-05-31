package com.inuker.bluetooth.library.channel;

/* JADX INFO: loaded from: classes2.dex */
public class ChannelStateBlock {
    public ChannelEvent event;
    public IChannelStateHandler handler;
    public ChannelState state;

    public ChannelStateBlock(ChannelState channelState, ChannelEvent channelEvent, IChannelStateHandler iChannelStateHandler) {
        this.state = channelState;
        this.event = channelEvent;
        this.handler = iChannelStateHandler;
    }
}
