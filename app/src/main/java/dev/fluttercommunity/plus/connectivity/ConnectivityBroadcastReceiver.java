package dev.fluttercommunity.plus.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Handler;
import android.os.Looper;
import io.flutter.plugin.common.EventChannel;

/* JADX INFO: loaded from: classes2.dex */
public class ConnectivityBroadcastReceiver extends BroadcastReceiver implements EventChannel.StreamHandler {
    public static final String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private final Connectivity connectivity;
    private final Context context;
    private EventChannel.EventSink events;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private ConnectivityManager.NetworkCallback networkCallback;

    public ConnectivityBroadcastReceiver(Context context, Connectivity connectivity) {
        this.context = context;
        this.connectivity = connectivity;
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onListen(Object obj, EventChannel.EventSink eventSink) {
        this.events = eventSink;
        this.networkCallback = new ConnectivityManager.NetworkCallback() { // from class: dev.fluttercommunity.plus.connectivity.ConnectivityBroadcastReceiver.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                ConnectivityBroadcastReceiver.this.sendEvent();
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                ConnectivityBroadcastReceiver.this.sendEvent("none");
            }
        };
        this.connectivity.getConnectivityManager().registerDefaultNetworkCallback(this.networkCallback);
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onCancel(Object obj) {
        if (this.networkCallback != null) {
            this.connectivity.getConnectivityManager().unregisterNetworkCallback(this.networkCallback);
            this.networkCallback = null;
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        EventChannel.EventSink eventSink = this.events;
        if (eventSink != null) {
            eventSink.success(this.connectivity.getNetworkType());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEvent() {
        this.mainHandler.post(new Runnable() { // from class: dev.fluttercommunity.plus.connectivity.ConnectivityBroadcastReceiver$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m651x91aa8d91();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$sendEvent$0$dev-fluttercommunity-plus-connectivity-ConnectivityBroadcastReceiver, reason: not valid java name */
    /* synthetic */ void m651x91aa8d91() {
        this.events.success(this.connectivity.getNetworkType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendEvent(final String str) {
        this.mainHandler.post(new Runnable() { // from class: dev.fluttercommunity.plus.connectivity.ConnectivityBroadcastReceiver$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m652xca8aee30(str);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$sendEvent$1$dev-fluttercommunity-plus-connectivity-ConnectivityBroadcastReceiver, reason: not valid java name */
    /* synthetic */ void m652xca8aee30(String str) {
        this.events.success(str);
    }
}
