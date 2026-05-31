package com.baseflow.geolocator;

import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;
import com.baseflow.geolocator.location.LocationServiceStatusReceiver;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;

/* JADX INFO: loaded from: classes.dex */
public class LocationServiceHandlerImpl implements EventChannel.StreamHandler {
    private static final String TAG = "LocationServiceHandler";
    private EventChannel channel;
    private Context context;
    private LocationServiceStatusReceiver receiver;

    void startListening(Context context, BinaryMessenger binaryMessenger) {
        if (this.channel != null) {
            Log.w(TAG, "Setting a event call handler before the last was disposed.");
            stopListening();
        }
        EventChannel eventChannel = new EventChannel(binaryMessenger, "flutter.baseflow.com/geolocator_service_updates_android");
        this.channel = eventChannel;
        eventChannel.setStreamHandler(this);
        this.context = context;
    }

    void stopListening() {
        if (this.channel == null) {
            return;
        }
        disposeListeners();
        this.channel.setStreamHandler(null);
        this.channel = null;
    }

    void setContext(Context context) {
        this.context = context;
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onListen(Object obj, EventChannel.EventSink eventSink) {
        if (this.context == null) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter("android.location.PROVIDERS_CHANGED");
        intentFilter.addAction("android.intent.action.PROVIDER_CHANGED");
        LocationServiceStatusReceiver locationServiceStatusReceiver = new LocationServiceStatusReceiver(eventSink);
        this.receiver = locationServiceStatusReceiver;
        this.context.registerReceiver(locationServiceStatusReceiver, intentFilter);
    }

    @Override // io.flutter.plugin.common.EventChannel.StreamHandler
    public void onCancel(Object obj) {
        disposeListeners();
    }

    private void disposeListeners() {
        LocationServiceStatusReceiver locationServiceStatusReceiver;
        Context context = this.context;
        if (context == null || (locationServiceStatusReceiver = this.receiver) == null) {
            return;
        }
        context.unregisterReceiver(locationServiceStatusReceiver);
    }
}
